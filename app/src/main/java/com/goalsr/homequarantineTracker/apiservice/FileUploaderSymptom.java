package com.goalsr.homequarantineTracker.apiservice;

import android.os.Handler;
import android.os.Looper;

import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.resposemodel.hwSymtommaker.ReqHWSymtomAdd;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.ResSymptomInsertUpdate;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.Respatientinsertupdate;
import com.google.gson.JsonElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by RTLPC3 on 4/9/2019.
 */

public class FileUploaderSymptom {

    public FileUploaderCallback fileUploaderCallback;
    private ArrayList<ReqHWSymtomAdd> files;
    public int uploadIndex = -1;
    private String uploadURL = "";
    private long totalFileLength = 0;
    private long totalFileUploaded = 0;
    private String filekey="";
    private UploadInterface uploadInterface;
    private String auth_token = "";
    private ArrayList<ResSymptomInsertUpdate> responses;


    private interface UploadInterface {

        /*@Multipart
        @POST
        Call<JsonElement> uploadFile(@Url String url, @Part MultipartBody.Part file, @Header("Authorization") String authorization);*/

        @Multipart
        @POST("WebApi_Hwatch/api/HealthWatch/Multipart_UpdatePrimaryPatientSymptoms")
        Call<ResSymptomInsertUpdate> uploadFilewithsymptom(@Part MultipartBody.Part file,
                                                           @Part("imageName") RequestBody imageName,
                                                           @Part("MobileImages") RequestBody MobileImages,
                                                           @Part("localID") RequestBody localID,
                                                           @Part("citizenID") RequestBody citizenID,
                                                           @Part("familyMemberID") RequestBody familyMemberID,
                                                           @Part("symptoms") RequestBody symptoms,
                                                           @Part("dateTime") RequestBody dateTime,
                                                           @Part("latitude") RequestBody latitude,
                                                           @Part("longitude") RequestBody longitude,
                                                           @Part("p_security_name") RequestBody p_security_name,
                                                           @Part("p_security_passphrase") RequestBody p_security_passphrase,
                                                           @Part("role_id") RequestBody roleid,
                                                           @Part("userID") RequestBody userid
                                                          );

        @Multipart
        @POST("WebApi_Hwatch/api/HealthWatch/Multipart_UpdatePatientFamilySymptoms")
        Call<ResSymptomInsertUpdate> uploadFilewithsymptomfamily(@Part MultipartBody.Part file,
                                                           @Part("imageName") RequestBody imageName,
                                                           @Part("MobileImages") RequestBody MobileImages,
                                                           @Part("localID") RequestBody localID,
                                                           @Part("citizenID") RequestBody citizenID,
                                                           @Part("familyMemberID") RequestBody familyMemberID,
                                                           @Part("symptoms") RequestBody symptoms,
                                                           @Part("dateTime") RequestBody dateTime,
                                                           @Part("latitude") RequestBody latitude,
                                                           @Part("longitude") RequestBody longitude,
                                                           @Part("p_security_name") RequestBody p_security_name,
                                                           @Part("p_security_passphrase") RequestBody p_security_passphrase,
                                                           @Part("role_id") RequestBody roleid,
                                                           @Part("userID") RequestBody userid
        );
    }

    public interface FileUploaderCallback{
        void onError();
        void onFinish(ArrayList<ResSymptomInsertUpdate> responses);
        void onProgressUpdate(int currentpercent, int totalpercent, int filenumber);
    }

    public class PRRequestBody extends RequestBody {
        private File mFile;

        private static final int DEFAULT_BUFFER_SIZE = 2048;

        public PRRequestBody(final File file) {
            mFile = file;

        }

        @Override
        public MediaType contentType() {
            // i want to upload only images
            return MediaType.parse("*/*");
        }

        @Override
        public long contentLength() throws IOException {
            return mFile.length();
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            long fileLength = mFile.length();
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            FileInputStream in = new FileInputStream(mFile);
            long uploaded = 0;

            try {
                int read;
                Handler handler = new Handler(Looper.getMainLooper());
                while ((read = in.read(buffer)) != -1) {

                    // update progress on UI thread
                    handler.post(new ProgressUpdater(uploaded, fileLength));
                    uploaded += read;
                    sink.write(buffer, 0, read);
                }
            } finally {
                in.close();
            }
        }
    }

    public FileUploaderSymptom(){
        uploadInterface = ApiClient.getClient().create(UploadInterface.class);
    }

    public void uploadFiles(String url, String filekey, ArrayList<ReqHWSymtomAdd> files, FileUploaderCallback fileUploaderCallback){
        uploadFiles(url,filekey,files,fileUploaderCallback,"");
    }

    public void uploadFiles(String url, String filekey, ArrayList<ReqHWSymtomAdd> files, FileUploaderCallback fileUploaderCallback, String auth_token){
        this.fileUploaderCallback = fileUploaderCallback;
        this.files = files;
        this.uploadIndex = -1;
        this.uploadURL = url;
        this.filekey = filekey;
        this.auth_token = auth_token;
        totalFileUploaded = 0;
        totalFileLength = 0;
        uploadIndex = -1;
        responses = new ArrayList<>();
        for(int i=0; i<files.size(); i++){
            totalFileLength = totalFileLength + files.size();
        }
        uploadNext();
    }

    private void uploadNext(){
        if(files.size()>0){
            if(uploadIndex!= -1)
                totalFileUploaded = totalFileUploaded + files.size();
            uploadIndex++;
            if(uploadIndex < files.size()){
                uploadSingleFile(uploadIndex);
            }else{
                fileUploaderCallback.onFinish(responses);
            }
        }else{
           fileUploaderCallback.onFinish(responses);
        }
    }

    private void uploadSingleFile(final int index){
        File sendfile=new File(files.get(index).getImageFilePath());
        PRRequestBody fileBody = new PRRequestBody(sendfile);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("File", files.get(index).getImageName(), fileBody);
        Call<ResSymptomInsertUpdate> call=null;
        /*if(auth_token.isEmpty()){
           call  = uploadInterface.uploadFilewithsymptom(*//*uploadURL,*//* filePart);
        }else{
            call  = uploadInterface.uploadFile(uploadURL, filePart, auth_token);
        }*/


        String p_security_name="BhoomiWapi@2020";
        String p_security_passphrase="c2a2b557-c792-48f9-9ccd-56fda45974b9";
        int role_id= PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.ROLL_ID);
        String userID="PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.USER_ID_login)";

//        call=uploadInterface.uploadFilewithsymptom(filePart,files.get(index).getImageName(),"mobileimage",)
        RequestBody filename = RequestBody.create(files.get(index).getImageName(),MediaType.parse("text/plain"));
        RequestBody mobileimage = RequestBody.create("mobileimage",MediaType.parse("text/plain"));
        RequestBody localid = RequestBody.create(files.get(index).getLocalID(),MediaType.parse("text/plain"));
        RequestBody citizenid = RequestBody.create(files.get(index).getCitizenID()+"",MediaType.parse("text/plain"));
        RequestBody familymemberid = RequestBody.create(files.get(index).getFamilyMemberID()+"",MediaType.parse("text/plain"));
        RequestBody strsymptom = RequestBody.create(files.get(index).getSymptoms()+"",MediaType.parse("text/plain"));
        RequestBody datetime = RequestBody.create(files.get(index).getDateTime(),MediaType.parse("text/plain"));
        RequestBody latitude = RequestBody.create(files.get(index).getLatitude()+"",MediaType.parse("text/plain"));
        RequestBody longitude = RequestBody.create(files.get(index).getLongitude()+"",MediaType.parse("text/plain"));
        RequestBody psecurity = RequestBody.create(p_security_name,MediaType.parse("text/plain"));
        RequestBody psecuritypass = RequestBody.create(p_security_passphrase,MediaType.parse("text/plain"));
        RequestBody roleid = RequestBody.create(""+role_id,MediaType.parse("text/plain"));
        RequestBody userid = RequestBody.create(userID,MediaType.parse("text/plain"));

        if (files.get(index).getTypeofpatient().equalsIgnoreCase("self")){
            call  = uploadInterface.uploadFilewithsymptom(/*uploadURL,*/ filePart,filename,
                    mobileimage,localid,citizenid,familymemberid
                    ,strsymptom,datetime,latitude,longitude,psecurity,psecuritypass,roleid,userid);
        }else if (files.get(index).getTypeofpatient().equalsIgnoreCase("family")){
            call  = uploadInterface.uploadFilewithsymptomfamily(/*uploadURL,*/ filePart,filename,
                    mobileimage,localid,citizenid,familymemberid
                    ,strsymptom,datetime,latitude,longitude,psecurity,psecuritypass,roleid,userid);
        }

      /*  call  = uploadInterface.uploadFilewithsymptom(*//*uploadURL,*//* filePart,filename,
                mobileimage,localid,citizenid,familymemberid
        ,strsymptom,datetime,latitude,longitude,psecurity,psecuritypass,roleid,userid);*/
        call.enqueue(new Callback<ResSymptomInsertUpdate>() {
            @Override
            public void onResponse(Call<ResSymptomInsertUpdate> call, retrofit2.Response<ResSymptomInsertUpdate> response) {
                /*if (response.isSuccessful()) {
                   // JsonElement jsonElement = response.body();
                    responses.add(response.body());

                }else{
                    responses[index] = "";
                }*/
                responses.add(response.body());
                uploadNext();
            }

            @Override
            public void onFailure(Call<ResSymptomInsertUpdate> call, Throwable t) {
                fileUploaderCallback.onError();
            }
        });
    }

    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;
        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            int current_percent = (int)(100 * mUploaded / mTotal);
            int total_percent = (int)(100 * (totalFileUploaded+mUploaded) / totalFileLength);
            fileUploaderCallback.onProgressUpdate(current_percent, total_percent,uploadIndex+1 );
        }
    }
}

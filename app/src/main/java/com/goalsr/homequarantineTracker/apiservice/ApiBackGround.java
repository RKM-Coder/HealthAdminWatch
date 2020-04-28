package com.goalsr.homequarantineTracker.apiservice;

import android.content.Context;
import android.util.Log;

import com.goalsr.homequarantineTracker.Utils.AppConstants;
import com.goalsr.homequarantineTracker.Utils.FileUploader;
import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.db.model.QHTracker;
import com.goalsr.homequarantineTracker.db.repository.HWPatientFamilyinfoRepository;
import com.goalsr.homequarantineTracker.db.repository.HWPatientinfoRepository;
import com.goalsr.homequarantineTracker.db.repository.SymptoAddRepository;
import com.goalsr.homequarantineTracker.db.repository.TravelTrackingRepository;
import com.goalsr.homequarantineTracker.resposemodel.HWSecurity.HealthWPSecurity;
import com.goalsr.homequarantineTracker.resposemodel.ReqHeader;
import com.goalsr.homequarantineTracker.resposemodel.ReqTrailer;
import com.goalsr.homequarantineTracker.resposemodel.emergency.ReqBodyEmergency;
import com.goalsr.homequarantineTracker.resposemodel.emergency.ReqEmegency;
import com.goalsr.homequarantineTracker.resposemodel.hwSymtommaker.ReqHWSymtomAdd;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientFamilyDetailsItem;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientListDataItem;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.ReqInsertUpdatePatientFamilyInfo;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.ReqInsertUpdatePatientInfo;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.ResSymptomInsertUpdate;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.Respatientinsertupdate;
import com.goalsr.homequarantineTracker.resposemodel.poststatus.ReqStatus;
import com.goalsr.homequarantineTracker.resposemodel.poststatus.ReqSymtomBody;
import com.goalsr.homequarantineTracker.resposemodel.poststatus.UserTracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ApiBackGround {

    boolean isApicall=true;

    private Context mContext;
    TravelTrackingRepository travelTrackingRepository;
    private NetworkService networkService;
    private HWPatientinfoRepository hwPatientinfoRepository;
    private HWPatientFamilyinfoRepository hwPatientFamilyinfoRepository;
    private SymptoAddRepository symptoAddRepository;
    OnSyncResponse onSyncResponse;
    public ApiBackGround(Context mContext) {
        this.mContext = mContext;
        travelTrackingRepository=new TravelTrackingRepository(mContext);
        hwPatientinfoRepository=new HWPatientinfoRepository(mContext);
        symptoAddRepository=new SymptoAddRepository(mContext);
        hwPatientFamilyinfoRepository=new HWPatientFamilyinfoRepository(mContext);

        initMvp();
    }

    public void setOnSyncResponse(OnSyncResponse onSyncResponse){
        this.onSyncResponse=onSyncResponse;
    }

    public interface OnSyncResponse {
        void onSyncSccess();

        void onFail();
    }

    private void initMvp() {
        networkService = new NetworkService();
        networkService.inject(mContext);
    }

    public void makesyncCall(){

        makePatientAddedForSync();

    }



    public void makePatientAddedForSync(){

        ReqInsertUpdatePatientInfo reqInsertUpdatePatientInfo=new ReqInsertUpdatePatientInfo();
        reqInsertUpdatePatientInfo.setPrimary_patient_information(getListOfPatientInsertUpdateInfo());
        reqInsertUpdatePatientInfo.setUser_id(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.USER_ID_login));
        reqInsertUpdatePatientInfo.setRole_id(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.ROLL_ID));
        reqInsertUpdatePatientInfo.setP_security(AppConstants.getHealthWatchSecurityObjectupdated());

        networkService.makeHWPatientInfoinsertupdate(reqInsertUpdatePatientInfo, new NetworkService.NetworkServiceListener() {
            @Override
            public void onFailure(Object response) {
                if (onSyncResponse!=null){
                    onSyncResponse.onFail();
                }

            }

            @Override
            public void onAuthFail(Object error) {

            }

            @Override
            public void onSuccess(Object response, Boolean cancelFlag) {
                makePatientFamilyAddedforsyncNONEmptyFamilyID();

            }
        });

    }




    public void makePatientAdded(){

        ReqInsertUpdatePatientInfo reqInsertUpdatePatientInfo=new ReqInsertUpdatePatientInfo();
        reqInsertUpdatePatientInfo.setPrimary_patient_information(getListOfPatientInsertUpdateInfo());
        reqInsertUpdatePatientInfo.setUser_id(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.USER_ID_login));
        reqInsertUpdatePatientInfo.setRole_id(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.ROLL_ID));
        reqInsertUpdatePatientInfo.setP_security(AppConstants.getHealthWatchSecurityObjectupdated());

        networkService.makeHWPatientInfoinsertupdate(reqInsertUpdatePatientInfo,null);

    }
    public void makePatientFamilyAddedforsyncNONEmptyFamilyID(){

        ReqInsertUpdatePatientFamilyInfo reqInsertUpdatePatientInfo=new ReqInsertUpdatePatientFamilyInfo();
        reqInsertUpdatePatientInfo.setPatient_family_information(getListOfPatientFamilyInsertUpdateInfoNonEmptydata());
        reqInsertUpdatePatientInfo.setUser_id(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.USER_ID_login));
        reqInsertUpdatePatientInfo.setRole_id(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.ROLL_ID));
        reqInsertUpdatePatientInfo.setP_security(AppConstants.getHealthWatchSecurityObjectupdated());

        networkService.makeHWPatientFamilyInfoinsertupdate(reqInsertUpdatePatientInfo, new NetworkService.NetworkServiceListener() {
            @Override
            public void onFailure(Object response) {
                if (onSyncResponse!=null){
                    onSyncResponse.onFail();
                }
            }

            @Override
            public void onAuthFail(Object error) {

            }

            @Override
            public void onSuccess(Object response, Boolean cancelFlag) {
                makePatientFamilyAddedWithEmptyvalue();
                /*if (response instanceof  Respatientinsertupdate){
                    if (((Respatientinsertupdate) response).getStatusCode()==200){


                    }
                }*/

            }
        });

    }

    public void makePatientFamilyAddedWithEmptyvalue(){

        ReqInsertUpdatePatientFamilyInfo reqInsertUpdatePatientInfo=new ReqInsertUpdatePatientFamilyInfo();
        reqInsertUpdatePatientInfo.setPatient_family_information(getListOfPatientFamilyInsertUpdateInfoEmptyFid());
        reqInsertUpdatePatientInfo.setUser_id(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.USER_ID_login));
        reqInsertUpdatePatientInfo.setRole_id(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.ROLL_ID));
        reqInsertUpdatePatientInfo.setP_security(AppConstants.getHealthWatchSecurityObjectupdated());

        networkService.makeHWPatientFamilyInfoinsertupdate(reqInsertUpdatePatientInfo, new NetworkService.NetworkServiceListener() {
            @Override
            public void onFailure(Object response) {

            }

            @Override
            public void onAuthFail(Object error) {

            }

            @Override
            public void onSuccess(Object response, Boolean cancelFlag) {
                makeFileUploadPatientInfo();
                /*if (response instanceof  Respatientinsertupdate){
                    if (((Respatientinsertupdate) response).getStatusCode()==200){

                    }
                }*/
            }
        });

    }


    public void makePatientFamilyAdded(){

        ReqInsertUpdatePatientFamilyInfo reqInsertUpdatePatientInfo=new ReqInsertUpdatePatientFamilyInfo();
        reqInsertUpdatePatientInfo.setPatient_family_information(getListOfPatientFamilyInsertUpdateInfo());
        reqInsertUpdatePatientInfo.setRole_id(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.ROLL_ID));
        reqInsertUpdatePatientInfo.setP_security(AppConstants.getHealthWatchSecurityObjectupdated());

        networkService.makeHWPatientFamilyInfoinsertupdate(reqInsertUpdatePatientInfo,null);

    }

    public void makeFileUploadPatientInfo(){

        List<ReqHWSymtomAdd> symtopmWithImageList=new ArrayList<>();
        symtopmWithImageList.addAll(getSymtomWithImagePatient());
        if (symtopmWithImageList!=null)
            if (symtopmWithImageList.size()>0){
                FileUploaderSymptom fileUploaderSymptom=new FileUploaderSymptom();
                fileUploaderSymptom.uploadFiles("", "", (ArrayList<ReqHWSymtomAdd>) symtopmWithImageList, new FileUploaderSymptom.FileUploaderCallback() {
                    @Override
                    public void onError() {
                        if (onSyncResponse!=null){
                            onSyncResponse.onFail();
                        }
                    }

                    @Override
                    public void onFinish(ArrayList<ResSymptomInsertUpdate> responses) {

                        if (responses.size()>0){
                            for (ResSymptomInsertUpdate item:responses){
                                if (item.getStatusCode()==200){
                                    Log.e("Resultservice", item.getPatientupdatedata().getLocalID() );

                                    symptoAddRepository.clearbyID(item.getPatientupdatedata().getLocalID());
                                }
                            }

                        }
                        makeFileUpload();

                    }

                    @Override
                    public void onProgressUpdate(int currentpercent, int totalpercent, int filenumber) {

                    }
                });
               /* for (ReqHWSymtomAdd item:symtopmWithImageList){
                    FileUploaderSymptom fileUploaderSymptom=new FileUploaderSymptom();
                    fileUploaderSymptom.uploadFiles();
                }*/
            }else {
                makeFileUpload();
            }

        //networkService.makeHWPatientFamilyInfoinsertupdate(reqInsertUpdatePatientInfo,null);

    }
    public void makeFileUpload(){

       List<ReqHWSymtomAdd> symtopmWithImageList=new ArrayList<>();
        symtopmWithImageList.addAll(getSymtomWithImage());
        if (symtopmWithImageList!=null)
            if (symtopmWithImageList.size()>0){
                FileUploaderSymptom fileUploaderSymptom=new FileUploaderSymptom();
                fileUploaderSymptom.uploadFiles("", "", (ArrayList<ReqHWSymtomAdd>) symtopmWithImageList, new FileUploaderSymptom.FileUploaderCallback() {
                    @Override
                    public void onError() {

                    }

                    @Override
                    public void onFinish(ArrayList<ResSymptomInsertUpdate> responses) {

                        if (responses.size()>0){
                            for (ResSymptomInsertUpdate item:responses){
                                if (item.getStatusCode()==200){
                                    Log.e("Resultservice", item.getPatientupdatedata().getLocalID() );
                                    symptoAddRepository.clearbyID(item.getPatientupdatedata().getLocalID());
                                }
                            }

                        }
                        if (onSyncResponse!=null){
                            onSyncResponse.onSyncSccess();
                        }



                    }

                    @Override
                    public void onProgressUpdate(int currentpercent, int totalpercent, int filenumber) {

                    }
                });
               /* for (ReqHWSymtomAdd item:symtopmWithImageList){
                    FileUploaderSymptom fileUploaderSymptom=new FileUploaderSymptom();
                    fileUploaderSymptom.uploadFiles();
                }*/
            }else {
                if (onSyncResponse != null) {
                    onSyncResponse.onSyncSccess();
                }
            }

        //networkService.makeHWPatientFamilyInfoinsertupdate(reqInsertUpdatePatientInfo,null);

    }

    private Collection<? extends ReqHWSymtomAdd> getSymtomWithImage() {
        return symptoAddRepository.getListAllItemByAdmin();
    }
    private Collection<? extends ReqHWSymtomAdd> getSymtomWithImagePatient() {
        return symptoAddRepository.getListAllItemByAdminPatient();
    }


    private List<PatientListDataItem> getListOfPatientInsertUpdateInfo() {
        return hwPatientinfoRepository.getListAllItemByAdminNONSYNC();
    }
    private List<PatientFamilyDetailsItem> getListOfPatientFamilyInsertUpdateInfo() {
        return hwPatientFamilyinfoRepository.getPatientFamilyInfoNONSYNC();
    }

    private List<PatientFamilyDetailsItem> getListOfPatientFamilyInsertUpdateInfoNonEmptydata() {
        return hwPatientFamilyinfoRepository.getPatientFamilyInfoNONSYNCNonEmpty();
    }

    private List<PatientFamilyDetailsItem> getListOfPatientFamilyInsertUpdateInfoEmptyFid() {
        return hwPatientFamilyinfoRepository.getPatientFamilyInfoNONSYNCEmpty();
    }

    private Collection<? extends ReqHWSymtomAdd> getSymtomWithImageNonEmptyCidAndFiD() {
        return symptoAddRepository.getListAllItemByAdmin();
    }

    private List<PatientListDataItem> getListOfPatientInsertUpdateInfoNonEmptyCID() {
        return hwPatientinfoRepository.getListAllItemByAdminNONSYNC();
    }
    private List<PatientFamilyDetailsItem> getListOfPatientFamilyInsertUpdateInfoNoEmptyFMID() {
        return hwPatientFamilyinfoRepository.getPatientFamilyInfoNONSYNC();
    }
   /* public HealthWPSecurity getHealthWatchSecurityObject(){
        HealthWPSecurity securityObject =new HealthWPSecurity();
        securityObject.setName("BhoomiWapi@2020");
        securityObject.setPassphrase("c2a2b557-c792-48f9-9ccd-56fda45974b9");

        return securityObject;
    }*/




    public void uploadsync(boolean b){

        ArrayList<QHTracker> list=new ArrayList<>();
        ArrayList<ReqSymtomBody> reqSymtomBodylist=new ArrayList<>();
        if (travelTrackingRepository.getTravelListNonSync().size()>0) {
            list.addAll(travelTrackingRepository.getTravelListNonSync());
            reqSymtomBodylist.addAll(getreqListObject(list));
        }

        ReqStatus synstatus=new ReqStatus();
        UserTracker userTracker=new UserTracker();
        userTracker.setUserTrackData(reqSymtomBodylist);
        synstatus.setBody(userTracker);
        synstatus.setHeader(new ReqHeader());
        synstatus.setTrailer(new ReqTrailer());

        //networkService.makestatusupdate(synstatus,b,null);
       // uploadtoServer();
    }
    private void uploadtoServer() {
        ArrayList<String> ll=new ArrayList<String>();
        ll.addAll(travelTrackingRepository.getTravelListNonSyncImage());
        if (ll.size()>0) {
            new FileUploader().uploadFiles(YelligoApplication.getContext(), ll, "", new FileUploader.FileUploaderCallback() {
                @Override
                public void onError() {
                    //hideProgressDialog();
                    // Toast.makeText(getActivity(),ConstantMessage.tryagain,Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFinish(String[] responses) {


                /*if (responses != null && responses.length > 0) {
                    for (int i = 0; i < responses.length; i++) {
                        TaskAttachment attachment = filesattachment.get(i);
                        attachment.setAttachment(responses[i]);
                        attachment.setSyncstatus(true);
                        filesattachment.add(attachment);
                    }
                    taskAttachmentViewModel.UpdateItem(filesattachment);
                }*/

                }

                @Override
                public void onProgressUpdate(int currentpercent, int totalpercent, int filenumber) {

                }
            });
        }
    }

    public void makeEmaergencyReqOut() {

        ReqEmegency reqEmegency=new ReqEmegency();
        ReqBodyEmergency reqBodyEmergency=new ReqBodyEmergency();
        reqBodyEmergency.setLatitude(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.USER_LATITUDE));
        reqBodyEmergency.setLongitude(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.USER_LONGITUDE));
       // reqBodyEmergency.setUserId(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID));
        reqBodyEmergency.setQuarantineStatus("2");
        reqEmegency.setBody(reqBodyEmergency);
        reqEmegency.setHeader(new ReqHeader());
        reqEmegency.setTrailer(new ReqTrailer());

        networkService.makeemergency(reqEmegency, null);


    }


    private ArrayList<ReqSymtomBody> getreqListObject(ArrayList<QHTracker> list) {
        ArrayList<ReqSymtomBody> reqSymtomBodylist=new ArrayList<>();
        for (QHTracker tracker:list){
            ReqSymtomBody body=new ReqSymtomBody();
           // body.setUserId(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID));
            body.setLocalId(tracker.getPrimary_id());
            body.setLatitude(tracker.getLocation_lat());
            body.setLongitude(tracker.getLocation_lng());
            body.setDateTime(tracker.getMcurrentdatetimejulian());
            body.setTypeOfTracker(tracker.getTypeofdatatracker());
            //if (tracker.isSyncstutas())
            body.setSyncStatus("1");
            body.setSyncStatusImage("1");
            body.setSelfie(tracker.getSelfifilepathserver());//TODO
            body.setSelfiePathLocal(tracker.getSelfifilepathlocal());//TODO
            body.setSymptoms(tracker.getInfodata());
            reqSymtomBodylist.add(body);
        }

        return reqSymtomBodylist;
    }

    public void makereturn() {
        boolean b=PreferenceStore.getPrefernceHelperInstace().getFlag(YelligoApplication.getContext(),PreferenceStore.LAST_OUTOFRADIOUS);
        if (b) {
            PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(), PreferenceStore.LAST_OUTOFRADIOUS, true);
            ReqEmegency reqEmegency=new ReqEmegency();
            ReqBodyEmergency reqBodyEmergency=new ReqBodyEmergency();
            reqBodyEmergency.setLatitude(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.USER_LATITUDE));
            reqBodyEmergency.setLongitude(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.USER_LONGITUDE));
            //reqBodyEmergency.setUserId(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID));
            reqEmegency.setBody(reqBodyEmergency);
            reqEmegency.setHeader(new ReqHeader());
            reqEmegency.setTrailer(new ReqTrailer());

            networkService.makeemergencyinside(reqEmegency, null);

        }

    }
}

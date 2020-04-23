package com.goalsr.homequarantineTracker.apiservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import com.goalsr.homequarantineTracker.BuildConfig;
import com.goalsr.homequarantineTracker.db.repository.HWPatientFamilyinfoRepository;
import com.goalsr.homequarantineTracker.db.repository.HWPatientinfoRepository;
import com.goalsr.homequarantineTracker.db.repository.PatientFamilyinfoRepository;
import com.goalsr.homequarantineTracker.db.repository.PatientinfoRepository;
import com.goalsr.homequarantineTracker.db.repository.SymptoAddRepository;
import com.goalsr.homequarantineTracker.db.repository.TravelTrackingRepository;
import com.goalsr.homequarantineTracker.resposemodel.FnInsertDOCCHNKResult;
import com.goalsr.homequarantineTracker.resposemodel.ReqGvtPatientFamillySymptom;
import com.goalsr.homequarantineTracker.resposemodel.ReqGvtPatientSymptom;
import com.goalsr.homequarantineTracker.resposemodel.ReqImageChunk;
import com.goalsr.homequarantineTracker.resposemodel.ReqPAtientInfoByAdmin;
import com.goalsr.homequarantineTracker.resposemodel.ResImage;
import com.goalsr.homequarantineTracker.resposemodel.ResSymtomChecker;
import com.goalsr.homequarantineTracker.resposemodel.emergency.ReqEmegency;
import com.goalsr.homequarantineTracker.resposemodel.emergency.ResEmergency;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ReqPatient;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ReqUpdatePatentIFamilynfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ReqUpdatePatentInfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientFamilyInfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfoByAdmin;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResUpdateInfo;

import com.goalsr.homequarantineTracker.resposemodel.gotOtpreq.ResGvtValidOtp;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientFamilyDetailsItem;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientListDataItem;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientupdatedataItem;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.ReqGetPatientinfobody;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.ReqInsertUpdatePatientFamilyInfo;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.ReqInsertUpdatePatientInfo;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.ResPatientData;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.Respatientinsertupdate;
import com.goalsr.homequarantineTracker.resposemodel.hwreqotp.ReqHWOtp;
import com.goalsr.homequarantineTracker.resposemodel.hwreqotp.ReqHWOtpValidate;
import com.goalsr.homequarantineTracker.resposemodel.hwreqotp.ResHWGetOtp;
import com.goalsr.homequarantineTracker.resposemodel.hwreqotp.ResOtpValidate;
import com.goalsr.homequarantineTracker.resposemodel.otpvalidGovt.ReqOtpValidGvt;
import com.goalsr.homequarantineTracker.resposemodel.otpvalidGovt.ResGvtValidOtpValid;
import com.goalsr.homequarantineTracker.resposemodel.poststatus.ReqStatus;
import com.goalsr.homequarantineTracker.resposemodel.poststatus.ResTracker;
import com.goalsr.homequarantineTracker.resposemodel.poststatus.TrackResponseItem;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkService {

    Context mContext;
    TravelTrackingRepository travelTrackingRepository;
    private PatientFamilyinfoRepository patientFamilyinfoRepository;
    private PatientinfoRepository patientinfoRepository;

    private HWPatientinfoRepository hwPatientinfoRepository;
    private HWPatientFamilyinfoRepository hwPatientFamilyinfoRepository;
    private SymptoAddRepository symptoAddRepository;


    public void inject(Context mContext) {
        this.mContext = mContext;
        travelTrackingRepository = new TravelTrackingRepository(mContext);
        patientFamilyinfoRepository = new PatientFamilyinfoRepository(mContext);
        patientinfoRepository = new PatientinfoRepository(mContext);

        /*HWatch*/
        hwPatientinfoRepository = new HWPatientinfoRepository(mContext);
        hwPatientFamilyinfoRepository = new HWPatientFamilyinfoRepository(mContext);
        symptoAddRepository=new SymptoAddRepository(mContext);
    }


    public interface NetworkServiceListener<T> {
        void onFailure(T response);

        void onAuthFail(T error);

        void onSuccess(T response, Boolean cancelFlag);
    }

    public boolean haveNetworkAccess() {
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo connection = manager.getActiveNetworkInfo();
            if (connection != null && connection.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }

    /*HEALTH WATCH*/


    // req otp
    public void makeHWReqgetOTTPLogin(ReqHWOtp request, NetworkServiceListener listener) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ResHWGetOtp> response = apiService.makeHWReqOtp(request);
        response.enqueue(new Callback<ResHWGetOtp>() {
            @Override
            public void onResponse(Call<ResHWGetOtp> call, Response<ResHWGetOtp> response) {


                if (response.isSuccessful()) {


                    if (listener != null) {
                        if (response.body().getStatus_code() == 200) {
                            listener.onSuccess(response.body(), true);

                        } else {

                        }
                    }


                } else if (response.code() == 401) {
//                    LoginResponse respObj = new LoginResponse();
//                    listener.onAuthFail(makeErrorResponse(respObj,""+response.code()));
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    if (listener != null) {
                        ResHWGetOtp res = new ResHWGetOtp();
                        listener.onFailure(makeErrorResponse(res, "" + error.message()));
                    }


                   /* if (BuildConfig.DEBUG) {
                        Log.d("error message", error.message());
                    }*/
                }
            }

            @Override
            public void onFailure(Call<ResHWGetOtp> call, Throwable t) {
                if (BuildConfig.DEBUG) {
                    //Log.d("error message", t.getMessage());
                    if (listener != null) {
                        ResHWGetOtp loginResponse = new ResHWGetOtp();
                        listener.onFailure(makeErrorResponse(loginResponse, "" + t.getMessage()));
                    }
                }
            }
        });
    }

    public void makeHWotpvalid(ReqHWOtpValidate request, NetworkServiceListener listener) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ResOtpValidate> response = apiService.makeHWReqOtpValidate(request);
        response.enqueue(new Callback<ResOtpValidate>() {
            @Override
            public void onResponse(Call<ResOtpValidate> call, Response<ResOtpValidate> response) {


                if (response.isSuccessful()) {

                    if (listener != null) {
                        if (response.body().getStatus_code() == 200) {
                            listener.onSuccess(response.body(), true);
                        } else {
                            listener.onFailure(response.body());
                        }

                    }

                } else if (response.code() == 401) {
//                    LoginResponse respObj = new LoginResponse();
//                    listener.onAuthFail(makeErrorResponse(respObj,""+response.code()));
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    if (listener != null) {
                        ResOtpValidate res = new ResOtpValidate();
                        listener.onFailure(makeErrorResponse(res, "" + error.message()));
                    }


                   /* if (BuildConfig.DEBUG) {
                        Log.d("error message", error.message());
                    }*/
                }
            }

            @Override
            public void onFailure(Call<ResOtpValidate> call, Throwable t) {
                if (BuildConfig.DEBUG) {
                    Log.d("error message", t.getMessage());
                    if (listener != null) {
                        ResOtpValidate loginResponse = new ResOtpValidate();
                        listener.onFailure(makeErrorResponse(loginResponse, "" + t.getMessage()));
                    }
                }
            }
        });
    }

    public void getHWPatientFamillyInfo(ReqGetPatientinfobody request, NetworkServiceListener listener) {
        /* String s=new Gson().toJson(request);*/
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ResPatientData> response = apiService.getHWPatientFamilyInfo(request);

        response.enqueue(new Callback<ResPatientData>() {
            @Override
            public void onResponse(Call<ResPatientData> call, Response<ResPatientData> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == 200) {
                        if (listener != null) {
                            if (response.body().getPatientListData().size() > 0) {

                                insetpatientdtatainDB(response,listener);

                            } else {
                                listener.onFailure("No result found");
                            }
                        }
                    } else {
                        listener.onFailure("" + response.body().getStatusMessaage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResPatientData> call, Throwable t) {
                if (listener != null) {
                    listener.onFailure("Something went wrong");
                }

            }
        });
    }

    private void insetpatientdtatainDB(Response<ResPatientData> dataResponse, NetworkServiceListener listener) {

        for (PatientListDataItem item : dataResponse.body().getPatientListData()) {
            hwPatientinfoRepository.insert(item);
            if (item.getPatientFamilyDetails().size() > 0) {
//                List<PatientFamilyDetailsItem> familyDetailsItems=new ArrayList<>();
                for (PatientFamilyDetailsItem itemfamily:item.getPatientFamilyDetails()){
                    itemfamily.setCitizenIDLocalId(item.getLocalID());
                    hwPatientFamilyinfoRepository.insertitem(itemfamily);
                }




            }
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listener!=null)
                listener.onSuccess(dataResponse.body(), true);
            }
        }, 4000);


    }

    public void makeHWPatientInfoinsertupdate(ReqInsertUpdatePatientInfo request, NetworkServiceListener listener) {
        Log.e("PATIENT UPDATE", "reqhit--"+request.getPrimary_patient_information().size());
        /*if (request.getPrimary_patient_information().size() > 0) {*/
            //String s = new Gson().toJson(request);
            //Log.e("DATA", s);
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<Respatientinsertupdate> response = apiService.makeHWinsertUpdatePatientInfo(request);

            response.enqueue(new Callback<Respatientinsertupdate>() {
                @Override
                public void onResponse(Call<Respatientinsertupdate> call, Response<Respatientinsertupdate> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200) {
                            if (response.body().getPatientupdatedata().size() > 0) {
                                for (PatientupdatedataItem item : response.body().getPatientupdatedata()) {
                                    Log.e("PATIENT UPDATE", item.getLocalID() + "-------------------" + item.getCitizenID());
                                    hwPatientinfoRepository.updatesyncdatainserupdatepatient(true, item.getLocalID(), item.getCitizenID());
                                    hwPatientFamilyinfoRepository.updatesyncdatainserupdateFamilyCID(true, item.getLocalID(), item.getCitizenID());
                                    symptoAddRepository.updatesyncdatainserupdatepatientByCitizenId(true, item.getLocalID(), item.getCitizenID());
                                }
                            }
                            if (listener!=null){
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        listener.onSuccess(response.body(), true);
                                    }
                                }, 2000);
                            }

                        }else if (response.body().getStatusCode() == 400) {
                            if (listener!=null){
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        listener.onSuccess(response.body(), true);
                                    }
                                }, 2000);
                            }
                        }



                    }else {
                        if (listener!=null){
                            listener.onFailure("Something went wrong");
                        }
                    }
                }

                @Override
                public void onFailure(Call<Respatientinsertupdate> call, Throwable t) {

                    if (listener!=null){
                        listener.onFailure("Something went wrong");
                    }


                }
            });

    }

    public void makeHWPatientFamilyInfoinsertupdate(ReqInsertUpdatePatientFamilyInfo request, NetworkServiceListener listener) {
        Log.e("PATIENT UPDATE", "reqhit--"+request.getPatient_family_information().size());
        /*if (request.getPatient_family_information().size() > 0) {*/
            String s = new Gson().toJson(request);
            Log.e("DATA", s);
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<Respatientinsertupdate> response = apiService.makeHWinsertUpdatePatientFamilyInfo(request);

            response.enqueue(new Callback<Respatientinsertupdate>() {
                @Override
                public void onResponse(Call<Respatientinsertupdate> call, Response<Respatientinsertupdate> response) {
                    if (response.isSuccessful()) {

                        Log.e("Familyupdat---",response.body().toString());
                        if (response.body().getStatusCode() == 200) {
                            if (response.body().getPatientupdatedata().size() > 0) {
                                for (PatientupdatedataItem item : response.body().getPatientupdatedata()) {
                                    Log.e("PATIENT UPDATE", item.getLocalID() + "-------------------" + item.getCitizenID());
                                    hwPatientFamilyinfoRepository.updatesyncdatainserupdatepatient(true, item.getLocalID(), item.getCitizenID());
                                    symptoAddRepository.updatesyncdatainserupdatepatientByFamilyID(true, item.getLocalID(), item.getCitizenID());
                                }
                            }


                        }
                        if (listener!=null){
                            if (listener!=null){
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        listener.onSuccess(response.body(), true);
                                    }
                                }, 2000);
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<Respatientinsertupdate> call, Throwable t) {
                    if (listener!=null){
                        listener.onFailure("Something went wrong");
                    }

                }
            });

    }




    /*QWATCH*********************************************************************************************************/

    // req otp
    public void makeAppLogin(String request, NetworkServiceListener listener) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ResGvtValidOtp> response = apiService.makeOtpReqGOVT(request);
        response.enqueue(new Callback<ResGvtValidOtp>() {
            @Override
            public void onResponse(Call<ResGvtValidOtp> call, Response<ResGvtValidOtp> response) {


                if (response.isSuccessful()) {

                    if (listener != null) {
                        listener.onSuccess(response.body(), true);
                    }

                } else if (response.code() == 401) {
//                    LoginResponse respObj = new LoginResponse();
//                    listener.onAuthFail(makeErrorResponse(respObj,""+response.code()));
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    if (listener != null) {
                        ResGvtValidOtp res = new ResGvtValidOtp();
                        listener.onFailure(makeErrorResponse(res, "" + error.message()));
                    }


                   /* if (BuildConfig.DEBUG) {
                        Log.d("error message", error.message());
                    }*/
                }
            }

            @Override
            public void onFailure(Call<ResGvtValidOtp> call, Throwable t) {
                if (BuildConfig.DEBUG) {
                    //Log.d("error message", t.getMessage());
                    if (listener != null) {
                        ResGvtValidOtp loginResponse = new ResGvtValidOtp();
                        listener.onFailure(makeErrorResponse(loginResponse, "" + t.getMessage()));
                    }
                }
            }
        });
    }

    public void makeotpvalid(ReqOtpValidGvt request, NetworkServiceListener listener) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ResGvtValidOtpValid> response = apiService.makeOtpValidReq(request);
        response.enqueue(new Callback<ResGvtValidOtpValid>() {
            @Override
            public void onResponse(Call<ResGvtValidOtpValid> call, Response<ResGvtValidOtpValid> response) {


                if (response.isSuccessful()) {

                    if (listener != null) {
                        if (response.body().getStatuscode() == 200) {
                            listener.onSuccess(response.body(), true);
                        } else {
                            listener.onFailure(response.body());
                        }

                    }

                } else if (response.code() == 401) {
//                    LoginResponse respObj = new LoginResponse();
//                    listener.onAuthFail(makeErrorResponse(respObj,""+response.code()));
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    if (listener != null) {
                        ResGvtValidOtpValid res = new ResGvtValidOtpValid();
                        listener.onFailure(makeErrorResponse(res, "" + error.message()));
                    }


                   /* if (BuildConfig.DEBUG) {
                        Log.d("error message", error.message());
                    }*/
                }
            }

            @Override
            public void onFailure(Call<ResGvtValidOtpValid> call, Throwable t) {
                if (BuildConfig.DEBUG) {
                    Log.d("error message", t.getMessage());
                    if (listener != null) {
                        ResGvtValidOtpValid loginResponse = new ResGvtValidOtpValid();
                        listener.onFailure(makeErrorResponse(loginResponse, "" + t.getMessage()));
                    }
                }
            }
        });
    }

    public void getPatientInfo(ReqPatient request, NetworkServiceListener listener) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<List<ResPatientInfo>> response = apiService.getPAtientInfo(request);

        response.enqueue(new Callback<List<ResPatientInfo>>() {
            @Override
            public void onResponse(Call<List<ResPatientInfo>> call, Response<List<ResPatientInfo>> response) {
                if (response.isSuccessful()) {
                    if (listener != null) {
                        if (response.body().size() > 0) {
                            listener.onSuccess(response.body().get(0), true);
                        } else {
                            listener.onFailure("No result found");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResPatientInfo>> call, Throwable t) {
                if (listener != null) {
                    listener.onFailure("Something went wrong");
                }

            }
        });
    }

    public void getPatientInfoListByAdmin(ReqPAtientInfoByAdmin request, NetworkServiceListener listener) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<List<ResPatientInfoByAdmin>> response = apiService.getPAtientInfoListByAdmin(request);

        response.enqueue(new Callback<List<ResPatientInfoByAdmin>>() {
            @Override
            public void onResponse(Call<List<ResPatientInfoByAdmin>> call, Response<List<ResPatientInfoByAdmin>> response) {
                if (response.isSuccessful()) {
                    if (listener != null) {
                        if (response.body().size() > 0) {
                            Log.e("PatientList--", response.body().size() + "");
                            insertTODB(response.body());
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onSuccess(response.body().get(0), true);
                                }
                            }, 100);

                        } else {
                            listener.onFailure("No result found");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResPatientInfoByAdmin>> call, Throwable t) {
                if (listener != null) {
                    listener.onFailure("Something went wrong");
                }

            }
        });
    }

    private void insertTODB(List<ResPatientInfoByAdmin> body) {

        for (ResPatientInfoByAdmin infoByAdmin : body) {

            ResPatientInfo resPatientInfo = new ResPatientInfo();
            resPatientInfo.setCitizenID(infoByAdmin.getCitizenID());
            resPatientInfo.setName(infoByAdmin.getName());
            resPatientInfo.setAge(infoByAdmin.getAge());
            resPatientInfo.setMobile(infoByAdmin.getMobileNo());
            resPatientInfo.setEmail(infoByAdmin.getEmail());
            resPatientInfo.setDOA(infoByAdmin.getDateOfArrial());
            resPatientInfo.setDateQurantine(infoByAdmin.getDateOfQurantine());
            resPatientInfo.setPOOrigin(infoByAdmin.getPlaceOfOrigin());
            resPatientInfo.setPoArrival(infoByAdmin.getPlaceOfArrival());
            resPatientInfo.setHNo(infoByAdmin.getHouseNo());
            resPatientInfo.setBuilding(infoByAdmin.getBuilding());
            resPatientInfo.setStreet(infoByAdmin.getStreet());
            resPatientInfo.setCity(infoByAdmin.getCity());
            resPatientInfo.setCreatedDate(infoByAdmin.getCreatedDate());
            resPatientInfo.setAdditional(infoByAdmin.getAdditionalInfo());
            resPatientInfo.setLatitude(infoByAdmin.getLatitude());
            resPatientInfo.setLongitude(infoByAdmin.getLongitude());
            resPatientInfo.setDistrictCode(infoByAdmin.getDistCode());
            resPatientInfo.setTalukCode(infoByAdmin.getTalukCode());
            resPatientInfo.setCoughSourThroat(infoByAdmin.isCitz_CoughSourThroat());
            resPatientInfo.setFever(infoByAdmin.isCitz_Fever());
            resPatientInfo.setBreathingProblem(infoByAdmin.isCitz_BreathingProb());
            resPatientInfo.setDiarrhea(infoByAdmin.isCitz_Diarrhea());
            resPatientInfo.setURoleBy(infoByAdmin.getURoleID());
            resPatientInfo.setDiabetes(infoByAdmin.isDiabetes());
            resPatientInfo.setHypertension(infoByAdmin.isHypertension());
            resPatientInfo.setHeartIssue(infoByAdmin.isHeartIssue());
            resPatientInfo.setHIV(infoByAdmin.isHIV());

            patientinfoRepository.insert(resPatientInfo);

        }

    }

    public void getPatientFamilyInfo(ReqPatient request, NetworkServiceListener listener) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<List<ResPatientFamilyInfo>> response = apiService.getPAtientFamilyInfo(request);

        response.enqueue(new Callback<List<ResPatientFamilyInfo>>() {
            @Override
            public void onResponse(Call<List<ResPatientFamilyInfo>> call, Response<List<ResPatientFamilyInfo>> response) {
                if (response.isSuccessful()) {
                    if (listener != null) {
                        if (response.body().size() > 0) {
                            patientFamilyinfoRepository.insert(response.body());
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onSuccess(response.body().get(0), true);
                                }
                            }, 100);

                        } else {
                            listener.onFailure("");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResPatientFamilyInfo>> call, Throwable t) {
                if (listener != null) {
                    listener.onFailure("Something went wrong");
                }

            }
        });

    }

    public void updatepatentInfo(ReqUpdatePatentInfo request, NetworkServiceListener listener) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<List<ResUpdateInfo>> response = apiService.updatePAtientInfo(request);

        response.enqueue(new Callback<List<ResUpdateInfo>>() {
            @Override
            public void onResponse(Call<List<ResUpdateInfo>> call, Response<List<ResUpdateInfo>> response) {
                if (response.isSuccessful()) {
                    if (listener != null) {
                        if (response.body().size() > 0) {
                            listener.onSuccess(response.body().get(0), true);
                        } else {
                            listener.onFailure("No result found");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResUpdateInfo>> call, Throwable t) {
                if (listener != null) {
                    listener.onFailure("Something went wrong");
                }

            }
        });
    }

    public void addpatentfamillyInfo(ReqUpdatePatentIFamilynfo request, NetworkServiceListener listener) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<List<ResUpdateInfo>> response = apiService.updatePAtientFamillyInfo(request);

        response.enqueue(new Callback<List<ResUpdateInfo>>() {
            @Override
            public void onResponse(Call<List<ResUpdateInfo>> call, Response<List<ResUpdateInfo>> response) {
                if (response.isSuccessful()) {
                    if (listener != null) {
                        if (response.body().size() > 0) {
                            listener.onSuccess(response.body().get(0), true);
                        } else {
                            listener.onFailure("No result found");
                        }
                    }
                } else {
                    if (listener != null) {
                        listener.onFailure("Something went wrong");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResUpdateInfo>> call, Throwable t) {
                if (listener != null) {
                    listener.onFailure("Something went wrong");
                }

            }
        });
    }

    public void sendpatientsymtomInfo(ReqGvtPatientSymptom request, NetworkServiceListener listener) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<List<ResSymtomChecker>> response = apiService.sendpatientSymptom(request);

        response.enqueue(new Callback<List<ResSymtomChecker>>() {
            @Override
            public void onResponse(Call<List<ResSymtomChecker>> call, Response<List<ResSymtomChecker>> response) {
                if (response.isSuccessful()) {
                    if (listener != null) {

                        if (response.body().size() > 0) {
                            if (response.body().get(0).isSuccess()) {
                                listener.onSuccess(response.body().get(0), true);
                            } else {
                                listener.onFailure("No result found");
                            }
                        } else {
                            listener.onFailure("No result found");
                        }
                    }
                } else {
                    if (listener != null) {
                        listener.onFailure("Something went wrong");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResSymtomChecker>> call, Throwable t) {
                if (listener != null) {
                    listener.onFailure("Something went wrong");
                }

            }
        });
    }

    public void sendpatientFamillysymtomInfo(ReqGvtPatientFamillySymptom request, NetworkServiceListener listener) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<List<ResSymtomChecker>> response = apiService.sendpatientFamilySymptom(request);

        response.enqueue(new Callback<List<ResSymtomChecker>>() {
            @Override
            public void onResponse(Call<List<ResSymtomChecker>> call, Response<List<ResSymtomChecker>> response) {
                if (response.isSuccessful()) {
                    if (listener != null) {

                        if (response.body().size() > 0) {
                            if (response.body().get(0).isSuccess()) {
                                listener.onSuccess(response.body().get(0), true);
                            } else {
                                listener.onFailure("No result found");
                            }
                        } else {
                            listener.onFailure("No result found");
                        }
                    }
                } else {
                    if (listener != null) {
                        listener.onFailure("Something went wrong");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResSymtomChecker>> call, Throwable t) {
                if (listener != null) {
                    listener.onFailure("Something went wrong");
                }

            }
        });
    }

    public void sendImageFile(ReqImageChunk request, NetworkServiceListener listener) {
        ApiInterface apiService =
                ApiClient.getClientImageUpload().create(ApiInterface.class);
        Call<ResImage> response = apiService.sendprofileImage(request, "bhoomiWS@2020", "b0b17574-9457-4674-9e98-899aae87dc6e");

        String res = new Gson().toJson(request);
        Log.e("RESPONSE-------", res);

        response.enqueue(new Callback<ResImage>() {
            @Override
            public void onResponse(Call<ResImage> call, Response<ResImage> response) {

                if (response.isSuccessful()) {
                    // Log.e("response",response.body().toString());
                    if (listener != null) {
                        if (!response.body().getFn_Insert_DOCCHNKResult().equalsIgnoreCase("")) {
                            FnInsertDOCCHNKResult result = new Gson().fromJson(response.body().getFn_Insert_DOCCHNKResult(), FnInsertDOCCHNKResult.class);
                            if (result.getSTATUS().equalsIgnoreCase("TRUE")) {
                                listener.onSuccess(result.getSTATUS(), true);
                            } else {
                                listener.onFailure("No result found");
                            }


                           /* if (response.body().getFn_Insert_DOCCHNKResult().getSTATUS().equalsIgnoreCase("TRUE")) {
                                listener.onSuccess(response.body().getFn_Insert_DOCCHNKResult().getSTATUS(), true);
                            }else {
                                listener.onFailure("No result found");
                            }*/

                        } else {
                            listener.onFailure("No result found");
                        }
                    }
                } else {
                    if (listener != null) {
                        listener.onFailure("Something went wrong");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResImage> call, Throwable t) {
                if (listener != null) {
                    listener.onFailure("Something went wrong");
                }

            }
        });
    }

    public void makestatusupdate(ReqStatus request, boolean b, NetworkServiceListener listener) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ResTracker> response = apiService.makeTraker(request);
        response.enqueue(new Callback<ResTracker>() {
            @Override
            public void onResponse(Call<ResTracker> call, Response<ResTracker> response) {

                if (response.isSuccessful()) {

                    if (listener != null) {
                        if (response.body().getStatus().equalsIgnoreCase("0")) {
                            updatedb(response.body().getData().getTrackResponse(), b);
                            listener.onSuccess(response.body(), true);

                        } else {
                            listener.onFailure(response.body());
                        }

                    } else {
                        if (response.body().getStatus().equalsIgnoreCase("0")) {
                            updatedb(response.body().getData().getTrackResponse(), b);
                        }
                    }

                } else if (response.code() == 401) {
//                    LoginResponse respObj = new LoginResponse();
//                    listener.onAuthFail(makeErrorResponse(respObj,""+response.code()));
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    if (listener != null) {
                        ResTracker res = new ResTracker();
                        listener.onFailure(makeErrorResponse(res, "" + error.message()));
                    }


                   /* if (BuildConfig.DEBUG) {
                        Log.d("error message", error.message());
                    }*/
                }
            }

            @Override
            public void onFailure(Call<ResTracker> call, Throwable t) {
                if (BuildConfig.DEBUG) {
                    Log.d("error message", t.getMessage());
                    if (listener != null) {
                        ResTracker loginResponse = new ResTracker();
                        listener.onFailure(makeErrorResponse(loginResponse, "" + t.getMessage()));
                    }
                }
            }
        });
    }

    private void updatedb(List<TrackResponseItem> trackResponse, boolean b) {
        if (b) {
            travelTrackingRepository.clear();
        } else {
            if (trackResponse.size() > 0) {
                for (TrackResponseItem item : trackResponse) {
                    travelTrackingRepository.updatesyncdata(true, item.getLocalId());
                }
            }
        }
    }

    public void makeemergency(ReqEmegency request, NetworkServiceListener listener) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ResEmergency> response = apiService.makeEmergencyTraker(request);
        response.enqueue(new Callback<ResEmergency>() {
            @Override
            public void onResponse(Call<ResEmergency> call, Response<ResEmergency> response) {

                if (response.isSuccessful()) {

                    if (listener != null) {
                        if (response.body().getStatus().equalsIgnoreCase("0")) {

                            listener.onSuccess(response.body(), true);

                        } else {
                            listener.onFailure(response.body());
                        }

                    } else {

                    }

                } else if (response.code() == 401) {
//                    LoginResponse respObj = new LoginResponse();
//                    listener.onAuthFail(makeErrorResponse(respObj,""+response.code()));
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    if (listener != null) {
                        ResEmergency res = new ResEmergency();
                        listener.onFailure(makeErrorResponse(res, "" + error.message()));
                    }


                   /* if (BuildConfig.DEBUG) {
                        Log.d("error message", error.message());
                    }*/
                }
            }

            @Override
            public void onFailure(Call<ResEmergency> call, Throwable t) {
                if (BuildConfig.DEBUG) {
                    Log.d("error message", t.getMessage());
                    if (listener != null) {
                        ResEmergency loginResponse = new ResEmergency();
                        listener.onFailure(makeErrorResponse(loginResponse, "" + t.getMessage()));
                    }
                }
            }
        });
    }

    public void makeemergencyinside(ReqEmegency request, NetworkServiceListener listener) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> response = apiService.makeEmergencyTrakerinside(request);
        response.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                if (BuildConfig.DEBUG) {
                    /*Log.d("error message", t.getMessage());
                    if (listener != null) {
                        ResEmergency loginResponse = new ResEmergency();
                        listener.onFailure(makeErrorResponse(loginResponse, "" + t.getMessage()));
                    }*/
                }
            }
        });
    }


    private Object makeErrorResponse(Object object, String error) {
        if (object instanceof ResHWGetOtp) {
            ((ResHWGetOtp) object).setStatus_messaage(error);
        }
        if (object instanceof ResOtpValidate) {
            ((ResOtpValidate) object).setStatus_messaage(error);
        }


       /* if (object instanceof LoginResponse) {
            ((LoginResponse) object).setMessage(error);
        } else if (object instanceof ResponseOtp) {
            ((ResponseOtp) object).setMessage(error);
        } else if (object instanceof ResponseValidateOtp) {
            ((ResponseValidateOtp) object).setMessage(error);
        } else if (object instanceof ResCartDetails) {
            ((ResCartDetails) object).setMessage(error);
        } else if (object instanceof SignupResponse) {
            ((SignupResponse) object).setMessage(error);
        } else if (object instanceof ResponseResetPassword) {
            ((ResponseResetPassword) object).setMessage(error);
        } else if (object instanceof ResponseForgotPassword) {
            ((ResponseForgotPassword) object).setMessage(error);
        } else if (object instanceof ResponseProfileUpdate) {
            ((ResponseProfileUpdate) object).setMessage(error);
        } else if (object instanceof ResProducts) {
            ((ResProducts) object).setMessage(error);
        } else if (object instanceof ResYourOrders) {
            ((ResYourOrders) object).setMessage(error);
        } else if (object instanceof ResProductDetails) {
            ((ResProductDetails) object).setMessage(error);
        } else if (object instanceof ResWishList) {
            ((ResWishList) object).setMessage(error);
        } else if (object instanceof ResAddWishList) {
            ((ResAddWishList) object).setMessage(error);
        } else if (object instanceof ResDeleteWishList) {
            ((ResDeleteWishList) object).setMessage(error);
        } else if (object instanceof ResUpdateCartQuantity) {
            ((ResUpdateCartQuantity) object).setMessage(error);
        } else if (object instanceof ResAddToCart) {
            ((ResAddToCart) object).setMessage(error);
        } else if (object instanceof ResCustomerAddress) {
            ((ResCustomerAddress) object).setMessage(error);
        } else if (object instanceof ResponseSubscribe) {
            ((ResponseSubscribe) object).setMessage(error);
        } else if (object instanceof ResSimilarProducts) {
            ((ResSimilarProducts) object).setMessage(error);
        } else if (object instanceof ResSetDefAddr) {
            ((ResSetDefAddr) object).setMessage(error);
        } else if (object instanceof ResSearch) {
            ((ResSearch) object).setMessage(error);
        } else if (object instanceof ResponseToken) {
            ((ResponseToken) object).setMessage(error);
        } else if (object instanceof ResPlaceOrder) {
            ((ResPlaceOrder) object).setMessage(error);
        } else if (object instanceof ResLogout) {
            ((ResLogout) object).setMessage(error);
        } else if (object instanceof ResponseSponsors) {
            ((ResponseSponsors) object).setMessage(error);
        } else if (object instanceof RespAnnouncement) {
            ((RespAnnouncement) object).setMessage(error);
        } else if (object instanceof ResAddAddress){
            ((ResAddAddress) object).setMessage(error);
        } else if (object instanceof ResCheckout){
            ((ResCheckout) object).setMessage(error);
        }*/
        return object;
    }
}

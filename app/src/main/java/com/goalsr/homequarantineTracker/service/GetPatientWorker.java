package com.goalsr.homequarantineTracker.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.goalsr.homequarantineTracker.Utils.AppConstants;
import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.apiservice.ApiBackGround;
import com.goalsr.homequarantineTracker.apiservice.ApiClient;
import com.goalsr.homequarantineTracker.apiservice.ApiInterface;
import com.goalsr.homequarantineTracker.apiservice.NetworkService;
import com.goalsr.homequarantineTracker.db.repository.HWPatientFamilyinfoRepository;
import com.goalsr.homequarantineTracker.db.repository.HWPatientinfoRepository;
import com.goalsr.homequarantineTracker.resposemodel.HWSecurity.HealthWPSecurity;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientFamilyDetailsItem;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientListDataItem;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.ReqGetPatientinfobody;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.ResPatientData;
import com.goalsr.homequarantineTracker.ui.DasboardPType;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class GetPatientWorker extends Worker {

    private HWPatientinfoRepository hwPatientinfoRepository;
    private HWPatientFamilyinfoRepository hwPatientFamilyinfoRepository;
    Context mcontext;
    NetworkService networkService;

    public GetPatientWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.mcontext = context;
        networkService = new NetworkService();
        networkService.inject(mcontext);
        hwPatientinfoRepository = new HWPatientinfoRepository(mcontext);
        hwPatientFamilyinfoRepository = new HWPatientFamilyinfoRepository(mcontext);
    }

    @NonNull
    @Override
    public Result doWork() {
        //Log.i("Sven---", "activity取到了任务回传的数据");
        ReqGetPatientinfobody reqPatient = new ReqGetPatientinfobody();
       /* HealthWPSecurity securityObject = new HealthWPSecurity();
        securityObject.setName("BhoomiWapi@2020");
        securityObject.setPassphrase("c2a2b557-c792-48f9-9ccd-56fda45974b9");*/
      /*  int cId = PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(), PreferenceStore.CITIZEN_ID);
        reqPatient.setCitizenId(cId);
        reqPatient.setLevel(2);*/
        //Log.e("PatientList",PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.DISTRICT_ID)+"");
        reqPatient.setDistrict_code(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(), PreferenceStore.DISTRICT_ID));
        reqPatient.setUser_id(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(), PreferenceStore.USER_ID_login));
        reqPatient.setCity_code(-1);
        reqPatient.setGram_Panchayat_code(-1);
        reqPatient.setTaluk_code(-1);
        reqPatient.setWard_code(-1);
        reqPatient.setP_security(AppConstants.getHealthWatchSecurityObjectupdated());
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ResPatientData> response = apiService.getHWPatientFamilyInfo(reqPatient);
        try {
            Response<ResPatientData> responsssse = response.execute();
            if (responsssse.isSuccessful()) {
                if (responsssse.body().getStatusCode() == 200) {
                    if (responsssse.body().getPatientListData().size() > 0) {

                        hwPatientinfoRepository.insert(responsssse.body().getPatientListData());
                        for (PatientListDataItem item : responsssse.body().getPatientListData()) {
                            if (item.getPatientFamilyDetails().size() > 0) {
//                List<PatientFamilyDetailsItem> familyDetailsItems=new ArrayList<>();
                                for (PatientFamilyDetailsItem itemfamily : item.getPatientFamilyDetails()) {
                                    itemfamily.setCitizenIDLocalId(item.getLocalID());
                                    hwPatientFamilyinfoRepository.insertitem(itemfamily);
                                }


                            }
                        }
                    }
                }
                return Result.success();
            }else {
                return Result.failure();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure();
        }



    }
}

package com.goalsr.homequarantineTracker.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.goalsr.homequarantineTracker.db.dao.PatientInfoDao;
import com.goalsr.homequarantineTracker.db.repository.HWPatientinfoRepository;
import com.goalsr.homequarantineTracker.db.repository.PatientinfoRepository;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientListDataItem;

import java.util.List;

public class PatientViewmodel extends AndroidViewModel {
    private HWPatientinfoRepository patientinfoRepository;
    private LiveData<List<PatientListDataItem>> liveData;
    public PatientViewmodel(@NonNull Application application) {
        super(application);
        patientinfoRepository=new HWPatientinfoRepository(application);
        //liveData=patientinfoRepository.getListAllItemByAdminLivedata();

    }

    public LiveData<List<PatientListDataItem>> getLivedatPAtient(int ptype){
        return patientinfoRepository.getListAllItemByAdminLivedata(ptype);
    }
}

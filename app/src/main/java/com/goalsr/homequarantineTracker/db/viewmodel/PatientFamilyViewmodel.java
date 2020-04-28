package com.goalsr.homequarantineTracker.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.goalsr.homequarantineTracker.db.repository.HWPatientFamilyinfoRepository;
import com.goalsr.homequarantineTracker.db.repository.PatientFamilyinfoRepository;
import com.goalsr.homequarantineTracker.db.repository.PatientinfoRepository;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientFamilyInfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientFamilyDetailsItem;

import java.util.List;

public class PatientFamilyViewmodel extends AndroidViewModel {
    private HWPatientFamilyinfoRepository patientinfoRepository;
    private LiveData<List<PatientFamilyDetailsItem>> liveData;
    public PatientFamilyViewmodel(@NonNull Application application) {
        super(application);
        patientinfoRepository=new HWPatientFamilyinfoRepository(application);
       // liveData=patientinfoRepository.getListAllItemLivedata();

    }

    public LiveData<List<PatientFamilyDetailsItem>> getLivedatPAtient(int cid){
        return patientinfoRepository.getListAllItemLivedata(cid);
    }
    public LiveData<List<PatientFamilyDetailsItem>> getLivedatPAtientByLocalID(String cid){
        return patientinfoRepository.getListAllItemLivedataByLocalId(cid);
    }
    public LiveData<List<PatientFamilyDetailsItem>> getLivedatPAtientbylocalid(int cid){
        return patientinfoRepository.getListAllItemLivedata(cid);
    }

}

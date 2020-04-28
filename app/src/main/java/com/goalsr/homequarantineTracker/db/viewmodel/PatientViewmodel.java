package com.goalsr.homequarantineTracker.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.db.dao.PatientInfoDao;
import com.goalsr.homequarantineTracker.db.repository.HWPatientinfoRepository;
import com.goalsr.homequarantineTracker.db.repository.PatientinfoRepository;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientListDataItem;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PatientViewmodel extends AndroidViewModel {
    private HWPatientinfoRepository patientinfoRepository;
    private LiveData<List<PatientListDataItem>> liveData;

    public LiveData<PagedList<PatientListDataItem>> userpaggedList;
    private int patienttype=0;

    public PatientViewmodel(@NonNull Application application) {
        super(application);
        patientinfoRepository=new HWPatientinfoRepository(application);
        liveData=patientinfoRepository.getListAllItemByAdminLivedata();

    }

    public LiveData<List<PatientListDataItem>> getLivedatPAtient(int ptype){
        return patientinfoRepository.getListAllItemByAdminLivedata(ptype);
    }

    public LiveData<List<PatientListDataItem>> getLivedatPAtientTest(){
        return liveData;
    }

    public void  insertdata(List<PatientListDataItem> list){
        patientinfoRepository.insert(list);
    }


    private Executor executor;
    public void initPaged(){
        executor = Executors.newFixedThreadPool(5);

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                        .setPrefetchDistance(20)
                        .setInitialLoadSizeHint(40)
                        .setPageSize(20).build();

        if (PreferenceStore.getPrefernceHelperInstace().getFlag(YelligoApplication.getContext(), PreferenceStore.PERSIONTYPE)) {
            patienttype = 1;
        }else {
            patienttype = 2;
        }

        userpaggedList = (new LivePagedListBuilder(patientinfoRepository.getCustomerList(patienttype), pagedListConfig))
                .setFetchExecutor(executor)
                .build();


    }
}

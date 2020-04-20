package com.goalsr.homequarantineTracker.db.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.goalsr.homequarantineTracker.db.YellligoRoomDatabase;
import com.goalsr.homequarantineTracker.db.dao.HWPatientFamilyInfoDao;
import com.goalsr.homequarantineTracker.db.dao.PatientFamilyInfoDao;
import com.goalsr.homequarantineTracker.db.model.QHTracker;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientFamilyDetailsItem;

import java.util.List;

public class HWPatientFamilyinfoRepository {

    private HWPatientFamilyInfoDao mDao;
    private LiveData<List<PatientFamilyDetailsItem>> mListLiveData;
    private List<PatientFamilyDetailsItem> mList;

    public HWPatientFamilyinfoRepository(Application application) {
        YellligoRoomDatabase db = YellligoRoomDatabase.getDataBase(application);
        this.mDao = db.hwpatientFamilyInfoDao();
       // mListLiveData = mDao.getListAllItem();
    }
    public HWPatientFamilyinfoRepository(Context application) {
        YellligoRoomDatabase db = YellligoRoomDatabase.getDataBase(application);
        this.mDao = db.hwpatientFamilyInfoDao();
       //mListLiveData = mDao.getListAllItem(id);
    }

    public void insert(List<PatientFamilyDetailsItem> value) {

       new InsertAsynctaskList(mDao, value).execute();
        // mDao.insertItem(value);
    }

    public void insertitem(PatientFamilyDetailsItem value) {

        new InsertAsynctask(mDao, value).execute();
        // mDao.insertItem(value);
    }

    public List<PatientFamilyDetailsItem> getPatientFamilyInfo(){
        return mDao.getListAllItem();
    }

    public LiveData<List<PatientFamilyDetailsItem>> getPatientFamilyInfoLivedataById(int familyid){
        return mDao.getListAllItemLivedataById(familyid);
    }

    public PatientFamilyDetailsItem getPatientFamilyInfo(int id){
        return mDao.getListAllItem(id);
    }

   /* public List<String> getTravelListNonSyncImage(){
        return mDao.getListAllItemNonSyncImage(false);
    }*/

    public void update(PatientFamilyDetailsItem value) {

        new UpdateAsynctask(mDao, value).execute();
        // mDao.insertItem(value);
    }

    public void updatesyncdata(boolean status, String id) {

        new UpdateAsynctask2(mDao, status, id).execute();
        // mDao.insertItem(value);
    }

    public void updatesyncdataimagestatus(boolean status, String filename) {

        new UpdateAsynctaskimgestatus(mDao, status, filename).execute();
        // mDao.insertItem(value);
    }

   /* public PatientFamilyDetailsItem getItem(String checkinid) {

        return mDao.getItemById(checkinid);
    }*/

    public void clear() {
        mDao.clearTable();
    }

    public void clearbyID(String id) {
        mDao.clearTable();
    }

    public PatientFamilyDetailsItem checkIsExist(String mob) {

        return mDao.getItemMobileNoExist(mob);
    }



   /* public List<LocationTrackingModel> getAllItem(){

        return mDao.getListAll();
    }
    public List<LocationTrackingModel> getListAllTaskComplete(){

        return mDao.getListAllTaskComplete("3");
    }*/

    public LiveData<List<PatientFamilyDetailsItem>> getListAllItemLivedata(int cid) {

        return mDao.getListAllItemLivedata(cid);
    }

    private class InsertAsynctask extends AsyncTask<Void, Void, Void> {
        PatientFamilyDetailsItem value;
        HWPatientFamilyInfoDao mDao;

        public InsertAsynctask(HWPatientFamilyInfoDao mDao, PatientFamilyDetailsItem value) {
            this.value = value;
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.insertItem(value);
            return null;
        }
    }

    private class InsertAsynctaskList extends AsyncTask<Void, Void, Void> {
        List<PatientFamilyDetailsItem> value;
        HWPatientFamilyInfoDao mDao;

        public InsertAsynctaskList(HWPatientFamilyInfoDao mDao, List<PatientFamilyDetailsItem> value) {
            this.value = value;
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (PatientFamilyDetailsItem item : value) {
                mDao.insertItem(item);
            }

            return null;
        }
    }

    private class UpdateAsynctask extends AsyncTask<Void, Void, Void> {
        PatientFamilyDetailsItem value;
        HWPatientFamilyInfoDao mDao;

        public UpdateAsynctask(HWPatientFamilyInfoDao mDao, PatientFamilyDetailsItem value) {
            this.value = value;
            this.mDao = mDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            mDao.updateItem(value);
            return null;
        }
    }

    private class UpdateAsynctask2 extends AsyncTask<Void, Void, Void> {

        HWPatientFamilyInfoDao mDao;
        boolean status;
        String id;
        String today;

        public UpdateAsynctask2(HWPatientFamilyInfoDao mDao, boolean status, String id) {
            this.mDao = mDao;
            this.status = status;
            this.id = id;
            this.today = today;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            //mDao.update(status,id);
            return null;
        }
    }

    private class UpdateAsynctaskimgestatus extends AsyncTask<Void, Void, Void> {

        HWPatientFamilyInfoDao mDao;
        boolean status;
        String filename;
        String today;

        public UpdateAsynctaskimgestatus(HWPatientFamilyInfoDao mDao, boolean status, String filename) {
            this.mDao = mDao;
            this.status = status;
            this.filename = filename;
            this.today = today;
        }


        @Override
        protected Void doInBackground(Void... voids) {
           // mDao.updateinsertpatientsyncstatus(status,filename);
            return null;
        }
    }
}

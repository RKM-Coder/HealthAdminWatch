package com.goalsr.homequarantineTracker.db.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.goalsr.homequarantineTracker.db.YellligoRoomDatabase;
import com.goalsr.homequarantineTracker.db.dao.HWPatientInfoDao;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientListDataItem;

import java.util.List;

public class HWPatientinfoRepository {

    private HWPatientInfoDao mDao;
    private LiveData<List<PatientListDataItem>> mListLiveData;
    private List<PatientListDataItem> mList;

    public HWPatientinfoRepository(Application application) {
        YellligoRoomDatabase db = YellligoRoomDatabase.getDataBase(application);
        this.mDao = db.hwpatientInfoDao();
        //mListLiveData = mDao.getListAllItemByAdminLivedata();
    }
    public HWPatientinfoRepository(Context application) {
        YellligoRoomDatabase db = YellligoRoomDatabase.getDataBase(application);
        this.mDao = db.hwpatientInfoDao();
       // mListLiveData = mDao.getListAllItemByAdminLivedata();
    }

    public void insert(List<PatientListDataItem> value) {

       new InsertAsynctaskList(mDao, value).execute();
        // mDao.insertItem(value);
    }

    public void insert(PatientListDataItem value) {

        new InsertAsynctask(mDao, value).execute();
        // mDao.insertItem(value);
    }

    public PatientListDataItem getPatientInfo(int cid){
        return mDao.getListAllItem(cid);
    }

    public List<PatientListDataItem> getListAllItemByAdmin(){
        return mDao.getListAllItemByAdmin();
    }

    public List<PatientListDataItem> getListAllItemByAdminNONSYNC(){
        return mDao.getListAllItemByAdminNONSYNC(false);
    }

    public LiveData<List<PatientListDataItem>> getListAllItemByAdminLivedata(int ptype){
        return mDao.getListAllItemByAdminLivedata(ptype);
    }

   /* public List<String> getTravelListNonSyncImage(){
        return mDao.getListAllItemNonSyncImage(false);
    }*/

   /* public void update(PatientListDataItem value) {

        new UpdateAsynctask(mDao, value).execute();
        // mDao.insertItem(value);
    }*/

    public void updatesyncdatainserupdatepatient(boolean status, String id, int citizenid) {

        new UpdateAsynctaskInsertUpdatepatientInfo(mDao, status, id,citizenid).execute();
        // mDao.insertItem(value);
    }

    /*public void updatesyncdataimagestatus(boolean status, String filename) {

        new UpdateAsynctaskimgestatus(mDao, status, filename).execute();
        // mDao.insertItem(value);
    }*/

   /* public PatientListDataItem getItem(String checkinid) {

        return mDao.getItemById(checkinid);
    }*/

    public void clear() {
        mDao.clearTable();
    }

    public void clearbyID(String id) {
        mDao.clearTable();
    }



   /* public List<LocationTrackingModel> getAllItem(){

        return mDao.getListAll();
    }
    public List<LocationTrackingModel> getListAllTaskComplete(){

        return mDao.getListAllTaskComplete("3");
    }*/

    /*public LiveData<List<QHTracker>> getAllItemLivedata() {

        return mDao.getListAllItem();
    }*/

    private class InsertAsynctask extends AsyncTask<Void, Void, Void> {
        PatientListDataItem value;
        HWPatientInfoDao mDao;

        public InsertAsynctask(HWPatientInfoDao mDao, PatientListDataItem value) {
            this.value = value;
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            long id=mDao.insertItem(value);
            return null;
        }


    }

    private class InsertAsynctaskList extends AsyncTask<Void, Void, Void> {
        List<PatientListDataItem> value;
        HWPatientInfoDao mDao;

        public InsertAsynctaskList(HWPatientInfoDao mDao, List<PatientListDataItem> value) {
            this.value = value;
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (PatientListDataItem item : value) {
                mDao.insertItem(item);
            }

            return null;
        }
    }

    private class UpdateAsynctask extends AsyncTask<Void, Void, Void> {
        PatientListDataItem value;
        HWPatientInfoDao mDao;

        public UpdateAsynctask(HWPatientInfoDao mDao, PatientListDataItem value) {
            this.value = value;
            this.mDao = mDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            mDao.updateItem(value);
            return null;
        }
    }

    private class UpdateAsynctaskInsertUpdatepatientInfo extends AsyncTask<Void, Void, Void> {

        HWPatientInfoDao mDao;
        boolean status;
        String id;
        int citizenid;
        String today;

        public UpdateAsynctaskInsertUpdatepatientInfo(HWPatientInfoDao mDao, boolean status, String id, int cid) {
            this.mDao = mDao;
            this.status = status;
            this.id = id;
            this.citizenid = cid;
            this.today = today;
        }


        @Override
        protected Void doInBackground(Void... voids) {

            try {
                mDao.updateinsertpatientsyncstatus(status,id,citizenid);
            }catch (Exception e){

            }

            return null;
        }
    }

    private class UpdateAsynctaskimgestatus extends AsyncTask<Void, Void, Void> {

        HWPatientInfoDao mDao;
        boolean status;
        String filename;
        String today;

        public UpdateAsynctaskimgestatus(HWPatientInfoDao mDao, boolean status, String filename) {
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

package com.goalsr.homequarantineTracker.db.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.goalsr.homequarantineTracker.db.YellligoRoomDatabase;
import com.goalsr.homequarantineTracker.db.dao.SymtoAddDao;
import com.goalsr.homequarantineTracker.resposemodel.hwSymtommaker.ReqSymtomAdd;

import java.util.List;

public class SymptoAddRepository {

    private SymtoAddDao mDao;
    private LiveData<List<ReqSymtomAdd>> mListLiveData;
//    private List<QHTracker> mList;

    public SymptoAddRepository(Application application) {
        YellligoRoomDatabase db = YellligoRoomDatabase.getDataBase(application);
        this.mDao = db.symtodao();
        //mListLiveData = mDao.getListOfDistrictLivedata();
    }
    public SymptoAddRepository(Context application) {
        YellligoRoomDatabase db = YellligoRoomDatabase.getDataBase(application);
        this.mDao = db.symtodao();
        //mListLiveData = mDao.getListOfDistrictLivedata();
    }

    public void insert(List<ReqSymtomAdd> value) {

       new InsertAsynctaskList(mDao, value).execute();
        // mDao.insertItem(value);
    }

    public void insert(ReqSymtomAdd value) {

        new InsertAsynctask(mDao, value).execute();
        // mDao.insertItem(value);
    }

    

    public List<ReqSymtomAdd> getListAllItemByAdmin(){
        return mDao.getListOfDistrict();
    }
    public List<ReqSymtomAdd> getListAllItemByDistId(String id){
        return mDao.getListOfVilageByPID(id);
    }
    /*public List<ReqSymtomAdd> getListAllWordItemByTownId(String disid){
        return mDao.getListOfWord(disid);
    }
*/
    /*public LiveData<List<ReqSymtomAdd>> getListDistLivedata(){
        return mListLiveData;
    }*/

   /* public List<String> getTravelListNonSyncImage(){
        return mDao.getListAllItemNonSyncImage(false);
    }*/

    public void update(ReqSymtomAdd value) {

        new UpdateAsynctask(mDao, value).execute();
        // mDao.insertItem(value);
    }

   /* public void updatesyncdatainserupdatepatient(boolean status, String id) {

        new UpdateAsynctask2(mDao, status, id).execute();
        // mDao.insertItem(value);
    }*/

   /* public void updatesyncdataimagestatus(boolean status, String filename) {

        new UpdateAsynctaskimgestatus(mDao, status, filename).execute();
        // mDao.insertItem(value);
    }*/

   /* public ReqSymtomAdd getItem(String checkinid) {

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
        ReqSymtomAdd value;
        SymtoAddDao mDao;

        public InsertAsynctask(SymtoAddDao mDao, ReqSymtomAdd value) {
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
        List<ReqSymtomAdd> value;
        SymtoAddDao mDao;

        public InsertAsynctaskList(SymtoAddDao mDao, List<ReqSymtomAdd> value) {
            this.value = value;
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (ReqSymtomAdd item : value) {
                mDao.insertItem(item);
            }

            return null;
        }
    }

    private class UpdateAsynctask extends AsyncTask<Void, Void, Void> {
        ReqSymtomAdd value;
        SymtoAddDao mDao;

        public UpdateAsynctask(SymtoAddDao mDao, ReqSymtomAdd value) {
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

        SymtoAddDao mDao;
        boolean status;
        String id;
        String today;

        public UpdateAsynctask2(SymtoAddDao mDao, boolean status, String id) {
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

        SymtoAddDao mDao;
        boolean status;
        String filename;
        String today;

        public UpdateAsynctaskimgestatus(SymtoAddDao mDao, boolean status, String filename) {
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

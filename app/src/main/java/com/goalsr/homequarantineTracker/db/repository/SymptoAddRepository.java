package com.goalsr.homequarantineTracker.db.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.goalsr.homequarantineTracker.db.YellligoRoomDatabase;
import com.goalsr.homequarantineTracker.db.dao.SymtoAddDao;
import com.goalsr.homequarantineTracker.resposemodel.hwSymtommaker.ReqHWSymtomAdd;

import java.io.File;
import java.util.List;

public class SymptoAddRepository {

    private SymtoAddDao mDao;
    private LiveData<List<ReqHWSymtomAdd>> mListLiveData;
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

    public void insert(List<ReqHWSymtomAdd> value) {

       new InsertAsynctaskList(mDao, value).execute();
        // mDao.insertItem(value);
    }

    public void insert(ReqHWSymtomAdd value) {

        new InsertAsynctask(mDao, value).execute();
        // mDao.insertItem(value);
    }

    public List<ReqHWSymtomAdd> getListAllItemByAdminPatient(){
        return mDao.getListOfPatientSymptom();
    }

    public List<ReqHWSymtomAdd> getListAllItemByAdmin(){
        return mDao.getListOfDistrict();
    }
    public List<ReqHWSymtomAdd> getListAllItemByDistId(String id){
        return mDao.getListOfVilageByPID(id);
    }
    /*public List<ReqHWSymtomAdd> getListAllWordItemByTownId(String disid){
        return mDao.getListOfWord(disid);
    }
*/
    /*public LiveData<List<ReqHWSymtomAdd>> getListDistLivedata(){
        return mListLiveData;
    }*/

   /* public List<String> getTravelListNonSyncImage(){
        return mDao.getListAllItemNonSyncImage(false);
    }*/

    public void update(ReqHWSymtomAdd value) {

        new UpdateAsynctask(mDao, value).execute();
        // mDao.insertItem(value);
    }

    public void updatesyncdatainserupdatepatientByCitizenId(boolean status, String localid,int cid) {

        new UpdateAsyncTaskByCitizenId(mDao, status, localid,cid).execute();
        // mDao.insertItem(value);
    }

    public void updatesyncdatainserupdatepatientByFamilyID(boolean status, String localid,int cid) {

        new UpdateAsyncTaskByFamilyId(mDao, status, localid,cid).execute();
        // mDao.insertItem(value);
    }

   /* public void updatesyncdataimagestatus(boolean status, String filename) {

        new UpdateAsynctaskimgestatus(mDao, status, filename).execute();
        // mDao.insertItem(value);
    }*/

   /* public ReqHWSymtomAdd getItem(String checkinid) {

        return mDao.getItemById(checkinid);
    }*/

    public void clear() {
        mDao.clearTable();
    }

    public void clearbyID(String id) {
       new AsyncTask<Void, Void, Void>() {
           @Override
           protected Void doInBackground(Void... voids) {

               ReqHWSymtomAdd reqHWSymtomAdd=mDao.getItem(id);
               try {
                   if (reqHWSymtomAdd!=null){
                       File fdelete = new File(reqHWSymtomAdd.getImageFilePath());
                       if (fdelete.exists()) {
                           if (fdelete.delete()) {
                               mDao.clearTableByid(id);
                           }
                       }
                   }

               }catch (Exception e){

               }
               return null;
           }
       }.execute();


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
        ReqHWSymtomAdd value;
        SymtoAddDao mDao;

        public InsertAsynctask(SymtoAddDao mDao, ReqHWSymtomAdd value) {
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
        List<ReqHWSymtomAdd> value;
        SymtoAddDao mDao;

        public InsertAsynctaskList(SymtoAddDao mDao, List<ReqHWSymtomAdd> value) {
            this.value = value;
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (ReqHWSymtomAdd item : value) {
                mDao.insertItem(item);
            }

            return null;
        }
    }

    private class UpdateAsynctask extends AsyncTask<Void, Void, Void> {
        ReqHWSymtomAdd value;
        SymtoAddDao mDao;

        public UpdateAsynctask(SymtoAddDao mDao, ReqHWSymtomAdd value) {
            this.value = value;
            this.mDao = mDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            mDao.updateItem(value);
            return null;
        }
    }

    private class UpdateAsyncTaskByCitizenId extends AsyncTask<Void, Void, Void> {

        SymtoAddDao mDao;
        boolean status;
        String localid;
        int id;
        String today;

        public UpdateAsyncTaskByCitizenId(SymtoAddDao mDao, boolean status, String localid, int id) {
            this.mDao = mDao;
            this.status = status;
            this.localid = localid;
            this.id=id;
            this.today = today;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            mDao.updatestatuscitizenid(status,localid,id);
            return null;
        }
    }

    private class UpdateAsyncTaskByFamilyId extends AsyncTask<Void, Void, Void> {

        SymtoAddDao mDao;
        boolean status;
        String localid;
        int id;
        String today;

        public UpdateAsyncTaskByFamilyId(SymtoAddDao mDao, boolean status, String localid, int id) {
            this.mDao = mDao;
            this.status = status;
            this.localid = localid;
            this.id=id;
            this.today = today;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            mDao.updatestatusfamilyid(status,localid,id);
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

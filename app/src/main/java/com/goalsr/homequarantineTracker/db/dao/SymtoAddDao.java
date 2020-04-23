package com.goalsr.homequarantineTracker.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.goalsr.homequarantineTracker.resposemodel.hwSymtommaker.ReqHWSymtomAdd;

import java.util.List;

@Dao
public interface SymtoAddDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<ReqHWSymtomAdd> ndhColorList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(ReqHWSymtomAdd item);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(ReqHWSymtomAdd item);

    @Query("SELECT * from symptom_addpatientfamily")
    List<ReqHWSymtomAdd> getListOfDistrict();

    @Query("SELECT * from symptom_addpatientfamily where citizenID !=0")
    List<ReqHWSymtomAdd> getListOfPatientSymptom();

  /*  @Query("SELECT * from table_village wher GROUP BY villageName order by dist_name ASC")
    List<VillageModel> getListOfDistrict();

    @Query("SELECT * from table_village GROUP BY dist_name order by dist_name ASC")
    LiveData<List<VillageModel>> getListOfDistrictLivedata();*/

    @Query("SELECT * from symptom_addpatientfamily where localID =:districtid")
    List<ReqHWSymtomAdd> getListOfVilageByPID(String districtid);

    @Query("SELECT * from symptom_addpatientfamily where localID =:localid")
    ReqHWSymtomAdd getItem(String localid);

    @Query("UPDATE symptom_addpatientfamily SET syncstatus = :synstatus,citizenID =:cid  WHERE citizenlocalId =:c_local ")
    void updatestatuscitizenid(boolean synstatus, String c_local,int cid);

    @Query("UPDATE symptom_addpatientfamily SET syncstatus = :synstatus,familyMemberID =:fid  WHERE familylocalID =:c_local ")
    void updatestatusfamilyid(boolean synstatus, String c_local,int fid);

    /*@Query("SELECT * from table_village where ksrsac_town_code =:townid")
    List<VillageModel> getListOfWord(String townid);*/

    /*@Query("SELECT * from patient_info  where name not null order by Name ASC")
    List<ResStaticMasterDistricDB> getListAllItemByAdmin();

    @Query("SELECT * from patient_info where name not null order by Name ASC")
    LiveData<List<ResStaticMasterDistricDB>> getListAllItemByAdminLivedata();*/



    //Clear DB DATA
    @Query("DELETE FROM symptom_addpatientfamily")
    public void clearTable();
    //Clear perticular raw
    @Query("DELETE FROM symptom_addpatientfamily where localID=:id")
    public void clearTableByid(String id);
}

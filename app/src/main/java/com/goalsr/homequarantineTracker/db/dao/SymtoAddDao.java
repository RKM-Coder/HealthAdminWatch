package com.goalsr.homequarantineTracker.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.goalsr.homequarantineTracker.resposemodel.VillageModel;
import com.goalsr.homequarantineTracker.resposemodel.hwSymtommaker.ReqSymtomAdd;

import java.util.List;

@Dao
public interface SymtoAddDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<ReqSymtomAdd> ndhColorList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(ReqSymtomAdd item);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(ReqSymtomAdd item);
    @Query("SELECT * from symptom_addpatientfamily")
    List<ReqSymtomAdd> getListOfDistrict();

  /*  @Query("SELECT * from table_village wher GROUP BY villageName order by dist_name ASC")
    List<VillageModel> getListOfDistrict();

    @Query("SELECT * from table_village GROUP BY dist_name order by dist_name ASC")
    LiveData<List<VillageModel>> getListOfDistrictLivedata();*/

    @Query("SELECT * from symptom_addpatientfamily where localID =:districtid")
    List<ReqSymtomAdd> getListOfVilageByPID(String districtid);

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
   /* @Query("DELETE FROM table_mater_address_urban")
    public void clearTableByid(String id);*/
}

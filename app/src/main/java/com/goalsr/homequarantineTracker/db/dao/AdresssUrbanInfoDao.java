package com.goalsr.homequarantineTracker.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.goalsr.homequarantineTracker.resposemodel.ResStaticMasterDistricDB;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;

import java.util.List;

@Dao
public interface AdresssUrbanInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<ResStaticMasterDistricDB> ndhColorList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(ResStaticMasterDistricDB item);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(ResStaticMasterDistricDB item);

    @Query("SELECT * from table_mater_address_urban GROUP BY ksrsac_dist_code order by dist_name ASC")
    List<ResStaticMasterDistricDB> getListOfDistrict();

    @Query("SELECT * from table_mater_address_urban GROUP BY ksrsac_dist_code order by dist_name ASC")
    LiveData<List<ResStaticMasterDistricDB>> getListOfDistrictLivedata();

    @Query("SELECT * from table_mater_address_urban where rdrp_dist_code =:districtid GROUP BY ksrsac_town_code order by town_name ASC")
    List<ResStaticMasterDistricDB> getListOfTown(String districtid);

    @Query("SELECT * from table_mater_address_urban where rdrp_dist_code =:districtid GROUP BY ksrsac_town_code order by town_name ASC limit 1")
    ResStaticMasterDistricDB getDistric(int districtid);

    @Query("SELECT * from table_mater_address_urban where ksrsac_town_code =:cityid GROUP BY ksrsac_town_code limit 1")
    ResStaticMasterDistricDB getCity(int cityid);

    @Query("SELECT * from table_mater_address_urban where ksrac_word_code =:cityid GROUP BY ksrsac_town_code limit 1")
    ResStaticMasterDistricDB getWordBiWID(int cityid);

    @Query("SELECT * from table_mater_address_urban where ksrsac_town_code =:townid")
    List<ResStaticMasterDistricDB> getListOfWord(String townid);

    /*@Query("SELECT * from patient_info  where name not null order by Name ASC")
    List<ResStaticMasterDistricDB> getListAllItemByAdmin();

    @Query("SELECT * from patient_info where name not null order by Name ASC")
    LiveData<List<ResStaticMasterDistricDB>> getListAllItemByAdminLivedata();*/



    //Clear DB DATA
    @Query("DELETE FROM table_mater_address_urban")
    public void clearTable();
    //Clear perticular raw
   /* @Query("DELETE FROM table_mater_address_urban")
    public void clearTableByid(String id);*/
}

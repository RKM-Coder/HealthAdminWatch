package com.goalsr.homequarantineTracker.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientFamilyInfo;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientFamilyDetailsItem;

import java.util.List;

@Dao
public interface HWPatientFamilyInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<PatientFamilyDetailsItem> ndhColorList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(PatientFamilyDetailsItem item);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(PatientFamilyDetailsItem item);

    @Query("SELECT * from hwatch_family_info")
    List<PatientFamilyDetailsItem> getListAllItem();

    @Query("SELECT * from hwatch_family_info where citizenID =:fid")
    List<PatientFamilyDetailsItem> getListAllItemById(int fid);

    @Query("SELECT * from hwatch_family_info where citizenID =:cid")
    LiveData<List<PatientFamilyDetailsItem>> getListAllItemLivedata(int cid);

    @Query("SELECT * from hwatch_family_info where citizenID =:id")
    LiveData<List<PatientFamilyDetailsItem>> getListAllItemLivedataById(int id);

    @Query("SELECT * from hwatch_family_info where familyMemberID =:id")
    PatientFamilyDetailsItem getListAllItem(int id);

    @Query("SELECT * from hwatch_family_info where mobileNo =:mobile")
    PatientFamilyDetailsItem getItemMobileNoExist(String mobile);

    /*@Query("SELECT * from qh_travel_tracking where syncstutas= :status")
    List<QHTracker> getListAllItemNonSync(boolean status);

    @Query("SELECT selfifilepathlocal from qh_travel_tracking where syncstatusimage= :status and selfifilepathlocal not NULL")
    List<String> getListAllItemNonSyncImage(boolean status);

    @Query("SELECT * from qh_travel_tracking")
    List<QHTracker> getListAll();

    @Query("SELECT * FROM qh_travel_tracking where primary_id = :id")
    public QHTracker getItemById(String id);

    @Query("UPDATE qh_travel_tracking SET syncstutas = :synstatus  WHERE primary_id =:id ")
    void update(boolean synstatus, String id);

    @Query("UPDATE qh_travel_tracking SET syncstatusimage = :synstatus  WHERE selfifilepathlocal =:filename ")
    void updateinsertpatientsyncstatus(boolean synstatus, String filename);*/

    //Clear DB DATA
    @Query("DELETE FROM hwatch_family_info")
    public void clearTable();
    //Clear perticular raw
    @Query("DELETE FROM hwatch_family_info where CitizenID = :id")
    public void clearTableByid(String id);
}

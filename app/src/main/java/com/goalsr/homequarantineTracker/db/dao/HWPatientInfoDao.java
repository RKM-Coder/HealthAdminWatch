package com.goalsr.homequarantineTracker.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;
import com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily.PatientListDataItem;

import java.util.List;

@Dao
public interface HWPatientInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<PatientListDataItem> ndhColorList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertItem(PatientListDataItem item);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(PatientListDataItem item);

    @Query("SELECT * from hwatch_patientifo where CitizenID =:cid")
    PatientListDataItem getListAllItem(int cid);


    @Query("SELECT * from hwatch_patientifo where name not null order by Name ASC")
    List<PatientListDataItem> getListAllItemByAdmin();

    @Query("SELECT * from hwatch_patientifo where syncstatus =:b")
    List<PatientListDataItem> getListAllItemByAdminNONSYNC(boolean b);

    @Query("SELECT * from hwatch_patientifo where name not null and patientQuarantineStatus =:ptype order by Name ASC")
    LiveData<List<PatientListDataItem>> getListAllItemByAdminLivedata(int ptype);

    @Query("UPDATE hwatch_patientifo SET syncstatus = :synstatus and citizenID=:citiId  WHERE localID =:localid ")
    void updateinsertpatientsyncstatus(boolean synstatus, String localid, int citiId);

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
    @Query("DELETE FROM hwatch_patientifo")
    public void clearTable();
    //Clear perticular raw
    @Query("DELETE FROM hwatch_patientifo where CitizenID = :id")
    public void clearTableByid(String id);
}

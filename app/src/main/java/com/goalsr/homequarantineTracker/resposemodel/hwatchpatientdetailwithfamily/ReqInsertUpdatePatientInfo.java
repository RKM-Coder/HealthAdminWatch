package com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily;

import com.goalsr.homequarantineTracker.resposemodel.HWSecurity.HealthWPSecurity;

import java.util.ArrayList;
import java.util.List;

public class ReqInsertUpdatePatientInfo {

    private List<PatientListDataItem> primary_patient_information;

    private int role_id;
    private int user_id;

    private HealthWPSecurity p_security;


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<PatientListDataItem> getPrimary_patient_information() {
        return primary_patient_information;
    }

    public void setPrimary_patient_information(List<PatientListDataItem> primary_patient_information) {
        this.primary_patient_information = primary_patient_information;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public HealthWPSecurity getP_security() {
        return p_security;
    }

    public void setP_security(HealthWPSecurity p_security) {
        this.p_security = p_security;
    }
}

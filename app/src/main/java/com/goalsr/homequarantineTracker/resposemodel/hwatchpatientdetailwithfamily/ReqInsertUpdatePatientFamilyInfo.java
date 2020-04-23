package com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily;

import com.goalsr.homequarantineTracker.resposemodel.HWSecurity.HealthWPSecurity;

import java.util.List;

public class ReqInsertUpdatePatientFamilyInfo {

    private List<PatientFamilyDetailsItem> patient_family_information;

    private int role_id;

    private HealthWPSecurity p_security;

    public List<PatientFamilyDetailsItem> getPatient_family_information() {
        return patient_family_information;
    }

    public void setPatient_family_information(List<PatientFamilyDetailsItem> patient_family_information) {
        this.patient_family_information = patient_family_information;
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

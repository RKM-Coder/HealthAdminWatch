package com.goalsr.homequarantineTracker.resposemodel.hwreqotp;

import com.goalsr.homequarantineTracker.resposemodel.HWSecurity.HealthWPSecurity;

public class ReqHWOtpValidate {

    private String mobile_number;
    private int otp;
    private int role_id;

    private HealthWPSecurity p_security;

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
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

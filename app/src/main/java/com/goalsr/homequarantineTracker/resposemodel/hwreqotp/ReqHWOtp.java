package com.goalsr.homequarantineTracker.resposemodel.hwreqotp;

import com.goalsr.homequarantineTracker.resposemodel.HWSecurity.HealthWPSecurity;

public class ReqHWOtp {

    private String mobile_number;
    private HealthWPSecurity p_security;

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public HealthWPSecurity getP_security() {
        return p_security;
    }

    public void setP_security(HealthWPSecurity p_security) {
        this.p_security = p_security;
    }
}

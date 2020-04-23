package com.goalsr.homequarantineTracker.resposemodel.hwreqotp;

public class ResOtpValidate {

    private int status_code;
    private String status_messaage;
    private ResOtpValidateData data;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus_messaage() {
        return status_messaage;
    }

    public void setStatus_messaage(String status_messaage) {
        this.status_messaage = status_messaage;
    }

    public ResOtpValidateData getData() {
        return data;
    }

    public void setData(ResOtpValidateData data) {
        this.data = data;
    }
}

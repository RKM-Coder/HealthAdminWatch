package com.goalsr.homequarantineTracker.resposemodel;

public class ModelBBMPWard {

    /* {
        "bbmp_zone_no_ksrsac": "8",
        "software_zone_no": "BBMP Zone No:8",
        "zone_name": "YELAHANKA"
    }*/

    private String bbmp_zone_no_ksrsac;
    private String ward_no_ksrac;
    private String ward_no;
    private String ward_name;

    public String getWard_no_ksrac() {
        return ward_no_ksrac;
    }

    public void setWard_no_ksrac(String ward_no_ksrac) {
        this.ward_no_ksrac = ward_no_ksrac;
    }

    public String getBbmp_zone_no_ksrsac() {
        return bbmp_zone_no_ksrsac;
    }

    public void setBbmp_zone_no_ksrsac(String bbmp_zone_no_ksrsac) {
        this.bbmp_zone_no_ksrsac = bbmp_zone_no_ksrsac;
    }

    public String getWard_no() {
        return ward_no;
    }

    public void setWard_no(String ward_no) {
        this.ward_no = ward_no;
    }

    public String getWard_name() {
        return ward_name;
    }

    public void setWard_name(String ward_name) {
        this.ward_name = ward_name;
    }
}

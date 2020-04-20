package com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily;

import com.goalsr.homequarantineTracker.resposemodel.HWSecurity.HealthWPSecurity;

public class ReqGetPatientinfobody {
	private int user_id;
	private int district_code;
	private int ward_code;
	private int city_code;
	private int taluk_code;
	private int gram_Panchayat_code;

	private HealthWPSecurity p_security;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getDistrict_code() {
		return district_code;
	}

	public void setDistrict_code(int district_code) {
		this.district_code = district_code;
	}

	public int getWard_code() {
		return ward_code;
	}

	public void setWard_code(int ward_code) {
		this.ward_code = ward_code;
	}

	public int getCity_code() {
		return city_code;
	}

	public void setCity_code(int city_code) {
		this.city_code = city_code;
	}

	public int getTaluk_code() {
		return taluk_code;
	}

	public void setTaluk_code(int taluk_code) {
		this.taluk_code = taluk_code;
	}

	public int getGram_Panchayat_code() {
		return gram_Panchayat_code;
	}

	public void setGram_Panchayat_code(int gram_Panchayat_code) {
		this.gram_Panchayat_code = gram_Panchayat_code;
	}

	public HealthWPSecurity getP_security() {
		return p_security;
	}

	public void setP_security(HealthWPSecurity p_security) {
		this.p_security = p_security;
	}
}

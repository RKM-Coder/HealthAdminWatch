package com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResSymptomInsertUpdate {

	@SerializedName("status_messaage")
	private String statusMessaage;

	@SerializedName("status_code")
	private int statusCode;

	@SerializedName("data")
	private PatientupdatedataItem patientupdatedata;

	public void setStatusMessaage(String statusMessaage){
		this.statusMessaage = statusMessaage;
	}

	public String getStatusMessaage(){
		return statusMessaage;
	}

	public void setStatusCode(int statusCode){
		this.statusCode = statusCode;
	}

	public int getStatusCode(){
		return statusCode;
	}

	public PatientupdatedataItem getPatientupdatedata() {
		return patientupdatedata;
	}

	public void setPatientupdatedata(PatientupdatedataItem patientupdatedata) {
		this.patientupdatedata = patientupdatedata;
	}

	@Override
 	public String toString(){
		return 
			"Respatientinsertupdate{" +
			"status_messaage = '" + statusMessaage + '\'' + 
			",status_code = '" + statusCode + '\'' + 
			",patientupdatedata = '" + patientupdatedata + '\'' + 
			"}";
		}
}
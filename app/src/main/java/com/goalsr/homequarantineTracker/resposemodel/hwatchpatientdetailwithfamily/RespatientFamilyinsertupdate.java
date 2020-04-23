package com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RespatientFamilyinsertupdate {

	@SerializedName("status_messaage")
	private String statusMessaage;

	@SerializedName("status_code")
	private int statusCode;

	@SerializedName("data")
	private List<PatientupdatedataItem> patientupdatedata;

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

	public void setPatientupdatedata(List<PatientupdatedataItem> patientupdatedata){
		this.patientupdatedata = patientupdatedata;
	}

	public List<PatientupdatedataItem> getPatientupdatedata(){
		return patientupdatedata;
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
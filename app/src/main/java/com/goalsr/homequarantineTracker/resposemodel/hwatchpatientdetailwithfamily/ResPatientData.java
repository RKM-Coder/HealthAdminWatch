package com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResPatientData{

	@SerializedName("status_messaage")
	private String statusMessaage;

	@SerializedName("status_code")
	private int statusCode;

	@SerializedName("PatientListData")
	private List<PatientListDataItem> patientListData;

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

	public void setPatientListData(List<PatientListDataItem> patientListData){
		this.patientListData = patientListData;
	}

	public List<PatientListDataItem> getPatientListData(){
		return patientListData;
	}

	@Override
 	public String toString(){
		return 
			"ResPatientData{" + 
			"status_messaage = '" + statusMessaage + '\'' + 
			",status_code = '" + statusCode + '\'' + 
			",patientListData = '" + patientListData + '\'' + 
			"}";
		}
}
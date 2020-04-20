package com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily;

import com.google.gson.annotations.SerializedName;

public class PatientupdatedataItem{

	@SerializedName("citizenID")
	private int citizenID;

	@SerializedName("localID")
	private String localID;

	public void setCitizenID(int citizenID){
		this.citizenID = citizenID;
	}

	public int getCitizenID(){
		return citizenID;
	}

	public void setLocalID(String localID){
		this.localID = localID;
	}

	public String getLocalID(){
		return localID;
	}

	@Override
 	public String toString(){
		return 
			"PatientupdatedataItem{" + 
			"citizenID = '" + citizenID + '\'' + 
			",localID = '" + localID + '\'' + 
			"}";
		}
}
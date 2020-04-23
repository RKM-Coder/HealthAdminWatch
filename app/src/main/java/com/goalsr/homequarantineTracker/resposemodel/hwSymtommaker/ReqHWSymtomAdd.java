package com.goalsr.homequarantineTracker.resposemodel.hwSymtommaker;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "symptom_addpatientfamily")
public class ReqHWSymtomAdd {

	@PrimaryKey
	@NonNull
	@SerializedName("localID")
	private String localID;

	@SerializedName("symptoms")
	private String symptoms;

	@SerializedName("dateTime")
	private String dateTime;

	@SerializedName("imageName")
	private String imageName;

	@SerializedName("familyMemberID")
	private int familyMemberID;

	@SerializedName("familylocalId")
	private String familylocalID;

	@SerializedName("citizenLocalId")
	private String citizenlocalId;

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("imageFilePath")
	private String imageFilePath;

	@SerializedName("citizenID")
	private int citizenID;


	@SerializedName("longitude")
	private double longitude;

	@SerializedName("imagelocalFilePath")
	private String imagelocalFilePath;

	private boolean syncstatus;

	private String typeofpatient;

	public String getFamilylocalID() {
		return familylocalID;
	}

	public void setFamilylocalID(String familylocalID) {
		this.familylocalID = familylocalID;
	}

	public String getCitizenlocalId() {
		return citizenlocalId;
	}

	public void setCitizenlocalId(String citizenlocalId) {
		this.citizenlocalId = citizenlocalId;
	}

	public String getTypeofpatient() {
		return typeofpatient;
	}

	public void setTypeofpatient(String typeofpatient) {
		this.typeofpatient = typeofpatient;
	}

	public String getImagelocalFilePath() {
		return imagelocalFilePath;
	}

	public void setImagelocalFilePath(String imagelocalFilePath) {
		this.imagelocalFilePath = imagelocalFilePath;
	}

	public boolean isSyncstatus() {
		return syncstatus;
	}

	public void setSyncstatus(boolean syncstatus) {
		this.syncstatus = syncstatus;
	}

	public void setSymptoms(String symptoms){
		this.symptoms = symptoms;
	}

	public String getSymptoms(){
		return symptoms;
	}

	public void setDateTime(String dateTime){
		this.dateTime = dateTime;
	}

	public String getDateTime(){
		return dateTime;
	}

	public void setImageName(String imageName){
		this.imageName = imageName;
	}

	public String getImageName(){
		return imageName;
	}

	public void setFamilyMemberID(int familyMemberID){
		this.familyMemberID = familyMemberID;
	}

	public int getFamilyMemberID(){
		return familyMemberID;
	}

	public void setLatitude(double latitude){
		this.latitude = latitude;
	}

	public double getLatitude(){
		return latitude;
	}

	public void setImageFilePath(String imageFilePath){
		this.imageFilePath = imageFilePath;
	}

	public String getImageFilePath(){
		return imageFilePath;
	}

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

	public void setLongitude(double longitude){
		this.longitude = longitude;
	}

	public double getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return 
			"ReqHWSymtomAdd{" +
			"symptoms = '" + symptoms + '\'' + 
			",dateTime = '" + dateTime + '\'' + 
			",imageName = '" + imageName + '\'' + 
			",familyMemberID = '" + familyMemberID + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",imageFilePath = '" + imageFilePath + '\'' + 
			",citizenID = '" + citizenID + '\'' + 
			",localID = '" + localID + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}
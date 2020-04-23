package com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;
@Entity(tableName = "hwatch_family_info")
public class PatientFamilyDetailsItem{


	@SerializedName("citizenID")
	private int citizenID;

	@SerializedName("citizenIDlocalID")
	private String citizenIDLocalId;

	@PrimaryKey
	@NotNull
	@SerializedName("familyLocalID")
	private String familyLocalID;

	@SerializedName("familyMemberID")
	private int familyMemberID=0;


	@SerializedName("startDateOfQuarantine")
	private String startDateOfQuarantine="";

	@SerializedName("endDateOfQuarantine")
	private String endDateOfQuarantine="";

	@SerializedName("portOfArrival")
	private String portOfArrival="";

	@SerializedName("profileCreatedBy")
	private int profileCreatedBy=0;

	@SerializedName("latitude")
	private double latitude=0.0;

	@SerializedName("portOfOrigin")
	private String portOfOrigin="";

	@SerializedName("mobileNo")
	private String mobileNo="";

	@SerializedName("relationShipCode")
	private int relationShipCode=0;

	@SerializedName("symptoms")
	private String symptoms="";

	@SerializedName("genderCode")
	private int genderCode=-1;

	@SerializedName("profileUpdatedBy")
	private int profileUpdatedBy=0;

	@SerializedName("createdDate")
	private String createdDate="";

	@SerializedName("patientQuarantineStatus")
	private int patientQuarantineStatus=0;

	@SerializedName("name")
	private String name;

	@SerializedName("additionalInfo")
	private String additionalInfo="";

	@SerializedName("isProfileUpdated")
	private boolean isProfileUpdated=true;

	@SerializedName("email")
	private String email="";

	@SerializedName("age")
	private int age=0;

	@SerializedName("longitude")
	private double longitude=0.0;


	private boolean syncstatus=true;

	@SerializedName("IsHavingTravelHistory")
	private boolean isHavingTravelHistory=false;

	@SerializedName("HisOfLabCaseConfirmed")
	private boolean hisOfLabCaseConfirmed=false;

	@SerializedName("IsHospitalized")
	private boolean isHospitalized=false;


	@SerializedName("RiskArea")
	private int riskArea=0;

	@SerializedName("DateOfFirstSymptom")
	private String dateOfFirstSymptom="";



	public boolean isHavingTravelHistory() {
		return isHavingTravelHistory;
	}

	public void setHavingTravelHistory(boolean havingTravelHistory) {
		isHavingTravelHistory = havingTravelHistory;
	}

	public boolean isHisOfLabCaseConfirmed() {
		return hisOfLabCaseConfirmed;
	}

	public void setHisOfLabCaseConfirmed(boolean hisOfLabCaseConfirmed) {
		this.hisOfLabCaseConfirmed = hisOfLabCaseConfirmed;
	}

	public boolean isHospitalized() {
		return isHospitalized;
	}

	public void setHospitalized(boolean hospitalized) {
		isHospitalized = hospitalized;
	}

	public int getRiskArea() {
		return riskArea;
	}

	public void setRiskArea(int riskArea) {
		this.riskArea = riskArea;
	}

	public String getDateOfFirstSymptom() {
		return dateOfFirstSymptom;
	}

	public void setDateOfFirstSymptom(String dateOfFirstSymptom) {
		this.dateOfFirstSymptom = dateOfFirstSymptom;
	}

	public String getCitizenIDLocalId() {
		return citizenIDLocalId;
	}

	public void setCitizenIDLocalId(String citizenIDLocalId) {
		this.citizenIDLocalId = citizenIDLocalId;
	}

	public boolean isSyncstatus() {
		return syncstatus;
	}

	public void setSyncstatus(boolean syncstatus) {
		this.syncstatus = syncstatus;
	}

	public String getEndDateOfQuarantine() {
		return endDateOfQuarantine;
	}

	public void setEndDateOfQuarantine(String endDateOfQuarantine) {
		this.endDateOfQuarantine = endDateOfQuarantine;
	}

	public boolean isProfileUpdated() {
		return isProfileUpdated;
	}

	public void setProfileUpdated(boolean profileUpdated) {
		isProfileUpdated = profileUpdated;
	}

	public void setStartDateOfQuarantine(String startDateOfQuarantine){
		this.startDateOfQuarantine = startDateOfQuarantine;
	}

	public String getStartDateOfQuarantine(){
		return startDateOfQuarantine;
	}


	public void setPortOfArrival(String portOfArrival){
		this.portOfArrival = portOfArrival;
	}

	public String getPortOfArrival(){
		return portOfArrival;
	}

	public void setProfileCreatedBy(int profileCreatedBy){
		this.profileCreatedBy = profileCreatedBy;
	}

	public int getProfileCreatedBy(){
		return profileCreatedBy;
	}

	public void setLatitude(double latitude){
		this.latitude = latitude;
	}

	public double getLatitude(){
		return latitude;
	}

	public void setPortOfOrigin(String portOfOrigin){
		this.portOfOrigin = portOfOrigin;
	}

	public String getPortOfOrigin(){
		return portOfOrigin;
	}

	public void setMobileNo(String mobileNo){
		this.mobileNo = mobileNo;
	}

	public String getMobileNo(){
		return mobileNo;
	}

	public void setRelationShipCode(int relationShipCode){
		this.relationShipCode = relationShipCode;
	}

	public int getRelationShipCode(){
		return relationShipCode;
	}

	public void setSymptoms(String symptoms){
		this.symptoms = symptoms;
	}

	public String getSymptoms(){
		return symptoms;
	}

	public void setGenderCode(int genderCode){
		this.genderCode = genderCode;
	}

	public int getGenderCode(){
		return genderCode;
	}

	public void setProfileUpdatedBy(int profileUpdatedBy){
		this.profileUpdatedBy = profileUpdatedBy;
	}

	public int getProfileUpdatedBy(){
		return profileUpdatedBy;
	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setPatientQuarantineStatus(int patientQuarantineStatus){
		this.patientQuarantineStatus = patientQuarantineStatus;
	}

	public int getPatientQuarantineStatus(){
		return patientQuarantineStatus;
	}

	public void setFamilyMemberID(int familyMemberID){
		this.familyMemberID = familyMemberID;
	}

	public int getFamilyMemberID(){
		return familyMemberID;
	}

	public void setFamilyLocalID(String familyLocalID){
		this.familyLocalID = familyLocalID;
	}

	public String getFamilyLocalID(){
		return familyLocalID;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setAdditionalInfo(String additionalInfo){
		this.additionalInfo = additionalInfo;
	}

	public String getAdditionalInfo(){
		return additionalInfo;
	}



	public void setCitizenID(int citizenID){
		this.citizenID = citizenID;
	}

	public int getCitizenID(){
		return citizenID;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setAge(int age){
		this.age = age;
	}

	public int getAge(){
		return age;
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
			"PatientFamilyDetailsItem{" + 
			"startDateOfQuarantine = '" + startDateOfQuarantine + '\'' + 
			",endDateOfQuarantine = '" + endDateOfQuarantine + '\'' + 
			",portOfArrival = '" + portOfArrival + '\'' + 
			",profileCreatedBy = '" + profileCreatedBy + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",portOfOrigin = '" + portOfOrigin + '\'' + 
			",mobileNo = '" + mobileNo + '\'' + 
			",relationShipCode = '" + relationShipCode + '\'' + 
			",symptoms = '" + symptoms + '\'' + 
			",genderCode = '" + genderCode + '\'' + 
			",profileUpdatedBy = '" + profileUpdatedBy + '\'' + 
			",createdDate = '" + createdDate + '\'' + 
			",patientQuarantineStatus = '" + patientQuarantineStatus + '\'' + 
			",familyMemberID = '" + familyMemberID + '\'' + 
			",familyLocalID = '" + familyLocalID + '\'' + 
			",name = '" + name + '\'' + 
			",additionalInfo = '" + additionalInfo + '\'' + 
			",isProfileUpdated = '" + isProfileUpdated + '\'' + 
			",citizenID = '" + citizenID + '\'' + 
			",email = '" + email + '\'' + 
			",age = '" + age + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}
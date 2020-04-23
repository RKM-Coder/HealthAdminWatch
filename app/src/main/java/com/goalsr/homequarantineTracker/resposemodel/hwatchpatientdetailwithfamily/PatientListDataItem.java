package com.goalsr.homequarantineTracker.resposemodel.hwatchpatientdetailwithfamily;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "hwatch_patientifo")
public class PatientListDataItem{


	@SerializedName("citizenID")
	private int citizenID;

	@PrimaryKey
	@NotNull
	@SerializedName("localID")
	private String localID;

	@SerializedName("startDateOfQuarantine")
	private String startDateOfQuarantine="";

	@SerializedName("endDateOfQuarantine")
	private String endDateOfQuarantine="";

	@SerializedName("cityCode")
	private int cityCode=-1;

	@SerializedName("latitude")
	private double latitude=-1;

	@SerializedName("building")
	private String building="";

	@Ignore
	@SerializedName("patient_family_details")
	private List<PatientFamilyDetailsItem> patientFamilyDetails;

	@SerializedName("distCode")
	private int distCode=-1;

	@SerializedName("patientQuarantineStatus")
	private int patientQuarantineStatus=0;

	@SerializedName("street")
	private String street="";

	@SerializedName("additionalInfo")
	private String additionalInfo="";

	@SerializedName("houseNo")
	private String houseNo="";

	@SerializedName("isProfileUpdated")
	private boolean isProfileUpdated=false;

	@SerializedName("wardCode")
	private int wardCode=-1;

	@SerializedName("email")
	private String email="";

	@SerializedName("longitude")
	private double longitude=0.0;

	@SerializedName("talukCode")
	private int talukCode=-1;

	@SerializedName("portOfArrival")
	private String portOfArrival="";

	@SerializedName("gramPanchayatCode")
	private int gramPanchayatCode=-1;

	@SerializedName("profileCreatedBy")
	private int profileCreatedBy=0;

	@SerializedName("portOfOrigin")
	private String portOfOrigin="";

	@SerializedName("mobileNo")
	private String mobileNo="";

	@SerializedName("villageCode")
	private int villageCode=-1;

	@SerializedName("symptoms")
	private String symptoms="";

	@SerializedName("genderCode")
	private int genderCode=0;

	@SerializedName("profileUpdatedBy")
	private int profileUpdatedBy=0;

	@SerializedName("createdDate")
	private String createdDate="";

	@SerializedName("name")
	private String name;

	@SerializedName("age")
	private int age=0;

	@SerializedName("IsHavingTravelHistory")
	private boolean isHavingTravelHistory=false;

	@SerializedName("HisOfLabCaseConfirmed")
	private boolean hisOfLabCaseConfirmed=false;

	@SerializedName("IsHospitalized")
	private boolean isHospitalized=false;

	@SerializedName("AreaType")
	private int areaType=0;

	@SerializedName("ZoneBBMPID")
	private int zoneBBMPID=0;

	@SerializedName("WardBBMPID")
	private int wardBBMPID=0;

	@SerializedName("RiskArea")
	private int riskArea=0;

	@SerializedName("DateOfFirstSymptom")
	private String dateOfFirstSymptom="";

	public int getRiskArea() {
		return riskArea;
	}

	public void setRiskArea(int riskArea) {
		this.riskArea = riskArea;
	}

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

	public int getAreaType() {
		return areaType;
	}

	public void setAreaType(int areaType) {
		this.areaType = areaType;
	}

	public int getZoneBBMPID() {
		return zoneBBMPID;
	}

	public void setZoneBBMPID(int zoneBBMPID) {
		this.zoneBBMPID = zoneBBMPID;
	}

	public int getWardBBMPID() {
		return wardBBMPID;
	}

	public void setWardBBMPID(int wardBBMPID) {
		this.wardBBMPID = wardBBMPID;
	}

	public String getDateOfFirstSymptom() {
		return dateOfFirstSymptom;
	}

	public void setDateOfFirstSymptom(String dateOfFirstSymptom) {
		this.dateOfFirstSymptom = dateOfFirstSymptom;
	}

	private boolean syncstatus=true;

	public boolean isSyncstatus() {
		return syncstatus;
	}

	public void setSyncstatus(boolean syncstatus) {
		this.syncstatus = syncstatus;
	}

	public String getStartDateOfQuarantine() {
		return startDateOfQuarantine;
	}

	public String getEndDateOfQuarantine() {
		return endDateOfQuarantine;
	}

	public void setEndDateOfQuarantine(String endDateOfQuarantine) {
		this.endDateOfQuarantine = endDateOfQuarantine;
	}

	public List<PatientFamilyDetailsItem> getPatientFamilyDetails() {
		return patientFamilyDetails;
	}

	public void setPatientFamilyDetails(List<PatientFamilyDetailsItem> patientFamilyDetails) {
		this.patientFamilyDetails = patientFamilyDetails;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
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





	public void setCityCode(int cityCode){
		this.cityCode = cityCode;
	}

	public int getCityCode(){
		return cityCode;
	}

	public void setLatitude(double latitude){
		this.latitude = latitude;
	}

	public double getLatitude(){
		return latitude;
	}

	public void setBuilding(String building){
		this.building = building;
	}

	public String getBuilding(){
		return building;
	}



	public void setDistCode(int distCode){
		this.distCode = distCode;
	}

	public int getDistCode(){
		return distCode;
	}

	public void setPatientQuarantineStatus(int patientQuarantineStatus){
		this.patientQuarantineStatus = patientQuarantineStatus;
	}

	public int getPatientQuarantineStatus(){
		return patientQuarantineStatus;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getStreet(){
		return street;
	}



	public void setHouseNo(String houseNo){
		this.houseNo = houseNo;
	}

	public String getHouseNo(){
		return houseNo;
	}



	public void setWardCode(int wardCode){
		this.wardCode = wardCode;
	}

	public int getWardCode(){
		return wardCode;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setLongitude(double longitude){
		this.longitude = longitude;
	}

	public double getLongitude(){
		return longitude;
	}

	public void setTalukCode(int talukCode){
		this.talukCode = talukCode;
	}

	public int getTalukCode(){
		return talukCode;
	}

	public void setPortOfArrival(String portOfArrival){
		this.portOfArrival = portOfArrival;
	}

	public String getPortOfArrival(){
		return portOfArrival;
	}

	public void setGramPanchayatCode(int gramPanchayatCode){
		this.gramPanchayatCode = gramPanchayatCode;
	}

	public int getGramPanchayatCode(){
		return gramPanchayatCode;
	}

	public void setProfileCreatedBy(int profileCreatedBy){
		this.profileCreatedBy = profileCreatedBy;
	}

	public int getProfileCreatedBy(){
		return profileCreatedBy;
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

	public void setLocalID(String localID){
		this.localID = localID;
	}

	public String getLocalID(){
		return localID;
	}

	public void setVillageCode(int villageCode){
		this.villageCode = villageCode;
	}

	public int getVillageCode(){
		return villageCode;
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

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCitizenID(int citizenID){
		this.citizenID = citizenID;
	}

	public int getCitizenID(){
		return citizenID;
	}

	public void setAge(int age){
		this.age = age;
	}

	public int getAge(){
		return age;
	}

	@Override
 	public String toString(){
		return 
			"PatientListDataItem{" + 
			"startDateOfQuarantine = '" + startDateOfQuarantine + '\'' + 
			",endDateOfQuarantine = '" + endDateOfQuarantine + '\'' + 
			",cityCode = '" + cityCode + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",building = '" + building + '\'' + 
			",patient_family_details = '" + patientFamilyDetails + '\'' + 
			",distCode = '" + distCode + '\'' + 
			",patientQuarantineStatus = '" + patientQuarantineStatus + '\'' + 
			",street = '" + street + '\'' + 
			",additionalInfo = '" + additionalInfo + '\'' + 
			",houseNo = '" + houseNo + '\'' + 
			",isProfileUpdated = '" + isProfileUpdated + '\'' + 
			",wardCode = '" + wardCode + '\'' + 
			",email = '" + email + '\'' + 
			",longitude = '" + longitude + '\'' + 
			",talukCode = '" + talukCode + '\'' + 
			",portOfArrival = '" + portOfArrival + '\'' + 
			",gramPanchayatCode = '" + gramPanchayatCode + '\'' + 
			",profileCreatedBy = '" + profileCreatedBy + '\'' + 
			",portOfOrigin = '" + portOfOrigin + '\'' + 
			",mobileNo = '" + mobileNo + '\'' + 
			",localID = '" + localID + '\'' + 
			",villageCode = '" + villageCode + '\'' + 
			",symptoms = '" + symptoms + '\'' + 
			",genderCode = '" + genderCode + '\'' + 
			",profileUpdatedBy = '" + profileUpdatedBy + '\'' + 
			",createdDate = '" + createdDate + '\'' + 
			",name = '" + name + '\'' + 
			",citizenID = '" + citizenID + '\'' + 
			",age = '" + age + '\'' + 
			"}";
		}
}
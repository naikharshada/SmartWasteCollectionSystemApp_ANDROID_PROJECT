package com.example.smartwastecollectionsystem.ui.authority;

public class User1Data {
    private String userId;
    private String BranchName;
    private String memail;
    private String mphone;
    private String mpassword;
    private String acceptDate;
    private String acceptTime;
    private String wasteLocation;
    private String wasteCategory;
    private String userMail;

    public User1Data(String userId, String branchName, String memail, String mphone, String mpassword, String acceptDate, String acceptTime, String wasteLocation, String wasteCategory, String userMail) {
        this.userId = userId;
        BranchName = branchName;
        this.memail = memail;
        this.mphone = mphone;
        this.mpassword = mpassword;
        this.acceptDate = acceptDate;
        this.acceptTime = acceptTime;
        this.wasteLocation = wasteLocation;
        this.wasteCategory = wasteCategory;
        this.userMail = userMail;
    }

    public User1Data() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }

    public String getMemail() {
        return memail;
    }

    public void setMemail(String memail) {
        this.memail = memail;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public String getMpassword() {
        return mpassword;
    }

    public void setMpassword(String mpassword) {
        this.mpassword = mpassword;
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getWasteLocation() {
        return wasteLocation;
    }

    public void setWasteLocation(String wasteLocation) {
        this.wasteLocation = wasteLocation;
    }

    public String getWasteCategory() {
        return wasteCategory;
    }

    public void setWasteCategory(String wasteCategory) {
        this.wasteCategory = wasteCategory;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
}

package com.example.smartwastecollectionsystem.ui.authority;

public class User1Data {
    private String userId;
    private String BranchName;
    private String memail;
    private String mphone;
    private String mpassword;

    public User1Data(String userId, String branchName, String memail, String mphone, String mpassword) {
        this.userId = userId;
        BranchName = branchName;
        this.memail = memail;
        this.mphone = mphone;
        this.mpassword = mpassword;
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
}

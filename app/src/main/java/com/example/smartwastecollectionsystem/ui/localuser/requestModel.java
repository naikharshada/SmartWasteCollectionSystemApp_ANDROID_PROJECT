package com.example.smartwastecollectionsystem.ui.localuser;

public class requestModel {
    private String userId;
    private String email;
    private String phone;
    private String Category;
    private float Longitude;
    private float Latitude;
    private String Address;
    private String imageurl;
    private String requestTime;
    private String requestDate;
    private String userToken;
    private String msg;

    public requestModel() {
    }

    public requestModel(String userId, String email, String phone, String category, float longitude, float latitude, String address, String imageurl, String requestTime, String requestDate, String userToken, String msg) {
        this.userId = userId;
        this.email = email;
        this.phone = phone;
        Category = category;
        Longitude = longitude;
        Latitude = latitude;
        Address = address;
        this.imageurl = imageurl;
        this.requestTime = requestTime;
        this.requestDate = requestDate;
        this.userToken = userToken;
        this.msg = msg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

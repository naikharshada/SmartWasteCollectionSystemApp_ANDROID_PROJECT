package com.example.smartwastecollectionsystem.ui.localuser;

public class UserData {
    private String userId;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String Category;
    private float Longitude;
    private float Latitude;
    private String Address;
    private String Locality;
    private String imageurl;
    private String requestTime;
    private String requestDate;

    public UserData() {
    }


    public UserData(String userId, String username, String email, String phone, String password, String category, float longitude, float latitude, String address, String locality, String imageurl, String requestTime, String requestDate) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        Category = category;
        Longitude = longitude;
        Latitude = latitude;
        Address = address;
        Locality = locality;
        this.imageurl = imageurl;
        this.requestTime = requestTime;
        this.requestDate = requestDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
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
}


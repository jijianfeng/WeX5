package com.zlzkj.app.model;

public class User {
    private Integer id;

    private String userPhone;

    private String userPassword;

    private String realName;

    private String nickName;

    private String imageUrl;

    private String userMail;

    private Integer userAge;

    private String userIntro;

    private Integer userSex;

    private Integer loginTime;

    private Integer roleId;

    private Integer universityName;

    private Integer userType;

    private String checkCode;

    private String checkCodeTime;

    private Integer tyrLogin;

    private Integer tryLoginTime;

    private String userAddress;

    private String workName;

    private Integer status;

    private String threeId;

    private Integer threeType;

    private String registrationId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail == null ? null : userMail.trim();
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserIntro() {
        return userIntro;
    }

    public void setUserIntro(String userIntro) {
        this.userIntro = userIntro == null ? null : userIntro.trim();
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public Integer getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Integer loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUniversityName() {
        return universityName;
    }

    public void setUniversityName(Integer universityName) {
        this.universityName = universityName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode == null ? null : checkCode.trim();
    }

    public String getCheckCodeTime() {
        return checkCodeTime;
    }

    public void setCheckCodeTime(String checkCodeTime) {
        this.checkCodeTime = checkCodeTime == null ? null : checkCodeTime.trim();
    }

    public Integer getTyrLogin() {
        return tyrLogin;
    }

    public void setTyrLogin(Integer tyrLogin) {
        this.tyrLogin = tyrLogin;
    }

    public Integer getTryLoginTime() {
        return tryLoginTime;
    }

    public void setTryLoginTime(Integer tryLoginTime) {
        this.tryLoginTime = tryLoginTime;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress == null ? null : userAddress.trim();
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName == null ? null : workName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getThreeId() {
        return threeId;
    }

    public void setThreeId(String threeId) {
        this.threeId = threeId == null ? null : threeId.trim();
    }

    public Integer getThreeType() {
        return threeType;
    }

    public void setThreeType(Integer threeType) {
        this.threeType = threeType;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId == null ? null : registrationId.trim();
    }
}
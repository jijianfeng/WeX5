package com.zlzkj.app.model;

public class About {
    private Integer id;

    private Integer sendNumber;

    private String sendLetter;

    private String linkUser;

    private String linkTel;

    private String linkMail;

    private String linkAddress;

    private String sendUrl;

    private String exampleUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSendNumber() {
        return sendNumber;
    }

    public void setSendNumber(Integer sendNumber) {
        this.sendNumber = sendNumber;
    }

    public String getSendLetter() {
        return sendLetter;
    }

    public void setSendLetter(String sendLetter) {
        this.sendLetter = sendLetter == null ? null : sendLetter.trim();
    }

    public String getLinkUser() {
        return linkUser;
    }

    public void setLinkUser(String linkUser) {
        this.linkUser = linkUser == null ? null : linkUser.trim();
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel == null ? null : linkTel.trim();
    }

    public String getLinkMail() {
        return linkMail;
    }

    public void setLinkMail(String linkMail) {
        this.linkMail = linkMail == null ? null : linkMail.trim();
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress == null ? null : linkAddress.trim();
    }

    public String getSendUrl() {
        return sendUrl;
    }

    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl == null ? null : sendUrl.trim();
    }

    public String getExampleUrl() {
        return exampleUrl;
    }

    public void setExampleUrl(String exampleUrl) {
        this.exampleUrl = exampleUrl == null ? null : exampleUrl.trim();
    }
}
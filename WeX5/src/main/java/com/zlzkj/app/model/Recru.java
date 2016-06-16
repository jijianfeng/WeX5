package com.zlzkj.app.model;

public class Recru {
    private Integer id;

    private String job;

    private String address;

    private String description;

    private String yearSalary;

    private String jobTag;

    private String requireTag;

    private Integer addUser;

    private Integer addTime;

    private Integer endTime;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getYearSalary() {
        return yearSalary;
    }

    public void setYearSalary(String yearSalary) {
        this.yearSalary = yearSalary == null ? null : yearSalary.trim();
    }

    public String getJobTag() {
        return jobTag;
    }

    public void setJobTag(String jobTag) {
        this.jobTag = jobTag == null ? null : jobTag.trim();
    }

    public String getRequireTag() {
        return requireTag;
    }

    public void setRequireTag(String requireTag) {
        this.requireTag = requireTag == null ? null : requireTag.trim();
    }

    public Integer getAddUser() {
        return addUser;
    }

    public void setAddUser(Integer addUser) {
        this.addUser = addUser;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
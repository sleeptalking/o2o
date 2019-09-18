package com.zx.o2o.entity;

import java.util.Date;

public class PersonInfo {

    private Long userId;
    private String name;
    private String profieImg;
    private String email;
    private String gender;
    private Integer enableStatus;
    //1.顾客 2.店家 3.超级管理员
    private Integer userType;
    private Date createTime;
    private Date lastEdeitTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfieImg() {
        return profieImg;
    }

    public void setProfieImg(String profieImg) {
        this.profieImg = profieImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEdeitTime() {
        return lastEdeitTime;
    }

    public void setLastEdeitTime(Date lastEdeitTime) {
        this.lastEdeitTime = lastEdeitTime;
    }


}

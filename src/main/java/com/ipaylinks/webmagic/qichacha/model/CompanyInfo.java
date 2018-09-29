package com.ipaylinks.webmagic.qichacha.model;

import java.util.Date;

public class CompanyInfo {

    /**
     * 公司名字
     */
    private String companyName;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 法人代表
     */
    private String oper;
    /**
     * 注册号
     */
    private String registrationId;

    private String email;

    private String creditNum;
    private String registeredCapital;
    private String createDate;
    private String companyType;
    private String companyStatus;
    private String range;
    private String address;
    private Date createTime;
    private String extraId;
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExtraId() {
        return extraId;
    }

    public String getCreditNum() {
        return creditNum;
    }

    public void setCreditNum(String creditNum) {
        this.creditNum = creditNum;
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CompanyInfo{" +
                "companyName='" + companyName + '\'' +
                ", phone='" + phone + '\'' +
                ", oper='" + oper + '\'' +
                ", registrationId='" + registrationId + '\'' +
                ", email='" + email + '\'' +
                ", creditNum='" + creditNum + '\'' +
                ", registeredCapital='" + registeredCapital + '\'' +
                ", createDate='" + createDate + '\'' +
                ", companyType='" + companyType + '\'' +
                ", companyStatus='" + companyStatus + '\'' +
                ", range='" + range + '\'' +
                ", address='" + address + '\'' +
                ", createTime=" + createTime +
                ", extraId='" + extraId + '\'' +
                '}';
    }

    public void setExtraId(String extraId) {
        this.extraId = extraId;
    }

}

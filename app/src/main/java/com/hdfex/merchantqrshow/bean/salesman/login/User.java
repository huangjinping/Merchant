package com.hdfex.merchantqrshow.bean.salesman.login;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.io.File;
import java.util.Map;

/**
 * Created by harrishuang on 16/7/5.
 */
public class User extends BaseBean {

    private String token;
    private String id;
    private String bussinessId;
    private String bussinessName;
    private String bannerUrl;
    private String phone;
    private String name;
    private String remark;
    private String sourceType;
    private Boolean createCommFlag = false;
    private File file;
    private String houseType;
    private String bizRole;
    private String account;

    private boolean isSign;

    public boolean isSign() {
        return isSign;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }

    private String addHouseFlag;


    /**
     * 这是一个临时数据保存数据
     */
    private String currentBussinessId;
    private String currentBussinessName;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCurrentBussinessName() {
        return currentBussinessName;
    }

    public void setCurrentBussinessName(String currentBussinessName) {
        this.currentBussinessName = currentBussinessName;
    }

    public String getCurrentBussinessId() {
        return currentBussinessId;
    }

    public void setCurrentBussinessId(String currentBussinessId) {
        this.currentBussinessId = currentBussinessId;
    }

    public String getBizRole() {
        return bizRole;
    }

    public void setBizRole(String bizRole) {
        this.bizRole = bizRole;
    }

    private Map<String, String> sourceTypeMap;
    private Map<String, String> houseTypeMap;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Boolean getCreateCommFlag() {
        return createCommFlag;
    }

    public void setCreateCommFlag(Boolean createCommFlag) {
        this.createCommFlag = createCommFlag;
    }

    public String getBussinessName() {
        return bussinessName;
    }

    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(String bussinessId) {
        this.bussinessId = bussinessId;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Map<String, String> getSourceTypeMap() {
        return sourceTypeMap;
    }

    public void setSourceTypeMap(Map<String, String> sourceTypeMap) {
        this.sourceTypeMap = sourceTypeMap;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public Map<String, String> getHouseTypeMap() {
        return houseTypeMap;
    }

    public void setHouseTypeMap(Map<String, String> houseTypeMap) {
        this.houseTypeMap = houseTypeMap;
    }


    public String getAddHouseFlag() {
        return addHouseFlag;
    }

    public void setAddHouseFlag(String addHouseFlag) {
        this.addHouseFlag = addHouseFlag;
    }

    @Override
    public String toString() {
        return "User{" +
                "token='" + token + '\'' +
                ", id='" + id + '\'' +
                ", bussinessId='" + bussinessId + '\'' +
                ", bussinessName='" + bussinessName + '\'' +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", createCommFlag=" + createCommFlag +
                ", file=" + file +
                ", houseType='" + houseType + '\'' +
                ", sourceTypeMap=" + sourceTypeMap +
                ", houseTypeMap=" + houseTypeMap +
                '}';
    }
}

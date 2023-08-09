package com.hdfex.merchantqrshow.bean.salesman.home;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.io.File;

/**
 * author Created by harrishuang on 2017/9/11.
 * email : huangjinping@hdfex.com
 */

public class AdvancedInfo extends BaseBean {

    private String idName;
    private String idNo;
    private String idAddress;
    private String idTermBegin;
    private String idTermEnd;
    private String endDate;


    /**
     * 手持证件照
     */
    private File idCardBackImg;
    private File idCardFrontImg;
    /**
     * 处理数据
     */
    private String idCardBackImgStr;
    private String idCardFrontImgStr;

//00-	未认证
//01-	认证中，
// 02-	认证成功，
// 03-	认证失败

    public final static String AUTH_STATUS_NO = "00";
    public final static String AUTH_STATUS_ING = "01";
    public final static String AUTH_STATUS_SUCCESS = "02";
    public final static String AUTH_STATUS_EIRR = "03";


    private String authStatus;
    private String avatar;
    private String desc;


    private String entryDate;

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }


    public String getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(String idAddress) {
        this.idAddress = idAddress;
    }

    public String getIdTermBegin() {
        return idTermBegin;
    }

    public void setIdTermBegin(String idTermBegin) {
        this.idTermBegin = idTermBegin;
    }

    public String getIdTermEnd() {
        return idTermEnd;
    }

    public void setIdTermEnd(String idTermEnd) {
        this.idTermEnd = idTermEnd;
    }

    public File getIdCardBackImg() {
        return idCardBackImg;
    }

    public void setIdCardBackImg(File idCardBackImg) {
        this.idCardBackImg = idCardBackImg;
    }

    public File getIdCardFrontImg() {
        return idCardFrontImg;
    }

    public void setIdCardFrontImg(File idCardFrontImg) {
        this.idCardFrontImg = idCardFrontImg;
    }

    public String getIdCardBackImgStr() {
        return idCardBackImgStr;
    }

    public void setIdCardBackImgStr(String idCardBackImgStr) {
        this.idCardBackImgStr = idCardBackImgStr;
    }

    public String getIdCardFrontImgStr() {
        return idCardFrontImgStr;
    }

    public void setIdCardFrontImgStr(String idCardFrontImgStr) {
        this.idCardFrontImgStr = idCardFrontImgStr;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

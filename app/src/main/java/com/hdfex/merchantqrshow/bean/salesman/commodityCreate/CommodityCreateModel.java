package com.hdfex.merchantqrshow.bean.salesman.commodityCreate;

import com.hdfex.merchantqrshow.bean.BaseBean;
import com.hdfex.merchantqrshow.bean.salesman.commodity.Pic;

import java.util.List;

/**
 * Created by maidou521 on 2016/9/7.
 */
public class CommodityCreateModel extends BaseBean {
    private String commodityName;
    private String addrProvince;
    private String addrCounty;
    private String addrArea;
    private String addrDetail;
    private String name;
    private String phone;
    private String startDate;
    private String endDate;
    private String downpaymentType;
    private String monthRent;
    private String duration;
    private List<Pic> list;
    private String qrcodeUrl;
    private String packageId;
    private String applyAmount;
    private String courtName;
    private String typeModeCode;
    private String DurationCode;
    private String addrProCode;
    private String addrCountyCode;
    private String addrAreaCode;
    private String commodityId;
    private String createTime;

    private String caseId;

    private String gracePeriodAmount;
    private String gracePeriod;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }

    public String getAddrCounty() {
        return addrCounty;
    }

    public void setAddrCounty(String addrCounty) {
        this.addrCounty = addrCounty;
    }

    public String getAddrArea() {
        return addrArea;
    }

    public void setAddrArea(String addrArea) {
        this.addrArea = addrArea;
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDownpaymentType() {
        return downpaymentType;
    }

    public void setDownpaymentType(String downpaymentType) {
        this.downpaymentType = downpaymentType;
    }

    public String getMonthRent() {
        return monthRent;
    }

    public void setMonthRent(String monthRent) {
        this.monthRent = monthRent;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<Pic> getList() {
        return list;
    }

    public void setList(List<Pic> list) {
        this.list = list;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getTypeModeCode() {
        return typeModeCode;
    }

    public void setTypeModeCode(String typeModeCode) {
        this.typeModeCode = typeModeCode;
    }

    public String getDurationCode() {
        return DurationCode;
    }

    public void setDurationCode(String durationCode) {
        DurationCode = durationCode;
    }

    public String getAddrProCode() {
        return addrProCode;
    }

    public void setAddrProCode(String addrProCode) {
        this.addrProCode = addrProCode;
    }

    public String getAddrCountyCode() {
        return addrCountyCode;
    }

    public void setAddrCountyCode(String addrCountyCode) {
        this.addrCountyCode = addrCountyCode;
    }

    public String getAddrAreaCode() {
        return addrAreaCode;
    }

    public void setAddrAreaCode(String addrAreaCode) {
        this.addrAreaCode = addrAreaCode;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }


    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getGracePeriodAmount() {
        return gracePeriodAmount;
    }

    public void setGracePeriodAmount(String gracePeriodAmount) {
        this.gracePeriodAmount = gracePeriodAmount;
    }

    public String getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(String gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    @Override
    public String toString() {
        return "CommodityCreateModel{" +
                "commodityName='" + commodityName + '\'' +
                ", addrProvince='" + addrProvince + '\'' +
                ", addrCounty='" + addrCounty + '\'' +
                ", addrArea='" + addrArea + '\'' +
                ", addrDetail='" + addrDetail + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", downpaymentType='" + downpaymentType + '\'' +
                ", monthRent='" + monthRent + '\'' +
                ", duration='" + duration + '\'' +
                ", list=" + list +
                ", qrcodeUrl='" + qrcodeUrl + '\'' +
                ", packageId='" + packageId + '\'' +
                ", applyAmount='" + applyAmount + '\'' +
                ", courtName='" + courtName + '\'' +
                ", typeModeCode='" + typeModeCode + '\'' +
                ", DurationCode='" + DurationCode + '\'' +
                ", addrProCode='" + addrProCode + '\'' +
                ", addrCountyCode='" + addrCountyCode + '\'' +
                ", addrAreaCode='" + addrAreaCode + '\'' +
                ", commodityId='" + commodityId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", caseId='" + caseId + '\'' +
                ", gracePeriodAmount='" + gracePeriodAmount + '\'' +
                ", gracePeriod='" + gracePeriod + '\'' +
                '}';
    }
}

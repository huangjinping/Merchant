package com.hdfex.merchantqrshow.bean.salesman.resource;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * Created by harrishuang on 2017/3/20.
 */

public class Apartment extends BaseBean {


    private String apartmentId;
    private String addrProCode;
    private String addrCountyCode;
    private String addrAreaCode;
    private String courtName;
    private String rentType;
    private String buildingNo;
    private String addrDetail;
    private String apartmentAddress;

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

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getRentType() {
        return rentType;
    }

    public void setRentType(String rentType) {
        this.rentType = rentType;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    public String getApartmentAddress() {
        return apartmentAddress;
    }

    public void setApartmentAddress(String apartmentAddress) {
        this.apartmentAddress = apartmentAddress;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }
}

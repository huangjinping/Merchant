package com.hdfex.merchantqrshow.bean.manager.business;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class BusinessForHouseResult extends BaseBean {

    private  String emptyHouse;
    private  String emptyRate;
    private  String houseCount;

    public String getEmptyHouse() {
        return emptyHouse;
    }

    public void setEmptyHouse(String emptyHouse) {
        this.emptyHouse = emptyHouse;
    }

    public String getEmptyRate() {
        return emptyRate;
    }

    public void setEmptyRate(String emptyRate) {
        this.emptyRate = emptyRate;
    }

    public String getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(String houseCount) {
        this.houseCount = houseCount;
    }

    @Override
    public String toString() {
        return "BusinessForHouseResult{" +
                "emptyHouse='" + emptyHouse + '\'' +
                ", emptyRate='" + emptyRate + '\'' +
                ", houseCount='" + houseCount + '\'' +
                '}';
    }
}

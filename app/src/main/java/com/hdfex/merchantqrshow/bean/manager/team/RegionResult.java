package com.hdfex.merchantqrshow.bean.manager.team;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class RegionResult extends BaseBean {


    private  String regionName;
    private  String custCount;

    private List<Person> personList;

    public List<Person> getPersonList() {
        return personList;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCustCount() {
        return custCount;
    }

    public void setCustCount(String custCount) {
        this.custCount = custCount;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}

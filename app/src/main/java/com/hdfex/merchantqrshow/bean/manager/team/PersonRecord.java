package com.hdfex.merchantqrshow.bean.manager.team;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class PersonRecord extends BaseBean {


    private String name;
    private  String personalAmt;
    private  String region;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalAmt() {
        return personalAmt;
    }

    public void setPersonalAmt(String personalAmt) {
        this.personalAmt = personalAmt;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}

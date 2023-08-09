package com.hdfex.merchantqrshow.bean.manager.team;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class PersonalPerform extends BaseBean {

    private String name;
    private String personalAmt;


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
}

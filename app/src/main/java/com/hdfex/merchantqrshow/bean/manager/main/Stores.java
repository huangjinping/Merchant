package com.hdfex.merchantqrshow.bean.manager.main;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/6/6.
 * email : huangjinping@hdfex.com
 */

public class Stores extends BaseBean {


    private String bussinessId;
    private String  bussinessName;

    public String getBussinessName() {
        return bussinessName;
    }

    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    public String getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(String bussinessId) {
        this.bussinessId = bussinessId;
    }

    public Stores() {
    }

    public Stores(String bussinessId, String bussinessName) {
        this.bussinessId = bussinessId;
        this.bussinessName = bussinessName;
    }
}

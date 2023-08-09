package com.hdfex.merchantqrshow.bean.manager.business;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class BusinessForCurOrderResult extends BaseBean {

    private String curPassCount;
    private  String curOrderCount;
    private  String  backOrderCount;

    public String getCurPassCount() {
        return curPassCount;
    }

    public void setCurPassCount(String curPassCount) {
        this.curPassCount = curPassCount;
    }

    public String getCurOrderCount() {
        return curOrderCount;
    }

    public void setCurOrderCount(String curOrderCount) {
        this.curOrderCount = curOrderCount;
    }

    public String getBackOrderCount() {
        return backOrderCount;
    }

    public void setBackOrderCount(String backOrderCount) {
        this.backOrderCount = backOrderCount;
    }
}

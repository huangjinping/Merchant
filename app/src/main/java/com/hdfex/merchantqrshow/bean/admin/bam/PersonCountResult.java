package com.hdfex.merchantqrshow.bean.admin.bam;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/12/6.
 * email : huangjinping@hdfex.com
 */

public class PersonCountResult extends BaseBean {

    private String startUser;
    private String forbidUser;
    private String awaitPutaway;
    private String putaway;
    private String soldOut;

    public String getStartUser() {
        return startUser;
    }

    public void setStartUser(String startUser) {
        this.startUser = startUser;
    }

    public String getForbidUser() {
        return forbidUser;
    }

    public void setForbidUser(String forbidUser) {
        this.forbidUser = forbidUser;
    }

    public String getAwaitPutaway() {
        return awaitPutaway;
    }

    public void setAwaitPutaway(String awaitPutaway) {
        this.awaitPutaway = awaitPutaway;
    }

    public String getPutaway() {
        return putaway;
    }

    public void setPutaway(String putaway) {
        this.putaway = putaway;
    }

    public String getSoldOut() {
        return soldOut;
    }

    public void setSoldOut(String soldOut) {
        this.soldOut = soldOut;
    }
}

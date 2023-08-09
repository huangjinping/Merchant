package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/9/14.
 * email : huangjinping@hdfex.com
 */

public class TurnRequest extends BaseBean {

    private String turnDesc;
    private String subscribeId;

    public String getTurnDesc() {
        return turnDesc;
    }

    public void setTurnDesc(String turnDesc) {
        this.turnDesc = turnDesc;
    }

    public String getSubscribeId() {
        return subscribeId;
    }

    public void setSubscribeId(String subscribeId) {
        this.subscribeId = subscribeId;
    }
}

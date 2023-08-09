package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2017/9/13.
 * email : huangjinping@hdfex.com
 */

public class SubscribeListResponse extends Response {

    private List<SubscribeItem> result;

    public List<SubscribeItem> getResult() {
        return result;
    }

    public void setResult(List<SubscribeItem> result) {
        this.result = result;
    }
}

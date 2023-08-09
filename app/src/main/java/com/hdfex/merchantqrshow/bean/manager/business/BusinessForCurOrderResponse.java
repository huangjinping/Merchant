package com.hdfex.merchantqrshow.bean.manager.business;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class BusinessForCurOrderResponse extends Response {


    private BusinessForCurOrderResult result;

    public BusinessForCurOrderResult getResult() {
        return result;
    }

    public void setResult(BusinessForCurOrderResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BusinessForCurOrderResponse{" +
                "result=" + result +
                '}';
    }
}

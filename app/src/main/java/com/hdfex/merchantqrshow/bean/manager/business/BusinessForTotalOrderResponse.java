package com.hdfex.merchantqrshow.bean.manager.business;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class BusinessForTotalOrderResponse extends Response {
    private  BusinessForTotalOrderResult result;

    public BusinessForTotalOrderResult getResult() {
        return result;
    }

    public void setResult(BusinessForTotalOrderResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BusinessForTotalOrderResponse{" +
                "result=" + result +
                '}';
    }
}

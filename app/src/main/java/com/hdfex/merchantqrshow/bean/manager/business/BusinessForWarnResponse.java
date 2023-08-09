package com.hdfex.merchantqrshow.bean.manager.business;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class BusinessForWarnResponse extends Response {


    private BusinessForWarnResult result;

    public BusinessForWarnResult getResult() {
        return result;
    }

    public void setResult(BusinessForWarnResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BusinessForWarnResponse{" +
                "result=" + result +
                '}';
    }
}

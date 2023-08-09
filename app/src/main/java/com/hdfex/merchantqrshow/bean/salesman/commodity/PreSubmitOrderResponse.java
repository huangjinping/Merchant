package com.hdfex.merchantqrshow.bean.salesman.commodity;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/8/7.
 * email : huangjinping@hdfex.com
 */

public class PreSubmitOrderResponse extends Response {
    private PreSubmitOrder result;

    public PreSubmitOrder getResult() {
        return result;
    }


    public void setResult(PreSubmitOrder result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "PreSubmitOrderResponse{" +
                "result=" + result +
                '}';
    }
}

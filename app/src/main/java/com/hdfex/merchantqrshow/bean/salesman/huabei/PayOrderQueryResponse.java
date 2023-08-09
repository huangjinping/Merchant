package com.hdfex.merchantqrshow.bean.salesman.huabei;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/8/4.
 * email : huangjinping@hdfex.com
 */

public class PayOrderQueryResponse extends Response {


    private PayOrderQueryResult result;

    public PayOrderQueryResult getResult() {
        return result;
    }

    public void setResult(PayOrderQueryResult result) {
        this.result = result;
    }
}

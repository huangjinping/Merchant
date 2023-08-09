package com.hdfex.merchantqrshow.bean.salesman.huabei;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/6/21.
 * email : huangjinping@hdfex.com
 */

public class AlipaySubmitOrderResponse extends Response {

    private AlipaySubmitOrder result;

    public AlipaySubmitOrder getResult() {
        return result;
    }

    public void setResult(AlipaySubmitOrder result) {
        this.result = result;
    }
}

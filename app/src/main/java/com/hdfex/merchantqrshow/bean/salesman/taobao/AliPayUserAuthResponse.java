package com.hdfex.merchantqrshow.bean.salesman.taobao;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/7/3.
 * email : huangjinping@hdfex.com
 */

public class AliPayUserAuthResponse extends Response {

 private    AliPayUserAuth result;

    public AliPayUserAuth getResult() {
        return result;
    }

    public void setResult(AliPayUserAuth result) {
        this.result = result;
    }
}

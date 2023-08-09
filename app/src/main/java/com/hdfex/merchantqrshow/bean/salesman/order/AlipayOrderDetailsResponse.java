package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.huabei.AlipayOrder;

/**
 * author Created by harrishuang on 2017/7/4.
 * email : huangjinping@hdfex.com
 */

public class AlipayOrderDetailsResponse extends Response {

    private AlipayOrder result;

    public AlipayOrder getResult() {
        return result;
    }

    public void setResult(AlipayOrder result) {
        this.result = result;
    }
}

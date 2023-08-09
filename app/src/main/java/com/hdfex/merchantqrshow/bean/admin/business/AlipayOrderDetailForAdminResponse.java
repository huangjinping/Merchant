package com.hdfex.merchantqrshow.bean.admin.business;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/12/28.
 * email : huangjinping@hdfex.com
 */

public class AlipayOrderDetailForAdminResponse extends Response {
    private AlipayOrderDetail  result;
    public AlipayOrderDetail getResult() {
        return result;
    }

    public void setResult(AlipayOrderDetail result) {
        this.result = result;
    }
}

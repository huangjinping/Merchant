package com.hdfex.merchantqrshow.bean.salesman.huabei;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/22.
 * email : huangjinping@hdfex.com
 */

public class AlipayOrderResponse extends Response {

    private List<AlipayOrder>  result;

    public List<AlipayOrder> getResult() {
        return result;
    }

    public void setResult(List<AlipayOrder> result) {
        this.result = result;
    }
}

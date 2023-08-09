package com.hdfex.merchantqrshow.bean.salesman.resource;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2018/7/12.
 * email : huangjinping@hdfex.com
 */
public class PayTypeResponse extends Response {

    private List<PayType> result;

    public List<PayType> getResult() {
        return result;
    }

    public void setResult(List<PayType> result) {
        this.result = result;
    }
}

package com.hdfex.merchantqrshow.bean.salesman.home;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2019-11-27.
 * email : huangjinping1000@163.com
 */
public class LedingOrderResponse extends Response {

    private List<LedingOrder> result;

    public List<LedingOrder> getResult() {
        return result;
    }

    public void setResult(List<LedingOrder> result) {
        this.result = result;
    }
}

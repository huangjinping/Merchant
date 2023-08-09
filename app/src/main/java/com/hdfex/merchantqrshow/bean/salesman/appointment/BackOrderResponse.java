package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2017/11/17.
 * email : huangjinping@hdfex.com
 */

public class BackOrderResponse extends Response {
    private List<BackOrder>  result;

    public BackOrderResponse(List<BackOrder> result) {
        this.result = result;
    }

    public List<BackOrder> getResult() {
        return result;
    }

    public void setResult(List<BackOrder> result) {
        this.result = result;
    }
}

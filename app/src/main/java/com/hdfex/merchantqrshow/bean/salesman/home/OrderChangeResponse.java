package com.hdfex.merchantqrshow.bean.salesman.home;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2019/11/12.
 * email : huangjinping1000@163.com
 */
public class OrderChangeResponse extends Response {

    private List<OrderChange> result;

    public List<OrderChange> getResult() {
        return result;
    }

    public void setResult(List<OrderChange> result) {
        this.result = result;
    }
}

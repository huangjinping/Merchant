package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2019/9/6.
 * email : huangjinping1000@163.com
 */
public class MultiOrderResponse extends Response {

    private List<MultiOrder> result;

    public List<MultiOrder> getResult() {
        return result;
    }

    public void setResult(List<MultiOrder> result) {
        this.result = result;
    }
}

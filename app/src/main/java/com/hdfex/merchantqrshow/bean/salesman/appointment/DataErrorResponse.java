package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2017/11/17.
 * email : huangjinping@hdfex.com
 */

public class DataErrorResponse extends Response {
    private List<DataErrorItem>  result;

    public List<DataErrorItem> getResult() {
        return result;
    }

    public void setResult(List<DataErrorItem> result) {
        this.result = result;
    }
}

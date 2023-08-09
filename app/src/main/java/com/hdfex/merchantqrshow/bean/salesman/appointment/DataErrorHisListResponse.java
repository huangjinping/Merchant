package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/11/20.
 * email : huangjinping@hdfex.com
 */

public class DataErrorHisListResponse extends Response {
    private DataErrorHisItem result;

    public DataErrorHisItem getResult() {
        return result;
    }

    public void setResult(DataErrorHisItem result) {
        this.result = result;
    }
}

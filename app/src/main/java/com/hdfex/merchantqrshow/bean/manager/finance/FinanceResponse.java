package com.hdfex.merchantqrshow.bean.manager.finance;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/6/7.
 * email : huangjinping@hdfex.com
 */

public class FinanceResponse extends Response {


    private  Finance result;


    public Finance getResult() {
        return result;
    }

    public void setResult(Finance result) {
        this.result = result;
    }
}

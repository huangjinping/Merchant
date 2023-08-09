package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by harrishuang on 2017/3/2.
 */

public class PeriodAmountResponese  extends Response{

    private  PeriodAmountResult result;

    public PeriodAmountResult getResult() {
        return result;
    }

    public void setResult(PeriodAmountResult result) {
        this.result = result;
    }
}

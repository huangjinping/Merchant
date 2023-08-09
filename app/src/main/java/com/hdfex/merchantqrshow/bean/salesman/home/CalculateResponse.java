package com.hdfex.merchantqrshow.bean.salesman.home;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by harrishuang on 2017/4/26.
 */

public class CalculateResponse extends Response{
    private  CalculateResult result;

    public CalculateResult getResult() {
        return result;
    }

    public void setResult(CalculateResult result) {
        this.result = result;
    }
}

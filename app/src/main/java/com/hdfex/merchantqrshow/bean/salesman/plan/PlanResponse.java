package com.hdfex.merchantqrshow.bean.salesman.plan;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/5/26.
 * email : huangjinping@hdfex.com
 */

public class PlanResponse extends Response {

    private PlanDetail result;

    public PlanDetail getResult() {
        return result;
    }

    public void setResult(PlanDetail result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "PlanResponse{" +
                "result=" + result +
                '}';
    }
}

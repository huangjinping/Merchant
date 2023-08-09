package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/11/20.
 * email : huangjinping@hdfex.com
 */

public class FollowUpMaterialDetailQueryResponse extends Response {
    private FollowUpMaterialDetailQueryResult result;

    public FollowUpMaterialDetailQueryResult getResult() {
        return result;
    }

    public void setResult(FollowUpMaterialDetailQueryResult result) {
        this.result = result;
    }
}

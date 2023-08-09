package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/9/14.
 * email : huangjinping@hdfex.com
 */

public class FollowUpDetailQueryResponse extends Response {
    private FollowUpDetail result;

    public FollowUpDetail getResult() {
        return result;
    }

    public void setResult(FollowUpDetail result) {
        this.result = result;
    }
}

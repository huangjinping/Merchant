package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/11/20.
 * email : huangjinping@hdfex.com
 */

public class FollowUpDetailResponse extends Response {

    private FollowUpDetailItem result;

    public FollowUpDetailItem getResult() {
        return result;
    }

    public void setResult(FollowUpDetailItem result) {
        this.result = result;
    }
}

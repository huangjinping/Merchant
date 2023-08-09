package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2017/11/14.
 * email : huangjinping@hdfex.com
 */

public class FollowUpListQueryV2Response extends Response {


    private List<FollowUpListQueryV2Result> result;

    public List<FollowUpListQueryV2Result> getResult() {
        return result;
    }

    public void setResult(List<FollowUpListQueryV2Result> result) {
        this.result = result;
    }
}

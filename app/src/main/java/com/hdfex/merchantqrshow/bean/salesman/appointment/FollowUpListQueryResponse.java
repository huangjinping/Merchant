package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2017/9/14.
 * email : huangjinping@hdfex.com
 */

public class FollowUpListQueryResponse extends Response {

    private List<FollowUpItem>   result;

    public List<FollowUpItem> getResult() {
        return result;
    }

    public void setResult(List<FollowUpItem> result) {
        this.result = result;
    }
}

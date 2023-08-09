package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2017/11/17.
 * email : huangjinping@hdfex.com
 */

public class RepayListResponse extends Response {

    private List<RepayListItem> result;

    public List<RepayListItem> getResult() {
        return result;
    }

    public void setResult(List<RepayListItem> result) {
        this.result = result;
    }
}

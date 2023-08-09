package com.hdfex.merchantqrshow.bean.admin.bam;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/12/19.
 * email : huangjinping@hdfex.com
 */

public class QueryFinanceInfoResponse extends Response {

    private  QueryFinanceInfo result;

    public QueryFinanceInfo getResult() {
        return result;
    }

    public void setResult(QueryFinanceInfo result) {
        this.result = result;
    }
}

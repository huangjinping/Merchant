package com.hdfex.merchantqrshow.bean.admin.bam;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2017/12/7.
 * email : huangjinping@hdfex.com
 */

public class QueryBussinessCaseInfoResponse extends Response {
    private List<QueryBussinessCaseInfo> result;

    public List<QueryBussinessCaseInfo> getResult() {
        return result;
    }

    public void setResult(List<QueryBussinessCaseInfo> result) {
        this.result = result;
    }
}

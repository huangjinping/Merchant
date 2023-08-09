package com.hdfex.merchantqrshow.bean.salesman.redpackage;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/7/3.
 * email : huangjinping@hdfex.com
 */

public class RedPackageListResponse extends Response {


    private RedPackageResult result;

    public RedPackageResult getResult() {
        return result;
    }

    public void setResult(RedPackageResult result) {
        this.result = result;
    }
}

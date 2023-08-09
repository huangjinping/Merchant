package com.hdfex.merchantqrshow.bean.admin.bam;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/12/7.
 * email : huangjinping@hdfex.com
 */

public class FindCommodityDetailResponse extends Response {
    private  FindCommodityDetail result;

    public FindCommodityDetail getResult() {
        return result;
    }

    public void setResult(FindCommodityDetail result) {
        this.result = result;
    }
}

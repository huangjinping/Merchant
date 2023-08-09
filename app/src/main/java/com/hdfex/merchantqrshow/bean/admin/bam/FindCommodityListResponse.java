package com.hdfex.merchantqrshow.bean.admin.bam;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2017/12/6.
 * email : huangjinping@hdfex.com
 */

public class FindCommodityListResponse extends Response {

    private List<FindCommodityItem> result;

    public List<FindCommodityItem> getResult() {
        return result;
    }

    public void setResult(List<FindCommodityItem> result) {
        this.result = result;
    }
}

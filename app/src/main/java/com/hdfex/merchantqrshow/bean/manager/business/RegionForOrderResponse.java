package com.hdfex.merchantqrshow.bean.manager.business;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/7.
 * email : huangjinping@hdfex.com
 */

public class RegionForOrderResponse extends Response {

    private List<RegionForOrder> result;

    public List<RegionForOrder> getResult() {
        return result;
    }

    public void setResult(List<RegionForOrder> result) {
        this.result = result;
    }
}

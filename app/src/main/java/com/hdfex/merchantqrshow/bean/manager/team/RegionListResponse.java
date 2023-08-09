package com.hdfex.merchantqrshow.bean.manager.team;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class RegionListResponse extends Response {

    private List<RegionResult> result;

    public List<RegionResult> getResult() {
        return result;
    }

    public void setResult(List<RegionResult> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RegionListResponse{" +
                "result=" + result +
                '}';
    }
}

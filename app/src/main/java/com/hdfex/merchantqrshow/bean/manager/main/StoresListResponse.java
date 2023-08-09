package com.hdfex.merchantqrshow.bean.manager.main;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/6.
 * email : huangjinping@hdfex.com
 */

public class StoresListResponse extends Response {

    private List<Stores> result;

    public List<Stores> getResult() {
        return result;
    }

    public void setResult(List<Stores> result) {
        this.result = result;
    }
}

package com.hdfex.merchantqrshow.bean.salesman.resource;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * Created by harrishuang on 2017/2/20.
 */

public class HouseListResponse extends Response {


    private List<ItemHouse> result;

    public List<ItemHouse> getResult() {
        return result;
    }

    public void setResult(List<ItemHouse> result) {
        this.result = result;
    }
}

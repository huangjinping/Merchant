package com.hdfex.merchantqrshow.bean.salesman.resource;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * Created by harrishuang on 2017/3/20.
 */

public class ApartmentResponse extends Response {


    private List<Apartment> result;

    public List<Apartment> getResult() {
        return result;
    }

    public void setResult(List<Apartment> result) {
        this.result = result;
    }
}

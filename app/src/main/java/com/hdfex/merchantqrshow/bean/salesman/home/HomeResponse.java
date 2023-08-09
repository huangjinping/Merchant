package com.hdfex.merchantqrshow.bean.salesman.home;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by harrishuang on 16/10/14.
 */

public class HomeResponse  extends Response{

    private  HomeResult result;

    public HomeResult getResult() {
        return result;
    }

    public void setResult(HomeResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "HomeResponse{" +
                "result=" + result +
                '}';
    }
}

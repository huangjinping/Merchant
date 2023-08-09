package com.hdfex.merchantqrshow.bean.salesman.home;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2019/6/3.
 * email : huangjinping1000@163.com
 */
public class SignStatusListResponse extends Response {


    private List<SignStatusItem> result;

    public List<SignStatusItem> getResult() {
        return result;
    }

    public void setResult(List<SignStatusItem> result) {
        this.result = result;
    }
}

package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2019/9/6.
 * email : huangjinping1000@163.com
 */
public class UserStepResponse extends Response {

    private List<UserStep> result;

    public List<UserStep> getResult() {
        return result;
    }

    public void setResult(List<UserStep> result) {
        this.result = result;
    }
}

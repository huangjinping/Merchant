package com.hdfex.merchantqrshow.bean.admin.business;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2017/12/28.
 * email : huangjinping@hdfex.com
 */

public class AlipayOrderForAdminResponse extends Response {
    private List<AlipayOrderForAdmin> result;

    public List<AlipayOrderForAdmin> getResult() {
        return result;
    }

    public void setResult(List<AlipayOrderForAdmin> result) {
        this.result = result;
    }
}

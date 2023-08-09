package com.hdfex.merchantqrshow.bean.salesman.home;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2018/10/26.
 * email : huangjinping@hdfex.com
 */
public class NoticeResponse extends Response {
    private List<Notice> result;

    public List<Notice> getResult() {
        return result;
    }

    public void setResult(List<Notice> result) {
        this.result = result;
    }
}

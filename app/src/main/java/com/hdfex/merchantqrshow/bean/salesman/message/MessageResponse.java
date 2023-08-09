package com.hdfex.merchantqrshow.bean.salesman.message;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2017/7/3.
 * email : huangjinping@hdfex.com
 */

public class MessageResponse extends Response {

    private List<MessageItem>  result;

    public List<MessageItem> getResult() {
        return result;
    }

    public void setResult(List<MessageItem> result) {
        this.result = result;
    }
}

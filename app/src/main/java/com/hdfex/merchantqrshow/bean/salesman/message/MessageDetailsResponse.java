package com.hdfex.merchantqrshow.bean.salesman.message;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/7/7.
 * email : huangjinping@hdfex.com
 */

public class MessageDetailsResponse extends Response {

    private MessageDetails result;

    public MessageDetails getResult() {
        return result;
    }

    public void setResult(MessageDetails result) {
        this.result = result;
    }
}

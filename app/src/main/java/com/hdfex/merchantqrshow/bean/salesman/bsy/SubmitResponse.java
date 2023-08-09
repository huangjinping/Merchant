package com.hdfex.merchantqrshow.bean.salesman.bsy;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by harrishuang on 2016/11/28.
 */

public class SubmitResponse extends Response {
   private SubmitResult result;

    public SubmitResult getResult() {
        return result;
    }

    public void setResult(SubmitResult result) {
        this.result = result;
    }
}

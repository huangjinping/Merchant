package com.hdfex.merchantqrshow.bean.salesman.bsy;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by harrishuang on 2016/11/29.
 */

public class BSYOrderDetailsResponse extends Response {

    private  BSYOrderItem   result;

    public BSYOrderItem getResult() {
        return result;
    }

    public void setResult(BSYOrderItem result) {
        this.result = result;
    }
}

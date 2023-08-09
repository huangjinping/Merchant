package com.hdfex.merchantqrshow.bean.salesman.bsy;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by harrishuang on 2016/11/29.
 */

public class BSYOrderListResponse extends Response {

private BSYOrderItemResult result;


    public BSYOrderItemResult getResult() {
        return result;
    }

    public void setResult(BSYOrderItemResult result) {
        this.result = result;
    }
}

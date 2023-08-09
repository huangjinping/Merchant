package com.hdfex.merchantqrshow.bean.salesman.bsy;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by harrishuang on 2016/11/26.
 */

public class ProductDetailResponse extends Response {

    private  ProductItem result;

    public ProductItem getResult() {
        return result;
    }

    public void setResult(ProductItem result) {
        this.result = result;
    }


}

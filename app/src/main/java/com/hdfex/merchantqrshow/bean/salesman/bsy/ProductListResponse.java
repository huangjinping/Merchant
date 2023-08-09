package com.hdfex.merchantqrshow.bean.salesman.bsy;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by harrishuang on 2016/11/25.
 */

public class ProductListResponse  extends Response{

   private  ProductListResult result;

    public ProductListResult getResult() {
        return result;
    }

    public void setResult(ProductListResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ProductListResponse{" +
                "result=" + result +
                '}';
    }
}

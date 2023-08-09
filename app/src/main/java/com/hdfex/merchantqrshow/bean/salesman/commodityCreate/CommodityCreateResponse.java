package com.hdfex.merchantqrshow.bean.salesman.commodityCreate;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by maidou521 on 2016/9/7.
 */
public class CommodityCreateResponse extends Response {
    private CommodityCreateInfo result;

    public CommodityCreateInfo getResult() {
        return result;
    }

    public void setResult(CommodityCreateInfo result) {
        this.result = result;
    }
}

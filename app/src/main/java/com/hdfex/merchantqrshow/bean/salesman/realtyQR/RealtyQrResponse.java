package com.hdfex.merchantqrshow.bean.salesman.realtyQR;

import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.CommodityCreateModel;

/**
 * Created by maidou521 on 2016/9/7.
 */
public class RealtyQrResponse extends Response{
    private CommodityCreateModel result;

    public CommodityCreateModel getResult() {
        return result;
    }

    public void setResult(CommodityCreateModel result) {
        this.result = result;
    }
}

package com.hdfex.merchantqrshow.bean.salesman.commodity;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by maidou521 on 2016/7/5.
 */
public class TypeResponse extends Response{
    private CommodityType result;

    public CommodityType getCommodityType() {
        return result;
    }

    public void setCommodityType(CommodityType commodityType) {
        this.result = commodityType;
    }

    @Override
    public String toString() {
        return "TypeResponse{" +
                "commodityType=" + result +
                '}';
    }
}

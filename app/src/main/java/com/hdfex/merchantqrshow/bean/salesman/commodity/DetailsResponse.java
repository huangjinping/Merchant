package com.hdfex.merchantqrshow.bean.salesman.commodity;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by maidou521 on 2016/7/5.
 */
public class DetailsResponse extends Response{
    private CommodityDetails result;

    public CommodityDetails getCommodityDetails() {
        return result;
    }

    public void setCommodityDetails(CommodityDetails commodityDetails) {
        this.result = commodityDetails;
    }

    @Override
    public String toString() {
        return "DetailsResponse{" +
                "commodityDetails=" + result +
                '}';
    }
}

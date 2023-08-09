package com.hdfex.merchantqrshow.bean.admin.bam;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/12/6.
 * email : huangjinping@hdfex.com
 */

public class FindCommodityItem extends BaseBean {


    /**
     * commoditCategoryCode : 020320
     * commoditCategory : 华为
     * commodityPrice : 6000.0
     * commodityName : p10
     * pic : https://hdfex.com/hd-merchant-web/mobile/picture/down?file=commodity_brand/huawei.jpg
     * commodityId : 6305704538652279809
     */

    private String commoditCategoryCode;
    private String commoditCategory;
    private String commodityPrice;
    private String commodityName;
    private String pic;
    private String commodityId;

    public String getCommoditCategoryCode() {
        return commoditCategoryCode;
    }

    public void setCommoditCategoryCode(String commoditCategoryCode) {
        this.commoditCategoryCode = commoditCategoryCode;
    }

    public String getCommoditCategory() {
        return commoditCategory;
    }

    public void setCommoditCategory(String commoditCategory) {
        this.commoditCategory = commoditCategory;
    }

    public String getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(String commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }
}

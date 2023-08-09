package com.hdfex.merchantqrshow.bean.admin.bam;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * author Created by harrishuang on 2017/12/7.
 * email : huangjinping@hdfex.com
 */

public class FindCommodityDetail extends BaseBean {


    /**
     * commodityCategoryList : [{"categoryCode":"0201","categoryName":"计算机"},{"categoryCode":"0202","categoryName":"网络通讯"},{"categoryCode":"0203","categoryName":"手机"}]
     * caseStr :
     * commoditCategory : 0203
     * commoditCategoryName : 03
     * brandList : [{"brand":"苹果","brandCode":"020310"},{"brand":"华为","brandCode":"020320"},{"brand":"小米","brandCode":"020330"},{"brand":"VIVO","brandCode":"020340"},{"brand":"oppo","brandCode":"020350"},{"brand":"魅族","brandCode":"020360"},{"brand":"锤子","brandCode":"020370"},{"brand":"三星","brandCode":"020380"}]
     */

    private String caseStr;
    private String commoditCategory;
    private String commoditCategoryName;
    private List<CommodityCategoryListBean> commodityCategoryList;
    private List<BrandListBean> brandList;

    public String getCaseStr() {
        return caseStr;
    }

    public void setCaseStr(String caseStr) {
        this.caseStr = caseStr;
    }

    public String getCommoditCategory() {
        return commoditCategory;
    }

    public void setCommoditCategory(String commoditCategory) {
        this.commoditCategory = commoditCategory;
    }

    public String getCommoditCategoryName() {
        return commoditCategoryName;
    }

    public void setCommoditCategoryName(String commoditCategoryName) {
        this.commoditCategoryName = commoditCategoryName;
    }

    public List<CommodityCategoryListBean> getCommodityCategoryList() {
        return commodityCategoryList;
    }

    public void setCommodityCategoryList(List<CommodityCategoryListBean> commodityCategoryList) {
        this.commodityCategoryList = commodityCategoryList;
    }

    public List<BrandListBean> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<BrandListBean> brandList) {
        this.brandList = brandList;
    }
}

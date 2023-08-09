package com.hdfex.merchantqrshow.bean.salesman.commodityCreate;

import com.hdfex.merchantqrshow.bean.BaseBean;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageModel;

import java.util.List;

/**
 * Created by maidou521 on 2016/9/7.
 */
public class CreateSeri extends BaseBean {
    private CommodityCreateModel commodityCreateModel;
    private CommodityCreateInfo commodityCreateInfo;
    private List<ImageModel> rentModelLists;
    private List<ImageModel> agencyModelLists;

    public List<ImageModel> getRentModelLists() {
        return rentModelLists;
    }

    public void setRentModelLists(List<ImageModel> rentModelLists) {
        this.rentModelLists = rentModelLists;
    }

    public List<ImageModel> getAgencyModelLists() {
        return agencyModelLists;
    }

    public void setAgencyModelLists(List<ImageModel> agencyModelLists) {
        this.agencyModelLists = agencyModelLists;
    }

    public CommodityCreateModel getCommodityCreateModel() {
        return commodityCreateModel;
    }

    public void setCommodityCreateModel(CommodityCreateModel commodityCreateModel) {
        this.commodityCreateModel = commodityCreateModel;
    }

    public CommodityCreateInfo getCommodityCreateInfo() {
        return commodityCreateInfo;
    }

    public void setCommodityCreateInfo(CommodityCreateInfo commodityCreateInfo) {
        this.commodityCreateInfo = commodityCreateInfo;
    }
}

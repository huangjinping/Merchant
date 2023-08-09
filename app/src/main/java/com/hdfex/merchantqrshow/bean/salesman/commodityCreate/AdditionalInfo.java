package com.hdfex.merchantqrshow.bean.salesman.commodityCreate;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * Created by maidou521 on 2016/9/9.
 */
public class AdditionalInfo extends BaseBean{
    private List<TypeModel> payTypeList;
    private List<TypeModel> durationList;

    public List<TypeModel> getPayTypeList() {
        return payTypeList;
    }

    public void setPayTypeList(List<TypeModel> payTypeList) {
        this.payTypeList = payTypeList;
    }

    public List<TypeModel> getDurationList() {
        return durationList;
    }

    public void setDurationList(List<TypeModel> durationList) {
        this.durationList = durationList;
    }
}

package com.hdfex.merchantqrshow.bean.salesman.commodity;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * Created by maidou521 on 2016/7/5.
 */
public class CommodityDetails extends BaseBean {
    private List<Details> list;

    public List<Details> getList() {
        return list;
    }

    public void setList(List<Details> list) {
        this.list = list;
    }
}

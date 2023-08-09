package com.hdfex.merchantqrshow.bean.salesman.commodity;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * Created by maidou521 on 2016/7/5.
 */
public class CommodityType extends BaseBean {
    private List<Type> list;

    public List<Type> getList() {
        return list;
    }

    public void setList(List<Type> list) {
        this.list = list;
    }
}

package com.hdfex.merchantqrshow.bean.salesman.bsy;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * Created by harrishuang on 2016/11/25.
 */

public class ProductListResult extends BaseBean{

    private List<ProductItem> list;

    public List<ProductItem> getList() {
        return list;
    }

    public void setList(List<ProductItem> list) {
        this.list = list;
    }

}

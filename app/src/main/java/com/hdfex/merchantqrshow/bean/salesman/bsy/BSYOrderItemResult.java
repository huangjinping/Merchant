package com.hdfex.merchantqrshow.bean.salesman.bsy;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * Created by harrishuang on 2016/11/29.
 */

public class BSYOrderItemResult extends BaseBean {

    private List<BSYOrderItem> list;

    public List<BSYOrderItem> getList() {
        return list;
    }

    public void setList(List<BSYOrderItem> list) {
        this.list = list;
    }
}

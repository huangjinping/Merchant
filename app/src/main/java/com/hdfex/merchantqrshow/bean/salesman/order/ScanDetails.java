package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * Created by maidou521 on 2016/7/19.
 */
public class ScanDetails extends BaseBean {
    private List<QueryUncompelete> list;

    public List<QueryUncompelete> getList() {
        return list;
    }

    public void setList(List<QueryUncompelete> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ScanDetails{" +
                "list=" + list +
                '}';
    }
}

package com.hdfex.merchantqrshow.bean.salesman.commodity;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * Created by maidou521 on 2016/7/5.
 */
public class Type extends BaseBean{
    private String category;
    public Type(){}
    public Type(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Type{" +
                "category='" + category + '\'' +
                '}';
    }
}

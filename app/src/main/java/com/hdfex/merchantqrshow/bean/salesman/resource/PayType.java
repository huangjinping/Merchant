package com.hdfex.merchantqrshow.bean.salesman.resource;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2018/7/12.
 * email : huangjinping@hdfex.com
 */
public class PayType extends BaseBean {

    private String value;
    private String name;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

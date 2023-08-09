package com.hdfex.merchantqrshow.bean.salesman.customer;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * Created by harrishuang on 16/10/14.
 */

public class Lable extends BaseBean {

    private  String labelName;

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    @Override
    public String toString() {
        return "Lable{" +
                "labelName='" + labelName + '\'' +
                '}';
    }
}

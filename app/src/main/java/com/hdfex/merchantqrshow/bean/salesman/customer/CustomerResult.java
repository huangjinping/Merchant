package com.hdfex.merchantqrshow.bean.salesman.customer;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * Created by harrishuang on 16/10/14.
 */

public class CustomerResult extends BaseBean {

    private  String custName;
    private  String phoneticName;
    private  String phone;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getPhoneticName() {
        return phoneticName;
    }

    public void setPhoneticName(String phoneticName) {
        this.phoneticName = phoneticName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "CustomerResult{" +
                "custName='" + custName + '\'' +
                ", phoneticName='" + phoneticName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

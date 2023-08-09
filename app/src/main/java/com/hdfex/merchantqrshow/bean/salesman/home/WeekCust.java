package com.hdfex.merchantqrshow.bean.salesman.home;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * Created by harrishuang on 16/10/14.
 */

public class WeekCust extends BaseBean {

    private  String date;
    private  String  custCount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustCount() {
        return custCount;
    }

    public void setCustCount(String custCount) {
        this.custCount = custCount;
    }

    @Override
    public String toString() {
        return "WeekCust{" +
                "date='" + date + '\'' +
                ", custCount='" + custCount + '\'' +
                '}';
    }
}

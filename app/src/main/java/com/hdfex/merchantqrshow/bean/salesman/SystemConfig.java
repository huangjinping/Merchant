package com.hdfex.merchantqrshow.bean.salesman;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2020-01-07.
 * email : huangjinping1000@163.com
 */
public class SystemConfig extends BaseBean {

    private int authFlag;
    private String k12AuthFlag;
    private String orderString;
    private  String configValue;

    public int getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(int authFlag) {
        this.authFlag = authFlag;
    }

    public String getK12AuthFlag() {
        return k12AuthFlag;
    }

    public void setK12AuthFlag(String k12AuthFlag) {
        this.k12AuthFlag = k12AuthFlag;
    }

    public String getOrderString() {
        return orderString;
    }

    public void setOrderString(String orderString) {
        this.orderString = orderString;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}

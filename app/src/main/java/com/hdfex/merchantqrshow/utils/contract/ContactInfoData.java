package com.hdfex.merchantqrshow.utils.contract;

import java.io.Serializable;

/**
 * author Created by harrishuang on 2017/9/4.
 * email : huangjinping@hdfex.com
 */

public class ContactInfoData implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private String mobile;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "ContactInfoData{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}

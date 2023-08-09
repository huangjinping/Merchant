package com.hdfex.merchantqrshow.bean.salesman;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2019/9/9.
 * email : huangjinping1000@163.com
 */
public class NameValue extends BaseBean {
    private String key;
    private String name;

    public NameValue() {
    }

    public NameValue(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

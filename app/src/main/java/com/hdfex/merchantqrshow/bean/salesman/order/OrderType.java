package com.hdfex.merchantqrshow.bean.salesman.order;

/**
 * Created by harrishuang on 2017/4/25.
 */

public class OrderType {
    private String type;
    private String name;

    public OrderType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

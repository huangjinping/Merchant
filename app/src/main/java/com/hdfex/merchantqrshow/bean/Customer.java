package com.hdfex.merchantqrshow.bean;

/**
 * Created by harrishuang on 16/9/28.
 */

public class Customer extends BaseBean {

    private  String name;
    private  String head;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", head='" + head + '\'' +
                '}';
    }
}

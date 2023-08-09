package com.hdfex.merchantqrshow.bean.salesman.home;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2018/10/26.
 * email : huangjinping@hdfex.com
 */
public class Notice extends BaseBean {
    private String title;
    private String content;
    private String contentUrl;
    private String url;
    private String pulishTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPulishTime() {
        return pulishTime;
    }

    public void setPulishTime(String pulishTime) {
        this.pulishTime = pulishTime;
    }
}

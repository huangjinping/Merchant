package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/11/20.
 * email : huangjinping@hdfex.com
 */

public class FollowUpItemWeek extends BaseBean{

    private String bizCallFllowDate;
    private String followUpListOutweek;
    private String bizRemark;

    public String getBizCallFllowDate() {
        return bizCallFllowDate;
    }

    public void setBizCallFllowDate(String bizCallFllowDate) {
        this.bizCallFllowDate = bizCallFllowDate;
    }

    public String getFollowUpListOutweek() {
        return followUpListOutweek;
    }

    public void setFollowUpListOutweek(String followUpListOutweek) {
        this.followUpListOutweek = followUpListOutweek;
    }

    public String getBizRemark() {
        return bizRemark;
    }

    public void setBizRemark(String bizRemark) {
        this.bizRemark = bizRemark;
    }
}

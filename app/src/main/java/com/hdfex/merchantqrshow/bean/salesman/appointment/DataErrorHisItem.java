package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * author Created by harrishuang on 2017/11/20.
 * email : huangjinping@hdfex.com
 */

public class DataErrorHisItem extends BaseBean {

    private String custId;
    private String custName;
    private String loanAmount;
    private String type;
    private List<FollowUpItemWeek> followUpListInweek;
    private List<FollowUpItemWeek> followUpListOutweek;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<FollowUpItemWeek> getFollowUpListInweek() {
        return followUpListInweek;
    }

    public void setFollowUpListInweek(List<FollowUpItemWeek> followUpListInweek) {
        this.followUpListInweek = followUpListInweek;
    }

    public List<FollowUpItemWeek> getFollowUpListOutweek() {
        return followUpListOutweek;
    }

    public void setFollowUpListOutweek(List<FollowUpItemWeek> followUpListOutweek) {
        this.followUpListOutweek = followUpListOutweek;
    }
}

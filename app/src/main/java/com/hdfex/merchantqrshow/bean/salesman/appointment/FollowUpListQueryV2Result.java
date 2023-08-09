package com.hdfex.merchantqrshow.bean.salesman.appointment;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/11/14.
 * email : huangjinping@hdfex.com
 */

public class FollowUpListQueryV2Result extends BaseBean {


    /**
     * custCount : 0
     * type : 00
     * remindDesc : 提醒客户接听电话，提高订单审核效率
     */

    private int custCount;
    private String type;
    private String remindDesc;

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCustCount() {
        return custCount;
    }

    public void setCustCount(int custCount) {
        this.custCount = custCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemindDesc() {
        return remindDesc;
    }

    public void setRemindDesc(String remindDesc) {
        this.remindDesc = remindDesc;
    }
}

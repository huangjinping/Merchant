package com.hdfex.merchantqrshow.bean.salesman.huabei;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/8/15.
 * email : huangjinping@hdfex.com
 */

public class AlipayPlan extends BaseBean {
    private String duration;
    private  String durationAmt;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDurationAmt() {
        return durationAmt;
    }

    public void setDurationAmt(String durationAmt) {
        this.durationAmt = durationAmt;
    }

    @Override
    public String toString() {
        return "AlipayPlan{" +
                "duration='" + duration + '\'' +
                ", durationAmt='" + durationAmt + '\'' +
                '}';
    }
}

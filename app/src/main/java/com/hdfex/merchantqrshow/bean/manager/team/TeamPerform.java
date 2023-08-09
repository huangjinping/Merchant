package com.hdfex.merchantqrshow.bean.manager.team;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class TeamPerform extends BaseBean {

    private  String region;
    private  String  teamAmt;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTeamAmt() {
        return teamAmt;
    }

    public void setTeamAmt(String teamAmt) {
        this.teamAmt = teamAmt;
    }
}

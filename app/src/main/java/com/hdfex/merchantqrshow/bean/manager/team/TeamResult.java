package com.hdfex.merchantqrshow.bean.manager.team;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class TeamResult extends BaseBean {

    private String watchword;

    private List<TeamPerform> teamPerformList;

    private List<PersonalPerform> personalPerformList;

    private String bussPersonCount;
    private String teamCount;
    private String outRate;
    private String inRate;
    private String praiseRate;

    public String getWatchword() {
        return watchword;
    }

    public void setWatchword(String watchword) {
        this.watchword = watchword;
    }

    public List<TeamPerform> getTeamPerformList() {
        return teamPerformList;
    }

    public void setTeamPerformList(List<TeamPerform> teamPerformList) {
        this.teamPerformList = teamPerformList;
    }

    public List<PersonalPerform> getPersonalPerformList() {
        return personalPerformList;
    }

    public void setPersonalPerformList(List<PersonalPerform> personalPerformList) {
        this.personalPerformList = personalPerformList;
    }

    public String getBussPersonCount() {
        return bussPersonCount;
    }

    public void setBussPersonCount(String bussPersonCount) {
        this.bussPersonCount = bussPersonCount;
    }

    public String getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(String teamCount) {
        this.teamCount = teamCount;
    }

    public String getOutRate() {
        return outRate;
    }

    public void setOutRate(String outRate) {
        this.outRate = outRate;
    }

    public String getInRate() {
        return inRate;
    }

    public void setInRate(String inRate) {
        this.inRate = inRate;
    }

    public String getPraiseRate() {
        return praiseRate;
    }

    public void setPraiseRate(String praiseRate) {
        this.praiseRate = praiseRate;
    }
}

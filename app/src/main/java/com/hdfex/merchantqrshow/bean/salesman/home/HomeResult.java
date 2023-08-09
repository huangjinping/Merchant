package com.hdfex.merchantqrshow.bean.salesman.home;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by harrishuang on 16/10/14.
 */

public class HomeResult extends BaseBean {

    private BigDecimal monthTotAmount;
    private String checkingOrders;
    private String payingOrders;
    private String monthCustCount;
    private String totCustCount;
    private List<WeekCust> weekCustList;
    private List<Adcommodity> banners;


    public List<Adcommodity> getBanners() {
        return banners;
    }

    public void setBanners(List<Adcommodity> banners) {
        this.banners = banners;
    }

    public BigDecimal getMonthTotAmount() {
        return monthTotAmount;
    }

    public void setMonthTotAmount(BigDecimal monthTotAmount) {
        this.monthTotAmount = monthTotAmount;
    }

    public String getCheckingOrders() {
        return checkingOrders;
    }

    public void setCheckingOrders(String checkingOrders) {
        this.checkingOrders = checkingOrders;
    }

    public String getPayingOrders() {
        return payingOrders;
    }

    public void setPayingOrders(String payingOrders) {
        this.payingOrders = payingOrders;
    }

    public String getMonthCustCount() {
        return monthCustCount;
    }

    public void setMonthCustCount(String monthCustCount) {
        this.monthCustCount = monthCustCount;
    }

    public String getTotCustCount() {
        return totCustCount;
    }

    public void setTotCustCount(String totCustCount) {
        this.totCustCount = totCustCount;
    }

    public List<WeekCust> getWeekCustList() {
        return weekCustList;
    }

    public void setWeekCustList(List<WeekCust> weekCustList) {
        this.weekCustList = weekCustList;
    }

    @Override
    public String toString() {
        return "HomeResult{" +
                "monthTotAmount=" + monthTotAmount +
                ", checkingOrders='" + checkingOrders + '\'' +
                ", payingOrders='" + payingOrders + '\'' +
                ", monthCustCount='" + monthCustCount + '\'' +
                ", totCustCount='" + totCustCount + '\'' +
                ", weekCustList=" + weekCustList +
                '}';
    }
}

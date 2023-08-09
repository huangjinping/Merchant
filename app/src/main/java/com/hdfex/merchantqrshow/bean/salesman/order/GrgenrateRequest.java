package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.Request;
import com.hdfex.merchantqrshow.bean.salesman.commodity.Details;

import java.util.List;

/**
 * Created by harrishuang on 16/7/11.
 */
public class GrgenrateRequest extends Request {

    private  String token;
    private  String  id;
    private  String duration;
    private  String downpayment;
    private  String ApplyAmount;
    private  String periodAmount;
    private List<Details>  list;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDownpayment() {
        return downpayment;
    }

    public void setDownpayment(String downpayment) {
        this.downpayment = downpayment;
    }

    public String getApplyAmount() {
        return ApplyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        ApplyAmount = applyAmount;
    }

    public String getPeriodAmount() {
        return periodAmount;
    }

    public void setPeriodAmount(String periodAmount) {
        this.periodAmount = periodAmount;
    }

    public List<Details> getList() {
        return list;
    }

    public void setList(List<Details> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "GrgenrateRequest{" +
                "token='" + token + '\'' +
                ", id='" + id + '\'' +
                ", duration='" + duration + '\'' +
                ", downpayment='" + downpayment + '\'' +
                ", ApplyAmount='" + ApplyAmount + '\'' +
                ", periodAmount='" + periodAmount + '\'' +
                ", list=" + list +
                '}';
    }
}

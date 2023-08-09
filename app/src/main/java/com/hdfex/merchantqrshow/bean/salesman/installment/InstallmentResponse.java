package com.hdfex.merchantqrshow.bean.salesman.installment;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * Created by maidou521 on 2016/7/11.
 */
public class InstallmentResponse extends Response{
    private List<Installment> result;

    public List<Installment> getResult() {
        return result;
    }

    public void setResult(List<Installment> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "InstallmentResponse{" +
                "result=" + result +
                '}';
    }
}

package com.hdfex.merchantqrshow.bean.salesman.commodityCreate;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by harrishuang on 2017/2/20.
 */

public class CaluateDueDateResponse extends Response {


    private  CaluateDueDateResult result;

    public CaluateDueDateResult getResult() {
        return result;
    }

    public void setResult(CaluateDueDateResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CaluateDueDateResponse{" +
                "result=" + result +
                '}';
    }
}

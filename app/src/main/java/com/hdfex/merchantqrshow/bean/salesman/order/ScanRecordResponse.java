package com.hdfex.merchantqrshow.bean.salesman.order;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by maidou521 on 2016/7/19.
 */
public class ScanRecordResponse extends Response {
    private ScanDetails result;

    public ScanDetails getResult() {
        return result;
    }

    public void setResult(ScanDetails result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ScanRecordResponse{" +
                "result=" + result +
                '}';
    }
}

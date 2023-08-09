package com.hdfex.merchantqrshow.bean.salesman.huabei;

import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.installment.Installment;

import java.util.List;

/**
 * author Created by harrishuang on 2017/12/13.
 * email : huangjinping@hdfex.com
 */

public class BussinessCaseResponse extends Response {


    private List<Installment> result;

    public List<Installment> getResult() {
        return result;
    }

    public void setResult(List<Installment> result) {
        this.result = result;
    }
}

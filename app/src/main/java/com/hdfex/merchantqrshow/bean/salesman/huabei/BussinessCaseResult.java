package com.hdfex.merchantqrshow.bean.salesman.huabei;

import com.hdfex.merchantqrshow.bean.BaseBean;
import com.hdfex.merchantqrshow.bean.salesman.installment.Installment;

import java.util.List;

/**
 * author Created by harrishuang on 2017/12/13.
 * email : huangjinping@hdfex.com
 */

public class BussinessCaseResult extends BaseBean {

    private List<Installment> list;

    public List<Installment> getList() {
        return list;
    }

    public void setList(List<Installment> list) {
        this.list = list;
    }
}

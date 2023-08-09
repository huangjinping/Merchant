package com.hdfex.merchantqrshow.bean.salesman.home;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * author Created by harrishuang on 2019/11/12.
 * email : huangjinping1000@163.com
 */
public class CaseWithCaseIdListResponse extends BaseBean {



    private List<CaseWithCaseIdListBean> result;

    public List<CaseWithCaseIdListBean> getResult() {
        return result;
    }

    public void setResult(List<CaseWithCaseIdListBean> result) {
        this.result = result;
    }
}

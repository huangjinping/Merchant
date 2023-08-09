package com.hdfex.merchantqrshow.bean.salesman.home;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * author Created by harrishuang on 2019/11/12.
 * email : huangjinping1000@163.com
 */
public class CaseWithCaseIdListBean extends BaseBean {

    private String capitalName;
    private List<CaseWithCase> caseList;

    public String getCapitalName() {
        return capitalName;
    }

    public void setCapitalName(String capitalName) {
        this.capitalName = capitalName;
    }

    public List<CaseWithCase> getCaseList() {
        return caseList;
    }

    public void setCaseList(List<CaseWithCase> caseList) {
        this.caseList = caseList;
    }
}

package com.hdfex.merchantqrshow.bean.manager.team;

import com.hdfex.merchantqrshow.bean.Response;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class PersonRecordListResponse extends Response {

    private List<PersonRecord>  result;

    public List<PersonRecord> getResult() {
        return result;
    }

    public void setResult(List<PersonRecord> result) {
        this.result = result;
    }
}

package com.hdfex.merchantqrshow.bean.admin.bam;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/12/6.
 * email : huangjinping@hdfex.com
 */

public class PersonCountResponse extends Response {


    private  PersonCountResult result;

    public PersonCountResult getResult() {
        return result;
    }

    public void setResult(PersonCountResult result) {
        this.result = result;
    }
}

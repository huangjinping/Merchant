package com.hdfex.merchantqrshow.bean.manager.team;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public class PersonDetailResponse extends Response {


    private PersonDetail result;

    public PersonDetail getResult() {
        return result;
    }

    public void setResult(PersonDetail result) {
        this.result = result;
    }
}

package com.hdfex.merchantqrshow.bean.admin.financial;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/12/20.
 * email : huangjinping@hdfex.com
 */

public class WithdrawDepositResponse extends Response {

    private WithdrawDeposit response;

    public WithdrawDeposit getResponse() {
        return response;
    }

    public void setResponse(WithdrawDeposit response) {
        this.response = response;
    }
}

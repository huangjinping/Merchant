package com.hdfex.merchantqrshow.bean.salesman.taobao;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/7/3.
 * email : huangjinping@hdfex.com
 */

public class WithdrawDeposit extends BaseBean {


    private String transferStatus;
    private String transferDesc;

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getTransferDesc() {
        return transferDesc;
    }

    public void setTransferDesc(String transferDesc) {
        this.transferDesc = transferDesc;
    }
}

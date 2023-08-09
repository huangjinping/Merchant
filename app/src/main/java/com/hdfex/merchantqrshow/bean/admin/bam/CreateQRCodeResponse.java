package com.hdfex.merchantqrshow.bean.admin.bam;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2017/12/29.
 * email : huangjinping@hdfex.com
 */

public class CreateQRCodeResponse extends Response {

    private CreateQRCode  result;

    public CreateQRCode getResult() {
        return result;
    }

    public void setResult(CreateQRCode result) {
        this.result = result;
    }
}

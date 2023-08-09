package com.hdfex.merchantqrshow.bean.apliysale;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * author Created by harrishuang on 2018/1/3.
 * email : huangjinping@hdfex.com
 */

public class QRCodeResponse extends Response {


    private QRCodeResult result;

    public QRCodeResult getResult() {
        return result;
    }

    public void setResult(QRCodeResult result) {
        this.result = result;
    }
}

package com.hdfex.merchantqrshow.bean.salesman.commodity;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by harrishuang on 16/9/6.
 */

public class UploadInfoResponse extends Response {

    private  UploadInfo result;

    public UploadInfo getResult() {
        return result;
    }

    public void setResult(UploadInfo result) {
        this.result = result;
    }
}

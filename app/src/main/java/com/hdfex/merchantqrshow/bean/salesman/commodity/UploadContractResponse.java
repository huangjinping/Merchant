package com.hdfex.merchantqrshow.bean.salesman.commodity;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by harrishuang on 16/9/8.
 */

public class UploadContractResponse  extends Response{

  private  UploadContractResult result;

    public UploadContractResult getResult() {
        return result;
    }

    public void setResult(UploadContractResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "UploadContractResponse{" +
                "result=" + result +
                '}';
    }
}

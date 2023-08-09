package com.hdfex.merchantqrshow.bean.salesman.resource;

import com.hdfex.merchantqrshow.bean.Response;

/**
 * Created by harrishuang on 2017/2/20.
 */

public class CreateHouseResponse extends Response {

  private  CreateHouseResult  result;

    public CreateHouseResult getResult() {
        return result;
    }

    public void setResult(CreateHouseResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CreateHouseResponse{" +
                "result=" + result +
                '}';
    }
}

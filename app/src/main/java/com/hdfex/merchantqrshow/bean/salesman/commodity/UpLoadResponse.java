package com.hdfex.merchantqrshow.bean.salesman.commodity;

import java.io.Serializable;

/**
 * Created by harrishuang on 16/9/6.
 */

public class UpLoadResponse implements Serializable {

    private String result;

    public String getResult() {
        return result;
    }


    public void setResult(String result) {
        this.result = result;
    }

    private String msg;
    private String errcode;
    private String picId;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }
}

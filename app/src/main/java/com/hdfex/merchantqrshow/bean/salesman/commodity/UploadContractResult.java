package com.hdfex.merchantqrshow.bean.salesman.commodity;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * Created by harrishuang on 16/9/8.
 */

public class UploadContractResult extends BaseBean {

    private  String picId;
    private  String relativePath;
    private  String absolutePath;

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    @Override
    public String toString() {
        return "UploadContractResult{" +
                "picId='" + picId + '\'' +
                '}';
    }
}

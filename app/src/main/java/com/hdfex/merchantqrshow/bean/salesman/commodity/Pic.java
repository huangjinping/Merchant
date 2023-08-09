package com.hdfex.merchantqrshow.bean.salesman.commodity;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.io.File;

/**
 * Created by harrishuang on 16/9/6.
 */

public class Pic extends BaseBean {

    private  String  picId;
    private  String  picUrl;
    /**
     * O 或者空:不是编辑状态
     * 1 :编辑状态
     * 2 :选中状态
     */
    private  String state;
    private String type;
    private File imageFile;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Pic{" +
                "picId='" + picId + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}

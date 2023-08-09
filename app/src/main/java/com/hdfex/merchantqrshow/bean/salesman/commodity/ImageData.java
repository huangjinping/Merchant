package com.hdfex.merchantqrshow.bean.salesman.commodity;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * Created by harrishuang on 2016/11/15.
 */

public class ImageData  extends BaseBean{

    private List<ImageModel> imageModelList;

    public List<ImageModel> getImageModelList() {
        return imageModelList;
    }

    public void setImageModelList(List<ImageModel> imageModelList) {
        this.imageModelList = imageModelList;
    }
}

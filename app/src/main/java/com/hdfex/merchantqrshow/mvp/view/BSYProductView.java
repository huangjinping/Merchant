package com.hdfex.merchantqrshow.mvp.view;

import com.hdfex.merchantqrshow.bean.salesman.bsy.ProductItem;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * Created by harrishuang on 2016/11/24.
 */

public interface BSYProductView  extends MvpView{

    void  onResult(ProductItem result);

    void   onLocationResult(String province, String city, String county);
   void onlocationCode(String provinceCode, String cityCode, String countyCode);




}

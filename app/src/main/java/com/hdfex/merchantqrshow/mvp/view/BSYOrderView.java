package com.hdfex.merchantqrshow.mvp.view;

import com.hdfex.merchantqrshow.bean.salesman.bsy.BSYOrderItem;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * Created by harrishuang on 2016/11/24.
 */

public interface BSYOrderView extends MvpView {



    void  onDetailsResult(BSYOrderItem orderDetails);


}

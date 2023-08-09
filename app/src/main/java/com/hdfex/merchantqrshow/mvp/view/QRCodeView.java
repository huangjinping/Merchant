package com.hdfex.merchantqrshow.mvp.view;

import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.AdditionalInfo;
import com.hdfex.merchantqrshow.mvp.MvpView;

/**
 * Created by harrishuang on 2017/2/15.
 */

public interface QRCodeView extends MvpView{

    void addPassWordFragmentToStack(AdditionalInfo info);

    void saveCurrentImage();


}

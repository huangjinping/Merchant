package com.hdfex.merchantqrshow.mvp.view;

import com.hdfex.merchantqrshow.bean.salesman.bsy.ProductItem;
import com.hdfex.merchantqrshow.bean.salesman.redpackage.RedPackage;
import com.hdfex.merchantqrshow.mvp.MvpView;
import com.hdfex.merchantqrshow.utils.LoadMode;

import java.util.List;

/**
 * Created by harrishuang on 2016/11/24.
 */

public interface BSYSelectView extends MvpView {
    void getData(List<ProductItem> list, LoadMode mode);
    void onEmpty();
     void onContentView();
    void  onEirr();
    void  onAfter();

    void showRedPackageDialog(RedPackage redPackage);
}

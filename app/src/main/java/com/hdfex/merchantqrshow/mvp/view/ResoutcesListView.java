package com.hdfex.merchantqrshow.mvp.view;

import com.hdfex.merchantqrshow.bean.salesman.resource.ItemHouse;
import com.hdfex.merchantqrshow.mvp.MvpView;
import com.hdfex.merchantqrshow.utils.LoadMode;

import java.util.List;

/**
 * Created by harrishuang on 2017/2/20.
 */

public interface ResoutcesListView  extends MvpView{
    void returnItemHouseList(List<ItemHouse> result, LoadMode loadMode);

    void onAfter();
}

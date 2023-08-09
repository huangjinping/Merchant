package com.hdfex.merchantqrshow.mvp.view;

import com.hdfex.merchantqrshow.bean.salesman.resource.Apartment;
import com.hdfex.merchantqrshow.mvp.MvpView;

import java.util.List;

/**
 * Created by harrishuang on 2017/3/20.
 */

public interface ApartmentView  extends MvpView{
    void returnAparmentList(List<Apartment> result);
}

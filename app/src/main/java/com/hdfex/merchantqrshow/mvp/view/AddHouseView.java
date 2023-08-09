package com.hdfex.merchantqrshow.mvp.view;

import com.hdfex.merchantqrshow.bean.salesman.resource.Apartment;
import com.hdfex.merchantqrshow.bean.salesman.resource.CreateHouseResult;
import com.hdfex.merchantqrshow.mvp.MvpView;

import java.util.List;

/**
 * Created by harrishuang on 2017/2/17.
 */

public interface AddHouseView  extends MvpView{

    void returnAddCreateHouse(CreateHouseResult result);

    void returnDeleteHouse(CreateHouseResult result);

    void deleteHouse();

    void houseType(String s, int index);

    void returnApartment(List<Apartment> result,int type);

    void returnShowLeaseMode(int index, String s);
}

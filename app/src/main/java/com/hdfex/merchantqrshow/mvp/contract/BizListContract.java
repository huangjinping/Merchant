package com.hdfex.merchantqrshow.mvp.contract;

import android.content.Context;

import com.hdfex.merchantqrshow.bean.manager.team.RegionResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;
import com.hdfex.merchantqrshow.utils.LoadMode;

import java.util.List;

/**
 * author Created by harrishuang on 2017/9/13.
 * email : huangjinping@hdfex.com
 */

public interface BizListContract {

    interface View extends MvpView {


        void returnListResponse(List<RegionResult> result, LoadMode loadMode);

        void returnRefresh();
    }

     abstract class Presenter extends BasePresenter<View> {

        public abstract void getRegionList(User user, LoadMode nomal);

        public abstract void initData(Context context, User user);
    }
}

package com.hdfex.merchantqrshow.mvp.contract;

import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageModel;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

import java.util.List;

/**
 * Created by harrishuang on 2017/4/7.
 */

public interface UpLoadSendContract {


    interface View extends MvpView {

        void onSuccess();
    }

    abstract class Presenter extends BasePresenter<UpLoadSendContract.View> {
        public abstract void submitSendInfo(User user, String applyId, List<ImageModel> bankList);
        /**
         * 试算接口
         * @param user
         * @param applyAmount
         * @param caseId
         */
    }
}

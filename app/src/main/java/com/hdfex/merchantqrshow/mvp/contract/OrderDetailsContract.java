package com.hdfex.merchantqrshow.mvp.contract;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.LinearLayout;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderDetails;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

import java.io.File;

/**
 * Created by harrishuang on 2017/4/21.
 */

public interface OrderDetailsContract {

    interface View extends MvpView {
        void returnShotBitmap(Bitmap bitmap);
        void returnShotFile(File file);

        void returnDetails(OrderDetails orderDetails);
    }

    abstract class Persenter extends BasePresenter<View> {

        public abstract void shotAllView(Context context, LinearLayout layout_rootview, android.view.View[] views);

        public abstract void loadOrderDetails(String stringExtra, User user);

        public abstract void loadRepayDetail(Context context,User user, String applyId);
    }


}

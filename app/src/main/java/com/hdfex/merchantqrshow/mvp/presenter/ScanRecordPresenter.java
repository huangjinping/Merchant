package com.hdfex.merchantqrshow.mvp.presenter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.hdfex.merchantqrshow.salesman.add.activity.PayMonthlyQRCodeActivity;
import com.hdfex.merchantqrshow.salesman.add.activity.QRCodeProduceActivity;
import com.hdfex.merchantqrshow.salesman.add.activity.ZuFangQRCodeProduceActivity;
import com.hdfex.merchantqrshow.bean.salesman.order.QueryUncompelete;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.view.ScanView;
import com.hdfex.merchantqrshow.net.IntentFlag;

/**
 * Created by harrishuang on 2017/2/14.
 */

public class ScanRecordPresenter extends BasePresenter<ScanView> {

    public void onSelectScan(String status, QueryUncompelete queryUncompelete, FragmentActivity activity) {
        Intent intent = new Intent();
        if (IntentFlag.SCAN_TYPE_ZUFANG.equals(status)) {
            intent.setClass(activity, ZuFangQRCodeProduceActivity.class);
        } else if (IntentFlag.SCAN_TYPE_OTHER.equals(status)){
            intent.setClass(activity, QRCodeProduceActivity.class);
        }else {
            intent.setClass(activity, PayMonthlyQRCodeActivity.class);
        }
        intent.putExtra(IntentFlag.INTENT_NAME, IntentFlag.CONFORM);
        intent.putExtra("packageId", queryUncompelete.getPackageId());
        activity.startActivity(intent);
    }
}

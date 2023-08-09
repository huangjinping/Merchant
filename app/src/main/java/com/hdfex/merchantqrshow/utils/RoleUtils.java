package com.hdfex.merchantqrshow.utils;

import android.app.Activity;
import android.content.Intent;

import com.hdfex.merchantqrshow.admin.bam.activity.AdminIndexActivity;
import com.hdfex.merchantqrshow.apliySale.main.activity.ApliyMainActivity;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.manager.main.activity.ManagerIndexActivity;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.salesman.main.activity.IndexActivity;

/**
 * author Created by harrishuang on 2017/12/11.
 * email : huangjinping@hdfex.com
 */

public class RoleUtils {

    public static final String ROLE_FLAG_LOGIN = "1";

    public static void startAction(Activity activity, User user, String flag) {
        Intent intent;
        if (IntentFlag.ROLE_ADMINISTRATOR.equals(user.getBizRole())) {
            intent = new Intent(activity, ManagerIndexActivity.class);
        } else if (IntentFlag.ROLE_ADMIN.equals(user.getBizRole())) {
            intent = new Intent(activity, AdminIndexActivity.class);
        } else if (IntentFlag.ROLE_APLIY_SALE.equals(user.getBizRole())) {
            intent = new Intent(activity, ApliyMainActivity.class);
        } else {
            intent = new Intent(activity, IndexActivity.class);
        }
//        intent = new Intent(activity, ApliyMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void startActionAndIndex(Activity activity, User user, int index) {
        Intent intent;
        if (IntentFlag.ROLE_ADMINISTRATOR.equals(user.getBizRole())) {
            intent = new Intent(activity, ManagerIndexActivity.class);
        } else if (IntentFlag.ROLE_ADMIN.equals(user.getBizRole())) {
            intent = new Intent(activity, AdminIndexActivity.class);
        } else if (IntentFlag.ROLE_APLIY_SALE.equals(user.getBizRole())) {
            intent = new Intent(activity, ApliyMainActivity.class);
        } else {
            intent = new Intent(activity, IndexActivity.class);
        }
//        intent = new Intent(activity, ApliyMainActivity.class);
        intent.putExtra(IntentFlag.INDEX, index);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }
}

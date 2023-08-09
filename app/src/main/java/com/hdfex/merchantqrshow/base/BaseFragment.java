package com.hdfex.merchantqrshow.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.salesman.my.activity.LoginActivity;
import com.hdfex.merchantqrshow.utils.NetWorkTool;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;

import org.json.JSONObject;

/**
 * 碎片基类
 * Created by harrishuang on 16/7/4.
 */
public class BaseFragment extends Fragment {


    private BaseActivity baseActivity;




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            baseActivity = (BaseActivity) activity;
        }
    }

    /**
     * 显示进度条
     */
    public void showProgress() {
        if (baseActivity != null && !baseActivity.isFinishing()) {
            baseActivity.showProgress();
        }
    }

    /**
     * 取消进度条
     */
    public void dismissProgress() {
        if (baseActivity != null && !baseActivity.isFinishing()) {
            baseActivity.dismissProgress();
        }
    }

    /**
     * 判断网络状态
     *
     * @return
     */
    protected boolean connect() {

        if (NetWorkTool.isNetworkAvailable(getActivity())) {
            return true;
        } else {
            showToast("请确定联网后操作");
            return false;
        }

    }

    /**
     * Toast message
     *
     * @param msg
     */
    public void showToast(String msg) {
        if (getActivity() == null) {
            return;
        }
        if (!TextUtils.isEmpty(msg)) {
            if (msg.toLowerCase().contains("<html>")) {
                ToastUtils.makeText(getActivity(), "服务异常！").show();
            } else {
                ToastUtils.makeText(getActivity(), msg).show();
            }
        }
    }


    public boolean checkResonse(String res) {
        if (TextUtils.isEmpty(res)) {
            showWebEirr();
            return false;
        }

        try {
            JSONObject json = new JSONObject(res);
            int code = json.optInt("code", -10000);
            if (code == -10000) {
                showWebEirr();
                return false;
            }

            switch (code) {
                case 0:
                    return true;
                case -1:
                    break;
                case -2:
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case -3:
                    break;
                case -4:
                    break;
                case -100:
                    break;
            }
            showToast(json.optString("message"));
        } catch (Exception e) {
            showWebEirr();
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 服务器或者数据异常的时候
     *
     * @param e
     */
    public void showWebEirr(Exception e) {
        if (e == null) {
            showWebEirr();
            return;
        }
        showToast("" + e.getMessage());
    }

    public void showWebEirr() {
        showToast("系统维护中，请稍后再试...");
    }


    protected boolean isLogin() {
        if (UserManager.isLogin(getActivity())) {
            return true;
        } else {
            Intent mIntent = new Intent(getActivity(), LoginActivity.class);
            startActivity(mIntent);
            return false;
        }
    }
}

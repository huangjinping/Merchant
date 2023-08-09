package com.hdfex.merchantqrshow.admin.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.activity.WebActivity;
import com.hdfex.merchantqrshow.admin.my.activity.AccountSecurityActivity;
import com.hdfex.merchantqrshow.admin.my.activity.MergeQrCodeActivity;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.apliysale.QRCodeResponse;
import com.hdfex.merchantqrshow.bean.salesman.home.AdvancedInfo;
import com.hdfex.merchantqrshow.bean.salesman.home.AdvancedInfoResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.taobao.RedPackageAccountInfoResponse;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.my.activity.AccountManagerActivity;
import com.hdfex.merchantqrshow.salesman.my.activity.SettingActivity;
import com.hdfex.merchantqrshow.utils.GlideCircleTransform;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.StringUtils;
import com.hdfex.merchantqrshow.utils.UserManager;

import okhttp3.Call;
import okhttp3.Request;

/**
 * n
 * author Created by harrishuang on 2017/12/8.
 * email : huangjinping@hdfex.com
 */

public class MyAdminFragment extends BaseFragment implements View.OnClickListener {

    private ImageView img_user_pic;
    private TextView txt_username;
    private TextView txt_biaoqian;
    private TextView txt_phone;
    private LinearLayout layout_my_center;
    private TextView txt_comper_name;
    private LinearLayout layout_setting;
    private User user;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private LinearLayout layout_operation_description;
    private LinearLayout layout_electronic_contract;
    private LinearLayout layout_merge_qrcode;
    private LinearLayout layout_account_security;

    public static MyAdminFragment getInstance() {
        MyAdminFragment adminFragment = new MyAdminFragment();
        return adminFragment;


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_admin_my, container, false);
        initView(view);
        user = UserManager.getUser(getActivity());
        img_back.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        user = UserManager.getUser(getActivity());
        loadUserInfo();
        try {
            setViewbyDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView(View view) {
        img_user_pic = (ImageView) view.findViewById(R.id.img_user_pic);
        txt_username = (TextView) view.findViewById(R.id.txt_username);
        txt_biaoqian = (TextView) view.findViewById(R.id.txt_biaoqian);
        txt_phone = (TextView) view.findViewById(R.id.txt_phone);
        layout_my_center = (LinearLayout) view.findViewById(R.id.layout_my_center);
        txt_comper_name = (TextView) view.findViewById(R.id.txt_comper_name);
        layout_setting = (LinearLayout) view.findViewById(R.id.layout_setting);
        layout_setting.setOnClickListener(this);
        layout_my_center.setOnClickListener(this);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) view.findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setText("我的");
        layout_operation_description = (LinearLayout) view.findViewById(R.id.layout_operation_description);
        layout_operation_description.setOnClickListener(this);
        layout_electronic_contract = (LinearLayout) view.findViewById(R.id.layout_electronic_contract);
        layout_electronic_contract.setOnClickListener(this);
        layout_merge_qrcode = (LinearLayout) view.findViewById(R.id.layout_merge_qrcode);
        layout_merge_qrcode.setOnClickListener(this);
        layout_account_security = (LinearLayout) view.findViewById(R.id.layout_account_security);
        layout_account_security.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent mIntent;
        switch (v.getId()) {

            case R.id.layout_setting:
                mIntent = new Intent(getActivity(), SettingActivity.class);
                startActivity(mIntent);
                break;
            case R.id.layout_my_center:
                if (isLogin()) {
                    mIntent = new Intent(getActivity(), AccountManagerActivity.class);
                    startActivity(mIntent);
                }
                break;
            case R.id.layout_operation_description:
                WebActivity.start(getContext(), "操作说明", getString(R.string.update_operation_description));
                break;
            case R.id.layout_electronic_contract:
                WebActivity.start(getContext(), "电子合同", getString(R.string.update_electronic_contract));

                break;
            case R.id.layout_merge_qrcode:
                requestMergeQrcode();
                break;
            case R.id.layout_account_security:
                requestRedPackage();
                break;

        }
    }

    /**
     * 下单红包数据
     */
    private void requestRedPackage() {
        OkHttpUtils
                .post()
                .addParams("id", user.getId())
                .addParams("token", user.getToken())
                .tag(this)
                .url(NetConst.RED_PACKAGEACCOUNT_INFO)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        showToast("数据异常！");
                    }

                    @Override
                    public void onResponse(String response) {
                        if (checkResonse(response)) {
                            RedPackageAccountInfoResponse house = GsonUtil.changeGsonToBean(response, RedPackageAccountInfoResponse.class);
                            if (house.getResult() != null) {
                                AccountSecurityActivity.startAction(getActivity(), house.getResult());
                            } else {
                                showToast("数据异常！");
                            }
                        }
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissProgress();
                    }
                });
    }


    private void requestMergeQrcode() {
        User user = UserManager.getUser(getActivity());
        if (!isLogin()) {
            return;
        }

        showProgress();
        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .url(NetConst.APLIY_GET_QR_CODE).build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e) {
                showToast("服务异常");
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (checkResonse(response)) {
                        QRCodeResponse qrCodeResponse = GsonUtil.changeGsonToBean(response, QRCodeResponse.class);
                        MergeQrCodeActivity.startAction(getActivity(), qrCodeResponse.getResult());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onAfter() {
                super.onAfter();
                dismissProgress();
            }
        });
    }

    /**
     * 更新数据
     */
    private void setViewbyDate() {
        txt_username.setText("");
        txt_comper_name.setText("");
        txt_phone.setText("");
        txt_biaoqian.setText("");
        boolean login = UserManager.isLogin(getActivity());
        if (login) {
            user = UserManager.getUser(getActivity());
            if (!TextUtils.isEmpty(user.getName())) {
                txt_username.setText(user.getName());
            }
            if (!TextUtils.isEmpty(user.getBussinessName())) {
                txt_comper_name.setText(user.getBussinessName());
            }
            if (!TextUtils.isEmpty(user.getPhone())) {

                txt_phone.setText(StringUtils.getPhoneText(user.getPhone()));
            }

            if (!TextUtils.isEmpty(user.getRemark())) {
                txt_biaoqian.setText(user.getRemark());
            }

            if (!login) {
                img_user_pic.setImageResource(R.mipmap.ic_combined_shape);
                return;
            }

        }
    }


    /**
     * 下载数据
     */
    private void loadUserInfo() {
        showProgress();


        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .url(NetConst.ID_CARD_QUERY).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {

                try {
                    if (checkResonse(response)) {
                        AdvancedInfoResponse advancedInfoResponse = GsonUtil.changeGsonToBean(response, AdvancedInfoResponse.class);
                        AdvancedInfo result = advancedInfoResponse.getResult();
                        if (result != null) {
                            Glide.with(getActivity()).load(result.getAvatar()).bitmapTransform(new GlideCircleTransform(getActivity())).placeholder(R.mipmap.ic_combined_shape).error(R.mipmap.ic_combined_shape).into(img_user_pic);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissProgress();
            }
        });
    }
}

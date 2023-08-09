package com.hdfex.merchantqrshow.salesman.my.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.home.AdvancedInfo;
import com.hdfex.merchantqrshow.bean.salesman.home.AdvancedInfoResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.AuthHt;
import com.hdfex.merchantqrshow.bean.salesman.login.AuthHtResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.MyContract;
import com.hdfex.merchantqrshow.mvp.presenter.MyPresenter;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.add.activity.RentCalculatorActivity;
import com.hdfex.merchantqrshow.salesman.main.activity.ScanRecordActivity;
import com.hdfex.merchantqrshow.salesman.my.activity.AccountManagerActivity;
import com.hdfex.merchantqrshow.salesman.my.activity.ResourcesActivity;
import com.hdfex.merchantqrshow.salesman.my.activity.SettingActivity;
import com.hdfex.merchantqrshow.salesman.my.activity.SignResultActivity;
import com.hdfex.merchantqrshow.salesman.order.fragment.ChangeFundSourceFragment;
import com.hdfex.merchantqrshow.utils.GlideCircleTransform;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.StringUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.log.LogUtil;
import com.hdfex.merchantqrshow.widget.picker.ChangeSourcePicker;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 我的界面
 * Created by harrishuang on 16/9/27.
 */

public class MyFragment extends BaseFragment implements View.OnClickListener, MyContract.View {
    private ImageView img_user_pic;
    private TextView txt_username;
    private TextView txt_biaoqian;
    private TextView txt_phone;
    private TextView txt_comper_name;
    private LinearLayout layout_setting;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private TextView txt_left_name;
    private LinearLayout layout_my_center;
    private LinearLayout layout_my_resource;
    private LinearLayout layout_update;
    private LinearLayout layout_zufang;
    private LinearLayout layout_calculator;
    private LinearLayout layout_red_package;
    private MyContract.Presenter presenter;
    private User user;
    private LinearLayout layout_scan_history;
    private LinearLayout layout_sign_result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        presenter = new MyPresenter();
        presenter.attachView(this);
        initView(view);
        user = UserManager.getUser(getActivity());
        return view;
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
            case R.id.layout_my_resource:
                if (isLogin()) {
                    mIntent = new Intent(getActivity(), ResourcesActivity.class);
                    startActivity(mIntent);
                }
                break;
            case R.id.layout_update:
                getTime(null);
//                WebActivity.start(getContext(), "更新说明", getString(R.string.update_message));
                break;
            case R.id.layout_calculator:
                RentCalculatorActivity.start(getContext());
                break;
            case R.id.layout_red_package:
                presenter.loadRedPackage(user, getActivity());
                break;
            case R.id.layout_scan_history:
                if (isLogin()) {
                    Intent intent = new Intent(getActivity(), ScanRecordActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.layout_sign_result:
                if (isLogin()) {
                    Intent intent = new Intent(getActivity(), SignResultActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }


    /**
     * 选择时间
     *
     * @param edittext
     */
    private void getTime(final EditText edittext) {

//        DatePicker datePicker = new DatePicker(getActivity());
//        datePicker.setOnDateSelectListener(new DatePicker.OnDateSelectListener() {
//            @Override
//            public void onSelect(String year, String month, String day) {
//                StringBuffer sb = new StringBuffer();
//                sb.append(year);
//                sb.append("-");
//                sb.append(String.format("%02d", Integer.parseInt(month)));
//                sb.append("-");
//                sb.append(String.format("%02d", Integer.parseInt(day)));
//
//            }
//        });
//        datePicker.show();


        ChangeSourcePicker changeSourcePicker = new ChangeSourcePicker(getActivity());
        changeSourcePicker.show();



    }

    private void initView(View view) {
        img_user_pic = (ImageView) view.findViewById(R.id.img_user_pic);
        txt_username = (TextView) view.findViewById(R.id.txt_username);
        txt_biaoqian = (TextView) view.findViewById(R.id.txt_biaoqian);
        txt_phone = (TextView) view.findViewById(R.id.txt_phone);
        txt_comper_name = (TextView) view.findViewById(R.id.txt_comper_name);
        layout_setting = (LinearLayout) view.findViewById(R.id.layout_setting);
        layout_setting.setOnClickListener(this);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        tb_tv_titile = (TextView) view.findViewById(R.id.tb_tv_titile);
        iv_setting = (ImageView) view.findViewById(R.id.iv_setting);
        tv_home = (TextView) view.findViewById(R.id.tv_home);
        layout_toolbar = (LinearLayout) view.findViewById(R.id.layout_toolbar);
        tb_tv_titile.setText("我的");
        tb_tv_titile.setTextColor(getResources().getColor(R.color.black));
        layout_toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        txt_left_name = (TextView) view.findViewById(R.id.txt_left_name);
        layout_my_center = (LinearLayout) view.findViewById(R.id.layout_my_center);
        layout_my_center.setOnClickListener(this);
        layout_my_resource = (LinearLayout) view.findViewById(R.id.layout_my_resource);
        layout_my_resource.setOnClickListener(this);
        layout_update = (LinearLayout) view.findViewById(R.id.layout_update);
        layout_update.setOnClickListener(this);
        layout_zufang = (LinearLayout) view.findViewById(R.id.layout_zufang);

        layout_calculator = (LinearLayout) view.findViewById(R.id.layout_calculator);
        layout_calculator.setOnClickListener(this);
        layout_red_package = (LinearLayout) view.findViewById(R.id.layout_red_package);
        layout_red_package.setOnClickListener(this);
        layout_scan_history = (LinearLayout) view.findViewById(R.id.layout_scan_history);
        layout_scan_history.setOnClickListener(this);
        layout_sign_result = (LinearLayout) view.findViewById(R.id.layout_sign_result);
        layout_sign_result.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        user = UserManager.getUser(getActivity());
        loadUserInfo();

        try {
            setViewbyDate();
            authHt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void authHt() {
        if (!isLogin()) {
            return;
        }
        final User pUser = UserManager.getUser(getActivity());
        final Context context = getActivity();
        showProgress();
        OkHttpUtils
                .post()
                .url(NetConst.AUTH_HT)
                .addParams("token", pUser.getToken())
                .addParams("id", pUser.getId())
                .addParams("account", pUser.getAccount())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        try {
                            dismissProgress();
                            User user = UserManager.getUser(context);
                            if (user.isSign()) {
                                layout_sign_result.setVisibility(View.VISIBLE);
                            } else {
                                layout_sign_result.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        LogUtil.d("okhttp", e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                        dismissProgress();

                        try {
                            boolean b = checkResonse(response);

                            LogUtil.d("okhttp", "==============++++++++++++>>>>>>>>>>" + response);

                            if (b) {
                                AuthHtResponse mUserInfo = GsonUtil.changeGsonToBean(response, AuthHtResponse.class);
                                AuthHt result = mUserInfo.getResult();
                                User user = UserManager.getUser(context);
                                user.setSign(result.isSign());
                                UserManager.saveUser(context, user);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showWebEirr();
                        }
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
        layout_zufang.setVisibility(View.GONE);
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
            Map<String, String> map = user.getSourceTypeMap();
            if (map != null) {
                if (!TextUtils.isEmpty(map.get("2"))) {
                    layout_zufang.setVisibility(View.VISIBLE);
                }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}

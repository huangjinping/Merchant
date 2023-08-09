package com.hdfex.merchantqrshow.admin.bam.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.admin.bam.activity.AddCommodityActivity;
import com.hdfex.merchantqrshow.admin.bam.activity.AddEmployeeActivity;
import com.hdfex.merchantqrshow.admin.bam.activity.AdminCommodityListActivity;
import com.hdfex.merchantqrshow.admin.bam.activity.EmployeeManagerActivity;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.admin.bam.PersonCountResponse;
import com.hdfex.merchantqrshow.bean.admin.bam.PersonCountResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;

import okhttp3.Call;

/**
 * 后台
 * author Created by harrishuang on 2017/12/4.
 * email : huangjinping@hdfex.com
 */

public class AdminFragment extends BaseFragment implements View.OnClickListener {

    private Context context;
    private RelativeLayout layout_add_commodity;
    private RelativeLayout layout_add_employee;
    private TextView txt_putaway;
    private TextView txt_awaitPutaway;
    private TextView txt_soldOut;
    private TextView txt_startUser;
    private TextView txt_forbidUser;
    private User user;
    private LinearLayout layout_adminCommodity;
    private LinearLayout layout_adminEmployee;
    private TextView tb_tv_titile;
    private ImageView img_back;


    public static BaseFragment getInstance() {
        AdminFragment fragment = new AdminFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        context = getActivity();
        initView(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        user = UserManager.getUser(getActivity());

        if (UserManager.isLogin(getActivity())) {
            loadHome();
        }

    }

    private void initView(View view) {
        layout_add_commodity = (RelativeLayout) view.findViewById(R.id.layout_add_commodity);
        layout_add_employee = (RelativeLayout) view.findViewById(R.id.layout_add_employee);
        layout_add_commodity.setOnClickListener(this);
        layout_add_employee.setOnClickListener(this);
        txt_putaway = (TextView) view.findViewById(R.id.txt_putaway);
        txt_awaitPutaway = (TextView) view.findViewById(R.id.txt_awaitPutaway);
        txt_soldOut = (TextView) view.findViewById(R.id.txt_soldOut);
        txt_startUser = (TextView) view.findViewById(R.id.txt_startUser);
        txt_forbidUser = (TextView) view.findViewById(R.id.txt_forbidUser);
        layout_adminCommodity = (LinearLayout) view.findViewById(R.id.layout_adminCommodity);
        layout_adminCommodity.setOnClickListener(this);
        layout_adminEmployee = (LinearLayout) view.findViewById(R.id.layout_adminEmployee);
        layout_adminEmployee.setOnClickListener(this);
        tb_tv_titile = (TextView) view.findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setOnClickListener(this);
        tb_tv_titile.setText("后台管理");
        img_back = (ImageView) view.findViewById(R.id.img_back);
        img_back.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_add_commodity:
                AddCommodityActivity.startAction(context);
                break;
            case R.id.layout_add_employee:
                AddEmployeeActivity.startAction(context);
                break;
            case R.id.layout_adminEmployee:
                EmployeeManagerActivity.startAction(context);
                break;
            case R.id.layout_adminCommodity:
                AdminCommodityListActivity.startAction(getActivity());
                break;

        }
    }


    /**
     * 下载详情
     *
     * @param
     */
    private void loadHome() {
        showProgress();
        OkHttpUtils.post()
                .addParams("id", user.getBussinessId())
                .addParams("token", user.getToken())
                .url(NetConst.ADMIN_PERSON_COUNT).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (checkResonse(response)) {
                        PersonCountResponse response1 = GsonUtil.changeGsonToBean(response, PersonCountResponse.class);
                        setViewByData(response1.getResult());
                    }
                } catch (Exception e) {
                    showToast("数据异常");
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
     * 文件数据
     */
    private void setViewByData(PersonCountResult result) {
        if (!TextUtils.isEmpty(result.getAwaitPutaway())) {
            txt_awaitPutaway.setText(result.getAwaitPutaway());
        }
        if (!TextUtils.isEmpty(result.getForbidUser())) {
            txt_forbidUser.setText(result.getForbidUser());
        }
        if (!TextUtils.isEmpty(result.getPutaway())) {
            txt_putaway.setText(result.getPutaway());
        }
        if (!TextUtils.isEmpty(result.getSoldOut())) {
            txt_soldOut.setText(result.getSoldOut());
        }
        if (!TextUtils.isEmpty(result.getStartUser())) {
            txt_startUser.setText(result.getStartUser());
        }
    }
}

package com.hdfex.merchantqrshow.manager.main.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.manager.main.Stores;
import com.hdfex.merchantqrshow.bean.manager.main.StoresListResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.ManagerContract;
import com.hdfex.merchantqrshow.mvp.presenter.ManagerPresenter;
import com.hdfex.merchantqrshow.salesman.my.activity.LoginActivity;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;

/**
 * author Created by harrishuang on 2017/6/1.
 * email : huangjinping@hdfex.com
 */

public class MyFragment extends BaseFragment implements View.OnClickListener, ManagerContract.View, MerchantListFragment.OnSelectedCallBack {
    private Button btn_logout;
    private LinearLayout layout_other_content;
    private RelativeLayout layout_change_merchant;
    private TextView txt_account;
    private TextView txt_role;
    private TextView txt_version_name;
    private TextView txt_weixin_code;
    private User user;
    private ManagerContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_managermy, container, false);
        initView(view);
        user = UserManager.getUser(getActivity());
        presenter = new ManagerPresenter();
        presenter.attachView(this);
        if (!TextUtils.isEmpty(user.getCurrentBussinessName())) {
            txt_account.setText(user.getCurrentBussinessName());
        }
//        角色权限
//        1-	管理员
//        2-	业务员
        if (!TextUtils.isEmpty(user.getBizRole())) {
            if ("1".equals(user.getBizRole())) {
                txt_role.setText(R.string.administrator);
                layout_change_merchant.setVisibility(View.VISIBLE);
            } else if ("2".equals(user.getBizRole())) {
                txt_role.setText(R.string.storeman);
                layout_change_merchant.setVisibility(View.GONE);

            }
        }
        txt_weixin_code.setText("分付君");
        txt_version_name.setText(getVersion());
        return view;

    }


    /**
     * 获取版本信息
     *
     * @return
     */
    public String getVersion() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "最新版本";
        }
    }

    private void initView(View view) {
        btn_logout = (Button) view.findViewById(R.id.btn_logout);
        layout_other_content = (LinearLayout) view.findViewById(R.id.layout_other_content);
        btn_logout.setOnClickListener(this);
        layout_change_merchant = (RelativeLayout) view.findViewById(R.id.layout_change_merchant);
        layout_change_merchant.setOnClickListener(this);
        txt_account = (TextView) view.findViewById(R.id.txt_account);
        txt_account.setOnClickListener(this);
        txt_role = (TextView) view.findViewById(R.id.txt_role);
        txt_role.setOnClickListener(this);
        txt_version_name = (TextView) view.findViewById(R.id.txt_version_name);
        txt_version_name.setOnClickListener(this);
        txt_weixin_code = (TextView) view.findViewById(R.id.txt_weixin_code);
        txt_weixin_code.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                if (UserManager.isLogin(getActivity())) {
                    UserManager.logout(getActivity());
                    ToastUtils.makeText(getActivity(), "退出成功").show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);

                    startActivity(intent);
                }
                getActivity().finish();
                break;
            case R.id.layout_change_merchant:
                presenter.loadStoresList(user);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


    @Override
    public void returnStroes(StoresListResponse res) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        MerchantListFragment instance = MerchantListFragment.getInstance(res);
        instance.setCallBack(this);
        transaction.setCustomAnimations(
                R.anim.fragment_up_enter,
                R.anim.fragment_down_outer,
                R.anim.fragment_up_enter,
                R.anim.fragment_down_outer);
        transaction.replace(R.id.layout_other_content, instance).
                addToBackStack(MerchantListFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onCall(Stores stores) {
        user = UserManager.getUser(getActivity());
        if (!TextUtils.isEmpty(user.getCurrentBussinessName())) {
            txt_account.setText(user.getCurrentBussinessName());
        }
    }
}

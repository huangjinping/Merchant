package com.hdfex.merchantqrshow.apliySale.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.apliySale.main.activity.ReceiptMoneyActivity;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.ApliyMainContract;
import com.hdfex.merchantqrshow.mvp.presenter.ApliyMainPresenter;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.Utils;

/**
 * author Created by harrishuang on 2018/1/3.
 * email : huangjinping@hdfex.com
 * 花呗业务员首页
 */

public class ApliyMainFragment extends BaseFragment implements View.OnClickListener, ApliyMainContract.View {
    private LinearLayout layout_mayi_apliy;
    private LinearLayout layout_receipt_money;
    private LinearLayout layout_receipt_card;
    private ApliyMainContract.Presenter presenter;
    private User user;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private LinearLayout layout_apliy_invitation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aplitymain, container, false);
        presenter = new ApliyMainPresenter();
        presenter.attachView(this);
        user = UserManager.getUser(getActivity());
        initView(view);
        return view;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_mayi_apliy:
                //跳转到花呗页面
                presenter.bussinessCase(getActivity(), user);
                break;
            case R.id.layout_receipt_money:
                /**
                 * 收款
                 */
                ReceiptMoneyActivity.startAction(getActivity());
//                showToast("暂未开通");
                break;
            case R.id.layout_receipt_card:
                /**
                 *收款码页面
                 */
                presenter.requestQrCode(getActivity(), user);
                break;
            case R.id.layout_apliy_invitation:
                boolean hasInstalledAlipayClient = Utils.hasInstalledAlipayClient(getActivity());
                if (hasInstalledAlipayClient) {
                    Utils.startIntentUrl(getActivity(), "alipayqr://platformapi/startapp?saId=10000007&qrcode=https://render.alipay.com/p/f/fd-j78f9olq/index.html");


                } else {
                    showToast("请下载安装支付宝app!");
                }
                break;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();

    }

    private void initView(View view) {
        layout_mayi_apliy = (LinearLayout) view.findViewById(R.id.layout_mayi_apliy);
        layout_receipt_money = (LinearLayout) view.findViewById(R.id.layout_receipt_money);
        layout_receipt_card = (LinearLayout) view.findViewById(R.id.layout_receipt_card);
        layout_mayi_apliy.setOnClickListener(this);
        layout_receipt_money.setOnClickListener(this);
        layout_receipt_card.setOnClickListener(this);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) view.findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setOnClickListener(this);
        img_back.setVisibility(View.GONE);
        tb_tv_titile.setText("首页");
        layout_apliy_invitation = (LinearLayout) view.findViewById(R.id.layout_apliy_invitation);
        layout_apliy_invitation.setOnClickListener(this);
    }
}

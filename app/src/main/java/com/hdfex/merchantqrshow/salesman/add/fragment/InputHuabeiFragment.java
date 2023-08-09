package com.hdfex.merchantqrshow.salesman.add.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.huabei.AlipayPlan;
import com.hdfex.merchantqrshow.bean.salesman.huabei.DurationPayment;
import com.hdfex.merchantqrshow.bean.salesman.installment.Installment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.Order;
import com.hdfex.merchantqrshow.mvp.contract.InputHuabeiContract;
import com.hdfex.merchantqrshow.mvp.presenter.InputHuabeiPresenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.keyboardvisibilityevent.KeyboardVisibilityEvent;
import com.hdfex.merchantqrshow.utils.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import com.hdfex.merchantqrshow.view.virtualKeyboard.VirtualKeyboardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * author Created by harrishuang on 2017/7/31.
 * email : huangjinping@hdfex.com
 */

public class InputHuabeiFragment extends BaseFragment implements View.OnClickListener, InputHuabeiContract.View {

    private View v_background;
    private TextView txt_back;
    private EditText txt_account;
    private TextView txt_apply_scan;
    private VirtualKeyboardView vk_keyboard;
    private GridView grid;
    private RelativeLayout ll_root;
    private ArrayList<Map<String, String>> keyValuslists;
    private EditText edt_order_duration;
    private Order order;
    private InputHuabeiCallback callback;
    private LinearLayout layout_order_duration;
    private TextView txt_input_title;
    private TextView txt_feeAmount;
    private LinearLayout layout_feeAmount;
    private TextView txt_plan;
    private InputHuabeiContract.Presenter presenter;
    private User user;
    private String loanAmount;
    private int index;
    private TextView txt_scan_pic;

    public void setCallback(InputHuabeiCallback callback) {
        this.callback = callback;
    }

    public static InputHuabeiFragment getInstance() {
        InputHuabeiFragment fragment = new InputHuabeiFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inputhuabei, container, false);
        initView(view);
        presenter = new InputHuabeiPresenter();
        presenter.attachView(this);
        user = UserManager.getUser(getActivity());
        Bundle arguments = getArguments();
        if (arguments.getSerializable(IntentFlag.INTENT_NAME) != null) {
            order = (Order) arguments.getSerializable(IntentFlag.INTENT_NAME);
            if (!TextUtils.isEmpty(order.getCurrentApplyAmount())) {
                txt_account.setText(order.getCurrentApplyAmount() + "");
            } else {
                if (!TextUtils.isEmpty(order.getTotalPrice())) {
                    txt_account.setText(order.getTotalPrice() + "");
                }
            }
            updateViewByIndex(order.getIndex());
        }

        grid = vk_keyboard.getGridView();
        keyValuslists = vk_keyboard.getValueList();
        grid.setOnItemClickListener(onItemClickListener);
        v_background.setOnClickListener(this);
        txt_back.setOnClickListener(this);
        Observable<Object> register = EventRxBus.getInstance().register(IntentFlag.CURRENT_DURATION);
        Subscription subscribe = register.subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Object o) {
                int i = (int) o;
                updateViewByIndex(i);
            }
        });

        txt_account.setFocusable(true);
        txt_account.setFocusableInTouchMode(true);
        txt_account.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager inputManager =
                        (InputMethodManager) txt_account.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(txt_account, 0);
            }
        }, 200);

        KeyboardVisibilityEvent.setEventListener(getActivity(), new KeyboardVisibilityEventListener() {

            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (!isOpen) {
                    calculationPayAmount(InputHuabeiContract.Presenter.UPDATE);
                }
            }
        });
        return view;
    }

    private String caseId;

    /**
     * 更新链表数据
     */
    private void updateViewByIndex(int index) {
        this.index = index;
        if (callback != null) {
            callback.onDuration(index);
        }
        Installment installment = order.getList().get(index);
        if (Installment.DISCOUNT_TYPE_YES.equals(installment.getDiscount())) {
            txt_plan.setVisibility(View.VISIBLE);

        } else {
            txt_plan.setVisibility(View.GONE);

        }
        caseId = installment.getCaseId();
        edt_order_duration.setText(installment.getDuration() + "期");
        calculationPayAmount(InputHuabeiContract.Presenter.UPDATE);
    }

    /**
     * 文件问题
     */
    private void calculationPayAmount(int type) {
        if (TextUtils.isEmpty(txt_account.getText().toString().trim())) {
            return;
        }
        if (TextUtils.isEmpty(caseId)) {
            return;
        }
        presenter.requestDurationPayment(user, txt_account.getText().toString().trim(), caseId, type);

    }


    private void initView(View view) {
        v_background = (View) view.findViewById(R.id.v_background);
        txt_back = (TextView) view.findViewById(R.id.txt_back);
        txt_account = (EditText) view.findViewById(R.id.txt_account);
        txt_apply_scan = (TextView) view.findViewById(R.id.txt_apply_scan);
        vk_keyboard = (VirtualKeyboardView) view.findViewById(R.id.vk_keyboard);
        ll_root = (RelativeLayout) view.findViewById(R.id.ll_root);
        edt_order_duration = (EditText) view.findViewById(R.id.edt_order_duration);
        edt_order_duration.setOnClickListener(this);
        ll_root.setOnClickListener(this);
        txt_apply_scan.setOnClickListener(this);
        layout_order_duration = (LinearLayout) view.findViewById(R.id.layout_order_duration);
        layout_order_duration.setVisibility(View.VISIBLE);
        txt_input_title = (TextView) view.findViewById(R.id.txt_input_title);
        txt_input_title.setOnClickListener(this);
        txt_input_title.setText("花呗分期支付");
        txt_feeAmount = (TextView) view.findViewById(R.id.txt_feeAmount);
        txt_feeAmount.setOnClickListener(this);
        layout_feeAmount = (LinearLayout) view.findViewById(R.id.layout_feeAmount);
        layout_feeAmount.setOnClickListener(this);
        layout_feeAmount.setVisibility(View.VISIBLE);
        txt_plan = (TextView) view.findViewById(R.id.txt_plan);
        txt_plan.setOnClickListener(this);
        txt_scan_pic = (TextView) view.findViewById(R.id.txt_scan_pic);
        txt_scan_pic.setOnClickListener(this);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String password = txt_account.getText().toString().trim();
            if (position < 11 && position != 9) {
                //点击0~9
                password = password + keyValuslists.get(position).get("name");
                txt_account.setText(password);
            } else if (position == 11) {
                //点击退格
                if (password.length() > 0) {
                    password = password.substring(0, password.length() - 1);
                    txt_account.setText(password);
                }
            }
            callback.onAccountChange(txt_account.getText().toString(), txt_account.getText().toString());
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_back:
                onBack();
                break;
            case R.id.v_background:
                onBack();
                break;
            case R.id.edt_order_duration:
                addFragmentToStack();
                break;
            case R.id.txt_apply_scan:
                if (TextUtils.isEmpty(txt_account.getText().toString().trim())) {
                    showToast("请输入金额");
                    return;
                }
                if (TextUtils.isEmpty(loanAmount)) {
                    calculationPayAmount(InputHuabeiContract.Presenter.SUBMIT);
                    return;
                }
                callback.onAccountChange(txt_account.getText().toString(), loanAmount);

                if (callback != null) {
                    callback.onSubmitOrder();
                }
                break;
            case R.id.txt_plan:
                onPlan();
                break;
            case R.id.txt_scan_pic:
                if (callback != null) {
                    if (TextUtils.isEmpty(txt_account.getText().toString().trim())) {
                        showToast("请输入金额");
                        return;
                    }
                    if (TextUtils.isEmpty(loanAmount)) {
                        calculationPayAmount(InputHuabeiContract.Presenter.SUBMIT);
                        return;
                    }
                    callback.onAccountChange(txt_account.getText().toString(), loanAmount);
                    callback.onHuabeiSubmitPrecreate(callBackMessage);
                }
                break;
        }
    }

    /**
     * 跳转到订单
     */
    private void onPlan() {
        if (TextUtils.isEmpty(txt_account.getText().toString().trim())) {
            return;
        }
        if (TextUtils.isEmpty(caseId)) {
            return;
        }
        presenter.onPlan(getActivity(), user, txt_account.getText().toString().trim(), caseId);
    }

    /**
     * 后退方法
     */
    private void onBack() {
        dismissProgress();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    /**
     * 选择专案
     */
    private void addFragmentToStack() {
        if (order == null) {
            return;
        }
        BaseFragment huabeiApertmentFragment = HuabeiApertmentFragment.getInstance();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.fragment_right_enter,
                R.anim.fragment_right_out,
                R.anim.fragment_right_enter,
                R.anim.fragment_right_out);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.INTENT_NAME, order);
        bundle.putInt(IntentFlag.INDEX, index);
        huabeiApertmentFragment.setArguments(bundle);
        transaction.replace(R.id.layout_fragment, huabeiApertmentFragment);
        transaction.addToBackStack("HuabeiApertmentFragment");
        transaction.commit();

    }


    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
    }


    String callBackMessage = "";

    @Override
    public void returnDurationPayment(DurationPayment result) {
        if (result == null) {
            txt_feeAmount.setText("");
            return;
        }
        loanAmount = TextUtils.isEmpty(result.getRealLoanAmount()) ? "" : result.getRealLoanAmount();
        String feeAmount = TextUtils.isEmpty(result.getFeeAmount()) ? "" : result.getFeeAmount();
        String feeAmountAver = TextUtils.isEmpty(result.getFeeAmountAver()) ? "" : result.getFeeAmountAver();

        String commodityAmount = TextUtils.isEmpty(result.getCommodityAmount()) ? "" : result.getCommodityAmount();
        String durationAmt="";
        List<AlipayPlan> durationList = result.getDurationList();

        if (durationList!=null&&durationList.size()>0){
            durationAmt=durationList.get(0).getDurationAmt();
        }


        String all;
        if (TextUtils.isEmpty(feeAmount)) {
            all = loanAmount;
            callBackMessage = "商品金额：" + loanAmount;
        } else {
//            all = loanAmount + "(商品" + commodityAmount + "，手续费" + feeAmountAver + ")";
            all=loanAmount+"\n"+"每期还款"+durationAmt+"元，含服务费"+feeAmountAver+"元";
            callBackMessage = " 每期还款"+durationAmt+"元，含服务费"+feeAmountAver+"元";
        }
        txt_feeAmount.setText(all);
    }

    @Override
    public void submitScan() {
        callback.onAccountChange(txt_account.getText().toString(), loanAmount);
        onBack();
        if (callback != null) {
            callback.onSubmitOrder();
        }
    }


    public interface InputHuabeiCallback {
        void onDuration(int index);

        void onAccountChange(String s, String account);

        void onSubmitOrder();

        void onHuabeiSubmitPrecreate(String callBackMessage);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ll_root.setBackgroundColor(getResources().getColor(R.color.transparent_quarter));
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        handler.sendMessageDelayed(handler.obtainMessage(), 300);
    }

    @Override
    public void onPause() {
        super.onPause();
        ll_root.setBackgroundColor(getResources().getColor(R.color.transparent));
    }


}

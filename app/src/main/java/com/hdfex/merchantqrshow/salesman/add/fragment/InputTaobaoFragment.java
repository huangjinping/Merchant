package com.hdfex.merchantqrshow.salesman.add.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.view.virtualKeyboard.VirtualKeyboardView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * author Created by harrishuang on 2017/7/31.
 * email : huangjinping@hdfex.com
 */

public class InputTaobaoFragment extends BaseFragment implements View.OnClickListener {

    private View v_background;
    private TextView txt_back;
    private EditText txt_account;
    private TextView txt_apply_scan;
    private VirtualKeyboardView vk_keyboard;
    private GridView grid;
    private RelativeLayout ll_root;
    private ArrayList<Map<String, String>> keyValuslists;
    private EditText edt_order_duration;
    /**
     * 反馈信息
     */
    private InputTaobaoCallback callback;
    private TextView txt_taobao_pic;


    public void setCallback(InputTaobaoCallback callback) {
        this.callback = callback;
    }

    public static InputTaobaoFragment getInstance() {
        InputTaobaoFragment fragment = new InputTaobaoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_taobaoinput, container, false);
        initView(view);
        Bundle arguments = getArguments();
        if (arguments.getSerializable(IntentFlag.INTENT_NAME) != null) {
            txt_account.setText(arguments.getString(IntentFlag.INTENT_NAME));
        }
        grid = vk_keyboard.getGridView();
        keyValuslists = vk_keyboard.getValueList();
        grid.setOnItemClickListener(onItemClickListener);
        v_background.setOnClickListener(this);
        txt_back.setOnClickListener(this);
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


        return view;
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
        txt_taobao_pic = (TextView) view.findViewById(R.id.txt_taobao_pic);
        txt_taobao_pic.setOnClickListener(this);
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
            if (callback != null) {
                callback.onTaobaoAccountChange(txt_account.getText().toString());
            }
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

            case R.id.txt_apply_scan:
                if (TextUtils.isEmpty(txt_account.getText().toString())) {
                    showToast("请输入商品金额");
                    return;
                }


                if (callback != null) {
                    callback.onTaobaoAccountChange(txt_account.getText().toString().trim());
                    callback.onTaobeoSubmitOrder();
                }
                break;
            case R.id.txt_taobao_pic:


                if (TextUtils.isEmpty(txt_account.getText().toString())) {
                    showToast("请输入商品金额");
                    return;
                }


                if (callback != null) {
                    callback.onTaobaoAccountChange(txt_account.getText().toString().trim());
                    callback.onTaobeoSubmitPrecreate();
                }
                break;
        }
    }

    /**
     * 后退方法
     */
    private void onBack() {
        getActivity().getSupportFragmentManager().popBackStack();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    public interface InputTaobaoCallback {


        void onTaobaoAccountChange(String account);

        void onTaobeoSubmitOrder();

        void onTaobeoSubmitPrecreate();

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

package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.home.CalculateResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.RentCalculatorContract;
import com.hdfex.merchantqrshow.mvp.presenter.RentCalculatorPresenter;
import com.hdfex.merchantqrshow.utils.StringUtils;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.DeleteEditText;

/**
 * 房租计算器
 * Created by harrishuang on 2017/4/20.
 */

public class RentCalculatorActivity extends BaseActivity implements RentCalculatorContract.View, View.OnClickListener {


    private EditText et_startdata;
    private EditText et_entdata;
    private EditText et_unbinddata;
    private TextView txt_innerdata;
    private TextView txt_outdata;
    private DeleteEditText et_rentprice;
    private TextView btn_submit;
    private RentCalculatorContract.Persenter persenter;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private User user;
    private TextView txt_outdata_title;
    private RelativeLayout layout_innerdata;
    private RelativeLayout layout_outdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentcalculator);
        setTheme(R.style.ActionSheetStyle);
        user = UserManager.getUser(this);
        persenter = new RentCalculatorPresenter();
        persenter.attachView(this);
        initView();
        persenter.addEvents(user,et_startdata, et_entdata, et_unbinddata);
    }


//    private void setEvent(final EditText editSearchView){
//        editSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    String content = editSearchView.getText().toString();
//                    if (TextUtils.isEmpty(content)) {
//                        return true;
//                    }
//                    // TODO: 16/4/7  搜索现象
//                    ((InputMethodManager) editSearchView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
//                            .hideSoftInputFromWindow(
//                                    getCurrentFocus()
//                                            .getWindowToken(),
//                                    InputMethodManager.HIDE_NOT_ALWAYS);
//
//                    submit();
//                    return true;
//                }
//                return false;
//            }
//        });
//
//    }

    /**
     * 打开房租计算器界面
     *
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, RentCalculatorActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                submit();
//                showAlert(this, "", false);
                break;
            case R.id.et_startdata:
                persenter.selectData(this, et_startdata);
                break;
            case R.id.et_entdata:
                persenter.selectMonth(this, et_entdata, getSupportFragmentManager());
                break;
            case R.id.et_unbinddata:
                persenter.selectData(this, et_unbinddata);
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void initView() {
        et_startdata = (EditText) findViewById(R.id.et_startdata);
        et_startdata.setOnClickListener(this);
        et_entdata = (EditText) findViewById(R.id.et_entdata);
        et_entdata.setOnClickListener(this);
        et_unbinddata = (EditText) findViewById(R.id.et_unbinddata);
        et_unbinddata.setOnClickListener(this);
        txt_innerdata = (TextView) findViewById(R.id.txt_innerdata);
        txt_outdata = (TextView) findViewById(R.id.txt_outdata);
        et_rentprice = (DeleteEditText) findViewById(R.id.et_rentprice);
        btn_submit = (TextView) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setOnClickListener(this);
        tb_tv_titile.setText("房租计算器");
        txt_outdata_title = (TextView) findViewById(R.id.txt_outdata_title);
        txt_outdata_title.setOnClickListener(this);
        layout_innerdata = (RelativeLayout) findViewById(R.id.layout_innerdata);
        layout_outdata = (RelativeLayout) findViewById(R.id.layout_outdata);
    }

    private void submit() {
        // validate
        String startDate = et_startdata.getText().toString().trim();
        if (TextUtils.isEmpty(startDate)) {
            ToastUtils.d(this, "请选择开始日期", Toast.LENGTH_SHORT).show();
            return;
        }

        String duration = et_entdata.getText().toString().trim();
        if (TextUtils.isEmpty(duration)) {
            ToastUtils.d(this, "请选择租住月份", Toast.LENGTH_SHORT).show();
            return;
        }
        String terminationDate = et_unbinddata.getText().toString().trim();
        if (TextUtils.isEmpty(terminationDate)) {
            ToastUtils.d(this, "请选择解约日期", Toast.LENGTH_SHORT).show();
            return;
        }
        String monthRent = et_rentprice.getText().toString().trim();
        if (TextUtils.isEmpty(monthRent)) {
            ToastUtils.d(this, "请输入月租金", Toast.LENGTH_SHORT).show();
            return;
        }

        persenter.requestResult(user, startDate, duration, terminationDate, monthRent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        persenter.detachView();
    }

    @Override
    public void returnCalculateResult(CalculateResult result) {
        /**
         * 补交
         */
        String backAmt = result.getBackAmt();
        /**
         * 已住
         */
        String liveDay = result.getLiveDay();
        /**
         * 退款
         */
        String refundAmt = result.getRefundAmt();
        /**
         * 未住或者超住
         */
        String unliveDay = result.getUnliveDay();

        if (!TextUtils.isEmpty(liveDay)) {
            layout_innerdata.setVisibility(View.VISIBLE);
            txt_innerdata.setText(liveDay);
        }

        if (!TextUtils.isEmpty(unliveDay)) {
            txt_outdata.setText(unliveDay);
        }
        layout_innerdata.setVisibility(View.GONE);
        layout_outdata.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(backAmt)) {
            layout_outdata.setVisibility(View.VISIBLE);
            txt_outdata_title.setText("超住时间");
//            persenter.showAlert(backAmt,"type");
            showAlert(this, backAmt, false);

        } else if (!TextUtils.isEmpty(refundAmt)) {
            layout_outdata.setVisibility(View.VISIBLE);
            txt_outdata_title.setText("未住时间");
//            persenter.showAlert(backAmt,"type");
            showAlert(this, refundAmt, true);
        }


    }

    @Override
    public void returnTestCalculateResult(CalculateResult result) {
        /**
         * 补交
         */
        String backAmt = result.getBackAmt();
        /**
         * 已住
         */
        String liveDay = result.getLiveDay();
        /**
         * 退款
         */
        String refundAmt = result.getRefundAmt();
        /**
         * 未住或者超住
         */
        String unliveDay = result.getUnliveDay();
        layout_innerdata.setVisibility(View.GONE);
        layout_outdata.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(liveDay)) {
            layout_innerdata.setVisibility(View.VISIBLE);
            txt_innerdata.setText(liveDay);
        }

        if (!TextUtils.isEmpty(unliveDay)) {
            txt_outdata.setText(unliveDay);
        }

        if (!TextUtils.isEmpty(backAmt)) {
            layout_outdata.setVisibility(View.VISIBLE);
            txt_outdata_title.setText("超住时间");
//            persenter.showAlert(backAmt,"type");

        } else if (!TextUtils.isEmpty(refundAmt)) {
            layout_outdata.setVisibility(View.VISIBLE);
            txt_outdata_title.setText("未住时间");
//            persenter.showAlert(backAmt,"type");
        }


    }


    /**
     * 弹出信息
     *
     * @param message
     * @param
     */
    private void showAlert(Context context, String message, boolean isReund) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.layout_calculatoresult, null);
        ViewHolder holder = new ViewHolder(view);
        if (isReund) {
            holder.txt_calculator_title.setText(R.string.refundAmt);
            holder.txt_amt.setText("-" + StringUtils.formatDecimal(message));
            holder.txt_amt.setSelected(true);
        } else {
            holder.txt_calculator_title.setText(R.string.backAmt);
            holder.txt_amt.setText("+" + StringUtils.formatDecimal(message));
        }
        builder.setView(view);
        builder.create().show();
    }

    public static class ViewHolder {
        public View rootView;
        public TextView txt_calculator_title;
        public TextView txt_amt;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_calculator_title = (TextView) rootView.findViewById(R.id.txt_calculator_title);
            this.txt_amt = (TextView) rootView.findViewById(R.id.txt_amt);
        }
    }
}

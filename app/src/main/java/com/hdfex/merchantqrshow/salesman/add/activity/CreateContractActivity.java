package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.CommodityCreateModel;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.resource.ItemHouse;
import com.hdfex.merchantqrshow.bean.salesman.resource.PayMonthly;
import com.hdfex.merchantqrshow.bean.salesman.resource.PayType;
import com.hdfex.merchantqrshow.bean.salesman.resource.PayTypeResponse;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.RegexUtils;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.ActionSheet;
import com.hdfex.merchantqrshow.widget.DateUtils;
import com.hdfex.merchantqrshow.widget.DeleteEditText;
import com.hdfex.merchantqrshow.widget.picker.DatePicker;

import java.util.List;

import okhttp3.Call;
import rx.Observable;
import rx.Subscriber;

/**
 * 填写信息
 * author Created by harrishuang on 2018/7/9.
 * email : huangjinping@hdfex.com
 */

public class CreateContractActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_commodity_name;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private TextView txt_address_add;
    private CommodityCreateModel createModel;
    private EditText et_from;
    private EditText et_to;
    private EditText edt_pay_method;
    private Button btn_submit;
    private EditText edt_pay_method_other;
    private FrameLayout layout_pay_method_other;
    private DeleteEditText et_monthRent;
    private DeleteEditText et_deposit;
    private User user;
    private List<PayType> payTypeList;
    private DeleteEditText et_telephone;


    public static void startAction(Context context) {
        Intent intent = new Intent(context, CreateContractActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contract);
        setTheme(R.style.ActionSheetStyle);
        initView();
        init();
    }

    /**
     *
     */
    private void init() {
        user = UserManager.getUser(this);
        createModel = new CommodityCreateModel();
        Observable<Object> house = EventRxBus.getInstance().register(IntentFlag.INTENT_HOUSE);
        house.subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                ItemHouse itemHouse = (ItemHouse) o;
                txt_address_add.setVisibility(View.VISIBLE);
                txt_address_add.setText("" + itemHouse.getDetailAddress());
                txt_address_add.setTag(itemHouse.getCommodityId());
                createModel.setAddrDetail(itemHouse.getDetailAddress());
                createModel.setCourtName(itemHouse.getCourtName());
                createModel.setCommodityId(itemHouse.getCommodityId());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_commodity_name:
                Intent intent = new Intent(this, SelectPayResourceActivity.class);
                startActivity(intent);
                break;
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.et_from:
                getTime(et_from);
                break;
            case R.id.et_to:
                getTime(et_to);
                break;
            case R.id.edt_pay_method:
                getPayTypeList();
                break;
            case R.id.btn_submit:
                submit();
                break;
        }

    }

    private void initView() {
        et_commodity_name = (EditText) findViewById(R.id.et_commodity_name);
        et_commodity_name.setOnClickListener(this);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setText("创建租房信息");
        txt_address_add = (TextView) findViewById(R.id.txt_address_add);
        txt_address_add.setOnClickListener(this);
        et_from = (EditText) findViewById(R.id.et_from);
        et_from.setOnClickListener(this);
        et_to = (EditText) findViewById(R.id.et_to);
        et_to.setOnClickListener(this);
        edt_pay_method = (EditText) findViewById(R.id.edt_pay_method);
        edt_pay_method.setOnClickListener(this);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        edt_pay_method_other = (EditText) findViewById(R.id.edt_pay_method_other);
        layout_pay_method_other = (FrameLayout) findViewById(R.id.layout_pay_method_other);
        layout_pay_method_other.setOnClickListener(this);
        et_monthRent = (DeleteEditText) findViewById(R.id.et_monthRent);
        et_monthRent.setOnClickListener(this);
        et_deposit = (DeleteEditText) findViewById(R.id.et_deposit);
        et_deposit.setOnClickListener(this);
        et_telephone = (DeleteEditText) findViewById(R.id.et_telephone);

    }

    /**
     * 选择时间
     *
     * @param edittext
     */
    private void getTime(final EditText edittext) {

        DatePicker datePicker = new DatePicker(this);
        datePicker.setOnDateSelectListener(new DatePicker.OnDateSelectListener() {
            @Override
            public void onSelect(String year, String month, String day) {
                StringBuffer sb = new StringBuffer();
                sb.append(year);
                sb.append("-");
                sb.append(String.format("%02d", Integer.parseInt(month)));
                sb.append("-");
                sb.append(String.format("%02d", Integer.parseInt(day)));
                edittext.setText(sb.toString());

            }
        });
        datePicker.show();

    }

    /**
     * 选择支付方式
     */
    private void selectPaymethod(List<PayType> payTypeList) {

        final String[] dataArr = new String[payTypeList.size()];
        final String[] dataCode = new String[payTypeList.size()];
        for (int i = 0; i < payTypeList.size(); i++) {
            dataArr[i] = payTypeList.get(i).getName();
            dataCode[i] = payTypeList.get(i).getValue();
        }
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(dataArr)
                .setCancelableOnTouchOutside(true).setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
            }

            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                edt_pay_method.setText(dataArr[index]);
                edt_pay_method.setTag(dataCode[index]);
                if (!"04".equals(dataCode[index])) {
                    layout_pay_method_other.setVisibility(View.GONE);
                } else {
                    layout_pay_method_other.setVisibility(View.VISIBLE);
                }
            }
        }).show();
    }

    /**
     * 选择支付时间
     */
    private void selectLeaseTime() {
        final String[] dataArr = {"一个月", "三个月", "半年", "一年", "两年"};

        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(dataArr)
                .setCancelableOnTouchOutside(true).setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
            }

            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
            }
        }).show();
    }

    private void submit() {
        // validate
        String address = txt_address_add.getText().toString().trim();

        if (TextUtils.isEmpty(address)) {
            showToast("选择房间");
            return;
        }

        String telephone = et_telephone.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            showToast("请填写电话号码");
            return;
        }

        if (!RegexUtils.mobile(telephone)) {
            ToastUtils.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(address)) {
            showToast("请填写电话号码");
            return;
        }

        String fromDate = et_from.getText().toString().trim();
        if (TextUtils.isEmpty(fromDate)) {
            showToast("请选开始时间");
            return;
        }
        String toDate = et_to.getText().toString().trim();
        if (TextUtils.isEmpty(toDate)) {
            showToast("请选择结束时间");
            return;
        }

        String method = edt_pay_method.getText().toString();
        if (TextUtils.isEmpty(method)) {
            showToast("请选择支付方式");
            return;
        }
        String methodCode = edt_pay_method.getTag().toString().trim();
        if (!DateUtils.isValidDate(et_from.getText().toString())) {
            showToast("有效期错误");
        }
        boolean b = DateUtils.compare_date(et_from.getText().toString(), et_to.getText().toString());
        if (b) {
            showToast("结束日应大于开始日期");
            return;
        }
        String monthRent = et_monthRent.getText().toString().trim();
        if (TextUtils.isEmpty(monthRent)) {
            showToast("请输入每月应付租金");
            return;
        }
        /**
         * 判断是不是显示其他月份
         */
        String methodOhter = edt_pay_method_other.getText().toString().trim();

        if (layout_pay_method_other.getVisibility() == View.VISIBLE) {
            if (TextUtils.isEmpty(methodOhter)) {
                showToast("请输入其他支付方式月数");
                return;
            }
            try {
                int month = Integer.parseInt(methodOhter);
                if (month < 1) {
                    showToast("其他支付方式月数不能小于1");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                showToast("其他支付方式月数数据格式不正确");
                return;
            }
        }

        String deposit = et_deposit.getText().toString().trim();
        if (TextUtils.isEmpty(deposit)) {
            showToast("请输入应付押金");
            return;
        }

        PayMonthly bean = new PayMonthly();
        bean.setStartDate(fromDate);
        bean.setEndDate(toDate);
        bean.setPhone(telephone);
        bean.setOtherMonth(methodOhter);
        bean.setCommodityId(createModel.getCommodityId());
        bean.setDownpaymentType(methodCode);
        bean.setDownpaymentTypeStr(method);
        bean.setCommodityName(createModel.getAddrDetail());
        bean.setMonthRent(monthRent);
        bean.setDeposit(deposit);
        MonthlyPreviewActivity.startAction(bean, this);
    }

    /**
     * 获取信息
     */
    private void getPayTypeList() {

        if (payTypeList != null && payTypeList.size() != 0) {
            selectPaymethod(payTypeList);
            return;
        }
        showProgress();
        OkHttpUtils.post().url(NetConst.GET_PAY_TYPE_LIST)
                .addParams("id", user.getId())
                .addParams("token", user.getToken())

                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                        try {

                            if (checkResonse(response)) {
                                PayTypeResponse resp = GsonUtil.changeGsonToBean(response, PayTypeResponse.class);
                                if (resp.getResult() == null || resp.getResult().size() == 0) {
                                    showToast("服务异常");
                                    return;
                                }
                                payTypeList = resp.getResult();
                                selectPaymethod(payTypeList);
                            }
                        } catch (Exception e) {
                            showToast("服务异常");
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

package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageData;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageModel;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadContractResponse;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadContractResult;
import com.hdfex.merchantqrshow.bean.salesman.installment.Installment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.GrgenrateRequest;
import com.hdfex.merchantqrshow.bean.salesman.order.Order;
import com.hdfex.merchantqrshow.bean.salesman.order.PeriodAmountResult;
import com.hdfex.merchantqrshow.bean.salesman.order.RepayPlan;
import com.hdfex.merchantqrshow.mvp.contract.ConfirmorderContract;
import com.hdfex.merchantqrshow.mvp.presenter.ConfirmPresenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.add.adapter.UpLoadAdapter;
import com.hdfex.merchantqrshow.utils.Constant;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.camera.compress.FileImageUtils;
import com.hdfex.merchantqrshow.utils.camera.easyphotopicker.EasyImageConfig;
import com.hdfex.merchantqrshow.utils.keyboardvisibilityevent.KeyboardVisibilityEvent;
import com.hdfex.merchantqrshow.utils.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import com.hdfex.merchantqrshow.view.MGridView.MGridView;
import com.hdfex.merchantqrshow.widget.DateUtils;
import com.hdfex.merchantqrshow.widget.ListV3Dialog;
import com.hdfex.merchantqrshow.widget.picker.DatePicker;
import com.hdfex.merchantqrshow.widget.picker.DurationPicker;
import com.jhworks.library.ImageSelector;
import com.yuyh.library.EasyGuide;
import com.yuyh.library.support.HShape;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Request;
import rx.Observable;
import rx.Subscriber;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 确认订单界面
 * Created by harrishuang on 16/7/7.
 */
public class ConfirmorderActivity extends BaseActivity implements View.OnClickListener, ConfirmorderContract.View {
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3; //请求码 裁剪图片

    private ImageView img_back;
    private TextView tb_tv_titile;
    private LinearLayout layout_toolbar;
    private RelativeLayout layout_duration;
    private RelativeLayout layout_submit;
    private TextView edt_order_duration;
    private List<Installment> projectList;
    private EditText edt_down_payment;
    private EditText edt_commodity_price;
    private EditText edt_apply_amount;
    private EditText edt_period_amount;
    private String duration;
    private String caseId;
    private Order order;
    private ConfirmorderContract.Presenter presenter;
    private User user;
    private LinearLayout layout_height_view;
    private DisplayMetrics displayMetrics;
    private int index = -1;
    private MGridView grv_lease_contract;
    private LinearLayout layout_upload_file;
    private ArrayList<String> mSelectPath;
    private ImageModel currentImageModel;
    private List<ImageModel> bankList;
    private UpLoadAdapter bankAdapter;
    private Installment currentInstallment;
    private TextView txt_managementFees;
    private boolean applyAmounthasFocus = false;
    private boolean downPaymenthasFocus = false;
    private EasyGuide easyGuide;
    private String type = null;
    private EditText edt_trainingCycle;
    private EditText edt_trainingStartDate;
    private EditText edt_trainingEndDate;
    private LinearLayout layout_trainingCycle;
    private TextView txt_open_repayPlan;
    private List<RepayPlan> mRepayPlanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);
        displayMetrics = new DisplayMetrics();
        initView();
        user = UserManager.getUser(this);
        presenter = new ConfirmPresenter();
        presenter.attachView(this);
        bankList = new ArrayList<>();
        mRepayPlanList = new ArrayList<>();
        tb_tv_titile.setText("确认选择");
        layout_submit.setOnClickListener(this);
        img_back.setOnClickListener(this);
        addDefoultChild(bankList, 0, "liushui");
        initData();
        setOnLiteners();
        initDataUpLoad();
    }

    /**
     * 添加信息
     *
     * @param bankList
     * @param row
     * @param name
     */
    private void addDefoultChild(List<ImageModel> bankList, int row, String name) {
        for (int i = 0; i < 1; i++) {
            ImageModel imageModel = new ImageModel(i, row, name);
            bankList.add(imageModel);
        }
    }

    /**
     * 初始化数据
     */
    private void initDataUpLoad() {
        bankAdapter = new UpLoadAdapter(bankList, this);
        grv_lease_contract.setAdapter(bankAdapter);
    }

    /**
     * 设置监听器
     */
    private void setOnLiteners() {
        currentImageModel = UserManager.currentImageModel;
        grv_lease_contract.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!sdcardExist()) {
                    ToastUtils.makeText(ConfirmorderActivity.this, "未检测到SD卡").show();
                    return;
                }
                currentImageModel = bankList.get(position);
                if (TextUtils.isEmpty(currentImageModel.getPath()) && currentImageModel.getImageFile() == null) {
                    UserManager.currentImageModel = currentImageModel;
                    ImageSelector selector = ImageSelector.create();
                    selector.origin(null)
                            .showCamera(true)
                            .openCameraOnly(false)
                            .count(9)
                            .spanCount(4)
                            .start(ConfirmorderActivity.this, Constant.REQUEST_IMAGE_FORM);

                    return;
                }
                openPreview(position, bankList);
            }
        });
        edt_trainingCycle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateTrainingView();
            }
        });
    }


//    /**
//     * 显示标题选择器
//     * @param
//     */
//    private void showPickerView() {
//        if (durationItem == null || durationItem.size() == 0) {
//            return;
//        }
//        DurationPicker picker = new DurationPicker(this, durationItem, new DurationPicker.OnDurationPickerSelectedListener() {
//            @Override
//            public void selected(int index, String item) {
//                edt_order_duration.setText(item);
//                duration = projectList.get(index).getDuration();
//                caseId = projectList.get(index).getCaseId();
//                calcuclation();
//            }
//        });
//        picker.show();
//    }

    /**
     * 打开预览及消除
     *
     * @param position
     * @param imageModelList
     */
    private void openPreview(int position, List<ImageModel> imageModelList) {
        ImageData imageData = new ImageData();
        List<ImageModel> list = new ArrayList<ImageModel>();
        list.addAll(imageModelList);
        list.remove(0);
        imageData.setImageModelList(list);
        Intent intent = new Intent(ConfirmorderActivity.this, PreViewAndDeleteActivity.class);
        intent.putExtra(IntentFlag.POSITION, position - 1);
        intent.putExtra(IntentFlag.INTENT_PIC, IntentFlag.INTENT_PIC);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.PICTURE_SELECT, imageData);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constant.PHOTO_VIEW);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        Serializable serializable = intent.getExtras().getSerializable(IntentFlag.SUBMIT_ORDER);
        if (serializable == null) {
            return;
        }
        order = (Order) serializable;
        edt_down_payment.setTextColor(getResources().getColor(R.color.text_hint2));
        edt_apply_amount.setTextColor(getResources().getColor(R.color.text_hint2));
        if (TextUtils.isEmpty(order.getDownpayment())) {
            edt_down_payment.setText("0.00");
        } else {
            edt_down_payment.setText(order.getDownpayment());
        }
        edt_down_payment.setSelection(edt_down_payment.getText().length());
//        edt_down_payment.setKeyListener(new DigitsKeyListener(true, false));
        edt_commodity_price.setText(order.getTotalPrice());
        if (TextUtils.isEmpty(order.getApplyAmount())) {
            BigDecimal applyamount = new BigDecimal(order.getTotalPrice()).subtract(new BigDecimal(edt_down_payment.getText().toString()));
            edt_apply_amount.setText(applyamount.toString());
        } else {
            edt_apply_amount.setText(order.getApplyAmount());
        }


        /**
         * 这个是检测可以选择哪个专案
         */
        projectList = order.getList();
        if (projectList.size() != 0) {
            updateView(projectList.size() - 1);
        }

//        setDefault(projectList);


        calcuclation();
        setLiteners();
        layout_height_view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {// 加载完成后回调
                // 务必取消监听，否则会多次调用
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    layout_height_view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    layout_height_view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                if (UserManager.getGuide(ConfirmorderActivity.this)) {
//                    showGuide();
                }
            }
        });

        Observable<Object> register = EventRxBus.getInstance().register(IntentFlag.CURRENT_DURATION);
        register.subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                updateView((int) o);
            }
        });
    }

    /**
     * 设置默认专案
     */
    private void setDefault(List<Installment> projectList) {
        if (projectList.size() != 0) {
            for (int i = (projectList.size() - 1); i >= 0; i--) {
                Installment installment = projectList.get(i);
                if (Installment.STATUS_YES.equals(installment.getStatus())) {
                    updateView(i);
                    break;
                }
            }
        }
    }

    /**
     * @param position
     */
    private void updateView(int position) {
        index = position;
        currentInstallment = projectList.get(position);
        duration = projectList.get(index).getDuration();
        edt_order_duration.setText(duration + "期");
        caseId = projectList.get(index).getCaseId();
        if (Installment.NEED_YES.equals(projectList.get(index).getIsNeedContract())) {
            layout_upload_file.setVisibility(View.VISIBLE);
        } else {
            layout_upload_file.setVisibility(View.GONE);
        }
        calcuclation();
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        layout_duration = (RelativeLayout) findViewById(R.id.layout_duration);
        layout_submit = (RelativeLayout) findViewById(R.id.layout_submit);
        edt_order_duration = (TextView) findViewById(R.id.edt_order_duration);
        edt_down_payment = (EditText) findViewById(R.id.edt_down_payment);
        edt_commodity_price = (EditText) findViewById(R.id.edt_commodity_price);
        edt_apply_amount = (EditText) findViewById(R.id.edt_apply_amount);
        layout_height_view = (LinearLayout) findViewById(R.id.layout_height_view);
        layout_duration.setOnClickListener(this);
        img_back.setOnClickListener(this);
        edt_period_amount = (EditText) findViewById(R.id.edt_period_amount);
        edt_period_amount.setOnClickListener(this);
        grv_lease_contract = (MGridView) findViewById(R.id.grv_lease_contract);
        layout_upload_file = (LinearLayout) findViewById(R.id.layout_upload_file);
        txt_managementFees = (TextView) findViewById(R.id.txt_managementFees);
        edt_trainingCycle = (EditText) findViewById(R.id.edt_trainingCycle);
//        edt_trainingCycle.setOnClickListener(this);
        edt_trainingStartDate = (EditText) findViewById(R.id.edt_trainingStartDate);
        edt_trainingStartDate.setOnClickListener(this);
        edt_trainingEndDate = (EditText) findViewById(R.id.edt_trainingEndDate);
        layout_trainingCycle = (LinearLayout) findViewById(R.id.layout_trainingCycle);
        txt_open_repayPlan = (TextView) findViewById(R.id.txt_open_repayPlan);
        txt_open_repayPlan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_submit:
                submit();
                break;
            case R.id.layout_duration:
                InstallmentActivity.start(ConfirmorderActivity.this, order, index);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.edt_trainingStartDate:
                getTime(edt_trainingStartDate);
                break;
            case R.id.txt_open_repayPlan:
                showRepayPlan();
                break;
//            case R.id.edt_trainingCycle:
//                getTrainingCycle(edt_trainingCycle);
//                break;
        }
    }

    /**
     * 提交数据
     */
    private ListV3Dialog dialogV3;

    private void showRepayPlan() {

        dialogV3 = new ListV3Dialog(this, mRepayPlanList, "还款计划", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogV3.dismiss();
            }
        });
        dialogV3.show();
    }

    private void getTrainingCycle(final TextView edittext) {
        final List<String> regionList = new ArrayList<>();
        for (int i = 1; i < 200; i++) {
            regionList.add(i + "个月");
        }
        DurationPicker picker = new DurationPicker(this, regionList);
        picker.setOnDurationPickerSelectedListener(new DurationPicker.OnDurationPickerSelectedListener() {
            @Override
            public void selected(int index, String item) {
                edittext.setText(item.replace("个月", ""));
                updateTrainingView();
            }
        });
        picker.show();
    }

    /**
     * 获取时间的控件
     *
     * @param edittext
     */
    private void getTime(final TextView edittext) {
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
                updateTrainingView();
            }
        });
        datePicker.show();

    }


    /**
     * 更新View
     */
    private void updateTrainingView() {
        try {
            String trainingCycle = edt_trainingCycle.getText().toString().trim();
            String trainingStartDate = edt_trainingStartDate.getText().toString().trim();
            if (TextUtils.isEmpty(trainingCycle)) {
                return;
            }
            if (TextUtils.isEmpty(trainingStartDate)) {
                return;
            }
            int tp_trainingCycle = Integer.parseInt(trainingCycle);

            Calendar calendar = Calendar.getInstance();
            Date parse = DateUtils.dateFormat.parse(trainingStartDate);
            calendar.setTime(parse);
            calendar.add(Calendar.MONTH, tp_trainingCycle);
            String endTime = DateUtils.dateFormat.format(calendar.getTime());
            edt_trainingEndDate.setText(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setLiteners() {
        edt_down_payment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txt_open_repayPlan.setVisibility(View.GONE);
                edt_down_payment.setTextColor(getResources().getColor(R.color.black));
                edt_apply_amount.setTextColor(getResources().getColor(R.color.black));

                if (applyAmounthasFocus) {
                    return;
                }

                if (TextUtils.isEmpty(s)) {
                    s = "0";
                }

                Pattern pattern = Pattern.compile("^\\d+$");
                Matcher isNum = pattern.matcher(s.toString());
                if (isNum.matches()) {
                    if (s.toString().startsWith("0")) {
                        if (!s.toString().equals("0")) {
                            s = new BigDecimal(s.toString()).toString();
                            edt_down_payment.setText(s);
                            edt_down_payment.setSelection(s.length());
                        }
                    }
                } else {
                    if (s.toString().startsWith(".")) {
                        s = "0" + s;
                        edt_down_payment.setText(s);
                        edt_down_payment.setSelection(s.length());
                    } else {
                        if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                            s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                            edt_down_payment.setText(s);
                            edt_down_payment.setSelection(s.length());
                        }
                    }
                }

                String commodityPrice = edt_commodity_price.getText().toString();
                if (new BigDecimal("" + s).compareTo(new BigDecimal(commodityPrice)) == 1) {
                    ToastUtils.makeText(ConfirmorderActivity.this, "首付不能高于总价").show();
                    edt_down_payment.setText(commodityPrice);
                    edt_down_payment.setSelection(edt_down_payment.getText().length());
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_down_payment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                downPaymenthasFocus = hasFocus;
                if (hasFocus) {
                    if (edt_down_payment.getText().toString().equals("0.00")
                            || edt_down_payment.getText().toString().equals(order.getDownpayment()))
                        edt_down_payment.setText("");
                }
            }
        });

        edt_apply_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_apply_amount.getText().toString().equals(order.getTotalPrice())
                        || edt_apply_amount.getText().toString().equals(order.getApplyAmount())) {
                    edt_apply_amount.setText("");
                }
            }
        });

        edt_apply_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txt_open_repayPlan.setVisibility(View.GONE);

                edt_down_payment.setTextColor(getResources().getColor(R.color.black));
                edt_apply_amount.setTextColor(getResources().getColor(R.color.black));
                if (downPaymenthasFocus) {
                    return;
                }
                if (TextUtils.isEmpty(s)) {
                    s = "0";
                }

                Pattern pattern = Pattern.compile("^\\d+$");
                Matcher isNum = pattern.matcher(s.toString());
                if (isNum.matches()) {
                    if (s.toString().startsWith("0")) {
                        if (!s.toString().equals("0")) {
                            s = new BigDecimal(s.toString()).toString();
                            edt_apply_amount.setText(s);
                            edt_apply_amount.setSelection(s.length());
                        }
                    }
                } else {
                    if (s.toString().startsWith(".")) {
                        s = "0" + s;
                        edt_apply_amount.setText(s);
                        edt_apply_amount.setSelection(s.length());
                    } else {
                        if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                            s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                            edt_apply_amount.setText(s);
                            edt_apply_amount.setSelection(s.length());
                        }
                    }
                }

                String commodityPrice = edt_commodity_price.getText().toString();
                if (new BigDecimal("" + s).compareTo(new BigDecimal(commodityPrice)) == 1) {
                    ToastUtils.makeText(ConfirmorderActivity.this, "分期金额不能高于总价").show();
                    edt_apply_amount.setText(commodityPrice);
                    edt_apply_amount.setSelection(edt_apply_amount.getText().length());
                    return;
                }
//                double sub = MathUtils.sub(Double.parseDouble(edt_commodity_price.getText().toString()), Double.parseDouble(s.toString()));
//                edt_down_payment.setText(sub + "");
//calcuclation();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        edt_apply_amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                applyAmounthasFocus = hasFocus;
                if (hasFocus) {
                    if (edt_apply_amount.getText().toString().equals(order.getTotalPrice())
                            || edt_apply_amount.getText().toString().equals(order.getApplyAmount())) {
                        edt_apply_amount.setText("");

                    }
                }
            }
        });

        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {

            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (!isOpen) {
                    calcuclation();
                }
            }
        });
    }

    private void submit() {
        // validate
        String payment = edt_down_payment.getText().toString().trim();
        if (TextUtils.isEmpty(payment)) {
            ToastUtils.makeText(this, "首付金额不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String price = edt_commodity_price.getText().toString().trim();
        if (TextUtils.isEmpty(price)) {
            ToastUtils.makeText(this, "总价不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String amount = edt_apply_amount.getText().toString().trim();
        if (TextUtils.isEmpty(amount)) {
            ToastUtils.makeText(this, "分期金额不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (new BigDecimal("" + edt_apply_amount.getText().toString()).compareTo(BigDecimal.ZERO) == 0) {
            ToastUtils.makeText(this, "分期金额不能为0", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(caseId)) {
            showToast("请选择分期产品");
            return;
        }

        String periosAmount = edt_period_amount.getText().toString();
        if (TextUtils.isEmpty(periosAmount)) {
            ToastUtils.makeText(this, "月还款额不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = UserManager.getUser(this);
        submit(user);
    }

    private void calcuclation() {
        txt_open_repayPlan.setVisibility(View.GONE);

        //分期金额
        String applyAmount = edt_apply_amount.getText().toString();
        if (TextUtils.isEmpty(applyAmount)) {
            applyAmount = "0";
        }
        if (applyAmount.toString().startsWith(".")) {
            applyAmount = "0" + applyAmount;
        }

        presenter.calculateUpdate(user, applyAmount, caseId);
//        if (order.getList().get(0).getDiscount().equals("1")) {
//            //贴息
//            BigDecimal div = new BigDecimal(applyAmount).divide(new BigDecimal(duration), 2, BigDecimal.ROUND_HALF_UP);
//            edt_period_amount.setText(div + "");
//        } else {
//            //不贴息
//            BigDecimal repayment = new BigDecimal(applyAmount).divide(new BigDecimal(duration), 2, BigDecimal.ROUND_HALF_UP);
//            BigDecimal interest = new BigDecimal(applyAmount).multiply(new BigDecimal("0.008"));
//            BigDecimal total = repayment.add(interest);
//            double div = total.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            edt_period_amount.setText(div + "");
//        }
    }


    private void checkShowByBuss() {
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(NetConst.CHECK_SHOW_BY_BUSS)
                .addParams("token", user.getToken())
                .addParams("id", user.getId());
        postFormBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        showWebEirr(e);
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject jsonObject1 = jsonObject.optJSONObject("result");
                                if ("1".equals(jsonObject1.optString("showFlag"))) {
                                    layout_trainingCycle.setVisibility(View.VISIBLE);
                                } else {
                                    layout_trainingCycle.setVisibility(View.GONE);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showWebEirr(e);
                        }
                    }
                });
    }


    private void submit(final User user) {
        if (!connect() && !isLogin()) {
            return;
        }

        String trainingCycle = edt_trainingCycle.getText().toString().trim();
        String trainingStartDate = edt_trainingStartDate.getText().toString().trim();
        String trainingEndDate = edt_trainingEndDate.getText().toString().trim();
        if (layout_trainingCycle.getVisibility() == View.VISIBLE) {
            if (TextUtils.isEmpty(trainingCycle)) {
                showToast("请输入培训周期");
                return;
            }
            try {
                int tp_trainingCycle = Integer.parseInt(trainingCycle);
                if (tp_trainingCycle < 1) {
                    showToast("培训周期不小于1");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (TextUtils.isEmpty(trainingStartDate)) {
                showToast("请选择开始时间");
                return;
            }

        }
        /**
         * 判断下是不是需要提交资料
         */

        StringBuffer buffer = new StringBuffer();
        if (currentInstallment != null && Installment.NEED_YES.equals(currentInstallment.getIsNeedContract())) {
            if (bankList.size() == 1) {
                ToastUtils.d(this, "请上传合同资料").show();
                return;
            }


            List<ImageModel> tempList = new ArrayList<>();
            tempList.addAll(bankList);
            tempList.remove(0);
            for (ImageModel imageModel : tempList) {
                buffer.append(imageModel.getAbsolutePath());
                buffer.append(",");
            }

            if (buffer.length() > 1) {
                buffer.deleteCharAt(buffer.length() - 1);
            }
        }
        GrgenrateRequest request = new GrgenrateRequest();
        request.setId(user.getId());
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(NetConst.APP_GRENTATE)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("duration", duration)
                .addParams("caseId", caseId + "")
                .addParams("downpayment", edt_down_payment.getText().toString())
                .addParams("applyAmount", edt_apply_amount.getText().toString())
                .addParams("periodAmount", edt_period_amount.getText().toString())
                .addParams("totalPrice", edt_commodity_price.getText().toString())
                .addParams("list", order.getProuctList())
                .addParams("files", buffer.toString());
//                .addParams("commoditys", order.getProuctList())
        if (layout_trainingCycle.getVisibility() == View.VISIBLE) {
            postFormBuilder.addParams("trainingCycle", trainingCycle);
            postFormBuilder.addParams("trainingStartDate", trainingStartDate);
            postFormBuilder.addParams("trainingEndDate", trainingEndDate);
        }


        postFormBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissProgress();
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        showProgress();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        showWebEirr(e);
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject jsonObject1 = jsonObject.optJSONObject("result");
                                Intent mIntent = new Intent(ConfirmorderActivity.this, QRCodeProduceActivity.class);
                                mIntent.putExtra(IntentFlag.INTENT_NAME, IntentFlag.CONFORM);
                                mIntent.putExtra(IntentFlag.INTENT_OPEN_TYPE, IntentFlag.INTENT_OPEN_CREATE);
                                mIntent.putExtra("packageId", jsonObject1.optString("packageId"));
                                startActivity(mIntent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showWebEirr(e);
                        }
                    }
                });
    }

    @Override
    public void returnPeriodAmount(String periodAmount) {
        edt_period_amount.setText(periodAmount);
    }

    @Override
    public void returnPeriodAmountResult(PeriodAmountResult result) {
        StringBuffer buffer = new StringBuffer();
        if (result.getGracePeriod() != null && result.getGracePeriod() > 0) {
            buffer.append("宽限期：" + result.getManagementFees() + "x" + result.getGracePeriod() + "期" + "\n");
            buffer.append("非宽限期:" + result.getPeriodAmount() + "x" + result.getPeriod() + "期" + "");
        } else {
            buffer.append("分期金额：" + result.getPeriodAmount() + "x" + result.getPeriod() + "期" + "");
        }
        txt_managementFees.setText("" + buffer.toString());

        List<RepayPlan> repayPlanList = result.getRepayPlanList();
        mRepayPlanList.clear();
        if (repayPlanList != null) {
            mRepayPlanList.addAll(repayPlanList);
            if (mRepayPlanList.size() > 0) {
                txt_open_repayPlan.setVisibility(View.VISIBLE);
            }
        }

    }

    /**
     * 引导图
     */
    public void showGuide() {
        easyGuide = new EasyGuide.Builder(this)
                // 增加View高亮区域，可同时显示多个
                .addHightArea(layout_height_view, HShape.OVAL)
                .addView(createTipsView(), 0, displayMetrics.heightPixels - (layout_height_view.getHeight() * 2),
                        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                .dismissAnyWhere(true)
                .performViewClick(true)
                .build();
        easyGuide.show();
    }

    /**
     * 自定义view
     *
     * @return
     */
    private View createTipsView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_tips_create, null);
        TextView txt_no_hint = (TextView) view.findViewById(R.id.txt_no_hint);
        txt_no_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (easyGuide != null) {
                    UserManager.setGuide(ConfirmorderActivity.this, false);
                    easyGuide.dismiss();
                }
            }
        });
        return view;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constant.REQUEST_IMAGE_FORM:
                mSelectPath = data.getStringArrayListExtra(ImageSelector.EXTRA_RESULT);
                compressorImage(FileImageUtils.getFile(mSelectPath.get(0)), 0, Constant.REQUEST_IMAGE_FORM);
                break;
            case Constant.REQUEST_IMAGE_TOM:
                mSelectPath = data.getStringArrayListExtra(ImageSelector.EXTRA_RESULT);
                compressorImage(FileImageUtils.getFile(mSelectPath.get(0)), 0, Constant.REQUEST_IMAGE_TOM);
                break;
            case REQUEST_CODE_CROP_IMAGE:
                break;
            case EasyImageConfig.REQ_PICK_PICTURE_FROM_DOCUMENTS:
            case EasyImageConfig.REQ_PICK_PICTURE_FROM_GALLERY:
            case EasyImageConfig.REQ_SOURCE_CHOOSER:
            case EasyImageConfig.REQ_TAKE_PICTURE:
//                processEasyImage(requestCode, resultCode, data);
                break;
            case Constant.PHOTO_VIEW:
                Bundle extras = data.getExtras();
                ImageData imageData = (ImageData) extras.getSerializable(IntentFlag.INTENT_PIC);
                removeViewList(imageData.getImageModelList(), extras.getInt(IntentFlag.INTENT_ROW_TYPE));
                break;
            default:
                break;
        }
    }

    /**
     * 处理数据问题
     *
     * @param imageModelList
     */
    private void removeViewList(List<ImageModel> imageModelList, int rowType) {
        if (imageModelList == null) {
            return;
        }
        switch (rowType) {
            case 0:
                ImageModel imageModel = bankList.get(0);
                bankList.clear();
                bankList.add(imageModel);
                bankList.addAll(imageModelList);
                bankAdapter.notifyDataSetChanged();
                break;
            case 1:
                break;
        }
    }

    /**
     * 先压缩后上传
     *
     * @param file
     * @param postion
     * @param imagetype
     */
    private void compressorImage(final File file, final int postion, final int imagetype) {
        if (!connect()) {
            return;
        }
        /**
         * 判断文件是不是0temp 文件
         */
        if (!FileImageUtils.isFileChack(file)) {
            ToastUtils.makeText(ConfirmorderActivity.this, "图片格式或大小异常,请重新选择").show();
            return;
        }

        Luban.with(ConfirmorderActivity.this)
                .load(file)                     //传人要压缩的图片
                .setCompressListener(new OnCompressListener() { //设置回调

                    @Override
                    public void onStart() {
                        //  压缩开始前调用，可以在方法内启动 loading UI
                        ToastUtils.d(ConfirmorderActivity.this, "正在处理").show();

                    }

                    @Override
                    public void onSuccess(File file) {
//    压缩成功后调用，返回压缩后的图片文件

                        upLoadURL(file, postion, imagetype);

                    }

                    @Override
                    public void onError(Throwable e) {
                        //  当压缩过去出现问题时调用
                        ToastUtils.d(ConfirmorderActivity.this, "图片处理异常").show();
                    }
                }).launch();    //启动压缩


    }

    /**
     * 上传文件方法
     *
     * @param file
     */
    private void upLoadURL(final File file, final int postion, final int imagetype) {


        /**
         * 1 租赁合同, 2 代理合同
         */
        if (Constant.REQUEST_IMAGE_FORM == imagetype) {
            type = "1";
        } else {
            type = "2";
        }
        currentImageModel.setType(type);
        updateView(file, null, null, true, type);
        OkHttpUtils.post()
                .url(NetConst.UPLOAD_CONTRACT)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("type", type)
                .addFile("imgFile", file.getName() + ".jpg", file).build()
                .connTimeOut(10000)
                .readTimeOut(10000)
                .writeTimeOut(10000).execute(new StringCallback() {
            @Override
            public void onAfter() {

            }

            @Override
            public void inProgress(float progress) {
                showProgressForChild(progress);
            }

            @Override
            public void onBefore(Request request) {

                showProgress();
            }

            @Override
            public void onError(Call call, Exception e) {
                dismissProgress();
                removeEnd(currentImageModel.getRow());

                ToastUtils.makeText(ConfirmorderActivity.this, e.getMessage()).show();
            }

            @Override
            public void onResponse(String response) {

                try {
                    UploadContractResponse bussinessNameRepponse = new UploadContractResponse();
                    bussinessNameRepponse = GsonUtil.changeGsonToBean(response, UploadContractResponse.class);
                    if (bussinessNameRepponse.getCode() == 0 && bussinessNameRepponse.getResult() != null) {
                        UploadContractResult result = bussinessNameRepponse.getResult();
                        updateView(file, result.getRelativePath(), result.getRelativePath(), false, type);
                        if (postion < (mSelectPath.size() - 1)) {
                            File file = FileImageUtils.getFile(mSelectPath.get(postion + 1));
                            upLoadURL(file, postion + 1, imagetype);
                            return;
                        }
                        dismissProgress();
                        /**
                         * 去哪里
                         */
                    } else {
                        dismissProgress();
                        ToastUtils.e(ConfirmorderActivity.this, bussinessNameRepponse.getMessage() + "").show();
                    }
                } catch (Exception e) {
                    dismissProgress();
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkShowByBuss();
    }

    /**
     * 获取图片更新UI接口
     *
     * @param imageFile
     */
    private void updateView(File imageFile, String relativePath, String absolutePath, boolean isAddChild, String type) {


        switch (currentImageModel.getRow()) {
            case 0:
                if (isAddChild) {
                    bankList.get(currentImageModel.getPosition()).setImageFile(imageFile);
                    bankAdapter.notifyDataSetChanged();
                    updateNewChild(bankList, bankAdapter);
                } else {
                    bankList.get(currentImageModel.getPosition() + 1).setImageFile(imageFile);
                    bankList.get(currentImageModel.getPosition() + 1).setRelativePath(relativePath);
                    bankList.get(currentImageModel.getPosition() + 1).setAbsolutePath(absolutePath);
                    bankList.get(currentImageModel.getPosition() + 1).setAbsolutePath(absolutePath);
                    bankList.get(currentImageModel.getPosition() + 1).setType(type);
                    bankAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    /**
     * 判断更细UI
     *
     * @param list
     * @param adapter
     */
    private void updateNewChild(List<ImageModel> list, BaseAdapter adapter) {
        boolean allUpLoad = isAllUpLoad(list);
        if (allUpLoad) {
            ImageModel imagemode = new ImageModel(0, currentImageModel.getRow(), currentImageModel.getName());
            currentImageModel = imagemode;
            List<ImageModel> temp = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                ImageModel imageModel = list.get(i);
                imageModel.setPosition(i + 1);
                temp.add(imageModel);
            }
            list.clear();
            list.add(imagemode);
            list.addAll(temp);
        }
        adapter.notifyDataSetChanged();
    }


    private boolean isAllUpLoad(List<ImageModel> list) {
        for (ImageModel imageMode : list) {
            if ((imageMode.getImageFile() == null && (TextUtils.isEmpty(imageMode.getPath())))) {
                return false;
            }

        }
        return true;
    }


    /**
     * 显示子View的进度
     *
     * @param progress
     */
    private void showProgressForChild(float progress) {
        int proInt = (int) (progress * 100);
        switch (currentImageModel.getRow()) {
            case 0:
                if (proInt > 0 && proInt < 100) {
                    updateChild(grv_lease_contract, bankAdapter, String.valueOf(proInt + "%"), "图片上传中");
                } else {
                    updateChild(grv_lease_contract, bankAdapter, "", "");
                }
                break;
        }
    }

    /**
     * 刷新子进度
     *
     * @param gridView
     * @param adapter
     */
    private void updateChild(MGridView gridView, UpLoadAdapter adapter, String progress, String title) {
        View view = gridView.getChildAt(1);
        UpLoadAdapter.ViewHolder viewHolder = (UpLoadAdapter.ViewHolder) view.getTag();
        viewHolder.txt_progress.setText(progress);
        viewHolder.txt_progress_title.setText(title);
    }

    /**
     * 删除最后一个
     */
    private void removeEnd(int row) {
        switch (row) {
            case 0:
                bankList.remove(bankList.size() - 1);
                bankAdapter.notifyDataSetChanged();
                break;
        }
    }


}

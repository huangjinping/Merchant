package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageData;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageModel;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadContractResponse;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadContractResult;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadData;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.AdditionalInfo;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.CommodityCreateModel;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.CreateSeri;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.TypeModel;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.resource.ItemHouse;
import com.hdfex.merchantqrshow.mvp.presenter.CreateCommodityPresenter;
import com.hdfex.merchantqrshow.mvp.view.CreateCommodityView;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.add.adapter.UpLoadAdapter;
import com.hdfex.merchantqrshow.utils.Constant;
import com.hdfex.merchantqrshow.utils.DialogUtils.NiftyDialogBuilder;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.RegexUtils;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.camera.compress.FileImageUtils;
import com.hdfex.merchantqrshow.utils.camera.easyphotopicker.EasyImageConfig;
import com.hdfex.merchantqrshow.utils.log.LogUtil;
import com.hdfex.merchantqrshow.view.MGridView.MGridView;
import com.hdfex.merchantqrshow.widget.ActionSheet;
import com.hdfex.merchantqrshow.widget.DeleteEditText;
import com.hdfex.merchantqrshow.widget.picker.AddressInitTask;
import com.hdfex.merchantqrshow.widget.picker.AddressPicker;
import com.hdfex.merchantqrshow.widget.picker.DatePicker;
import com.jhworks.library.ImageSelector;
import com.yuyh.library.EasyGuide;
import com.yuyh.library.support.HShape;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
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
 * 新增商品界面
 * Created by maidou521 on 2016/9/5.
 */
public class CreateCommodityActivity extends BaseActivity implements View.OnClickListener, CreateCommodityView {
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3; //请求码 裁剪图片
    private String type = null;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private EditText et_commodity_name;
    private DeleteEditText et_live_addrProvince;
    private DeleteEditText et_live_addrTown;
    private DeleteEditText et_customer_name;
    private DeleteEditText et_telephone;
    private EditText et_from;
    private EditText et_to;
    private EditText et_pay_mode;
    private DeleteEditText et_monthly_rent;
    private EditText et_pay_duration;
    private Button btn_submit;
    private List<TypeModel> downpaymentLists;
    private List<TypeModel> durationLists;
    private CommodityCreateModel createModel;
    private User user;
    private String typeModeCode;
    private String durationCode;
    private TextView txt_left_name;
    private ImageView iv_setting;
    private TextView tv_home;
    private DeleteEditText et_down_payment;
    private MGridView grv_lease_contract;
    private ScrollView sc_root;
    private UpLoadAdapter bankAdapter;
    private List<ImageModel> bankList;
    private ImageModel currentImageModel;
    private ArrayList<String> mSelectPath;
    private File mFileTemp;  //临时图片
    private CreateCommodityPresenter presenter;
    private int index = -1;
    private TextView txt_height_view;
    private DisplayMetrics displayMetrics;
    private TextView txt_no_hint;
    private TextView txt_address_add;
    private String caseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_commodity);
        setTheme(R.style.ActionSheetStyle);
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        bankList = new ArrayList<>();
        presenter = new CreateCommodityPresenter();
        presenter.attachView(this);
        initView();
        try {
            initDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        init();
        initDataUpLoad();
        setOnLiteners();
        presenter.showAlert(this);
    }


    private void setOnLiteners() {
        currentImageModel = UserManager.currentImageModel;
        grv_lease_contract.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!sdcardExist()) {
                    ToastUtils.makeText(CreateCommodityActivity.this, "未检测到SD卡").show();
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
                            .start(CreateCommodityActivity.this, Constant.REQUEST_IMAGE_FORM);

                    return;
                }
                openPreview(position, bankList);
            }
        });

        txt_height_view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {// 加载完成后回调
                // 务必取消监听，否则会多次调用
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    txt_height_view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    txt_height_view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                if (UserManager.getGuide(CreateCommodityActivity.this)) {
                    show();
                }
            }
        });
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
            ToastUtils.makeText(CreateCommodityActivity.this, "图片格式或大小异常,请重新选择").show();
            return;
        }

        Luban.with(CreateCommodityActivity.this)
                .load(file)                     //传人要压缩的图片
                .setCompressListener(new OnCompressListener() { //设置回调

                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        ToastUtils.d(CreateCommodityActivity.this, "正在处理").show();

                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件

                        upLoadURL(file, postion, imagetype);

                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过去出现问题时调用
                        ToastUtils.d(CreateCommodityActivity.this, "图片处理异常").show();
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

        if (currentImageModel == null) {
            showToast("您的手机内存严重不足！请清除其他后台运行应用，足够内存才能使用");
            return;
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

                ToastUtils.makeText(CreateCommodityActivity.this, e.getMessage()).show();
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
                        ToastUtils.e(CreateCommodityActivity.this, bussinessNameRepponse.getMessage() + "").show();
                    }
                } catch (Exception e) {
                    dismissProgress();
                    e.printStackTrace();
                }
            }
        });
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
        Intent intent = new Intent(CreateCommodityActivity.this, PreViewAndDeleteActivity.class);
        intent.putExtra(IntentFlag.POSITION, position - 1);
        intent.putExtra(IntentFlag.INTENT_PIC, IntentFlag.INTENT_PIC);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.PICTURE_SELECT, imageData);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constant.PHOTO_VIEW);
    }


    protected void init() {
        addDefoultChild(bankList, 0, "liushui");
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
                index = (int) o;
                et_pay_duration.setText(durationLists.get(index).getName() + "期");
                et_pay_duration.setTag(index);

            }
        });
    }


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
     * 初始化数据库
     */
    private void initDataBase() {

        initData();
    }


    private void initData() {
        tb_tv_titile.setText("创建租房信息");
        user = UserManager.getUser(this);
        createModel = new CommodityCreateModel();
        presenter.requestAdditional(user, CreateCommodityPresenter.NORMAL_TYPE);
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        txt_height_view = (TextView) findViewById(R.id.txt_height_view);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        et_commodity_name = (EditText) findViewById(R.id.et_commodity_name);
        et_commodity_name.setOnClickListener(this);
        et_live_addrProvince = (DeleteEditText) findViewById(R.id.et_live_addrProvince);
        et_live_addrProvince.setOnClickListener(this);
        et_live_addrTown = (DeleteEditText) findViewById(R.id.et_live_addrTown);
        et_customer_name = (DeleteEditText) findViewById(R.id.et_customer_name);
        et_telephone = (DeleteEditText) findViewById(R.id.et_telephone);
        et_from = (EditText) findViewById(R.id.et_from);
        txt_address_add = (TextView) findViewById(R.id.txt_address_add);
        et_from.setOnClickListener(this);
        et_to = (EditText) findViewById(R.id.et_to);
        et_to.setOnClickListener(this);
        et_pay_mode = (EditText) findViewById(R.id.et_pay_mode);
        et_pay_mode.setOnClickListener(this);
        et_monthly_rent = (DeleteEditText) findViewById(R.id.et_down_payment);
        et_pay_duration = (EditText) findViewById(R.id.et_pay_duration);
        et_pay_duration.setOnClickListener(this);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        et_monthly_rent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //输入金额实时校验
                if (TextUtils.isEmpty(s)) {
                    s = "0";
                }
                Pattern pattern = Pattern.compile("^\\d+$");
                Matcher isNum = pattern.matcher(s.toString());
                if (isNum.matches()) {
                    if (s.toString().startsWith("0")) {
                        if (!s.toString().equals("0")) {
                            s = new BigDecimal(s.toString()).toString();
                            et_monthly_rent.setText(s);
                            et_monthly_rent.setSelection(s.length());
                        }
                    }
                } else {
                    if (s.toString().startsWith(".")) {
                        s = "0" + s;
                        et_monthly_rent.setText(s);
                        et_monthly_rent.setSelection(s.length());
                    } else {
                        if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                            s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                            et_monthly_rent.setText(s);
                            et_monthly_rent.setSelection(s.length());
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txt_left_name = (TextView) findViewById(R.id.txt_left_name);
        txt_left_name.setOnClickListener(this);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        iv_setting.setOnClickListener(this);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_home.setOnClickListener(this);

        et_down_payment = (DeleteEditText) findViewById(R.id.et_down_payment);
        et_down_payment.setOnClickListener(this);
        grv_lease_contract = (MGridView) findViewById(R.id.grv_lease_contract);
        sc_root = (ScrollView) findViewById(R.id.sc_root);
        sc_root.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_commodity_name:
                Intent intent = new Intent(this, SelectResourceActivity.class);
                startActivity(intent);
                break;
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.btn_submit:
                getEndeDate();
                break;
            case R.id.et_live_addrProvince:
                onAddrProvince();
                break;
            case R.id.et_from:
                getTime(et_from);
                break;
            case R.id.et_to:
                getTime(et_to);
                break;
            case R.id.et_pay_mode:
                presenter.requestAdditional(user, CreateCommodityPresenter.DOWNPAYMENT_TYPE);
                break;
            case R.id.et_pay_duration:
                presenter.requestAdditional(user, CreateCommodityPresenter.DURATION_TYPE);
                break;
        }
    }


    private void DataPicker(final List<TypeModel> downpaymentLists, final EditText editext) {


        if (downpaymentLists == null || downpaymentLists.size() == 0) {
            return;
        }
        final List<String> dataStringlists = new ArrayList<>();
        for (TypeModel typeModel : downpaymentLists) {
            dataStringlists.add(typeModel.getName());
        }
//        DurationPicker picker = new DurationPicker(this, dataStringlists, new DurationPicker.OnDurationPickerSelectedListener() {
//            @Override
//            public void selected(int index, String item) {
//                editext.setText(item);
//                editext.setTag(index);
//            }
//        });
//        picker.show();
        String[] dataArr = dataStringlists.toArray(new String[dataStringlists.size()]);
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(dataArr)
                .setCancelableOnTouchOutside(true).setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
            }

            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                editext.setText(dataStringlists.get(index));
                editext.setTag(index);
            }
        }).show();
    }

    private void onAddrProvince() {
        new AddressInitTask(CreateCommodityActivity.this, new AddressPicker.OnAddressPickListener() {
            @Override
            public void onAddressPicked(String province, String city, String county) {
                if (province.equals(city)) {
                    et_live_addrProvince.setText(province + " " + county);
                } else {
                    et_live_addrProvince.setText(province + " " + city + " " + county);
                }
                createModel.setAddrProvince(province);
                createModel.setAddrCounty(city);
                createModel.setAddrArea(county);
                et_live_addrProvince.clearDrawable();
            }

            @Override
            public void onAddressPickedCode(String provinceCode, String cityCode, String countyCode) {


            }


        }).execute("北京市", "北京市", "东城区");
    }

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

    private void submit() {

        if (txt_address_add.getTag() == null) {
            ToastUtils.makeText(this, "选择房源", Toast.LENGTH_SHORT).show();
            return;
        }
        String commodityName = txt_address_add.getText().toString().trim();

//        String addrProvince = et_live_addrProvince.getText().toString().trim();
//        if (TextUtils.isEmpty(addrProvince)) {
//            ToastUtils.makeText(this, "请选择省/市/区/县", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        String addrDetail = et_live_addrTown.getText().toString().trim();
//        if (TextUtils.isEmpty(addrDetail) || addrDetail.length() < 5) {
//            ToastUtils.makeText(this, "请输入正确的详细地址(街道/楼/室)", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        String customername = et_customer_name.getText().toString().trim();
//        if (TextUtils.isEmpty(customername) || customername.length() < 2) {
//            ToastUtils.makeText(this, "请输入正确的客户姓名", Toast.LENGTH_SHORT).show();
//            return;
//        }

        String telephone = et_telephone.getText().toString().trim();
        if (!RegexUtils.mobile(telephone)) {
            ToastUtils.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }

        String from = et_from.getText().toString().trim();
        if (TextUtils.isEmpty(from)) {
            ToastUtils.makeText(this, "请选择起租日期", Toast.LENGTH_SHORT).show();
            return;
        }

        String to = et_to.getText().toString().trim();
//        if (TextUtils.isEmpty(to)) {
//            ToastUtils.makeText(this, "请选择到租日期", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        if (verifyDate(from, to)) {
//            ToastUtils.makeText(this, "到期日应该大于起租日", Toast.LENGTH_SHORT).show();
//            return;
//        }

        String mode = et_pay_mode.getText().toString().trim();
        if (TextUtils.isEmpty(mode)) {
            ToastUtils.makeText(this, "请选择租客首付方式", Toast.LENGTH_SHORT).show();
            return;
        }

        String payment = et_monthly_rent.getText().toString().trim();
        if (TextUtils.isEmpty(payment)) {
            ToastUtils.makeText(this, "请输入每月租金金额", Toast.LENGTH_SHORT).show();
            return;
        }

        String duration = et_pay_duration.getText().toString().trim();
        if (TextUtils.isEmpty(duration)) {
            ToastUtils.makeText(this, "请选择需要分期几个月", Toast.LENGTH_SHORT).show();
            return;
        }

        createModel.setCommodityName(commodityName);
//        createModel.setAddrDetail(addrDetail);
//        createModel.setName(customername);
        createModel.setPhone(telephone);
        createModel.setStartDate(from);
        createModel.setEndDate(to);
        typeModeCode = downpaymentLists.get((int) et_pay_mode.getTag()).getCode();
        durationCode = durationLists.get((int) et_pay_duration.getTag()).getCode();
        caseId = durationLists.get((int) et_pay_duration.getTag()).getCaseId();
        createModel.setDownpaymentType(mode);
        createModel.setMonthRent(payment);
        createModel.setDuration(durationCode);
        createModel.setTypeModeCode(typeModeCode);
        createModel.setDurationCode(durationCode);
        createModel.setCaseId(caseId);
        onPriview();

    }

    /**
     * 加载上传图片页面
     */
    private void toUploadPhotoActivity() {
        savePic();
        Intent mIntent = new Intent(this, UploadPhotoNewActivity.class);
        CreateSeri createSeri = new CreateSeri();
        createSeri.setCommodityCreateModel(createModel);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.SERIALIZABLE, createSeri);
        mIntent.putExtras(bundle);
        startActivity(mIntent);
    }


    /**
     * 展示图片
     *
     * @param
     */
    public void onPriview() {
        if (!checkPhoto()) {
            return;
        }
        LogUtil.d("okhttp", createModel.toString());
        savePic();
        Intent intent = new Intent(this, PreviewActivity.class);
        CreateSeri createSeri = new CreateSeri();
        createSeri.setCommodityCreateModel(createModel);
        createSeri.setRentModelLists(bankList);
        List<ImageModel> agencyModelLists = new ArrayList<>();
        createSeri.setAgencyModelLists(agencyModelLists);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.SERIALIZABLE, createSeri);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * 最少传两张图片
     *
     * @return
     */
    private boolean checkPhoto() {
        if (bankList.size() < 2) {
            ToastUtils.d(getApplicationContext(), "请上传租赁合同").show();
            return false;
        }
        return true;

    }

    /**
     * 保存图片信息
     */
    private void savePic() {
        UploadData upload = new UploadData();
        upload.setBankList(bankList);
        UserManager.saveUploadPic(this, upload);
    }


    @Override
    public void onBackPressed() {
        showBackDialog();
    }

    public void showBackDialog() {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("提示")
                .withTitleColor("#FFFFFF")
                .withMessage("当前信息未保存，退出将重新编辑")
                .withMessageColor("#000000")
                .withDialogColor("#FFFFFF")
                .withIcon(this.getResources().getDrawable(R.mipmap.ic_merchant_logo))
                .isCancelableOnTouchOutside(true)
                .withDuration(100)
//              .withEffect(Effectstype.Shake)
                .withButton1Text("否")
                .withButton2Text("是")
                .isCancelable(false)
                .isCancelableOnTouchOutside(false)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        finish();
                    }
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        EventRxBus.getInstance().unregister(IntentFlag.INTENT_HOUSE);
        EventRxBus.getInstance().unregister(IntentFlag.CURRENT_DURATION);

    }

    @Override
    public void returnEndDate(String endDate) {
        et_to.setText(endDate);
        submit();

    }

    @Override
    public void returnDurationLists(List<TypeModel> durationLists) {
        this.durationLists = durationLists;
        AdditionalInfo info = new AdditionalInfo();
        info.setDurationList(durationLists);
        PeriodsActivity.start(CreateCommodityActivity.this, info, index);
    }


    @Override
    public void returnDownpaymentLists(List<TypeModel> downpaymentLists) {
        this.downpaymentLists = downpaymentLists;
        DataPicker(downpaymentLists, et_pay_mode);
    }

    /**
     * 获取end时间
     */
    private void getEndeDate() {
        if (txt_address_add.getTag() == null) {
            ToastUtils.makeText(this, "选择房源", Toast.LENGTH_SHORT).show();
            return;
        }
        String commodityName = txt_address_add.getTag().toString().trim();

        String from = et_from.getText().toString().trim();
        if (TextUtils.isEmpty(from)) {
            ToastUtils.makeText(this, "请选择起租日期", Toast.LENGTH_SHORT).show();

            return;
        }
        String mode = et_pay_mode.getText().toString().trim();
        if (TextUtils.isEmpty(mode)) {
            ToastUtils.makeText(this, "请选择租客首付方式", Toast.LENGTH_SHORT).show();

            return;
        }
        String duration = et_pay_duration.getText().toString().trim();
        if (TextUtils.isEmpty(duration)) {
            ToastUtils.makeText(this, "请选择需要分期几个月", Toast.LENGTH_SHORT).show();
            return;
        }
        typeModeCode = downpaymentLists.get((int) et_pay_mode.getTag()).getCode();
        durationCode = durationLists.get((int) et_pay_duration.getTag()).getCode();
        caseId = durationLists.get((int) et_pay_duration.getTag()).getCode();
        presenter.requestCalculateDueDate(user, from, typeModeCode, durationCode);
    }

    private EasyGuide easyGuide;

    /**
     * 引导图
     */
    public void show() {
        easyGuide = new EasyGuide.Builder(this)
                // 增加View高亮区域，可同时显示多个
                .addHightArea(txt_height_view, HShape.OVAL)
                .addView(createTipsView(), 0, displayMetrics.heightPixels - (txt_height_view.getHeight() * 5),
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
        txt_no_hint = (TextView) view.findViewById(R.id.txt_no_hint);
        txt_no_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (easyGuide != null) {
                    UserManager.setGuide(CreateCommodityActivity.this, false);
                    easyGuide.dismiss();
                }
            }
        });
        return view;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadRedPackage(this);
    }
}

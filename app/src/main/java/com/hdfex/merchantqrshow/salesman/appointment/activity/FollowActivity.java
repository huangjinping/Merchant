package com.hdfex.merchantqrshow.salesman.appointment.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpDetail;
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpDetailItem;
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpItem;
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpMaterialDetailQueryResult;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageData;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageModel;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadContractResponse;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadContractResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.add.activity.PreViewAndDeleteActivity;
import com.hdfex.merchantqrshow.salesman.add.adapter.UpLoadAdapter;
import com.hdfex.merchantqrshow.utils.Constant;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.camera.compress.FileImageUtils;
import com.hdfex.merchantqrshow.utils.camera.easyphotopicker.EasyImageConfig;
import com.hdfex.merchantqrshow.view.MGridView.MGridView;
import com.hdfex.merchantqrshow.widget.picker.DateHourPicker;
import com.jhworks.library.ImageSelector;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * author Created by harrishuang on 2017/9/13.
 * email : huangjinping@hdfex.com
 */

public class FollowActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private TextView txt_desc;
    private TextView txt_result;
    private TextView txt_commodityName;
    private TextView txt_time_date;
    private User user;
    private EditText txt_remark;
    private Button btn_submit;
    private LinearLayout layout_appointment_time;
    private TextView txt_customer_time;
    private MGridView grv_lease_contract;
    private LinearLayout layout_upload_file;
    private ArrayList<String> mSelectPath;
    private ImageModel currentImageModel;
    private List<ImageModel> bankList;
    private UpLoadAdapter bankAdapter;
    private FollowUpDetail detail;
    private FollowUpDetailItem followUpDetailItem;
    private FollowUpMaterialDetailQueryResult followUpMaterialDetailQueryResult;
    private LinearLayout layout_phone_message;


    /**
     * 打开界面
     *
     * @param context
     * @param result
     */
    public static void startAction(Context context, FollowUpDetail result) {
        Intent intent = new Intent(context, FollowActivity.class);
        intent.putExtra(FollowUpDetail.class.getSimpleName(), result);
        context.startActivity(intent);
    }

    /**
     * 打开界面
     *
     * @param context
     * @param result
     */
    public static void startAction(Context context, FollowUpDetailItem result) {
        Intent intent = new Intent(context, FollowActivity.class);
        intent.putExtra(FollowUpDetailItem.class.getSimpleName(), result);
        context.startActivity(intent);
    }

    /**
     * 打开界面
     *
     * @param context
     * @param result
     */
    public static void startAction(Context context, FollowUpMaterialDetailQueryResult result) {
        Intent intent = new Intent(context, FollowActivity.class);
        intent.putExtra(FollowUpMaterialDetailQueryResult.class.getSimpleName(), result);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        initView();
        user = UserManager.getUser(this);
        Intent intent = getIntent();
        if (intent.getSerializableExtra(FollowUpDetail.class.getSimpleName()) != null) {
            detail = (FollowUpDetail) intent.getSerializableExtra(FollowUpDetail.class.getSimpleName());
            setViewByData(detail);
        }

        if (intent.getSerializableExtra(FollowUpDetailItem.class.getSimpleName()) != null) {
            followUpDetailItem = (FollowUpDetailItem) intent.getSerializableExtra(FollowUpDetailItem.class.getSimpleName());
            setViewByData(followUpDetailItem);
        }

        if (intent.getSerializableExtra(FollowUpMaterialDetailQueryResult.class.getSimpleName()) != null) {
            followUpMaterialDetailQueryResult = (FollowUpMaterialDetailQueryResult) intent.getSerializableExtra(FollowUpMaterialDetailQueryResult.class.getSimpleName());
            setViewByData(followUpMaterialDetailQueryResult);
        }

        bankList = new ArrayList<>();
        addDefoultChild(bankList, 0, "liushui");
        setOnLiteners();
        initDataUpLoad();
    }

    private void setViewByData(FollowUpDetailItem followUpDetailItem) {
        if (followUpDetailItem == null) {
            return;
        }
        layout_appointment_time.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(followUpDetailItem.getDesc())) {
            txt_desc.setText(followUpDetailItem.getDesc());
        }
        if (!TextUtils.isEmpty(followUpDetailItem.getResult())) {
            txt_result.setText(followUpDetailItem.getResult());
        }
        if (!TextUtils.isEmpty(followUpDetailItem.getCommodityName())) {
            txt_commodityName.setText(followUpDetailItem.getCommodityName());
        }
        layout_phone_message.setVisibility(View.VISIBLE);
//        if (!TextUtils.isEmpty(followUpDetailItem.getStartDate())) {
//            txt_time_date.setText(followUpDetailItem.getStartDate() + "-" + followUpDetailItem.getEndDate());
//        }
//
//        if (FollowUpItem.FOLLOWED.equals(followUpDetailItem.getStatus())) {
//            btn_submit.setVisibility(View.GONE);
//            txt_remark.setEnabled(false);
//        } else if (FollowUpItem.UN_FOLLOW.equals(detail.getStatus())) {
//            btn_submit.setVisibility(View.VISIBLE);
//            txt_remark.setEnabled(true);
//        }

        if (!TextUtils.isEmpty(followUpDetailItem.getBizRemark())) {
            txt_remark.setText(followUpDetailItem.getBizRemark());
        }
    }

    private void setViewByData(FollowUpMaterialDetailQueryResult queryResult) {
        if (queryResult == null) {
            return;
        }

        layout_appointment_time.setVisibility(View.VISIBLE);
        layout_upload_file.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(queryResult.getDesc())) {
            txt_desc.setText(queryResult.getDesc());
        }
        if (!TextUtils.isEmpty(queryResult.getResult())) {
            txt_result.setText(queryResult.getResult());
        }
        if (!TextUtils.isEmpty(queryResult.getCommodityName())) {
            txt_commodityName.setText(queryResult.getCommodityName());
        }
//        if (!TextUtils.isEmpty(detail.getStartDate())) {
//            txt_time_date.setText(detail.getStartDate() + "-" + detail.getEndDate());
//        }

//        if (FollowUpItem.FOLLOWED.equals(detail.getStatus())) {
//            btn_submit.setVisibility(View.GONE);
//            txt_remark.setEnabled(false);
//        } else if (FollowUpItem.UN_FOLLOW.equals(detail.getStatus())) {
//            btn_submit.setVisibility(View.VISIBLE);
//            txt_remark.setEnabled(true);
//        }

        if (!TextUtils.isEmpty(queryResult.getBizRemark())) {
            txt_remark.setText(queryResult.getBizRemark());
        }
    }

    /**
     * 详情赋值
     *
     * @param detail
     */
    private void setViewByData(FollowUpDetail detail) {
        if (detail == null) {
            return;
        }
        if (!TextUtils.isEmpty(detail.getDesc())) {
            txt_desc.setText(detail.getDesc());
        }
        if (!TextUtils.isEmpty(detail.getResult())) {
            txt_result.setText(detail.getResult());
        }
        if (!TextUtils.isEmpty(detail.getCommodityName())) {
            txt_commodityName.setText(detail.getCommodityName());
        }
        if (!TextUtils.isEmpty(detail.getStartDate())) {
            txt_time_date.setText(detail.getStartDate() + "-" + detail.getEndDate());
        }

        if (FollowUpItem.FOLLOWED.equals(detail.getStatus())) {
            btn_submit.setVisibility(View.GONE);
            txt_remark.setEnabled(false);
        } else if (FollowUpItem.UN_FOLLOW.equals(detail.getStatus())) {
            btn_submit.setVisibility(View.VISIBLE);
            txt_remark.setEnabled(true);
        }


        if (!TextUtils.isEmpty(detail.getBizRemark())) {
            txt_remark.setText(detail.getBizRemark());
        }
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setText("跟进详情");
        txt_desc = (TextView) findViewById(R.id.txt_desc);
        txt_result = (TextView) findViewById(R.id.txt_result);
        txt_commodityName = (TextView) findViewById(R.id.txt_commodityName);
        txt_time_date = (TextView) findViewById(R.id.txt_time_date);
        txt_remark = (EditText) findViewById(R.id.txt_remark);
        txt_remark.setOnClickListener(this);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        layout_appointment_time = (LinearLayout) findViewById(R.id.layout_appointment_time);
        layout_appointment_time.setOnClickListener(this);
        txt_customer_time = (TextView) findViewById(R.id.txt_customer_time);
        txt_customer_time.setOnClickListener(this);
        grv_lease_contract = (MGridView) findViewById(R.id.grv_lease_contract);
        layout_upload_file = (LinearLayout) findViewById(R.id.layout_upload_file);
        layout_phone_message = (LinearLayout) findViewById(R.id.layout_phone_message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_submit:
                if (followUpMaterialDetailQueryResult != null) {
                    followUpOrtherUpdate(followUpMaterialDetailQueryResult);
                } else {
                    if (detail != null) {
                        followUpUpdate(detail.getApplyId(), detail.getExamineId());
                    } else if (followUpDetailItem != null) {
                        followUpUpdate(followUpDetailItem.getApplyId(), followUpDetailItem.getExamineId());
                    }
                }
                break;
            case R.id.layout_appointment_time:
                if (followUpDetailItem != null) {
                    FollowRecordActivity.startAction(this, "00", followUpDetailItem.getApplyId(), followUpDetailItem.getExamineId());
                } else if (followUpMaterialDetailQueryResult != null) {
                    FollowRecordActivity.startAction(this, "01", followUpMaterialDetailQueryResult.getApplyId(), followUpMaterialDetailQueryResult.getExamineId());
                }
                break;
            case R.id.txt_customer_time:
                getTime(txt_customer_time);
                break;
        }
    }

    /**
     * 获取时间
     *
     * @param edittext
     */
    private void getTime(final TextView edittext) {
        DateHourPicker dateWeekPicker = new DateHourPicker(this);
        dateWeekPicker.setOnDateSelectListener(new DateHourPicker.OnDateSelectListener() {
            @Override
            public void onSelect(Calendar calendar, String data) {
                edittext.setText(data.toString());
            }
        });
        dateWeekPicker.show();
    }

    /**
     * 下载详情
     *
     * @param
     */
    private void followUpUpdate(String applyId, String examineId) {
        String remark = txt_remark.getText().toString().trim();

        if (TextUtils.isEmpty(remark)) {
            showToast("请输入备注信息");
            return;
        }

        showProgress();
        OkHttpUtils.post().addParams("id", user.getId())
                .addParams("token", user.getToken())
                .addParams("applyId", applyId)
                .addParams("remark", txt_remark.getText().toString().trim())
                .addParams("examineId", examineId)
                .url(NetConst.FOLLOW_UP_UPDATE).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                showToast(e.getMessage());
            }


            @Override
            public void onResponse(String response) {
                if (checkResonse(response)) {
                    Response queryResponse = GsonUtil.changeGsonToBean(response, Response.class);
                    showToast("提交成功");
                    EventRxBus.getInstance().post(IntentFlag.FOLLOW_LIST_UPDATE, IntentFlag.FOLLOW_LIST_UPDATE);
                    finish();
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
                    ToastUtils.makeText(FollowActivity.this, "未检测到SD卡").show();
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
                            .start(FollowActivity.this, Constant.REQUEST_IMAGE_FORM);

                    return;
                }
                openPreview(position, bankList);
            }
        });

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
        Intent intent = new Intent(FollowActivity.this, PreViewAndDeleteActivity.class);
        intent.putExtra(IntentFlag.POSITION, position - 1);
        intent.putExtra(IntentFlag.INTENT_PIC, IntentFlag.INTENT_PIC);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.PICTURE_SELECT, imageData);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constant.PHOTO_VIEW);
    }

    public static final int REQUEST_CODE_CROP_IMAGE = 0x3; //请求码 裁剪图片

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
            ToastUtils.makeText(FollowActivity.this, "图片格式或大小异常,请重新选择").show();
            return;
        }

        Luban.with(FollowActivity.this)
                .load(file)                     //传人要压缩的图片
                .setCompressListener(new OnCompressListener() { //设置回调

                    @Override
                    public void onStart() {
                        //  压缩开始前调用，可以在方法内启动 loading UI
                        ToastUtils.d(FollowActivity.this, "正在处理").show();

                    }

                    @Override
                    public void onSuccess(File file) {
//    压缩成功后调用，返回压缩后的图片文件

                        upLoadURL(file, postion, imagetype);

                    }

                    @Override
                    public void onError(Throwable e) {
                        //  当压缩过去出现问题时调用
                        ToastUtils.d(FollowActivity.this, "图片处理异常").show();
                    }
                }).launch();    //启动压缩


    }


    private String type = null;

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

                ToastUtils.makeText(FollowActivity.this, e.getMessage()).show();
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
                        ToastUtils.e(FollowActivity.this, bussinessNameRepponse.getMessage() + "").show();
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
     * 上传图片
     */
    private void followUpOrtherUpdate(FollowUpMaterialDetailQueryResult followUpMaterialDetailQueryResult) {
        StringBuffer buffer = new StringBuffer();
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
        OkHttpUtils
                .post()
                .url(NetConst.FOLLOW_UP_ORTHER_UPDATE)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("applyId", followUpMaterialDetailQueryResult.getApplyId())
                .addParams("examineId", followUpMaterialDetailQueryResult.getExamineId())
                .addParams("remark", txt_remark.getText().toString().trim())
                .addParams("files", buffer.toString())
                .build()
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
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showWebEirr(e);
                        }
                    }
                });
    }
}

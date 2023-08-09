package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.salesman.add.adapter.UpLoadAdapter;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageData;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageModel;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadContractResponse;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadContractResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.UpLoadSendContract;
import com.hdfex.merchantqrshow.mvp.presenter.UpLoadSendPersenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.Constant;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.camera.compress.FileImageUtils;
import com.hdfex.merchantqrshow.utils.camera.easyphotopicker.EasyImageConfig;
import com.hdfex.merchantqrshow.view.MGridView.MGridView;
import com.jhworks.library.ImageSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by harrishuang on 2017/4/7.
 */

public class UpLoadSendActivity extends BaseActivity implements UpLoadSendContract.View, View.OnClickListener {
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3; //请求码 裁剪图片
    private ImageView img_back;
    private TextView txt_left_name;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private MGridView grv_lease_contract;
    private ImageModel currentImageModel;
    private ArrayList<String> mSelectPath;
    private List<ImageModel> bankList;
    private User user;
    private UpLoadAdapter bankAdapter;
    private UpLoadSendContract.Presenter presenter;
    private String applyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadsend);
        initView();
        presenter = new UpLoadSendPersenter();
        presenter.attachView(this);
        setOnLiteners();
        init();

    }

    private void init() {
        applyId=getIntent().getStringExtra("applyId");
        bankList = new ArrayList<>();
        user = UserManager.getUser(this);
        addDefoultChild(bankList, 0, "liushui");
        initDataUpLoad();
    }


    /**
     * 初始化数据
     */
    private void initDataUpLoad() {
        bankAdapter = new UpLoadAdapter(bankList, this);
        grv_lease_contract.setAdapter(bankAdapter);
    }


    private void addDefoultChild(List<ImageModel> bankList, int row, String name) {
        for (int i = 0; i < 1; i++) {
            ImageModel imageModel = new ImageModel(i, row, name);
            bankList.add(imageModel);
        }
    }

    private void setOnLiteners() {
        currentImageModel = UserManager.currentImageModel;
        grv_lease_contract.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!sdcardExist()) {
                    ToastUtils.makeText(UpLoadSendActivity.this, "未检测到SD卡").show();
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
                            .start(UpLoadSendActivity.this, Constant.REQUEST_IMAGE_FORM);

                    return;
                }
                openPreview(position, bankList);
            }
        });

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
        Intent intent = new Intent(UpLoadSendActivity.this, PreViewAndDeleteActivity.class);
        intent.putExtra(IntentFlag.POSITION, position - 1);
        intent.putExtra(IntentFlag.INTENT_PIC, IntentFlag.INTENT_PIC);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.PICTURE_SELECT, imageData);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constant.PHOTO_VIEW);
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        txt_left_name = (TextView) findViewById(R.id.txt_left_name);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setText("图片上传");
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        tv_home = (TextView) findViewById(R.id.tv_home);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        grv_lease_contract = (MGridView) findViewById(R.id.grv_lease_contract);
        img_back.setOnClickListener(this);
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
            ToastUtils.makeText(UpLoadSendActivity.this, "图片格式或大小异常,请重新选择").show();
            return;
        }

        Luban.with(UpLoadSendActivity.this)
                .load(file)                     //传人要压缩的图片
                .setCompressListener(new OnCompressListener() { //设置回调

                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        ToastUtils.d(UpLoadSendActivity.this, "正在处理").show();

                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件

                        upLoadURL(file, postion, imagetype);

                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过去出现问题时调用
                        ToastUtils.d(UpLoadSendActivity.this, "图片处理异常").show();
                    }
                }).launch();    //启动压缩


    }


    String type;

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

                ToastUtils.makeText(UpLoadSendActivity.this, e.getMessage()).show();
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
                        ToastUtils.e(UpLoadSendActivity.this, bussinessNameRepponse.getMessage() + "").show();
                    }
                } catch (Exception e) {
                    dismissProgress();
                    e.printStackTrace();
                }
            }
        });
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.img_back:
                onBackPressed();
                break;
        }
    }

    public void onSend(View view) {
        presenter.submitSendInfo(user, applyId, bankList);

    }

    @Override
    public void onSuccess() {
        this.finish();
    }

    public static void start(Context context, String applyId) {
        Intent intent = new Intent(context, UpLoadSendActivity.class);
        intent.putExtra("applyId",applyId);
        context.startActivity(intent);
    }
}

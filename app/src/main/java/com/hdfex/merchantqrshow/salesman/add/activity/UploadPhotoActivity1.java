package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.salesman.add.adapter.UpLoadAdapter;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageModel;
import com.hdfex.merchantqrshow.bean.salesman.commodity.Pic;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadContractResponse;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadInfo;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadInfoResponse;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.CreateSeri;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.Constant;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.StringUtils;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.camera.EasyImage;
import com.hdfex.merchantqrshow.utils.camera.compress.FileImageUtils;
import com.hdfex.merchantqrshow.utils.camera.compress.Util;
import com.hdfex.merchantqrshow.utils.camera.easyphotopicker.DefaultCallback;
import com.hdfex.merchantqrshow.utils.camera.easyphotopicker.EasyImageConfig;
import com.hdfex.merchantqrshow.utils.log.LogUtil;
import com.hdfex.merchantqrshow.view.MGridView.MGridView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 上传图片
 * Created by harrishuang on 16/9/5.
 */

public class UploadPhotoActivity1 extends BaseActivity implements View.OnClickListener {
    private UpLoadAdapter bankAdapter;
    private List<ImageModel> bankList;
    private ImageModel currentImageModel;
    private File mFileTemp;  //临时图片
    private UpLoadAdapter proveAdapter;
    private List<ImageModel> proveList;
    //    private String userID = "";
    private User user;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private LinearLayout layout_bottom;
    private MGridView grv_lease_contract;
    private MGridView grv_agency_contract;
    private TextView tv_add_more;
    private CreateSeri createSeri;


    protected void init() {
        tb_tv_titile.setText("上传合同信息");
        bankList = new ArrayList<>();
        addDefoultChild(bankList, 0, "liushui");
        proveList = new ArrayList<>();
        addDefoultChild(proveList, 1, "zichan");
        Intent comIntent = getIntent();
        Serializable serializable = comIntent.getExtras().getSerializable(IntentFlag.SERIALIZABLE);
        createSeri = (CreateSeri) serializable;
    }


    private void updateView() {
        bankAdapter = new UpLoadAdapter(bankList, this);
        grv_lease_contract.setAdapter(bankAdapter);
        proveAdapter = new UpLoadAdapter(proveList, this);
        grv_agency_contract.setAdapter(proveAdapter);
    }

    private void addDefoultChild(List<ImageModel> bankList, int row, String name) {
        for (int i = 0; i < 1; i++) {
            ImageModel imageModel = new ImageModel(i, row, name);
            bankList.add(imageModel);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadphoto);
//        user = new User();
//        user.setToken("1iycp09ympkc3");
//        user.setId("549216");
//        UserManager.saveUser(user);
        catchpath = getCacheDir().getAbsolutePath();
//        openChooseraByPermission();
        initView();
        user = UserManager.getUser(this);
//        userID = user.getId();
        setOnLiteners();
        init();
//        getPic();
        updateView();

    }


    /**
     * 上传文件方法
     *
     * @param file
     */
    private void upLoadURL(final File file) {
        String type = null;
        /**
         * 1 租赁合同, 2 代理合同
         */
        if (currentImageModel != null) {
            if (currentImageModel.getRow() == 0) {
                type = "1";
            } else {
                type = "2";
            }
        }
        OkHttpUtils.post()
                .url(NetConst.UPLOAD_CONTRACT)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("packageId", createSeri.getCommodityCreateInfo().getPackageId())
                .addParams("type", type)//暂时写死
                .addFile("imgFile", file.getName() + ".jpg", file).build()
                .connTimeOut(10000)
                .readTimeOut(10000)
                .writeTimeOut(10000).execute(new StringCallback() {
            @Override
            public void onAfter() {
                Log.d("hjp", "onAfter");
                dismissProgress();
            }

            @Override
            public void onBefore(Request request) {
                Log.d("hjp", "");
                showProgress();
            }

            @Override
            public void onError(Call call, Exception e) {
                Log.d("hjp", e.getMessage());
                ToastUtils.makeText(UploadPhotoActivity1.this, e.getMessage()).show();
            }

            @Override
            public void onResponse(String response) {
                LogUtil.e("okhttp", "" + response);
                UploadContractResponse bussinessNameRepponse = new UploadContractResponse();
                bussinessNameRepponse = GsonUtil.changeGsonToBean(response, UploadContractResponse.class);
                if (bussinessNameRepponse.getCode() == 0 && bussinessNameRepponse.getResult() != null && !TextUtils.isEmpty(bussinessNameRepponse.getResult().getPicId())) {
                    updateView(file, bussinessNameRepponse.getResult().getPicId());
                    /**
                     * 去哪里
                     */
                } else {

                    ToastUtils.e(UploadPhotoActivity1.this, bussinessNameRepponse.getMessage() + "").show();
                }


            }
        });

    }


    private void removeView(ImageModel imageModel) {

        switch (imageModel.getRow()) {
            case 0:
                bankList.remove(imageModel.getPosition());

                for (int i = 0; i < bankList.size(); i++) {
                    bankList.get(i).setPosition(i);
                }
                updateNewChild(bankList, bankAdapter);
                bankAdapter.notifyDataSetChanged();
                break;
            case 1:
                proveList.remove(imageModel.getPosition());
                for (int i = 0; i < proveList.size(); i++) {
                    proveList.get(i).setPosition(i);
                }
                updateNewChild(proveList, proveAdapter);
                proveAdapter.notifyDataSetChanged();
                break;
            case 2:

                break;
        }


        saveSelect();
    }


    /**
     * 获取图片更新UI接口
     *
     * @param imageFile
     */
    private void updateView(File imageFile, String picId) {


        Log.d("hjp", "========picId===" + picId);
        currentImageModel.setPicId(picId);
        switch (currentImageModel.getRow()) {
            case 0:
                bankList.get(currentImageModel.getPosition()).setImageFile(imageFile);
                bankAdapter.notifyDataSetChanged();
                updateNewChild(bankList, bankAdapter);

                break;
            case 1:
                proveList.get(currentImageModel.getPosition()).setImageFile(imageFile);
                proveAdapter.notifyDataSetChanged();
                updateNewChild(proveList, proveAdapter);

                break;

        }

        saveSelect();

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
            ImageModel imagemode = new ImageModel(list.size(), currentImageModel.getRow(), currentImageModel.getName());
            list.add(imagemode);
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


    int row;
    int postion;

    /**
     * 设置监听器
     */
    private void setOnLiteners() {
        currentImageModel = UserManager.currentImageModel;

        grv_lease_contract.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!sdcardExist()) {
                    ToastUtils.makeText(UploadPhotoActivity1.this, "未检测到SD卡").show();
                    return;
                }
                currentImageModel = bankList.get(position);
                if (TextUtils.isEmpty(currentImageModel.getPath()) && currentImageModel.getImageFile() == null) {
                    UserManager.currentImageModel = currentImageModel;
                    saveSelect();

//                    EasyImage.openCamera(UploadPhotoActivity.this);
                    EasyImage.openChooser(UploadPhotoActivity1.this, "选择图片来源");


                    return;
                }
                Intent intent = new Intent(UploadPhotoActivity1.this, PhotoViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PhotoViewActivity.INTENT_KEY, currentImageModel);
                intent.putExtras(bundle);
                startActivityForResult(intent, Constant.PHOTO_VIEW);

            }
        });
        grv_agency_contract.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!sdcardExist()) {
                    ToastUtils.makeText(UploadPhotoActivity1.this, "未检测到SD卡").show();
                    return;
                }
                currentImageModel = proveList.get(position);
                if (TextUtils.isEmpty(currentImageModel.getPath()) && currentImageModel.getImageFile() == null) {
                    UserManager.currentImageModel = currentImageModel;
                    saveSelect();
                    EasyImage.openChooser(UploadPhotoActivity1.this, "选择图片来源");
                    return;
                }

                Intent intent = new Intent(UploadPhotoActivity1.this, PhotoViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PhotoViewActivity.INTENT_KEY, currentImageModel);
                intent.putExtras(bundle);
                startActivityForResult(intent, Constant.PHOTO_VIEW);


            }
        });
    }

    /**
     * 选择
     */
    private void saveSelect() {
//        UploadData upLoadData = UserManager.getUpLoadData();
//        if (upLoadData == null) {
//            upLoadData = new UploadData();
//        }
//        upLoadData.setBankList(bankList);
//        upLoadData.setProveList(proveList);
//        upLoadData.setOtherList(otherList);
//        upLoadData.setCurrentImageModel(currentImageModel);
//        UserManager.saveUpLoadData(upLoadData);
    }


    public static final int REQUEST_CODE_CROP_IMAGE = 0x3; //请求码 裁剪图片

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        Log.d("okhttp", requestCode + "====onActivityResult=========" + resultCode);


        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CROP_IMAGE:

                onPhotoReturned(mFileTemp);
                break;

            case EasyImageConfig.REQ_PICK_PICTURE_FROM_DOCUMENTS:
            case EasyImageConfig.REQ_PICK_PICTURE_FROM_GALLERY:
            case EasyImageConfig.REQ_SOURCE_CHOOSER:
            case EasyImageConfig.REQ_TAKE_PICTURE:
                processEasyImage(requestCode, resultCode, data);
                break;
            case Constant.PHOTO_VIEW:

                Bundle extras = data.getExtras();
                ImageModel imageModel = (ImageModel) extras.getSerializable(PhotoViewActivity.INTENT_KEY);
                if (!TextUtils.isEmpty(imageModel.getPath())) {
//                    getPic();
                } else {
                    removeView(imageModel);
                }
                break;
            default:
                break;
        }

    }


    private void onPhotoReturned(File imageFile) {
//          Log.d("hjp",
//                  "========》》》》》》》》》》"+imageFile.getAbsolutePath().toString());
        if (currentImageModel == null) {
            return;
        }

//        currentImageModel.setImageFile(imageFile);

        upLoadURL(imageFile);


//        Glide.with(UploadPhotoActivity.this).load(imageFile).into(currentImageModel);


    }

    public static final int MAX_NUM_OF_REPAIR = 800 * 800;

    private static String catchpath;

    /**
     * 处理图片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void processEasyImage(int requestCode, int resultCode, Intent data) {
        EasyImage.handleActivityResult(requestCode, resultCode, data, UploadPhotoActivity1.this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source) {

                Log.d("hjp", e.toString());
                ToastUtils.i(UploadPhotoActivity1.this, "拍照过程中遇到问题" + e.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source) {
                mFileTemp = imageFile;
                //剪切 图片
                Bitmap zoombitmapBitmap = Util.zoombitmapBitmap(
                        imageFile.getAbsolutePath(), MAX_NUM_OF_REPAIR);
                Log.d("hjp", "==剪切===" + zoombitmapBitmap.getRowBytes());
                FileImageUtils.saveCatchBitmap(StringUtils.md5(mFileTemp.getName()), zoombitmapBitmap, UploadPhotoActivity1.this, catchpath);
                File bitmap = FileImageUtils.getFile(catchpath + "/" + StringUtils.md5(mFileTemp.getName()));

                onPhotoReturned(bitmap);
//                startCropImage(imageFile);
                LogUtil.d(UploadPhotoActivity1.class.getSimpleName(), imageFile.toString());
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source) {
                ToastUtils.i(UploadPhotoActivity1.this, "拍照过程中遇到问题onCanceled", Toast.LENGTH_LONG).show();
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(UploadPhotoActivity1.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }


    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setOnClickListener(this);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        iv_setting.setOnClickListener(this);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_home.setOnClickListener(this);

        layout_bottom = (LinearLayout) findViewById(R.id.layout_bottom);
        layout_bottom.setOnClickListener(this);
        grv_lease_contract = (MGridView) findViewById(R.id.grv_lease_contract);
        grv_agency_contract = (MGridView) findViewById(R.id.grv_agency_contract);
        tv_add_more = (TextView) findViewById(R.id.tv_add_more);
        tv_add_more.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_add_more:
                Intent intent = new Intent(this, SelectionPicActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
    }


    /**
     *
     */
    private void getPic() {

        if (connect()) {
            User user = UserManager.getUser(this);
            OkHttpUtils.post()
                    .url("http://123.56.136.151/hd-merchant-web/mobile/customInfo/pictureInfo")
                    .addParams("token", user.getToken())
                    .addParams("id", user.getId())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {

                        }

                        @Override
                        public void inProgress(float progress) {
                            super.inProgress(progress);

                        }

                        @Override
                        public void onBefore(Request request) {
                            super.onBefore(request);
                            showProgress();
                        }

                        @Override
                        public void onAfter() {
                            super.onAfter();
                            dismissProgress();
                        }

                        @Override
                        public void onResponse(String response) {
                            try {
                                boolean b = checkResonse(response);
                                if (b) {
                                    UploadInfoResponse info = GsonUtil.changeGsonToBean(response, UploadInfoResponse.class);
                                    Log.d("hjp", "info" + info.toString());
                                    Log.d("okhttp", "info" + info.toString());
                                    if (info.getCode() == 0) {
                                        getResponse(info.getResult());
                                        updateView();
                                    } else {
                                        ToastUtils.i(UploadPhotoActivity1.this, "获取初始化失败").show();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }

    }


    /**
     * 获取信息接口
     */
    private void getResponse(UploadInfo result) {

        if (result == null) {
            return;
        }

        if (result.getLiushui() == null) {
        } else {
            List<Pic> liushuiList = result.getLiushui();
            bankList.clear();
            for (int i = 0; i < liushuiList.size(); i++) {
                Pic pic = liushuiList.get(i);
                ImageModel imageModel = new ImageModel(i, 0, "liushui");
                imageModel.setPath(pic.getPicUrl());
                imageModel.setPicId(pic.getPicId());

                bankList.add(imageModel);
            }
            ImageModel imageModel = new ImageModel(bankList.size(), 0, "liushui");
            bankList.add(imageModel);

        }


        if (result.getZichan() == null) {
        } else {
            List<Pic> zichan = result.getZichan();
            proveList.clear();
            for (int i = 0; i < zichan.size(); i++) {
                Pic pic = zichan.get(i);
                ImageModel imageModel = new ImageModel(i, 1, "zichan");
                imageModel.setPath(pic.getPicUrl());
                imageModel.setPicId(pic.getPicId());

                proveList.add(imageModel);
            }
            ImageModel imageModel = new ImageModel(proveList.size(), 1, "zichan");
            proveList.add(imageModel);

        }


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
        if (proveList.size() < 2) {
            ToastUtils.d(getApplicationContext(), "请上传代理合同").show();
            return false;
        }
        return true;

    }

    /**
     * 展示图片
     *
     * @param view
     */
    public void onPriview(View view) {
        if (!checkPhoto()) {
            return;
        }
        Intent intent = new Intent(this, PreviewActivity.class);
        createSeri.setRentModelLists(bankList);
        createSeri.setAgencyModelLists(proveList);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.SERIALIZABLE, createSeri);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 生成二维码
     *
     * @param view
     */
    public void onCreatrQRCode(View view) {
        if (!checkPhoto()) {
            return;
        }
        Intent intent = new Intent(this, QRCodeProduceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.SERIALIZABLE, createSeri);
        intent.putExtras(bundle);
        intent.putExtra(IntentFlag.CONFORM, "UploadPhotoActivity");
        startActivity(intent);
    }
}



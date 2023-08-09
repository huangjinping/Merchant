package com.hdfex.merchantqrshow.salesman.add.activity;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
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
import com.hdfex.merchantqrshow.bean.salesman.commodity.Contract;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageData;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageModel;
import com.hdfex.merchantqrshow.bean.salesman.commodity.Pic;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadContractResponse;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadContractResult;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadData;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadInfo;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.CommodityCreateInfo;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.CommodityCreateModel;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.CommodityCreateResponse;
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
import com.hdfex.merchantqrshow.utils.dexter.OnPermissionListener;
import com.hdfex.merchantqrshow.utils.dexter.SampleMultipleBackgroundPermissionListener;
import com.hdfex.merchantqrshow.utils.log.LogUtil;
import com.hdfex.merchantqrshow.view.MGridView.MGridView;
import com.jhworks.library.ImageSelector;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;

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

public class UploadPhotoNewActivity extends BaseActivity implements View.OnClickListener {
    private UpLoadAdapter bankAdapter;
    private List<ImageModel> bankList;
    private ImageModel currentImageModel;
    private File mFileTemp;  //临时图片
    private UpLoadAdapter proveAdapter;
    private List<ImageModel> proveList;
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
    private ArrayList<String> mSelectPath;


    protected void init() {
        addDefoultChild(bankList, 0, "liushui");
        addDefoultChild(proveList, 1, "zichan");

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
        bankList = new ArrayList<>();
        proveList = new ArrayList<>();
        catchpath = getCacheDir().getAbsolutePath();
        openChooseraByPermission();
        initView();
        tb_tv_titile.setText("上传合同信息");
        user = UserManager.getUser(this);
        setOnLiteners();
        Intent comIntent = getIntent();
        Serializable serializable = comIntent.getExtras().getSerializable(IntentFlag.SERIALIZABLE);
        createSeri = (CreateSeri) serializable;

        UploadData upLoadPic = UserManager.getUpLoadPic(this);
        if (upLoadPic == null) {
            init();
        } else {
            if (upLoadPic.getBankList() != null) {
                bankList.addAll(upLoadPic.getBankList());
            }
            if (upLoadPic.getProveList() != null) {
                proveList.addAll(upLoadPic.getProveList());
            }
        }
        updateView();

    }
    String type = null;

    /**
     * 上传文件方法
     *
     * @param file
     */
    private void upLoadURL(final File file, final int postion, final int imagetype) {

        if (!connect()){
            return;
        }

        /**
         *
         *
         *
         * 判断文件是不是0temp 文件
         */
        if (!FileImageUtils.isFileChack(file)) {
            ToastUtils.makeText(UploadPhotoNewActivity.this, "图片格式或大小异常,请重新选择").show();
            return;
        }
        File temp = FileImageUtils.compressByFile(file, this);

        /**
         * 1 租赁合同, 2 代理合同
         */
        if (Constant.REQUEST_IMAGE_FORM == imagetype) {
            type = "1";
        } else {
            type = "2";
        }
        currentImageModel.setType(type);
        updateView(file, null, null, true,type);
        OkHttpUtils.post()
                .url(NetConst.UPLOAD_CONTRACT)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("type", type)
                .addFile("imgFile", file.getName() + ".jpg", temp).build()
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

                ToastUtils.makeText(UploadPhotoNewActivity.this, e.getMessage()).show();
            }

            @Override
            public void onResponse(String response) {

                try {
                    UploadContractResponse bussinessNameRepponse = new UploadContractResponse();
                    bussinessNameRepponse = GsonUtil.changeGsonToBean(response, UploadContractResponse.class);
                    if (bussinessNameRepponse.getCode() == 0 && bussinessNameRepponse.getResult() != null) {
                        UploadContractResult result = bussinessNameRepponse.getResult();
                        updateView(file, result.getRelativePath(), result.getRelativePath(), false,type);
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
                        ToastUtils.e(UploadPhotoNewActivity.this, bussinessNameRepponse.getMessage() + "").show();
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
    private void removeEnd(int row){
        switch (row){
            case 0:
                bankList.remove(bankList.size()-1);
                bankAdapter.notifyDataSetChanged();
                break;
            case 1:
                proveList.remove(proveList.size()-1);
                proveAdapter.notifyDataSetChanged();
                break;

        }
    }



    /**
     * 刷新子进度
     *
     * @param gridView
     * @param adapter
     */
    private void updateChild(MGridView gridView, UpLoadAdapter adapter, String progress,String title) {
        View view = gridView.getChildAt(1);
        UpLoadAdapter.ViewHolder viewHolder = (UpLoadAdapter.ViewHolder) view.getTag();
        viewHolder.txt_progress.setText(progress);
        viewHolder.txt_progress_title.setText(title);
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

                    updateChild(grv_lease_contract, bankAdapter,String.valueOf(proInt+"%"),"图片上传中");
                } else {
                    updateChild(grv_lease_contract, bankAdapter,"","");
                }


                break;
            case 1:
                if (proInt > 0 && proInt < 100) {
                    updateChild(grv_agency_contract, proveAdapter,String.valueOf(proInt+"%"),"图片上传中");

                } else {
                    updateChild(grv_agency_contract, proveAdapter,"","");

                }


                break;
        }

    }

    /**
     * 处理数据问题
     *
     * @param imageModel
     */
    private void setDataForAdapter(ImageModel imageModel) {
        switch (imageModel.getRow()) {
            case 0:
                bankList.remove(imageModel.getPosition());

                bankAdapter.notifyDataSetChanged();
                break;
            case 1:
                proveList.remove(imageModel.getPosition());

                proveAdapter.notifyDataSetChanged();
                break;
            case 2:

                break;
        }


        saveSelect();
    }



    /**
     * 处理数据问题
     *
     * @param imageModelList
     */
    private void removeViewList(List<ImageModel> imageModelList,int rowType) {
        if (imageModelList==null){
            return;
        }

        switch (rowType) {
            case 0:

                ImageModel imageModel = bankList.get(0);
                bankList.clear();
                bankList.add(imageModel);

                bankList.addAll(imageModelList);
//                for (int i = 0; i < bankList.size(); i++) {
//                    bankList.get(i).setPosition(i);
//                }
//                updateNewChild(bankList, bankAdapter);
                bankAdapter.notifyDataSetChanged();

                break;
            case 1:
                ImageModel imagePro = proveList.get(0);
                proveList.clear();
                proveList.add(imagePro);
                proveList.addAll(imageModelList);

//              updateNewChild(proveList, proveAdapter);
                proveAdapter.notifyDataSetChanged();
                break;
            case 2:

                break;
        }



    }




    /**
     * 处理数据问题
     *
     * @param imageModel
     */
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



    }


    /**
     * 获取图片更新UI接口
     *
     * @param imageFile
     */
    private void updateView(File imageFile, String relativePath, String absolutePath, boolean isAddChild,String type) {

//        currentImageModel.setRelativePath(relativePath);
//        currentImageModel.setAbsolutePath(absolutePath);

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
            case 1:

                if (isAddChild) {
                    proveList.get(currentImageModel.getPosition()).setImageFile(imageFile);
                    proveAdapter.notifyDataSetChanged();
                    updateNewChild(proveList, proveAdapter);
                } else {
                    proveList.get(currentImageModel.getPosition() + 1).setImageFile(imageFile);
                    proveList.get(currentImageModel.getPosition() + 1).setRelativePath(relativePath);
                    proveList.get(currentImageModel.getPosition() + 1).setAbsolutePath(absolutePath);
                    proveList.get(currentImageModel.getPosition() + 1).setAbsolutePath(absolutePath);
                    proveList.get(currentImageModel.getPosition() + 1).setType(type);
                    proveAdapter.notifyDataSetChanged();
                }

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
                    ToastUtils.makeText(UploadPhotoNewActivity.this, "未检测到SD卡").show();
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
                            .start(UploadPhotoNewActivity.this, Constant.REQUEST_IMAGE_FORM);

                    return;
                }
                openPreview(position,bankList);
            }
        });
        grv_agency_contract.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!sdcardExist()) {
                    ToastUtils.makeText(UploadPhotoNewActivity.this, "未检测到SD卡").show();
                    return;
                }
                currentImageModel = proveList.get(position);
                if (TextUtils.isEmpty(currentImageModel.getPath()) && currentImageModel.getImageFile() == null) {
                    UserManager.currentImageModel = currentImageModel;
                    ImageSelector selector = ImageSelector.create();
                    selector.origin(null)
                            .showCamera(true)
                            .openCameraOnly(false)
                            .count(9)
                            .spanCount(4)
                            .start(UploadPhotoNewActivity.this, Constant.REQUEST_IMAGE_TOM);
                    return;
                }
                openPreview(position,proveList);
            }
        });
    }

    /**
     * 打开预览及消除
     * @param position
     * @param imageModelList
     */
    private void openPreview(int position,List<ImageModel> imageModelList ){
        ImageData imageData=new ImageData();
        List<ImageModel> list=new ArrayList<ImageModel>();
        list.addAll(imageModelList);
        list.remove(0);
        imageData.setImageModelList(list);
        Intent intent = new Intent(UploadPhotoNewActivity.this, PreViewAndDeleteActivity.class);
        intent.putExtra(IntentFlag.POSITION,position-1);
        intent.putExtra(IntentFlag.INTENT_PIC,IntentFlag.INTENT_PIC);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.PICTURE_SELECT, imageData);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constant.PHOTO_VIEW);
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
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constant.REQUEST_IMAGE_FORM:
                mSelectPath = data.getStringArrayListExtra(ImageSelector.EXTRA_RESULT);
                upLoadURL(FileImageUtils.getFile(mSelectPath.get(0)), 0, Constant.REQUEST_IMAGE_FORM);
                break;
            case Constant.REQUEST_IMAGE_TOM:
                mSelectPath = data.getStringArrayListExtra(ImageSelector.EXTRA_RESULT);
                upLoadURL(FileImageUtils.getFile(mSelectPath.get(0)), 0, Constant.REQUEST_IMAGE_TOM);
                break;
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
                ImageData imageData = (ImageData) extras.getSerializable(IntentFlag.INTENT_PIC);
                removeViewList(imageData.getImageModelList(),extras.getInt(IntentFlag.INTENT_ROW_TYPE));
                break;
            default:
                break;
        }
    }


    private void onPhotoReturned(File imageFile) {
        if (currentImageModel == null) {
            return;
        }

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

        EasyImage.handleActivityResult(requestCode, resultCode, data, UploadPhotoNewActivity.this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source) {

                Log.d("hjp", e.toString());
                ToastUtils.i(UploadPhotoNewActivity.this, "拍照过程中遇到问题" + e.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source) {
                mFileTemp = imageFile;
                //剪切 图片
                Bitmap zoombitmapBitmap = Util.zoombitmapBitmap(
                        imageFile.getAbsolutePath(), MAX_NUM_OF_REPAIR);
                FileImageUtils.saveCatchBitmap(StringUtils.md5(mFileTemp.getName()), zoombitmapBitmap, UploadPhotoNewActivity.this, catchpath);
                File bitmap = FileImageUtils.getFile(catchpath + "/" + StringUtils.md5(mFileTemp.getName()));
                onPhotoReturned(bitmap);
                LogUtil.d(UploadPhotoNewActivity.class.getSimpleName(), imageFile.toString());
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source) {
                ToastUtils.i(UploadPhotoNewActivity.this, "拍照过程中遇到问题onCanceled", Toast.LENGTH_LONG).show();
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(UploadPhotoNewActivity.this);
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
                savePic();
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
        savePic();
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
    public void onQRCode(View view) {
        if (!checkPhoto()) {
            return;
        }
        toNextPage();
    }

    @Override
    public void onBackPressed() {
        savePic();
        finish();
    }

    /**
     * 保存图片信息
     */
    private void savePic() {
        UploadData upload = new UploadData();
        upload.setBankList(bankList);
        upload.setProveList(proveList);
        UserManager.saveUploadPic(this, upload);
    }

    /**
     * 下一步
     */
    private void toNextPage() {
        CommodityCreateModel createModel = createSeri.getCommodityCreateModel();
        if (connect()) {
            OkHttpUtils.post()
                    .url(NetConst.COMMODITY_CREATE)
                    .addParams("token", user.getToken())
                    .addParams("id", user.getId())
                    .addParams("bussinessId", user.getBussinessId())
                    .addParams("bussinessCustName", user.getName())
                    .addParams("commodityName", createModel.getCommodityName())
                    .addParams("addrProvince", createModel.getAddrProvince())
                    .addParams("addrCounty", createModel.getAddrCounty())
                    .addParams("addrArea", createModel.getAddrArea())
                    .addParams("addrDetail", createModel.getAddrDetail())
                    .addParams("name", createModel.getName())
                    .addParams("phone", createModel.getPhone())
                    .addParams("startDate", createModel.getStartDate())
                    .addParams("endDate", createModel.getEndDate())
                    .addParams("downpaymentType", createModel.getTypeModeCode())
                    .addParams("monthRent", createModel.getMonthRent())
                    .addParams("duration", createModel.getDurationCode())
                    .addParams("files", getPicList())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            showWebEirr();
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

                            analysis(response);
                        }
                    });
        }
    }

    private void analysis(String response) {
        try {
            boolean b = checkResonse(response);
            if (b) {
                CommodityCreateResponse commodityCreateResponse = GsonUtil.changeGsonToBean(response, CommodityCreateResponse.class);
                CommodityCreateInfo info = commodityCreateResponse.getResult();
                Intent intent = new Intent(this, QRCodeProduceActivity.class);
                Bundle bundle = new Bundle();
                createSeri.setCommodityCreateInfo(info);
                bundle.putSerializable(IntentFlag.SERIALIZABLE, createSeri);
                intent.putExtras(bundle);
                intent.putExtra(IntentFlag.CONFORM, "UploadPhotoActivity");
                startActivity(intent);

            }
        } catch (Exception e) {
            e.printStackTrace();
            showWebEirr(e);
        }
    }


    /**
     * 获取数据
     *
     * @return
     */
    private String getPicList() {
        List<Contract> dataList = new ArrayList<>();
        for (ImageModel model : bankList) {
            if (!TextUtils.isEmpty(model.getAbsolutePath()) && !TextUtils.isEmpty(model.getRelativePath())) {
                Contract contrct = new Contract();
                contrct.setFileType(model.getType());
                contrct.setFilePath(model.getRelativePath());
                dataList.add(contrct);
            }
        }
        for (ImageModel model : proveList) {
            if (!TextUtils.isEmpty(model.getAbsolutePath()) && !TextUtils.isEmpty(model.getRelativePath())) {
                Contract contrct = new Contract();
                contrct.setFileType(model.getType());
                contrct.setFilePath(model.getRelativePath());
                dataList.add(contrct);
            }
        }
        String gsonString = GsonUtil.createGsonString(dataList);
        return gsonString;
    }


    /**
     * 权限监听器
     */
    SampleMultipleBackgroundPermissionListener multiplePermissionListener = new SampleMultipleBackgroundPermissionListener(new OnPermissionListener() {
        @Override
        public void showPermissionGranted(String permission) {

        }

        @Override
        public void showPermissionDenied(String permission, boolean isPermanentlyDenied) {


            showPermissDeniedAlert();
        }

        @Override
        public void showPermissionRationale(PermissionToken token) {


            token.continuePermissionRequest();
        }
    });

    /**
     * 初始化权限
     */
    private void openChooseraByPermission() {
        Log.d("hjp", "initPermission");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Dexter.isRequestOngoing()) {
                return;
            }
            Log.d("hjp", "initPermission");

            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Dexter.checkPermissionsOnSameThread(multiplePermissionListener, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }.start();

        }
    }

}



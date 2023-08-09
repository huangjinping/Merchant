package com.hdfex.merchantqrshow.admin.business.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.admin.business.adapter.AdminAdapter;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageData;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageModel;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadContractResponse;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadContractResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.add.adapter.UpLoadAdapter;
import com.hdfex.merchantqrshow.utils.Constant;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.camera.compress.FileImageUtils;
import com.hdfex.merchantqrshow.utils.camera.easyphotopicker.EasyImageConfig;
import com.hdfex.merchantqrshow.utils.log.LogUtil;
import com.hdfex.merchantqrshow.view.MGridView.MGridView;
import com.hdfex.merchantqrshow.widget.HDAlertDialog;
import com.jhworks.library.ImageSelector;
import com.nineoldandroids.animation.ObjectAnimator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * author Created by harrishuang on 2017/12/26.
 * email : huangjinping@hdfex.com
 */

public class ExpandBusinessActivity extends BaseActivity implements View.OnClickListener {
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3; //请求码 裁剪图片

    private RelativeLayout layout_taobao;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private LinearLayout layout_rootView;
    private MGridView grid_pic_view;
    private AdminAdapter bankAdapter;
    private List<ImageModel> bankList;
    private ImageModel currentImageModel;
    private ArrayList<String> mSelectPath;
    private User user;



    public static void startAction(Context context) {
        Intent intent = new Intent(context, ExpandBusinessActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_business);
        user=UserManager.getUser(this);
        initView();
        initViewStatus(layout_rootView);
        bankList = new ArrayList<>();
        addDefoultChild(bankList, 0, "lishi");
        bankAdapter = new AdminAdapter(bankList, this);
        grid_pic_view.setAdapter(bankAdapter);
        setOnLiteners();

        showAlert(IntentFlag.EXTENDS_BUSINESS);
    }
    /**
     * 提交数据
     * @param
     * @param smsBody
     */
    protected void showAlert(final String smsBody) {

        LogUtil.d("okhttp",smsBody);
        final HDAlertDialog dialog1 = HDAlertDialog.getInstance(this)
                .withTitle("收款账户信息说明")
                .withHtmlMsg(smsBody);
        dialog1.withButton1Onclick("我知道了",new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           dialog1.dismiss();
                                       }
                                   }
        );
        dialog1.show();
    }




    /**
     * 设置监听器
     */
    private void setOnLiteners() {
        currentImageModel = UserManager.currentImageModel;
        grid_pic_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!sdcardExist()) {
                    ToastUtils.makeText(ExpandBusinessActivity.this, "未检测到SD卡").show();
                    return;
                }
                currentImageModel = bankList.get(position);
//                if (TextUtils.isEmpty(currentImageModel.getPath()) && currentImageModel.getImageFile() == null) {
                    UserManager.currentImageModel = currentImageModel;
                    ImageSelector selector = ImageSelector.create();
                    selector.origin(null)
                            .showCamera(true)
                            .openCameraOnly(false)
                            .count(1)
                            .spanCount(4)
                            .start(ExpandBusinessActivity.this, Constant.REQUEST_IMAGE_FORM);
//                    return;
//                }
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
    final String[] picTitles = {"营业执照", "身份证正面", "身份证反面", "门头照", "店内展柜照", "店内整体照"};

    private void addDefoultChild(List<ImageModel> bankList, int row, String name) {
        for (int i = 0; i < picTitles.length; i++) {
            ImageModel imageModel = new ImageModel(i, row, name);
            imageModel.setTitle(picTitles[i]);
            bankList.add(imageModel);
        }
    }

    /**
     * @param rootView
     */
    private void initViewStatus(ViewGroup rootView) {
        for (int i = 0; i < rootView.getChildCount(); i++) {
            ViewGroup view = (ViewGroup) rootView.getChildAt(i);
            initStatus((ViewGroup) view.getChildAt(0));
        }
    }

    /**
     * 初始化数据
     */
    private void initView() {
        layout_taobao = (RelativeLayout) findViewById(R.id.layout_taobao);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        layout_rootView = (LinearLayout) findViewById(R.id.layout_rootView);
        tb_tv_titile.setText("拓展商户");
        grid_pic_view = (MGridView) findViewById(R.id.grid_pic_view);
    }

    /**
     * @param target
     */
    private void initStatus(ViewGroup target) {
        ViewGroup parent = (ViewGroup) target.getParent();
        final View context_Layout = parent.getChildAt(1);
        final View flag = target.getChildAt(1);
        target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((v.getTag() != null) && ((Integer) v.getTag() == 1)) {
                    v.setTag(0);
                    /**
                     * 当前状态是展开
                     * 下面进行闭合操作
                     */
                    context_Layout.setVisibility(View.GONE);
                    resetAnimation(flag);

                } else {
                    v.setTag(1);

                    /**
                     * 当前状态是关闭
                     * 下面进行展开操作
                     */
                    context_Layout.setVisibility(View.VISIBLE);
                    startAnimationBy(flag);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
    /**
     * 初始化View
     * @param view
     */
    private void resetAnimation(View view) {
        float rotation = view.getRotation();
        if (0 < rotation && rotation <= 180) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", -180, 0).setDuration(500);
            view.setPivotX(view.getWidth() / 2);
            view.setPivotY(view.getHeight() / 2);
            animator.start();
        }
    }

    /**
     * 开始动画View
     */
    private void startAnimationBy(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, 180).setDuration(500);
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight() / 2);
        animator.start();
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
            ToastUtils.makeText(ExpandBusinessActivity.this, "图片格式或大小异常,请重新选择").show();
            return;
        }

        Luban.with(ExpandBusinessActivity.this)
                .load(file)                     //传人要压缩的图片
                .setCompressListener(new OnCompressListener() { //设置回调

                    @Override
                    public void onStart() {
                        //  压缩开始前调用，可以在方法内启动 loading UI
                        ToastUtils.d(ExpandBusinessActivity.this, "正在处理").show();

                    }

                    @Override
                    public void onSuccess(File file) {
//    压缩成功后调用，返回压缩后的图片文件

                        upLoadURL(file, postion, imagetype);

                    }

                    @Override
                    public void onError(Throwable e) {
                        //  当压缩过去出现问题时调用
                        ToastUtils.d(ExpandBusinessActivity.this, "图片处理异常").show();
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

                ToastUtils.makeText(ExpandBusinessActivity.this, e.getMessage()).show();
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
                        ToastUtils.e(ExpandBusinessActivity.this, bussinessNameRepponse.getMessage() + "").show();
                    }
                } catch (Exception e) {
                    dismissProgress();
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 刷新子进度
     *
     * @param gridView
     * @param adapter
     */
    private void updateChild(MGridView gridView, AdminAdapter adapter, String progress, String title) {
        View view = gridView.getChildAt(1);
        AdminAdapter.ViewHolder viewHolder = (AdminAdapter.ViewHolder) view.getTag();
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
//                    updateNewChild(bankList, bankAdapter);
                } else {
                    bankList.get(currentImageModel.getPosition() ).setImageFile(imageFile);
                    bankList.get(currentImageModel.getPosition() ).setRelativePath(relativePath);
                    bankList.get(currentImageModel.getPosition() ).setAbsolutePath(absolutePath);
                    bankList.get(currentImageModel.getPosition() ).setAbsolutePath(absolutePath);
                    bankList.get(currentImageModel.getPosition() ).setType(type);
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
                    updateChild(grid_pic_view, bankAdapter, String.valueOf(proInt + "%"), "图片上传中");
                } else {
                    updateChild(grid_pic_view, bankAdapter, "", "");
                }
                break;
        }
    }


}

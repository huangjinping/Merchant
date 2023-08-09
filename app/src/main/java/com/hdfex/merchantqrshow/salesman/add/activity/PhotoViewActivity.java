package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageModel;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.salesman.my.activity.AuthenticationActivity;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.view.photoview.PhotoView;

import java.io.File;

/**
 *
 * 照片预览界面
 * Created by harrishuang on 16/9/6.
 */

public class PhotoViewActivity extends BaseActivity implements View.OnClickListener {

    public static final String INTENT_KEY = "ImageModel";
    public static final String   STATUS = "STATUS";
    public static final String   PREVIEW = "PREVIEW";

    private PhotoView pho_view;
    private TextView txt_photo_delete;
    private TextView txt_photo_cancel;
    private ImageModel imageModel;
    private User user;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private String status;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    protected void init() {
        tb_tv_titile.setText("预览");
        user = UserManager.getUser(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview);
        initView();
        init();
        Intent intent = getIntent();
        imageModel = (ImageModel) intent.getExtras().getSerializable(INTENT_KEY);
         status = intent.getStringExtra(STATUS);

        if (PREVIEW.equals(status)){
            txt_photo_delete.setVisibility(View.GONE);
        }
        Drawable d = getResources().getDrawable(R.mipmap.ic_defoult);
        RequestManager with = Glide.with(this);

        if (imageModel.getImageFile() != null) {
            with.load(imageModel.getImageFile())
                    .error(d)
                    .placeholder(d)
                    .into(pho_view);
        } else {
            with.load(imageModel.getPath()).error(d)
                    .placeholder(d)
                    .into(pho_view);
        }
        txt_photo_delete.setOnClickListener(this);
        txt_photo_cancel.setOnClickListener(this);

    }


    private void initView() {
        pho_view = (PhotoView) findViewById(R.id.pho_view);
        txt_photo_delete = (TextView) findViewById(R.id.txt_photo_delete);
        txt_photo_cancel = (TextView) findViewById(R.id.txt_photo_cancel);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setOnClickListener(this);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        iv_setting.setOnClickListener(this);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_home.setOnClickListener(this);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
            case R.id.txt_photo_cancel:
                PhotoViewActivity.this.finish();
                break;
            case R.id.txt_photo_delete:
                delete();
                break;

        }
    }


    /**
     * 获取资料接口
     */
    private void delete() {

        ToastUtils.i(PhotoViewActivity.this, "资料删除成功").show();
        PhotoViewActivity.this.setResult(RESULT_OK, getIntent());
        PhotoViewActivity.this.finish();
//        if (connect()) {
//            OkHttpUtils.post()
//                    .url(NetConst.DEL_PIC)
//                    .addParams("token", user.getToken())
//                    .addParams("id", user.getId())
//                    .addParams("picId", imageModel.getPicId())
//                    .build()
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onError(Call call, Exception e) {
//
//                        }
//
//                        @Override
//                        public void inProgress(float progress) {
//                            super.inProgress(progress);
//
//                        }
//
//                        @Override
//                        public void onBefore(Request request) {
//                            super.onBefore(request);
//
//                        }
//
//                        @Override
//                        public void onAfter() {
//                            super.onAfter();
//
//                        }
//
//                        @Override
//                        public void onResponse(String response) {
//
//
////                            {"code":0,"message":"request success!"}
//                            try {
//                                JSONObject jsonObject = new JSONObject(response);
//                                if (jsonObject.optInt("code", -1) == 0) {
//                                    if (TextUtils.isEmpty(imageModel.getPath())) {
//                                        ToastUtils.i(PhotoViewActivity.this, "资料删除成功").show();
//                                        PhotoViewActivity.this.setResult(RESULT_OK, getIntent());
//                                        PhotoViewActivity.this.finish();
//                                        return;
//                                    }
//                                    ToastUtils.i(PhotoViewActivity.this, "资料删除成功").show();
//                                    PhotoViewActivity.this.setResult(RESULT_OK, getIntent());
//                                    PhotoViewActivity.this.finish();
//                                    return;
//                                } else {
//                                    ToastUtils.i(PhotoViewActivity.this, jsonObject.optString("message", "资料删除失败")).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    });
//        }

    }

    public static void startAction(Context context, File file, String path) {
        Intent mIntent = new Intent(context, PhotoViewActivity.class);
        mIntent.putExtra(PhotoViewActivity.STATUS,PhotoViewActivity.PREVIEW);
        Bundle bundle = new Bundle();
        ImageModel currentImageModel = new ImageModel();
        currentImageModel.setImageFile(file);
        currentImageModel.setPath(path);
        bundle.putSerializable(PhotoViewActivity.INTENT_KEY, currentImageModel);

        mIntent.putExtras(bundle);
        context.startActivity(mIntent);


    }
}

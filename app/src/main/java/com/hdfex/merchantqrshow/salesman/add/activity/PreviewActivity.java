package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.salesman.add.adapter.UpLoadAdapter;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.commodity.Contract;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageModel;
import com.hdfex.merchantqrshow.bean.salesman.commodity.Pic;
import com.hdfex.merchantqrshow.bean.salesman.commodity.PicSelectModel;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.CommodityCreateInfo;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.CommodityCreateModel;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.CommodityCreateResponse;
import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.CreateSeri;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.view.MGridView.MGridView;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 预览界面
 * Created by harrishuang on 16/9/6.
 */

public class PreviewActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private TextView txt_commodity_address;
    private TextView txt_commodity_price;
    private TextView txt_coustomer_name;
    private TextView txt_coustomer_phone;
    private TextView txt_start_time;
    private TextView txt_end_time;
    private TextView txt_downpayment_type;
    private MGridView grv_lease_contract;
    private Button btn_submit;
    private List<Pic> rentPactLists;
    private List<ImageModel> rentModelLists;
    private User user;
    private CreateSeri createSeri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        Intent comIntent = getIntent();
        Serializable serializable = comIntent.getExtras().getSerializable(IntentFlag.SERIALIZABLE);
        createSeri = (CreateSeri) serializable;
        initView();

        initData();
    }

    private void initData() {
        tb_tv_titile.setText("预览");
        img_back.setImageResource(R.mipmap.ic_close);
        user = UserManager.getUser(this);
        analysis();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_submit:
                toNextPage();
                break;
        }
    }

    /**
     * 生成二维码
     */
    private void toQRActivity() {
        Intent intent = new Intent(this, QRCodeProduceActivity.class);
        Intent comIntent = getIntent();
        Serializable serializable = comIntent.getExtras().getSerializable(IntentFlag.SERIALIZABLE);
        CreateSeri createSeri = (CreateSeri) serializable;
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.SERIALIZABLE, createSeri);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 打开预览模式
     *
     * @param picList
     * @param position
     */
    private void toPictureAlbumActivity(List<Pic> picList, int position) {
        Intent mIntent = new Intent(this, PicturePreviewAlbumActivity.class);
        PicSelectModel picSelectModel = new PicSelectModel();
        picSelectModel.setSelectLists(picList);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.PICTURE_SELECT, picSelectModel);
        mIntent.putExtras(bundle);
        mIntent.putExtra(IntentFlag.CONFORM, "PreviewActivity");
        mIntent.putExtra(IntentFlag.POSITION, position);
        startActivity(mIntent);
    }

    //解析意图并填充数据
    private void analysis() {
        Intent comIntent = getIntent();
        Serializable serializable = comIntent.getExtras().getSerializable(IntentFlag.SERIALIZABLE);
        CreateSeri createSeri = (CreateSeri) serializable;

        rentModelLists = createSeri.getRentModelLists();
        rentModelLists.remove(0);
        rentPactLists = new ArrayList<>();
        for (int i = 0; i < rentModelLists.size(); i++) {
            Pic pic = new Pic();
            pic.setPicId(rentModelLists.get(i).getPicId());
            pic.setPicUrl(rentModelLists.get(i).getPath());
            pic.setImageFile(rentModelLists.get(i).getImageFile());
            rentPactLists.add(pic);
        }


        CommodityCreateModel createModel = createSeri.getCommodityCreateModel();
        txt_commodity_address.setText(createModel.getAddrDetail());


        try {

            DecimalFormat df = new DecimalFormat("0.00"); // 保留几位小数
            String str = df.format(Double.parseDouble(createModel.getMonthRent()));
            txt_commodity_price.setText("￥" + str + " x " + createModel.getDuration() + "期");
        } catch (Exception e) {
            e.printStackTrace();
        }


        txt_coustomer_name.setText(createModel.getName());
        txt_coustomer_phone.setText(createModel.getPhone());
        txt_start_time.setText(createModel.getStartDate());
        txt_end_time.setText(createModel.getEndDate());
        txt_downpayment_type.setText(createModel.getDownpaymentType());

        toSetAdapter();
    }

    /**
     * 设置适配器
     */
    private void toSetAdapter() {
        UpLoadAdapter rentAdapter = new UpLoadAdapter(rentModelLists, this);
        grv_lease_contract.setAdapter(rentAdapter);
        grv_lease_contract.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toPictureAlbumActivity(rentPactLists, position);
            }
        });

    }


    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        txt_commodity_address = (TextView) findViewById(R.id.txt_commodity_address);
        txt_commodity_price = (TextView) findViewById(R.id.txt_commodity_price);
        txt_coustomer_name = (TextView) findViewById(R.id.txt_coustomer_name);
        txt_coustomer_phone = (TextView) findViewById(R.id.txt_coustomer_phone);
        txt_start_time = (TextView) findViewById(R.id.txt_start_time);
        txt_end_time = (TextView) findViewById(R.id.txt_end_time);
        txt_downpayment_type = (TextView) findViewById(R.id.txt_downpayment_type);
        grv_lease_contract = (MGridView) findViewById(R.id.grv_lease_contract);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
    }

    /***
     * 返回修改
     */
    public void onRefox(View view) {
        Intent intent = new Intent(this, CreateCommodityActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    /**
     * 下一步
     */
    private void toNextPage() {
        CommodityCreateModel createModel = createSeri.getCommodityCreateModel();
//        System.out.println(createModel.toString());
//        LogUtil.d("hjp", createModel.toString());
        if (connect()) {
            OkHttpUtils.post()
                    .url(NetConst.CREATE_QRCODE)
                    .addParams("token", user.getToken())
                    .addParams("id", user.getId())
                    .addParams("bussinessId", user.getBussinessId())
                    .addParams("bussinessCustName", user.getName())
                    .addParams("commodityId", createModel.getCommodityId())
                    .addParams("phone", createModel.getPhone())
                    .addParams("startDate", createModel.getStartDate())
                    .addParams("endDate", createModel.getEndDate())
                    .addParams("downpaymentType", createModel.getTypeModeCode())
                    .addParams("monthRent", createModel.getMonthRent())
                    .addParams("duration", createModel.getDurationCode())
                    .addParams("caseId", createModel.getCaseId())
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
                Intent intent = new Intent(this, ZuFangQRCodeProduceActivity.class);
                Bundle bundle = new Bundle();
                createSeri.setCommodityCreateInfo(info);
                bundle.putSerializable(IntentFlag.SERIALIZABLE, createSeri);
                intent.putExtras(bundle);
                intent.putExtra(IntentFlag.CONFORM, IntentFlag.INTENT_CONFORM_ZUFANG);
                startActivity(intent);
                finish();
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
        for (ImageModel model : rentModelLists) {
            if (!TextUtils.isEmpty(model.getAbsolutePath()) && !TextUtils.isEmpty(model.getRelativePath())) {
                Contract contrct = new Contract();
                contrct.setFileType(model.getType());
                contrct.setFilePath(model.getRelativePath());
                dataList.add(contrct);
            }
        }
//        for (ImageModel model : agencyModelLists) {
//            if (!TextUtils.isEmpty(model.getAbsolutePath()) && !TextUtils.isEmpty(model.getRelativePath())) {
//                Contract contrct = new Contract();
//                contrct.setFileType(model.getType());
//                contrct.setFilePath(model.getRelativePath());
//                dataList.add(contrct);
//            }
//        }

        String gsonString = GsonUtil.createGsonString(dataList);

        return gsonString;
    }
}

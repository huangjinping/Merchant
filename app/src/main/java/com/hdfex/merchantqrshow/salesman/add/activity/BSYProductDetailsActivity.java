package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.bsy.ProductItem;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.presenter.BSYProductPresenter;
import com.hdfex.merchantqrshow.mvp.view.BSYProductView;
import com.hdfex.merchantqrshow.utils.RegexUtils;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.DeleteEditText;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by harrishuang on 2016/11/24.
 * 商品详情界面
 */

public class BSYProductDetailsActivity extends BaseActivity implements View.OnClickListener, BSYProductView {

    private static final String INTENT_NAME = "INTENT_NAME";
    private ImageView img_back;
    private TextView txt_left_name;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private ImageView img_order_pic;
    private TextView txt_commodity_name;
    private TextView txt_product_id;
    private TextView txt_product_status;
    private TextView txt_amount;
    private LinearLayout layout_item_orderlist;
    private TextView et_commodity_name;
    private DeleteEditText et_customer_name;
    private DeleteEditText et_live_addrProvince;
    private DeleteEditText et_live_addrTown;
    private DeleteEditText et_customer_phone;
    private Button btn_submit;
    private BSYProductPresenter presenter;
    private User user;
    private ProductItem productItem;
    private String provinceCode;
    private String cityCode;
    private String areaCode;

    /**
     * 发意图了
     */
    public static void start(ProductItem productItem, Context context) {
        Intent intent = new Intent(context, BSYProductDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(INTENT_NAME, productItem);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsyproduct);
        initView();
        initData();

    }

    private void initData() {
        tb_tv_titile.setText("商品详情");
        user = UserManager.getUser(this);
        presenter = new BSYProductPresenter();
        presenter.attachView(this);
        productItem = (ProductItem) getIntent().getExtras().getSerializable("INTENT_NAME");
        setViewByData();

    }

    private void setViewByData() {

        presenter.loadData(user,productItem.getCommodityId());
        if (!TextUtils.isEmpty(productItem.getCountLeft())){
            Glide.with(getApplicationContext())
                    .load(productItem.getCommodityUrl())
                    .placeholder(R.mipmap.ic_defoult)
                    .error(R.mipmap.ic_defoult)
                    .into(img_order_pic);
        }

        if (!TextUtils.isEmpty(productItem.getCommodityName())){
            txt_commodity_name.setText(productItem.getCommodityName());
        }
        if (!TextUtils.isEmpty(productItem.getParameter())){
             txt_product_id.setText(productItem.getParameter());
        }
        if (productItem.getCommodityPrice()!=null){
            txt_amount.setText(productItem.getCommodityPrice().toString());
        }
        if (productItem.getCountLeft()!=null){
            txt_amount.setText(productItem.getCountLeft().toString());
        }

    }


    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        txt_left_name = (TextView) findViewById(R.id.txt_left_name);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        tv_home = (TextView) findViewById(R.id.tv_home);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        img_order_pic = (ImageView) findViewById(R.id.img_order_pic);
        txt_commodity_name = (TextView) findViewById(R.id.txt_commodity_name);
        txt_product_id = (TextView) findViewById(R.id.txt_product_id);
        txt_product_status = (TextView) findViewById(R.id.txt_product_status);
        txt_amount = (TextView) findViewById(R.id.txt_amount);
        layout_item_orderlist = (LinearLayout) findViewById(R.id.layout_item_orderlist);
        et_commodity_name = (TextView) findViewById(R.id.et_commodity_name);
        et_customer_name = (DeleteEditText) findViewById(R.id.et_customer_name);
        et_live_addrProvince = (DeleteEditText) findViewById(R.id.et_live_addrProvince);
        et_live_addrTown = (DeleteEditText) findViewById(R.id.et_live_addrTown);
        et_customer_phone = (DeleteEditText) findViewById(R.id.et_customer_phone);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_live_addrProvince.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                submit();

                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.et_live_addrProvince:
                presenter.openAddrProvince();
                break;
        }
    }

    private void submit() {
        // validate
        String name = et_customer_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.d(this, "请填写真实姓名，方便收货", Toast.LENGTH_SHORT).show();
            return;
        }

        String addrProvince = et_live_addrProvince.getText().toString().trim();
        if (TextUtils.isEmpty(addrProvince)) {
            ToastUtils.d(this, "请选择省／市／区", Toast.LENGTH_SHORT).show();
            return;
        }

        String addrTown = et_live_addrTown.getText().toString().trim();
        if (TextUtils.isEmpty(addrTown)) {
            ToastUtils.d(this, "请填写详细地址", Toast.LENGTH_SHORT).show();
            return;
        }

        String phone = et_customer_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.d(this, "请填写真实手机号，方便收货", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!RegexUtils.mobile(phone)) {
            ToastUtils.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> param = new HashMap<>();
        param.put("commodityId", productItem.getCommodityId());
        param.put("commodityCount", "1");
        param.put("totalAmount", productItem.getCommodityPrice() + "");
        param.put("bussinessId", user.getBussinessId());
        param.put("custName", et_customer_name.getText().toString());
        param.put("phoneNo", et_customer_phone.getText().toString());
        param.put("provinceCode", provinceCode);
        param.put("cityCode", cityCode);
        param.put("areaCode", areaCode);
        param.put("address", et_live_addrTown.getText().toString());

        presenter.onSubmit(user,param);
    }

    @Override
    public void onResult(ProductItem result) {
        if (!TextUtils.isEmpty(result.getParameter())) {
            txt_product_id.setText(result.getParameter());
        }
        if (!TextUtils.isEmpty(result.getCommodityName())) {
            txt_commodity_name.setText(result.getCommodityName());
        }
        if (result.getCommodityPrice() != null) {
            txt_amount.setText(result.getCommodityPrice().toString());
        }
        if (!TextUtils.isEmpty(result.getCountLeft())) {
            txt_product_status.setText("库存"+result.getCountLeft()+"件");
        }

        Glide.with(getApplicationContext())
                .load(result.getCommodityUrl())
                .placeholder(R.mipmap.ic_defoult)
                .error(R.mipmap.ic_defoult)
                .into(img_order_pic);


    }

    @Override
    public void onLocationResult(String province, String city, String county) {
        if (province.equals(city)) {
            et_live_addrProvince.setText(province + " " + county);
        } else {
            et_live_addrProvince.setText(province + " " + city + " " + county);
        }
        
//        createModel.setAddrProvince(province);
//        createModel.setAddrCounty(city);
//        createModel.setAddrArea(county);
        et_live_addrProvince.clearDrawable();
    }

    @Override
    public void onlocationCode(String mProvinceCode, String mCityCode, String mCountyCode) {
        provinceCode=mProvinceCode;
        cityCode=mCityCode;
        areaCode=mCountyCode;
    }
}

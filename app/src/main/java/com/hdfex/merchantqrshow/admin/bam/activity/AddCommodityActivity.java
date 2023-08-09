package com.hdfex.merchantqrshow.admin.bam.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.admin.bam.BrandListBean;
import com.hdfex.merchantqrshow.bean.admin.bam.CommodityCategoryListBean;
import com.hdfex.merchantqrshow.bean.admin.bam.FindCommodityDetail;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.AddCommodityContract;
import com.hdfex.merchantqrshow.mvp.presenter.AddCommodityPersenter;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.fragment.UpLoadFragment;

import java.util.List;

import okhttp3.Call;

/**
 * 添加商品
 * author Created by harrishuang on 2017/11/30.
 * email : huangjinping@hdfex.com
 */

public class AddCommodityActivity extends BaseActivity implements View.OnClickListener, AddCommodityContract.View {

    private Button btn_submit;
    private User user;
    private TextView txt_commoditCategory;
    private EditText edt_commodityName;
    private EditText edt_commodityPrice;
    private EditText edt_caseIds;
    private LinearLayout layout_caseId;

    private LinearLayout layout_grid_upload;
    private AddCommodityContract.Presenter presenter;
    private LinearLayout layout_commodityCategory;
    private LinearLayout layout_brand;
    private String commodityId;
    private List<CommodityCategoryListBean> commodityCategoryList;
    private List<BrandListBean> brandList;
    private TextView txt_categoryName;
    private UpLoadFragment upLoadFragment;
    private String pics;
    private String caseIds;
    private ImageView img_back;
    private TextView tb_tv_titile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.ActionSheetStyle);
        setContentView(R.layout.activity_add_commodity);
        user = UserManager.getUser(this);
        presenter = new AddCommodityPersenter();
        presenter.attachView(this);
        initView();
        commodityId = "";
        presenter.loadDetails(this, commodityId);
        upLoadFragment.setOnUpLoadLitener(new UpLoadFragment.OnUpLoadLitener() {
            @Override
            public void onUpLoadClick(String pics) {
                AddCommodityActivity.this.pics = pics;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = UserManager.getUser(this);
    }

    private void initView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        upLoadFragment = UpLoadFragment.getInstance("");

        transaction.replace(R.id.layout_grid_upload, upLoadFragment, "UpLoadFragment").commit();
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        txt_commoditCategory = (TextView) findViewById(R.id.txt_commoditCategory);
        txt_commoditCategory.setOnClickListener(this);
        edt_commodityName = (EditText) findViewById(R.id.edt_commodityName);
        edt_commodityPrice = (EditText) findViewById(R.id.edt_commodityPrice);
        edt_caseIds = (EditText) findViewById(R.id.edt_caseIds);
        edt_caseIds.setOnClickListener(this);
        layout_grid_upload = (LinearLayout) findViewById(R.id.layout_grid_upload);
        layout_grid_upload.setOnClickListener(this);
        layout_commodityCategory = (LinearLayout) findViewById(R.id.layout_commodityCategory);
        layout_commodityCategory.setOnClickListener(this);
        layout_brand = (LinearLayout) findViewById(R.id.layout_brand);
        layout_brand.setOnClickListener(this);
        txt_categoryName = (TextView) findViewById(R.id.txt_categoryName);
        layout_caseId = (LinearLayout) findViewById(R.id.layout_caseId);
        layout_caseId.setOnClickListener(this);

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        tb_tv_titile.setOnClickListener(this);
        tb_tv_titile.setText("添加商品");
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, AddCommodityActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("UpLoadFragment");
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
//                AdminCommodityListActivity.startAction(this);
//                addCommodity();
                submit();
                break;
            case R.id.layout_commodityCategory:
                presenter.showActionCatrgory(this, commodityId, getSupportFragmentManager());
                break;
            case R.id.layout_brand:
                presenter.showBrand(this, brandList);
                break;
            case R.id.layout_caseId:
            case R.id.edt_caseIds:

                String price = edt_commodityPrice.getText().toString().trim();
                if (TextUtils.isEmpty(price)){
                    showToast("请输入商品金额");
                return;
                }
                presenter.showCasePicker(this, price);
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }


    /**
     * 提交出来
     */
    private void submit() {
        // validate

        if (txt_commoditCategory.getTag() == null) {
            showToast("请选择类型");
            return;
        }

        String category = "";
        if ("0203".equals(txt_commoditCategory.getTag().toString())) {
            if (txt_categoryName.getTag() == null) {
                showToast("请选择品牌");
                return;
            }
            category = txt_categoryName.getTag().toString().trim();
        }

        String commodityName = edt_commodityName.getText().toString().trim();
        if (TextUtils.isEmpty(commodityName)) {
            showToast("请输入商品名称");
            return;
        }
        String commodityPrice = edt_commodityPrice.getText().toString().trim();
        if (TextUtils.isEmpty(commodityPrice)) {
            showToast("请输入商品金额");
            return;
        }
        if (TextUtils.isEmpty(caseIds)) {
            showToast("请选择专案");
            return;
        }

        if (TextUtils.isEmpty(pics)) {
            showToast("请上传必要的图片");
            return;
        }
        showProgress();
        OkHttpUtils.post()
                .addParams("id", user.getBussinessId())
                .addParams("token", user.getToken())
                .addParams("commoditCategory", txt_commoditCategory.getTag().toString().trim())
                .addParams("commodityName", commodityName)
                .addParams("commodityPrice", commodityPrice)
                .addParams("caseIds", caseIds)
                .addParams("brandCode", category)
                .addParams("pic", pics)
                .url(NetConst.ADMIN_SAVE_COMMODIY).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                try {
                    if (checkResonse(response)) {
                        Response response1 = GsonUtil.changeGsonToBean(response, Response.class);
                        showToast("添加成功");
                        finish();
                    }
                } catch (Exception e) {
                    showToast("数据异常");
                    e.printStackTrace();
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissProgress();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void reutrnHouseType(String s, int index) {
        CommodityCategoryListBean commodityCategoryListBean = commodityCategoryList.get(index);
        txt_commoditCategory.setText(s + "");
        txt_commoditCategory.setTag(commodityCategoryListBean.getCategoryCode());
        if ("0203".equals(commodityCategoryListBean.getCategoryCode())) {
            layout_brand.setVisibility(View.VISIBLE);
        } else {
            layout_brand.setVisibility(View.GONE);
        }
    }

    @Override
    public void returnFindCommodityDetail(FindCommodityDetail result) {
        commodityCategoryList = result.getCommodityCategoryList();
        brandList = result.getBrandList();

        if (commodityCategoryList == null) {
            return;
        }
        for (int i = 0; i < commodityCategoryList.size(); i++) {
            CommodityCategoryListBean categoryListBean = commodityCategoryList.get(i);
            if ("0203".equals(categoryListBean.getCategoryCode())) {
                reutrnHouseType(categoryListBean.getCategoryName(), i);
                return;
            }
        }

    }

    @Override
    public void returnBrand(BrandListBean brandListBean) {
        txt_categoryName.setText(brandListBean.getBrand());
        txt_categoryName.setTag(brandListBean.getBrandCode());
    }

    @Override
    public void returnFlowPicker(String s, String caseIds) {
        this.caseIds = caseIds;
        edt_caseIds.setText(s + " ");
    }


}

package com.hdfex.merchantqrshow.salesman.order.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.salesman.add.activity.UpLoadSendActivity;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderCommodityDetail;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderDetails;
import com.hdfex.merchantqrshow.bean.salesman.order.OrderDetailsResponse;
import com.hdfex.merchantqrshow.bean.salesman.order.QueryUncompelete;
import com.hdfex.merchantqrshow.mvp.contract.OrderDetailsContract;
import com.hdfex.merchantqrshow.mvp.presenter.OrderDetailsPresenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.StringUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.widget.HDAlertDialog;
import com.jakewharton.rxbinding.view.RxView;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Request;
import rx.Subscriber;

/**
 * 订单详情界面
 */
public class OrderDetailNewActivity extends BaseActivity implements View.OnClickListener, OrderDetailsContract.View {

    private ImageView img_back;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private User user;
    private String applyId;
    private TextView txt_left_name;
    private ListView list_orderdetails;
    private List<OrderCommodityDetail> dataList;
    private List<OrderCommodityDetail> currentList;
    private ViewHeadHolder header;
    private ViewFooterHolder footer;
    private OrderListAdapter adapter;
    private boolean isOpen;
    private Button btn_upload;
    private LinearLayout layout_rootview;
    private OrderDetailsContract.Persenter persenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_detail1);
        persenter = new OrderDetailsPresenter();
        persenter.attachView(this);
        dataList = new ArrayList<>();
        currentList = new ArrayList<>();
        initView();
        initListView();
    }

    /**
     * 初始化list
     */
    private void initListView() {
        View headView = getHeadView();
        View footerView = getFooterView();
        list_orderdetails.addHeaderView(headView);
        list_orderdetails.addFooterView(footerView);
        adapter = new OrderListAdapter();
        list_orderdetails.setAdapter(adapter);
    }

    private void initDate() {
        user = UserManager.getUser(this);
        applyId = getIntent().getStringExtra("applyId");
        Serializable serializable = getIntent().getExtras().getSerializable(IntentFlag.SCAN_DETAILS);
        if (serializable == null) {
            tb_tv_titile.setText("订单详情");
            loadData(applyId);
        } else {
            tb_tv_titile.setText("申请详情");
            QueryUncompelete queryUncompelete = (QueryUncompelete) serializable;
            ChangeToOrderDetails(queryUncompelete);
        }

    }

    private void ChangeToOrderDetails(QueryUncompelete queryUncompelete) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setBusinessId("");
        orderDetails.setBusinessName("");
        orderDetails.setPic("");
        orderDetails.setOrderNo(queryUncompelete.getPackageId());
        orderDetails.setTime(queryUncompelete.getScanDate());
        orderDetails.setTotalPrice(queryUncompelete.getTotalPrice());
        orderDetails.setPeriodAmount(queryUncompelete.getPeriodAmount());
        orderDetails.setDuration(queryUncompelete.getDuration());
        orderDetails.setDownpayment(queryUncompelete.getDownpayment());
        orderDetails.setApplyAmount(queryUncompelete.getApplyAmount());
        orderDetails.setStatus("未提交");
        orderDetails.setFeedback("");
        orderDetails.setBankName("");
        orderDetails.setCardNo("");
        orderDetails.setPhone(queryUncompelete.getPhone());
        orderDetails.setIdName(queryUncompelete.getIdName());
        orderDetails.setOrderCommoditys(queryUncompelete.getCommoditys());
        setViewByData(orderDetails);

//        lv_commodity.setAdapter(new OrderDetailRecyclerAdapter(OrderDetailNewActivity.this, orderDetails));
    }


    private View getHeadView() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_commodity_header, null);
        header = new ViewHeadHolder(view);
        header.img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext();
                if (header.tv_telephone.getTag() == null) {
                    return;
                }

                showAlertForPhone(header.tv_telephone.getTag().toString().trim());


            }
        });
        header.img_call.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
//                        header.img_call.setTextColor(v.getResources().getColor(R.color.text_color3));
                        break;
                    case MotionEvent.ACTION_CANCEL:
//                        header.img_call.setTextColor(v.getResources().getColor(R.color.text_color3));
                        break;
                }
                return false;
            }
        });
        return view;
    }

    /**
     * 打电话
     *
     * @param phone
     */
    protected void showAlertForPhone(String phone) {
        final HDAlertDialog dialog1 = HDAlertDialog.getInstance(this)
                .withTitle("提示")
                .withMsg("呼叫：" + phone);
        dialog1.withButton1Onclick(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           dialog1.dismiss();
//                                           Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + header.tv_telephone.getTag().toString().trim()));
//                                           if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                                               ToastUtils.e(v.getContext(), "请您检查并开启拨打电话权限").show();
//                                               return;
//                                           }
//                                           v.getContext().startActivity(intent);
                                           Intent intent = new Intent(Intent.ACTION_DIAL);
                                           Uri data = Uri.parse("tel:" + header.tv_telephone.getTag().toString().trim());
                                           intent.setData(data);
                                           startActivity(intent);


                                       }
                                   }
        ).withButton2Onclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        dialog1.show();
    }

    /**
     * 保存数据
     */
    private void saveCurrentImage() {


        persenter.shotAllView(this, layout_rootview, new View[]{layout_toolbar, list_orderdetails, btn_upload});
        adapter.notifyDataSetChanged();
    }


    private View getFooterView() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_commodity_detail_footer, null);
        footer = new ViewFooterHolder(view);
        footer.ll_addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    adapter.closeDetail();
                } else {
                    adapter.openDetail();
                }
                isOpen = !isOpen;
                loadMoreStatus();

            }
        });
        return view;
    }


    private void loadMoreStatus() {
        if (isOpen) {
            footer.tv_footer.setText("点击收起列表");
            footer.iv_status.setImageResource(R.mipmap.ic_upper);
        } else {
            footer.tv_footer.setText("点击查看更多");
            footer.iv_status.setImageResource(R.mipmap.ic_downer);
        }
    }


    /**
     * geiView 设置数据
     *
     * @param orderDetails
     */
    private void setViewByData(OrderDetails orderDetails) {


        if (orderDetails == null) {
            return;
        }
        if (orderDetails.getOrderCommoditys() == null || orderDetails.getOrderCommoditys().size() == 0) {
            return;
        }
        dataList.clear();
        dataList.addAll(orderDetails.getOrderCommoditys());
        currentList.clear();
        currentList.add(dataList.get(0));

        if (dataList.size() >= 2) {
            currentList.add(dataList.get(1));
        }
        adapter.notifyDataSetChanged();


        if (!TextUtils.isEmpty(orderDetails.getStatus())) {
            header.tv_status.setText(orderDetails.getStatus());
        }
        if ("审核中".equals(orderDetails.getStatus()) || "未提交".equals(orderDetails.getStatus()) || "已完成".equals(orderDetails.getStatus())) {
            header.tv_status.setTextColor(header.tv_status.getContext().getResources().getColor(R.color.text_color3));
        } else if ("已打回".equals(orderDetails.getStatus())) {
            header.tv_status.setTextColor(header.tv_status.getContext().getResources().getColor(R.color.red_light));
        } else if ("已取消".equals(orderDetails.getStatus()) || "已拒绝".equals(orderDetails.getStatus())) {
            header.tv_status.setTextColor(header.tv_status.getContext().getResources().getColor(R.color.text_status_reject));
        } else {
            header.tv_status.setTextColor(header.tv_status.getContext().getResources().getColor(R.color.blue_light));
        }
        header.tv_name.setText(orderDetails.getIdName());
        header.tv_telephone.setText(StringUtils.getPhoneFormat(orderDetails.getPhone()));
        header.tv_telephone.setTag(orderDetails.getPhone());
        header.tv_total_price.setText(orderDetails.getTotalPrice());
        header.tv_down_playment.setText(orderDetails.getDownpayment());
        header.tv_period_amount.setText(orderDetails.getPeriodAmount());
        header.tv_duration.setText("" + orderDetails.getDuration());
        int totalnum = 0;
        for (int j = 0; j < dataList.size(); j++) {
            OrderCommodityDetail commodityDetail = (OrderCommodityDetail) dataList.get(j);
            totalnum += commodityDetail.getCommodityCount();
        }
//        header.tv_total_number.setText("共" + totalnum + "件商品");



        /**
         * 下面的数据展示
         */

        if (OrderDetails.SEND_FALG_YES.equals(orderDetails.getSendFlag())) {
            btn_upload.setVisibility(View.VISIBLE);
        } else {
            btn_upload.setVisibility(View.GONE);
        }

        if (dataList.size() <= 2) {
            footer.ll_addmore.setVisibility(View.GONE);
//                footer.tv_footer.setText("共" + commoditylist.size() + "类商品");
//                footer.iv_status.setVisibility(View.GONE);
        } else {
            if (isOpen) {
                footer.tv_footer.setText("点击收起列表");
                footer.iv_status.setImageResource(R.mipmap.ic_upper);
            } else {
                footer.tv_footer.setText("点击查看更多");
                footer.iv_status.setImageResource(R.mipmap.ic_downer);
            }
        }
        footer.tv_order_number.setText(orderDetails.getOrderNo());
        footer.tv_time.setText(orderDetails.getTime());
        if ("未提交".equals(orderDetails.getStatus())) {
            footer.tv_order_title.setText("申请信息");
            footer.tv_order_number_title.setText("申请编号：");
            footer.tv_time_title.setText("申请时间：");
        }
        if (!TextUtils.isEmpty(orderDetails.getFeedback())) {
            footer.layout_feedback_content.setVisibility(View.VISIBLE);
            footer.tv_feedback.setText(orderDetails.getFeedback());
//            if ("已打回".equals(orderDetails.getStatus())){
//                footer.tv_feedback_title.setText("打回原因:");
//            }else if ("已取消".equals(orderDetails.getStatus())){
//                footer.tv_feedback_title.setText("取消原因:");
//            }else if ("已拒绝".equals(orderDetails.getStatus())){
//                footer.tv_feedback_title.setText("拒绝原因:");
//            }else {
            footer.tv_feedback_title.setText(orderDetails.getStatus());
//            }
        }
        loadMoreStatus();
    }


    private void loadData(String applyId) {
        if (TextUtils.isEmpty(applyId) || !connect()) {
            return;
        }
        OkHttpUtils
                .post()
                .url(NetConst.ORDER_DETAIL)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("applyId", applyId)
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
//                            LogUtil.e("zbt1",response);
                            boolean b = checkResonse(response);
                            if (b) {
                                OrderDetailsResponse orderDetailsResponse = GsonUtil.changeGsonToBean(response, OrderDetailsResponse.class);
                                OrderDetails orderDetails = orderDetailsResponse.getResult();

                                setViewByData(orderDetails);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showWebEirr();
                        }
                    }
                });
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        tv_home = (TextView) findViewById(R.id.tv_home);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        tv_home.setVisibility(View.VISIBLE);
        tv_home.setText("···");
        tv_home.setTextSize(30);
        img_back.setOnClickListener(this);
        txt_left_name = (TextView) findViewById(R.id.txt_left_name);
        txt_left_name.setOnClickListener(this);
        list_orderdetails = (ListView) findViewById(R.id.list_orderdetails);
        btn_upload = (Button) findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(this);
        layout_rootview = (LinearLayout) findViewById(R.id.layout_rootview);
        RxView.clicks(tv_home).throttleFirst(2* 1000, TimeUnit.MILLISECONDS).subscribe(new Subscriber<Void>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {
                saveCurrentImage();


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_upload:
                onUpLoad();
                break;


            case R.id.layout_rootview:

                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        initDate();
    }

    /**
     * 上传图片
     */
    public void onUpLoad() {
        UpLoadSendActivity.start(this, applyId);
    }

    @Override
    public void returnShotBitmap(Bitmap bitmap) {

    }

    @Override
    public void returnShotFile(File file) {
    }

    @Override
    public void returnDetails(OrderDetails orderDetails) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        persenter.detachView();
    }

    private class OrderListAdapter extends BaseAdapter {
        ViewHolder content;

        @Override
        public int getCount() {
            return currentList.size();
        }

        @Override
        public Object getItem(int position) {
            return currentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(OrderDetailNewActivity.this).inflate(R.layout.item_commodity_detail, null);
                content = new ViewHolder(convertView);
                convertView.setTag(content);
            } else {
                content = (ViewHolder) convertView.getTag();
            }
            OrderCommodityDetail commodityDetail = currentList.get(position);
            content.tv_commodity_name.setText(commodityDetail.getCommodityName());
            content.tv_commodity_number.setText("" + commodityDetail.getCommodityCount());
            BigDecimal price = new BigDecimal(commodityDetail.getCommodityPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
            content.tv_commodity_price.setText("" + price);
//            if(i == templist.size()-1){
//                content.iv_divider.setVisibility(View.INVISIBLE);
//            }else {
//                content.iv_divider.setVisibility(View.VISIBLE);
//            }

            return convertView;
        }

        public void openDetail() {
            currentList.clear();
            currentList.addAll(dataList);


            notifyDataSetChanged();
//        notifyItemRangeInserted(3, sub);
        }

        public void closeDetail() {
            currentList.clear();
            if (dataList.size() > 0) {
                currentList.add(currentList.get(0));
            }
            if (dataList.size() > 1) {
                currentList.add(currentList.get(0));
            }
            notifyDataSetChanged();
        }

        class ViewHolder {
            public View rootView;
            public TextView tv_commodity_name;
            public TextView tv_commodity_price;
            public TextView tv_commodity_number;
            public ImageView iv_divider;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tv_commodity_name = (TextView) rootView.findViewById(R.id.tv_commodity_name);
                this.tv_commodity_price = (TextView) rootView.findViewById(R.id.tv_commodity_price);
                this.tv_commodity_number = (TextView) rootView.findViewById(R.id.tv_commodity_number);
                this.iv_divider = (ImageView) rootView.findViewById(R.id.iv_divider);
            }

        }
    }
}


final class ViewHeadHolder {
    public View rootView;
    public TextView tv_status;
    public TextView tv_name;
    public TextView tv_telephone;
    public ImageView img_call;
    public TextView tv_total_price;
    public TextView tv_down_playment;
    public TextView tv_period_amount;
    public TextView tv_duration;
    public TextView tv_total_number;

    public ViewHeadHolder(View rootView) {
        this.rootView = rootView;
        this.tv_status = (TextView) rootView.findViewById(R.id.tv_status);
        this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        this.tv_telephone = (TextView) rootView.findViewById(R.id.tv_telephone);
        this.img_call = (ImageView) rootView.findViewById(R.id.img_call);
        this.tv_total_price = (TextView) rootView.findViewById(R.id.tv_total_price);
        this.tv_down_playment = (TextView) rootView.findViewById(R.id.tv_down_playment);
        this.tv_period_amount = (TextView) rootView.findViewById(R.id.tv_period_amount);
        this.tv_duration = (TextView) rootView.findViewById(R.id.tv_duration);
        this.tv_total_number = (TextView) rootView.findViewById(R.id.tv_total_number);
    }
}

class ViewFooterHolder {
    public View rootView;
    public TextView tv_footer;
    public ImageView iv_status;
    public LinearLayout ll_addmore;
    public TextView tv_order_title;
    public TextView tv_order_number_title;
    public TextView tv_order_number;
    public TextView tv_time_title;
    public TextView tv_time;
    public TextView tv_feedback_title;
    public TextView tv_feedback;
    public LinearLayout layout_feedback_content;

    public ViewFooterHolder(View rootView) {
        this.rootView = rootView;
        this.tv_footer = (TextView) rootView.findViewById(R.id.tv_footer);
        this.iv_status = (ImageView) rootView.findViewById(R.id.iv_status);
        this.ll_addmore = (LinearLayout) rootView.findViewById(R.id.ll_addmore);
        this.tv_order_title = (TextView) rootView.findViewById(R.id.tv_order_title);
        this.tv_order_number_title = (TextView) rootView.findViewById(R.id.tv_order_number_title);
        this.tv_order_number = (TextView) rootView.findViewById(R.id.tv_order_number);
        this.tv_time_title = (TextView) rootView.findViewById(R.id.tv_time_title);
        this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
        this.tv_feedback_title = (TextView) rootView.findViewById(R.id.tv_feedback_title);
        this.tv_feedback = (TextView) rootView.findViewById(R.id.tv_feedback);
        this.layout_feedback_content = (LinearLayout) rootView.findViewById(R.id.layout_feedback_content);
    }


}

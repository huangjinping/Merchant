package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.activity.WebActivity;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.commodity.Details;
import com.hdfex.merchantqrshow.bean.salesman.commodity.DetailsResponse;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ShoppingCart;
import com.hdfex.merchantqrshow.bean.salesman.commodity.SignUrlResponse;
import com.hdfex.merchantqrshow.bean.salesman.commodity.Type;
import com.hdfex.merchantqrshow.bean.salesman.commodity.TypeResponse;
import com.hdfex.merchantqrshow.bean.salesman.installment.Installment;
import com.hdfex.merchantqrshow.bean.salesman.installment.InstallmentResponse;
import com.hdfex.merchantqrshow.bean.salesman.installment.ShoppingList;
import com.hdfex.merchantqrshow.bean.salesman.login.AuthHt;
import com.hdfex.merchantqrshow.bean.salesman.login.AuthHtResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.order.Order;
import com.hdfex.merchantqrshow.bean.salesman.order.Prouct;
import com.hdfex.merchantqrshow.mvp.contract.CommodityListContract;
import com.hdfex.merchantqrshow.mvp.presenter.CommodityListPresenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.add.adapter.CapitalTypeAdapter;
import com.hdfex.merchantqrshow.salesman.add.adapter.DetailsListAdapter;
import com.hdfex.merchantqrshow.salesman.add.adapter.PreSubmitGoodsAdapter;
import com.hdfex.merchantqrshow.salesman.add.adapter.TypeListAdapter;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.log.LogUtil;
import com.hdfex.merchantqrshow.widget.KeyBoardLayout;
import com.hdfex.merchantqrshow.widget.LinkListView;
import com.hdfex.merchantqrshow.widget.MyListViewClickListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 创建商品界面
 */
public class CommodityListActivity extends BaseActivity implements LinkListView.LinkListViewListener, CommodityListContract.View, View.OnClickListener {

    private ListView lv_type;
    private LinkListView linklv_details;
    private ListView lv_shopping;
    private ListView lv_capitaltype;
    private ArrayList<Type> typelist;
    private ArrayList<ShoppingCart> shoppinglist;
    private View cart;
    private View capitalView;
    private View shelter;
    private View v_popup_title_top;
    private ImageView iv_shelter;
    private TextView tb_tv_titile;
    private Button btn_total_count;
    private TextView tv_total_price;
    private Button btn_conform;
    private EditText edt_order_search;
    private LinearLayout layout_shopping_cart;
    private LinearLayout layout_confrom;
    private KeyBoardLayout layout_root_commodity;
    private ImageView iv_back;
    private String currentCategory;
    private int currentposition;
    private LinearLayout layout_delete;
    private String commodityName;
    private int NOMAL = 0;
    private int SEARCH = 1;
    private int LOADMORE_STATE = NOMAL;
    private ArrayList<Details> categorygoodslist;//具体分类下的商品
    private DetailsListAdapter detailsListAdapter;
    private PreSubmitGoodsAdapter shoppingAdapter;
    private List<Installment> capitalTypeList;
    private CapitalTypeAdapter capitalTypeAdapter;
    private TypeListAdapter typeListAdapter;
    private User user;
    private InputMethodManager imm;
    private int searchPageIndex = 0;
    private int categoryPageIndex = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            if (msg.what == 1) {
                if (detailsListAdapter != null) {
                    refreshlist();
                }
            }
            if ("search_null".equals(bundle.getString("search_null"))) {
                if (typelist != null) {
                    categoryPageIndex = 0;
                    currentCategory = typelist.get(0).getCategory();
                    typeListAdapter.setSelectItem(0);
                    typeListAdapter.notifyDataSetChanged();
                    LOADMORE_STATE = NOMAL;
                    initCommodity(currentCategory);
                }
            }

        }
    };
    private View v_popup_dismiss_top;
    private View v_popup_dismiss_bottom;
    private View v_popup_next;
    //购物车列表popup
    private PopupWindow shoppingCart;
    //购物车列表popup
    private PopupWindow showPayType;
    private CommodityListContract.Presenter presenter;
    private Button btn_sign;
    private  LinearLayout layout_conform;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_commodity);
        presenter = new CommodityListPresenter();
        presenter.attachView(this);
        initView();
        initDate();
    }

    private void initDate() {
        tb_tv_titile.setText("选择商品");
        edt_order_search.setHint("搜索商品");
        user = UserManager.getUser(this);
        categorygoodslist = new ArrayList<>();
        initType();

        //添加footer顶开购物车的空间
        LinearLayout layoutfooter = (LinearLayout) View.inflate(this, R.layout.item_footer, null);
        linklv_details.addFooterView(layoutfooter, null, false);
//        lv_type.addFooterView(layoutfooter, null, false);

        //清除自定义header和foot
        linklv_details.removeHeaderOrFoot(true);

        //开启下拉刷新
        linklv_details.setDoMoreWhenBottom(false);
        linklv_details.setOnMoreListener(this);

        capitalTypeList = new ArrayList<>();
        capitalTypeAdapter = new CapitalTypeAdapter(this, capitalTypeList, new MyListViewClickListener() {
            @Override
            public void itemClick(Object obj, int position) {
                lv_capitaltype.measure(0, 0);
                capitalTypeAdapter.notifyDataSetChanged();
            }
        });

        lv_capitaltype.setAdapter(capitalTypeAdapter);
        //购物车列表
        shoppinglist = new ArrayList<>();
        //购物车适配器
        shoppingAdapter = new PreSubmitGoodsAdapter(this, shoppinglist, new MyListViewClickListener() {
            @Override
            public void itemClick(Object obj, int position) {
                shoppinglist = (ArrayList<ShoppingCart>) shoppingAdapter.getShoppinglist();
                initShoppingBar();
                if (shoppinglist.size() == 0) {
//                    btn_conform.setVisibility(View.GONE);
                    layout_conform.setVisibility(View.GONE);
                    shoppingCart.dismiss();
                }
                refreshlist();
                lv_shopping.measure(0, 0);
                shoppingAdapter.notifyDataSetChanged();
            }
        });
        lv_shopping.setAdapter(shoppingAdapter);
        initSearchView();
    }

    /**
     * 同步刷新列表数据
     */
    private void refreshlist() {
        ArrayList<Details> newDetailslist = (ArrayList<Details>) detailsListAdapter.getDetailsList();
        for (int i = 0; i < newDetailslist.size(); i++) {
            newDetailslist.get(i).setCommodityNumber("0");
            if (shoppinglist != null && shoppinglist.size() > 0) {
                for (int j = 0; j < shoppinglist.size(); j++) {
                    if (newDetailslist.get(i).getCommodityId().equals(shoppinglist.get(j).getCommodityId())) {
                        newDetailslist.get(i).setCommodityNumber(shoppinglist.get(j).getCommodityNumber());
                    }
                }
            }
        }
        shoppingCartHeightSetting();
        detailsListAdapter.notifyDataSetChanged();
    }

    /**
     * 动态配置购物车高度
     */
    private void shoppingCartHeightSetting() {
        int totalHeight = 0;
        for (int i = 0; i < shoppingAdapter.getCount(); i++) {
            View listItem = shoppingAdapter.getView(i, null, lv_shopping);
            listItem.measure(0, 0);
            if (i < 4) {
                totalHeight += listItem.getMeasuredHeight();
            } else {
                totalHeight = 4 * listItem.getMeasuredHeight();
            }
        }
        ViewGroup.LayoutParams params = lv_shopping.getLayoutParams();
        params.height = totalHeight;
        lv_shopping.setLayoutParams(params);
    }

    /**
     * 1.动画和购物车的显示隐藏
     * 2.根据输入内容进行搜索
     */
    private void initSearchView() {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt_order_search.getWindowToken(), 0);
        edt_order_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(edt_order_search.getText().toString().trim())) {
                    layout_delete.setVisibility(View.GONE);
                } else {
                    layout_delete.setVisibility(View.VISIBLE);
                }
            }
        });

        edt_order_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    commodityName = edt_order_search.getText().toString().trim();
                    if (!TextUtils.isEmpty(commodityName)) {
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        //清除商品list和页数信息
                        searchPageIndex = 0;
                        currentposition = -1;
                        categorygoodslist.clear();
                        typeListAdapter.setSelectItem(-1);
                        //请求网络搜索数据
                        LOADMORE_STATE = SEARCH;
                        toSearch(commodityName);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void toSearch(String commodityName) {
        if (!connect() && !isLogin()) {
            return;
        }
        OkHttpUtils
                .post()
                .url(NetConst.PRODUCT_LIST)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("bussinessId", user.getBussinessId())
                .addParams("pageIndex", "" + searchPageIndex)//页数 下拉刷新一次+1
                .addParams("pageSize", "" + 20)//每页内容个数
                .addParams("commodityName", commodityName)//根据商品名称查询
                .build()
                .execute(new StringCallback() {
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
                    public void onError(Call call, Exception e) {
                        showWebEirr(e);
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                                currentCategory = "";
                                DetailsResponse detailsResponse = GsonUtil.changeGsonToBean(response, DetailsResponse.class);
                                ArrayList<Details> newCategorygoodslist = (ArrayList<Details>) detailsResponse.getCommodityDetails().getList();
                                if (newCategorygoodslist.size() == 0) {
                                    if (searchPageIndex == 0) {
                                        ToastUtils.makeText(CommodityListActivity.this, "无此商品,3秒后回到所有商品...").show();
                                        Message msg = Message.obtain();
                                        Bundle bundle = new Bundle();
                                        bundle.putCharSequence("search_null", "search_null");
                                        msg.setData(bundle);
                                        handler.sendMessageDelayed(msg, 3000);
                                        return;
                                    }
                                    ToastUtils.makeText(CommodityListActivity.this, "没有更多数据").show();
                                    return;
                                }
                                searchPageIndex++;
                                categorygoodslist.addAll(newCategorygoodslist);
                                //商品详情适配器
                                detailsListAdapter = new DetailsListAdapter(CommodityListActivity.this, categorygoodslist, new MyListViewClickListener() {
                                    @Override
                                    public void itemClick(Object obj, int position) {
                                        ShoppingCart shopping = (ShoppingCart) obj;
                                        if (shoppinglist != null && shoppinglist.size() == 0) {
                                            if (shopping.getCommodityNumber().equals("0")) {
                                                return;
                                            }
                                            shoppinglist.add(shopping);
                                        } else {
                                            boolean ishave = false;
                                            for (int i = 0; i < shoppinglist.size(); i++) {
                                                if (shopping.getCommodityId().equals(shoppinglist.get(i).getCommodityId())) {
                                                    if (Integer.parseInt(shopping.getCommodityNumber()) > 0) {
                                                        ishave = true;
                                                        BigDecimal number = new BigDecimal(shopping.getCommodityNumber());
                                                        BigDecimal price = new BigDecimal(shopping.getCommodityPrice());
                                                        shoppinglist.get(i).setCommodityNumber("" + number);
                                                        shoppinglist.get(i).setCommodityPrice("" + number.multiply(price));
                                                    } else if (Integer.parseInt(shopping.getCommodityNumber()) == 0) {
                                                        ishave = true;
                                                        shoppinglist.remove(i);
                                                    } else {
                                                        ToastUtils.makeText(CommodityListActivity.this, "商品数量不正确").show();
                                                        shoppinglist.remove(i);
                                                    }
                                                }
                                            }
                                            if (!ishave) {
                                                if (shopping.getCommodityNumber().equals("0")) {
                                                    return;
                                                }
                                                shoppinglist.add(shopping);
                                            }
                                        }
                                        shoppingAdapter.notifyDataSetChanged();
                                        refreshlist();
                                        initShoppingBar();
                                    }
                                });
                                linklv_details.setAdapter(detailsListAdapter);
                                handler.sendEmptyMessage(1);
                                linklv_details.setSelection(currentposition);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showWebEirr(e);
                        }
                    }
                });
    }

    /**
     * 购物栏统计价格数量
     */
    private void initShoppingBar() {
        int totalCount = 0;
        BigDecimal totalPrice = new BigDecimal(0);
        if (shoppinglist.size() > 0) {
//            btn_conform.setVisibility(View.VISIBLE);
            layout_conform.setVisibility(View.VISIBLE);
            for (int i = 0; i < shoppinglist.size(); i++) {
                totalCount += Integer.parseInt(shoppinglist.get(i).getCommodityNumber());
                totalPrice = totalPrice.add(new BigDecimal(shoppinglist.get(i).getCommodityPrice()));
                btn_total_count.setText("" + totalCount);
                tv_total_price.setText("" + totalPrice);
            }
            if (totalCount >= 100) {
                btn_total_count.setTextSize(8);
            } else {
                btn_total_count.setTextSize(10);
            }
            btn_total_count.setVisibility(View.VISIBLE);
        } else {
            btn_total_count.setText("0");
            tv_total_price.setText("0");
            btn_total_count.setVisibility(View.GONE);
//            btn_conform.setVisibility(View.GONE);
            layout_conform.setVisibility(View.GONE);
        }
        //清零
        totalCount = 0;
        totalPrice = new BigDecimal(0);
    }

    /**
     * 加载商品详情
     *
     * @param category
     */
    private void initCommodity(String category) {
        if (!connect() && !isLogin()) {
            return;
        }
//        LogUtil.e("zbt",user);
        OkHttpUtils
                .post()
                .url(NetConst.PRODUCT_LIST)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("bussinessId", user.getBussinessId())
                .addParams("pageIndex", "" + categoryPageIndex)//页数
                .addParams("pageSize", "" + 20)//每页内容
                .addParams("category", category)
//                .addParams("commodityName", "")//根据商品名称查询(二选一)
                .build()
                .execute(new StringCallback() {
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
                    public void onError(Call call, Exception e) {
                        showWebEirr(e);
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = checkResonse(response);
//                            LogUtil.e("zbt", response);
                            if (b) {
                                DetailsResponse detailsResponse = GsonUtil.changeGsonToBean(response, DetailsResponse.class);
                                ArrayList<Details> newCategorygoodslist = (ArrayList<Details>) detailsResponse.getCommodityDetails().getList();
                                if (newCategorygoodslist.size() == 0) {
                                    if (categoryPageIndex == 0) {
                                        categorygoodslist.clear();
                                        if (detailsListAdapter != null) {
                                            detailsListAdapter.notifyDataSetChanged();
                                        }
                                    }
                                    ToastUtils.makeText(CommodityListActivity.this, "没有更多数据").show();
                                    return;
                                }
                                categoryPageIndex++;
                                categorygoodslist.addAll(newCategorygoodslist);
                                //商品详情适配器
                                detailsListAdapter = new DetailsListAdapter(CommodityListActivity.this, categorygoodslist, new MyListViewClickListener() {
                                    @Override
                                    public void itemClick(Object obj, int position) {
                                        ShoppingCart shopping = (ShoppingCart) obj;
                                        /**
                                         * 购物车
                                         * 1.为空时直接添加商品
                                         * 2.中有该商品，变更商品数量
                                         * 3.无该商品，添加该商品
                                         * 4.商品数量为0时，移除该商品
                                         */
                                        if (shoppinglist != null && shoppinglist.size() == 0) {
                                            if (shopping.getCommodityNumber().equals("0")) {
                                                return;
                                            }
                                            shoppinglist.add(shopping);
                                        } else {
                                            boolean ishave = false;
                                            for (int i = 0; i < shoppinglist.size(); i++) {
                                                if (shopping.getCommodityId().equals(shoppinglist.get(i).getCommodityId())) {
                                                    if (Integer.parseInt(shopping.getCommodityNumber()) > 0) {
                                                        ishave = true;
                                                        BigDecimal number = new BigDecimal(shopping.getCommodityNumber());
                                                        BigDecimal price = new BigDecimal(shopping.getCommodityPrice());
                                                        shoppinglist.get(i).setCommodityNumber("" + number);
                                                        shoppinglist.get(i).setCommodityPrice("" + number.multiply(price));
                                                    } else if (Integer.parseInt(shopping.getCommodityNumber()) == 0) {
                                                        ishave = true;
                                                        shoppinglist.remove(i);
                                                    } else {
                                                        ToastUtils.makeText(CommodityListActivity.this, "商品数量不正确").show();
                                                        shoppinglist.remove(i);
                                                    }
                                                }
                                            }
                                            if (!ishave) {
                                                if (shopping.getCommodityNumber().equals("0")) {
                                                    return;
                                                }
                                                shoppinglist.add(shopping);
                                            }
                                        }
                                        shoppingAdapter.notifyDataSetChanged();
                                        refreshlist();
                                        initShoppingBar();
                                    }
                                });
                                linklv_details.setAdapter(detailsListAdapter);
                                handler.sendEmptyMessage(1);
                                linklv_details.setSelection(currentposition);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showWebEirr();
                        }
                    }
                });

    }

    /**
     * 商品分类查询
     */
    private void initType() {
        if (!connect() && !isLogin()) {
            return;
        }
        OkHttpUtils
                .post()
                .url(NetConst.COMMODITY_TYPE)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("bussinessId", user.getBussinessId())
                .build()
                .execute(new StringCallback() {
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
                    public void onError(Call call, Exception e) {
                        showWebEirr(e);
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            currentposition = 0;
                            boolean b = checkResonse(response);
                            if (b) {
                                TypeResponse typeResponse = GsonUtil.changeGsonToBean(response, TypeResponse.class);
                                typelist = new ArrayList<>();
                                ArrayList<Type> categroylist = (ArrayList<Type>) typeResponse.getCommodityType().getList();
                                Type type = new Type();
                                type.setCategory("null");
                                categroylist.add(type);
                                typelist.addAll(categroylist);
                                if (categroylist != null && categroylist.size() > 0) {
                                    //商品分类适配器
                                    typeListAdapter = new TypeListAdapter(CommodityListActivity.this, typelist, new MyListViewClickListener() {
                                        //自定义的回调，监听typeListView的点击事件
                                        @Override
                                        public void itemClick(Object obj, int position) {
                                            //根据商品类型，查询商品
                                            if (!TextUtils.isEmpty(currentCategory) && currentCategory.equals(typelist.get(position).getCategory())) {
                                                return;
                                            }
                                            //清除页码和滑动记录等
                                            categoryPageIndex = 0;
//                                            allcommodityPage = 0;
                                            currentposition = 0;
                                            edt_order_search.getText().clear();
                                            LOADMORE_STATE = NOMAL;
                                            if (categorygoodslist != null) {
                                                categorygoodslist.clear();
                                            }
                                            currentCategory = typelist.get(position).getCategory();
                                            initCommodity(currentCategory);
//
                                        }
                                    });

                                    lv_type.setAdapter(typeListAdapter);
                                    //设置type第一列高亮
                                    typeListAdapter.setSelectItem(0);
                                    typeListAdapter.notifyDataSetChanged();
                                    handler.sendEmptyMessage(1);
//                                    initAllCommodity();
                                    currentCategory = categroylist.get(0).getCategory();
                                    initCommodity(currentCategory);
                                } else {
                                    ToastUtils.makeText(CommodityListActivity.this, "该商户暂未添加数据").show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showWebEirr(e);
                        }
                    }
                });

    }

    /**
     * 初始化数据
     */
    private void initView() {
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        lv_type = (ListView) findViewById(R.id.lv_type);
        linklv_details = (LinkListView) findViewById(R.id.linklv_details);
        btn_conform = (Button) findViewById(R.id.btn_conform);
        layout_conform=(LinearLayout)findViewById(R.id.layout_conform);
        btn_conform.setOnClickListener(this);
        capitalView = LayoutInflater.from(this).inflate(R.layout.item_capitaltype, null);
        lv_capitaltype = (ListView) capitalView.findViewById(R.id.lv_capitaltype);
//        popup购物车
        cart = LayoutInflater.from(this).inflate(R.layout.item_shopping_cart, null);
        lv_shopping = (ListView) cart.findViewById(R.id.lv_shopping);
        v_popup_dismiss_top = (View) cart.findViewById(R.id.v_popup_dismiss_top);
        v_popup_dismiss_bottom = (View) cart.findViewById(R.id.v_popup_dismiss_bottom);
        v_popup_next = (View) cart.findViewById(R.id.v_popup_next);
        v_popup_title_top = cart.findViewById(R.id.v_popup_title_top);
        v_popup_dismiss_top.setOnClickListener(this);
        v_popup_dismiss_bottom.setOnClickListener(this);
        v_popup_next.setOnClickListener(this);
        v_popup_title_top.setOnClickListener(this);

        shelter = LayoutInflater.from(this).inflate(R.layout.item_popup_shelter, null);
        iv_shelter = (ImageView) shelter.findViewById(R.id.iv_shelter);
        iv_shelter.setOnClickListener(this);

        btn_total_count = (Button) findViewById(R.id.btn_total_count);
        btn_total_count.setVisibility(View.GONE);
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);
        layout_confrom = (LinearLayout) findViewById(R.id.layout_confrom);
        layout_shopping_cart = (LinearLayout) findViewById(R.id.layout_shopping_cart);
        layout_shopping_cart.setOnClickListener(this);
        iv_back = (ImageView) findViewById(R.id.img_back);
        iv_back.setOnClickListener(this);

        //搜索栏
        edt_order_search = (EditText) findViewById(R.id.edt_order_search);
        edt_order_search.setOnClickListener(this);
        layout_delete = (LinearLayout) findViewById(R.id.layout_delete);
        layout_delete.setOnClickListener(this);
        layout_root_commodity = (KeyBoardLayout) findViewById(R.id.layout_root_commodity);
        layout_root_commodity.setOnkbdStateListener(new KeyBoardLayout.onKybdsChangeListener() {
            @Override
            public void onKeyBoardStateChange(int state) {
                switch (state) {
                    case KeyBoardLayout.KEYBOARD_STATE_HIDE:
                        layout_confrom.setVisibility(View.VISIBLE);
                        handler.sendEmptyMessage(1);
                        break;
                    case KeyBoardLayout.KEYBOARD_STATE_SHOW:
                        layout_confrom.setVisibility(View.GONE);
                        break;
                }
            }
        });

        btn_sign = (Button) findViewById(R.id.btn_sign);
        btn_sign.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.v_popup_next:
            case R.id.btn_conform:
                verifyInstallment();

                break;

            case R.id.layout_shopping_cart:
                showShoppingCart(v);
                break;

            case R.id.v_popup_dismiss_top:
            case R.id.v_popup_dismiss_bottom:
            case R.id.v_popup_title_top:
                shoppingCart.dismiss();
                break;
            case R.id.img_back:
                this.finish();
                break;
            case R.id.layout_delete:
                edt_order_search.getText().clear();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                break;
            case R.id.btn_sign:
                onSign();
                break;
        }
    }


    private void authHt() {
        if (!isLogin()) {
            return;
        }
        final User pUser = UserManager.getUser(this);
        final Context context = CommodityListActivity.this;
        showProgress();
        OkHttpUtils
                .post()
                .url(NetConst.AUTH_HT)
                .addParams("token", pUser.getToken())
                .addParams("id", pUser.getId())
                .addParams("account", pUser.getAccount())

                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        try {
                            dismissProgress();
                            User user = UserManager.getUser(context);
                            if (user.isSign()) {
                                btn_sign.setVisibility(View.VISIBLE);
                            } else {
                                btn_sign.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        LogUtil.d("okhttp", e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {

                        LogUtil.d("okhttp", "2222" + response);
                        try {
                            if (checkResonse(response)) {
                                AuthHtResponse mUserInfo = GsonUtil.changeGsonToBean(response, AuthHtResponse.class);
                                AuthHt result = mUserInfo.getResult();
                                User user = UserManager.getUser(context);
                                user.setSign(result.isSign());
                                UserManager.saveUser(context, user);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showWebEirr();
                        }
                    }
                });

    }


    /**
     * 签约接口
     */
    private void onSign() {

        ArrayList<Prouct> prouctlist = new ArrayList<Prouct>();
        for (int i = 0; i < shoppinglist.size(); i++) {
            Prouct prouct = new Prouct();
            prouct.setCommodityId(shoppinglist.get(i).getCommodityId());
            prouct.setCommodityCount(shoppinglist.get(i).getCommodityNumber());
            prouct.setCommodityName(shoppinglist.get(i).getCommodityName());
            prouctlist.add(prouct);
        }
        String prouctslist = GsonUtil.createGsonString(prouctlist);
        if (prouctlist.size() == 0) {
            showToast("请选择商品");
            return;
        }
        OkHttpUtils
                .post()
                .url(NetConst.QUERY_SIGNURL)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("list", prouctslist)
                .build()
                .execute(new StringCallback() {
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
                    public void onError(Call call, Exception e) {
                        showWebEirr(e);
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            LogUtil.d("okhttp", "============" + response);
                            boolean b = checkResonse(response);
                            if (b) {
                                SignUrlResponse installmentResponse = GsonUtil.changeGsonToBean(response, SignUrlResponse.class);
                                if (installmentResponse != null && !TextUtils.isEmpty(installmentResponse.getResult())) {
                                    WebActivity.start(CommodityListActivity.this, "优选加", installmentResponse.getResult());
                                    return;
                                }
                                showToast("获取链接失败，请联系管理员查看配置");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showWebEirr(e);
                        }
                    }
                });
    }

    /**
     * 分期校验
     */
    private void verifyInstallment() {
//        if (outTotalPrice()) {
//            return;
//        }

        ArrayList<ShoppingList> splist = new ArrayList<>();
        for (int i = 0; i < shoppinglist.size(); i++) {
            ShoppingList sp = new ShoppingList();
            sp.setCommodityId(shoppinglist.get(i).getCommodityId());
            splist.add(sp);
        }
        String splistGson = GsonUtil.createGsonString(splist);

        OkHttpUtils
                .post()
                .url(NetConst.COMMODITY_DETAILS)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("list", splistGson)//商品列表
                .build()
                .execute(new StringCallback() {
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
                    public void onError(Call call, Exception e) {
                        showWebEirr(e);
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                                InstallmentResponse installmentResponse = GsonUtil.changeGsonToBean(response, InstallmentResponse.class);
                                ArrayList<Installment> installmentslist = (ArrayList<Installment>) installmentResponse.getResult();


                                /**
                                 * 计算弹出啊狂
                                 */

                                List<Installment> capitalList = new ArrayList<Installment>();
                                List<Installment> alipyList = new ArrayList<Installment>();
                                List<Installment> otherList = new ArrayList<Installment>();
                                for (int i = 0; i < installmentslist.size(); i++) {
                                    Installment installment = installmentslist.get(i);
                                    boolean isHade = false;
                                    for (int j = 0; j < capitalList.size(); j++) {
                                        Installment temp = capitalList.get(j);
                                        if (temp.getCapitalId().equals(installment.getCapitalId())) {
                                            isHade = true;
                                            break;
                                        }
                                    }
                                    if (!isHade) {
                                        capitalList.add(installment);
                                    }
                                    if (Installment.CAPITALID_ALIPAY.equals(installment.getCapitalId())) {
                                        alipyList.add(installment);
                                    } else {
                                        otherList.add(installment);
                                    }
                                }

                                Order order = new Order();
//                                order.setDuration(installmentslist.get(0).getDuration());
                                order.setTotalPrice(tv_total_price.getText().toString());
//                                order.setList(installmentslist);
                                ArrayList<Prouct> prouctlist = new ArrayList<Prouct>();
                                BigDecimal downpayment = new BigDecimal("0");
                                for (int i = 0; i < shoppinglist.size(); i++) {
                                    Prouct prouct = new Prouct();
                                    prouct.setCommodityId(shoppinglist.get(i).getCommodityId());
                                    prouct.setCommodityCount(shoppinglist.get(i).getCommodityNumber());
                                    prouct.setCommodityName(shoppinglist.get(i).getCommodityName());

                                    prouctlist.add(prouct);
                                    downpayment = downpayment.add(
                                            new BigDecimal(shoppinglist.get(i).getDownpayment())
                                                    .multiply(new BigDecimal(shoppinglist.get(i).getCommodityNumber()))
                                                    .setScale(2, BigDecimal.ROUND_HALF_UP));
                                }
                                String prouctslist = GsonUtil.createGsonString(prouctlist);
                                order.setProuctList(prouctslist);
                                order.setCommoditysList(prouctlist);
                                order.setDownpayment(downpayment.toString());


                                /**
                                 * 判断显示页面
                                 */
                                if (alipyList.size() == 0 && otherList.size() == 0) {
                                    showToast("该商品没有提供资金");
                                    return;
                                }

                                /**
                                 * 蚂蚁花呗
                                 */
                                parseCapitanTypeAction(alipyList, otherList, order);
                                /**
                                 * 花呗和另一个都有
                                 */

                                if (alipyList.size() != 0 && otherList.size() != 0) {
                                    showPayType(layout_shopping_cart, capitalList, alipyList, otherList, order);
                                    return;
                                }


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showWebEirr(e);
                        }
                    }
                });
    }

    /**
     * 是不是总钱数
     *
     * @return
     */
    private Boolean outTotalPrice() {
        BigDecimal totalprice = new BigDecimal(tv_total_price.getText().toString());
        int compare = totalprice.compareTo(new BigDecimal("200000"));
        if (compare == 1) {
            ToastUtils.makeText(this, "商品总价不能高于20万").show();
            return true;
        }
        return false;
    }

    /**
     * 商品列表
     *
     * @param v
     */
    private void showShoppingCart(View v) {
        if (shoppinglist.size() == 0) {
//            btn_conform.setVisibility(View.GONE);
            layout_conform.setVisibility(View.GONE);
            return;
        }
        shoppingCart = new PopupWindow(cart, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        shoppingCart.setTouchable(true);
        shoppingCart.setOutsideTouchable(true);
        shoppingCart.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    shoppingCart.dismiss();
                    return true;
                }
                return false;
            }
        });
        shoppingCart.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popup_cart));
        int srceenHight = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getHeight();
        shoppingCart.setAnimationStyle(R.style.Animation_Cart);
        shoppingCart.showAsDropDown(v, 0, -srceenHight);
//        shoppingCart.showAtLocation(v, Gravity.BOTTOM,0,0);
//        btn_conform.setVisibility(View.VISIBLE);

        layout_conform.setVisibility(View.VISIBLE);


    }

    /**
     * 展示信息
     *
     * @param v
     * @param capitalList
     * @param alipyList
     * @param otherList
     * @param order
     */
    private void showPayType(View v, final List<Installment> capitalList, final List<Installment> alipyList, final List<Installment> otherList, final Order order) {

        if (shoppinglist.size() == 0) {
//            btn_conform.setVisibility(View.GONE);
            layout_conform.setVisibility(View.GONE);

            return;
        }

        capitalTypeList.clear();
        capitalTypeList.addAll(capitalList);
        capitalTypeAdapter.notifyDataSetChanged();

        View v_popup_dismiss_top = capitalView.findViewById(R.id.v_popup_dismiss_top);
        v_popup_dismiss_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPayType.dismiss();
            }
        });
        TextView txt_capitaltype_cancel = (TextView) capitalView.findViewById(R.id.txt_capitaltype_cancel);
        txt_capitaltype_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPayType.dismiss();
            }
        });
        v_popup_dismiss_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPayType.dismiss();
            }
        });
        showPayType = new PopupWindow(capitalView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        showPayType.setTouchable(true);
        showPayType.setOutsideTouchable(true);
        showPayType.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    showPayType.dismiss();
                    return true;
                }
                return false;
            }
        });
        lv_capitaltype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Installment installment = capitalList.get(position);
                showPayType.dismiss();
                selectedCaptionTypeAction(installment, order, alipyList, otherList);
            }
        });
        showPayType.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popup_cart));
        int srceenHight = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getHeight();
        showPayType.setAnimationStyle(R.style.Animation_Cart);
        showPayType.showAsDropDown(v, 0, -srceenHight);
//        shoppingCart.showAtLocation(v, Gravity.BOTTOM,0,0);
//        btn_conform.setVisibility(View.VISIBLE);
        layout_conform.setVisibility(View.VISIBLE);
    }

    /**
     * 选择类型后的操作
     *
     * @param installment
     * @param order
     * @param alipyList
     */
    private void selectedCaptionTypeAction(Installment installment, Order order, List<Installment> alipyList, List<Installment> otherList) {
        /**
         * 蚂蚁花呗
         */
        if (Installment.CAPITALID_ALIPAY.equals(installment.getCapitalId())) {
            order.setDuration(alipyList.get(0).getDuration());
            order.setList(alipyList);

//            LinePaymentActivity.startAction(CommodityListActivity.this, order);
//            ConfirmCommodityActivity.startAction(CommodityListActivity.this, order);

            presenter.preSubmitOrder(CommodityListActivity.this, order, user);
            return;
        }
        /**
         *非花呗
         */
        if (!Installment.CAPITALID_ALIPAY.equals(installment.getCapitalId())) {
            order.setDuration(otherList.get(0).getDuration());
            order.setList(otherList);
            Intent mIntent = new Intent(CommodityListActivity.this, ConfirmorderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentFlag.SUBMIT_ORDER, order);
            mIntent.putExtras(bundle);
            startActivity(mIntent);
            return;
        }
    }

    /**
     * 下载问解析后的操作
     *
     * @param alipyList
     * @param otherList
     * @param order
     */
    private void parseCapitanTypeAction(List<Installment> alipyList, List<Installment> otherList, Order order) {
        /**
         * 蚂蚁花呗
         */
        if (alipyList.size() != 0 && otherList.size() == 0) {
            order.setDuration(alipyList.get(0).getDuration());
            order.setList(alipyList);
//            ConfirmCommodityActivity.startAction(CommodityListActivity.this, order);

            presenter.preSubmitOrder(this, order, user);
            return;
        }
        /**
         *非花呗
         */
        if (alipyList.size() == 0 && otherList.size() != 0) {
            order.setDuration(otherList.get(0).getDuration());
            order.setList(otherList);
            Intent mIntent = new Intent(CommodityListActivity.this, ConfirmorderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentFlag.SUBMIT_ORDER, order);
            mIntent.putExtras(bundle);
            startActivity(mIntent);
            return;
        }
    }


    //下拉刷新或者加载更多选择
    @Override
    public boolean onRefreshOrMore(LinkListView linkListView, boolean isRefresh) {
        if (isRefresh) {
            // 下拉刷新
        } else {
            // 加载更多
            currentposition = linklv_details.getFirstVisiblePosition();
            if (LOADMORE_STATE == NOMAL) {
                initCommodity(currentCategory);
            } else {
                if (!TextUtils.isEmpty(commodityName))
                    toSearch(commodityName);
            }

        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        authHt();
        presenter.loadRedPackage(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}

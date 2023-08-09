package com.hdfex.merchantqrshow.salesman.customer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.customer.CustomerResponse;
import com.hdfex.merchantqrshow.bean.salesman.customer.CustomerResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.customer.activity.CustomerActivity;
import com.hdfex.merchantqrshow.utils.CustomerCompartor;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.RegexUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.view.LetterListView.LetterListView;
import com.hdfex.merchantqrshow.view.xlistView.XListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 客户页
 * Created by harrishuang on 16/9/27.
 */

public class CustomerFragment extends BaseFragment implements View.OnClickListener {
    private XListView list_customer;
    private List<CustomerResult> dataList;
    private List<CustomerResult> dataSearchList;
    private CustomerAdapter adapter;
    private LetterListView let_view;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private User user;
    private ViewHolder viewHolder;
    private TextView txt_left_name;
    private ListView lisv_customer_search;
    private CustomerSearchAdapter searchAdapter;
    private ImageView img_custom_null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);
        initView(view);
        user = UserManager.getUser(getActivity());
        dataList = new ArrayList<>();
        dataSearchList = new ArrayList<>();
        adapter = new CustomerAdapter();
        View searchView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_coustomer_search, null);
        viewHolder = new ViewHolder(searchView);
        viewHolder.txt_search.setOnClickListener(this);
        viewHolder.layout_delete.setOnClickListener(this);
        list_customer.addHeaderView(searchView);
        list_customer.setAdapter(adapter);
        searchAdapter = new CustomerSearchAdapter();
        lisv_customer_search.setAdapter(searchAdapter);
        initData();
        setOnLiteners();
        if (UserManager.isLogin(getActivity())) {
            loadData();
        }
        return view;
    }

    /**
     * 设置监听器
     */
    private void setOnLiteners() {

        list_customer.setPullLoadEnable(false);
        list_customer.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                loadData();

            }

            @Override
            public void onLoadMore() {
            }
        });


        let_view.setOnTouchingLetterChangedListener(new LetterListView.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                try {
                    for (int i = 0; i < dataList.size(); i++) {
                        CustomerResult customerResult = dataList.get(i);
                        if (String.valueOf(customerResult.getPhoneticName().charAt(0)).toUpperCase().equals(s)) {
                            list_customer.setSelection(i);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        list_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {


                    Intent intent = new Intent(getActivity(), CustomerActivity.class);
                    CustomerResult customerResult = dataList.get(position - 2);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(IntentFlag.INTENT_CUSTOMER, customerResult);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        viewHolder.edt_order_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(viewHolder.edt_order_search.getText().toString())) {
                    viewHolder.layout_delete.setVisibility(View.GONE);
                    dataSearchList.clear();
                    searchAdapter.notifyDataSetChanged();
                    lisv_customer_search.setVisibility(View.GONE);
                    let_view.setVisibility(View.VISIBLE);
                    list_customer.setEnabled(true);

                } else {
                    viewHolder.layout_delete.setVisibility(View.VISIBLE);
                    dataSearchList.clear();
                    list_customer.setEnabled(false);

                    searchAdapter.notifyDataSetChanged();
                    lisv_customer_search.setVisibility(View.VISIBLE);
                    let_view.setVisibility(View.GONE);
                    loadDataForSearch(viewHolder.edt_order_search.getText().toString());
                }
            }
        });

        lisv_customer_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CustomerActivity.class);
                CustomerResult customerResult = dataSearchList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentFlag.INTENT_CUSTOMER, customerResult);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化话数据
     */
    private void initData() {

        adapter.notifyDataSetChanged();
    }

    /**
     * @param view
     */
    private void initView(View view) {
        list_customer = (XListView) view.findViewById(R.id.list_customer);
        let_view = (LetterListView) view.findViewById(R.id.let_view);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        tb_tv_titile = (TextView) view.findViewById(R.id.tb_tv_titile);
        iv_setting = (ImageView) view.findViewById(R.id.iv_setting);
        tv_home = (TextView) view.findViewById(R.id.tv_home);
        layout_toolbar = (LinearLayout) view.findViewById(R.id.layout_toolbar);
        img_custom_null = (ImageView) view.findViewById(R.id.img_custom_null);
        img_back.setVisibility(View.INVISIBLE);
        tb_tv_titile.setText("客户");
        tb_tv_titile.setTextColor(getResources().getColor(R.color.black));
        layout_toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        txt_left_name = (TextView) view.findViewById(R.id.txt_left_name);
        lisv_customer_search = (ListView) view.findViewById(R.id.lisv_customer_search);
    }

    /**
     * 联系人接口
     */
    private class CustomerAdapter extends BaseAdapter {
        private ViewHolder viewHolder;

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_customer, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            CustomerResult customerResult = dataList.get(position);
            viewHolder.name.setText(customerResult.getCustName());
            viewHolder.alpha.setVisibility(View.GONE);
            if (position == 0) {
                viewHolder.alpha.setVisibility(View.VISIBLE);
            }

            String phoneticName = customerResult.getPhoneticName();
            if (!TextUtils.isEmpty(phoneticName) && phoneticName.length() > 0) {
                if (position > 0) {
                    char c = phoneticName.charAt(0);
                    CustomerResult customerResult1 = dataList.get(position - 1);
                    if (customerResult1 != null && !TextUtils.isEmpty(customerResult1.getPhoneticName())) {
                        char c1 = customerResult1.getPhoneticName().charAt(0);
                        if (c != c1) {
                            viewHolder.alpha.setVisibility(View.VISIBLE);
                        }
                    }
                }
                viewHolder.alpha.setText(phoneticName.charAt(0) + "");
            }
            return convertView;
        }

        /**
         * 数据问题
         */
        public final class ViewHolder {
            public View rootView;
            public TextView alpha;
            public TextView name;
            public LinearLayout ll_item;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.alpha = (TextView) rootView.findViewById(R.id.alpha);
                this.name = (TextView) rootView.findViewById(R.id.name);
                this.ll_item = (LinearLayout) rootView.findViewById(R.id.ll_item);
            }

        }
    }


    /**
     * @param length
     */
    private void setletViewHeight(int length) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) let_view.getLayoutParams();
        layoutParams.height = list_customer.getHeight() * length / 27;
        let_view.setLayoutParams(layoutParams);
        let_view.invalidate();

    }


    /**
     * 下载数据
     */
    private void loadData() {
        if (!connect()) {
            return;
        }
        OkHttpUtils
                .post()
                .url(NetConst.CUSTLIST)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
//                .addParams("custName", user.getName())
//                .addParams("phone", user.getPhone())

                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        onLoad();

                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
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
                                CustomerResponse customerResponse = GsonUtil.changeGsonToBean(response, CustomerResponse.class);
                                List<CustomerResult> result = customerResponse.getResult();
                                if (result == null || result.size() == 0) {
                                    img_custom_null.setVisibility(View.VISIBLE);
                                } else {
                                    img_custom_null.setVisibility(View.GONE);
                                }
                                setViewByData(result);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                });
    }

    /**
     * 删除数据
     *
     * @param result
     */
    private void setViewByData(List<CustomerResult> result) {


        CustomerCompartor customerCompartor = new CustomerCompartor();
        Collections.sort(result, customerCompartor);
        dataList.clear();
        dataList.addAll(result);
        adapter.notifyDataSetChanged();
    }

    public final class ViewHolder {
        public View rootView;
        public EditText edt_order_search;
        public TextView txt_search;
        public LinearLayout layout_delete;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.edt_order_search = (EditText) rootView.findViewById(R.id.edt_order_search);
            this.txt_search = (TextView) rootView.findViewById(R.id.txt_search);
            this.layout_delete = (LinearLayout) rootView.findViewById(R.id.layout_delete);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_delete:
                viewHolder.edt_order_search.getText().clear();
                dataSearchList.clear();
                searchAdapter.notifyDataSetChanged();
                lisv_customer_search.setVisibility(View.GONE);
                let_view.setVisibility(View.VISIBLE);
                break;
        }
    }


    /**
     * 联系人接口
     */
    private class CustomerSearchAdapter extends BaseAdapter {
        private ViewHolder viewHolder;

        @Override
        public int getCount() {
            return dataSearchList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataSearchList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_customer, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            CustomerResult customerResult = dataSearchList.get(position);
            viewHolder.name.setText(customerResult.getCustName());
            viewHolder.alpha.setVisibility(View.GONE);
            return convertView;
        }

        /**
         * 数据问题
         */
        public final class ViewHolder {
            public View rootView;
            public TextView alpha;
            public TextView name;
            public LinearLayout ll_item;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.alpha = (TextView) rootView.findViewById(R.id.alpha);
                this.name = (TextView) rootView.findViewById(R.id.name);
                this.ll_item = (LinearLayout) rootView.findViewById(R.id.ll_item);
            }

        }

    }

    /**
     * 下载数据
     */
    private void loadDataForSearch(String content) {
        dataSearchList.clear();
        for (int i = 0; i < dataList.size(); i++) {
            CustomerResult customerResult = dataList.get(i);
            if (RegexUtils.number(content)) {
                if (customerResult.getPhone().contains(content)) {
                    dataSearchList.add(customerResult);
                }
            } else {
                if (customerResult.getCustName().contains(content)) {
                    dataSearchList.add(customerResult);
                }
            }
        }
        searchAdapter.notifyDataSetChanged();
    }

    private void onLoad() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    list_customer.stopRefresh();
                    list_customer.stopLoadMore();
                    list_customer.setRefreshTime("刚刚");
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });
    }

}

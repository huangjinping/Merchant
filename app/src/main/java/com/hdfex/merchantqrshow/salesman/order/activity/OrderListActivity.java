package com.hdfex.merchantqrshow.salesman.order.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.app.App;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.salesman.order.fragment.OrderSearchListFragment;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.ACache;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 订单列表
 */
public class OrderListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private EditText edt_order_search;
    private TextView txt_search;
    private FragmentTransaction fragmentTransaction;
    private OrderSearchListFragment fragment;
    private ArrayList<String> searchList;
    private ListView lisv_seatch_resoult;
    private LinearLayout layout_content_view;
    private ArrayAdapter<String> arrayAapter;
    private LinearLayout layout_search;
    private View footerView;
    private ViewHolder viewHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        initView();
        initData();
        setLiteners();
    }

    /**
     *
     */
    private void setLiteners() {
        lisv_seatch_resoult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edt_order_search.setText(searchList.get(i));
                edt_order_search.setSelection(searchList.get(i).length());
                layout_search.setVisibility(View.GONE);
                layout_content_view.setVisibility(View.VISIBLE);
                submit();
            }
        });
        edt_order_search.setHint("请输入分期客户姓名");

        edt_order_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = edt_order_search.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        return true;
                    }
                    // TODO: 16/4/7  搜索现象
                    ((InputMethodManager) edt_order_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    layout_search.setVisibility(View.GONE);
                    layout_content_view.setVisibility(View.VISIBLE);
                    submit();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        footerView = LayoutInflater.from(this).inflate(R.layout.layout_clearhistory, null);
        viewHolder = new ViewHolder(footerView);
        Object asObject = ACache.get(App.getAppContext()).getAsObject(IntentFlag.SEARCH_NAME);
        if (asObject == null) {
            searchList = new ArrayList<>();
        } else {
            searchList = (ArrayList<String>) asObject;
        }
        lisv_seatch_resoult.addFooterView(footerView);
        updateClearView();
        Collections.reverse(searchList);
        arrayAapter = new ArrayAdapter<String>(this, R.layout.item_position_list, searchList);
        lisv_seatch_resoult.setAdapter(arrayAapter);
    }
    /**
     * 更新View
     */
    private void updateClearView() {
        if (searchList!=null&&searchList.size()!=0) {
            viewHolder.txt_clearcatch.setVisibility(View.VISIBLE);
        }else {
            viewHolder.txt_clearcatch.setVisibility(View.GONE);
        }
        viewHolder.txt_clearcatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ACache.get(App.getAppContext()).remove(IntentFlag.SEARCH_NAME);
                searchList.clear();
                arrayAapter.notifyDataSetChanged();
                viewHolder.txt_clearcatch.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_search:
                layout_search.setVisibility(View.GONE);
                layout_content_view.setVisibility(View.VISIBLE);
                submit();
                break;
        }

    }

    /**
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, OrderListActivity.class);
        context.startActivity(intent);
    }

    /**
     * 提交数据问题
     */
    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        edt_order_search = (EditText) findViewById(R.id.edt_order_search);
        txt_search = (TextView) findViewById(R.id.txt_search);
        img_back.setOnClickListener(this);
        txt_search.setOnClickListener(this);
        lisv_seatch_resoult = (ListView) findViewById(R.id.lisv_seatch_resoult);
        layout_content_view = (LinearLayout) findViewById(R.id.layout_content_view);
        layout_search = (LinearLayout) findViewById(R.id.layout_search);
        layout_search.setOnClickListener(this);
    }

    /**
     * 提交
     */
    private void submit() {
        String search = edt_order_search.getText().toString().trim();
        if (TextUtils.isEmpty(search)) {
            return;
        }
        Collections.reverse(searchList);
        if (!searchList.contains(search)) {
            searchList.add(search);
        }
        if (searchList.size() > 10) {
            searchList.remove(0);
        }
        ACache.get(App.getAppContext()).put(IntentFlag.SEARCH_NAME, searchList);
        if (fragment == null) {
            fragment = OrderSearchListFragment.getInstance("0", search);
            fragmentTransaction.replace(R.id.layout_content_view, fragment).commit();
        } else {
            fragment.setSearch(search);
        }
    }

    public class ViewHolder {
        public View rootView;
        public TextView txt_clearcatch;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.txt_clearcatch = (TextView) rootView.findViewById(R.id.txt_clearcatch);
        }

    }
}

package com.hdfex.merchantqrshow.manager.main.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.manager.main.adapter.CommAdapter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.UserManager;

import java.util.ArrayList;

/**
 * Created by harrishuang on 2017/3/20.
 */
public class CommFragment extends BaseFragment implements View.OnClickListener {

    private ArrayList<String> dataList;
    private ListView lisv_apertment;
    private CommAdapter commAdapter;
    private Button btn_cancel;
    private User user;
    private LinearLayout layout_view_top;
    private LinearLayout layout_rootview;
    private CallBack callBack;
    private TextView txt_title;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public static CommFragment getInstance() {
        CommFragment fragement = new CommFragment();
        return fragement;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_apartment, container, false);
        initView(view);
        user = UserManager.getUser(getActivity());
        dataList = new ArrayList<>();
        ArrayList<String> stringArrayList = getArguments().getStringArrayList(IntentFlag.COMM_LIST);
        txt_title.setText(getArguments().getString("title"));
        dataList.addAll(stringArrayList);
        commAdapter = new CommAdapter(getActivity(), dataList);
        lisv_apertment.setAdapter(commAdapter);
        lisv_apertment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (callBack != null) {
                    callBack.onCall(i, dataList.get(i));
                }
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            layout_rootview.setBackgroundColor(getResources().getColor(R.color.transparent_quarter));
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        handler.sendMessageDelayed(handler.obtainMessage(), 300);
    }

    @Override
    public void onPause() {
        super.onPause();
        layout_rootview.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    private void initView(View view) {
        lisv_apertment = (ListView) view.findViewById(R.id.lisv_apertment);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        layout_view_top = (LinearLayout) view.findViewById(R.id.layout_view_top);
        layout_view_top.setOnClickListener(this);
        layout_rootview = (LinearLayout) view.findViewById(R.id.layout_rootview);
        layout_rootview.setOnClickListener(this);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.layout_view_top:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }


    public interface CallBack {
        void onCall(int position, String name);
    }
}

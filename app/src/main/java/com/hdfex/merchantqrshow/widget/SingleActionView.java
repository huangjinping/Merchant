package com.hdfex.merchantqrshow.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.view.MyItemClickListener;

/**
 * Created by harrishuang on 2017/4/26.
 */

public class SingleActionView extends Fragment implements View.OnClickListener {

    private LinearLayout layout_view_top;
    private TextView txt_title;
    private ListView lisv_section;
    private Button btn_cancel;
    private LinearLayout layout_rootview;
    private SectionAdapter adapter;
    private String[] stringArray;
    private String title;
    private MyItemClickListener myItemClickListener;


    public void setMyItemClickListener(MyItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }

    public static SingleActionView getInstance() {
        SingleActionView fragement = new SingleActionView();
        return fragement;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_singleaciton, container, false);
        initView(view);
        stringArray = getArguments().getStringArray("data");
        title = getArguments().getString("title");
        txt_title.setText(title + "");
        adapter = new SectionAdapter(getActivity(), stringArray);
        lisv_section.setAdapter(adapter);

        lisv_section.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (myItemClickListener!=null){
                    myItemClickListener.onItemClick(view,position);
                }

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;
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

    private void initView(View view) {
        layout_view_top = (LinearLayout) view.findViewById(R.id.layout_view_top);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        lisv_section = (ListView) view.findViewById(R.id.lisv_section);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        layout_rootview = (LinearLayout) view.findViewById(R.id.layout_rootview);
        btn_cancel.setOnClickListener(this);
        layout_view_top = (LinearLayout) view.findViewById(R.id.layout_view_top);
        layout_view_top.setOnClickListener(this);
        layout_rootview = (LinearLayout) view.findViewById(R.id.layout_rootview);
        layout_rootview.setOnClickListener(this);

    }

    /**
     * 适配器
     */
    private class SectionAdapter extends BaseAdapter {
        private Context context;
        private String[] dataList;
        private ViewHolder holder;

        public SectionAdapter(Context context, String[] dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.length;
        }

        @Override
        public Object getItem(int i) {
            return dataList[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_apertment, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            String s = dataList[i];
            holder.txt_house_name.setText(s + "");
            return view;
        }

        public class ViewHolder {
            public View rootView;
            public TextView txt_house_name;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.txt_house_name = (TextView) rootView.findViewById(R.id.txt_house_name);
            }
        }

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

}

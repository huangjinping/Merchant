package com.hdfex.merchantqrshow.salesman.appointment.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.appointment.DataErrorHisItem;
import com.hdfex.merchantqrshow.bean.salesman.appointment.FollowUpItemWeek;
import com.hdfex.merchantqrshow.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/10/25.
 * email : huangjinping@hdfex.com
 */

public class FollowRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DataErrorHisItem result;

    public FollowRecordAdapter(DataErrorHisItem result) {
        this.result = result;
        Log.d("okhttp","=====result==="+ GsonUtil.createGsonString(result));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_followrecord, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        List<FollowUpItemWeek> followUpListInweek = result.getFollowUpListInweek();
        List<FollowUpItemWeek> followUpListOutweek = result.getFollowUpListOutweek();
        /**
         * 都有的时候
         */
            if ((followUpListInweek!=null&&followUpListInweek.size()>0)&&(followUpListOutweek!=null&&followUpListOutweek.size()>0)){
                if (position==0){
                    viewHolder.txt_follow_title.setText("本周  跟进:"+followUpListInweek.size()+"次");
                    RecordSectionAdapter adapter = new RecordSectionAdapter(followUpListInweek);
                    viewHolder.rec_follow_history.setLayoutManager(new LinearLayoutManager(viewHolder.rootView.getContext()));
                    viewHolder.rec_follow_history.setAdapter(adapter);

                }else {
                    if (followUpListOutweek!=null&&followUpListOutweek.size()>0){
                        RecordSectionAdapter adapter = new RecordSectionAdapter(followUpListOutweek);
                        viewHolder.rec_follow_history.setLayoutManager(new LinearLayoutManager(viewHolder.rootView.getContext()));
                        viewHolder.rec_follow_history.setAdapter(adapter);
                    }
                }

                return;
            }



        Log.d("okhttp==in====", GsonUtil.createGsonString(followUpListInweek));
        Log.d("okhttp===out==", GsonUtil.createGsonString(followUpListOutweek));


        if ((followUpListInweek!=null&&followUpListInweek.size()>0)){
            viewHolder.txt_follow_title.setText("本周  跟进:"+followUpListInweek.size()+"次");
                RecordSectionAdapter adapter = new RecordSectionAdapter(followUpListInweek);
                viewHolder.rec_follow_history.setLayoutManager(new LinearLayoutManager(viewHolder.rootView.getContext()));
                viewHolder.rec_follow_history.setAdapter(adapter);
            return;
        }

        if ((followUpListOutweek!=null&&followUpListOutweek.size()>0)){
            RecordSectionAdapter adapter = new RecordSectionAdapter(followUpListOutweek);
            viewHolder.rec_follow_history.setLayoutManager(new LinearLayoutManager(viewHolder.rootView.getContext()));
            viewHolder.rec_follow_history.setAdapter(adapter);
            return;
        }











    }

    @Override
    public int getItemCount() {
        if (result == null) {
            return 0;
        }
        List<FollowUpItemWeek> followUpListInweek = result.getFollowUpListInweek();
        List<FollowUpItemWeek> followUpListOutweek = result.getFollowUpListOutweek();

        int count = 0;
        if (followUpListOutweek != null && followUpListOutweek.size() > 0) {
            count++;
        }
        if (followUpListInweek != null && followUpListInweek.size() > 0) {
            count++;
        }
        Log.d("okhttp",count+"count");
        return count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_follow_title;
        public RecyclerView rec_follow_history;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_follow_title = (TextView) rootView.findViewById(R.id.txt_follow_title);
            this.rec_follow_history = (RecyclerView) rootView.findViewById(R.id.rec_follow_history);
        }

    }
}

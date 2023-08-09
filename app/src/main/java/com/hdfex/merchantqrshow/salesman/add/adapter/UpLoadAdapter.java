package com.hdfex.merchantqrshow.salesman.add.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageModel;

import java.util.List;

/**
 *
 * Created by harrishuang on 16/9/6.
 */

public class UpLoadAdapter extends BaseAdapter {
    private List<ImageModel> imageModellist;
    private ViewHolder viewHolder;
    private Context context;

    public UpLoadAdapter(List<ImageModel> imageModellist, Context context) {
        this.imageModellist = imageModellist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageModellist.size();
    }

    @Override
    public Object getItem(int position) {
        return imageModellist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_upload, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageModel imageModel = imageModellist.get(position);
        RequestManager with = Glide.with(context);
        Drawable d = null;
        if (imageModel.getRow() == 0) {
            d = context.getResources().getDrawable(R.mipmap.ic_take_pics);
        } else {
            d = context.getResources().getDrawable(R.mipmap.ic_add_pics);
        }
        if (imageModel.getImageFile() != null) {
            with.load(imageModel.getImageFile())
                    .error(d)
                    .placeholder(d)
                    .into(viewHolder.img_duration);
        } else {
            with.load(imageModel.getAbsolutePath())
                    .error(d)
                    .placeholder(d)
                    .into(viewHolder.img_duration);
        }

//        if (imageModel.isShowProgress()){
//            viewHolder.layout_progress_content.setVisibility(View.VISIBLE);
//        }else {
//            viewHolder.layout_progress_content.setVisibility(View.GONE);
//        }


        return convertView;
    }




    public static   class ViewHolder {
        public View rootView;
        public ImageView img_duration;
        public TextView txt_progress_title;
        public TextView txt_progress;
        public LinearLayout layout_progress_content;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.img_duration = (ImageView) rootView.findViewById(R.id.img_duration);
            this.txt_progress_title = (TextView) rootView.findViewById(R.id.txt_progress_title);
            this.txt_progress = (TextView) rootView.findViewById(R.id.txt_progress);
            this.layout_progress_content = (LinearLayout) rootView.findViewById(R.id.layout_progress_content);
        }

    }
}

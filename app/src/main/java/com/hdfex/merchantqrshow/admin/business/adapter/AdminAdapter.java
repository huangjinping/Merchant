package com.hdfex.merchantqrshow.admin.business.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
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
import com.hdfex.merchantqrshow.salesman.add.adapter.UpLoadAdapter;

import java.util.List;

/**
 * author Created by harrishuang on 2017/12/27.
 * email : huangjinping@hdfex.com
 */

public class AdminAdapter extends BaseAdapter {

    private List<ImageModel> imageModellist;
    private ViewHolder viewHolder;
    private Context context;

    public AdminAdapter(List<ImageModel> imageModellist, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_upload_admin, null);
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

        if (!TextUtils.isEmpty(imageModel.getTitle())){
            viewHolder.txt_title_name.setText(imageModel.getTitle());
        }
        return convertView;
    }




    public static   class ViewHolder {
        public View rootView;
        public ImageView img_duration;
        public TextView txt_progress_title;
        public TextView txt_progress;
        public LinearLayout layout_progress_content;
        public  TextView txt_title_name;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.img_duration = (ImageView) rootView.findViewById(R.id.img_duration);
            this.txt_progress_title = (TextView) rootView.findViewById(R.id.txt_progress_title);
            this.txt_progress = (TextView) rootView.findViewById(R.id.txt_progress);
            this.layout_progress_content = (LinearLayout) rootView.findViewById(R.id.layout_progress_content);
            this.txt_title_name=(TextView) rootView.findViewById(R.id.txt_title_name);

        }
    }
}

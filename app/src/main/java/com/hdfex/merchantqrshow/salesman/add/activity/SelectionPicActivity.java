package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.commodity.Pic;
import com.hdfex.merchantqrshow.bean.salesman.commodity.PicFlag;
import com.hdfex.merchantqrshow.bean.salesman.commodity.PicSelectModel;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadInfo;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadInfoResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.log.LogUtil;
import com.hdfex.merchantqrshow.view.MGridView.MGridView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 *
 * 选择图片
 * Created by harrishuang on 16/8/31.
 */

public class SelectionPicActivity extends BaseActivity implements View.OnClickListener {
    private List<List<Pic>> dataList;
    private Toolbar hd_toolbar;
    private ListView lisv_pic;
    private SelctionAdapter adapter;
    private TextView txt_preview;
    private TextView txt_complate;
    private User user;
    private List<Pic> selectLists;
    private ImageView img_back;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectionpic);
        initView();
        initData();

    }

    protected void initData() {
        user = UserManager.getUser(this);
        getPic();
    }

    /**
     * 获取历史图片
     */
    private void getPic() {
        if (connect()) {
            OkHttpUtils.post()
                    .url("http://123.56.136.151/hd-merchant-web/mobile/customInfo/pictureInfo")
                    .addParams("token", user.getToken())
                    .addParams("id", user.getId())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {

                        }

                        @Override
                        public void inProgress(float progress) {
                            super.inProgress(progress);

                        }

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
                        public void onResponse(String response) {
                            UploadInfoResponse info = GsonUtil.changeGsonToBean(response, UploadInfoResponse.class);
                            initPicture(info.getResult());
                        }
                    });
        }

    }

    private void initPicture(UploadInfo result) {
        dataList = new ArrayList<>();
        selectLists = new ArrayList<>();
        dataList.add(result.getLiushui());
        dataList.add(result.getZichan());
        dataList.add(result.getOthers());
        refreshData();
        adapter = new SelctionAdapter();
        lisv_pic.setAdapter(adapter);
    }

    private void initView() {
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        hd_toolbar = (Toolbar) findViewById(R.id.hd_toolbar);
        lisv_pic = (ListView) findViewById(R.id.lisv_pic);
        tb_tv_titile.setText("选择资料信息");
        txt_preview = (TextView) findViewById(R.id.txt_preview);
        txt_preview.setOnClickListener(this);
        txt_complate = (TextView) findViewById(R.id.txt_complate);
        txt_complate.setOnClickListener(this);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
    }


    private class SelctionAdapter extends BaseAdapter {

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
                convertView = LayoutInflater.from(SelectionPicActivity.this).inflate(R.layout.item_selectionpic, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.txt_creattime.setText("position");
            PicAdapter adapter = new PicAdapter(dataList.get(position));
            viewHolder.grv_pic.setAdapter(adapter);
            return convertView;
        }

        public final class ViewHolder {
            public View rootView;
            public TextView txt_creattime;
            public MGridView grv_pic;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.txt_creattime = (TextView) rootView.findViewById(R.id.txt_creattime);
                this.grv_pic = (MGridView) rootView.findViewById(R.id.grv_pic);
            }
        }
    }

    /**
     * 内部图频
     */
    private class PicAdapter extends BaseAdapter {

        private List<Pic> picList;
        private ViewChildHolder viewHolder;

        public PicAdapter(List<Pic> picList) {

            this.picList = picList;
        }

        @Override
        public int getCount() {
            return picList.size();
        }

        @Override
        public Object getItem(int position) {
            return picList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(SelectionPicActivity.this).inflate(R.layout.item_pic, null);
                viewHolder = new ViewChildHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewChildHolder) convertView.getTag();
            }
            Drawable nompic = getResources().getDrawable(R.mipmap.ic_defoult);
            Pic pic = picList.get(position);
            Glide.with(viewHolder.img_child_pic.getContext())
                    .load(pic.getPicUrl())
                    .error(nompic)
                    .placeholder(nompic)
                    .into(viewHolder.img_child_pic);

            if (TextUtils.isEmpty(pic.getState()) || PicFlag.NORMAL.equals(pic.getState())) {
                //未选中
                viewHolder.img_child_flag.setVisibility(View.INVISIBLE);
            } else if (PicFlag.SELECTED.equals(pic.getState())) {
                //选中
                viewHolder.img_child_flag.setVisibility(View.VISIBLE);
                viewHolder.img_child_flag.setImageResource(R.mipmap.ic_check);
            }
            setOnSelection(position);
            return convertView;
        }

        private void setOnSelection(final int position) {
            final PicAdapter adapter = this;

            viewHolder.img_child_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pic pic = picList.get(position);
                    if (TextUtils.isEmpty(pic.getState()) || PicFlag.NORMAL.equals(pic.getState())) {
                        //点击选定
                        pic.setState(PicFlag.SELECTED);
                    } else if (PicFlag.SELECTED.equals(pic.getState())) {
                        //删除选择
                        pic.setState(PicFlag.NORMAL);
                    }
                    refreshData();
                    adapter.notifyDataSetChanged();
                }
            });
        }

        public final class ViewChildHolder {
            public View rootView;
            public ImageView img_child_pic;
            public ImageView img_child_flag;

            public ViewChildHolder(View rootView) {
                this.rootView = rootView;
                this.img_child_pic = (ImageView) rootView.findViewById(R.id.img_child_pic);
                this.img_child_flag = (ImageView) rootView.findViewById(R.id.img_child_flag);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_preview:
                toPreViewActivity();
                break;
            case R.id.txt_complate:
                verifyCount(false);
                break;
        }
    }

    private void toPreViewActivity() {
        if (verifyCount(true)) {
            return;
        }
        LogUtil.e("zbt", "submit" + selectLists.size());

        Intent mIntent = new Intent(this, PicturePreviewAlbumActivity.class);
        PicSelectModel picSelectModel = new PicSelectModel();
        picSelectModel.setDataList(dataList);
        picSelectModel.setSelectLists(selectLists);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.PICTURE_SELECT, picSelectModel);
        mIntent.putExtras(bundle);
        startActivityForResult(mIntent, IntentFlag.PICTURE_REQUESTCODE);
    }

    /**
     * 统计已选数量
     *
     * @return
     */
    private int statistics(int mode) {
        int count = 0;
        for (List<Pic> list : dataList) {
            for (Pic pic : list) {
                if (PicFlag.SELECTED.equals(pic.getState())) {
                    if (mode == 1) {
                        selectLists.add(pic);
                    }
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 数量校验
     *
     * @param isAdd 是否添加到选中列表
     * @return
     */
    private Boolean verifyCount(boolean isAdd) {
        int count;
        if (isAdd) {
            count = statistics(1);
        } else {
            count = statistics(0);
        }
        if (count == 0) {
            ToastUtils.makeText(this, "您还未选择图片").show();
            return true;
        }
        return false;
    }

    private void refreshData() {
        int count = statistics(0);
        if (count == 0) {
            txt_complate.setText("未选择");
        } else {
            txt_complate.setText("完成(" + count + ")");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.e("zbt", "requestCode:" + requestCode + ",resultCode:" + resultCode);
        //清除原来的选中图片
        selectLists = new ArrayList<>();
        switch (resultCode) {
            case IntentFlag.PICTURE_RESULT: {
                Serializable serializable = data.getExtras().getSerializable(IntentFlag.PICTURE_SELECT);
                PicSelectModel picSelectModel = (PicSelectModel) serializable;
                List<Pic> newSelectLists = picSelectModel.getSelectLists();
                //先简单处理
                if (dataList == null) {
                    getPic();
                }
                for (int i = 0; i < dataList.size(); i++) {
                    for (int j = 0; j < dataList.get(i).size(); j++) {
                        for (int k = 0; k < newSelectLists.size(); k++) {
                            if (dataList.get(i).get(j).getPicId().equals(newSelectLists.get(k).getPicId())) {
                                dataList.get(i).get(j).setState(newSelectLists.get(k).getState());
                            }
                        }
                    }
                }
                refreshData();
                adapter.notifyDataSetChanged();
                break;
            }
            default:
                break;
        }
    }
}

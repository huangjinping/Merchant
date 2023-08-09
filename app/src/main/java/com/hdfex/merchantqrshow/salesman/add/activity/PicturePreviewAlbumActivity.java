package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.commodity.Pic;
import com.hdfex.merchantqrshow.bean.salesman.commodity.PicFlag;
import com.hdfex.merchantqrshow.bean.salesman.commodity.PicSelectModel;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.view.photoview.PhotoView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 浏览照片
 * Created by maidou521 on 2016/9/1.
 */
public class PicturePreviewAlbumActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager fvp_pager;
    private List<Pic> allPics;
    private ArrayList<View> viewList;
    private List<Pic> selectLists;
    private TextView tv_count;
    private LinearLayout ll_back;
    private TextView tv_complete;
    private LinearLayout ll_select;
    private int totalCount;
    private int selectCount;
    private ImageView iv_select;
    private Intent comIntent;
    private RelativeLayout rl_bottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_album);
        initView();
        initData();
    }



    private void initData() {
        initStatusBarColor(R.color.black);
        comIntent = getIntent();
        if ("PreviewActivity".equals(comIntent.getStringExtra(IntentFlag.CONFORM))) {
            tv_complete.setVisibility(View.GONE);
            rl_bottom.setVisibility(View.GONE);
        }

        getPic();
        getViewList();






        PagerAdapter adapter = new MyPageAdapter(viewList);
        fvp_pager.setAdapter(adapter);

        int position = comIntent.getIntExtra(IntentFlag.POSITION, 0);
        fvp_pager.setCurrentItem(position);

        tv_count.setText("" + ++position + "/" + totalCount);
        tv_count.setText("" + 1 + "/" + totalCount);
        tv_complete.setText("完成(" + totalCount + "/" + totalCount + ")");
    }

    /**
     * 获取图片
     */
    private void getViewList() {
        viewList = new ArrayList<>();
        Drawable nompic = getResources().getDrawable(R.mipmap.ic_defoult);
        for (Pic pic : selectLists) {
            PhotoView photoView = new PhotoView(this);
            photoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            RequestManager rm = Glide.with(this);
            DrawableTypeRequest dtr = null;
            if (pic.getImageFile() != null) {
                dtr = rm.load(pic.getImageFile());
            } else {
                dtr = rm.load(pic.getPicUrl());
            }
            dtr.error(nompic)
                    .placeholder(nompic)
                    .into(photoView);
            viewList.add(photoView);
        }
    }

    /**
     * 获取意图信息
     */
    private void getPic() {
        Serializable serializable = comIntent.getExtras().getSerializable(IntentFlag.PICTURE_SELECT);
        PicSelectModel picSelectModel = (PicSelectModel) serializable;
        allPics = new ArrayList<>();
        List<List<Pic>> dataList = picSelectModel.getDataList();
        if (dataList != null) {
            for (List<Pic> list : dataList) {
                allPics.addAll(list);
            }
        }
        selectLists = picSelectModel.getSelectLists();
        totalCount = selectLists.size();
        selectCount = totalCount;
    }

    private void initView() {
        fvp_pager = (ViewPager) findViewById(R.id.fvp_pager);
        tv_count = (TextView) findViewById(R.id.tv_count);
        iv_select = (ImageView) findViewById(R.id.iv_select);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_complete = (TextView) findViewById(R.id.tv_complete);
        tv_complete.setOnClickListener(this);
        ll_select = (LinearLayout) findViewById(R.id.ll_select);
        ll_select.setOnClickListener(this);
        fvp_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                refreshData(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rl_bottom = (RelativeLayout) findViewById(R.id.rl_bottom);
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_back:
                goback();
                break;
            case R.id.ll_select:
                refreshData();
                break;
            case R.id.tv_complete:
                ToastUtils.makeText(this, "提交").show();
                break;
        }
    }

    private void goback() {
        Intent mIntent = new Intent();
        PicSelectModel picSelectModel = new PicSelectModel();
        picSelectModel.setSelectLists(selectLists);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.PICTURE_SELECT, picSelectModel);
        mIntent.putExtras(bundle);
        this.setResult(IntentFlag.PICTURE_RESULT, mIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(0x123, null);
        finish();
    }

    private void refreshData() {
        if (PicFlag.SELECTED.equals(selectLists.get(fvp_pager.getCurrentItem()).getState())) {
            selectLists.get(fvp_pager.getCurrentItem()).setState(PicFlag.NORMAL);
            selectCount--;
            iv_select.setImageResource(R.mipmap.ic_check_gray);
        } else {
            selectLists.get(fvp_pager.getCurrentItem()).setState(PicFlag.SELECTED);
            selectCount++;
            iv_select.setImageResource(R.mipmap.ic_check_light);
        }
        tv_complete.setText("完成(" + selectCount + "/" + totalCount + ")");
    }

    private void refreshData(int position) {
        if (PicFlag.SELECTED.equals(selectLists.get(position).getState())) {
            iv_select.setImageResource(R.mipmap.ic_check_light);
        } else {
            iv_select.setImageResource(R.mipmap.ic_check_gray);
        }
        tv_count.setText("" + ++position + "/" + totalCount);
    }

    /**
     * ViewPager的适配器
     */
    class MyPageAdapter extends PagerAdapter {

        private ArrayList<View> listViews;

        private int size;

        public MyPageAdapter(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public void setListViews(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public int getCount() {
            return size;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(listViews.get(arg1 % size));
        }

        public void finishUpdate(View arg0) {
        }

        public Object instantiateItem(View arg0, int arg1) {
            try {
                ((ViewPager) arg0).addView(listViews.get(arg1 % size), 0);

            } catch (Exception e) {
            }
            return listViews.get(arg1 % size);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }
}

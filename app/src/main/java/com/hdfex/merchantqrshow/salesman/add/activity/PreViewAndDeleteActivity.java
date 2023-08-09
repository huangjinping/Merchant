package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Context;
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
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageData;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageModel;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.view.photoview.PhotoView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by harrishuang on 2016/11/15.
 */

public class PreViewAndDeleteActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager fvp_pager;
    private ArrayList<View> viewList;
    private ArrayList<ImageModel> selectLists;
    private TextView tv_count;
    private LinearLayout ll_back;
    private TextView tv_complete;
    private LinearLayout ll_select;
    private int totalCount;
    private int selectCount;
    private ImageView iv_select;
    private Intent comIntent;
    private RelativeLayout rl_bottom;
    private int position;
    private ImagePageAdapter adapter;
    private List<ImageModel> removeList;
    private  int rowType;


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
        if (IntentFlag.INTENT_PIC.equals(comIntent.getStringExtra(IntentFlag.INTENT_PIC))) {
            rl_bottom.setVisibility(View.GONE);
        }
        getPic();
        getViewList();
//         adapter = new MyPageAdapter(viewList);
        adapter = new ImagePageAdapter(this, selectLists, Glide.with(this));
        fvp_pager.setAdapter(adapter);
        fvp_pager.removeAllViews();
        int position = comIntent.getIntExtra(IntentFlag.POSITION, 0);
        fvp_pager.setCurrentItem(position);
        tv_count.setText("" + ++position + "/" + totalCount);
        tv_count.setText("" + 1 + "/" + totalCount);
        tv_complete.setText("删除");
    }

    /**
     * 获取图片
     */
    private void getViewList() {
        viewList = new ArrayList<>();
        removeList = new ArrayList<>();
        Drawable nompic = getResources().getDrawable(R.mipmap.ic_defoult);
        for (ImageModel imageModel : selectLists) {
            PhotoView photoView = new PhotoView(this);
            photoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            RequestManager rm = Glide.with(this);
            DrawableTypeRequest dtr = null;
            if (imageModel.getImageFile() != null) {
                dtr = rm.load(imageModel.getImageFile());
            } else {
                dtr = rm.load(imageModel.getAbsolutePath());
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
        ImageData imageData = (ImageData) serializable;
        selectLists = new ArrayList<>();
        selectLists.addAll(imageData.getImageModelList());
        rowType=selectLists.get(0).getRow();
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
        switch (v.getId()) {
            case R.id.ll_back:
                goback();
                break;
            case R.id.ll_select:

                break;
            case R.id.tv_complete:
                removeList.add(selectLists.get(position));
                selectLists.remove(position);
                adapter.notifyDataSetChanged();
                if (selectLists.size()==0){
                    goback();
                }
                break;
        }
    }

    /**
     * 后退数据
     */
    private void goback() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        ImageData imageData=new ImageData();
        imageData.setImageModelList(selectLists);
        bundle.putSerializable(IntentFlag.INTENT_PIC, imageData);
        bundle.putInt(IntentFlag.INTENT_ROW_TYPE,rowType);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goback();
    }


    private void refreshData(int position) {
        this.position = position;
        tv_count.setText("" + ++position + "/" + selectLists.size());


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


    private class ImagePageAdapter extends PagerAdapter {
        private Context mContext;
        private ArrayList<ImageModel> mMedias;
        private RequestManager mRequestManager;

        public ImagePageAdapter(Context context, ArrayList<ImageModel> medias, RequestManager requestManager) {
            mContext = context;
            mMedias = medias;
            mRequestManager = requestManager;
        }

        @Override
        public int getCount() {
            return mMedias == null ? 0 : mMedias.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            uk.co.senab.photoview.PhotoView photoView = new uk.co.senab.photoview.PhotoView(mContext);
            Drawable d = mContext.getResources().getDrawable(R.mipmap.ic_defoult);

            ImageModel imageModel = mMedias.get(position);
            if (imageModel.getImageFile() != null) {
                mRequestManager.load(imageModel.getImageFile())
                        .error(d)
                        .placeholder(d)
                        .into(photoView);
            } else {
                mRequestManager.load(imageModel.getPath()).error(d)
                        .placeholder(d)
                        .into(photoView);

            }

            container.addView(photoView);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        private int mChildCount = 0;

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount > 0) {
                mChildCount--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }
    }
}

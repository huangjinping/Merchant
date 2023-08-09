package com.hdfex.merchantqrshow.salesman.order.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.home.CaseWithCase;
import com.hdfex.merchantqrshow.bean.salesman.home.CaseWithCaseIdListBean;
import com.hdfex.merchantqrshow.bean.salesman.home.CaseWithCaseIdListResponse;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.widget.picker.ScreenUtils;
import com.hdfex.merchantqrshow.widget.picker.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2019/11/8.
 * email : huangjinping1000@163.com
 */
public class ChangeFundSourceFragment extends BaseFragment implements View.OnClickListener {
    /**
     * The Text size.
     */
    protected int textSize = WheelView.TEXT_SIZE;
    /**
     * The Text color normal.
     */
    protected int textColorNormal = WheelView.TEXT_COLOR_NORMAL;
    /**
     * The Text color focus.
     */
    protected int textColorFocus = WheelView.TEXT_COLOR_FOCUS;
    /**
     * The Line color.
     */
    protected int lineColor = WheelView.LINE_COLOR;
    /**
     * The Line visible.
     */
    protected boolean lineVisible = true;
    /**
     * The Offset.
     */
    /**
     * The constant MATCH_PARENT.
     */
    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    /**
     * The constant WRAP_CONTENT.
     */
    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

    protected int screenWidthPixels;
    protected int screenHeightPixels;
    protected int offset = WheelView.OFF_SET;
    private ImageView iv_back;
    private RecyclerView rec_change_left;
    private RecyclerView rec_change_right;
    private Button btn_submit;
    private List<String> dataList;
    private Context mContext;
    private View v_background;
    private LinearLayout layout_case_container;
    private List<CaseWithCaseIdListBean> caseWithCaseIdList;
    private List<CaseWithCase> caseList;
    private CaseWithCase currentCaseWithCase;
    private OnSelectedListener onSelectedListener;

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    public static ChangeFundSourceFragment getInstance(Activity activity) {
        ChangeFundSourceFragment changeFundSourceFragment = new ChangeFundSourceFragment();
        return changeFundSourceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_change_fundsource, container, false);
        initView(view);

        Bundle bundle = getArguments();
        CaseWithCaseIdListResponse response = (CaseWithCaseIdListResponse) bundle.getSerializable(IntentFlag.INTENT_CASE_WITH_CASEID);
        caseWithCaseIdList = response.getResult();

        List<String> title1 = new ArrayList<>();
        for (int i = 0; i < caseWithCaseIdList.size(); i++) {
            title1.add(caseWithCaseIdList.get(i).getCapitalName()+"");
        }


        dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            dataList.add("===11======left=");
        }
        rec_change_left.setLayoutManager(new LinearLayoutManager(getActivity()));
        rec_change_right.setLayoutManager(new LinearLayoutManager(getActivity()));
        ChangeFundSourceAdapter changeLeftFundSourceAdapter = new ChangeFundSourceAdapter(dataList);
        ChangeFundSourceAdapter changeRightFundSourceAdapter = new ChangeFundSourceAdapter(dataList);
        rec_change_left.setAdapter(changeLeftFundSourceAdapter);
        rec_change_right.setAdapter(changeRightFundSourceAdapter);

        textSize = 13;
        Activity activity = getActivity();
        DisplayMetrics displayMetrics = ScreenUtils.displayMetrics(activity);
        screenWidthPixels = displayMetrics.widthPixels;
        screenHeightPixels = displayMetrics.heightPixels;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidthPixels / 3, WRAP_CONTENT);
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        final WheelView leftView = new WheelView(activity);
        final WheelView rightView = new WheelView(activity);
        leftView.setLayoutParams(params);
        leftView.setTextSize(10);
        leftView.setTextColor(textColorNormal, textColorFocus);
        leftView.setLineVisible(lineVisible);
        leftView.setLineColor(lineColor);
        leftView.setOffset(offset);
        leftView.setItems(title1, 0);
        leftView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                rightView.setItems(getNumItem(caseWithCaseIdList, selectedIndex), 0);
            }
        });
        leftView.setBackgroundColor(getActivity().getResources().getColor(R.color.red_light));


        LinearLayout.LayoutParams  params1 = new LinearLayout.LayoutParams(screenWidthPixels * 3 / 4, WRAP_CONTENT);
        layout.addView(leftView);
        rightView.setLayoutParams(params1);
        rightView.setTextSize(textSize);
        rightView.setTextColor(textColorNormal, textColorFocus);
        rightView.setLineVisible(lineVisible);
        rightView.setLineColor(lineColor);
        rightView.setOffset(offset);
        rightView.setBackgroundColor(getActivity().getResources().getColor(R.color.blue_light));
        rightView.setItems(getNumItem(caseWithCaseIdList, 0), 0);
        rightView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {

                try {
                    currentCaseWithCase = caseList.get(selectedIndex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        layout.addView(rightView);
        layout_case_container.addView(layout);
        return view;
    }

    private void initView(View view) {
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        rec_change_left = (RecyclerView) view.findViewById(R.id.rec_change_left);
        rec_change_right = (RecyclerView) view.findViewById(R.id.rec_change_right);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        v_background = (View) view.findViewById(R.id.v_background);
        v_background.setOnClickListener(this);
        layout_case_container = (LinearLayout) view.findViewById(R.id.layout_case_container);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:

                if (onSelectedListener != null && currentCaseWithCase != null) {
                    onSelectedListener.onResult(currentCaseWithCase);
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    showToast("专案选择异常请重新选择");
                }

                break;
            case R.id.iv_back:
            case R.id.v_background:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }


    private class ChangeFundSourceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<String> rDataList;
        private Integer mSelected;

        public void setmSelected(int mSelected) {
            this.mSelected = mSelected;
        }

        public ChangeFundSourceAdapter(List<String> rDataList) {
            this.rDataList = rDataList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_changefundsource, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ViewHolder mViewHolder = (ViewHolder) holder;
            String target = rDataList.get(position);
            mViewHolder.txt_target.setText("");
            if (!TextUtils.isEmpty(target)) {
                mViewHolder.txt_target.setText(target);
            }
            if (mSelected != null && position == mSelected) {
                mViewHolder.txt_target.setTextColor(ContextCompat.getColor(mContext, R.color.blue_light));
            } else {
                mViewHolder.txt_target.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            }
            mViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelected = position;
                    ChangeFundSourceAdapter.this.notifyDataSetChanged();
                }
            });

        }

        @Override
        public int getItemCount() {
            return rDataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public View rootView;
            public TextView txt_target;

            public ViewHolder(View rootView) {
                super(rootView);
                this.rootView = rootView;
                this.txt_target = (TextView) rootView.findViewById(R.id.txt_target);
            }

        }
    }


    public interface OnSelectedListener {
        void onResult(CaseWithCase caseWithCase);
    }


    /**
     * 提交其他问题
     *
     * @param i
     * @param caseWithCaseIdList
     * @param i
     * @return
     */
    public List<String> getNumItem(List<CaseWithCaseIdListBean> caseWithCaseIdList, int i) {
        List<String> item = new ArrayList<>();
        CaseWithCaseIdListBean caseBean = caseWithCaseIdList.get(i);
        caseList = caseBean.getCaseList();
        currentCaseWithCase = null;
        for (int j = 0; j < caseList.size(); j++) {
            CaseWithCase caseWithCase = caseList.get(j);
            if (i == 0) {
                currentCaseWithCase = caseWithCase;
            }
            item.add(caseWithCase.getCaseName()+"");
        }
        return item;
    }
}

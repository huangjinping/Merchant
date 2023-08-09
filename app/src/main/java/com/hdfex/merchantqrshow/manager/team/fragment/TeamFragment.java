package com.hdfex.merchantqrshow.manager.team.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.manager.team.PersonRecord;
import com.hdfex.merchantqrshow.bean.manager.team.PersonalPerform;
import com.hdfex.merchantqrshow.bean.manager.team.TeamPerform;
import com.hdfex.merchantqrshow.bean.manager.team.TeamResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.manager.team.activity.PersonnelManagerActivity;
import com.hdfex.merchantqrshow.manager.team.adapter.AchievementGridAdapter;
import com.hdfex.merchantqrshow.manager.team.adapter.AchievementListAdapter;
import com.hdfex.merchantqrshow.manager.team.adapter.ChartAdapter;
import com.hdfex.merchantqrshow.mvp.contract.TeamContract;
import com.hdfex.merchantqrshow.mvp.presenter.TeamPresenter;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.DialogUtils.Effectstype;
import com.hdfex.merchantqrshow.utils.DialogUtils.effects.BaseEffects;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.view.MGridView.MGridView;
import com.hdfex.merchantqrshow.view.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/5/27.
 * email : huangjinping@hdfex.com
 */

public class TeamFragment extends BaseFragment implements View.OnClickListener, TeamContract.View {

    private LinearLayout layout_commpany;
    private NoScrollListView lisv_line;
    private ChartAdapter adapter;
    private List<TeamPerform> teamPerformList;
    private MGridView grv_achievement;
    private NoScrollListView list_achievement;
    private TeamContract.Presenter presenter;
    private TextView txt_bussPersonCount;
    private TextView txt_teamCount;
    private TextView txt_outRate;
    private TextView txt_inRate;
    private User user;
    private TextView txt_team;
    private AchievementGridAdapter gridAdapter;
    private List<PersonalPerform> personalPerformList;
    private AchievementListAdapter achievementListAdapter;
    private List<PersonRecord> personRecordList;
    private RelativeLayout layout_anmation;
    private TextView txt_bussiness_name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team, container, false);
        initView(view);

        user = UserManager.getUser(getActivity());
        txt_bussiness_name.setText(user.getCurrentBussinessName());
        presenter = new TeamPresenter();
        presenter.attachView(this);
        teamPerformList = new ArrayList<>();
        adapter = new ChartAdapter(teamPerformList, getActivity());
        lisv_line.setAdapter(adapter);
        presenter.requestTeam(user);
        presenter.requestPersonRecordList(user);
        /**
         * 个人排名
         */
        personalPerformList = new ArrayList<>();
        personalPerformList.add(new PersonalPerform());
        personalPerformList.add(new PersonalPerform());
        personalPerformList.add(new PersonalPerform());
        gridAdapter = new AchievementGridAdapter(getActivity(), personalPerformList);
        grv_achievement.setAdapter(gridAdapter);
        personRecordList = new ArrayList<>();
        personRecordList.add(new PersonRecord());
        personRecordList.add(new PersonRecord());
        personRecordList.add(new PersonRecord());
        personRecordList.add(new PersonRecord());
        personRecordList.add(new PersonRecord());
        personRecordList.add(new PersonRecord());
        personRecordList.add(new PersonRecord());
        personRecordList.add(new PersonRecord());
        personRecordList.add(new PersonRecord());
        personRecordList.add(new PersonRecord());

        achievementListAdapter = new AchievementListAdapter(getActivity(), personRecordList);
        list_achievement.setAdapter(achievementListAdapter);

        return view;
    }


    private void initView(View view) {
        layout_commpany = (LinearLayout) view.findViewById(R.id.layout_commpany);
        layout_commpany.setOnClickListener(this);
        lisv_line = (NoScrollListView) view.findViewById(R.id.lisv_line);
        grv_achievement = (MGridView) view.findViewById(R.id.grv_achievement);
        list_achievement = (NoScrollListView) view.findViewById(R.id.list_achievement);
        txt_bussPersonCount = (TextView) view.findViewById(R.id.txt_bussPersonCount);
        txt_bussPersonCount.setOnClickListener(this);
        txt_teamCount = (TextView) view.findViewById(R.id.txt_teamCount);
        txt_teamCount.setOnClickListener(this);
        txt_outRate = (TextView) view.findViewById(R.id.txt_outRate);
        txt_outRate.setOnClickListener(this);
        txt_inRate = (TextView) view.findViewById(R.id.txt_inRate);
        txt_inRate.setOnClickListener(this);
        txt_team = (TextView) view.findViewById(R.id.txt_team);
        txt_team.setOnClickListener(this);
        layout_anmation = (RelativeLayout) view.findViewById(R.id.layout_anmation);
        layout_anmation.setOnClickListener(this);
        layout_anmation.setTag(true);
        txt_bussiness_name = (TextView) view.findViewById(R.id.txt_bussiness_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_commpany:
                EventRxBus.getInstance().post(IntentFlag.INDEX_DRAWER, IntentFlag.INDEX_DRAWER);
                break;
            case R.id.txt_team:
                PersonnelManagerActivity.startAction(getActivity());
                break;
            case R.id.layout_anmation:
                if ((boolean) v.getTag()) {
                    v.setTag(false);
                    list_achievement.setVisibility(View.VISIBLE);
                    grv_achievement.setVisibility(View.GONE);
                    start(Effectstype.RotateLeft, list_achievement);

                } else {
                    v.setTag(true);
                    list_achievement.setVisibility(View.GONE);
                    grv_achievement.setVisibility(View.VISIBLE);
                    start(Effectstype.RotateLeft, grv_achievement);
                }
                break;
        }
    }

    /**
     * 开始动画
     *
     * @param type
     * @param view
     */
    private void start(Effectstype type, View view) {
        BaseEffects animator = type.getAnimator();
        animator.setDuration(Math.abs(1000));
        animator.start(view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void returnTeamResult(TeamResult result) {
        teamPerformList.clear();
        teamPerformList.addAll(result.getTeamPerformList());
        adapter.notifyDataSetChanged();

        personalPerformList.clear();
        if (personalPerformList != null) {
            personalPerformList.addAll(result.getPersonalPerformList());
        }
        gridAdapter.notifyDataSetChanged();
        if (!TextUtils.isEmpty(result.getBussPersonCount())) {
            txt_bussPersonCount.setText(result.getBussPersonCount() + "");
        }
        if (!TextUtils.isEmpty(result.getTeamCount())) {
            txt_teamCount.setText(result.getTeamCount() + "");
        }

        if (!TextUtils.isEmpty(result.getOutRate())) {
            txt_outRate.setText(result.getOutRate()+"%");
        }
        if (!TextUtils.isEmpty(result.getInRate())) {
            txt_inRate.setText(result.getInRate()+"%");
        }
    }

    @Override
    public void returnPersonRecordList(List<PersonRecord> result) {
        personRecordList.clear();
        if (result != null) {
            personRecordList.addAll(result);
        }
        achievementListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (this.isResumed() && !hidden) {
            reLoad();
        }
    }

    /***
     * 重新刷新
     */
    public void reLoad() {
        user = UserManager.getUser(getActivity());
        txt_bussiness_name.setText(user.getCurrentBussinessName());
        presenter.requestTeam(user);
        presenter.requestPersonRecordList(user);
    }
}

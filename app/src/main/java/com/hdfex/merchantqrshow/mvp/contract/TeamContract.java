package com.hdfex.merchantqrshow.mvp.contract;

import com.hdfex.merchantqrshow.bean.manager.team.PersonRecord;
import com.hdfex.merchantqrshow.bean.manager.team.TeamResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.MvpView;

import java.util.List;

/**
 * author Created by harrishuang on 2017/6/2.
 * email : huangjinping@hdfex.com
 */

public interface TeamContract {

    interface View extends MvpView {

        void returnTeamResult(TeamResult result);

        void returnPersonRecordList(List<PersonRecord> result);
    }

    abstract class Presenter extends BasePresenter<TeamContract.View> {

        public abstract void requestTeam(User user);

        public abstract void requestPersonRecordList(User user);
    }
}

package com.hdfex.merchantqrshow.mvp.dagger.module;

import com.hdfex.merchantqrshow.mvp.contract.TaobaoAuthContract;
import com.hdfex.merchantqrshow.mvp.presenter.TaobaoAuthPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * author Created by harrishuang on 2017/6/26.
 * email : huangjinping@hdfex.com
 */

@Module
public class TaobaoAuthModule {


    @Provides// 关键字，标明该方法提供依赖对象
    TaobaoAuthContract.Presenter providerPerson() {
        //提供Person对象
        return new TaobaoAuthPresenter();
    }



}

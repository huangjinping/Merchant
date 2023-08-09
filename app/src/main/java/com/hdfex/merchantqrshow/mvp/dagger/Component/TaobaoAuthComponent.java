package com.hdfex.merchantqrshow.mvp.dagger.Component;

import com.hdfex.merchantqrshow.comm.taobao.TaobaoAuthActivity;
import com.hdfex.merchantqrshow.mvp.dagger.module.TaobaoAuthModule;

import dagger.Component;

/**
 * author Created by harrishuang on 2017/6/26.
 * email : huangjinping@hdfex.com
 */

@Component(modules = TaobaoAuthModule.class)
public interface TaobaoAuthComponent {

    void inject(TaobaoAuthActivity activity);

}

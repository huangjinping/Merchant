package com.hdfex.merchantqrshow.utils;

/**
 * Created by harrishuang on 2017/4/24.
 */

public interface OnComTabSelectListener {

    /**
     * 是不是选中
     * @param position
     */
    void onTabSelect(int position);

    /**
     * 重复选择的开关
     * @param position
     * @param open
     */
    void onTabReselect(int position,boolean open);

}

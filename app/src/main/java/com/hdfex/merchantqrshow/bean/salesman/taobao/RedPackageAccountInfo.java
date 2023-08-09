package com.hdfex.merchantqrshow.bean.salesman.taobao;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/7/7.
 * email : huangjinping@hdfex.com
 */

public class RedPackageAccountInfo extends BaseBean {




    public  static  final String BAND_ALIPAY_FLAG_YES="0";
    public  static  final String BAND_ALIPAY_FLAG_NO="1";
    /**
     * 开启体现密码
     */
    public  static  final  String WITH_DRAWAL_SWITCH_YES="1";
    /**
     *关闭体现密码
     */
    public  static  final  String WITH_DRAWAL_SWITCH_NO="0";


    private String balAmt;
    private String band_alipay_flag;
    private  String nickName;
    private String avatar;

    private String withdrawalSwitch;

    public String getWithdrawalSwitch() {
        return withdrawalSwitch;
    }

    public void setWithdrawalSwitch(String withdrawalSwitch) {
        this.withdrawalSwitch = withdrawalSwitch;
    }

    public String getBalAmt() {
        return balAmt;
    }

    public void setBalAmt(String balAmt) {
        this.balAmt = balAmt;
    }

    public String getBand_alipay_flag() {
        return band_alipay_flag;
    }

    public void setBand_alipay_flag(String band_alipay_flag) {
        this.band_alipay_flag = band_alipay_flag;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

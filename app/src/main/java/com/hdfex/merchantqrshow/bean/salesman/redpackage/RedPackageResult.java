package com.hdfex.merchantqrshow.bean.salesman.redpackage;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * author Created by harrishuang on 2017/7/3.
 * email : huangjinping@hdfex.com
 */

public class RedPackageResult extends BaseBean {

    /**
     * 淘宝授权
     */
    public static final String BAND_ALIPAY_FLAG_YES = "0";
    /**
     * 没有淘宝授权
     */
    public static final String BAND_ALIPAY_FLAG_NO = "1";


    private String balAmt;
    private String band_alipay_flag;

    private String nickName;
    private String avatar;
    private List<RedPackage> redPacketList;

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

    public List<RedPackage> getRedPacketList() {
        return redPacketList;
    }

    public void setRedPacketList(List<RedPackage> redPacketList) {
        this.redPacketList = redPacketList;
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

    @Override
    public String toString() {
        return "RedPackageResult{" +
                "balAmt='" + balAmt + '\'' +
                ", band_alipay_flag='" + band_alipay_flag + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", redPacketList=" + redPacketList +
                '}';
    }
}

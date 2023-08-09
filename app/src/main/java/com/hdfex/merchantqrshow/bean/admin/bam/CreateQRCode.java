package com.hdfex.merchantqrshow.bean.admin.bam;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/12/29.
 * email : huangjinping@hdfex.com
 */

public class CreateQRCode extends BaseBean {

    private String qrcodeUrl;

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }
}

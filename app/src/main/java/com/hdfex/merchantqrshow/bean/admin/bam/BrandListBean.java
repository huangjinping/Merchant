package com.hdfex.merchantqrshow.bean.admin.bam;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/12/7.
 * email : huangjinping@hdfex.com
 */

public class BrandListBean extends BaseBean {


    /**
     * brand : 苹果
     * brandCode : 020310
     */

    private String brand;
    private String brandCode;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }
}

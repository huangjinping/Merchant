package com.hdfex.merchantqrshow.bean.salesman.commodity;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * Created by harrishuang on 16/9/6.
 */

public class UploadInfo  extends BaseBean{

    private List<Pic> liushui;
    private  List<Pic> others;
    private  List<Pic> zichan;

    public List<Pic> getLiushui() {
        return liushui;
    }

    public void setLiushui(List<Pic> liushui) {
        this.liushui = liushui;
    }

    public List<Pic> getOthers() {
        return others;
    }

    public void setOthers(List<Pic> others) {
        this.others = others;
    }

    public List<Pic> getZichan() {
        return zichan;
    }

    public void setZichan(List<Pic> zichan) {
        this.zichan = zichan;
    }
}

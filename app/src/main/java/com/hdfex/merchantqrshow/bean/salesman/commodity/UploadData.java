package com.hdfex.merchantqrshow.bean.salesman.commodity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by harrishuang on 16/9/12.
 */

public class UploadData implements Serializable {
    private List<ImageModel> bankList;
    private List<ImageModel> proveList;

    public List<ImageModel> getBankList() {
        return bankList;
    }

    public void setBankList(List<ImageModel> bankList) {
        this.bankList = bankList;
    }

    public List<ImageModel> getProveList() {
        return proveList;
    }

    public void setProveList(List<ImageModel> proveList) {
        this.proveList = proveList;
    }
}

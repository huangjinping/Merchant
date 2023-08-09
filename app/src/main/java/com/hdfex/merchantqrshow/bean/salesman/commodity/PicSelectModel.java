package com.hdfex.merchantqrshow.bean.salesman.commodity;

import com.hdfex.merchantqrshow.bean.BaseBean;

import java.util.List;

/**
 * Created by maidou521 on 2016/9/1.
 */
public class PicSelectModel extends BaseBean {
    private List<List<Pic>> dataList;
    private List<Pic> selectLists;

    public List<Pic> getSelectLists() {
        return selectLists;
    }

    public void setSelectLists(List<Pic> selectLists) {
        this.selectLists = selectLists;
    }

    public List<List<Pic>> getDataList() {
        return dataList;
    }

    public void setDataList(List<List<Pic>> dataList) {
        this.dataList = dataList;
    }
}

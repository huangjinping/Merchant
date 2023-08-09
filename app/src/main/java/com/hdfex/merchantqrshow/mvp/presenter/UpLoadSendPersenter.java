package com.hdfex.merchantqrshow.mvp.presenter;

import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageModel;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.UpLoadSendContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by harrishuang on 2017/4/7.
 */

public class UpLoadSendPersenter extends UpLoadSendContract.Presenter {


    @Override
    public void submitSendInfo(User user, String applyId, List<ImageModel> bankList) {
        if (bankList.size() == 1) {
            getmMvpView().showToast("请上传照片");
            return;
        }
        List<ImageModel> tempList = new ArrayList<>();
        tempList.addAll(bankList);
        StringBuffer buffer = new StringBuffer();
        tempList.remove(0);
        for (ImageModel imageModel : tempList) {
            buffer.append(imageModel.getAbsolutePath());
            buffer.append(",");
        }
        if (buffer.length()>1){
            buffer.deleteCharAt(buffer.length() - 1);
        }
        OkHttpUtils.post().url(NetConst.SUBMIT_SENDINFO)
                .addParams("id", user.getId())
                .addParams("applyId", applyId)
                .addParams("token", user.getToken())
                .addParams("files", buffer.toString())
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        try {

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        try {
                            getmMvpView().showWebEirr(e);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            if (getmMvpView().checkResonse(response)) {
                                getmMvpView().onSuccess();
                            }
                        } catch (Exception e) {
                            onError(null, e);
                            e.printStackTrace();
                        }
                    }
                });
    }
}

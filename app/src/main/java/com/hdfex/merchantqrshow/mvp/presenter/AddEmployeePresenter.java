package com.hdfex.merchantqrshow.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.hdfex.merchantqrshow.bean.Response;
import com.hdfex.merchantqrshow.bean.manager.team.Person;
import com.hdfex.merchantqrshow.bean.manager.team.RegionListResponse;
import com.hdfex.merchantqrshow.bean.manager.team.RegionResult;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.mvp.contract.AddEmployeeContract;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.StringUtils;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.widget.picker.DurationPicker;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2017/12/5.
 * email : huangjinping@hdfex.com
 */

public class AddEmployeePresenter extends AddEmployeeContract.Presenter {
    //联系人ID
    private String contactId;
    //电话号码
    private String phoneNum = "";

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

        if (resultCode == activity.RESULT_OK) {
            Cursor c = null;
            if (data == null) {
                return;
            }

            Uri contentUri = data.getData();
            c = activity.getContentResolver().query(contentUri, null, null, null, null);
            while (c.moveToNext()) {
                //获取联系人ID
                contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                //获取联系人手机号
                Cursor phone = activity.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                                + contactId, null, null);
                while (phone.moveToNext()) {
                    phoneNum = phone.getString(phone.getColumnIndex("data1"));
                    if (phoneNum != null) {
                        String name = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        Log.d("okhttp", phoneNum);
                        phoneNum = StringUtils.formatInsertPhone(phoneNum);
                        phoneNum = String.valueOf(phoneNum.trim().replace(" ", "").replace("+", ""));
                        Log.d("okhttp", phoneNum);

//                        StringUtils.formatInsertPhone()
                        getmMvpView().setPhoneMessage(requestCode, name, phoneNum);
                        return;
                    } else {
                        ToastUtils.i(activity, "获取联系人失败，请重新获取或手动输入").show();
                    }
                }
            }


        }
    }

    @Override
    public void requestPersonForbid(User user, String id,final String status) {
        OkHttpUtils.post()
                .addParams("flag", status)
                .addParams("token", user.getToken())
                .addParams("id", id)
                .url(NetConst.PERSON_FORBID).build().execute(new StringCallback() {

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                getmMvpView().showProgress();
            }

            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    Response result = GsonUtil.changeGsonToBean(response, Response.class);
                    getmMvpView().returnResult(status);
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                getmMvpView().dismissProgress();
            }
        });

    }

    /**
     * @param context
     * @param requestCode
     */
    public void selectContacts(Activity context, int requestCode) {


        try {

            Intent i = new Intent(Intent.ACTION_PICK);
            i.setData(Uri.parse("content://com.android.contacts/contacts"));
            context.startActivityForResult(i, requestCode);

        } catch (Exception e) {
            ToastUtils.i(context, "您的设备不支持此功能").show();
            e.printStackTrace();
        }

    }

    @Override
    public void openCategoryPicker(Activity context) {
//        CategoryPicker  picker=new CategoryPicker(context);
//        picker.show();


    }

    private List<String> regionList;

    @Override
    public void getRegionList(User user, final Activity context) {

        if (regionList != null && regionList.size() > 0) {
            showDataPicker(context, regionList);
            return;
        }

        OkHttpUtils.post()
                .addParams("token", user.getToken())
                .addParams("id", user.getBussinessId())
                .addParams("userId", user.getId())
                .addParams("type", "1")
                .url(NetConst.REGIONLIST).build().execute(new StringCallback() {

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                getmMvpView().showProgress();
            }

            @Override
            public void onError(Call call, Exception e) {
                getmMvpView().showToast(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (getmMvpView().checkResonse(response)) {
                    RegionListResponse house = GsonUtil.changeGsonToBean(response, RegionListResponse.class);
                    List<RegionResult> result = house.getResult();
                    regionList = new ArrayList<String>();
                    for (int i = 0; i < result.size(); i++) {
                        regionList.add(result.get(i).getRegionName());
                    }
                    showDataPicker(context, regionList);

                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                getmMvpView().dismissProgress();
            }
        });
    }

    /**
     * @param activity
     */
    private void showDataPicker(Activity activity, List<String> regionList) {
        if (regionList == null) {
            return;
        }
        DurationPicker picker = new DurationPicker(activity, regionList);
        picker.setOnDurationPickerSelectedListener(new DurationPicker.OnDurationPickerSelectedListener() {
            @Override
            public void selected(int index, String item) {
                getmMvpView().onRegionReturn(index,item);
            }
        });
        picker.show();
    }



}

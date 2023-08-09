package com.hdfex.merchantqrshow.widget.picker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.ACache;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.log.LogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 获取地址数据并显示地址选择器
 */
public class AddressInitTask extends AsyncTask<String, Void, ArrayList<AddressPicker.Province>> {
    private Activity activity;
    private ProgressDialog dialog;
    private String selectedProvince = "", selectedCity = "", selectedCounty = "";

    private AddressPicker.OnAddressPickListener mOnAddressPickListener;


    public AddressInitTask(Activity activity) {
        this.activity = activity;
        dialog = ProgressDialog.show(activity, null, "正在初始化数据...", true, true);
    }

    public AddressInitTask(Activity activity, AddressPicker.OnAddressPickListener listener) {
        this.activity = activity;
        this.mOnAddressPickListener =listener;
//        dialog = ProgressDialog.show(activity, null, "正在初始化数据...", true, true);
    }

    @Override
    protected ArrayList<AddressPicker.Province> doInBackground(String... params) {
        if (params != null) {
            switch (params.length) {
                case 1:
                    selectedProvince = params[0];
                    break;
                case 2:
                    selectedProvince = params[0];
                    selectedCity = params[1];
                    break;
                case 3:
                    selectedProvince = params[0];
                    selectedCity = params[1];
                    selectedCounty = params[2];
                    break;
                default:
                    break;
            }
        }
        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        try {
            String json = AssetsUtils.readText(activity, "area.json");
            ACache.get(activity).put(IntentFlag.CATCH_CITY,json);
            String asString = ACache.get(activity).getAsString(IntentFlag.CATCH_CITY);
            /**
             * 万一所有的接口都没有对只
             */
            System.out.println("asString"+asString);
            LogUtil.d("asString",asString);
            data.addAll(GsonUtil.stringToArray(json, AddressPicker.Province[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(ArrayList<AddressPicker.Province> result) {
//        dialog.dismiss();
        if (result.size() > 0) {
            AddressPicker picker = new AddressPicker(activity, result);
            picker.setSelectedItem(selectedProvince, selectedCity, selectedCounty);
            picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(String province, String city, String county) {
                    Toast.makeText(activity, province + city + county, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onAddressPickedCode(String provinceCode, String cityCode, String countyCode) {

                }
            });

            if (mOnAddressPickListener!=null){
                picker.setOnAddressPickListener(mOnAddressPickListener);
            }

            picker.show();
        } else {
            Toast.makeText(activity, "数据初始化失败", Toast.LENGTH_SHORT).show();
        }
    }

}

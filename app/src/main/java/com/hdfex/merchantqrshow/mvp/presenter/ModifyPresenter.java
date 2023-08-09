package com.hdfex.merchantqrshow.mvp.presenter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;

import com.hdfex.merchantqrshow.bean.salesman.commodityCreate.TypeModel;
import com.hdfex.merchantqrshow.mvp.BasePresenter;
import com.hdfex.merchantqrshow.mvp.view.ModifyView;
import com.hdfex.merchantqrshow.widget.picker.DatePicker;
import com.hdfex.merchantqrshow.widget.picker.DurationPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harrishuang on 2017/2/16.
 */

public class ModifyPresenter extends BasePresenter<ModifyView> {


    public void dataPicker(List<TypeModel> downpaymentLists, final EditText editext, Activity context) {
        if (downpaymentLists == null || downpaymentLists.size() == 0) {
            return;
        }
        List<String> dataStringlists = new ArrayList<>();
        for (TypeModel typeModel : downpaymentLists) {
            dataStringlists.add(typeModel.getName());
        }
        DurationPicker picker = new DurationPicker(context, dataStringlists, new DurationPicker.OnDurationPickerSelectedListener() {
            @Override
            public void selected(int index, String item) {
                editext.setText(item);
                editext.setTag(index);
            }
        });
        picker.show();

    }


    public void getTime(final EditText edittext, FragmentActivity activity) {
        DatePicker datePicker = new DatePicker(activity);
        datePicker.setOnDateSelectListener(new DatePicker.OnDateSelectListener() {
            @Override
            public void onSelect(String year, String month, String day) {
                StringBuffer sb = new StringBuffer();
                sb.append(year);
                sb.append("-");
                sb.append(String.format("%02d", Integer.parseInt(month)));
                sb.append("-");
                sb.append(String.format("%02d", Integer.parseInt(day)));
                edittext.setText(sb.toString());
            }
        });
        datePicker.show();
    }
}

package com.hdfex.merchantqrshow.view.virtualKeyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.hdfex.merchantqrshow.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * author Created by harrishuang on 2017/7/31.
 * email : huangjinping@hdfex.com
 */

/**
 * 虚拟键盘
 */
public class VirtualKeyboardView extends RelativeLayout {

    private Context context;
    private GridView gridView;
    private ArrayList<Map<String, String>> valueList;

    public VirtualKeyboardView(Context context) {
        this(context, null);
    }

    public VirtualKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = View.inflate(context, R.layout.layout_virtual_keyboard, null);
        valueList = new ArrayList<>();
        gridView = (GridView) view.findViewById(R.id.gv_keybord);

        initValueList();
        setupView();
        addView(view);
    }


    public ArrayList<Map<String, String>> getValueList() {
        return valueList;
    }

    private void initValueList() {

        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", ".");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "");
            }
            valueList.add(map);
        }
    }

    public GridView getGridView() {
        return gridView;
    }

    private void setupView() {
        KeyBoardAdapter keyBoardAdapter = new KeyBoardAdapter(context, valueList);
        gridView.setAdapter(keyBoardAdapter);
    }
}

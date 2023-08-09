package com.hdfex.merchantqrshow.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;

/**
 * author Created by harrishuang on 2017/7/4.
 * email : huangjinping@hdfex.com
 */

public class HuabeiDialog extends Dialog {


    private TextView txt_progress;
    private TextView txt_progress_title;
    private static final int DURATION = 30;
    private int progress = DURATION;

    private boolean isProgress = true;

    public HuabeiDialog(@NonNull Context context) {
        super(context, R.style.widget_dialogbuilder_dialog_untran);
    }


    public static HuabeiDialog getInstance(Context context) {
        HuabeiDialog huabeiDialog = new HuabeiDialog(context);
        return huabeiDialog;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_huabei);
        txt_progress = (TextView) findViewById(R.id.txt_progress);
        txt_progress_title = (TextView) findViewById(R.id.txt_progress_title);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (isProgress) {
                    try {
                        Thread.sleep(1 * 1000);
                        progress--;
                        handler.sendEmptyMessage(progress);
                        if (progress == 0) {
                            return;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            onUpdate(msg.what + "");

        }
    };


    private void onUpdate(String progress) {
        txt_progress.setText(progress + "");
    }


    @Override
    public void dismiss() {
        super.dismiss();
        isProgress = false;

    }


}

package com.hdfex.merchantqrshow.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;


/**
 *自定义弹出框
 */
public class HDAlertDialog extends Dialog {
    //实例
    private static HDAlertDialog instance;
    //上下文
    private static Context tempContext;
    //对话框根view
    private View mDialogView;
    /**
     * 标题
     */
    private TextView dialogTitle;
    //确定按钮
    private Button button1;
    //取消按钮
    private Button button2;
    //信息
    private TextView msg;
    //最外层
    private RelativeLayout main_RelativeLayout;

    private boolean cancelableOnTouchOutside;

    protected HDAlertDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public HDAlertDialog(Context context) {
        super(context);
        init(context);
    }


    public HDAlertDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((WindowManager.LayoutParams) params);
    }


    public static HDAlertDialog getInstance(Context context) {

        if (tempContext != null || !(context.equals(tempContext))) {

            synchronized (HDAlertDialog.class) {
                instance = new HDAlertDialog(context, R.style.widget_dialogbuilder_dialog_untran);
            }
        }
        tempContext = context;

        return instance;
    }

    private void init(Context context) {

        mDialogView = View.inflate(context, R.layout.widget_dialogbuilder_layout, null);

        dialogTitle = (TextView) mDialogView.findViewById(R.id.widget_dialogbuilder_dialog_alertTitle);

        button1 = (Button) mDialogView.findViewById(R.id.widget_dialogbuilder_dialog_btn_button1);
        button2 = (Button) mDialogView.findViewById(R.id.widget_dialogbuilder_dialog_btn_button2);
        msg = (TextView) mDialogView.findViewById(R.id.widget_dialogbuilder_dialog_message);
        main_RelativeLayout = (RelativeLayout) mDialogView.findViewById(R.id.widget_dialogbuilder_dialog_main);

        main_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cancelableOnTouchOutside) {
                    dismiss();
                }
            }
        });

        setContentView(mDialogView);
    }

    /**
     * 点击外部取消
     *
     * @param cancelableOnTouchOutside
     * @return
     */
    public HDAlertDialog withCancelableOnTouchOutside(boolean cancelableOnTouchOutside) {

        this.cancelableOnTouchOutside = cancelableOnTouchOutside;
        return this;
    }


    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public HDAlertDialog withTitle(String title) {
        dialogTitle.setText(title);

        return this;
    }


    public HDAlertDialog withbuton(String buton1, String buton2) {
        button1.setText(buton1);
        button2.setText(buton2);
        return this;
    }

    public HDAlertDialog hideButon1() {
        button1.setVisibility(View.GONE);
        return this;
    }





    /**
     * 设置信息
     *
     * @param msg
     * @return
     */
    public HDAlertDialog withMsg(String msg) {
        this.msg.setText(msg);
        return this;
    }



    /**
     * 设置信息
     *
     * @param msg
     * @return
     */
    public HDAlertDialog withHtmlMsg(String msg) {
        this.msg.setText(Html.fromHtml(msg));

        return this;
    }

    /**
     * 设置确定按钮
     *
     * @param listener
     * @return
     */

    public HDAlertDialog withButton1Onclick(View.OnClickListener listener) {

        if (button1 != null)
            button1.setVisibility(View.VISIBLE);

        button1.setOnClickListener(listener);

        return this;

    }



    /**
     * 设置确定按钮
     *
     * @param listener
     * @return
     */

    public HDAlertDialog withButton1Onclick(String title, View.OnClickListener listener) {

        if (button1 != null){
            button1.setVisibility(View.VISIBLE);

            if (!TextUtils.isEmpty(title)){
               button1.setText(title);
           }
            if (listener==null){

                button1.setVisibility(View.GONE);
            }else {
                button1.setOnClickListener(listener);
            }
        }

        return this;

    }



    public HDAlertDialog withButton2Onclick(View.OnClickListener listener) {

        if (button2 != null){
            button2.setVisibility(View.VISIBLE);
            button2.setOnClickListener(listener);
            if (listener==null){
                button2.setVisibility(View.GONE);
            }
        }
        return this;

    }


}

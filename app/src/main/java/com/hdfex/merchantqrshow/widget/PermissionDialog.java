package com.hdfex.merchantqrshow.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.activity.WebActivity;
import com.hdfex.merchantqrshow.net.NetConst;

/**
 * author Created by harrishuang on 2020-01-06.
 * email : huangjinping1000@163.com
 */
public class PermissionDialog extends Dialog {

    private View.OnClickListener onCancelLitener;
    private View.OnClickListener onConfirmLitener;
    public TextView txt_alert_title;
    public ImageView img_alert_icon;
    public TextView txt_alert_content;
    public Button btn_alert_no;
    public Button btn_alert_yes;
    Context mContext;

    public PermissionDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public PermissionDialog(@NonNull Context context, View.OnClickListener onCancelLitener, View.OnClickListener onConfirmLitener) {
        super(context, R.style.widget_dialogbuilder_dialog_untran);
        mContext = context;

        this.onCancelLitener = onCancelLitener;
        this.onConfirmLitener = onConfirmLitener;
    }

    public PermissionDialog(@NonNull Context context, int themeResId, View.OnClickListener onCancelLitener, View.OnClickListener onConfirmLitener) {
        super(context, R.style.widget_dialogbuilder_dialog_untran);
        mContext = context;

        this.onCancelLitener = onCancelLitener;
        this.onConfirmLitener = onConfirmLitener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_permission_alert);
        this.txt_alert_title = (TextView) findViewById(R.id.txt_alert_title);
        this.img_alert_icon = (ImageView) findViewById(R.id.img_alert_icon);
        this.txt_alert_content = (TextView) findViewById(R.id.txt_alert_content);
        this.btn_alert_no = (Button) findViewById(R.id.btn_alert_no);
        this.btn_alert_yes = (Button) findViewById(R.id.btn_alert_yes);
        this.btn_alert_no.setOnClickListener(onCancelLitener);

        txt_alert_content.setText(getClickableSpan());
        txt_alert_content.setMovementMethod(LinkMovementMethod.getInstance());

        this.btn_alert_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onConfirmLitener != null) {
                    onConfirmLitener.onClick(v);
                }
                PermissionDialog.this.dismiss();
            }
        });
    }


    private SpannableString getClickableSpan() {


        View.OnClickListener l2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.start(v.getContext(), "隐私政策", NetConst.PERMISSION_URL);
            }
        };
        String messaage = "感谢您信任并使用优选加！\n" +
                "       我们依据最新法律要求，更新了隐私政策，特此向您推本提示。\n" +
                "        我们一直采用行业领先的安全防护措施来保护您的信息安全。我们会根据您使用服务的具体功能收取信息（可能涉及账户、设备等信息），我们不会向任何第三方提供您的信息，除非得到您的授权。您可以阅读我们完整的《优选加用户隐私保护政策》（点击链接）了解我们的承诺。";

        SpannableString spanableInfo = new SpannableString(messaage);

        int privacy_start = spanableInfo.length() - 14;
        int privacy_end = spanableInfo.length() - 9;
        spanableInfo.setSpan(new Clickable(l2), privacy_start, privacy_end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanableInfo;
    }


    private void avoidHintColor(View view) {
        if (view instanceof TextView)
            ((TextView) view).setHighlightColor(mContext.getResources().getColor(android.R.color.transparent));
    }


    /**
     * 内部类，用于截获点击富文本后的事件
     */
    class Clickable extends ClickableSpan implements View.OnClickListener {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener mListener) {
            this.mListener = mListener;
        }

        @Override
        public void onClick(View v) {
            avoidHintColor(v);
            mListener.onClick(v);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(mContext.getResources().getColor(R.color.blue_light));
            ds.setUnderlineText(false);    //去除超链接的下划线
        }
    }

}

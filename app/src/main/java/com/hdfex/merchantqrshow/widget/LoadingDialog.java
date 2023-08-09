package com.hdfex.merchantqrshow.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.hdfex.merchantqrshow.R;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * 加载框
 * Created by harrishuang on 16/7/6.
 */
public class LoadingDialog extends Dialog {
    private GifImageView img_gif;
    private Context context;
    
    public LoadingDialog(Context context) {
        super(context, R.style.widget_dialogbuilder_dialog_untran);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_loading);
        img_gif = (GifImageView) findViewById(R.id.img_gif);
        try {
            GifDrawable gifFromAssets = new GifDrawable(context.getAssets(), "ic_loading.gif");
            img_gif.setImageDrawable(gifFromAssets);
            this.setCanceledOnTouchOutside(false);
            this.setCancelable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.hdfex.merchantqrshow.view;

/**
 * Created by harrishuang on 16/10/17.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.hdfex.merchantqrshow.utils.Utils;
import com.hdfex.merchantqrshow.utils.camera.compress.Util;

/**
 * 自定义的Viewloading
 */
public class LoadingView extends View {
    private int count = 1;
    private Paint textPaint = new Paint();
    private String text = "0";
    private int timeLenth = 3;
    private  int  len=3;
    private  OnLoadingListener loadingListener;
    private  boolean  state_end=false;
    Paint paint = new Paint();
    private Context context;
    public void setLoadingListener(OnLoadingListener loadingListener) {
        this.loadingListener = loadingListener;
    }

    public void setTimeLenth(int timeLenth) {
        this.timeLenth = (timeLenth-1);
        text=String.valueOf(timeLenth);
        len=timeLenth;
        invalidate();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;

        init();
    }

    public LoadingView(Context context) {
        super(context);
        this.context=context;
        init();
    }

    private void init() {
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(100);

        paint.setStrokeWidth(Utils.dp2px(context,5));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 2;
        int height = getHeight() / 2;
        int radius = (getWidth() - 20) / 2;

        float startAngle01 = -90;
        float sweepAngle01 = count;
        textPaint.setTextSize(radius*3 / 4);
        RectF rect = new RectF(center - radius, center - radius, center
                + radius, center + radius);
        canvas.drawArc(rect, startAngle01, sweepAngle01, false, paint);
        Rect textRect = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textRect);
        canvas.drawText(text, center - (textRect.width() / 2), height
                + (textRect.height() / 2), textPaint);
        super.onDraw(canvas);
    }
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (state_end){
                return;
            }
            count++;
            if (count >350) {
                count = 0;
                text=String.valueOf(0);
                invalidate();

                if (loadingListener!=null){
                    loadingListener.onFinish();
                }

                return;
            }
            if (count % (360 / len) == 0) {
                int temp = timeLenth--;
                text = String.valueOf(temp);
            }
            invalidate();
            start();
        };
    };

    public void start() {
        if (state_end){
            return;
        }
        Message obtainMessage = handler.obtainMessage();
        handler.sendMessageDelayed(obtainMessage, (timeLenth * 1000) / 360);
    }

    public interface OnLoadingListener{
        void  onFinish();
    }

    /**
     *
     */
    public  void  endLoad(){
        state_end=true;
    }
}

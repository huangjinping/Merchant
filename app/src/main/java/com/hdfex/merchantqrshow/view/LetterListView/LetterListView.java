package com.hdfex.merchantqrshow.view.LetterListView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by harrishuang on 16/9/28.
 */

public class LetterListView extends View {

    OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
            "Y", "Z"};
    int choose = -1;
    Paint paint = new Paint();
    boolean showBkg = false;

    public LetterListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LetterListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LetterListView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        if (showBkg) {
            paint.setColor(Color.parseColor("#cccccc"));
            paint.setAntiAlias(false);
            canvas.drawCircle(width/2, width/2,width/2 , paint);
            canvas.drawCircle(width/2, height-width/2,width/2 , paint);
            RectF rectF=new RectF();
            rectF.top=width/2;
            rectF.bottom=height-width/2;
            rectF.right=width;
            rectF.left=0;
            canvas.drawRect(rectF, paint);
        }

        int singleHeight = height / b.length;
        for (int i = 0; i < b.length; i++) {
            paint.setColor(Color.parseColor("#3492E9"));
            paint.setTextSize(30);
            // paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);

            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * b.length);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBkg = true;
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < b.length) {
                        listener.onTouchingLetterChanged(b[c]);
                        choose = c;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < b.length) {
                        listener.onTouchingLetterChanged(b[c]);
                        choose = c;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBkg = false;
                choose = -1;
                invalidate();
                break;
        }
        return true;
    }

    /**
     * 设置数据
     * @param date
     */
    public  void  setDataList(String [] date){
        this.b=date;
        this.invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String s);
    }
}

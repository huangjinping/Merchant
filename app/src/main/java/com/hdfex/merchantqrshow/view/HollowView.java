package com.hdfex.merchantqrshow.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hdfex.merchantqrshow.R;

/**
 * Created by harrishuang on 2016/11/26.
 */

public class HollowView extends View {


    private int mWidth;
    private int mHeight;
    private int mRadius ;
    private int mButtom ;

    public HollowView(Context context) {
        super(context);
    }

    public HollowView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }
    public HollowView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.HollowView, defStyle, 0);
        mRadius = a.getDimensionPixelSize(R.styleable.HollowView_mRadius, mRadius);
        mButtom = a.getDimensionPixelSize(R.styleable.HollowView_mButtom, mButtom);



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 设置宽度
         */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mWidth = specSize;
        }
        mWidth = specSize;

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mHeight = specSize;
        }
        mHeight = specSize;

        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(createCircleImage(createRect(), mHeight), 0, 0, null);
    }

    // /**
    // * 根据原图和变长绘制圆形图片
    // *
    // * @param source
    // * @param min
    // * @return
    // */
    private Bitmap createCircleImage(Bitmap source, int min) {
        final Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(mWidth / 2, mHeight - mButtom, mRadius / 2, paint);
        /**
         * 使用SRC_IN，参考上面的说明
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    private Bitmap createRect() {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight,
                Bitmap.Config.ARGB_8888);
        RectF rect = new RectF();
        rect.left = 0;
        rect.top = 0;
        rect.right = mWidth;
        rect.bottom = mHeight;
        Canvas c = new Canvas(bitmap);
        final Paint paint = new Paint();
        paint.setColor(Color.parseColor("#a7000000"));
        paint.setAntiAlias(true);
        c.drawRect(rect, paint);
        return bitmap;
    }

}

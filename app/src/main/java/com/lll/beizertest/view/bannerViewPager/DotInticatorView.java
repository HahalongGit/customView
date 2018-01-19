package com.lll.beizertest.view.bannerViewPager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by longlong on 2018/1/19.
 *
 * @ClassName: DotInticatorView
 * @Description: 指示器的View
 * @Date 2018/1/19
 */

public class DotInticatorView extends View {

    private Drawable mDrawable;


    public DotInticatorView(Context context) {

        this(context,null);
    }

    public DotInticatorView(Context context, @Nullable AttributeSet attrs) {

        this(context, attrs,0);
    }

    public DotInticatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        if(mDrawable!=null){
//            mDrawable.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight());
//            mDrawable.draw(canvas);
//        }

        Bitmap bitmap = drawableToBitmap(mDrawable);
        Bitmap circleBitmap = getCircleBitmap(bitmap);
        canvas.drawBitmap(circleBitmap,0,0,null);
    }

    /**
     * 获取圆形bitmap
     * @param bitmap
     * @return
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        //创建一个空白的Bitmap
        Bitmap circleBiemap = Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBiemap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);//防止抖动
        paint.setFilterBitmap(true);
        //画圆
        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,getMeasuredWidth()/2,paint);
        //bitmap默认是矩形，我们画了圆，去矩形和圆的交集部分（参考网络上的setXfermode）
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,0,0,paint);

        return circleBiemap;
    }

    /**
     * 转换drawable为Bitmap
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if(drawable instanceof BitmapDrawable){
            return ((BitmapDrawable)drawable).getBitmap();
        }
        //其他类型的Drawable
        //创建一个空 Bitmap
        Bitmap bitmap = Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(),Bitmap.Config.ARGB_8888);
        //把Drawable 画在Bitmap
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void setDrawable(Drawable drawable) {
        this.mDrawable = drawable;
        invalidate();
    }
}

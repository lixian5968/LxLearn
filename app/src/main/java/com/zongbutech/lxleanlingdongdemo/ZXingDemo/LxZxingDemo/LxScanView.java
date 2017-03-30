package com.zongbutech.lxleanlingdongdemo.ZXingDemo.LxZxingDemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.zongbutech.lxleanlingdongdemo.R;

/**
 * Created by lixian on 2017/3/22.
 */

public class LxScanView extends View {
    public LxScanView(Context context) {
        super(context);
        init(context);
    }

    public LxScanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LxScanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    Context ct;
    int AllWidth;
    Paint mPaint;
    int AngleWidth;
    int AngleLength;
    Bitmap mBitmap;
    private void init(Context context) {
        ct = context;
        AllWidth = dp2px(ct, 200);
        AngleWidth = dp2px(ct, 3);
        AngleLength = dp2px(ct, 20);
        mPaint = new Paint();
        BitmapDrawable mBitmapDrawable = (BitmapDrawable) ct.getResources().getDrawable(R.drawable.scan_icon_scanline);
        mBitmap = mBitmapDrawable.getBitmap();

    }

    Rect Scan;
    int mScanLineTop;

    public Rect getScan(){
        return Scan;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Scan = new Rect((getWidth() - AllWidth) / 2, dp2px(ct, 100), (getWidth() - AllWidth) / 2 + AllWidth, dp2px(ct, 100) + AllWidth);
        mScanLineTop = Scan.top +mBitmap.getHeight()/2*3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (Scan != null) {
//            画遮盖
            PaintedCover(canvas);
            //画边框
            DrawBorder(canvas);
            //话角
            PaintingAngle(canvas);
            //扫描线
            ScanLine(canvas);
        }


    }

    boolean add = true;
    private void ScanLine(Canvas canvas) {
        if (mScanLineTop >= (Scan.bottom-mBitmap.getHeight()/2*3) || mScanLineTop <= (Scan.top+mBitmap.getHeight()/2)) {
//            mScanLineTop = Scan.top+mBitmap.getHeight()/2*3;
            add =!add;
        }
        //划线
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setColor(Color.WHITE);
//        canvas.drawRect(Scan.left + AngleWidth / 2, mScanLineTop, Scan.right - AngleWidth / 2, mScanLineTop+dp2px(ct, 1), mPaint);



        RectF lineRect = new RectF(Scan.left + AngleWidth / 2, mScanLineTop, Scan.right - AngleWidth / 2, mScanLineTop + mBitmap.getHeight());
        canvas.drawBitmap(mBitmap, null, lineRect, mPaint);

        //移动
        if(add){
            mScanLineTop += dp2px(ct, 2);
        }else{
            mScanLineTop -= dp2px(ct, 2);
        }

        postInvalidateDelayed(10);
    }

    private void PaintingAngle(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(AngleWidth);
        canvas.drawLine(Scan.left - AngleWidth / 2, Scan.top, Scan.left + AngleLength - AngleWidth / 2, Scan.top, mPaint);
        canvas.drawLine(Scan.left, Scan.top - AngleWidth / 2, Scan.left, Scan.top + AngleLength - AngleWidth / 2, mPaint);

        canvas.drawLine(Scan.right - AngleLength + AngleWidth / 2, Scan.top, Scan.right + AngleWidth / 2, Scan.top, mPaint);
        canvas.drawLine(Scan.right, Scan.top - AngleWidth / 2, Scan.right, Scan.top + AngleLength - AngleWidth / 2, mPaint);

        canvas.drawLine(Scan.left - AngleWidth / 2, Scan.bottom, Scan.left + AngleLength - AngleWidth / 2, Scan.bottom, mPaint);
        canvas.drawLine(Scan.left, Scan.bottom + AngleWidth / 2, Scan.left, Scan.bottom - AngleLength + AngleWidth / 2, mPaint);


        canvas.drawLine(Scan.right - AngleLength + AngleWidth / 2, Scan.bottom, Scan.right + AngleWidth / 2, Scan.bottom, mPaint);
        canvas.drawLine(Scan.right, Scan.bottom + AngleWidth / 2, Scan.right, Scan.bottom - AngleLength + AngleWidth / 2, mPaint);
    }

    private void DrawBorder(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(dp2px(ct, 1));
        canvas.drawRect(Scan, mPaint);
    }

    private void PaintedCover(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#33FFFFFF"));
        //上
        canvas.drawRect(0, 0, width, Scan.top, mPaint);
        //左
        canvas.drawRect(0, Scan.top, Scan.left, Scan.bottom, mPaint);
        //右
        canvas.drawRect(Scan.right, Scan.top, width, Scan.bottom, mPaint);
        //下
        canvas.drawRect(0, Scan.bottom, width, height, mPaint);
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

}

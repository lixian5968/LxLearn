package com.zongbutech.lxleanlingdongdemo.PorterBuffDemo.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixian on 2017/4/13.
 */

public class StartAndMovieView extends FrameLayout {
    public StartAndMovieView(Context context) {
        super(context);
        init(context);
    }

    public StartAndMovieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StartAndMovieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    Paint paint;
    PorterDuffXfermode pdMode;

    private void init(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        pdMode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);


    }

    int Width;
    int Height;
    List<LxPoint> mPoints;
    float LxR;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Width = getMeasuredWidth();
        Height = getMeasuredHeight();
        LxR = Math.min(Width, Height) / 2;
        mPoints = new ArrayList<>();
        this.setRotation(-90);
        for (int i = 0; i < getChildCount(); i++) {
            this.getChildAt(i).setRotation(90);
        }
    }

    int count = 3;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (count < 3) {
            this.count = 3;
        } else {
            this.count = count;
        }
        invalidate();
    }

    int type =1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        paint.setXfermode(pdMode);

        if(type==1){
            mPoints.clear();
            mPoints.add(new LxPoint(LxR, 0));
            for (int i = 1; i < count; i++) {
                int angle = 360 / count * i;
                mPoints.add(new LxPoint((cos(angle) * LxR), (sin(angle) * LxR)));
            }



            Path mPath = new Path();
            mPath.moveTo(mPoints.get(0).x + LxR, mPoints.get(0).y + LxR);
            mPath.lineTo(2 * LxR, 2 * LxR);
            mPath.lineTo(0, 2 * LxR);
            mPath.lineTo(0, 0);
            mPath.lineTo(2 * LxR, 0);
            mPath.lineTo(mPoints.get(0).x + LxR, mPoints.get(0).y + LxR);
            for (int i = 0; i < mPoints.size(); i++) {
                mPath.lineTo(mPoints.get(mPoints.size() - 1 - i).x + LxR, mPoints.get(mPoints.size() - 1 - i).y + LxR);
            }
            canvas.drawPath(mPath, paint);
        }else if(type ==2){
            mPoints.clear();
            float inerRadius = cos(360 / count/2)*LxR/2;
            for (int i = 0; i < count; i++) {
                int angle = 360 / count * i;
                mPoints.add(new LxPoint((cos(angle) * LxR), (sin(angle) * LxR)));
                mPoints.add(new LxPoint(inerRadius * cos(360 / count * i+(360 / count )/2), inerRadius * sin(360 / count * i+(360 / count )/2)));
            }




            Path mPath = new Path();
            mPath.moveTo(mPoints.get(0).x + LxR, mPoints.get(0).y + LxR);
            mPath.lineTo(2 * LxR, 2 * LxR);
            mPath.lineTo(0, 2 * LxR);
            mPath.lineTo(0, 0);
            mPath.lineTo(2 * LxR, 0);
            mPath.lineTo(mPoints.get(0).x + LxR, mPoints.get(0).y + LxR);
            for (int i = 0; i < mPoints.size(); i++) {
                mPath.lineTo(mPoints.get(mPoints.size() - 1 - i).x + LxR, mPoints.get(mPoints.size() - 1 - i).y + LxR);
            }
            canvas.drawPath(mPath, paint);
        }




        canvas.restoreToCount(saveCount);
        paint.setXfermode(null);

    }


    public class LxPoint implements Serializable {
        public float x;
        public float y;

        public LxPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }

    /**
     * Math.sin的参数为弧度，使用起来不方便，重新封装一个根据角度求sin的方法
     *
     * @param num 角度
     * @return
     */
    float sin(int num) {
        return (float) Math.sin(num * Math.PI / 180);
    }

    /**
     * 与sin同理
     */
    float cos(int num) {
        return (float) Math.cos(num * Math.PI / 180);
    }
}

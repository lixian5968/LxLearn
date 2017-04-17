package com.zongbutech.lxleanlingdongdemo.PorterBuffDemo.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixian on 2017/4/14.
 */

public class LXStartView extends View {

    public LXStartView(Context context) {
        super(context);
    }

    public LXStartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LXStartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int width;
    int height;
    float LxR;
    List<LxPoint> mPoints;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        LxR = Math.min(width, height) / 2;
        mPoints = new ArrayList<>();
    }

    int count = 10;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (count < 3) {
            this.count = 3;
        } else {
            this.count = count;
        }
    }

    int type = 0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(-90);

        if (type == 0) {
            Path mPath = new Path();
            mPath.moveTo(LxR, 0);
            for (int i = 1; i < count; i++) {
                int angle = 360 / count * i;
                mPath.lineTo(cos(angle) * LxR, sin(angle) * LxR);
            }
            canvas.drawPath(mPath, paint);
        } else {
            mPoints.add(new LxPoint(LxR, 0));
            for (int i = 1; i < count; i++) {
                int angle = 360 / count * i;
                mPoints.add(new LxPoint((cos(angle) * LxR), (sin(angle) * LxR)));
            }
            Path mPath = new Path();
            mPath.moveTo(mPoints.get(0).x, mPoints.get(0).y);
            mPath.lineTo(LxR, LxR);
            mPath.lineTo(-LxR, LxR);
            mPath.lineTo(-LxR, -LxR);
            mPath.lineTo(LxR, -LxR);
            mPath.lineTo(mPoints.get(0).x, mPoints.get(0).y);
            for (int i = 0; i < mPoints.size(); i++) {
                mPath.lineTo(mPoints.get(mPoints.size() - 1 - i).x, mPoints.get(mPoints.size() - 1 - i).y);
            }
            canvas.drawPath(mPath, paint);
        }



        canvas.translate(-getWidth() / 2, -getHeight() / 2);
        canvas.rotate(90);

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

}
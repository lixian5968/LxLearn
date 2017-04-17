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

public class LXPointStartView extends View {

    public LXPointStartView(Context context) {
        super(context);
    }

    public LXPointStartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LXPointStartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int width;
    int height;
    float LxR;
    List<LxPoint> mLxPoints;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        LxR = Math.min(width, height) / 2;
        mLxPoints = new ArrayList<>();
    }

    int count = 7;
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



//        Path path = new Path();
//        paint.setColor(Color.RED);
//        float inerRadius = count % 2 == 0 ?
//                (LxR * (cos(360 / count / 2) - sin(360 / count / 2) * sin(90 - 360 / count) / cos(90 - 360 / count)))
//                : (LxR * sin(360 / count / 4) / sin(180 - 360 / count / 2 - 360 / count / 4));

//        float inerRadius = (LxR * sin(360 / count / 4) / sin(180 - 360 / count / 2 - 360 / count / 4));
//        for (int i = 0; i < count; i++) {
//            if (i == 0) {
//                path.moveTo(LxR * cos(360 / count * i), LxR * sin(360 / count * i));
//            } else {
//                path.lineTo(LxR * cos(360 / count * i), LxR * sin(360 / count * i));
//            }
//            mLxPoints.add(new LxPoint(LxR * cos(360 / count * i), LxR * sin(360 / count * i)));
//            path.lineTo(inerRadius * cos(360 / count * i + 360 / count / 2), inerRadius * sin(360 / count * i + 360 / count / 2));
//            mLxPoints.add(new LxPoint(inerRadius * cos(360 / count * i + 360 / count / 2), inerRadius * sin(360 / count * i + 360 / count / 2)));
//        }
//        canvas.drawPath(path, paint);
//        paint.setColor(Color.YELLOW);
//        canvas.drawLine(0,0,mLxPoints.get(0).x,mLxPoints.get(0).y,paint);
//        canvas.drawLine(0,0,mLxPoints.get(1).x,mLxPoints.get(1).y,paint);
//        canvas.drawLine(0,0,mLxPoints.get(2).x,mLxPoints.get(2).y,paint);


      

        if (type == 0) {
            Path path = new Path();
            paint.setColor(Color.RED);
            float inerRadius = cos(360 / count/2)*LxR/2;
            for (int i = 0; i < count; i++) {
                if (i == 0) {
                    path.moveTo(LxR * cos(360 / count * i), LxR * sin(360 / count * i));
                } else {
                    path.lineTo(LxR * cos(360 / count * i), LxR * sin(360 / count * i));
                }
                path.lineTo(inerRadius * cos(360 / count * i+(360 / count )/2), inerRadius * sin(360 / count * i+(360 / count )/2)) ;
            }
            canvas.drawPath(path, paint);
        } else {
            float inerRadius = cos(360 / count/2)*LxR/2;
            for (int i = 0; i < count; i++) {
                int angle = 360 / count * i;
                mLxPoints.add(new LxPoint((cos(angle) * LxR), (sin(angle) * LxR)));
                mLxPoints.add(new LxPoint(inerRadius * cos(360 / count * i+(360 / count )/2), inerRadius * sin(360 / count * i+(360 / count )/2)));
            }
            Path mPath = new Path();
            mPath.moveTo(mLxPoints.get(0).x, mLxPoints.get(0).y);
            mPath.lineTo(LxR, LxR);
            mPath.lineTo(-LxR, LxR);
            mPath.lineTo(-LxR, -LxR);
            mPath.lineTo(LxR, -LxR);
            mPath.lineTo(mLxPoints.get(0).x, mLxPoints.get(0).y);
            for (int i = 0; i < mLxPoints.size(); i++) {
                mPath.lineTo(mLxPoints.get(mLxPoints.size() - 1 - i).x, mLxPoints.get(mLxPoints.size() - 1 - i).y);
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
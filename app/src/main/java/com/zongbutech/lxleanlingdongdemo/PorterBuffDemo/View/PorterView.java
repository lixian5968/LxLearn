package com.zongbutech.lxleanlingdongdemo.PorterBuffDemo.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import com.zongbutech.lxleanlingdongdemo.R;

/**
 * Created by lixian on 2017/4/13.
 */

public class PorterView extends View {

    private static final Xfermode[] sModes = {
            new PorterDuffXfermode(PorterDuff.Mode.CLEAR),
            new PorterDuffXfermode(PorterDuff.Mode.SRC),
            new PorterDuffXfermode(PorterDuff.Mode.DST),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
            new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.XOR),
            new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
            new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
            new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
            new PorterDuffXfermode(PorterDuff.Mode.SCREEN)
    };

    private static final String[] sLabels = {
            "Clear", "Src", "Dst", "SrcOver",
            "DstOver", "SrcIn", "DstIn", "SrcOut",
            "DstOut", "SrcATop", "DstATop", "Xor",
            "Darken", "Lighten", "Multiply", "Screen"
    };

    public PorterView(Context context) {
        super(context);
        init(context, null);
    }


    public PorterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PorterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    int angle;
    Paint paint;

    public void setAngle(int angle) {
        this.angle = angle;
    }


    int Frist =1;

    public int getFrist() {
        return Frist;
    }

    public void setFrist(int frist) {
        Frist = frist;
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.DiagonalLayout, 0, 0);
            angle = styledAttributes.getInt(R.styleable.DiagonalLayout_diagonal_angle, 0);
            styledAttributes.recycle();
        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 禁止硬件加速，硬件加速会有一些问题，这里禁用掉
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }


    int width;
    int height;
    int min;//画圆的半径


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获得控件的宽高
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        min = Math.min(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(255, 139, 197, 186);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        canvas.save();
        int layer = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);


        if (Frist == 0) {
            //画圆 a
            paint.setColor(Color.YELLOW);
            canvas.drawCircle(min / 2, min / 2, min / 2, paint);
            paint.setXfermode(sModes[angle]);
            //B 压在A上面，显示公共部分
            //画矩形 b
            paint.setColor(Color.BLUE);
            canvas.drawRect(0, 0, min, min, paint);
            paint.setXfermode(null);
            canvas.restoreToCount(layer);
        } else {
            paint.setColor(Color.BLUE);
            canvas.drawRect(0, 0, min, min, paint);
            paint.setXfermode(sModes[angle]);
            paint.setColor(Color.YELLOW);
            canvas.drawCircle(min / 2, min / 2, min / 2, paint);
            paint.setXfermode(null);
            canvas.restoreToCount(layer);
        }


        paint.setColor(Color.RED);
        paint.setTextSize(50);//设置字体大小
        canvas.drawText(sLabels[angle], min / 3, min / 2, paint);

    }


}

package com.zongbutech.lxleanlingdongdemo.Accessibility;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zongbutech.lxleanlingdongdemo.R;

/**
 * Created by jinliangshan on 16/12/26.
 */
public class FloatingView extends LinearLayout {
    public static final String TAG = "FloatingView";

    private final Context mContext;
    private final WindowManager mWindowManager;
    private TextView mTvPackageName;
    private TextView mTvClassName;
    private ImageView mIvClose;

    public FloatingView(Context context) {
        super(context);
        mContext = context;
        mWindowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        initView();
    }

    private void initView() {
        inflate(mContext, R.layout.layout_floating, this);
        mTvPackageName = (TextView) findViewById(R.id.tv_package_name);
        mTvClassName = (TextView) findViewById(R.id.tv_class_name);
        mIvClose = (ImageView) findViewById(R.id.iv_close);

        mIvClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContext instanceof GetTopActivityAccessibilityService){
                    ((GetTopActivityAccessibilityService)mContext).closeAndroidShowView();
                }
                Toast.makeText(mContext, "关闭悬浮框", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setPackageText(String packageName,String className){
        mTvPackageName.setText(packageName);
        mTvClassName.setText(className.startsWith(packageName)?
                        className.substring(packageName.length()):
                        className
        );
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


    Point preP, curP;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                preP = new Point((int)event.getRawX(), (int)event.getRawY());
                break;

            case MotionEvent.ACTION_MOVE:
                curP = new Point((int)event.getRawX(), (int)event.getRawY());
                int dx = curP.x - preP.x,
                        dy = curP.y - preP.y;

                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.getLayoutParams();
                layoutParams.x += dx;
                layoutParams.y += dy;
                mWindowManager.updateViewLayout(this, layoutParams);

                preP = curP;
                break;
        }

        return false;
    }
}

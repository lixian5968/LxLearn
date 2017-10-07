package com.zongbutech.lxleanlingdongdemo.Accessibility;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by lixian on 2017/9/1.
 */

public class GetTopActivityAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (mFloatingView != null) {
                mFloatingView.setPackageText(event.getPackageName().toString(), event.getClassName().toString());
            }
            Log.e("LxService", event.getPackageName().toString() + "\n" + event.getClassName().toString());
        }
    }


    @Override
    public void onInterrupt() {
        Log.e("", "");
    }


    //开启
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        InitAndroidShowView(this);
    }

    WindowManager mWindowManager;
    WindowManager.LayoutParams params;
    FloatingView mFloatingView;

    private void InitView(Context ct) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) ct.getSystemService(Context.WINDOW_SERVICE);
        }
        if (params == null) {
            params = new WindowManager.LayoutParams();
            params.x = 0;
            params.y = 0;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.LEFT | Gravity.TOP;
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
            params.format = PixelFormat.RGBA_8888;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        }
        if (mFloatingView == null) {
            mFloatingView = new FloatingView(ct);
        }
    }

    private void InitAndroidShowView(Context ct) {
        InitView(ct);
        mWindowManager.addView(mFloatingView, params);
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    //关闭
    @Override
    public void onDestroy() {
        closeAndroidShowView();
        super.onDestroy();
    }

    public void closeAndroidShowView() {
        if (mWindowManager != null && mFloatingView != null) {
            mWindowManager.removeView(mFloatingView);
            mWindowManager = null;
            mFloatingView = null;
        }
    }

}

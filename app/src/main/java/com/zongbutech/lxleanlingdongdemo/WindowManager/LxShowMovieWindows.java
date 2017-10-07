package com.zongbutech.lxleanlingdongdemo.WindowManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.zongbutech.lxleanlingdongdemo.R;

public class LxShowMovieWindows extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_windows);
        Log.e("lx", "lx");
    }

    public void Goto(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                checkOverlayPermission();
                break;
            case R.id.bt2:
                closeAndroidShowView();
                break;
        }
    }


    private void checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                startActivityForResult(
                        new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()))
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                        100
                );
                Toast.makeText(this, "请先授予 \"Activity 栈\" 悬浮窗权限", Toast.LENGTH_LONG).show();
            } else {
                //有权限
                InitAndroidShowView(LxShowMovieWindows.this);
            }
        } else {
            //有权限
            InitAndroidShowView(LxShowMovieWindows.this);
        }
    }


    WindowManager mWindowManager;
    WindowManager.LayoutParams params;
    LxVideoView mLxVideoView;

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
        if (mLxVideoView == null) {
            mLxVideoView = new LxVideoView(ct);
        }
    }

    private void closeAndroidShowView() {
        if (mWindowManager != null && mLxVideoView != null) {
            mWindowManager.removeView(mLxVideoView);
        }
    }

    private void InitAndroidShowView(Context ct) {
        InitView(ct);
        mWindowManager.addView(mLxVideoView, params);
        mLxVideoView.StartVideo();
    }


}

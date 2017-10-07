package com.zongbutech.lxleanlingdongdemo.Accessibility;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.zongbutech.lxleanlingdongdemo.R;

import java.io.File;

public class LxAllAccessAcitivity extends AppCompatActivity {


    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_access);
        mTextView = (TextView) findViewById(R.id.mTextView);
        Log.e("lx", "lx");
    }
    File[] file;
    public void Goto(View v) {
        switch (v.getId()) {
            case R.id.bt0:
                AccessibilityUtil.StartAccessibility(LxAllAccessAcitivity.this);
                break;
            case R.id.bt1:
                checkOverlayPermission();
                break;
            case R.id.bt2:
                closeAndroidShowView();
                break;

            case R.id.bt3:

                String all="";
                File mFiles = new File(Environment.getExternalStorageDirectory(), "LxInstalls");
                if (!mFiles.exists()) {
                    mFiles.mkdirs();
                }
                all+=mFiles.getPath()+"\n";
                file = mFiles.listFiles();
                for (File f : file) {
                    String fileName = f.getName();
                    if (fileName.endsWith(".apk")) {
                        all+=fileName+"\n";
                    }
                }
                mTextView.setText(all);
                if(file.length>0){
                    count=0;
                    for(File mFile :file){
                        startService(new Intent(LxAllAccessAcitivity.this, InstallService.class).putExtra("InstallUrl", mFile.getPath()));
                        count++;
                    }
                }



                break;
        }

    }

    int count=-1;

    @Override
    protected void onResume() {
        super.onResume();
        if(count>0){
            Log.e("Lx","安装完成");
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
                InitAndroidShowView(LxAllAccessAcitivity.this);
            }
        } else {
            //有权限
            InitAndroidShowView(LxAllAccessAcitivity.this);
        }
    }


    WindowManager mWindowManager;
    WindowManager.LayoutParams params;
    LxTextView mLxTextView;

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
        if (mLxTextView == null) {
            mLxTextView = new LxTextView(ct);
            mLxTextView.setText("Lx HAHAHA");
        }
    }

    private void InitAndroidShowView(Context ct) {
        InitView(ct);
        mWindowManager.addView(mLxTextView, params);
    }

    private void closeAndroidShowView() {
        if (mWindowManager != null && mLxTextView != null) {
            mWindowManager.removeView(mLxTextView);
        }
    }


    public class LxTextView extends TextView {

        public LxTextView(Context context) {
            super(context);
        }

        public LxTextView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public LxTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }


        Point preP, curP;

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    preP = new Point((int) event.getRawX(), (int) event.getRawY());
                    break;

                case MotionEvent.ACTION_MOVE:
                    curP = new Point((int) event.getRawX(), (int) event.getRawY());
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

}

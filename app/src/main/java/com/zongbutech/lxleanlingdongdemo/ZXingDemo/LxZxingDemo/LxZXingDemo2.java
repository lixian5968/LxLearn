package com.zongbutech.lxleanlingdongdemo.ZXingDemo.LxZxingDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zongbutech.lxleanlingdongdemo.R;

public class LxZXingDemo2 extends AppCompatActivity {
    LxZxingSurfaceView2 mLxZxingSurfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lx_zxing_demo2);
        mLxZxingSurfaceView = $(R.id.mLxZxingSurfaceView);

    }


    public <T extends View> T $(int id) {
        return (T) findViewById(id);
    }



}

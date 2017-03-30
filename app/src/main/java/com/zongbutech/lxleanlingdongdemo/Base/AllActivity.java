package com.zongbutech.lxleanlingdongdemo.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zongbutech.lxleanlingdongdemo.R;
import com.zongbutech.lxleanlingdongdemo.ZXingDemo.CustomView.SacnActivity;
import com.zongbutech.lxleanlingdongdemo.ZXingDemo.LxZxingDemo.LxZXingDemo;
import com.zongbutech.lxleanlingdongdemo.ZXingDemo.ZXingDemo.ZXingDemo;

public class AllActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
    }


    public void Sacn1(View v) {
        startActivity(new Intent(AllActivity.this, SacnActivity.class ));
    }


    public void Sacn2(View v) {
        startActivity(new Intent(AllActivity.this, ZXingDemo.class ));
    }


    public void Sacn3(View v) {
        startActivity(new Intent(AllActivity.this, LxZXingDemo.class ));
    }
}

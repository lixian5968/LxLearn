package com.zongbutech.lxleanlingdongdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zongbutech.lxleanlingdongdemo.ConnectPhone.ConnectAcitivity;
import com.zongbutech.lxleanlingdongdemo.FingerDemo.FingerActivityDemo;
import com.zongbutech.lxleanlingdongdemo.InPutInfoAndOutofInfo.TestInputInfo;
import com.zongbutech.lxleanlingdongdemo.InPutInfoAndOutofInfo.TestOutPutInfo;
import com.zongbutech.lxleanlingdongdemo.LxAppExtractor.LxDeleteActivity;
import com.zongbutech.lxleanlingdongdemo.LxAppExtractor.LxExtractorActivity;
import com.zongbutech.lxleanlingdongdemo.PhoneManager.PhoneManagerActivity;
import com.zongbutech.lxleanlingdongdemo.PorterBuffDemo.AllPorterActivity;
import com.zongbutech.lxleanlingdongdemo.SDManager.SDManagerActivity;
import com.zongbutech.lxleanlingdongdemo.Test.TestPermissionActivity2;
import com.zongbutech.lxleanlingdongdemo.Test.TestRunnableActivity;
import com.zongbutech.lxleanlingdongdemo.ZXingDemo.LxZxingDemo.LxZXingDemo;
import com.zongbutech.lxleanlingdongdemo.ZXingDemo.LxZxingDemo.LxZXingDemo2;

public class AllAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_acitivity);

        Log.e("lx","lx");
    }

    public void Goto(View v){
        Intent it = null;
        switch (v.getId()){
            case R.id.bt0:
                it = new Intent(AllAcitivity.this, FingerActivityDemo.class);
                break;

            case R.id.bt1:
                it = new Intent(AllAcitivity.this, LxExtractorActivity.class);
                break;

            case R.id.bt2:
                it = new Intent(AllAcitivity.this, PhoneManagerActivity.class);
                break;

            case R.id.bt3:
                it = new Intent(AllAcitivity.this, AllPorterActivity.class);
                break;

            case R.id.bt4:
                it = new Intent(AllAcitivity.this, SDManagerActivity.class);
                break;
            case R.id.bt5:
                it = new Intent(AllAcitivity.this, LxZXingDemo.class);
                break;
            case R.id.bt6:
                it = new Intent(AllAcitivity.this, ConnectAcitivity.class);
                break;
            case R.id.bt7:
                it = new Intent(AllAcitivity.this, LxDeleteActivity.class);
                break;
            case R.id.bt8:
                it = new Intent(AllAcitivity.this, LxZXingDemo2.class);
                break;
            case R.id.bt9:
                it = new Intent(AllAcitivity.this, TestRunnableActivity.class);
                break;
            case R.id.bt10:
                it = new Intent(AllAcitivity.this, TestPermissionActivity2.class);
                break;
            case R.id.bt11:
                it = new Intent(AllAcitivity.this, TestInputInfo.class);
                break;
            case R.id.bt12:
                it = new Intent(AllAcitivity.this, TestOutPutInfo.class);
                break;

        }

        startActivity(it);
    }

}

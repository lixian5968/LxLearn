package com.zongbutech.lxleanlingdongdemo.BroadcastReceiverDemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zongbutech.lxleanlingdongdemo.R;

public class BroadActivityDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_demo);
    }

    public void mButton(View v){
        Intent intent = new Intent();
        intent.setAction("com.dsw.send");
        intent.putExtra("data", "data");
        sendBroadcast(intent);
    }
}

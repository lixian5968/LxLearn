package com.zongbutech.lxleanlingdongdemo.GsonDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zongbutech.lxleanlingdongdemo.R;

public class GsonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);


        String Json = "{\"Lx\":\"hha\"}";
        try {
            StudentBean bean =JsonUtils.deserialize(Json,StudentBean.class);
            Log.e("","");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("","");
        }


    }
}

package com.zongbutech.lxleanlingdongdemo.Anim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zongbutech.lxleanlingdongdemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ToActivity2(View v){
        startActivity(new Intent(MainActivity.this,Main2Activity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void ToActivity2NoAmin(View v){
        startActivity(new Intent(MainActivity.this,Main2Activity.class));
    }


}

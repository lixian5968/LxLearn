package com.zongbutech.lxleanlingdongdemo.PorterBuffDemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zongbutech.lxleanlingdongdemo.R;

public class AllPorterActivity extends AppCompatActivity {
    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_porter);
        it = new Intent(AllPorterActivity.this, PorterActivity.class);
    }

    public void ToPor1(View v) {
        it.putExtra("Type", 0);
        startActivity(it);
    }


    public void ToPor2(View v) {
        it.putExtra("Type", 1);
        startActivity(it);
    }

    public void StartView(View v) {
        it = new Intent(AllPorterActivity.this, StartActivity.class);
        startActivity(it);
    }
    public void LxStarView(View v) {
        it = new Intent(AllPorterActivity.this, LxStartActivity.class);
        startActivity(it);
    }

    public void LxStartAndMoveView(View v) {
        it = new Intent(AllPorterActivity.this, LxStartAndMoveViewActivty.class);
        startActivity(it);
    }

    public void LxPointStart(View v) {
        it = new Intent(AllPorterActivity.this, PointStartActivity.class);
        startActivity(it);
    }


}

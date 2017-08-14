package com.zongbutech.lxleanlingdongdemo.Test;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zongbutech.lxleanlingdongdemo.R;

import java.text.SimpleDateFormat;

public class TestRunnableActivity extends AppCompatActivity {
    Context ct;
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_runnable);
        ct = this;
        TextView mTextView = $(R.id.mTextView);
        mHandler = new Handler();
    }


    public <T extends View> T $(int id) {
        return (T) findViewById(id);
    }


    public void Start(View v){
        mHandler.removeCallbacks(LxRunnable);
        mHandler.postDelayed(LxRunnable, 100);
    }
    public void Stop(View v){
        mHandler.removeCallbacks(LxRunnable);
    }


    private Runnable LxRunnable = new Runnable() {
        @Override
        public void run() {
            long timeNow = System.currentTimeMillis();
            java.util.Date uDate = new java.util.Date(timeNow);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(uDate);
            Log.e("LxRunnable",dateString);

            mHandler.postDelayed(LxRunnable, 5000);
        }
    };


}

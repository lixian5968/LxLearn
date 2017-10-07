package com.zongbutech.lxleanlingdongdemo.Test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zongbutech.lxleanlingdongdemo.R;

import java.text.SimpleDateFormat;

public class TestRunnableActivity2 extends AppCompatActivity {
    Context ct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_runnable);
        ct = this;
        TextView mTextView = $(R.id.mTextView);
    }


    public <T extends View> T $(int id) {
        return (T) findViewById(id);
    }

    Thread LxThread;
    public void Start(View v){
        Start();
    }
    private void Start() {
        LxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                long timeNow = System.currentTimeMillis();
                java.util.Date uDate = new java.util.Date(timeNow);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(uDate);
                Log.e("LxRunnable",dateString);
                Start();
            }
        });
        LxThread.start();
    }

    public void Stop(View v){
        if (LxThread != null) {
            LxThread.interrupt();
        }
    }




}

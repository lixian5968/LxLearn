package com.zongbutech.lxleanlingdongdemo.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zongbutech.lxleanlingdongdemo.InternatDemo.InternatActivity;
import com.zongbutech.lxleanlingdongdemo.Utils.LanguageUtil;

/**
 * Created by lixian on 2017/3/27.
 */

public class BaseActivity extends AppCompatActivity {
    public <T extends View> T $(int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LanguageUtil.isSetValue(BaseActivity.this)) {
            StartActivity();
        }
    }

    public void StartActivity() {
        Intent intent = new Intent(this, InternatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
// 杀掉进程
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}

package com.zongbutech.lxleanlingdongdemo.BroadcastReceiverDemo;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by lixian on 2017/3/14.
 */

public class GostBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra("data");
        Toast.makeText(context, data + "/cast", Toast.LENGTH_LONG).show();
        if(context instanceof Application){
//BootstrapApplication
        }
    }
}
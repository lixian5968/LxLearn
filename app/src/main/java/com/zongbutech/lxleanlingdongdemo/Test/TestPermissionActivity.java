package com.zongbutech.lxleanlingdongdemo.Test;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zongbutech.lxleanlingdongdemo.R;

public class TestPermissionActivity extends AppCompatActivity {

//    日历权限组  CALENDAR
//    READ_CALENDAR
//    WRITE_CALENDAR

//    相机权限组 CAMERA
//    CAMERA

//    CONTACTS 联系权限组
//    READ_CONTACTS
//    WRITE_CONTACTS
//    GET_ACCOUNTS

//    LOCATION 位置权限组
//    ACCESS_FINE_LOCATION
//    ACCESS_COARSE_LOCATION


//    MICROPHONE 微电话信息权限组
//    RECORD_AUDIO

//    PHONE 电话权限组
//    READ_PHONE_STATE
//    CALL_PHONE
//    READ_CALL_LOG
//    WRITE_CALL_LOG
//    ADD_VOICEMAIL
//    USE_SIP
//    PROCESS_OUTGOING_CALLS

//    SENSORS 传感器权限组
//    BODY_SENSORS

//    SMS 短信权限组
//    SEND_SMS
//    RECEIVE_SMS
//    READ_SMS
//    RECEIVE_WAP_PUSH
//    RECEIVE_MMS


//    STORAGE 存储权限组
//    READ_EXTERNAL_STORAGE
//    WRITE_EXTERNAL_STORAGE


    Context ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_per);
        ct = this;
        TextView mTextView = $(R.id.mTextView);
    }


    public <T extends View> T $(int id) {
        return (T) findViewById(id);
    }


    public void Start(View v) {

        if (Build.VERSION.SDK_INT >= 23) {
            if (
                            //日历
                            ContextCompat.checkSelfPermission(ct, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED ||
                            //相机
                            ContextCompat.checkSelfPermission(ct, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                            //联系权限组
                            ContextCompat.checkSelfPermission(ct,  Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
                            //位置权限组
                            ContextCompat.checkSelfPermission(ct, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                            //微电话信息权限组
                            ContextCompat.checkSelfPermission(ct, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                            //电话权限组
                            ContextCompat.checkSelfPermission(ct, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                            //传感器权限组
                            ContextCompat.checkSelfPermission(ct, Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED ||
                            //短信权限组
                            ContextCompat.checkSelfPermission(ct, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
                            //存储权限组
                            ContextCompat.checkSelfPermission(ct, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    ) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.BODY_SENSORS,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                }, AllPer);
            } else {
                mToast("6.0 有权限");
            }


//            if (ContextCompat.checkSelfPermission(ct, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
//                    ContextCompat.checkSelfPermission(ct, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, AllPer);
//            } else {
//                mToast("6.0 有权限");
//            }
        } else {
            mToast("6.0以前 有权限");
        }

    }

    private void showPerDialog() {
        AlertDialog mAlertDialog = new AlertDialog.Builder(ct)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JumpPermissionManagement.GoToSetting(TestPermissionActivity.this);
                        dialog.dismiss();
                    }
                }).setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        mAlertDialog.setTitle("是否到设置界面给予权限？");
        mAlertDialog.show();
    }

    public void Stop(View v) {
    }

    private static final int AllPer = 17;

//    //日历
//                            ContextCompat.checkSelfPermission(ct, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED ||
//            //相机
//            ContextCompat.checkSelfPermission(ct, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
//            //联系权限组
//            ContextCompat.checkSelfPermission(ct, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
//            //位置权限组
//            ContextCompat.checkSelfPermission(ct, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
//            //微电话信息权限组
//            ContextCompat.checkSelfPermission(ct, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
//            //电话权限组
//            ContextCompat.checkSelfPermission(ct, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
//            //传感器权限组
//            ContextCompat.checkSelfPermission(ct, Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED ||
//            //短信权限组
//            ContextCompat.checkSelfPermission(ct, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
//            //存储权限组
//            ContextCompat.checkSelfPermission(ct, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == AllPer) {
            if (grantResults.length == 9) {
                boolean READ_CALENDARCheck = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean CAMERACheck = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                boolean READ_CONTACTSCheck = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                boolean ACCESS_FINE_LOCATIONCheck = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                boolean RECORD_AUDIOCheck = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                boolean READ_PHONE_STATECheck = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                boolean BODY_SENSORSCheck = grantResults[6] == PackageManager.PERMISSION_GRANTED;
                boolean SEND_SMSCheck = grantResults[7] == PackageManager.PERMISSION_GRANTED;
                boolean READ_EXTERNAL_STORAGECheck = grantResults[8] == PackageManager.PERMISSION_GRANTED;

                if (READ_CALENDARCheck && CAMERACheck
                        &READ_CONTACTSCheck && ACCESS_FINE_LOCATIONCheck
                        &RECORD_AUDIOCheck && READ_PHONE_STATECheck
                        &BODY_SENSORSCheck && SEND_SMSCheck
                        &READ_EXTERNAL_STORAGECheck) {
                    mToast("申请到了权限");
                } else {
                    mToast("没有申请到权限1");
                    showPerDialog();
                }
            } else {
                mToast("没有申请到权限2");
                showPerDialog();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    public void mToast(String s) {
        Toast.makeText(ct, s, Toast.LENGTH_LONG).show();
    }


}

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

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class TestPermissionActivity2 extends AppCompatActivity {


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
                    ContextCompat.checkSelfPermission(ct, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(ct, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(ct, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(ct, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    ) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        READ_EXTERNAL_STORAGE
                }, AllPer);
            } else {
                mToast("6.0 有权限");
            }
        } else {
            mToast("6.0以前 有权限");
        }

    }

    private void showPerDialog() {
        AlertDialog mAlertDialog = new AlertDialog.Builder(ct)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JumpPermissionManagement.GoToSetting(TestPermissionActivity2.this);
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
        showPerDialog();
    }

    private static final int AllPer = 17;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == AllPer) {
            if (grantResults.length == 4) {
                boolean CAMERACheck = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean ACCESS_FINE_LOCATIONCheck = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                boolean READ_PHONE_STATECheck = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                boolean READ_EXTERNAL_STORAGECheck = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                if (CAMERACheck && ACCESS_FINE_LOCATIONCheck && READ_PHONE_STATECheck &&READ_EXTERNAL_STORAGECheck) {
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

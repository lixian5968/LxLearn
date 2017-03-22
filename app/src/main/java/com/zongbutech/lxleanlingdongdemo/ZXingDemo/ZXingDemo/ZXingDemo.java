package com.zongbutech.lxleanlingdongdemo.ZXingDemo.ZXingDemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.zongbutech.lxleanlingdongdemo.R;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ZXingDemo extends AppCompatActivity implements QRCodeView.Delegate {
    private static final String TAG = ZXingDemo.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    private QRCodeView mQRCodeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing_demo);
        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);

        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i("Lx", "result:" + result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        vibrate();
//        mQRCodeView.startSpot();
    }
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e("Lx", "打开相机出错");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);

        mQRCodeView.showScanRect();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mQRCodeView.showScanRect();
    }

}

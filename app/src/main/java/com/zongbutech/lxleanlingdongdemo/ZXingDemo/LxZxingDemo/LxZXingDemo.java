package com.zongbutech.lxleanlingdongdemo.ZXingDemo.LxZxingDemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.zongbutech.lxleanlingdongdemo.R;

import java.io.File;
import java.io.FileOutputStream;

import cn.bingoogolapple.qrcode.core.QRCodeView;

public class LxZXingDemo extends AppCompatActivity {
    LxZxingSurfaceView mLxZxingSurfaceView;
    LxScanView mLxScanView;
    ImageView mImageView;
    Rect mRect;
    public static LxZXingDemo instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lx_zxing_demo);
        mLxZxingSurfaceView = $(R.id.mLxZxingSurfaceView);
        mLxScanView = $(R.id.mLxScanView);
        mImageView = $(R.id.mImageView);
        instance = this;


        mLxZxingSurfaceView.setListener(new QRCodeView.Delegate() {
            @Override
            public void onScanQRCodeSuccess(String result) {

                if (result.startsWith("http://") || result.startsWith("https://")) {
                    Uri uri = Uri.parse(result);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Toast.makeText(LxZXingDemo.this, result, Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onScanQRCodeOpenCameraError() {

            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLxZxingSurfaceView.startSacn();
                mRect = mLxScanView.getScan();
                mLxZxingSurfaceView.setScan(mRect);
            }
        }, 1000);


        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NewBitmap != null) {
                    String myFile = Environment.getExternalStorageDirectory() + File.separator + "LxLearnImage";
                    File newFile = new File(myFile);
                    if (!newFile.exists()) {
                        newFile.mkdir();
                    }
                    File ImageFile = new File(newFile, System.currentTimeMillis() + ".jpg");
                    try {
                        FileOutputStream outputStream = new FileOutputStream(ImageFile);
                        NewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                        outputStream.flush();
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(LxZXingDemo.this, "保存成功", Toast.LENGTH_SHORT).show();


                }
            }
        });
    }


    public <T extends View> T $(int id) {
        return (T) findViewById(id);
    }

    Bitmap NewBitmap;

    public void setBitmap(Bitmap bmp) {
        NewBitmap = Bitmap.createBitmap(bmp, mRect.left, mRect.top, mRect.width(), mRect.height());
        mImageView.setImageBitmap(NewBitmap);
//        mImageView.setImageBitmap(bmp);
    }


}

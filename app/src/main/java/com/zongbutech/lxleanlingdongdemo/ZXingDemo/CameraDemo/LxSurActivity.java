package com.zongbutech.lxleanlingdongdemo.ZXingDemo.CameraDemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zongbutech.lxleanlingdongdemo.R;


public class LxSurActivity extends AppCompatActivity {
    ImageView mImageView;
    ImageView mImageView2;
    LxSurfaceView mLxSurfaceView;
    Bitmap mBitmap;
    Bitmap mBitmap2;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lx_sur);
        mImageView = $(R.id.mImageView);
        mImageView2 = $(R.id.mImageView2);
        mLxSurfaceView = $(R.id.mLxSurfaceView);
        mTextView = $(R.id.mTextView);

    }

    public <T extends View> T $(int id) {
        return (T) findViewById(id);
    }


    public void getImage(View v) {
        mLxSurfaceView.setOngetImageListener(new LxSurfaceView.GetImageListener() {
            @Override
            public void getImage(Bitmap bt,Bitmap bt2) {
                mImageView.setImageBitmap(bt);
                mImageView2.setImageBitmap(bt2);
                mBitmap = bt;
                mBitmap2 = bt2;
            }
        });
        mLxSurfaceView.getImage();
    }

    public void getInfo(View v) {
        String result = QRCodeDecoder.syncDecodeQRCode(mBitmap2);
        if(result==null){
            result ="解析失败";
        }
        mTextView.setText(result);

//        BitmapDrawable mBitmapDrawable = (BitmapDrawable) LxSurActivity.this.getResources().getDrawable(R.drawable.liantu);
//        String result = QRCodeDecoder.syncDecodeQRCode(mBitmapDrawable.getBitmap());
//        mTextView.setText(result);

    }


}




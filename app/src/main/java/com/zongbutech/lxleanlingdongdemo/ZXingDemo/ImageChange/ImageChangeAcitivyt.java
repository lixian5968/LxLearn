package com.zongbutech.lxleanlingdongdemo.ZXingDemo.ImageChange;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.zongbutech.lxleanlingdongdemo.R;
import com.zongbutech.lxleanlingdongdemo.ZXingDemo.CameraDemo.QRCodeDecoder;

import java.io.ByteArrayOutputStream;

public class ImageChangeAcitivyt extends AppCompatActivity {
    Bitmap mBitmap;
    ImageView ChangeImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_change_acitivyt);
        BitmapDrawable mBitmapDrawable = (BitmapDrawable) ImageChangeAcitivyt.this.getResources().getDrawable(R.drawable.imge_src);
        mBitmap = mBitmapDrawable.getBitmap();

        ChangeImage = (ImageView) findViewById(R.id.ChangeImage);

    }


    public void getInfo(View v) {
        String result = QRCodeDecoder.syncDecodeQRCode(mBitmap);
        mToast(result);
    }


    public void changeInfo(View v) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] mData = baos.toByteArray();
        Bitmap newBitmap =  BitmapFactory.decodeByteArray(mData, 0, mData.length);
        ChangeImage.setImageBitmap(newBitmap);



//        int width = mBitmap.getWidth();
//        int height = mBitmap.getHeight();
//        mBitmap.getRowBytes()*height
//        width*height
//        byte[] rotatedData = new byte[mData.length];
//        rotatedData.length
//        Log.e("","");


//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                rotatedData[x + y * width] = mData[x + y * width];
//            }
//        }
//        final YuvImage image = new YuvImage(mData, ImageFormat.NV21, width, height, null);
//        ByteArrayOutputStream os = new ByteArrayOutputStream(mData.length);
//        if (image.compressToJpeg(new Rect(0, 0, width, height), 100, os)) {
//            byte[] tmp = os.toByteArray();
//            Bitmap bmp = BitmapFactory.decodeByteArray(tmp, 0, tmp.length);
//            Matrix matrix = new Matrix();
//            matrix.reset();
//            matrix.postRotate(90);
//            Bitmap NewBitmap =  Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
//            ChangeImage.setImageBitmap(NewBitmap);
//
//        }
    }


    public void mToast(String s) {
        Toast.makeText(ImageChangeAcitivyt.this, s, Toast.LENGTH_SHORT).show();
    }
}

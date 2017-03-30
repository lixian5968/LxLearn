package com.zongbutech.lxleanlingdongdemo.ZXingDemo.LxZxingDemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class LxProcessDataTask extends AsyncTask<Void, Void, String> {
    private Camera mCamera;
    private byte[] mData;
    private Delegate mDelegate;

    public LxProcessDataTask(Camera camera, byte[] data, Delegate delegate) {
        mCamera = camera;
        mData = data;
        mDelegate = delegate;
    }

    public LxProcessDataTask perform() {
        if (Build.VERSION.SDK_INT >= 11) {
            executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            execute();
        }
        return this;
    }

    public void cancelTask() {
        if (getStatus() != Status.FINISHED) {
            cancel(true);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mDelegate = null;
    }

    boolean frist =true;
    @Override
    protected String doInBackground(Void... params) {
        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size size = parameters.getPreviewSize();
        int width = size.width;
        int height = size.height;

        if(LxZXingDemo.instance!=null &&frist ){
            frist =false;
            //生产图片
            LxZXingDemo.instance.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Camera.Parameters parameters = mCamera.getParameters();
                    Camera.Size size = parameters.getPreviewSize();
                    int width = size.width;
                    int height = size.height;

//                    byte[] rotatedData = new byte[mData.length];
//                    for (int y = 0; y < height; y++) {
//                        for (int x = 0; x < width; x++) {
//                            rotatedData[x * height + height - y - 1] = mData[x + y * width];
//                        }
//                    }
//                    int zong = width;
//                    width =height;
//                    height =zong;
//                    final YuvImage image = new YuvImage(rotatedData, ImageFormat.NV21, width, height, null);


//                    byte[] rotatedData = new byte[mData.length];
//                    for (int y = 0; y < height; y++) {
//                        for (int x = 0; x < width; x++) {
//                            rotatedData[x + y * width] = mData[x + y * width];
//                        }
//                    }




                    final YuvImage image = new YuvImage(mData, ImageFormat.NV21, width, height, null);
                    ByteArrayOutputStream os = new ByteArrayOutputStream(mData.length);
                    if(image.compressToJpeg(new Rect(0, 0, width, height), 100, os)){
                        byte[] tmp = os.toByteArray();
                        Bitmap bmp = BitmapFactory.decodeByteArray(tmp, 0,tmp.length);

                        Matrix matrix = new Matrix();
                        matrix.reset();
                        matrix.postRotate(90);
                        LxZXingDemo.instance.setBitmap(Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true));

                        Log.e("","");
                    }

//                    mCamera.getParameters().getPictureFormat();
//                    Bitmap newBitmap =  BitmapFactory.decodeByteArray(mData, 0, mData.length);
//                    if(newBitmap!=null){
//                        Matrix matrix = new Matrix();
//                        matrix.reset();
//                        matrix.postRotate(90);
//                        LxZXingDemo.instance.setBitmap(Bitmap.createBitmap(newBitmap, 0, 0, newBitmap.getWidth(), newBitmap.getHeight(), matrix, true));
//                    }


                }
            });
        }






        byte[] rotatedData = new byte[mData.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rotatedData[x * height + height - y - 1] = mData[x + y * width];
            }
        }
        int tmp = width;
        width = height;
        height = tmp;

        try {
            if (mDelegate == null) {
                return null;
            }
            return mDelegate.processData(rotatedData, width, height, false);
        } catch (Exception e1) {
            try {
                return mDelegate.processData(rotatedData, width, height, true);
            } catch (Exception e2) {
                return null;
            }
        }


//        try {
//            if (mDelegate == null) {
//                return null;
//            }
//            return mDelegate.processData(mData, width, height, false);
//        } catch (Exception e1) {
//            try {
//                return mDelegate.processData(mData, width, height, true);
//            } catch (Exception e2) {
//                return null;
//            }
//        }
    }

    public interface Delegate {
        String processData(byte[] data, int width, int height, boolean isRetry);
    }
}

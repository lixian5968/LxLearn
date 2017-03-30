package com.zongbutech.lxleanlingdongdemo.ZXingDemo.LxZxingDemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;


/**
 * Created by lixian on 2017/3/23.
 */

public class LxZxingSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback , LxProcessDataTask.Delegate {
    public LxZxingSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public LxZxingSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LxZxingSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    SurfaceHolder mSurfaceHolder;
    Context ct;
    Handler mHandler;


    private void init(Context context) {
        ct = context;
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        mHandler = new Handler();

    }


    Camera camera;
    private CameraConfigurationManager mCameraConfigurationManager;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open(0);
        try {
            camera.setPreviewDisplay(holder);    //这句是用来设置显示的，不调用的话没有图像
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (ct instanceof Activity) {
            setCameraDisplayOrientation((Activity) ct, 0, camera);
        }


        init();


        camera.startPreview();
//        camera.autoFocus(autoFocusCB);
    }

    private void init() {
//        mCameraConfigurationManager = new CameraConfigurationManager(getContext());
//        mCameraConfigurationManager.initFromCameraParameters(camera);
//        mCameraConfigurationManager.setDesiredCameraParameters(camera);

        Camera.Parameters parameters = camera.getParameters();
        //设置图片大小的分辨率
        parameters.setPreviewSize(1920, 1080);
        parameters.set("zoom", String.valueOf(27 / 10.0));
        camera.setDisplayOrientation(90);
        camera.setParameters(parameters);

//        camera.getParameters().get("zoom");
//        camera.getParameters().getPreviewSize();
        Log.e("", "");
    }


    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            postDelayed(doAutoFocus, 1000);
        }
    };
    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (camera != null) {
                try {
                    camera.autoFocus(autoFocusCB);
                } catch (Exception e) {
                }
            }
        }
    };

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            try {
                camera.cancelAutoFocus();
                camera.setOneShotPreviewCallback(null);
                camera.stopPreview();
            } catch (Exception e) {
                Log.e("Lx", e.toString(), e);
            }
        }
    }


    public void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;   // compensate the mirror
        } else {   // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }


    //开始扫描
    MultiFormatReader    mMultiFormatReader;
    public void startSacn() {
        mHandler.removeCallbacks(mOneShotPreviewCallbackTask);
        mHandler.postDelayed(mOneShotPreviewCallbackTask, 1500);
        mMultiFormatReader = new MultiFormatReader();
        //public void setHints（Map < DecodeHintType，？> hints）
//        此方法将状态添加到MultiFormatReader。通过设置提示一次，对decodeWithState（image）的后续调用可以重用同一组读取器，而无需重新分配内存。这对于连续扫描客户端的性能很重要。
        mMultiFormatReader.setHints(QRCodeDecoder.HINTS);

    }

    private Runnable mOneShotPreviewCallbackTask = new Runnable() {
        @Override
        public void run() {
            if (camera != null) {
                try {
                    camera.setOneShotPreviewCallback(LxZxingSurfaceView.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    protected QRCodeView.Delegate mDelegate;
    @Override
    public void onPreviewFrame(byte[] data, final Camera camera) {
        cancelProcessDataTask();
        mLxProcessDataTask = new LxProcessDataTask(camera, data, this) {
            @Override
            protected void onPostExecute(String result) {
                if (mDelegate != null && !TextUtils.isEmpty(result)) {
                    try {
                        mDelegate.onScanQRCodeSuccess(result);
                    } catch (Exception e) {
                    }
                } else {
                    try {
                        camera.setOneShotPreviewCallback(LxZxingSurfaceView.this);
                    } catch (Exception e) {
                    }
                }
            }
        }.perform();
    }



    /**
     * 取消数据处理任务
     */
    protected LxProcessDataTask mLxProcessDataTask;

    protected void cancelProcessDataTask() {
        if (mLxProcessDataTask != null) {
            mLxProcessDataTask.cancelTask();
            mLxProcessDataTask = null;
        }
    }


    @Override
    public String processData(byte[] data, int width, int height, boolean isRetry) {
        String result = null;
        Result rawResult = null;

        try {
            PlanarYUVLuminanceSource source = null;
            if (rect != null) {
                source = new PlanarYUVLuminanceSource(data, width, height, rect.left, rect.top, rect.width(), rect.height(), false);
            } else {
                source = new PlanarYUVLuminanceSource(data, width, height, 0, 0, width, height, false);
            }
            rawResult = mMultiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(source)));





        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mMultiFormatReader.reset();
        }

        if (rawResult != null) {
            result = rawResult.getText();
        }
        return result;
    }



    Rect rect;
    public void setScan(Rect scan) {
        this.rect =scan;
    }

    public void setListener(QRCodeView.Delegate delegate) {
        mDelegate =delegate;
    }
}

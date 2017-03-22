package com.zongbutech.lxleanlingdongdemo.ZXingDemo.CameraDemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by lixian on 2017/3/21.
 */

public class LxSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {
    public LxSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public LxSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);


    }

    public LxSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    SurfaceHolder mSurfaceHolder;

    Context ct;

    private void init(Context context) {
        ct = context;
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
    }


    Camera camera;

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


//        Camera.Parameters parameters = camera.getParameters();    //获取他的熟悉来进行设置
//  /* 设置预览照片的大小，此处设置为全屏 */
//        WindowManager wm = (WindowManager) ct.getSystemService(Context.WINDOW_SERVICE); // 获取当前屏幕管理器对象
//        Display display = wm.getDefaultDisplay();                           // 获取屏幕信息的描述类
//        parameters.setPreviewSize(display.getWidth(), display.getHeight());
//        parameters.setPictureSize(display.getWidth(), display.getHeight());// 如果是定制机或是进行过深度测试的不需要设置这俩个属性
//  /* 每秒从摄像头捕获5帧画面， */
//        parameters.setPreviewFrameRate(5);
//  /* 设置照片的输出格式:jpg */
//        parameters.setPictureFormat(PixelFormat.JPEG);
//  /* 照片质量 */
//        parameters.set("jpeg-quality", 85);
//            /* 设置照片的大小：此处照片大小等于屏幕大小 */
//  /* 将参数对象赋予到 camera 对象上 */
//        camera.setParameters(parameters);
        camera.startPreview();

//        this.holder.lockCanvas();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("", "");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        setupLayout();
        Log.e("", "");
    }

    @Override
    public void onDraw(Canvas canvas) {
        Log.e("", "");
        drawFrame(canvas);
    }
    RectF mFrameRect;
    private void drawFrame(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        Paint mPaintFrame = new Paint();
        mPaintFrame.setAntiAlias(true);
        mPaintFrame.setFilterBitmap(true);
        mPaintFrame.setStyle(Paint.Style.STROKE);
        mPaintFrame.setColor(Color.BLUE);
        mPaintFrame.setStrokeWidth(2.0f);
        mFrameRect = new RectF((float) 240.0, (float) 223.5, (float) 840.0, (float) 824.5);
        canvas.drawRect(mFrameRect, mPaintFrame);

//        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
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

    GetImageListener listener;

    public void setOngetImageListener(GetImageListener listener) {
        this.listener = listener;
    }


    public interface GetImageListener {
        void getImage(Bitmap bt, Bitmap bt2);
    }

    public void getImage() {
        camera.autoFocus(new Camera.AutoFocusCallback() {//自动对焦
            @Override
            public void onAutoFocus(boolean success, Camera mCamera) {
                // TODO Auto-generated method stub
                if (success) {
                    camera.cancelAutoFocus();
                    //设置参数，并拍照
                    Camera.Parameters params = camera.getParameters();
                    params.setPictureFormat(PixelFormat.JPEG);//图片格式
                    params.setPreviewSize(800, 480);//图片大小
                    camera.setParameters(params);//将参数设置到我的camera
                    camera.takePicture(null, null, jpeg);//将拍摄到的照片给自定义的对象
                }
            }
        });
//        try {
//            camera.setOneShotPreviewCallback(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//        listener.getImage(bitmap);
    }

    Camera.PictureCallback jpeg = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                Matrix matrix = new Matrix();
                matrix.reset();
                matrix.postRotate(90);
                Bitmap bMapRotate = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                listener.getImage(bMapRotate,getBitMapSmall(bMapRotate));
                camera.stopPreview();//关闭预览 处理数据
                camera.startPreview();//数据处理完后继续开始预览
//                bitmap.recycle();//回收bitmap空间
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    private Bitmap getBitMapSmall(Bitmap originBitmap) {
        //原始照片的宽高
        float picWidth = originBitmap.getWidth();
        float picHeight = originBitmap.getHeight();

        //预览界面的宽高
        float preWidth = getWidth();
        float preHeight = getHeight();

        //预览界面和照片的比例
        float preRW = picWidth / preWidth;
        float preRH = picHeight / preHeight;

        //裁剪框的位置和宽高
        float frameLeft = mFrameRect.left;
        float frameTop = mFrameRect.top;
        float frameWidth = mFrameRect.width();
        float frameHeight = mFrameRect.height();

        int cropLeft = (int) (frameLeft * preRW);
        int cropTop = (int) (frameTop * preRH);
        int cropWidth = (int) (frameWidth * preRW);
        int cropHeight = (int) (frameHeight * preRH);

        Bitmap cropBitmap = Bitmap.createBitmap(originBitmap, cropLeft, cropTop, cropWidth, cropHeight);
        return cropBitmap;
    }
}

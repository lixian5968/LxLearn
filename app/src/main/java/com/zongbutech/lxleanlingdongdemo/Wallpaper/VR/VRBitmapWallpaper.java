package com.zongbutech.lxleanlingdongdemo.Wallpaper.VR;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import com.zongbutech.lxleanlingdongdemo.R;

public class VRBitmapWallpaper extends WallpaperService {


    // 实现WallpaperService必须实现的抽象方法  
    public Engine onCreateEngine() {
        // 返回自定义的CameraEngine
        return new CameraEngine();
    }


    class CameraEngine extends Engine {

        private WallpaperGLSurfaceView glSurfaceView;
        private boolean rendererSet;
        Ball mBall;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            glSurfaceView = new WallpaperGLSurfaceView(VRBitmapWallpaper.this);
            mBall = new Ball(getApplicationContext(), R.drawable.img2_vr);
            glSurfaceView.setEGLContextClientVersion(2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                glSurfaceView.setPreserveEGLContextOnPause(true);
            }
            glSurfaceView.setRenderer(mBall);
            rendererSet = true;
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);

            if (rendererSet) {
                if (visible) {
                    glSurfaceView.onResume();
                } else {
                    glSurfaceView.onPause();
                }
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();

            glSurfaceView.onWallpaperDestroy();
        }


        class WallpaperGLSurfaceView extends GLSurfaceView {
            private static final String TAG = "WallpaperGLSurfaceView";

            WallpaperGLSurfaceView(Context context) {
                super(context);
            }

            @Override
            public SurfaceHolder getHolder() {

                return getSurfaceHolder();
            }

            public void onWallpaperDestroy() {

                super.onDetachedFromWindow();
            }
        }
    }
}
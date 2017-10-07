package com.zongbutech.lxleanlingdongdemo.Wallpaper;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

import java.io.File;

public class VideoLiveWallpaper extends WallpaperService {

    public static boolean ShowVolume =false;

    // 实现WallpaperService必须实现的抽象方法  
    public Engine onCreateEngine() {
        // 返回自定义的CameraEngine
        return new CameraEngine();
    }


    class CameraEngine extends Engine {


        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    playVideo(getSurfaceHolder());
                }
            }, 500);

            // 设置处理触摸事件
            setTouchEventsEnabled(true);

        }


        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);

        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if(mediaPlayer!=null){
                mediaPlayer.stop();
            }
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if (visible) {
                if(mediaPlayer!=null){
                    mediaPlayer.start();
                    if(ShowVolume){
                        mediaPlayer.setVolume(1f, 1f);
                    }else{
                        mediaPlayer.setVolume(0f, 0f);
                    }
                }
            } else {
                if(mediaPlayer!=null){
//                    mediaPlayer.stop();
                    if(ShowVolume){
                        mediaPlayer.setVolume(1f, 1f);
                    }else{
                        mediaPlayer.setVolume(0f, 0f);
                    }
                }
            }
        }
    }



    public static String PlayUrl ="";
    MediaPlayer mediaPlayer;
    File file=null;
    private void playVideo(SurfaceHolder surfaceHolder) {
        if(PlayUrl==null || PlayUrl.length()==0){
            file = new File(Environment.getExternalStorageDirectory(), "video.mp4");
        }else{
            file = new File(PlayUrl);
        }
        if (!file.exists()) {
            Toast.makeText(this, "视频文件路径错误", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 设置播放的视频源
            mediaPlayer.setDataSource(file.getAbsolutePath());
            // 设置显示视频的SurfaceHolder
//            mediaPlayer.setDisplay(surfaceHolder);
            mediaPlayer.setSurface(surfaceHolder.getSurface());

            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    mediaPlayer.setVolume(0f, 0f);
                    // 按照初始位置播放
                    mediaPlayer.seekTo(0);
                    Log.e("","");
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.e("","");
                    mediaPlayer.start();
                    // 按照初始位置播放
                    mediaPlayer.seekTo(0);
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.e("","");
                    mediaPlayer.start();
                    mediaPlayer.seekTo(0);
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
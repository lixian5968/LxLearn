package com.zongbutech.lxleanlingdongdemo.MP4SRTDemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.zongbutech.lxleanlingdongdemo.R;


public class MP4SRTDemoActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    MediaPlayer mediaPlayer;
    SurfaceHolder surfaceHolder;
    SurfaceView playerSurfaceView;
    TextView tv_subtitle;
    String videoSrc = Environment.getExternalStorageDirectory().getPath() + "/f2.mp4";//SRTDemo
    String subTitleSrc = Environment.getExternalStorageDirectory().getPath() + "/f2.srt";
    Context ct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp4_srtdemo);
        ct = this;

        playerSurfaceView = (SurfaceView) findViewById(R.id.playersurface);
        tv_subtitle = (TextView) findViewById(R.id.tv_subtitle);
        surfaceHolder = playerSurfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void surfaceCreated(SurfaceHolder arg0) {

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDisplay(surfaceHolder);
            mediaPlayer.setDataSource(videoSrc);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepare();


            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (Exception e) {
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onPrepared(MediaPlayer mp) {
        try {
            mediaPlayer.addTimedTextSource(subTitleSrc, MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
            MediaPlayer.TrackInfo[] trackInfos = mediaPlayer.getTrackInfo();

            if (trackInfos != null && trackInfos.length > 0) {
                for (int i = 0; i < trackInfos.length; i++) {
                    final MediaPlayer.TrackInfo info = trackInfos[i];
                    if (info.getTrackType() == MediaPlayer.TrackInfo.MEDIA_TRACK_TYPE_AUDIO) {
                        // mMediaPlayer.selectTrack(i);
                    } else if (info.getTrackType() == MediaPlayer.TrackInfo.MEDIA_TRACK_TYPE_TIMEDTEXT) {
                        mediaPlayer.selectTrack(i);
                    }
                }
            }

            mediaPlayer.setOnTimedTextListener(new MediaPlayer.OnTimedTextListener() {
                @Override
                public void onTimedText(final MediaPlayer mediaPlayer, final TimedText timedText) {
                    if (timedText != null) {
                        String strSend = "";
                        try {
                            strSend = new String(timedText.getText().getBytes("UTF-8"), "UTF-8");
                        } catch (Exception e) {
                            e.printStackTrace();
                            strSend = "";
                        }
                        tv_subtitle.setText(strSend);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        mediaPlayer.start();
    }
}
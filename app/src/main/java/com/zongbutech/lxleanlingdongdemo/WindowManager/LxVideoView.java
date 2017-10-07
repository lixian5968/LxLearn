package com.zongbutech.lxleanlingdongdemo.WindowManager;

import android.content.Context;
import android.graphics.Point;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.zongbutech.lxleanlingdongdemo.R;

import java.io.File;

/**
 * Created by jinliangshan on 16/12/26.
 */
public class LxVideoView extends LinearLayout {
    public static final String TAG = "FloatingView";

    VideoView mVideoView;
    WindowManager mWindowManager;
    TextView   mTextView;


    public LxVideoView(Context context) {
        super(context);
        View v = inflate(context, R.layout.activity_movie_windows_video, this);
        mVideoView = (VideoView) v.findViewById(R.id.mVideoView);
        mTextView = (TextView) v.findViewById(R.id.mTextView);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        mTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mVideoView.isPlaying()){
                    mVideoView.pause();
                }else{
                    mVideoView.start();
                }
            }
        });

        mTextView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mVideoView.getVisibility() == View.VISIBLE){
                    mVideoView.pause();
                    mVideoView.setVisibility(View.GONE);
                }else{
                    mVideoView.pause();
                    mVideoView.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }

    public void StartVideo() {
        File mFile = new File(Environment.getExternalStorageDirectory(), "video.mp4");
        if (mFile.exists()) {
            mVideoView.setVideoPath(mFile.getAbsolutePath());
            mVideoView.start();
            mVideoView.requestFocus();
        }
    }

    Point preP, curP;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preP = new Point((int) event.getRawX(), (int) event.getRawY());
                break;

            case MotionEvent.ACTION_MOVE:
                curP = new Point((int) event.getRawX(), (int) event.getRawY());
                int dx = curP.x - preP.x,
                        dy = curP.y - preP.y;

                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.getLayoutParams();
                layoutParams.x += dx;
                layoutParams.y += dy;
                mWindowManager.updateViewLayout(this, layoutParams);

                preP = curP;
                break;
        }
        return false;
    }

}

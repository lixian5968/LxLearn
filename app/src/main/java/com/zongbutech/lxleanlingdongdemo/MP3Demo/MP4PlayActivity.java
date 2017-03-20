package com.zongbutech.lxleanlingdongdemo.MP3Demo;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zongbutech.lxleanlingdongdemo.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MP4PlayActivity extends AppCompatActivity {
    Context ct;
    List<LrcDemo> mLrcDemos;
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp4_play);
        ct = MP4PlayActivity.this;

        mTextView = $(R.id.mTextView);

        //从assets目录下读取歌词文件内容
        String lrc = getFromAssets("cbg2.lrc");
        List<String> mStrings = Arrays.asList(lrc.split("\r\n"));
        mLrcDemos = new ArrayList<>();
        for (String mString : mStrings) {
            if (!(mString.indexOf("[") != 0 || mString.indexOf("]") != 9)) {
                int lastIndexOfRightBracket = mString.lastIndexOf("]");
                String content = mString.substring(lastIndexOfRightBracket + 1, mString.length());
                String times = mString.substring(0, lastIndexOfRightBracket + 1).replace("[", "").replace("]", "");
                mLrcDemos.add(new LrcDemo(times, timeConvert(times), content));
            }
        }
        Log.e("", "");
        mp = MediaPlayer.create(ct, R.raw.man);
        //准备播放歌曲监听
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            //准备完毕
            public void onPrepared(MediaPlayer mp) {
//                mp.start();
                if (mTimer == null) {
                    mTimer = new Timer();
                    mTask = new LrcTask();
                    mTimer.scheduleAtFixedRate(mTask, 0, mPalyTimerDuration);
                }
            }
        });
    }
    MediaPlayer mp;

    public <T extends View> T $(int id) {
        return (T) findViewById(id);
    }

    /**
     * 将解析得到的表示时间的字符转化为Long型
     */
    private static long timeConvert(String timeString) {
        //因为给如的字符串的时间格式为XX:XX.XX,返回的long要求是以毫秒为单位
        //将字符串 XX:XX.XX 转换为 XX:XX:XX
        timeString = timeString.replace('.', ':');
        //将字符串 XX:XX:XX 拆分
        String[] times = timeString.split(":");
        // mm:ss:SS
        return Integer.valueOf(times[0]) * 60 * 1000 +//分
                Integer.valueOf(times[1]) * 1000 +//秒
                Integer.valueOf(times[2]);//毫秒
    }

    /**
     * 从assets目录下读取歌词文件内容
     *
     * @param fileName
     * @return
     */
    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String result = "";
            while ((line = bufReader.readLine()) != null) {
                if (line.trim().equals(""))
                    continue;
                result += line + "\r\n";
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }






    public void PlayMp3(View v) {

        if(mp.isPlaying()){
            mp.pause();
        }else{
            mp.start();
        }





    }

    //更新歌词的频率，每秒更新一次
    private int mPalyTimerDuration = 1000;
    //更新歌词的定时器
    private Timer mTimer;
    //更新歌词的定时任务
    private TimerTask mTask;

    /**
     * 展示歌曲的定时任务
     */
    class LrcTask extends TimerTask {
        @Override
        public void run() {
            //获取歌曲播放的位置
            final long timePassed = mp.getCurrentPosition();
            MP4PlayActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    for (int i = 0; i < mLrcDemos.size(); i++) {
                        LrcDemo current = mLrcDemos.get(i);
                        LrcDemo next = i + 1 == mLrcDemos.size() ? null : mLrcDemos.get(i + 1);
                        /**
                         *  正在播放的时间大于current行的歌词的时间而小于next行歌词的时间， 设置要高亮的行为current行
                         *  正在播放的时间大于current行的歌词，而current行为最后一句歌词时，设置要高亮的行为current行
                         */
                        if ((timePassed >= current.time && next != null && timePassed < next.time)
                                || (timePassed > current.time && next == null)){
                            //滚动歌词
                            mTextView.setText(current.content);
                        }
                    }

                }
            });

        }
    }

    ;
}

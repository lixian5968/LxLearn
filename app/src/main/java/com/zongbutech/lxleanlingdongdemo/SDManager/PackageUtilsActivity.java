package com.zongbutech.lxleanlingdongdemo.SDManager;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.zongbutech.lxleanlingdongdemo.R;

import java.io.File;

public class PackageUtilsActivity extends AppCompatActivity {
    TextView mTextView;
    String TAG = "LxDemo";


    /**
     * 03-06 16:31:35.506 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: isSdcardMounted ret=true
     03-06 16:31:35.514 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: isExternalStorageEmulated:true;isExternalStorageRemovable:false
     03-06 16:31:35.517 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: isSdcardMounted ret=true
     03-06 16:31:35.518 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: DIRECTORY_NOTIFICATIONS=/storage/emulated/0/Notifications
     03-06 16:31:35.519 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: DIRECTORY_ALARMS=/storage/emulated/0/Alarms
     03-06 16:31:35.520 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: DIRECTORY_DCIM=/storage/emulated/0/DCIM
     03-06 16:31:35.521 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: DIRECTORY_DOWNLOADS=/storage/emulated/0/Download
     03-06 16:31:35.522 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: DIRECTORY_MOVIES=/storage/emulated/0/Movies
     03-06 16:31:35.523 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: DIRECTORY_MUSIC=/storage/emulated/0/Music
     03-06 16:31:35.524 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: DIRECTORY_PICTURES=/storage/emulated/0/Pictures
     03-06 16:31:35.525 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: DIRECTORY_PODCASTS=/storage/emulated/0/Podcasts
     03-06 16:31:35.527 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: DIRECTORY_RINGTONES=/storage/emulated/0/Ringtones
     03-06 16:31:35.530 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: isExternalStorageEmulated=true
     03-06 16:31:35.533 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: isExternalStorageRemovable=false
     03-06 16:31:35.533 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: getDataDirectory()=/data
     03-06 16:31:35.534 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: getExternalStorageDirectory()=/storage/emulated/0
     03-06 16:31:35.534 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: getDownloadCacheDirectory()=/cache
     03-06 16:31:35.534 8948-8948/com.zongbutech.lxleanlingdongdemo E/LxDemo: getRootDirectory()=/system
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_utils);
        mTextView = (TextView) findViewById(R.id.mTextView);


        String str = Environment.getExternalStorageState();
        boolean ret = Environment.MEDIA_MOUNTED.equals(str);
        Log.e("LxDemo", "isSdcardMounted ret=" + ret);

        Log.e("LxDemo", "isExternalStorageEmulated:" + Environment.isExternalStorageEmulated() + ";isExternalStorageRemovable:" + Environment.isExternalStorageRemovable());


//        static boolean	isExternalStorageEmulated()
//        返回是否模拟主共享/外部存储介质。
//        static boolean	isExternalStorageEmulated(File path)
//        返回给定路径上的共享/外部存储介质是否被模拟。
//        static boolean	isExternalStorageRemovable()
//        返回主共享/外部存储介质是否是物理可移动的。
//        static boolean	isExternalStorageRemovable(File path)
//        返回给定路径上的共享/外部存储介质是否是物理可移动的。

        isSdcardMounted();
        publicDirTest();
        enviromentAPITest();
    }

    private boolean isSdcardMounted() {
        String str = Environment.getExternalStorageState();
        boolean ret = Environment.MEDIA_MOUNTED.equals(str);

        Log.e(TAG, "isSdcardMounted ret=" + ret);

        return ret;
    }

    private void enviromentAPITest() {

        Log.e(TAG, "isExternalStorageEmulated=" + Environment.isExternalStorageEmulated());
        Log.e(TAG, "isExternalStorageRemovable=" + Environment.isExternalStorageRemovable());

        File file = null;
        file = Environment.getDataDirectory();
        Log.e(TAG, "getDataDirectory()=" + file.getPath());

        file = Environment.getExternalStorageDirectory();
        Log.e(TAG, "getExternalStorageDirectory()=" + file.getPath());

        file = Environment.getDownloadCacheDirectory();
        Log.e(TAG, "getDownloadCacheDirectory()=" + file.getPath());

        file = Environment.getRootDirectory();
        Log.e(TAG, "getRootDirectory()=" + file.getPath());
    }

    private void publicDirTest() {
        File file = null;
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS);//通知的目录
        Log.e(TAG, "DIRECTORY_NOTIFICATIONS=" + file.getPath());

        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);//闹钟列表中的任何音频文件
        Log.e(TAG, "DIRECTORY_ALARMS=" + file.getPath());

        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);//图片和视频的传统位置
        Log.e(TAG, "DIRECTORY_DCIM=" + file.getPath());

        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);//已下载的文件的标准目录
        Log.e(TAG, "DIRECTORY_DOWNLOADS=" + file.getPath());

        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);//用于放置用户可用的电影的标准目录
        Log.e(TAG, "DIRECTORY_MOVIES=" + file.getPath());

        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);//在其中放置应该在用户的音乐的常规列表中的任何音频文件的标准目录
        Log.e(TAG, "DIRECTORY_MUSIC=" + file.getPath());

        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);//用于放置用户可用的图片的标准目录
        Log.e(TAG, "DIRECTORY_PICTURES=" + file.getPath());

        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS);//用于放置应在用户可以选择的播客列表中的任何音频文件（不是常规音乐）的标准目录
        Log.e(TAG, "DIRECTORY_PODCASTS=" + file.getPath());

        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES);//用于放置应在用户可以选择的铃声列表中的任何音频文件的标准目录
        Log.e(TAG, "DIRECTORY_RINGTONES=" + file.getPath());
    }

}

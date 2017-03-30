package com.zongbutech.lxleanlingdongdemo.Base;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.zongbutech.lxleanlingdongdemo.Utils.SharePrefUtil;

import java.util.Locale;


/**
 * Created by sll on 2016/3/8.
 */
public class MyApplication extends Application {
    public static final int ChanegBySystem = 0;
    public static final int EnglishUS = 1;
    public static final int ChineseSimple = 2;
    public static final int ChineseTraditional = 3;

    @Override
    public void onCreate() {
        super.onCreate();


        Resources resources = getResources();
        Configuration config = resources.getConfiguration(); // 获取资源配置

        int LanguageType = SharePrefUtil.getInt(getBaseContext(), "LanguageType", 0);
        if (LanguageType == MyApplication.ChanegBySystem) {
            config.locale = Locale.getDefault();
        } else if (LanguageType == MyApplication.EnglishUS) {
            config.locale = Locale.US; // 设置当前语言配置为英文
        } else if (LanguageType == MyApplication.ChineseSimple) {
            config.locale = Locale.SIMPLIFIED_CHINESE; // 设置当前语言配置为英文
        } else if (LanguageType == MyApplication.ChineseTraditional) {
            config.locale = Locale.TRADITIONAL_CHINESE; // 设置当前语言配置为英文
        }
        DisplayMetrics metrics = new DisplayMetrics();
        resources.updateConfiguration(config, metrics); // 更新配置文件

    }

}

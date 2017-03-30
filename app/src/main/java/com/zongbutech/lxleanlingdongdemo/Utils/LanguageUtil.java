package com.zongbutech.lxleanlingdongdemo.Utils;

import android.content.Context;

import com.zongbutech.lxleanlingdongdemo.Base.MyApplication;

import java.util.Locale;

/**
 * Created by lixian on 2017/3/30.
 */

public class LanguageUtil {
    /**
     * 是否是设置值
     *
     * @return 是否是设置值
     */
    public static boolean isSetValue(Context ct) {
        Locale currentLocale = ct.getResources().getConfiguration().locale;
        Locale SetLocal = Locale.getDefault();
        int LanguageType = SharePrefUtil.getInt(ct, "LanguageType", 0);
        if (LanguageType == MyApplication.ChanegBySystem) {
            SetLocal = Locale.getDefault();
        } else if (LanguageType == MyApplication.EnglishUS) {
            SetLocal = Locale.US; // 设置当前语言配置为英文
        } else if (LanguageType == MyApplication.ChineseSimple) {
            SetLocal = Locale.SIMPLIFIED_CHINESE; // 设置当前语言配置为英文
        } else if (LanguageType == MyApplication.ChineseTraditional) {
            SetLocal = Locale.TRADITIONAL_CHINESE; // 设置当前语言配置为英文
        }
        return currentLocale.equals(SetLocal);
    }
}

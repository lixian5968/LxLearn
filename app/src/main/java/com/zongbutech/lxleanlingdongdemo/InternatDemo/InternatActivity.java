package com.zongbutech.lxleanlingdongdemo.InternatDemo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zongbutech.lxleanlingdongdemo.Base.BaseActivity;
import com.zongbutech.lxleanlingdongdemo.Base.MyApplication;
import com.zongbutech.lxleanlingdongdemo.R;
import com.zongbutech.lxleanlingdongdemo.Utils.SharePrefUtil;

import java.util.Locale;

public class InternatActivity extends BaseActivity {

    Context ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Resources resources = getResources();
//        DisplayMetrics dm = resources.getDisplayMetrics();
//        Configuration config = resources.getConfiguration();
//        config.locale = Locale.US;
//        resources.updateConfiguration(config, dm);


        setContentView(R.layout.activity_internat);
        ct = this;

        String s = getString(R.string.HelloWorld);

        String sta = getResources().getConfiguration().locale.getCountry();
        //用于判断当前的语言，
        Locale.getDefault().getLanguage(); //语言:取到的比如中文为zh，英文为en，日文为ko；
        Locale.getDefault().toString();//具体的类别:比如繁体为zh_TW，简体为zh_CN。英文中有en_GB；日文有

        Log.e("", "");

    }

    public void ChangeBySystem(View v) {
        SharePrefUtil.saveInt(ct,"LanguageType" ,MyApplication.ChanegBySystem);
        StartActivity();
    }
    public void ChangeChinese(View v) {


//        Resources resources = ct.getResources();
//        DisplayMetrics dm = resources.getDisplayMetrics();
//        Configuration config = resources.getConfiguration();
//// 应用用户选择语言
//        config.locale = Locale.SIMPLIFIED_CHINESE;
//        resources.updateConfiguration(config, dm);
//        StartActivity();

        SharePrefUtil.saveInt(ct,"LanguageType" ,MyApplication.ChineseSimple);
        StartActivity();
    }

    public void ChangeChineseTW(View v) {
//        Resources resources = ct.getResources();
//        DisplayMetrics dm = resources.getDisplayMetrics();
//        Configuration config = resources.getConfiguration();
//// 应用用户选择语言
//        config.locale = Locale.TRADITIONAL_CHINESE;
//        resources.updateConfiguration(config, dm);
//        StartActivity();

        SharePrefUtil.saveInt(ct,"LanguageType" ,MyApplication.ChineseTraditional);
        StartActivity();
    }


    public void ChangeEnglish(View v) {
//        Resources resources = getResources();
//        Configuration config = resources.getConfiguration(); // 获取资源配置
//        config.locale = Locale.US; // 设置当前语言配置为英文
//        DisplayMetrics metrics = new DisplayMetrics();
//        resources.updateConfiguration(config, metrics); // 更新配置文件
//        StartActivity();


        SharePrefUtil.saveInt(ct,"LanguageType" ,MyApplication.EnglishUS);
        StartActivity();
    }





}

package com.zongbutech.lxleanlingdongdemo.Accessibility;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by jinliangshan on 16/12/26.
 */
public class AccessibilityUtil {

    public static void StartAccessibility(Context context) {
        // 判断辅助功能是否开启
        if (!AccessibilityUtil.isAccessibilitySettingsOn(context)) {
            // 引导至辅助功能设置页面
            context.startActivity(
                    new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            );
        }
    }

    public static boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        if (accessibilityEnabled == 1) {
            return false;
//            String services = Settings.Secure.getString(context.getContentResolver(),
//                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);//获取已经开启服务的列表
//            if (services != null) {
//                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
//            }
        }

        return false;
    }
}

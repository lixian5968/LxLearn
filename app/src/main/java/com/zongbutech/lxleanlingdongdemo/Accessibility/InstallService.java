package com.zongbutech.lxleanlingdongdemo.Accessibility;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.io.File;
import java.util.List;

public class InstallService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d("InstallService", event.toString());
        checkInstall(event);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String command = intent.getStringExtra("InstallUrl");
        if (command != null) {
            Uri uri = Uri.fromFile(new File(command));
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            localIntent.setDataAndType(uri, "application/vnd.android.package-archive");
            startActivity(localIntent);
            start = true;

        }
        return super.onStartCommand(intent, flags, startId);
    }


    boolean start = false;

    private void checkInstall(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            Log.e("LxService", event.getPackageName().toString() + "\n" + event.getClassName().toString());
            ////com.zongbutech.lxleanlingdongdemo.Accessibility.LxAllAccessAcitivity
            if ("com.android.packageinstaller.PackageInstallerActivity".equals(event.getClassName().toString()) && start) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    AccessibilityNodeInfo source = getRootInActiveWindow();
                    if (source != null) {
                        List<AccessibilityNodeInfo> installInfos = source.findAccessibilityNodeInfosByText("安装");
                        if (installInfos != null && installInfos.size() > 0) {
                            for (AccessibilityNodeInfo mNode : installInfos) {
                                if (mNode.isEnabled() && mNode.isClickable() && mNode.getClassName().equals("android.widget.Button")) {
                                    mNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                }
                            }
                        }
                    }
                }
            } else if ("com.android.packageinstaller.InstallAppProgress".equals(event.getClassName().toString()) && start) {
                InstallSuccess();
            }
        }
    }

    private void InstallSuccess() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    AccessibilityNodeInfo source = getRootInActiveWindow();
                    if (source != null) {
                        List<AccessibilityNodeInfo> installInfos = source.findAccessibilityNodeInfosByText("完成");
                        if (installInfos != null && installInfos.size() > 0) {
                            for (AccessibilityNodeInfo mNode : installInfos) {
                                if (mNode.isEnabled() && mNode.isClickable() && mNode.getClassName().equals("android.widget.Button")) {
                                    mNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                }
                            }
                        }else{
                            InstallSuccess();
                        }
                    }
                }
            }
        }, 5000);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void installAPK(AccessibilityEvent event) {
        AccessibilityNodeInfo source = getRootInActiveWindow();
        List<AccessibilityNodeInfo> nextInfos = source.findAccessibilityNodeInfosByText("下一步");
        nextClick(nextInfos);
        List<AccessibilityNodeInfo> installInfos = source.findAccessibilityNodeInfosByText("安装");
        nextClick(installInfos);
        List<AccessibilityNodeInfo> openInfos = source.findAccessibilityNodeInfosByText("完成");
        nextClick(openInfos);

        runInBack(event);

    }

    private void runInBack(AccessibilityEvent event) {
        event.getSource().performAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }

    private void nextClick(List<AccessibilityNodeInfo> infos) {
        if (infos != null)
            for (AccessibilityNodeInfo info : infos) {
                if (info.isEnabled() && info.isClickable())
                    info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private boolean checkTilte(AccessibilityNodeInfo source) {
        List<AccessibilityNodeInfo> infos = getRootInActiveWindow().findAccessibilityNodeInfosByViewId("@id/app_name");
        for (AccessibilityNodeInfo nodeInfo : infos) {
            if (nodeInfo.getClassName().equals("android.widget.TextView")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        Log.d("InstallService", "auto install apk");
    }
}


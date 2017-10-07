package com.zongbutech.lxleanlingdongdemo.Accessibility.QQ;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixian on 2017/9/1.
 */

public class QQResultAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e("", "");
        Log.e("LxAccessibilityService", event.getEventType() + "," + event.getPackageName() + "," + event.getClassName());
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                handleNotification(event);
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                String className = event.getClassName().toString();
                if (className.equals("com.tencent.mobileqq.activity.SplashActivity") && SendResult) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();

                        mAccessibilityNodeInfos = new ArrayList<>();
                        getViewByInfo(nodeInfo);
                        if(mAccessibilityNodeInfos.size()>0){
                            AccessibilityNodeInfo mAccessibilityNodeInfo = mAccessibilityNodeInfos.get(0);
                            InsertText(mAccessibilityNodeInfo);
                            sendMessage(nodeInfo);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    back2Home();
                                }
                            },100);
                        }
                    }
                }
                break;
        }

    }


    List<AccessibilityNodeInfo> mAccessibilityNodeInfos;
    private void getViewByInfo(AccessibilityNodeInfo nodeInfoAll) {
        if (nodeInfoAll != null && nodeInfoAll.getChildCount() > 0) {
            for (int i = 0; i < nodeInfoAll.getChildCount(); i++) {
                AccessibilityNodeInfo nodeInfo0=  nodeInfoAll.getChild(i);
                if (nodeInfo0.getChildCount()>0){
                    getViewByInfo(nodeInfo0);
                }else{
                    if ("android.widget.EditText".equals(nodeInfo0.getClassName())) {
                        mAccessibilityNodeInfos.add(nodeInfo0);
                    }
                }
            }
        }
    }

    private void sendMessage(AccessibilityNodeInfo nodeInfo) {
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("发送");
        if (list != null && list.size() > 0) {
            for (AccessibilityNodeInfo n : list) {
                if (n.getClassName().equals("android.widget.Button") && n.isEnabled()) {
                    n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    SendResult = false;
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void InsertText(AccessibilityNodeInfo nodeInfoChild) {
        Bundle arguments = new Bundle();
        arguments.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT,
                AccessibilityNodeInfo.MOVEMENT_GRANULARITY_WORD);
        arguments.putBoolean(AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN,
                true);
        nodeInfoChild.performAction(AccessibilityNodeInfo.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY, arguments);
        nodeInfoChild.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
        ClipData clip = ClipData.newPlainText("label", "自动回复:在忙请稍等");
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(clip);
        nodeInfoChild.performAction(AccessibilityNodeInfo.ACTION_PASTE);
    }

    boolean SendResult = false;

    private void handleNotification(AccessibilityEvent event) {
        List<CharSequence> texts = event.getText();
        if (!texts.isEmpty()) {
            for (CharSequence text : texts) {
                String content = text.toString();
                Log.e("Lx", content + "");
                //如果微信红包的提示信息,则模拟点击进入相应的聊天窗口
                if (content.contains("天使在人间")) {
                    if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
                        Notification notification = (Notification) event.getParcelableData();
                        PendingIntent pendingIntent = notification.contentIntent;
                        try {
                            pendingIntent.send();
                            SendResult = true;
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.e("", "");
    }
    //回到系统桌面
    private void back2Home() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }
}

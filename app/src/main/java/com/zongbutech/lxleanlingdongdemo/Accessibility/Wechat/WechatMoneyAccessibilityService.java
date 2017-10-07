package com.zongbutech.lxleanlingdongdemo.Accessibility.Wechat;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by lixian on 2017/9/1.
 */

public class WechatMoneyAccessibilityService extends AccessibilityService {

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
                if (className.equals("com.tencent.mm.ui.LauncherUI") && SendResult) {//
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                        List<AccessibilityNodeInfo> mWechatList = nodeInfo.findAccessibilityNodeInfosByText("领取红包");
                        if (mWechatList.size() > 0) {
                            AccessibilityNodeInfo mAccessibilityNodeInfo = mWechatList.get(mWechatList.size() - 1).getParent();
                            if (mAccessibilityNodeInfo != null && mAccessibilityNodeInfo.isEnabled()) {
                                mAccessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            }
                        }
                    }
                } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.En_fba4b94f") && SendResult) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                        List<AccessibilityNodeInfo> mNodeList = nodeInfo.findAccessibilityNodeInfosByText(newContent);
                        if (mNodeList != null && mNodeList.size() > 0) {
                            AccessibilityNodeInfo mNodeText = mNodeList.get(0);
                            if (mNodeText != null && mNodeText.getParent() != null) {
                                AccessibilityNodeInfo ParentNode = mNodeText.getParent();
                                if (ParentNode.getChildCount() > 0) {
                                    for (int i = 0; i < ParentNode.getChildCount(); i++) {
                                        if ("android.widget.Button".equals(ParentNode.getChild(i).getClassName())){
                                            if (ParentNode.getChild(i).isEnabled()) {
                                                ParentNode.getChild(i).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("@id/bp6");
                        SendResult = false;
                        SendClose = true;
                    }
                } else if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI".equals(className)&& SendClose ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                        List<AccessibilityNodeInfo> mCloseViews = nodeInfo.findAccessibilityNodeInfosByText("返回");
                        if (mCloseViews != null && mCloseViews.size() > 0) {
                            AccessibilityNodeInfo mAccessibilityNodeInfo = mCloseViews.get(0);
                            if (mAccessibilityNodeInfo.getParent().isEnabled()) {
                                mAccessibilityNodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            }
                        }
                        SendClose = false;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                back2Home();
                            }
                        }, 1000);
                    }
                }

                break;
        }

    }


    //回到系统桌面
    private void back2Home() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }


    boolean SendClose = false;
    boolean SendResult = false;
    String newContent = "";
    private void handleNotification(AccessibilityEvent event)  {
        List<CharSequence> texts = event.getText();
        if (!texts.isEmpty()) {
            for (CharSequence text : texts) {
                String content = text.toString();
                Log.e("Lx", content + "");
                String[] cc = content.split(":");
                if(cc.length>1){
                    content = cc[1].trim();
                    Log.e("Lx", content + "");
                    //如果微信红包的提示信息,则模拟点击进入相应的聊天窗口
                    if (content.contains("[微信红包]")) {
                        if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
                            Notification notification = (Notification) event.getParcelableData();
                            PendingIntent pendingIntent = notification.contentIntent;
                            try {
                                pendingIntent.send();
                                newContent = content.replace("[微信红包]", "");
                                SendResult = true;
                            } catch (PendingIntent.CanceledException e) {
                                e.printStackTrace();
                            }
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


}

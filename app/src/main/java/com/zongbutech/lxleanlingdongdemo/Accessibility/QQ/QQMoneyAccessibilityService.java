package com.zongbutech.lxleanlingdongdemo.Accessibility.QQ;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixian on 2017/9/1.
 */

public class QQMoneyAccessibilityService extends AccessibilityService {

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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                        mAccessibilityNodeInfos = new ArrayList<>();
                        getViewByInfo(nodeInfo);
                        if (mAccessibilityNodeInfos.size() > 0) {
                            AccessibilityNodeInfo mAccessibilityNodeInfo = mAccessibilityNodeInfos.get(mAccessibilityNodeInfos.size() - 1);
                            if (mAccessibilityNodeInfo.isEnabled()) {
                                mAccessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                SendClose=true;
                            }
                        }
                        SendResult = false;
                        newContent = "";
                    }
                } else if ("cooperation.qwallet.plugin.QWalletPluginProxyActivity".equals(className) &&SendClose) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                        mCloseView= new ArrayList<>();
                        getCloseView(nodeInfo);
                        if (mCloseView.size() > 0) {
                            AccessibilityNodeInfo mAccessibilityNodeInfo = mCloseView.get(0);
                            if (mAccessibilityNodeInfo.isEnabled()) {
                                mAccessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            }
                        }else{
//                            AccessibilityNodeInfo mAccessibilityNodeInfo = nodeInfo.findAccessibilityNodeInfosByText("0.01").get(1);
                            List<AccessibilityNodeInfo>  mCloseViews =  nodeInfo.findAccessibilityNodeInfosByText("关闭");
                            if(mCloseViews!=null && mCloseViews.size()>0){
                                AccessibilityNodeInfo mAccessibilityNodeInfo =  mCloseViews.get(0);
                                if (mAccessibilityNodeInfo.isEnabled()) {
                                    mAccessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                }
                            }
                        }
                        SendClose = false;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                back2Home();
                            }
                        },1000);
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


    List<AccessibilityNodeInfo> mAccessibilityNodeInfos;
    private void getViewByInfo(AccessibilityNodeInfo nodeInfoAll) {
        if (nodeInfoAll != null && nodeInfoAll.getChildCount() > 0) {
            for (int i = 0; i < nodeInfoAll.getChildCount(); i++) {
                AccessibilityNodeInfo nodeInfo0 = nodeInfoAll.getChild(i);
                if (nodeInfo0.getChildCount() > 0) {
                    if ("android.widget.RelativeLayout".equals(nodeInfo0.getClassName())
                            && nodeInfo0.getContentDescription() != null
                            && (nodeInfo0.getContentDescription() + "").length() > 0
                            && (nodeInfo0.getContentDescription() + "").contains(newContent)) {
                        mAccessibilityNodeInfos.add(nodeInfo0);
                    } else {
                        getViewByInfo(nodeInfo0);
                    }
                }
            }
        }
    }


    boolean SendResult = false;
    String newContent = "";
    private void handleNotification(AccessibilityEvent event) {
        List<CharSequence> texts = event.getText();
        if (!texts.isEmpty()) {
            for (CharSequence text : texts) {
                String content = text.toString();
                Log.e("Lx", content + "");
                String[] cc = content.split(":");
                content = cc[1].trim();
                Log.e("Lx", content + "");
                //如果微信红包的提示信息,则模拟点击进入相应的聊天窗口
                if (content.contains("[QQ红包]")) {
                    if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
                        Notification notification = (Notification) event.getParcelableData();
                        PendingIntent pendingIntent = notification.contentIntent;
                        try {
                            pendingIntent.send();
                            newContent = content.replace("[QQ红包]", "");
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


    boolean SendClose = false;
    List<AccessibilityNodeInfo> mCloseView;
    private void getCloseView(AccessibilityNodeInfo nodeInfoAll) {
        if (nodeInfoAll != null && nodeInfoAll.getChildCount() > 0) {
            for (int i = 0; i < nodeInfoAll.getChildCount(); i++) {
                AccessibilityNodeInfo nodeInfo0=  nodeInfoAll.getChild(i);
                if (nodeInfo0.getChildCount()>0){
                    getCloseView(nodeInfo0);
                }else{
                    if ("android.widget.ImageButton".equals(nodeInfo0.getClassName()) && "关闭".equals(nodeInfo0.getContentDescription())) {
                        mCloseView.add(nodeInfo0);
                    }
                }
            }
        }
    }

}

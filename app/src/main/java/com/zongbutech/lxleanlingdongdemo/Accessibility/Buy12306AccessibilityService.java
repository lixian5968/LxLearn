package com.zongbutech.lxleanlingdongdemo.Accessibility;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by lixian on 2017/9/1.
 */

public class Buy12306AccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e("", "");
        Log.e("Buy12306Service", event.getEventType() + "," + event.getPackageName() + "," + event.getClassName());
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                String className = event.getClassName().toString();
                if (className.equals("com.MobileTicket.MobileTicket")) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        AccessibilityNodeInfo source = getRootInActiveWindow();
                        if (source != null) {
                            List<AccessibilityNodeInfo> installInfos = source.findAccessibilityNodeInfosByText("G7661");source.getChild(0);
                            if (installInfos != null && installInfos.size() > 0) {
                                for (AccessibilityNodeInfo mNode : installInfos) {
//                                    if (mNode.isEnabled() && mNode.isClickable() && mNode.getClassName().equals("android.widget.Button")) {
//                                        mNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                                    }
                                }
                            }
                        }
                    }
                    Log.e("","");
                }
                break;
        }
    }


    @Override
    public void onInterrupt() {
        Log.e("", "");
    }


}

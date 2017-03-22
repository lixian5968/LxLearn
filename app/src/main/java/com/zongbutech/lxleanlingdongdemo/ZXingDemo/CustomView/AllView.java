package com.zongbutech.lxleanlingdongdemo.ZXingDemo.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import cn.bingoogolapple.qrcode.core.ScanBoxView;

/**
 * Created by lixian on 2017/3/21.
 */

public class AllView extends RelativeLayout {
    public AllView(Context context) {
        super(context);
    }

    public AllView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public AllView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        ScanBoxView mScanBoxView = new ScanBoxView(getContext());
        mScanBoxView.initCustomAttrs(context, attrs);
        addView(mScanBoxView);
    }

}

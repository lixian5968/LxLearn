package com.zongbutech.lxleanlingdongdemo.TestFont;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by lixian on 2017/3/29.
 */

public class LxTextView extends TextView {
    public LxTextView(Context context) {
        super(context);
        init(context);
    }

    public LxTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LxTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));
    }


}

package com.zongbutech.lxleanlingdongdemo.TestFont;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.zongbutech.lxleanlingdongdemo.Base.BaseActivity;
import com.zongbutech.lxleanlingdongdemo.R;

public class TextViewFontActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_font);

        TextView mTextView = $(R.id.mTextView7);
        Typeface mTypeface =  Typeface.create("sans-serif-thin", Typeface.NORMAL);
        mTextView.setTypeface(mTypeface);



    }
}

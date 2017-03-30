package com.zongbutech.lxleanlingdongdemo.PhoneScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.zongbutech.lxleanlingdongdemo.Base.BaseActivity;
import com.zongbutech.lxleanlingdongdemo.R;

public class ScreenTestActivity extends BaseActivity {
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_test);
        mTextView = $(R.id.mTextView);

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        float m1dpTopx =  ScreenTestActivity.this.getResources().getDimension(R.dimen.Lx_1);
        String outString = "宽度："+outMetrics.widthPixels + ",高度：" + outMetrics.heightPixels;
        mTextView.setText(outString+",1dp:"+m1dpTopx);




    }


    //坏点检测
    public void BadPointTest(View v){
        Intent it = new Intent(ScreenTestActivity.this,ReplaceActivity.class);
        it.putExtra("type",ReplaceActivity.BadPoint);
        startActivity(it);
    }

    //坏点检测
    public void SreemTouchTest(View v){
        Intent it = new Intent(ScreenTestActivity.this,SreemTouchActivity.class);
        startActivity(it);
    }


}

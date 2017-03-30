package com.zongbutech.lxleanlingdongdemo.PhoneScreen;

import android.os.Bundle;
import android.view.WindowManager;

import com.zongbutech.lxleanlingdongdemo.Base.BaseActivity;
import com.zongbutech.lxleanlingdongdemo.R;

public class ReplaceActivity extends BaseActivity {

    public static final  int BadPoint = 1;
    BadPointFragment mBadPointFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_replace);
        int type = getIntent().getIntExtra("type", 0);
        mBadPointFragment  = BadPointFragment.newInstance();
        switch (type) {
            case BadPoint:
                getSupportFragmentManager().beginTransaction().replace(R.id.ReplaceFragment,mBadPointFragment).commit();
                break;
        }


    }
}

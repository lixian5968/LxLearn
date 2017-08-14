package com.zongbutech.lxleanlingdongdemo.InPutInfoAndOutofInfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zongbutech.lxleanlingdongdemo.R;

/**
 * Created by lixian on 2017/7/27.
 */

public class TestInputInfo extends AppCompatActivity {

    String all ="adb shell input text \"Lx\" "+"\n"+"adb shell am start -n com.zongbutech.lxleanlingdongdemo/.InPutInfoAndOutofInfo.TestInputInfo -d \"Lx\"";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_input_info);

        TextView mTextView0 = (TextView) findViewById(R.id.mTextView0);
        mTextView0.setText(all);

        TextView mTextView = (TextView) findViewById(R.id.mTextView);
        String result = getIntent().getDataString();
        try {
            mTextView.setText(new String(result.getBytes(),"UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

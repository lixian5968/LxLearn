package com.zongbutech.lxleanlingdongdemo.InPutInfoAndOutofInfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.zongbutech.lxleanlingdongdemo.R;

/**
 * Created by lixian on 2017/7/27.
 */

public class TestOutPutInfo extends AppCompatActivity {
    public static TestOutPutInfo instance;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_out_put_info);
        mEditText = (EditText) findViewById(R.id.mEditText);
        instance = this;

    }

    public String getEditString() {
        if (mEditText != null && mEditText.getText() != null && mEditText.getText().length() > 0) {
            return mEditText.getText().toString();
        }
        return "";
    }
}

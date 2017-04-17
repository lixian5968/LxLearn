package com.zongbutech.lxleanlingdongdemo.PorterBuffDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.zongbutech.lxleanlingdongdemo.PorterBuffDemo.View.PorterView;
import com.zongbutech.lxleanlingdongdemo.R;

public class PorterActivity extends AppCompatActivity {
    private static final String[] sLabels = {
            "Clear", "Src", "Dst", "SrcOver",
            "DstOver", "SrcIn", "DstIn", "SrcOut",
            "DstOut", "SrcATop", "DstATop", "Xor",
            "Darken", "Lighten", "Multiply", "Screen"
    };
    int Type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porter);
        Type = getIntent().getIntExtra("Type",0);
        GridView mGridView = (GridView) findViewById(R.id.mPorterGridView);
        mGridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return sLabels.length;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = LayoutInflater.from(PorterActivity.this).inflate(R.layout.activity_porter_oiem, null);
                PorterView mPorterView = (PorterView) v.findViewById(R.id.mPorterView);
                mPorterView.setFrist(Type);

                mPorterView.setAngle(position);
                return v;
            }
        });

    }
}

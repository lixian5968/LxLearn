package com.zongbutech.lxleanlingdongdemo.PorterBuffDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.zongbutech.lxleanlingdongdemo.PorterBuffDemo.View.LXStartView;
import com.zongbutech.lxleanlingdongdemo.R;

public class LxStartActivity extends AppCompatActivity {

    int count =100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lx_start);
        GridView mGridView = (GridView) findViewById(R.id.mLXStartGridView);
        mGridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return count;
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
                View v = LayoutInflater.from(LxStartActivity.this).inflate(R.layout.activity_lx_start_item, null);
                LXStartView mLXStartView = (LXStartView) v.findViewById(R.id.mLXStartView);

                if(position>50){
                    mLXStartView.setCount(count-position);
                    mLXStartView.setType(1);
                }else{
                    mLXStartView.setCount(position);
                    mLXStartView.setType(0);
                }

                return v;
            }
        });
    }
}

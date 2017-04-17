package com.zongbutech.lxleanlingdongdemo.PorterBuffDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.zongbutech.lxleanlingdongdemo.PorterBuffDemo.View.LXPointStartView;
import com.zongbutech.lxleanlingdongdemo.R;

import static com.zongbutech.lxleanlingdongdemo.R.id.mLXStartView;

public class PointStartActivity extends AppCompatActivity {
    int count =100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_start);
        GridView mGridView = (GridView) findViewById(R.id.mPointStartGridView);
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
                View v = LayoutInflater.from(PointStartActivity.this).inflate(R.layout.activity_lx_point_start_item, null);
                LXPointStartView mLXPointStartView = (LXPointStartView) v.findViewById(R.id.mLXPointStartView);

                if(position>50){
                    mLXPointStartView.setCount(count-position);
                    mLXPointStartView.setType(1);
                }else{
                    mLXPointStartView.setCount(position);
                    mLXPointStartView.setType(0);
                }

                return v;
            }
        });
    }
}

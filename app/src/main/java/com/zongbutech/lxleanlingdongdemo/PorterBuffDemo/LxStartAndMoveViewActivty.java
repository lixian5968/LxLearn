package com.zongbutech.lxleanlingdongdemo.PorterBuffDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.zongbutech.lxleanlingdongdemo.PorterBuffDemo.View.StartAndMovieView;
import com.zongbutech.lxleanlingdongdemo.R;

public class LxStartAndMoveViewActivty extends AppCompatActivity {
    StartAndMovieView mStartAndMovieView;
    KenBurnsView mKenBurnsView;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lx_start_and_move_view_activty);
        mStartAndMovieView = (StartAndMovieView) findViewById(R.id.mStartAndMovieView);
        mKenBurnsView = (KenBurnsView) findViewById(R.id.mKenBurnsView);
        mEditText = (EditText) findViewById(R.id.mEditText);
    }

    public void Add(View v){
        mStartAndMovieView.setCount(Integer.parseInt(mEditText.getText().toString())+1);
        mEditText.setText(Integer.parseInt(mEditText.getText().toString())+1+"");
    }
    public void Sub(View v){
        mStartAndMovieView.setCount(Integer.parseInt(mEditText.getText().toString())-1);
        mEditText.setText(Integer.parseInt(mEditText.getText().toString())-1+"");
    }


    public void StartNoPoint(View v){
        mStartAndMovieView.setType(1);
    }
    public void StartPoint(View v){
        mStartAndMovieView.setType(2);
    }
}

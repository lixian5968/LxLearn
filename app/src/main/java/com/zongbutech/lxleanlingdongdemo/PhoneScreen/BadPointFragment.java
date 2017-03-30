package com.zongbutech.lxleanlingdongdemo.PhoneScreen;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zongbutech.lxleanlingdongdemo.Base.BaseFragment;
import com.zongbutech.lxleanlingdongdemo.R;

/**
 * Created by lixian on 2017/3/27.
 */

public class BadPointFragment extends BaseFragment {
    int  count;
    int[] Colors;

    ImageView mImageView;
    @Override
    public View getResourcesView(LayoutInflater inflater, ViewGroup container) {
        Colors = new int[]{Color.BLUE,Color.RED,Color.GREEN,Color.GRAY,Color.WHITE,Color.BLACK};
        count = 0;
        mImageView = new ImageView(ct);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                                                                                                                                                                           mImageView.setLayoutParams(params);
        mImageView.setBackgroundColor(Colors[count]);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count>Colors.length-1 ){
                    if(count == Colors.length){
                        mImageView.setBackground(ct.getResources().getDrawable(R.drawable.color_bg));
                    }else{
                        mToast("检测结束");
                        getActivity().finish();
                    }
                }else{
                    mImageView.setBackgroundColor(Colors[count]);
                }

            }
        });

        return mImageView;
    }

    @Override
    public void afterOncreate(Bundle savedInstanceState) {

    }


    private static BadPointFragment fragment;
    public static BadPointFragment newInstance() {
        fragment = new BadPointFragment();
        return fragment;
    }
}

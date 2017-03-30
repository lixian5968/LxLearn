package com.zongbutech.lxleanlingdongdemo.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by lixian on 2017/3/27.
 */

public abstract class BaseFragment extends Fragment {

    protected View view;
    public Context ct;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ct = getActivity();
        //防止 多个数据 调用同一个界面
        if (view == null) {
            view = getResourcesView(inflater, container);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        afterOncreate(savedInstanceState);
    }

    public abstract View getResourcesView(LayoutInflater inflater, ViewGroup container);

    public abstract void afterOncreate(Bundle savedInstanceState);






    public void mToast(String s) {
        try {
            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T extends View> T $(int id) {
        return (T) getActivity().findViewById(id);
    }
}

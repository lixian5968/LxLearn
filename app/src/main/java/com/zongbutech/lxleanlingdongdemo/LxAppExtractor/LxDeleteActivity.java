package com.zongbutech.lxleanlingdongdemo.LxAppExtractor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zongbutech.lxleanlingdongdemo.R;
import com.zongbutech.lxleanlingdongdemo.Utils.RootUtils.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LxDeleteActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Context ct;
    List<ApplicationInfo> packages;
    List<AppInfo> mAppInfos;

    EditText et;

    public void Search(View v) {
        et = new EditText(LxDeleteActivity.this);
        new AlertDialog.Builder(LxDeleteActivity.this).setTitle("搜索")
                .setView(et).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (et.getText().length() == 0) {
                    mAppInfos.clear();
                    for (ApplicationInfo info : packages) {
                        mAppInfos.add(new AppInfo(info.packageName, info.loadIcon(ct.getPackageManager()), info));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    mAppInfos.clear();
                    for (ApplicationInfo info : packages) {
                        if (info.packageName.contains(et.getText().toString())) {
                            mAppInfos.add(new AppInfo(info.packageName, info.loadIcon(ct.getPackageManager()), info));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }).show();
    }

    LxAPPInfoAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lx_delete);

        Root mRoot = new Root();
        if (mRoot.isDeviceRooted()) {
            Toast.makeText(LxDeleteActivity.this, "装备已经刷机", Toast.LENGTH_SHORT).show();
        }

        ct = this;
        ListView mListView = (ListView) findViewById(R.id.mListView);
        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.HomeSwiperefresh);
        mSwipeRefreshWidget.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshWidget.setOnRefreshListener(this);


        mAppInfos = new ArrayList<>();
        adapter = new LxAPPInfoAdapter(ct, mAppInfos);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                mAppInfos.get(position).select = !mAppInfos.get(position).select;
                adapter.notifyDataSetChanged();

//                String message = "文件包名：" + mAppInfos.get(position).info.packageName;
//                new AlertDialog.Builder(ct).setTitle("是否删除APP？").setMessage(message)
//                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                uninstallApk(LxDeleteActivity.this, mAppInfos.get(position).info.packageName);
//                                dialog.dismiss();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).show();


            }
        });


//        onRefresh();
    }


    public void uninstallApk(Context context, String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                packages = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
                mAppInfos.clear();
                for (ApplicationInfo info : packages) {

                    boolean flag = false;
                    //手机预安装程序
                    if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                        // Updated system app
                        flag = true;
                    }
                    //手机内置程序
                    else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        // Non-system app
                        flag = true;
                    }
                    if (flag) {
                        mAppInfos.add(new AppInfo(info.packageName, info.loadIcon(ct.getPackageManager()), info));
                    } else {
                        Log.e("", "");
                    }


                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshWidget.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    public void Log(View v) {
        String All = "";
        for (AppInfo mAppInfo : mAppInfos) {
            if(mAppInfo.select){
                All += "runtime.exec(\"adb.exe uninstall "+mAppInfo.name +"\");";
            }
        }
        Log.e("UninstallAPP", All);

    }

    public class AppInfo implements Serializable {
        public String name;
        public Drawable icon;
        public ApplicationInfo info;

        public boolean select;

        public AppInfo(String name, Drawable icon, ApplicationInfo info) {
            this.name = name;
            this.icon = icon;
            this.info = info;
        }
    }

    public class LxAPPInfoAdapter extends BaseAdapter {
        Context ct;
        List<AppInfo> mAppInfos;

        public LxAPPInfoAdapter(Context ct, List<AppInfo> mAppInfos) {
            this.ct = ct;
            this.mAppInfos = mAppInfos;
        }

        @Override
        public int getCount() {
            return mAppInfos.size();
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

            View v = LayoutInflater.from(ct).inflate(R.layout.adapter_extra_item, null);
            ImageView mImageView = (ImageView) v.findViewById(R.id.mImageView);
            TextView mTextView = (TextView) v.findViewById(R.id.mTextView);
            ImageView CheckImage = (ImageView) v.findViewById(R.id.CheckImage);
            AppInfo mAppInfo = mAppInfos.get(position);
            mTextView.setText(mAppInfo.name);
            mImageView.setImageDrawable(mAppInfo.icon);
            if (mAppInfo.select) {
                CheckImage.setVisibility(View.VISIBLE);
            } else {
                CheckImage.setVisibility(View.GONE);
            }
            return v;
        }
    }
}

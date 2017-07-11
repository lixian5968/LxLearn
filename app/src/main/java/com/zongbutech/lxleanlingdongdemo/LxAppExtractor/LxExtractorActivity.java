package com.zongbutech.lxleanlingdongdemo.LxAppExtractor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class LxExtractorActivity extends AppCompatActivity {

    Context ct;
    List<ApplicationInfo> packages;
    List<AppInfo> mAppInfos;

    EditText et;

    public void Search(View v) {
        et = new EditText(LxExtractorActivity.this);
        new AlertDialog.Builder(LxExtractorActivity.this).setTitle("搜索")
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lx_extractor);

        Root mRoot = new Root();
        if (mRoot.isDeviceRooted()) {
            Toast.makeText(LxExtractorActivity.this, "装备已经刷机", Toast.LENGTH_SHORT).show();
        }

        ct = this;
        ListView mListView = (ListView) findViewById(R.id.mListView);
        packages = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        mAppInfos = new ArrayList<>();
        for (ApplicationInfo info : packages) {
            mAppInfos.add(new AppInfo(info.packageName, info.loadIcon(ct.getPackageManager()), info));
        }
        adapter = new LxAPPInfoAdapter(ct, mAppInfos);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String message = "文件位置：" + mAppInfos.get(position).info.sourceDir;
                new AlertDialog.Builder(ct).setTitle("是否导出文件？").setMessage(message)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                File src = new File(mAppInfos.get(position).info.sourceDir);
                                File OutAll = new File(Environment.getExternalStorageDirectory(), "LxApks");
                                if (!OutAll.exists()) {
                                    OutAll.mkdirs();
                                }
                                File OutAllAPk = new File(OutAll.getPath(), mAppInfos.get(position).name + ".apk");
                                try {
                                    FileInputStream inputStream = new FileInputStream(src);
                                    FileOutputStream outputStream = new FileOutputStream(OutAllAPk);
                                    FileChannel inChan = inputStream.getChannel();
                                    FileChannel outChan = outputStream.getChannel();
                                    inChan.transferTo(0, inChan.size(), outChan);
                                    inputStream.close();
                                    outputStream.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                ToMainFile(OutAll);
                                Toast.makeText(ct, "复制成功", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    public void ToMainFile(File mFile) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(Uri.fromFile(mFile), "*/*");
        //设置文件类型
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // 添加Category属性
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "没有正确打开文件管理器", Toast.LENGTH_SHORT).show();
        }
    }


    public class AppInfo implements Serializable {
        public String name;
        public Drawable icon;
        public ApplicationInfo info;

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
            AppInfo mAppInfo = mAppInfos.get(position);
            mTextView.setText(mAppInfo.name);
            mImageView.setImageDrawable(mAppInfo.icon);
            return v;
        }
    }
}

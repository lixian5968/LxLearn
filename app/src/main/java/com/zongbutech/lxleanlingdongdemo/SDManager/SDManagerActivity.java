package com.zongbutech.lxleanlingdongdemo.SDManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zongbutech.lxleanlingdongdemo.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static android.os.Environment.getExternalStorageDirectory;
import static com.zongbutech.lxleanlingdongdemo.SDManager.Utils.MIME_MapTable;


public class SDManagerActivity extends AppCompatActivity {

    TextView Title;
    ListView mListView;

    //  if (o1.isDirectory() && o2.isFile())  文件夹 或 文件
    List<FileInfo> mFileInfos;
    SDManagerAdapter adapter;
    Context ct;
    File mFile;
    File mCopyFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdmanager);
        Title = $(R.id.Title);
        mListView = $(R.id.mListView);
        ct = this;

        mFileInfos = new ArrayList<>();
        adapter = new SDManagerAdapter(SDManagerActivity.this, mFileInfos);
        mListView.setAdapter(adapter);


        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            updateFiles(getExternalStorageDirectory());
        } else {
            mToast("没有内存卡");
        }


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FileInfo mFileInfo = mFileInfos.get(position);
                File mFile = new File(mFileInfo.path);
                if (mFile.exists()) {
                    if (mFile.isFile()) {
                        openFile(mFileInfo.path);
                    } else if (mFile.isDirectory()) {
                        updateFiles(mFile);
                    }
                }
            }
        });


        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FileInfo mFileInfo = mFileInfos.get(position);
                if (mFileInfo.name.equals("返回上一级")) {

                } else {
                    mFile = new File(mFileInfo.path);
                    new AlertDialog.Builder(ct)
                            .setTitle("请点击想要的操作")
                            .setItems(
                                    new String[]{"重命名", "复制", "剪切", "删除", "属性","蓝牙传送"},
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case 0:
                                                    updateFielName(mFile);
                                                    break;
                                                case 1:
                                                    mCopyFile = new File(mFile.getPath());
                                                    mToast("文件已经复制");
                                                    break;

                                                case 3:
                                                    if (mFile != null && mFile.exists()) {
                                                        deleteFile(mFile);
                                                    }
                                                    break;
                                                case 4:
                                                    setFileInfo(mFile);
                                                    break;
                                                case 5:
                                                   if(mFile.isFile()){
                                                       Uri uri = Uri.fromFile(mFile);
                                                       //打开系统蓝牙模块并发送文件
                                                       Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                                       sharingIntent.setType("*/*");
                                                       sharingIntent.setPackage("com.android.bluetooth");
                                                       sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                                       startActivity(Intent.createChooser(sharingIntent, "Share"));
                                                   }else{
                                                       mToast("文件夹不允许传送");
                                                   }
                                                    break;
                                            }
                                        }
                                    }
                            ).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }
                return true;
            }
        });
    }


    //更改文件名
    EditText et;

    private void updateFielName(final File mFile) {
        et = new EditText(ct);
        et.setText(mFile.getName());
        new AlertDialog.Builder(ct).setTitle("更改用户名").setView(et)
                .setPositiveButton("修改用户名", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (et.getText().length() > 0) {
                            File mNewFile = new File(mFile.getParentFile().getPath() + "/" + et.getText());
                            if (mFile.renameTo(mNewFile)) {
                                mToast("更改用户名成功");
                                updateFiles(mFile.getParentFile());
                            } else {
                                mToast("更改用户名失败");
                            }
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        }).show();
    }

    //显示文件属性
    private void setFileInfo(File mFile) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = "Size：" + mFile.length() + "B\n," +
                "Name:" + mFile.getName() + ",\n" +
                "Path:" + mFile.getPath() + ",\n" +
                "LastUpdate:" + formatter.format(new Date(mFile.lastModified()));
        new AlertDialog.Builder(ct).setTitle("文件属性").setMessage(s).setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    //删除文件或文件夹
    int DeleteCount =0;
    public void deleteFile(File file) {
        DeleteCount++;
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
            }
            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(childFiles[i]);
            }
            file.delete();
        }
        DeleteCount--;
        if(DeleteCount==0){
            updateFiles(new File(Title.getText().toString()));
        }
    }


    //辅助文件
    public void copy(View v) {
        if (mCopyFile != null && mCopyFile.exists()) {
            if (mCopyFile.isFile()) {
                final File newToFile = new File(Title.getText() + "/" + mCopyFile.getName());
                if (newToFile.exists()) {
                    new AlertDialog.Builder(ct).setTitle("文件已存在,是否覆盖?").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Utils.copyFile(mCopyFile.getPath(), newToFile.getPath());
                            updateFiles(new File(Title.getText().toString()));
                        }
                    }).show();
                } else {
                    Utils.copyFile(mCopyFile.getPath(), newToFile.getPath());
                    updateFiles(new File(Title.getText().toString()));
                }
            } else if (mCopyFile.isDirectory()) {
                if (mCopyFile != null && mCopyFile.exists()) {
                    File newToFile = new File(Title.getText() + "/" + mCopyFile.getName());
                    copyFolder(mCopyFile.getPath(), newToFile.getPath());
                }
            }
        } else {
            mToast("没有复制的文件");
        }


    }
    //传输文件

    //复制文件夹
    int copyFolderCount = 0;
    public void copyFolder(String oldPath, String newPath) {
        copyFolderCount++;
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            Log.e("APKMainActivity", "复制整个文件夹内容操作出错");
            e.printStackTrace();
        }
        copyFolderCount--;
        if (copyFolderCount == 0) {
            updateFiles(new File(Title.getText().toString()));
            Toast.makeText(ct, "全部复制成功", Toast.LENGTH_SHORT).show();
        }
    }


    public void updateFiles(File file) {
        mFileInfos.clear();
        Title.setText(file.getPath());
        if (file.exists() && file.canRead()) {
            if (!file.getPath().equals(getExternalStorageDirectory().getPath())) {
                mFileInfos.add(new FileInfo("返回上一级", file.getParentFile().getPath()));
            }

            File[] mFiles = file.listFiles();
            List<File> mFileList = Arrays.asList(mFiles);
            //小的在上面 -1 表示 01 比 02小
            Collections.sort(mFileList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    if (o1.isFile() && o2.isDirectory()) {
                        return -1;
                    } else if (o1.isDirectory() && o2.isFile()) {
                        return 1;
                    } else {
                        return o1.getName().compareTo(o2.getName());
                    }
                }
            });
            if (mFiles != null && mFiles.length > 0) {
                for (File mFile : mFiles) {
                    if (mFile.exists()) {
                        mFileInfos.add(new FileInfo(mFile.getName(), mFile.getPath()));
                    }
                }
            }

        } else {
            mToast("不能读取文件");
        }

        adapter.notifyDataSetChanged();
    }

    public void mToast(String s) {
        Toast.makeText(SDManagerActivity.this, s, Toast.LENGTH_SHORT).show();
    }


    public <T extends View> T $(int id) {
        return (T) super.findViewById(id);
    }


    public class FileInfo implements Serializable {
        public String name;
        public String path;

        public FileInfo(String name, String path) {
            this.name = name;
            this.path = path;
        }
    }

    public class SDManagerAdapter extends BaseAdapter {

        public Context ct;
        List<FileInfo> mFileInfos;

        public SDManagerAdapter(Context ct, List<FileInfo> mFileInfos) {
            this.ct = ct;
            this.mFileInfos = mFileInfos;
        }

        @Override
        public int getCount() {
            return mFileInfos.size();
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
            TextView tv = new TextView(ct);
            tv.setHeight(100);
            tv.setGravity(Gravity.CENTER);
            FileInfo mFileInfo = mFileInfos.get(position);
            File mFile = new File(mFileInfo.path);
            if (mFile.exists()) {
                if (mFile.isDirectory()) {
                    tv.setText("文件夹：" + mFileInfos.get(position).name);
                } else if (mFile.isFile()) {
                    String[] mStrings = mFileInfos.get(position).name.split("\\.");
                    if (mStrings != null && mStrings.length > 1 && mStrings[mStrings.length - 1] != null && mStrings[mStrings.length - 1].length() > 0) {
                        tv.setText(mStrings[mStrings.length - 1] + ": " + mFileInfos.get(position).name);
                    } else {
                        tv.setText("文件：" + mFileInfos.get(position).name);
                    }
                }
            } else {
                tv.setText(mFileInfos.get(position).name);
            }
            return tv;
        }
    }


    //打开文件
    private void openFile(String filepath) {
        File file = new File(filepath);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = getMIMEType(file);
        //设置intent的data和Type属性。
        if (type.equals("*/*")) {
            mToast("文件不能打开");
        } else {
            intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
            //跳转
            startActivity(intent);
        }

    }

    //获取文件mimetype
    private String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
    /* 获取文件的后缀名*/
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }


}

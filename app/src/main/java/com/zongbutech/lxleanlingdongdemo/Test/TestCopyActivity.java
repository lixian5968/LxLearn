package com.zongbutech.lxleanlingdongdemo.Test;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zongbutech.lxleanlingdongdemo.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class TestCopyActivity extends AppCompatActivity {
    Context ct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_copy);
        ct = this;
    }


    public void CopyFolderBt(View v) {
        File StartFile = new File(Environment.getExternalStorageDirectory(), "alxcopydemo");
        if (StartFile.exists() && StartFile.isDirectory()) {
            File EndFile = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "Successseries", "alxcopydemo");
            if(EndFile.getPath().contains(StartFile.getPath())){
                Toast.makeText(ct,"不能复制",Toast.LENGTH_SHORT).show();
            }else{
//                copyFolder(StartFile.getPath(), EndFile.getPath());

                if(!EndFile.exists()){
                    EndFile.mkdirs();
                }
                copyFile(StartFile.getPath(), EndFile.getPath());

            }
        }
    }


    //复制文件夹
    int i=0;
    public void copyFolder(String oldPath, String newPath) {
        i++;
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
        i--;
        if(i ==0){
            Toast.makeText(ct,"全部复制成功",Toast.LENGTH_SHORT).show();
        }
    }


    //剪切复制文件
    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            Log.e("APKMainActivity", "复制单个文件操作出错");
            e.printStackTrace();
        }
    }

}

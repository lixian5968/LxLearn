package com.zongbutech.lxleanlingdongdemo.Wallpaper.DateAnd;

import android.graphics.Bitmap;

import com.zongbutech.lxleanlingdongdemo.GsonDemo.BaseBean;

import java.io.Serializable;

/**
 * Created by lixian on 2017/2/17.
 */

public class DownLoadBaseBean extends BaseBean implements Serializable {
    public String file;
    public String title;
    public Bitmap mBitmap;
    public String size;

    public DownLoadBaseBean(String file, String title) {
        this.file = file;
        this.title = title;
    }
    public DownLoadBaseBean(String file, String title, Bitmap mBitmap) {
        this.file = file;
        this.title = title;
        this.mBitmap = mBitmap;
    }

    public DownLoadBaseBean(String file, String title, String size) {
        this.file = file;
        this.title = title;
        this.size = size;
    }

    public DownLoadBaseBean(String file, String title, Bitmap mBitmap, String size) {
        this.file = file;
        this.title = title;
        this.mBitmap = mBitmap;
        this.size = size;
    }
}

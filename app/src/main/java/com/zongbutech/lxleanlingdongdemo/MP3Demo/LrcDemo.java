package com.zongbutech.lxleanlingdongdemo.MP3Demo;

import java.io.Serializable;

/**
 * Created by lixian on 2017/3/14.
 */

public class LrcDemo implements Serializable {
    public String strTime;
    public long time;
    public String content;

    public LrcDemo(String strTime, long time, String content) {
        this.strTime = strTime;
        this.time = time;
        this.content = content;
    }
}

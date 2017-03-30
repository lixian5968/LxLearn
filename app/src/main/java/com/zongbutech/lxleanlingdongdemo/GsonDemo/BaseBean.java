package com.zongbutech.lxleanlingdongdemo.GsonDemo;

import java.io.Serializable;

/**
 * Created by lixian on 2017/3/24.
 */

public class BaseBean implements Serializable{

    private String Lx;

    public String getLx() {
        return Lx;
    }

    public void setLx(String lx) {
        Lx = lx;
    }
}

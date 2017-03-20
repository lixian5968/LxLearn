package com.zongbutech.lxleanlingdongdemo.PhoneManager;

import android.app.ActivityManager;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static android.text.format.Formatter.formatFileSize;

/**
 * Created by lixian on 2017/3/13.
 */

public class ManagerUtils {
    //        //最大为1GB
//    String size2 = Formatter.formatFileSize(ct,1024*1024*1024);
    //\\s - 匹配单个空格字符
//            \\s+ - 匹配一个或多个空格字符的序列。


    //获取系统可用的内存
    public static String getTotalMemory(Context ct) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }
            initial_memory = Long.valueOf(arrayOfString[1]) * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return Formatter.formatFileSize(ct, initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }


    //获取可用的内存
    public static String getAvailMemory(Context ct) {// 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) ct.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(ct, mi.availMem);// 将获取的内存大小规格化
    }


    /**
     * 根据路径获取内存状态
     *
     * @param path
     * @return
     */
    public static String getMemoryInfo(Context ct, File path) {
        // 获得一个磁盘状态对象
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();   // 获得一个扇区的大小
        long totalBlocks = stat.getBlockCount();    // 获得扇区的总数
        long availableBlocks = stat.getAvailableBlocks();   // 获得可用的扇区数量
        // 总空间
        String totalMemory = formatFileSize(ct, totalBlocks * blockSize);
        // 可用空间
        String availableMemory = formatFileSize(ct, availableBlocks * blockSize);
        return "总空间: " + totalMemory + ",可用空间: " + availableMemory;
    }


    //获取CPU名字
    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+");
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //获取CPU名字
    public static String getCpuName2() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader bufr = new BufferedReader(fr);
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = bufr.readLine()) != null) {
                buffer.append(line);
            }
            bufr.close();
            String[] mStrings = buffer.toString().split(":\\s+");
            return mStrings[mStrings.length - 1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getNumberOfCPUCores() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            return 1;
        }
        int cores;
        try {
            File[] mFiles = new File("/sys/devices/system/cpu/").listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.getName().contains("cpu")) {
                        if (pathname.getName().charAt(3) < '0' || pathname.getName().charAt(3) > '9') {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
            });
            cores = mFiles.length;
        } catch (Exception e) {
            cores = 0;
        }
        return cores;
    }

    // 实时获取CPU当前频率（单位KHZ）
    public static String getCurCpuFreq() {
        String result = "0";
        try {
            FileReader fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result + "/KHZ";
    }


    /**
     * 判断手机是否ROOT
     */
    public static boolean isRoot() {
        boolean root = false;
        try {
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())) {
                root = false;
            } else {
                root = true;
            }
        } catch (Exception e) {
        }
        return root;
    }


    //获取IP地址
    public static String getWIFILocalIpAdress(Context mContext) {
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = formatIpAddress(ipAddress);
        return ip;
    }
    public static String formatIpAddress(int ipAdress) {
        return (ipAdress & 0xFF) + "." +
                ((ipAdress >> 8) & 0xFF) + "." +
                ((ipAdress >> 16) & 0xFF) + "." +
                (ipAdress >> 24 & 0xFF);
    }
}

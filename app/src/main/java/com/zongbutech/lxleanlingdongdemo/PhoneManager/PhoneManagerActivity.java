

package com.zongbutech.lxleanlingdongdemo.PhoneManager;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zongbutech.lxleanlingdongdemo.R;

import java.util.ArrayList;
import java.util.List;

import static com.zongbutech.lxleanlingdongdemo.PhoneManager.ManagerUtils.getCpuName2;
import static com.zongbutech.lxleanlingdongdemo.PhoneManager.ManagerUtils.getCurCpuFreq;
import static com.zongbutech.lxleanlingdongdemo.PhoneManager.ManagerUtils.getMemoryInfo;
import static com.zongbutech.lxleanlingdongdemo.PhoneManager.ManagerUtils.getNumberOfCPUCores;
import static com.zongbutech.lxleanlingdongdemo.PhoneManager.ManagerUtils.getWIFILocalIpAdress;
import static com.zongbutech.lxleanlingdongdemo.PhoneManager.ManagerUtils.isRoot;

public class PhoneManagerActivity extends AppCompatActivity {

    Context ct;
    UserInfoAdapter adapter;
    TextView PhonePower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_manager);
        ListView mListView = $(R.id.mListView);
        PhonePower = $(R.id.PhonePower);

        ct = PhoneManagerActivity.this;


        List<String> mPhoneInfos = new ArrayList<>();
        //型号
        mPhoneInfos.add("型号：" + android.os.Build.MODEL);
        //品牌
        mPhoneInfos.add("品牌：" + android.os.Build.BRAND);
        //版本
        mPhoneInfos.add("Android版本：" + android.os.Build.VERSION.RELEASE);
        //手机内存信息
        mPhoneInfos.add("总内存：" + ManagerUtils.getTotalMemory(ct) + ",可用内存:" + ManagerUtils.getAvailMemory(ct));
        // 获得存储卡状态
        mPhoneInfos.add(getMemoryInfo(ct, Environment.getExternalStorageDirectory()));
        //CPU 名称
        mPhoneInfos.add("CPU:" + getCpuName2());
        //核心数目
        mPhoneInfos.add("核心数:" + getNumberOfCPUCores());
        //频率
        mPhoneInfos.add("频率:" + getCurCpuFreq());
        //Root
        mPhoneInfos.add("ROOT:" + isRoot());
        //IMEI
        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        mPhoneInfos.add("IMEI:" + tm.getDeviceId());
        //IMEI
        mPhoneInfos.add("IP:" + getWIFILocalIpAdress(ct));


        adapter = new UserInfoAdapter(PhoneManagerActivity.this, mPhoneInfos);
        mListView.setAdapter(adapter);

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = ct.registerReceiver(null, ifilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;


        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;


        //当前剩余电量
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//电量最大值
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
//电量百分比
        float batteryPct = level / (float) scale;


        PhonePower.setText("充电：" + isCharging+",level:"+level+",scale:"+scale+",batteryPct:"+batteryPct);
    }


    public class UserInfoAdapter extends BaseAdapter {
        Context ct;
        List<String> mPhoneInfos;

        public UserInfoAdapter(Context ct, List<String> mPhoneInfos) {
            this.ct = ct;
            this.mPhoneInfos = mPhoneInfos;
        }

        @Override
        public int getCount() {
            return mPhoneInfos.size();
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
            tv.setText(mPhoneInfos.get(position));
            return tv;
        }
    }


    public <T extends View> T $(int id) {
        return (T) findViewById(id);
    }


}

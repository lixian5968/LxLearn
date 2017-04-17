package com.zongbutech.lxleanlingdongdemo.GPSChangeDemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zongbutech.lxleanlingdongdemo.R;

public class GPSChangeAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpschange_acitivity);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                location.getProvider().equals(LocationManager.GPS_PROVIDER);
                Log.e("", "");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });


        locationManager.addGpsStatusListener(new GpsStatus.Listener() {
            @Override
            public void onGpsStatusChanged(int event) {
                switch (event) {//第一次定位
                    case GpsStatus.GPS_EVENT_FIRST_FIX:
                        Log.e("","");
                        break;
                    //卫星状态改变
                    case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                        Log.e("","");
                        break;
                    //定位启动
                    case GpsStatus.GPS_EVENT_STARTED:
                        Log.e("","");
                        break;
                    //定位结束
                    case GpsStatus.GPS_EVENT_STOPPED:
                        Log.e("","");
                        break;
                }
            }
        });

    }
}

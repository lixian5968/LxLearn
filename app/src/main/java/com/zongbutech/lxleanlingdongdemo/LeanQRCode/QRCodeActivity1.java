package com.zongbutech.lxleanlingdongdemo.LeanQRCode;

import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ToggleButton;

import com.zongbutech.lxleanlingdongdemo.R;

public class QRCodeActivity1 extends AppCompatActivity implements View.OnClickListener {
    private ToggleButton toggleButton;
    private Camera m_Camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode1);
        toggleButton = (ToggleButton) this.findViewById(R.id.toggleButton1);
        toggleButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ToggleButton tb = (ToggleButton) v;
        if (!tb.isChecked()) {
            PackageManager pm = this.getPackageManager();
            FeatureInfo[] features = pm.getSystemAvailableFeatures();
            for (FeatureInfo f : features) {
                if (PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) {
                    if (null == m_Camera) {
                        m_Camera = Camera.open();
                    }
                    Camera.Parameters parameters = m_Camera.getParameters();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    m_Camera.setParameters(parameters);
                    m_Camera.startPreview();
                    toggleButton.setBackgroundColor(0x30ffffff);
                }
            }
        } else {
            if (m_Camera != null) {
                m_Camera.stopPreview();
                m_Camera.release();
                m_Camera = null;
            }
            toggleButton.setBackgroundColor(0xffffffff);
        }
    }
}
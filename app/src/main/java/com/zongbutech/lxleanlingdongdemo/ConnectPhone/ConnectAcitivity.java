package com.zongbutech.lxleanlingdongdemo.ConnectPhone;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;
import com.zongbutech.lxleanlingdongdemo.R;

import java.util.List;

public class ConnectAcitivity extends AppCompatActivity {
    String message = "'";
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        mTextView = (TextView) findViewById(R.id.mTextView);
        List<ApplicationInfo> packages = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo info : packages) {
            if (info.packageName.contains("com.zongbutech.lxleanlingdongdemo")) {
                message += ("文件位置：" + info.sourceDir);
            }
        }
        mTextView.setText(message);


    }

    public void GotoShow(View v){
        startActivity(new Intent(ConnectAcitivity.this,GotoShowActivity.class));
    }



    public void startServer(View v) {
        AsyncHttpServer server = new AsyncHttpServer();
        server.get("/lx", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                response.send("Hello!!!");
            }
        });
        server.listen(5000);

    }

}

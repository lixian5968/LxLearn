package com.zongbutech.lxleanlingdongdemo.ConnectPhone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zongbutech.lxleanlingdongdemo.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GotoShowActivity extends AppCompatActivity {
    EditText mEditText;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goto_show);
        mEditText = (EditText) findViewById(R.id.mEditText);
        mImageView = (ImageView) findViewById(R.id.mImageView);


    }

    String path = "http://localhost:53516/lx.jpg";

    public void start(View v) {
        if (mEditText.length() > 0) {
            path = mEditText.getText().toString();
        }
        gitImage();
    }

    private void gitImage() {
        Glide.with(GotoShowActivity.this).load(path).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(mImageView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gitImage();
            }
        }, 1000);

//            @Override
//            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                mImageView.setImageDrawable(resource);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        gitImage();
//                    }
//                },100);
//            }
//        });
    }


    //加载图片
    public Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

}

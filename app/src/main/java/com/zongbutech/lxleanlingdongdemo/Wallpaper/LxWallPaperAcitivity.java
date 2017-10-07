package com.zongbutech.lxleanlingdongdemo.Wallpaper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zongbutech.lxleanlingdongdemo.Base.BaseActivity;
import com.zongbutech.lxleanlingdongdemo.R;

public class LxWallPaperAcitivity extends BaseActivity {
    TextView PlayUrl;

    ImageView image1, image2, image3;
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_paper);
        PlayUrl = $(R.id.PlayUrl);

        image1 = $(R.id.image1);
        image2 = $(R.id.image2);
        image3 = $(R.id.image3);
        mTextView = $(R.id.mTextView);

        CheckBox mCheckBox1 = $(R.id.mCheckBox1);
        mCheckBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    VideoLiveWallpaper.ShowVolume = true;
                } else {
                    VideoLiveWallpaper.ShowVolume = false;
                }
            }
        });
    }

    public void GoTo(View v) {
        final Intent pickWallpaper = new Intent(Intent.ACTION_SET_WALLPAPER);
        Intent chooser = Intent.createChooser(pickWallpaper, "选择壁纸");
        startActivity(chooser);
    }

    public void getUrl(View v) {
        Intent intent = new Intent();
        intent.putExtra("getUrl", true);
        intent.setClass(LxWallPaperAcitivity.this, LxGetMp4Activity.class);
        startActivityForResult(intent, 1000);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            String str = data.getExtras().getString("result");
            PlayUrl.setText(str);
            VideoLiveWallpaper.PlayUrl = str;

            Bitmap bt1 =ThumbnailUtils.createVideoThumbnail(str, MediaStore.Images.Thumbnails.MINI_KIND);
            Bitmap bt2 =ThumbnailUtils.createVideoThumbnail(str, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
            Bitmap bt3 =ThumbnailUtils.createVideoThumbnail(str, MediaStore.Images.Thumbnails.MICRO_KIND);

            image1.setImageBitmap(bt1);
            image2.setImageBitmap(bt2);
            image3.setImageBitmap(bt3);

            String all1 ="Width:"+bt1.getWidth()+",Height:"+bt1.getHeight()+",Config:"+bt1.getConfig()+",ByteCount:"+bt1.getByteCount()+"\n";
            String all2 ="Width:"+bt2.getWidth()+",Height:"+bt2.getHeight()+",Config:"+bt2.getConfig()+",ByteCount:"+bt2.getByteCount()+"\n";
            String all3 ="Width:"+bt3.getWidth()+",Height:"+bt3.getHeight()+",Config:"+bt3.getConfig()+",ByteCount"+bt3.getByteCount()+"\n";
            String all = all1+all2+all3;
            mTextView.setText(all);
        }
    }
}

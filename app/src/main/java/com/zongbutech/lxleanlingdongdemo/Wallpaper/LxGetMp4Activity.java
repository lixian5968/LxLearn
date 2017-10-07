package com.zongbutech.lxleanlingdongdemo.Wallpaper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zongbutech.lxleanlingdongdemo.Base.BaseActivity;
import com.zongbutech.lxleanlingdongdemo.R;
import com.zongbutech.lxleanlingdongdemo.Wallpaper.DateAnd.DownLoadBaseBean;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LxGetMp4Activity extends BaseActivity {
    LxFileAdapter mAdapter;
    ListView mListView;
    boolean getUrl = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_paper_select);
        getUrl = getIntent().getBooleanExtra("getUrl", false);
        mListView = (ListView) findViewById(R.id.mListView);
        mAdapter = new LxFileAdapter(LxGetMp4Activity.this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mDownLoadBaseBeans != null && mDownLoadBaseBeans.get(position) != null) {
                    if (getUrl) {
                        Intent intent = new Intent();
                        intent.putExtra("result", mDownLoadBaseBeans.get(position).file);
                        LxGetMp4Activity.this.setResult(RESULT_OK, intent);
                        LxGetMp4Activity.this.finish();
                    }
                }
            }
        });

    }

    List<DownLoadBaseBean> mDownLoadBaseBeans;
    public boolean GetAll = false;

    public void getMp4Info(View v) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            GetAll = false;
            File path = Environment.getExternalStorageDirectory();// 获得SD卡路径
            mDownLoadBaseBeans = new ArrayList<>();
            mAdapter.setmDate(mDownLoadBaseBeans);
            rx.Observable.just(path).subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io()).map(new Func1<File, ArrayList<DownLoadBaseBean>>() {
                @Override
                public ArrayList<DownLoadBaseBean> call(File file) {
                    getFileName(file.toString());  //视频列表
                    return (ArrayList<DownLoadBaseBean>) mDownLoadBaseBeans;
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ArrayList<DownLoadBaseBean>>() {
                @Override
                public void call(ArrayList<DownLoadBaseBean> downLoadBaseBeen) {
                    GetAll = true;
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(LxGetMp4Activity.this, "结束搜索", Toast.LENGTH_LONG).show();
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    mAdapter.setmDate(new ArrayList<DownLoadBaseBean>());
                }
            });
        }
    }

    int count = 0;

    private void getFileName(String url) {
        count++;
        File files = new File(url);
        File[] file = files.listFiles();
        for (File f : file) {
            if (f.isDirectory()) {
                getFileName(f.getAbsolutePath());
            } else {
                String fileName = f.getName();
                if (IsMovieType(fileName)) {
                    //获得视频名,去掉扩展名
                    String name = fileName.substring(0, fileName.lastIndexOf(".")).toString();
                    Log.e("LxMovie", f.toString());
                    mDownLoadBaseBeans.add(new DownLoadBaseBean(f.getPath(), name, getImage(f.getPath()), FormetFileSize(f.length())));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }
        count--;
        if (count == 0) {

        }
    }


    public class LxFileAdapter extends BaseAdapter {
        private List<DownLoadBaseBean> mDatas;
        private Context mContext;


        public ImageView SDCardImage;
        public TextView SDCardTitle;
        public TextView SDCardMovieSize;
        public View SDCardLine;

        public LxFileAdapter(Context context) {
            this.mContext = context;
        }

        public void setmDate(List<DownLoadBaseBean> mDatas) {
            this.mDatas = mDatas;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (mDatas == null) {
                return 0;
            }
            return mDatas.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_home_download_item, null);
            SDCardImage = (ImageView) v.findViewById(R.id.SDCardImage);
            SDCardTitle = (TextView) v.findViewById(R.id.SDCardTitle);
            SDCardMovieSize = (TextView) v.findViewById(R.id.SDCardMovieSize);
            SDCardLine = (View) v.findViewById(R.id.SDCardLine);


            DownLoadBaseBean bean = mDatas.get(position);
            if (bean.file != null && bean.file.length() > 0 && bean.mBitmap != null) {
                SDCardImage.setImageBitmap(bean.mBitmap);
            }
            if (bean.title != null && bean.title.length() > 0) {
                SDCardTitle.setText(bean.title);
            } else {
                SDCardTitle.setText("");
            }
            if (bean.size != null && bean.size.length() > 0) {
                SDCardMovieSize.setText(bean.size);
            } else {
                SDCardMovieSize.setText("");
            }
            if (position == mDatas.size() - 1) {
                SDCardLine.setVisibility(View.INVISIBLE);
            } else {
                SDCardLine.setVisibility(View.VISIBLE);
            }
            return v;
        }


        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }


    public static boolean IsMovieType(String fileName) {
        return fileName.endsWith(".m4v") || fileName.endsWith(".mp4") || fileName.endsWith(".mov") || fileName.endsWith(".avi") || fileName.endsWith(".mpeg");
    }

    public static Bitmap getImage(String SdUrl) {
        try {
            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(SdUrl, MediaStore.Images.Thumbnails.MINI_KIND);
            Bitmap NewBitmap = ThumbnailUtils.extractThumbnail(bitmap, 100, 100);
            return NewBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

}

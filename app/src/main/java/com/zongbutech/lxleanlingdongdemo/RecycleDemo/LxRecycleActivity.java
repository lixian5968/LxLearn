package com.zongbutech.lxleanlingdongdemo.RecycleDemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zongbutech.lxleanlingdongdemo.R;

public class LxRecycleActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lx_recycle);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
//        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager manager = new GridLayoutManager(this, 4);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 4;
                } else if (position == 1) {
                    return 3;
                } else if (position == 2) {
                    return 2;
                } else if (position == 3) {
                    return 2;
                } else if (position == 4) {
                    return 2;
                } else if (position == 5) {
                    return 2;
                } else if (position == 6) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(new LXAdapter(LxRecycleActivity.this));

    }

    public class LXAdapter extends RecyclerView.Adapter {
        public Context ct;

        public LXAdapter(Context ct) {
            this.ct = ct;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(ct).inflate(R.layout.lx_recycle_item, parent, false);
            return new LXViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, 300);
//            layoutParams.height = (int) (200 + (position % 15) * 10);
//
//            holder.itemView.findViewById(R.id.title).setLayoutParams(layoutParams);
//            if (position == 30) {
//                StaggeredGridLayoutManager.LayoutParams lp = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                lp.setFullSpan(true);
//                holder.itemView.setLayoutParams(lp);
//            } else {
//                ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
//                if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
//                    ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(false);
//                }
//            }
            ((TextView) holder.itemView.findViewById(R.id.title)).setText(Integer.toString(position));
        }

        @Override
        public int getItemCount() {
            return 60;
        }
    }

    static class LXViewHolder extends RecyclerView.ViewHolder {

        public LXViewHolder(View itemView) {
            super(itemView);
        }
    }

}

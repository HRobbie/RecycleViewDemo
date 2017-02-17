package com.hrobbie.recycleviewdemo.demo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.hrobbie.recycleviewdemo.R;
import com.hrobbie.recycleviewdemo.adapter.BaseAdapter;
import com.hrobbie.recycleviewdemo.interfaceclass.OnItemClickListener;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RefreshActivity extends AppCompatActivity {
    private XRecyclerView recycler_view;//https://github.com/jianghejie/XRecyclerView

    private List<String> mData=new ArrayList<>();
    private Context mContext;
    private BaseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        mContext=this;
        initData();
        initView();
    }

    private void initView() {
        recycler_view = (XRecyclerView)findViewById(R.id.recycler_view);

        //设置为垂直的样式
        recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        //使用的是系统默认的分割线
        recycler_view.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        //设置适配器
        recycler_view.setAdapter(adapter=new BaseAdapter(mContext,mData));
        //设置默认动画
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        //设置刷新样式
        recycler_view.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        //设置下拉加载样式
        recycler_view.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        final Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.alpha);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                view.startAnimation(animation);
                Toast.makeText(mContext, position + " click",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                view.startAnimation(animation);
                Toast.makeText(mContext, position + " long click",
                        Toast.LENGTH_SHORT).show();

            }
        });

        recycler_view.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refresh();
            }



            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
    }

    private void loadMore() {
//        for(int i=0;i<10;i++){
//            mData.add(i+"");
//        }
//        new Thread(){
//            public void run(){
//                SystemClock.sleep(5000);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        recycler_view.refreshComplete();
//                        adapter.notifyDataSetChanged();
//                    }
//                });
//
//            }
//        }.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    mData.add(i+"");
                }
                recycler_view.refreshComplete();

                adapter.notifyDataSetChanged();
            }
        },1000);

    }

    private void refresh() {
        mData.clear();
        initData();
        new Thread(){
            public void run(){
                SystemClock.sleep(5000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recycler_view.refreshComplete();
                        adapter.notifyDataSetChanged();

                    }
                });

            }
        }.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mData.clear();
                initData();

                recycler_view.refreshComplete();
                adapter.notifyDataSetChanged();
            }
        },1000);

    }

    private void initData() {
        for (int i = 'A'; i <= 'u'; i++)
        {
            mData.add("" + (char) i);
        }
    }
}

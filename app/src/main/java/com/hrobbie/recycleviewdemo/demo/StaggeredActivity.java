package com.hrobbie.recycleviewdemo.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.hrobbie.recycleviewdemo.R;
import com.hrobbie.recycleviewdemo.adapter.BaseAdapter;
import com.hrobbie.recycleviewdemo.decoration.SpacesItemDecoration;
import com.hrobbie.recycleviewdemo.interfaceclass.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class StaggeredActivity extends AppCompatActivity {
    private RecyclerView recycler_view;
    private List<String> mData;
    private Context mContext;
    private BaseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggered);

        mContext=this;
        initData();
        initView();
    }

    private void initView() {
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        //设置为垂直网格样式
//        recycler_view.setLayoutManager(new GridLayoutManager(mContext,4));
        //设置为垂直3列
        recycler_view.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        ////item间隔16
        recycler_view.addItemDecoration(new SpacesItemDecoration(16));
        //设置默认动画
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        recycler_view.setAdapter(adapter=new BaseAdapter(mContext,mData));

        final Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.alpha);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                view.startAnimation(animation);
                Toast.makeText(mContext, position + " 添加A",
                        Toast.LENGTH_SHORT).show();
                adapter.addData(0);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                view.startAnimation(animation);
                Toast.makeText(mContext, position + " 删除B",
                        Toast.LENGTH_SHORT).show();
                adapter.removeData(2);
            }
        });
    }

    private void initData() {
        mData=new ArrayList<>();
        for (int i = 'A'; i <= 'z'; i++)
        {
            mData.add("" + (char) i);
        }
    }
}

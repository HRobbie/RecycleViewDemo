package com.hrobbie.recycleviewdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hrobbie.recycleviewdemo.demo.BaseActivity;
import com.hrobbie.recycleviewdemo.demo.DifferentItemActivity;
import com.hrobbie.recycleviewdemo.demo.FourLevelActivity;
import com.hrobbie.recycleviewdemo.demo.GridActivity;
import com.hrobbie.recycleviewdemo.demo.RefreshActivity;
import com.hrobbie.recycleviewdemo.demo.StaggeredActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_base;
    private Button btn_grid;
    private Button btn_four;
    private Button btn_refresh;
    private Button btn_staggered;
    private Button btn_different;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=this;

        initView();
    }

    private void initView() {
        btn_base = (Button)findViewById(R.id.btn_base);
        btn_base.setOnClickListener(this);

        btn_grid = (Button)findViewById(R.id.btn_grid);
        btn_grid.setOnClickListener(this);

        btn_four = (Button)findViewById(R.id.btn_four);
        btn_four.setOnClickListener(this);

        btn_refresh = (Button)findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(this);

        btn_staggered = (Button)findViewById(R.id.btn_staggered);
        btn_staggered.setOnClickListener(this);

        btn_different = (Button)findViewById(R.id.btn_different);
        btn_different.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //一个简单的listview垂直效果图
            case R.id.btn_base:
                startActivity(new Intent(mContext, BaseActivity.class));
                break;
            case R.id.btn_grid:
                startActivity(new Intent(mContext, GridActivity.class));
                break;
            case R.id.btn_four:
                startActivity(new Intent(mContext, FourLevelActivity.class));
                break;
            case R.id.btn_refresh:
                startActivity(new Intent(mContext, RefreshActivity.class));
                break;
            case R.id.btn_staggered:
                startActivity(new Intent(mContext, StaggeredActivity.class));
                break;
            case R.id.btn_different:
                startActivity(new Intent(mContext, DifferentItemActivity.class));
                break;
        }
    }
}

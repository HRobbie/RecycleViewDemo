package com.hrobbie.recycleviewdemo.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.hrobbie.recycleviewdemo.R;
import com.hrobbie.recycleviewdemo.adapter.FourLevelAdapter;
import com.hrobbie.recycleviewdemo.bean.Level;
import com.hrobbie.recycleviewdemo.db.DBhelper;
import com.hrobbie.recycleviewdemo.decoration.DividerGridItemDecoration;
import com.hrobbie.recycleviewdemo.interfaceclass.OnItemClickListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FourLevelActivity extends AppCompatActivity {
    private RecyclerView lv_continent;
    private RecyclerView lv_country;
    private RecyclerView lv_province;
    private RecyclerView lv_city;

    private DBhelper bhelper;


    private ArrayList<Level> continentValues=new ArrayList<Level>();
    private ArrayList<Level> countryValues=new ArrayList<Level>();
    private ArrayList<Level> provinceValues=new ArrayList<Level>();
    private ArrayList<Level> cityValues;

    private int continentPosition=0;
    private int countryPosition=0;
    private int provincePosition=0;
    private int cityPosition=0;

    private int countryNumber=-1;
    private int provinceNumber=-1;

    private long countryTime=0;
    private long provinceTime=0;
    private FourLevelAdapter continentAdapter;
    private FourLevelAdapter cityAdapter;
    private FourLevelAdapter provinceAdapter;
    private FourLevelAdapter countryAdapter;

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_level);

        mContext=this;
        initView();
    }

    private void initView() {
        lv_continent = (RecyclerView)findViewById(R.id.lv_continent);
        lv_country = (RecyclerView)findViewById(R.id.lv_country);
        lv_province = (RecyclerView)findViewById(R.id.lv_province);
        lv_city = (RecyclerView)findViewById(R.id.lv_city);

        bhelper = new DBhelper(this);

        initData();
    }

    private void initData() {
        setContinent();
        setCountry();
        setProvince();
        setCity();
    }

    /**
     * 设置市
     */
    private void setCity() {
        cityValues=getCity(provinceValues.get(provincePosition).getPlaceid());
        if(!(cityValues.isEmpty())){
            cityAdapter=new FourLevelAdapter(this, cityValues);
            cityAdapter.setSelectedPositionNoNotify(cityPosition, cityValues);
            lv_city.setLayoutManager(new LinearLayoutManager(this));
            lv_city.addItemDecoration(new DividerGridItemDecoration(this));
            lv_city.setAdapter(cityAdapter);
            cityAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Toast.makeText(mContext,cityValues.get(position).getPlacename(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });}
    }

    /**
     * 设置省
     */
    private void setProvince() {
        provinceValues=getProvince(countryValues.get(countryPosition).getPlaceid());
        if(!(provinceValues.isEmpty())){
            provinceAdapter=new FourLevelAdapter(this, provinceValues);
            provinceAdapter.setSelectedPositionNoNotify(provincePosition, provinceValues);
            lv_province.setLayoutManager(new LinearLayoutManager(this));
            lv_province.addItemDecoration(new DividerGridItemDecoration(this));
            lv_province.setAdapter(provinceAdapter);
            provinceAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if(provinceNumber!=position){//记录不是当前点击的
                        provinceNumber=position;//就记录当前条目
                        provinceTime=System.currentTimeMillis();//并记录第一次时间戳
                        Timer timer=new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                provinceNumber=-1;
                                provinceTime=0;
                            }
                        }, 300);
                    }else{//记录的是当前点击的
                        long num = System.currentTimeMillis()-provinceTime;//判断时间差，是不是双击
                        if(num<=300){//时间差200毫秒内
                            Toast.makeText(mContext,provinceValues.get(position).getPlacename(),Toast.LENGTH_SHORT).show();
                        }
                        provinceNumber=-1;//重置过的记录
                        provinceTime=0;//重置时间的记录
                    }
                    cityValues.clear();
                    if(!(provinceValues.isEmpty())){
                        cityValues=getCity(provinceValues.get(position).getPlaceid());
                        if(cityValues.isEmpty()){
                            Toast.makeText(mContext,provinceValues.get(position).getPlacename(),Toast.LENGTH_SHORT).show();
                        }
                        cityAdapter.notifyDataSetChanged();
                        cityAdapter.setSelectedPositionNoNotify(0, cityValues);
                        lv_city.smoothScrollToPosition(0);
                    }else{
                        cityAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });}
    }

    /**
     * 设置国
     */
    private void setCountry() {
        countryValues = getCountry(continentValues.get(continentPosition).getPlaceid());
        if (!(countryValues.isEmpty())) {
            countryAdapter = new FourLevelAdapter(this, countryValues);
            countryAdapter.setSelectedPositionNoNotify(countryPosition, countryValues);
            lv_country.setLayoutManager(new LinearLayoutManager(this));
            lv_country.addItemDecoration(new DividerGridItemDecoration(this));
            lv_country.setAdapter(countryAdapter);
            countryAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (countryNumber != position) {//记录不是当前点击的
                        countryNumber = position;//就记录当前条目
                        countryTime = System.currentTimeMillis();//并记录第一次时间戳
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                countryNumber = -1;
                                countryTime = 0;
                            }
                        }, 300);
                    } else {//记录的是当前点击的
                        long num = System.currentTimeMillis() - countryTime;//判断时间差，是不是双击
                        if (num <= 300) {//时间差200毫秒内
                            Toast.makeText(mContext,countryValues.get(position).getPlacename(),Toast.LENGTH_SHORT).show();
                        }
                        countryNumber = -1;//重置过的记录
                        countryTime = 0;//重置时间的记录
                    }
                    provinceValues.clear();
                    if (!(countryValues.isEmpty())) {
                        provinceValues = getProvince(countryValues.get(position).getPlaceid());
                        if(provinceValues.isEmpty()){
                            Toast.makeText(mContext,countryValues.get(position).getPlacename(),Toast.LENGTH_SHORT).show();
                        }
                        provinceAdapter.notifyDataSetChanged();
                        provinceAdapter.setSelectedPositionNoNotify(0, provinceValues);
                        lv_province.smoothScrollToPosition(0);
                    } else {
                        provinceAdapter.notifyDataSetChanged();
                    }

                    cityValues.clear();
                    if (!(provinceValues.isEmpty())) {
                        cityValues = getCity(provinceValues.get(0).getPlaceid());
//                        if(cityValues.isEmpty()){
//                            CommentUtils.showToast(OfficeSelectActivity.this, provinceValues.get(position).getPlacename());
//                        }
                        cityAdapter.notifyDataSetChanged();
                        cityAdapter.setSelectedPositionNoNotify(0, cityValues);
                        lv_city.smoothScrollToPosition(0);
                    } else {
                        cityAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
        }
    }

    /**
     * 设置洲
     */
    private void setContinent() {
        continentValues=bhelper.getContinent();
        if(!(continentValues.isEmpty())){
            continentAdapter=new FourLevelAdapter(this, continentValues);

            continentAdapter.setSelectedPositionNoNotify(continentPosition, continentValues);
            lv_continent.setLayoutManager(new LinearLayoutManager(this));
            lv_continent.addItemDecoration(new DividerGridItemDecoration(this));
            lv_continent.setAdapter(continentAdapter);
            continentAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    countryValues.clear();
                    if(!(continentValues.isEmpty())){
                        countryValues=getCountry(continentValues.get(position).getPlaceid());
                        if(countryValues.isEmpty()){
                            Toast.makeText(mContext,continentValues.get(position).getPlacename(),Toast.LENGTH_SHORT).show();
                        }
                        countryAdapter.notifyDataSetChanged();
                        countryAdapter.setSelectedPositionNoNotify(0, countryValues);
                        lv_country.smoothScrollToPosition(0);
                    }else{
                        countryAdapter.notifyDataSetChanged();
                    }

                    provinceValues.clear();
                    if(!(countryValues.isEmpty())){
                        provinceValues=getProvince(countryValues.get(0).getPlaceid());
//                        if(provinceValues.isEmpty()){
//                            CommentUtils.showToast(OfficeSelectActivity.this, countryValues.get(position).getPlacename());
//                        }
                        provinceAdapter.notifyDataSetChanged();
                        provinceAdapter.setSelectedPositionNoNotify(0, provinceValues);
                        lv_province.smoothScrollToPosition(0);
                    }else{
                        provinceAdapter.notifyDataSetChanged();
                    }

                    cityValues.clear();
                    if(!(provinceValues.isEmpty())){
                        cityValues=getCity(provinceValues.get(0).getPlaceid());
//                        if(cityValues.isEmpty()){
//                            CommentUtils.showToast(OfficeSelectActivity.this, provinceValues.get(position).getPlacename());
//                        }
                        cityAdapter.notifyDataSetChanged();
                        cityAdapter.setSelectedPositionNoNotify(0, cityValues);
                        lv_city.smoothScrollToPosition(0);
                    }else{
                        cityAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });}
    }


    /**
     * 获取国
     * @param placetoid
     * @return
     */
    public ArrayList<Level> getCountry(String placetoid) {
        return bhelper.getCountry(placetoid);
    }

    /**
     * 获取省
     * @param placetoid
     * @return
     */
    public ArrayList<Level> getProvince(String placetoid) {
        return bhelper.getProvince(placetoid);
    }

    /**
     * 获取市
     * @param placetoid
     * @return
     */
    public ArrayList<Level> getCity(String placetoid) {
        return bhelper.getCity(placetoid);
    }
}

package com.hrobbie.recycleviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hrobbie.recycleviewdemo.R;
import com.hrobbie.recycleviewdemo.interfaceclass.OnItemClickListener;

import java.util.List;

/**
 * user:HRobbie
 * Date:2017/2/16
 * Time:14:22
 * 邮箱：hwwyouxiang@163.com
 * Description:Page Function.
 */

public class DifferentItemAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<String> mData;

    private static final int CASE1=1;
    private static final int CASE2=2;

    public DifferentItemAdapter(Context mContext, List<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }
    //自定义点击事件和长按事件
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener)
    {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case CASE1:
               return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_base, parent, false));
            case CASE2:
                return new DifferentHolder(LayoutInflater.from(mContext).inflate(R.layout.item_different,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position%2==0){
            DifferentHolder holder1= (DifferentHolder) holder;
            holder1.tv_text.setText(mData.get(position));
        }else{
            MyViewHolder holder2= (MyViewHolder) holder;
            holder2.tv_text.setText(mData.get(position));
        }

        setClickListener(holder,position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    //这个非常重要，根据这个选择不同的布局
    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return CASE2;
        }else{
            return CASE1;
        }
    }

    //初始化布局信息
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_text;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_text = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }

    class DifferentHolder extends RecyclerView.ViewHolder{
        TextView tv_text;
        public DifferentHolder(View itemView) {
            super(itemView);
            tv_text = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }


    //设置点击事件
    private void setClickListener(final RecyclerView.ViewHolder holder, int position) {
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                int pos = holder.getLayoutPosition();
                mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                return true;//返回true可以屏蔽点击监听的响应
            }
        });
    }
}

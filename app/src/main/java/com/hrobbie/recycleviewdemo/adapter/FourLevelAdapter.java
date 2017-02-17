package com.hrobbie.recycleviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hrobbie.recycleviewdemo.R;
import com.hrobbie.recycleviewdemo.bean.Level;
import com.hrobbie.recycleviewdemo.interfaceclass.OnItemClickListener;

import java.util.ArrayList;

/**
 * Success is the sum of small efforts, repeated day in and day out.
 * 成功就是日复一日那一点点小小努力的积累。
 * AndroidGroup：158423375
 * Author：Johnny
 * AuthorQQ：956595454
 * AuthorWX：Qiang_it
 * AuthorPhone：nothing
 * Created by 2016/12/1.
 */
public class FourLevelAdapter extends RecyclerView.Adapter<FourLevelAdapter.MyViewHolder>{
    private Context mContext;
    private View.OnClickListener onClickListener;
    private OnItemClickListener mOnItemClickListener;
    private int selectedPos = -1;
    private String selectedText = "";
    private ArrayList<Level> mData;

    public FourLevelAdapter(Context mContext, ArrayList<Level> mData) {
        this.mContext = mContext;
        this.mData = mData;
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPos = (Integer) view.getTag();
                setSelectedPosition(selectedPos);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, selectedPos);
                }
            }
        };
    }

    /**
     * 设置选中的position,并通知刷新其它列表
     */
    public void setSelectedPosition(int pos) {
        if (mData != null && pos < mData.size()) {
            selectedPos = pos;
            selectedText = mData.get(pos).getPlacename();
            notifyDataSetChanged();
        }
    }

    @Override
    public FourLevelAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        MyViewHolder myViewHolder=new MyViewHolder(LayoutInflater.from(mContext
        ).inflate(R.layout.adapter_levellistview,viewGroup,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(FourLevelAdapter.MyViewHolder holder, int position) {
        String placeName = mData.get(position).getPlacename();
        holder.continent_text.setText(placeName);
        if (selectedText != null && selectedText.equals(placeName)) {
            holder.continent_text.setBackgroundResource(R.drawable.choose_item_selected);
        } else {
            holder.continent_text.setBackgroundResource(R.drawable.choose_eara_item_selector);
        }
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }


    //控制item子布局的控制器
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView continent_text;



        public MyViewHolder(View v) {
            super(v);

            continent_text = (TextView) v.findViewById(R.id.continent_text);


        }
    }

    /**
     * 重新定义菜单选项单击接口
     */
//    public interface OnItemClickListener {
//        void onItemClick(View view, int position);
//    }
    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }


    /**
     * 设置选中的position,但不通知刷新
     */
    public void setSelectedPositionNoNotify(int pos,ArrayList<Level> level) {
        selectedPos = pos;
        mData = level;
        if (mData != null && pos < mData.size()) {
            selectedText = mData.get(pos).getPlacename();
        }
    }
}

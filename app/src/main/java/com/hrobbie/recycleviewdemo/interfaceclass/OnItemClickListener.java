package com.hrobbie.recycleviewdemo.interfaceclass;

import android.view.View;

/**
 * user:HRobbie
 * Date:2017/2/15
 * Time:11:13
 * 邮箱：hwwyouxiang@163.com
 * Description:Page Function.
 */

public interface OnItemClickListener {
    void onItemClick(View view, int position);
    void onItemLongClick(View view , int position);
}

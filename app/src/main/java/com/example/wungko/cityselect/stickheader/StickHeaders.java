package com.example.wungko.cityselect.stickheader;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by wungko on 2016/11/8.
 * 悬停adapter item
 */

public interface StickHeaders<T extends RecyclerView.ViewHolder> {
    long getHeaderId(int var1);

    T onCreateHeaderViewHolder(ViewGroup parent);

    void onBindHeaderViewHolder(T holder, int position);

    int getItemCount();
}

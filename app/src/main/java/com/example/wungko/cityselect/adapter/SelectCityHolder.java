package com.example.wungko.cityselect.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by wungko on 2016/11/8.
 */

public abstract class SelectCityHolder<T> extends RecyclerView.ViewHolder{

    public SelectCityHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public abstract void bindData(T t, int position);
}

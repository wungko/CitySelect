package com.example.wungko.cityselect.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wungko.cityselect.R;
import com.example.wungko.cityselect.entity.City;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wungko on 2016/11/8.
 */

public class ReachCityResultAdapter extends RecyclerView.Adapter<ReachCityResultAdapter.MyViewHolder> {

    private List<City> datas;
    private ReachCityResultAdapterListener listener;

    public interface ReachCityResultAdapterListener{
        void onSelected(int position);
    }
    public void setOnReachCityResultAdapterListener(ReachCityResultAdapterListener listener){
        this.listener = listener;
    }

    public ReachCityResultAdapter(List<City> datas) {
        this.datas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_city, parent, false),listener);
        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_city.setText(datas.get(position).cityName);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_city)
        TextView tv_city;
        @BindView(R.id.line)
        ImageView line;

        ReachCityResultAdapterListener mListener;

        public MyViewHolder(View itemView,ReachCityResultAdapterListener listener) {
            super(itemView);
            this.mListener = listener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tv_city) public void onClick(View view){
            if (mListener != null){
                mListener.onSelected(getAdapterPosition());
            }
        }

    }

}

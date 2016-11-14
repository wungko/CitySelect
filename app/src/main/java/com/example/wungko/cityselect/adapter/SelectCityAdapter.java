package com.example.wungko.cityselect.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wungko.cityselect.R;
import com.example.wungko.cityselect.entity.City;
import com.example.wungko.cityselect.entity.CurrentCity;
import com.example.wungko.cityselect.entity.HotCityModel;
import com.example.wungko.cityselect.entity.LocationCity;
import com.example.wungko.cityselect.entity.RecentTripCity;
import com.example.wungko.cityselect.stickheader.StickHeaders;
import com.example.wungko.cityselect.view.FlowLayout;
import com.example.wungko.cityselect.view.MeasureUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wungko on 2016/11/8.
 */

public class SelectCityAdapter extends RecyclerView.Adapter<SelectCityHolder> implements StickHeaders {
    public static final int CURRENT_CITY = 0;
    public static final int LOCATION_CITY = 1;
    public static final int RECENT_TRIP_CITY = 2;
    public static final int HOT_CITY = 3;
    public static final int NORMAL_CITY = 4;
    private Activity mActivity;
    private List mItems;


    public void setItems(List items) {
        this.mItems = items;
        this.notifyDataSetChanged();

    }


    public SelectCityAdapter(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Override
    public SelectCityHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        SelectCityHolder selectCityHolder = null;
        switch (viewType) {
            case CURRENT_CITY:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.current_select_city, viewGroup, false);
                selectCityHolder = new CurrentCityHodler(view);
                break;
            case LOCATION_CITY:
                // 定位条目
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_select_city_location, viewGroup, false);
                selectCityHolder = new LocationHolder(view);
                break;
            case RECENT_TRIP_CITY:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recent_trip_city, viewGroup, false);
                selectCityHolder = new RecentTripCityHolder(view,mActivity);
                break;
            case HOT_CITY:
                // 热门城市
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_select_city_hotcity, viewGroup, false);
                selectCityHolder = new HotCityHolder(view, mActivity);
                break;
            case NORMAL_CITY:
                // 城市列表
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_select_city, viewGroup, false);
                selectCityHolder = new CityViewHolder(view);
                break;
        }
        return selectCityHolder;
    }

    @Override
    public void onBindViewHolder(SelectCityHolder holder, int position) {
        holder.bindData(getItem(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        City item = getItem(position);

        if(item instanceof CurrentCity) {
            return CURRENT_CITY;
        } else if(item instanceof LocationCity) {
            return LOCATION_CITY;
        } else if(item instanceof RecentTripCity) {
            return RECENT_TRIP_CITY;
        } else if(item instanceof HotCityModel) {
            return HOT_CITY;
        } else if(item instanceof City) {
            return NORMAL_CITY;
        }

        return -1;
    }


    @Override
    public long getHeaderId(int i) {
        City city = getItem(i);
        //当前城市不需要展示悬停
        if (city instanceof CurrentCity) {
            return -1;
        } else if(city instanceof LocationCity || city instanceof RecentTripCity || city instanceof  HotCityModel) {
            return city.cityName.charAt(0);
        }
        return city.citySpell.charAt(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_section_abc, viewGroup, false);
        ViewHolderTitle holderTitle = new ViewHolderTitle(view);
        return holderTitle;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolderTitle holderTitle = (ViewHolderTitle) viewHolder;
        City item = getItem(position);
        if (item instanceof LocationCity){
            holderTitle.number.setText(item.cityName + "城市");
        } else if(item instanceof RecentTripCity) {
            holderTitle.number.setText(item.cityName + "访问城市");
        } else if(item instanceof HotCityModel) {
            holderTitle.number.setText(item.cityName + "城市");
        } else {
            holderTitle.number.setText("" + item.citySpell.toUpperCase().charAt(0));
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolderTitle extends RecyclerView.ViewHolder {

        @BindView(R.id.number)
        TextView number;

        public ViewHolderTitle(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    /**
     * 当前城市
     */
    class CurrentCityHodler extends SelectCityHolder<CurrentCity> {

        @BindView(R.id.tv_city)
        TextView tvCity;

        public CurrentCityHodler(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(CurrentCity currentCity, int i) {
            tvCity.setText(currentCity.currentCityName);
        }

        @OnClick(R.id.tv_city)
        public void selectCity() {
            String s = tvCity.getText().toString();
            System.out.println(s);
        }

    }

    /**
     * 定位
     */
    class LocationHolder extends SelectCityHolder<LocationCity> {

        @BindView(R.id.tv_location)
        TextView tv_location;

        public LocationHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(LocationCity locationCityMode, int i) {
            tv_location.setText(locationCityMode.location);
        }

    }

    /**
     * 最近访问城市
     */
    class RecentTripCityHolder extends SelectCityHolder<RecentTripCity> {

        private Activity mActivity;
        @BindView(R.id.flowLayout)
        FlowLayout flowLayout;

        public RecentTripCityHolder(View itemView, Activity activity) {
            super(itemView);
            mActivity = activity;
        }

        @Override
        public void bindData(RecentTripCity hotCityModel, int i) {
            flowLayout.removeAllViews();
            flowLayout.setHorizontalSpacing((int) MeasureUtil.dp2px(mActivity, 10));
            flowLayout.setVerticalSpacing((int) MeasureUtil.dp2px(mActivity, 10));
            List<View> tags = new ArrayList<>();

            for (String hotCity : hotCityModel.recentTripCityNames) {
                // android:background="@drawable/selector_item"
                TextView textView = new TextView(mActivity);
                textView.setText(hotCity);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.parseColor("#323232"));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                Drawable drawable = mActivity.getResources().getDrawable(R.drawable.selector_city_item);
                textView.setBackgroundDrawable(drawable);
                textView.setWidth((int) MeasureUtil.dp2px(mActivity, 102));
                textView.setHeight((int) MeasureUtil.dp2px(mActivity, 40));
                tags.add(textView);
            }
            flowLayout.setTags(tags);

            flowLayout.setOnClickCityItemListener(new FlowLayout.OnClickCityItemListener() {

                @Override
                public void clickCityItem(View view) {
                    TextView tag = (TextView) view;
                    String s = tag.getText().toString();
                    System.out.println(s);
                }
            });
        }
    }

    /**
     * 热门城市
     */
    class HotCityHolder extends SelectCityHolder<HotCityModel> {

        private Activity mActivity;
        @BindView(R.id.flowLayout)
        FlowLayout flowLayout;

        public HotCityHolder(View itemView, Activity activity) {
            super(itemView);
            mActivity = activity;
        }

        @Override
        public void bindData(HotCityModel hotCityModel, int i) {
            flowLayout.removeAllViews();
            flowLayout.setHorizontalSpacing((int) MeasureUtil.dp2px(mActivity, 10));
            flowLayout.setVerticalSpacing((int) MeasureUtil.dp2px(mActivity, 10));
            List<View> tags = new ArrayList<>();

            for (String hotCity : hotCityModel.hotcities) {
                // android:background="@drawable/selector_item"
                TextView textView = new TextView(mActivity);
                textView.setText(hotCity);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.parseColor("#323232"));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                Drawable drawable = mActivity.getResources().getDrawable(R.drawable.selector_city_item);
                textView.setBackgroundDrawable(drawable);
                textView.setWidth((int) MeasureUtil.dp2px(mActivity, 102));
                textView.setHeight((int) MeasureUtil.dp2px(mActivity, 40));
                tags.add(textView);
            }
            flowLayout.setTags(tags);

            flowLayout.setOnClickCityItemListener(new FlowLayout.OnClickCityItemListener() {

                @Override
                public void clickCityItem(View view) {
                    TextView tag = (TextView) view;
                    String s = tag.getText().toString();
                    System.out.println(s);
                }
            });
        }
    }

    /**
     * 城市列表
     */
    class CityViewHolder extends SelectCityHolder<City> {

        @BindView(R.id.tv_city)
        TextView tv_city;

        @BindView(R.id.line)
        ImageView line;

        public CityViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(City city, int i) {
            tv_city.setText(city.cityName);
//            if (getAdapterPosition() < i - 1) {
//                City nextCity = getItem(getAdapterPosition() + 1);
//                line.setVisibility(nextCity.citySpell.charAt(0) == city.citySpell.charAt(0) ? View.VISIBLE : View.GONE);
//            } else {
//                line.setVisibility(View.GONE);
//            }
        }

        @OnClick(R.id.ll_city_container)
        public void clickItem(View view) {
            int position = getAdapterPosition();
            City item = getItem(position);
            System.out.println(item.cityName);

        }
    }

    public <T> T getItem(int i) {
        T t = (T) mItems.get(i);
        return t;
    }

}

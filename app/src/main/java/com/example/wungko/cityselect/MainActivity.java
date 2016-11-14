package com.example.wungko.cityselect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wungko.cityselect.adapter.ReachCityResultAdapter;
import com.example.wungko.cityselect.adapter.SelectCityAdapter;
import com.example.wungko.cityselect.entity.City;
import com.example.wungko.cityselect.entity.CurrentCity;
import com.example.wungko.cityselect.entity.HotCityModel;
import com.example.wungko.cityselect.entity.LocationCity;
import com.example.wungko.cityselect.entity.RecentTripCity;
import com.example.wungko.cityselect.stickheader.StickyRecyclerHeadersDecoration;
import com.example.wungko.cityselect.view.SideBarWithText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wungko.cityselect.R.id.rl_search_result;

public class MainActivity extends AppCompatActivity implements ReachCityResultAdapter.ReachCityResultAdapterListener, SideBarWithText.OnTouchingLetterChangedListener {
    @BindView(R.id.et_search_city)
    EditText etSearchCity;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.recyclerClient)
    RecyclerView recyclerClient;
    @BindView(R.id.sideBar)
    SideBarWithText sideBar;
    @BindView(R.id.layer)
    TextView layer;
    @BindView(R.id.rl_city_list)
    RelativeLayout rlCityList;
    @BindView(rl_search_result)
    RecyclerView rlSearchResult;

    private SelectCityAdapter	selectCityAdapter;
    private ReachCityResultAdapter	reachCityResultAdapter;
    private List<City> reachCities;
    LinearLayoutManager mLinearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        List<City> selectCityType = new ArrayList<>();
        //城市备选
        selectCityAdapter = new SelectCityAdapter(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerClient.setLayoutManager(mLinearLayoutManager);

        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(selectCityAdapter);
        recyclerClient.addItemDecoration(headersDecor);
        recyclerClient.setAdapter(selectCityAdapter);
        selectCityAdapter.setItems(selectCityType);


        //搜索结果
        reachCities = new ArrayList<>();
        rlSearchResult.setLayoutManager(new LinearLayoutManager(this));
        reachCityResultAdapter = new ReachCityResultAdapter(reachCities);
        reachCityResultAdapter.setOnReachCityResultAdapterListener(this);
        rlSearchResult.setAdapter(reachCityResultAdapter);

        //当前城市
        CurrentCity currentCity = new CurrentCity();
        currentCity.currentCityName ="当前城市：北京";
        currentCity.cityName = "定位";
        selectCityType.add(currentCity);

        //当前定位城市
        LocationCity locationCity = new LocationCity();
        locationCity.location = "北京";
        locationCity.cityName ="定位";
        selectCityType.add(locationCity);

        //最近访问城市
        String cs = "济南,杭州,南京,武汉";
        RecentTripCity recentTripCity = new RecentTripCity();
        recentTripCity.recentTripCityNames = new ArrayList<>();
        for(String c : cs.split(",")) {
            recentTripCity.recentTripCityNames.add(c);
            recentTripCity.cityName = "最近";
        }
        selectCityType.add(recentTripCity);

        //热门城市
        HotCityModel hotCityModel = new HotCityModel();
        hotCityModel.hotcities = new ArrayList<>();

        //普通待选城市
        List<City> citys = new ArrayList<>();
        citys.add(new City(0,"上海","shanghai"));
        citys.add(new City(0,"上清","shangqing"));
        citys.add(new City(0, "上1", "shang1"));
        citys.add(new City(0,"上2","shang2"));
        citys.add(new City(0,"上3","shang3"));
        hotCityModel.hotcities.add("上1");
        hotCityModel.cityName="热门";

        citys.add(new City(0,"广海","guanghai"));
        citys.add(new City(0,"广清","guangqing"));
        citys.add(new City(0,"广1","guang1"));
        citys.add(new City(0,"广2","guang2"));
        citys.add(new City(0,"广3","guang3"));
        hotCityModel.hotcities.add("广3");
        hotCityModel.cityName="热门";

        citys.add(new City(0,"哈海","huanghai"));
        citys.add(new City(0,"哈清","huangqing"));
        citys.add(new City(0,"哈1","huang1"));
        citys.add(new City(0,"哈2","huang2"));
        citys.add(new City(0,"哈3","huang3"));
        hotCityModel.hotcities.add("哈3");
        hotCityModel.cityName="热门";

        citys.add(new City(0,"鸡海","juanghai"));
        citys.add(new City(0,"鸡清","juangqing"));
        citys.add(new City(0,"鸡1","juang1"));
        citys.add(new City(0,"鸡2","juang2"));
        citys.add(new City(0,"鸡3","juang3"));
        hotCityModel.hotcities.add("哈3");
        hotCityModel.cityName="热门";

        selectCityType.add(hotCityModel);
        selectCityType.addAll(citys);
        selectCityAdapter.notifyDataSetChanged();

//set abc
        List<String> abc = new ArrayList<>();
        for (City tmp : selectCityType) {
            if (!TextUtils.isEmpty(tmp.citySpell)) {
                String c = "" + tmp.citySpell.charAt(0);
                if (!abc.contains(c)) {
                    abc.add(c);
                }
            } else {
                String c = "" + tmp.cityName;
                if (!abc.contains(c)) {
                    abc.add(c);
                }
            }
        }
        String[] abcArray = new String[abc.size()];
        abc.toArray(abcArray);
        sideBar.setABC(abcArray);
        sideBar.setTextView(layer);
        sideBar.setOnTouchingLetterChangedListener(this);
    }

    @Override
    public void onSelected(int position) {

    }

    @Override
    public void onTouchingLetterChanged(String s) {
        int count = selectCityAdapter.getItemCount();
        for (int i = 0; i < count; i++) {
            City city = selectCityAdapter.getItem(i);
            if (s.equals("定位") && isBlank(city.citySpell)) {
                recyclerClient.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLinearLayoutManager.scrollToPositionWithOffset(1,0);
                    }
                },1);
                break;
            } else if(s.equals("最近")) {

                recyclerClient.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLinearLayoutManager.scrollToPositionWithOffset(2,0);
                    }
                },1);
                break;
            }else if(s.equals("热门")) {
                recyclerClient.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLinearLayoutManager.scrollToPositionWithOffset(3,0);
                    }
                },1);

                break;
            }else {
                if (!isBlank(city.citySpell)) {
                    String number = "" + city.citySpell.charAt(0);
                    final int j = i;
                    if (s.equals(number)) {
                        recyclerClient.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mLinearLayoutManager.scrollToPositionWithOffset(j,0);
                            }
                        },1);

                        break;
                    }
                }
            }
        }
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
}

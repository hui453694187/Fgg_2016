package com.yunfangdata.fgg.logic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.enumObj.City;
import com.yunfangdata.fgg.ui.HomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2015/12/18.
 */
public class CityListOperator implements View.OnClickListener,AdapterView.OnItemClickListener{

    private View contextView;

    /**父Activity*/
    private HomeActivity homeActivity;

    private ListView cityLv;

    private TextView cancelTv;

    private LayoutInflater inflater;

    private CityListAdapter cityListAdapter;

    public CityListOperator(HomeActivity homeActivity,View contextView){
        this.contextView=contextView;
        this.homeActivity=homeActivity;
        initView();
        inflater=LayoutInflater.from(this.homeActivity);
    }

    private void initView() {
        cityLv=(ListView)this.contextView.findViewById(R.id.city_lv);
        cancelTv=(TextView)this.contextView.findViewById(R.id.cancel_tv);
        cityLv.setOnItemClickListener(this);
        cancelTv.setOnClickListener(this);
        cityListAdapter=new CityListAdapter();
        cityLv.setAdapter(cityListAdapter);
    }

    @Override
    public void onClick(View view) {
        homeActivity.dismissCityListPop();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        City c=(City)cityListAdapter.getItem(i);
        FggApplication.getInstance().city=c;
        homeActivity.dismissCityListPop();
    }

    private class CityListAdapter extends BaseAdapter{

        List<City> cities;

        private TextView cityTv;

        CityListAdapter(){
            cities=new ArrayList<>();
            City[] city=City.values();
            for(City c:city){
                cities.add(c);
            }
        }

        @Override
        public int getCount() {
            return cities.size();
        }

        @Override
        public Object getItem(int i) {
            return cities.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view==null){
                view=inflater.inflate(R.layout.list_item_filtrate_bar,null,false);
                cityTv=(TextView)view.findViewById(R.id.name_tv);
                view.setTag(cityTv);
            }else{
                cityTv=(TextView)view.getTag();
            }
            City c=cities.get(i);
            cityTv.setText(c.getCityName());

            return view;
        }
    }
}

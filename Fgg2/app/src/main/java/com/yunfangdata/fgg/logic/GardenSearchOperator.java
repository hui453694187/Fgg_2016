package com.yunfangdata.fgg.logic;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yunfang.framework.httpClient.RESTActionRequest;
import com.yunfang.framework.utils.YFLog;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.base.FggApplication;
import com.yunfangdata.fgg.model.GardenDetail;
import com.yunfangdata.fgg.model.ResidentialParameters;
import com.yunfangdata.fgg.model.SearchResult;
import com.yunfangdata.fgg.ui.HomeActivity;
import com.yunfangdata.fgg.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2015/12/19.
 * 小区搜索界面逻辑
 */
public class GardenSearchOperator implements TextWatcher, View.OnClickListener, AdapterView.OnItemClickListener {

    private HomeActivity content;

    private LayoutInflater inflater;

    private View contentView;

    private TextView cacelTv;

    private EditText searchEdt;

    private TextView cityTv;

    private ListView resultLv;

    private SearchResultAdapter resultAdapter;

    private ImageView clear_img;

    public GardenSearchOperator(HomeActivity content, View contextView) {
        this.content = content;
        inflater = LayoutInflater.from(this.content);
        this.contentView = contextView;
        initView();

    }

    private void initView() {
        cacelTv = $(R.id.cancel_tv);
        searchEdt = $(R.id.search_edt);
        cityTv = $(R.id.city_name_tv);
        resultLv = $(R.id.search_garden_lv);
        clear_img = $(R.id.clear_img);

        setCityTv();

        resultAdapter = new SearchResultAdapter();
        resultLv.setAdapter(resultAdapter);
        clear_img.setVisibility(View.GONE);

        resultLv.setOnItemClickListener(this);
        clear_img.setOnClickListener(this);
        cacelTv.setOnClickListener(this);
        cityTv.setOnClickListener(this);
        searchEdt.addTextChangedListener(this);
    }

    public void setCityTv(){
        cityTv.setText(FggApplication.getInstance().city.getCityName());
    }

    private <E extends View> E $(int id) {
        return (E) this.contentView.findViewById(id);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if (charSequence.length() > 0) {
            clear_img.setVisibility(View.VISIBLE);
        } else {
            clear_img.setVisibility(View.GONE);
        }
        content.doWork("", HomeViewModel.FUZZY_SEARCH_GARDEN_DETAIL, charSequence.toString());

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear_img:
                searchEdt.setText("");
                break;
            case R.id.cancel_tv:
                content.dismissGardenSearchPopup();
                break;
            case R.id.city_name_tv:
                content.showCityListPopWind();
                //切换城市，清空当前搜索数据
                resultAdapter.refreshData(new ArrayList<SearchResult>());
                searchEdt.setText("");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SearchResult result = resultAdapter.getItem(i);
        /*ResidentialParameters parameters = new ResidentialParameters();
        parameters.setResidentialAreaName(result.getResidentialAreaName());
        //parameters.setDistrictName(result.getDistrictFullName());*/
        content.doWork("", HomeViewModel.GET_SINGLE_GARDEN_DATAIL, result.getResidentialAreaID());
    }


    /***
     * 搜索结果适配器
     */
    private class SearchResultAdapter extends BaseAdapter {
        /**
         * 搜索到的小区详情
         */
        List<SearchResult> details;

        SearchResultAdapter() {
            details = new ArrayList<>();
        }

        public void refreshData(List<SearchResult> details) {
            if (this.details == null) {
                this.details = new ArrayList<>();
            }
            this.details.clear();

            this.details.addAll(details);
            this.notifyDataSetChanged();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder childView;
            if (view == null) {
                childView = new ViewHolder();
                view = inflater.inflate(R.layout.list_item_search_garden, null, false);
                childView.gardenNameTv = (TextView) view.findViewById(R.id.garden_name_tv);
                childView.addressTv = (TextView) view.findViewById(R.id.address_tv);
                view.setTag(childView);
            } else {
                childView = (ViewHolder) view.getTag();
            }
            SearchResult gd = details.get(i);
            childView.gardenNameTv.setText(gd.getResidentialAreaName());
            childView.addressTv.setText(gd.getAddress());

            return view;
        }

        @Override
        public int getCount() {
            return details.size();
        }

        @Override
        public SearchResult getItem(int i) {
            return details.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        class ViewHolder {
            TextView gardenNameTv;
            TextView addressTv;
        }


    }

    public void refreshAdapter(List<SearchResult> searchResults) {
        resultAdapter.refreshData(searchResults);
    }

}

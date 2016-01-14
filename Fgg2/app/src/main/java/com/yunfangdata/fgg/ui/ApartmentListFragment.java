package com.yunfangdata.fgg.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yunfang.framework.base.BaseWorkerFragment;
import com.yunfang.framework.utils.YFLog;
import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.helper.IntentHelper;
import com.yunfangdata.fgg.model.GardenDetail;
import com.yunfangdata.fgg.ui.adapter.GardenListAdapter;

/**
 * @author kevin
 *  小区列表Fragment
 */
public class ApartmentListFragment extends BaseWorkerFragment implements AdapterView.OnItemClickListener{

    private HomeActivity homeActivity;
    /*小区列表**/
    private ListView garden_info_lv;
    /*小区列表适配器**/
    private GardenListAdapter gardenListAdapter;

    public ApartmentListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void handlerBackgroundHandler(Message msg) {

    }

    @Override
    protected void handUiMessage(Message msg) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apartment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        gardenListAdapter=new GardenListAdapter(homeActivity,homeActivity.viewModel.getResidentialAreas());
        garden_info_lv=$(R.id.garden_info_lv);
        garden_info_lv.setAdapter(gardenListAdapter);

        garden_info_lv.setOnItemClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            homeActivity = (HomeActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void resetData() {
        gardenListAdapter.refreshData(homeActivity.viewModel.getResidentialAreas());
    }

    public final <E extends View> E $(int id) {
        return (E) this.getView().findViewById(id);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        GardenDetail gd=(GardenDetail)gardenListAdapter.getItem(i);
        IntentHelper.intentToHouseDetial(homeActivity, gd.getResidentialAreaID());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden){
            gardenListAdapter.refreshData(homeActivity.viewModel.getResidentialAreas());
        }

    }
}

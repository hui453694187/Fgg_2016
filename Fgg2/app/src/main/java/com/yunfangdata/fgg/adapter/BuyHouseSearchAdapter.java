package com.yunfangdata.fgg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yunfangdata.fgg.R;
import com.yunfangdata.fgg.model.BuyHouseBeen;

import java.util.List;


/**
 * 小区搜索
 *
 */
public class BuyHouseSearchAdapter extends BaseAdapter{
	private static final String TAG = "BuyHouseSearchAdapter";
	private static final int LOGLEVEL = 1;

	private Context context;
	private BuyHouseBeen buyHouseBeen;
//	private ArrayFilter mFilter;
//	private final Object mLock = new Object();

	public void setBuyHouseBeen(BuyHouseBeen buyHouseBeen) {
		this.buyHouseBeen = buyHouseBeen;
	}

	public BuyHouseSearchAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int getCount() {
		if (buyHouseBeen!= null && buyHouseBeen.getResultData()!= null && buyHouseBeen.getResultData().size()>0){
			return buyHouseBeen.getResultData().size();
		}else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_buy_house_search, null);
			holder = new ViewHolder();
			holder.itemName = (TextView) convertView.findViewById(R.id.item_name);
			holder.itemCommunity = (TextView) convertView.findViewById(R.id.item_community);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		List<BuyHouseBeen.ResultDataEntity> resultData = buyHouseBeen.getResultData();
		BuyHouseBeen.ResultDataEntity resultDataEntity = resultData.get(position);
		if (resultDataEntity!= null){
			//设置匹配的名字
			holder.itemName.setText(resultDataEntity.getResidentialAreaName());

			//设置小区名字
			holder.itemCommunity.setText(resultDataEntity.getDistrictFullName());
		}
		return convertView;

	}


	private class ViewHolder {
		private TextView itemName;  // 匹配的名字
		private TextView itemCommunity;  // 小区名字
	}

//
//	@Override
//	public Filter getFilter() {
//		if (mFilter == null) {
//			mFilter = new ArrayFilter();
//		}
//		return mFilter;
//	}
//
//	private class ArrayFilter extends Filter {
//
//		@Override
//		protected FilterResults performFiltering(CharSequence constraint) {
//			FilterResults results = new FilterResults();
//			List<BuyHouseBeen.ResultDataEntity> resultData = buyHouseBeen.getResultData();
//			if (resultData!= null && resultData.size()>0){
//				results.values = resultData;
//				results.count = resultData.size();
//			}else {
//				results.count = 0;
//			}
//			return results;
//		}
//
//		@Override
//		protected void publishResults(CharSequence constraint, FilterResults results) {
//			if (results.count > 0) {
//				notifyDataSetChanged();
//			} else {
//				notifyDataSetInvalidated();
//			}
//		}
//	}
}

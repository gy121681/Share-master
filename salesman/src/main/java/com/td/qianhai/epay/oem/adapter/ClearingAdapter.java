package com.td.qianhai.epay.oem.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.td.qianhai.epay.oem.R;

/**
 * 结算方式的dialog
 * 
 * @author liangge
 * 
 */
public class ClearingAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<HashMap<String, String>> list;

	public ClearingAdapter(Context context,
			ArrayList<HashMap<String, String>> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		RadioViewHolder holder = null;
		if (convertView != null) {
			holder = (RadioViewHolder) convertView.getTag();
		} else {
			holder = new RadioViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.radio_list_item, null);
			holder.itemName = (TextView) convertView
					.findViewById(R.id.radio_item_text);
			convertView.setTag(holder);
		}
		
		String item = list.get(position).get("itemName");
		if(item!=null){
//			if(item.length()>=2){
//				item = item+"0";
//			}
			holder.itemName.setText(list.get(position).get("itemName").toString());
		}
		
		return convertView;
	}

	class RadioViewHolder {
		TextView itemName;
	}

}

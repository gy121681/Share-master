package com.shareshenghuo.app.shop.adapter;

import com.shareshenghuo.app.shop.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BasAdapter extends BaseAdapter {
	private Context con;
	public String[] adapter_list;

	public BasAdapter(String[] selectPicTypeStr,Context  context) {
		adapter_list = selectPicTypeStr;
		this.con = context;
	}

	@Override
	public int getCount() {
		return adapter_list.length;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {

//		 LayoutInflater inflater = LayoutInflater.from(con);
//		 View view = inflater.inflate(R.layout.bastextview, null);
//		 TextView v = (TextView) view.findViewById(R.id.tvs);
//		 v.setText(adapter_list[position]);

		TextView tv = new TextView(con);
		tv.setTextColor(con.getResources().getColor(R.color.black));
		tv.setPadding(80, 20, 20, 20);
		tv.setTextSize(18);
		tv.setBackgroundResource(R.drawable.form_bg_wihte);
		tv.setText(adapter_list[position]);
		return tv;
	}

}

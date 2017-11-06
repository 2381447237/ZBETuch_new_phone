package com.youli.zbetuch.jingan.naire;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.youli.zbetuch.jingan.R;

public class WenTypeAdapter extends BaseAdapter {

	private Context context;
	private List<WenJuanType> juanTypes;

	public WenTypeAdapter(Context context, List<WenJuanType> juanTypes) {
		super();
		this.context = context;
		this.juanTypes = juanTypes;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return juanTypes.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return juanTypes.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return juanTypes.get(arg0).getID();
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		HoldItem holdItem = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.item_question_naire, null);
			holdItem = new HoldItem();
			holdItem.tv_id = (TextView) view.findViewById(R.id.tv_item_question_naire_no);
			holdItem.tv_name = (TextView) view.findViewById(R.id.tv_item_question_naire_title);
			holdItem.tv_no = (TextView) view.findViewById(R.id.tv_item_question_naire_naire_no);
			holdItem.tv_time = (TextView) view.findViewById(R.id.tv_item_question_naireo_date);
			view.setTag(holdItem);
		} else {
			holdItem = (HoldItem) view.getTag();
		}
	//	view.setBackgroundResource(MainTools.getbackground1(position));
		if (position % 2 == 0) {
			view.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item1);
		} else {
			view.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item2);
		}
		WenJuanType wenJuanType = juanTypes.get(position);
		holdItem.tv_id.setText(position + 1 + "");
		holdItem.tv_name.setText(wenJuanType.getTITLE());
		holdItem.tv_no.setText(wenJuanType.getNO());
		holdItem.tv_time.setText(wenJuanType.getCREATE_TIME().substring(0, 10));
		return view;
	}

	class HoldItem {
		TextView tv_id, tv_name, tv_no, tv_time;
	}

}

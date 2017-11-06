package com.youli.zbetuch.jingan.naire;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.youli.zbetuch.jingan.R;

public class HistoryInfoAdapter extends BaseAdapter {

	private Context mContext;
	private List<HistoryInfo> historyInfos;

	public HistoryInfoAdapter(Context mContext, List<HistoryInfo> historyInfos) {
		super();
		this.mContext = mContext;
		this.historyInfos = historyInfos;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return historyInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return historyInfos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return historyInfos.get(arg0).getID();
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		HoldItem holdItem = null;
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(
					R.layout.item_person_info, null);
			holdItem = new HoldItem();
			holdItem.tv_id = (TextView) view.findViewById(R.id.tv_bianhao);
			holdItem.tv_name = (TextView) view.findViewById(R.id.tv_name);
			holdItem.tv_sex = (TextView) view.findViewById(R.id.tv_sex);
			holdItem.tv_lxdz = (TextView) view.findViewById(R.id.tv_lxdz);
			holdItem.tv_jd = (TextView) view.findViewById(R.id.tv_jd);
			holdItem.tv_jw = (TextView) view.findViewById(R.id.tv_jw);
			view.setTag(holdItem);
		} else {
			holdItem = (HoldItem) view.getTag();
		}
		//view.setBackgroundResource(MainTools.getbackground1(position));
		if (position % 2 == 0) {
			view.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item1);
		} else {
			view.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item2);
		}
		HistoryInfo info = historyInfos.get(position);
		holdItem.tv_id.setText(info.getNO());
		holdItem.tv_name.setText(info.getNAME());
		holdItem.tv_sex.setText(info.getSEX());
		holdItem.tv_lxdz.setText(info.getRECEIVED_TIME().substring(0, 10));
		holdItem.tv_jd.setText(info.getJD());
		holdItem.tv_jw.setText(info.getJW());
		return view;
	}

	class HoldItem {
		TextView tv_id, tv_name, tv_sex, tv_jd, tv_jw, tv_lxdz;
	}

}

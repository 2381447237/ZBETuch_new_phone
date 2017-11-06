package com.youli.zbetuch.jingan.naire;

import java.util.List;


import com.youli.zbetuch.jingan.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class HistoryListAdapter extends BaseAdapter {

	private List<FamilyInfo> infos;
	private ShowPersionHistoryList context;
	public HistoryListAdapter(List<FamilyInfo> infos, ShowPersionHistoryList context) {
		super();
		this.infos = infos;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return infos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		HoldItem holdItem = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.item_person_list_sphl, null);
			holdItem = new HoldItem();
			holdItem.tv_name = (TextView) view.findViewById(R.id.tv_name_person_sphl);
			holdItem.tv_lxdz = (TextView) view.findViewById(R.id.tv_lxdz_person_sphl);
			holdItem.tv_jd = (TextView) view.findViewById(R.id.tv_jd_person_sphl);
			holdItem.tv_jw = (TextView) view.findViewById(R.id.tv_jw_person_sphl);
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
		FamilyInfo info = infos.get(position);
		holdItem.tv_name.setText(info.getSQR());
		if(context.info.getQA_MASTER()==5){
			holdItem.tv_name.setVisibility(View.VISIBLE);
		}else if(context.info.getQA_MASTER()==6){
			holdItem.tv_name.setVisibility(View.GONE);
		}
		holdItem.tv_lxdz.setText(info.getADRESS());
		holdItem.tv_jd.setText(info.getXZJD());
		holdItem.tv_jw.setText(info.getSQJWC());
		
		
		return view;
	}

	class HoldItem {
		TextView tv_name, tv_sex, tv_jd, tv_jw, tv_lxdz;
	}

}

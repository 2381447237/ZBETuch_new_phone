package com.youli.zbetuch.jingan.naire;

import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.youli.zbetuch.jingan.R;

public class WenJuanPersonAdapter extends BaseAdapter {

	private List<WenJuanPersonInfo> infos;
	private WenJuanPersonActivity context;
	public WenJuanPersonAdapter(List<WenJuanPersonInfo> infos, WenJuanPersonActivity context) {
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
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		HoldItem holdItem = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.item_gxperson_info, null);
			holdItem = new HoldItem();
			holdItem.tv_name = (TextView) view.findViewById(R.id.tv_item_naire_person_name);
			holdItem.tv_sex = (TextView) view.findViewById(R.id.tv_item_naire_person_sex);
			holdItem.tv_lxdz = (TextView) view.findViewById(R.id.tv_item_naire_person_huji);
			holdItem.tv_jd = (TextView) view.findViewById(R.id.tv_item_naire_person_street);
			holdItem.tv_jw = (TextView) view.findViewById(R.id.tv_item_naire_person_jw);
			holdItem.tv_jtcys=(TextView) view.findViewById(R.id.tv_item_naire_person_family_num);
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
		WenJuanPersonInfo info = infos.get(position);
		holdItem.tv_name.setText(info.getNAME());
		holdItem.tv_sex.setText(info.getSEX());
		holdItem.tv_lxdz.setText(info.getDZ());
		holdItem.tv_jd.setText(info.getJD());
		holdItem.tv_jw.setText(info.getJW());
		if(context.myISJYSTATUS){
			holdItem.tv_jtcys.setVisibility(View.VISIBLE);
		}else{
			holdItem.tv_jtcys.setVisibility(View.GONE);
		}
		holdItem.tv_jtcys.setText(info.getJTCYSL()+"");
		return view;
	}

	class HoldItem {
		TextView tv_id, tv_name, tv_sex, tv_jd, tv_jw, tv_lxdz,tv_jtcys;
	}

}

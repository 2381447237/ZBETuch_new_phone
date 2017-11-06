package com.youli.zbetuch.jingan.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CheckBoxAdapter extends BaseAdapter {

	private List<CheckBox> boxs;
	private Context mContext;
	private List<CheckBox> selectedBoxs = new ArrayList<CheckBox>();

	public List<CheckBox> getSelectedBoxs() {
		return selectedBoxs;
	}

	public CheckBoxAdapter(List<CheckBox> boxs, Context mContext) {
		this.boxs = boxs;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return boxs.size();
	}

	@Override
	public Object getItem(int location) {
		return boxs.get(location);
	}

	@Override
	public long getItemId(int location) {
		return location;
	}

	@Override
	public View getView(int location, View arg1, ViewGroup arg2) {
		final CheckBox box = boxs.get(location);
		box.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				System.out.println("checkedChange");
				if (isChecked) {
					selectedBoxs.add(box);
					if (selectedBoxs.size() > 3) {
						box.setChecked(false);
						selectedBoxs.remove(box);
						Toast.makeText(mContext, "最多选择三项！", Toast.LENGTH_SHORT)
								.show();
					}

				} else {
					selectedBoxs.remove(box);
				}
			}
		});
		return box;
	}

	public void setValue(String value) {
		for (int i = 0; i < boxs.size(); i++) {
			CheckBox box = boxs.get(i);

			if (value.trim().equals(box.getTag().toString().trim())) {
				box.setChecked(true);
			}
		}
		notifyDataSetChanged();

	}

}

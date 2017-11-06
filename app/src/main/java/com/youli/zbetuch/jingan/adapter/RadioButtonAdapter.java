package com.youli.zbetuch.jingan.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;

public class RadioButtonAdapter extends BaseAdapter {

	private List<RadioButton> boxs;
	private Context mContext;
	private RadioButton selectedButton;

	public RadioButton getSelectedButton() {
		return selectedButton;
	}

	public RadioButtonAdapter(List<RadioButton> boxs, Context mContext) {
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
		final RadioButton button = boxs.get(location);
		button.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					setAllNotSelected();
					selectedButton = button;
					buttonView.setChecked(true);
				}
			}
		});
		return button;
	}

	private void setAllNotSelected() {
		for (int i = 0; i < boxs.size(); i++) {
			RadioButton button = boxs.get(i);
			button.setChecked(false);
		}
	}

	public void setValue(String value) {
		for (int i = 0; i < boxs.size(); i++) {
			RadioButton box = boxs.get(i);
			if (value.trim().equals(box.getTag().toString().trim())) {
				box.setChecked(true);
				selectedButton = box;
			} else {
				box.setChecked(false);
			}
		}
		notifyDataSetChanged();
	}

}

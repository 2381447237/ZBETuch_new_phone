package com.youli.zbetuch.jingan.naire;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.activity.BaseActivity;


public class ShowPersionHistoryActivity extends BaseActivity implements IActivity {

	private ListView history_listview;
	private List<HistoryInfo> historyInfos = new ArrayList<HistoryInfo>();
	private HistoryInfoAdapter historyInfoAdapter;
	private TextView lblNum;

	private String sfz;

	public static final int REFRESH_INFO = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_list);
		
		init();
		initView();
		initListener();
		sfz = getIntent().getStringExtra("sfz");

		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("sfz", sfz);
		params.put("data", data);
		PersonTask task = new PersonTask(PersonTask.ACTIVITY_HISTORY_LIST,
				params);
		PersonService.newTask(task);

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		PersonService.addActivity(this);
	}

	private void initView() {
		history_listview = (ListView) this.findViewById(R.id.pull_down_view);
		historyInfoAdapter = new HistoryInfoAdapter(this, historyInfos);
		history_listview.setAdapter(historyInfoAdapter);
		lblNum = (TextView) this.findViewById(R.id.lblNum);
	}

	private void initListener() {
		history_listview.setOnItemClickListener(new MyItemOnClickListener());
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.parseInt(params[0].toString().trim())) {
		case REFRESH_INFO:
			if (!"".equals(params[1].toString().trim())
					&& null != params[1].toString().trim()) {
				List<HistoryInfo> temphistoryInfos = getHistoryInfo(params[1]
						.toString().trim());
				if (temphistoryInfos.size() > 0) {
					historyInfos.addAll(temphistoryInfos);
				}
				historyInfoAdapter.notifyDataSetChanged();
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	private class MyItemOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
			if ("".equals(historyInfos.get(arg2).getMARK())) {
				
				if(historyInfos.get(arg2).isIsJYStatus()){
					
					Intent intent = new Intent(ShowPersionHistoryActivity.this, ShowPersionHistoryList.class);
					intent.putExtra("info",
							(Serializable) historyInfos.get(arg2));
					intent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("QUESTIONMASTERID", 0));
					
					intent.putExtra("myStatus",historyInfos.get(arg2).isIsJYStatus());
					startActivity(intent);
				}else{
					
					Intent showIntent = new Intent(ShowPersionHistoryActivity.this,
							WenJuanDetailActivity.class);
					showIntent.setAction("historyList");
					showIntent.putExtra("answerId", historyInfos.get(arg2).getID());
					showIntent.putExtra("pid", historyInfos.get(arg2)
							.getQA_MASTER());
					showIntent.putExtra("NO", historyInfos.get(arg2).getNO());
					showIntent.putExtra("lishiName", historyInfos.get(arg2).getNAME());
					showIntent.putExtra("rb", false);
					startActivity(showIntent);		
				}
				
				
				
				
			} else {
				Builder builder = new Builder(
						ShowPersionHistoryActivity.this);
				builder.setTitle("无法调查原因").setMessage(
						historyInfos.get(arg2).getMARK());
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						}).create().show();
			}
		}

	}

	private List<HistoryInfo> getHistoryInfo(String str) {
		List<HistoryInfo> newHistoryInfo = new ArrayList<HistoryInfo>();
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				HistoryInfo historyInfo = new HistoryInfo();
				historyInfo.setID(jsonObject.getInt("ID"));
				historyInfo.setNO(jsonObject.getString("NO"));
				historyInfo.setPID(jsonObject.getString("PID"));
				historyInfo.setNAME(jsonObject.getString("NAME"));
				historyInfo.setSFZ(jsonObject.getString("SFZ"));
				historyInfo.setSEX(jsonObject.getString("SEX"));
				historyInfo.setEDU(jsonObject.getString("EDU"));
				historyInfo.setZT(jsonObject.getString("ZT"));
				historyInfo.setDZ(jsonObject.getString("DZ"));
				historyInfo.setQX(jsonObject.getString("QX"));
				historyInfo.setJD(jsonObject.getString("JD"));
				historyInfo.setJW(jsonObject.getString("JW"));
				historyInfo.setLXDZ(jsonObject.getString("LXDZ"));
				historyInfo.setPHONE(jsonObject.getString("PHONE"));
				historyInfo.setQA_MASTER(jsonObject.getInt("QA_MASTER"));
				historyInfo.setCREATE_DATE(jsonObject.getString("CREATE_DATE"));
				historyInfo.setCREATE_STAFF(jsonObject.getInt("CREATE_STAFF"));
				historyInfo.setRECEIVED(jsonObject.getInt("RECEIVED"));
				historyInfo.setMARK(jsonObject.getString("MARK"));
				historyInfo.setRECEIVED_STAFF(jsonObject
						.getInt("RECEIVED_STAFF"));
				historyInfo.setRECEIVED_TIME(jsonObject
						.getString("RECEIVED_TIME"));
				historyInfo.setRecordCount(jsonObject.getInt("RecordCount"));
				historyInfo.setIsCreate(jsonObject.getBoolean("IsCreate"));
				historyInfo.setIsDelete(jsonObject.getBoolean("IsDelete"));
				historyInfo.setIsJYStatus(jsonObject.getBoolean("IsJYStatus"));
				newHistoryInfo.add(historyInfo);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newHistoryInfo;
	}

}

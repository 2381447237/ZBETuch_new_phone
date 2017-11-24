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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.activity.BaseActivity;


public class WenJuanPersonActivity extends BaseActivity implements IActivity{
	private PullToRefreshListView personquery_listview;
	//private PullDownView mPullDownView;
	private List<WenJuanPersonInfo> wenJuanPersonInfos = new ArrayList<WenJuanPersonInfo>();
	private WenJuanPersonAdapter personAdapter;
	private TextView lblNum;

	private int index = 0;

	private RadioGroup rg;
	private RadioButton rb1, rb2;

	private Map<String, String> data;

	public static final int REFRESH_INFO = 0;

	public static final int REFRESH_PERSION_INFO = 1;

	private ProgressDialog dialog;
	
	public static int wenJuanPersonPosition;

	public static boolean isWeichaRg=true;
	private TextView jtcyslTv;
	public boolean myISJYSTATUS;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_wenjuan_person);
		
		myISJYSTATUS=getIntent().getBooleanExtra("ISJYSTATUS", false);
		init();
		initviews();

		
		rb1.setChecked(true);
	}


	
	private void initviews() {
		jtcyslTv=(TextView) findViewById(R.id.jtcysl_tv);
		if(getIntent().getBooleanExtra("ISJYSTATUS", false)){
			jtcyslTv.setVisibility(View.VISIBLE);
		}else{
			jtcyslTv.setVisibility(View.GONE);
		}
		personquery_listview = (PullToRefreshListView) findViewById(R.id.pull_down_view);
		personquery_listview.setMode(PullToRefreshBase.Mode.BOTH);

		personquery_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				index=0;
				getPageInfo(index);

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

				index++;
				getPageInfo(index);

			}
		});
		personAdapter = new WenJuanPersonAdapter(wenJuanPersonInfos, this);
		personquery_listview.setAdapter(personAdapter);

		personquery_listview
				.setOnItemClickListener(new MyOnItemClickListener());
		lblNum = (TextView) findViewById(R.id.lblNum);

		rg = (RadioGroup) this.findViewById(R.id.my_group);
		rb1 = (RadioButton) this.findViewById(R.id.btn_weicha_rg);
		rb2 = (RadioButton) this.findViewById(R.id.btn_yicha_rg);
		rg.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
	}



	@Override
	public void init() {
		// TODO Auto-generated method stub
		PersonService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.parseInt(params[0].toString().trim())) {
		case REFRESH_INFO:
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}

			Log.e("2017/10/24","问卷调查人=="+params[1].toString().trim());

			if (!"".equals(params[1].toString().trim())
					|| null != params[1].toString().trim()) {
				List<WenJuanPersonInfo> newJuanPersonInfos = parseWenjuanPersonInfo(params[1]
						.toString().trim());
				
				
				if (newJuanPersonInfos.size() >0||newJuanPersonInfos.size()==0) {
					if(index==0) {
						wenJuanPersonInfos.clear();
					}
					wenJuanPersonInfos.addAll(newJuanPersonInfos);
					if(newJuanPersonInfos.size()==0){
						lblNum.setText("共有"+wenJuanPersonInfos.size()+"人");
					}else{
					lblNum.setText("共有"
							+ newJuanPersonInfos.get(0).getRecordCount() + "人");
					}

			}

				personAdapter.notifyDataSetChanged();

			}

			if (personquery_listview.isRefreshing()) {
				personquery_listview.onRefreshComplete();
			}
			break;

		case REFRESH_PERSION_INFO:
			
			if ("True".equals(params[1].toString().trim())) {
				Toast.makeText(WenJuanPersonActivity.this, "提交完成!", Toast.LENGTH_LONG).show();
				int potion = (Integer) params[2];
				if (wenJuanPersonInfos.size() > 0) {
					if(potion >-1){
					wenJuanPersonInfos.remove(potion - 1);
					}
					
				}
				personAdapter.notifyDataSetChanged();

			}

			if (personquery_listview.isRefreshing()) {
				personquery_listview.onRefreshComplete();
			}
			break;

		default:
			break;
		}
	}



	private void getPageInfo(int index) {
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("master_id", getIntent().getIntExtra("id", 1) + "");
		data.put("page", index + "");
		data.put("rows", "15");
		params.put("data", data);
		PersonTask task = new PersonTask(
				PersonTask.WENJUANACTIVITY_GET_WENJUANPERSON, params);
		PersonService.newTask(task);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	private class MyOnItemClickListener implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
				long arg3) {
			// TODO Auto-generated method stub

			wenJuanPersonPosition=arg2;


			if (rb1.isChecked()) {
				isWeichaRg=true;
				Intent intent = new Intent(WenJuanPersonActivity.this, ShowPersionDetailInfo.class);
				intent.putExtra("info",
						(Serializable) wenJuanPersonInfos.get(arg2 - 1));
				intent.putExtra("rb", true);
				intent.putExtra("position", arg2);
				intent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("id", 1));
				intent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
				startActivity(intent);
			} else if (rb2.isChecked()) {
				isWeichaRg=false;
				if(getIntent().getBooleanExtra("ISJYSTATUS", false)){

					if ("".equals(wenJuanPersonInfos.get(arg2 - 1).getMARK())) {
						Intent showIntent = new Intent(WenJuanPersonActivity.this,
								ShowPersionDetailInfo.class);

						showIntent.putExtra("info",
								(Serializable) wenJuanPersonInfos.get(arg2 - 1));
						showIntent.putExtra("pid", wenJuanPersonInfos.get(arg2 - 1)
								.getID());
						showIntent.putExtra("NO", wenJuanPersonInfos.get(arg2 - 1)
								.getNO());
						showIntent.putExtra("wname", wenJuanPersonInfos.get(arg2 - 1)
								.getNAME());
						showIntent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("id", 1));
						showIntent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
						if (rb1.isChecked()) {
							showIntent.putExtra("rb", true);
						} else if (rb2.isChecked()) {
							showIntent.putExtra("rb", false);
						}
						startActivity(showIntent);
					} else {
						AlertDialog.Builder builder = new AlertDialog.Builder(WenJuanPersonActivity.this);
						builder.setTitle("无法调查原因").setMessage(
								wenJuanPersonInfos.get(arg2 - 1).getMARK());
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

				}else{




				if ("".equals(wenJuanPersonInfos.get(arg2 - 1).getMARK())) {

					Intent showIntent = new Intent(WenJuanPersonActivity.this,
							WenJuanDetailActivity.class);
					showIntent.putExtra("info",
							(Serializable) wenJuanPersonInfos.get(arg2 - 1));
					showIntent.putExtra("pid", wenJuanPersonInfos.get(arg2 - 1)
							.getID());
					showIntent.putExtra("NO", wenJuanPersonInfos.get(arg2 - 1)
							.getNO());
					showIntent.putExtra("wname", wenJuanPersonInfos.get(arg2 - 1)
							.getNAME());
					showIntent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("id", 1));
					showIntent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
					if (rb1.isChecked()) {
						showIntent.putExtra("rb", true);
					} else if (rb2.isChecked()) {
						showIntent.putExtra("rb", false);
					}
					startActivity(showIntent);
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(WenJuanPersonActivity.this);
					builder.setTitle("无法调查原因").setMessage(
							wenJuanPersonInfos.get(arg2 - 1).getMARK());
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

		}

	}

	private List<WenJuanPersonInfo> parseWenjuanPersonInfo(String str) {
		List<WenJuanPersonInfo> juanPersonInfos = new ArrayList<WenJuanPersonInfo>();

		if(!TextUtils.isEmpty(str)){
		try {
			
			JSONArray array = new JSONArray(str);
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = (JSONObject) array.get(i);
				WenJuanPersonInfo personInfo = new WenJuanPersonInfo();
				personInfo.setCREATE_DATE(jsonObject.getString("CREATE_DATE"));
				personInfo.setCREATE_STAFF(jsonObject.getInt("CREATE_STAFF"));
				personInfo.setDZ(jsonObject.getString("DZ"));
				personInfo.setEDU(jsonObject.getString("EDU"));
				personInfo.setID(jsonObject.getInt("ID"));
				personInfo.setJD(jsonObject.getString("JD"));
				personInfo.setJW(jsonObject.getString("JW"));
				personInfo.setLXDZ(jsonObject.getString("LXDZ"));
				personInfo.setMARK(jsonObject.getString("MARK"));
				personInfo.setNAME(jsonObject.getString("NAME"));
				personInfo.setNO(jsonObject.getString("NO"));
				personInfo.setPHONE(jsonObject.getString("PHONE"));
				personInfo.setPID(jsonObject.getString("PID"));
				personInfo.setQA_MASTER(jsonObject.getInt("QA_MASTER"));
				personInfo.setQX(jsonObject.getString("QX"));
				personInfo.setRECEIVED_ID(jsonObject.getInt("RECEIVED"));
				personInfo.setRecordCount(jsonObject.getInt("RecordCount"));
				personInfo.setSEX(jsonObject.getString("SEX"));
				personInfo.setSFZ(jsonObject.getString("SFZ"));
				personInfo.setZT(jsonObject.getString("ZT"));
				personInfo.setJTCYSL(jsonObject.getInt("conut"));
				juanPersonInfos.add(personInfo);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		return juanPersonInfos;
	}

	private class MyOnCheckedChangeListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			// TODO Auto-generated method stub
			index = 0;
			wenJuanPersonInfos.clear();
			personAdapter.notifyDataSetChanged();

			lblNum.setText(" ");
			showDialog();
			if (arg1 == R.id.btn_weicha_rg) {
				data = new HashMap<String, String>();
				data.put("type", "1");
			} else if (arg1 == R.id.btn_yicha_rg) {
				data = new HashMap<String, String>();
				data.put("type", "0");
			}
			getPageInfo(index);
		}

	}

	private void showDialog() {
		dialog = new ProgressDialog(WenJuanPersonActivity.this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("数据信息加载中...");
		dialog.show();
	}

}

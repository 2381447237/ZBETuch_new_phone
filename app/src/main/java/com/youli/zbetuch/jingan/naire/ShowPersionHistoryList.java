package com.youli.zbetuch.jingan.naire;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.activity.BaseActivity;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowPersionHistoryList extends BaseActivity {

	private TextView qx_tv, pid_tv, number_tv, name_tv, sex_tv, sfz_tv, edu_tv,
zt_tv, jd_tv, jw_tv, lxdz_tv, phone, dzszqx_tv,sphl_tv_name;
	public HistoryInfo info;
	private LinearLayout lv_title;
	private ListView lv;
	private HistoryListAdapter adapter; 
	private List<FamilyInfo> listInfo=new ArrayList<FamilyInfo>();
	private ProgressDialog dialog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showpersionhistorylist);
		
		info = (HistoryInfo) getIntent().getSerializableExtra("info");
		
		qx_tv = (TextView) this.findViewById(R.id.qx_sphl);
		pid_tv = (TextView) this.findViewById(R.id.pid_sphl);
		number_tv = (TextView) this.findViewById(R.id.id_sphl);
		name_tv = (TextView) this.findViewById(R.id.name_sphl);
		sex_tv = (TextView) this.findViewById(R.id.sex_sphl);
		edu_tv = (TextView) this.findViewById(R.id.edu_sphl);
		sfz_tv = (TextView) this.findViewById(R.id.sfz_sphl);
		jd_tv = (TextView) this.findViewById(R.id.jd_sphl);
		jw_tv = (TextView) this.findViewById(R.id.jw_sphl);
		lxdz_tv = (TextView) this.findViewById(R.id.lxdz_sphl);
		phone = (TextView) this.findViewById(R.id.lxdh_sphl);
		zt_tv = (TextView) this.findViewById(R.id.status_sphl);
		dzszqx_tv = (TextView) this.findViewById(R.id.hjqx_sphl);
		
		lv_title=(LinearLayout) findViewById(R.id.sphl_ll_title);
		sphl_tv_name=(TextView) findViewById(R.id.sphl_tv_name);
		
		if(info.getQA_MASTER()==5){
			sphl_tv_name.setVisibility(View.VISIBLE);
		}else if(info.getQA_MASTER()==6){
			sphl_tv_name.setVisibility(View.GONE);
		}
		
		lv=(ListView) findViewById(R.id.sphl_lv);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				toWenJuanDetail(position);
			}
		});
		if(info!=null){
			qx_tv.setText(info.getQX());
			pid_tv.setText(info.getPID());
			number_tv.setText(info.getNO());
			name_tv.setText(info.getNAME());
			sex_tv.setText(info.getSEX());
			sfz_tv.setText(info.getSFZ());
			edu_tv.setText(info.getEDU());
			jd_tv.setText(info.getJD());
			jw_tv.setText(info.getJW());
			lxdz_tv.setText(info.getLXDZ());
			phone.setText(info.getPHONE());
			zt_tv.setText(info.getZT());
			dzszqx_tv.setText(info.getQX());
		}
		
		getFamilyList();
	}
	
	private void showDialog() {
		dialog = new ProgressDialog(ShowPersionHistoryList.this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("数据信息加载中...");
		dialog.show();
	}
	
	private void getFamilyList(){

		showDialog();
		// http://192.168.11.11:89/Json/Get_Home.aspx?TYPE=1&QA_MASTER=5&SFZ=310108197604155814
		OkHttpUtils.post().url(MyOkHttpUtils.BaseUrl+ShowPersionDetailInfo.familyListUrl).addParams("TYPE","1").addParams("SFZ",info.getSFZ()).addParams("QA_MASTER",info.getQA_MASTER()+"").build().execute(new StringCallback() {

			@Override
			public void onError(Call call, Exception e) {

				dialog.dismiss();
				Toast.makeText(ShowPersionHistoryList.this,"请连接网络",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onResponse(final String infoStr) {

						
				if(TextUtils.equals(infoStr,"[]")){
					lv_title.setVisibility(View.GONE);
		        	lv.setVisibility(View.GONE);
		        	
		        	 dialog.dismiss();
		        	
					return;
				}else{
					lv_title.setVisibility(View.VISIBLE);
		        	lv.setVisibility(View.VISIBLE);
				}
				
				runOnUiThread(new Runnable() {
					public void run() {
						
						Gson gson=new Gson();
						Type listType=new TypeToken<LinkedList<FamilyInfo>>(){}.getType();
						
						LinkedList<FamilyInfo> fi=gson.fromJson(infoStr,listType);
						
						listInfo.clear();
						
						for (Iterator iterator = fi.iterator(); iterator
								.hasNext();) {

							FamilyInfo content = (FamilyInfo) iterator
									.next();

							listInfo.add(content);
							
						}

						if(adapter==null){
							adapter=new HistoryListAdapter(listInfo, ShowPersionHistoryList.this);
							lv.setAdapter(adapter);					
						}else{
							adapter.notifyDataSetChanged();
						}
						
				        setListViewHeightBasedOnChildren(lv); 
				        dialog.dismiss();
					}
				});
				
			}
			

		});
		
	}
	public void setListViewHeightBasedOnChildren(ListView listView) {   
        // 获取ListView对应的Adapter   
        ListAdapter listAdapter = listView.getAdapter();   
        if (listAdapter == null) {   
            return;   
        }   
   
        int totalHeight = 0;   
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
            // listAdapter.getCount()返回数据项的数目   
            View listItem = listAdapter.getView(i, null, listView);   
            // 计算子项View 的宽高   
            listItem.measure(0, 0);    
            // 统计所有子项的总高度   
            totalHeight += listItem.getMeasuredHeight();    
        }   
   
        ViewGroup.LayoutParams params = listView.getLayoutParams();   
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
        // listView.getDividerHeight()获取子项间分隔符占用的高度   
        // params.height最后得到整个ListView完整显示需要的高度   
        listView.setLayoutParams(params);   
    }
	private void toWenJuanDetail(final int position){
		
		//	http://192.168.11.11:89/Json/Get_Tb_Home_Answer_Info.aspx?HOMEID=241
			OkHttpUtils.post().url(MyOkHttpUtils.BaseUrl+ShowPersionDetailInfo.answerUrl).addParams("HOMEID", listInfo.get(position).getID()+"").build().execute(new StringCallback() {
				
				@Override
				public void onResponse(String str) {
					
					Intent intent=new Intent(ShowPersionHistoryList.this,WenJuanDetailActivity.class);
					if(TextUtils.equals(str, "false")){
						intent.putExtra("rb", true);
						
						Toast.makeText(ShowPersionHistoryList.this,"该人员未答题，请先答题",Toast.LENGTH_SHORT).show();
						
						return;
						
					}else{
						intent.putExtra("rb", false);
					}
					//intent.putExtra("info", info);
					intent.putExtra("pid", info.getID());
					intent.putExtra("NO", info.getNO());
					
					
					intent.setAction("historyList");
					intent.putExtra("sname", listInfo.get(position).getSQR());
					intent.putExtra("position", position);
					intent.putExtra("QUESTIONMASTERID",listInfo.get(position).getQUESTIONMASTERID());
					intent.putExtra("myHOMEID", listInfo.get(position).getID());
					intent.putExtra("myStatus",getIntent().getBooleanExtra("myStatus",false));
			 	MainTools.map.put("lishiwenjuaninfo",
							(WenJuanType) ShowWenJuanActivity.lishiJuanTypes.get(listInfo.get(position).getQUESTIONMASTERID()-1));
					startActivity(intent);
				}
				
				@Override
				public void onError(Call arg0, Exception arg1) {
					Toast.makeText(ShowPersionHistoryList.this,"请连接网络",Toast.LENGTH_SHORT).show();
				}
			});
				
		}
}

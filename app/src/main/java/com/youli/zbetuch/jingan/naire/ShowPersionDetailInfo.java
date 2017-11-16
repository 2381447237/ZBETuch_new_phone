package com.youli.zbetuch.jingan.naire;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.activity.PersonInfoActivity;
import com.youli.zbetuch.jingan.activity.ZiyuanDetailListActivity;
import com.youli.zbetuch.jingan.entity.PersonInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowPersionDetailInfo extends Activity implements OnClickListener {

	private Context mContext=this;
	private final int  PERSONINFO=10001;
	private final int  NOPERSONINFO=10002;
	private final int  PROBLEM=10003;
	private List<PersonInfo> personInfos=new ArrayList<>();
	private Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what){



				case PROBLEM:

					Toast.makeText(mContext,"提交失败",Toast.LENGTH_SHORT).show();

					break;


				case NOPERSONINFO:

					Toast.makeText(mContext,"对不起,查无此人",Toast.LENGTH_SHORT).show();

					break;

				case PERSONINFO:

					personInfos=(List<PersonInfo>)msg.obj;

					Intent intent=new Intent(mContext,PersonInfoActivity.class);
					intent.putExtra("personInfos", (Serializable) personInfos.get(0));
					startActivity(intent);

					break;
			}

		}
	};

	private TextView qx_tv, pid_tv, number_tv, name_tv, sex_tv, sfz_tv, edu_tv,
			zt_tv, jd_tv, jw_tv, lxdz_tv, phone, dzszqx_tv,title_name_tv;
	public Button disscus, no_disscus,population,family;
	private Button persion_info, history_list;
	public LinearLayout lv_title;
	public WenJuanPersonInfo info;
	private boolean rb;
	private int position;

	public ListView lv;
	private WenJuanPersonListAdapter adapter;
	private List<FamilyInfo> listInfo=new ArrayList<FamilyInfo>();
	public static String  familyListUrl="/Json/Get_Home.aspx";
	public static String answerUrl="/Json/Get_Tb_Home_Answer_Info.aspx";
	private ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_wenjuan_persion_info);
		info = (WenJuanPersonInfo) getIntent().getSerializableExtra("info");
		
		rb = getIntent().getBooleanExtra("rb", true);
		position = getIntent().getIntExtra("position", 0);
		initView();
		initValue();
		
		if(getIntent().getBooleanExtra("ISJYSTATUS", false)){
			getFamilyList();
			
		}else{
			lv_title.setVisibility(View.GONE);
        	lv.setVisibility(View.GONE);
        	disscus.setText("点击调查");
		}
		
	}

	private void showDialog() {
		dialog = new ProgressDialog(ShowPersionDetailInfo.this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("数据信息加载中...");
		dialog.show();



	}
	
	private void getFamilyList(){
		showDialog();
		// http://192.168.11.11:89/Json/Get_Home.aspx?TYPE=1&QA_MASTER=5&SFZ=310108196301092815
		OkHttpUtils.post().url(MyOkHttpUtils.BaseUrl+familyListUrl).addParams("TYPE","1").addParams("SFZ",info.getSFZ()).addParams("QA_MASTER",getIntent().getIntExtra("QUESTIONMASTERID", 0)+"").build().execute(new StringCallback() {
			
			@Override
			public void onResponse(final String infoStr) {

				if(TextUtils.equals(infoStr,"[]")){
					no_disscus.setEnabled(true);
					lv_title.setVisibility(View.GONE);
		        	lv.setVisibility(View.GONE);
		        	 dialog.dismiss();
		        	 disscus.setText("点击调查");
					return;
				}else{
					disscus.setText("点击调查");
					no_disscus.setEnabled(false);
					lv_title.setVisibility(View.VISIBLE);
		        	lv.setVisibility(View.VISIBLE);
				}
				
				runOnUiThread(new Runnable() {
					public void run() {
						
						Gson gson=new Gson();
						Type listType=new TypeToken<LinkedList<FamilyInfo>>(){}.getType();

						try{
							LinkedList<FamilyInfo> fi=gson.fromJson(infoStr,listType);



						
						listInfo.clear();
						
						for (Iterator iterator = fi.iterator(); iterator
								.hasNext();) {

							FamilyInfo content = (FamilyInfo) iterator
									.next();

							listInfo.add(content);
							
						}
						}catch (Exception e){

						}
						if(adapter==null){
							adapter=new WenJuanPersonListAdapter(listInfo, ShowPersionDetailInfo.this);
							lv.setAdapter(adapter);					
						}else{
							adapter.notifyDataSetChanged();
						}

				        setListViewHeightBasedOnChildren(lv); 
				        dialog.dismiss();
				        
				        setButtonText();
				        
					}
				});
				
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {

				dialog.dismiss();
				Toast.makeText(ShowPersionDetailInfo.this,"请连接网络",Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
	
	private void setButtonText(){
		
OkHttpUtils.post().url(MyOkHttpUtils.BaseUrl+answerUrl).addParams("HOMEID", listInfo.get(0).getID()+"").build().execute(new StringCallback() {
			
			@Override
			public void onResponse(String str) {
				
				if(TextUtils.equals(str, "false")){
					disscus.setText("点击调查");
					return;
				}else{
					disscus.setText("新增成员调查");
					
				}
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {

				Toast.makeText(ShowPersionDetailInfo.this,"请连接网络",Toast.LENGTH_SHORT).show();
			}
		});
		
		
	}
	
	private void initView() {
		qx_tv = (TextView) this.findViewById(R.id.qx);
		pid_tv = (TextView) this.findViewById(R.id.pid);
		number_tv = (TextView) this.findViewById(R.id.id);
		name_tv = (TextView) this.findViewById(R.id.name);
		sex_tv = (TextView) this.findViewById(R.id.sex);
		edu_tv = (TextView) this.findViewById(R.id.edu);
		sfz_tv = (TextView) this.findViewById(R.id.sfz);
		jd_tv = (TextView) this.findViewById(R.id.jd);
		jw_tv = (TextView) this.findViewById(R.id.jw);
		lxdz_tv = (TextView) this.findViewById(R.id.lxdz);
		phone = (TextView) this.findViewById(R.id.lxdh);
		zt_tv = (TextView) this.findViewById(R.id.status);
		dzszqx_tv = (TextView) this.findViewById(R.id.hjqx);
		lv_title=(LinearLayout) findViewById(R.id.awpi_ll_title);
		title_name_tv=(TextView) findViewById(R.id.awpi_tv_name);
		
		if(getIntent().getIntExtra("QUESTIONMASTERID", 0)==5){
			title_name_tv.setVisibility(View.VISIBLE);
		}else{
			title_name_tv.setVisibility(View.GONE);
		}
		
		lv=(ListView) findViewById(R.id.awpi_lv); 
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
			
				toWenJuanDetail(position);
				
				
			}
		});
		disscus = (Button) this.findViewById(R.id.disscus);
		no_disscus = (Button) this.findViewById(R.id.no_disscus);
		population=(Button) findViewById(R.id.population);
		family=(Button) findViewById(R.id.family);
		
		if(getIntent().getBooleanExtra("ISJYSTATUS", false)){
			population.setVisibility(View.VISIBLE);
			family.setVisibility(View.GONE);
			if(rb){
				
				disscus.setVisibility(View.VISIBLE);
				family.setVisibility(View.GONE);
			}else{
				family.setVisibility(View.GONE);
			}
			
		}else{
			population.setVisibility(View.GONE);
			family.setVisibility(View.GONE);
			
                  if(rb){
				disscus.setVisibility(View.VISIBLE);
			}else{		

			}
		}
		
		if(WenJuanPersonActivity.isWeichaRg){
			no_disscus.setVisibility(View.VISIBLE);
		}else{
			no_disscus.setVisibility(View.GONE);
		}
		persion_info = (Button) this.findViewById(R.id.persion_info);
		history_list = (Button) this.findViewById(R.id.history_list);

		persion_info.setOnClickListener(this);
		history_list.setOnClickListener(this);
		disscus.setOnClickListener(this);
		no_disscus.setOnClickListener(this);
		population.setOnClickListener(this);
		family.setOnClickListener(this);
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
	
	private void initValue() {
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
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.disscus:

			if(getIntent().getBooleanExtra("ISJYSTATUS", false)){
				
				if(listInfo.size()>0){
					
				Intent showIntent = new Intent(ShowPersionDetailInfo.this,
						WenJuanDetailActivity.class);
				showIntent.putExtra("info", info);
				showIntent.putExtra("pid", info.getID());
				showIntent.putExtra("NO", info.getNO());
				if(listInfo.get(0).getZT()!=1){
					
					showIntent.putExtra("sname", listInfo.get(0).getSQR());
					showIntent.putExtra("myHOMEID", listInfo.get(0).getID());
				}else{
					
					showIntent.putExtra("familyInfo",(Serializable)listInfo.get(0));
				}
				showIntent.putExtra("position", position);
			    showIntent.putExtra("zt", listInfo.get(0).getZT());
				showIntent.putExtra("rb", true);
				showIntent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("QUESTIONMASTERID", 0));
				showIntent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
				startActivityForResult(showIntent,111111);
				}else{
					
					Intent showIntent = new Intent(ShowPersionDetailInfo.this,
							WenJuanDetailActivity.class);
					showIntent.putExtra("info", info);
					showIntent.putExtra("pid", info.getID());
					showIntent.putExtra("NO", info.getNO());
					showIntent.putExtra("sname", info.getNAME());
					showIntent.putExtra("position", position);
					showIntent.putExtra("rb", true);
					showIntent.putExtra("zt", 1);
					showIntent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("QUESTIONMASTERID", 0));
					showIntent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
					startActivityForResult(showIntent,111111);
					
				}
			}else{
				Intent showIntent = new Intent(ShowPersionDetailInfo.this,
						WenJuanDetailActivity.class);
				showIntent.putExtra("info", info);
				showIntent.putExtra("pid", info.getID());
				showIntent.putExtra("NO", info.getNO());
				showIntent.putExtra("sname", info.getNAME());
				showIntent.putExtra("position", position);
				showIntent.putExtra("rb", rb);
				showIntent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("QUESTIONMASTERID", 0));
				showIntent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
				startActivity(showIntent);
			}

			break;

		case R.id.no_disscus:

				Intent intent = new Intent(ShowPersionDetailInfo.this,
						ShowWenJuanMarkActivity.class);
				intent.putExtra("pid", String.valueOf(info.getID()));
				intent.putExtra("position", position);
				startActivity(intent);
			break;

		case R.id.persion_info:

			getPersonInfo();

//			Intent itemIntent = new Intent(ShowPersionDetailInfo.this,
//					PersoninfoMainActivity.class);
//			itemIntent.putExtra("mysfz", info.getSFZ());
//			startActivity(itemIntent);
			break;

		case R.id.history_list:
			Intent historyIntent = new Intent(ShowPersionDetailInfo.this,
					ShowPersionHistoryActivity.class);
			historyIntent.putExtra("sfz", info.getSFZ());
			historyIntent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("QUESTIONMASTERID", 0));
			
			startActivity(historyIntent);
			break;
			
		case R.id.population://登记人数
			
			Intent showIntent = new Intent(ShowPersionDetailInfo.this,
					WenJuanRegisterInfo.class);
			showIntent.putExtra("info", info);
			showIntent.putExtra("pid", info.getID());
			showIntent.putExtra("NO", info.getNO());
			showIntent.putExtra("sname", info.getNAME());
			showIntent.putExtra("position", position);
			showIntent.putExtra("rb", rb);
			showIntent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("QUESTIONMASTERID", 0));
			showIntent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
			if(listInfo.size()>0){
			showIntent.putExtra("familyList", listInfo.get(0));
			}
			startActivityForResult(showIntent,111111);
			
			break;
			
case R.id.family://家庭成员调查
			
	if(getIntent().getBooleanExtra("ISJYSTATUS", false)){
		
		if(listInfo.size()>0){
			
		Intent familyIntent = new Intent(ShowPersionDetailInfo.this,
				WenJuanDetailActivity.class);
		familyIntent.putExtra("info", info);
		familyIntent.putExtra("pid", info.getID());
		familyIntent.putExtra("NO", info.getNO());
		if(listInfo.get(0).getZT()!=1){
			
			familyIntent.putExtra("sname", listInfo.get(0).getSQR());
			familyIntent.putExtra("myHOMEID", listInfo.get(0).getID());
		}else{
			
			familyIntent.putExtra("familyInfo",(Serializable)listInfo.get(0));
		}
		familyIntent.putExtra("position", position);
		familyIntent.putExtra("zt", listInfo.get(0).getZT());
		familyIntent.putExtra("rb", true);
		familyIntent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("QUESTIONMASTERID", 0));
		familyIntent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
		startActivityForResult(familyIntent,111111);
		}else{

			Intent familyIntent = new Intent(ShowPersionDetailInfo.this,
					WenJuanDetailActivity.class);
			familyIntent.putExtra("info", info);
			familyIntent.putExtra("pid", info.getID());
			familyIntent.putExtra("NO", info.getNO());
			familyIntent.putExtra("sname", info.getNAME());
			familyIntent.putExtra("position", position);
			familyIntent.putExtra("rb", true);
			familyIntent.putExtra("zt", 1);
			familyIntent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("QUESTIONMASTERID", 0));
			familyIntent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
			startActivityForResult(familyIntent,111111);
			
		}
	}
			break;	
			
		default:
			break;
		}
		
		if(!getIntent().getBooleanExtra("ISJYSTATUS", false)){
		ShowPersionDetailInfo.this.finish();
		}
	}

	private void toWenJuanDetail(final int position){
		
	//	http://192.168.11.11:89/Json/Get_Tb_Home_Answer_Info.aspx?HOMEID=89	
		OkHttpUtils.post().url(MyOkHttpUtils.BaseUrl+answerUrl).addParams("HOMEID", listInfo.get(position).getID()+"").build().execute(new StringCallback() {
			
			@Override
			public void onResponse(String str) {

				
				Intent intent=new Intent(ShowPersionDetailInfo.this,WenJuanDetailActivity.class);
				if(TextUtils.equals(str, "false")){
					intent.putExtra("rb", true);
					
					Toast.makeText(ShowPersionDetailInfo.this,"请您先答问卷", Toast.LENGTH_SHORT).show();
					return;
				}else{
					intent.putExtra("rb", false);
				}
				intent.putExtra("info", info);
				intent.putExtra("pid", info.getID());
				intent.putExtra("NO", info.getNO());
				intent.putExtra("sname", listInfo.get(position).getSQR());
				intent.putExtra("position", position);
				
				intent.putExtra("QUESTIONMASTERID",getIntent().getIntExtra("QUESTIONMASTERID", 0));
				intent.putExtra("myHOMEID", listInfo.get(position).getID());
				intent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
				startActivity(intent);
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				Toast.makeText(ShowPersionDetailInfo.this,"请连接网络",Toast.LENGTH_SHORT).show();
			}
		});
			
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

			if(getIntent().getBooleanExtra("ISJYSTATUS", false)){
				getFamilyList();

				
			}else{
				lv_title.setVisibility(View.GONE);
	        	lv.setVisibility(View.GONE);
				
			}

		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(getIntent().getBooleanExtra("ISJYSTATUS", false)){
			
		if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
			
			Builder builder=new Builder(ShowPersionDetailInfo.this);
			builder.setTitle("关闭提示").setMessage("您确定要退出调查问卷吗?")
			.setIcon(android.R.drawable.ic_dialog_info)
			.setCancelable(true)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					finish();
				}
			}).setNegativeButton("取消",null).show();
				
		
		
		}
		return false;
		}else{

	return super.onKeyDown(keyCode, event);
		}
	}


	public void getPersonInfo(){
		// http://web.youli.pw:89/Json/Get_BASIC_INFORMATION.aspx?sfz=310101198711030515
		new Thread(

				new Runnable() {
					@Override
					public void run() {

						String url=MyOkHttpUtils.BaseUrl+"/Json/Get_BASIC_INFORMATION.aspx?sfz="+info.getSFZ();

						Response response=MyOkHttpUtils.okHttpGet(url);

						if(response!=null){

							try {
								String strRes=response.body().string();

								Message msg=Message.obtain();
								if(TextUtils.equals(strRes,"[null]")){


									msg.what=NOPERSONINFO;
									mHandler.sendMessage(msg);

								}else{

									Gson gson=new Gson();
									msg.obj=gson.fromJson(strRes,new TypeToken<List<PersonInfo>>(){}.getType());
									msg.what=PERSONINFO;
									mHandler.sendMessage(msg);

								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						}

					}
				}

		).start();

	}
}

package com.youli.zbetuch.jingan.naire;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import com.youli.zbetuch.jingan.utils.SharedPreferencesUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class WenJuanDetailActivity extends Activity implements IActivity {

	private Button btn_star;//开始答题
	private Button btn_upload;//提交
	private Button btn_next_question;//下一题
	private Button btn_up_question;//上一题
	private Button btn_all;//查看全部
	private LinearLayout llDuty;

	private List<WenJuanInfo> juanInfos = new ArrayList<WenJuanInfo>();
	
	private List<WenJuanInfo> answerInfo = new ArrayList<WenJuanInfo>();

	private List<WenJuanInfo> allanswerEditInfo = new ArrayList<WenJuanInfo>();

	// 保存所选的答案和题号
	private List<AnswerInfo> answerInfos2 = new ArrayList<AnswerInfo>();

	private Activity context = this;

	public static final int REFRESH_INFO = 0;

	public static final int REFRESH_INFO_HISTORY_LIST = 1;
	
	public static final int XUHUIREFRESH_INFO = 2;

	private List<RadioButton> radioButtons = new ArrayList<RadioButton>();
	private List<CheckBox> this_CheckBoxs = new ArrayList<CheckBox>();//多选
	// 保存小题的编辑框
	private List<EditText> editTexts = new ArrayList<EditText>();
	// 保存大题的编辑框
	private List<EditText> questionEditTexts = new ArrayList<EditText>();
	private List<TextView> questionTextViews = new ArrayList<TextView>();
	private List<WenJuanInfo> questionInfos;
	private int index = 0;

	private int tempQuestionIndex = 0;// 用于标识上一题的题号

	// 用于保存上一步回答的题目
	private List<Integer> tempList = new ArrayList<Integer>();

	private WenJuanType typeMap;

	private View view;

	private WenJuanInfo currentInfo;// 用于表示当前题

	private boolean rb;

	private List<YiChaWenJuan> allchaWenJuans = new ArrayList<YiChaWenJuan>();
	private IActivity activity = null;
	private String nameStr;
    private int huZhu;//当为-1,0是户主,当为1是家庭成员
	
    private FamilyInfo fInfo;
    private String uploadName;
    private List<FamilyInfo> listInfo=new ArrayList<FamilyInfo>();
   private WenJuanPersonInfo wjPInfo;
   private ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_wenjuan_list);
		rb = getIntent().getBooleanExtra("rb", true);
		
		wjPInfo=(WenJuanPersonInfo) getIntent().getSerializableExtra("info");
		
		huZhu=getIntent().getIntExtra("zt",0);
		
		fInfo=(FamilyInfo) getIntent().getSerializableExtra("familyInfo");
		
		init();
		initView();		
		
		
		if (rb) {
			btn_star.setVisibility(View.VISIBLE);
		} else {
			
			showDialog();
		//	btn_all.setVisibility(View.VISIBLE);
			
//			if("historyList".equals(getIntent().getAction())){
//				if(getIntent().getBooleanExtra("myStatus",false)){
//					typeMap = MainTools.map.get("lishiwenjuaninfo");
//				}
//			}else{
//				typeMap = MainTools.map.get("wenjuaninfo");
//			}
//			
//			checkAnswer();
		}
		
		if ("historyList".equals(getIntent().getAction())) {
			
			if(!getIntent().getBooleanExtra("myStatus",false)){
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, String> data = new HashMap<String, String>();
			data.put("qa_master", getIntent().getIntExtra("pid", 0) + "");
			
			params.put("data", data);
			PersonTask task = new PersonTask(
					PersonTask.WENJUAN_HISTORY_LIST_ACTIVITY_GET_WENJUANINFO,
					params);
			PersonService.newTask(task);
			}else{
				typeMap = MainTools.map.get("lishiwenjuaninfo");
				Map<String, Object> params1 = new HashMap<String, Object>();
				Map<String, String> data1 = new HashMap<String, String>();
				
				data1.put("HOMEID", getIntent().getIntExtra("myHOMEID", 0)+"");
				params1.put("data", data1);
				//xuhui查看已查的数据
				PersonTask task1 = new PersonTask(
						PersonTask.WENJUANLISTACTIVITY_XUHUI_INFO_LIST_ANSWER,
						params1);
				PersonService.newTask(task1);
			}
		} else {
			typeMap = MainTools.map.get("wenjuaninfo");
			addTitle();
			if (!rb) {
				if(!getIntent().getBooleanExtra("ISJYSTATUS", false)){
					Map<String, Object> params = new HashMap<String, Object>();
					Map<String, String> data = new HashMap<String, String>();
					data.put("Personnel_id", getIntent().getIntExtra("pid", 0) + "");
					params.put("data", data);
					//老答案查看已查的数据
					PersonTask task = new PersonTask(
							PersonTask.WENJUANLISTACTIVITY_GET_INFO_LIST_ANSWER,
							params);
					PersonService.newTask(task);
					
				}else{
				Map<String, Object> params1 = new HashMap<String, Object>();
				Map<String, String> data1 = new HashMap<String, String>();
				data1.put("HOMEID", getIntent().getIntExtra("myHOMEID", 0)+"");
				params1.put("data", data1);
				//xuhui查看已查的数据
				PersonTask task1 = new PersonTask(
						PersonTask.WENJUANLISTACTIVITY_XUHUI_INFO_LIST_ANSWER,
						params1);
				PersonService.newTask(task1);
				}
				
			}
		}

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		PersonService.addActivity(this);
	}

	private void initView() {
		llDuty = (LinearLayout) this.findViewById(R.id.llDuty);
		btn_star = (Button) this.findViewById(R.id.btn_star_question);
		btn_upload = (Button) this.findViewById(R.id.btn_up_wenjuan);
		btn_next_question = (Button) this.findViewById(R.id.btn_next_question);
		btn_up_question = (Button) this.findViewById(R.id.btn_up_question);
		btn_all = (Button) this.findViewById(R.id.btn_all);
        //开始答题
		btn_star.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				List<WenJuanInfo> newJuanInfos = typeMap.getJuanInfos();
				if (newJuanInfos.size() > 0) {
					juanInfos.clear();
					radioButtons.clear();
					this_CheckBoxs.clear();
					editTexts.clear();
					questionEditTexts.clear();
					questionTextViews.clear();
					index = 0;
					juanInfos.addAll(newJuanInfos);
				}
				if (juanInfos.size() > 0) {
					//添加问题
					questionInfos = getQuestionByParent();
					if (questionInfos.size() > 0) {
						fretchTree(llDuty, questionInfos.get(index), "");
					}
				}

				btn_next_question.setVisibility(View.VISIBLE);
				btn_up_question.setVisibility(View.VISIBLE);
				btn_all.setVisibility(View.GONE);
				btn_star.setVisibility(View.GONE);
				btn_upload.setVisibility(View.GONE);
			}
		});
        //提交
		btn_upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Builder builder = new Builder(context);
				builder.setTitle("温馨提示")
						.setMessage("确定要提交吗?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										
										if(huZhu==1){
											if(fInfo!=null){
												getFlistInfo();			
											}else{
												newNullInfo();
											}
											return;
										}
										
										getAnswerInfo();
										String answerString;
										if(getIntent().getBooleanExtra("ISJYSTATUS", false)){
											
												answerString = parseAnswerInfo2();
														
											
										}else{
											answerString = parseAnswerInfo();
										}
										//上面两个方法是在拼接要提交的答案
										
										if (answerInfos2.size() > 0) {
											answerInfos2.clear();
										}

										if (questionInfos.size() > 0) {
											llDuty.removeAllViews();
											if (tempList.size() > 0) {
												tempList.clear();
											}
											llDuty.addView(view);
											for (WenJuanInfo answerInfo : questionInfos) {
												fretchTree(llDuty, answerInfo,
														"all");
											}
										}
										btn_star.setVisibility(View.GONE);
										btn_upload.setEnabled(false);


										if(getIntent().getBooleanExtra("ISJYSTATUS", false)){
										Map<String, Object> data = new HashMap<String, Object>();
										data.put("data", answerString);
										//提交答案			
										postJson_WenJuan("/Json/Set_Tb_Home_Answer_Info.aspx",data);
										}else{
											
											Map<String, Object> params = new HashMap<String, Object>();
											Map<String, String> data = new HashMap<String, String>();
											data.put("Personnel_id", getIntent()
													.getIntExtra("pid", 0) + "");
											data.put("mark", "");
											data.put("position", getIntent()
													.getIntExtra("position", 0)
													+ "");
											data.put("data", answerString);
											params.put("data", data);
											//提交答案
											PersonTask task = new PersonTask(
													PersonTask.UPLOADWENJUAN_SET_WENJUAN,
													params);
											PersonService.newTask(task);
											
										}
										WenJuanDetailActivity.this.finish();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
									}
								}).create().show();

			}
		});
        //下一题 
		btn_next_question.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(questionInfos==null){
					
					Toast.makeText(WenJuanDetailActivity.this,"已经是最后一题了", 0).show();
					
					return;
				}
				
				WenJuanInfo info = questionInfos.get(index);

			List<WenJuanInfo> tempSmallWenJuan = getAnswerByParentId(info);

				int questionNo = info.getID();
				if (questionEditTexts.size() > 0) {
					for (EditText editText : questionEditTexts) {
						if (editText.getId() == questionNo
								&& "".equals(editText.getText().toString()
										.trim())) {
							Toast.makeText(context, "答案不能为空!",
									Toast.LENGTH_SHORT).show();
							return;
						}
					}
				}
				if (questionTextViews.size() > 0) {
					for (TextView textView : questionTextViews) {
						if (textView.getId() == questionNo
								&& "请点击选择".equals(textView.getText().toString()
										.trim())) {
							Toast.makeText(context, "答案不能为空!",
									Toast.LENGTH_SHORT).show();
							return;
						}
					}
				}
				if (tempSmallWenJuan.size() > 0
						&& !checkRadioIsChecked(answerInfo, questionNo)) {
					Toast.makeText(context, "请选择合适的答案", Toast.LENGTH_LONG)
							.show();
					return;
				}

				if (makeEdit(answerInfo))
					return;
				if (makeEdit_checkBox(answerInfo))
					return;
				
				if (index >= questionInfos.size() - 1) {
					Toast.makeText(context, "已经是最后一题了", Toast.LENGTH_LONG)
							.show();
					btn_all.setVisibility(View.VISIBLE);
					return;
				}
				List<WenJuanInfo> tempAnswer = new ArrayList<WenJuanInfo>();
				
				List<WenJuanInfo> tempTitleAnswer = new ArrayList<WenJuanInfo>();
				
				for (WenJuanInfo answer : answerInfo) {
					if (answer.getPARENT_ID() == questionNo) {
						tempAnswer.add(answer);
					}
				}
						
				for (WenJuanInfo answer : juanInfos) {
					if (answer.getPARENT_ID() ==0) {
						tempTitleAnswer.add(answer);
					}
				}
				
				tempQuestionIndex = index;
				//tempList.add(tempQuestionIndex);
				String jumpCode = null;
				
				for(EditText editText:questionEditTexts){
					
					if(!TextUtils.equals("", editText.getText().toString())){
						
						for(WenJuanInfo temp:tempTitleAnswer){
						
							if(editText.getId()==temp.getID()){
								
								jumpCode = temp.getJUMP_CODE();
								
							}
						}							
					}
					
				}	
				
				for (RadioButton radioButton : radioButtons) {
					if (radioButton.isChecked()) {
						for (WenJuanInfo temp : tempAnswer) {
							if (radioButton.getId() == temp.getID()) {
								
								if(temp.getJUMP_CODE().contains("-")){
									jumpCode=(temp.getJUMP_CODE().subSequence(temp.getJUMP_CODE().indexOf(0)+1,temp.getJUMP_CODE().indexOf("-")))+"";
								}else if(temp.getJUMP_CODE().contains("—")){
									jumpCode=(temp.getJUMP_CODE().subSequence(temp.getJUMP_CODE().indexOf(0)+1,temp.getJUMP_CODE().indexOf("—")))+"";
								}else{						
								jumpCode = temp.getJUMP_CODE();
								}
							}
						}
					}
				}
				for (CheckBox checkBox : this_CheckBoxs) {
					if (checkBox.isChecked()) {
						for (WenJuanInfo temp : tempAnswer) {
							if (checkBox.getId() == temp.getID()) {
								if(temp.getJUMP_CODE().contains("-")){
									jumpCode=(temp.getJUMP_CODE().subSequence(temp.getJUMP_CODE().indexOf(0)+1,temp.getJUMP_CODE().indexOf("-")))+"";
								}else if(temp.getJUMP_CODE().contains("—")){
									jumpCode=(temp.getJUMP_CODE().subSequence(temp.getJUMP_CODE().indexOf(0)+1,temp.getJUMP_CODE().indexOf("—")))+"";
								}else{						
								jumpCode = temp.getJUMP_CODE();
								}
							}
						}
					}
				}				
						
				int tempIndex = 0;
				
				if (jumpCode != null) {
					
					
					if(TextUtils.equals("结束",jumpCode)){
						
						
						Toast.makeText(context, "已经是最后一题了", Toast.LENGTH_LONG).show();
						btn_all.setVisibility(View.VISIBLE);
						tempList.add(tempQuestionIndex-1);
						
						if(tempList.size()>tempQuestionIndex-1){
							
						tempList.remove(tempQuestionIndex-1);
						}else{
							tempList.remove(tempList.size()-1);
						}
						return;
					}else{
						btn_all.setVisibility(View.GONE);
					}
					
					for (WenJuanInfo wenJuanInfo : questionInfos) {
						
						if (jumpCode.equals(wenJuanInfo.getCODE())) {
							index = questionInfos.indexOf(wenJuanInfo);
							tempIndex = 0;
							break;
						} else {
							tempIndex++;
						}
					}
				} else {
					tempIndex++;
				}
				
				tempList.add(tempQuestionIndex);
				
				if (tempIndex > 0) {
					index++;
				}
				tempAnswer.clear();
				currentInfo = questionInfos.get(index);
				
				fretchTree(llDuty, currentInfo, "");
			}
		});
        //上一题
	btn_up_question.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (index == 0) {
					Toast.makeText(context, "已经是第一题了", Toast.LENGTH_LONG)
							.show();
					return;
				}

				// 去掉答题的文本
				for (EditText editText : questionEditTexts) {
					if (currentInfo.getID() == editText.getId()) {
						editText.setText("");
					}
				}
				
				// 去掉选择日期的文本
				for (TextView textView : questionTextViews) {
					if (currentInfo.getID() == textView.getId()) {
						textView.setText("请点击选择");
					}
				}
				
				// 去掉小题的文本
			List<WenJuanInfo> list = getAnswerByParentId(currentInfo);
				for (WenJuanInfo wenJuanInfo : list) {
					for (EditText editText : editTexts) {
						if (editText.getId() == wenJuanInfo.getID()) {
							editText.setText("");
						}
					}
				}
				// 去掉单选按钮
				for (WenJuanInfo wenJuanInfo : list) {
					for (RadioButton radioButton : radioButtons) {
						if (radioButton.getId() == wenJuanInfo.getID()) {
							radioButton.setChecked(false);
						}
					}
					// 去掉多选按钮
					for (CheckBox checkBox : this_CheckBoxs) {
						if (checkBox.getId() == wenJuanInfo.getID()) {
							checkBox.setChecked(false);
						}
					}
				}

				if (tempList.size() > 0) {
					List<Integer> temp = new ArrayList<Integer>();
					temp.addAll(tempList.subList(tempList.size() - 1,
							tempList.size()));
					tempQuestionIndex = temp.get(0);
					tempList.removeAll(temp);
					temp.clear();
				}
				index = tempQuestionIndex;
				currentInfo = questionInfos.get(index);
				fretchTree(llDuty, currentInfo, "");
				if (index < questionInfos.size()) {
					btn_all.setVisibility(View.GONE);
				}
				btn_all.setVisibility(View.GONE);
				
			}
		});
        //查看全部
		btn_all.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!rb) {
					
					List<WenJuanInfo> newJuanInfos = typeMap.getJuanInfos();
					if (newJuanInfos.size() > 0) {
						juanInfos.clear();
						radioButtons.clear();
						editTexts.clear();
						questionEditTexts.clear();
						questionTextViews.clear();
						index = 0;
						juanInfos.addAll(newJuanInfos);
					}
				
					questionInfos = getQuestionByParent();
                     
				
					btn_star.setVisibility(View.GONE);
					btn_upload.setVisibility(View.GONE);
				
				} else {
					
					btn_star.setVisibility(View.VISIBLE);
					btn_star.setText("重新答题");
					btn_upload.setVisibility(View.VISIBLE);
				}

				if (questionInfos.size() > 0) {
					llDuty.removeAllViews();
					if (tempList.size() > 0) {
						tempList.clear();
					}
					llDuty.addView(view);
					
					for (WenJuanInfo answerInfo : questionInfos) {
						
						fretchTree(llDuty, answerInfo, "all");
					}
				}

				btn_next_question.setVisibility(View.GONE);
				btn_up_question.setVisibility(View.GONE);
				btn_all.setVisibility(View.GONE);
				
			}
		});
	}

	private void getAnswerInfo() {
		if (radioButtons.size() > 0) {
			for (RadioButton radioButton : radioButtons) {
				if (radioButton.isChecked()) {
					AnswerInfo answerInfo = new AnswerInfo();

					for (WenJuanInfo wenJuanInfo : juanInfos) {
						if (wenJuanInfo.getID() == radioButton.getId()) {
							answerInfo.setAnswerId(wenJuanInfo.getID());
							answerInfo.setAnswerNo(wenJuanInfo.getNO());
							answerInfo.setAnswerText("");
						}
					}
					if (editTexts.size() > 0) {
						for (EditText editText : editTexts) {
							if (radioButton.getId() == editText.getId()) {
								answerInfo.setAnswerText(editText.getText()
										.toString().trim());
							}
						}
					}
					answerInfos2.add(answerInfo);
				}
			}
		}
	if (this_CheckBoxs.size() > 0) {
		for (CheckBox radioButton : this_CheckBoxs) {
			if (radioButton.isChecked()) {
				AnswerInfo answerInfo = new AnswerInfo();
				
				for (WenJuanInfo wenJuanInfo : juanInfos) {
					if (wenJuanInfo.getID() == radioButton.getId()) {
						answerInfo.setAnswerId(wenJuanInfo.getID());
						answerInfo.setAnswerNo(wenJuanInfo.getNO());
						answerInfo.setAnswerText("");
					}
				}
				if (editTexts.size() > 0) {
					for (EditText editText : editTexts) {
						if (radioButton.getId() == editText.getId()) {
							answerInfo.setAnswerText(editText.getText()
									.toString().trim());
						}
					}
				}
				answerInfos2.add(answerInfo);
			}
		}
	}

		if (questionEditTexts.size() > 0) {
			List<WenJuanInfo> questionInfos = getQuestionByParent();
			for (EditText editText : questionEditTexts) {
				for (WenJuanInfo wenJuanInfo : questionInfos) {
					if (editText.getId() == wenJuanInfo.getID()
							&& !"".equals(editText.getText().toString().trim())
							&& null != editText.getText().toString().trim()) {
						AnswerInfo answerInfo = new AnswerInfo();
						answerInfo.setAnswerId(wenJuanInfo.getID());
						answerInfo.setAnswerNo(wenJuanInfo.getNO());
						answerInfo.setAnswerText(editText.getText().toString()
								.trim());
						answerInfos2.add(answerInfo);
					}
				}
			}
		}
		
		if (questionTextViews.size() > 0) {
			List<WenJuanInfo> questionInfos = getQuestionByParent();
			for (TextView textView : questionTextViews) {
				for (WenJuanInfo wenJuanInfo : questionInfos) {
					if (textView.getId() == wenJuanInfo.getID()
							&& !"请点击选择".equals(textView.getText().toString().trim())
							) {
						AnswerInfo answerInfo = new AnswerInfo();
						answerInfo.setAnswerId(wenJuanInfo.getID());
						answerInfo.setAnswerNo(wenJuanInfo.getNO());
						answerInfo.setAnswerText(textView.getText().toString()
								.trim());
						answerInfos2.add(answerInfo);
					}
				}
			}
		}
		
	}
    //组装答案
	
	private String parseAnswerInfo() {
		JSONArray array = new JSONArray();
		if (answerInfos2.size() > 0) {
			for (AnswerInfo answerInfo : answerInfos2) {
				JSONObject object = new JSONObject();
				try {
					object.put("DETIL_ID", answerInfo.getAnswerId());
					object.put("INPUT_VALUE", answerInfo.getAnswerText());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				array.put(object);
			}
		}

		return array.toString();
	}
	
	private String parseAnswerInfo2() {
		JSONArray array = new JSONArray();
		if (answerInfos2.size() > 0) {
			for (AnswerInfo answerInfo : answerInfos2) {
				JSONObject object = new JSONObject();
				try {
					object.put("QUESTION_ID", answerInfo.getAnswerId());
					object.put("INPUT_VALUE", answerInfo.getAnswerText());
					if(huZhu==1){
						object.put("HOMEID", listInfo.get(listInfo.size()-1).getID()+"");
					}else{			
						object.put("HOMEID", getIntent().getIntExtra("myHOMEID", 0) + "");
					}
					object.put("QUESTIONMASTERID", getIntent().getIntExtra("QUESTIONMASTERID", 0)+"");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				array.put(object);
			}
		}

		return array.toString();
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.parseInt(params[0].toString().trim())) {
		case REFRESH_INFO:
			if ("historyList".equals(getIntent().getAction())) {
				addTitle();
			}
			List<YiChaWenJuan> chaWenJuans = parseAnswerInfo(params[1]
					.toString().trim());
			if (chaWenJuans != null && chaWenJuans.size() > 0) {
				allchaWenJuans.addAll(chaWenJuans);
			}
            checkAnswer();
			break;

		case XUHUIREFRESH_INFO:
			if ("historyList".equals(getIntent().getAction())) {
				
				addTitle();
			}
			List<YiChaWenJuan> chaWenJuans2 = parseAnswerInfo2(params[1]
					.toString().trim());
			if (chaWenJuans2 != null && chaWenJuans2.size() > 0) {
				allchaWenJuans.addAll(chaWenJuans2);
			}

			checkAnswer();
			break;
			
		case REFRESH_INFO_HISTORY_LIST:
			if (!"".equals(params[1].toString().trim())
					|| null != params[1].toString().trim()) {
				List<WenJuanType> newWenJuanType = getWenJuanTypes(params[1]
						.toString().trim());
				if (newWenJuanType.size() > 0) {
					MainTools.map.put("wenjuaninfohistory",
							(WenJuanType) newWenJuanType.get(0));
					typeMap = MainTools.map.get("wenjuaninfohistory");
					Map<String, Object> params1 = new HashMap<String, Object>();
					Map<String, String> data = new HashMap<String, String>();
					data.put("Personnel_id",
							getIntent().getIntExtra("answerId", 0) + "");
					params1.put("data", data);
					//查看已查的数据
					PersonTask task = new PersonTask(
							PersonTask.WENJUANLISTACTIVITY_GET_INFO_LIST_ANSWER,
							params1);
					PersonService.newTask(task);
				}
			}
			break;

		default:
			break;
		}
	}
	//数据填充
	private void fretchTree(LinearLayout layout, WenJuanInfo info, String isAll) {
		
		if ("".equals(isAll)) {
			llDuty.removeAllViews();
		}
		LinearLayout alllinearLayout = new LinearLayout(context);
		LinearLayout.LayoutParams allparam = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		alllinearLayout.setLayoutParams(allparam);
		alllinearLayout.setOrientation(LinearLayout.VERTICAL);
		HorizontalScrollView hsv=new HorizontalScrollView(context);
		hsv.setLayoutParams(allparam);
		LinearLayout qlinearLayout = new LinearLayout(context);
		qlinearLayout.setLayoutParams(allparam);
		qlinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		TextView textView = new TextView(context);
		textView.setLayoutParams(allparam);
		textView.setText(info.getTITLE_L());
	
		textView.setTextSize(16);
		qlinearLayout.addView(textView, allparam);
		
		if (info.isINPUT()) {
			
			if(!info.getTITLE_L().contains("出生")&&!info.getTITLE_L().contains("就业时间")&&!info.getTITLE_L().contains("就业的时间")&&!info.getTITLE_L().contains("什么时候")){	
				
				EditText editText = new EditText(context);
				LinearLayout.LayoutParams edit = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			editText.setLayoutParams(edit);
				editText.setText("        ");
			if(info.getTITLE_L().contains("姓名")){
				
				editText.setText(nameStr);

				
			}
			if ("int".equals(info.getINPUT_TYPE())) {
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			}
			if (questionEditTexts.size() > 0) {
				List<EditText> tempEditTexts = new ArrayList<EditText>();
				for (EditText editText2 : questionEditTexts) {
					if (editText2.getId() == info.getID()) {
						editText.setText(editText2.getText());
						//从答案中得到家庭成员的名字
						if(info.getTITLE_L().contains("姓名")){
							uploadName=editText2.getText().toString().trim();
						}
						tempEditTexts.add(editText2);
					}
				}
				questionEditTexts.removeAll(tempEditTexts);
				tempEditTexts.clear();
			}

			editText.setId(info.getID());
			
			if (allchaWenJuans.size() > 0) {
				for (YiChaWenJuan yiChaWenJuan : allchaWenJuans) {
					if (yiChaWenJuan.getDETIL_ID() == editText.getId()) {
						editText.setText(yiChaWenJuan.getINPUT_VALUE());
					}
				}
			}
			if ("all".equals(isAll)) {
				editText.setEnabled(false);
			}		
			questionEditTexts.add(editText);
			qlinearLayout.addView(editText, edit);
			}else{	
				
				final TextView TextView = new TextView(context);
				LinearLayout.LayoutParams tv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
				TextView.setLayoutParams(tv);
				TextView.setTextSize(16);
			TextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
            TextView.setTextColor(0xff538ee5);
				TextView.setPadding(10,0,0,0);
				Drawable drawable= getResources().getDrawable(R.drawable.rili);  
				/// 这一步必须要做,否则不会显示.  
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());  
				TextView.setCompoundDrawables(null,null,drawable,null);
				
				TextView.setText("请点击选择");
				
				TextView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Calendar c=Calendar.getInstance();
						new DatePickerDialog(WenJuanDetailActivity.this, 
						new DatePickerDialog.OnDateSetListener() {
							
							@Override
							public void onDateSet(DatePicker view, int year, int monthOfYear,
									int dayOfMonth) {
								TextView.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
							}
						},c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
						
					}
				});
				
				if (questionTextViews.size() > 0) {
					List<TextView> tempTextViews = new ArrayList<TextView>();
					for (TextView textView2 : questionTextViews) {
						if (textView2.getId() == info.getID()) {
							TextView.setText(textView2.getText());
							tempTextViews.add(textView2);
						}
					}
					questionTextViews.removeAll(tempTextViews);
					tempTextViews.clear();
				}

				TextView.setId(info.getID());
				if (allchaWenJuans.size() > 0) {
					for (YiChaWenJuan yiChaWenJuan : allchaWenJuans) {
						if (yiChaWenJuan.getDETIL_ID() == TextView.getId()) {
						TextView.setText(yiChaWenJuan.getINPUT_VALUE());
						}
					}
				}

				if ("all".equals(isAll)) {
					TextView.setEnabled(false);
				}
				
				questionTextViews.add(TextView);
				qlinearLayout.addView(TextView,tv);
				
				}			
				
			
			if (!"".equals(info.getTITLE_R())) {
				TextView rTextView = new TextView(context);
				rTextView.setLayoutParams(allparam);
				rTextView.setTextSize(16);
				rTextView.setText(info.getTITLE_R());
				qlinearLayout.addView(rTextView, allparam);
			}
		}
		hsv.addView(qlinearLayout, allparam);
		alllinearLayout.addView(hsv, allparam);

		//alllinearLayout.addView(qlinearLayout, allparam);//2017/10/26

		LinearLayout aLinearLayout = new LinearLayout(context);
		aLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		aLinearLayout.setLayoutParams(allparam);

		RadioGroup radioGroup = new RadioGroup(context);
		radioGroup.setLayoutParams(allparam);

		LinearLayout xuanxiangLinearLayout = new LinearLayout(context);
		xuanxiangLinearLayout.setLayoutParams(allparam);
		xuanxiangLinearLayout.setPadding(0, 0, 0, 0);
		xuanxiangLinearLayout.setOrientation(LinearLayout.VERTICAL);
		//用问题的信息得到选项的信息
		answerInfo = getAnswerByParentId(info);
		if (allanswerEditInfo.size() > 0) {
			if (allanswerEditInfo.containsAll(answerInfo)) {
				allanswerEditInfo.removeAll(answerInfo);
			}
		}
		allanswerEditInfo.addAll(answerInfo);
        
		if(info.getTITLE_L().contains("多选")||info.getTITLE_L().contains("可以选择多个答案")){
			
			 //多选题
			List<CheckBox> CheckBoxGroup = new ArrayList<CheckBox>();

			for (WenJuanInfo wenJuanInfo : answerInfo) {
				fretchTreeByQuestionMultiSelect(CheckBoxGroup, radioGroup,
						wenJuanInfo, xuanxiangLinearLayout, isAll,
						getMaxxuangxiang(info.getTITLE_L()));
			}
			
		}else{
			//单选题
		for (WenJuanInfo wenJuanInfo : answerInfo) {
			fretchTreeByQuestion(radioGroup, wenJuanInfo,
					xuanxiangLinearLayout, isAll);
		}

		}
		aLinearLayout.addView(radioGroup, allparam);
		aLinearLayout.addView(xuanxiangLinearLayout, allparam);

		alllinearLayout.addView(aLinearLayout, allparam);

		layout.addView(alllinearLayout, allparam);
	}
	
	private int getMaxxuangxiang(String a){
		
            if(a.contains("最多选")&&a.contains("项")){
			
			return Integer.valueOf(a.substring(a.indexOf("最多选")+3,a.indexOf("项")));
		
		}
            
            if(a.contains("不要超过")&&a.contains("个)")){
    			
    			return Integer.valueOf(a.substring(a.indexOf("不要超过")+4,a.indexOf("个)")));
    		
    			
    		}
		
            if(a.contains("不要超过")&&a.contains("个）")){
    			
    			return Integer.valueOf(a.substring(a.indexOf("不要超过")+4,a.indexOf("个）")));
    		
    			
    		}
            
            return 30;
	}
	
	//多选题选项的布局
  private void fretchTreeByQuestionMultiSelect(List<CheckBox> CheckBoxGroup,
		RadioGroup group, WenJuanInfo wenJuanInfo,
		LinearLayout xuanxiangLinearLayout, String isAll, int MultiSelect){
	
	LinearLayout linearLayout = new LinearLayout(context);
	LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);//60 2017/10/26
	linearLayout.setLayoutParams(param1);
	linearLayout.setOrientation(LinearLayout.HORIZONTAL);
	
	CheckBox CheckButton = new CheckBox(context);
	
	CheckButton.setOnCheckedChangeListener(new MyOnCheckedChangeListener(
			CheckBoxGroup, MultiSelect));
	LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, 60);//60 2017/10/26
	CheckButton.setLayoutParams(param2);
	//给复选框设置ID
	CheckButton.setId(wenJuanInfo.getID());
	group.addView(CheckButton);
	if (this_CheckBoxs.size() > 0) {
		List<CheckBox> tempRadioButtons = new ArrayList<CheckBox>();
		for (CheckBox _CheckBox : this_CheckBoxs) {
			if (_CheckBox.getId() == wenJuanInfo.getID()
					&& _CheckBox.isChecked()) {
				CheckButton.setChecked(true);
				tempRadioButtons.add(_CheckBox);
			}
		}
		this_CheckBoxs.removeAll(tempRadioButtons);
		tempRadioButtons.clear();

	}
	
	if (allchaWenJuans.size() > 0) {
		for (YiChaWenJuan yiChaWenJuan : allchaWenJuans) {

			if (yiChaWenJuan.getDETIL_ID() == CheckButton.getId()) {
				
				CheckButton.setChecked(true);

			}
		}
	}
	
	if ("all".equals(isAll)) {
		CheckButton.setEnabled(false);
	}

	
	for(int i=0;i<this_CheckBoxs.size();i++)
	{
		if(this_CheckBoxs.get(i).getId()==CheckButton.getId())
		{
			this_CheckBoxs.remove(i);
			break;
		}
	}
	this_CheckBoxs.add(CheckButton);
	//多选题选项的文字和输入框
	if (wenJuanInfo.isINPUT()) {
				LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);//45 2017/10/26
				TextView textView = new TextView(context);
				textView.setGravity(Gravity.CENTER);
				textView.setLayoutParams(text);
				textView.setTextSize(13);
				textView.setText(wenJuanInfo.getTITLE_L());
				linearLayout.addView(textView, text);
				EditText editText = new EditText(context);

				//editText.setTextSize(24);
				editText.setOnKeyListener(new MyOnKeyListener(editText));
				//给编辑框设置ID
				editText.setId(wenJuanInfo.getID());
		        editText.setText("        ");
		        editText.setTextSize(13);
				if ("int".equals(wenJuanInfo.getINPUT_TYPE())) {
					editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				}
				if (editTexts.size() > 0) {
					List<EditText> tempEditTexts = new ArrayList<EditText>();
					for (EditText editText2 : editTexts) {
						if (editText2.getId() == wenJuanInfo.getID()) {
							editText.setText(editText2.getText());
							tempEditTexts.add(editText2);
						}

					}
					editTexts.removeAll(tempEditTexts);
					tempEditTexts.clear();
				}

				if (allchaWenJuans.size() > 0) {
					for (YiChaWenJuan yiChaWenJuan : allchaWenJuans) {
						if (yiChaWenJuan.getDETIL_ID() == editText.getId()) {
							editText.setText(yiChaWenJuan.getINPUT_VALUE());
						}
					}
				}
				
				if ("all".equals(isAll)) {
					editText.setEnabled(false);
				}
				
				editTexts.add(editText);
				LinearLayout.LayoutParams edit = new LinearLayout.LayoutParams(100,
						LayoutParams.WRAP_CONTENT);//45 2017/10/26
				editText.setLayoutParams(edit);
		linearLayout.setPadding(0,-25,0,0);
				linearLayout.addView(editText, edit);

				if (!"".equals(wenJuanInfo.getTITLE_R())) {
					LinearLayout.LayoutParams rParams = new LinearLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);//45 2017/10/26
					TextView rTextView = new TextView(context);
					textView.setLayoutParams(rParams);
					rTextView.setTextSize(23);
					rTextView.setText(wenJuanInfo.getTITLE_R());
					linearLayout.setPadding(0,-30,0,0);
					linearLayout.addView(rTextView, rParams);
				}
			} else {	
				TextView textView = new TextView(context);
				LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, 60);//45 2017/10/26
				textView.setGravity(Gravity.CENTER);
				CheckButton.setId(wenJuanInfo.getID());
				textView.setText(wenJuanInfo.getTITLE_L());
				textView.setTextSize(13);
				linearLayout.addView(textView, textparams);
			}
			xuanxiangLinearLayout.addView(linearLayout, param1);
			
}
	
	private void fretchTreeByQuestion(RadioGroup group,
			final WenJuanInfo wenJuanInfo, LinearLayout xuanxiangLinearLayout,
			String isAll) {//单选题
		LinearLayout linearLayout = new LinearLayout(context);
		LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);//60 2017/10/26 整个文字选项的布局
		linearLayout.setLayoutParams(param1);
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);

		RadioButton radioButton = new RadioButton(context);
		LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 60);//60 2017/10/26
		radioButton.setLayoutParams(param2);
		radioButton.setId(wenJuanInfo.getID());
		radioButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if(!TextUtils.equals("结束", wenJuanInfo.getJUMP_CODE())){
					btn_all.setVisibility(View.GONE);
				}
			}
		});
		group.addView(radioButton);
		if (radioButtons.size() > 0) {
			List<RadioButton> tempRadioButtons = new ArrayList<RadioButton>();
			for (RadioButton radioButton2 : radioButtons) {
				if (radioButton2.getId() == wenJuanInfo.getID()
						&& radioButton2.isChecked()) {
					radioButton.setChecked(true);
					tempRadioButtons.add(radioButton2);
				}
			}
			radioButtons.removeAll(tempRadioButtons);
			tempRadioButtons.clear();
		}

		if (allchaWenJuans.size() > 0) {
			for (YiChaWenJuan yiChaWenJuan : allchaWenJuans) {
				if (yiChaWenJuan.getDETIL_ID() == radioButton.getId()) {
					radioButton.setChecked(true);
				}
			}
		}

		if ("all".equals(isAll)) {
			radioButton.setEnabled(false);
		}
		radioButtons.add(radioButton);
		if (wenJuanInfo.isINPUT()) {
			LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);//60 2017/10/26
			TextView textView = new TextView(context);
			textView.setGravity(Gravity.CENTER);
			textView.setLayoutParams(text);
			textView.setTextSize(13);
			textView.setText(wenJuanInfo.getTITLE_L());
			linearLayout.addView(textView, text);
			EditText editText = new EditText(context);
			editText.setId(wenJuanInfo.getID());
			editText.setText("        ");
			editText.setTextSize(13);
			if ("int".equals(wenJuanInfo.getINPUT_TYPE())) {
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			}
			if (editTexts.size() > 0) {
				List<EditText> tempEditTexts = new ArrayList<EditText>();
				for (EditText editText2 : editTexts) {
					if (editText2.getId() == wenJuanInfo.getID()) {
						editText.setText(editText2.getText());
						tempEditTexts.add(editText2);
					}
				}
				editTexts.removeAll(tempEditTexts);
				tempEditTexts.clear();
			}

			if (allchaWenJuans.size() > 0) {
				for (YiChaWenJuan yiChaWenJuan : allchaWenJuans) {
					if (yiChaWenJuan.getDETIL_ID() == editText.getId()) {
						editText.setText(yiChaWenJuan.getINPUT_VALUE());
					}
				}
			}

			if ("all".equals(isAll)) {
				editText.setEnabled(false);
			}
			
			editTexts.add(editText);
			LinearLayout.LayoutParams edit = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);//10 2017/10/26
			editText.setLayoutParams(edit);
			//linearLayout.setPadding(0,-35,0,0);
			linearLayout.setPadding(0,-25,0,0);
			linearLayout.addView(editText, edit);

			if (!"".equals(wenJuanInfo.getTITLE_R())) {
				LinearLayout.LayoutParams rParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);//30 2017/10/26
				TextView rTextView = new TextView(context);
				textView.setLayoutParams(rParams);
				rTextView.setTextSize(13);
				rTextView.setText(wenJuanInfo.getTITLE_R());
				//linearLayout.setPadding(0,-46,0,0);
				linearLayout.setPadding(0,-30,0,0);
				linearLayout.addView(rTextView, rParams);
			}
		} else {
			TextView textView = new TextView(context);
			LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,60);//50  这里不能乱改  不是输入文字的选项的布局
			textView.setGravity(Gravity.CENTER);
			radioButton.setId(wenJuanInfo.getID());
			textView.setText(wenJuanInfo.getTITLE_L());
			textView.setTextSize(13);
			linearLayout.addView(textView, textparams);
		}
		xuanxiangLinearLayout.addView(linearLayout, param1);
	}

	private List<WenJuanInfo> getQuestionByParent() {
		List<WenJuanInfo> questionInfos = new ArrayList<WenJuanInfo>();
		for (WenJuanInfo wenJuanInfo : juanInfos) {
			if (wenJuanInfo.getPARENT_ID() == 0) {
				questionInfos.add(wenJuanInfo);
			}
		}
		return questionInfos;
	}
	//用问题的信息得到选项的信息
	private List<WenJuanInfo> getAnswerByParentId(WenJuanInfo info) {
		List<WenJuanInfo> anwserInfos = new ArrayList<WenJuanInfo>();
		for (WenJuanInfo wenJuanInfo : juanInfos) {
			if (wenJuanInfo.getPARENT_ID() == info.getID()) {
				anwserInfos.add(wenJuanInfo);
			}
		}
		return anwserInfos;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	private void addTitle() {
		view = LayoutInflater.from(context).inflate(R.layout.item_wenjuan_title, null);
		TextView tv_no = (TextView) view.findViewById(R.id.tv_number);
		TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
		TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title_);
		WebView webView = (WebView) view.findViewById(R.id.my_webview);
		tv_no.setText(Html.fromHtml("<u>" + getIntent().getStringExtra("NO")
				+ "</u>"));
		tv_title.setText(typeMap.getTITLE());
		if(getIntent().getSerializableExtra("sname")!=null){
			
			
			if(TextUtils.equals(getIntent().getSerializableExtra("sname")+"","null")){
				tv_name.setText("");
			}else{
			tv_name.setText(getIntent().getSerializableExtra("sname")+"");
			}
		}
		if(getIntent().getSerializableExtra("wname")!=null){
			
			
			tv_name.setText(getIntent().getSerializableExtra("wname")+"");
		}
		if(getIntent().getStringExtra("lishiName")!=null){
			
			tv_name.setText(getIntent().getStringExtra("lishiName")+"");
		}
		
		nameStr=tv_name.getText().toString().trim();
		
        tv_time.setText(typeMap.getCREATE_TIME().substring(0, 10));
		
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		String dateStr;
//		
//		dateStr=sdf.format(new Date());
//		
//		tv_time.setText(dateStr);
		
		webView.loadUrl(MyOkHttpUtils.BaseUrl + "/json/" + typeMap.getTEXT());
		llDuty.addView(view);
	}

	private boolean makeEdit(List<WenJuanInfo> infos) {
		if (editTexts.size() > 0) {
			for (RadioButton radioButton : radioButtons) {
				if (radioButton.isChecked()) {
					for (EditText editText : editTexts) {
						for (WenJuanInfo wenJuanInfo : infos) {
							if (radioButton.getId() == wenJuanInfo.getID()
									&& editText.getId() == wenJuanInfo.getID()
									&& "".equals(editText.getText().toString()
											.trim())) {
								Toast.makeText(context, "答案不能为空!",
										Toast.LENGTH_SHORT).show();
								return true;
							}
						}
					}
				}

			}
		}
		return false;
	}

	private boolean makeEdit_checkBox(List<WenJuanInfo> infos) {
		if (editTexts.size() > 0) {
			for (CheckBox radioButton : this_CheckBoxs) {
				if (radioButton.isChecked()) {
					for (EditText editText : editTexts) {
						for (WenJuanInfo wenJuanInfo : infos) {
							if (radioButton.getId() == wenJuanInfo.getID()
									&& editText.getId() == wenJuanInfo.getID()
									&& "".equals(editText.getText().toString()
											.trim())) {
								Toast.makeText(context, "答案不能为空!",
										Toast.LENGTH_SHORT).show();

								return true;
							}

						}
					}
				}

			}
		}
		return false;
	}
	
	private boolean checkRadioIsChecked(List<WenJuanInfo> infos, int questionId) {
		for (RadioButton radioButton : radioButtons) {
			for (WenJuanInfo wenJuanInfo : infos) {
				if (radioButton.getId() == wenJuanInfo.getID()
						&& wenJuanInfo.getPARENT_ID() == questionId
						&& radioButton.isChecked()) {
					return true;
				}
			}
		}
		for (CheckBox _CheckBox : this_CheckBoxs) {
			for (WenJuanInfo wenJuanInfo : infos) {
				if (_CheckBox.getId() == wenJuanInfo.getID()
						&& wenJuanInfo.getPARENT_ID() == questionId
						&& _CheckBox.isChecked()) {
					return true;
				}
			}
		}
		return false;
	}

	private List<YiChaWenJuan> parseAnswerInfo(String str) {
		List<YiChaWenJuan> newyiChaWenJuans = new ArrayList<YiChaWenJuan>();
		try {
			JSONArray array = new JSONArray(str);
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = (JSONObject) array.get(i);
				YiChaWenJuan chaWenJuan = new YiChaWenJuan();
				chaWenJuan.setID(jsonObject.getInt("ID"));
				chaWenJuan.setDETIL_ID(jsonObject.getInt("DETIL_ID"));
				chaWenJuan.setINPUT_VALUE(jsonObject.getString("INPUT_VALUE"));
				chaWenJuan.setPERSONNEL_ID(jsonObject.getInt("PERSONNEL_ID"));
				chaWenJuan.setRecordCount(jsonObject.getInt("RecordCount"));
				newyiChaWenJuans.add(chaWenJuan);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newyiChaWenJuans;
	}

	private List<YiChaWenJuan> parseAnswerInfo2(String str) {
		List<YiChaWenJuan> newyiChaWenJuans = new ArrayList<YiChaWenJuan>();
		try {
			JSONArray array = new JSONArray(str);
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = (JSONObject) array.get(i);
				YiChaWenJuan chaWenJuan = new YiChaWenJuan();
				chaWenJuan.setID(jsonObject.getInt("ID"));
				chaWenJuan.setDETIL_ID(jsonObject.getInt("QUESTION_ID"));
				chaWenJuan.setINPUT_VALUE(jsonObject.getString("INPUT_VALUE"));
				//chaWenJuan.setPERSONNEL_ID(jsonObject.getInt("PERSONNEL_ID"));
				//chaWenJuan.setRecordCount(jsonObject.getInt("RecordCount"));
				newyiChaWenJuans.add(chaWenJuan);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newyiChaWenJuans;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (rb) {
				showMessage("温馨提示", "确定要放弃答题吗?");
			} else {
				showMessage("温馨提示", "确定要退出吗?");
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showMessage(String title, String message) {
		Builder builder = new Builder(context);
		builder.setTitle(title).setMessage(message)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						WenJuanDetailActivity.this.finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).create().show();
	}

	private List<WenJuanType> getWenJuanTypes(String str) {
		List<WenJuanType> juanTypes = new ArrayList<WenJuanType>();
		try {
			JSONArray jsonArray = new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				WenJuanType juanType = new WenJuanType();
				juanType.setID(jsonObject.getInt("ID"));
				juanType.setCREATE_TIME(jsonObject.getString("CREATE_TIME"));
				juanType.setNO(jsonObject.getString("NO"));
				juanType.setRecordCount(jsonObject.getInt("RecordCount"));
				juanType.setTEXT(jsonObject.getString("TEXT"));
				juanType.setTITLE(jsonObject.getString("TITLE"));
				String details = jsonObject.getString("Detils").toString();
				juanType.setJuanInfos(parseInfos(details));
				juanTypes.add(juanType);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return juanTypes;
	}

	private List<WenJuanInfo> parseInfos(String str) {
		List<WenJuanInfo> newInfos = new ArrayList<WenJuanInfo>();
		try {
			JSONArray array = new JSONArray(str);
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = (JSONObject) array.get(i);
				WenJuanInfo info = new WenJuanInfo();
				info.setID(object.getInt("ID"));
				info.setCODE(object.getString("CODE"));
				info.setINPUT(object.getBoolean("INPUT"));
				info.setINPUT_TYPE(object.getString("INPUT_TYPE"));
				info.setJUMP_CODE(object.getString("JUMP_CODE"));
				info.setNO(object.getDouble("NO"));
				info.setPARENT_ID(object.getInt("PARENT_ID"));
				info.setRecordCount(object.getInt("RecordCount"));
				info.setTITLE_L(object.getString("TITLE_L"));
				info.setTITLE_R(object.getString("TITLE_R"));
				info.setREMOVE_CODE(object.getString("REMOVE_CODE"));
				newInfos.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newInfos;
	}
	
	//编辑框的监听
		public class MyOnKeyListener implements OnKeyListener {
			public MyOnKeyListener(EditText EditText) {
				_EditText = EditText;

			}

			EditText _EditText;

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (event.getKeyCode() == 67)
					return false;
				// 去掉回车\r\n
				_EditText.setText(_EditText.getText().toString().replace("\r", "")
						.replace("\n", ""));
				// 让输入光标在尾部
				_EditText.setSelection(_EditText.getText().toString().length());

				if (_EditText.getText().toString().length() < 10) {
					LinearLayout.LayoutParams edit = new LinearLayout.LayoutParams(
							460, 60);
					_EditText.setLayoutParams(edit);
				} else {
					LinearLayout.LayoutParams edit = new LinearLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT, 60);
					_EditText.setLayoutParams(edit);
				}
				return false;
			}

		}
	
	//复选框的监听
		public class MyOnCheckedChangeListener implements OnCheckedChangeListener {
			int _MultiSelect;
			List<CheckBox> _group;

			public MyOnCheckedChangeListener(List<CheckBox> group, int MultiSelect) {

				_group = group;
				_MultiSelect = MultiSelect;
			}

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
					if (_group.size() > _MultiSelect - 1) {
						buttonView.setChecked(false);
						Toast.makeText(context, "最多选" + _MultiSelect + "项",
								Toast.LENGTH_SHORT).show();
					} else {
						_group.add((CheckBox) buttonView);
					}
				} else {
					CheckBox _check_box = (CheckBox) buttonView;
					if (_group.contains(_check_box)) {
						_group.remove(_check_box);
					}
				}
			}

		}
		
		//提交答案
		private void postJson_WenJuan(final String url,final Map<String,Object> data) {
			Log.e("2017/10/26","提交答案=========");
				final HttpClient client = new DefaultHttpClient();

				final int myHOMEID;
				if(huZhu==1){
					myHOMEID=listInfo.get(listInfo.size()-1).getID();
				}else{
					myHOMEID=getIntent().getIntExtra("myHOMEID", 0);
				}
				

			final String strhttp = MyOkHttpUtils.BaseUrl + url+"?ID="+myHOMEID;
				

			new Thread(

					new Runnable() {
						@Override
						public void run() {
							String cookies = SharedPreferencesUtils.getString("cookies");
							HttpPost post = new HttpPost(strhttp);
				try {
					//if (!HttpUrls_.staffName.trim().equals("")) {
						post.setHeader("cookie", cookies);
					//}
						if (data!=null) {				
							byte[] json=(data.get("data").toString()).getBytes("utf-8");
						String str = Base64.encodeToString(json, Base64.DEFAULT);
						StringEntity stringEntity = new StringEntity(str);
						stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
								"application/json"));
						stringEntity.setContentEncoding(new BasicHeader(
								HTTP.CONTENT_ENCODING, HTTP.UTF_8));
						post.setEntity(stringEntity);
						}

					HttpResponse response = client.execute(post);
					
					if (response.getStatusLine().getStatusCode() == 200) {

            runOnUiThread(new Runnable() {
				@Override
				public void run() {
					activity = (WenJuanPersonActivity) PersonService
							.getActivityByName("WenJuanPersonActivity");
					if (activity != null) {

						if(WenJuanPersonActivity.isWeichaRg){


							activity.refresh(
									WenJuanPersonActivity.REFRESH_PERSION_INFO,"True",WenJuanPersonActivity.wenJuanPersonPosition);
						}else{
							activity.refresh(
									WenJuanPersonActivity.REFRESH_PERSION_INFO,"True",-1);
						}
					}


					getPersonNum();
				}
			});


					}
				} catch (Exception e) {

					e.printStackTrace();
				} finally {
					post.abort();
				}

						}
					}

			).start();

			}
	
		private void getPersonNum(){
			String cookies = SharedPreferencesUtils.getString("cookies");
			int typeId;
			
			if(WenJuanPersonActivity.isWeichaRg){
				typeId=1;
			}else{
				typeId=0;
			}
			
			OkHttpUtils.post().url(MyOkHttpUtils.BaseUrl+"/Json/Get_Qa_UpLoad_Personnel.aspx")
			.addParams("type",typeId+"").addParams("page", "0").addParams("rows","15")
			.addParams("master_id", getIntent().getIntExtra("QUESTIONMASTERID", 0)+"").addHeader("cookie", cookies).build().execute(new StringCallback() {
				
				@Override
				public void onResponse(String arg0) {

					activity = (WenJuanPersonActivity) PersonService
							.getActivityByName("WenJuanPersonActivity");
				
					if (activity != null) {
						
			
						
					activity.refresh(WenJuanPersonActivity.REFRESH_INFO,
							arg0);
					}
					
					
				}
				
				@Override
				public void onError(Call arg0, Exception arg1) {
					
					Toast.makeText(WenJuanDetailActivity.this,"请连接网络",Toast.LENGTH_SHORT).show();
				}
			});
			
		}
	//注册家庭人员信息
	private void getFlistInfo(){
		String cookies = SharedPreferencesUtils.getString("cookies");
		OkHttpUtils.post().url(MyOkHttpUtils.BaseUrl+WenJuanRegisterInfo.registerInfoUrl).addParams("TYPE",1+"").addParams("NAME",fInfo.getNAME()+"")
		.addParams("SFZ",fInfo.getSFZ()+"").addParams("LXR",fInfo.getLXR()).addParams("LXDF",fInfo.getLXDF()+"")
		.addParams("JZNUM",fInfo.getJZNUM()+"").addParams("NAN",fInfo.getNAN()+"").addParams("NV",fInfo.getNV()+"").addParams("ZS",fInfo.getZS()+"")
		.addParams("ID",((WenJuanPersonInfo) getIntent().getSerializableExtra("info")).getID()+"").addParams("SQR",uploadName+"")
		.addParams("XSQ",fInfo.getXSQ()+"").addParams("XZJD",fInfo.getXZJD()+"").addParams("SQJWC",fInfo.getSQJWC()+"")
		.addParams("ADRESS",fInfo.getADRESS()+"").addParams("QUESTIONMASTERID",getIntent().getIntExtra("QUESTIONMASTERID", 0)+"")
		.addHeader("cookie", cookies)
		.build().execute(new StringCallback() {
			
			@Override
			public void onResponse(final String infoStr) {

				getFamilyList();
				
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				
				
				Toast.makeText(WenJuanDetailActivity.this,"请连接网络",Toast.LENGTH_SHORT).show();
			}
		});
		
		
	}
	
	
	//新建家庭人员信息
		private void newNullInfo(){
			
			String phoneStr;
			String cookies = SharedPreferencesUtils.getString("cookies");
			if(TextUtils.equals(wjPInfo.getPHONE(),"")){
				phoneStr="";
			}else{
				phoneStr=wjPInfo.getPHONE()+"";
			}
			
			OkHttpUtils.post().url(MyOkHttpUtils.BaseUrl+WenJuanRegisterInfo.registerInfoUrl).addParams("TYPE",1+"").addParams("NAME",wjPInfo.getNAME()+"")
			.addParams("SFZ",wjPInfo.getSFZ()+"").addParams("LXR","").addParams("LXDF",phoneStr)
			.addParams("JZNUM","0").addParams("NAN","0").addParams("NV","0").addParams("ZS","0")
			.addParams("ID",((WenJuanPersonInfo) getIntent().getSerializableExtra("info")).getID()+"").addParams("SQR",uploadName+"")
			.addParams("XSQ",wjPInfo.getQX()+"").addParams("XZJD",wjPInfo.getJD()+"").addParams("SQJWC",wjPInfo.getJW()+"")
			.addParams("ADRESS",wjPInfo.getLXDZ()+"").addParams("QUESTIONMASTERID",getIntent().getIntExtra("QUESTIONMASTERID", 0)+"")
			.addHeader("cookie", cookies)
			.build().execute(new StringCallback() {
				
				@Override
				public void onResponse(final String infoStr) {
					getFamilyList();
					
				}
				
				@Override
				public void onError(Call arg0, Exception arg1) {
					
					
					Toast.makeText(WenJuanDetailActivity.this,"请连接网络",Toast.LENGTH_SHORT).show();
				}
			});
			
			
		}
	
	private void getFamilyList(){
OkHttpUtils.post().url(MyOkHttpUtils.BaseUrl+ShowPersionDetailInfo.familyListUrl).addParams("TYPE","1").addParams("SFZ",wjPInfo.getSFZ()+"").addParams("QA_MASTER",getIntent().getIntExtra("QUESTIONMASTERID", 1)+"").build().execute(new StringCallback() {
			
			@Override
			public void onResponse(final String infoStr) {

				
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

						getAnswerInfo();
						String answerString = null;
						if(getIntent().getBooleanExtra("ISJYSTATUS", false)){
							
								answerString = parseAnswerInfo2();
										
						}
						if (answerInfos2.size() > 0) {
							answerInfos2.clear();
						}

						if (questionInfos.size() > 0) {
							llDuty.removeAllViews();
							if (tempList.size() > 0) {
								tempList.clear();
							}
							llDuty.addView(view);
							for (WenJuanInfo answerInfo : questionInfos) {
								fretchTree(llDuty, answerInfo,
										"all");
							}
						}
						btn_star.setVisibility(View.GONE);
						btn_upload.setEnabled(false);


						if(getIntent().getBooleanExtra("ISJYSTATUS", false)){
						Map<String, Object> data = new HashMap<String, Object>();
						data.put("data", answerString);
						//提交答案			
						postJson_WenJuan("/Json/Set_Tb_Home_Answer_Info.aspx",data);
						WenJuanDetailActivity.this.finish();
						}
					}
				});
				
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				
				//Log.i("2017/3/6","===2017/3/6失败==="+arg0+"===2017/3/6异常==="+arg1);
				
				Toast.makeText(WenJuanDetailActivity.this,"请连接网络",Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void checkAnswer(){
		
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		
		if (!rb) {
			
			List<WenJuanInfo> newJuanInfos = typeMap.getJuanInfos();
			if (newJuanInfos.size() > 0) {
				juanInfos.clear();
				radioButtons.clear();
				editTexts.clear();
				questionEditTexts.clear();
				questionTextViews.clear();
				index = 0;
				juanInfos.addAll(newJuanInfos);
			}
		
			questionInfos = getQuestionByParent();
             
		
			btn_star.setVisibility(View.GONE);
			btn_upload.setVisibility(View.GONE);
		
		} else {
			
			btn_star.setVisibility(View.VISIBLE);
			btn_star.setText("重新答题");
			btn_upload.setVisibility(View.VISIBLE);
		}

		if (questionInfos.size() > 0) {
		//	llDuty.removeAllViews();
			if (tempList.size() > 0) {
				tempList.clear();
			}
			
			if(view==null){
				addTitle();
			}
			
	 //llDuty.addView(view);
			
			for (WenJuanInfo answerInfo : questionInfos) {
				fretchTree(llDuty, answerInfo, "all");
			}
		}

		btn_next_question.setVisibility(View.GONE);
		btn_up_question.setVisibility(View.GONE);
		btn_all.setVisibility(View.GONE);
	}
	
	private void showDialog() {
		dialog = new ProgressDialog(WenJuanDetailActivity.this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("数据信息加载中...");
		dialog.show();
	}
	
}

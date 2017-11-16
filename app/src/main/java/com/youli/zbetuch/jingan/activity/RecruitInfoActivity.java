package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.entity.AreaInfo;
import com.youli.zbetuch.jingan.entity.CompanyPropertyInfo;
import com.youli.zbetuch.jingan.entity.GzbsInfo;
import com.youli.zbetuch.jingan.entity.GzxzInfo;
import com.youli.zbetuch.jingan.entity.IndustryInfo;
import com.youli.zbetuch.jingan.entity.JobInfoListInfo;
import com.youli.zbetuch.jingan.entity.OccInfo;
import com.youli.zbetuch.jingan.entity.RecruitEduInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Response;

/**
 * Created by liutao on 2017/8/22.
 */

public class RecruitInfoActivity extends BaseActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener,CheckBox.OnCheckedChangeListener
,AdapterView.OnItemSelectedListener{

    private Context mContext=this;
    private final int SUCCEED=10000;
    private final int SUCCEED_COMPANYPROPERTY=10001;
    private final int SUCCEED_EDU=10002;
    private final int SUCCEED_GZXZ=10003;
    private final int SUCCEED_GZBS=10004;
    private final int SUCCEED_AREA=10005;
    private final int SUCCEED_INDUSTRY_BIG=10006;
    private final int SUCCEED_INDUSTRY_SMALL=10007;
    private final int SUCCEED_OCC_BIG=10008;
    private final int SUCCEED_OCC_SMALL=10009;
    private final int SUCCEED_NODATA=10010;
    private final int  PROBLEM=10011;

    private int PageIndex=0;
    private String queryUrl,ModifyStartDateStr,ModifyEndDateStr,industryCode,occPid;

    private String comNameStr,jobNoStr,jobNameStr,incomeStartStr,incomeEndStr,ageStr;

    private boolean IsDirectInterview;//直接面试
    private boolean IsNewGraduates;//应届生
    private boolean IsDisabledPerson;//残疾人
    private boolean IsAssurance;//协保人员
    private int comPropertyId;//单位性质对应的id
    private int eduId;//文化程度对应的id
    private int industryClassId;//行业大类对应的id
    private int industryClassChildId;//行业小类对应的id
    private int zyflId;//职业大类对应的id
    private int zyflChildId;//职业小类对应的id
    private int gzxzId;//工作性质对应的id
    private int gzbsId;//工作班时对应的id
    private int areaIdOne,areaIdTwo,areaIdThree;//工作地区对应的id
    private Button queryBtn;

    private Spinner eduSp,companySp,commonSp,industryBigSp,industrySmallSp,occBigSp,occSmallSp,workSp,classSp,
            areaOneSp,areaTwoSp,areaThreeSp;

    private EditText companyEt,jobNoEt,jobNameEt,incomeStartEt,incomeEndEt,ageEt;

    private List<CompanyPropertyInfo> companySpData=new ArrayList<>();//"单位性质"
    private List<String> commonSpData=new ArrayList<>();//常见工种
    private String commonSpArray [];
    private List<RecruitEduInfo> eduSpData=new ArrayList<>();//文化程度
    private List<IndustryInfo> industryBigSpData=new ArrayList<>();//行业大类
    private List<IndustryInfo> industrySmallSpData=new ArrayList<>();//行业小类
    private List<OccInfo> occBigSpData=new ArrayList<>();//职业大类
    private List<OccInfo> occSmallSpData=new ArrayList<>();//职业小类
    private List<GzxzInfo> workSpData=new ArrayList<>();//工作性质
    private List<GzbsInfo> classSpData=new ArrayList<>(); //工作班时
    private List<AreaInfo> areaSpData=new ArrayList<>();//工作地区

    private RadioGroup rg;
    private CheckBox cbDirectInterview, cbNewGraduates, cbDisabledPerson,
            cbAssurance;

    private List<JobInfoListInfo> jobInfoList=new ArrayList<>();

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

                         switch (msg.what){

                 case SUCCEED:

                     jobInfoList.clear();
                    jobInfoList.addAll((List<JobInfoListInfo>)msg.obj);

                     Intent intent=new Intent(mContext,JobInfoListActivity.class);
                     intent.putExtra("JobInfoList",(Serializable)jobInfoList);
                     intent.putExtra("queryUrl",queryUrl);
                     startActivity(intent);

                     break;

                  case SUCCEED_COMPANYPROPERTY:

                      spSetAdapter("单位性质",msg);
                      getSpinnerInfo("文化程度");

                   break;
                  case SUCCEED_EDU:
                  spSetAdapter("文化程度",msg);
                      getSpinnerInfo("工作性质");
                                 break;

                             case SUCCEED_GZXZ:
                                 spSetAdapter("工作性质",msg);
                                 getSpinnerInfo("工作班时");
                                 break;

                             case SUCCEED_GZBS:
                                 spSetAdapter("工作班时",msg);
                                 getSpinnerInfo("工作地区");
                                 break;
                             case SUCCEED_AREA:
                                 spSetAdapter("工作地区",msg);
                                 getSpinnerInfo("行业大类");
                                 break;
                             case SUCCEED_INDUSTRY_BIG:
                                 spSetAdapter("行业大类",msg);
                                 getSpinnerInfo("职业大类");
                                 break;
                             case SUCCEED_OCC_BIG:
                                 spSetAdapter("职业大类",msg);
                                 break;
                             case SUCCEED_INDUSTRY_SMALL:
                                 spSetAdapter("行业小类",msg);

                                 break;
                             case SUCCEED_OCC_SMALL:
                                 spSetAdapter("职业小类",msg);

                                 break;
                 case PROBLEM:

                     Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();

                     break;

                             case SUCCEED_NODATA:
                                 break;
             }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recruit_info);

        initViews();


    }

    private void initViews(){

        //单位性质
        companySp= (Spinner) findViewById(R.id.sp_recruit_info_company_nature);
        companySp.setOnItemSelectedListener(this);
        getSpinnerInfo("单位性质");


      //常见工种
        commonSp= (Spinner) findViewById(R.id.sp_recruit_info_common_work);
        commonSp.setOnItemSelectedListener(this);
        commonSpArray=getResources().getStringArray(R.array.cboWorkType);

        for(String commonStr:commonSpArray){
            commonSpData.add(commonStr);
        }

        commonSp.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,commonSpData));

        //文化程度
        eduSp= (Spinner) findViewById(R.id.sp_recruit_info_edu);
        eduSp.setOnItemSelectedListener(this);
        //行业大类
        industryBigSp= (Spinner) findViewById(R.id.sp_recruit_info_industry_big);
        industryBigSp.setOnItemSelectedListener(this);
        //行业小类
        industrySmallSp= (Spinner) findViewById(R.id.sp_recruit_info_industry_small);
        industrySmallSp.setOnItemSelectedListener(this);
        //职业大类
        occBigSp= (Spinner) findViewById(R.id.sp_recruit_info_occupation_big);
        occBigSp.setOnItemSelectedListener(this);
        //职业小类
        occSmallSp= (Spinner) findViewById(R.id.sp_recruit_info_occupation_small);
        occSmallSp.setOnItemSelectedListener(this);
        //工作性质
        workSp= (Spinner) findViewById(R.id.sp_recruit_info_work_nature);
        workSp.setOnItemSelectedListener(this);
        //工作班时
        classSp= (Spinner) findViewById(R.id.sp_recruit_info_work_class);
        classSp.setOnItemSelectedListener(this);
        //工作地区1
        areaOneSp= (Spinner) findViewById(R.id.sp_recruit_info_work_area_one);
        areaOneSp.setOnItemSelectedListener(this);
        //工作地区2
        areaTwoSp= (Spinner) findViewById(R.id.sp_recruit_info_work_area_two);
        areaTwoSp.setOnItemSelectedListener(this);
        //工作地区3
        areaThreeSp= (Spinner) findViewById(R.id.sp_recruit_info_work_area_three);
        areaThreeSp.setOnItemSelectedListener(this);


        queryBtn= (Button) findViewById(R.id.btn_recruit_info_find);
        queryBtn.setOnClickListener(this);

        rg= (RadioGroup) findViewById(R.id.rg_recruit_info);
        rg.setOnCheckedChangeListener(this);
        rg.check(R.id.rb_recruit_info1);

        cbDirectInterview= (CheckBox) findViewById(R.id.cb_recruit_info1);
        cbNewGraduates= (CheckBox) findViewById(R.id.cb_recruit_info2);
        cbDisabledPerson= (CheckBox) findViewById(R.id.cb_recruit_info3);
        cbAssurance= (CheckBox) findViewById(R.id.cb_recruit_info4);

        cbDirectInterview.setOnCheckedChangeListener(this);
        cbNewGraduates.setOnCheckedChangeListener(this);
        cbDisabledPerson.setOnCheckedChangeListener(this);
        cbAssurance.setOnCheckedChangeListener(this);

        companyEt= (EditText) findViewById(R.id.et_recruit_info_company_name);//单位名称
        jobNoEt= (EditText) findViewById(R.id.et_recruit_info_job_no);//岗位编号
        jobNameEt= (EditText) findViewById(R.id.et_recruit_info_job_name);//岗位名称
        incomeStartEt= (EditText) findViewById(R.id.et_recruit_info_income_start);//收入上限
        incomeEndEt= (EditText) findViewById(R.id.et_recruit_info_income_end);//收入下限
        ageEt= (EditText) findViewById(R.id.et_recruit_info_age);//年龄

    }

    @Override
    public void onClick(View v) {

        comNameStr=companyEt.getText().toString().trim();//单位名称
        jobNoStr=jobNoEt.getText().toString().trim();//岗位编号
        jobNameStr=jobNameEt.getText().toString().trim();//岗位名称
        incomeStartStr=incomeStartEt.getText().toString().trim();//收入上限
        incomeEndStr=incomeEndEt.getText().toString().trim();//收入下限
        ageStr=ageEt.getText().toString().trim();//年龄

        if(TextUtils.equals("",incomeEndStr)){
            incomeEndStr="0";
        }
        if(TextUtils.equals("",incomeStartStr)){
            incomeStartStr="0";
        }
        if(TextUtils.equals("",ageStr)){
            ageStr="-1";
        }

        switch (v.getId()){

            case R.id.btn_recruit_info_find:

//                Toast.makeText(mContext,"单位名称=="+comNameStr,Toast.LENGTH_SHORT).show();
//                Toast.makeText(mContext,"岗位编号=="+jobNoStr,Toast.LENGTH_SHORT).show();
//                Toast.makeText(mContext,"岗位名称=="+jobNameStr,Toast.LENGTH_SHORT).show();
//                Toast.makeText(mContext,"收入上限=="+incomeStartStr,Toast.LENGTH_SHORT).show();
//                Toast.makeText(mContext,"收入下限=="+incomeEndStr,Toast.LENGTH_SHORT).show();
//                Toast.makeText(mContext,"年龄=="+ageStr,Toast.LENGTH_SHORT).show();

                //使软键盘隐藏
                //小米手机的bug，如果不隐藏键盘的话，会改变下一个界面的样子
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
               getJobListData();

                break;

        }

    }


    private void getSpinnerInfo(final String content){

        String urlSp = null;

        if(TextUtils.equals(content,"单位性质")){

            urlSp=MyOkHttpUtils.BaseUrl+"/Json/Get_CompanyProperty.aspx";

        }else if(TextUtils.equals(content,"文化程度")){
            urlSp=MyOkHttpUtils.BaseUrl+"/Json/Get_Edu.aspx";
        }else if(TextUtils.equals(content,"工作性质")) {
            urlSp = MyOkHttpUtils.BaseUrl + "/Json/Get_Gzxz.aspx";
        }else if(TextUtils.equals(content,"工作班时")) {
            urlSp = MyOkHttpUtils.BaseUrl + "/Json/Get_Gzbs.aspx";
        }else if(TextUtils.equals(content,"工作地区")) {
            urlSp = MyOkHttpUtils.BaseUrl + "/Json/Get_Area_Info.aspx";
        }else if(TextUtils.equals(content,"行业大类")) {
            urlSp = MyOkHttpUtils.BaseUrl + "/Json/Get_Industry_Class.aspx";
        }else if(TextUtils.equals(content,"职业大类")) {
            urlSp = MyOkHttpUtils.BaseUrl + "/Json/Get_Zyfl.aspx";
        }else if(TextUtils.equals(content,"行业小类")) {
            urlSp = MyOkHttpUtils.BaseUrl + "/Json/Get_Industry_Class_Child.aspx?parent_code="+industryCode;

        }else if(TextUtils.equals(content,"职业小类")) {
            urlSp = MyOkHttpUtils.BaseUrl + "/Json/Get_Zyfl_Child.aspx?parent_id="+occPid;

        }

       final String finalUrlSp = urlSp;
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        Response response=MyOkHttpUtils.okHttpGet(finalUrlSp);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String resStr=response.body().string();

                                if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")) {

                                    Gson gson = new Gson();
                                    if (TextUtils.equals(content, "单位性质")) {
                                        msg.obj = gson.fromJson(resStr, new TypeToken<List<CompanyPropertyInfo>>() {
                                        }.getType());
                                        msg.what = SUCCEED_COMPANYPROPERTY;
                                    } else if (TextUtils.equals(content, "文化程度")) {
                                        msg.obj = gson.fromJson(resStr, new TypeToken<List<RecruitEduInfo>>() {
                                        }.getType());
                                        msg.what = SUCCEED_EDU;
                                    } else if (TextUtils.equals(content, "工作性质")) {
                                        msg.obj = gson.fromJson(resStr, new TypeToken<List<GzxzInfo>>() {
                                        }.getType());
                                        msg.what = SUCCEED_GZXZ;
                                    } else if (TextUtils.equals(content, "工作班时")) {
                                        msg.obj = gson.fromJson(resStr, new TypeToken<List<GzbsInfo>>() {
                                        }.getType());
                                        msg.what = SUCCEED_GZBS;
                                    } else if (TextUtils.equals(content, "工作地区")) {
                                        msg.obj = gson.fromJson(resStr, new TypeToken<List<AreaInfo>>() {
                                        }.getType());
                                        msg.what = SUCCEED_AREA;
                                    } else if (TextUtils.equals(content, "行业大类")) {
                                        msg.obj = gson.fromJson(resStr, new TypeToken<List<IndustryInfo>>() {
                                        }.getType());
                                        msg.what = SUCCEED_INDUSTRY_BIG;
                                    } else if (TextUtils.equals(content, "职业大类")) {
                                        msg.obj = gson.fromJson(resStr, new TypeToken<List<OccInfo>>() {
                                        }.getType());
                                        msg.what = SUCCEED_OCC_BIG;
                                    } else if (TextUtils.equals(content, "行业小类")) {
                                        msg.obj = gson.fromJson(resStr, new TypeToken<List<IndustryInfo>>() {
                                        }.getType());

                                        msg.what = SUCCEED_INDUSTRY_SMALL;
                                    } else if (TextUtils.equals(content, "职业小类")) {
                                        msg.obj = gson.fromJson(resStr, new TypeToken<List<OccInfo>>() {
                                        }.getType());

                                        msg.what = SUCCEED_OCC_SMALL;
                                    }
                                }
                                mHandler.sendMessage(msg);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else{

                            msg.what=PROBLEM;
                            mHandler.sendMessage(msg);

                        }

                    }
                }

        ).start();

    }

    private void spSetAdapter(String type,Message msg){

         switch (type){

             case "单位性质":

                 companySpData.clear();
                 companySpData.add(new CompanyPropertyInfo(-1,"请选择"));
                 companySpData.addAll((List<CompanyPropertyInfo>)msg.obj);
                 companySp.setAdapter(new ArrayAdapter<CompanyPropertyInfo>(mContext,android.R.layout.simple_list_item_1,companySpData));

                 break;

             case "文化程度":

                 eduSpData.clear();
                 eduSpData.add(new RecruitEduInfo("请选择",-1));
                 eduSpData.addAll((List<RecruitEduInfo>)msg.obj);
                 eduSp.setAdapter(new ArrayAdapter<RecruitEduInfo>(mContext,android.R.layout.simple_list_item_1,eduSpData));

                 break;

             case "工作性质":

                 workSpData.clear();
                 workSpData.add(new GzxzInfo(-1,null,"请选择"));
                 workSpData.addAll((List<GzxzInfo>)msg.obj);
                 workSp.setAdapter(new ArrayAdapter<GzxzInfo>(mContext,android.R.layout.simple_list_item_1,workSpData));

                 break;

             case "工作班时":

                 classSpData.clear();
                 classSpData.add(new GzbsInfo(null,-1,"请选择"));
                 classSpData.addAll((List<GzbsInfo>)msg.obj);
                 classSp.setAdapter(new ArrayAdapter<GzbsInfo>(mContext,android.R.layout.simple_list_item_1,classSpData));

                 break;

             case "工作地区":

                 areaSpData.clear();
                 areaSpData.add(new AreaInfo(null,-1,"请选择"));
                 areaSpData.addAll((List<AreaInfo>)msg.obj);
                 areaOneSp.setAdapter(new ArrayAdapter<AreaInfo>(mContext,android.R.layout.simple_list_item_1,areaSpData));
                 areaTwoSp.setAdapter(new ArrayAdapter<AreaInfo>(mContext,android.R.layout.simple_list_item_1,areaSpData));
                 areaThreeSp.setAdapter(new ArrayAdapter<AreaInfo>(mContext,android.R.layout.simple_list_item_1,areaSpData));

                 break;

             case "行业大类":

                 industryBigSpData.clear();
                 industryBigSpData.add(new IndustryInfo(null,-1,"请选择"));
                 industryBigSpData.addAll((List<IndustryInfo>)msg.obj);
                 industryBigSp.setAdapter(new ArrayAdapter<IndustryInfo>(mContext,android.R.layout.simple_list_item_1, industryBigSpData));


                 break;

             case "行业小类":

                 industrySmallSpData.clear();
                 industrySmallSpData.add(new IndustryInfo(null,-1,"请选择"));
                 industrySmallSpData.addAll((List<IndustryInfo>)msg.obj);
                 industrySmallSp.setAdapter(new ArrayAdapter<IndustryInfo>(mContext,android.R.layout.simple_list_item_1, industrySmallSpData));


                 break;

             case "职业大类":

                 occBigSpData.clear();
                 occBigSpData.add(new OccInfo(null,-1,"请选择",null));
                 occBigSpData.addAll((List<OccInfo>)msg.obj);
                 occBigSp.setAdapter(new ArrayAdapter<OccInfo>(mContext,android.R.layout.simple_list_item_1, occBigSpData));


                 break;

             case "职业小类":

                 occSmallSpData.clear();
                 occSmallSpData.add(new OccInfo(null,-1,"请选择",null));
                 occSmallSpData.addAll((List<OccInfo>)msg.obj);
                 occSmallSp.setAdapter(new ArrayAdapter<OccInfo>(mContext,android.R.layout.simple_list_item_1, occSmallSpData));


                 break;

         }

    }

    private void getJobListData(){

               // http://web.youli.pw:89/Json/GetJobs_Search.aspx?PageRecCnts=15&ZyflId=-1&Age=-1&GZXZId=-1&ZyflChildId=-1&JobName=&IsDisabledPerson=false&IsDirectInterview=false&ComPropertyId=-1&JobNo=&ModifyStartDate=2010-01-01&IsAssurance=false&EndSalary=0&IndustryClassChildId=-1&ComName=&GZBSId=-1&EduID=-1&AreaId3=-1&AreaId2=-1&AreaId1=-1&ModifyEndDate=2030-01-01&StartSalary=0&IndustryClassId=-1&IsNewGraduates=false&PageIndex=0

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        queryUrl=MyOkHttpUtils.BaseUrl+"/Json/GetJobs_Search.aspx?PageRecCnts=15&ZyflId="+zyflId+"&Age="+ageStr+"&GZXZId="+gzxzId+"&ZyflChildId="+zyflChildId+"&JobName="+jobNameStr+"&IsDisabledPerson="+IsDisabledPerson+"&IsDirectInterview="+IsDirectInterview+"&ComPropertyId="+comPropertyId+"&JobNo="+jobNoStr+"&ModifyStartDate="+ModifyStartDateStr+"&IsAssurance="+IsAssurance+"&EndSalary="+incomeEndStr+"&IndustryClassChildId="+ industryClassChildId+"&ComName="+comNameStr+"&GZBSId="+gzbsId+"&EduID="+eduId+"&AreaId3="+areaIdThree+"&AreaId2="+areaIdTwo+"&AreaId1="+areaIdOne+"&ModifyEndDate="+ModifyEndDateStr+"&StartSalary="+incomeStartStr+"&IndustryClassId="+industryClassId+"&IsNewGraduates="+IsNewGraduates+"&PageIndex=";

                       String url= queryUrl+PageIndex;


                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String resStr=response.body().string();

                                if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")) {

                                    Gson gson = new Gson();

                                    msg.obj = gson.fromJson(resStr, new TypeToken<List<JobInfoListInfo>>() {
                                    }.getType());
                                    msg.what = SUCCEED;
                                }else{
                                    msg.what = SUCCEED_NODATA;
                                }
                                mHandler.sendMessage(msg);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else{

                            msg.what=PROBLEM;
                            mHandler.sendMessage(msg);

                        }

                    }
                }

        ).start();

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {


        switch (checkedId){

            case R.id.rb_recruit_info1:
                setTime("无要求");
                break;
            case R.id.rb_recruit_info2:
                setTime("当天");
                break;
            case R.id.rb_recruit_info3:
                setTime("最近3天");
                break;
            case R.id.rb_recruit_info4:
                setTime("最近一周");
                break;
            case R.id.rb_recruit_info5:
                setTime("最近一月");
                break;

        }

    }


    private void setTime(String type){

        Calendar calender=Calendar.getInstance();

        if(TextUtils.equals(type,"无要求")){
            ModifyStartDateStr="2010-01-01";
             ModifyEndDateStr="2030-01-01";
        }else{
            ModifyEndDateStr=calender.get(Calendar.YEAR)+"-"+(calender.get(Calendar.MONTH)+1)+"-"+calender.get(Calendar.DATE);
            if(TextUtils.equals(type,"当天")){
                ModifyStartDateStr=ModifyEndDateStr;
            }else if(TextUtils.equals(type,"最近3天")){
                ModifyStartDateStr=calender.get(Calendar.YEAR)+"-"+(calender.get(Calendar.MONTH)+1)+"-"+(calender.get(Calendar.DATE)-3);
            }else if(TextUtils.equals(type,"最近一周")){
                ModifyStartDateStr=calender.get(Calendar.YEAR)+"-"+(calender.get(Calendar.MONTH)+1)+"-"+(calender.get(Calendar.DATE)-7);
            }else if(TextUtils.equals(type,"最近一月")){
                ModifyStartDateStr=calender.get(Calendar.YEAR)+"-"+calender.get(Calendar.MONTH)+"-"+(calender.get(Calendar.DATE));
            }
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()){

            case R.id.cb_recruit_info1:

                if(isChecked){
                    IsDirectInterview=true;
                }else{
                    IsDirectInterview=false;
                }

                break;

            case R.id.cb_recruit_info2:


                if(isChecked){
                    IsNewGraduates=true;
                }else{
                    IsNewGraduates=false;
                }

                break;

            case R.id.cb_recruit_info3:


                if(isChecked){
                    IsDisabledPerson=true;
                }else{
                    IsDisabledPerson=false;
                }

                break;

            case R.id.cb_recruit_info4:


                if(isChecked){
                    IsAssurance=true;
                }else{
                  IsAssurance=false;
                }

                break;
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){

            case R.id.sp_recruit_info_company_nature://单位性质

                comPropertyId=((CompanyPropertyInfo)companySp.getSelectedItem()).getCompropertyid();

                break;

            case R.id.sp_recruit_info_common_work://常见工种

                jobNameStr=(String)commonSp.getSelectedItem();
                if(!TextUtils.equals("请选择",jobNameStr)) {
                    jobNameEt.setText(jobNameStr);
                }

                break;

            case R.id.sp_recruit_info_edu://文化程度

                eduId=((RecruitEduInfo)eduSp.getSelectedItem()).getId();

                break;

            case R.id.sp_recruit_info_industry_big://行业大类

                String code=((IndustryInfo)industryBigSp.getSelectedItem()).getCode();

                industryClassId=((IndustryInfo)industryBigSp.getSelectedItem()).getId();

                if(TextUtils.equals("请选择",((IndustryInfo)industryBigSp.getSelectedItem()).getName())){

                    industryClassChildId=-1;
                }
                if(code!=null){

                    industryCode=code;

                    getSpinnerInfo("行业小类");

                }

                break;

            case R.id.sp_recruit_info_industry_small://行业小类

                industryClassChildId=((IndustryInfo)industrySmallSp.getSelectedItem()).getId();
                if(TextUtils.equals("请选择",((IndustryInfo)industryBigSp.getSelectedItem()).getName())){

                    industryClassChildId=-1;
                }
                break;

            case R.id.sp_recruit_info_occupation_big://职业大类

                String pid=((OccInfo)occBigSp.getSelectedItem()).getCode();

                zyflId=((OccInfo)occBigSp.getSelectedItem()).getId();

                if(TextUtils.equals("请选择",((OccInfo)occBigSp.getSelectedItem()).getName())){

                    zyflChildId=-1;
                }

                if(pid!=null){

                    occPid=pid;

                    getSpinnerInfo("职业小类");
                }

                break;

            case R.id.sp_recruit_info_occupation_small://职业小类

                zyflChildId=((OccInfo)occSmallSp.getSelectedItem()).getId();
                if(TextUtils.equals("请选择",((OccInfo)occSmallSp.getSelectedItem()).getName())){

                    zyflChildId=-1;
                }
                break;

            case R.id.sp_recruit_info_work_nature://工作性质

                gzxzId=((GzxzInfo)workSp.getSelectedItem()).getId();

                break;

            case R.id.sp_recruit_info_work_class://工作班时

                gzbsId=((GzbsInfo)classSp.getSelectedItem()).getId();

                break;

            case R.id.sp_recruit_info_work_area_one://工作地区1
                areaIdOne=((AreaInfo)areaOneSp.getSelectedItem()).getAreaid();
                break;
            case R.id.sp_recruit_info_work_area_two://工作地区2
                areaIdTwo=((AreaInfo)areaTwoSp.getSelectedItem()).getAreaid();
                break;
            case R.id.sp_recruit_info_work_area_three://工作地区3
                areaIdThree=((AreaInfo)areaThreeSp.getSelectedItem()).getAreaid();
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

package com.youli.zbetuch.jingan.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.entity.ChildJobPostInfo;
import com.youli.zbetuch.jingan.entity.JobPostInfo;
import com.youli.zbetuch.jingan.entity.PersonReInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by ZHengBin on 2017/8/26.
 */

public class PersonReActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    private Context mContext=PersonReActivity.this;

    private final int SUCCESS=10000;
    private final int SUCCESS_UPLOAD=10001;//上传个人简历
    private final int SUCCESS_NODATA=10002;
    private final int  PROBLEM=10003;

    private List<PersonReInfo> data=new ArrayList<>();

    private String sfzStr;
    private TextView tvSubmit;
    private Spinner workNatureSp;//用工性质
    private String workNatureSpArray [];
    private Spinner workClassSp;//工作班时
    private String workClassSpArray [];
    private Spinner  workAreaOneSp;//工作地区1
    private String workAreaOneSpArray [];
    private Spinner  workAreaTwoSp;//工作地区2
    private String workAreaTwoSpArray [];
    private Spinner  workAreaThreeSp;//工作地区3
    private String workAreaThreeSpArray [];
    private Spinner wantWorkOneSp,wantWorkOneDetailSp;//欲从事岗位1
    private List<JobPostInfo> wantWorkOneSpData=new ArrayList<>();
    private List<ChildJobPostInfo> wantWorkOneDetailSpData=new ArrayList<>();
    private Spinner wantWorkTwoSp,wantWorkTwoDetailSp;//欲从事岗位2
    private List<JobPostInfo> wantWorkTwoSpData=new ArrayList<>();
    private List<ChildJobPostInfo> wantWorkTwoDetailSpData=new ArrayList<>();
    private EditText wantWorkThreeEt;//欲从事岗位3
    private Spinner wantSalarySp;//期望薪资
    private String wantSalarySpArray [];
    private Spinner computerLevelSp;//计算机应用能力
    private String computerLevelSpArray [];
    private EditText computerCertEt;//计算机证书
    private Spinner languageOneSp,languageProfOneSp;//外语语种1和熟练程度
    private String languageOneSpArray [];
    private String languageProfOneSpArray [];
    private Spinner languageTwoSp,languageProfTwoSp;//外语语种2和熟练程度
    private String languageTwoSpArray [];
    private String languageProfTwoSpArray [];
    private EditText languageCertEt;//外语类证书
    private EditText otherCertsEt;//其他职业技能书
    private EditText selfEvaluationEt;//自我评价

    //要上传的参数============================
    private String workNatureStr;//用工性质
    private String workClassStr;//工作班时
    private String   workAreaOneStr,workAreaTwoStr,workAreaThreeStr;//工作地区1,2,3
    private String wantWorkOneStr,wantWorkOneDetailStr;//欲从事岗位1
    private String wantWorkTwoStr,wantWorkTwoDetailStr;//欲从事岗位2
    private String wantWorkThreeStr;//欲从事岗位3
    private String computerLevelStr;//计算机应用能力
    private String computerCertStr;//计算机证书
    private String languageOneStr,languageProfOneStr;//外语语种1和熟练程度
    private String languageTwoStr,languageProfTwoStr;//外语语种2和熟练程度
    private String languageCertStr;//外语类证书
    private String otherCertsStr;//其他职业技能书
    private String selfEvaluationStr;//自我评价
    private String wantSalaryStr,startSalaryStr,endSalaryStr;//期望薪资

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS:

                    data.clear();
                    data.addAll((List<PersonReInfo> )msg.obj);
                    if(data!=null&&data.size()>0) {

                       spinnerCheckTo(data,workNatureSp);//用工性质
                        //    workNatureTv.setText(data.get(0).getGZXZNAME());
                        spinnerCheckTo(data,workClassSp);//工作班时
                        //   workClassTv.setText(data.get(0).getGZBSNAME());
                        spinnerCheckTo(data,workAreaOneSp);//工作地区1
                    //    workAreaOneTv.setText(data.get(0).getAREAID_1());
                        spinnerCheckTo(data,workAreaTwoSp);//工作地区2
                      //  workAreaTwoTv.setText(data.get(0).getAREAID_2());
                        spinnerCheckTo(data,workAreaThreeSp);//工作地区3
                       // workAreaThreeTv.setText(data.get(0).getAREAID_3());
                        spinnerCheckTo(data,wantWorkOneSp);//欲从事岗位1
                      //  wantWorkOneTv.setText(data.get(0).getZYFLID_1());
                       // spinnerCheckTo(data,wantWorkOneDetailSp);//欲从事岗位详细1
                       // wantWorkOneDetailTv.setText(data.get(0).getZYFLCHILDID_1());
                        spinnerCheckTo(data,wantWorkTwoSp);//欲从事岗位2
                      //  wantWorkTwoTv.setText(data.get(0).getZYFLID_2());
                       // wantWorkTwoDetailTv.setText(data.get(0).getZYFLCHILDID_2());
                        wantWorkThreeEt.setText("\t\t"+data.get(0).getOTHERZYFL()+"\t\t");

                        spinnerCheckTo(data,wantSalarySp);//期望薪资
//                        if (data.get(0).getENDSALARY() == -1) {
//                            wantSalaryTv.setText(data.get(0).getSTARTSALARY() + "及以上");
//                        } else {
//                            wantSalaryTv.setText(data.get(0).getSTARTSALARY() + "-" + data.get(0).getENDSALARY());
//                        }

                        spinnerCheckTo(data,computerLevelSp);//计算机应用能力
                    //    computerLevelTv.setText(data.get(0).getCOMPUTERLEVELID());
                        computerCertEt.setText("\t\t"+data.get(0).getCOMPUTERCERT()+"\t\t");

                        spinnerCheckTo(data,languageOneSp);//外语语种1
                    //    languageOneTv.setText(data.get(0).getLANGUAGEID_1());
                        spinnerCheckTo(data,languageProfOneSp);//熟练程度
                      //  languageProfOneTv.setText(data.get(0).getLANGUAGEPROFICIENCYID_1());
                        spinnerCheckTo(data,languageTwoSp);//外语语种2
                     //   languageTwoTv.setText(data.get(0).getLANGUAGEID_2());
                        spinnerCheckTo(data,languageProfTwoSp);//熟练程度
                       // languageProfTwoTv.setText(data.get(0).getLANGUAGEPROFICIENCYID_2());
                        languageCertEt.setText("\t\t"+data.get(0).getLANGUAGECERT()+"\t\t");
                        otherCertsEt.setText("\t\t"+data.get(0).getOTHERCERTS()+"\t\t");
                        selfEvaluationEt.setText(data.get(0).getSELFEVALUATION());
                    }

                    break;


                case SUCCESS_UPLOAD:

                    if(TextUtils.equals("True",(String)msg.obj)){
                        Toast.makeText(mContext,"上传成功",Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new PersonReInfo());//注意这里post()里面一定要传一个对象，否则就会报错
                    }

                    break;
                case PROBLEM:

                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();

                    break;

                case SUCCESS_NODATA:


                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_resume);

        sfzStr=getIntent().getStringExtra("SFZ");

        initViews();
    }

    private void initViews(){

         workNatureSp= (Spinner) findViewById(R.id.sp_person_re_work_nature);//用工性质
        workNatureSp.setOnItemSelectedListener(this);
        workNatureSpArray=getResources().getStringArray(R.array.personyonggongxingzi);
        workNatureSp.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,workNatureSpArray));

         workClassSp= (Spinner) findViewById(R.id.sp_person_re_work_class);//工作班时
        workClassSp.setOnItemSelectedListener(this);
        workClassSpArray=getResources().getStringArray(R.array.personworktime);
        workClassSp.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,workClassSpArray));

         workAreaOneSp= (Spinner) findViewById(R.id.sp_person_re_work_area_one);//工作地区1
        workAreaOneSp.setOnItemSelectedListener(this);
        workAreaOneSpArray=getResources().getStringArray(R.array.personaddress);
        workAreaOneSp.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,workAreaOneSpArray));


          workAreaTwoSp= (Spinner) findViewById(R.id.sp_person_re_work_area_two);//工作地区2
        workAreaTwoSp.setOnItemSelectedListener(this);
        workAreaTwoSpArray=getResources().getStringArray(R.array.personaddress);
        workAreaTwoSp.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,workAreaTwoSpArray));

          workAreaThreeSp= (Spinner) findViewById(R.id.sp_person_re_work_area_three);//工作地区3
        workAreaThreeSp.setOnItemSelectedListener(this);
        workAreaThreeSpArray=getResources().getStringArray(R.array.personaddress);
        workAreaThreeSp.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,workAreaThreeSpArray));
         wantWorkOneSp= (Spinner) findViewById(R.id.sp_person_re_want_work_one);
        wantWorkOneSp.setOnItemSelectedListener(this);
        wantWorkOneSpData.clear();
        wantWorkOneSpData.addAll(new JobPostInfo().getJobPost());
        wantWorkOneSp.setAdapter(new ArrayAdapter<JobPostInfo>(mContext,android.R.layout.simple_list_item_1,wantWorkOneSpData));


        wantWorkOneDetailSp= (Spinner) findViewById(R.id.sp_person_re_want_work_one_detail);//欲从事岗位1
        wantWorkOneDetailSp.setOnItemSelectedListener(this);

         wantWorkTwoSp= (Spinner) findViewById(R.id.sp_person_re_want_work_two);
        wantWorkTwoSp.setOnItemSelectedListener(this);
        wantWorkTwoSpData.clear();
        wantWorkTwoSpData.addAll(new JobPostInfo().getJobPost());

        wantWorkTwoSp.setAdapter(new ArrayAdapter<JobPostInfo>(mContext,android.R.layout.simple_list_item_1,wantWorkTwoSpData));
        wantWorkTwoDetailSp= (Spinner) findViewById(R.id.sp_person_re_want_work_two_detail);//欲从事岗位2
        wantWorkTwoDetailSp.setOnItemSelectedListener(this);


         wantWorkThreeEt= (EditText) findViewById(R.id.et_person_re_want_work_three);//欲从事岗位3


         wantSalarySp= (Spinner) findViewById(R.id.sp_person_re_want_salary);//期望薪资
        wantSalarySp.setOnItemSelectedListener(this);
        wantSalarySpArray=getResources().getStringArray(R.array.personxinzi);
        wantSalarySp.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,wantSalarySpArray));


         computerLevelSp= (Spinner) findViewById(R.id.sp_person_re_computer_level);//计算机应用能力
        computerLevelSp.setOnItemSelectedListener(this);
        computerLevelSpArray=getResources().getStringArray(R.array.personSkills);
        computerLevelSp.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,computerLevelSpArray));
         computerCertEt= (EditText) findViewById(R.id.et_person_re_computer_cert);//计算机证书

         languageOneSp= (Spinner) findViewById(R.id.sp_person_re_language_one);
        languageOneSp.setOnItemSelectedListener(this);
        languageOneSpArray=getResources().getStringArray(R.array.personLanguage);
        languageOneSp.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,languageOneSpArray));

        languageProfOneSp= (Spinner) findViewById(R.id.sp_person_re_language_proficiency_one);//外语语种1和熟练程度
        languageProfOneSp.setOnItemSelectedListener(this);
        languageProfOneSpArray=getResources().getStringArray(R.array.personSkills);
        languageProfOneSp.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,languageProfOneSpArray));

         languageTwoSp= (Spinner) findViewById(R.id.sp_person_re_language_two);
        languageTwoSp.setOnItemSelectedListener(this);
        languageTwoSpArray=getResources().getStringArray(R.array.personLanguage);
        languageTwoSp.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,languageTwoSpArray));

        languageProfTwoSp= (Spinner) findViewById(R.id.sp_person_re_language_proficiency_two);//外语语种2和熟练程度
        languageProfTwoSp.setOnItemSelectedListener(this);
        languageProfTwoSpArray=getResources().getStringArray(R.array.personSkills);
        languageProfTwoSp.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,languageProfTwoSpArray));

         languageCertEt= (EditText) findViewById(R.id.et_person_re_language_cert);//外语类证书
         otherCertsEt= (EditText) findViewById(R.id.et_person_re_other_certs);//其他职业技能书
         selfEvaluationEt= (EditText) findViewById(R.id.et_person_resume_self_evaluation);//自我评价

        tvSubmit= (TextView) findViewById(R.id.tv_person_resume_submit);
        tvSubmit.setOnClickListener(this);

        initDatas();
    }

    private void initDatas(){

        // http://web.youli.pw:89/Json/Get_Resumes_Info.aspx?sfz=310108198004026642

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String resUrl= MyOkHttpUtils.BaseUrl+"/Json/Get_Resumes_Info.aspx?sfz="+sfzStr;

                        Response response=MyOkHttpUtils.okHttpGet(resUrl);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String resStr=response.body().string();

                                if(!TextUtils.equals("",resStr)&&!TextUtils.equals("[]",resStr)){

                                    Gson gson=new Gson();

                                    msg.obj=gson.fromJson(resStr,new TypeToken<List<PersonReInfo>>(){}.getType());

                                    msg.what=SUCCESS;
                                    mHandler.sendMessage(msg);

                                }else{
                                    msg.what= SUCCESS_NODATA;
                                    mHandler.sendMessage(msg);

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else{

                            msg.what= PROBLEM;
                            mHandler.sendMessage(msg);

                        }

                    }
                }

        ).start();


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tv_person_resume_submit:

                submitDialog();

            break;

        }

    }

    //给Spinner控件赋值
    private void spinnerCheckTo(List<PersonReInfo> data,Spinner spinner){

        if(spinner==workNatureSp){
            for(int i=0;i<workNatureSpArray.length;i++){
                if(TextUtils.equals(data.get(0).getGZXZNAME(),workNatureSpArray[i])){
                    spinner.setSelection(i);}}
        }else if(spinner==workClassSp){
            for(int i=0;i<workClassSpArray.length;i++){
                if(TextUtils.equals(data.get(0).getGZBSNAME(),workClassSpArray[i])){
                    spinner.setSelection(i);}}
        }else if(spinner==workAreaOneSp){
            for(int i=0;i<workAreaOneSpArray.length;i++){
                if(TextUtils.equals(data.get(0).getAREAID_1(),workAreaOneSpArray[i])){
                    spinner.setSelection(i);}}
        }else if(spinner==workAreaTwoSp){
            for(int i=0;i<workAreaTwoSpArray.length;i++){
                if(TextUtils.equals(data.get(0).getAREAID_2(),workAreaTwoSpArray[i])){
                    spinner.setSelection(i);}}
        }else if(spinner==workAreaThreeSp){
            for(int i=0;i<workAreaThreeSpArray.length;i++){
                if(TextUtils.equals(data.get(0).getAREAID_3(),workAreaThreeSpArray[i])){
                    spinner.setSelection(i);}}
        }else if(spinner==wantSalarySp){
            for(int i=0;i<wantSalarySpArray.length;i++){
                  if(data.get(0).getENDSALARY()==-1){
                      spinner.setSelection(0);
                  }else if(data.get(0).getENDSALARY()==0){
                      if(data.get(0).getSTARTSALARY()==0){
                          spinner.setSelection(0);
                      }else{
                          spinner.setSelection(wantSalarySpArray.length-1);
                      }
                  }else{
                      if(TextUtils.equals(data.get(0).getSTARTSALARY()+"-"+data.get(0).getENDSALARY(),wantSalarySpArray[i])){
                            spinner.setSelection(i);
                        }

                  }

            }
        }else if(spinner==computerLevelSp) {
            for (int i = 0; i < computerLevelSpArray.length; i++) {
                if (TextUtils.equals(data.get(0).getCOMPUTERLEVELID(), computerLevelSpArray[i])) {
                    spinner.setSelection(i);}}
        }else if(spinner==languageOneSp) {
            for (int i = 0; i < languageOneSpArray.length; i++) {
                if (TextUtils.equals(data.get(0).getLANGUAGEID_1(), languageOneSpArray[i])) {
                    spinner.setSelection(i);}}
        }else if(spinner==languageProfOneSp) {
            for (int i = 0; i < languageProfOneSpArray.length; i++) {
                if (TextUtils.equals(data.get(0).getLANGUAGEPROFICIENCYID_1(), languageProfOneSpArray[i])) {
                    spinner.setSelection(i);}}
        }else if(spinner==languageTwoSp) {
            for (int i = 0; i < languageTwoSpArray.length; i++) {
                if (TextUtils.equals(data.get(0).getLANGUAGEID_2(), languageTwoSpArray[i])) {
                    spinner.setSelection(i);}}
        }else if(spinner==languageProfTwoSp) {
            for (int i = 0; i < languageProfTwoSpArray.length; i++) {
                if (TextUtils.equals(data.get(0).getLANGUAGEPROFICIENCYID_2(), languageProfTwoSpArray[i])) {
                    spinner.setSelection(i);}}
        }else if(spinner==wantWorkOneSp) {
            for (int i = 0; i < wantWorkOneSpData.size(); i++) {
                if (TextUtils.equals(data.get(0).getZYFLID_1(), wantWorkOneSpData.get(i).getJobName())) {
                    spinner.setSelection(i);}

            }
        }else if(spinner==wantWorkTwoSp) {
            for (int i = 0; i < wantWorkTwoSpData.size(); i++) {
                if (TextUtils.equals(data.get(0).getZYFLID_2(), wantWorkTwoSpData.get(i).getJobName())) {
                    spinner.setSelection(i);}

            }
        }else if(spinner==wantWorkOneDetailSp) {
            if (wantWorkOneDetailSpData.size() != 0) {
            for (int i = 0; i <wantWorkOneDetailSpData.size(); i++) {

                if(data!=null&&data.size()>0) {
                    if (TextUtils.equals(data.get(0).getZYFLCHILDID_1(), wantWorkOneDetailSpData.get(i).getChildJobName())) {
                        spinner.setSelection(i);
                    }
                }
                }
            }
        }else if(spinner==wantWorkTwoDetailSp) {
            if(wantWorkTwoDetailSpData.size()!=0) {
            for (int i = 0; i <wantWorkTwoDetailSpData.size(); i++) {
                if(data!=null&&data.size()>0) {
                    if (TextUtils.equals(data.get(0).getZYFLCHILDID_2(), wantWorkTwoDetailSpData.get(i).getChildJobName())) {
                        spinner.setSelection(i);
                    }
                }
                }

            }
        }
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        switch (parent.getId()){

            case R.id.sp_person_re_work_nature://工作性质

                workNatureStr=(String)workNatureSp.getSelectedItem();
                break;
            case R.id.sp_person_re_work_class://工作班时

                workClassStr=(String)workClassSp.getSelectedItem();
                break;

            case R.id.sp_person_re_work_area_one://工作地区1

                workAreaOneStr=(String)workAreaOneSp.getSelectedItem();

                break;

            case R.id.sp_person_re_work_area_two://工作地区2

                workAreaTwoStr=(String)workAreaTwoSp.getSelectedItem();
                break;

            case R.id.sp_person_re_work_area_three://工作地区3
                workAreaThreeStr=(String)workAreaThreeSp.getSelectedItem();

                break;

            case R.id.sp_person_re_want_work_one://欲从事岗位1

                     jobPostFindChildJobPost(((JobPostInfo) wantWorkOneSp.getSelectedItem()).getJobCode(),wantWorkOneDetailSpData,wantWorkOneDetailSp);

                wantWorkOneStr=((JobPostInfo) wantWorkOneSp.getSelectedItem()).getJobName();

                break;

            case R.id.sp_person_re_want_work_one_detail://欲从事岗位1详情

                wantWorkOneDetailStr=((ChildJobPostInfo)wantWorkOneDetailSp.getSelectedItem()).getChildJobName();

                break;

            case R.id.sp_person_re_want_work_two://欲从事岗位2
                jobPostFindChildJobPost(((JobPostInfo) wantWorkTwoSp.getSelectedItem()).getJobCode(),wantWorkTwoDetailSpData,wantWorkTwoDetailSp);
                wantWorkTwoStr=((JobPostInfo) wantWorkTwoSp.getSelectedItem()).getJobName();

                break;

            case R.id.sp_person_re_want_work_two_detail://欲从事岗位2详情

                wantWorkTwoDetailStr=((ChildJobPostInfo)wantWorkTwoDetailSp.getSelectedItem()).getChildJobName();

                break;

            case R.id.sp_person_re_want_salary://期望薪资

                wantSalaryStr=(String)wantSalarySp.getSelectedItem();

                if(wantSalaryStr.indexOf("请选择")!=-1){//包含"请选择"
                    startSalaryStr="0";
                    endSalaryStr="0";
                }else{

                    if(wantSalaryStr.indexOf("以上")!=-1){
                          startSalaryStr=wantSalaryStr.replace("以上","");
                        endSalaryStr="0";

                    }else{

                        if(wantSalaryStr.indexOf("-")!=-1){
                            startSalaryStr=wantSalaryStr.split("-")[0];
                            endSalaryStr=wantSalaryStr.split("-")[1];
                        }

                    }

                }

                break;

            case R.id.sp_person_re_computer_level://计算机应用能力
                computerLevelStr=(String)computerLevelSp.getSelectedItem();
                break;

            case R.id.sp_person_re_language_one://外语语种1
                languageOneStr=(String)languageOneSp.getSelectedItem();
                break;

            case R.id.sp_person_re_language_proficiency_one://熟练程度语种1
                languageProfOneStr=(String)languageProfOneSp.getSelectedItem();
                break;

            case R.id.sp_person_re_language_two://外语语种2
                languageTwoStr=(String)languageTwoSp.getSelectedItem();
                break;

            case R.id.sp_person_re_language_proficiency_two://熟练程度语种2
                languageProfTwoStr=(String)languageProfTwoSp.getSelectedItem();

                break;


        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    //根据父岗位的编码找到子岗位
    private void jobPostFindChildJobPost(String jobCode,List<ChildJobPostInfo> childSpData ,Spinner childSp){

        List<ChildJobPostInfo> childList=new ArrayList<>();

        childList.clear();
        childSpData.clear();

            childList.addAll(new ChildJobPostInfo().getChildJobInfo());

        for(int i=0;i<childList.size();i++){

            if(TextUtils.equals(jobCode,childList.get(i).getJobCode())){

                childSpData.add(childList.get(i));

                childSp.setAdapter(new ArrayAdapter<ChildJobPostInfo>(mContext,android.R.layout.simple_list_item_1,childSpData));

            }

        }

        spinnerCheckTo(data,childSp);
    }

    //提交对话框
    private void submitDialog(){

        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("上传信息提示");
        builder.setMessage("您确定上传简历信息吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                submitInfo();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

//    修改个人简历
//    http://web.youli.pw:89/Json/Set_Resumes_Info.aspx
//
//            [{"SELFEVALUATION":"","COMPUTERLEVELID":"请选择","GZXZID":"请选择","LANGUAGEID_1":"请选择",
// "LANGUAGEPROFICIENCYID_1":"请选择","LANGUAGEID_2":"请选择","IDNO":"310108198004026642","LANGUAGEPROFICIENCYID_2":"请选择",
// "ENDSALARY":"8000","GZBSID":"请选择","LANGUAGECERT":"","COMPUTERCERT":"","AREAID_3":"请选择","OTHERZYFL":"","GPS":"0.0,0.0",
// "ZYFLCHILDID_1":"","ZYFLID_1":"请选择","STARTSALARY":"5100","ZYFLCHILDID_2":"请选择","OTHERCERTS":"","ZYFLID_2":"请选择",
// "AREAID_2":"请选择","AREAID_1":"请选择"}]
    private void submitInfo(){

        wantWorkThreeStr=wantWorkThreeEt.getText().toString().trim();
        computerCertStr=computerCertEt.getText().toString().trim();
        languageCertStr=languageCertEt.getText().toString().trim();
        otherCertsStr=otherCertsEt.getText().toString().trim();
        selfEvaluationStr=selfEvaluationEt.getText().toString().trim();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObj=new JSONObject();
        try {
            jsonObj.put("SELFEVALUATION",selfEvaluationStr);//自我评价
            jsonObj.put("COMPUTERLEVELID",computerLevelStr);//计算机应用能力
            jsonObj.put("GZXZID",workNatureStr);//用工性质
            jsonObj.put("LANGUAGEID_1",languageOneStr);//外语语种1
            jsonObj.put("LANGUAGEPROFICIENCYID_1",languageProfOneStr);//外语语种1熟练程度
            jsonObj.put("LANGUAGEID_2",languageTwoStr);//外语语种2
            jsonObj.put("LANGUAGEPROFICIENCYID_2",languageProfTwoStr);//外语语种2熟练程度
            jsonObj.put("IDNO",sfzStr);//身份证
            jsonObj.put("STARTSALARY",startSalaryStr);//薪水下限
            jsonObj.put("ENDSALARY",endSalaryStr);//薪水上限
            jsonObj.put("GZBSID",workClassStr);//工作班时
            jsonObj.put("LANGUAGECERT",languageCertStr);//外语类证书
            jsonObj.put("COMPUTERCERT",computerCertStr);//计算机证书
            jsonObj.put("OTHERZYFL",wantWorkThreeStr);//欲从事岗位3
            jsonObj.put("GPS",MainLayoutActivity.jingDu+","+MainLayoutActivity.weiDu);
            //jsonObj.put("GPS","0.0,0.0");
            jsonObj.put("ZYFLCHILDID_1",wantWorkOneDetailStr);//欲从事岗位1详细
            jsonObj.put("ZYFLID_1",wantWorkOneStr);//欲从事岗位1
            jsonObj.put("ZYFLCHILDID_2",wantWorkTwoDetailStr);//欲从事岗位2详细
            jsonObj.put("ZYFLID_2",wantWorkTwoStr);//欲从事岗位2
            jsonObj.put("OTHERCERTS",otherCertsStr);//其他职业技能书
            jsonObj.put("AREAID_3",workAreaThreeStr);//工作地区3
            jsonObj.put("AREAID_2",workAreaTwoStr);//工作地区2
            jsonObj.put("AREAID_1",workAreaOneStr);//工作地区1
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonArray.put(jsonObj);

        String personReUrl=MyOkHttpUtils.BaseUrl+"/Json/Set_Resumes_Info.aspx";


                        Response response=  MyOkHttpUtils.okHttpPersonRePost(personReUrl,jsonArray.toString());

                        Message msg=Message.obtain();
                       if(response!=null){

                           try {
                               msg.obj=response.body().string();
                               msg.what=SUCCESS_UPLOAD;

                           } catch (IOException e) {
                               e.printStackTrace();
                           }

                       }else{

                         msg.what=PROBLEM;

                       }

                        mHandler.sendMessage(msg);

                    }
                }

        ).start();



    }


}

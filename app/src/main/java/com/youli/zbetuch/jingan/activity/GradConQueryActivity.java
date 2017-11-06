package com.youli.zbetuch.jingan.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.entity.CompanyPropertyInfo;
import com.youli.zbetuch.jingan.entity.GraQueryInfo;
import com.youli.zbetuch.jingan.entity.GzxzInfo;
import com.youli.zbetuch.jingan.entity.IndustryInfo;
import com.youli.zbetuch.jingan.entity.JinAnStreetInfo;
import com.youli.zbetuch.jingan.entity.JwInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/10/11.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 应届毕业生里面的条件查询界面
 */

public class GradConQueryActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    private final int SUCCESS_STREET=10001;
    private final int SUCCESS_TYPE=10002;
    private final int SUCCESS_INDUSTRY=10003;
    private final int SUCCESS_JW=10004;
    private final int FAIL=10005;

    private Context mContext=GradConQueryActivity.this;
    private Button btnQuery;
    private Spinner spStreet;//街道
    private List<JinAnStreetInfo> spStreetData=new ArrayList<>();//街道
    private Spinner spJw;//居委
    private List<JwInfo> spJwData=new ArrayList<>();//居委
    private Spinner spSex;//性别
    private Spinner spEdu;//文化程度
    private Spinner spCase;//特殊情况
    private Spinner spType;//企业类型
    private List<CompanyPropertyInfo> spTypeData=new ArrayList<>();//企业类型
    private Spinner spIndustry;//行业类别
    private List<IndustryInfo> spIndustryData=new ArrayList<>();//行业类别
    private Spinner spJob;//岗位类别
    private Spinner spSalary;//税后薪资
    private Spinner spPcaseOne;//人员基本情况
    private Spinner spPcaseTwo;//人员具体情况
    private String spPcaseTwoArray [];
    private Spinner spJobGui;//是否职业指导
    private Spinner spWorkGui;//是否推荐就业岗位

    private EditText etAgeStart,etAgeEnd;

    private String streetId;

    private String streetStr;

    private GraQueryInfo queryInfo=new GraQueryInfo();

    private Handler mHandler=new Handler(){


        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS_STREET://街道
                    spSetAdapter("街道",msg);
                    getSpinnerInfo("企业类型");
                    break;
                case SUCCESS_TYPE://企业类型

                    spSetAdapter("企业类型",msg);
                    getSpinnerInfo("行业类别");
                    break;

                case SUCCESS_INDUSTRY://行业类别

                    spSetAdapter("行业类别",msg);
                    break;

                case SUCCESS_JW://居委
                    spSetAdapter("居委",msg);

                    break;

                case FAIL:
                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradcon_query);

        initViews();
    }

    private void initViews(){

        etAgeStart= (EditText) findViewById(R.id.et_gradcon_query_age_start);
        etAgeEnd= (EditText) findViewById(R.id.et_gradcon_query_age_end);
        btnQuery= (Button) findViewById(R.id.btn_gradcon_query_find);
        btnQuery.setOnClickListener(this);

        spStreet= (Spinner) findViewById(R.id.sp_gradcon_query_street);
        spStreet.setOnItemSelectedListener(this);
        getSpinnerInfo("街道");

        spJw= (Spinner) findViewById(R.id.sp_gradcon_query_jw);
        spJw.setOnItemSelectedListener(this);
        spSex= (Spinner) findViewById(R.id.sp_gradcon_query_sex);
        spSex.setOnItemSelectedListener(this);
        spEdu= (Spinner) findViewById(R.id.sp_gradcon_query_edu);
        spEdu.setOnItemSelectedListener(this);
        spCase= (Spinner) findViewById(R.id.sp_gradcon_query_case);
        spCase.setOnItemSelectedListener(this);
        spType= (Spinner) findViewById(R.id.sp_gradcon_query_type);
        spType.setOnItemSelectedListener(this);
        spIndustry= (Spinner) findViewById(R.id.sp_gradcon_query_industry);
        spIndustry.setOnItemSelectedListener(this);
        spJob= (Spinner) findViewById(R.id.sp_gradcon_query_job);
        spJob.setOnItemSelectedListener(this);
        spSalary= (Spinner) findViewById(R.id.sp_gradcon_query_salary);
        spSalary.setOnItemSelectedListener(this);
        spPcaseOne= (Spinner) findViewById(R.id.sp_gradcon_query_pcase_one);
        spPcaseOne.setOnItemSelectedListener(this);
        spPcaseTwo= (Spinner) findViewById(R.id.sp_gradcon_query_pcase_two);
        spPcaseTwo.setOnItemSelectedListener(this);
        spJobGui= (Spinner) findViewById(R.id.sp_gradcon_query_job_guide);
        spJobGui.setOnItemSelectedListener(this);
        spWorkGui= (Spinner) findViewById(R.id.sp_gradcon_query_work_guide);
        spWorkGui.setOnItemSelectedListener(this);

        spJwData.clear();
        spJwData.add(new JwInfo(-1,"请选择"));
        spJw.setAdapter(new ArrayAdapter<JwInfo>(mContext,android.R.layout.simple_list_item_1,spJwData));
    }


    private void getSpinnerInfo(final String content){

        String urlSp = null;

        if(TextUtils.equals(content,"街道")){
           // http://web.youli.pw:89/Json/Get_Area.aspx?STREET=310108
            urlSp= MyOkHttpUtils.BaseUrl+"/Json/Get_Area.aspx?STREET=310108";
        }else if(TextUtils.equals(content,"企业类型")){
            //http://web.youli.pw:89/Json/Get_CompanyProperty.aspx
            urlSp= MyOkHttpUtils.BaseUrl+"/Json/Get_CompanyProperty.aspx";
        }else if(TextUtils.equals(content,"行业类别")){
             //http://web.youli.pw:89/Json/Get_Industry_Class.aspx
            urlSp= MyOkHttpUtils.BaseUrl+"/Json/Get_Industry_Class.aspx";
        }else if(TextUtils.equals(content,"居委")){
            //http://web.youli.pw:89/Json/Get_Area.aspx?COMMITTEE=6013
            urlSp=MyOkHttpUtils.BaseUrl+"/Json/Get_Area.aspx?COMMITTEE="+streetId;
        }

        final String finalUrlSp = urlSp;
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        Response response=MyOkHttpUtils.okHttpGet(finalUrlSp);

                        Message msg=Message.obtain();

                        if(response!=null){

                            if(response.body()!=null){

                                try {
                                    String resStr=response.body().string();

                                    if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")) {

                                        Gson gson = new Gson();
                                        if (TextUtils.equals(content, "街道")) {
                                            msg.obj = gson.fromJson(resStr, new TypeToken<List<JinAnStreetInfo>>() {
                                            }.getType());
                                            msg.what = SUCCESS_STREET;
                                        } else if (TextUtils.equals(content, "企业类型")) {
                                            msg.obj = gson.fromJson(resStr, new TypeToken<List<CompanyPropertyInfo>>() {
                                            }.getType());
                                            msg.what = SUCCESS_TYPE;
                                        } else if (TextUtils.equals(content, "行业类别")) {
                                            msg.obj = gson.fromJson(resStr, new TypeToken<List<IndustryInfo>>() {
                                            }.getType());
                                            msg.what = SUCCESS_INDUSTRY;
                                        } else if (TextUtils.equals(content, "居委")) {
                                            msg.obj = gson.fromJson(resStr, new TypeToken<List<JwInfo>>() {
                                            }.getType());
                                            msg.what = SUCCESS_JW;
                                        }
                                    }
                                    mHandler.sendMessage(msg);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }else{

                                msg.what=FAIL;
                                mHandler.sendMessage(msg);
                            }

                        }else{

                            msg.what=FAIL;
                            mHandler.sendMessage(msg);

                        }

                    }
                }

        ).start();

    }

    private void spSetAdapter(String type,Message msg){

        switch (type){

            case "街道":

                spStreetData.clear();
                spStreetData.add(new JinAnStreetInfo("-1","请选择","-1"));
                spStreetData.addAll((List<JinAnStreetInfo>)msg.obj);
                spStreet.setAdapter(new ArrayAdapter<JinAnStreetInfo>(mContext,android.R.layout.simple_list_item_1,spStreetData));

                break;

            case "居委":

                spJwData.clear();
                spJwData.add(new JwInfo(-1,"请选择"));
                spJwData.addAll((List<JwInfo>)msg.obj);
                spJw.setAdapter(new ArrayAdapter<JwInfo>(mContext,android.R.layout.simple_list_item_1,spJwData));

                break;

            case "企业类型":

                spTypeData.clear();
                spTypeData.add(new CompanyPropertyInfo(-1,"请选择"));
                spTypeData.addAll((List<CompanyPropertyInfo>)msg.obj);
                spType.setAdapter(new ArrayAdapter<CompanyPropertyInfo>(mContext,android.R.layout.simple_list_item_1,spTypeData));

                break;

            case "行业类别":

                spIndustryData.clear();
                spIndustryData.add(new IndustryInfo("-1",-1,"请选择"));
                spIndustryData.addAll((List<IndustryInfo>)msg.obj);
                spIndustry.setAdapter(new ArrayAdapter<IndustryInfo>(mContext,android.R.layout.simple_list_item_1,spIndustryData));

                break;
        }

    }

    @Override
    public void onClick(View v) {

        streetStr=spStreet.getSelectedItem().toString().trim();

        switch (v.getId()){

            case R.id.btn_gradcon_query_find:
                getQuryInfo();
                Intent intent=new Intent();
                intent.putExtra("QueryInfo",queryInfo);
                setResult(20000,intent);
                finish();

                break;

        }

    }


    private void getQuryInfo(){

        queryInfo.setSTART_AGE(etAgeStart.getText().toString().trim().length()==0?"-1":etAgeStart.getText().toString().trim());
        queryInfo.setEND_AGE(etAgeEnd.getText().toString().trim().length()==0?"-1":etAgeEnd.getText().toString().trim());
        queryInfo.setSEX(spSex.getSelectedItem().toString().trim().equals("请选择")?"":spSex.getSelectedItem().toString().trim());
        queryInfo.setEDU(spEdu.getSelectedItem().toString().trim().equals("请选择")?"":spEdu.getSelectedItem().toString().trim());
        queryInfo.setMARK(spCase.getSelectedItem().toString().trim().equals("请选择")?"":spCase.getSelectedItem().toString().trim());

        if((CompanyPropertyInfo)spType.getSelectedItem()!=null) {
            queryInfo.setCOMPROPERTY_ID(String.valueOf(((CompanyPropertyInfo)spType.getSelectedItem()).getCompropertyid()));

        }else{
            queryInfo.setCOMPROPERTY_ID("-1");
        }

        if((IndustryInfo)spIndustry.getSelectedItem()!=null) {
            queryInfo.setINDUSTARY_CATEGORY_ID(String.valueOf(((IndustryInfo)spIndustry.getSelectedItem()).getId()));

        }else{
            queryInfo.setINDUSTARY_CATEGORY_ID("-1");
        }

        queryInfo.setJOB_CATEGORY(spJob.getSelectedItem().toString().trim().equals("请选择")?"":spJob.getSelectedItem().toString().trim());
        queryInfo.setSALARY(spSalary.getSelectedItem().toString().trim().equals("请选择")?"":spSalary.getSelectedItem().toString().trim());
        queryInfo.setBASE_SITUATION(spPcaseOne.getSelectedItem().toString().trim().equals("请选择")?"":spPcaseOne.getSelectedItem().toString().trim());
        queryInfo.setDETAIL_SITUATION(spPcaseTwo.getSelectedItem().toString().trim().equals("请选择")?"":spPcaseTwo.getSelectedItem().toString().trim());

        String jobGui=null;

        if(spJobGui.getSelectedItem().toString().trim().equals("请选择")){
            jobGui="";
        }else if(spJobGui.getSelectedItem().toString().trim().equals("是")){
            jobGui="true";
        }else if(spJobGui.getSelectedItem().toString().trim().equals("否")){
            jobGui="false";
        }

        queryInfo.setCHECK_GUIDE(jobGui);

        String workGui=null;
        if(spWorkGui.getSelectedItem().toString().trim().equals("请选择")){
            workGui="";
        }else if(spWorkGui.getSelectedItem().toString().trim().equals("是")){
            workGui="true";
        }else if(spWorkGui.getSelectedItem().toString().trim().equals("否")){
            workGui="false";
        }
        queryInfo.setCHECK_RECOMMEND(workGui);

        queryInfo.setSTREET_ID(spStreet.getSelectedItem().toString().trim().equals("请选择")?"-1":((JinAnStreetInfo)spStreet.getSelectedItem()).getID());
        queryInfo.setCOMMITTEE_ID(spJw.getSelectedItem().toString().trim().equals("请选择")?"-1":((JwInfo)spJw.getSelectedItem()).getID()+"");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_gradcon_query_street://街道

                if(TextUtils.equals("请选择",((JinAnStreetInfo) spStreet.getSelectedItem()).getNAME())){
                    spJwData.clear();
                    spJwData.add(new JwInfo(-1,"请选择"));
                    spJw.setAdapter(new ArrayAdapter<JwInfo>(mContext,android.R.layout.simple_list_item_1,spJwData));
                }else{

                    streetId = ((JinAnStreetInfo) spStreet.getSelectedItem()).getID();
                    getSpinnerInfo("居委");
                }
                break;
            case R.id.sp_gradcon_query_jw://居委
                break;
            case R.id.sp_gradcon_query_sex://性别
                break;
            case R.id.sp_gradcon_query_edu://文化程度
                break;
            case R.id.sp_gradcon_query_case://特殊情况
                break;
            case R.id.sp_gradcon_query_type://企业类型
                break;
            case R.id.sp_gradcon_query_industry://行业类别
                break;
            case R.id.sp_gradcon_query_job://岗位类别
                break;
            case R.id.sp_gradcon_query_salary://税后薪资
                break;
            case R.id.sp_gradcon_query_pcase_one://人员基本情况
                String value = spPcaseOne.getSelectedItem().toString().trim();
                if(TextUtils.equals(value,"请选择")){
                     getSpData(value);
                }else if(TextUtils.equals(value,"已就业")){
                    getSpData(value);
                }else if(TextUtils.equals(value,"未就业")){
                    getSpData(value);
                }else if(TextUtils.equals(value,"暂不要求就业")){
                    getSpData(value);
                }else if(TextUtils.equals(value,"其他")){
                    getSpData(value);
                }

                break;
            case R.id.sp_gradcon_query_pcase_two://人员具体情况
                break;
            case R.id.sp_gradcon_query_job_guide://是否职业指导
                break;
            case R.id.sp_gradcon_query_work_guide://是否推荐就业岗位
                break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    //给人员具体情况赋值
    private void getSpData(String value){

        if(TextUtils.equals(value,"请选择")){
            spPcaseTwoArray=getResources().getStringArray(R.array.gradeate_empty);
        }else if(TextUtils.equals(value,"已就业")){
            spPcaseTwoArray=getResources().getStringArray(R.array.gradeate_detailSituation_yjy);
        }else if(TextUtils.equals(value,"未就业")){
            spPcaseTwoArray=getResources().getStringArray(R.array.gradeate_detailSituation_wjy);
        }else if(TextUtils.equals(value,"暂不要求就业")){
            spPcaseTwoArray=getResources().getStringArray(R.array.gradeate_detailSituation_zbjy);
        }else if(TextUtils.equals(value,"其他")){
            spPcaseTwoArray=getResources().getStringArray(R.array.gradeate_detailSituation_qt);
        }

        spPcaseTwo.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,spPcaseTwoArray));

    }

}

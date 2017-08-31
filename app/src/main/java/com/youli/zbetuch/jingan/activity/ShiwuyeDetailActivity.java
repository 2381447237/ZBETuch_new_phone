package com.youli.zbetuch.jingan.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.entity.PersonInfo;
import com.youli.zbetuch.jingan.entity.ResourcesDetailInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import com.youli.zbetuch.jingan.utils.SharedPreferencesUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by liutao on 2017/8/11.
 */

public class ShiwuyeDetailActivity extends BaseActivity implements View.OnClickListener{


    private Context mContext=ShiwuyeDetailActivity.this;
    private boolean isCheck;
    private Button diaochaBtn,submitBtn,detailInfoBtn,resultBtn;
    private LinearLayout diaochaLl,shidengLi,shidengAndphenoLi;
    private TextView titleTv,diaochaDateTv;
    private ResourcesDetailInfo RDInfo;
    private EditText sfzEt,nameEt,sexEt,birthdayEt,nationEt,educationEt,hujiAddressEt,
            streetEt,jwEt,detailAddressEt,presentStatusEt,masterDateEt,presentIntentionEt,
            shidengDateEt,shidengVidEt,phoneEt,diaocharenEt,remarksEt;

    private Spinner currStaSp,currIntSp;
    private String currIntStr,currStaStr;
    private String [] currStaData,currIntData;

    private boolean isSave;//是否提交

    private final int SUCCESS=10000;
    private final int  PERSONINFO=10001;
    private final int  NOPERSONINFO=10002;
    private final int  PROBLEM=10003;
    private List<PersonInfo> personInfos=new ArrayList<>();

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS:

                    Toast.makeText(mContext,"提交成功",Toast.LENGTH_SHORT).show();
                    isSave=true;
                    setResult(ZiyuanDetailListActivity.ResultCode,null);

                    break;

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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shiwuye_detail);

        initViews();
    }


    private void initViews(){

        RDInfo=(ResourcesDetailInfo)getIntent().getSerializableExtra("RDInfo");
        isCheck=getIntent().getBooleanExtra("IsCheck",false);

        diaochaBtn= (Button) findViewById(R.id.shiwuye_detail_diaocha_btn);
        diaochaBtn.setOnClickListener(this);
        submitBtn= (Button) findViewById(R.id.shiwuye_detail_submit_btn);
        submitBtn.setOnClickListener(this);
        detailInfoBtn= (Button) findViewById(R.id.shiwuye_detail_info_btn);
        detailInfoBtn.setOnClickListener(this);
          resultBtn= (Button) findViewById(R.id.shiwuye_detail_result_btn);
        resultBtn.setOnClickListener(this);

        titleTv= (TextView) findViewById(R.id.shiwuye_detail_title_tv);
        shidengLi= (LinearLayout) findViewById(R.id.shiwuye_detail_shideng_ll);
        shidengAndphenoLi= (LinearLayout) findViewById(R.id.shiwuye_detail_shideng_and_phone_ll);
        diaochaLl= (LinearLayout) findViewById(R.id.shiwuye_detail_diaocha_ll);

        sfzEt= (EditText) findViewById(R.id.shiwuye_detail_sfz_et);
        nameEt= (EditText) findViewById(R.id.shiwuye_detail_name_et);
        sexEt= (EditText) findViewById(R.id.shiwuye_detail_sex_et);
        birthdayEt= (EditText) findViewById(R.id.shiwuye_detail_birthday_et);
        nationEt= (EditText) findViewById(R.id.shiwuye_detail_nation_et);
        educationEt= (EditText) findViewById(R.id.shiwuye_detail_education_et);
        hujiAddressEt= (EditText) findViewById(R.id.shiwuye_detail_huji_address_et);
                streetEt= (EditText) findViewById(R.id.shiwuye_detail_street_et);
        jwEt= (EditText) findViewById(R.id.shiwuye_detail_jw_et);
        detailAddressEt= (EditText) findViewById(R.id.shiwuye_detail_detail_address_et);
        presentStatusEt= (EditText) findViewById(R.id.shiwuye_detail_present_status_et);
        masterDateEt= (EditText) findViewById(R.id.shiwuye_detail_master_date_et);
        presentIntentionEt= (EditText) findViewById(R.id.shiwuye_detail_present_intention_et);
                shidengDateEt= (EditText) findViewById(R.id.shiwuye_detail_shideng_date_et);
        shidengVidEt= (EditText) findViewById(R.id.shiwuye_detail_shideng_vid_et);
        phoneEt= (EditText) findViewById(R.id.shiwuye_detail_phone_et);
        diaocharenEt= (EditText) findViewById(R.id.shiwuye_detail_diaocharen_et);
        remarksEt= (EditText) findViewById(R.id.shiwuye_detail_remarks_et);


         diaochaDateTv= (TextView) findViewById(R.id.shiwuye_detail_diaochadate_tv);
        diaochaDateTv.setOnClickListener(this);

        currStaSp= (Spinner) findViewById(R.id.sp_shiwuye_detail_curr_sta);
        currIntSp= (Spinner) findViewById(R.id.sp_shiwuye_detail_curr_int);

        initData();

    }

    private void initData(){

        diaocharenEt.setText(MainLayoutActivity.adminName);

        if(isCheck){
            diaochaLl.setVisibility(View.VISIBLE);
            submitBtn.setClickable(false);
            submitBtn.setBackgroundResource(R.drawable.button_enabled);
            diaochaDateTv.setEnabled(false);
            remarksEt.setFocusable(false);
            remarksEt.setText(RDInfo.getMARK());
            remarksEt.setTextColor(Color.parseColor("#c0c0c0"));
        }else{
            remarksEt.setTextColor(Color.parseColor("#000000"));
            remarksEt.setFocusable(true);
            diaochaDateTv.setEnabled(true);
            diaochaLl.setVisibility(View.GONE);
            submitBtn.setClickable(true);
        }

        if(TextUtils.equals(RDInfo.getType(),"失业")){
            currStaData=getResources().getStringArray(R.array.resources_shiye_status);
            titleTv.setText("失业详细");
            shidengAndphenoLi.setVisibility(View.VISIBLE);
            shidengLi.setVisibility(View.VISIBLE);
            shidengDateEt.setText(RDInfo.getZJSYDJRQ().split("T")[0]);
            shidengVidEt.setText(RDInfo.getSYDJYXQ().split("T")[0]);
                    phoneEt.setText(RDInfo.getPHONE());
        }else if(TextUtils.equals(RDInfo.getType(),"无业")){
            currStaData=getResources().getStringArray(R.array.resources_wuye_status);
            titleTv.setText("无业详细");
            shidengAndphenoLi.setVisibility(View.GONE);
            shidengLi.setVisibility(View.INVISIBLE);
        }

        sfzEt.setText(RDInfo.getSFZ());
        nameEt.setText(RDInfo.getNAME());
        sexEt.setText(RDInfo.getSEX());
        birthdayEt.setText(RDInfo.getCSRQ().split("T")[0]);
        nationEt.setText(RDInfo.getMZ());
        educationEt.setText(RDInfo.getEDU());
        hujiAddressEt.setText(RDInfo.getHKDZ());
                streetEt.setText(RDInfo.getJD());
        jwEt.setText(RDInfo.getJW());
        detailAddressEt.setText(RDInfo.getHKDZ());
        presentStatusEt.setText(RDInfo.getMQZK());
        masterDateEt.setText(RDInfo.getMDRQ());
        presentIntentionEt.setText(RDInfo.getDQYX());
        diaochaDateTv.setText(RDInfo.getSURVEY_DATE().split("T")[0]);


        ArrayAdapter<String> currStaAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,currStaData);
       currStaSp.setAdapter(currStaAdapter);


        currIntData=getResources().getStringArray(R.array.resources_wuye_yixiang);
        ArrayAdapter<String> currIntAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,currIntData);
        currIntSp.setAdapter(currIntAdapter);

        if(isCheck){
            spinnerSetSelection("new");
            currIntSp.setEnabled(false);
            currStaSp.setEnabled(false);
        }else{
            currIntSp.setEnabled(true);
            currStaSp.setEnabled(true);
        }

        currIntSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currIntStr=(String)currIntSp.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        currStaSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currStaStr=(String)currStaSp.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.shiwuye_detail_diaocha_btn://点击调查

                if (diaochaLl.getVisibility() == View.GONE) {
                    diaochaLl.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.shiwuye_detail_submit_btn://提交

                if(TextUtils.equals((String)currStaSp.getSelectedItem(),"请选择")){
                    Toast.makeText(this,"请选择目前状况!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.equals((String)currIntSp.getSelectedItem(),"请选择")){
                    Toast.makeText(this,"请选择当前意向!",Toast.LENGTH_SHORT).show();
                    return;
                }


                if(isSave){
                    Toast.makeText(this,"已经提交过了，不能重复提交",Toast.LENGTH_SHORT).show();
                }else{
                    showSubmitDialog();
                }

                break;

            case R.id.shiwuye_detail_result_btn://调查结果同上

                 spinnerSetSelection("old");


                break;

            case R.id.shiwuye_detail_info_btn://详细信息

                getPersonInfo();
                break;

            case R.id.shiwuye_detail_diaochadate_tv://调查日期

                final Calendar c = Calendar.getInstance();

                new DatePickerDialog(mContext,new DatePickerDialog.OnDateSetListener(){


                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        diaochaDateTv.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);


                        if(year>c.get(Calendar.YEAR)||monthOfYear>c.get(Calendar.MONTH)||dayOfMonth>c.get(Calendar.DAY_OF_MONTH)){
                            Toast.makeText(mContext,"时间必须是当年且小于当前时间!",Toast.LENGTH_SHORT).show();
                            diaochaDateTv.setText(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DAY_OF_MONTH));
                        }

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();


                break;
        }

    }

    private void spinnerSetSelection(String type){

        for(int i=0; i<currStaData.length;i++){

            if(TextUtils.equals("old",type)){

                if(TextUtils.equals(RDInfo.getMQZK(),currStaData[i])){

                    currStaSp.setSelection(i);

                }

            }else{

                if(TextUtils.equals(RDInfo.getNEW_MQZK(),currStaData[i])){

                    currStaSp.setSelection(i);

                }

            }



        }

        for(int i=0; i<currIntData.length;i++){

            if(TextUtils.equals("old",type)){

                if(TextUtils.equals(RDInfo.getDQYX(),currIntData[i])){

                    currIntSp.setSelection(i);

                }

            }else{

                if(TextUtils.equals(RDInfo.getNEW_DQYX(),currIntData[i])){

                    currIntSp.setSelection(i);

                }

            }

        }

    }


    private void showSubmitDialog(){

        final AlertDialog.Builder builder=new  AlertDialog.Builder(this);
        builder.setTitle("温馨提示");
        builder.setMessage("确定保存此信息吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    new Thread(

                            new Runnable() {
                                @Override
                                public void run() {
                                    submitInfo(RDInfo.getType());
                                }
                            }

                    ).start();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void submitInfo(String type){
        String url = null;
        //http://web.youli.pw:89/Json/Set_Resource_Survey_Detil_WY.aspx?ID=1303&NEW_DQYX=就业条件较好&SURVEY_DATE=2017-08-16&MARK=&NEW_MQZK=家庭手工业
        if(TextUtils.equals(type,"无业")) {

           url = MyOkHttpUtils.BaseUrl + "/Json/Set_Resource_Survey_Detil_WY.aspx";

        }else if(TextUtils.equals(type,"失业")){

           url = MyOkHttpUtils.BaseUrl + "/Json/Set_Resource_Survey_Detil_SY.aspx";

        }

            Response response=MyOkHttpUtils.okHttpPost(url,RDInfo.getID()+"",currIntStr,currStaStr,diaochaDateTv.getText().toString()
                    ,remarksEt.getText().toString());

            try {
                Message msg=Message.obtain();
                if(response!=null) {

                    String resStr = response.body().string();
                    if(TextUtils.equals("True",resStr)){

                        msg.what=SUCCESS;
                        mHandler.sendMessage(msg);

                    }else{
                        msg.what=PROBLEM;
                        mHandler.sendMessage(msg);
                    }
                }else{

                    msg.what=PROBLEM;
                    mHandler.sendMessage(msg);

                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    private void getPersonInfo(){
       // http://web.youli.pw:89/Json/Get_BASIC_INFORMATION.aspx?sfz=310101198711030515
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url=MyOkHttpUtils.BaseUrl+"/Json/Get_BASIC_INFORMATION.aspx?sfz="+RDInfo.getSFZ();

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
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }

        ).start();

    }


}

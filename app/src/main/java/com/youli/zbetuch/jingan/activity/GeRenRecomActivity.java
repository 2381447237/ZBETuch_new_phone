package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.entity.InterviewInfo;
import com.youli.zbetuch.jingan.entity.PersonInfo;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/10/10.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 个人推荐
 */

public class GeRenRecomActivity extends BaseActivity implements View.OnClickListener{


    private final int SUCCESS_INFO=10001;
    private final int SUCCESS_RECOM=10002;
    private final int FAIL=10003;

    private Context mContext=GeRenRecomActivity.this;
    private Button btnScan;//扫描
    private Button btnQuery;//查询
    private Button btnRecom;//推荐
    private EditText etIdCard;
    private String idNumStr;//身份证号
    private ScrollView svInfo;

    private TextView tvName;//姓名
    private TextView tvSex;//性别
    private TextView tvBirth;//出生年月
    private TextView tvNation;//民族
    private TextView tvJiguan;//籍贯
    private TextView tvState;//状态
    private TextView tvEdu;//文化程度
    private TextView tvMd;//摸底情况
    private TextView tvJd;//街道
    private TextView tvJwh;//居委会
    private TextView tvSfz;//证件号码
    private TextView tvPhone;//联系电话
    private TextView tvCurrInt;//当前意向
    private TextView tvHuji;//户籍地址
    private TextView tvJuzhu;//居住地址

    private String masterId;
    private String code;

    private List<PersonInfo> pInfo=new ArrayList<>();

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS_INFO:

                    pInfo.clear();

                    pInfo.addAll((List<PersonInfo>)msg.obj);

                   if(pInfo.get(0)!=null) {

                       svInfo.setVisibility(View.VISIBLE);

                       svSetInfo(pInfo);

                       btnRecom.setEnabled(true);
                   }else{
                       svInfo.setVisibility(View.GONE);
                       Toast.makeText(mContext, "查无此人信息", Toast.LENGTH_SHORT).show();

                       btnRecom.setEnabled(false);
                   }
                    break;

                case SUCCESS_RECOM:

                    if (TextUtils.equals("True",(String)msg.obj)) {
                        Toast.makeText(mContext, " 推荐成功",
                                Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.equals("False",(String)msg.obj)) {
                        Toast.makeText(mContext,
                                "已推荐\n无需重复推荐", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case FAIL:
                    svInfo.setVisibility(View.GONE);
                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();

                    break;
            }


        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geren_recom);

        masterId=getIntent().getStringExtra("master_id");
        code=getIntent().getStringExtra("JOBNO");


        initViews();

    }


    private void initViews(){


        btnScan= (Button) findViewById(R.id.btn_geren_recom_scanning);
        btnQuery= (Button) findViewById(R.id.btn_geren_recom_query);
        btnRecom= (Button) findViewById(R.id.btn_geren_recom_recom);
        btnScan.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        btnRecom.setOnClickListener(this);
        etIdCard= (EditText) findViewById(R.id.et_geren_recom_idcard);
        svInfo= (ScrollView) findViewById(R.id.sv_geren_recom);
        svInfo.setVisibility(View.GONE);

         tvName= (TextView) findViewById(R.id.tv_geren_recom_name);
         tvSex= (TextView) findViewById(R.id.tv_geren_recom_sex);
         tvBirth= (TextView) findViewById(R.id.tv_geren_recom_birth);
         tvNation= (TextView) findViewById(R.id.tv_geren_recom_nation);
         tvJiguan= (TextView) findViewById(R.id.tv_geren_recom_jiguan);
         tvState= (TextView) findViewById(R.id.tv_geren_recom_state);
         tvEdu= (TextView) findViewById(R.id.tv_geren_recom_edu);
         tvMd= (TextView) findViewById(R.id.tv_geren_recom_md);
         tvJd= (TextView) findViewById(R.id.tv_geren_recom_jd);
         tvJwh= (TextView) findViewById(R.id.tv_geren_recom_jwh);
         tvSfz= (TextView) findViewById(R.id.tv_geren_recom_sfz);
         tvPhone= (TextView) findViewById(R.id.tv_geren_recom_phone);
         tvCurrInt= (TextView) findViewById(R.id.tv_geren_recom_curr_intent);
         tvHuji= (TextView) findViewById(R.id.tv_geren_recom_huji);
         tvJuzhu= (TextView) findViewById(R.id.tv_geren_recom_juzhu);

    }

    @Override
    public void onClick(View v) {

        idNumStr=etIdCard.getText().toString().trim();

        switch (v.getId()){

            case R.id.btn_geren_recom_scanning://扫描
                break;
            case R.id.btn_geren_recom_query://查询

                if(TextUtils.equals("",idNumStr)){
                    Toast.makeText(mContext,"对不起，您查询的身份证号码不能为空！",Toast.LENGTH_SHORT).show();
                }else if (idNumStr.length() < 18) {
                    Toast.makeText(mContext,
                            "对不起，您输入的身份证号码不正确！请重新输入。", Toast.LENGTH_SHORT)
                            .show();
                }else{

                    getQueryInfo(idNumStr);

                }



                break;
            case R.id.btn_geren_recom_recom://推荐

                if(TextUtils.equals("",idNumStr)){
                    Toast.makeText(mContext,"对不起，您查询的身份证号码不能为空！",Toast.LENGTH_SHORT).show();
                }else if (idNumStr.length() < 18) {
                    Toast.makeText(mContext,
                            "对不起，您输入的身份证号码不正确！请重新输入。", Toast.LENGTH_SHORT)
                            .show();
                }else{

                  gerenRecom(idNumStr,masterId,code);

                }

                break;
        }

    }

    //获取个人信息
    //http://web.youli.pw:89/Json/Get_BASIC_INFORMATION.aspx?sfz=310108198004026642
    private void getQueryInfo(final String sfz) {

         new Thread(

                 new Runnable() {
                     @Override
                     public void run() {

                         String url= MyOkHttpUtils.BaseUrl+"/Json/Get_BASIC_INFORMATION.aspx?sfz="+sfz;

                         Response response=MyOkHttpUtils.okHttpGet(url);


                         try {
                             String resStr=response.body().string();

                             Message msg=Message.obtain();

                             if(!TextUtils.equals("",resStr)){

                                 Gson gson=new Gson();

                                 msg.obj=gson.fromJson(resStr,new TypeToken<List<PersonInfo>>(){}.getType());

                                 msg.what=SUCCESS_INFO;

                             }else{

                                 msg.what=FAIL;

                             }

                             mHandler.sendMessage(msg);

                         } catch (IOException e) {
                             e.printStackTrace();
                         }


                     }
                 }

         ).start();

    }


    //给个人信息布局赋值
    private void svSetInfo(List<PersonInfo> info){

        tvName.setText(info.get(0).getNAME());
        tvSex.setText(info.get(0).getSEX());
        tvBirth.setText(MyDateUtils.stringToYMD(info.get(0).getBIRTH_DATE()));
        tvNation.setText(info.get(0).getNATIONS());
        tvJiguan.setText(info.get(0).getNATIVE());
        tvState.setText(info.get(0).getTYPE());
        tvEdu.setText(info.get(0).getCULTURAL_CODE());
        tvMd.setText(info.get(0).getMd());
        tvJd.setText(info.get(0).getCenter().getQ户口所属街道());
        tvJwh.setText(info.get(0).getCenter().getQ居委会());
        tvSfz.setText(info.get(0).getCenter().getQ证件号码());
        tvPhone.setText(info.get(0).getCONTACT_NUMBER());
        tvCurrInt.setText(info.get(0).getCurrent_intent());
        tvHuji.setText(info.get(0).getCenter().getQ户口地址());
        tvJuzhu.setText(info.get(0).getNOW_ROAD()+info.get(0).getNOW_LANE()+info.get(0).getNOW_NO()+info.get(0).getNOW_ROOM());

    }


    //推荐
    private void gerenRecom(final String sfz, final String masterId, final String code){
    http://web.youli.pw:89/Json/Set_JobFairRecommend.aspx?sfz=111111111111111111&rows=15&master_id=1&page=0&code=158286114

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url=MyOkHttpUtils.BaseUrl+"/Json/Set_JobFairRecommend.aspx?sfz="+sfz+"&rows=15&page=0&master_id="+masterId+"&code="+code;

                        Response response=MyOkHttpUtils.okHttpGet(url);


                        try {
                            String resStr=response.body().string();

                            Message msg=Message.obtain();

                            if(!TextUtils.equals("",resStr)){

                                msg.obj=resStr;

                                msg.what=SUCCESS_RECOM;

                            }else{

                                msg.what=FAIL;

                            }

                            mHandler.sendMessage(msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }

        ).start();


    }

}

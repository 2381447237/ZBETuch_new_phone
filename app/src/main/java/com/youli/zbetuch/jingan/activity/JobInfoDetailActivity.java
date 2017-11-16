package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.entity.CounselorInfo;
import com.youli.zbetuch.jingan.entity.JobDetailInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by ZHengBin on 2017/8/26.
 */

public class JobInfoDetailActivity extends BaseActivity implements View.OnClickListener{

    private final int SUCCEED=10000;
    private final int SUCCEED_Counselor=10001;
    private final int SUCCEED_NODATA=10002;
    private final int  PROBLEM=10003;

    private List<JobDetailInfo> detailList=new ArrayList<>();
    private List<CounselorInfo> counselorList=new ArrayList<>();
    private Context mContext=this;
    private TextView titleTv;
    private TextView jobNameTv;//岗位名称
    private TextView jobNoTv;//岗位编号
    private TextView jobTypeTv;//单位类型
    private TextView jobScopeTv;//经营范围
    private TextView intruTv;//单位简介
    private TextView salaryTv;//月收入
    private TextView welfareTv;//员工福利
    private TextView probSalaryTv;//试用期收入
    private TextView probMonthTv;//试用期月数
    private TextView recruitNumsTv;//招聘人数
    private TextView ageTv;//年龄范围
    private TextView areaTv;//工作地区
    private TextView eduTv;//文化程度
    private TextView gzbsTv;//工作班时
    private TextView natureTv;//工作性质
    private TextView dispatchTv;//劳务派遣
    private TextView languagesTv;//外语要求
    private TextView profTv;//熟练程度
    private TextView titleNameTv;//职称名称
    private TextView titleRequireTv;//职称要求
    private TextView certTv;//职业证书
    private TextView levelTv;//资格等级
    private TextView jobContentTv;//工作内容
    private TextView jobRequTv;//任职要求
    private TextView suppleTv;//其他补充
    private TextView otherTv;//同时可招
    private TextView releaseDateTv;//发布时间
    private TextView updateDateTv;//更新时间
    private TextView endDateTv;//终止日期
    private TextView contactsTv;//联系人
    private TextView addressTv;//地址
    private TextView phoneTv;//电话
    private TextView zipTv;//邮政编码
    private Button  queryBtn;
    private String jobNo;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCEED:

                    detailList.clear();
                    detailList.addAll((List<JobDetailInfo>)msg.obj);

                    titleTv.setText(detailList.get(0).getComname());
                    jobNameTv.setText(detailList.get(0).getJobname());
                    jobNoTv.setText(detailList.get(0).getJobno());
                    jobTypeTv.setText(detailList.get(0).getCompropertyname());
                    jobScopeTv.setText(detailList.get(0).getComscope());
                    intruTv.setText(detailList.get(0).getComintroduction());
                    salaryTv.setText((int)detailList.get(0).getStartsalary()+"-"+(int)detailList.get(0).getEndsalary());
                    welfareTv.setText(detailList.get(0).getWelfare());
                    if(detailList.get(0).getProbstartsalary()!=-1) {
                        probSalaryTv.setText(detailList.get(0).getProbstartsalary() + "-" + detailList.get(0).getProbendsalary());
                    }
                    if(detailList.get(0).getProbmonth()!=-1) {
                        probMonthTv.setText(detailList.get(0).getProbmonth() + "");
                    }
                    recruitNumsTv.setText("共"+detailList.get(0).getRecruitnums()+"人");
                    ageTv.setText(detailList.get(0).getStartage()+"-"+detailList.get(0).getEndage()+"  "+detailList.get(0).getAgelimitname());

                 //  areaTv.setText(detailList.get(0).getAreaname_1()+" "+detailList.get(0).getAreaname_2()+" "+detailList.get(0).getAreaname_3());
                    String areaStr=detailList.get(0).getAreaname_1()+" "+detailList.get(0).getAreadesc_1();
                    int areaid =detailList.get(0).getAreaid_2();
                    if (areaid != -1) {
                        areaStr += "  " + detailList.get(0).getAreaname_2() + "  "
                                +detailList.get(0).getAreadesc_2();
                    }
                    areaid = detailList.get(0).getAreaid_3();
                    if (areaid != -1) {
                        areaStr += "  " + detailList.get(0).getAreaname_3() + "  "
                                +detailList.get(0).getAreadesc_3();
                    }

                    areaTv.setText(areaStr);
                   eduTv.setText(detailList.get(0).getEduname()+"("+detailList.get(0).getEdulimitname()+")");
                  gzbsTv.setText(detailList.get(0).getGzbsname());
                   natureTv.setText(detailList.get(0).getGzxzname());
                    if(detailList.get(0).isIsdispatched()){
                        dispatchTv.setText("是");
                    }else{
                        dispatchTv.setText("否");
                    }
                    languagesTv.setText(detailList.get(0).getLanguagename());
                    profTv.setText(detailList.get(0).getLanguageproficiencyname());
                    titleNameTv.setText(detailList.get(0).getProfessionalrequirementname());
                    titleRequireTv.setText(detailList.get(0).getProfessionallevelname());
                    certTv.setText(detailList.get(0).getQualifiedcertid());
                    levelTv.setText(detailList.get(0).getQualifiedlevelname());
                    jobContentTv.setText(detailList.get(0).getResponsibilities());
                    jobRequTv.setText(detailList.get(0).getQualifications());
                    suppleTv.setText(detailList.get(0).getOthers());

                    String isOther = "";
                    boolean isnewgraduates = detailList.get(0).isIsnewgraduates();

                    boolean isdisabledperson = detailList.get(0).isIsdisabledperson();
                    if (isnewgraduates || isdisabledperson) {
                        isOther += "";
                        if (isnewgraduates) {
                            isOther += " 应届毕业生";
                        }
                        if (isdisabledperson) {
                            isOther += " " + detailList.get(0).getCjlxname();
                        }
                    }

                    otherTv.setText(isOther);
                    releaseDateTv.setText(detailList.get(0).getPublishdate().split("T")[0]);
                    updateDateTv.setText(detailList.get(0).getModifydate().split("T")[0]);
                    endDateTv.setText(detailList.get(0).getEnddate().split("T")[0]);

                    getDetailData("Counselor");
                    break;

                case SUCCEED_Counselor:

                    counselorList.clear();
                    counselorList.addAll((List<CounselorInfo>)msg.obj);
                    contactsTv.setText(counselorList.get(0).getName());
                    addressTv.setText(counselorList.get(0).getAddress());
                    phoneTv.setText(counselorList.get(0).getPhone());
                    zipTv.setText(counselorList.get(0).getZip());
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
        setContentView(R.layout.activity_jobinfo_detail);

        jobNo=getIntent().getStringExtra("JOBNO");

        initViews();

    }

    private void initViews(){

        titleTv= (TextView) findViewById(R.id.tv_jobinfo_detail_title);
        jobNameTv= (TextView) findViewById(R.id.tv_jobinfo_detail_jobname);
        jobNoTv= (TextView) findViewById(R.id.tv_jobinfo_detail_jobno);
        jobTypeTv= (TextView) findViewById(R.id.tv_jobinfo_detail_type);
         jobScopeTv= (TextView) findViewById(R.id.tv_jobinfo_detail_scope);
        intruTv= (TextView) findViewById(R.id.tv_jobinfo_detail_introduction);
        salaryTv= (TextView) findViewById(R.id.tv_jobinfo_detail_salary);
        welfareTv= (TextView) findViewById(R.id.tv_jobinfo_detail_welfare);
        probSalaryTv= (TextView) findViewById(R.id.tv_jobinfo_detail_probsalary);
        probMonthTv= (TextView) findViewById(R.id.tv_jobinfo_detail_probmonth);
        recruitNumsTv= (TextView) findViewById(R.id.tv_jobinfo_detail_recruitnums);
         ageTv= (TextView) findViewById(R.id.tv_jobinfo_detail_age);
        areaTv= (TextView) findViewById(R.id.tv_jobinfo_detail_area);
        eduTv= (TextView) findViewById(R.id.tv_jobinfo_detail_edu);
        gzbsTv= (TextView) findViewById(R.id.tv_jobinfo_detail_gzbs);
        natureTv= (TextView) findViewById(R.id.tv_jobinfo_detail_nature);
        dispatchTv= (TextView) findViewById(R.id.tv_jobinfo_detail_dispatch);
        languagesTv= (TextView) findViewById(R.id.tv_jobinfo_detail_languages);
        profTv= (TextView) findViewById(R.id.tv_jobinfo_detail_proficiency);
        titleNameTv= (TextView) findViewById(R.id.tv_jobinfo_detail_titlename);
        titleRequireTv= (TextView) findViewById(R.id.tv_jobinfo_detail_title_require);
        levelTv= (TextView) findViewById(R.id.tv_jobinfo_detail_level);
        certTv= (TextView) findViewById(R.id.tv_jobinfo_detail_cert);
        jobContentTv= (TextView) findViewById(R.id.tv_jobinfo_detail_job_content);
        jobRequTv= (TextView) findViewById(R.id.tv_jobinfo_detail_job_requ);
        suppleTv= (TextView) findViewById(R.id.tv_jobinfo_detail_supple);
        otherTv= (TextView) findViewById(R.id.tv_jobinfo_detail_other);
        releaseDateTv= (TextView) findViewById(R.id.tv_jobinfo_detail_release_date);
        updateDateTv= (TextView) findViewById(R.id.tv_jobinfo_detail_update_date);
        endDateTv= (TextView) findViewById(R.id.tv_jobinfo_detail_end_date);
        contactsTv= (TextView) findViewById(R.id.tv_jobinfo_detail_name);
        addressTv= (TextView) findViewById(R.id.tv_jobinfo_detail_address);
        phoneTv= (TextView) findViewById(R.id.tv_jobinfo_detail_phone);
        zipTv= (TextView) findViewById(R.id.tv_jobinfo_detail_zip);

        queryBtn= (Button) findViewById(R.id.btn_jobinfo_detail_find);
        queryBtn.setOnClickListener(this);

        getDetailData("Detail");

    }

    private void getDetailData(final String sign){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
//                        查询招聘详细信息
//                        http://web.youli.pw:89/Json/GetJobDetail.aspx?JobNo=4
                        String url = null;

                        if(TextUtils.equals("Detail",sign)){
                            url=MyOkHttpUtils.BaseUrl+"/Json/GetJobDetail.aspx?JobNo="+jobNo;
                        }else if(TextUtils.equals("Counselor",sign)){
                            url=MyOkHttpUtils.BaseUrl+"/Json/Get_Career_Counselor.aspx?JobNo="+jobNo;

                        }

                        Log.e("2017/8/29","路径=="+url);

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();
                        if(response!=null){

                            try {
                                String detailStr=response.body().string();
                                Gson gson=new Gson();

                                if(!TextUtils.equals(detailStr,"")&&!TextUtils.equals(detailStr,"[]")) {

                                    if (TextUtils.equals("Detail", sign)) {
                                        msg.what = SUCCEED;
                                        msg.obj = gson.fromJson(detailStr, new TypeToken<List<JobDetailInfo>>() {
                                        }.getType());
                                    } else if (TextUtils.equals("Counselor", sign)) {
                                        msg.what = SUCCEED_Counselor;
                                        msg.obj = gson.fromJson(detailStr, new TypeToken<List<CounselorInfo>>() {
                                        }.getType());
                                    }

                                }else{
                                    msg.what=SUCCEED_NODATA;
                                }
                            } catch (Exception e) {
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

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_jobinfo_detail_find:

                Intent intent=new Intent(mContext,PersonalInfoQueryResult.class);
                intent.putExtra("JOBID",detailList.get(0).getJobid());
                startActivity(intent);

                break;

        }

    }
}

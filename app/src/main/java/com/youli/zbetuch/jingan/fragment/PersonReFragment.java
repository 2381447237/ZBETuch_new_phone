package com.youli.zbetuch.jingan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.activity.JobInfoListActivity;
import com.youli.zbetuch.jingan.entity.EduInfo;
import com.youli.zbetuch.jingan.entity.PersonReInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by ZHengBin on 2017/8/12.
 */
//注意相关实体类PersonReInfo
public class PersonReFragment extends Fragment{

    private String sfzStr;

    public String getSfzStr() {
        return sfzStr;
    }

    public PersonReFragment(String sfzStr) {
        this.sfzStr = sfzStr;
    }

    private View contentView;

    private TextView workNatureTv,workClassTv,workAreaOneTv,workAreaTwoTv,
            workAreaThreeTv,wantWorkOneTv,wantWorkOneDetailTv,wantWorkTwoTv
            ,wantWorkTwoDetailTv,wantWorkThreeTv,wantSalaryTv,
            computerLevelTv,computerCertTv,languageOneTv,languageProfOneTv,
            languageTwoTv,languageProfTwoTv,languageCertTv,otherCertsTv,selfEvaluationTv;

    private List<PersonReInfo> data=new ArrayList<>();

    private Button findWorkBtn;

    private final int SUCCESS=10000;
    private final int  PROBLEM=10001;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS:

                    data.addAll((List<PersonReInfo> )msg.obj);
                    if(data.size()!=0) {
                        workNatureTv.setText(data.get(0).getGZXZNAME());
                        workClassTv.setText(data.get(0).getGZBSNAME());
                        workAreaOneTv.setText(data.get(0).getAREAID_1());
                        workAreaTwoTv.setText(data.get(0).getAREAID_2());
                        workAreaThreeTv.setText(data.get(0).getAREAID_3());
                        wantWorkOneTv.setText(data.get(0).getZYFLID_1());
                        wantWorkOneDetailTv.setText(data.get(0).getZYFLCHILDID_1());
                        wantWorkTwoTv.setText(data.get(0).getZYFLID_2());
                        wantWorkTwoDetailTv.setText(data.get(0).getZYFLCHILDID_2());
                        wantWorkThreeTv.setText(data.get(0).getOTHERZYFL());
                        if (data.get(0).getENDSALARY() == -1) {
                            wantSalaryTv.setText(data.get(0).getSTARTSALARY() + "及以上");
                        } else {
                            wantSalaryTv.setText(data.get(0).getSTARTSALARY() + "-" + data.get(0).getENDSALARY());
                        }

                        computerLevelTv.setText(data.get(0).getCOMPUTERLEVELID());
                        computerCertTv.setText(data.get(0).getCOMPUTERCERT());
                        languageOneTv.setText(data.get(0).getLANGUAGEID_1());
                        languageProfOneTv.setText(data.get(0).getLANGUAGEPROFICIENCYID_1());
                        languageTwoTv.setText(data.get(0).getLANGUAGEID_2());
                        languageProfTwoTv.setText(data.get(0).getLANGUAGEPROFICIENCYID_2());
                        languageCertTv.setText(data.get(0).getLANGUAGECERT());
                        otherCertsTv.setText(data.get(0).getOTHERCERTS());
                        selfEvaluationTv.setText(data.get(0).getSELFEVALUATION());
                    }

                    break;

                case PROBLEM:

                    Toast.makeText(getActivity(),"网络不给力",Toast.LENGTH_SHORT).show();

                    break;
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        contentView=LayoutInflater.from(getContext()).inflate(R.layout.framgment_personal_resume,container,false);

        initView(contentView);


        return contentView;
    }

    private void initView(View view){

        workNatureTv= (TextView) view.findViewById(R.id.tv_person_resume_work_nature);
        workClassTv= (TextView) view.findViewById(R.id.tv_person_resume_work_class);
        workAreaOneTv= (TextView) view.findViewById(R.id.tv_person_resume_work_area_one);
        workAreaTwoTv= (TextView) view.findViewById(R.id.tv_person_resume_work_area_two);
        workAreaThreeTv= (TextView) view.findViewById(R.id.tv_person_resume_work_area_three);
        wantWorkOneTv= (TextView) view.findViewById(R.id.tv_person_resume_want_work_one);
        wantWorkOneDetailTv= (TextView) view.findViewById(R.id.tv_person_resume_want_work_one_detail);
        wantWorkTwoTv = (TextView) view.findViewById(R.id.tv_person_resume_want_work_two);
        wantWorkTwoDetailTv= (TextView) view.findViewById(R.id.tv_person_resume_want_work_two_detail);
        wantWorkThreeTv = (TextView) view.findViewById(R.id.tv_person_resume_want_work_three);
        wantSalaryTv= (TextView) view.findViewById(R.id.tv_person_resume_want_salary);
        computerLevelTv= (TextView) view.findViewById(R.id.tv_person_resume_computer_level);
        computerCertTv= (TextView) view.findViewById(R.id.tv_person_resume_computer_cert);
        languageOneTv= (TextView) view.findViewById(R.id.tv_person_resume_language_one);
        languageProfOneTv= (TextView) view.findViewById(R.id.tv_person_resume_language_proficiency_one);
        languageTwoTv=(TextView) view.findViewById(R.id.tv_person_resume_language_two);
        languageProfTwoTv= (TextView) view.findViewById(R.id.tv_person_resume_language_proficiency_two);
        languageCertTv= (TextView) view.findViewById(R.id.tv_person_resume_language_cert);
        otherCertsTv= (TextView) view.findViewById(R.id.tv_person_resume_other_certs);
        selfEvaluationTv= (TextView) view.findViewById(R.id.tv_person_resume_self_evaluation);

        findWorkBtn= (Button) view.findViewById(R.id.btn_person_resume_find_work);
        findWorkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"查找对应工作",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(), JobInfoListActivity.class);
                startActivity(intent);


            }
        });

        initDatas();
    }

    private void initDatas(){

//        workNatureTv.setText("全日制劳动合同");
//        workClassTv.setText("常日班");
//        workAreaOneTv.setText("浦东新区");
//        workAreaTwoTv.setText("浦东新区");
//        workAreaThreeTv.setText("浦东新区");
//        wantWorkOneTv.setText("工程技术人员");
//        wantWorkOneDetailTv.setText("地质勘探工程技术人员");
//        wantWorkTwoTv.setText("农业技术人员");
//        wantWorkTwoDetailTv.setText("兽医兽药技术人员");
//        wantWorkThreeTv.setText("欲从事岗位3333");
//        wantSalaryTv.setText("5001-8000");
//        computerLevelTv.setText("精通");
//        computerCertTv.setText("2级");
//        languageOneTv.setText("英语");
//        languageProfOneTv.setText("精通");
//        languageTwoTv.setText("阿拉伯语");
//        languageProfTwoTv.setText("良好");
//        languageCertTv.setText("英语4级");
//        otherCertsTv.setText("教师资格证");
//        selfEvaluationTv.setText("本人性格开朗");

       // http://web.youli.pw:89/Json/Get_Resumes_Info.aspx?sfz=310108198004026642

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String resUrl= MyOkHttpUtils.BaseUrl+"/Json/Get_Resumes_Info.aspx?sfz="+getSfzStr();

                        Response response=MyOkHttpUtils.okHttpGet(resUrl);

                        Message msg=Message.obtain();

                         if(response!=null){

                             try {
                                 String resStr=response.body().string();

                                 if(resStr!=null){

                                     Gson gson=new Gson();

                                     msg.obj=gson.fromJson(resStr,new TypeToken<List<PersonReInfo>>(){}.getType());

                                     msg.what=SUCCESS;
                                     mHandler.sendMessage(msg);

                                 }else{
                                     msg.what= PROBLEM;
                                     mHandler.sendMessage(msg);

                                 }

                             } catch (IOException e) {
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

}

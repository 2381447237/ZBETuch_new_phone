package com.youli.zbetuch.jingan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import com.youli.zbetuch.jingan.entity.JobInfoListInfo;
import com.youli.zbetuch.jingan.entity.PersonReInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by ZHengBin on 2017/8/12.
 */
//注意相关实体类PersonReInfo
public class PersonReFragment extends Fragment{

    private String sfzStr;


    public static final PersonReFragment newInstance(String sfz){

        PersonReFragment fragment = new PersonReFragment();
        Bundle bundle = new Bundle();
        bundle.putString("sfz", sfz);
        fragment.setArguments(bundle);

        return fragment;
    }

    private View contentView;

    private TextView workNatureTv,workClassTv,workAreaOneTv,workAreaTwoTv,
            workAreaThreeTv,wantWorkOneTv,wantWorkOneDetailTv,wantWorkTwoTv
            ,wantWorkTwoDetailTv,wantWorkThreeTv,wantSalaryTv,
            computerLevelTv,computerCertTv,languageOneTv,languageProfOneTv,
            languageTwoTv,languageProfTwoTv,languageCertTv,otherCertsTv,selfEvaluationTv;

    private List<PersonReInfo> data=new ArrayList<>();
    private PersonReInfo pri;
    private List<JobInfoListInfo> jobInfoList=new ArrayList<>();

    private Button findWorkBtn;
    private String queryUrl;
    private final int SUCCESS=10000;
    private final int SUCCESS_JOB=10001;
    private final int  SUCCESS_NODATA=10002;
    private final int  PROBLEM=10003;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS:
                    data.clear();
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

                        if(data.get(0).getSTARTSALARY()==0){

                            wantSalaryTv.setVisibility(View.GONE);
                        }else {
                            wantSalaryTv.setVisibility(View.VISIBLE);
                            if (data.get(0).getENDSALARY() != 0) {
                                wantSalaryTv.setText(data.get(0).getSTARTSALARY() + "-" + data.get(0).getENDSALARY());
                            } else if(data.get(0).getENDSALARY() == 0){
                                wantSalaryTv.setText(data.get(0).getSTARTSALARY() + "及以上");
                            }
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

                case SUCCESS_JOB:

                    jobInfoList.clear();
                    jobInfoList.addAll((List<JobInfoListInfo>)msg.obj);
                    if(getActivity()!=null) {
                        Intent intent = new Intent(getActivity(), JobInfoListActivity.class);
                        intent.putExtra("JobInfoList", (Serializable) jobInfoList);
                        intent.putExtra("queryUrl", queryUrl);
                        startActivity(intent);
                    }
                    break;

                case PROBLEM:
                    if(getActivity()!=null) {
                        Toast.makeText(getActivity(), "网络不给力", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case SUCCESS_NODATA:

                    break;
            }

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sfzStr=getArguments().getString("sfz");
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
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

                getJobList(sfzStr);

            }
        });

        initDatas(pri);
    }


    public void getJobList(final String sfz){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

//                        根据身份证查询岗位信息
//                        http://web.youli.pw:89/Json/Get_JobsInfo.aspx?sfz=422201197209204223&PageIndex=0&PageRecCnts=15

                        queryUrl=MyOkHttpUtils.BaseUrl+"/Json/Get_JobsInfo.aspx?sfz="+sfz+"&PageRecCnts=15&PageIndex=";

                        String urlJobList=queryUrl+"0";

                        Log.e("2017/10/26","urlJobList=="+urlJobList);

                        Response response=MyOkHttpUtils.okHttpGet(urlJobList);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String jobStr=response.body().string();

                                if(!TextUtils.equals(jobStr,"")){
                                    Gson gson=new Gson();
                                    msg.what=SUCCESS_JOB;
                                    msg.obj=gson.fromJson(jobStr,new TypeToken<List<JobInfoListInfo>>(){}.getType());
                                }else{

                                    msg.what=SUCCESS_NODATA;
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
    //这里我们的ThreadMode设置为MAIN，事件的处理会在UI线程中执行
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initDatas(PersonReInfo pri){//注意方法里面一定要传一个对象，否则就eventBus就会报错

    //    Log.e("2017/10/13","赋值=================================");

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

                        String resUrl= MyOkHttpUtils.BaseUrl+"/Json/Get_Resumes_Info.aspx?sfz="+sfzStr;

                        Response response=MyOkHttpUtils.okHttpGet(resUrl);

                        Message msg=Message.obtain();

                         if(response!=null){

                             try {
                                 String resStr=response.body().string();

                                 if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")){

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
    public void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

}

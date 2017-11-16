package com.youli.zbetuch.jingan.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.CheckBoxAdapter;
import com.youli.zbetuch.jingan.adapter.RadioButtonAdapter;
import com.youli.zbetuch.jingan.entity.CompanyPropertyInfo;
import com.youli.zbetuch.jingan.entity.GraduateInfo;
import com.youli.zbetuch.jingan.entity.IndustryInfo;
import com.youli.zbetuch.jingan.entity.JobIntentInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/10/16.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 从应届毕业生界面进去的个人求职意愿碎片
 */

public class PersonJobIntFragment extends BaseFragment{

    private final  int SUCCESS_CP=10001;//企业类型
    private final  int SUCCESS_IND=10002;//行业类型
    private final  int SUCCESS_JOB_INTENT=10003;//个人求职意愿的勾选情况
    private final int SUCCESS_SAVE=10004;//保存
    private final int FAIL=10005;

    private GraduateInfo gInfo;

    public static final PersonJobIntFragment  newInstance(GraduateInfo gInfo){

        PersonJobIntFragment  fragment = new PersonJobIntFragment ();
        Bundle bundle = new Bundle();
        bundle.putSerializable("gInfo",gInfo);
        fragment.setArguments(bundle);

        return fragment;
    }

    private View contentView;
    private GridView gvComproperty, gvIndustryCategory,gvJobCategory,gvSalary;

    private List<CheckBox> compropertyList = new ArrayList<CheckBox>();
    private List<CheckBox> industryCatagoryList = new ArrayList<CheckBox>();
    private List<CheckBox> jobCatagoryList = new ArrayList<CheckBox>();

    private List<CompanyPropertyInfo> cpData=new ArrayList<>();
    private List<IndustryInfo> indData=new ArrayList<>();

    private List<RadioButton> salaryList = new ArrayList<RadioButton>();

    private CheckBoxAdapter compropertyAdapter, industryCatagoryAdapter,jobCatagoryAdapter;

    private RadioButtonAdapter salaryAdapter;

    private List<JobIntentInfo> jobIntList=new ArrayList<>();//选中的选项

    private Button btnSave;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS_CP:
                    if(getActivity()!=null) {
                    cpData.clear();
                    cpData.addAll((List<CompanyPropertyInfo>)msg.obj);
                    for(CompanyPropertyInfo info:cpData){
                        fretchComproperty(info);
                    }

                        if (compropertyAdapter == null) {
                            compropertyAdapter = new CheckBoxAdapter(compropertyList, getActivity());
                            gvComproperty.setAdapter(compropertyAdapter);
                        } else {
                            compropertyAdapter.notifyDataSetChanged();
                        }
                    }
                    getProperty("行业类型");

                    break;

                case SUCCESS_IND:
                    if(getActivity()!=null) {
                        indData.clear();
                        indData.addAll((List<IndustryInfo>) msg.obj);

                        for (IndustryInfo info : indData) {
                            fretchIndustryCatagory(info);
                        }

                        if (industryCatagoryAdapter == null) {
                            industryCatagoryAdapter = new CheckBoxAdapter(industryCatagoryList, getActivity());
                            gvIndustryCategory.setAdapter(industryCatagoryAdapter);
                        } else {
                            industryCatagoryAdapter.notifyDataSetChanged();
                        }


                        fretchJobCatagory();
                        if(jobCatagoryAdapter==null) {
                            jobCatagoryAdapter = new CheckBoxAdapter(jobCatagoryList, getActivity());
                            gvJobCategory.setAdapter(jobCatagoryAdapter);
                        }else {
                            jobCatagoryAdapter.notifyDataSetChanged();
                        }

                        fretchSalary();
                        if(salaryAdapter==null) {
                            salaryAdapter = new RadioButtonAdapter(salaryList, getActivity());
                            gvSalary.setAdapter(salaryAdapter);
                        }else{
                            salaryAdapter.notifyDataSetChanged();
                        }
                    }
                    getJobIntList();

                    break;

                case SUCCESS_JOB_INTENT:

                    jobIntList.clear();
                    jobIntList.addAll((List<JobIntentInfo>)msg.obj);

                    dataCheck(jobIntList);

                    break;

                case SUCCESS_SAVE:

                    if (getActivity() != null) {
                        if (jobIntList.get(0) != null) {
                            if (TextUtils.equals(jobIntList.get(0).getID() + "", msg.obj + "")) {
                                EventBus.getDefault().post(new GraduateInfo());
                                Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            EventBus.getDefault().post(new GraduateInfo());
                            Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;

                case FAIL:
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "网络不给力", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gInfo=(GraduateInfo)getArguments().getSerializable("gInfo");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        contentView=LayoutInflater.from(getContext()).inflate(R.layout.fragment_person_job_intent,container,false);

        isViewCreated=true;//标记

        return contentView;
    }

    @Override
    public void lazyLoadData() {
        if(isViewCreated){
          //逻辑都写这里面
            initViews();
        }
    }

    private void initViews(){

        gvComproperty= (GridView) contentView.findViewById(R.id.gv_comproperty);
        gvIndustryCategory= (GridView) contentView.findViewById(R.id.gv_IndustryCategory);
        gvJobCategory= (GridView) contentView.findViewById(R.id.gv_jobCategory);
        gvSalary= (GridView) contentView.findViewById(R.id.gv_salary);
        getProperty("企业类型");
        btnSave= (Button) contentView.findViewById(R.id.btn_fmt_person_base_info_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkFrm()){
                    showSaveDialog();
                }
            }
        });
    }


    private void getProperty(final String content){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url = null;

                        if (TextUtils.equals(content, "企业类型")) {
                            //企业类型:http://web.youli.pw:89/Json/Get_CompanyProperty.aspx
                            url = MyOkHttpUtils.BaseUrl + "/Json/Get_CompanyProperty.aspx";
                        } else if (TextUtils.equals(content, "行业类型")) {
                            //行业类型:http://web.youli.pw:89/Json/Get_Industry_Class.aspx
                            url = MyOkHttpUtils.BaseUrl + "/Json/Get_Industry_Class.aspx";
                        }

                        Response response = MyOkHttpUtils.okHttpGet(url);

                        Message msg = Message.obtain();

                        if (response != null) {

                            if (response.body() != null) {

                                try {
                                    String resStr = response.body().string();

                                    if (!TextUtils.equals(resStr, "")&&!TextUtils.equals(resStr, "[]")) {

                                        Gson gson = new Gson();
                                        if (TextUtils.equals(content, "企业类型")) {
                                            msg.obj = gson.fromJson(resStr, new TypeToken<List<CompanyPropertyInfo>>() {
                                            }.getType());
                                            msg.what = SUCCESS_CP;
                                        } else if (TextUtils.equals(content, "行业类型")) {
                                            msg.obj = gson.fromJson(resStr, new TypeToken<List<IndustryInfo>>() {
                                            }.getType());
                                            msg.what = SUCCESS_IND;
                                        }
                                        mHandler.sendMessage(msg);
                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {

                                msg.what = FAIL;
                                mHandler.sendMessage(msg);
                            }
                        }
                    }
                }
        ).start();

    }

    /**
     * 填充企业类型复选框
     *
     *
     */
    private void fretchComproperty(CompanyPropertyInfo info) {

                    CheckBox box = (CheckBox) LayoutInflater.from(getActivity())
                            .inflate(R.layout.item_gradeate_checkbox, null);
                    box.setText(info.getCompropertyname());
                    box.setTag(info.getCompropertyid());
//                   box.setText(object.getString("compropertyname").trim());
//                    box.setTag(object.getString("compropertyid").trim());
                    compropertyList.add(box);
    }


    /**
     * 填充行业类型复选框
     *
     *
     */
    private void fretchIndustryCatagory(IndustryInfo info) {

                    CheckBox box = (CheckBox) LayoutInflater.from(getActivity())
                            .inflate(R.layout.item_gradeate_checkbox, null);
                      box.setText(info.getName());
                      box.setTag(info.getId());
//                    box.setText(object.getString("name").trim());
//                    box.setTag(object.getString("id").trim());
                    industryCatagoryList.add(box);

    }

    /**
     * 填充岗位类别复选框
     */
    private void fretchJobCatagory() {
        String[] jobStys = getResources().getStringArray(
                R.array.gradeate_ParamQuery_jobStyle);

        for (String job : jobStys) {
            CheckBox box = (CheckBox) LayoutInflater.from(getActivity()).inflate(
                    R.layout.item_gradeate_checkbox, null);

            if(!TextUtils.equals("请选择",job.trim())){
                box.setText(job.trim());
                box.setTag(job.trim());
                jobCatagoryList.add(box);
            }
        }
    }

    /**
     * 填充工资类别复选框
     */
    private void fretchSalary() {
        String[] jobStys = getResources().getStringArray(
                R.array.gradeate_ParamQuery_salaryStyle);
        for (String job : jobStys) {
            RadioButton box = (RadioButton) LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_gradeate_radiobutton, null);
            if(!TextUtils.equals("请选择",job.trim())) {
                box.setText(job.trim());
                box.setTag(job.trim());
                salaryList.add(box);
            }
        }
    }

    private void getJobIntList(){
    //http://web.youli.pw:89/Json/Get_JobIntent.aspx?master_id=100
     new Thread(

             new Runnable() {
                 @Override
                 public void run() {

                     String url=MyOkHttpUtils.BaseUrl+"/Json/Get_JobIntent.aspx?master_id="+gInfo.getID();

                     Response response=MyOkHttpUtils.okHttpGet(url);

                     Message msg=Message.obtain();

                     if(response!=null){

                         if(response.body()!=null){

                             try {
                                 String resStr=response.body().string();

                                 if(!TextUtils.equals("",resStr)&&!TextUtils.equals("[]",resStr)){

                                     Gson gson=new Gson();
                                     msg.obj = gson.fromJson(resStr, new TypeToken<List<JobIntentInfo>>() {
                                     }.getType());
                                     msg.what = SUCCESS_JOB_INTENT;
                                     mHandler.sendMessage(msg);
                                 }

                             } catch (Exception e) {
                                 e.printStackTrace();
                             }


                         }else{

                             msg.what=FAIL;
                             mHandler.sendMessage(msg);

                         }

                     }

                 }
             }

     ).start();

    }


   private void dataCheck(List<JobIntentInfo> list){

            if(list.get(0)==null){
                return;
            }

          compropertyAdapter.setValue(list.get(0).getCOMPROPERTYID1()+"");
          compropertyAdapter.setValue(list.get(0).getCOMPROPERTYID2()+"");
          compropertyAdapter.setValue(list.get(0).getCOMPROPERTYID3()+"");

          industryCatagoryAdapter.setValue(list.get(0).getINDUSTRY_CATEGORY1());
          industryCatagoryAdapter.setValue(list.get(0).getINDUSTRY_CATEGORY2());
          industryCatagoryAdapter.setValue(list.get(0).getINDUSTRY_CATEGORY3());

          jobCatagoryAdapter.setValue(list.get(0).getJOB_CATEGORY1());
          jobCatagoryAdapter.setValue(list.get(0).getJOB_CATEGORY2());
          jobCatagoryAdapter.setValue(list.get(0).getJOB_CATEGORY3());

          salaryAdapter.setValue(list.get(0).getSALARY1());
   }


    /**
     * 页面非空验证
     *
     * @return
     */
    private boolean checkFrm() {
//        if (GradeatePersonInfoActivity.gradeateId == 0) {
//            Toast.makeText(mContext, "人员还未保存，请先保存或选中人员", Toast.LENGTH_SHORT)
//                    .show();
//            return false;
//        }
        if(getActivity()==null){
            return false;
        }

        if (compropertyAdapter.getSelectedBoxs().size() == 0) {
            Toast.makeText(getActivity(), "请选择企业类型！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (industryCatagoryAdapter.getSelectedBoxs().size() == 0) {
            Toast.makeText(getActivity(), "请选择行业类别！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (jobCatagoryAdapter.getSelectedBoxs().size() == 0) {
            Toast.makeText(getActivity(), "请选择岗位类别！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (salaryAdapter.getSelectedButton() == null) {
            Toast.makeText(getActivity(), "请选择税后薪资！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void  showSaveDialog(){

        if(getActivity()!=null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("保存信息提示");
            builder.setMessage("您确定保存此个人求职意愿吗?");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                   saveInfo();

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
    }


   // {INDUSTRY_CATEGORY1=3,  INDUSTRY_CATEGORY2=11,INDUSTRY_CATEGORY3=6,SALARY1=3,000元至5,000元(含),SALARY2=,  SALARY3=,
   // JOB_CATEGORY1=管理类,JOB_CATEGORY2=技术类,JOB_CATEGORY3=,COMPROPERTYID1=5, COMPROPERTYID2=-1, COMPROPERTYID3=-1,
   //  MASTER_ID=100, ID=16}
     private void saveInfo(){

         final Map<String,String> data=new HashMap<>();
         data.put("MASTER_ID",gInfo.getID()+"");
         if(jobIntList.get(0)!=null) {
             data.put("ID", jobIntList.get(0).getID() + "");
         }else{
             data.put("ID","0");
         }
         List<CheckBox> selectedBoxs=null;
         selectedBoxs=compropertyAdapter.getSelectedBoxs();
         data.put("COMPROPERTYID1",selectedBoxs.get(0).getTag().toString().trim());
         data.put("COMPROPERTYID2",selectedBoxs.size()>=2?selectedBoxs.get(1).getTag().toString().trim():"-1");
         data.put("COMPROPERTYID3",selectedBoxs.size()>=3?selectedBoxs.get(2).getTag().toString().trim():"-1");

         selectedBoxs = industryCatagoryAdapter.getSelectedBoxs();
         data.put("INDUSTRY_CATEGORY1",selectedBoxs.get(0).getTag().toString().trim());
         data.put("INDUSTRY_CATEGORY2",selectedBoxs.size() >= 2 ? selectedBoxs.get(1).getTag().toString().trim() : "-1");
         data.put("INDUSTRY_CATEGORY3",selectedBoxs.size() == 3 ? selectedBoxs.get(2).getTag().toString().trim() : "-1");

         selectedBoxs = jobCatagoryAdapter.getSelectedBoxs();
         data.put("JOB_CATEGORY1",selectedBoxs.get(0).getTag().toString().trim());
         data.put("JOB_CATEGORY2",selectedBoxs.size() >= 2 ? selectedBoxs.get(1).getTag().toString().trim() : "");
         data.put("JOB_CATEGORY3",selectedBoxs.size() == 3 ? selectedBoxs.get(2).getTag().toString().trim() : "");

         data.put("SALARY1",salaryAdapter.getSelectedButton().getTag().toString().trim());
         data.put("SALARY2","");
         data.put("SALARY3","");



         new Thread(

                 new Runnable() {
                     @Override
                     public void run() {
                        // http://web.youli.pw:89/Json/Set_JobIntent.aspx
                         String url=MyOkHttpUtils.BaseUrl+"/Json/Set_JobIntent.aspx";

                         Response response=MyOkHttpUtils.okHttpPostFormBody(url,(HashMap<String, String>) data);

                         Message msg=Message.obtain();

                         if(response!=null){

                             if(response.body()!=null){

                                 try {
                                     String resStr=response.body().string();

                                     if(!TextUtils.equals(resStr,"0")){
                                         msg.what=SUCCESS_SAVE;
                                         msg.obj=resStr;
                                         mHandler.sendMessage(msg);
                                     }

                                 } catch (IOException e) {
                                     e.printStackTrace();
                                 }

                             }

                         }else{

                            msg.what=FAIL;
                             mHandler.sendMessage(msg);

                         }

                     }
                 }

         ).start();

     }

}

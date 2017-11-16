package com.youli.zbetuch.jingan.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.EduInfo;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by ZHengBin on 2017/8/26.
 *
 * 教育信息
 */

public class PersonEduActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext=this;
    private PullToRefreshListView lv;
    private List<EduInfo> data=new ArrayList<>();
    private CommonAdapter adapter;
    private final int SUCCESS=10000;
    private final int SUCCESS_DELETE=10001;//删除
    private final int SUCCESS_NEW=10002;//新建
    private final int SUCCESS_MODIFY=10003;//修改
    private final int SUCCESS_NODATA=10004;
    private final int  PROBLEM=10005;
    private String sfzStr;
    private Button btnNew;
    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            EventBus.getDefault().post(new EduInfo());//注意这里post()里面一定要传一个对象，否则就会报错

            switch (msg.what){

                case SUCCESS:

                    data.clear();
                    data.addAll((List<EduInfo>)msg.obj);

                    Log.e("2017/8/29","教育信息=="+data);

                    setLvAdapter(data);

                    break;

                case SUCCESS_DELETE://删除

                    if(TextUtils.equals("True",(String)msg.obj)) {
                        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                        data.remove(msg.arg1);
                        adapter.notifyDataSetChanged();
                    }
                    break;

                case SUCCESS_NEW://新建

                    if(TextUtils.equals("True",(String)msg.obj)) {

                        Toast.makeText(mContext, "新建成功", Toast.LENGTH_SHORT).show();
                        initDatas();
                    }

                    break;

                case SUCCESS_MODIFY://修改

                    if(TextUtils.equals("True",(String)msg.obj)) {

                        Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
                        initDatas();
                    }


                    break;

                case PROBLEM:

                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();

                    break;

                case SUCCESS_NODATA:

                    if(lv.isRefreshing()){
                        lv.onRefreshComplete();
                    }

                    break;
            }

        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_education);

        sfzStr=getIntent().getStringExtra("SFZ");

        initViews();
    }

    private void initViews(){

        lv= (PullToRefreshListView) findViewById(R.id.lv_person_education);

        btnNew= (Button) findViewById(R.id.btn_person_education_new);
        btnNew.setOnClickListener(this);
       initDatas();
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                initDatas();
            }


            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // Toast.makeText(mContext, "上拉加载", Toast.LENGTH_SHORT).show();


            }
        });
    }
    private void initDatas(){
        //http://web.youli.pw:89/Json/Get_Educational_Information.aspx?sfz=310108198004026642
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_Educational_Information.aspx?sfz="+sfzStr;

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String resStr=response.body().string();
                                if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")) {
                                    Gson gson = new Gson();

                                    msg.what = SUCCESS;
                                    msg.obj = gson.fromJson(resStr, new TypeToken<List<EduInfo>>() {
                                    }.getType());
                                }else{
                                    msg.what = SUCCESS_NODATA;
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

    private void setLvAdapter(List<EduInfo> eInfo){

       if(adapter==null) {

           adapter = new CommonAdapter<EduInfo>(mContext, eInfo, R.layout.item_fmt_education_info_lv) {

               @Override
               public void convert(CommonViewHolder holder, EduInfo item, final int position) {

                   TextView name = holder.getView(R.id.tv_item_fmt_education_info_name);
                   name.setText(item.getSCHOOL());
                   TextView edu = holder.getView(R.id.tv_item_fmt_education_info_edu);
                   edu.setText(item.getEDUCATION());
                   TextView major = holder.getView(R.id.tv_item_fmt_education_info_major);
                   major.setText(item.getSPECIALTY());
                   TextView startTime = holder.getView(R.id.tv_item_fmt_education_info_starttime);
                   startTime.setText(item.getSTART_DATE().split("T")[0]);
                   TextView endTime = holder.getView(R.id.tv_item_fmt_education_info_endtime);
                   endTime.setText(item.getEND_DATE().split("T")[0]);

                   LinearLayout ll = holder.getView(R.id.ll_item_fmt_education_info);

                   if (position % 2 != 0) {
                       ll.setBackgroundColor(Color.WHITE);
                   } else {
                       ll.setBackgroundColor(Color.parseColor("#b7daf4"));
                   }
                   RelativeLayout rlContent = holder.getView(R.id.rl_item_fmt_education_info_content);
                   rlContent.setVisibility(View.VISIBLE);

                   Button upDate = holder.getView(R.id.btn_item_fmt_education_info_update);
                   upDate.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {

                           // Toast.makeText(mContext,"第"+position+"个修改",Toast.LENGTH_SHORT).show();

                           showNewOrModifyDialog("modify", position);

                       }
                   });

                   Button delete = holder.getView(R.id.btn_item_fmt_education_info_delete);
                   delete.setOnClickListener(new View.OnClickListener() {//删除
                       @Override
                       public void onClick(View v) {

                           showDeleteDialog(position);
                       }
                   });
               }
           };

           lv.setAdapter(adapter);
       }else{

           adapter.notifyDataSetChanged();

       }
       if(lv.isRefreshing()){
           lv.onRefreshComplete();
       }


    }


    private void  showDeleteDialog(final int p){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("修改信息提示");
        builder.setMessage("您确定删除此项服务信息吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                deleteInfo(p);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void deleteInfo(final int p){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("ID", "" + data.get(p).getID());
                            obj.put("Type", "-1");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Map<String, String> data = new HashMap<String, String>();
                        data.put("json", obj.toString());
                       // http://web.youli.pw:89/Json/Set_Educational_Information.aspx?json={"ID":"8","Type":"-1"}
                        String urlDelete=MyOkHttpUtils.BaseUrl+"/Json/Set_Educational_Information.aspx";
                        Response response=MyOkHttpUtils.okHttpPostEduInfo(urlDelete,data.get("json"));

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String str=response.body().string();

                                msg.what=SUCCESS_DELETE;
                                msg.obj=str;
                                msg.arg1=p;
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

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_person_education_new:

                showNewOrModifyDialog("new",-1);

                break;

        }

    }

    private void showNewOrModifyDialog(final String sign, final int position){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(mContext).inflate(R.layout.dialog_person_edu,null,false);
        builder.setView(view);

       final AlertDialog dialog=builder.create();

        final EditText etName= (EditText) view.findViewById(R.id.et_dialog_person_edu_name);
        final EditText etEdu= (EditText) view.findViewById(R.id.et_dialog_person_edu_edu);
        final EditText etMajor= (EditText) view.findViewById(R.id.et_dialog_person_edu_major);
        final Calendar c = Calendar.getInstance();
        final TextView tvStartTime= (TextView) view.findViewById(R.id.tv_dialog_person_edu_start_time);
        final TextView tvEndTime= (TextView) view.findViewById(R.id.tv_dialog_person_edu_end_time);

        if(TextUtils.equals(sign,"new")){
            tvStartTime.setText(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1)+ "-" + c.get(Calendar.DAY_OF_MONTH));
            tvEndTime.setText(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1)+ "-" + c.get(Calendar.DAY_OF_MONTH));
        }else if(TextUtils.equals(sign,"modify")){
            tvStartTime.setText(MyDateUtils.stringToYMD(data.get(position).getSTART_DATE()));
            tvEndTime.setText(MyDateUtils.stringToYMD(data.get(position).getEND_DATE()));
        }

        tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(mContext,new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvStartTime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(mContext,new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvEndTime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        if(TextUtils.equals(sign,"modify")){
            etName.setText(data.get(position).getSCHOOL());
            etEdu.setText(data.get(position).getEDUCATION());
            etMajor.setText(data.get(position).getSPECIALTY());
        }

        Button btnSure= (Button) view.findViewById(R.id.btn_dialog_person_edu_sure);
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(mContext,"确定",Toast.LENGTH_SHORT).show();

                String nameStr=etName.getText().toString().trim();
                String eduStr=etEdu.getText().toString().trim();
                String majorStr=etMajor.getText().toString().trim();
                String startTimeStr=tvStartTime.getText().toString().trim();
                String endTimeStr=tvEndTime.getText().toString().trim();

                 //Toast.makeText(mContext,"学校="+nameStr+"学历="+eduStr+"专业="+majorStr+"开始时间="+startTimeStr+"结束时间="+endTimeStr,Toast.LENGTH_SHORT).show();

                newOrModifyEduInfo(data,position,sign,nameStr,eduStr,majorStr,startTimeStr,endTimeStr);

                dialog.dismiss();
            }
        });
        Button btnCancel = (Button) view.findViewById(R.id.btn_dialog_person_edu_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,"取消",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    };


    private void newOrModifyEduInfo(List<EduInfo> list, int position, final String sign, final String name, final String edu, final String major, final String startTime, final String endTime){
    //    http://web.youli.pw:89/Json/Set_Educational_Information.aspx?
        // json={"ID":"0","START_DATE":"2017-09-11","EDUCATION":"我的学历","SFZ":"310108198004026642","SPECIALTY":"我的专业","END_DATE":"2017-09-04","SCHOOL":"我的学校"}
        //Toast.makeText(mContext,"学校="+name+"学历="+edu+"专业="+major+"开始时间="+startTime+"结束时间="+endTime,Toast.LENGTH_SHORT).show();
       JSONObject jsonObj=new JSONObject();
        try {

            if(TextUtils.equals(sign,"new")){
                jsonObj.put("ID",""+0);
            }else if(TextUtils.equals(sign,"modify")){
                jsonObj.put("ID",list.get(position).getID()+"");
                jsonObj.put("UPDATE_STAFF",""+list.get(position).getUPDATE_STAFF());
                jsonObj.put("Type",""+list.get(position).getType());
                jsonObj.put("CREATE_STAFF",""+list.get(position).getCREATE_STAFF());
                jsonObj.put("UPDATE_DATE",list.get(position).getUPDATE_DATE());
                jsonObj.put("CREATE_DATE",list.get(position).getCREATE_DATE());
            }


            jsonObj.put("START_DATE",startTime);//开始时间
            jsonObj.put("EDUCATION",edu);//学历
            jsonObj.put("SFZ",sfzStr);//身份证
            jsonObj.put("SPECIALTY",major);//专业
            jsonObj.put("END_DATE",endTime);//结束时间
            jsonObj.put("SCHOOL",name);//学校名称

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String data=jsonObj.toString();

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String urlDelete=MyOkHttpUtils.BaseUrl+"/Json/Set_Educational_Information.aspx";
                        Response response=MyOkHttpUtils.okHttpPostEduInfo(urlDelete,data);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String str=response.body().string();

                                if(TextUtils.equals(sign,"new")){
                                    msg.what=SUCCESS_NEW;
                                }else if(TextUtils.equals(sign,"modify")){
                                    msg.what=SUCCESS_MODIFY;
                                }

                                msg.obj=str;
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

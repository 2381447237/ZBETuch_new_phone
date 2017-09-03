package com.youli.zbetuch.jingan.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.EduInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by ZHengBin on 2017/8/26.
 */

public class PersonEduActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext=this;
    private ListView lv;
    private List<EduInfo> data=new ArrayList<>();
    private CommonAdapter adapter;
    private final int SUCCESS=10000;
    private final int SUCCESS_DELETE=10001;
    private final int  PROBLEM=10002;
    private String sfzStr;
    private Button btnNew;
    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS:

                    data.addAll((List<EduInfo>)msg.obj);

                    Log.e("2017/8/29","教育信息=="+data);

                    setLvAdapter(data);

                    break;

                case SUCCESS_DELETE:

                    Toast.makeText(mContext,"删除成功",Toast.LENGTH_SHORT).show();
                    //data.remove(p);
                    adapter.notifyDataSetChanged();
                    break;

                case PROBLEM:

                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();

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

        lv= (ListView) findViewById(R.id.lv_person_education);

        btnNew= (Button) findViewById(R.id.btn_person_education_new);
        btnNew.setOnClickListener(this);
       initDatas();

    }
    private void initDatas(){
        //http://web.youli.pw:89/Json/Get_Educational_Information.aspx?sfz=310108198004026642
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_Educational_Information.aspx?sfz="+sfzStr;

                        Log.e("2017/8/29","url=="+url);

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String resStr=response.body().string();

                                Gson gson=new Gson();

                                msg.what=SUCCESS;
                                msg.obj=gson.fromJson(resStr, new TypeToken<List<EduInfo>>(){}.getType());
                                mHandler.sendMessage(msg);

                            } catch (IOException e) {
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

        adapter=new CommonAdapter<EduInfo>(mContext,eInfo,R.layout.item_fmt_education_info_lv) {

            @Override
            public void convert(CommonViewHolder holder, EduInfo item, final int position) {

                TextView name=holder.getView(R.id.tv_item_fmt_education_info_name);
                name.setText(item.getSCHOOL());
                TextView edu=holder.getView(R.id.tv_item_fmt_education_info_edu);
                edu.setText(item.getEDUCATION());
                TextView major=holder.getView(R.id.tv_item_fmt_education_info_major);
                major.setText(item.getSPECIALTY());
                TextView startTime=holder.getView(R.id.tv_item_fmt_education_info_starttime);
                startTime.setText(item.getSTART_DATE().split("T")[0]);
                TextView endTime=holder.getView(R.id.tv_item_fmt_education_info_endtime);
                endTime.setText(item.getEND_DATE().split("T")[0]);

                LinearLayout ll=holder.getView(R.id.ll_item_fmt_education_info);

                if(position%2!=0){
                    ll.setBackgroundColor(Color.WHITE);
                }else{
                    ll.setBackgroundColor(Color.parseColor("#b7daf4"));
                }
                RelativeLayout rlContent=holder.getView(R.id.rl_item_fmt_education_info_content);
                rlContent.setVisibility(View.VISIBLE);

                Button upDate=holder.getView(R.id.btn_item_fmt_education_info_update);
                upDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(mContext,"第"+position+"个修改",Toast.LENGTH_SHORT).show();

                        showNewOrModifyDialog("modify",position);

                    }
                });

                Button upDelete=holder.getView(R.id.btn_item_fmt_education_info_delete);
                upDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        showDeleteDialog(position);
                    }
                });
            }
        };

        lv.setAdapter(adapter);


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

                       // http://web.youli.pw:89/Json/Set_Educational_Information.aspx?ID=&Type=-1

                        String deleteUrl=MyOkHttpUtils.BaseUrl+"/Json/Set_Educational_Information.aspx?ID="+data.get(p).getID()+"&Type=-1";

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

    private void showNewOrModifyDialog(String sign,int position){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(mContext).inflate(R.layout.dialog_person_edu,null,false);
        builder.setView(view);

       final AlertDialog dialog=builder.create();

        EditText etName= (EditText) view.findViewById(R.id.et_dialog_person_edu_name);
        EditText etEdu= (EditText) view.findViewById(R.id.et_dialog_person_edu_edu);
        EditText etMajor= (EditText) view.findViewById(R.id.et_dialog_person_edu_major);

        if(TextUtils.equals(sign,"modify")){
            etName.setText(data.get(position).getSCHOOL());
            etEdu.setText(data.get(position).getEDUCATION());
            etMajor.setText(data.get(position).getSPECIALTY());
        }

        Button btnSure= (Button) view.findViewById(R.id.btn_dialog_person_edu_sure);
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"确定",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        Button btnCancel = (Button) view.findViewById(R.id.btn_dialog_person_edu_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"取消",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    };

}

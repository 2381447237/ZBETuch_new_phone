package com.youli.zbetuch.jingan.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.MainAdapter;
import com.youli.zbetuch.jingan.entity.GetStaffInfo;
import com.youli.zbetuch.jingan.entity.JobsInfo;
import com.youli.zbetuch.jingan.entity.MainContent;
import com.youli.zbetuch.jingan.entity.MeetInfo;
import com.youli.zbetuch.jingan.entity.NewsInfo;
import com.youli.zbetuch.jingan.entity.WorkNoticeInfo;
import com.youli.zbetuch.jingan.utils.IOUtil;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import com.youli.zbetuch.jingan.view.CircleImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Response;

public class MainLayoutActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext=MainLayoutActivity.this;
    private GridView gv;
    private String titleArray []={"会议通知","工作通知","岗位信息","近期热点"};
    private List<MainContent> data=new ArrayList<>();
    private List<MeetInfo> childData1=new ArrayList<>();
    private List<WorkNoticeInfo> childData2=new ArrayList<>();
    private List<JobsInfo> childData3=new ArrayList<>();
    private List<NewsInfo> childData4=new ArrayList<>();

    private TextView nameTv,emailTv,phoneTv;
    private CircleImageView picIv;
    private Button workBtn;

    private final int SUCCEED_NAME=10000;
    private final int SUCCEED_PIC=10001;
    private final int SUCCEED_MI=10002;
    private final int SUCCEED_WN=10003;
    private final int SUCCEED_JOB=10004;
    private final int SUCCEED_NI=10005;
    private final int  PROBLEM=10006;

    public static String adminName;

    public static int adminId;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCEED_NAME:

                    nameTv.setText(((GetStaffInfo)msg.obj).getNAME());
                    emailTv.setText(((GetStaffInfo)msg.obj).getEMAIL()+"2381447237@qq.com");
                    phoneTv.setText(((GetStaffInfo)msg.obj).getPHONE()+"13120601125");
                    adminName=nameTv.getText().toString().trim();
                    adminId=((GetStaffInfo)msg.obj).getID();
                    break;

                case SUCCEED_PIC:

                    picIv.setImageBitmap((Bitmap) msg.obj);
                  // picIv.setLayoutParams(new RelativeLayout.LayoutParams(100,700));



                    break;

                case SUCCEED_MI:

                    int itemSize1=childData1.size();


                        for (int i = 0; i < 4 - itemSize1; i++) {

                            childData1.add(new MeetInfo("", ""));

                        }


                    data.add(new MainContent(titleArray[0], childData1,null,null,null));
                    getWorkNotice();//工作通知
                    break;

                case SUCCEED_WN:



                    int itemSize2=childData2.size();



                        for (int i = 0; i < 4 - itemSize2; i++) {

                            childData2.add(new WorkNoticeInfo("", ""));

                        }

                    data.add(new MainContent(titleArray[1], null,childData2,null,null));

                    getJobsInfo();//岗位信息


                    break;
                case SUCCEED_JOB:


                    int itemSize3=childData3.size();



                        for (int i = 0; i < 4 - itemSize3; i++) {

                            childData3.add(new JobsInfo("", ""));

                        }

                            data.add(new MainContent(titleArray[2], null,null,childData3,null));

                       getNewsInfo();//近期热点
                    break;

                case SUCCEED_NI:

                    int itemSize4=childData4.size();

                        for (int i = 0; i < 4 - itemSize4; i++) {

                            childData4.add(new NewsInfo("", ""));

                        }

                    data.add(new MainContent(titleArray[3], null,null,null,childData4));

                        MainAdapter mainAdapter=new MainAdapter(data,MainLayoutActivity.this);
                        gv.setAdapter(mainAdapter);

                    break;

                case PROBLEM:

                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();

                    break;
            }

        }
    };

    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        nameTv= (TextView) findViewById(R.id.main_layout_name_tv);
        emailTv= (TextView) findViewById(R.id.main_layout_email_tv);
        phoneTv= (TextView) findViewById(R.id.main_layout_phone_tv);
        picIv= (CircleImageView) findViewById(R.id.main_layout_head_iv);
        gv= (GridView) findViewById(R.id.main_layout_gv);
        workBtn= (Button) findViewById(R.id.main_layout_work_btn);
        workBtn.setOnClickListener(this);
        initData();

    }

    private void initData(){

        getStaffName();//登录人员的名称，电话，电子邮箱

        getStaffPic();//登录人员的头像

        getMeetInfo();//会议通知
    }

    private void getStaffPic(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                        //http://web.youli.pw:89/Json/GetStaffPic.aspx
                        String urlPic=MyOkHttpUtils.BaseUrl+"/Json/GetStaffPic.aspx";
                        Response response=MyOkHttpUtils.okHttpGet(urlPic);
                        try {
                            Message msg=Message.obtain();

                            if(response!=null){
                            InputStream is=response.body().byteStream();

                              byte[] picData=IOUtil.getBytesByStream(is);

                               Bitmap btp= BitmapFactory.decodeByteArray(picData,0,picData.length);

                                msg.obj=btp;
                                msg.what=SUCCEED_PIC;
                                mHandler.sendMessage(msg);

                            }else{

                                sendProblemMessage(msg);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();

    }

    private void getStaffName(){

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        String urlStaff= MyOkHttpUtils.BaseUrl+"/Json/Get_Staff.aspx";
                        Response response=MyOkHttpUtils.okHttpGet(urlStaff);

                        try {
                            Message msg=Message.obtain();
                            if(response!=null){

                                String infoStr=response.body().string();
                                Gson gson=new Gson();
                                GetStaffInfo staffInfo=gson.fromJson(infoStr,GetStaffInfo.class);
                                msg.obj=staffInfo;
                                msg.what=SUCCEED_NAME;
                                mHandler.sendMessage(msg);
                            }else {
                                sendProblemMessage(msg);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();


    }

    private void getMeetInfo(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String urlMi=MyOkHttpUtils.BaseUrl+"/Json/Get_Meeting_Master.aspx?State=true&page=0&rows=4";
                        Response response=MyOkHttpUtils.okHttpGet(urlMi);

                        try {
                            Message msg=Message.obtain();
                            if(response!=null){
                                String miStr=response.body().string();
                                Gson gson=new Gson();
                                childData1=gson.fromJson(miStr,new TypeToken<List<MeetInfo>>(){}.getType());
                                msg.obj=childData1;
                                msg.what=SUCCEED_MI;
                                mHandler.sendMessage(msg);
                            }else{
                                sendProblemMessage(msg);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();

    }

    private void getWorkNotice(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String urlWn=MyOkHttpUtils.BaseUrl+"/Json/Get_Work_Notice.aspx?page=0&rows=4";
                        Response response=MyOkHttpUtils.okHttpGet(urlWn);

                        try {
                            Message msg=Message.obtain();
                            if(response!=null){
                                String wnStr=response.body().string();
                                Gson gson=new Gson();
                               childData2=gson.fromJson(wnStr,new TypeToken<List<WorkNoticeInfo>>(){}.getType());
                                msg.obj=childData2;
                                msg.what=SUCCEED_WN;
                                mHandler.sendMessage(msg);
                            }else{
                                sendProblemMessage(msg);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();

    }


    private void getJobsInfo(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String urlJi=MyOkHttpUtils.BaseUrl+"/Json/GetJobs.aspx?page=0&rows=4";
                        Response response=MyOkHttpUtils.okHttpGet(urlJi);

                        try {
                            Message msg=Message.obtain();
                            if(response!=null){
                                String jiStr=response.body().string();
                                Gson gson=new Gson();
                                childData3=gson.fromJson(jiStr,new TypeToken<List<JobsInfo>>(){}.getType());
                                msg.obj=childData3;
                                msg.what=SUCCEED_JOB;
                                mHandler.sendMessage(msg);
                            }else{
                                sendProblemMessage(msg);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();

    }

    private void getNewsInfo(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String urlNi= MyOkHttpUtils.BaseUrl+"/Json/Get_News.aspx?page=0&rows=4";
                        Response response=MyOkHttpUtils.okHttpGet(urlNi);

                        try {
                            Message msg=Message.obtain();
                            if(response!=null){
                                String niStr=response.body().string();
                                Gson gson=new Gson();
                                childData4=gson.fromJson(niStr,new TypeToken<List<NewsInfo>>(){}.getType());
                                msg.obj=childData4;
                                msg.what=SUCCEED_NI;
                                mHandler.sendMessage(msg);
                            }else{
                                sendProblemMessage(msg);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();

    }

    private  void sendProblemMessage(Message msg){
        msg.what=PROBLEM;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.main_layout_work_btn:


                Intent intent=new Intent(this,FunctionListActivity.class);
                startActivity(intent);

                break;
        }

    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("您确定退出吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            ActivityController.finishAll();
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

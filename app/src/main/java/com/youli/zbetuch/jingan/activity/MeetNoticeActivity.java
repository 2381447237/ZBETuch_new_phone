package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.entity.MeetNoticeInfo;
import com.youli.zbetuch.jingan.entity.WorkNoticeInfo;
import com.youli.zbetuch.jingan.entity.WorkNoticeReadInfo;
import com.youli.zbetuch.jingan.fragment.CurrentMeetFragment;
import com.youli.zbetuch.jingan.fragment.HistoryMeetFragment;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by liutao on 2017/9/1.
 */

public class MeetNoticeActivity extends FragmentActivity{

    private Context mContext=MeetNoticeActivity.this;
    private final int SUCCEED_READNUM=10000;
    private final int SUCCEED_NODATA=10001;
    private final int  PROBLEM=10002;


    private TextView readNum;
    private List<WorkNoticeReadInfo> readNumList=new ArrayList<>();
    private FragmentManager fm=this.getSupportFragmentManager();
    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCEED_READNUM:
                    String totalNum ="0";
                    String weiduNum ="0";
                    String  rate = "0.00";

                    readNumList.clear();
                    readNumList.addAll((List<WorkNoticeReadInfo>)msg.obj);

                    if(readNumList.get(0).getVALUE1()!=null){
                        totalNum=readNumList.get(0).getVALUE1();
                    }

                    if(readNumList.get(0).getVALUE2()!=null){
                        weiduNum=readNumList.get(0).getVALUE2();
                    }

                    if(readNumList.get(0).getRATE()!=null){
                        rate=readNumList.get(0).getRATE();
                    }

                    readNum.setText("本月" + totalNum + "条已读" + weiduNum + "条"
                            + "读取率:" + rate+ "%");
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
       setContentView(R.layout.activity_meet_notice);

        FragmentTransaction ft=fm.beginTransaction();

        CurrentMeetFragment cmf=new CurrentMeetFragment();

        ft.add(R.id.fl_meet_notice,cmf);

        ft.commit();

        readNum= (TextView) findViewById(R.id.tv_meet_notice_readnum);


        initDatas();
    }

    //获得读取数目
    private void initDatas(){

      //  http://web.youli.pw:89/Json/Get_Meeting_Read.aspx

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String readUrl=MyOkHttpUtils.BaseUrl+"/Json/Get_Meeting_Read.aspx";

                        Response response=MyOkHttpUtils.okHttpGet(readUrl);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String dataStr=response.body().string();

                                if(!TextUtils.equals(dataStr,"")&&!TextUtils.equals(dataStr,"[]")) {
                                    Gson gson = new Gson();

                                    msg.obj = gson.fromJson(dataStr, new TypeToken<List<WorkNoticeReadInfo>>() {
                                    }.getType());
                                    msg.what = SUCCEED_READNUM;
                                }else{
                                    msg.what = SUCCEED_NODATA;
                                }
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

    public void onChange(View v){




        switch (v.getId()){

            case R.id.rb_meet_notice_current://当前会议

                FragmentTransaction cft=fm.beginTransaction();

               CurrentMeetFragment cmt=new CurrentMeetFragment();

                cft.replace(R.id.fl_meet_notice,cmt);

                cft.commit();

                break;

            case R.id.rb_meet_notice_history://历史会议

                FragmentTransaction hft=fm.beginTransaction();

                HistoryMeetFragment hmt=new HistoryMeetFragment();

                hft.replace(R.id.fl_meet_notice,hmt);

                hft.commit();

                break;

        }

    }

    @Override
    public void onBackPressed() {
       finish();
    }
}

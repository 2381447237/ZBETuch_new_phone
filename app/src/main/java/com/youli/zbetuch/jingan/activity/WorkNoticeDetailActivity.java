package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.entity.WorkNoticeInfo;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by liutao on 2017/8/30.
 */

public class WorkNoticeDetailActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext=WorkNoticeDetailActivity.this;

    private final int SUCCEED_BUTTON_GET_STATE=10000;
    private final int SUCCEED_BUTTON_SET_STATE=10001;
    private final int  PROBLEM=10002;

    private Button btnIsRead;
    private TextView tvTitle,tvDoc,tvNoticeTime,tvNotifier,tvCreateDate;
    private WorkNoticeInfo info;


    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCEED_BUTTON_GET_STATE:

                    if(TextUtils.equals("true",(String)msg.obj)){
                        btnIsRead.setEnabled(false);
                        btnIsRead.setText("已读");
                    } else {
                        btnIsRead.setEnabled(true);
                        btnIsRead.setText("未读");
                    }

                    break;

                case SUCCEED_BUTTON_SET_STATE:

                    if(TextUtils.equals("true",(String)msg.obj)){
                        btnIsRead.setEnabled(false);
                        btnIsRead.setText("已读");
                        Toast.makeText(mContext, "已阅读", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "提交失败", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case PROBLEM:

                    Toast.makeText(mContext,"网络不给力", Toast.LENGTH_SHORT).show();


                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worknotice_detail);

        info=(WorkNoticeInfo)getIntent().getExtras().getSerializable("WORKDATA");

        initViews();
    }

    private void initViews(){

        btnIsRead= (Button) findViewById(R.id.btn_worknotice_detail_isread);
        btnIsRead.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_worknotice_detail_title);
        tvDoc= (TextView) findViewById(R.id.tv_worknotice_detail_doc);
        tvNoticeTime= (TextView) findViewById(R.id.tv_worknotice_detail_notice_time);
        tvNotifier= (TextView) findViewById(R.id.tv_worknotice_detail_notifier);
        tvCreateDate= (TextView) findViewById(R.id.tv_worknotice_detail_create_date);

          initDatas();
    }

     private void initDatas(){

      tvTitle.setText(info.getTITLE());
         tvDoc.setText(info.getDOC());
         tvNoticeTime.setText(MyDateUtils.stringToYMDHMS(info.getNOTICE_TIME()));
         tvNotifier.setText("通知人："+info.getCREATE_STAFF_NAME());
         tvCreateDate.setText("发布时间："+MyDateUtils.stringToYMDHMS(info.getCREATE_DATE()));


//         获取工作通知按钮的状态
         getorSetButtonState("get");
     }


    private void getorSetButtonState(final String sign){
        //         获取工作通知按钮的状态
//         http://web.youli.pw:89/Json/Get_Work_Notice_Check.aspx?master_id=1
//        已读按钮事件
//        http://web.youli.pw:89/Json/Set_Work_Notice_Check.aspx?master_id=1
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url = null;

                        if(TextUtils.equals(sign,"get")){
                            url= MyOkHttpUtils.BaseUrl+"/Json/Get_Work_Notice_Check.aspx?master_id="+info.getID();
                        }else if(TextUtils.equals(sign,"set")){
                            url= MyOkHttpUtils.BaseUrl+"/Json/Set_Work_Notice_Check.aspx?master_id="+info.getID();
                        }

                        Message msg=Message.obtain();

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        if(response!=null){

                            try {
                                String resStr=response.body().string();
                               if(TextUtils.equals(sign,"get")) {
                                   msg.what = SUCCEED_BUTTON_GET_STATE;

                               }else if(TextUtils.equals(sign,"set")){
                                   msg.what = SUCCEED_BUTTON_SET_STATE;

                               }
                                msg.obj = resStr;
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

            case R.id.btn_worknotice_detail_isread:

                getorSetButtonState("set");

                break;

        }

    }
}

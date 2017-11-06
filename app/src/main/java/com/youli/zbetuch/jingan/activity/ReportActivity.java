package com.youli.zbetuch.jingan.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/9/27.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 统计报表
 *
 */

public class ReportActivity extends BaseActivity implements View.OnClickListener{

    private final int SUCCESS_DATE=10001;
    private final int SUCCESS_NODATA=10002;
    private final int FAIL=10003;

    private Context mContext=ReportActivity.this;

    private ImageView ivStreet1,ivCommittee1,ivAge1,ivSex1,ivDegree1;
    private ImageView ivStreet2,ivCommittee2,ivAge2,ivSex2,ivDegree2;
    private TextView tvDate;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS_DATE:
                    String[] times = (msg.obj+"").split(" ");
                    tvDate.setText("报表日期："+ times[0]);

                    break;

                case FAIL:

                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();

                    break;

                case SUCCESS_NODATA:
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        initViews();

    }

    private void initViews(){

        ivStreet1= (ImageView) findViewById(R.id.iv_report_street1);
        ivCommittee1= (ImageView) findViewById(R.id.iv_report_committee1);
        ivAge1= (ImageView) findViewById(R.id.iv_report_age1);
        ivSex1= (ImageView) findViewById(R.id.iv_report_sex1);
        ivDegree1= (ImageView) findViewById(R.id.iv_report_degree1);
        ivStreet2= (ImageView) findViewById(R.id.iv_report_street2);
        ivCommittee2= (ImageView) findViewById(R.id.iv_report_committee2);
        ivAge2= (ImageView) findViewById(R.id.iv_report_age2);
        ivSex2= (ImageView) findViewById(R.id.iv_report_sex2);
        ivDegree2= (ImageView) findViewById(R.id.iv_report_degree2);
        ivStreet1.setOnClickListener(this);
        ivCommittee1.setOnClickListener(this);
        ivAge1.setOnClickListener(this);
        ivSex1.setOnClickListener(this);
        ivDegree1.setOnClickListener(this);
        ivStreet2.setOnClickListener(this);
        ivCommittee2.setOnClickListener(this);
        ivAge2.setOnClickListener(this);
        ivSex2.setOnClickListener(this);
        ivDegree2.setOnClickListener(this);

        tvDate= (TextView) findViewById(R.id.tv_report_date);

        getDate();
    }

    private  void getDate(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                       // http://web.youli.pw:89/Json/Get_Update_Time.aspx
                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_Update_Time.aspx";

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response.body()!=null){


                            try {
                                String resStr=response.body().string();

                                if(!TextUtils.equals(resStr,"")){

                                    msg.what=SUCCESS_DATE;
                                    msg.obj=resStr;

                                }else{

                                    msg.what=SUCCESS_NODATA;

                                }
                                mHandler.sendMessage(msg);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }else{

                           msg.what=FAIL;
                            mHandler.sendMessage(msg);

                        }


                    }
                }

        ).start();

    }

    @Override
    public void onClick(View v) {

        Intent intent=new Intent();

        switch (v.getId()){

            case R.id.iv_report_street1:

                intent.setClass(mContext,ReportDetailActivity.class);
                intent.putExtra(
                        "url",
                        MyOkHttpUtils.BaseUrl + "/Chart/Chart_STREET.aspx?staff="
                                + MainLayoutActivity.adminId+ "&type="
                                + URLEncoder.encode("登记失业"));
                startActivity(intent);

                break;

            case R.id.iv_report_committee1:


                intent.setClass(mContext,ReportDetailActivity.class);
                intent.putExtra(
                        "url",
                        MyOkHttpUtils.BaseUrl
                                + "/Chart/Chart_committee.aspx?staff="
                                + MainLayoutActivity.adminId + "&type="
                                + URLEncoder.encode("登记失业"));
                startActivity(intent);
                break;
            case R.id.iv_report_age1:


                intent.setClass(mContext,ReportDetailActivity.class);
                intent.putExtra("url", MyOkHttpUtils.BaseUrl
                        + "/Chart/Chart_age.aspx?staff=" + MainLayoutActivity.adminId
                        + "&type=" + URLEncoder.encode("登记失业"));
                startActivity(intent);
                break;
            case R.id.iv_report_sex1:


                intent.setClass(mContext,ReportDetailActivity.class);
                intent.putExtra("url",MyOkHttpUtils.BaseUrl
                        + "/Chart/Chart_sex.aspx?staff=" + MainLayoutActivity.adminId
                        + "&type=" + URLEncoder.encode("登记失业"));
                startActivity(intent);
                break;
            case R.id.iv_report_degree1:


                intent.setClass(mContext,ReportDetailActivity.class);
                intent.putExtra("url", MyOkHttpUtils.BaseUrl
                        + "/Chart/Chart_edu.aspx?staff=" + MainLayoutActivity.adminId
                        + "&type=" + URLEncoder.encode("登记失业"));
                startActivity(intent);
                break;
            case R.id.iv_report_street2:


                intent.setClass(mContext,ReportDetailActivity.class);
                intent.putExtra(
                        "url",
                        MyOkHttpUtils.BaseUrl + "/Chart/Chart_STREET.aspx?staff="
                                + MainLayoutActivity.adminId + "&type="
                                + URLEncoder.encode("未登记失业"));
                startActivity(intent);
                break;
            case R.id.iv_report_committee2:


                intent.setClass(mContext,ReportDetailActivity.class);
                intent.putExtra(
                        "url",
                        MyOkHttpUtils.BaseUrl
                                + "/Chart/Chart_committee.aspx?staff="
                                + MainLayoutActivity.adminId + "&type="
                                + URLEncoder.encode("未登记失业"));
                startActivity(intent);
                break;
            case R.id.iv_report_age2:


                intent.setClass(mContext,ReportDetailActivity.class);
                intent.putExtra("url", MyOkHttpUtils.BaseUrl
                        + "/Chart/Chart_age.aspx?staff=" + MainLayoutActivity.adminId
                        + "&type=" + URLEncoder.encode("未登记失业"));
                startActivity(intent);
                break;
            case R.id.iv_report_sex2:


                intent.setClass(mContext,ReportDetailActivity.class);
                intent.putExtra("url", MyOkHttpUtils.BaseUrl
                        + "/Chart/Chart_sex.aspx?staff=" + MainLayoutActivity.adminId
                        + "&type=" + URLEncoder.encode("未登记失业"));
                startActivity(intent);
                break;
            case R.id.iv_report_degree2:


                intent.setClass(mContext,ReportDetailActivity.class);
                intent.putExtra("url", MyOkHttpUtils.BaseUrl
                        + "/Chart/Chart_edu.aspx?staff=" + MainLayoutActivity.adminId
                        + "&type=" + URLEncoder.encode("未登记失业"));
                startActivity(intent);
                break;
        }


    }
}

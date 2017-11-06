package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.MainAdapter;
import com.youli.zbetuch.jingan.adapter.MyFpAdapter;
import com.youli.zbetuch.jingan.entity.GetStaffInfo;
import com.youli.zbetuch.jingan.entity.JobsInfo;
import com.youli.zbetuch.jingan.entity.MainContent;
import com.youli.zbetuch.jingan.entity.MeetNoticeInfo;
import com.youli.zbetuch.jingan.entity.NewsInfo;
import com.youli.zbetuch.jingan.entity.WorkNoticeInfo;
import com.youli.zbetuch.jingan.fragment.FollowListFragment;
import com.youli.zbetuch.jingan.fragment.LoginInfoFragment;
import com.youli.zbetuch.jingan.fragment.OpRecordFragment;
import com.youli.zbetuch.jingan.fragment.SysInstallFragment;
import com.youli.zbetuch.jingan.fragment.WorkLogFragment;
import com.youli.zbetuch.jingan.utils.IOUtil;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/10/13.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 个人数据中心
 */

public class PersonDataCenterActivity extends FragmentActivity implements View.OnClickListener{

    private Context mContext=PersonDataCenterActivity.this;

    private final int SUCCEED_PIC=10001;
    private final int SUCCEED_NAME=10002;
    private final int SUCCEED_NODATA=10003;
    private final int  PROBLEM=10006;

   private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private TabLayout tl;
    private LinearLayout llPi;//个人信息;
    private Button btnCaiDan,btnJiugongge,btnZhuye;
    private ImageView ivHead;
    private TextView tvName,tvBm,tvPhone;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCEED_NAME:

                    tvName.setText(((GetStaffInfo)msg.obj).getNAME());
                    tvBm.setText(((GetStaffInfo)msg.obj).getDEPT());
                    tvPhone.setText(((GetStaffInfo)msg.obj).getPHONE());

                    break;

                case SUCCEED_PIC:

                    ivHead.setImageBitmap((Bitmap) msg.obj);




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
        setContentView(R.layout.activity_person_datacenter);

        btnCaiDan= (Button) findViewById(R.id.btn_person_datacenter_caidan);
        btnCaiDan.setOnClickListener(this);
        btnJiugongge= (Button) findViewById(R.id.btn_person_datacenter_jiugongge);
        btnJiugongge.setOnClickListener(this);
        btnZhuye= (Button) findViewById(R.id.btn_person_datacenter_zhuye);
        btnZhuye.setOnClickListener(this);
        llPi= (LinearLayout) findViewById(R.id.ll_person_datacenter_pif);
        ivHead= (ImageView) findViewById(R.id.iv_person_datacenter_head);
        tvName= (TextView) findViewById(R.id.tv_person_datacenter_name);
        tvBm= (TextView) findViewById(R.id.tv_person_datacenter_bm);
        tvPhone= (TextView) findViewById(R.id.tv_person_datacenter_phone);
        getStaffPic();//登录人员的头像
        getStaffName();//登录人员的名称，电话，电子邮箱

        viewPager= (ViewPager) findViewById(R.id.vp_person_datacenter);
        tl= (TabLayout) findViewById(R.id.tl_person_datacenter);
        fragmentList=new ArrayList<>();

        fragmentList.add(new LoginInfoFragment());//登录信息
        fragmentList.add(new WorkLogFragment());//工作日志
        fragmentList.add(new OpRecordFragment());//操作记录
        fragmentList.add(new FollowListFragment());//关注列表
        fragmentList.add(new SysInstallFragment());//系统安装情况

        FragmentManager fm=getSupportFragmentManager();
        MyFpAdapter fpAdapter=new MyFpAdapter(fm,fragmentList);
        viewPager.setAdapter(fpAdapter);
        viewPager.setOffscreenPageLimit(5);
        tl.setupWithViewPager(viewPager);

        for(int i=0;i<tl.getTabCount();i++){
            TabLayout.Tab tab=tl.getTabAt(i);
            tab.setCustomView(getTabView(i));
        }
    }



    private View getTabView(int i){

        View view= LayoutInflater.from(this).inflate(R.layout.tab_item,null,false);


        TextView tv= (TextView) view.findViewById(R.id.tab_item_tv);

        if(i==0){
            tv.setText("登录信息");
        }else if(i==1){
            tv.setText("工作日志");
        }else if(i==2){
            tv.setText("操作记录");
        }else if(i==3){
            tv.setText("关注列表");
        }else if(i==4){
            tv.setText("系统安装情况");
        }

        return view;

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_person_datacenter_caidan:

                if(llPi.getVisibility()==View.VISIBLE){
                    llPi.setVisibility(View.GONE);
                }else if(llPi.getVisibility()==View.GONE){
                    llPi.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.btn_person_datacenter_jiugongge:

                Intent fIntent=new Intent(this,FunctionListActivity.class);
                startActivity(fIntent);

                break;

            case R.id.btn_person_datacenter_zhuye:

                Intent mIntent=new Intent(this,MainLayoutActivity.class);
                startActivity(mIntent);
                finish();

                break;
        }

    }

    private void getStaffPic(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                        //http://web.youli.pw:89/Json/GetStaffPic.aspx
                        String urlPic= MyOkHttpUtils.BaseUrl+"/Json/GetStaffPic.aspx";
                        Response response=MyOkHttpUtils.okHttpGet(urlPic);
                        try {
                            Message msg=Message.obtain();

                            if(response!=null){
                                InputStream is=response.body().byteStream();

                                byte[] picData= IOUtil.getBytesByStream(is);

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

                                if(!TextUtils.equals(infoStr,"")&&!TextUtils.equals(infoStr,"{}")) {

                                    Gson gson = new Gson();
                                    GetStaffInfo staffInfo = gson.fromJson(infoStr, GetStaffInfo.class);
                                    msg.obj = staffInfo;
                                    msg.what = SUCCEED_NAME;
                                }else{
                                    msg.what = SUCCEED_NODATA;
                                }
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

    private  void sendProblemMessage(Message msg){
        msg.what=PROBLEM;
        mHandler.sendMessage(msg);
    }

}

package com.youli.zbetuch.jingan.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.MyFpAdapter;
import com.youli.zbetuch.jingan.entity.PersonInfo;
import com.youli.zbetuch.jingan.fragment.EduInfoFragment;
import com.youli.zbetuch.jingan.fragment.FamilyInfoFragment;
import com.youli.zbetuch.jingan.fragment.PersonInfoFragment;
import com.youli.zbetuch.jingan.fragment.PersonReFragment;
import com.youli.zbetuch.jingan.fragment.ServiceReFramgent;
import com.youli.zbetuch.jingan.utils.IOUtil;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by ZHengBin on 2017/8/12.
 */

public class PersonInfoActivity extends FragmentActivity implements View.OnClickListener{

    private Context mContext=PersonInfoActivity.this;

    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private List<Fragment> mFragments;

    private Button caidanBtn,jiugonggeBtn,zhuyeBtn,guanzhuBtn;
    private ImageView xiugaiIv;
    private LinearLayout pifLl;

    private PersonInfo personInfo;
    private TextView nameTv,sexTv,statusTv,sfzTv;
    private ImageView headIv;

    private final int SUCCEED_PHOTO=10000;
    private final int SUCCEED_FOLLOW=10001;
    private final int  PROBLEM=10002;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCEED_PHOTO:

                    if(msg.obj!=null) {
                        headIv.setImageBitmap((Bitmap) msg.obj);
                    }
                    break;

                case SUCCEED_FOLLOW:

                    if(TextUtils.equals((String)msg.obj,"True")){
                        Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mContext, "已经添加过", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case PROBLEM:
                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();

                    break;

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);

        personInfo=(PersonInfo)getIntent().getSerializableExtra("personInfos");//从ShiwuyeDetailActivity传过来的//从PersonalInfoQueryResult传过来的//从RecomListActivity传过来的//从FollowListActivity传过来的
        if(mFragments==null) {

            initData();
            initView();
        }

    }

    private void initData(){

        mFragments=new ArrayList<Fragment>();

        if(personInfo!=null){
            mFragments.add(new PersonInfoFragment(personInfo));
            mFragments.add(new FamilyInfoFragment(personInfo.getSFZ()));
            mFragments.add(new PersonReFragment(personInfo.getSFZ()));
            mFragments.add(new ServiceReFramgent(personInfo.getSFZ()));
            mFragments.add(new EduInfoFragment(personInfo.getSFZ()));
        }

    }


    private void initView(){

        nameTv= (TextView) findViewById(R.id.tv_person_info_name);
        sexTv= (TextView) findViewById(R.id.tv_person_info_sex);
        statusTv= (TextView) findViewById(R.id.tv_person_info_status);
        sfzTv= (TextView) findViewById(R.id.tv_person_info_sfz);
        headIv= (ImageView) findViewById(R.id.iv_person_info_head);

        if(personInfo!=null) {
            nameTv.setText(personInfo.getNAME());
            sexTv.setText(personInfo.getSEX());
            statusTv.setText(personInfo.getTYPE());
            sfzTv.setText(personInfo.getSFZ());
        }
        pifLl= (LinearLayout) findViewById(R.id.ll_person_info_pif);

        xiugaiIv= (ImageView) findViewById(R.id.iv_person_info_modify);
        xiugaiIv.setOnClickListener(this);
        caidanBtn= (Button) findViewById(R.id.btn_person_info_caidan);
        caidanBtn.setOnClickListener(this);
        jiugonggeBtn= (Button) findViewById(R.id.btn_person_info_jiugongge);
        jiugonggeBtn.setOnClickListener(this);
        zhuyeBtn= (Button) findViewById(R.id.btn_person_info_zhuye);
        zhuyeBtn.setOnClickListener(this);
        guanzhuBtn= (Button) findViewById(R.id.btn_person_info_follow);
        guanzhuBtn.setOnClickListener(this);

          mViewPager= (ViewPager) findViewById(R.id.vp_person_info);
          mRadioGroup= (RadioGroup) findViewById(R.id.rg_person_info);
        MyFpAdapter adapter=new MyFpAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(adapter);

        mViewPager.setOffscreenPageLimit(5);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position,float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position){

                    case 0:
                        mRadioGroup.check(R.id.rb_person_info);
                        break;
                    case 1:
                        mRadioGroup.check(R.id.rb_family_info);
                        break;
                    case 2:
                        mRadioGroup.check(R.id.rb_person_resume);
                        break;
                    case 3:
                        mRadioGroup.check(R.id.rb_service_record);
                        break;
                    case 4:
                        mRadioGroup.check(R.id.rb_edu_info);
                        break;
                }

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){

                    case R.id.rb_person_info:
                        mViewPager.setCurrentItem(0,false);
                        break;
                    case R.id.rb_family_info:
                        mViewPager.setCurrentItem(1,false);
                        break;
                    case R.id.rb_person_resume:
                        mViewPager.setCurrentItem(2,false);
                        break;
                    case R.id.rb_service_record:
                        mViewPager.setCurrentItem(3,false);
                        break;
                    case R.id.rb_edu_info:
                        mViewPager.setCurrentItem(4,false);
                        break;
                }
            }
        });


        if(personInfo!=null) {
            getHeadPhoto();
        }
    }

    //获得头像图片
    private void getHeadPhoto(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
//http://web.youli.pw:89/Web/Personal/windows/ShowPic.aspx?sfz=422201197209204223 头像
                        String urlHead= MyOkHttpUtils.BaseUrl+"/Web/Personal/windows/ShowPic.aspx?sfz="+personInfo.getSFZ();

                        Response response=MyOkHttpUtils.okHttpGet(urlHead);

                        Message msg=Message.obtain();

                        if(response!=null){

                            InputStream is=response.body().byteStream();

                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 2;//图片大小，设置越大，图片越不清晰，占用空间越小
                            Bitmap bmp=BitmapFactory.decodeStream(is,null,options);
                            msg.obj=bmp;
                            msg.what=SUCCEED_PHOTO;
                            mHandler.sendMessage(msg);

                        }else{

                            msg.what=PROBLEM;
                            mHandler.sendMessage(msg);

                        }

                    }
                }

        ).start();

    }

    @Override
    public void onBackPressed() {

        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_person_info_caidan:

                if(pifLl.getVisibility()==View.GONE){
                    pifLl.setVisibility(View.VISIBLE);
                }else if(pifLl.getVisibility()==View.VISIBLE){
                    pifLl.setVisibility(View.GONE);
                }

                break;


            case R.id.btn_person_info_jiugongge:

                Intent fIntent=new Intent(this,FunctionListActivity.class);
                startActivity(fIntent);

                break;

            case R.id.btn_person_info_zhuye:

                Intent mIntent=new Intent(this,MainLayoutActivity.class);
                startActivity(mIntent);
                finish();
                break;

            case R.id.btn_person_info_follow:

                addFollow();

                break;

            case R.id.iv_person_info_modify:

                showModifyDialog();

                break;
        }


    }

    //添加关注
    private void addFollow(){

       // http://web.youli.pw:89/Json/Set_Attention.aspx?sfz=310108198004026642&type=0&name=储明净静

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String urlFollow=MyOkHttpUtils.BaseUrl+"/Json/Set_Attention.aspx?sfz="+personInfo.getSFZ()+"&type=0&name="+personInfo.getNAME();

                        Response response=MyOkHttpUtils.okHttpGet(urlFollow);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String resStr=response.body().string();

                                msg.what=SUCCEED_FOLLOW;
                                msg.obj=resStr;
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

    private void showModifyDialog(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final Intent intent = new Intent();
        builder.setTitle("修改信息提示");
        builder.setMessage("您确定修改此人的信息吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (mRadioGroup.getCheckedRadioButtonId()){

                    case R.id.rb_person_info://基本信息
                        intent.setClass(mContext,PersonBaseInfoActivity.class);
                        intent.putExtra("PINFO",personInfo);
                        startActivity(intent);
                        break;
                    case R.id.rb_family_info://家庭信息

                        break;
                    case R.id.rb_person_resume://个人简历
                        intent.setClass(mContext,PersonReActivity.class);
                        if(personInfo!=null){
                            intent.putExtra("SFZ",personInfo.getSFZ());
                        }
                        startActivity(intent);
                        break;
                    case R.id.rb_service_record://服务记录

                        break;
                    case R.id.rb_edu_info://教育信息
                        intent.setClass(mContext,PersonEduActivity.class);
                        if(personInfo!=null){
                            intent.putExtra("SFZ",personInfo.getSFZ());
                        }

                        startActivity(intent);

                        break;

                }

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

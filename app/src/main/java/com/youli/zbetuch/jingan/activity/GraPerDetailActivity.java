package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.entity.GraQueryInfo;
import com.youli.zbetuch.jingan.entity.GraduateInfo;
import com.youli.zbetuch.jingan.fragment.JobRouteFragment;
import com.youli.zbetuch.jingan.fragment.PersonBaseInfoFragment;
import com.youli.zbetuch.jingan.fragment.PersonInfoFragment;
import com.youli.zbetuch.jingan.fragment.PersonJobIntFragment;

/**
 * 作者: zhengbin on 2017/10/13.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 毕业生个人详情界面
 */

public class GraPerDetailActivity extends FragmentActivity {

    private Context mContext=GraPerDetailActivity.this;
    private FragmentManager fm=this.getSupportFragmentManager();
    private GraduateInfo gInfo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graper_detail);

        gInfo=(GraduateInfo)getIntent().getSerializableExtra("info");

        FragmentTransaction ft=fm.beginTransaction();

       // PersonBaseInfoFragment pif=new PersonBaseInfoFragment(gInfo);
        PersonBaseInfoFragment pif=PersonBaseInfoFragment.newInstance(gInfo);
        ft.add(R.id.fl_graper_detail,pif);

        ft.commit();

    }

    public void onChange(View v){




        switch (v.getId()){

            case R.id.rb_graper_detail_one://个人基本信息

                FragmentTransaction cft=fm.beginTransaction();

                //PersonBaseInfoFragment pif=new PersonBaseInfoFragment(gInfo);

                PersonBaseInfoFragment pif=PersonBaseInfoFragment.newInstance(gInfo);

                cft.replace(R.id.fl_graper_detail,pif);

                cft.commit();

                break;

            case R.id.rb_graper_detail_two://个人求职意愿

                FragmentTransaction pjt=fm.beginTransaction();

                //PersonJobIntFragment pjf=new PersonJobIntFragment(gInfo);

                PersonJobIntFragment pjf=PersonJobIntFragment.newInstance(gInfo);

                pjt.replace(R.id.fl_graper_detail,pjf);

                pjt.commit();

                break;

            case R.id.rb_graper_detail_three://就业工作轨迹

                FragmentTransaction hft=fm.beginTransaction();

               // JobRouteFragment jrf=new JobRouteFragment(gInfo);

                JobRouteFragment jrf=JobRouteFragment.newInstance(gInfo);

                hft.replace(R.id.fl_graper_detail,jrf);

                hft.commit();

                break;

        }

    }

    @Override
    public void onBackPressed() {
        GraQueryInfo queryInfo=null;
        Intent intent=new Intent();
        intent.putExtra("QueryInfo",queryInfo);
        setResult(30000,intent);
        finish();
    }



}

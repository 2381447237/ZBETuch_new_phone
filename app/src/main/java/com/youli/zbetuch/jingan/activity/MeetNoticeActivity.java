package com.youli.zbetuch.jingan.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.fragment.CurrentMeetFragment;
import com.youli.zbetuch.jingan.fragment.HistoryMeetFragment;

/**
 * Created by liutao on 2017/9/1.
 */

public class MeetNoticeActivity extends FragmentActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_meet_notice);

        FragmentManager fm=this.getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        CurrentMeetFragment cmf=new CurrentMeetFragment();

        ft.add(R.id.fl_meet_notice,cmf);

        ft.commit();

    }


    public void onChange(View v){

        FragmentManager fm=this.getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        switch (v.getId()){

            case R.id.rb_meet_notice_current://当前会议

               CurrentMeetFragment cmt=new CurrentMeetFragment();

                ft.replace(R.id.fl_meet_notice,cmt);

                ft.commit();

                break;

            case R.id.rb_meet_notice_history://历史会议

                HistoryMeetFragment hmf=new HistoryMeetFragment();

                ft.replace(R.id.fl_meet_notice,hmf);

                ft.addToBackStack(null);

                ft.commit();

                break;

        }

    }

    @Override
    public void onBackPressed() {
       finish();
    }
}

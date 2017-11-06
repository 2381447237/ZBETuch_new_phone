package com.youli.zbetuch.jingan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.activity.GraPerDetailActivity;
import com.youli.zbetuch.jingan.entity.GraduateInfo;

/**
 * 作者: zhengbin on 2017/10/16.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 从应届毕业生界面进去的就业工作轨迹碎片
 */

public class JobRouteFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener{

    private GraduateInfo gInfo;

//    public JobRouteFragment(GraduateInfo gInfo) {
//        this.gInfo = gInfo;
//    }

    public static final JobRouteFragment newInstance(GraduateInfo gInfo){

        JobRouteFragment fragment = new JobRouteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("gInfo",gInfo);
        fragment.setArguments(bundle);

        return fragment;
    }

    private View contentView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private RadioGroup rg;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gInfo=(GraduateInfo)getArguments().getSerializable("gInfo");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        contentView=LayoutInflater.from(getContext()).inflate(R.layout.fragment_job_route,container,false);

        isViewCreated=true;//标记
        fm=getActivity().getSupportFragmentManager();
        ft=fm.beginTransaction();

        JobRouteOneFragment oneFmt=JobRouteOneFragment.newInstance(gInfo);

        ft.add(R.id.fl_job_route,oneFmt);

        ft.commit();

        rg= (RadioGroup) contentView.findViewById(R.id.rg_job_route);
        rg.setOnCheckedChangeListener(this);

        return contentView;
    }

    @Override
    public void lazyLoadData() {
        if(isViewCreated){
          //逻辑都写这里面

        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId){

            case R.id.rb_job_route_one://个人基本信息

                ft=fm.beginTransaction();

                JobRouteOneFragment oneFmt=JobRouteOneFragment.newInstance(gInfo);

                ft.replace(R.id.fl_job_route,oneFmt);

                ft.commit();

                break;

            case R.id.rb_job_route_two://个人求职意愿

                ft=fm.beginTransaction();

                JobRouteTwoFragment twoFmt=JobRouteTwoFragment.newInstance(gInfo);

                ft.replace(R.id.fl_job_route,twoFmt);

                ft.addToBackStack(null);

                ft.commit();

                break;



        }

    }
}

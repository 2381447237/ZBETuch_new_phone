package com.youli.zbetuch.jingan.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.activity.PersonInfoActivity;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.EduInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by ZHengBin on 2017/8/12.
 */

public class EduInfoFragment extends Fragment{

    private String sfzStr;


    public EduInfoFragment(String sfzStr) {
        this.sfzStr = sfzStr;
    }

    public String getSfzStr() {
        return sfzStr;
    }


    private  View contentView;
    private ListView lv;
    private List<EduInfo> data=new ArrayList<>();
    private CommonAdapter adapter;

    private final int SUCCESS=10000;
    private final int  PROBLEM=10001;
    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS:

                    data.addAll((List<EduInfo>)msg.obj);

                    setLvAdapter(data);

                    break;

                case PROBLEM:

                    Toast.makeText(getActivity(),"网络不给力",Toast.LENGTH_SHORT).show();

                    break;
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView=LayoutInflater.from(getContext()).inflate(R.layout.framgment_education_info,container,false);
        if(getSfzStr()!=null) {
            initView(contentView);
        }
        return contentView;
    }

    private void initView(View view){

        lv= (ListView) view.findViewById(R.id.lv_fmt_education_info);



        initDatas();

   }

    private void initDatas(){
        //http://web.youli.pw:89/Json/Get_Educational_Information.aspx?sfz=310108198004026642
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_Educational_Information.aspx?sfz="+getSfzStr();

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

                adapter=new CommonAdapter<EduInfo>(getActivity(),eInfo,R.layout.item_fmt_education_info_lv) {

            @Override
            public void convert(CommonViewHolder holder, EduInfo item, int position) {

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

                LinearLayout llContent=holder.getView(R.id.ll_item_fmt_education_info_content);
                llContent.setBackgroundColor(Color.parseColor("#b7daf4"));

                RelativeLayout rlContent=holder.getView(R.id.rl_item_fmt_education_info_content);
                rlContent.setVisibility(View.GONE);
            }
        };

        lv.setAdapter(adapter);


    }


}

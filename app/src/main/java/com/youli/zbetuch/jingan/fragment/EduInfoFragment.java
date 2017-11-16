package com.youli.zbetuch.jingan.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.EduInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by ZHengBin on 2017/8/12.
 */

public class EduInfoFragment extends Fragment{

    private String sfzStr;


    public static final EduInfoFragment newInstance(String sfz){

        EduInfoFragment fragment = new EduInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("sfz", sfz);
        fragment.setArguments(bundle);

        return fragment;
    }


    private  View contentView;
    private ListView lv;
    private EduInfo info=null;
    private List<EduInfo> data=new ArrayList<>();
    private CommonAdapter adapter;

    private final int SUCCESS=10000;
    private final int  PROBLEM=10001;
    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS:

                    data.clear();
                    data.addAll((List<EduInfo>)msg.obj);
                    if(getActivity()!=null) {
                        setLvAdapter(data);
                    }
                    break;

                case PROBLEM:

                    if(getActivity()!=null) {
                        Toast.makeText(getActivity(), "网络不给力", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sfzStr=getArguments().getString("sfz");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        contentView=LayoutInflater.from(getContext()).inflate(R.layout.framgment_education_info,container,false);

//        if(getSfzStr()!=null) {
//            initView(contentView);
//        }

        if(sfzStr!=null) {
            initView(contentView);
        }

        EventBus.getDefault().register(this);

        return contentView;
    }


    private void initView(View view){

        lv= (ListView) view.findViewById(R.id.lv_fmt_education_info);

        initDatas(info);

   }

    //这里我们的ThreadMode设置为MAIN，事件的处理会在UI线程中执行
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initDatas(EduInfo info){//注意方法里面一定要传一个对象，否则就eventBus就会报错
        //http://web.youli.pw:89/Json/Get_Educational_Information.aspx?sfz=310108198004026642
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_Educational_Information.aspx?sfz="+sfzStr;

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String resStr=response.body().string();
                             if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")) {
                                 Gson gson = new Gson();

                                 msg.what = SUCCESS;
                                 msg.obj = gson.fromJson(resStr, new TypeToken<List<EduInfo>>() {
                                 }.getType());
                                 mHandler.sendMessage(msg);
                             }
                            } catch (Exception e) {
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }




}

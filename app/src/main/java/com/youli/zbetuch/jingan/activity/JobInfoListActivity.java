package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.JobInfoListInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import com.youli.zbetuch.jingan.view.MyListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Response;

/**
 * Created by liutao on 2017/8/23.
 */

public class JobInfoListActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private Context mContext=this;
    private final int SUCCEED_REFRESH=10001;
    private final int  PROBLEM=10002;

    private MyListView lv;
    private View headerLv;
    private List<JobInfoListInfo> jobInfoList=new ArrayList<>();
    private CommonAdapter commonAdapter;
    private PullToRefreshScrollView hsv;
    private int PageIndex=0;
    private String queryUrl;
    private TextView rowNum;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

             switch (msg.what){

                 case SUCCEED_REFRESH:

                            if(PageIndex==0) {
                                jobInfoList.clear();
                            }

                     jobInfoList.addAll((List<JobInfoListInfo>)msg.obj);
                     rowNum.setText("共有数据"+jobInfoList.get(0).getMax_row()+"条");
                     if(jobInfoList!=null) {
                         lvSetAdapter(jobInfoList);
                     }

                     break;

                 case PROBLEM:

                     Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();

                     break;

             }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_info);


        jobInfoList=(List<JobInfoListInfo>)getIntent().getSerializableExtra("JobInfoList");
        queryUrl=getIntent().getStringExtra("queryUrl");

        initViews();
    }

    private void initViews() {

        rowNum= (TextView) findViewById(R.id.tv_job_info_num);
        if(jobInfoList!=null&&jobInfoList.size()>0) {
            rowNum.setText("共有数据" + jobInfoList.get(0).getMax_row() + "条");
        }else{
            rowNum.setText("沒有数据");
        }
        hsv = (PullToRefreshScrollView) findViewById(R.id.hsv_job_info);
        hsv.setMode(PullToRefreshBase.Mode.BOTH);
        hsv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if(jobInfoList!=null&&jobInfoList.size()>0) {
                    refresh();
                }else{

                    hsv.onRefreshComplete();

                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

                if(jobInfoList!=null&&jobInfoList.size()>0) {
                moreData();}else{
                    hsv.onRefreshComplete();
                }
            }
        });

        lv = (MyListView) findViewById(R.id.lv_job_info);

        headerLv= LayoutInflater.from(this).inflate(R.layout.header_job_info_lv,lv,false);

        lv.addHeaderView(headerLv);
         lv.setOnItemClickListener(this);

        if(jobInfoList!=null) {
            lvSetAdapter(jobInfoList);
        }

    }

    private void lvSetAdapter(List<JobInfoListInfo> list){
if(commonAdapter==null){
    commonAdapter = new CommonAdapter<JobInfoListInfo>(this,list, R.layout.item_job_info_lv) {
        @Override
        public void convert(CommonViewHolder holder, JobInfoListInfo item, int position) {

            TextView comnameTv = holder.getView(R.id.tv_item_job_info_comname);
            comnameTv.setText(item.getComname());
            TextView jobnameTv = holder.getView(R.id.tv_item_job_info_jobname);
            jobnameTv.setText(item.getJobname());
            TextView jobnoTv = holder.getView(R.id.tv_item_job_info_jobno);
            jobnoTv.setText(item.getJobno());
            TextView edunameTv = holder.getView(R.id.tv_item_job_info_eduname);
            edunameTv.setText(item.getEduname());
            TextView ageTv = holder.getView(R.id.tv_item_job_info_age);
            ageTv.setText(item.getStartage() + "-" + item.getEndage());
            TextView numsTv = holder.getView(R.id.tv_item_job_info_recruitnums);
            numsTv.setText(item.getRecruitnums() + " ");
            TextView salaryTv = holder.getView(R.id.tv_item_job_info_salary);
            salaryTv.setText((int)item.getStartsalary() + "-" + (int)item.getEndsalary());
            TextView timeTv = holder.getView(R.id.tv_item_job_info_time);
            if(item.getModifydate()!=null) {
                timeTv.setText(item.getModifydate().split("T")[0]);
            }
            LinearLayout ll = holder.getView(R.id.item_job_info_ll);
            if (position % 2 == 0) {
                ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item1);
            } else {
                ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item2);
            }
        }
    };
    lv.setAdapter(commonAdapter);
}else{
    commonAdapter.notifyDataSetChanged();
}
        hsv.onRefreshComplete();
    }

    //刷新
    private void refresh(){

        PageIndex=0;
        refreshOrMoreData(queryUrl,PageIndex);

}

    //更多
    private void moreData(){


        PageIndex++;
        refreshOrMoreData(queryUrl,PageIndex);
    }

    private void refreshOrMoreData(final String queryUrl, final int pageIndex){

                new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url=queryUrl+pageIndex;

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String resStr=response.body().string();

                                Gson gson=new Gson();

                                msg.obj=gson.fromJson(resStr,new TypeToken<List<JobInfoListInfo>>(){}.getType());
                                msg.what=SUCCEED_REFRESH;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent=new Intent(this,JobInfoDetailActivity.class);
        intent.putExtra("JOBNO",jobInfoList.get(position-1).getJobno());
        startActivity(intent);

    }
}

package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.InterviewInfo;
import com.youli.zbetuch.jingan.entity.PersonReInfo;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/9/28.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 现场面试
 */

public class InterviewActivity extends BaseActivity{

    private final int SUCCESS_INFO=10001;
    private final int FAIL=10002;

    private Context mContext=InterviewActivity.this;
    private List<InterviewInfo> data=new ArrayList<InterviewInfo>();
    private PullToRefreshListView lv;
    private CommonAdapter adapter;

    private int pageIndex;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS_INFO:
                    if(pageIndex==0) {
                        data.clear();
                    }
                    data.addAll((List<InterviewInfo>)msg.obj);
                    if(lv.isRefreshing()){
                        lv.onRefreshComplete();
                    }
                    lvSetAdapter(data);

                    break;

                case FAIL:
                    Toast.makeText(mContext, "网络不给力", Toast.LENGTH_SHORT).show();
                    if(lv.isRefreshing()){
                        lv.onRefreshComplete();
                    }
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);

        initViews();
    }

    private void initViews(){

        lv= (PullToRefreshListView) findViewById(R.id.lv_interview);
        lv.setMode(PullToRefreshBase.Mode.BOTH);

        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>(){

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                //Toast.makeText(mContext,"刷新",Toast.LENGTH_SHORT).show();
                pageIndex=0;
                getDates();
            }


            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //  Toast.makeText(mContext,"加载更多",Toast.LENGTH_SHORT).show();
                pageIndex++;
                getDates();
            }
        });

        getDates();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(mContext,InterviewDetailsActivity.class);
                intent.putExtra("details",data.get(position-1));
                startActivity(intent);

            }
        });

    }


    private void getDates(){

        //http://web.youli.pw:89/Json/GetJobFairMaster.aspx?page=0&rows=15

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        HashMap<String,String> mapData=new HashMap<String,String>();
                        mapData.put("page",""+pageIndex);
                        mapData.put("rows","15");

                        String url= MyOkHttpUtils.BaseUrl+"/Json/GetJobFairMaster.aspx";

                        Response response=MyOkHttpUtils.okHttpPostFormBody(url,mapData);

                        try {
                            String resStr=response.body().string();

                            Message msg=Message.obtain();

                            if(!TextUtils.equals(resStr,"")){

                                Gson gson=new Gson();
                                msg.obj=gson.fromJson(resStr,new TypeToken<List<InterviewInfo>>(){}.getType());
                                msg.what=SUCCESS_INFO;

                            }else{

                                msg.what=FAIL;

                            }

                            mHandler.sendMessage(msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }

        ).start();

    }


    private void lvSetAdapter(List<InterviewInfo> list){

        if(adapter==null){

            adapter=new CommonAdapter<InterviewInfo>(mContext,list,R.layout.item_lv_interview) {
                @Override
                public void convert(CommonViewHolder holder, InterviewInfo item, int position) {

                    TextView tvTitle=holder.getView(R.id.item_lv_interview_title);
                    tvTitle.setText(item.getNAME());
                    TextView tvDate=holder.getView(R.id.item_lv_interview_date);
                    tvDate.setText(MyDateUtils.stringToYMDHMS(item.getJOBFAIRDATA()));
                    TextView tvComNum=holder.getView(R.id.item_lv_interview_company_num);
                    tvComNum.setText(item.getCompany_num());
                    TextView tvPostNum=holder.getView(R.id.item_lv_interview_post_num);
                    tvPostNum.setText(item.getJob_num());
                    TextView tvAddress=holder.getView(R.id.item_lv_interview_address);
                    tvAddress.setText(item.getADDRESS());

                    LinearLayout ll=holder.getView(R.id.item_lv_interview_ll);
                    ll.setBackgroundResource(R.drawable.selector_interview_item);
                }
            };

            lv.setAdapter(adapter);

        }else if(adapter!=null){

            adapter.notifyDataSetChanged();

        }

    }

}

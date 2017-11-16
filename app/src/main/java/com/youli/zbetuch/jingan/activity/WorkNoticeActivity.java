package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import com.youli.zbetuch.jingan.entity.WorkNoticeInfo;
import com.youli.zbetuch.jingan.entity.WorkNoticeReadInfo;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by liutao on 2017/8/30.
 */

public class WorkNoticeActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private final int SUCCEED_LIST=10000;
    private final int SUCCEED_READ=10001;
    private final int SUCCEED_NODATA=10002;
    private final int  PROBLEM=10003;


    private Context mContext=WorkNoticeActivity.this;
   private PullToRefreshListView ptrLv;
    private CommonAdapter adapter;
    private List<WorkNoticeInfo> workData=new ArrayList<>();
    private List<WorkNoticeReadInfo> readNumList=new ArrayList<>();
    private int PageIndex=0;
    private TextView tvReadNum;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCEED_LIST:
                    if(PageIndex==0) {
                        workData.clear();
                    }
                    workData.addAll((List<WorkNoticeInfo>)msg.obj);

                    if(ptrLv.isRefreshing()){
                        ptrLv.onRefreshComplete();
                    }

                    lvSetAdapter(workData);

                    getReadInfo();

                    break;

                case SUCCEED_READ:

                    String totalNum ="0";
                    String weiduNum ="0";
                    String  rate = "0.00";

                    readNumList.clear();
                    readNumList.addAll((List<WorkNoticeReadInfo>)msg.obj);

                    if(readNumList.get(0).getVALUE1()!=null){
                        totalNum=readNumList.get(0).getVALUE1();
                    }

                    if(readNumList.get(0).getVALUE2()!=null){
                        weiduNum=readNumList.get(0).getVALUE2();
                    }

                    if(readNumList.get(0).getRATE()!=null){
                        rate=readNumList.get(0).getRATE();
                    }

                    tvReadNum.setText("本月" + totalNum + "条已读" + weiduNum + "条"
                            + "读取率:" + rate+ "%");

                    break;

                case PROBLEM:

                    Toast.makeText(mContext,"网络不给力", Toast.LENGTH_SHORT).show();
                    if(ptrLv.isRefreshing()){
                        ptrLv.onRefreshComplete();
                    }
                    break;

                case SUCCEED_NODATA:

                    if(ptrLv.isRefreshing()){
                        ptrLv.onRefreshComplete();
                    }
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_notice);

        initViews();
    }

    private void initViews(){

        tvReadNum= (TextView) findViewById(R.id.tv_work_notice_readnum);
        ptrLv= (PullToRefreshListView) findViewById(R.id.lv_work_notice);
        ptrLv.setOnItemClickListener(this);
        ptrLv.setMode(PullToRefreshBase.Mode.BOTH);

        ptrLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>(){

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                //Toast.makeText(mContext,"刷新",Toast.LENGTH_SHORT).show();
                PageIndex=0;
                initData();
            }


            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
              //  Toast.makeText(mContext,"加载更多",Toast.LENGTH_SHORT).show();
                PageIndex++;
                initData();
            }
        });

        initData();

    }

    private void initData(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
//http://web.youli.pw:89/Json/Get_Work_Notice.aspx?page=0&rows=15
                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_Work_Notice.aspx?page="+PageIndex+"&rows=15";

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String dataStr=response.body().string();

                                if(!TextUtils.equals(dataStr,"")&&!TextUtils.equals(dataStr,"[]")) {

                                    Gson gson = new Gson();

                                    msg.obj = gson.fromJson(dataStr, new TypeToken<List<WorkNoticeInfo>>() {
                                    }.getType());
                                    msg.what = SUCCEED_LIST;
                                }else{
                                    msg.what = SUCCEED_NODATA;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else{

                          msg.what=PROBLEM;

                        }

                        mHandler.sendMessage(msg);

                    }
                }

        ).start();

    }

    private void lvSetAdapter(List<WorkNoticeInfo> data){

        if(adapter==null){

            adapter=new CommonAdapter<WorkNoticeInfo>(mContext,data,R.layout.item_work_notice) {

                @Override
                public void convert(CommonViewHolder holder, WorkNoticeInfo item, int position) {

                    TextView noTv=holder.getView(R.id.tv_item_work_notice_number);
                    noTv.setText((position+1)+"");
                    TextView titleTv=holder.getView(R.id.tv_item_work_notice_title);
                    titleTv.setText(item.getTITLE());
                    TextView dateTv=holder.getView(R.id.tv_item_work_notice_date);
                    dateTv.setText(MyDateUtils.stringToYMDHMS(item.getCREATE_DATE()));

                    LinearLayout ll = holder.getView(R.id.item_work_notice_ll);
                    if (position % 2 != 0) {
                        ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item1);
                    } else {
                        ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item2);
                    }

                }
            };

            ptrLv.setAdapter(adapter);

        }else {

            adapter.notifyDataSetChanged();

        }

    }

    //获得工作通知读取数目
    private void getReadInfo(){
       // http://web.youli.pw:89/Json/Get_Work_Notice_Read.aspx

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String urlRead=MyOkHttpUtils.BaseUrl+"/Json/Get_Work_Notice_Read.aspx";

                        Response response=MyOkHttpUtils.okHttpGet(urlRead);

                        Message msg=Message.obtain();

                        if(response!=null){
                            try {
                                String dataStr=response.body().string();
                                if(!TextUtils.equals(dataStr,"")&&!TextUtils.equals(dataStr,"[]")) {
                                    Gson gson = new Gson();

                                    msg.obj = gson.fromJson(dataStr, new TypeToken<List<WorkNoticeReadInfo>>() {
                                    }.getType());
                                    msg.what = SUCCEED_READ;
                                }else {
                                    msg.what = SUCCEED_NODATA;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else{

                            msg.what=PROBLEM;

                        }

                        mHandler.sendMessage(msg);

                    }
                }

        ).start();


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent=new Intent(mContext,WorkNoticeDetailActivity.class);
        intent.putExtra("WORKDATA",workData.get(position-1));
        startActivity(intent);
    }
}

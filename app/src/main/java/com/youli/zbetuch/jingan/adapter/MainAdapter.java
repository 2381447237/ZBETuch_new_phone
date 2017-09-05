package com.youli.zbetuch.jingan.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.activity.JobInfoDetailActivity;
import com.youli.zbetuch.jingan.activity.JobInfoListActivity;
import com.youli.zbetuch.jingan.activity.MainLayoutActivity;
import com.youli.zbetuch.jingan.activity.MeetDetailActivity;
import com.youli.zbetuch.jingan.activity.MeetNoticeActivity;
import com.youli.zbetuch.jingan.activity.RecentNewsActivity;
import com.youli.zbetuch.jingan.activity.WorkNoticeActivity;
import com.youli.zbetuch.jingan.activity.WorkNoticeDetailActivity;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.JobInfoListInfo;
import com.youli.zbetuch.jingan.entity.JobsInfo;
import com.youli.zbetuch.jingan.entity.MainContent;
import com.youli.zbetuch.jingan.entity.MeetNoticeInfo;
import com.youli.zbetuch.jingan.entity.NewsInfo;
import com.youli.zbetuch.jingan.entity.WorkNoticeInfo;
import com.youli.zbetuch.jingan.utils.MyApplication;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import com.youli.zbetuch.jingan.view.MyListView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Response;

/**
 * Created by liutao on 2017/8/2.
 */

public class MainAdapter extends BaseAdapter{
    // http://web.youli.pw:89/Json/GetJobs_Search.aspx?PageRecCnts=15&ZyflId=-1&Age=-1&GZXZId=-1&ZyflChildId=-1&JobName=&IsDisabledPerson=false&IsDirectInterview=false&ComPropertyId=-1&JobNo=&ModifyStartDate=2010-01-01&IsAssurance=false&EndSalary=0&IndustryClassChildId=-1&ComName=&GZBSId=-1&EduID=-1&AreaId3=-1&AreaId2=-1&AreaId1=-1&ModifyEndDate=2030-01-01&StartSalary=0&IndustryClassId=-1&IsNewGraduates=false&PageIndex=0

    private  String  queryUrl= MyOkHttpUtils.BaseUrl+"/Json/GetJobs_Search.aspx?PageRecCnts=15&ZyflId=-1&Age=-1&GZXZId=-1&ZyflChildId=-1&JobName=&IsDisabledPerson=false&IsDirectInterview=false&ComPropertyId=-1&JobNo=&ModifyStartDate=2010-01-01&IsAssurance=false&EndSalary=0&IndustryClassChildId=-1&ComName=&GZBSId=-1&EduID=-1&AreaId3=-1&AreaId2=-1&AreaId1=-1&ModifyEndDate=2030-01-01&StartSalary=0&IndustryClassId=-1&IsNewGraduates=false&PageIndex=";


    private final int SUCCEED_JOBINFO=10000;
    private final int  PROBLEM=10001;

    private List<MainContent> data;
    private MainLayoutActivity context;
    private CommonAdapter commonAdapter;

    private List<JobInfoListInfo> jobInfoList=new ArrayList<>();//岗位信息的数据

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCEED_JOBINFO:

                    jobInfoList.clear();
                    jobInfoList.addAll((List<JobInfoListInfo>)msg.obj);
                  Intent  intent=new Intent(context,JobInfoListActivity.class);
                       intent.putExtra("queryUrl",queryUrl);
                    intent.putExtra("JobInfoList",(Serializable)jobInfoList);
                       context.startActivity(intent);

                break;
                case PROBLEM:

                    Toast.makeText(context,"网络不给力",Toast.LENGTH_SHORT).show();

                    break;
            }

        }
    };

    public MainAdapter(List<MainContent> data, MainLayoutActivity context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }


    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int p, View convertView, ViewGroup parent) {

        ViewHolder vh;

        if(convertView==null){

            vh=new ViewHolder();

            convertView=LayoutInflater.from(context).inflate(R.layout.item_main_layout_gv,parent,false);

            vh.titleTv= (TextView) convertView.findViewById(R.id.item_main_layout_gv_title_tv);
            vh.contentLv= (MyListView) convertView.findViewById(R.id.item_main_layout_gv_content_lv);
            vh.moreTv= (TextView) convertView.findViewById(R.id.item_main_layout_gv_more_tv);
            convertView.setTag(vh);
        }else{

            vh= (ViewHolder) convertView.getTag();

        }


        if(p==0) {
            commonAdapter = new CommonAdapter<MeetNoticeInfo>(context, data.get(p).getMeetInfos(), R.layout.item_main_layout_lv) {
                @Override
                public void convert(CommonViewHolder holder, MeetNoticeInfo item, int position) {
                    TextView title = holder.getView(R.id.item_main_layout_lv_content_tv);
                    title.setText(data.get(p).getMeetInfos().get(position).getTITLE());
                    TextView time = holder.getView(R.id.item_main_layout_lv_time_tv);
                    time.setText(MyDateUtils.stringToYMDHMS(data.get(p).getMeetInfos().get(position).getMEETING_TIME()));
                }

            };

        }else if(p==1){

            commonAdapter=new CommonAdapter<WorkNoticeInfo>(context,data.get(p).getWorkNoticeInfos(),R.layout.item_main_layout_lv) {

                @Override
                public void convert(CommonViewHolder holder, WorkNoticeInfo item, int position) {

                    TextView title = holder.getView(R.id.item_main_layout_lv_content_tv);
                    title.setText(data.get(p).getWorkNoticeInfos().get(position).getTITLE());
                    TextView time = holder.getView(R.id.item_main_layout_lv_time_tv);
                    time.setText(data.get(p).getWorkNoticeInfos().get(position).getCREATE_DATE().split("T")[0]);

                }
            };

        }else if(p==2){

            commonAdapter=new CommonAdapter<JobsInfo>(context,data.get(p).getJobsInfos(),R.layout.item_main_layout_lv) {

                @Override
                public void convert(CommonViewHolder holder, JobsInfo item, int position) {
                    TextView title = holder.getView(R.id.item_main_layout_lv_content_tv);
                    title.setText(data.get(p).getJobsInfos().get(position).getJobname());
                    TextView time = holder.getView(R.id.item_main_layout_lv_time_tv);
                    time.setText(data.get(p).getJobsInfos().get(position).getModifydate().split("T")[0]);
                }
            };

        }else if(p==3){

            commonAdapter=new CommonAdapter<NewsInfo>(context,data.get(p).getNewsInfos(),R.layout.item_main_layout_lv) {

                @Override
                public void convert(CommonViewHolder holder, NewsInfo item, int position) {
                    TextView title = holder.getView(R.id.item_main_layout_lv_content_tv);
                    title.setText(data.get(p).getNewsInfos().get(position).getTITLE());
                    TextView time = holder.getView(R.id.item_main_layout_lv_time_tv);
                    time.setText(data.get(p).getNewsInfos().get(position).getCREATE_TIME().split("T")[0]);
                }
            };

        }

       vh.contentLv.setAdapter(commonAdapter);
       vh.contentLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

           Intent intent=null;

           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               switch (p){

                   case 0://会议通知
                       intent=new Intent(context,MeetDetailActivity.class);
                       intent.putExtra("MEETINFO",data.get(p).getMeetInfos().get(position));
                       context.startActivity(intent);
                       break;
                   case 1://工作通知
                       intent=new Intent(context,WorkNoticeDetailActivity.class);
                       intent.putExtra("WORKDATA",data.get(p).getWorkNoticeInfos().get(position));
                       context.startActivity(intent);
                      // Toast.makeText(context,data.get(p).getWorkNoticeInfos().get(position).getTITLE(),Toast.LENGTH_SHORT).show();
                       break;
                   case 2://岗位信息
                       intent=new Intent(context,JobInfoDetailActivity.class);
                       intent.putExtra("JOBNO",data.get(p).getJobsInfos().get(position).getJobno());
                       context.startActivity(intent);
                       break;
                   case 3://近期热点
                       showDetailDialog(data.get(p).getNewsInfos().get(position));

                       break;
               }

           }
       });
        vh.titleTv.setText(data.get(p).getTitle());
        //更多按钮
        vh.moreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;

                switch (p){
                    case 0://会议通知
                        intent=new Intent(context, MeetNoticeActivity.class);
                        context.startActivity(intent);
                       break;
                   case 1://工作通知
                       intent=new Intent(context, WorkNoticeActivity.class);
                       context.startActivity(intent);
                       break;
                   case 2://岗位信息

                           getJobData(queryUrl);

                       break;
                   case 3://近期热点
                     intent=new Intent(context, RecentNewsActivity.class);
                       context.startActivity(intent);
                       break;


                }

            }
        });
        return convertView;
    }

    class ViewHolder{


        TextView titleTv;
        MyListView contentLv;
        TextView moreTv;
    }

    private void showDetailDialog(NewsInfo list){

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_recentnews_detail,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        lp.height = (int) (d.getHeight() * 0.7);   //高度设置为屏幕的0.7
        //  lp.width = (int) (d.getWidth() * 0.5);    //宽度设置为屏幕的0.5
        dialog.getWindow().setAttributes(lp);     //设置生效

        dialog.setCanceledOnTouchOutside(false);

        TextView nameTv= (TextView) view.findViewById(R.id.tv_dialog_recentnews_name);
        TextView contentTv= (TextView) view.findViewById(R.id.tv_dialog_recentnews_content);
        TextView dateTv= (TextView) view.findViewById(R.id.tv_dialog_recentnews_date);
        TextView hourTv= (TextView) view.findViewById(R.id.tv_dialog_recentnews_hour);
        nameTv.setText(list.getTITLE());
        contentTv.setText("\t\t\t\t"+list.getDOC());
        dateTv.setText(list.getCREATE_TIME().split("T")[0]);
        hourTv.setText(MyDateUtils.stringToHMS(list.getCREATE_TIME().split("T")[1]));
    }


    private void getJobData(final String url){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                       String  urlStr=url+"0";

                        Response response=MyOkHttpUtils.okHttpGet(urlStr);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String dataStr=response.body().string();
                                Gson gson=new Gson();
                                msg.what=SUCCEED_JOBINFO;
                                msg.obj=gson.fromJson(dataStr,new TypeToken<List<JobInfoListInfo>>(){}.getType());

                            } catch (IOException e) {
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
}

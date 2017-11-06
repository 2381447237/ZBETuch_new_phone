package com.youli.zbetuch.jingan.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.NewsInfo;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by liutao on 2017/8/29.
 */

public class RecentNewsActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private final int SUCCEED=10000;
    private final int SUCCEED_NODATA=10001;
    private final int  PROBLEM=10002;
    private Context mContext=this;

    private List<NewsInfo> newsList=new ArrayList<>();
    private ListView lv;
    private CommonAdapter adapter;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCEED:

                    newsList.clear();
                    newsList.addAll((List<NewsInfo>)msg.obj);
                    setLvAdapter(newsList);

                    break;
                case PROBLEM:

                    Toast.makeText(mContext,"网络不给力", Toast.LENGTH_SHORT).show();

                    break;

                case SUCCEED_NODATA:
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_news);

        initViews();

    }

    private void initViews(){

        lv= (ListView) findViewById(R.id.lv_recent_news);
         lv.setOnItemClickListener(this);

        initData();

    }

    private void initData(){

           new Thread(

                   new Runnable() {
                       @Override
                       public void run() {


                          // http://web.youli.pw:89/Json/Get_News.aspx?page=0&rows=15

                           String url= MyOkHttpUtils.BaseUrl+"/Json/Get_News.aspx?page=0&rows=15";
                           Response response=MyOkHttpUtils.okHttpGet(url);

                           Message msg=Message.obtain();

                           if(response!=null){

                               try {
                                   String resStr=response.body().string();

                                   if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")) {

                                       Gson gson = new Gson();

                                       msg.obj = gson.fromJson(resStr, new TypeToken<List<NewsInfo>>() {
                                       }.getType());
                                       msg.what = SUCCEED;

                                   }else{
                                       msg.what = SUCCEED_NODATA;
                                   }
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


    private void setLvAdapter(List<NewsInfo> data){

        if(adapter==null){

            adapter=new CommonAdapter<NewsInfo>(mContext,data,R.layout.item_recent_news) {

                @Override
                public void convert(CommonViewHolder holder, NewsInfo item, int position) {

                    TextView titleTv=holder.getView(R.id.tv_item_recent_news_title);
                    titleTv.setText(item.getTITLE());
                    TextView dateTv=holder.getView(R.id.tv_item_recent_news_date);
                    dateTv.setText(MyDateUtils.stringToYMDHMS(item.getCREATE_TIME()));

                }
            };
          lv.setAdapter(adapter);
        }else{

            adapter.notifyDataSetChanged();

        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        showDetailDialog(position);

    }

    private void showDetailDialog(int p){

        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        View view= LayoutInflater.from(mContext).inflate(R.layout.dialog_recentnews_detail,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();
        WindowManager m = getWindowManager();
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
        nameTv.setText(newsList.get(p).getTITLE());
        contentTv.setText("\t\t\t\t"+newsList.get(p).getDOC());
        dateTv.setText(newsList.get(p).getCREATE_TIME().split("T")[0]);
        hourTv.setText(MyDateUtils.stringToHMS(newsList.get(p).getCREATE_TIME().split("T")[1]));
    }

}

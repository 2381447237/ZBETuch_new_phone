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
import com.youli.zbetuch.jingan.entity.NaireListInfo;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/10/22.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 问卷调查
 */

public class QuestionNaireActivity extends BaseActivity{

    private final  int SUCCESS_NAIRE=10001;
    private final int FAIL=10002;
    private final int NODATA=10003;

    private Context mContext=this;
    private PullToRefreshListView lv;
    private CommonAdapter adapter;
    private List<NaireListInfo> data=new ArrayList<>();
    private TextView tvNum;
    private int pageIndex;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS_NAIRE:
                    if(pageIndex==0) {
                        data.clear();
                    }
                    data.addAll((List<NaireListInfo>)msg.obj);
                    LvSetAdapter(data);
                    tvNum.setText("共有"+data.get(0).getRecordCount()+"篇");
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }
                    break;
                case FAIL:
                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }
                    break;
                case NODATA:
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }
                    if(data.size()>0) {
                        if (data.get(0) != null) {
                            tvNum.setText("共有" + data.get(0).getRecordCount() + "篇");
                        } else {
                            tvNum.setText("共有0篇");
                        }
                    }else{
                        tvNum.setText("共有0篇");
                    }
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_naire);

        initViews();
    }

    private void initViews(){

        tvNum= (TextView) findViewById(R.id.tv_question_naire_num);
        lv= (PullToRefreshListView) findViewById(R.id.lv_question_naire);
        lv.setMode(PullToRefreshBase.Mode.BOTH);

        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex=0;
                initDates();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                pageIndex++;
                initDates();

            }
        });
       initDates();

      lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              Intent intent=new Intent(mContext,NairePersonActivity.class);
              intent.putExtra("NaireInfo",data.get(position-1));

              startActivity(intent);
          }
      });

    }

    private void initDates(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                       // http://web.youli.pw:89/Json/Get_Qa_Master.aspx?page=0&rows=15
                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_Qa_Master.aspx?page="+pageIndex+"&rows=15";

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null){

                            if(response.body()!=null){


                                try {
                                    String resStr=response.body().string();

                                    if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")){
                                        Gson gson=new Gson();

                                        msg.obj=gson.fromJson(resStr,new TypeToken<List<NaireListInfo>>(){}.getType());

                                        msg.what=SUCCESS_NAIRE;

                                    }else{
                                        msg.what=NODATA;
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }else{

                               msg.what=FAIL;

                            }

                            mHandler.sendMessage(msg);

                        }
                    }
                }

        ).start();

    }

    private void LvSetAdapter(List<NaireListInfo> data){

        if(adapter==null){

            adapter=new CommonAdapter<NaireListInfo>(mContext,data,R.layout.item_question_naire) {
                @Override
                public void convert(CommonViewHolder holder, NaireListInfo item, int position) {

                    TextView tvNo=holder.getView(R.id.tv_item_question_naire_no);
                    tvNo.setText((position+1)+"");
                    TextView tvTitle=holder.getView(R.id.tv_item_question_naire_title);
                    tvTitle.setText(item.getTITLE());
                    TextView tvNaireNo=holder.getView(R.id.tv_item_question_naire_naire_no);
                    tvNaireNo.setText(item.getNO());
                    TextView tvDate=holder.getView(R.id.tv_item_question_naireo_date);
                    tvDate.setText(MyDateUtils.stringToYMD(item.getCREATE_TIME()));

                    LinearLayout ll=holder.getView(R.id.ll_item_question_naire);
                    if (position % 2 == 0) {
                        ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item1);
                    } else {
                        ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item2);
                    }
                }
            };

            lv.setAdapter(adapter);

        }else{

            adapter.notifyDataSetChanged();

        }

    }

}

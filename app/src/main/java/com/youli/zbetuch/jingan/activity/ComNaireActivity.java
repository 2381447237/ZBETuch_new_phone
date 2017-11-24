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
import com.youli.zbetuch.jingan.entity.ComNaireInfo;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/11/20.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 企业调查
 */

public class ComNaireActivity extends BaseActivity{


    private Context mContext=this;

    private final int SUCCESS=10000;
    private final int FAIL=10001;
    private final int SUCCESS_NODATA=10002;

    private List<ComNaireInfo> data=new ArrayList<ComNaireInfo>();
    private PullToRefreshListView lv;
    private CommonAdapter adapter;

    private TextView tvNum;

    private int pageIndex;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS:

                    if(pageIndex==0){
                        data.clear();
                    }

                    data.addAll((List<ComNaireInfo>)msg.obj);

                    LvSetAdapter(data);

                    if(data.size()>0) {
                        tvNum.setText("共有" + data.get(0).getRecordCount() + "篇");
                    }
                    break;

                case FAIL:
                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();
                    if(lv.isRefreshing()){
                        lv.onRefreshComplete();
                    }
                    break;

                case SUCCESS_NODATA:
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
        setContentView(R.layout.activity_com_naire);

        initViews();
    }

    private void initViews(){
        tvNum= (TextView) findViewById(R.id.tv_question_naire_num);
        lv= (PullToRefreshListView) findViewById(R.id.lv_com_naire);
        lv.setMode(PullToRefreshBase.Mode.BOTH);

        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>(){

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                //Toast.makeText(mContext,"刷新",Toast.LENGTH_SHORT).show();
                pageIndex=0;
                getData();
            }


            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //  Toast.makeText(mContext,"加载更多",Toast.LENGTH_SHORT).show();
                pageIndex++;
                getData();
            }
        });

       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               if(data.get(position-1).getDetils().size()>0) {
                   Intent i = new Intent(mContext, ComListActivity.class);
                   i.putExtra("NaireInfo", data.get(position-1));
                   startActivity(i);
               }

           }
       });


        getData();


    }

    private void getData(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                //http://web.youli.pw:89/Json/Get_Qa_Company_Master.aspx?page=0&rows=15
                String url= MyOkHttpUtils.BaseUrl+"/Json/Get_Qa_Company_Master.aspx?page="+pageIndex+"&rows=15";

                Response response=MyOkHttpUtils.okHttpGet(url);

                Message msg=Message.obtain();

                if(response!=null){

                    if(response.body()!=null){

                        try {
                            String resStr=response.body().string();

                            if(!TextUtils.equals(resStr,"[]")){

                                Gson gson=new Gson();
                                msg.obj = gson.fromJson(resStr, new TypeToken<List<ComNaireInfo>>() {}.getType());
                                msg.what=SUCCESS;
                            }else{
                               msg.what=SUCCESS_NODATA;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }else{
                        msg.what=FAIL;
                    }

                }else{

                    msg.what=FAIL;

                }

                mHandler.sendMessage(msg);

            }
        }).start();

    }

    private void LvSetAdapter(List<ComNaireInfo> list){

        if(adapter==null){

            adapter=new CommonAdapter<ComNaireInfo>(mContext,list,R.layout.item_com_naire) {
                @Override
                public void convert(CommonViewHolder holder, ComNaireInfo item, int position) {

                    TextView tvId=holder.getView(R.id.tv_item_com_naire_no);
                     tvId.setText((position+1)+"");
                    TextView tvTitle=holder.getView(R.id.tv_item_com_naire_title);
                    tvTitle.setText(item.getTITLE());
                    TextView tvNo=holder.getView(R.id.tv_item_com_naire_naire_no);
                    tvNo.setText(item.getNO());
                    TextView tvTime=holder.getView(R.id.tv_item_com_naire_date);
                    tvTime.setText(MyDateUtils.stringToYMD(item.getCREATE_TIME()));

                    LinearLayout ll=holder.getView(R.id.ll_item_com_naire);

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
        if(lv.isRefreshing()){
            lv.onRefreshComplete();
        }
    }

}

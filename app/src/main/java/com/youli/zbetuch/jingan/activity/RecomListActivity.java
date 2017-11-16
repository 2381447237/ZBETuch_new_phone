package com.youli.zbetuch.jingan.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.youli.zbetuch.jingan.entity.PersonInfo;
import com.youli.zbetuch.jingan.entity.RecomListInfo;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/10/11.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 推荐列表
 */

public class RecomListActivity extends BaseActivity{

    private final int SUCCESS_LIST=10001;
    private final int  PERSONINFO=10002;
    private final int  NOPERSONINFO=10003;
    private final int SUCCESS_NODATA=10004;
    private final int FAIL=10005;
    private Context mContext=RecomListActivity.this;
    private PullToRefreshListView lv;
    private List<RecomListInfo> data=new ArrayList<>();
    private CommonAdapter adapter;
    private String masterId;
    private String code;
    private int pageIndex;
    private List<PersonInfo> personInfos=new ArrayList<>();

    private Button btnTongji;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS_LIST:
                   // if(pageIndex==0) {
                        data.clear();
                 //   }
                    data.addAll((List<RecomListInfo>)msg.obj);
                    lvSetAdapter(data);
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }
                    break;

                case FAIL:

                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }

                case NOPERSONINFO:

                    Toast.makeText(mContext,"对不起,查无此人",Toast.LENGTH_SHORT).show();

                    break;

                case PERSONINFO:

                    personInfos=(List<PersonInfo>)msg.obj;
                    if(personInfos!=null) {
                        Intent intent = new Intent(mContext, PersonInfoActivity.class);
                        intent.putExtra("personInfos", (Serializable) personInfos.get(0));
                        startActivity(intent);
                    }else{
                        Toast.makeText(mContext,"对不起,查无此人",Toast.LENGTH_SHORT).show();
                    }

                    break;
                case SUCCESS_NODATA:
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recom_list);
        masterId=getIntent().getStringExtra("master_id");
        code=getIntent().getStringExtra("JOBNO");
        initViews();
    }

    private void initViews(){

        lv= (PullToRefreshListView) findViewById(R.id.lv_recom_list);
        lv.setMode(PullToRefreshBase.Mode.BOTH);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Intent intent=new Intent(mContext,PersonInfoActivity.class);
//                startActivity(intent);

                getPersonInfo(data.get(position-1).getSFZ());

            }
        });

        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex=0;
                getRecomList();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                pageIndex++;
                getRecomList();

            }
        });

        getRecomList();

        btnTongji= (Button) findViewById(R.id.btn_recom_list_meet_tongji);
        btnTongji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showRecruitInfo();

            }
        });

    }

    //获取推荐列表
    private void getRecomList(){
       // http://web.youli.pw:89/Json/Get_JobFairRecommend.aspx?rows=15&master_id=1&page=0&code=158286114
       new Thread(

               new Runnable() {
                   @Override
                   public void run() {

                       String url= MyOkHttpUtils.BaseUrl+"/Json/Get_JobFairRecommend.aspx?rows=15&master_id="+masterId+"&page="+pageIndex+"&code="+code;

                       Response response=MyOkHttpUtils.okHttpGet(url);

                       try {
                           String resStr=response.body().string();

                           Message msg=Message.obtain();

                           if(!TextUtils.equals("",resStr)&&!TextUtils.equals("[]",resStr)){

                               Gson gson=new Gson();

                               msg.obj=gson.fromJson(resStr,new TypeToken<List<RecomListInfo>>(){}.getType());

                               msg.what=SUCCESS_LIST;

                           }else{

                               msg.what=SUCCESS_NODATA;

                           }

                           mHandler.sendMessage(msg);

                       } catch (Exception e) {
                           e.printStackTrace();
                       }

                   }
               }

       ).start();

    }

    private void lvSetAdapter(List<RecomListInfo> data){

        if(adapter==null){

            adapter=new CommonAdapter<RecomListInfo>(mContext,data,R.layout.item_recom_list) {
                @Override
                public void convert(CommonViewHolder holder, RecomListInfo item, int position) {

                    TextView tvNo=holder.getView(R.id.tv_item_recom_list_no);
                    tvNo.setText((position+1)+"");
                    TextView tvJobNo=holder.getView(R.id.tv_item_recom_list_jobno);
                    tvJobNo.setText(item.getJOB_CODE());
                    TextView tvMeetNo=holder.getView(R.id.tv_item_recom_list_meetno);
                    tvMeetNo.setText(item.getMASTER_ID()+"");
                    TextView tvSfz=holder.getView(R.id.tv_item_recom_list_sfz);
                    tvSfz.setText(item.getSFZ());
                    TextView tvDate=holder.getView(R.id.tv_item_recom_list_date);
                    tvDate.setText(MyDateUtils.stringToYMDHMS(item.getCREATE_DATE()));
                    LinearLayout ll = holder.getView(R.id.item_recom_list_ll);
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


    private void getPersonInfo(final String sfz){
        // http://web.youli.pw:89/Json/Get_BASIC_INFORMATION.aspx?sfz=310101198711030515
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url=MyOkHttpUtils.BaseUrl+"/Json/Get_BASIC_INFORMATION.aspx?sfz="+sfz;

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        if(response!=null){

                            try {
                                String strRes=response.body().string();

                                Message msg=Message.obtain();
                                if(TextUtils.equals(strRes,"[null]")){


                                    msg.what=NOPERSONINFO;
                                    mHandler.sendMessage(msg);

                                }else{

                                    Gson gson=new Gson();
                                    msg.obj=gson.fromJson(strRes,new TypeToken<List<PersonInfo>>(){}.getType());
                                    msg.what=PERSONINFO;
                                    mHandler.sendMessage(msg);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }

        ).start();

    }


    //招聘会统计
    private void showRecruitInfo(){
     //http://web.youli.pw:89/Chart/JobFairQuery.aspx?staff=2
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(mContext).inflate(R.layout.dialog_recruit_info,null,false);
        builder.setView(view);
        final AlertDialog dialog=builder.create();

        WebView wv= (WebView) view.findViewById(R.id.wv_recuritment);
        loadUrl(wv);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

    }

    private void loadUrl(WebView wv) {
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAllowFileAccess(true);
        wv.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);

        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        // wvReport.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        // 加载数据
//        wv.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress == 100) {
//                    if (progressDialog != null && progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                        progressDialog = null;
//                    }
//                }
//            }
//        });
        wv.loadUrl(MyOkHttpUtils.BaseUrl+ "/Chart/JobFairQuery.aspx?staff="
                +MainLayoutActivity.adminId);

    }

}

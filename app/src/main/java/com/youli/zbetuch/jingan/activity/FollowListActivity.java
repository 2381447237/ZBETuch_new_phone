package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
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
import com.youli.zbetuch.jingan.entity.FollowListInfo;
import com.youli.zbetuch.jingan.entity.PersonInfo;
import com.youli.zbetuch.jingan.entity.RecomListInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/9/28.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 关注列表
 *
 */

public class FollowListActivity extends BaseActivity{

    private final int SUCCESS_LIST=10001;
    private final int  PERSONINFO=10002;
    private final int  NOPERSONINFO=10003;
    private final int CANCEL_FOLLOW=10004;
    private final int SUCCESS_NODATA=10005;
    private final int FAIL=10006;

    private Context mContext=FollowListActivity.this;

    private PullToRefreshListView lv;
    private List<FollowListInfo> data=new ArrayList<>();
    private CommonAdapter adapter;
    private int pageIndex;
    private List<PersonInfo> personInfos=new ArrayList<>();

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case SUCCESS_LIST:

                 if(pageIndex==0) {
                    data.clear();
                }
                    data.addAll((List<FollowListInfo>)msg.obj);
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
                    break;

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

                case CANCEL_FOLLOW:
                    if (((String)msg.obj).equalsIgnoreCase("true")) {
                        Toast.makeText(mContext, "删除成功！", Toast.LENGTH_SHORT).show();
                        pageIndex=0;
                        getListDates();
                    } else {
                        Toast.makeText(mContext, "删除失败！", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_follow_list);

        initViews();

    }

    private void initViews(){

        lv= (PullToRefreshListView) findViewById(R.id.lv_follow_list);
        lv.setMode(PullToRefreshBase.Mode.BOTH);

        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex=0;
                getListDates();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                pageIndex++;
                getListDates();

            }
        });
        getListDates();


    }


    //http://web.youli.pw:89/Json/Get_Attention.aspx?page=0&rows=15
    private void getListDates(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_Attention.aspx?page="+pageIndex+"&rows=15";

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        try {
                            String resStr=response.body().string();

                            Message msg=Message.obtain();

                            if(!TextUtils.equals("",resStr)&&!TextUtils.equals("[]",resStr)){

                                Gson gson=new Gson();

                                msg.obj=gson.fromJson(resStr,new TypeToken<List<FollowListInfo>>(){}.getType());

                                msg.what=SUCCESS_LIST;

                            }else{

                                msg.what=SUCCESS_NODATA;

                            }

                            mHandler.sendMessage(msg);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

    }


    private void lvSetAdapter(List<FollowListInfo> list){

        if(adapter==null){

            adapter=new CommonAdapter<FollowListInfo>(mContext,list,R.layout.item_follow_list) {
                @Override
                public void convert(CommonViewHolder holder, final FollowListInfo item, final int position) {

                    TextView tvName=holder.getView(R.id.tv_item_follow_list_name);
                    tvName.setText(item.getNAME());
                    TextView tvIdCard=holder.getView(R.id.tv_item_follow_list_idcard);
                    tvIdCard.setText(item.getSFZ());

                    LinearLayout ll=holder.getView(R.id.ll_item_follow_list);

                    if(position%2==0){
                        ll.setBackgroundColor(Color.parseColor("#C9E2FA"));
                    }else{
                        ll.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }

                    Button btnDetails=holder.getView(R.id.btn_item_follow_list_details);
                    btnDetails.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            getPersonInfo(item.getSFZ());

                        }
                    });

                    Button btnFollow=holder.getView(R.id.btn_item_follow_list_follow);
                    btnFollow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            cancelFollow(item.getNAME(),item.getSFZ());


                        }
                    });

                }
            };

            lv.setAdapter(adapter);

        }else if(adapter!=null){

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

    //取消关注
    //http://web.youli.pw:89/Json/Set_Attention.aspx
  //  {name=陶林峰, type=1, sfz=310108198607242635}
   private void cancelFollow(final String name, final String sfz){

       new Thread(

               new Runnable() {
                   @Override
                   public void run() {

                       String url=MyOkHttpUtils.BaseUrl+"/Json/Set_Attention.aspx";

                       Map<String,String> mapData=new HashMap<String, String>();
                       mapData.put("name",name);
                       mapData.put("type","1");
                       mapData.put("sfz",sfz);

                       Response response=MyOkHttpUtils.okHttpPostFormBody(url, (HashMap<String, String>) mapData);

                       try {
                           String resStr=response.body().string();

                           Message msg=Message.obtain();
                           msg.obj=resStr;
                           msg.what=CANCEL_FOLLOW;

                           mHandler.sendMessage(msg);

                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                   }
               }

       ).start();



   }
}

package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.ShiyeTongjiBigAdapter;
import com.youli.zbetuch.jingan.entity.ShiyeTongjiInfo;
import com.youli.zbetuch.jingan.entity.TongjiInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/10/12.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 失业统计
 */

public class ShiyeTongjiActivity extends BaseActivity{

    private final int SUCCESS_BIG_LIST=10001;
    private final int FAIL=10002;

    private Context mContext=ShiyeTongjiActivity.this;
   // private ListView bigLv;
    private List<TongjiInfo> tongjiData=new ArrayList<>();//后台返回的数据
    private List<ShiyeTongjiInfo> bigData=new ArrayList<>();//父集合
    private List<ShiyeTongjiInfo.ShiyeTongjiChildInfo> chlidData=new ArrayList<>();//子集合

    // 组数据和子数据的对应表
    Map<String, Integer> itemMap= new HashMap<String, Integer>();

    private ShiyeTongjiBigAdapter adapter;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS_BIG_LIST:

                    tongjiData.clear();
                    tongjiData.addAll((List<TongjiInfo>)msg.obj);

                    fretchGroupItems(tongjiData);
                    fretchSubItems(tongjiData);
                    lvSetAdapter(bigData);

                    break;

                case FAIL:
                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shiye_tongji);

        initViews();

    }

    private void initViews(){

      //  bigLv= (ListView) findViewById(R.id.lv_shiye_tongji_big);
        getDates();


    }

    //获取网络数据
    //http://web.youli.pw:89/Json/Get_Resources_Query.aspx
    private void getDates(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_Resources_Query.aspx";

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        try {
                            String resStr=response.body().string();

                            Message msg=Message.obtain();

                            if(!TextUtils.equals("",resStr)){

                                Gson gson=new Gson();

                                msg.obj=gson.fromJson(resStr,new TypeToken<List<TongjiInfo>>(){}.getType());

                                msg.what=SUCCESS_BIG_LIST;

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

    private void lvSetAdapter(List<ShiyeTongjiInfo> data){

        if(adapter==null) {

            adapter = new ShiyeTongjiBigAdapter(data, mContext, bigLv);
            bigLv.setAdapter(adapter);
        }else{

            adapter.notifyDataSetChanged();
        }
    }

    //填充父集合
    private void fretchGroupItems(List<TongjiInfo> data){


        if(data!=null &&data.size()>0){

            int index=0;

            for(int i=0;i<data.size();i++){

                if(!data.get(i).getOrder_id().substring(0,1).equals("4")){

                    bigData.add(new ShiyeTongjiInfo(data.get(i),false));

                    itemMap.put(data.get(i).getCommittee_id()+data.get(i).getType(),index);

                    index++;
                }

            }

        }


    }

    //填充子集合
    private void fretchSubItems(List<TongjiInfo> data){

        if(data!=null &&data.size()>0){

            for(int i=0;i<itemMap.size();i++){



            }

        }

    }

}

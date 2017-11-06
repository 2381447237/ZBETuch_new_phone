package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.ShiyeExpandAdapter;
import com.youli.zbetuch.jingan.entity.ShiyeTongjiInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import com.youli.zbetuch.jingan.utils.ProgressDialogUtils;

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

public class ShiyeTongjiActivity extends BaseActivity implements ExpandableListView.OnChildClickListener{

    private final int SUCCESS_BIG_LIST=10001;
    private final int SUCCESS_NODATA=10002;
    private final int FAIL=10003;

    private Context mContext=ShiyeTongjiActivity.this;
    private ExpandableListView elv;
    // 源数据
    private List<ShiyeTongjiInfo> items=new ArrayList<>();
    // 组数据
    private List<ShiyeTongjiInfo> groupItems=new ArrayList<>();
    // 子数据
    private List<List<ShiyeTongjiInfo>> subItems=new ArrayList<>();
    // 组数据和子数据的对应表
   private Map<String, Integer> itemMap= new HashMap<String, Integer>();

    private ShiyeExpandAdapter adapter;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            ProgressDialogUtils.dismissMyProgressDialog(mContext);
            switch (msg.what){

                case SUCCESS_BIG_LIST:

                    items.clear();
                    items.addAll((List<ShiyeTongjiInfo>)msg.obj);

                    fretchGroupItems(items);
                    fretchSubItems(items);

                    adapter = new ShiyeExpandAdapter(groupItems,mContext,subItems);
                    elv.setAdapter(adapter);
                    break;

                case FAIL:
                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();
                    break;

                case SUCCESS_NODATA:
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

        elv= (ExpandableListView) findViewById(R.id.elv_shiye_tongji_big);
        elv.setOnChildClickListener(this);
        getDates();


    }

    //获取网络数据
    //http://web.youli.pw:89/Json/Get_Resources_Query.aspx
    private void getDates(){
        ProgressDialogUtils.showMyProgressDialog(this);
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_Resources_Query.aspx";

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        try {
                            String resStr=response.body().string();

                            Message msg=Message.obtain();

                            if(!TextUtils.equals("",resStr)&&!TextUtils.equals("[]",resStr)){

                                Gson gson=new Gson();

                                msg.obj=gson.fromJson(resStr,new TypeToken<List<ShiyeTongjiInfo>>(){}.getType());

                                msg.what=SUCCESS_BIG_LIST;

                            }else{

                                msg.what=SUCCESS_NODATA;

                            }

                            mHandler.sendMessage(msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

        ).start();

    }



    // 填充组数据
    private void fretchGroupItems(List<ShiyeTongjiInfo> items) {

        if (items != null && items.size() > 0) {
            int index = 0;
            for (ShiyeTongjiInfo item : items) {
                String orderidStr = "" + item.getOrder_id();
                if (!orderidStr.trim().substring(0, 1).equals("4")) {
                    groupItems.add(item);
                    itemMap.put(item.getCommittee_id() + item.getType().trim(), index);
                    index++;
                }
            }
        }

    }
    // 填充子数据
    private void fretchSubItems(List<ShiyeTongjiInfo> items) {
        if (items != null && items.size() > 0) {
            for (int i = 0; i < itemMap.size(); i++) {
                subItems.add(new ArrayList<ShiyeTongjiInfo>());
            }

            for (ShiyeTongjiInfo item : items) {
                String orderidStr = "" + item.getOrder_id();
                if (orderidStr.trim().substring(0, 1).equals("4")) {
                    subItems.get(
                            itemMap.get(orderidStr.trim().substring(1)
                                    + item.getType().trim())).add(item);
                }
            }
        }
    }


    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

      // Toast.makeText(mContext,"子条目="+subItems.get(groupPosition).get(childPosition),Toast.LENGTH_SHORT).show();
        String url_suffix="&order_id="+ subItems.get(groupPosition).get(childPosition).getOrder_id()
                +"&COMMITTEE_ID=" + subItems.get(groupPosition).get(childPosition).getCommittee_id()
                         + "&TYPE=" + subItems.get(groupPosition).get(childPosition).getType()+"&Resources=true";
        Intent intent=new Intent(mContext,PersonalInfoQueryResult.class);
        intent.putExtra("queryUrl", url_suffix);
        startActivity(intent);

        return false;
    }
}

package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.youli.zbetuch.jingan.entity.NairePersonInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
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
 * 问卷被调查人
 */

public class NairePersonActivity extends BaseActivity{

    private  final int SUCCESS_LIST=10001;
    private  final int SUCCESS_NODATA=10002;
    private  final int FAIL=10003;

    private Context mContext=this;
    private PullToRefreshListView lv;
    private TextView tvNum,tvFamilyNum;
    private List<NairePersonInfo> data=new ArrayList<>();
    private CommonAdapter adapter;
    private int pageIndex;
    private int type=1;//1:表示未查，2:表示已查
    private NaireListInfo naireInfo;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS_LIST:
                    if(pageIndex==0) {
                        data.clear();
                    }
                    data.addAll((List<NairePersonInfo>)msg.obj);
                    LvSetAdapter(data);
                    tvNum.setText("共有"+data.get(0).getRecordCount()+"人");
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }
                    break;
                case SUCCESS_NODATA:
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }
                    if(data.size()>0) {
                        if (data.get(0) != null) {
                            tvNum.setText("共有" + data.get(0).getRecordCount() + "人");
                        } else {
                            tvNum.setText("共有0人");
                        }
                    }else{
                        tvNum.setText("共有0人");
                }
                    break;
                case FAIL:
                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_naire_person);

        naireInfo=(NaireListInfo)getIntent().getSerializableExtra("NaireInfo");

        Log.e("2017/10/23","问卷信息="+naireInfo);

        initViews();
    }

    private void initViews(){

        tvNum= (TextView) findViewById(R.id.tv_naire_person_num);
        tvFamilyNum= (TextView) findViewById(R.id.tv_naire_person_family_num);

        if(naireInfo.isISJYSTATUS()){
            tvFamilyNum.setVisibility(View.VISIBLE);
        }else{
            tvFamilyNum.setVisibility(View.GONE);
        }

        lv= (PullToRefreshListView) findViewById(R.id.lv_naire_person);
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
    }

    private void initDates(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                       // http://web.youli.pw:89/Json/Get_Qa_UpLoad_Personnel.aspx?page=0&rows=15&master_id=5&type=1

                        String [] strs=naireInfo.getTEXT().split("=");

                        String masterId=strs[1];


                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_Qa_UpLoad_Personnel.aspx?page="+pageIndex+"&rows=15&master_id="+masterId+"&type="+type;

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null){


                            if(response.body()!=null){

                                try {
                                    String resStr=response.body().string();

                                    if(!TextUtils.equals("",resStr)&&!TextUtils.equals("[]",resStr)){

                                        Gson gson=new Gson();

                                        msg.obj=gson.fromJson(resStr,new TypeToken<List<NairePersonInfo>>(){}.getType());

                                        msg.what=SUCCESS_LIST;

                                    }else{
                                        msg.what=SUCCESS_NODATA;
                                    }

                                } catch (IOException e) {
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
                }

        ).start();


    }

    private void LvSetAdapter(List<NairePersonInfo> data){

        if(adapter==null){

            adapter=new CommonAdapter<NairePersonInfo>(mContext,data,R.layout.item_naire_person) {
                @Override
                public void convert(CommonViewHolder holder, NairePersonInfo item, int position) {

                    TextView tvName=holder.getView(R.id.tv_item_naire_person_name);
                    tvName.setText(item.getNAME());
                    TextView tvSex=holder.getView(R.id.tv_item_naire_person_sex);
                    tvSex.setText(item.getSEX());
                    TextView tvStreet=holder.getView(R.id.tv_item_naire_person_street);
                    tvStreet.setText(item.getJD());
                    TextView tvJw=holder.getView(R.id.tv_item_naire_person_jw);
                    tvJw.setText(item.getJW());
                    TextView tvHuji=holder.getView(R.id.tv_item_naire_person_huji);
                    tvHuji.setText(item.getDZ());
                    TextView tvFnum=holder.getView(R.id.tv_item_naire_person_family_num);
                    tvFnum.setText(item.getConut()+"");
                    if(naireInfo.isISJYSTATUS()){
                        tvFnum.setVisibility(View.VISIBLE);
                    }else{
                        tvFnum.setVisibility(View.GONE);
                    }

                    LinearLayout ll=holder.getView(R.id.ll_item_naire_person);
                    if (position % 2 == 0) {
                        ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item1);
                    } else {
                        ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item2);
                    }
                }
            };

            lv.setAdapter(adapter);

        }else {

            adapter.notifyDataSetChanged();

        }

    }

    public void onRadio(View v){

        switch (v.getId()){

            case R.id.rb_naire_person_no_check:

                type=1;
                initDates();
            break;
            case R.id.rb_naire_person_checked:

                type=0;
                initDates();
                break;
        }

    }

}

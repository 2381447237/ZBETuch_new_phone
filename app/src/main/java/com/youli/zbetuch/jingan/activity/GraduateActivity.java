package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.AppendixInfo;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.GraQueryInfo;
import com.youli.zbetuch.jingan.entity.GraduateInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/10/9.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 应届毕业生
 */

public class GraduateActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private final int SUCCESS_LIST=10001;
    private final int FAIL=10002;
    private final int NODATA=10003;
    private Context mContext=GraduateActivity.this;

    private PullToRefreshListView lv;
    private List<GraduateInfo> data=new ArrayList<>();
    private CommonAdapter adapter;
    private Button btnFind,btnConFind;
    private TextView tvPnum;//人数
    private Spinner spYear;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    private EditText etName,etSfz;
    private String nameStr,sfzStr,yearStr;
    private GraduateInfo gInfo=new GraduateInfo();

    private int pageIndex;

    private GraQueryInfo queryInfo;

    private Handler mHandler=new Handler(){


        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS_LIST:

                    if(pageIndex==0) {
                        data.clear();
                    }
                    data.addAll((List<GraduateInfo>)msg.obj);
                    tvPnum.setText("共有"+data.get(0).getRecordCount()+"人");
                    LvSetAdapter(data);
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }

                    break;
                case NODATA:
                    data.clear();
                    LvSetAdapter(data);
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }
                    Toast.makeText(mContext,"查无此人",Toast.LENGTH_SHORT).show();
                    tvPnum.setText("共有0人");
                    break;
                case FAIL:

                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }
                    break;

            }

        }
    } ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graduate);

        //注册事件
        EventBus.getDefault().register(this);


        initViews();


    }

    private void initViews(){

       lv= (PullToRefreshListView) findViewById(R.id.lv_graduate);
        lv.setOnItemClickListener(this);
        lv.setMode(PullToRefreshBase.Mode.BOTH);

        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex=0;
                initDates(gInfo);


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                pageIndex++;
                initDates(gInfo);


            }
        });
        tvPnum= (TextView) findViewById(R.id.tv_graduate_num);
        tvPnum.setText("共有0人");
        spYear= (Spinner) findViewById(R.id.sp_graduate_year);

       initSpValue();
       btnFind= (Button) findViewById(R.id.btn_graduate_find);
        btnFind.setOnClickListener(this);
        btnConFind= (Button) findViewById(R.id.btn_graduate_condition_find);
        btnConFind.setOnClickListener(this);

        etName= (EditText) findViewById(R.id.et_graduate_name);
        etSfz= (EditText) findViewById(R.id.et_graduate_sfz);
    }


    private void LvSetAdapter(List<GraduateInfo> data){

        if(adapter==null) {
            adapter = new CommonAdapter<GraduateInfo>(mContext, data, R.layout.item_graduate) {
                @Override
                public void convert(CommonViewHolder holder, GraduateInfo item, int position) {

                    TextView tvNo = holder.getView(R.id.tv_item_graduate_no);
                    tvNo.setText((position + 1) + "");
                    TextView tvName = holder.getView(R.id.tv_item_graduate_name);
                    tvName.setText(item.getNAME());
                    TextView tvSex = holder.getView(R.id.tv_item_graduate_sex);
                    tvSex.setText(item.getSEX().trim());
                    TextView tvIdCard = holder.getView(R.id.tv_item_graduate_idcard);
                    tvIdCard.setText(item.getSFZ());
                    TextView tvPhone = holder.getView(R.id.tv_item_graduate_phone);
                    tvPhone.setText(item.getCONTACT_NUMBER());
                    LinearLayout ll = holder.getView(R.id.item_graduate_ll);
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

    @Override
    public void onClick(View v) {

        nameStr=etName.getText().toString().trim();
        sfzStr=etSfz.getText().toString().trim();
        yearStr=spYear.getSelectedItem().toString().trim();
        switch (v.getId()){

            case R.id.btn_graduate_find:

                initDates(gInfo);

                break;
            case R.id.btn_graduate_condition_find:

                Intent intent=new Intent(mContext,GradConQueryActivity.class);
                startActivityForResult(intent,10000);

                break;
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initDates(GraduateInfo gInfo){
//        {BASE_SITUATION(人员基本情况)=, MARK(特殊情况)=, CHECK_RECOMMEND(是否推荐就业岗位)=, COMPROPERTY_ID(企业类型)=-1, CHECK_GUIDE(是否职业指导)=,
//                EDU(文化程度)=, JOB_CATEGORY(岗位类别)=, rows=15, SALARY(税后薪资)=, INDUSTARY_CATEGORY_ID（行业类别）=-1, COMMITTEE_ID（居委）=-1, START_AGE=-1, page=0, SEX(性别)=, year=2014, STREET_ID(街道)=-1, END_AGE=-1, DETAIL_SITUATION(人员具体情况)=}
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        //http://web.youli.pw:89/Json/Get_Graduate_Master.aspx?page=0&rows=15&name=&sfz=&year=2014

                        String url=null;

                        if(queryInfo!=null) {

                          url = MyOkHttpUtils.BaseUrl + "/Json/Get_Graduate_Master.aspx?page=" + pageIndex + "&rows=15&name=" + nameStr + "&sfz=" + sfzStr + "&year=" + yearStr

                                    + "&BASE_SITUATION="+queryInfo.getBASE_SITUATION()+"&MARK="+queryInfo.getMARK()+"&CHECK_RECOMMEND="+queryInfo.getCHECK_RECOMMEND()+"&COMPROPERTY_ID="+queryInfo.getCOMPROPERTY_ID()+"&CHECK_GUIDE="+queryInfo.getCHECK_GUIDE()+"&EDU="+queryInfo.getEDU()+"&JOB_CATEGORY="+queryInfo.getJOB_CATEGORY()+"&SALARY="+queryInfo.getSALARY()+"&INDUSTARY_CATEGORY_ID="+queryInfo.getINDUSTARY_CATEGORY_ID()+"&COMMITTEE_ID="+queryInfo.getCOMMITTEE_ID() +
                                    "&START_AGE="+queryInfo.getSTART_AGE()+"&SEX="+queryInfo.getSEX()+"&STREET_ID="+queryInfo.getSTREET_ID()+"&END_AGE="+queryInfo.getEND_AGE()+"&DETAIL_SITUATION="+queryInfo.getDETAIL_SITUATION();
                        }else{
                            url = MyOkHttpUtils.BaseUrl + "/Json/Get_Graduate_Master.aspx?page=" + pageIndex + "&rows=15&name=" + nameStr + "&sfz=" + sfzStr + "&year=" + yearStr;
                        }

                        Log.e("2017/10/20","url=="+url);

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null){

                            if(response.body()!=null){

                                try {
                                    String resStr=response.body().string();

                                    if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")) {

                                        Gson gson = new Gson();

                                        msg.obj = gson.fromJson(resStr, new TypeToken<List<GraduateInfo>>() {
                                        }.getType());
                                        msg.what = SUCCESS_LIST;

                                        mHandler.sendMessage(msg);
                                    }else{

                                        msg.what = NODATA;
                                        mHandler.sendMessage(msg);

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }else{

                                msg.what=FAIL;
                                mHandler.sendMessage(msg);
                            }

                        }else{

                           msg.what=FAIL;
                            mHandler.sendMessage(msg);

                        }

                    }
                }

        ).start();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent=new Intent(mContext,GraPerDetailActivity.class);
        intent.putExtra("info",data.get(position-1));
        //startActivity(intent);
        startActivityForResult(intent,10000);
    }

    private void initSpValue() {
        String year = sdf.format(new Date());
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 5; i >= 0; i--) {
            list.add((Integer.parseInt(year) - i));
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(mContext,
                android.R.layout.simple_list_item_1, list);
        spYear.setAdapter(adapter);
        spYear.setSelection(list.size() - 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {

        if((requestCode==10000)&&(resultCode==20000)){
            queryInfo=(GraQueryInfo)i.getSerializableExtra("QueryInfo");
            initDates(gInfo);
        }else if((requestCode==10000)&&(resultCode==30000)){
            queryInfo=(GraQueryInfo)i.getSerializableExtra("QueryInfo");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

}

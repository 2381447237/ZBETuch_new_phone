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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.ResourcesDetailInfo;
import com.youli.zbetuch.jingan.entity.ResourcesInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import com.youli.zbetuch.jingan.utils.ProgressDialogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by liutao on 2017/8/9.
 */

public class ZiyuanDetailListActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener,AdapterView.OnItemClickListener{



    private Context mContext=ZiyuanDetailListActivity.this;
    private TextView typeTv;
    private EditText et;
    private String sfzStr;
    private Button queryBtn;
    private String typeStr;
    private int masterId;
    private int typeId;
    private int pageIndex;
    private ResourcesInfo rInfo;
    private TextView totalTv,noDataTv;
    private PullToRefreshListView lv;
    private RadioGroup rg;
    private List<ResourcesDetailInfo> dInfo=new ArrayList<>();
    private CommonAdapter commonAdapter;

    private final int SUCCESS=10000;
    private final int NODATA=10001;
    private final int PROBLEM=10002;

    public final static int RequestCode=111111;
    public final static int ResultCode=222222;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            ProgressDialogUtils.dismissMyProgressDialog(mContext);

            switch (msg.what){

                case SUCCESS:

                    if(!((List<ResourcesDetailInfo>)(msg.obj)).isEmpty()){
                        dInfo.addAll((List<ResourcesDetailInfo>)msg.obj);
                        noDataTv.setVisibility(View.GONE);
                        lv.setVisibility(View.VISIBLE);

                        lvSetAdapter(dInfo);
                    }else{
                        totalTv.setText("总共0条数据");
                        noDataTv.setVisibility(View.VISIBLE);
                        lv.setVisibility(View.GONE);
                    }

                    break;

                case PROBLEM:

                    Toast.makeText(mContext, "网络不给力", Toast.LENGTH_SHORT).show();

                    break;
                case NODATA:
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }
                    Toast.makeText(mContext, "没有更多数据了", Toast.LENGTH_SHORT).show();

                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ziyuan_detail);

        initViews();
    }

    private void initViews(){
         typeStr=getIntent().getStringExtra("TYPE");
         rInfo= (ResourcesInfo) getIntent().getSerializableExtra("RInfo");
        masterId=rInfo.getMASTER_ID();
        et= (EditText) findViewById(R.id.ziyuan_detail_et);
        queryBtn= (Button) findViewById(R.id.ziyuan_detail_query_btn);
        queryBtn.setOnClickListener(this);
        rg= (RadioGroup) findViewById(R.id.ziyuan_detail_rg);
        rg.check(R.id.ziyuan_detail_weicha_rb);
        setTypeId();
        rg.setOnCheckedChangeListener(this);
        typeTv= (TextView) findViewById(R.id.ziyuan_detail_type_tv);
        noDataTv= (TextView) findViewById(R.id.ziyuan_detail_tv_nodata);
        totalTv= (TextView) findViewById(R.id.ziyuan_detail_num_tv);
        if(TextUtils.equals("失业",typeStr)){
            typeTv.setText("失业调查");
        }else if(TextUtils.equals("无业",typeStr)){
            typeTv.setText("无业调查");
        }

        lv= (PullToRefreshListView) findViewById(R.id.ziyuan_detail_lv);
        lv.setMode(PullToRefreshBase.Mode.BOTH);


        lv.setOnItemClickListener(this);

        getNetWorkData(masterId,typeStr,typeId,"null",pageIndex);

        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                        pageIndex=0;
        if(sfzStr==null){
            getNetWorkData(masterId,typeStr,typeId,"null",pageIndex);
        }else{
            getNetWorkData(masterId,typeStr,typeId,sfzStr,pageIndex);
        }

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                        pageIndex++;

        if(sfzStr==null){
            getNetWorkData(masterId,typeStr,typeId,"null",pageIndex);
        }else{
            getNetWorkData(masterId,typeStr,typeId,sfzStr,pageIndex);
        }

            }
        });
    }

//身份证查询:
//    http://web.youli.pw:89/Json/Get_Resource_Survey_Detil_WY.aspx?
//    page=0&rows=15&Master_id=2&typeName=无业&sfz=310108198301172638
    private void getNetWorkData(final int masterId, final String typeName, final int typeId, final String sfz, final int page){

        ProgressDialogUtils.showMyProgressDialog(this);

        if(page==0) {
            if (dInfo != null && dInfo.size() > 0 ) {
                dInfo.clear();
            }
        }

        if(!TextUtils.equals(sfz,"null")) {
            dInfo.clear();
        }


        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String typeMark = null;

                        if(TextUtils.equals("失业",typeStr)){
                            typeMark="SY";
                        }else if(TextUtils.equals("无业",typeStr)){
                            typeMark="WY";
                        }
                        String url = null;
                        if(TextUtils.equals(sfz,"null")) {
                            url = MyOkHttpUtils.BaseUrl + "/Json/Get_Resource_Survey_Detil_" + typeMark + ".aspx?page="+page+"&rows=15&Master_id=" + masterId + "&typeName=" + typeName + "&type=" + typeId;
                        }else{
                            url = MyOkHttpUtils.BaseUrl + "/Json/Get_Resource_Survey_Detil_" + typeMark + ".aspx?page=0&rows=15&Master_id=" + masterId + "&typeName=" + typeName + "&sfz=" + sfz;
                        }

                        Log.e("2017/8/10","url=="+url);

                        Response response= MyOkHttpUtils.okHttpGet(url);

                        try {

                            Message msg=Message.obtain();

                            if(response!=null){

                                String infoStr=response.body().string();
                                Gson gson=new Gson();

                                if(((List<ResourcesDetailInfo>) gson.fromJson(infoStr,
                                        new TypeToken<List<ResourcesDetailInfo>>(){}.getType())).size()==0){
                                    msg.what=NODATA;
                                    mHandler.sendMessage(msg);
                                }else{
                                    msg.what=SUCCESS;
                                    msg.obj=gson.fromJson(infoStr,
                                            new TypeToken<List<ResourcesDetailInfo>>(){}.getType());
                                    mHandler.sendMessage(msg);
                                }

                            }else{

                                sendProblemMessage(msg);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }

        ).start();

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        pageIndex=0;

        switch (checkedId){

            case R.id.ziyuan_detail_weicha_rb:
                rg.check(R.id.ziyuan_detail_weicha_rb);
                setTypeId();
                sfzStr="null";


                    getNetWorkData(masterId,typeStr,typeId,"null",pageIndex);


                break;
            case R.id.ziyuan_detail_yicha_rb:
                rg.check(R.id.ziyuan_detail_yicha_rb);
                setTypeId();
                sfzStr="null";

                    getNetWorkData(masterId,typeStr,typeId,"null",pageIndex);

                break;
        }

    }




    private void lvSetAdapter(final List<ResourcesDetailInfo> data){

          if(commonAdapter==null) {

              commonAdapter = new CommonAdapter<ResourcesDetailInfo>(this, dInfo, R.layout.item_ziyuan_detail_lv) {

                  @Override
                  public void convert(CommonViewHolder holder, ResourcesDetailInfo item, int position) {

                      TextView numTv = holder.getView(R.id.item_ziyuan_detail_num_tv);
                      numTv.setText(position + 1 + "");
                      TextView nameTv = holder.getView(R.id.item_ziyuan_detail_name_tv);
                      nameTv.setText(data.get(position).getNAME());
                      TextView idCardTv = holder.getView(R.id.item_ziyuan_detail_idcard_tv);
                      idCardTv.setText(data.get(position).getSFZ());
                      TextView juWeiTv = holder.getView(R.id.item_ziyuan_detail_juwei_tv);
                      juWeiTv.setText(data.get(position).getJW());

                      LinearLayout ll = holder.getView(R.id.item_ziyuan_detail_ll);
                      if (position % 2 == 0) {
                          ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item1);
                      } else {
                          ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item2);
                      }

                  }
              };

              lv.setAdapter(commonAdapter);

          }else{

              commonAdapter.notifyDataSetChanged();

          }
        if (lv.isRefreshing()) {
                    lv.onRefreshComplete();
        }
        totalTv.setText("总共" + data.get(0).getRecordCount() + "条数据");
    }

    private void setTypeId(){

            if(rg.getCheckedRadioButtonId()==R.id.ziyuan_detail_weicha_rb){
                typeId=1;
            }else if(rg.getCheckedRadioButtonId()==R.id.ziyuan_detail_yicha_rb){
                typeId=0;
            }

    }

    private void sendProblemMessage(Message msg) {
        msg.what = PROBLEM;
        mHandler.sendMessage(msg);
    }


    @Override
    public void onClick(View v) {

        sfzStr=et.getText().toString().trim();

        switch (v.getId()){

            case R.id.ziyuan_detail_query_btn:

                if(sfzStr.length()==0) {

                    Toast.makeText(mContext, "身份证不能为空", Toast.LENGTH_SHORT).show();

                }else if(sfzStr.length()<18){
                    Toast.makeText(mContext, "请输入18位身份证号",
                            Toast.LENGTH_LONG).show();
                }else{



                    getNetWorkData(masterId,typeStr,typeId,sfzStr,pageIndex);
                }

                break;

        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        boolean isCheck = false;
        
        if(rg.getCheckedRadioButtonId()==R.id.ziyuan_detail_weicha_rb){
            isCheck=false;
        }else if(rg.getCheckedRadioButtonId()==R.id.ziyuan_detail_yicha_rb){
            isCheck=true;
        }
        
        Intent intent=new Intent(this,ShiwuyeDetailActivity.class);
        intent.putExtra("RDInfo",dInfo.get(position-1));
        intent.putExtra("IsCheck",isCheck);
        
        startActivityForResult(intent,RequestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==RequestCode&&resultCode==ResultCode){

           // Toast.makeText(mContext,"回传",Toast.LENGTH_SHORT).show();
            if(sfzStr==null){
                getNetWorkData(masterId,typeStr,typeId,"null",0);
            }else{
                getNetWorkData(masterId,typeStr,typeId,sfzStr,0);
            }
        }


    }
}

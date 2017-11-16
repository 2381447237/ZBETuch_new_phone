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
import com.youli.zbetuch.jingan.entity.InterviewDetailsInfo;
import com.youli.zbetuch.jingan.entity.InterviewInfo;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/10/10.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 招聘会细节
 */

public class InterviewDetailsActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemLongClickListener{

    private final int SUCCESS_LIST=10001;
    private final int SUCCESS_GET_STATE=10002;
    private final int SUCCESS_SET_STATE=10003;
    private final int SUCCESS_NODATA=10004;
    private final int FAIL=10005;

    private Context mContext=InterviewDetailsActivity.this;
    private TextView tvName,tvDate,tvCnum,tvPnum,tvAddress;
    private InterviewInfo iInfo;
    private Button btnRead;
    private PullToRefreshListView lv;
    private List<InterviewDetailsInfo> data=new ArrayList<>();
    private CommonAdapter adapter;
    private int pageIndex;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS_LIST:

                    if(pageIndex==0) {
                        data.clear();
                    }
                    data.addAll((List<InterviewDetailsInfo>)msg.obj);
                    if(lv.isRefreshing()){
                        lv.onRefreshComplete();
                    }
                    if(data.size()==0){
                        Toast.makeText(mContext, "没有岗位信息", Toast.LENGTH_SHORT).show();
                    }else{
                        lvSetAdapter(data);
                    }


                    break;

                case SUCCESS_GET_STATE:

                    if (TextUtils.equals("wd",(String)msg.obj)){
                        btnRead.setEnabled(true);
                        btnRead.setText("未读");
                    } else if (TextUtils.equals("yd",(String)msg.obj)) {
                        btnRead.setEnabled(false);
                        btnRead.setText("已读");
                    } else if (TextUtils.equals("gq",(String)msg.obj)) {
                        btnRead.setEnabled(false);
                        btnRead.setText("过期");
                    }

                    break;

                case SUCCESS_SET_STATE:

                    if (TextUtils.equals("True",(String)msg.obj)) {
                        btnRead.setEnabled(false);
                        btnRead.setText("已读");
                        Toast.makeText(mContext, "已阅读", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "提交失败", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case FAIL:

                    Toast.makeText(mContext, "网络不给力", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_interview_details);
        iInfo= (InterviewInfo) getIntent().getSerializableExtra("details");
        initViews();
    }

    private void initViews(){
        tvName= (TextView) findViewById(R.id.tv_interview_details_name);
        tvDate= (TextView) findViewById(R.id.tv_interview_details_date);
        tvCnum= (TextView) findViewById(R.id.tv_interview_details_company_num);
        tvPnum= (TextView) findViewById(R.id.tv_interview_details_post_num);
        tvAddress= (TextView) findViewById(R.id.tv_interview_details_address);
        btnRead= (Button) findViewById(R.id.btn_interview_detail_isread);
        btnRead.setOnClickListener(this);

        if(iInfo==null){
            return;
        }

        getReadState();

        lv= (PullToRefreshListView) findViewById(R.id.lv_interview_details);
        lv.setMode(PullToRefreshBase.Mode.BOTH);

        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>(){

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                //Toast.makeText(mContext,"刷新",Toast.LENGTH_SHORT).show();
                pageIndex=0;
                getNetListDates();
            }


            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //  Toast.makeText(mContext,"加载更多",Toast.LENGTH_SHORT).show();
                pageIndex++;
                getNetListDates();
            }
        });
        lv.setOnItemLongClickListener(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(mContext,JobInfoDetailActivity.class);
                intent.putExtra("JOBNO",data.get(position-1).getJobno());
                startActivity(intent);
            }
        });

        getNetListDates();
        setDates();

    }

    private void setDates(){

        tvName.setText(iInfo.getNAME());
        tvDate.setText(MyDateUtils.stringToYMDHMS(iInfo.getJOBFAIRDATA()));
        tvCnum.setText(iInfo.getCompany_num());
        tvPnum.setText(iInfo.getJob_num());
        tvAddress.setText(iInfo.getADDRESS());

    }

    //获取按钮的状态
   private void getReadState(){
       //http://web.youli.pw:89/Json/Get_JobFairCheck.aspx?master_id=1
       new Thread(

               new Runnable() {
                   @Override
                   public void run() {
                       Map<String,String> mapData=new HashMap<>();
                       mapData.put("master_id",""+iInfo.getID());

                       String url= MyOkHttpUtils.BaseUrl+"/Json/Get_JobFairCheck.aspx";

                       Response response=MyOkHttpUtils.okHttpPostFormBody(url, (HashMap<String, String>) mapData);

                       try {
                           String resStr=response.body().string();

                           Message msg=Message.obtain();


                           if(!TextUtils.equals(resStr,"")){

                               msg.what=SUCCESS_GET_STATE;
                               msg.obj=resStr;

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
    //获取现场面试详情列表
    private void getNetListDates(){
       // http://web.youli.pw:89/Json/GetJobFairDetail.aspx?master_id=1&page=0&rows=15
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                        Map<String,String> mapData=new HashMap<>();
                        mapData.put("master_id",""+iInfo.getID());
                        mapData.put("page",pageIndex+"");
                        mapData.put("rows","15");

                        String url= MyOkHttpUtils.BaseUrl+"/Json/GetJobFairDetail.aspx";

                        Response response=MyOkHttpUtils.okHttpPostFormBody(url, (HashMap<String, String>) mapData);

                        try {
                            String resStr=response.body().string();

                            Gson gson=new Gson();

                            Message msg=Message.obtain();

                            if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")){

                                msg.what=SUCCESS_LIST;
                                msg.obj=gson.fromJson(resStr,new TypeToken<List<InterviewDetailsInfo>>(){}.getType());

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

    private void lvSetAdapter(List<InterviewDetailsInfo> list){

        if(adapter==null){

            adapter=new CommonAdapter<InterviewDetailsInfo>(mContext,list,R.layout.item_interview_details) {
                @Override
                public void convert(CommonViewHolder holder, InterviewDetailsInfo item, int position) {

                    TextView tvCname=holder.getView(R.id.tv_item_interview_details_cname);
                    tvCname.setText(item.getComname());
                    TextView tvJname=holder.getView(R.id.tv_item_interview_details_jname);
                    tvJname.setText(item.getJobname());
                    TextView tvEdu=holder.getView(R.id.tv_item_interview_details_edu);
                    tvEdu.setText(item.getEduname());
                    TextView tvAge=holder.getView(R.id.tv_item_interview_details_age);
                    tvAge.setText(item.getStartage()+"-"+item.getEndage());
                    TextView tvNum=holder.getView(R.id.tv_item_interview_details_num);
                    tvNum.setText(item.getRecruitnums()+"");
                    TextView tvSalary=holder.getView(R.id.tv_item_interview_details_salary);
                    tvSalary.setText((int)item.getStartsalary()+"-"+(int)item.getEndsalary());
                    LinearLayout ll = holder.getView(R.id.item_interview_details_ll);
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

        switch (v.getId()){

            case R.id.btn_interview_detail_isread:

                if(iInfo.getID()!=0) {
                    setReadState();
                }
                break;

        }

    }


    //设置按钮状态
    private void  setReadState(){
//http://web.youli.pw:89/Json/Set_JobFairCheck.aspx?master_id=1
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                        Map<String,String> mapData=new HashMap<>();
                        mapData.put("master_id",""+iInfo.getID());

                        String url= MyOkHttpUtils.BaseUrl+"/Json/Set_JobFairCheck.aspx";

                        Response response=MyOkHttpUtils.okHttpPostFormBody(url, (HashMap<String, String>) mapData);

                        try {
                            String resStr=response.body().string();

                            Message msg=Message.obtain();

                            if(!TextUtils.equals(resStr,"")){

                                msg.what=SUCCESS_SET_STATE;
                                msg.obj=resStr;

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

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        showMyDialog(position);

        return true;
    }

    private void showMyDialog(final int position){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(mContext).inflate(R.layout.dialog_interview_details,null,false);
        builder.setView(view);
        final AlertDialog dialog=builder.create();
        Button btnGeRen= (Button) view.findViewById(R.id.btn_dialog_interview_details_geren);
        btnGeRen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext,GeRenRecomActivity.class);

                intent.putExtra("master_id",iInfo.getID()+"");
                intent.putExtra("JOBNO",data.get(position-1).getJobno());

                startActivity(intent);

                dialog.dismiss();
            }
        });
        Button btnLieBiao= (Button) view.findViewById(R.id.btn_dialog_interview_details_liebiao);
        btnLieBiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,RecomListActivity.class);
                intent.putExtra("master_id",iInfo.getID()+"");
                intent.putExtra("JOBNO",data.get(position-1).getJobno());
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

    }

}

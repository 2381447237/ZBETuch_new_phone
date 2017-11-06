package com.youli.zbetuch.jingan.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.GraduateInfo;
import com.youli.zbetuch.jingan.entity.InvestInfo;
import com.youli.zbetuch.jingan.entity.OccupGuideInfo;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import com.youli.zbetuch.jingan.utils.SpinnerUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/10/17.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 职业指导情况的碎片
 */

public class JobRouteTwoFragment extends BaseFragment implements View.OnClickListener{

    private final int SUCCESS_LIST=10001;
    private final int SUCCESS_ADD=10002;
    private final int SUCCESS_NODATA=10003;//新建和修改
    private final int SUCCESS_DELETE=10004;//删除
    private final int FAIL=10005;

    private GraduateInfo gInfo;

    public static final JobRouteTwoFragment newInstance(GraduateInfo gInfo){

        JobRouteTwoFragment fragment = new JobRouteTwoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("gInfo",gInfo);
        fragment.setArguments(bundle);

        return fragment;
    }


    private View contentView;
    private Button btnNew;
    private PullToRefreshListView lv;
    private CommonAdapter adapter;
    private List<OccupGuideInfo> data=new ArrayList<>();

    private AlertDialog newOrModifyDialog;
    private Calendar c = Calendar.getInstance();
    private Spinner spOccupGuide;//是否职业指导
    private TextView tvOccupGuideDate;//职业指导日期
    private EditText etOccupGuider;//职业指导员
    private EditText etOccupGuideContent;//职业指导内容

    private Spinner spRecomGuide;//是否推荐就业岗位
    private TextView tvRecomGuideDate;//推荐面试日期
    private EditText etRecomCom;//推荐面试单位名称
    private EditText etRecomJob;//推荐面试岗位名称
    private int pageIndex;

    private boolean isChange=false;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS_LIST:

                    if(pageIndex==0){
                        data.clear();
                    }
                    data.addAll((List<OccupGuideInfo>)msg.obj);

                    Log.e("2017/10/19","data=+="+data);

                    LvSetAdapter(data);
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }

                    break;

                case SUCCESS_ADD:
                    if (getActivity() != null) {
                        if (TextUtils.equals(msg.obj + "", "True")) {
                            EventBus.getDefault().post(new GraduateInfo());
                            Toast.makeText(getActivity(), "保存成功!", Toast.LENGTH_SHORT).show();
                            pageIndex = 0;
                            initDates();
                        }
                    }
                    break;

                case SUCCESS_DELETE:
                    if (getActivity() != null) {
                        if(TextUtils.equals(msg.obj+"","True")){
                            EventBus.getDefault().post(new GraduateInfo());
                            data.remove(msg.arg1);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), "删除成功!", Toast.LENGTH_SHORT).show();
//                            pageIndex=0;
//                            initDates();
                        }

                    }
                    break;

                case FAIL:

                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "网络不给力", Toast.LENGTH_SHORT).show();
                    }
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gInfo=(GraduateInfo)getArguments().get("gInfo");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        contentView=LayoutInflater.from(getContext()).inflate(R.layout.fragment_job_route_two,container,false);

        isViewCreated=true;//标记

        return contentView;
    }

    @Override
    public void lazyLoadData() {
        if(isViewCreated){
            //逻辑都写这里面
            initViews();
        }
    }


    private void initViews(){

        btnNew= (Button) contentView.findViewById(R.id.btn_job_route_two_new);
        btnNew.setOnClickListener(this);

        lv= (PullToRefreshListView) contentView.findViewById(R.id.lv_job_route_two);
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
                        //http://web.youli.pw:89/Json/Get_JobGuide.aspx?master_id=100&page=0&rows=15
                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_JobGuide.aspx?master_id="+gInfo.getID()+"&page="+pageIndex+"&rows=15";


                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null){

                            if(response.body()!=null){


                                try {
                                    String resStr=response.body().string();


                                    if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")){

                                        Gson gson=new Gson();
                                        msg.what=SUCCESS_LIST;
                                        msg.obj = gson.fromJson(resStr, new TypeToken<List<OccupGuideInfo>>() {
                                        }.getType());


                                    }else{
                                        msg.what=SUCCESS_NODATA;

                                    }
                                    mHandler.sendMessage(msg);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }else{

                             msg.what=FAIL;
                                mHandler.sendMessage(msg);

                            }

                        }

                    }
                }

        ).start();

    }

    private void LvSetAdapter(List<OccupGuideInfo> data){

        if(getActivity()!=null){

            if(adapter==null){

                adapter=new CommonAdapter<OccupGuideInfo>(getActivity(),data,R.layout.item_fmt_job_route_two) {
                    @Override
                    public void convert(CommonViewHolder holder, OccupGuideInfo item, final int position) {


                        LinearLayout llOccupGuider=holder.getView(R.id.ll_fmt_job_route_two_occup_guide_guider);
                        LinearLayout llOccupGuideContent=holder.getView(R.id.ll_fmt_job_route_two_occup_guide_content);
                        LinearLayout llOccupGuideDate=holder.getView(R.id.ll_fmt_job_route_two_occup_guide_date);

                        if(item.isCHECK_GUIDE()){
                            llOccupGuider.setVisibility(View.VISIBLE);
                            llOccupGuideContent.setVisibility(View.VISIBLE);
                            llOccupGuideDate.setVisibility(View.VISIBLE);
                        }else{
                            llOccupGuider.setVisibility(View.GONE);
                            llOccupGuideContent.setVisibility(View.GONE);
                            llOccupGuideDate.setVisibility(View.GONE);
                        }

                        TextView tvOccupGuide=holder.getView(R.id.tv_fmt_job_route_two_occup_guide);
                        tvOccupGuide.setText(item.isCHECK_GUIDE()?"是":"否");
                        TextView tvOccupGuideDate=holder.getView(R.id.tv_fmt_job_route_two_occup_guide_date);
                        tvOccupGuideDate.setText(MyDateUtils.stringToYMD(item.getGUIDE_DATE()));
                        TextView tvOccupGuider=holder.getView(R.id.tv_fmt_job_route_two_occup_guider);
                        tvOccupGuider.setText(item.getGUIDE_NAME());
                        TextView tvOccupGuideContent=holder.getView(R.id.tv_fmt_job_route_two_occup_guide_content);
                        tvOccupGuideContent.setText(item.getGUIDE_DOC());

                        TextView tvEmployPost=holder.getView(R.id.tv_fmt_job_route_two_employ_post);
                        tvEmployPost.setText(item.isCHECK_RECOMMEND()?"是":"否");

                        LinearLayout llInterviewCompanyName=holder.getView(R.id.ll_fmt_job_route_two_interview_company_name);
                        LinearLayout llInterviewPostName=holder.getView(R.id.ll_fmt_job_route_two_interview_post_name);
                        LinearLayout llInterviewDate=holder.getView(R.id.ll_fmt_job_route_two_interview_date);

                        if(item.isCHECK_RECOMMEND()){
                            llInterviewCompanyName.setVisibility(View.VISIBLE);
                            llInterviewPostName.setVisibility(View.VISIBLE);
                            llInterviewDate.setVisibility(View.VISIBLE);
                        }else{
                            llInterviewCompanyName.setVisibility(View.GONE);
                            llInterviewPostName.setVisibility(View.GONE);
                            llInterviewDate.setVisibility(View.GONE);
                        }

                        TextView tvInterviewCompanyName=holder.getView(R.id.tv_fmt_job_route_two_interview_company_name);
                        tvInterviewCompanyName.setText(item.getRECOMMEND_COM());
                        TextView tvInterviewPostName=holder.getView(R.id.tv_fmt_job_route_two_interview_post_name);
                        tvInterviewPostName.setText(item.getRECOMMEND_JOB());
                        TextView tvInterviewDate=holder.getView(R.id.tv_fmt_job_route_two_interview_date);
                        tvInterviewDate.setText(MyDateUtils.stringToYMD(item.getRECOMMEND_DATE()));

                        Button btnDelete=holder.getView(R.id.btn_fmt_job_route_two_delete);
                        btnDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                showDeleteDialog(position);//删除
                            }
                        });

                        Button btnModify=holder.getView(R.id.btn_fmt_job_route_two_modify);
                        btnModify.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                showNewOrModifyDialog("modify", position);//修改

                            }
                        });
                    }
                };

                lv.setAdapter(adapter);

            }else{

                adapter.notifyDataSetChanged();

            }

        }

    }

    private void  showDeleteDialog(final int p){

        if(getActivity()!=null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("修改信息提示");
            builder.setMessage("您确定删除此职业指导情况吗?");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    deleteInfo(p);

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
    }


    //调查走访情况的删除
    private void deleteInfo(final int p){

        final Map<String,String> map=new HashMap<>();
        map.put("ID",data.get(p).getID()+"");
        map.put("delete", "true");
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        //http://web.youli.pw:89/Json/Set_JobGuide.aspx
                        String url=MyOkHttpUtils.BaseUrl+"/Json/Set_JobGuide.aspx";

                        Response response=MyOkHttpUtils.okHttpPostFormBody(url, (HashMap<String, String>) map);

                        Message msg=Message.obtain();

                        if(response!=null){

                            if(response.body()!=null){

                                try {
                                    String resStr=response.body().string();
                                  if(!TextUtils.equals(resStr,"")) {
                                      msg.what = SUCCESS_DELETE;
                                      msg.arg1 = p;
                                      msg.obj = resStr;
                                      mHandler.sendMessage(msg);
                                  }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }else {

                                msg.what=FAIL;
                                mHandler.sendMessage(msg);

                            }

                        }

                    }
                }

        ).start();

    };

    private void showNewOrModifyDialog(final String sign, final int position) {

        if (getActivity() != null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_job_route_two, null, false);
            builder.setView(view);

            newOrModifyDialog= builder.create();

           tvOccupGuideDate=(TextView) view.findViewById(R.id.tv_dialog_job_route_two_occup_guide_time);//职业指导日期
            tvOccupGuideDate.setText(c.get(Calendar.YEAR)+ "-" +(c.get(Calendar.MONTH)+1) + "-" +c.get(Calendar.DAY_OF_MONTH));
            tvOccupGuideDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener(){


                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            tvOccupGuideDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            if(year>c.get(Calendar.YEAR)||monthOfYear>c.get(Calendar.MONTH)||dayOfMonth>c.get(Calendar.DAY_OF_MONTH)){

                                if(getActivity()==null){
                                    return;
                                }

                                Toast.makeText(getActivity(),"时间必须是当年且小于当前时间!",Toast.LENGTH_SHORT).show();
                                tvOccupGuideDate.setText(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DAY_OF_MONTH));
                            }

                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            etOccupGuider=(EditText) view.findViewById(R.id.et_dialog_job_route_two_occup_guider);//职业指导员
            etOccupGuideContent=(EditText) view.findViewById(R.id.et_dialog_job_route_two_occup_guide_content);//职业指导内容

            tvRecomGuideDate=(TextView) view.findViewById(R.id.tv_dialog_job_route_two_recom_guide_time);//推荐面试日期
            tvRecomGuideDate.setText(c.get(Calendar.YEAR)+ "-" +(c.get(Calendar.MONTH)+1) + "-" +c.get(Calendar.DAY_OF_MONTH));
            tvRecomGuideDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener(){


                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            tvRecomGuideDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            if(year>c.get(Calendar.YEAR)||monthOfYear>c.get(Calendar.MONTH)||dayOfMonth>c.get(Calendar.DAY_OF_MONTH)){

                                if(getActivity()==null){
                                    return;
                                }

                                Toast.makeText(getActivity(),"时间必须是当年且小于当前时间!",Toast.LENGTH_SHORT).show();
                                tvRecomGuideDate.setText(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DAY_OF_MONTH));
                            }

                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                }
            });


            etRecomCom=(EditText) view.findViewById(R.id.et_dialog_job_route_two_recom_com);//推荐面试单位名称
            etRecomJob=(EditText) view.findViewById(R.id.et_dialog_job_route_two_recom_job);//推荐面试岗位名称

            spOccupGuide=(Spinner) view.findViewById(R.id.sp_dialog_job_route_two_occup_guide);//是否职业指导
                    spOccupGuide.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {

                            if (spOccupGuide.getSelectedItem().toString().trim()
                                    .equals("是")) {
                                setGrideuserful(position);
                            } else {
                                setGrideNotUserful();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
            spRecomGuide=(Spinner) view.findViewById(R.id.sp_dialog_job_route_two_recom_guide);//是否推荐就业岗位
                    spRecomGuide.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {



                            if (spRecomGuide.getSelectedItem().toString().trim()
                                    .equals("是")) {
                                setRecommendUserful(position);
                            } else {
                                setRecommendNotUserful();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

            if(TextUtils.equals(sign,"modify")){

                isChange=true;

                String OgStr=data.get(position).isCHECK_GUIDE()?"是":"否";
                SpinnerUtils.setSpinnerSelect(spOccupGuide,OgStr);
                String RgStr=data.get(position).isCHECK_RECOMMEND()?"是":"否";
                SpinnerUtils.setSpinnerSelect(spRecomGuide,RgStr);
                tvOccupGuideDate.setText(MyDateUtils.stringToYMD(data.get(position).getGUIDE_DATE()));
                etOccupGuider.setText(data.get(position).getGUIDE_NAME());
                etOccupGuideContent.setText(data.get(position).getGUIDE_DOC());

                tvRecomGuideDate.setText(MyDateUtils.stringToYMD(data.get(position).getRECOMMEND_DATE()));
                etRecomCom.setText(data.get(position).getRECOMMEND_COM());
                etRecomJob.setText(data.get(position).getRECOMMEND_JOB());
            }else if(TextUtils.equals(sign,"new")){
                isChange=false;
            }

            Button btnSure= (Button) view.findViewById(R.id.btn_dialog_job_route_two_sure);//确定
            btnSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(checkFrm()){
                        showSaveDialog(sign,position);
                    }

                }
            });

            Button btnCancel= (Button) view.findViewById(R.id.btn_dialog_job_route_two_cancel);//取消
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    newOrModifyDialog.dismiss();

                }
            });


            newOrModifyDialog.show();
            newOrModifyDialog.setCanceledOnTouchOutside(false);
        }
        ;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_job_route_two_new:

                showNewOrModifyDialog("new",-1);//新建

                break;



        }

    }

    private void  showSaveDialog(final String sign, final int position){

        if(getActivity()!=null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("保存信息提示");
            builder.setMessage("您确定保存此职业指导情况吗?");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    newOrModifyDialog.dismiss();
                    newOrChangeInfo(sign,position);

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }

    }

    //职业指导情况的新建或者修改
    //http://web.youli.pw:89/Json/Set_JobGuide.aspx
    // {MASTER_ID=3174, ID=0,GUIDE_NAME=职业指导员, GUIDE_DATE=2017-10-19, GUIDE_DOC=指导内容, RECOMMEND_COM=单位名称, CHECK_RECOMMEND=true, CHECK_GUIDE=true,
    //  RECOMMEND_JOB=岗位名称, RECOMMEND_DATE=2017-10-20}
    private void  newOrChangeInfo(String sign,int position){

       final Map<String,String> mapData=new HashMap<String,String>();
        if(TextUtils.equals(sign,"new")) {
            mapData.put("ID", "0");
        }else if(TextUtils.equals(sign,"modify")){
            mapData.put("ID", data.get(position).getID()+"");
        }
        mapData.put("MASTER_ID",gInfo.getID()+"");
        mapData.put("CHECK_GUIDE",spOccupGuide.getSelectedItem().toString()
                .trim().equals("是") ? "true" : "false");
        mapData.put("GUIDE_NAME",etOccupGuider.getText().toString().trim());
        mapData.put("GUIDE_DATE",tvOccupGuideDate.getText().toString().trim()+"");
        mapData.put("GUIDE_DOC",etOccupGuideContent.getText().toString().trim());
        mapData.put("CHECK_RECOMMEND",spRecomGuide.getSelectedItem().toString()
                .trim().equals("是") ? "true" : "false");
        mapData.put("RECOMMEND_COM",etRecomCom.getText().toString().trim());
        mapData.put("RECOMMEND_JOB",etRecomJob.getText().toString().trim());

       String reDate=(c.get(Calendar.YEAR)+ "-" +(c.get(Calendar.MONTH)+1) + "-" +c.get(Calendar.DAY_OF_MONTH));

        mapData.put("RECOMMEND_DATE",tvRecomGuideDate.getText().toString().trim().length()==0?reDate:tvRecomGuideDate.getText().toString().trim());
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                        //http://web.youli.pw:89/Json/Set_JobGuide.aspx
                        String url=MyOkHttpUtils.BaseUrl+"/Json/Set_JobGuide.aspx";

                        Response response=MyOkHttpUtils.okHttpPostFormBody(url, (HashMap<String, String>) mapData);

                        Message msg=Message.obtain();

                        if(response!=null){

                            if(response.body()!=null){

                                try {
                                    String resStr=response.body().string();

                                    Log.e("2017/10/20","新建的响应=="+resStr);

                                    if(!TextUtils.equals(resStr,"")){

                                        msg.what=SUCCESS_ADD;
                                        msg.obj=resStr;
                                    }else{
                                        msg.what=FAIL;
                                    }

                                } catch (IOException e) {
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

    };

    /**
     * 设置推荐企业无效
     */
    private void setRecommendNotUserful() {
        setTextNotUserful(tvRecomGuideDate);
        setTextNotUserful(etRecomCom);
        setTextNotUserful(etRecomJob);
    }

    /**
     * 设置推荐企业有效
     */
    private void setRecommendUserful(int position) {
        setTextUserful(tvRecomGuideDate);
        setTextUserful(etRecomCom);
        setTextUserful(etRecomJob);
        if(!isChange) {
            tvRecomGuideDate.setText(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));
        }else{
            tvRecomGuideDate.setText(MyDateUtils.stringToYMD(data.get(position).getRECOMMEND_DATE()));
        }
    }


    /**
     * 设置职业指导员有效
     */
    private void setGrideuserful(int position) {
        setTextUserful(tvOccupGuideDate);
        setTextUserful(etOccupGuider);
        setTextUserful(etOccupGuideContent);
        if(!isChange) {
            tvOccupGuideDate.setText(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));
        }else{
            tvOccupGuideDate.setText(MyDateUtils.stringToYMD(data.get(position).getGUIDE_DATE()));
        }
    }

    /**
     * 设置职业指导员无效
     */
    private void setGrideNotUserful() {
        setTextNotUserful(tvOccupGuideDate);
        setTextNotUserful(etOccupGuider);
        setTextNotUserful(etOccupGuideContent);
    }


    /**
     * 设置文本框有效
     *
     * @param txt
     */
    private void setTextUserful(View txt) {
        txt.setEnabled(true);
        txt.setBackgroundResource(R.drawable.shape_ziyuan_detail_et);
    }

    /**
     * 设置文本框无效
     *
     * @param txt
     */
    private void setTextNotUserful(View txt) {
        txt.setEnabled(false);
        txt.setBackgroundResource(R.drawable.txtbgnouse);
        if(txt instanceof TextView){
            ((TextView) txt).setText("");
        }else if(txt instanceof EditText){
            ((EditText) txt).setText("");
        }
    }

    /**
     * 页面非空验证
     *
     * @return
     */
    private boolean checkFrm() {
//        if (GradeatePersonInfoActivity.gradeateId == 0) {
//            Toast.makeText(getActivity(), "人员还未保存，请先保存或选中人员！", Toast.LENGTH_SHORT)
//                    .show();
//            return false;
//        }

        if(getActivity()==null){
            return false;
        }

        if (spOccupGuide.getSelectedItem().toString().trim().equals("请选择")) {
            Toast.makeText(getActivity(), "请选择是否职业指导！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spOccupGuide.getSelectedItem().toString().trim().equals("是")) {
            if (etOccupGuider.getText().toString().trim().length() == 0) {
                Toast.makeText(getActivity(), "请输入职业指导员！", Toast.LENGTH_SHORT)
                        .show();
                etOccupGuider.requestFocus();
                return false;
            }
            if (etOccupGuideContent.getText().toString().trim().length() == 0) {
                Toast.makeText(getActivity(), "请输入职业指导内容！", Toast.LENGTH_SHORT)
                        .show();
                etOccupGuideContent.requestFocus();
                return false;
            }
        }
        if (spRecomGuide.getSelectedItem().toString().trim().equals("请选择")) {
            Toast.makeText(getActivity(), "请选择是否推荐就业岗位！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spRecomGuide.getSelectedItem().toString().trim().equals("是")) {
            if (etRecomCom.getText().toString().trim().length() == 0) {
                Toast.makeText(getActivity(), "请输入推荐面试单位名称！", Toast.LENGTH_SHORT)
                        .show();
                etRecomCom.requestFocus();
                return false;
            }
            if (etRecomJob.getText().toString().trim().length() == 0) {
                Toast.makeText(getActivity(), "请输入推荐面试岗位名称！", Toast.LENGTH_SHORT)
                        .show();
                etRecomJob.requestFocus();
                return false;
            }
        }

        return true;
    }


}

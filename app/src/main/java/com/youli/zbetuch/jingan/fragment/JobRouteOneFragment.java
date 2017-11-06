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
import android.widget.ArrayAdapter;
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
import com.youli.zbetuch.jingan.entity.CompanyPropertyInfo;
import com.youli.zbetuch.jingan.entity.GraduateInfo;
import com.youli.zbetuch.jingan.entity.IndustryInfo;
import com.youli.zbetuch.jingan.entity.InquirerInfo;
import com.youli.zbetuch.jingan.entity.InvestInfo;
import com.youli.zbetuch.jingan.entity.JinAnStreetInfo;
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
 * 调查走访情况的碎片
 */

public class JobRouteOneFragment extends BaseFragment implements View.OnClickListener{

    private final int SUCCESS_LIST=10001;//条目内容
    private final int SUCCESS_NODATA=10002;
    private final int SUCCESS_INQUIRER=10003;//调查人
    private final int SUCCESS_ADD=10004;//新建和修改
    private final int SUCCESS_DELETE=10005;//删除
    private final int FAIL=10006;

    private GraduateInfo gInfo;

    public static final JobRouteOneFragment newInstance(GraduateInfo gInfo){

        JobRouteOneFragment fragment = new JobRouteOneFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("gInfo",gInfo);
        fragment.setArguments(bundle);

        return fragment;
    }

    private View contentView;
    private Button btnNew;
    private PullToRefreshListView lv;
    private CommonAdapter adapter;
    private AlertDialog newOrModifyDialog;
    private Spinner spInquirer;//调查人
    private Spinner spBaseCase;//人员基本情况
    private Spinner spDetailCase;//人员具体情况
    private EditText etCompanyName;//单位名称
    private EditText etMark;//备注
    private TextView tvDate;//调查走访日期
    private List<InvestInfo> data=new ArrayList<>();
    private List<InquirerInfo> inquirerData=new ArrayList<>();
    private int pageIndex;

    private boolean isDitto=false;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS_LIST:

                    if(pageIndex==0){
                        data.clear();
                    }
                    data.addAll((List<InvestInfo>)msg.obj);
                    LvSetAdapter(data);
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }

                    break;

                case SUCCESS_ADD:
                    if (getActivity() != null) {
                        if(TextUtils.equals(msg.obj+"","True")){
                            EventBus.getDefault().post(new GraduateInfo());
                            Toast.makeText(getActivity(), "保存成功!", Toast.LENGTH_SHORT).show();
                            pageIndex=0;
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

                case SUCCESS_INQUIRER:
                    if (getActivity() != null) {
                        inquirerData.clear();
                        inquirerData.add(new InquirerInfo(-1, "请选择"));
                        inquirerData.addAll((List<InquirerInfo>) msg.obj);
                        spInquirer.setAdapter(new ArrayAdapter<InquirerInfo>(getActivity(), android.R.layout.simple_list_item_1, inquirerData));
                        if(inquirerData.size()>1) {
                            spInquirer.setSelection(1);
                        }
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

        contentView=LayoutInflater.from(getContext()).inflate(R.layout.fragment_job_route_one,container,false);

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

        btnNew= (Button) contentView.findViewById(R.id.btn_job_route_one_new);
        btnNew.setOnClickListener(this);

        lv= (PullToRefreshListView) contentView.findViewById(R.id.lv_job_route_one);
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
                        //http://web.youli.pw:89/Json/Get_JobMarks.aspx?master_id=100&page=0&rows=15
                          String url= MyOkHttpUtils.BaseUrl+"/Json/Get_JobMarks.aspx?master_id="+gInfo.getID()+"&page="+pageIndex+"&rows=15";

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null) {

                            if (response.body() != null) {

                                try {
                                    String resStr=response.body().string();

                                    if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")){

                                        Gson gson=new Gson();
                                        msg.obj = gson.fromJson(resStr, new TypeToken<List<InvestInfo>>() {
                                        }.getType());
                                        msg.what = SUCCESS_LIST;

                                    }else{

                                        msg.what =SUCCESS_NODATA;


                                    }
                                    mHandler.sendMessage(msg);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } else {

                                msg.what = FAIL;
                                mHandler.sendMessage(msg);
                            }
                        }
                    }
                }

        ).start();

    }

    private void LvSetAdapter(final List<InvestInfo> data){

        if(getActivity()!=null){

            if(adapter==null){

                adapter=new CommonAdapter<InvestInfo>(getActivity(),data,R.layout.item_fmt_job_route_one) {
                    @Override
                    public void convert(CommonViewHolder holder, InvestInfo item, final int position) {

                        TextView tvBaseCase=holder.getView(R.id.tv_fmt_job_route_one_base_case);
                        tvBaseCase.setText(item.getBASE_SITUATION());
                        TextView tvDetailCase=holder.getView(R.id.tv_fmt_job_route_one_detail_case);
                        tvDetailCase.setText(item.getDETAIL_SITUATION1());
                        TextView tvDate=holder.getView(R.id.tv_fmt_job_route_one_date);
                        tvDate.setText(MyDateUtils.stringToYMD(item.getVISIT_DATE()));
                        TextView tvCompany=holder.getView(R.id.tv_fmt_job_route_one_company);
                        tvCompany.setText(item.getDETAIL_COMPANY());
                        TextView tvInverster=holder.getView(R.id.tv_fmt_job_route_one_inves);
                        tvInverster.setText(item.getINQUIRER());
                        TextView tvMark=holder.getView(R.id.tv_fmt_job_route_one_mark);
                        tvMark.setText(item.getREMARK());

                        LinearLayout llCompany=holder.getView(R.id.ll_fmt_job_route_one_company);
                        if(TextUtils.equals("",item.getDETAIL_COMPANY())){
                            llCompany.setVisibility(View.GONE);
                        }else{
                            llCompany.setVisibility(View.VISIBLE);
                        }

                        Button btnDelete=holder.getView(R.id.btn_item_fmt_job_route_one_delete);
                        btnDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                showDeleteDialog(position);//删除
                            }
                        });

                        Button btnModify=holder.getView(R.id.btn_item_fmt_job_route_one_modify);
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
            builder.setMessage("您确定删除此调查走访情况吗?");
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

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_job_route_one_new:

                showNewOrModifyDialog("new",-1);//新建

                break;



        }

    }

    private void showNewOrModifyDialog(final String sign, final int position) {

        if (getActivity() != null) {

            getInquirerInfo();//获取调查人信息

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_job_route_one, null, false);
            builder.setView(view);

            newOrModifyDialog = builder.create();

            final Calendar c = Calendar.getInstance();

            tvDate= (TextView) view.findViewById(R.id.tv_dialog_job_route_one_time);//调查走访日期

            tvDate.setText(c.get(Calendar.YEAR)+ "-" +(c.get(Calendar.MONTH)+1) + "-" +c.get(Calendar.DAY_OF_MONTH));

            tvDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener(){


                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            tvDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            if(year>c.get(Calendar.YEAR)||monthOfYear>c.get(Calendar.MONTH)||dayOfMonth>c.get(Calendar.DAY_OF_MONTH)){

                                if(getActivity()==null){
                                    return;
                                }

                                Toast.makeText(getActivity(),"时间必须是当年且小于当前时间!",Toast.LENGTH_SHORT).show();
                                tvDate.setText(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DAY_OF_MONTH));
                            }

                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

           spBaseCase= (Spinner) view.findViewById(R.id.sp_dialog_job_route_one_base_case);//人员基本情况
            if(getActivity()!=null) {
                SpinnerUtils.setSpinner(spBaseCase, R.array.gradeate_baseSituation, getActivity());
            }

           spDetailCase= (Spinner) view.findViewById(R.id.sp_dialog_job_route_one_detail_case);//人员具体情况
            if(getActivity()!=null) {
                SpinnerUtils.setSpinner(spDetailCase, R.array.gradeate_empty, getActivity());
            }

            etCompanyName= (EditText) view.findViewById(R.id.et_dialog_job_route_one_company);//单位名称
            if(TextUtils.equals(etCompanyName.getText().toString().trim(),"")){
                setTextNotUserful(etCompanyName);
            }else{
                setTextUserful(etCompanyName);
            }

            spInquirer= (Spinner) view.findViewById(R.id.sp_dialog_job_route_one_inves);//调查人

            etMark= (EditText) view.findViewById(R.id.et_dialog_job_route_one_mark);//备注

            if(TextUtils.equals(sign,"modify")){
                tvDate.setText(MyDateUtils.stringToYMD(data.get(position).getVISIT_DATE()));
                SpinnerUtils.setSpinnerSelect(spBaseCase, data.get(position).getBASE_SITUATION());
                dCaseSpSelect(data.get(position).getBASE_SITUATION(), spDetailCase, data.get(position).getDETAIL_SITUATION1());//人员具体情况
                etCompanyName.setText(data.get(position).getDETAIL_COMPANY());
                etMark.setText(data.get(position).getREMARK());
            }

            spBaseCase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
                    String value = spBaseCase.getSelectedItem().toString().trim();
                    if(isDitto){
                        isDitto=false;
                        if(value.equals("已就业")){
                            setTextUserful(etCompanyName);
                        }else{
                            setTextNotUserful(etCompanyName);
                        }
                        return;
                    }


                    if(getActivity()!=null) {

                            if (value.equals("请选择")) {
                                SpinnerUtils.setSpinner(spDetailCase, R.array.gradeate_empty, getActivity());
                                setTextNotUserful(etCompanyName);
                            } else if (value.trim().equals("已就业")) {
                                SpinnerUtils.setSpinner(spDetailCase, R.array.gradeate_detailSituation_yjy, getActivity());
                                setTextUserful(etCompanyName);
                            } else if (value.trim().equals("未就业")) {
                                SpinnerUtils.setSpinner(spDetailCase, R.array.gradeate_detailSituation_wjy, getActivity());
                                setTextNotUserful(etCompanyName);
                            } else if (value.trim().equals("暂不要求就业")) {
                                SpinnerUtils.setSpinner(spDetailCase, R.array.gradeate_detailSituation_zbjy, getActivity());
                                setTextNotUserful(etCompanyName);
                            } else if (value.trim().equals("其他")) {
                                SpinnerUtils.setSpinner(spDetailCase, R.array.gradeate_detailSituation_qt, getActivity());
                                setTextNotUserful(etCompanyName);
                            }

                        if(TextUtils.equals(sign, "modify")){
                            dCaseSpSelect(value, spDetailCase, data.get(position).getDETAIL_SITUATION1());//人员具体情况
                        }

                        }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            Button btnDitto= (Button) view.findViewById(R.id.btn_dialog_job_route_one_ditto);//调查结果同上

            if(data.size()!=0&&TextUtils.equals(sign, "new")){
                btnDitto.setVisibility(View.VISIBLE);
            }else{
                btnDitto.setVisibility(View.INVISIBLE);
            }


            btnDitto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  if(data.get(0)!=null) {
                      isDitto = true;
                      etMark.setText(data.get(0).getREMARK());//备注
                      etCompanyName.setText(data.get(0).getDETAIL_COMPANY());//单位名称
                      SpinnerUtils.setSpinnerSelect(spBaseCase, data.get(0).getBASE_SITUATION());//人员基本情况
                      dCaseSpSelect(data.get(0).getBASE_SITUATION(), spDetailCase, data.get(0).getDETAIL_SITUATION1());//人员具体情况
                  }
                    
                }
            });

            Button btnSure= (Button) view.findViewById(R.id.btn_dialog_job_route_one_sure);//确定
            btnSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(checkFrm()){
                        showSaveDialog(sign,position);
                    }

                }
            });

            Button btnCancel= (Button) view.findViewById(R.id.btn_dialog_job_route_one_cancel);//取消
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

    /**
     * 设置文本框有效
     *
     * @param txt
     */
    private void setTextUserful(EditText txt) {
        txt.setEnabled(true);
        txt.setBackgroundResource(R.drawable.shape_ziyuan_detail_et);
    }

    /**
     * 设置文本框无效
     *
     * @param txt
     */
    private void setTextNotUserful(EditText txt) {
        txt.setEnabled(false);
        txt.setBackgroundResource(R.drawable.txtbgnouse);
        txt.setText("");
    }


    private void getInquirerInfo(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                        //http://web.youli.pw:89/Json/Get_COMMITTEE_Staff.aspx?COMMITTEE_ID=176
                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_COMMITTEE_Staff.aspx?COMMITTEE_ID="+gInfo.getCOMMITTEE_ID();

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null) {

                            if (response.body() != null) {

                                try {
                                    String resStr=response.body().string();

                                    if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")){

                                        Gson gson=new Gson();
                                        msg.obj = gson.fromJson(resStr, new TypeToken<List<InquirerInfo>>() {
                                        }.getType());
                                        msg.what = SUCCESS_INQUIRER;

                                    }else{

                                        msg.what =SUCCESS_NODATA;

                                    }
                                    mHandler.sendMessage(msg);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } else {

                                msg.what = FAIL;
                                mHandler.sendMessage(msg);
                            }
                        }
                    }
                }

        ).start();

    }

    /**
     * 页面非空验证
     *
     * @return
     */
    private boolean checkFrm() {

        if (getActivity() == null) {
            return false;
        }

//        if (GradeatePersonInfoActivity.gradeateId == 0) {
//            Toast.makeText(mContext, "人员还未保存，请先保存或选中人员！", Toast.LENGTH_SHORT)
//                    .show();
//            return false;
//        }
            if (spBaseCase.getSelectedItem().toString().trim().equals("请选择")) {
                Toast.makeText(getActivity(), "请选择人员基本情况！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (spDetailCase.getSelectedItem().toString().trim()
                    .equals("请选择")) {
                Toast.makeText(getActivity(), "请选择人员具体情况！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (spBaseCase.getSelectedItem().toString().trim().equals("已就业")
                    && etCompanyName.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "请输入单位名称！", Toast.LENGTH_SHORT).show();
                etCompanyName.requestFocus();
                return false;
            }
            if (spInquirer.getSelectedItemPosition() == 0) {
                Toast.makeText(getActivity(), "请选择调查人！", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }


    private void  showSaveDialog(final String sign, final int position){

        if(getActivity()!=null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("保存信息提示");
            builder.setMessage("您确定保存此走访情况吗?");
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


    //调查走访情况的新建或者修改
    //http://web.youli.pw:89/Json/Set_JobMarks.aspx
    //{BASE_SITUATION=已就业, INQUIRER=admin, DETAIL_SITUATION1=自主创业已领证照, MASTER_ID=3176, ID=0, REMARK=我是备注,
    // DETAIL_COMPANY=哟哟哟, DETAIL_SITUATION2=, VISIT_DATE=2017-10-19}
    private void newOrChangeInfo(String sign, int position){

        final Map<String,String> dataMap=new HashMap<>();
        dataMap.put("BASE_SITUATION",spBaseCase.getSelectedItem().toString().trim());
        dataMap.put("INQUIRER",spInquirer.getSelectedItem().toString().trim());
        dataMap.put("MASTER_ID",gInfo.getID()+"");
        if(TextUtils.equals(sign,"new")) {
            dataMap.put("ID", "0");
        }else if(TextUtils.equals(sign,"modify")) {
            dataMap.put("ID", data.get(position).getID()+"");
        }
        dataMap.put("REMARK",etMark.getText().toString().trim());
        if (spBaseCase.getSelectedItem().toString().trim().equals("已就业")) {
            dataMap.put("DETAIL_COMPANY", etCompanyName.getText().toString().trim());
        } else {
            dataMap.put("DETAIL_COMPANY", "");
        }
        dataMap.put("DETAIL_SITUATION1",spDetailCase.getSelectedItem().toString().trim());
        dataMap.put("DETAIL_SITUATION2","");
        dataMap.put("VISIT_DATE",tvDate.getText().toString().trim());

     new Thread(

             new Runnable() {
                 @Override
                 public void run() {
                     //http://web.youli.pw:89/Json/Set_JobMarks.aspx
                     String url=MyOkHttpUtils.BaseUrl+"/Json/Set_JobMarks.aspx";

                     Response response=MyOkHttpUtils.okHttpPostFormBody(url, (HashMap<String, String>) dataMap);

                     Message msg=Message.obtain();

                     if(response!=null){

                         if(response.body()!=null){

                             try {
                                 String resStr=response.body().string();

                                 msg.what=SUCCESS_ADD;
                                 msg.obj=resStr;
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

    //调查走访情况的删除
    private void deleteInfo(final int p){

        final Map<String,String> map=new HashMap<>();
        map.put("ID",data.get(p).getID()+"");
        map.put("delete", "true");
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        //http://web.youli.pw:89/Json/Set_JobMarks.aspx
                        String url=MyOkHttpUtils.BaseUrl+"/Json/Set_JobMarks.aspx";

                        Response response=MyOkHttpUtils.okHttpPostFormBody(url, (HashMap<String, String>) map);

                        Message msg=Message.obtain();

                        if(response!=null){

                            if(response.body()!=null){

                                try {
                                    String resStr=response.body().string();

                                    msg.what=SUCCESS_DELETE;
                                    msg.arg1=p;
                                    msg.obj=resStr;
                                    mHandler.sendMessage(msg);

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


    private void dCaseSpSelect(String sign,Spinner sp,String value){

        String data [] = new String[0];
        if(TextUtils.equals(sign,"已就业")) {
            data = getResources().getStringArray(R.array.gradeate_detailSituation_yjy);
            SpinnerUtils.setSpinner(spDetailCase, R.array.gradeate_detailSituation_yjy, getActivity());
        }else if(TextUtils.equals(sign,"未就业")) {
            data = getResources().getStringArray(R.array.gradeate_detailSituation_wjy);
            SpinnerUtils.setSpinner(spDetailCase, R.array.gradeate_detailSituation_wjy, getActivity());
        }else if(TextUtils.equals(sign,"暂不要求就业")) {
            data = getResources().getStringArray(R.array.gradeate_detailSituation_zbjy);
            SpinnerUtils.setSpinner(spDetailCase, R.array.gradeate_detailSituation_zbjy, getActivity());
        }else if(TextUtils.equals(sign,"其他")) {
            data = getResources().getStringArray(R.array.gradeate_detailSituation_qt);
            SpinnerUtils.setSpinner(spDetailCase, R.array.gradeate_detailSituation_qt, getActivity());
        }
        for(int i=0;i<data.length;i++){

            if(TextUtils.equals(data[i],value)){

                sp.setSelection(i);

                break;
            }

        }

    }

}

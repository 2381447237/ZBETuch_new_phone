package com.youli.zbetuch.jingan.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.activity.MainLayoutActivity;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.ServiceReInfo;
import com.youli.zbetuch.jingan.entity.ServiceTypeInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Response;

/**
 * Created by ZHengBin on 2017/8/12.
 */

public class ServiceReFramgent extends Fragment {

    private String sfz;



    public ServiceReFramgent(String sfz) {
        this.sfz = sfz;
    }

    public String getSfz() {
        return sfz;
    }

    private final int SUCCESS_TYPE=10000;
    private final int SUCCESS_LIST=10001;
    private final int  SUCCESS_NEW=10002;
    private final int  SUCCESS_DEL=10003;
    private final int  SUCCESS_MODIFY=10004;
    private final int  PROBLEM=10005;

    private View contentview;
    private ListView lv;
    private List<ServiceReInfo> data = new ArrayList<>();
    private Button newCreateBtn;
    private  Spinner sp;
    private EditText markEt;
    private TextView dateTv;
    private String markStr,dateStr;
    private int typeId,delPosition;
    private CommonAdapter adapter;
    private ArrayAdapter<ServiceTypeInfo> spAdapter;
    private List<ServiceTypeInfo> spList=new ArrayList<>();

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS_TYPE:

                    spList.add(new ServiceTypeInfo(0,"请选择",0));
                    spList.addAll((List<ServiceTypeInfo>)msg.obj);

                    if(spAdapter==null) {
                        spAdapter = new ArrayAdapter<ServiceTypeInfo>(getActivity(), android.R.layout.simple_spinner_item, spList);
                        sp.setAdapter(spAdapter);
                    }

                    if(msg.arg1==222){

                        for(int i=0;i<spList.size();i++){

                            if(spList.get(i).getID()==msg.arg2){

                                sp.setSelection(i);
                                break;
                            }

                        }

                    }

                    break;

                case SUCCESS_LIST:
                    data.clear();
                    data.addAll((List<ServiceReInfo>)msg.obj);
                    lvSetAdapter(data);

                    break;

                case SUCCESS_NEW:

                    if(TextUtils.equals("True",(String)msg.obj)){

                        Toast.makeText(getActivity(),"添加成功",Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(getActivity(),"添加失败",Toast.LENGTH_SHORT).show();
                    }

                    getServiceInfo();

                    break;

                case SUCCESS_DEL:

                    if(TextUtils.equals("True",(String)msg.obj)){
                        data.remove(delPosition);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(getActivity(),"删除失败",Toast.LENGTH_SHORT).show();
                    }

                    break;

                case SUCCESS_MODIFY:

                    if(TextUtils.equals("True",(String)msg.obj)){
                        getServiceInfo();
                        Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(getActivity(),"修改失败",Toast.LENGTH_SHORT).show();
                    }

                    break;


                case PROBLEM:

                    Toast.makeText(getActivity(),"网络不给力",Toast.LENGTH_SHORT).show();

                    break;
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentview = LayoutInflater.from(getContext()).inflate(R.layout.framgment_service_record, container, false);

        if(getSfz()!=null) {
            initView(contentview);
        }
        return contentview;
    }

    private void initView(View view) {

        lv = (ListView) view.findViewById(R.id.lv_fmt_service_record);
        newCreateBtn = (Button) view.findViewById(R.id.btn_service_record_new_create);
        newCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showNewDialog(111,null,0,null,0);

            }
        });

        getServiceInfo();

    }

   //服务记录列表
    private void getServiceInfo(){

       // http://web.youli.pw:89/Json/Get_Sfz_Service.aspx?sfz=310108198004026642
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url=MyOkHttpUtils.BaseUrl+"/Json/Get_Sfz_Service.aspx?sfz="+getSfz();

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String resStr=response.body().string();

                                if(resStr!=null){

                                    Gson gson=new Gson();
                                    msg.what=SUCCESS_LIST;
                                    msg.obj=gson.fromJson(resStr,new TypeToken<List<ServiceReInfo>>(){}.getType());
                                }else{
                                    msg.what=PROBLEM;
                                }
                                mHandler.sendMessage(msg);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }else{

                           msg.what=PROBLEM;
                            mHandler.sendMessage(msg);
                        }
                    }
                }

        ).start();

    }


    private void lvSetAdapter(List<ServiceReInfo> list){

        if(adapter==null){
                    adapter = new CommonAdapter<ServiceReInfo>(getActivity(), list, R.layout.item_fmt_service_record_lv) {
            @Override
            public void convert(CommonViewHolder holder, final ServiceReInfo item, final int position) {

                TextView name = holder.getView(R.id.tv_item_fmt_service_record_name);
                name.setText(item.getSTAFF_NAME());
                TextView date = holder.getView(R.id.tv_item_fmt_service_record_date);
                date.setText(item.getSERVICE_TIME().split("T")[0]);
                TextView content = holder.getView(R.id.tv_item_fmt_service_record_content);
                content.setText(item.getTYPE_NAME());
                TextView remarks = holder.getView(R.id.tv_item_fmt_service_record_remarks);
                remarks.setText(item.getMARK());

                Button btnModify = holder.getView(R.id.btn_item_fmt_service_record_modify);
                btnModify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showNewDialog(222,item.getSERVICE_TIME().split("T")[0],item.getTYPE(),item.getMARK(),item.getID());
                    }
                });

                Button btnDelete = holder.getView(R.id.btn_item_fmt_service_record_delete);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        delPosition=position;

                        showDeleteDialog(item.getID(),item.getSTAFF(),"修改信息提示","您确定删除此项服务信息吗？","delete",null);


                    }
                });
            }
        };

        lv.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }

    }


    private void showDeleteDialog(final int id, final int staffId, String title, String message, final String sign, final AlertDialog alertDialog) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("取消", null);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(TextUtils.equals(sign,"delete")){//删除

                    if (staffId!=MainLayoutActivity.adminId) {
                        Toast.makeText(getContext(), "没有权限删除",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        //删除信息
                        delSubmit(id);
                    }
               }else if(TextUtils.equals(sign,"modify")){//修改

                    alertDialog.dismiss();

                    if (staffId!=MainLayoutActivity.adminId) {
                        Toast.makeText(getContext(), "没有权限修改",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        newOrModifySubmit("modify",id);

                    }


                }else if(TextUtils.equals(sign,"new")){//新增
                    alertDialog.dismiss();
                    newOrModifySubmit("new",0);
                }

                adapter.notifyDataSetChanged();
            }
        });

        builder.show();
    }

    private void showNewDialog(final int sign, String date, int type, String mark, final int itemId){
    //注意这里哈sign等于111,表示新增；sign等于222,表示修改
   //======================================================
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fmt_service_record_new,null,false);
        builder.setView(view);
        final AlertDialog dialog=builder.create();

         sp= (Spinner) view.findViewById(R.id.sp_dialog_fmt_service_record);

         sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              typeId=position;

             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

        markEt= (EditText) view.findViewById(R.id.et_dialog_fmt_service_record_mark);

        dateTv= (TextView) view.findViewById(R.id.tv_dialog_fmt_service_record_date);

        final Calendar c = Calendar.getInstance();


        if(sign==222){
            markEt.setText(mark);
           dateTv.setText(date);
        }else if(sign==111){
            dateTv.setText(c.get(Calendar.YEAR)+ "-" +(c.get(Calendar.MONTH)+1) + "-" +c.get(Calendar.DAY_OF_MONTH));
        }


        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener(){


                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateTv.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                        if(year>c.get(Calendar.YEAR)||monthOfYear>c.get(Calendar.MONTH)||dayOfMonth>c.get(Calendar.DAY_OF_MONTH)){
                            Toast.makeText(getActivity(),"时间必须是当年且小于当前时间!",Toast.LENGTH_SHORT).show();
                            dateTv.setText(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DAY_OF_MONTH));
                        }

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        if(spList!=null){
            if(spList.size()==0) {
                getServiceType(sign,type);
            }else{

                spAdapter = new ArrayAdapter<ServiceTypeInfo>(getActivity(), android.R.layout.simple_spinner_item, spList);
                sp.setAdapter(spAdapter);
                if(sign==222){
                    for(int i=0;i<spList.size();i++){

                        if(spList.get(i).getID()==type){

                            sp.setSelection(i);

                            break;
                        }
                    }
                }
            }
        }


        Button btnSure= (Button) view.findViewById(R.id.btn_dialog_fmt_service_record_sure);
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sp.getSelectedItemPosition()==0){
                    Toast.makeText(getActivity(), "请选择服务内容", Toast.LENGTH_SHORT).show();
                }else{
                    if(sign==111){
                        showDeleteDialog(0,MainLayoutActivity.adminId,"保存信息提示","您确定保存此服务选项吗？","new",dialog);
                    }else if(sign==222){
                        showDeleteDialog(itemId,MainLayoutActivity.adminId,"保存信息提示","您确定保存此服务选项吗？","modify",dialog);
                    }


                }
            }
        });

        Button btnCancel= (Button) view.findViewById(R.id.btn_dialog_fmt_service_record_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        dialog.show();
        dialog.setCanceledOnTouchOutside(false);


    }

    //服务记录的新增
    private void newOrModifySubmit(final String sign, final int itemID){

        Log.e("2017/8/18","===itemID==="+itemID);

//        服务记录的新增
//        http://web.youli.pw:89/Json/Set_Sfz_Service.aspx?ID=0&SFZ=310108198004026642
//        &STAFF=2&SERVICE_TIME=2017-8-18&TYPE=1&MARK=测试

        markStr=markEt.getText().toString().trim();
        dateStr=dateTv.getText().toString().trim();


         new Thread(

                 new Runnable() {
                     @Override
                     public void run() {

                         String urlNew=MyOkHttpUtils.BaseUrl+"/Json/Set_Sfz_Service.aspx";

                        Response response=MyOkHttpUtils.okHttpPost(urlNew,
                                itemID+"",getSfz(),MainLayoutActivity.adminId+"",dateStr,typeId+"",markStr);
                         Message msg=Message.obtain();

                         if(response!=null){

                             try {
                                 String resStr=response.body().string();

                                 if(resStr!=null){
                                    if(TextUtils.equals("new",sign)){
                                        msg.what=SUCCESS_NEW;
                                    }else if(TextUtils.equals("modify",sign)){
                                         msg.what=SUCCESS_MODIFY;
                                    }

                                     msg.obj=resStr;
                                     mHandler.sendMessage(msg);

                                 }else{

                                     msg.what=PROBLEM;
                                     mHandler.sendMessage(msg);
                                 }

                             } catch (IOException e) {
                                 e.printStackTrace();
                             }

                         }else{

                            msg.what=PROBLEM;
                             mHandler.sendMessage(msg);

                         }


                     }
                 }

         ).start();

    }

    //服务记录的删除
    private void delSubmit(final int id){

        //http://web.youli.pw:89/Json/Set_Sfz_Service.aspx?ID=52&del=true

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String urlDel=MyOkHttpUtils.BaseUrl+"/Json/Set_Sfz_Service.aspx";
                        Response response=MyOkHttpUtils.okHttpPostDelServiceRe(urlDel,id+"","true");

                        Message msg=Message.obtain();
                        if(response!=null){

                            try {
                                String responseStr=response.body().string();
                                msg.what=SUCCESS_DEL;
                                msg.obj=responseStr;
                                mHandler.sendMessage(msg);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }else{
                            msg.what=PROBLEM;
                            mHandler.sendMessage(msg);
                        }


                    }
                }

        ).start();



    }

    //获得服务记录的内容选择
    private void getServiceType(final int sign, final int typeId){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url=MyOkHttpUtils.BaseUrl+"/Json/Get_Service_Type.aspx";

                        Response response= MyOkHttpUtils.okHttpGet(url);
                        Message msg=Message.obtain();
                        if(response!=null){

                            try {
                                String responseStr=response.body().string();
                                Gson gson=new Gson();

                                msg.what=SUCCESS_TYPE;
                                msg.obj=gson.fromJson(responseStr,new TypeToken<List<ServiceTypeInfo>>(){}.getType());
                                msg.arg1=sign;
                                msg.arg2=typeId;
                                mHandler.sendMessage(msg);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }else{
                            msg.what=PROBLEM;
                            mHandler.sendMessage(msg);
                        }

                    }
                }

        ).start();

    }

}

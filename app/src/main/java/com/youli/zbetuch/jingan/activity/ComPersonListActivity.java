package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.ComListInfo;
import com.youli.zbetuch.jingan.entity.ComNaireAnswerInfo;
import com.youli.zbetuch.jingan.entity.ComNaireInfo;
import com.youli.zbetuch.jingan.entity.ComPersonInfo;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/11/23.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 企业人员列表
 */

public class ComPersonListActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext=this;

    private final int SUCCESS_LIST=10000;
    private final int SUCCESS_NODATA=10001;
    //private final int SUCCESS_NEW=10002;
    private final int SUCCESS_EDIT=10003;
    private final int SUCCESS_PNAIRE=10004;
    private final int FAIL=10005;

    private List<ComNaireAnswerInfo> aInfo=new ArrayList<>();//答案的集合

    private ComListInfo comInfo;
    private ComNaireInfo naireInfo;
    private List<ComPersonInfo> data=new ArrayList<>();
    private ListView lv;
    private CommonAdapter adapter;

    private ComPersonInfo eBus;//eventBus专用参数

    private TextView tvNum;
    private Button naireBtn;
    AlertDialog dialog;
    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case SUCCESS_LIST:

                   // if (pageIndex == 0) {
                        data.clear();
                    //}

                    data.addAll((List<ComPersonInfo>) msg.obj);
                    if(data!=null&&data.size()>0) {
                        tvNum.setText("共" + data.size() + "人");
                    }
                    LvSetAdapter(data);

                    break;

                case SUCCESS_NODATA:
                    Toast.makeText(mContext, "暂无数据", Toast.LENGTH_SHORT).show();
                    break;

                case FAIL:
                    Toast.makeText(mContext, "网络不给力", Toast.LENGTH_SHORT).show();
                    break;

                case SUCCESS_EDIT://企业人员信息的修改
                    if(dialog!=null) {
                        dialog.dismiss();
                    }
                    getDates(eBus);

                    Toast.makeText(mContext, "修改成功!", Toast.LENGTH_SHORT).show();
                    break;

//                case SUCCESS_NEW://企业人员信息的创建
//
//                    if(dialog!=null) {
//                        dialog.dismiss();
//                    }
//                    getDates();
//                    EventBus.getDefault().post(new ComListInfo());//通知企业列表界面刷新
//                    //查看答题情况
//                    Intent i=new Intent(mContext,ComNaireDetailActivity.class);
//                    i.putExtra("pName",msg.obj+"");//个人姓名
//                    i.putExtra("wenjuan",naireInfo);
//                    startActivity(i);
//
//                    break;

                case SUCCESS_PNAIRE://获取人员问卷信息:

                    aInfo.clear();
                    aInfo.addAll((List<ComNaireAnswerInfo>)msg.obj);

                    Intent intent=new Intent(mContext,ComNaireDetailActivity.class);
                    intent.putExtra("pInfo",data.get(msg.arg1));//个人信息
                    intent.putExtra("wenjuan",naireInfo);
                    intent.putExtra("comInfo",comInfo);//企业信息
                    intent.putExtra("aInfo",(Serializable)aInfo);//答案信息
                    startActivity(intent);

                    break;

            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_person_list);

        comInfo=(ComListInfo) getIntent().getSerializableExtra("comInfo");//企业信息
        naireInfo=(ComNaireInfo) getIntent().getSerializableExtra("wenjuan");//问卷信息

        //注册事件
        EventBus.getDefault().register(this);

        initViews();
    }

    private void initViews(){

        tvNum= (TextView) findViewById(R.id.tv_com_person_list_num);//企业人员的人数


        lv= (ListView) findViewById(R.id.lv_com_person_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //查看答题情况

                getPersonNaireInfo(position);

            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                showNewOrModifyDialog("modify",position);//修改

                return true;
            }
        });
        naireBtn= (Button) findViewById(R.id.btn_com_person_list_naire);
        naireBtn.setOnClickListener(this);
       getDates(eBus);



    }

//    获取人员问卷信息:
//    http://web.youli.pw:89/Json/Get_Qa_Company_Receiv.aspx?Personnel_id=1
    private void  getPersonNaireInfo(final int position){


        new Thread(new Runnable() {
            @Override
            public void run() {

                //    http://web.youli.pw:89/Json/Get_Qa_Company_Receiv.aspx?Personnel_id=1

                String url=MyOkHttpUtils.BaseUrl+"/Json/Get_Qa_Company_Receiv.aspx?Personnel_id="+data.get(position).getID();

                Log.e("2017/11/24","答案的url="+url);

                Response response=MyOkHttpUtils.okHttpGet(url);

                Message msg=Message.obtain();

                if(response!=null){

                    if(response.body()!=null){

                        try {
                            String resStr=response.body().string();

                            if(resStr!=null){

                                msg.what=SUCCESS_PNAIRE;
                                msg.arg1=position;

                                Gson gson=new Gson();
                                msg.obj = gson.fromJson(resStr, new TypeToken<List<ComNaireAnswerInfo>>() {
                                }.getType());
                            }else{
                                msg.what=FAIL;
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
        }).start();


    };

      private void showNewOrModifyDialog(final String mark, final int position){

          final AlertDialog.Builder builder = new AlertDialog.Builder(this);
          View view = LayoutInflater.from(this).inflate(R.layout.dialog_com_person_list, null);
          builder.setView(view);

          dialog = builder.create();
          dialog.setCanceledOnTouchOutside(false);
          dialog.show();

          final EditText etName= (EditText) view.findViewById(R.id.et_dialog_com_person_list_name);//姓名
          final EditText etSfz= (EditText) view.findViewById(R.id.et_dialog_com_person_list_sfz);//身份证
          final EditText etMark= (EditText) view.findViewById(R.id.et_dialog_com_person_list_mark);//备注
          int pId=0;
          if(TextUtils.equals(mark,"modify")){
              etName.setText(data.get(position).getNAME());
              etSfz.setText(data.get(position).getSFZ());
              etMark.setText(data.get(position).getMARK());
              pId=data.get(position).getID();//个人ID
          }

          Button btnSure = (Button) view.findViewById(R.id.btn_dialog_com_person_list_sure);//确定

          if(TextUtils.equals(mark,"modify")) {
              btnSure.setText("确定");
          }else if(TextUtils.equals(mark,"new")){
              btnSure.setText("开始答题");
          }


          final int finalPId = pId;
          btnSure.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  if(checkEt(etName, "请输入姓名!")){
                      return;
                  };

                  if(checkEt(etSfz, "请输入身份证!")){
                      return;
                  };

                  if(etSfz.getText().toString().length()!=18){
                      Toast.makeText(mContext, "身份证格式不对!", Toast.LENGTH_SHORT).show();
                      etSfz.requestFocus();
                      return;
                  }

                  String nameStr=etName.getText().toString();
                  String sfzStr=etSfz.getText().toString();
                  String markStr=etMark.getText().toString();

                  if(TextUtils.equals(mark,"new")){//新建人员

                      newPersonInfo(nameStr,sfzStr,markStr);

                  }else if(TextUtils.equals(mark,"modify")){//修改人员信息
                      modifyComInfo(mark, comInfo.getID(), finalPId,nameStr,sfzStr,markStr);
                  }



              }
          });

          Button btnCancel = (Button) view.findViewById(R.id.btn_dialog_com_person_list_cancel);
          btnCancel.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  dialog.dismiss();

              }
          });

      }

    //新建人员
    private void newPersonInfo(String nameStr,String sfzStr,String markStr){

                            if(dialog!=null) {
                        dialog.dismiss();
                    }
        ComPersonInfo pInfo=new ComPersonInfo();
        pInfo.setID(-1);
        pInfo.setNAME(nameStr);
        pInfo.setSFZ(sfzStr);
        pInfo.setMARK(markStr);


                    Intent i=new Intent(mContext,ComNaireDetailActivity.class);
                    i.putExtra("pInfo",pInfo);//个人信息
                    i.putExtra("wenjuan",naireInfo);//问卷信息
                    i.putExtra("comInfo",comInfo);//企业信息
                    startActivity(i);


    }

    //修改人员信息
    private void modifyComInfo(final String mark, final int cId, final int pId, final String nameStr, final String sfzStr, final String markStr){

        new Thread(new Runnable() {
            @Override
            public void run() {

//                修改企业人员信息：
//                http://web.youli.pw:89/Json/Set_Qa_Company_Personnel.aspx?COMPANY_ID=1&ID=1&MARK=备注&NAME=姓名&SFZ=身份证

//                新建企业人员信息：
//                http://web.youli.pw:89/Json/Set_Qa_Company_Personnel.aspx?COMPANY_ID=1&MARK=备注&NAME=姓名&SFZ=身份证

                String url = null;
                if(TextUtils.equals(mark,"modify")) {
                    url = MyOkHttpUtils.BaseUrl + "/Json/Set_Qa_Company_Personnel.aspx?COMPANY_ID=" + cId + "&ID=" + pId + "&MARK=" + markStr + "&NAME=" + nameStr + "&SFZ=" + sfzStr;
//                }else if(TextUtils.equals(mark,"new")){
//                    url = MyOkHttpUtils.BaseUrl + "/Json/Set_Qa_Company_Personnel.aspx?COMPANY_ID=" + cId  + "&MARK=" + markStr + "&NAME=" + nameStr + "&SFZ=" + sfzStr;
                }

                Response response=MyOkHttpUtils.okHttpGet(url);

                Message msg = Message.obtain();

                if (response != null) {

                    if (response.body() != null) {

                        try {
                            String resStr = response.body().string();


 //                           if(!resStr.contains("<!DOCTYPE html>")){
//                                if(TextUtils.equals(mark,"new")) {
//                                    msg.what = SUCCESS_NEW;//新建企业人员
//                                    msg.obj=nameStr;
//                                    Log.e("2017/11/22", "新建企业人员==" + resStr);
//                                }

                                if(TextUtils.equals(resStr,"True")&&(TextUtils.equals(mark,"modify"))){
                                    msg.what=SUCCESS_EDIT;//修改企业人员
                              //  }

                            }else{

                                msg.what = FAIL;

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        msg.what = FAIL;
                    }

                } else {

                    msg.what = FAIL;

                }

                mHandler.sendMessage(msg);

            }
        }).start();

    }

    //这里我们的ThreadMode设置为MAIN，事件的处理会在UI线程中执行，用ListView来展示收到的事件消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDates(ComPersonInfo info){

        new Thread(new Runnable() {
            @Override
            public void run() {
                //http://web.youli.pw:89/Json/Get_Qa_Company_Personnel.aspx?Company_id=1
                String url= MyOkHttpUtils.BaseUrl+"/Json/Get_Qa_Company_Personnel.aspx?Company_id="+comInfo.getID();

                Response response=MyOkHttpUtils.okHttpGet(url);

                Message msg = Message.obtain();

                if (response != null) {

                    if (response.body() != null) {

                        try {
                            String resStr = response.body().string();

                            if (!TextUtils.equals(resStr, "[]")) {

                                Gson gson = new Gson();
                                msg.obj = gson.fromJson(resStr, new TypeToken<List<ComPersonInfo>>() {
                                }.getType());
                                msg.what = SUCCESS_LIST;
                            } else {
                                msg.what = SUCCESS_NODATA;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        msg.what = FAIL;
                    }

                } else {

                    msg.what = FAIL;

                }

                mHandler.sendMessage(msg);

            }
        }).start();

    }


    private void LvSetAdapter(List<ComPersonInfo> list) {

        if (adapter == null) {

            adapter = new CommonAdapter<ComPersonInfo>(mContext, data, R.layout.item_com_peson_list) {
                @Override
                public void convert(CommonViewHolder holder, ComPersonInfo item, int position) {

                    TextView tvNo = holder.getView(R.id.tv_item_com_person_list_no);//编号
                    tvNo.setText((position + 1) + "");

                    TextView tvName = holder.getView(R.id.tv_item_com_person_list_name);//姓名
                    tvName.setText(item.getNAME());
                    TextView tvSfz = holder.getView(R.id.tv_item_com_person_list_sfz);//身份证
                    tvSfz.setText(item.getSFZ());
                    TextView tvMark = holder.getView(R.id.tv_item_com_person_list_mark);//备注
                    tvMark.setText(item.getMARK());

                    LinearLayout ll = holder.getView(R.id.ll_item_com_person_list);

                    if (position % 2 == 0) {
                        ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item1);
                    } else {
                        ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item2);
                    }

                }
            };

            lv.setAdapter(adapter);
        } else {

            adapter.notifyDataSetChanged();

        }
//        if (lv.isRefreshing()) {
//            lv.onRefreshComplete();
//        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_com_person_list_naire:

               // Toast.makeText(mContext, "开始答题", Toast.LENGTH_SHORT).show();
                showNewOrModifyDialog("new",-1);//创建
                break;


        }

    }

    //新建界面的输入非空验证
    private boolean checkEt(EditText et, String str) {


        if (TextUtils.equals(et.getText().toString(), "")) {
            Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
            et.requestFocus();
            return true;
        } else {
            return false;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

}

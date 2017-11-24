package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.ComListInfo;
import com.youli.zbetuch.jingan.entity.ComNaireInfo;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.utils.GetManifestInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/11/22.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 * <p>
 * 企业列表
 */

public class ComListActivity extends BaseActivity implements View.OnClickListener {

    private Context mContext = this;

    private final int SUCCESS_LIST = 10001;
    private final int SUCCESS_NEW=10002;
    private final int SUCCESS_EDIT=10003;
    private final int NODATA = 10004;
    private final int FAIL = 10005;

    private ComNaireInfo naireInfo;
    private List<ComListInfo> data = new ArrayList<>();
    private PullToRefreshListView lv;
    private CommonAdapter adapter;

    private int pageIndex;
    private TextView tvNum;
    private Button newBtn;
    AlertDialog dialog;

    private ComListInfo eBus;//eventBus专用参数

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case SUCCESS_LIST:
                    if (pageIndex == 0) {
                        data.clear();
                    }

                    data.addAll((List<ComListInfo>) msg.obj);
                    if(data!=null&&data.size()>0) {
                        tvNum.setText("共" + data.get(0).getRecordCount() + "家");
                    }
                    LvSetAdapter(data);

                    break;

                case NODATA:
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }
                    break;

                case FAIL:
                    Toast.makeText(mContext, "网络不给力", Toast.LENGTH_SHORT).show();
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }
                    break;

                case SUCCESS_NEW://新建企业

                    if(dialog!=null) {
                        dialog.dismiss();
                    }
                    pageIndex=0;
                    getData(eBus);

                    Toast.makeText(mContext, "创建企业成功!", Toast.LENGTH_SHORT).show();
                    break;

                case SUCCESS_EDIT://修改企业
                    if(dialog!=null) {
                        dialog.dismiss();
                    }
                    getData(eBus);

                    Toast.makeText(mContext, "修改成功!", Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_list);

        naireInfo = (ComNaireInfo) getIntent().getSerializableExtra("NaireInfo");

        //注册事件
        EventBus.getDefault().register(this);

        initViews();
    }

    private void initViews() {

        tvNum= (TextView) findViewById(R.id.tv_com_list_num);
        newBtn = (Button) findViewById(R.id.btn_com_list_new);
        newBtn.setOnClickListener(this);
        lv = (PullToRefreshListView) findViewById(R.id.lv_com_list);

        lv.setMode(PullToRefreshBase.Mode.BOTH);

        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                //Toast.makeText(mContext,"刷新",Toast.LENGTH_SHORT).show();
                pageIndex = 0;
                getData(eBus);
            }


            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //  Toast.makeText(mContext,"加载更多",Toast.LENGTH_SHORT).show();
                pageIndex++;
                getData(eBus);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(mContext,ComPersonListActivity.class);
                intent.putExtra("comInfo",data.get(position-1));
                intent.putExtra("wenjuan",naireInfo);
                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                showNewOrModifyDialog("modify",position);//修改

                return true;
            }
        });

        getData(eBus);

    }

    //这里我们的ThreadMode设置为MAIN，事件的处理会在UI线程中执行，用ListView来展示收到的事件消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(ComListInfo eventBus) {
        // http://web.youli.pw:89/Json/Get_Qa_Company.aspx?master_id=1&page=0&rows=15

        new Thread(new Runnable() {
            @Override
            public void run() {

                String url = MyOkHttpUtils.BaseUrl + "/Json/Get_Qa_Company.aspx?master_id=" + naireInfo.getDetils().get(0).getMASTER_ID() + "&page=" + pageIndex + "&rows=15";

                Response response = MyOkHttpUtils.okHttpGet(url);

                Message msg = Message.obtain();

                if (response != null) {

                    if (response.body() != null) {

                        try {
                            String resStr = response.body().string();
                            Log.e("2017/11/22", "url==" + url);
                            Log.e("2017/11/22", "结果==" + resStr);
                            if (!TextUtils.equals(resStr, "[]")) {

                                Gson gson = new Gson();
                                msg.obj = gson.fromJson(resStr, new TypeToken<List<ComListInfo>>() {
                                }.getType());
                                msg.what = SUCCESS_LIST;
                            } else {
                                msg.what = NODATA;
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

    private void LvSetAdapter(List<ComListInfo> list) {

        if (adapter == null) {

            adapter = new CommonAdapter<ComListInfo>(mContext, data, R.layout.item_com_list) {
                @Override
                public void convert(CommonViewHolder holder, ComListInfo item, int position) {

                    TextView tvNo = holder.getView(R.id.tv_item_com_list_no);//编号
                    tvNo.setText((position + 1) + "");

                    TextView tvName = holder.getView(R.id.tv_item_com_list_name);//企业名称
                    tvName.setText(item.getCOMPANY_NAME());
                    TextView tvHy = holder.getView(R.id.tv_item_com_list_industry);//所属行业
                    tvHy.setText(item.getSSHY());
                    TextView tvYg = holder.getView(R.id.tv_item_com_list_yg_num);//员工总数
                    tvYg.setText(item.getYGZS() + "");
                    TextView tvGg = holder.getView(R.id.tv_item_com_list_gg_num);//高管人数
                    tvGg.setText(item.getGGRS() + "");
                    TextView tvZc = holder.getView(R.id.tv_item_com_list_zc_num);//中层人数
                    tvZc.setText(item.getZCRS() + "");
                    TextView tvWjs = holder.getView(R.id.tv_item_com_list_naire_num);//问卷数
                    tvWjs.setText(item.getWJS() + "");
                    LinearLayout ll = holder.getView(R.id.ll_item_com_list);

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
        if (lv.isRefreshing()) {
            lv.onRefreshComplete();
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_com_list_new:

                showNewOrModifyDialog("new",-1);
                break;

        }

    }

    //新建企业
    private void showNewOrModifyDialog(final String mrak, final int position) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_com_list_new, null);
        builder.setView(view);

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        final EditText etName = (EditText) view.findViewById(R.id.et_dialog_com_list_name);//单位名称
        final EditText etCode = (EditText) view.findViewById(R.id.et_dialog_com_list_code);//组织机构代码
        final EditText etYgNum = (EditText) view.findViewById(R.id.et_dialog_com_list_yg_num);//员工总数
        final EditText etGgNum = (EditText) view.findViewById(R.id.et_dialog_com_list_gg_num);//高管人数
        final EditText etZcNum = (EditText) view.findViewById(R.id.et_dialog_com_list_zc_num);//中层人数

        final RadioButton rbOne = (RadioButton) view.findViewById(R.id.rb_dialog_com_list_one);
        final RadioButton rbTwo = (RadioButton) view.findViewById(R.id.rb_dialog_com_list_two);
        int comId=0;//企业ID
        if(TextUtils.equals(mrak,"modify")){
            etName.setText(data.get(position-1).getCOMPANY_NAME());
            etCode.setText(data.get(position-1).getCOMPANY_NO());
            etYgNum.setText(data.get(position-1).getYGZS()+"");
            etGgNum.setText(data.get(position-1).getGGRS()+"");
            etZcNum.setText(data.get(position-1).getZCRS()+"");
            if(TextUtils.equals(data.get(position-1).getSSHY(),rbOne.getText().toString())){
                rbOne.setChecked(true);
            }else if(TextUtils.equals(data.get(position-1).getSSHY(),rbTwo.getText().toString())){
                rbTwo.setChecked(true);
            }
            comId =data.get(position-1).getID();//企业ID;
        }

        Button btnSure = (Button) view.findViewById(R.id.btn_dialog_com_list_sure);
        final int finalComId = comId;
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkEt(etName, "请输入单位名称!")){
                    return;
                };

                if(!rbOne.isChecked()&&!rbTwo.isChecked()){
                    Toast.makeText(mContext,"请选择所属行业",Toast.LENGTH_SHORT).show();
                    return;
                }

//                if(checkEt(etCode, "请输入组织机构代码!")){
//                    return;
//                };

                if(checkEt(etYgNum, "请输入企业员工总数!")){
                    return;
                };
                if(checkEt(etGgNum, "请输入企业高管人数!")){
                    return;
                };
                if(checkEt(etZcNum, "请输入企业中层人数!")){
                    return;
                };

                String nameStr=etName.getText().toString();
                String hyStr = null;
                                if(rbOne.isChecked()){
                                    hyStr=rbOne.getText().toString();
                }else if(rbTwo.isChecked()){
                                    hyStr=rbTwo.getText().toString();
                }
                String codeStr=etCode.getText().toString();
                String ygStr=etYgNum.getText().toString();
                String ggStr=etGgNum.getText().toString();
                String zcStr=etZcNum.getText().toString();



                newOrModifyComInfo(mrak, finalComId,nameStr,hyStr,codeStr,ygStr,ggStr,zcStr);
            }
        });

        Button btnCancel = (Button) view.findViewById(R.id.btn_dialog_com_list_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


    }

    //新建和修改企业信息
    private void newOrModifyComInfo(final String mrak, final int comId, final String nameStr, final String hyStr, final String codeStr, final String ygStr, final String ggStr, final String zcStr){

      new Thread(new Runnable() {
          @Override
          public void run() {
       //http://web.youli.pw:89/Json/Set_Qa_Company.aspx?MASTER_ID=1&COMPANY_NAME=企业名称&GGRS=企业高管人数&SSHY=所属行业:商贸,文创&YGZS=企业员工总数&ZCRS=企业中层人数&COMPANY_NO=组织机构代码/社会统一信用代码
             // 修改企业信息:
              //http://web.youli.pw:89/Json/Set_Qa_Company.aspx?MASTER_ID=1&ID=19&COMPANY_NAME=3&GGRS=10&SSHY=商贸&YGZS=100&ZCRS=20&COMPANY_NO=1000
              String url = null;
              if(TextUtils.equals(mrak,"new")) {
                  url = MyOkHttpUtils.BaseUrl + "/Json/Set_Qa_Company.aspx?MASTER_ID=" + naireInfo.getID() + "&COMPANY_NAME=" + nameStr + "&GGRS=" + ggStr + "&SSHY=" + hyStr + "&YGZS=" + ygStr + "&ZCRS=" + zcStr + "&COMPANY_NO=" + codeStr;
              }else if(TextUtils.equals(mrak,"modify")){
                  url = MyOkHttpUtils.BaseUrl + "/Json/Set_Qa_Company.aspx?MASTER_ID=" + naireInfo.getID() + "&ID="+comId+"&COMPANY_NAME=" + nameStr + "&GGRS=" + ggStr + "&SSHY=" + hyStr + "&YGZS=" + ygStr + "&ZCRS=" + zcStr + "&COMPANY_NO=" + codeStr;
              }
              Log.e("2017/11/22","新建url="+url);

              Response response=MyOkHttpUtils.okHttpGet(url);

              Message msg = Message.obtain();

              if (response != null) {

                  if (response.body() != null) {

                      try {
                          String resStr = response.body().string();
                          Log.e("2017/11/22", "结果==" + resStr);

                          if(!resStr.contains("<!DOCTYPE html>")){
                              if(TextUtils.equals(mrak,"new")) {
                                  msg.what = SUCCESS_NEW;//新建企业
                              }

                              if(TextUtils.equals(resStr,"True")&&(TextUtils.equals(mrak,"modify"))){
                                  msg.what=SUCCESS_EDIT;//修改企业
                              }

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

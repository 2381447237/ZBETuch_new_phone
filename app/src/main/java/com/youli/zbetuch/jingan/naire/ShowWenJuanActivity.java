package com.youli.zbetuch.jingan.naire;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.activity.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: zhengbin on 2017/10/24.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class ShowWenJuanActivity extends BaseActivity implements IActivity{

    private Context mContext=this;
    private PullToRefreshListView lv;
    private List<WenJuanType> wentJuanTypes = new ArrayList<WenJuanType>();
    public static List<WenJuanType> lishiJuanTypes = new ArrayList<WenJuanType>();
    private WenTypeAdapter typeAdapter;
    private TextView lblNum;
    private int index = 0;
    private Context context = this;
    public static final int REFRESH_INFO = 0;

   // private ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wenjuan_info);

        // 启动服务
        Intent service = new Intent();
        service.setClass(mContext,MainService.class);
        startService(service);
        Intent service2 = new Intent();
        service2.setClass(mContext,PersonService.class);
        startService(service2);


        init();
        initviews();
       // showDialog();
        getPageInfo(index);
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        PersonService.addActivity(this);
    }

    private void initviews() {
        lv= (PullToRefreshListView) findViewById(R.id.lv_question_naire);

        lv.setMode(PullToRefreshBase.Mode.BOTH);

        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                index=0;
                getPageInfo(index);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                index++;
                getPageInfo(index);

            }
        });
        typeAdapter = new WenTypeAdapter(context, wentJuanTypes);
        lv.setAdapter(typeAdapter);

        lv.setOnItemClickListener(new MyOnItemClickListener());
        lblNum = (TextView) findViewById(R.id.tv_question_naire_num);
    }


    @Override
    public void refresh(Object... params) {
        // TODO Auto-generated method stub
        switch (Integer.parseInt(params[0].toString().trim())) {
            case REFRESH_INFO:
//                if (dialog != null && dialog.isShowing()) {
//                    dialog.dismiss();
//                }
                if (!"".equals(params[1].toString().trim())
                        || null != params[1].toString().trim()||"[]".equals(params[1].toString().trim())) {
                    List<WenJuanType> newWenJuanType = getWenJuanTypes(params[1]
                            .toString().trim());

                    if (newWenJuanType.size() > 0) {

                        if(index==0){
                            wentJuanTypes.clear();
                        }

                        wentJuanTypes.addAll(newWenJuanType);
                        lishiJuanTypes.clear();
                        lishiJuanTypes.addAll(newWenJuanType);
                        lblNum.setText("共有"
                                + newWenJuanType.get(0).getRecordCount() + "篇");
                    } else {
                        lblNum.setText("共有0篇");
                    }

                    typeAdapter.notifyDataSetChanged();

                }

                if (lv.isRefreshing()) {
                    lv.onRefreshComplete();
                }

                break;

            default:
                break;
        }
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                long arg3) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(context, WenJuanPersonActivity.class);
            intent.putExtra("id",
                    ((WenJuanType) typeAdapter.getItem(position - 1)).getID());
            intent.putExtra("ISJYSTATUS", wentJuanTypes.get(position-1).isISJYSTATUS());
            MainTools.map.put("wenjuaninfo",
                    (WenJuanType) typeAdapter.getItem(position - 1));

            startActivity(intent);
        }

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        PersonService.allActivity.remove(this);
    }



    private void getPageInfo(int index) {

        Log.e("2017/10/24","getPageInfo(int index)");

        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, String> data = new HashMap<String, String>();
        data.put("page", index + "");
        data.put("rows", "15");
        params.put("data", data);
        PersonTask task = new PersonTask(
                PersonTask.WENJUANACTIVITY_GET_WENJUANINFO, params);
        PersonService.newTask(task);
    }

    private List<WenJuanType> getWenJuanTypes(String str) {
        List<WenJuanType> juanTypes = new ArrayList<WenJuanType>();
        try {
            JSONArray jsonArray = new JSONArray(str);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                WenJuanType juanType = new WenJuanType();
                juanType.setID(jsonObject.getInt("ID"));
                juanType.setCREATE_TIME(jsonObject.getString("CREATE_TIME"));
                juanType.setNO(jsonObject.getString("NO"));
                juanType.setISJYSTATUS(jsonObject.getBoolean("ISJYSTATUS"));
                juanType.setRecordCount(jsonObject.getInt("RecordCount"));
                juanType.setTEXT(jsonObject.getString("TEXT"));
                juanType.setTITLE(jsonObject.getString("TITLE"));
                String details = jsonObject.getString("Detils").toString();
                juanType.setJuanInfos(parseInfo(details));
                juanTypes.add(juanType);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return juanTypes;
    }

    private List<WenJuanInfo> parseInfo(String str) {
        List<WenJuanInfo> newInfos = new ArrayList<WenJuanInfo>();
        try {
            JSONArray array = new JSONArray(str);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = (JSONObject) array.get(i);
                WenJuanInfo info = new WenJuanInfo();
                info.setID(object.getInt("ID"));
                info.setCODE(object.getString("CODE"));
                info.setINPUT(object.getBoolean("INPUT"));
                info.setINPUT_TYPE(object.getString("INPUT_TYPE"));
                info.setJUMP_CODE(object.getString("JUMP_CODE"));
                info.setNO(object.getDouble("NO"));
                info.setPARENT_ID(object.getInt("PARENT_ID"));
                info.setRecordCount(object.getInt("RecordCount"));
                info.setTITLE_L(object.getString("TITLE_L"));
                info.setTITLE_R(object.getString("TITLE_R"));
                newInfos.add(info);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newInfos;
    }

//    private void showDialog() {
//        dialog = new ProgressDialog(context);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setMessage("数据信息加载中...");
//        dialog.show();
//    }

}

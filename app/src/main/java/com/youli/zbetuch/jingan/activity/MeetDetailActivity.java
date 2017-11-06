package com.youli.zbetuch.jingan.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.AppendixInfo;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.MeetNoticeInfo;
import com.youli.zbetuch.jingan.utils.FileUtils;
import com.youli.zbetuch.jingan.utils.IOUtil;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import com.youli.zbetuch.jingan.utils.ToastUtils;
import com.youli.zbetuch.jingan.view.MyListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by ZHengBin on 2017/9/2.
 */

public class MeetDetailActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private Context mContext=MeetDetailActivity.this;

    private final int SUCCEED_GET_STATUS=10001;
    private final int SUCCEED_SET_STATUS=10002;
    private final int SUCCEED_APPENDIX=10003;
    private final int SUCCEED_FILE=10004;
    private final int SUCCEED_SEE=10005;
    private final int SUCCEED_NODATA=10006;
    private final int  PROBLEM=10007;

    private TextView tvTitle;//会议名称
    private TextView tvTime;//会议名称
    private TextView tvAddress;//会议名称
    private TextView tvDoc;//会议内容
    private TextView tvNotifier;//通知人
    private TextView tvDate;//发布时间

    private Button btnIsRead;
    private MeetNoticeInfo meetInfo;

    private MyListView appendixLv;
    private List<AppendixInfo> lvData=new ArrayList<>();//附件的集合
    private CommonAdapter adapter;
    private String currentFile;
    private int pSign;
    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCEED_GET_STATUS://获得按钮的状态

                    if (TextUtils.equals("true",(String)msg.obj)) {
                        btnIsRead.setEnabled(false);
                        btnIsRead.setText("已读");
                    } else {
                        btnIsRead.setEnabled(true);
                        btnIsRead.setText("未读");
                    }
                    getorSetData("fujian");
                break;
                case SUCCEED_SET_STATUS://设置按钮的状态

                    if (TextUtils.equals("true",(String)msg.obj)) {
                        btnIsRead.setEnabled(false);
                        btnIsRead.setText("已读");
                        Toast.makeText(mContext, "已阅读", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "提交失败", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case SUCCEED_APPENDIX://获得附件

                    lvData.clear();
                    lvData.addAll((List<AppendixInfo>)msg.obj);

                    lvSetAdapter(lvData);

                    break;

                case PROBLEM:

                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();

                    break;

                case SUCCEED_FILE://下载文件

                    SavaFile((InputStream) msg.obj, Environment.getExternalStorageDirectory().getPath(),msg.arg1);

                    break;

                case SUCCEED_SEE://查看附件的具体内容

                    File file=new File(currentFile);
                    Intent intent = FileUtils.openFile(file.getAbsolutePath());
                    startActivity(intent);
                    currentFile = "";

                    break;

                case SUCCEED_NODATA:
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_detail);

        meetInfo=(MeetNoticeInfo) getIntent().getSerializableExtra("MEETINFO");

        if(meetInfo!=null) {
            initViews();
        }
    }

    private void initViews(){

        tvTitle= (TextView) findViewById(R.id.tv_meetnotice_detail_title);
        tvTime= (TextView) findViewById(R.id.tv_meetnotice_detail_time);
        tvAddress= (TextView) findViewById(R.id.tv_meetnotice_detail_address);
        tvDoc= (TextView) findViewById(R.id.tv_meetnotice_detail_doc);
        tvNotifier= (TextView) findViewById(R.id.tv_meetnotice_detail_notifier);
        tvDate= (TextView) findViewById(R.id.tv_meetnotice_detail_create_date);

        btnIsRead= (Button) findViewById(R.id.btn_meetnotice_detail_isread);
        btnIsRead.setOnClickListener(this);

        appendixLv= (MyListView) findViewById(R.id.lv_meetnotice_detail_appendix);
        appendixLv.setOnItemClickListener(this);

        initDatas();
    }

    private void initDatas(){

        tvTitle.setText(meetInfo.getTITLE());
        tvTime.setText(MyDateUtils.stringToYMDHMS(meetInfo.getMEETING_TIME()));
        tvAddress.setText(meetInfo.getMEETING_ADD());
        tvDoc.setText(meetInfo.getDOC());
        tvNotifier.setText("通知人："+meetInfo.getCREATE_STAFF_NAME());
        tvDate.setText("发布时间："+MyDateUtils.stringToYMDHMS(meetInfo.getCREATE_DATE()));

        //获得或者设置按钮的状态
        getorSetData("get");
    }

private void getorSetData(final String sign){
//    获取会议通知按钮的状态
//    http://web.youli.pw:89/Json/Get_Meeting_Check.aspx?master_id=10

    new Thread(

            new Runnable() {
                @Override
                public void run() {
                    String statusUrl = null;
                    
                    if(TextUtils.equals("get",sign)){

                        statusUrl= MyOkHttpUtils.BaseUrl+"/Json/Get_Meeting_Check.aspx?master_id="+meetInfo.getID();
                        
                    }else if(TextUtils.equals("set",sign)){
                        statusUrl= MyOkHttpUtils.BaseUrl+"/Json/Set_Meeting_Check.aspx?master_id="+meetInfo.getID();
                    }else if(TextUtils.equals("fujian",sign)){
                        statusUrl= MyOkHttpUtils.BaseUrl+"/Json/Get_Meeting_File.aspx?master_id="+meetInfo.getID();
                    }

                  

                    Response response=MyOkHttpUtils.okHttpGet(statusUrl);

                    Message msg=Message.obtain();

                    if(response!=null){

                        try {
                            String resStr=response.body().string();

                            if(!TextUtils.equals("",resStr)){
                                if(TextUtils.equals("get",sign)){
                                    msg.what=SUCCEED_GET_STATUS;
                                    msg.obj=resStr;
                                }else if(TextUtils.equals("set",sign)){
                                    msg.what=SUCCEED_SET_STATUS;
                                    msg.obj=resStr;
                                }else if(TextUtils.equals("fujian",sign)){
                                    if(!TextUtils.equals("[]",resStr)) {
                                        msg.what = SUCCEED_APPENDIX;
                                        Gson gson = new Gson();
                                        msg.obj = gson.fromJson(resStr, new TypeToken<List<AppendixInfo>>() {
                                        }.getType());
                                    }else{
                                        msg.what=SUCCEED_NODATA;
                                    }
                                }

                            }else{
                                msg.what=SUCCEED_NODATA;
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }else{

                      msg.what=PROBLEM;

                    }

                    mHandler.sendMessage(msg);

                }
            }

    ).start();

}

    private void lvSetAdapter(List<AppendixInfo> data){

        if(adapter==null){

            adapter=new CommonAdapter<AppendixInfo>(mContext,data,R.layout.item_worknotice_detail_appendix) {

                @Override
                public void convert(CommonViewHolder holder, AppendixInfo item, int position) {

                    TextView tvName=holder.getView(R.id.tv_item_worknotice_detail_appendix);
                    tvName.setText(item.getFILE_NAME());


                }
            };

            appendixLv.setAdapter(adapter);

        }else{

            adapter.notifyDataSetChanged();

        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_meetnotice_detail_isread:

                getorSetData("set");
                
                break;

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //下载图片
        getPermission(position);
        pSign=position;
        downLoadPic(lvData.get(position).getFILE_PATH(),position);
    }

    private void getPermission(int position) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ToastUtils.showShort(this, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},10000);
        } else {
            downLoadPic(lvData.get(position).getFILE_PATH(),position);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        downLoadPic(lvData.get(pSign).getFILE_PATH(),pSign);
    }
    private void downLoadPic(final String path, final int position){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url=MyOkHttpUtils.BaseUrl+"/"+path;

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null){

                            msg.obj= response.body().byteStream();

                            msg.what=SUCCEED_FILE;

                            msg.arg1=position;

                        }else{

                            msg.what=PROBLEM;

                        }

                        mHandler.sendMessage(msg);

                    }
                }

        ).start();

    }

    private void SavaFile(final InputStream is, final String path, final int position) {

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String fileName = lvData.get(position).getFILE_NAME();

                        Message msg=Message.obtain();

                        File file = new File(path);
                        FileOutputStream fileOutputStream = null;
                        //文件夹不存在，则创建它
                        if (!file.exists()) {
                            file.mkdir();
                        }

                        currentFile=path+"/"+fileName;

                        File saveFile = new File(file, fileName);

//                        if (saveFile.exists()) {
//                            Log.e("2017/9/4","你存在");
//                            msg.what=SUCCEED_SEE;
//                        } else {
                            try {

                                fileOutputStream = new FileOutputStream(saveFile);
                                //Log.e("2017/9/4","数据流=="+is);
                                fileOutputStream.write(IOUtil.getBytesByStream(is));
                                fileOutputStream.close();
                                Log.e("2017/9/4","创建");
                                msg.what=SUCCEED_SEE;
                            } catch (IOException e) {
                                if(saveFile.exists()){
                                    saveFile.delete();
                                }
                                e.printStackTrace();
                                msg.what=PROBLEM;
                            }
                    //    }
                        mHandler.sendMessage(msg);
                    }
                }

        ).start();

    }

}

package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
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
import com.youli.zbetuch.jingan.entity.WorkNoticeInfo;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import com.youli.zbetuch.jingan.view.MyListView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by liutao on 2017/8/30.
 */

public class WorkNoticeDetailActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private Context mContext=WorkNoticeDetailActivity.this;

    private final int SUCCEED_BUTTON_GET_STATE=10000;
    private final int SUCCEED_BUTTON_SET_STATE=10001;
    private final int SUCCEED_APPENDIX=10002;
    private final int SUCCEED_PICTURE=10003;
    private final int  PROBLEM=10004;

    private Button btnIsRead;
    private TextView tvTitle,tvDoc,tvNoticeTime,tvNotifier,tvCreateDate;
    private MyListView lvAppendix;
    private List<AppendixInfo> lvData=new ArrayList<>();//附件的集合
    private CommonAdapter adapter;
    private WorkNoticeInfo info;
    private String currentFile;


    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCEED_BUTTON_GET_STATE:

                    if(TextUtils.equals("true",(String)msg.obj)){
                        btnIsRead.setEnabled(false);
                        btnIsRead.setText("已读");
                    } else {
                        btnIsRead.setEnabled(true);
                        btnIsRead.setText("未读");
                    }
                    getorSetData("fujian");
                    break;

                case SUCCEED_BUTTON_SET_STATE:

                    if(TextUtils.equals("true",(String)msg.obj)){
                        btnIsRead.setEnabled(false);
                        btnIsRead.setText("已读");
                        Toast.makeText(mContext, "已阅读", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "提交失败", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case SUCCEED_APPENDIX://获得附件

                    lvData.clear();
                    lvData.addAll((List<AppendixInfo>)(msg.obj));
                    lvSetAdapter(lvData);
                    break;

                case SUCCEED_PICTURE://下载附件中的图片

                    boolean isDownLoad=SavaImage((Bitmap) msg.obj, Environment.getExternalStorageDirectory().getPath(),msg.arg1);

                    if(isDownLoad){//查看文件

                        File file=new File(currentFile);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        if (Build.VERSION.SDK_INT <Build.VERSION_CODES.N) {//小于7.0查看图片
                            intent.setDataAndType(Uri.fromFile(file), "image/*");
                        }else{//大于等于7.0查看图片
                            //解决方法，请查看：http://www.cnblogs.com/galibujianbusana/p/6482992.html
                                Uri photoURI = FileProvider.getUriForFile(mContext, "com.youli.zbetuch.jingan.provider", file);
                               intent.setDataAndType(photoURI, "image/*");
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);    //这一步很重要。给目标应用一个临时的授权。
                        }
                        startActivity(intent);
                        currentFile = "";

                    }else{
                        Toast.makeText(mContext,"文件下载失败，请重新点击",Toast.LENGTH_SHORT).show();
                    }

                    break;

                case PROBLEM:

                    Toast.makeText(mContext,"网络不给力", Toast.LENGTH_SHORT).show();


                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worknotice_detail);

        info=(WorkNoticeInfo)getIntent().getExtras().getSerializable("WORKDATA");
        initViews();
    }

    private void initViews(){

        btnIsRead= (Button) findViewById(R.id.btn_worknotice_detail_isread);
        btnIsRead.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_worknotice_detail_title);
        tvDoc= (TextView) findViewById(R.id.tv_worknotice_detail_doc);
        tvNoticeTime= (TextView) findViewById(R.id.tv_worknotice_detail_notice_time);
        tvNotifier= (TextView) findViewById(R.id.tv_worknotice_detail_notifier);
        tvCreateDate= (TextView) findViewById(R.id.tv_worknotice_detail_create_date);

        lvAppendix= (MyListView) findViewById(R.id.lv_worknotice_detail_appendix);
        lvAppendix.setOnItemClickListener(this);
          initDatas();
    }

     private void initDatas(){

      tvTitle.setText(info.getTITLE());
         tvDoc.setText(info.getDOC());
         tvNoticeTime.setText(MyDateUtils.stringToYMDHMS(info.getNOTICE_TIME()));
         tvNotifier.setText("通知人："+info.getCREATE_STAFF_NAME());
         tvCreateDate.setText("发布时间："+MyDateUtils.stringToYMDHMS(info.getCREATE_DATE()));


//         获取工作通知按钮的状态和附件的信息
         getorSetData("get");

     }


    private void getorSetData(final String sign){
        //         获取工作通知按钮的状态
//         http://web.youli.pw:89/Json/Get_Work_Notice_Check.aspx?master_id=1
//        已读按钮事件
//        http://web.youli.pw:89/Json/Set_Work_Notice_Check.aspx?master_id=1
        //工作通知中的附件
      //  http://web.youli.pw:89/Json/Get_Work_Notice_File.aspx?master_id=1
        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url = null;

                        if(TextUtils.equals(sign,"get")){
                            url= MyOkHttpUtils.BaseUrl+"/Json/Get_Work_Notice_Check.aspx?master_id="+info.getID();
                        }else if(TextUtils.equals(sign,"set")){
                            url= MyOkHttpUtils.BaseUrl+"/Json/Set_Work_Notice_Check.aspx?master_id="+info.getID();
                        }else if(TextUtils.equals(sign,"fujian")){
                            url= MyOkHttpUtils.BaseUrl+"/Json/Get_Work_Notice_File.aspx?master_id="+info.getID();
                        }

                        Message msg=Message.obtain();

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        if(response!=null){

                            try {
                                String resStr=response.body().string();
                               if(TextUtils.equals(sign,"get")) {
                                   msg.what = SUCCEED_BUTTON_GET_STATE;
                                   msg.obj = resStr;
                               }else if(TextUtils.equals(sign,"set")){
                                   msg.what = SUCCEED_BUTTON_SET_STATE;
                                   msg.obj = resStr;
                               }else if(TextUtils.equals(sign,"fujian")){

                                   Gson gson=new Gson();

                                   msg.obj=gson.fromJson(resStr,new TypeToken<List<AppendixInfo>>(){}.getType());

                                   msg.what = SUCCEED_APPENDIX;
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

            lvAppendix.setAdapter(adapter);

        }else{

            adapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_worknotice_detail_isread:

                getorSetData("set");

                break;

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //下载图片
        downLoadPic(lvData.get(position).getFILE_PATH(),position);

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

                              msg.obj= BitmapFactory.decodeStream((InputStream)(response.body().byteStream()));

                              msg.what=SUCCEED_PICTURE;

                              msg.arg1=position;

                          }else{

                              msg.what=PROBLEM;

                          }

                          mHandler.sendMessage(msg);

                      }
                  }

          ).start();

    }

    /**
     * 保存位图到本地
     * @param bitmap
     * @param path 本地路径,这个方法你按几次图片就会下载几次图片
     * @return void
     */
//    public void SavaImage(Bitmap bitmap, String path){
//        Log.e("2017-9-1", "path=="+path);
//
//        File file=new File(path);
//        FileOutputStream fileOutputStream=null;
//        //文件夹不存在，则创建它
//        if(!file.exists()){
//            file.mkdir();
//        }
//        try {
//
//            fileOutputStream=new FileOutputStream(path+"/"+System.currentTimeMillis()+".png");
//
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,fileOutputStream);
//            fileOutputStream.close();
//            Log.i("2017-9-1", "233成功");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 保存位图到本地
     * @param bitmap
     * @param path 本地路径，这个方法你只要下载过图片了，就不会重复下载
     * @return void
     */
    public boolean SavaImage(Bitmap bitmap, String path,int position) {

        String fileName = lvData.get(position).getFILE_NAME();

        File file = new File(path);
        FileOutputStream fileOutputStream = null;
        //文件夹不存在，则创建它
        if (!file.exists()) {
            file.mkdir();
        }

        currentFile=path+"/"+fileName;

        File saveFile = new File(file, fileName);

        if (saveFile.exists()) {
            return true;
        } else {
            try {
                fileOutputStream = new FileOutputStream(saveFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
                return true;
            } catch (Exception e) {
                if(saveFile.exists()){
                    saveFile.delete();
                }
                e.printStackTrace();
                return  false;
            }
        }

    }
}

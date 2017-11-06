package com.youli.zbetuch.jingan.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.entity.NewsInfo;
import com.youli.zbetuch.jingan.entity.WorkRecordInfo;
import com.youli.zbetuch.jingan.utils.IOUtil;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/10/15.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 工作日志详情界面
 */

public class WorkRecordDetailActivity extends BaseActivity{

    private Context mContext=WorkRecordDetailActivity.this;

    private final  int SUCCESS=10000;
    private final int FAIL=10001;

    private TextView tvTitle,tvContent,tvDate;
    private Button btnShowIv;
    private WorkRecordInfo info;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS:

                    if(msg.obj!=null){
                        showDetailDialog((Bitmap)msg.obj);
                    }else{
                        Toast.makeText(mContext, "没有可显示的图片", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case FAIL:
                    Toast.makeText(mContext, "网络不给力", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_record_detail);

        info=(WorkRecordInfo) getIntent().getSerializableExtra("info");

        initViews();

        if(info!=null) {
            initDates();
        }
    }

    private void initViews(){

       tvTitle= (TextView) findViewById(R.id.tv_work_record_detail_name);

        tvContent= (TextView) findViewById(R.id.tv_work_record_detail_content);

        tvDate= (TextView) findViewById(R.id.tv_work_record_detail_time);

        btnShowIv= (Button) findViewById(R.id.btn_work_record_detail);;
        btnShowIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPic(info.getID());

            }
        });

    }

    private void initDates(){

        tvTitle.setText(info.getTITLE());
        tvContent.setText(info.getDOC());
        tvDate.setText(MyDateUtils.stringToYMDHMS(info.getCREATE_DATE()));
    }

    private  void getPic(final int id){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                        //http://web.youli.pw:89/Json/Get_WorkToDate_Pic.aspx?id=28
                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_WorkToDate_Pic.aspx?id="+id;

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null){

                          if(response.body()!=null){


                                  byte[] picData= IOUtil.getBytesByStream(response.body().byteStream());

                                  Bitmap btp= BitmapFactory.decodeByteArray(picData,0,picData.length);
                                  msg.obj=btp;
                                  msg.what=SUCCESS;
                                  mHandler.sendMessage(msg);

                          }else{
                              msg.what=FAIL;
                              mHandler.sendMessage(msg);
                          }

                        }else{

                           msg.what=FAIL;
                            mHandler.sendMessage(msg);

                        }

                    }
                }

        ).start();

    }

    private void showDetailDialog(Bitmap bmp){

        AlertDialog.Builder builder=new AlertDialog.Builder(mContext,R.style.Translucent_NoTitle);
        View view= LayoutInflater.from(mContext).inflate(R.layout.dialog_work_record_detail,null);

        builder.setView(view);
        AlertDialog dialog=builder.create();

        dialog.show();

        ImageView iv= (ImageView) view.findViewById(R.id.iv_dialog_work_record_detail);
        iv.setImageBitmap(bmp);




//        WindowManager m = context.getWindowManager();
//        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
//        android.view.WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
//        lp.height = (int) (d.getHeight() * 0.7);   //高度设置为屏幕的0.7
//        //  lp.width = (int) (d.getWidth() * 0.5);    //宽度设置为屏幕的0.5
//        dialog.getWindow().setAttributes(lp);     //设置生效

    }

}

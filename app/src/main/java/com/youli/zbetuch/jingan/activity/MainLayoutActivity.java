package com.youli.zbetuch.jingan.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.MainAdapter;
import com.youli.zbetuch.jingan.entity.GetStaffInfo;
import com.youli.zbetuch.jingan.entity.JobsInfo;
import com.youli.zbetuch.jingan.entity.MainContent;
import com.youli.zbetuch.jingan.entity.MeetNoticeInfo;
import com.youli.zbetuch.jingan.entity.NewsInfo;
import com.youli.zbetuch.jingan.entity.PostMsgTask;
import com.youli.zbetuch.jingan.entity.WorkNoticeInfo;
import com.youli.zbetuch.jingan.service.PostMsgService;
import com.youli.zbetuch.jingan.utils.FileUtils;
import com.youli.zbetuch.jingan.utils.IOUtil;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import com.youli.zbetuch.jingan.view.CircleImageView;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import okhttp3.Response;

public class MainLayoutActivity extends CheckPermissionsActivity implements View.OnClickListener {

    private Context mContext = MainLayoutActivity.this;
    private GridView gv;
    private String titleArray[] = {"会议通知", "工作通知", "岗位信息", "近期热点"};
    private List<MainContent> data = new ArrayList<>();
    private List<MeetNoticeInfo> childData1 = new ArrayList<>();
    private List<WorkNoticeInfo> childData2 = new ArrayList<>();
    private List<JobsInfo> childData3 = new ArrayList<>();
    private List<NewsInfo> childData4 = new ArrayList<>();

    private TextView nameTv, emailTv, phoneTv;
    private TextView jDuTv, wDuTv, gDuTv;//经度，纬度，高度

    public static String jingDu,weiDu;
    private CircleImageView picIv;
    private Button workBtn;


    private final int SUCCEED_NAME = 10000;
    private final int SUCCEED_PIC = 10001;
    private final int SUCCEED_MI = 10002;
    private final int SUCCEED_WN = 10003;
    private final int SUCCEED_JOB = 10004;
    private final int SUCCEED_NI = 10005;
    private final int SUCCEED_NODATA = 10006;
    private final int PROBLEM = 10007;

    public static String adminName;

    public static int adminId;

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient;
    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case SUCCEED_NAME:

                    nameTv.setText(((GetStaffInfo) msg.obj).getNAME());
                    emailTv.setText(((GetStaffInfo) msg.obj).getEMAIL());
                    phoneTv.setText(((GetStaffInfo) msg.obj).getPHONE());
                    adminName = nameTv.getText().toString().trim();
                    adminId = ((GetStaffInfo) msg.obj).getID();
                    //一个长连接的推送
                    postMsg();
                    break;

                case SUCCEED_PIC:

                    picIv.setImageBitmap((Bitmap) msg.obj);

                    break;

                case SUCCEED_MI:

                    int itemSize1 = childData1.size();


                    for (int i = 0; i < 4 - itemSize1; i++) {

                        childData1.add(new MeetNoticeInfo("", ""));

                    }


                    data.add(new MainContent(titleArray[0], childData1, null, null, null));
                    getWorkNotice();//工作通知
                    break;

                case SUCCEED_WN:


                    int itemSize2 = childData2.size();


                    for (int i = 0; i < 4 - itemSize2; i++) {

                        childData2.add(new WorkNoticeInfo("", ""));

                    }

                    data.add(new MainContent(titleArray[1], null, childData2, null, null));

                    getJobsInfo();//岗位信息


                    break;
                case SUCCEED_JOB:


                    int itemSize3 = childData3.size();


                    for (int i = 0; i < 4 - itemSize3; i++) {

                        childData3.add(new JobsInfo("", ""));

                    }

                    data.add(new MainContent(titleArray[2], null, null, childData3, null));

                    getNewsInfo();//近期热点
                    break;

                case SUCCEED_NI:

                    int itemSize4 = childData4.size();

                    for (int i = 0; i < 4 - itemSize4; i++) {

                        childData4.add(new NewsInfo("", ""));

                    }

                    data.add(new MainContent(titleArray[3], null, null, null, childData4));

                    MainAdapter mainAdapter = new MainAdapter(data, MainLayoutActivity.this);
                    gv.setAdapter(mainAdapter);

                    break;

                case PROBLEM:

                    Toast.makeText(mContext, "网络不给力", Toast.LENGTH_SHORT).show();

                    break;

                case SUCCEED_NODATA:

                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        // 启动服务
        Intent service = new Intent();
        service.setClass(mContext,PostMsgService.class);
        startService(service);

        PostMsgService.addActivity(this);

        jDuTv = (TextView) findViewById(R.id.tv_layout_work_longitude);//经度
        wDuTv = (TextView) findViewById(R.id.tv_layout_work_latitude);//纬度
        gDuTv = (TextView) findViewById(R.id.tv_layout_work_height);//高度
        nameTv = (TextView) findViewById(R.id.main_layout_name_tv);
        emailTv = (TextView) findViewById(R.id.main_layout_email_tv);
        phoneTv = (TextView) findViewById(R.id.main_layout_phone_tv);
        picIv = (CircleImageView) findViewById(R.id.main_layout_head_iv);
        gv = (GridView) findViewById(R.id.main_layout_gv);
        workBtn = (Button) findViewById(R.id.main_layout_work_btn);
        workBtn.setOnClickListener(this);


        initData();

        //发送本机app和film
        sendPhoneInfo();
        getGpsInfo();//定位
    }

    private void sendPhoneInfo(){

        final Map<String, String> apkData = new HashMap<String, String>();
        apkData.put("apps", FileUtils.showAllApks(mContext));
        apkData.put("films", FileUtils.getMovies(mContext));


        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String str=FileUtils.sendApps(apkData);

                        Log.i("2017/10/27","本机信息="+str);

                    }
                }

        ).start();

    };


    private void getGpsInfo(){

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                if(aMapLocation!=null){

                    if(aMapLocation.getErrorCode()==0){
                        //可在其中解析amapLocation获取相应内容。

                        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        aMapLocation.getLatitude();//获取纬度
                        aMapLocation.getLongitude();//获取经度
                        aMapLocation.getAccuracy();//获取精度信息
                        aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                        aMapLocation.getCountry();//国家信息
                        aMapLocation.getProvince();//省信息
                        aMapLocation.getCity();//城市信息
                        aMapLocation.getDistrict();//城区信息
                        aMapLocation.getStreet();//街道信息
                        aMapLocation.getStreetNum();//街道门牌号信息
                        aMapLocation.getCityCode();//城市编码
                        aMapLocation.getAdCode();//地区编码
                        aMapLocation.getAoiName();//获取当前定位点的AOI信息
                        aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                        aMapLocation.getFloor();//获取当前室内定位的楼层
                        aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
                        //获取定位时间
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(aMapLocation.getTime());
                        df.format(date);
                        jDuTv.setText("经度:"+aMapLocation.getLongitude());
                        wDuTv.setText("纬度:"+aMapLocation.getLatitude());
                        gDuTv.setText("高度:"+aMapLocation.getAltitude()+"米");
                           jingDu=String.valueOf(aMapLocation.getLongitude());
                         weiDu=String.valueOf(aMapLocation.getLatitude());
                    }else{
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }

            }
        });

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

//        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);

        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);

        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);

        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }


    private void initData(){

        getStaffName();//登录人员的名称，电话，电子邮箱

        getStaffPic();//登录人员的头像

        getMeetInfo();//会议通知
    }

    private void getStaffPic(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                        //http://web.youli.pw:89/Json/GetStaffPic.aspx
                        String urlPic=MyOkHttpUtils.BaseUrl+"/Json/GetStaffPic.aspx";
                        Response response=MyOkHttpUtils.okHttpGet(urlPic);
                        try {
                            Message msg=Message.obtain();

                            if(response!=null){
                            InputStream is=response.body().byteStream();

                              byte[] picData=IOUtil.getBytesByStream(is);

                               Bitmap btp= BitmapFactory.decodeByteArray(picData,0,picData.length);

                                msg.obj=btp;
                                msg.what=SUCCEED_PIC;
                                mHandler.sendMessage(msg);

                            }else{

                                sendProblemMessage(msg);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();

    }

    private void getStaffName(){

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        String urlStaff= MyOkHttpUtils.BaseUrl+"/Json/Get_Staff.aspx";
                        Response response=MyOkHttpUtils.okHttpGet(urlStaff);

                        try {
                            Message msg=Message.obtain();
                            if(response!=null){

                                String infoStr=response.body().string();

                                if(!TextUtils.equals(infoStr,"")&&!TextUtils.equals(infoStr,"{}")) {

                                    Gson gson = new Gson();

                                    GetStaffInfo staffInfo = gson.fromJson(infoStr, GetStaffInfo.class);
                                    msg.obj = staffInfo;
                                    msg.what = SUCCEED_NAME;

                                }else{
                                    msg.what = SUCCEED_NODATA;
                                }
                                mHandler.sendMessage(msg);
                            }else {
                                sendProblemMessage(msg);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();


    }

    private void getMeetInfo(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String urlMi=MyOkHttpUtils.BaseUrl+"/Json/Get_Meeting_Master.aspx?State=true&page=0&rows=4";
                        Response response=MyOkHttpUtils.okHttpGet(urlMi);

                        try {
                            Message msg=Message.obtain();
                            if(response!=null){
                                String miStr=response.body().string();
                                if(!TextUtils.equals(miStr,"")) {
                                    Gson gson = new Gson();

                                    childData1 = gson.fromJson(miStr, new TypeToken<List<MeetNoticeInfo>>() {
                                    }.getType());
                                    msg.obj = childData1;
                                    msg.what = SUCCEED_MI;

                                }else{
                                    msg.what = SUCCEED_NODATA;
                                }
                                mHandler.sendMessage(msg);
                            }else{
                                sendProblemMessage(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();

    }

    private void getWorkNotice(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String urlWn=MyOkHttpUtils.BaseUrl+"/Json/Get_Work_Notice.aspx?page=0&rows=4";
                        Response response=MyOkHttpUtils.okHttpGet(urlWn);

                        try {
                            Message msg=Message.obtain();
                            if(response!=null){
                                String wnStr=response.body().string();
                                if(!TextUtils.equals(wnStr,"")) {
                                    Gson gson = new Gson();

                                    childData2 = gson.fromJson(wnStr, new TypeToken<List<WorkNoticeInfo>>() {
                                    }.getType());
                                    msg.obj = childData2;
                                    msg.what = SUCCEED_WN;

                                }else{
                                    msg.what = SUCCEED_NODATA;
                                }
                                mHandler.sendMessage(msg);
                            }else{
                                sendProblemMessage(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();

    }


    private void getJobsInfo(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String urlJi=MyOkHttpUtils.BaseUrl+"/Json/GetJobs.aspx?page=0&rows=4";
                        Response response=MyOkHttpUtils.okHttpGet(urlJi);

                        try {
                            Message msg=Message.obtain();
                            if(response!=null){
                                String jiStr=response.body().string();
                                if(!TextUtils.equals(jiStr,"")) {
                                    Gson gson = new Gson();

                                    childData3 = gson.fromJson(jiStr, new TypeToken<List<JobsInfo>>() {
                                    }.getType());
                                    msg.obj = childData3;
                                    msg.what = SUCCEED_JOB;

                                }else{
                                    msg.what = SUCCEED_NODATA;
                                }
                                mHandler.sendMessage(msg);
                            }else{
                                sendProblemMessage(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();

    }

    private void getNewsInfo(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String urlNi= MyOkHttpUtils.BaseUrl+"/Json/Get_News.aspx?page=0&rows=4";
                        Response response=MyOkHttpUtils.okHttpGet(urlNi);

                        try {
                            Message msg=Message.obtain();
                            if(response!=null){
                                String niStr=response.body().string();
                                if(!TextUtils.equals(niStr,"")) {
                                    Gson gson = new Gson();

                                    childData4 = gson.fromJson(niStr, new TypeToken<List<NewsInfo>>() {
                                    }.getType());
                                    msg.obj = childData4;
                                    msg.what = SUCCEED_NI;

                                }else{
                                    msg.what = SUCCEED_NODATA;
                                }
                                mHandler.sendMessage(msg);
                            }else{
                                sendProblemMessage(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

        ).start();

    }

    private  void sendProblemMessage(Message msg){
        msg.what=PROBLEM;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.main_layout_work_btn:


                Intent intent=new Intent(this,FunctionListActivity.class);
                startActivity(intent);

                break;
        }

    }


    public void postMsg(){
        Map<String, String> data = new HashMap<String, String>();
        Map<String, Object> params = new HashMap<String, Object>();
        data.put("content", "-1");
        data.put("staff", adminId+"");
        params.put("data", data);
        PostMsgTask task = new PostMsgTask(PostMsgTask.ACTIVITY_GET_POSTMSG, params);
        PostMsgService.newTask(task);
    }


    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("您确定退出吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //停止服务
                Intent service = new Intent();
                service.setClass(mContext,PostMsgService.class);
                stopService(service);

            ActivityController.finishAll();
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

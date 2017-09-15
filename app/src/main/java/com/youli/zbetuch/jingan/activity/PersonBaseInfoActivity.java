package com.youli.zbetuch.jingan.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.adapter.WhSpAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.MarkImgInfo;
import com.youli.zbetuch.jingan.entity.PersonInfo;
import com.youli.zbetuch.jingan.entity.StaffMarkInfo;
import com.youli.zbetuch.jingan.entity.WhMarkInfo;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Response;

/**
 * Created by ZHengBin on 2017/8/26.
 */

public class PersonBaseInfoActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener,CompoundButton.OnCheckedChangeListener{


    private final int SUCCEED_PHOTO=10000;//头像
    private final int SUCCEED_MARKIMG=10001;//标识图片
    private final int SUCCEED_MARK=10002;//专项标识
    private final int SUCCEED_MARKIMG_UPLOAD=10003;//上传标识
    private final int SUCCEED_MARK_WH=10004;//获取维护标识的信息
    private final int SUCCEED_MARK_WH_ADD=10005;//维护标识的添加
    private final int SUCCEED_MARK_WH_DEL=10006;//维护标识的删除
    private final int SUCCEED_MARK_WH_CHANGE=10007;//维护标识的修改
    private final int SUCCEED_MARK_UPLOAD=10008;//上传专项标识
    private final int  PROBLEM=10009;

    private Context mContext=PersonBaseInfoActivity.this;

    private Button btnTakePhoto;//拍照
    private Button btnChoicePhoto;//选择图片
    private Button btnUploadPhoto;//上传图片
    private Button btnSaveInfo;//保存信息
    private Button btnMark;// 标识
    private Button btnZxMark;//专项标识
    private List<StaffMarkInfo> zxMarkData=new ArrayList<>();//专项标识
    private List<StaffMarkInfo> zxMarkDataDialog=new ArrayList<>();//专项标识
    private List<WhMarkInfo> zxMarkWhData=new ArrayList<>();//维护专项标识
    private List<MarkImgInfo> markImgData=new ArrayList<>();//标识
    private List<MarkImgInfo> markDataDialog=new ArrayList<>();//标识
    private PersonInfo personInfo;

    private ImageView ivHead;//头像
    private ImageView yjbyshIv;//应届毕业生
    private ImageView jdryIv;//戒毒人员
    private ImageView jyknIv;//就业困难
    private ImageView qhryIv;//启航人员
    private ImageView wjyjIv;//零就业家庭
    private ImageView xjryIv;//刑解人员

    private EditText etName;//姓名
    private EditText etBirthday;//出生年月
    private EditText etOrigin;//籍贯
    private EditText etPhone;//电话
    private EditText etStreet;//街道
    private EditText etJwh;//居委会
    private EditText etSfz;//身份证
    private EditText etHujiRoad;//户籍路
    private EditText etHujiNong;//户籍弄
    private EditText etHujiNum;//户籍号
    private EditText etHujiRoom;//户籍室
    private EditText etJuzhuRoad;//居住路
    private EditText etJuzhuNong;//居住弄
    private EditText etJuzhuNum;//居住号
    private EditText etJuzhuRoom;//居住室
    private EditText etMark;//备注

    private Spinner spSex;//性别
    private String [] spSexData;
    private Spinner spNation;//民族
    private String [] spNationData;
    private Spinner spState;//状态
    private String [] spStateData;
      private Spinner spIntention;//当前意向
    private String [] spIntentionData;
    private Spinner spEdu;//文化程度
    private String [] spEduData;
       private Spinner spMd;//摸底情况
    private String [] spMdData;

    private CheckBox cbHuji;//户籍
    private CheckBox cbJuzhu;//居住

    private LinearLayout markLl;

    private CommonAdapter markAdapter;//标识对话框里面的适配器
    private CommonAdapter markZxAdapter;//专项标识对话框里面的适配器
    private  String  markArrayStr;
   private  String [] markArray;

    private AlertDialog editDialog;//编辑的对话框
    private AlertDialog whDialog;//维护的对话框
    private int whItemId;//维护条目的ID

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCEED_PHOTO://头像

                    if(msg.obj!=null) {
                        ivHead.setImageBitmap((Bitmap) msg.obj);
                    }

                    //标识图片
                    getNetDatas("markimg",-1);

                break;

                case SUCCEED_MARKIMG:

                    markImgData.clear();

                    markImgData.addAll((List<MarkImgInfo>)msg.obj);

                        yjbyshIv.setVisibility(View.GONE);
                        qhryIv.setVisibility(View.GONE);
                        xjryIv.setVisibility(View.GONE);
                        jdryIv.setVisibility(View.GONE);
                        wjyjIv.setVisibility(View.GONE);
                        jyknIv.setVisibility(View.GONE);

                      for (MarkImgInfo info : markImgData) {

                          if (TextUtils.equals(info.getMARK(), "应届毕业生")) {
                              yjbyshIv.setVisibility(View.VISIBLE);
                          }
                          if (TextUtils.equals(info.getMARK(), "启航人员")) {
                              qhryIv.setVisibility(View.VISIBLE);
                          }
                          if (TextUtils.equals(info.getMARK(), "刑解人员")) {
                              xjryIv.setVisibility(View.VISIBLE);
                          }
                          if (TextUtils.equals(info.getMARK(), "戒毒人员")) {
                              jdryIv.setVisibility(View.VISIBLE);
                          }
                          if (TextUtils.equals(info.getMARK(), "零就业家庭")) {
                              wjyjIv.setVisibility(View.VISIBLE);
                          }
                          if (TextUtils.equals(info.getMARK(), "就业困难人员")) {
                              jyknIv.setVisibility(View.VISIBLE);
                          }
                      }

                    //专项标识
                    getNetDatas("mark",-1);

                    break;

                case SUCCEED_MARK://专项标识


                    markLl.removeAllViews();

                    zxMarkData.clear();
                    zxMarkData.addAll(( List<StaffMarkInfo>)msg.obj);


//                    if(markZxAdapter!=null){
//                        zxMarkDataDialog.clear();
//                        zxMarkDataDialog.addAll((List<StaffMarkInfo>)zxMarkData);
//                        markZxAdapter.notifyDataSetChanged();
//                    }

                    for(StaffMarkInfo info : zxMarkData) {

                        TextView tv = new TextView(mContext);
                        tv.setText(info.getType_Name());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        tv.setLayoutParams(lp);
                        tv.setPadding(0, 0, 10, 0);
                        tv.setTextSize(16);
                        tv.setTextColor(Color.parseColor("#ffffff"));
                        markLl.addView(tv);
                    }
                    break;

                case SUCCEED_MARKIMG_UPLOAD://上传标识

                    if(TextUtils.equals("True",(String)msg.obj)){
                        Toast.makeText(mContext,"上传成功",Toast.LENGTH_SHORT).show();
                        editDialog.dismiss();
                    }
                    //标识图片
                    getNetDatas("markimg",-1);
                    break;
                case SUCCEED_MARK_WH://获取维护标识的信息

                    zxMarkWhData.clear();

                    zxMarkWhData.addAll((List<WhMarkInfo>)(msg.obj));

                    if(msg.arg1==10000){
                        showEditDialog("zxMark");
//                    }else if(msg.arg1==20000){
//                        //专项标识
//                        getNetDatas("mark",-1);
                    }



                    break;

                case SUCCEED_MARK_WH_ADD://维护标识的添加

                    if(TextUtils.equals("True",(String)msg.obj)){
                        Toast.makeText(mContext,"操作成功!",Toast.LENGTH_SHORT).show();
                        whDialog.dismiss();
                      //  getNetDatas("whMarkInfo",20000);
                    }

                    break;

                case SUCCEED_MARK_WH_DEL://维护标识的删除

                    if(TextUtils.equals("True",(String)msg.obj)){
                        Toast.makeText(mContext,"操作成功!",Toast.LENGTH_SHORT).show();
                        whDialog.dismiss();

                       // getNetDatas("whMarkInfo",20000);
                    }

                    break;

                case SUCCEED_MARK_WH_CHANGE://维护标识的修改

                    if(TextUtils.equals("True",(String)msg.obj)){
                        Toast.makeText(mContext,"操作成功!",Toast.LENGTH_SHORT).show();
                        whDialog.dismiss();

                       // getNetDatas("whMarkInfo",20000);
                    }


                    break;

                case SUCCEED_MARK_UPLOAD://上传专项标识
                    if(TextUtils.equals("True",(String)msg.obj)){
                        Toast.makeText(mContext,"上传成功",Toast.LENGTH_SHORT).show();
                        editDialog.dismiss();
                    }
                    //专项标识
                    getNetDatas("mark",-1);
                     break;

               case PROBLEM:

                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();

                    break;
            }


        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_baseinfo);

        personInfo=(PersonInfo) getIntent().getSerializableExtra("PINFO");

        initViews();
    }

    private void initViews(){

        cbHuji= (CheckBox) findViewById(R.id.cb_person_baseinfo_huji);
        cbHuji.setOnCheckedChangeListener(this);
        cbJuzhu= (CheckBox) findViewById(R.id.cb_person_baseinfo_juzhu);
        cbJuzhu.setOnCheckedChangeListener(this);

        spSex= (Spinner) findViewById(R.id.sp_person_baseinfo_sex);
        spSex.setOnItemSelectedListener(this);
         spNation=(Spinner) findViewById(R.id.sp_person_baseinfo_nation);//民族
        spNation.setOnItemSelectedListener(this);
         spState=(Spinner) findViewById(R.id.sp_person_baseinfo_state);//状态
        spState.setOnItemSelectedListener(this);
          spIntention=(Spinner) findViewById(R.id.sp_person_baseinfo_intention);//当前意向
        spIntention.setOnItemSelectedListener(this);
          spEdu=(Spinner) findViewById(R.id.sp_person_baseinfo_edu);//文化程度
        spEdu.setOnItemSelectedListener(this);
         spMd=(Spinner) findViewById(R.id.sp_person_baseinfo_md);//摸底情况
        spMd.setOnItemSelectedListener(this);

        markLl= (LinearLayout)findViewById(R.id.ll_person_baseinfo_special_mark);

        yjbyshIv= (ImageView) findViewById(R.id.iv_person_baseinfo_yjbysh);
        yjbyshIv.setVisibility(View.GONE);
        jdryIv= (ImageView) findViewById(R.id.iv_person_baseinfo_jdry);
        jdryIv.setVisibility(View.GONE);
        jyknIv= (ImageView) findViewById(R.id.iv_person_baseinfo_jykn);
        jyknIv.setVisibility(View.GONE);
        qhryIv= (ImageView) findViewById(R.id.iv_person_baseinfo_qhry);
        qhryIv.setVisibility(View.GONE);
        wjyjIv= (ImageView) findViewById(R.id.iv_person_baseinfo_wjyj);
        wjyjIv.setVisibility(View.GONE);
        xjryIv= (ImageView) findViewById(R.id.iv_person_baseinfo_xjry);
        xjryIv.setVisibility(View.GONE);


        btnTakePhoto= (Button) findViewById(R.id.btn_person_baseinfo_takephoto);
        btnChoicePhoto= (Button) findViewById(R.id.btn_person_baseinfo_choicephoto);
        btnUploadPhoto= (Button) findViewById(R.id.btn_person_baseinfo_uploadphoto);
        btnSaveInfo= (Button) findViewById(R.id.btn_person_baseinfo_saveinfo);
        btnMark= (Button) findViewById(R.id.btn_person_baseinfo_biaoshi);
        btnZxMark= (Button) findViewById(R.id.btn_person_baseinfo_zhuanxiang_biaoshi);

        ivHead= (ImageView) findViewById(R.id.iv_person_baseinfo_head);

        etName=(EditText) findViewById(R.id.et_person_baseinfo_name);//姓名
        etBirthday=(EditText) findViewById(R.id.et_person_baseinfo_birthday);//出生年月

        etOrigin=(EditText) findViewById(R.id.et_person_baseinfo_origin);//籍贯

        etPhone=(EditText) findViewById(R.id.et_person_baseinfo_phone);//电话
        etStreet=(EditText) findViewById(R.id.et_person_baseinfo_street);//街道
        etJwh=(EditText) findViewById(R.id.et_person_baseinfo_jwh);//居委会
        etSfz=(EditText) findViewById(R.id.et_person_baseinfo_sfz);//身份证
        etHujiRoad=(EditText) findViewById(R.id.et_person_baseinfo_huji_road);//户籍路
        etHujiNong=(EditText) findViewById(R.id.et_person_baseinfo_huji_nong);//户籍弄
        etHujiNum=(EditText) findViewById(R.id.et_person_baseinfo_huji_num);//户籍号
        etHujiRoom=(EditText) findViewById(R.id.et_person_baseinfo_huji_room);//户籍室
        etJuzhuRoad=(EditText) findViewById(R.id.et_person_baseinfo_juzhu_road);//居住路
        etJuzhuNong=(EditText) findViewById(R.id.et_person_baseinfo_juzhu_nong);//居住弄
        etJuzhuNum=(EditText) findViewById(R.id.et_person_baseinfo_juzhu_num);//居住号
        etJuzhuRoom=(EditText) findViewById(R.id.et_person_baseinfo_juzhu_room);//居住室
        etMark=(EditText) findViewById(R.id.et_person_baseinfo_mark);//备注

        btnTakePhoto.setOnClickListener(this);
        btnChoicePhoto.setOnClickListener(this);
        btnUploadPhoto.setOnClickListener(this);
        btnSaveInfo.setOnClickListener(this);
        btnMark.setOnClickListener(this);
        btnZxMark.setOnClickListener(this);

        initDatas();

    }

    private void initDatas(){

        spSexData=getResources().getStringArray(R.array.spinnersex);//性别
        spSex.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,spSexData));

        spNationData=getResources().getStringArray(R.array.person_national);//民族
        spNation.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,spNationData));

        spStateData=getResources().getStringArray(R.array.personstatus);//状态
        spState.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,spStateData));

        spIntentionData=getResources().getStringArray(R.array.personintention);//意向
        spIntention.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,spIntentionData));

        spEduData=getResources().getStringArray(R.array.spinner_wenhua);//文化程度
        spEdu.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,spEduData));

            etName.setText(personInfo.getNAME());
           // etSex.setText(personInfo.getSEX());
            etBirthday.setText(MyDateUtils.stringToYMD(personInfo.getBIRTH_DATE()));
         //   etNation.setText(personInfo.getNATIONS());
            etOrigin.setText(personInfo.getNATIVE());
          //  etState.setText(personInfo.getTYPE());
            etPhone.setText(personInfo.getCONTACT_NUMBER());
          //  etIntention.setText(personInfo.getCurrent_intent());
         //   etEdu.setText(personInfo.getCULTURAL_CODE());
         //   etMd.setText(personInfo.getCurrent_situation());
           etStreet.setText(personInfo.getCenter().getQ户口所属街道());
            etJwh.setText(personInfo.getCenter().getQ居委会());
            etSfz.setText(personInfo.getSFZ());
            etHujiRoad.setText(personInfo.getROAD());
            etHujiNong.setText(personInfo.getLANE());
            etHujiNum.setText(personInfo.getNO());
            etHujiRoom.setText(personInfo.getROOM());
            etJuzhuRoad.setText(personInfo.getNOW_ROAD());
            etJuzhuNong.setText(personInfo.getNOW_LANE());
            etJuzhuNum.setText(personInfo.getNOW_NO());
            etJuzhuRoom.setText(personInfo.getNOW_ROOM());
            etMark.setText(personInfo.getRemark());


        //获取头像
            getNetDatas("head",-1);
    }


    private void getNetDatas(final String sign, final int isFristWh){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
//http://web.youli.pw:89/Web/Personal/windows/ShowPic.aspx?sfz=422201197209204223 头像
                        String url=null;
                        if(TextUtils.equals(sign,"head")) {
                            url = MyOkHttpUtils.BaseUrl + "/Web/Personal/windows/ShowPic.aspx?sfz=" + personInfo.getSFZ();
                        }else if(TextUtils.equals(sign,"markimg")){
                            url=MyOkHttpUtils.BaseUrl+"/Json/Get_Tb_Mark.aspx?sfz="+personInfo.getSFZ();;
                        }else if(TextUtils.equals(sign,"mark")){
                            url=MyOkHttpUtils.BaseUrl+"/Json/Get_TB_Staff_Marks.aspx?sfz="+personInfo.getSFZ();
                        }else if(TextUtils.equals(sign,"whMarkInfo")){
                            url=MyOkHttpUtils.BaseUrl+"/Json/Get_TB_Staff_Mark_Type.aspx";
                        }
                        Message msg=Message.obtain();
                        Response response=MyOkHttpUtils.okHttpGet(url);

                        if(response!=null){
                            Gson gson=new Gson();
                            if(TextUtils.equals(sign,"head")) {

                                InputStream is = response.body().byteStream();
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inSampleSize = 2;//图片大小，设置越大，图片越不清晰，占用空间越小
                                Bitmap bmp = BitmapFactory.decodeStream(is, null, options);
                                msg.obj = bmp;
                                msg.what = SUCCEED_PHOTO;
                            }else if(TextUtils.equals(sign,"markimg")){
                                String resStr= null;
                                try {
                                    resStr = response.body().string();

                                    msg.obj=gson.fromJson(resStr,new TypeToken<List<MarkImgInfo>>(){}.getType());
                                    msg.what=SUCCEED_MARKIMG;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }else if(TextUtils.equals(sign,"mark")){

                                String resStr= null;
                                try {
                                    resStr = response.body().string();

                                    msg.what=SUCCEED_MARK;
                                    msg.obj=gson.fromJson(resStr,new TypeToken<List<StaffMarkInfo>>(){}.getType());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }else if(TextUtils.equals(sign,"whMarkInfo")){

                                String resStr=null;

                                try {
                                    resStr=response.body().string();
                                    msg.what=SUCCEED_MARK_WH;
                                    msg.arg1=isFristWh;
                                    msg.obj=gson.fromJson(resStr,new TypeToken<List<WhMarkInfo>>(){}.getType());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                        }else{
                            msg.what=PROBLEM;
                        }
                        mHandler.sendMessage(msg);
                    }
                }

        ).start();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_person_baseinfo_takephoto:

                Toast.makeText(mContext,"拍照",Toast.LENGTH_SHORT).show();

                break;

            case R.id.btn_person_baseinfo_choicephoto:

                Toast.makeText(mContext,"选择图片",Toast.LENGTH_SHORT).show();

                break;

            case R.id.btn_person_baseinfo_uploadphoto:

                showAlertDialog("uploadPhoto","uploadPhoto",-1);
                break;

            case R.id.btn_person_baseinfo_saveinfo:

                Toast.makeText(mContext,"保存信息",Toast.LENGTH_SHORT).show();

                break;

            case R.id.btn_person_baseinfo_biaoshi:

                //标识图片
                markDataDialog.clear();
                markDataDialog.addAll((List<MarkImgInfo> )markImgData);
                showEditDialog("mark");
                break;

            case R.id.btn_person_baseinfo_zhuanxiang_biaoshi:


                zxMarkDataDialog.clear();
                zxMarkDataDialog.addAll((List<StaffMarkInfo>)zxMarkData);
                //获取维护标识的数据
                getNetDatas("whMarkInfo",10000);

                break;
        }

    }


    private void showEditDialog(final String sign){//两个编辑标识的对话框

        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);

        View view= LayoutInflater.from(mContext).inflate(R.layout.dialog_person_baseinfo_mark,null,false);

        builder.setView(view);

        editDialog=builder.create();

        editDialog.show();
        editDialog.setCanceledOnTouchOutside(false);

        final Spinner sp= (Spinner) view.findViewById(R.id.sp_dialog_person_baseinfo_mark);
        final Button addBtn= (Button) view.findViewById(R.id.btn_dialog_person_baseinfo_mark_add);
        final Button whBtn= (Button) view.findViewById(R.id.btn_dialog_person_baseinfo_mark_wh);
        Button modifyBtn= (Button) view.findViewById(R.id.btn_dialog_person_baseinfo_mark_modify);
        Button closeBtn= (Button) view.findViewById(R.id.btn_dialog_person_baseinfo_mark_close);
        TextView dateTv= (TextView) view.findViewById(R.id.tv_dialog_person_baseinfo_date);
        ListView lv= (ListView) view.findViewById(R.id.lv_dialog_person_baseinfo_mark);

        if(TextUtils.equals(sign,"mark")){
            whBtn.setVisibility(View.GONE);
            dateTv.setText("备注");
            markArray=getResources().getStringArray(R.array.spinner_identifying);
            sp.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,markArray));
            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    markArrayStr =markArray[position];

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else if(TextUtils.equals(sign,"zxMark")){
            markArrayStr="请选择";
            whBtn.setVisibility(View.VISIBLE);
            dateTv.setText("标识日期");
            sp.setPrompt("请选择");
           sp.setAdapter(new WhSpAdapter(mContext,zxMarkWhData));
            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    markArrayStr =zxMarkWhData.get(position).getNAME();

                  //  Toast.makeText(mContext,"==="+markArrayStr,Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.equals("请选择",markArrayStr)){
                    Toast.makeText(mContext,"请选择标识类型",Toast.LENGTH_SHORT).show();
                }else{

                    if(TextUtils.equals(sign,"mark")){

                     if(!judmentMark(markDataDialog,markArrayStr)){

//                       if(markDataDialog.size()==1&&markDataDialog.get(0).getMARK()==null){
//                           markDataDialog.clear();
//                       }



                         MarkImgInfo mInfo=new MarkImgInfo();
                         mInfo.setMARK(markArrayStr);
                         mInfo.setSOURCE("现场采集");
                         mInfo.setSFZ(personInfo.getSFZ());
                         mInfo.setGPS("0.0.0.0");
                         SimpleDateFormat sdf = new SimpleDateFormat(
                                 "yyyy-MM-dd HH:mm");
                         String markDate = sdf.format(new Date());
                         mInfo.setCREATE_DATE(markDate);
                               markDataDialog.add(mInfo);

                                markAdapter.notifyDataSetChanged();
                     }



                    }else if(TextUtils.equals(sign,"zxMark")) {
                        int itemId=0;

                        for(WhMarkInfo whInfo:zxMarkWhData){

                            if(markArrayStr==whInfo.getNAME()){
                                itemId=whInfo.getID();
                            }
                        }

                        if(!judmentZxMark(zxMarkDataDialog,markArrayStr)){

//                       if(markDataDialog.size()==1&&markDataDialog.get(0).getMARK()==null){
//                           markDataDialog.clear();
//                       }

                            StaffMarkInfo mInfo=new StaffMarkInfo();
                            mInfo.setSFZ(personInfo.getSFZ());
                           mInfo.setTYPE(itemId);
                            mInfo.setType_Name(markArrayStr);
                            SimpleDateFormat sdf = new SimpleDateFormat(
                                    "yyyy-MM-dd HH:mm");
                            String markDate = sdf.format(new Date());
                            mInfo.setUPDATE_DATE(markDate);

                            zxMarkDataDialog.add(mInfo);
                           markZxAdapter.notifyDataSetChanged();
                        }

                    }
                }

            }
        });


        whBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showWhDialog();//维护标识的对话框

            }
        });


        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sign==mark就是标识，sign==zxMark就是专项标识
                showAlertDialog(sign,"modify",-1);
            }
        });


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editDialog.dismiss();//关闭
            }
        });


        if(TextUtils.equals(sign,"mark")){


            if(markAdapter==null) {

                markAdapter=new CommonAdapter<MarkImgInfo>(mContext,markDataDialog,R.layout.item_person_baseinfo_mark) {

                  @Override
                  public void convert(CommonViewHolder holder, MarkImgInfo item, final int position) {
                                      LinearLayout ll=holder.getView(R.id.ll_item_person_baseinfo_mark);

                TextView noTv=holder.getView(R.id.tv_item_person_baseinfo_mark_no);
                noTv.setText((position+1)+"");
                TextView nameTv=holder.getView(R.id.tv_item_person_baseinfo_mark_name);
                nameTv.setText(markDataDialog.get(position).getMARK());
                TextView dateTv=holder.getView(R.id.tv_item_person_baseinfo_mark_date);
                dateTv.setText(markDataDialog.get(position).getCREATE_DATE());
                Button deleteBtn=holder.getView(R.id.btn_item_person_baseinfo_mark_delete);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                           deleteEditMark(sign, position, markAdapter);//编辑标识对话框里面的删除

                    }
                });

                if (position % 2 == 0) {
                    ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item1);
                } else if (position % 2 != 0) {
                    ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item2);
                }

                    deleteBtn.setTextColor(Color.parseColor("#ff0000"));
                    dateTv.setVisibility(View.GONE);

                  }
              };

                }

           lv.setAdapter(markAdapter);

        }else if(TextUtils.equals(sign,"zxMark")){

            if(markZxAdapter==null){

               markZxAdapter=new CommonAdapter<StaffMarkInfo>(mContext,zxMarkDataDialog,R.layout.item_person_baseinfo_mark) {

                   @Override
                   public void convert(CommonViewHolder holder, StaffMarkInfo item, final int position) {


                           LinearLayout ll = holder.getView(R.id.ll_item_person_baseinfo_mark);

                           TextView noTv = holder.getView(R.id.tv_item_person_baseinfo_mark_no);
                           noTv.setText((position + 1) + "");
                           TextView nameTv = holder.getView(R.id.tv_item_person_baseinfo_mark_name);
                           nameTv.setText(zxMarkDataDialog.get(position).getType_Name() != null ? zxMarkDataDialog.get(position).getType_Name() : "null");
                           TextView dateTv = holder.getView(R.id.tv_item_person_baseinfo_mark_date);
                           dateTv.setText(MyDateUtils.stringToYMD(zxMarkDataDialog.get(position).getUPDATE_DATE()));
                           Button deleteBtn = holder.getView(R.id.btn_item_person_baseinfo_mark_delete);
                           deleteBtn.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {

                                   deleteEditMark(sign, position, null);//编辑标识对话框里面的删除
                               }
                           });

                           if (position % 2 == 0) {
                               ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item1);
                           } else if (position % 2 != 0) {
                               ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item2);
                           }

                           deleteBtn.setTextColor(Color.parseColor("#000000"));
                           dateTv.setVisibility(View.VISIBLE);

                       }
                   };

            }
            lv.setAdapter(markZxAdapter);

        }


    }

    private void showWhDialog(){//维护标识的对话框
        //获取维护标识的数据
        getNetDatas("whMarkInfo",-1);

        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);

        View view=LayoutInflater.from(mContext).inflate(R.layout.dialog_person_baseinfo_wh,null,false);

        builder.setView(view);

        whDialog =builder.create();

        whDialog.show();

        whDialog.setCanceledOnTouchOutside(false);

        Button btnAdd= (Button) view.findViewById(R.id.btn_dialog_person_baseinfo_wh_add);
        Button btnUpdate= (Button) view.findViewById(R.id.btn_dialog_person_baseinfo_wh_update);
        Button btnDelete= (Button) view.findViewById(R.id.btn_dialog_person_baseinfo_wh_delete);
        Button btnClose= (Button) view.findViewById(R.id.btn_dialog_person_baseinfo_wh_close);

        final EditText etName= (EditText) view.findViewById(R.id.et_dialog_person_baseinfo_wh_name);

      //  final String etNameStr=etName.getText().toString().trim();


        ListView lv= (ListView) view.findViewById(R.id.lv_dialog_person_baseinfo_wh);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etNameStr=etName.getText().toString().trim();
                showAlertDialog("add",etNameStr,-1);//维护标识里面的添加

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etNameStr=etName.getText().toString().trim();
                showAlertDialog("update",etNameStr,whItemId);//维护标识里面的修改
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etNameStr=etName.getText().toString().trim();
                showAlertDialog("delete",etNameStr,whItemId);//维护标识里面的删除
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                whDialog.dismiss();
            }
        });


        lv.setAdapter(new CommonAdapter<WhMarkInfo>(mContext,zxMarkWhData,R.layout.item_person_baseinfo_mark) {
            @Override
            public void convert(CommonViewHolder holder, WhMarkInfo item, final int position) {

                LinearLayout ll=holder.getView(R.id.ll_item_person_baseinfo_mark);

                TextView noTv=holder.getView(R.id.tv_item_person_baseinfo_mark_no);
                TextView nameTv=holder.getView(R.id.tv_item_person_baseinfo_mark_name);
                nameTv.setText(zxMarkWhData.get(position).getNAME());
                TextView dateTv=holder.getView(R.id.tv_item_person_baseinfo_mark_date);
                Button deleteBtn=holder.getView(R.id.btn_item_person_baseinfo_mark_delete);


                if (position % 2 == 0) {
                    ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item1);
                } else if (position % 2 != 0) {
                    ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item2);
                }

                noTv.setVisibility(View.GONE);
                deleteBtn.setVisibility(View.GONE);
                dateTv.setVisibility(View.GONE);

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                whItemId=zxMarkWhData.get(position).getID();

                etName.setText(zxMarkWhData.get(position).getNAME());

            }
        });

    }

    private void showAlertDialog(final String sign, final String name, final int id){//提示对话框

        if(TextUtils.equals(name,"")&&TextUtils.equals(sign,"add")){
            Toast.makeText(mContext,"名称不能为空！"+name,Toast.LENGTH_SHORT).show();
            return;
        }

        if((TextUtils.equals(sign,"update")||TextUtils.equals(sign,"delete"))) {
       // if(TextUtils.equals(sign,"delete")) {
            if(!itemContentMatch(id)){//维护标识对话框的删除,修改时，判断内容是否和列表内容匹配
                Toast.makeText(mContext,"请先选择条目！",Toast.LENGTH_SHORT).show();
                return;
            };
        }



     //   Toast.makeText(mContext,"名称=="+name,Toast.LENGTH_SHORT).show();

        String msgStr = null;

        String titleStr=null;

        if(TextUtils.equals(sign,"add")){//维护标识里面的添加
            msgStr="您确定要添加";
            titleStr="温馨提示";
        }else if(TextUtils.equals(sign,"update")){
            msgStr="您确定要修改";
            titleStr="温馨提示";
        }else if(TextUtils.equals(sign,"delete")){
            msgStr="您确定要删除";
            titleStr="温馨提示";
        }else if(TextUtils.equals(sign,"mark")){
            msgStr="您确认修改的标识上传服务器";
            titleStr="修改标识提示";
        }else if(TextUtils.equals(sign,"uploadPhoto")){
            msgStr="您确定要把头像，上传服务器";
            titleStr="上传头像确认提示";
        }else if(TextUtils.equals(sign,"zxMark")) {
            msgStr = "您确认修改的标识上传服务器";
            titleStr = "修改标识提示";
        }
            AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        builder.setTitle(titleStr);
        builder.setMessage(msgStr+"吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //sign==mark就是标识，sign==zxMark就是专项标识
               // if(TextUtils.equals(sign,"mark")){

                   // Toast.makeText(mContext,"标识",Toast.LENGTH_SHORT).show();



//                }else if(TextUtils.equals(sign,"zxMark")){
//                    Toast.makeText(mContext,"专项标识",Toast.LENGTH_SHORT).show();
//                }
                if(TextUtils.equals(sign,"add")){//维护标识里面的添加
                    addWhMark(sign,name);
                }else if(TextUtils.equals(sign,"delete")||TextUtils.equals(sign,"update")){
                       delOrUpDateWhMark(sign,id,name);//维护标识里面的删除或者修改
                }else if(TextUtils.equals(sign,"mark")){//上传标识
                    upLoadMark();
                }else if(TextUtils.equals(sign,"uploadPhoto")){

                }else if(TextUtils.equals(sign,"zxMark")) {//上传专项标识
                    upLoadZxMark();
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){

            case R.id.sp_person_baseinfo_sex://性别
                break;
            case R.id.sp_person_baseinfo_nation://民族
                break;
            case R.id.sp_person_baseinfo_state://状态

                if(TextUtils.equals((String)spState.getSelectedItem(),"登记失业")){
                    spMdData=getResources().getStringArray(R.array.personmodishiye);
                }else if(TextUtils.equals((String)spState.getSelectedItem(),"未登记失业")){
                    spMdData=getResources().getStringArray(R.array.personmodiwuye);
                }else if(TextUtils.equals((String)spState.getSelectedItem(),"学龄前儿童")){
                    spMdData=getResources().getStringArray(R.array.personmodiwuye);
                }

                if(spMdData!=null) {
                    spMd.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spMdData));
                }
                break;
            case R.id.sp_person_baseinfo_intention://当前意向
                break;
            case R.id.sp_person_baseinfo_edu://文化程度
                break;
            case R.id.sp_person_baseinfo_md://摸底情况
                break;

        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()){

            case R.id.cb_person_baseinfo_huji:

                if(cbHuji.isChecked()){
                    cbJuzhu.setChecked(false);
                }

                break;

            case R.id.cb_person_baseinfo_juzhu:

                if(cbJuzhu.isChecked()){
                    cbHuji.setChecked(false);
                }
                break;
        }

    }

    private void deleteEditMark(String sign,int position,CommonAdapter markAdapter){//编辑标识对话框里面的删除

        if(TextUtils.equals(sign,"mark")){//删除标识

          //  Toast.makeText(mContext,"删除标识第"+position+"个",Toast.LENGTH_SHORT).show();

            markDataDialog.remove(position);

            markAdapter.notifyDataSetChanged();

        }else if(TextUtils.equals(sign,"zxMark")){//删除专项标识

            zxMarkDataDialog.remove(position);

            markZxAdapter.notifyDataSetChanged();
        }

    }

    private boolean judmentMark(List<MarkImgInfo> info,String markName){//判断标识是否含有

        for(int i=0;i<info.size();i++){

            String name=info.get(i).getMARK();

            if(name.equals(markName)){

                Toast.makeText(mContext,
                        "对不起，不能重复添加标识,请重新选择", Toast.LENGTH_SHORT).show();
                return  true;
            }

        }

        return false;
    }


    private boolean judmentZxMark(List<StaffMarkInfo> info,String markName){//判断专项标识是否含有

        for(int i=0;i<info.size();i++){

            String name=info.get(i).getType_Name();
            if(name.equals(markName)){

                Toast.makeText(mContext,
                        "对不起，不能重复添加标识,请重新选择", Toast.LENGTH_SHORT).show();
                return  true;
            }

        }

        return false;
    }

    //上传标识
    private void upLoadMark() {

        new Thread(new Runnable() {
            @Override
            public void run() {


                String upLoadUrl;

                //Log.e("2017-9-13", "我的json==" + markData);

                    JSONArray jsonArray = new JSONArray();
                    JSONObject jsonObj;
                    try {
                    if(markDataDialog.size()>0){

                    for (MarkImgInfo mInfo : markDataDialog) {
                        jsonObj = new JSONObject();
                            jsonObj.put("MARK", mInfo.getMARK());
                            jsonObj.put("SOURCE", mInfo.getSOURCE());
                            jsonObj.put("CREATE_DATE", mInfo.getCREATE_DATE());
                            jsonObj.put("SFZ", mInfo.getSFZ());
                            jsonObj.put("GPS", mInfo.getGPS());
                            jsonArray.put(jsonObj);
                    }
                    }else {
                        jsonObj = new JSONObject();
                               jsonObj.put("ID", "-1");
                                jsonObj.put("SFZ", personInfo.getSFZ());
                                markDataDialog.clear();
                        jsonArray.put(jsonObj);
                    }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    upLoadUrl = MyOkHttpUtils.BaseUrl + "/Json/Set_Tb_Mark.aspx";
                  //  Log.e("2017-9-13", "上传的数据==" + jsonArray);
                    Response response=MyOkHttpUtils.okHttpPersonRePost(upLoadUrl,jsonArray.toString());
                    Message msg=Message.obtain();


                    if(response.isSuccessful()){


                        try {
                            msg.obj=response.body().string();
                            msg.what=SUCCEED_MARKIMG_UPLOAD;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }else{


                        msg.what=PROBLEM;
                    }

                    mHandler.sendMessage(msg);

                }

        }).start();
    }

    //上传专项标识
    private void upLoadZxMark() {

        new Thread(new Runnable() {
            @Override
            public void run() {


                String upLoadUrl;

                //Log.e("2017-9-13", "我的json==" + markData);


                    JSONArray jsonArray = new JSONArray();
                    JSONObject jsonObj;
                    try {
                        if(zxMarkDataDialog.size()>0){

                            for (StaffMarkInfo mInfo : zxMarkDataDialog) {
                                jsonObj = new JSONObject();
                                jsonObj.put("TYPE", mInfo.getTYPE());
                                jsonObj.put("UPDATE_DATE", mInfo.getUPDATE_DATE());
                                jsonObj.put("SFZ", mInfo.getSFZ());
                                jsonArray.put(jsonObj);
                            }
                        }else {
                           // jsonObj = new JSONObject();
                            zxMarkDataDialog.clear();
                          //  jsonArray.put(null);
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                    upLoadUrl = MyOkHttpUtils.BaseUrl + "/Json/Set_TB_Staff_Marks.aspx";

                Log.e("2017-9-13", "上传的json==" +jsonArray);

                byte [] upLoadStream=jsonArray.toString().getBytes();

                     Log.e("2017-9-13", "上传的数据==" + upLoadStream);
                    Response response=MyOkHttpUtils.okHttpZxMarkPost(upLoadUrl,upLoadStream);//这里要上传数据流还有问题
                    Message msg=Message.obtain();


                if(response.isSuccessful()){


                        try {
                            msg.obj=response.body().string();
                            msg.what=SUCCEED_MARK_UPLOAD;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }else{


                        msg.what=PROBLEM;
                    }

                    mHandler.sendMessage(msg);

                }

        }).start();
    }

    //维护标识的添加保存
    //http://web.youli.pw:89/Json/Set_TB_Staff_Mark_Type.aspx?name=123
    private void addWhMark(String sign, final String name){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                        String urlAdd=MyOkHttpUtils.BaseUrl+"/Json/Set_TB_Staff_Mark_Type.aspx";

                        Response response=MyOkHttpUtils.okHttpWhMarkAddPost(urlAdd,name);
                        Message msg=Message.obtain();


                        if(response.isSuccessful()){

                            try {
                                msg.obj=response.body().string();
                                msg.what=SUCCEED_MARK_WH_ADD;
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

  private boolean  itemContentMatch(int id){//维护标识对话框的删除,修改时，判断内容是否和列表内容匹配

      for(WhMarkInfo info:zxMarkWhData){

            if(id==info.getID()){

                 return true;
            }


      }

      return false;
  }


    private void delOrUpDateWhMark(final String sign, final int id, final String name){//维护标识里面的删除，修改

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {


                         //删除
                       // http://web.youli.pw:89/Json/Set_TB_Staff_Mark_Type.aspx?id=37&del=true
                      String url=MyOkHttpUtils.BaseUrl+"/Json/Set_TB_Staff_Mark_Type.aspx";

                      Response response=null;
                        if(TextUtils.equals(sign,"delete")) {
                            response = MyOkHttpUtils.okHttpWhMarkDelPost(url, id + "");
                        }else if(TextUtils.equals(sign,"update")){
                            response = MyOkHttpUtils.okHttpWhMarkModifyPost(url, id + "",name);
                        }
                        Message msg=Message.obtain();

                        if(response.isSuccessful()){
                            try {
                                msg.obj=response.body().string();
                                if(TextUtils.equals(sign,"delete")) {
                                    msg.what = SUCCEED_MARK_WH_DEL;
                                }else if(TextUtils.equals(sign,"update")){
                                    msg.what = SUCCEED_MARK_WH_CHANGE;
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


}

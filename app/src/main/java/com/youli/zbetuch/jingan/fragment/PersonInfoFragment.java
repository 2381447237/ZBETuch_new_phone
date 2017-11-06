package com.youli.zbetuch.jingan.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.entity.MarkImgInfo;
import com.youli.zbetuch.jingan.entity.PersonInfo;
import com.youli.zbetuch.jingan.entity.StaffMarkInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Response;

/**
 * Created by ZHengBin on 2017/8/12.
 */

public class PersonInfoFragment extends Fragment implements View.OnClickListener{

    private final int SUCCEED_MARK=10000;
    private final int SUCCEED_MARKIMG=10001;
    private final int SUCCEED_NODATA=10002;
    private final int  PROBLEM=10003;

    private PersonInfo pInfo;

    public static final PersonInfoFragment newInstance(PersonInfo pInfo){

        PersonInfoFragment fragment = new PersonInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("pInfo",pInfo);
        fragment.setArguments(bundle);

        return fragment;
    }

    private LinearLayout markLl;
    private View contentView;
    private PopupWindow popupWin;
    private View markView;
    private ImageView yjbyshIv;//应届毕业生
    private ImageView jdryIv;//戒毒人员
    private ImageView jyknIv;//就业困难
    private ImageView qhryIv;//启航人员
    private ImageView wjyjIv;//零就业家庭
    private ImageView xjryIv;//刑解人员

    private SimpleDateFormat sdf;
    private Date date;
    private TextView nameTv,sexTv,birthdayTv,nationTv,jiguanTv,stateTv,
            sfzTv,currIntTv,eduTv,mdTv,jdTv,jwhTv,hujiTv,phoneTv,juzhuTv,
            markTv,upDateTimeTv,overdueTv;

    private List<StaffMarkInfo> markData=new ArrayList<>();
    private List<MarkImgInfo> markImgData=new ArrayList<>();

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCEED_MARK:

                    markData.addAll(( List<StaffMarkInfo>)msg.obj);

                    if(getActivity()==null){
                        return;
                    }

                    for(StaffMarkInfo info : markData){

                        TextView tv=new TextView(getActivity());
            tv.setText(info.getType_Name());
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(lp);
            tv.setPadding(0,0,10,0);
            tv.setTextSize(16);
            markLl.addView(tv);

                    }

                    break;

                case SUCCEED_MARKIMG:

                    markImgData.addAll((List<MarkImgInfo>)msg.obj);

                    yjbyshIv.setVisibility(View.GONE);
                    qhryIv.setVisibility(View.GONE);
                    xjryIv.setVisibility(View.GONE);
                    jdryIv.setVisibility(View.GONE);
                    wjyjIv.setVisibility(View.GONE);
                    jyknIv.setVisibility(View.GONE);

                    for(MarkImgInfo info:markImgData){

                        if(TextUtils.equals(info.getMARK(),"应届毕业生")){
                            yjbyshIv.setVisibility(View.VISIBLE);
                        }
                        if(TextUtils.equals(info.getMARK(),"启航人员")){
                            qhryIv.setVisibility(View.VISIBLE);
                        }
                        if(TextUtils.equals(info.getMARK(),"刑解人员")){
                            xjryIv.setVisibility(View.VISIBLE);
                        }
                        if(TextUtils.equals(info.getMARK(),"戒毒人员")){
                            jdryIv.setVisibility(View.VISIBLE);
                        }
                        if(TextUtils.equals(info.getMARK(),"零就业家庭")){
                            wjyjIv.setVisibility(View.VISIBLE);
                        }
                        if(TextUtils.equals(info.getMARK(),"就业困难人员")){
                            jyknIv.setVisibility(View.VISIBLE);
                        }
                    }

                    break;



                case PROBLEM:
                    if(getActivity()!=null) {
                        Toast.makeText(getActivity(), "网络不给力", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case SUCCEED_NODATA:

                    break;
            }

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pInfo=(PersonInfo)getArguments().getSerializable("pInfo");
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        contentView=LayoutInflater.from(getContext()).inflate(R.layout.framgment_personal_info,container,false);


        if(pInfo!=null) {
            initViews(contentView);
        }
        return contentView;
    }



    private void initViews(View view){

        nameTv= (TextView) view.findViewById(R.id.tv_person_info_name);
        sexTv= (TextView) view.findViewById(R.id.tv_person_info_sex);
        birthdayTv= (TextView) view.findViewById(R.id.tv_person_info_birthday);
        nationTv= (TextView) view.findViewById(R.id.tv_person_info_nation);
        jiguanTv= (TextView) view.findViewById(R.id.tv_person_info_jiguan);
        stateTv= (TextView) view.findViewById(R.id.tv_person_info_state);
        sfzTv= (TextView) view.findViewById(R.id.tv_person_info_sfz);
        currIntTv= (TextView) view.findViewById(R.id.tv_person_info_curr_intent);
        eduTv= (TextView) view.findViewById(R.id.tv_person_info_edu);
        mdTv= (TextView) view.findViewById(R.id.tv_person_info_md);
        jdTv= (TextView) view.findViewById(R.id.tv_person_info_jd);
        jwhTv= (TextView) view.findViewById(R.id.tv_person_info_jwh);
        hujiTv= (TextView) view.findViewById(R.id.tv_person_info_huji);
        phoneTv= (TextView) view.findViewById(R.id.tv_person_info_phone);
        juzhuTv= (TextView) view.findViewById(R.id.tv_person_info_juzhu);
        markTv= (TextView) view.findViewById(R.id.tv_person_info_mark);
        upDateTimeTv= (TextView) view.findViewById(R.id.tv_person_info_update_time);
        overdueTv= (TextView) view.findViewById(R.id.tv_person_info_overdue);
        overdueTv.setVisibility(View.GONE);

        markLl= (LinearLayout) view.findViewById(R.id.ll_person_special_mark);

        nameTv.setText(pInfo.getNAME());
        sexTv.setText(pInfo.getSEX());
        birthdayTv.setText(pInfo.getBIRTH_DATE().split("T")[0]);
        nationTv.setText(pInfo.getNATIONS());
        jiguanTv.setText(pInfo.getNATIVE());
        stateTv.setText(pInfo.getTYPE());
        sfzTv.setText(pInfo.getSFZ());
        currIntTv.setText(pInfo.getCurrent_intent());
        eduTv.setText(pInfo.getCULTURAL_CODE());
        mdTv.setText(pInfo.getCurrent_situation());
        jdTv.setText(pInfo.getCenter().getQ户口所属街道());
        jwhTv.setText(pInfo.getCenter().getQ居委会());
        hujiTv.setText(pInfo.getCenter().getQ户口地址());
        phoneTv.setText(pInfo.getCONTACT_NUMBER());
        juzhuTv.setText(pInfo.getNOW_ROAD()+pInfo.getNOW_LANE()+pInfo.getNOW_NO()+pInfo.getNOW_ROOM());
        markTv.setText(pInfo.getRemark());

        if(TextUtils.equals(pInfo.getCenter().getCOMPARE_RESULT(),"缺失")){
            overdueTv.setVisibility(View.VISIBLE);
        }else{
            overdueTv.setVisibility(View.GONE);
        }


        try {
            sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(pInfo.getCenter().getCREATE_DATE().replace("T"," "));
            upDateTimeTv.setText(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }



       yjbyshIv= (ImageView) view.findViewById(R.id.iv_person_info_yjbysh);
        yjbyshIv.setVisibility(View.GONE);
        yjbyshIv.setOnClickListener(this);
        jdryIv= (ImageView) view.findViewById(R.id.iv_person_info_jdry);
        jdryIv.setVisibility(View.GONE);
        jdryIv.setOnClickListener(this);
        jyknIv= (ImageView) view.findViewById(R.id.iv_person_info_jykn);
        jyknIv.setVisibility(View.GONE);
        jyknIv.setOnClickListener(this);
         qhryIv= (ImageView) view.findViewById(R.id.iv_person_info_qhry);
        qhryIv.setVisibility(View.GONE);
        qhryIv.setOnClickListener(this);
        wjyjIv= (ImageView) view.findViewById(R.id.iv_person_info_wjyj);
        wjyjIv.setVisibility(View.GONE);
        wjyjIv.setOnClickListener(this);
         xjryIv= (ImageView) view.findViewById(R.id.iv_person_info_xjry);
        xjryIv.setVisibility(View.GONE);
        xjryIv.setOnClickListener(this);

        //专项标识的URL：http://web.youli.pw:89/Json/Get_TB_Staff_Marks.aspx?sfz=310108198004026642

        getMarksInfo();

        getMarksImg();
    }

    //获得标识图标的信息
    private void getMarksImg(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
//http://web.youli.pw:89/Json/Get_Tb_Mark.aspx?sfz=310108198004026642
                        String urlMarkImg=MyOkHttpUtils.BaseUrl+"/Json/Get_Tb_Mark.aspx?sfz="+pInfo.getSFZ();

                        Response response=MyOkHttpUtils.okHttpGet(urlMarkImg);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String resStr=response.body().string();

                                if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")) {

                                    Gson gson = new Gson();

                                    msg.obj = gson.fromJson(resStr, new TypeToken<List<MarkImgInfo>>() {
                                    }.getType());
                                    msg.what = SUCCEED_MARKIMG;
                                }else{
                                    msg.what = SUCCEED_NODATA;
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

    //获得专项标识
    private void getMarksInfo(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String markUrl= MyOkHttpUtils.BaseUrl+"/Json/Get_TB_Staff_Marks.aspx?sfz="+pInfo.getSFZ();

                        Response response=MyOkHttpUtils.okHttpGet(markUrl);

                        Message  msg=Message.obtain();

                        if(response!=null){

                            try {
                                String resStr=response.body().string();
                                if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")) {
                                    Gson gson = new Gson();

                                    msg.what = SUCCEED_MARK;
                                    msg.obj = gson.fromJson(resStr, new TypeToken<List<StaffMarkInfo>>() {
                                    }.getType());
                                }else{
                                    msg.what = SUCCEED_NODATA;
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

    @Override
    public void onClick(View v) {

switch (v.getId()){

    case R.id.iv_person_info_yjbysh:
        showPopupWindow(v,"应届毕业生");
        break;
    case R.id.iv_person_info_jdry:
        showPopupWindow(v,"戒毒人员");
        break;
    case R.id.iv_person_info_jykn:
        showPopupWindow(v,"就业困难人员");
        break;
    case R.id.iv_person_info_qhry:
        showPopupWindow(v,"启航人员");
        break;
    case R.id.iv_person_info_wjyj:
        showPopupWindow(v,"零就业家庭");
        break;
    case R.id.iv_person_info_xjry:
        showPopupWindow(v,"刑解人员");
        break;

}

    }

    private void showPopupWindow(View parent,String markStr){
      if(getActivity()==null){
          return;
      }
       if(popupWin==null){
           markView=LayoutInflater.from(getActivity()).inflate(R.layout.popup_person_mark,null);

           popupWin=new PopupWindow(markView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       }

        popupWin.setFocusable(true);
        popupWin.setOutsideTouchable(true);
        popupWin.setBackgroundDrawable(new BitmapDrawable());
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
      //  popupWin.setWindowLayoutMode();
        popupWin.showAsDropDown(parent,-250,10);

        TextView markNameTv= (TextView) markView.findViewById(R.id.tv_markname_popup);
        TextView markSourceTv= (TextView) markView.findViewById(R.id.tv_marksource_popup);
        TextView markTimeTv= (TextView) markView.findViewById(R.id.tv_marktime_popup);

        for(MarkImgInfo info:markImgData){

            if(TextUtils.equals(markStr,info.getMARK())){
                markNameTv.setText(info.getMARK());
                markSourceTv.setText(info.getSOURCE());

                try {
                    sdf=new SimpleDateFormat("yyyy-MM-dd");
                    date=sdf.parse(info.getCREATE_DATE().replace("T"," "));
                    markTimeTv.setText(sdf.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                break;
            }

        }

    }

}

package com.youli.zbetuch.jingan.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.entity.ComListInfo;
import com.youli.zbetuch.jingan.entity.ComNaireAnswerInfo;
import com.youli.zbetuch.jingan.entity.ComNaireInfo;
import com.youli.zbetuch.jingan.entity.ComPersonInfo;
import com.youli.zbetuch.jingan.naire.AnswerInfo;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import com.youli.zbetuch.jingan.utils.SharedPreferencesUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/11/15.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 企业调查里面的问卷
 */

public class ComNaireDetailActivity extends BaseActivity implements View.OnClickListener{


    private final int SUCCESS_NEW_PERSON=10001;//新建调查人
    private final int FAIL=10003;//失败

    private boolean isTablet=true;//判断是平板，还是手机

    private Context mContext=this;
    private LinearLayout bigll;
    private Button btnStart,btnNext,btnLast,btnAll,btnRestart,btnSubmit;

    private List<ComNaireInfo.DetilsBean> questionDetailsList;//问卷的信息

    private List<ComNaireInfo.DetilsBean> juanInfos = new ArrayList<>();//问卷的信息

    private List<ComNaireInfo.DetilsBean> questionTitleList = new ArrayList<>();//问题
    private List<ComNaireInfo.DetilsBean> answerInfo = new ArrayList<>();//选项

    private int tempQuestionIndex = 0;// 用于标识上一题的题号
    // 用于保存上一步回答的题目
    private List<Integer> tempList = new ArrayList<Integer>();
    private ComNaireInfo.DetilsBean currentInfo;// 用于表示当前题
    private int index = 0;//问题在集合中的索引

    private List<RadioButton> radioButtons = new ArrayList<>();//单选的答案
    private List<CheckBox> this_CheckBoxs = new ArrayList<CheckBox>();//多选的答案
    // 保存小题的编辑框
    private List<EditText> editTexts = new ArrayList<EditText>();
    // 保存大题的编辑框
    private List<EditText> questionEditTexts = new ArrayList<EditText>();
    private List<TextView> questionTextViews = new ArrayList<TextView>();

    // 保存所选的答案和题号
    private List<AnswerInfo> answerInfos2 = new ArrayList<AnswerInfo>();

    private ComPersonInfo pInfo;//个人信息
    private ComListInfo comInfo;//企业信息
    private String birthDay;

    private byte [] shujuliu;//答案数据流
    private List<ComNaireAnswerInfo> aInfo=new ArrayList<>();//上个界面传递过来的答案信息

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case SUCCESS_NEW_PERSON://新建调查人
                    EventBus.getDefault().post(new ComListInfo());//通知企业列表界面刷新

                    //上传答案
                    submit((String)msg.obj);

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
        setContentView(R.layout.activity_com_naire_detail);

        isTablet=isTabletDevice(this);
        aInfo=(List<ComNaireAnswerInfo>)getIntent().getSerializableExtra("aInfo");//上个界面传递过来的答案信息

        pInfo=(ComPersonInfo) getIntent().getSerializableExtra("pInfo");//个人信息

        birthDay=getIntent().getStringExtra("birthDay");

        Log.e("2017/11/28","生日"+birthDay);

        Log.e("2017/11/24","个人信息的个人ID="+pInfo.getID());

        comInfo=(ComListInfo) getIntent().getSerializableExtra("comInfo");//企业信息
        questionDetailsList=((ComNaireInfo)getIntent().getSerializableExtra("wenjuan")).getDetils();

        initViews();

        if(aInfo!=null&&aInfo.size()>0){//已查
            btnStart.setVisibility(View.GONE);

            bigll.removeAllViews();
            addTitle();

            if(questionDetailsList.size()>0) {
                juanInfos.addAll(questionDetailsList);
            }
            //添加问题
            questionTitleList = getQuestionByParent();
            for(ComNaireInfo.DetilsBean info:questionTitleList){
                fretchTree(bigll,info,"all");
            }
        }else{//未查
            addTitle();
        }



    }

    private void addTitle(){

        String number=((ComNaireInfo) (getIntent().getSerializableExtra("wenjuan"))).getNO();
        String date=((ComNaireInfo) (getIntent().getSerializableExtra("wenjuan"))).getCREATE_TIME().split("T")[0];
        String title=((ComNaireInfo) (getIntent().getSerializableExtra("wenjuan"))).getTITLE();
        //String content=((ComNaireInfo) (getIntent().getSerializableExtra("wenjuan"))).getQUESTION_TEST();
        View titleView= LayoutInflater.from(this).inflate(R.layout.com_question_title,null);

        TextView numberTv= (TextView) titleView.findViewById(R.id.title_tv_number);//试卷编号
        TextView numberFtv= (TextView) titleView.findViewById(R.id.title_tv_number_fianl);
        TextView nameTv= (TextView) titleView.findViewById(R.id.title_tv_name);//姓名
        TextView nameFtv= (TextView) titleView.findViewById(R.id.title_tv_name_fianl);
        TextView dateTv= (TextView) titleView.findViewById(R.id.title_tv_date);//访问日期
        TextView dateFtv= (TextView) titleView.findViewById(R.id.title_tv_date_fianl);
        TextView titleTv= (TextView) titleView.findViewById(R.id.title_tv_title);//问卷标题
        TextView contentTv= (TextView) titleView.findViewById(R.id.title_tv_content);//问卷介绍
        if(isTablet){
            numberTv.setTextSize(18);
            numberFtv.setTextSize(18);
            nameTv.setTextSize(18);
            nameFtv.setTextSize(18);
            dateTv.setTextSize(18);
            dateFtv.setTextSize(18);
            titleTv.setTextSize(19);
        }else{
            numberTv.setTextSize(14);
            numberFtv.setTextSize(14);
            nameTv.setTextSize(14);
            nameFtv.setTextSize(14);
            dateTv.setTextSize(14);
            dateFtv.setTextSize(14);
            titleTv.setTextSize(15);
        }

        numberTv.setText(number);
        if(pInfo!=null) {
            nameTv.setText(pInfo.getNAME());
        }
        dateTv.setText(date);
        titleTv.setText(title);
        titleTv.setTextColor(Color.parseColor("#000000"));
        //contentTv.setText(Html.fromHtml(content));

        //contentTv.setText("================");

        bigll.addView(titleView);

    }

    private void initViews(){
        bigll = (LinearLayout) findViewById(R.id.ll);//所有问题的父布局
        btnStart= (Button) findViewById(R.id.btn_start);//开始答题
        btnStart.setOnClickListener(this);
        btnNext= (Button) findViewById(R.id.btn_next);//下一题
        btnNext.setOnClickListener(this);
        btnLast= (Button) findViewById(R.id.btn_last);//上一题
        btnLast.setOnClickListener(this);
        btnAll= (Button) findViewById(R.id.btn_all);//查看全部
        btnAll.setOnClickListener(this);
        btnRestart= (Button) findViewById(R.id.btn_restart);//重新答题
        btnRestart.setOnClickListener(this);
        btnSubmit= (Button) findViewById(R.id.btn_submit);//提交
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_start://开始答题

                startQuestion();
                btnStart.setVisibility(View.GONE);
                if(questionTitleList.size()>0) {
                    btnLast.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(mContext,"已经是最后一题了",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_last://上一题

                btnAll.setVisibility(View.GONE);

                lastQuestion();
                break;

            case R.id.btn_next://下一题

                nextQuestion();
                break;

            case R.id.btn_all://查看全部

                btnAll.setVisibility(View.GONE);
                btnLast.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
                btnRestart.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.VISIBLE);

                seeAllQuestion();
                break;

            case R.id.btn_restart://重新答题


                restartQuestion();
                break;

            case R.id.btn_submit://提交

                submitAnswerInfo();
                break;
        }

    }

    //开始答题
    private void startQuestion(){

        radioButtons.clear();
        this_CheckBoxs.clear();
        editTexts.clear();
        questionEditTexts.clear();
        questionTextViews.clear();
        index=0;

        if(questionDetailsList.size()>0) {
            juanInfos.addAll(questionDetailsList);
        }
        //添加问题
        questionTitleList = getQuestionByParent();

        if(questionTitleList.size()>0){
            fretchTree(bigll, questionTitleList.get(index), "");
        }

    }

    private void lastQuestion(){
        //上一题

        if (index == 0) {
            Toast.makeText(this, "已经是第一题了", Toast.LENGTH_SHORT).show();
            return;
        }

        // 去掉答题的文本
        for (EditText editText : questionEditTexts) {
            if (currentInfo.getID() == editText.getId()) {
                editText.setText("");
            }
        }

        // 去掉选择日期的文本
        for (TextView textView : questionTextViews) {
            if (currentInfo.getID() == textView.getId()) {
                textView.setText("请点击选择");
            }
        }


        List<ComNaireInfo.DetilsBean> list = getAnswerByParentId(currentInfo);

        // 去掉小题的文本
        for (ComNaireInfo.DetilsBean wenJuanInfo : list) {
            for (EditText editText : editTexts) {
                if (editText.getId() == wenJuanInfo.getID()) {
                    editText.setText("");
                }
            }
        }


        for (ComNaireInfo.DetilsBean dl : list) {
            //去掉单选按钮
            for (RadioButton rb : radioButtons) {

                if (rb.getId() == dl.getID()) {
                    rb.setChecked(false);
                }

            }
               // 去掉多选按钮
            for (CheckBox checkBox : this_CheckBoxs) {
                if (checkBox.getId() == dl.getID()) {
                    checkBox.setChecked(false);
                }
            }
        }

        if (tempList.size() > 0) {
            List<Integer> temp = new ArrayList<>();
            temp.addAll(tempList.subList(tempList.size() - 1, tempList.size()));
            tempQuestionIndex = temp.get(0);
            tempList.removeAll(temp);
            temp.clear();
        }
        index = tempQuestionIndex;
        currentInfo = questionTitleList.get(index);

        if(questionTitleList.size()>0){
            fretchTree(bigll, currentInfo, "");
        }
    }
    //下一题
    private void nextQuestion(){


        ComNaireInfo.DetilsBean info=questionTitleList.get(index);

        List<ComNaireInfo.DetilsBean> tempSmallWenJuan = getAnswerByParentId(info);

        //判断是否已经作答了
        int questionNo = info.getID();
        if (questionEditTexts.size() > 0) {
            for (EditText editText : questionEditTexts) {
                if (editText.getId() == questionNo
                        && "".equals(editText.getText().toString()
                        .trim())) {
                    Toast.makeText(mContext, "答案不能为空!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        if (questionTextViews.size() > 0) {
            for (TextView textView : questionTextViews) {
                if (textView.getId() == questionNo
                        && "请点击选择".equals(textView.getText().toString()
                        .trim())) {
                    Toast.makeText(mContext, "答案不能为空!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        if(tempSmallWenJuan.size()>0&&!checkIsRadioed(answerInfo,questionNo)){

            Toast.makeText(this, "请选择合适的答案", Toast.LENGTH_SHORT).show();

            return;
        }

        if (makeEdit(answerInfo))
            return;
        if (makeEdit_checkBox(answerInfo))
            return;

                if (index >= questionTitleList.size() - 1) {
            Toast.makeText(this, "已经是最后一题了", Toast.LENGTH_SHORT).show();
            btnAll.setVisibility(View.VISIBLE);
            return;
        }

        List<ComNaireInfo.DetilsBean> tempAnswer = new ArrayList<>();

        List<ComNaireInfo.DetilsBean> tempTitleAnswer = new ArrayList<ComNaireInfo.DetilsBean>();

        for (ComNaireInfo.DetilsBean answer : answerInfo) {
            if (answer.getPARENT_ID() == questionNo) {
                tempAnswer.add(answer);
            }
        }
        for (ComNaireInfo.DetilsBean answer : juanInfos) {//这里可能会出错
            if (answer.getPARENT_ID() ==0) {
                tempTitleAnswer.add(answer);
            }
        }
        tempQuestionIndex = index;

        String jumpCode = null;

        for(EditText editText:questionEditTexts){

            if(!TextUtils.equals("", editText.getText().toString())){

                for(ComNaireInfo.DetilsBean temp:tempTitleAnswer){

                    if(editText.getId()==temp.getID()){

                        jumpCode = temp.getJUMP_CODE();

                    }
                }
            }

        }

        //判断如何跳题
        for (RadioButton radioButton : radioButtons) {
            if (radioButton.isChecked()) {
                for (ComNaireInfo.DetilsBean temp : tempAnswer) {
                    if (radioButton.getId() == temp.getID()) {

                        if(temp.getJUMP_CODE().contains("-")){
                            jumpCode=(temp.getJUMP_CODE().subSequence(temp.getJUMP_CODE().indexOf(0)+1,temp.getJUMP_CODE().indexOf("-")))+"";
                        }else if(temp.getJUMP_CODE().contains("—")){
                            jumpCode=(temp.getJUMP_CODE().subSequence(temp.getJUMP_CODE().indexOf(0)+1,temp.getJUMP_CODE().indexOf("—")))+"";
                        }else{
                            jumpCode = temp.getJUMP_CODE();
                        }
                    }
                }
            }
        }
        for (CheckBox checkBox : this_CheckBoxs) {
            if (checkBox.isChecked()) {
                for (ComNaireInfo.DetilsBean temp : tempAnswer) {
                    if (checkBox.getId() == temp.getID()) {
                        if(temp.getJUMP_CODE().contains("-")){
                            jumpCode=(temp.getJUMP_CODE().subSequence(temp.getJUMP_CODE().indexOf(0)+1,temp.getJUMP_CODE().indexOf("-")))+"";
                        }else if(temp.getJUMP_CODE().contains("—")){
                            jumpCode=(temp.getJUMP_CODE().subSequence(temp.getJUMP_CODE().indexOf(0)+1,temp.getJUMP_CODE().indexOf("—")))+"";
                        }else{
                            jumpCode = temp.getJUMP_CODE();
                        }
                    }
                }
            }
        }
        int tempIndex = 0;

        if (jumpCode != null) {

            if (TextUtils.equals("结束", jumpCode)) {

                Toast.makeText(this, "已经是最后一题了", Toast.LENGTH_SHORT).show();
                btnAll.setVisibility(View.VISIBLE);
                tempList.add(tempQuestionIndex - 1);

                if (tempList.size() > tempQuestionIndex - 1 && (tempQuestionIndex - 1) > -1) {

                    tempList.remove(tempQuestionIndex - 1);

                } else {
                    tempList.remove(tempList.size() - 1);
                }
                return;
            } else {
                btnAll.setVisibility(View.GONE);
            }

            for (ComNaireInfo.DetilsBean dl : questionTitleList) {

                if (jumpCode.equals(dl.getCODE())) {
                    index = questionTitleList.indexOf(dl);
                    tempIndex = 0;
                    break;
                } else {
                    tempIndex++;
                }

            }

        } else {
            tempIndex++;
        }

        tempList.add(tempQuestionIndex);

        if (tempIndex > 0) {
            index++;
        }
        tempAnswer.clear();
        currentInfo = questionTitleList.get(index);

        if(questionTitleList.size()>0){
            fretchTree(bigll, currentInfo, "");
        }
    }

    //提交
    private void submitAnswerInfo(){

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("温馨提示").setMessage("确定要提交吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        getAnswerInfo();

                        String answerString;

                        answerString = parseAnswerInfo();

                        if (answerInfos2.size() > 0) {
                            answerInfos2.clear();
                        }

                        if (questionTitleList.size() > 0) {
                           // bigll.removeAllViews();
                            if (tempList.size() > 0) {
                                tempList.clear();
                            }
                        }

                        //Toast.makeText(mContext, "您的答案是:"+answerString, Toast.LENGTH_SHORT).show();

                        shujuliu= answerString.getBytes();

                        if(pInfo.getID()!=-1){
                            submit(pInfo.getID()+"");
                        }else {

                            newPerson();//创建调查人
                        }

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                }).create().show();


    }

    //创建调查人
   private void newPerson(){

       new Thread(new Runnable() {
           @Override
           public void run() {
               //                新建企业人员信息：
//                http://web.youli.pw:89/Json/Set_Qa_Company_Personnel.aspx?COMPANY_ID=1&MARK=备注&NAME=姓名&SFZ=身份证
               String url= MyOkHttpUtils.BaseUrl+"/Json/Set_Qa_Company_Personnel.aspx?COMPANY_ID="+comInfo.getID()+"&MARK="+pInfo.getMARK()+"&NAME="+pInfo.getNAME()+"&SFZ="+pInfo.getSFZ();

               Response response=MyOkHttpUtils.okHttpGet(url);

               Message msg=Message.obtain();

               if(response!=null){

                   if(response.body()!=null){

                       try {
                           String resStr=response.body().string();

                           if(resStr!=null){
                               if(!resStr.contains("<!DOCTYPE html>")) {
                                   msg.what = SUCCESS_NEW_PERSON;
                                   msg.obj = resStr;
                               }
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

   }


    //提交
    private void submit(String pid){
       // http://web.youli.pw:89/Json/Set_Qa_Company_Receiv.aspx?Personnel_id=1251&mark=&Phone=null

        final HttpClient client = new DefaultHttpClient();

        String personId;

        if(pInfo.getID()!=-1){

            personId=pInfo.getID()+"";

        }else{

            personId=pid;

        }



        Log.e("2017/11/24","个人信息的个人ID111="+personId);

        final String strhttp = MyOkHttpUtils.BaseUrl+"/Json/Set_Qa_Company_Receiv.aspx?Personnel_id="+personId+"&mark="+pInfo.getMARK()+"&Phone=null";

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                        String cookies = SharedPreferencesUtils.getString("cookies");
                        HttpPost post = new HttpPost(strhttp);
                        try {
                            post.setHeader("cookie", cookies);
                            if (shujuliu!=null) {
                                String str = Base64.encodeToString(shujuliu, Base64.DEFAULT);
                                StringEntity stringEntity = new StringEntity(str);
                                stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
                                        "application/json"));
                                stringEntity.setContentEncoding(new BasicHeader(
                                        HTTP.CONTENT_ENCODING, HTTP.UTF_8));
                                post.setEntity(stringEntity);
                            }

                            HttpResponse response = client.execute(post);

                            if (response.getStatusLine().getStatusCode() == 200) {

                                HttpEntity entity=response.getEntity();
                                //EntityUtils中的toString()方法转换服务器的响应数据
                                final String str= EntityUtils.toString(entity, "utf-8");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(TextUtils.equals(str,"True")){
                                            Toast.makeText(mContext,"提交成功!",Toast.LENGTH_SHORT).show();
                                            EventBus.getDefault().post(new ComPersonInfo());//通知企业人员列表界面刷新
                                            finish();
                                        }else{
                                            Toast.makeText(mContext,"提交失败!",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });



                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                        } finally {
                            post.abort();
                        }

                    }
                }

        ).start();

    }

    //查看全部
    private void seeAllQuestion(){

        questionTitleList = getQuestionByParent();

        bigll.removeAllViews();
        addTitle();
        for(ComNaireInfo.DetilsBean info:questionTitleList){
            fretchTree(bigll,info,"all");
        }

    }

    //重新答题
    private void restartQuestion(){

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("温馨提示").setMessage("确定要重新答题吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        btnLast.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.VISIBLE);
                        btnSubmit.setVisibility(View.GONE);
                        btnRestart.setVisibility(View.GONE);

                        index=0;
                        radioButtons.clear();
                        this_CheckBoxs.clear();
                        editTexts.clear();
                        questionEditTexts.clear();
                        questionTextViews.clear();
                        if(questionTitleList.size()>0){
                            if (tempList.size() > 0) {
                                tempList.clear();
                            }
                            fretchTree(bigll, questionTitleList.get(index), "");
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();


    }

    //问题的布局
    private void fretchTree(LinearLayout layout, ComNaireInfo.DetilsBean info, String isAll){

        if("".equals(isAll)){
            bigll.removeAllViews();
        }

        LinearLayout alllinearLayout=new LinearLayout(this);//整体布局（包括问题和选项）
        alllinearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams allparam = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        alllinearLayout.setLayoutParams(allparam);
        LinearLayout qll=new LinearLayout(this);//问题的布局

        if(info.getTITLE_L().length()>=20) {//左边的文字长度大于等于20就换行

            qll.setOrientation(LinearLayout.VERTICAL);
        }else{

            qll.setOrientation(LinearLayout.HORIZONTAL);
        }
        TextView tvLeft = new TextView(this);//问题左边的部分

        tvLeft.setText(info.getTITLE_L());
        tvLeft.setPadding(10,0,0,0);//向右移动10xp
        tvLeft.setTextColor(Color.parseColor("#000000"));
        tvLeft.setTextSize(18);
        qll.addView(tvLeft,allparam);

        LinearLayout qRightll=new LinearLayout(this);//问题的右边布局(包括一个EditText和一个TextView)
        qRightll.setOrientation(LinearLayout.HORIZONTAL);
        if(info.isINPUT()){
            qRightll.setVisibility(View.VISIBLE);
            EditText et=new EditText(this);//问题的输入框
           // et.setTextSize(15);
            et.setGravity(Gravity.CENTER);
            if(isAll.equals("all")){
                et.setEnabled(false);
            }else{
                et.setText("            ");//12个空格
            }
            et.setPadding(0,0,0,10);
            et.setId(info.getID());
            if(!info.getTITLE_L().contains("出生")&&!info.getTITLE_L().contains("就业时间")&&!info.getTITLE_L().contains("就业的时间")&&!info.getTITLE_L().contains("什么时候")){
                if(TextUtils.equals(info.getINPUT_TYPE(),"int")) {
                    et.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                if(info.getTITLE_L().contains("姓名")){
                   // et.setText("张三丰");
                }

                if(questionEditTexts.size()>0){
                    List<EditText> tempEditTexts=new ArrayList<>();
                    for(EditText editText2:questionEditTexts){
                        if(editText2.getId()==info.getID()){
                            et.setText(editText2.getText());
                            //从答案中得到家庭成员的名字
                            if(info.getTITLE_L().contains("姓名")){

                            }
                            tempEditTexts.add(editText2);
                        }
                    }
                    questionEditTexts.removeAll(tempEditTexts);
                    tempEditTexts.clear();
                }


                questionEditTexts.add(et);

                if(aInfo!=null&&aInfo.size()>0){

                    for(ComNaireAnswerInfo caInfo:aInfo){

                        for(EditText myEt:questionEditTexts){
                            if(caInfo.getDETIL_ID()==myEt.getId()){

                                myEt.setText(caInfo.getINPUT_VALUE());

                            }
                        }

                    }

                }

                qRightll.addView(et,allparam);
            }else{

                final TextView tv=new TextView(this);//问题里面选择日期的
                if(isAll.equals("all")){
                    tv.setEnabled(false);
                }
                tv.setTextSize(15);

                tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
                tv.setTextColor(0xff538ee5);
                tv.setPadding(10,0,0,0);
                Drawable drawable= getResources().getDrawable(R.drawable.rili);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv.setCompoundDrawables(null,null,drawable,null);



                if(birthDay!=null&&!TextUtils.equals(birthDay,"")){
                    tv.setText(birthDay);
                }else{
                    tv.setText("请点击选择");
                }

                tv.setId(info.getID());
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Calendar c=Calendar.getInstance();

                        new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                tv.setText(year+"年"+(month+1)+"月"+dayOfMonth+"日");

                            }
                        },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();

                    }
                });

                if(questionTextViews.size()>0){
                    List<TextView> tempTextViews=new ArrayList<>();
                    for(TextView textView2:questionTextViews){
                        if(textView2.getId()==info.getID()){
                            tv.setText(textView2.getText());
                            tempTextViews.add(textView2);
                        }
                    }
                    questionTextViews.removeAll(tempTextViews);
                    tempTextViews.clear();
                }
                questionTextViews.add(tv);

                if(aInfo!=null&&aInfo.size()>0){

                    for(ComNaireAnswerInfo caInfo:aInfo){

                        for(TextView myTv:questionTextViews){
                            if(caInfo.getDETIL_ID()==myTv.getId()){

                                tv.setText(caInfo.getINPUT_VALUE());

                            }
                        }

                    }

                }

                qRightll.addView(tv,allparam);
            }


        }else{
            qRightll.setVisibility(View.GONE);
        }

        TextView tvRight = new TextView(this);//问题右边的部分
        tvRight.setText(info.getTITLE_R());
        tvRight.setTextSize(18);
        tvRight.setTextColor(Color.parseColor("#000000"));
        qRightll.addView(tvRight,allparam);

        qll.addView(qRightll,allparam);
        alllinearLayout.addView(qll,allparam);



        LinearLayout optionlinearLayout=new LinearLayout(this);//选项的布局
        optionlinearLayout.setOrientation(LinearLayout.HORIZONTAL);



        answerInfo = getAnswerByParentId(info);


        LinearLayout xuanxiangll=new LinearLayout(this);
        xuanxiangll.setOrientation(LinearLayout.VERTICAL);

        RadioGroup rg=new RadioGroup(this);
        rg.setLayoutParams(allparam);

        if(info.getTITLE_L().contains("多选")||info.getTITLE_L().contains("可以选择多个答案")){
            //多选题的布局

            List<CheckBox> checkBoxGroup=new ArrayList<CheckBox>();

            for(ComNaireInfo.DetilsBean list:answerInfo){

                fretchTreeByQuestionMultiSelect(checkBoxGroup, rg,
                        list, xuanxiangll, isAll,
                        getMaxxuangxiang(info.getTITLE_L()));

            }

        }else{
            //单选题的布局

            for(ComNaireInfo.DetilsBean list:answerInfo){

                fretchTreeByQuestion(rg,xuanxiangll,list,isAll);

            }

        }


        xuanxiangll.setLayoutParams(allparam);
        optionlinearLayout.addView(rg,allparam);

         optionlinearLayout.addView(xuanxiangll,allparam);
      //  optionlinearLayout.setBackgroundResource(R.drawable.gridebg);
         alllinearLayout.addView(optionlinearLayout,allparam);

        layout.addView(alllinearLayout,allparam);
    }

    private int getMaxxuangxiang(String a){

        if(a.contains("最多选")&&a.contains("项")){

            return Integer.valueOf(a.substring(a.indexOf("最多选")+3,a.indexOf("项")));

        }

        if(a.contains("不要超过")&&a.contains("个)")){

            return Integer.valueOf(a.substring(a.indexOf("不要超过")+4,a.indexOf("个)")));


        }

        if(a.contains("不要超过")&&a.contains("个）")){

            return Integer.valueOf(a.substring(a.indexOf("不要超过")+4,a.indexOf("个）")));


        }

        return 30;
    }

    //单选题的布局
    private void fretchTreeByQuestion(RadioGroup rg, LinearLayout xuanxiangll, final ComNaireInfo.DetilsBean info, String isAll) {

        LinearLayout linearLayout=new LinearLayout(this);
        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, 70);
        linearLayout.setLayoutParams(llParam);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        RadioButton rb=new RadioButton(this);
        LinearLayout.LayoutParams rbParam = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, 70);
        rb.setLayoutParams(rbParam);
        rb.setId(info.getID());
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.equals("结束", info.getJUMP_CODE())){
                    btnAll.setVisibility(View.GONE);
                }

            }
        });
        if(isAll.equals("all")){
            rb.setEnabled(false);
        }

        rg.addView(rb);


        if(radioButtons.size()>0){

            List<RadioButton> tempRadioButtons=new ArrayList<>();
            for(RadioButton rButton :radioButtons){

                if(rButton.getId()==info.getID()&&rButton.isChecked()){
                    rb.setChecked(true);
                    tempRadioButtons.add(rButton);
                }

            }
            radioButtons.removeAll(tempRadioButtons);
            tempRadioButtons.clear();
        }

        radioButtons.add(rb);

        if(aInfo!=null&&aInfo.size()>0){

            for(ComNaireAnswerInfo caInfo:aInfo){

                for(RadioButton myRb:radioButtons){
                    if(caInfo.getDETIL_ID()==myRb.getId()){

                        myRb.setChecked(true);

                    }
                }

            }

        }


        LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, 70);

        TextView tv=new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setText(info.getTITLE_L());
      //  tv.setTextColor(Color.parseColor("#000000"));
        linearLayout.addView(tv,textparams);

        if(info.isINPUT()) {
            EditText et = new EditText(this);
            if(isAll.equals("all")){
                et.setEnabled(false);
            }else{
                et.setText("            ");//12个空格
            }
            if(isTablet) {
                et.setPadding(0, 0, 0, 10);
            }else{
                et.setPadding(0, -20, 0, 0);
            }
           // et.setText("");
            et.setId(info.getID());

            if (TextUtils.equals(info.getINPUT_TYPE(), "int")) {
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
            if(editTexts.size()>0){
                List<EditText> tempEditTexts=new ArrayList<>();
                for(EditText editText2:editTexts){
                    if(editText2.getId()==info.getID()){
                        et.setText(editText2.getText());
                        tempEditTexts.add(editText2);
                    }
                }
                editTexts.removeAll(tempEditTexts);
                tempEditTexts.clear();
            }
            editTexts.add(et);

            if(aInfo!=null&&aInfo.size()>0){

                for(ComNaireAnswerInfo caInfo:aInfo){

                    for(EditText myEt:editTexts){
                        if(caInfo.getDETIL_ID()==myEt.getId()){
                            myEt.setText(caInfo.getINPUT_VALUE());
                        }
                    }

                }

            }

            linearLayout.addView(et, textparams);
        }

            TextView tvRight = new TextView(this);
       //     tvRight.setTextColor(Color.parseColor("#000000"));
            tvRight.setText(info.getTITLE_R());
            LinearLayout.LayoutParams tvRightParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 70);
            linearLayout.addView(tvRight, tvRightParams);


        xuanxiangll.addView(linearLayout,llParam);
    }


    //多选题选项的布局
    private void fretchTreeByQuestionMultiSelect(List<CheckBox> CheckBoxGroup,
     RadioGroup group, ComNaireInfo.DetilsBean info,
      LinearLayout xuanxiangll, String isAll, int MultiSelect){

        LinearLayout ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, 70);
        ll.setLayoutParams(llParam);

        CheckBox cb=new CheckBox(this);
        cb.setOnCheckedChangeListener(new MyOnCheckedChangeListener(
                CheckBoxGroup,MultiSelect));
        cb.setId(info.getID());
        if(isAll.equals("all")){
            cb.setEnabled(false);
        }
        group.addView(cb,llParam);

        if(this_CheckBoxs.size()>0){
            List<CheckBox> tempRadioButtons=new ArrayList<>();
            for(CheckBox _CheckBox:this_CheckBoxs){
                if(_CheckBox.getId()==info.getID()&&_CheckBox.isChecked()){
                   cb.setChecked(true);
                    tempRadioButtons.add(_CheckBox);
                }
            }
            this_CheckBoxs.removeAll(tempRadioButtons);
            tempRadioButtons.clear();
        }

        for(int i=0;i<this_CheckBoxs.size();i++){
            if(this_CheckBoxs.get(i).getId()==cb.getId()){
                this_CheckBoxs.remove(i);
                break;
            }
        }
        this_CheckBoxs.add(cb);

        //多选题选项的文字和输入框
        TextView tvLeft=new TextView(this);//左边的文字
        tvLeft.setText(info.getTITLE_L());
        tvLeft.setGravity(Gravity.CENTER);
        //tvLeft.setTextColor(Color.parseColor("#000000"));
        tvLeft.setLayoutParams(llParam);
        ll.addView(tvLeft,llParam);

        if(info.isINPUT()) {
            EditText et = new EditText(this);
            if(isAll.equals("all")){
                et.setEnabled(false);
            }
            if(isTablet) {
                et.setPadding(0, 0, 0, 10);
            }else{
                et.setPadding(0, -20, 0, 0);
            }
            et.setText("");
            et.setId(info.getID());

            if (TextUtils.equals(info.getINPUT_TYPE(), "int")) {
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
            }

            if (editTexts.size() > 0) {
                List<EditText> tempEditTexts = new ArrayList<EditText>();
                for (EditText editText2 : editTexts) {
                    if (editText2.getId() == info.getID()) {
                        et.setText(editText2.getText());
                        tempEditTexts.add(editText2);
                    }

                }
                editTexts.removeAll(tempEditTexts);
                tempEditTexts.clear();
            }
            editTexts.add(et);
            ll.addView(et, llParam);
            TextView tvRight = new TextView(this);
            tvRight.setGravity(Gravity.CENTER);
            //tvRight.setTextColor(Color.parseColor("#000000"));
            tvRight.setText(info.getTITLE_R());
            ll.addView(tvRight,llParam);
        }
        xuanxiangll.addView(ll,llParam);
    }

    private List<ComNaireInfo.DetilsBean> getQuestionByParent(){
        //从问卷信息中找出问题的信息
        List<ComNaireInfo.DetilsBean> questionInfos = new ArrayList<>();

        for(ComNaireInfo.DetilsBean detailsList : juanInfos){
            if(detailsList.getPARENT_ID()==0){
                questionInfos.add(detailsList);
            }
        }
        return questionInfos;
    }

    private List<ComNaireInfo.DetilsBean> getAnswerByParentId(ComNaireInfo.DetilsBean info){
        //用问题的信息得到选项的信息
        List<ComNaireInfo.DetilsBean> aInfos = new ArrayList<>();

        for(ComNaireInfo.DetilsBean list:juanInfos){

            if(list.getPARENT_ID()==info.getID()){
                aInfos.add(list);
            }

        }

        return aInfos;
    }

    private void getAnswerInfo() {
        if (radioButtons.size() > 0) {
            for (RadioButton radioButton : radioButtons) {
                if (radioButton.isChecked()) {
                    AnswerInfo answerInfo = new AnswerInfo();

                    for (ComNaireInfo.DetilsBean wenJuanInfo : juanInfos) {
                        if (wenJuanInfo.getID() == radioButton.getId()) {
                            answerInfo.setAnswerId(wenJuanInfo.getID());
                            answerInfo.setAnswerNo(wenJuanInfo.getNO());
                            answerInfo.setAnswerText("");
                        }
                    }
                    if (editTexts.size() > 0) {
                        for (EditText editText : editTexts) {
                            if (radioButton.getId() == editText.getId()) {
                                answerInfo.setAnswerText(editText.getText()
                                        .toString().trim());
                            }
                        }
                    }
                    answerInfos2.add(answerInfo);
                }
            }
        }
        if (this_CheckBoxs.size() > 0) {
            for (CheckBox radioButton : this_CheckBoxs) {
                if (radioButton.isChecked()) {
                    AnswerInfo answerInfo = new AnswerInfo();

                    for (ComNaireInfo.DetilsBean wenJuanInfo : juanInfos) {
                        if (wenJuanInfo.getID() == radioButton.getId()) {
                            answerInfo.setAnswerId(wenJuanInfo.getID());
                            answerInfo.setAnswerNo(wenJuanInfo.getNO());
                            answerInfo.setAnswerText("");
                        }
                    }
                    if (editTexts.size() > 0) {
                        for (EditText editText : editTexts) {
                            if (radioButton.getId() == editText.getId()) {
                                answerInfo.setAnswerText(editText.getText()
                                        .toString().trim());
                            }
                        }
                    }
                    answerInfos2.add(answerInfo);
                }
            }
        }

        if (questionEditTexts.size() > 0) {
            List<ComNaireInfo.DetilsBean> questionInfos = getQuestionByParent();
            for (EditText editText : questionEditTexts) {
                for (ComNaireInfo.DetilsBean wenJuanInfo : questionInfos) {
                    if (editText.getId() == wenJuanInfo.getID()
                            && !"".equals(editText.getText().toString().trim())
                            && null != editText.getText().toString().trim()) {
                        AnswerInfo answerInfo = new AnswerInfo();
                        answerInfo.setAnswerId(wenJuanInfo.getID());
                        answerInfo.setAnswerNo(wenJuanInfo.getNO());
                        answerInfo.setAnswerText(editText.getText().toString()
                                .trim());
                        answerInfos2.add(answerInfo);
                    }
                }
            }
        }

        if (questionTextViews.size() > 0) {
            List<ComNaireInfo.DetilsBean> questionInfos = getQuestionByParent();
            for (TextView textView : questionTextViews) {
                for (ComNaireInfo.DetilsBean wenJuanInfo : questionInfos) {
                    if (textView.getId() == wenJuanInfo.getID()
                            && !"请点击选择".equals(textView.getText().toString().trim())
                            ) {
                        AnswerInfo answerInfo = new AnswerInfo();
                        answerInfo.setAnswerId(wenJuanInfo.getID());
                        answerInfo.setAnswerNo(wenJuanInfo.getNO());
                        answerInfo.setAnswerText(textView.getText().toString()
                                .trim());
                        answerInfos2.add(answerInfo);
                    }
                }
            }
        }

    }

    //组装答案

    private String parseAnswerInfo() {
        JSONArray array = new JSONArray();
        if (answerInfos2.size() > 0) {
            for (AnswerInfo answerInfo : answerInfos2) {
                JSONObject object = new JSONObject();
                try {
                    object.put("DETIL_ID", answerInfo.getAnswerId());
                    object.put("INPUT_VALUE", answerInfo.getAnswerText());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                array.put(object);
            }
        }

        return array.toString();
    }

    //判断单选题多选题是否作答
    private boolean checkIsRadioed(List<ComNaireInfo.DetilsBean> infos, int questionId) {

        for (RadioButton rb : radioButtons) {

            for (ComNaireInfo.DetilsBean dl : infos) {

                if (rb.getId() == dl.getID() && dl.getPARENT_ID() == questionId && rb.isChecked()) {

                    return true;

                }

            }

        }

        for (CheckBox _CheckBox : this_CheckBoxs) {
            for (ComNaireInfo.DetilsBean dl : infos) {
                if (_CheckBox.getId() == dl.getID()
                        && dl.getPARENT_ID() == questionId
                        && _CheckBox.isChecked()) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean makeEdit(List<ComNaireInfo.DetilsBean> infos) {
        if (editTexts.size() > 0) {
            for (RadioButton radioButton : radioButtons) {
                if (radioButton.isChecked()) {
                    for (EditText editText : editTexts) {
                        for (ComNaireInfo.DetilsBean wenJuanInfo : infos) {
                            if (radioButton.getId() == wenJuanInfo.getID()
                                    && editText.getId() == wenJuanInfo.getID()
                                    && "".equals(editText.getText().toString()
                                    .trim())) {
                                Toast.makeText(mContext, "答案不能为空!",
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        }
                    }
                }

            }
        }
        return false;
    }

    private boolean makeEdit_checkBox(List<ComNaireInfo.DetilsBean> infos) {
        if (editTexts.size() > 0) {
            for (CheckBox radioButton : this_CheckBoxs) {
                if (radioButton.isChecked()) {
                    for (EditText editText : editTexts) {
                        for (ComNaireInfo.DetilsBean wenJuanInfo : infos) {
                            if (radioButton.getId() == wenJuanInfo.getID()
                                    && editText.getId() == wenJuanInfo.getID()
                                    && "".equals(editText.getText().toString()
                                    .trim())) {
                                Toast.makeText(mContext, "答案不能为空!",
                                        Toast.LENGTH_SHORT).show();

                                return true;
                            }

                        }
                    }
                }

            }
        }
        return false;
    }

    //复选框的监听
    public class MyOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        int _MultiSelect;
        List<CheckBox> _group;

        public MyOnCheckedChangeListener(List<CheckBox> _group, int _MultiSelect) {
            this._group = _group;
            this._MultiSelect = _MultiSelect;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if(isChecked){

                if(_group.size()>_MultiSelect-1){
                    buttonView.setChecked(false);
                    Toast.makeText(mContext, "最多选" + _MultiSelect + "项",
                            Toast.LENGTH_SHORT).show();
                }else{
                    _group.add((CheckBox)buttonView);
                }

            }else{

              CheckBox _check_box=(CheckBox) buttonView;

                if(_group.contains(_check_box)){
                    _group.remove(_check_box);
                };


            }

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (aInfo!=null&&aInfo.size()>0) {
                showMessage("温馨提示", "确定要退出吗?");
            } else {
                showMessage("温馨提示", "确定要放弃答题吗?");
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title).setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                }).create().show();
    }

    /**
     * 判断是否平板设备
     * @param context
     * @return true:平板,false:手机
     */
    private boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}

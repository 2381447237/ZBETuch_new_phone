package com.youli.zbetuch.jingan.activity;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/9/27.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 * 修改密码
 */

public class ChangePwdActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext=ChangePwdActivity.this;

    private final int SUCCESS=10001;
    private final int FAIL=10002;

    private Button confirmBtn,cancelBtn;
    private EditText etPwdOrg;//原密码
    private EditText etPwdNew;//新密码
    private EditText etPwdNewAga;//确认新密码
    private String pwdOrgStr,pwdNewStr,pwdNewAgaStr;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS:

                    Toast.makeText(mContext, "密码修改成功！", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case FAIL:

                    Toast.makeText(mContext, "密码修改失败！", Toast.LENGTH_SHORT).show();

                    break;
            }

        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        initViews();

    }


    private void initViews(){

        confirmBtn= (Button) findViewById(R.id.btn_change_pwd_confirm);
        confirmBtn.setOnClickListener(this);
        cancelBtn= (Button) findViewById(R.id.btn_change_pwd_cancel);
        cancelBtn.setOnClickListener(this);
        etPwdOrg= (EditText) findViewById(R.id.et_change_pwd_original);
        etPwdNew= (EditText) findViewById(R.id.et_change_pwd_new);
        etPwdNewAga= (EditText) findViewById(R.id.et_change_pwd_new_again);
    }

    @Override
    public void onClick(View v) {

        pwdOrgStr=etPwdOrg.getText().toString().trim();
        pwdNewStr=etPwdNew.getText().toString().trim();
        pwdNewAgaStr=etPwdNewAga.getText().toString().trim();
        switch (v.getId()){

            case R.id.btn_change_pwd_confirm:

              if(TextUtils.equals(pwdOrgStr,"")){
                  Toast.makeText(mContext,"原密码不能为空！",Toast.LENGTH_SHORT).show();
                  etPwdOrg.requestFocus();
                  return;
              }
                if(TextUtils.equals(pwdNewStr,"")){
                    Toast.makeText(mContext,"新密码不能为空！",Toast.LENGTH_SHORT).show();
                    etPwdNew.requestFocus();
                    return;
                }
                if(TextUtils.equals(pwdNewAgaStr,"")){
                    Toast.makeText(mContext,"确认新密码不能为空！",Toast.LENGTH_SHORT).show();
                    etPwdNewAga.requestFocus();
                    return;
                }

                if(!TextUtils.equals(pwdNewAgaStr,pwdNewStr)){
                    Toast.makeText(mContext,"新密码和确认新密码不同！",Toast.LENGTH_SHORT).show();
                    etPwdNew.setText("");
                    etPwdNewAga.setText("");
                    etPwdNew.requestFocus();
                    return;
                }

                //修改密码
                changPwd();

                break;

            case R.id.btn_change_pwd_cancel:

               finish();

                break;
        }


    }
/*
*http://web.youli.pw:89/Json/Set_Pwd.aspx?pwd=123&new_pwd=321
返回true，修改成功，否则，修改失败；
* */
    private void  changPwd(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        HashMap<String,String> data=new HashMap<String,String>();
                        data.put("pwd",pwdOrgStr);
                        data.put("new_pwd",pwdNewStr);

                        String url= MyOkHttpUtils.BaseUrl+"/Json/Set_Pwd.aspx";

                        Response response=MyOkHttpUtils.okHttpPostFormBody(url, data);

                        try {
                            String resStr=response.body().string();

                            Message msg=Message.obtain();

                            if(TextUtils.equals(resStr,"True")){

                                msg.what=SUCCESS;

                            }else{

                                msg.what=FAIL;

                            }

                            mHandler.sendEmptyMessage(msg.what);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }

        ).start();

    }

}

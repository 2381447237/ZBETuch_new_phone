package com.youli.zbetuch.jingan.activity;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.youli.zbetuch.jingan.R;

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
    private Button confirmBtn,cancelBtn;

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

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_change_pwd_confirm:

                Toast.makeText(mContext,"确认",Toast.LENGTH_SHORT).show();

                break;

            case R.id.btn_change_pwd_cancel:

                Toast.makeText(mContext,"取消",Toast.LENGTH_SHORT).show();

                break;
        }


    }
}

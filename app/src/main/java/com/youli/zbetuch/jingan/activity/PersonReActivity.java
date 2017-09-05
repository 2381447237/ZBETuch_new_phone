package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.youli.zbetuch.jingan.R;

/**
 * Created by ZHengBin on 2017/8/26.
 */

public class PersonReActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext=PersonReActivity.this;
    private TextView tvSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_resume);

        initViews();
    }

    private void initViews(){

        tvSubmit= (TextView) findViewById(R.id.tv_person_resume_submit);
        tvSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tv_person_resume_submit:

                Toast.makeText(mContext,"提交",Toast.LENGTH_SHORT).show();

            break;

        }

    }
}

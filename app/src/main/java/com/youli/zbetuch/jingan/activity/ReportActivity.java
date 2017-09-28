package com.youli.zbetuch.jingan.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.youli.zbetuch.jingan.R;

/**
 * 作者: zhengbin on 2017/9/27.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 统计报表
 *
 */

public class ReportActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext=ReportActivity.this;

    private ImageView ivStreet1,ivCommittee1,ivAge1,ivSex1,ivDegree1;
    private ImageView ivStreet2,ivCommittee2,ivAge2,ivSex2,ivDegree2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        initViews();

    }

    private void initViews(){

        ivStreet1= (ImageView) findViewById(R.id.iv_report_street1);
        ivCommittee1= (ImageView) findViewById(R.id.iv_report_committee1);
        ivAge1= (ImageView) findViewById(R.id.iv_report_age1);
        ivSex1= (ImageView) findViewById(R.id.iv_report_sex1);
        ivDegree1= (ImageView) findViewById(R.id.iv_report_degree1);
        ivStreet2= (ImageView) findViewById(R.id.iv_report_street2);
        ivCommittee2= (ImageView) findViewById(R.id.iv_report_committee2);
        ivAge2= (ImageView) findViewById(R.id.iv_report_age2);
        ivSex2= (ImageView) findViewById(R.id.iv_report_sex2);
        ivDegree2= (ImageView) findViewById(R.id.iv_report_degree2);
        ivStreet1.setOnClickListener(this);
        ivCommittee1.setOnClickListener(this);
        ivAge1.setOnClickListener(this);
        ivSex1.setOnClickListener(this);
        ivDegree1.setOnClickListener(this);
        ivStreet2.setOnClickListener(this);
        ivCommittee2.setOnClickListener(this);
        ivAge2.setOnClickListener(this);
        ivSex2.setOnClickListener(this);
        ivDegree2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.iv_report_street1:

                Toast.makeText(mContext,"绿街道",Toast.LENGTH_SHORT).show();

                break;

            case R.id.iv_report_committee1:

                Toast.makeText(mContext,"绿居委",Toast.LENGTH_SHORT).show();

                break;
            case R.id.iv_report_age1:

                Toast.makeText(mContext,"绿年龄",Toast.LENGTH_SHORT).show();

                break;
            case R.id.iv_report_sex1:

                Toast.makeText(mContext,"绿性别",Toast.LENGTH_SHORT).show();

                break;
            case R.id.iv_report_degree1:

                Toast.makeText(mContext,"绿学历",Toast.LENGTH_SHORT).show();

                break;
            case R.id.iv_report_street2:

                Toast.makeText(mContext,"红街道",Toast.LENGTH_SHORT).show();

                break;
            case R.id.iv_report_committee2:

                Toast.makeText(mContext,"红居委",Toast.LENGTH_SHORT).show();

                break;
            case R.id.iv_report_age2:

                Toast.makeText(mContext,"红年龄",Toast.LENGTH_SHORT).show();

                break;
            case R.id.iv_report_sex2:

                Toast.makeText(mContext,"红性别",Toast.LENGTH_SHORT).show();

                break;
            case R.id.iv_report_degree2:

                Toast.makeText(mContext,"红学历",Toast.LENGTH_SHORT).show();

                break;
        }


    }
}

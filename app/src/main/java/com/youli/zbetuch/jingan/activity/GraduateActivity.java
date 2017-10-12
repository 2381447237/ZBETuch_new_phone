package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.GraduateInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: zhengbin on 2017/10/9.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 应届毕业生
 */

public class GraduateActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext=GraduateActivity.this;

    private ListView lv;
    private List<GraduateInfo> data=new ArrayList<>();
    private CommonAdapter adapter;
    private Button btnFind,btnConFind;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graduate);

        initViews();
    }

    private void initViews(){

       lv= (ListView) findViewById(R.id.lv_graduate);
       btnFind= (Button) findViewById(R.id.btn_graduate_find);
        btnFind.setOnClickListener(this);
        btnConFind= (Button) findViewById(R.id.btn_graduate_condition_find);
        btnConFind.setOnClickListener(this);

        initDates();
    }

    private void initDates(){

        for(int i=1;i<100;i++){

            data.add(new GraduateInfo("123456789123456789","姓名"+i,""+i,"1231231231231","男"));

        }

        adapter=new CommonAdapter<GraduateInfo>(mContext,data,R.layout.item_graduate) {
            @Override
            public void convert(CommonViewHolder holder, GraduateInfo item, int position) {

                TextView tvNo=holder.getView(R.id.tv_item_graduate_no);
                tvNo.setText(item.getNoStr());
                TextView tvName=holder.getView(R.id.tv_item_graduate_name);
                tvName.setText(item.getNameStr());
                TextView tvSex=holder.getView(R.id.tv_item_graduate_sex);
                tvSex.setText(item.getSexStr());
                TextView tvIdCard=holder.getView(R.id.tv_item_graduate_idcard);
                tvIdCard.setText(item.getIdCardStr());
                TextView tvPhone=holder.getView(R.id.tv_item_graduate_phone);
                tvPhone.setText(item.getPhoneStr());
                LinearLayout ll = holder.getView(R.id.item_graduate_ll);
                if (position % 2 == 0) {
                    ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item1);
                } else {
                    ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item2);
                }
            }
        };

        lv.setAdapter(adapter);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_graduate_find:
                Toast.makeText(mContext,"查询",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_graduate_condition_find:

                Intent intent=new Intent(mContext,GradConQueryActivity.class);
                startActivity(intent);

                break;
        }

    }
}

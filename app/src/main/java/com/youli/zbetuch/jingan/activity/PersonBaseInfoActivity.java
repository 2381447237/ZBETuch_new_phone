package com.youli.zbetuch.jingan.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.StaffMarkInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHengBin on 2017/8/26.
 */

public class PersonBaseInfoActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext=PersonBaseInfoActivity.this;

    private Button btnTakePhoto;//拍照
    private Button btnChoicePhoto;//选择图片
    private Button btnUploadPhoto;//上传图片
    private Button btnSaveInfo;//保存信息
    private Button btnMark;// 标识
    private Button btnZxMark;//专项标识
    private List<StaffMarkInfo> markData=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_baseinfo);

        initViews();
    }

    private void initViews(){

        btnTakePhoto= (Button) findViewById(R.id.btn_person_baseinfo_takephoto);
        btnChoicePhoto= (Button) findViewById(R.id.btn_person_baseinfo_choicephoto);
        btnUploadPhoto= (Button) findViewById(R.id.btn_person_baseinfo_uploadphoto);
        btnSaveInfo= (Button) findViewById(R.id.btn_person_baseinfo_saveinfo);
        btnMark= (Button) findViewById(R.id.btn_person_baseinfo_biaoshi);
        btnZxMark= (Button) findViewById(R.id.btn_person_baseinfo_zhuanxiang_biaoshi);

        btnTakePhoto.setOnClickListener(this);
        btnChoicePhoto.setOnClickListener(this);
        btnUploadPhoto.setOnClickListener(this);
        btnSaveInfo.setOnClickListener(this);
        btnMark.setOnClickListener(this);
        btnZxMark.setOnClickListener(this);

        for(int i=1;i<10;i++){

            markData.add(new StaffMarkInfo("丧劳调查"+i,"2017-09-07"));

        }

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

                Toast.makeText(mContext,"上传图片",Toast.LENGTH_SHORT).show();

                break;

            case R.id.btn_person_baseinfo_saveinfo:

                Toast.makeText(mContext,"保存信息",Toast.LENGTH_SHORT).show();

                break;

            case R.id.btn_person_baseinfo_biaoshi:

                showEditDialog("mark");
                break;

            case R.id.btn_person_baseinfo_zhuanxiang_biaoshi:

                showEditDialog("zxMark");

                break;
        }

    }

    private void showEditDialog(final String sign){


        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);

        View view= LayoutInflater.from(mContext).inflate(R.layout.dialog_person_baseinfo_mark,null,false);

        builder.setView(view);

        final AlertDialog dialog=builder.create();

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        final Button addBtn= (Button) view.findViewById(R.id.btn_dialog_person_baseinfo_mark_add);
        final Button whBtn= (Button) view.findViewById(R.id.btn_dialog_person_baseinfo_mark_wh);
        Button modifyBtn= (Button) view.findViewById(R.id.btn_dialog_person_baseinfo_mark_modify);
        Button closeBtn= (Button) view.findViewById(R.id.btn_dialog_person_baseinfo_mark_close);
        TextView dateTv= (TextView) view.findViewById(R.id.tv_dialog_person_baseinfo_date);
        ListView lv= (ListView) view.findViewById(R.id.lv_dialog_person_baseinfo_mark);

        if(TextUtils.equals(sign,"mark")){
            whBtn.setVisibility(View.GONE);
            dateTv.setText("备注");
        }else if(TextUtils.equals(sign,"zxMark")){
            whBtn.setVisibility(View.VISIBLE);
            dateTv.setText("标识日期");
        }
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext,"添加",Toast.LENGTH_SHORT).show();

            }
        });


        whBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext,"维护",Toast.LENGTH_SHORT).show();

            }
        });


        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext,"修改",Toast.LENGTH_SHORT).show();

            }
        });


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext,"关闭",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        lv.setAdapter(new CommonAdapter<StaffMarkInfo>(mContext,markData,R.layout.item_person_baseinfo_mark) {
            @Override
            public void convert(CommonViewHolder holder, StaffMarkInfo item, final int position) {

                LinearLayout ll=holder.getView(R.id.ll_item_person_baseinfo_mark);

                TextView noTv=holder.getView(R.id.tv_item_person_baseinfo_mark_no);
                noTv.setText((position+1)+"");
                TextView nameTv=holder.getView(R.id.tv_item_person_baseinfo_mark_name);
                nameTv.setText(markData.get(position).getType_Name());
                TextView dateTv=holder.getView(R.id.tv_item_person_baseinfo_mark_date);
                dateTv.setText(markData.get(position).getCREATE_DATE());
                Button deleteBtn=holder.getView(R.id.btn_item_person_baseinfo_mark_delete);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(mContext,"删除第"+position+"个",Toast.LENGTH_SHORT).show();

                    }
                });

                if (position % 2 == 0) {
                    ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item1);
                } else if (position % 2 != 0) {
                    ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item2);
                }
                if(TextUtils.equals(sign,"mark")){
                    deleteBtn.setTextColor(Color.parseColor("#ff0000"));
                    dateTv.setVisibility(View.GONE);
                }else if(TextUtils.equals(sign,"zxMark")){
                    deleteBtn.setTextColor(Color.parseColor("#000000"));
                    dateTv.setVisibility(View.VISIBLE);
                }

            }
        });


    }

}

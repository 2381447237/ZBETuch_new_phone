package com.youli.zbetuch.jingan.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.MarkImgInfo;
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
    private List<StaffMarkInfo> zxMarkData=new ArrayList<>();//专项标识
    private List<MarkImgInfo> markData=new ArrayList<>();//标识
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

        for(int i=1;i<5;i++){

            zxMarkData.add(new StaffMarkInfo("丧劳调查"+i,"2017-09-07"));
            markData.add(new MarkImgInfo("应届毕业生"+i));
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

                showAlertDialog("uploadPhoto","uploadPhoto");
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

                Toast.makeText(mContext,"请选择标识类型",Toast.LENGTH_SHORT).show();

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

               // Toast.makeText(mContext,"修改",Toast.LENGTH_SHORT).show();
                showAlertDialog("modify","modify");
            }
        });


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();//关闭
            }
        });


        if(TextUtils.equals(sign,"mark")){

        lv.setAdapter(new CommonAdapter<MarkImgInfo>(mContext,markData,R.layout.item_person_baseinfo_mark) {
            @Override
            public void convert(CommonViewHolder holder, MarkImgInfo item, final int position) {

                LinearLayout ll=holder.getView(R.id.ll_item_person_baseinfo_mark);

                TextView noTv=holder.getView(R.id.tv_item_person_baseinfo_mark_no);
                noTv.setText((position+1)+"");
                TextView nameTv=holder.getView(R.id.tv_item_person_baseinfo_mark_name);
                nameTv.setText(markData.get(position).getMARK());
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

                    deleteBtn.setTextColor(Color.parseColor("#ff0000"));
                    dateTv.setVisibility(View.GONE);


            }
        });

        }else if(TextUtils.equals(sign,"zxMark")){

            lv.setAdapter(new CommonAdapter<StaffMarkInfo>(mContext,zxMarkData,R.layout.item_person_baseinfo_mark) {
            @Override
            public void convert(CommonViewHolder holder, StaffMarkInfo item, final int position) {

                LinearLayout ll=holder.getView(R.id.ll_item_person_baseinfo_mark);

                TextView noTv=holder.getView(R.id.tv_item_person_baseinfo_mark_no);
                noTv.setText((position+1)+"");
                TextView nameTv=holder.getView(R.id.tv_item_person_baseinfo_mark_name);
                nameTv.setText(zxMarkData.get(position).getType_Name());
                TextView dateTv=holder.getView(R.id.tv_item_person_baseinfo_mark_date);
                dateTv.setText(zxMarkData.get(position).getCREATE_DATE());
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

                    deleteBtn.setTextColor(Color.parseColor("#000000"));
                    dateTv.setVisibility(View.VISIBLE);

            }
        });

        }


    }

    private void showWhDialog(){//维护标识的对话框


        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);

        View view=LayoutInflater.from(mContext).inflate(R.layout.dialog_person_baseinfo_wh,null,false);

        builder.setView(view);

        final AlertDialog dialog=builder.create();

        dialog.show();

        Button btnAdd= (Button) view.findViewById(R.id.btn_dialog_person_baseinfo_wh_add);
        Button btnUpdate= (Button) view.findViewById(R.id.btn_dialog_person_baseinfo_wh_update);
        Button btnDelete= (Button) view.findViewById(R.id.btn_dialog_person_baseinfo_wh_delete);
        Button btnClose= (Button) view.findViewById(R.id.btn_dialog_person_baseinfo_wh_close);

        final EditText etName= (EditText) view.findViewById(R.id.et_dialog_person_baseinfo_wh_name);

      //  final String etNameStr=etName.getText().toString().trim();


        ListView lv= (ListView) view.findViewById(R.id.lv_dialog_person_baseinfo_wh);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                etName.setText(zxMarkData.get(position).getType_Name());
             //   etNameStr=etName.getText().toString().trim();
            }
        });



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etNameStr=etName.getText().toString().trim();
                showAlertDialog("add",etNameStr);//添加

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etNameStr=etName.getText().toString().trim();
                showAlertDialog("update",etNameStr);//修改
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etNameStr=etName.getText().toString().trim();
                showAlertDialog("delete",etNameStr);//删除
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        lv.setAdapter(new CommonAdapter<StaffMarkInfo>(mContext,zxMarkData,R.layout.item_person_baseinfo_mark) {
            @Override
            public void convert(CommonViewHolder holder, StaffMarkInfo item, final int position) {

                LinearLayout ll=holder.getView(R.id.ll_item_person_baseinfo_mark);

                TextView noTv=holder.getView(R.id.tv_item_person_baseinfo_mark_no);
                noTv.setText((position+1)+"");
                TextView nameTv=holder.getView(R.id.tv_item_person_baseinfo_mark_name);
                nameTv.setText(zxMarkData.get(position).getType_Name());
                TextView dateTv=holder.getView(R.id.tv_item_person_baseinfo_mark_date);
                dateTv.setText(zxMarkData.get(position).getCREATE_DATE());
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

                noTv.setVisibility(View.GONE);
                deleteBtn.setVisibility(View.GONE);
                dateTv.setVisibility(View.GONE);

            }
        });
    }

    private void showAlertDialog(String sign,String name){//提示对话框

        if(TextUtils.equals(name,"")){
            Toast.makeText(mContext,"名称不能为空！"+name,Toast.LENGTH_SHORT).show();
            return;
        }

     //   Toast.makeText(mContext,"名称=="+name,Toast.LENGTH_SHORT).show();

        String msgStr = null;

        String titleStr=null;

        if(TextUtils.equals(sign,"add")){
            msgStr="您确定要添加";
            titleStr="温馨提示";
        }else if(TextUtils.equals(sign,"update")){
            msgStr="您确定要修改";
            titleStr="温馨提示";
        }else if(TextUtils.equals(sign,"delete")){
            msgStr="您确定要删除";
            titleStr="温馨提示";
        }else if(TextUtils.equals(sign,"modify")){
            msgStr="您确认修改的标识上传服务器";
            titleStr="修改标识提示";
        }else if(TextUtils.equals(sign,"uploadPhoto")){
            msgStr="您确定要把头像，上传服务器";
            titleStr="上传头像确认提示";
        }

            AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        builder.setTitle(titleStr);
        builder.setMessage(msgStr+"吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


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

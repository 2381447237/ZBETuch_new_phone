package com.youli.zbetuch.jingan.activity;

import android.content.Context;
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
import com.youli.zbetuch.jingan.entity.WorkNoticeInfo;
import com.youli.zbetuch.jingan.entity.WorkRecordInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: zhengbin on 2017/10/9.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 工作日志
 */

public class WorkRecordActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext=WorkRecordActivity.this;

    private Button newBtn;
    private ListView lv;
    private List<WorkRecordInfo> data=new ArrayList<>();
    private CommonAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_record);

        initViews();

    }


    private void initViews(){

        newBtn= (Button) findViewById(R.id.btn_work_record_new);
        newBtn.setOnClickListener(this);
        lv= (ListView) findViewById(R.id.lv_work_record);
        initDates();

    }


    private void initDates(){

        for(int i=1;i<100;i++){

            data.add(new WorkRecordInfo("2017-10-9",""+i,"标题"+i));

        }

        adapter=new CommonAdapter<WorkRecordInfo>(mContext,data,R.layout.item_work_record) {
            @Override
            public void convert(CommonViewHolder holder, WorkRecordInfo item, int position) {

                TextView tvNo=holder.getView(R.id.item_work_record_no_tv);
                tvNo.setText(item.getNoStr());
                TextView tvTitle=holder.getView(R.id.item_work_record_title_tv);
                tvTitle.setText(item.getTitleStr());
                TextView tvDate=holder.getView(R.id.item_work_record_date_tv);
                tvDate.setText(item.getDateStr());

                LinearLayout ll = holder.getView(R.id.item_work_record_ll);
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

            case R.id.btn_work_record_new:

                Toast.makeText(mContext,"新建",Toast.LENGTH_SHORT).show();

                break;

        }

    }
}

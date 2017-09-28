package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.InterviewInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: zhengbin on 2017/9/28.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 现场面试
 */

public class InterviewActivity extends BaseActivity{

    private Context mContext=InterviewActivity.this;

    private List<InterviewInfo> data=new ArrayList<InterviewInfo>();
    private ListView lv;
    private CommonAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);

        initViews();
    }

    private void initViews(){

        lv= (ListView) findViewById(R.id.lv_interview);

        getData();

        lvSetAdapter();
    }

    private void getData(){

        for(int i=0;i<20;i++){

            data.add(new InterviewInfo("沪太所现场招聘会","4","2017-9-28 00:00:00","5","招聘会"));

        }

    }


    private void lvSetAdapter(){

        if(adapter==null){

            adapter=new CommonAdapter<InterviewInfo>(mContext,data,R.layout.item_lv_interview) {
                @Override
                public void convert(CommonViewHolder holder, InterviewInfo item, int position) {

                    TextView tvTitle=holder.getView(R.id.item_lv_interview_title);
                    tvTitle.setText(item.getTitle());
                    TextView tvDate=holder.getView(R.id.item_lv_interview_date);
                    tvDate.setText(item.getDate());
                    TextView tvComNum=holder.getView(R.id.item_lv_interview_company_num);
                    tvComNum.setText(item.getCompanyNum());
                    TextView tvPostNum=holder.getView(R.id.item_lv_interview_post_num);
                    tvPostNum.setText(item.getPostNum());
                    TextView tvAddress=holder.getView(R.id.item_lv_interview_address);
                    tvAddress.setText(item.getAddress());

                    LinearLayout ll=holder.getView(R.id.item_lv_interview_ll);
                    ll.setBackgroundResource(R.drawable.selector_interview_item);
                }
            };

            lv.setAdapter(adapter);

        }else if(adapter!=null){

            adapter.notifyDataSetChanged();

        }

    }

}

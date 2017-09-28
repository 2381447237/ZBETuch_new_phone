package com.youli.zbetuch.jingan.activity;

import android.content.Context;
import android.graphics.Color;
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
import com.youli.zbetuch.jingan.entity.FollowListInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: zhengbin on 2017/9/28.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 关注列表
 *
 */

public class FollowListActivity extends BaseActivity{

    private Context mContext=FollowListActivity.this;

    private ListView lv;
    private List<FollowListInfo> data=new ArrayList<>();
    private CommonAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_list);

        initViews();

    }

    private void initViews(){

        lv= (ListView) findViewById(R.id.lv_follow_list);

        getData();

        lvSetAdapter();

    }

    private void getData(){

        for(int i=0;i<100;i++){

            data.add(new FollowListInfo("123456789098765432","姓名"+i));

        }

    }

    private void lvSetAdapter(){

        if(adapter==null){

            adapter=new CommonAdapter<FollowListInfo>(mContext,data,R.layout.item_follow_list) {
                @Override
                public void convert(CommonViewHolder holder, FollowListInfo item, final int position) {

                    TextView tvName=holder.getView(R.id.tv_item_follow_list_name);
                    tvName.setText(data.get(position).getName());
                    TextView tvIdCard=holder.getView(R.id.tv_item_follow_list_idcard);
                    tvIdCard.setText(data.get(position).getIdCard());

                    LinearLayout ll=holder.getView(R.id.ll_item_follow_list);

                    if(position%2==0){
                        ll.setBackgroundColor(Color.parseColor("#C9E2FA"));
                    }else{
                        ll.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }

                    Button btnDetails=holder.getView(R.id.btn_item_follow_list_details);
                    btnDetails.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Toast.makeText(mContext,"查看详情"+position,Toast.LENGTH_SHORT).show();

                        }
                    });

                    Button btnFollow=holder.getView(R.id.btn_item_follow_list_follow);
                    btnFollow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Toast.makeText(mContext,"取消关注"+position,Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            };

            lv.setAdapter(adapter);

        }else if(adapter!=null){

            adapter.notifyDataSetChanged();

        }

    }

}

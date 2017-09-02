package com.youli.zbetuch.jingan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.activity.MeetDetailActivity;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.MeetNoticeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHengBin on 2017/9/2.
 */

public class CurrentMeetFragment extends Fragment implements AdapterView.OnItemClickListener{

    private View contentView;
    private ListView lv;
    private CommonAdapter adapter;
    private List<MeetNoticeInfo> data=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        contentView=LayoutInflater.from(getContext()).inflate(R.layout.fragment_current_meet,container,false);

        initViews(contentView);

        return contentView;
    }

    private void initViews(View view){

        lv= (ListView) contentView.findViewById(R.id.lv_fmt_current_meet);
        lv.setOnItemClickListener(this);

        for(int i=0;i<20;i++){

            data.add(new MeetNoticeInfo("9月"+i+"日当前会议","沪太路"+i+"号","2017-09-02 17:00:00"));

        }

        lvSetAdapter(data);

    }

    private void lvSetAdapter(List<MeetNoticeInfo> data){

        if(adapter==null){

            adapter=new CommonAdapter<MeetNoticeInfo>(getActivity(),data,R.layout.item_fmt_history_meet) {

                @Override
                public void convert(CommonViewHolder holder, MeetNoticeInfo item, int position) {

                    TextView tvNo=holder.getView(R.id.tv_item_fmt_hisory_meet_no);
                    tvNo.setText((position+1)+"");
                    TextView tvTheme=holder.getView(R.id.tv_item_fmt_hisory_meet_theme);
                    tvTheme.setText(item.getTheme());
                    TextView tvAddress=holder.getView(R.id.tv_item_fmt_hisory_meet_address);
                    tvAddress.setText(item.getAddress());
                    TextView tvTime=holder.getView(R.id.tv_item_fmt_hisory_meet_time);
                    tvTime.setText(item.getTime());

                    LinearLayout ll=holder.getView(R.id.ll_item_fmt_hisory_meet);

                    if (position % 2 != 0) {
                        ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item1);
                    } else {
                        ll.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item2);
                    }

                }
            };

            lv.setAdapter(adapter);

        }else{

            adapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), MeetDetailActivity.class);
        startActivity(intent);
    }
}

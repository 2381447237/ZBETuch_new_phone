package com.youli.zbetuch.jingan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.activity.MeetDetailActivity;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.MeetNoticeInfo;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by ZHengBin on 2017/9/2.
 */

public class HistoryMeetFragment extends Fragment implements AdapterView.OnItemClickListener{

    private View contentView;
    private PullToRefreshListView lv;
    private CommonAdapter adapter;
    private List<MeetNoticeInfo> data=new ArrayList<>();

    private final int SUCCEED=10001;
    private final int  PROBLEM=10002;

    private int PageIndex=0;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCEED:

                    if(PageIndex==0) {
                        data.clear();
                    }
                    data.addAll((List<MeetNoticeInfo>)msg.obj);
                    lvSetAdapter(data);


                    break;
                case PROBLEM:
                    Toast.makeText(getActivity(),"网络不给力",Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        contentView=LayoutInflater.from(getContext()).inflate(R.layout.fragment_history_meet,container,false);

        initViews(contentView);

        return contentView;
    }

    private void initViews(View view){

        lv= (PullToRefreshListView) contentView.findViewById(R.id.lv_fmt_history_meet);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
         lv.setOnItemClickListener(this);

        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                //刷新
                PageIndex=0;
                initDatas(PageIndex);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                //加载更多
                PageIndex++;
                initDatas(PageIndex);
            }
        });

       initDatas(PageIndex);

    }

    private void initDatas(final int pIndex){

        //http://web.youli.pw:89/Json/Get_Meeting_Master.aspx?State=false&page=0&rows=15
        new Thread(


                new Runnable() {
                    @Override
                    public void run() {

                        String meetUrl= MyOkHttpUtils.BaseUrl+"/Json/Get_Meeting_Master.aspx?State=false&page="+pIndex+"&rows=15";

                        Response response=MyOkHttpUtils.okHttpGet(meetUrl);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String meetStr=response.body().string();

                                if(meetStr!=null){

                                    Gson gson=new Gson();
                                    msg.obj=gson.fromJson(meetStr,new TypeToken<List<MeetNoticeInfo>>(){}.getType());
                                    msg.what=SUCCEED;
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }else{

                            msg.what=PROBLEM;

                        }

                        mHandler.sendMessage(msg);

                    }
                }


        ).start();

    }


    private void lvSetAdapter(List<MeetNoticeInfo> data){

        if(adapter==null){

            adapter=new CommonAdapter<MeetNoticeInfo>(getActivity(),data,R.layout.item_fmt_history_meet) {

                @Override
                public void convert(CommonViewHolder holder, MeetNoticeInfo item, int position) {

                    TextView tvNo=holder.getView(R.id.tv_item_fmt_hisory_meet_no);
                    tvNo.setText((position+1)+"");
                    TextView tvTheme=holder.getView(R.id.tv_item_fmt_hisory_meet_theme);
                    tvTheme.setText(item.getTITLE());
                    TextView tvAddress=holder.getView(R.id.tv_item_fmt_hisory_meet_address);
                    tvAddress.setText(item.getMEETING_ADD());
                    TextView tvTime=holder.getView(R.id.tv_item_fmt_hisory_meet_time);
                    tvTime.setText(MyDateUtils.stringToYMDHMS(item.getMEETING_TIME()));

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
        lv.onRefreshComplete();//停止刷新或加载更多
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent=new Intent(getActivity(), MeetDetailActivity.class);
        intent.putExtra("MEETINFO",data.get(position-1));
        startActivity(intent);

    }
}

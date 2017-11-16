package com.youli.zbetuch.jingan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.youli.zbetuch.jingan.activity.WorkRecordDetailActivity;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.WorkRecordInfo;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 作者: zhengbin on 2017/10/14.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 工作日志碎片
 */

public class WorkLogFragment extends BaseFragment{

    private final int SUCCESS_LIST=10000;
    private final int SUCCESS_NODATA=10001;
    private final int FAIL=10002;

    private int pageIndex;

    private View contentView;
    private PullToRefreshListView lv;
    private CommonAdapter adapter;
    private List<WorkRecordInfo> data=new ArrayList<>();

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS_LIST:

                    if(pageIndex==0) {
                        data.clear();
                    }
                    data.addAll((List<WorkRecordInfo>)msg.obj);

                    LvSetAdapter(data);
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }
                    break;



                case FAIL:

                    if(getActivity()!=null) {

                        Toast.makeText(getActivity(), "网络不给力", Toast.LENGTH_SHORT).show();
                    }
                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }
                    break;
                case SUCCESS_NODATA:

                    if (lv.isRefreshing()) {
                        lv.onRefreshComplete();
                    }
                    break;
            }

        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        contentView=LayoutInflater.from(getContext()).inflate(R.layout.fragment_work_log,container,false);


        isViewCreated=true;

        return contentView;
    }

    @Override
    public void lazyLoadData() {

        if(isViewCreated){

            //逻辑都写这里
            initViews();
        }
    }

    private void initViews(){

        lv= (PullToRefreshListView) contentView.findViewById(R.id.lv_fmt_work_log);
        lv.setMode(PullToRefreshBase.Mode.BOTH);

        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex=0;
                initDates();


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                pageIndex++;
                initDates();


            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(getActivity(), WorkRecordDetailActivity.class);
                intent.putExtra("info",data.get(position-1));
                startActivity(intent);

            }
        });
        initDates();

    }

    private void initDates(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                        // http://web.youli.pw:89/Json/Get_WorkToDate.aspx?rows=15&page=0
                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_WorkToDate.aspx?rows=15&page="+pageIndex;

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String resStr=response.body().string();

                                if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")){

                                    Gson gson=new Gson();
                                    msg.obj=gson.fromJson(resStr,new TypeToken<List<WorkRecordInfo>>(){}.getType());
                                    msg.what=SUCCESS_LIST;
                                }else{
                                    msg.what=SUCCESS_NODATA;
                                }
                                mHandler.sendMessage(msg);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }else{

                            msg.what=FAIL;
                            mHandler.sendMessage(msg);

                        }

                    }
                }

        ).start();

    }



    private void LvSetAdapter(List<WorkRecordInfo> data){

        if(getActivity()==null){
            return;
        }

        if(adapter==null){
            adapter=new CommonAdapter<WorkRecordInfo>(getActivity(),data,R.layout.item_fmt_work_log) {
                @Override
                public void convert(CommonViewHolder holder, WorkRecordInfo item, int position) {

                    TextView tvNo=holder.getView(R.id.tv_item_fmt_work_log_no);
                    tvNo.setText((position+1)+"");
                    TextView tvName=holder.getView(R.id.tv_item_fmt_work_log_name);
                    tvName.setText(item.getTITLE());
                    TextView tvDate=holder.getView(R.id.tv_item_fmt_work_log_time);
                    tvDate.setText(MyDateUtils.stringToYMD(item.getCREATE_DATE()));

                    LinearLayout ll=holder.getView(R.id.ll_item_fmt_work_log);

                    if (position % 2 == 0) {
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

}

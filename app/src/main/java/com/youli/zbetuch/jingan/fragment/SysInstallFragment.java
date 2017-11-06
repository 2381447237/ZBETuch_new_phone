package com.youli.zbetuch.jingan.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.activity.MainLayoutActivity;
import com.youli.zbetuch.jingan.adapter.CommonAdapter;
import com.youli.zbetuch.jingan.entity.CommonViewHolder;
import com.youli.zbetuch.jingan.entity.LoginInfo;
import com.youli.zbetuch.jingan.entity.SysInstallInfo;
import com.youli.zbetuch.jingan.utils.MyDateUtils;
import com.youli.zbetuch.jingan.utils.MyOkHttpUtils;

import java.io.IOException;
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
 * 系统安装碎片
 */

public class SysInstallFragment extends BaseFragment{
    private final int SUCCESS_LIST=10000;
    private final int SUCCESS_NODATA=10001;
    private final int FAIL=10002;

    private int pageIndex;
    private View contentView;
    private PullToRefreshListView lv;
    private CommonAdapter adapter;
    private List<SysInstallInfo> data=new ArrayList<>();

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case SUCCESS_LIST:

                    if(pageIndex==0) {
                        data.clear();
                    }
                    data.addAll((List<SysInstallInfo>)msg.obj);

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

        contentView=LayoutInflater.from(getContext()).inflate(R.layout.fragment_sys_install,container,false);


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

        lv= (PullToRefreshListView) contentView.findViewById(R.id.lv_fmt_sys_install);
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
        initDates();

    }


    private void initDates(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                        // http://web.youli.pw:89/Json/Get_TB_Staff_Pad_File.aspx?staff=2&page=0&rows=15
                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_TB_Staff_Pad_File.aspx?staff="+MainLayoutActivity.adminId+"&rows=15&page="+pageIndex;

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null){

                            try {
                                String resStr=response.body().string();

                                if(!TextUtils.equals(resStr,"")&&!TextUtils.equals(resStr,"[]")){

                                    Gson gson=new Gson();
                                    msg.obj=gson.fromJson(resStr,new TypeToken<List<SysInstallInfo>>(){}.getType());
                                    msg.what=SUCCESS_LIST;
                                }else{
                                    msg.what=SUCCESS_NODATA;
                                }
                                mHandler.sendMessage(msg);
                            } catch (IOException e) {
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


    private void LvSetAdapter(List<SysInstallInfo> data){

        if(getActivity()==null){
            return;
        }

        if(adapter==null){
            adapter=new CommonAdapter<SysInstallInfo>(getActivity(),data,R.layout.item_fmt_sys_install) {
                @Override
                public void convert(CommonViewHolder holder, SysInstallInfo item, int position) {

                    TextView tvNo=holder.getView(R.id.tv_item_fmt_sys_install_no);
                    tvNo.setText((position+1)+"");
                    TextView tvName=holder.getView(R.id.tv_item_fmt_sys_install_name);
                    tvName.setText(item.getNAME());
                    TextView tvType=holder.getView(R.id.tv_item_fmt_sys_install_type);
                    tvType.setText(item.getTYPE());
                    TextView tvDate=holder.getView(R.id.tv_item_fmt_sys_install_time);
                    tvDate.setText(MyDateUtils.stringToYMD(item.getCREATE_DATE()));

                    LinearLayout ll=holder.getView(R.id.ll_item_fmt_sys_install);

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

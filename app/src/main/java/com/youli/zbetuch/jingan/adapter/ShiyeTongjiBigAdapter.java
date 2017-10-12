package com.youli.zbetuch.jingan.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.entity.ShiyeTongjiInfo;
import com.youli.zbetuch.jingan.view.MyListView;

import java.util.List;

/**
 * 作者: zhengbin on 2017/10/12.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class ShiyeTongjiBigAdapter extends BaseAdapter{

    private List<ShiyeTongjiInfo> data;
    private Context mContext;
    private ShiyeTongjiChildAdapter childAdapter;
    private ListView lv;
    public ShiyeTongjiBigAdapter(List<ShiyeTongjiInfo> data, Context mContext,ListView lv) {
        this.data = data;
        this.mContext = mContext;
        this.lv=lv;
    }

    @Override
    public int getCount() {
        return data.size();
    }


    @Override
    public Object getItem(int position) {
        return data.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder vh;

        if(convertView==null){

            vh=new ViewHolder();

            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_shiye_tongji_big,null);
            vh.ivSign= (ImageView) convertView.findViewById(R.id.iv_item_shiye_tongji_big_sign);
            vh.tvName= (TextView) convertView.findViewById(R.id.tv_item_shiye_tongji_big_name);
            vh.tvNan= (TextView) convertView.findViewById(R.id.tv_item_shiye_tongji_big_nan);
            vh.tvNv= (TextView) convertView.findViewById(R.id.tv_item_shiye_tongji_big_nv);
            vh.tvAll= (TextView) convertView.findViewById(R.id.tv_item_shiye_tongji_big_all);
            vh.childLv= (MyListView) convertView.findViewById(R.id.lv_item_shiye_tongji_child);

            convertView.setTag(vh);
        }else{

            vh= (ViewHolder) convertView.getTag();

        }



        ShiyeTongjiInfo info=data.get(position);

        vh.tvName.setText(info.getName());
        vh.tvNan.setText(info.getSum_value_man()+"");
        vh.tvNv.setText(info.getSum_value_woman()+"");
        vh.tvAll.setText(info.getAll()+"");

        if(TextUtils.equals("登记失业",info.getType())){
            vh.ivSign.setImageResource(R.drawable.isdengji);
        }else{
            vh.ivSign.setImageResource(R.drawable.notdengji);
        }

        childAdapter=new ShiyeTongjiChildAdapter(info.getChildList(),mContext);
        vh.childLv.setAdapter(childAdapter);

        if(info.isCheck()){
            vh.childLv.setVisibility(View.VISIBLE);
        }else{
            vh.childLv.setVisibility(View.GONE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(position>1){

                    if(data.get(position).isCheck()){
                        data.get(position).setCheck(false);
                    }else{
                        for(ShiyeTongjiInfo info:data){

                            info.setCheck(false);

                        }

                        data.get(position).setCheck(true);
                    }

                }
                notifyDataSetChanged();
                lv.setSelection(position);
            }
        });

        return convertView;
    }

    class ViewHolder{

        ImageView ivSign;
        TextView tvName,tvNan,tvNv,tvAll;
        MyListView childLv;

    }

}

package com.youli.zbetuch.jingan.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

public class ShiyeTongjiChildAdapter extends BaseAdapter{

    private List<ShiyeTongjiInfo.ShiyeTongjiChildInfo> data;
    private Context mContext;

    public ShiyeTongjiChildAdapter(List<ShiyeTongjiInfo.ShiyeTongjiChildInfo> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {

        if(data!=null){
            return data.size();
        }

   return 0;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;

        if(convertView==null){

            vh=new ViewHolder();

            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_shiye_tongji_child,null);
            vh.ivSign= (ImageView) convertView.findViewById(R.id.iv_item_shiye_tongji_child_sign);
            vh.tvName= (TextView) convertView.findViewById(R.id.tv_item_shiye_tongji_child_name);
            vh.tvNan= (TextView) convertView.findViewById(R.id.tv_item_shiye_tongji_child_nan);
            vh.tvNv= (TextView) convertView.findViewById(R.id.tv_item_shiye_tongji_child_nv);
            vh.tvAll= (TextView) convertView.findViewById(R.id.tv_item_shiye_tongji_child_all);

            convertView.setTag(vh);
        }else{

            vh= (ViewHolder) convertView.getTag();

        }



        ShiyeTongjiInfo.ShiyeTongjiChildInfo info=data.get(position);

        vh.tvName.setText(info.getName());
        vh.tvNan.setText(info.getSum_value_man()+"");
        vh.tvNv.setText(info.getSum_value_woman()+"");
        vh.tvAll.setText(info.getAll()+"");


        if(TextUtils.equals("登记失业",info.getType())){
            vh.ivSign.setImageResource(R.drawable.isdengji);
        }else{
            vh.ivSign.setImageResource(R.drawable.notdengji);
        }

        if (position % 2 == 0) {
            convertView.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item1);
        } else {
            convertView.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item2);
        }

        return convertView;
    }

    class ViewHolder{

        ImageView ivSign;
        TextView tvName,tvNan,tvNv,tvAll;

    }

}

package com.youli.zbetuch.jingan.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.youli.zbetuch.jingan.entity.WhMarkInfo;

import java.util.List;

/**
 * Created by liutao on 2017/9/13.
 */
//专项标识对话框里面Spinner的适配器
public class WhSpAdapter extends BaseAdapter{

    private List<WhMarkInfo> data;
    private Context context;

    public WhSpAdapter(Context context, List<WhMarkInfo> data) {
        this.context = context;
        this.data = data;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;

        if(convertView==null){

            vh=new ViewHolder();

            convertView=LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,null,false);

            vh.nameTv= (TextView) convertView.findViewById(android.R.id.text1);

            convertView.setTag(vh);

        }else{

            vh= (ViewHolder) convertView.getTag();

        }

        vh.nameTv.setText(data.get(position).getNAME());

        return convertView;
    }

    class ViewHolder{

        TextView nameTv;

    }

}

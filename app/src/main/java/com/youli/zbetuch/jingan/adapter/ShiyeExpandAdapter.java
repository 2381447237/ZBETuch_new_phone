package com.youli.zbetuch.jingan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youli.zbetuch.jingan.R;
import com.youli.zbetuch.jingan.entity.ShiyeTongjiInfo;

import java.util.List;

/**
 * 作者: zhengbin on 2017/10/13.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class ShiyeExpandAdapter extends BaseExpandableListAdapter{

    private List<ShiyeTongjiInfo> groupData;
    private List<List<ShiyeTongjiInfo>> subData;
    private Context mContext;

    public ShiyeExpandAdapter(List<ShiyeTongjiInfo> groupData, Context mContext, List<List<ShiyeTongjiInfo>> subData) {
        this.groupData = groupData;
        this.mContext = mContext;
        this.subData = subData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return subData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return Integer.valueOf(subData.get(groupPosition).get(childPosition).getOrder_id());
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

       if(convertView==null){

           convertView= LayoutInflater.from(mContext).inflate(R.layout.item_shiye_tongji_child,null);

       }

        ImageView imgDj = (ImageView) convertView.findViewById(R.id.iv_item_shiye_tongji_child_sign);
        TextView lblName = (TextView) convertView.findViewById(R.id.tv_item_shiye_tongji_child_name);
        TextView lblManNum = (TextView) convertView
                .findViewById(R.id.tv_item_shiye_tongji_child_nan);
        TextView lblWomanNum = (TextView) convertView
                .findViewById(R.id.tv_item_shiye_tongji_child_nv);
        TextView lblAllNum = (TextView) convertView
                .findViewById(R.id.tv_item_shiye_tongji_child_all);

        if ("登记失业".equals(subData.get(groupPosition).get(childPosition)
                .getType().trim())) {
            imgDj.setImageResource(R.drawable.isdengji);
        } else {
            imgDj.setImageResource(R.drawable.notdengji);
        }
//        convertView.setBackgroundResource(MainTools
//                .getbackground1(childPosition));

        if (childPosition % 2 == 0) {
            convertView.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item1);
        } else {
            convertView.setBackgroundResource(R.drawable.selector_ziyuandiaocha_item2);
        }

        lblName.setText(subData.get(groupPosition).get(childPosition)
                .getName());
        lblManNum.setText(""
                + subData.get(groupPosition).get(childPosition)
                .getSum_value_man());
        lblWomanNum.setText(""
                + subData.get(groupPosition).get(childPosition)
                .getSum_value_woman());
        lblAllNum.setText(""
                + subData.get(groupPosition).get(childPosition).getAll());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return subData.get(groupPosition).size();
    }


    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return Integer.valueOf(groupData.get(groupPosition).getOrder_id());
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

      if(convertView==null){

          convertView=LayoutInflater.from(mContext).inflate(R.layout.item_shiye_tongji_big,null);

      }

        ImageView imgDj = (ImageView) convertView.findViewById(R.id.iv_item_shiye_tongji_big_sign);
        TextView lblName = (TextView) convertView.findViewById(R.id.tv_item_shiye_tongji_big_name);
        TextView lblManNum = (TextView) convertView
                .findViewById(R.id.tv_item_shiye_tongji_big_nan);
        TextView lblWomanNum = (TextView) convertView
                .findViewById(R.id.tv_item_shiye_tongji_big_nv);
        TextView lblAllNum = (TextView) convertView
                .findViewById(R.id.tv_item_shiye_tongji_big_all);

        if ("登记失业".equals(groupData.get(groupPosition).getType().trim())) {
            imgDj.setImageResource(R.drawable.isdengji);
        } else {
            imgDj.setImageResource(R.drawable.notdengji);
        }

        convertView.setBackgroundResource(R.drawable.policygroupitem);

        lblName.setText(groupData.get(groupPosition).getName());
        lblManNum
                .setText("" + groupData.get(groupPosition).getSum_value_man());
        lblWomanNum.setText(""
                + groupData.get(groupPosition).getSum_value_woman());
        lblAllNum.setText("" + groupData.get(groupPosition).getAll());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

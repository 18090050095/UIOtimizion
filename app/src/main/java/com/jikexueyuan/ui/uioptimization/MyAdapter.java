package com.jikexueyuan.ui.uioptimization;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15 0015.
 */
public class MyAdapter extends BaseAdapter {

    private Context mContext;

    private List<GoodModel> mData;

    public MyAdapter(Context context, List<GoodModel> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    public GoodModel getGood(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        Log.d("MyAdapter", "Position:" + position + "---"
                + String.valueOf(System.currentTimeMillis()));
        ViewHolder holder;
        if (convertView == null) {
            final LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.list_item_icon);
            holder.name = (TextView) convertView.findViewById(R.id.list_item_name);
            holder.buys = (TextView) convertView.findViewById(R.id.list_item_buys);
            holder.talks = (TextView) convertView.findViewById(R.id.list_item_talks);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(mData.get(position).name);
        holder.buys.setText(mData.get(position).buys + " 人购买");
        holder.talks.setText(mData.get(position).talks + " 人评论");

        return convertView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView name;   //商品名称
        TextView buys;   //购买人数
        TextView talks;  //评论人数
    }
}

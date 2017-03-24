package com.eq.EQSuperPlayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.bean.Areabean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/4.
 */
public class ItemAdapter extends BaseAdapter {
    private List<Areabean> areabeens;
    private Context context;

    public ItemAdapter(List<Areabean> areabeens, Context context) {
        this.areabeens = areabeens;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (areabeens != null) {
            return areabeens.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (areabeens != null) {
            return areabeens.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {

        TextView name;
        CheckBox cb;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.item_birth);
            viewHolder.cb = (CheckBox) convertView.findViewById(R.id.item_send);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            Areabean mAreabeen = areabeens.get(position);
            viewHolder.name.setText(mAreabeen.getName());
            viewHolder.cb.setText(mAreabeen.getNum());
        }
        return convertView;
    }
}
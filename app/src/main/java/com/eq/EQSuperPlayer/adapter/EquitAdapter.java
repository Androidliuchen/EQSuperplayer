package com.eq.EQSuperPlayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.bean.Areabean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */
public class EquitAdapter extends BaseAdapter {
    private Context context;
    private static List<Areabean> areabeens;
    private LayoutInflater layoutInflr;

    public EquitAdapter(Context context,List<Areabean> areabeens) {
        super();
        this.context = context;
        this.areabeens = areabeens;
        this.layoutInflr = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        TextView screen_name;
        TextView screen_model;
        TextView screen_ip;
        TextView screen_wh;
        TextView screen_prot;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = layoutInflr.inflate(R.layout.radio_itme, null);
            viewHolder.screen_name = (TextView) convertView
                    .findViewById(R.id.screen_name);
            viewHolder.screen_model = (TextView) convertView
                    .findViewById(R.id.screen_model);
            viewHolder.screen_ip = (TextView) convertView
                    .findViewById(R.id.screen_ip);
            viewHolder.screen_wh = (TextView) convertView
                    .findViewById(R.id.screen_wh);
            viewHolder.screen_prot = (TextView) convertView
                    .findViewById(R.id.screen_prot);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Areabean mAreabeen = areabeens.get(position);
        viewHolder.screen_name.setText(mAreabeen.getName());
        viewHolder.screen_wh.setText(mAreabeen.getWindowWidth() + "*" + mAreabeen.getWindowHeight());
        viewHolder.screen_model.setText(mAreabeen.getEquitType());
        viewHolder.screen_prot.setText(mAreabeen.getEquitProt());
        viewHolder.screen_ip.setText(mAreabeen.getEquitTp());
        return convertView;
    }
}



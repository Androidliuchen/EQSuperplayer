package com.eq.EQSuperPlayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.bean.Areabean;
import com.eq.EQSuperPlayer.bean.WiFiBean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/6.
 */
public class RadioAdapter extends BaseAdapter{
    private List<WiFiBean> wifiBeens;
    private Context context;

    public RadioAdapter(List<WiFiBean> wifiBeens, Context context) {
        this.wifiBeens = wifiBeens;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (wifiBeens != null) {
            return wifiBeens.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (wifiBeens != null) {
            return wifiBeens.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView screen_name;
        TextView screen_ip;
        TextView screen_mdeol;
        TextView screen_wh;
        TextView screen_prot;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.region_itme, null);
            viewHolder.screen_name = (TextView) convertView.findViewById(R.id.screen_name);
            viewHolder.screen_ip = (TextView) convertView.findViewById(R.id.screen_ip);
            viewHolder.screen_mdeol = (TextView) convertView.findViewById(R.id.screen_model);
            viewHolder.screen_wh = (TextView) convertView.findViewById(R.id.screen_wh);
            viewHolder.screen_prot = (TextView) convertView.findViewById(R.id.screen_prot);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            WiFiBean mWiFiBean = wifiBeens.get(position);
            viewHolder.screen_name.setText(mWiFiBean.getScreen());
            viewHolder.screen_ip.setText(mWiFiBean.getWifiIp());
            viewHolder.screen_mdeol.setText(mWiFiBean.getWifiIp());
            viewHolder.screen_wh.setText(mWiFiBean.getScreenWidth() +"*" +mWiFiBean.getScreenHeight());
            viewHolder.screen_prot.setText(mWiFiBean.getIpProt());
        }
        return convertView;
    }
}
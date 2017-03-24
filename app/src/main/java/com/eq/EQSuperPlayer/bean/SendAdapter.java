package com.eq.EQSuperPlayer.bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.eq.EQSuperPlayer.R;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */
public class SendAdapter extends BaseAdapter{
    private List<Areabean> areabeens;
    private Context context;

    public SendAdapter(List<Areabean> areabeens, Context context) {
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
        CheckBox checkBox;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView =  LayoutInflater.from(context).inflate(R.layout.send_itme, null);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.send_itme);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Areabean mAreabeen = areabeens.get(position);
        viewHolder.checkBox.setText(mAreabeen.getName());
        return convertView;
    }

}

package com.eq.EQSuperPlayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.bean.Areabean;
import com.eq.EQSuperPlayer.custom.CustomPopWindow;
import com.eq.EQSuperPlayer.fargament.EquitmentFragment;

import java.util.List;


/**
 * Created by Administrator on 2016/12/12.
 */
public class ProgramAdapter extends BaseAdapter {
    private Context context;
    private static List<Areabean> areabeens;
    private LayoutInflater layoutInflr;
    private OnRemoveListener mRemoveListener;
    private OnListener mOnListener;
    private boolean isChanged = EquitmentFragment.indexChang;

    public ProgramAdapter(Context context, List<Areabean> areabeens) {
        super();
        this.context = context;
        this.areabeens = areabeens;
        this.layoutInflr = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setRemoveListener(OnRemoveListener removeListener) {
        this.mRemoveListener = removeListener;
    }

    public void setListener(OnListener listener) {
        this.mOnListener = listener;
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
        TextView tvDelete;//隐藏的侧滑删除TextView
        TextView tvbianji;
        TextView name;
        ImageView num;
        TextView ip;
        TextView tvHeight;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = layoutInflr.inflate(R.layout.program_itme, null);
            viewHolder.name = (TextView) convertView
                    .findViewById(R.id.tv_name);
            viewHolder.num = (ImageView) convertView
                    .findViewById(R.id.tv_num);
            viewHolder.tvDelete = (TextView) convertView
                    .findViewById(R.id.tvDelete);
            viewHolder.tvbianji = (TextView) convertView
                    .findViewById(R.id.tvbianji);
            viewHolder.ip = (TextView) convertView
                    .findViewById(R.id.tv_ip);
            viewHolder.tvHeight = (TextView) convertView
                    .findViewById(R.id.tvHeight);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Areabean mAreabeen = areabeens.get(position);
        viewHolder.name.setText(mAreabeen.getName());
        viewHolder.tvHeight.setText(mAreabeen.getWindowWidth() + "*" + mAreabeen.getWindowHeight());
        viewHolder.ip.setText(mAreabeen.getEquitTp());
        if (isChanged) {
            viewHolder.num.setImageResource(R.drawable.online);
        }
        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRemoveListener != null)
                    mRemoveListener.onRemoveItem(position);
                    ProgramAdapter.this.notifyDataSetChanged();
            }
        });
        viewHolder.tvbianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnListener != null)
                    mOnListener.onClickListener(position);
            }
        });
        return convertView;
    }

    public interface OnRemoveListener {
        void onRemoveItem(int position);
    }

    public interface OnListener {
        void onClickListener(int position);
    }
}


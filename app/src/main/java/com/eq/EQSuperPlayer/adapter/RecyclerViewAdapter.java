package com.eq.EQSuperPlayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eq.EQSuperPlayer.R;

import java.util.List;

/**
 * Created by Administrator on 2017/1/17.
 */
public class RecyclerViewAdapter extends BaseAdapter {

    private static List<String> mDatas;
    private Context context;
    private LayoutInflater layoutInflr;
    private OnRemoveViewListener mRemoveListener;

    public RecyclerViewAdapter(Context context, List<String> mDatas ) {
        super();
        this.context = context;
        this.mDatas = mDatas;
    }

    public void setmRemoveViewListener(OnRemoveViewListener mRemoveListener) {
        this.mRemoveListener = mRemoveListener;
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDatas != null) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    static class ViewHolder {
        TextView ll_delete;
        TextView name;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        ViewHolder viewHolder ;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflr.from(context).inflate(R.layout.text_name, null);
            viewHolder.name = (TextView) convertView
                    .findViewById(R.id.tv_name_Re_left);
            viewHolder.ll_delete = (TextView) convertView
                    .findViewById(R.id.prmDelete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mDatas.get(position));
        viewHolder.ll_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRemoveListener != null)
                    mRemoveListener.onRemoveItem(position);
            }
        });
        return convertView;
    }
    public interface OnRemoveViewListener {
        void onRemoveItem(int position);
    }
}



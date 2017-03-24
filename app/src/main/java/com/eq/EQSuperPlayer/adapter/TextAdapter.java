package com.eq.EQSuperPlayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.bean.ProgramBean;
import com.eq.EQSuperPlayer.bean.TableBean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/30.
 */
public class TextAdapter extends BaseAdapter{
    private List<TableBean> tableBeens;
    private Context context;
    private LayoutInflater layoutInflr;
    private OnRemoveTextListener mRemoveListener;

    public TextAdapter(Context context, List<TableBean> tableBeens ) {
        super();
        this.context = context;
        this.tableBeens = tableBeens;
    }

    public void setmRemoveTextListener(OnRemoveTextListener mRemoveListener) {
        this.mRemoveListener = mRemoveListener;
    }

    @Override
    public int getCount() {
        if (tableBeens != null) {
            return tableBeens.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (tableBeens != null) {
            return tableBeens.get(position);
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
        ProgramBean programBean = (ProgramBean) tableBeens.get(position);
        viewHolder.name.setText(programBean.getName());
        viewHolder.ll_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRemoveListener != null)
                    mRemoveListener.onRemoveItem(position);
            }
        });
        return convertView;
    }
    public interface OnRemoveTextListener {
        void onRemoveItem(int position);
    }
}



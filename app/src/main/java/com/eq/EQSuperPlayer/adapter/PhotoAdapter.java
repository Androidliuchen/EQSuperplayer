package com.eq.EQSuperPlayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.bean.ImagePath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/10.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<ImagePath> imagePaths;
    private final static String TAG = "PhotoAdapter";

    public PhotoAdapter(Context context, List<ImagePath> imagePaths) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.imagePaths = imagePaths;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List<String> result = new ArrayList<>();
        for (ImagePath imagePath : imagePaths){
            result.add(imagePath.getPath());
        }
        Glide.with(context)
                .load(result.get(position))
                .centerCrop()
                .into(holder.ivPhoto);
    }

    @Override
    public int getItemCount() {
        return imagePaths.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);
        }

    }


}

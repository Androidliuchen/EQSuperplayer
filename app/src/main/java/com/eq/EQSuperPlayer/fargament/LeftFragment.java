package com.eq.EQSuperPlayer.fargament;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.SlidingMenuActivity.BrightnessActivity;
import com.eq.EQSuperPlayer.SlidingMenuActivity.LanguageActivity;
import com.eq.EQSuperPlayer.SlidingMenuActivity.OpenActivity;

public class LeftFragment extends Fragment implements View.OnClickListener {
    private View todayView;
    private View lastListView;
    private View commentsView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_menu, null);
        findViews(view);

        return view;
    }


    public void findViews(View view) {
        todayView = view.findViewById(R.id.tvToday);
        lastListView = view.findViewById(R.id.tvLastlist);
        commentsView = view.findViewById(R.id.tvMyComments);

        todayView.setOnClickListener(this);
        lastListView.setOnClickListener(this);
        commentsView.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tvToday: // 开关屏幕
                intent.setClass(getActivity(), OpenActivity.class);
                startActivity(intent);
                break;
            case R.id.tvLastlist:// 调节亮度
                intent.setClass(getActivity(), BrightnessActivity.class);
                startActivity(intent);
                break;
            case R.id.tvMyComments: // 语言
                intent.setClass(getActivity(), LanguageActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}

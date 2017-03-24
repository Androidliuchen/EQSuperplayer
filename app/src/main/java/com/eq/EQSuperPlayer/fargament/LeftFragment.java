package com.eq.EQSuperPlayer.fargament;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.eq.EQSuperPlayer.R;

public class LeftFragment extends Fragment implements View.OnClickListener {
    private View todayView;
    private View lastListView;
    private View discussView;
    private View favoritesView;
    private View commentsView;
    private View settingsView;

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
        discussView = view.findViewById(R.id.tvDiscussMeeting);
        favoritesView = view.findViewById(R.id.tvMyFavorites);
        commentsView = view.findViewById(R.id.tvMyComments);
        settingsView = view.findViewById(R.id.tvMySettings);

        todayView.setOnClickListener(this);
        lastListView.setOnClickListener(this);
        discussView.setOnClickListener(this);
        favoritesView.setOnClickListener(this);
        commentsView.setOnClickListener(this);
        settingsView.setOnClickListener(this);
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
        Fragment newContent = null;
        String title = null;
        switch (v.getId()) {
            case R.id.tvToday: // 开关屏幕
                newContent = new OpenFragment();
                title = getString(R.string.today);
                break;
            case R.id.tvLastlist:// 调节亮度
                newContent = new LastListFragment();
                title = getString(R.string.lastList);
                break;
            case R.id.tvDiscussMeeting: // 校正时间
                newContent = new DiscussFragment();
                title = getString(R.string.discussMeetting);
                break;
            case R.id.tvMyFavorites: // WiFi设置
                newContent = new MyFavoritesFragment();
                title = getString(R.string.myFavorities);
                break;
            case R.id.tvMyComments: // 语言
                newContent = new MyCommentsFragment();
                title = getString(R.string.myComments);
                break;
            case R.id.tvMySettings: // 关于
                newContent = new MySettingsFragment();
                title = getString(R.string.settings);
                break;
            default:
                break;
        }
//        if (newContent != null) {
//            switchFragment(newContent);
//        }
    }

    /**
     * 切换fragment
     * @param fragment
     */
//    private void switchFragment(Fragment fragment) {
//        if (getActivity() == null) {
//            return;
//        }
//        if (getActivity() instanceof MainActivity) {
//            MainActivity fca = (MainActivity) getActivity();
//            fca.switchContent(fragment);
//        }
//    }

}

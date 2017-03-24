package com.eq.EQSuperPlayer.fargament;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.activity.FragmentFactory;

/**
 * 显示主界面的Fragment
 */
public class MainFragment extends Fragment {
    private LinearLayout progarm_la = null;
    private LinearLayout control_la = null;
    private LinearLayout setting_la = null;
    private LinearLayout[] las = {progarm_la, control_la, setting_la};
    private int[] la_id = {R.id.main_la1, R.id.main_la2, R.id.main_la3};
    private int old_count = 0;  //记录上一次被选中的布局,默认选中0；
    private MyClick myClick;  //选中布局监听
    private ProgramFragment programFragment;
    private SendFragment sendFragment;
    private EquitmentFragment equitmentFragment;
    private FragmentTransaction transaction;
    private Fragment mContent = null;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        myClick = new MyClick();
        for (int i = 0; i < las.length; i++
                ) {
            las[i] = (LinearLayout) view.findViewById(la_id[i]);
            las[i].setOnClickListener(myClick);
        }
        //fragment 初始化
        if (programFragment == null) {
            programFragment = new ProgramFragment();
        }
        loadFragment(programFragment);
        return view;
    }

    private class MyClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_la1:
                    if (old_count != 0) {  //重复选中不刷新UI，节省开销,也可以选中的布局取消监听的方式来实现
                        las[old_count].setBackgroundResource(R.color.blud);
                        las[0].setBackgroundResource(R.color.bisque);
                        if (programFragment == null) {
                            programFragment = new ProgramFragment();
                        }
                        loadFragment(programFragment);
                        old_count = 0;
                    }
                    break;
                case R.id.main_la2:
                    if (old_count != 1) {
                        las[old_count].setBackgroundResource(R.color.blud);
                        las[1].setBackgroundResource(R.color.bisque);
                        if (sendFragment == null) {
                            sendFragment = new SendFragment();
                        }
                        loadFragment(sendFragment);
                        old_count = 1;
                    }
                    break;
                case R.id.main_la3:
                    if (old_count != 2) {
                        las[old_count].setBackgroundResource(R.color.blud);
                        las[2].setBackgroundResource(R.color.bisque);
                        if (equitmentFragment == null) {
                            equitmentFragment = new EquitmentFragment();
                        }
                        loadFragment(equitmentFragment);
                        old_count = 2;
                    }
                    break;
            }


        }
    }

    /*
       fragment 初始化
     */
    private void loadFragment(Fragment to) {

        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        //   if (mContent != to) {  //判断是否是重复加载fragment ，如果没有判断重复选中的情况，必须启用

        if (!to.isAdded()) { // 先判断是否被add过
            if (mContent != null) {  //初始化时调用，mContent为空

                transaction.hide(mContent).add(R.id.cancel_fragment, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {

                transaction.add(R.id.cancel_fragment, to).commit();
            }
        } else {
            transaction.hide(mContent).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
        mContent = to;


    }

}

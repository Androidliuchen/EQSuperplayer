package com.eq.EQSuperPlayer.activity;

import android.support.v4.app.Fragment;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.fargament.EquitmentFragment;
import com.eq.EQSuperPlayer.fargament.ProgramFragment;
import com.eq.EQSuperPlayer.fargament.SendFragment;

/**
 * Created by Administrator on 2016/12/14.
 */
public class FragmentFactory {
    public static Fragment getInstanceByIndex(int index) {
        Fragment fragment = null;
        switch (index) {
            case R.id.program:
                fragment = new ProgramFragment();
                break;
            case R.id.send:
                fragment = new SendFragment();
                break;
            case R.id.equitment:
                fragment = new EquitmentFragment();
                break;
        }
        return fragment;
    }
}

package com.eq.EQSuperPlayer.utils;

import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.eq.EQSuperPlayer.R;


/**
 * Created by Administrator on 2016/12/26.
 */
public class AnimationManager {
    /*
      动画选择
       */
    public static Animation GetAnimation(Context context, int type) {

        switch (type) {
            case 1:
                return MoveUp(context);
            case 2:
                return MoveDown(context);
            case 3:
                return MoveLeft(context);
            case 4:
                return MoveRight(context);
            case 5:
                return Flashing(context);
            default:
                return null;

        }

    }

    /*
    上移动画
     */
    public static Animation MoveUp(Context context) {
        Animation translateAnimation = AnimationUtils.loadAnimation(context, R.anim.moveup_anim);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    /*
     下移动画
      */
    public static Animation MoveDown(Context context) {
        Animation translateAnimation = AnimationUtils.loadAnimation(context, R.anim.movedown_anim);
        return translateAnimation;
    }

    /*
    左移动画
      */
    public static Animation MoveLeft(Context context) {
        Animation translateAnimation = AnimationUtils.loadAnimation(context, R.anim.moveleft_anim);
        return translateAnimation;
    }

    /*
    右移动画
      */
    public static Animation MoveRight(Context context) {
        Animation translateAnimation = AnimationUtils.loadAnimation(context, R.anim.moveright_anim);
        return translateAnimation;
    }

    //闪烁动画
    public static Animation Flashing(Context context){
        AlphaAnimation alphaAnimation1 = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation1.setRepeatCount(Animation.INFINITE);
        alphaAnimation1.setRepeatMode(Animation.REVERSE);
        return alphaAnimation1;
    }
}

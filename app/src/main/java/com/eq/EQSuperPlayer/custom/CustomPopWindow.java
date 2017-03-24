package com.eq.EQSuperPlayer.custom;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.utils.InterfaceClick;
import com.eq.EQSuperPlayer.utils.Utils;

/**
 * Created by Administrator on 2016/12/12.
 */
public class CustomPopWindow extends PopupWindow {
    private Activity at;
    private int id;   //控件的id。。位置xx控件之上下左右
    private InterfaceClick onClickListener;

    public CustomPopWindow(Activity at, int id) {
        super();
        this.at = at;
        this.id = id;
    }


    public void setView(View contentView, float width, float height) {
        // 设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        int w = (int) (Utils.getUiwidth(at) * width);
        int h = (int) (Utils.getUiheight(at) * height);
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(h);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
                if (onClickListener != null) {
                    onClickListener.Click();
                }
            }

        });
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.AnimationPreview);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = at.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        at.getWindow().setAttributes(lp);
    }
    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
//            int[] location = new int[2];
//            parent.getLocationOnScreen(location);
//            this.showAtLocation(parent, Gravity.NO_GRAVITY, location[0] + parent.getWidth(), location[1]);
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, parent.getLayoutParams().width , 10);
        } else {
            this.dismiss();
        }
    }
}

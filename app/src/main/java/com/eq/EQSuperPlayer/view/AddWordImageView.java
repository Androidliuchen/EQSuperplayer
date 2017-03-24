package com.eq.EQSuperPlayer.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.eq.EQSuperPlayer.bean.TextBean;

/**
 * Created by Administrator on 2017/2/13.
 */
public class AddWordImageView extends ImageView {
    private int borderColor = Color.TRANSPARENT;
    private int textColor = Color.WHITE;
    private boolean isStroke = false;
    public AddWordImageView(Context context) {
        super(context);
    }

    public AddWordImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddWordImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public boolean isStroke() {
        return isStroke;
    }

    public void setStroke(boolean stroke) {
        isStroke = stroke;
    }
    public void setBorderColor(int borderColor, int textColor, boolean isStroke) {
        this.borderColor = borderColor;
        this.textColor = textColor;
        this.isStroke = isStroke;
        invalidate();
    }

    public int getBorderColor() {
        return borderColor;
    }
}

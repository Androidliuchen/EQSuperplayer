package com.eq.EQSuperPlayer.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by pc on 2017/3/17.
 */
public class MarqueeButton extends Button {

    public MarqueeButton(Context context) {
        this(context,null);
    }

    public MarqueeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);

        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);
    }

    public MarqueeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusable(true);
        setFocusableInTouchMode(true);

        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);
    }
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect)
    {
        if (focused)
        {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean focused)
    {
        if (focused)
        {
            super.onWindowFocusChanged(focused);
        }
    }

    @Override
    public boolean isFocused()
    {
        return true;
    }

}

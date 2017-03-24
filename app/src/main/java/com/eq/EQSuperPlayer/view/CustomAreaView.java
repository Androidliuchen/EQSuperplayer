package com.eq.EQSuperPlayer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/12/23.
 */
public class CustomAreaView extends View
        implements View.OnClickListener{
    private int mBorderColor;
    private boolean mCanMove = true;
    private boolean mCanZoom = true;
    private int mOffset = 0;
    private OnAreaClickListener mOnAreaClickListener;

    public CustomAreaView(Context paramContext)
    {
        super(paramContext);
        setOnClickListener(this);
    }

    public CustomAreaView(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        setOnClickListener(this);
    }

    public boolean getCanMove()
    {
        return this.mCanMove;
    }

    public boolean getCanZoom()
    {
        return this.mCanZoom;
    }

    public int getInitX()
    {
        int[] arrayOfInt = new int[2];
        getLocationInWindow(arrayOfInt);
        return arrayOfInt[0];
    }

    public int getInitY()
    {
        int[] arrayOfInt = new int[2];
        getLocationInWindow(arrayOfInt);
        return arrayOfInt[1];
    }

    public void onClick(View paramView)
    {
        if (this.mOnAreaClickListener != null)
            this.mOnAreaClickListener.onAreaClick(paramView);
    }

    protected void onDraw(Canvas paramCanvas)
    {
        super.onDraw(paramCanvas);
        Paint localPaint = new Paint();
        localPaint.setStrokeWidth(2.0F);
        localPaint.setStyle(Paint.Style.STROKE);
        localPaint.setColor(this.mBorderColor);
        paramCanvas.drawRect(this.mOffset, this.mOffset, getWidth(), getHeight(), localPaint);
    }

    public void setBorderColor(int paramInt)
    {
        this.mBorderColor = paramInt;
    }

    public void setCanMove(boolean paramBoolean)
    {
        this.mCanMove = paramBoolean;
    }

    public void setCanZoom(boolean paramBoolean)
    {
        this.mCanZoom = paramBoolean;
    }

    public void setOnAreaClickListener(OnAreaClickListener paramOnAreaClickListener)
    {
        this.mOnAreaClickListener = paramOnAreaClickListener;
    }

    public static abstract interface OnAreaClickListener
    {
        public abstract void onAreaClick(View paramView);
    }
}

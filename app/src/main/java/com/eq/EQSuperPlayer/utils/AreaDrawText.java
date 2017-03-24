package com.eq.EQSuperPlayer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.bean.Areabean;
import com.eq.EQSuperPlayer.bean.TextBean;

/**
 * Created by Administrator on 2016/12/23.
 */
public abstract class AreaDrawText extends Context {
    public static void DrawArea(Context context, Canvas canvas, TextBean textBean) {
                drawText(context, canvas, textBean);
    }


//    private static void drawBitmap(Context context, Canvas canvas, ImageBean imageBean,int times){
//        //设置画笔属性
//        Paint paint1 = new Paint();
////        List<Bitmap> bitmaps = imageBean.getBitmaps();
//    }
    private static void drawText(Context context, Canvas canvas, TextBean textBean) {
        Utils.SplitScreen(textBean.getTexts(), textBean.getSingleTextValue(), textBean.getWidth(), textBean.getPaint());
        String show_str = textBean.getSingleTextValue();
        //设置画笔属性
        Paint paint1 = new Paint();
        paint1.setFakeBoldText(textBean.isStBold()); // 字体是否加粗
        Utils.setTypeface(context, paint1, (context.getResources().getStringArray(R.array.typeface_path))[textBean.getStTypeFace()]);
        paint1.setTextSize(Utils.getPaintSize(context, textBean.getStSize())); // 字体大小 进度条参数
        int[] number_colors = new int[]{textBean.getBorderColor(), textBean.getStBackground(), textBean.getStColor()};
        for (int i = 0; i < number_colors.length; i++) {
            switch (number_colors[i]) {
                case 0:
                    number_colors[i] = Color.YELLOW;
                    break;
                case 1:
                    number_colors[i] = Color.MAGENTA;
                    break;
                case 2:
                    number_colors[i] = Color.RED;
                    break;
                case 3:
                    number_colors[i] = Color.GREEN;
                    break;
                case 4:
                    number_colors[i] = Color.WHITE;
                    break;
                case 5:
                    number_colors[i] = Color.BLUE;
                    break;
                case 6:
                    number_colors[i] = Color.BLACK;
                    break;
                case 7:
                    number_colors[i] = Color.GRAY;
                    break;
            }
        }
        paint1.setColor(number_colors[2]); // 字体颜色 选择的 button **
        if (textBean.getStTypeFace() == 1 && textBean.isStBold() == true) {
            paint1.setTypeface(Typeface.DEFAULT_BOLD);
        }
        Areabean areaBean = new Areabean();
        if (!show_str.equals("")) {
            Paint.FontMetrics fm1 = paint1.getFontMetrics();
            float text_x = 0;
            float text_y = 0;
            //文本x坐标
//            text_x = (textBean.getWidth() - Utils.getTextWidth(paint1, show_str)) / 2;
            //文本y坐标
            text_y = -fm1.ascent + 1 + (textBean.getHeidht() - Utils.getFontHeight(paint1)) / 2 ;

            canvas.drawText(show_str, text_x, text_y, paint1);
            drawLine(context, canvas, areaBean.getArea_X(), areaBean.getArea_Y(), textBean.getWidth(), textBean.getHeidht());
        }
    }

    /*
      绘制边框
     */
    private static void drawLine(Context context, Canvas canvas, int x, int y, int widht, int height) {

        TextBean textBean = new TextBean();
        int[] number_colors = new int[]{textBean.getBorderColor(), textBean.getStBackground(), textBean.getStColor()};
        for (int i = 0; i < number_colors.length; i++) {
            switch (number_colors[i]) {
                case 0:
                    number_colors[i] = Color.YELLOW;
                    break;
                case 1:
                    number_colors[i] = Color.MAGENTA;
                    break;
                case 2:
                    number_colors[i] = Color.RED;
                    break;
                case 3:
                    number_colors[i] = Color.GREEN;
                    break;
                case 4:
                    number_colors[i] = Color.WHITE;
                    break;
                case 5:
                    number_colors[i] = Color.BLUE;
                    break;
                case 6:
                    number_colors[i] = Color.BLACK;
                    break;
                case 7:
                    number_colors[i] = Color.GRAY;
                    break;
            }
        }
            Paint paint2 = new Paint();
            paint2.setColor(number_colors[0]);
            int line = Utils.dip2px(context, 3.0f);
            paint2.setStrokeWidth(line);
            //上
            canvas.drawLine(line + x , line + y ,
                    -line + x  + widht , line + y , paint2);
            //左
            canvas.drawLine(line + x , line + y ,
                    line + x , y  + height  , paint2);
            //下
            canvas.drawLine(line + x ,
                    -line + y  + height ,
                    x  + widht  - line,
                    -line + y  + height ,
                    paint2);
            //右
            canvas.drawLine(x  + widht  - line,
                    line + y ,
                    x  + widht  - line,
                    y  + height ,
                    paint2);
    }

//    /*
//     获取bitmap
//    */
//    public static Bitmap getText(String content, TextBean textBean, ArrayList<Integer> bmpWidthList,Context context) {
//        Bitmap bitmap = Bitmap.createBitmap(textBean.getWidth(), textBean.getHeidht(), Bitmap.Config.ARGB_8888);
//        Utils.SplitScreen(textBean.getTexts(), textBean.getSingleTextValue(), textBean.getWidth(), textBean.getPaint());
//        content = textBean.getSingleTextValue();
//        Paint paint = textBean.getPaint();
//        int[] number_colors = new int[]{textBean.getBorderColor(), textBean.getStBackground(), textBean.getStColor()};
//        for (int i = 0; i < number_colors.length; i++) {
//            switch (number_colors[i]) {
//                case 0:
//                    number_colors[i] = Color.YELLOW;
//                    break;
//                case 1:
//                    number_colors[i] = Color.MAGENTA;
//                    break;
//                case 2:
//                    number_colors[i] = Color.RED;
//                    break;
//                case 3:
//                    number_colors[i] = Color.GREEN;
//                    break;
//                case 4:
//                    number_colors[i] = Color.WHITE;
//                    break;
//                case 5:
//                    number_colors[i] = Color.BLUE;
//                    break;
//                case 6:
//                    number_colors[i] = Color.BLACK;
//                    break;
//                case 7:
//                    number_colors[i] = Color.GRAY;
//                    break;
//            }
//        }
//        paint.setColor(number_colors[2]); // 字体颜色 选择的 button **
//        if (textBean.getStTypeFace() == 1 && textBean.isStBold() == true){
//            paint.setTypeface(Typeface.DEFAULT_BOLD);
//        }
//        Canvas canvas = new Canvas(bitmap); // 创建画布
//        canvas.drawColor(number_colors[1]); // 颜色黑色
//        Paint.FontMetrics fm = paint.getFontMetrics();
//        float text_x = (textBean.getWidth() - Utils.getTextWidth(paint, content)) / 2;
//        float text_y = -fm.ascent + 1 + (textBean.getHeidht() - Utils.getFontHeight(paint)) / 2;
//        bmpWidthList.add(Utils.getTextWidth(paint, content));
//        canvas.drawText(content, text_x, text_y, paint);
//        return bitmap;
//    }
    /*
     获取bitmap
    */
    public static Bitmap getText( TextBean textBean) {
        Paint paint = textBean.getPaint();
        String content = textBean.getSingleTextValue();
        int textWidth = content.length();
        Bitmap bitmap = Bitmap.createBitmap(textWidth, textBean.getHeidht(), Bitmap.Config.ARGB_8888);
        int[] number_colors = new int[]{textBean.getBorderColor(), textBean.getStBackground(), textBean.getStColor()};
        for (int i = 0; i < number_colors.length; i++) {
            switch (number_colors[i]) {
                case 0:
                    number_colors[i] = Color.YELLOW;
                    break;
                case 1:
                    number_colors[i] = Color.MAGENTA;
                    break;
                case 2:
                    number_colors[i] = Color.RED;
                    break;
                case 3:
                    number_colors[i] = Color.GREEN;
                    break;
                case 4:
                    number_colors[i] = Color.WHITE;
                    break;
                case 5:
                    number_colors[i] = Color.BLUE;
                    break;
                case 6:
                    number_colors[i] = Color.BLACK;
                    break;
                case 7:
                    number_colors[i] = Color.GRAY;
                    break;
            }
        }
        paint.setColor(number_colors[2]); // 字体颜色 选择的 button **
        if (textBean.getStTypeFace() == 1 && textBean.isStBold() == true){
            paint.setTypeface(Typeface.DEFAULT_BOLD);
        }
        Canvas canvas = new Canvas(bitmap); // 创建画布
        canvas.drawColor(number_colors[1]); // 颜色黑色
//        Paint.FontMetrics fm = paint.getFontMetrics();
//        float text_x = (textBean.getWidth() - Utils.getTextWidth(paint, content)) / 2;
//        float text_y = -fm.ascent + 1 + (textBean.getHeidht() - Utils.getFontHeight(paint)) / 2;
        canvas.drawText(content, content.length(), textBean.getHeidht() / 2, paint);
        if (bitmap.getWidth() <= textBean.getWidth() ){
//            Utils.saveMyBitmap(bitmap,"001");
        }else{
            copyBitmap(textBean);
        }

        return bitmap;
    }
    public static void copyBitmap(TextBean textBean){
        int x = 0;
        int y = getText(textBean).getHeight();
        for (int i = textBean.getWidth(); i < getText(textBean).getWidth();i++){
            if(x + i <= getText(textBean).getWidth()){
                Bitmap bmp = Bitmap.createBitmap(getText(textBean), x, 0,textBean.getWidth(),y,null,true);
                Utils.saveMyBitmap(bmp,"text" + i);
                x += i;
            }
        }
    }
}
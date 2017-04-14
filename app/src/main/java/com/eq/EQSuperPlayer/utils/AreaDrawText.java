package com.eq.EQSuperPlayer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.bean.Areabean;
import com.eq.EQSuperPlayer.bean.EQ_DateFile_Asc;
import com.eq.EQSuperPlayer.bean.TextBean;
import com.eq.EQSuperPlayer.bean.TimeBean;
import com.eq.EQSuperPlayer.custom.Constant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.cos;

/**
 * Created by Administrator on 2016/12/23.
 */
public abstract class AreaDrawText extends Context {
    public static void DrawArea(Context context, Canvas canvas, TextBean textBean) {
                drawText(context, canvas, textBean);
    }
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
                    number_colors[i] = R.color.yellow;
                    break;
                case 1:
                    number_colors[i] = R.color.dodgerblue;
                    break;
                case 2:
                    number_colors[i] = R.color.red;
                    break;
                case 3:
                    number_colors[i] =R.color.lime;
                    break;
                case 4:
                    number_colors[i] = R.color.mediumorchid;
                    break;
                case 5:
                    number_colors[i] = R.color.blue;
                    break;
                case 6:
                    number_colors[i] =R.color.black;
                    break;
                case 7:
                    number_colors[i] = R.color.white;
                    break;
                case 8:
                    number_colors[i] = R.color.grey;
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
    //获取模拟时钟Bitmap
    public static Byte getTime(Context context, TimeBean timeBean) {

        Paint paint = Utils.getPaint(context, Utils.getPaintSize(context, Integer.parseInt(context.getResources().getStringArray(R.array.text_size)[timeBean.getM_rgbClockTextSize()])
                + Constant.FONT_SIZE_CORRECTION));//字体参数启动读取
        Utils.setTypeface(context, paint, (context.getResources().getStringArray(R.array.typeface_path))[timeBean.getNumber_typeface()]);
        int[] number_colors = new int[]{timeBean.getM_rgbClockTextColor(), timeBean.getM_rgbDayTextColor()
                , timeBean.getM_rgbWeekTextColor(), timeBean.getM_rgbTimeColor(),
                timeBean.getSecondcolor(), timeBean.getMinutecolor(),
                timeBean.getHourscolor(), timeBean.getFenbiaocolorposition(),
                timeBean.getShibiaocolorposition()};
        for (int i = 0; i < number_colors.length; i++) {
            switch (number_colors[i]) {
                case 0:
                    number_colors[i] = Color.YELLOW;
                    break;
                case 1:
                    number_colors[i] = Color.GREEN;
                    break;
                case 2:
                    number_colors[i] = Color.RED;
                    break;
                case 3:
                    number_colors[i] = Color.BLACK;
                    break;
            }
        }
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        paint.setColor(number_colors[0]);//重置画笔颜色
        Bitmap bitmap = Bitmap.createBitmap(timeBean.getTimeTowidth(), timeBean.getTimeToheidht(), Bitmap.Config.ARGB_8888);
        Canvas ca = new Canvas(bitmap); // 创建画布
        ca.drawColor(Color.BLACK); // 颜色黑色
        Paint.FontMetrics fm = paint.getFontMetrics();
        //获取时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        timeBean.setMillSecond(calendar.get(Calendar.MILLISECOND));
        timeBean.setSecond(calendar.get(Calendar.SECOND));
        timeBean.setMinute(calendar.get(Calendar.MINUTE));
        timeBean.setHour(calendar.get(Calendar.HOUR));

        //偏移
        int[] timelag = timeBean.splitStrTimeLag(timeBean.getM_strTimeLag());
        if (timeBean.getM_nOffset() == 0) {   //超前
            calendar.add(Calendar.DATE, timeBean.getM_nDayLag());
            calendar.add(Calendar.HOUR, timelag[0]);
            calendar.add(Calendar.MINUTE, timelag[1]);
        } else {//滞后
            calendar.add(Calendar.DATE, -timeBean.getM_nDayLag());
            calendar.add(Calendar.HOUR, -timelag[0]);
            calendar.add(Calendar.MINUTE, -timelag[1]);
        }

        boolean[] str_ints = timeBean.getStrShowFormInt(timeBean.getM_strShowForm());
        EQ_DateFile_Asc[] m_gStuAsc = timeBean.getAsc();
        for (int i = 0; i < m_gStuAsc.length; i++) {
            m_gStuAsc[i] = new EQ_DateFile_Asc();
        }
        GetDateText(calendar, timeBean, str_ints);
        GetWeekText(calendar, timeBean, str_ints);
        GetTimeText(calendar, timeBean, str_ints);
        for(int i=0;i<100;i++)
        {
            m_gStuAsc[i].setM_byVaild(0);
        }
        int m_iAscPosition = 0;
        if (timeBean.getM_nClockType() == 3) { //模拟时钟

            float borderWidth = timeBean.getWidth_circle();
            // 获取宽高参数
            if (timeBean.getTimeTowidth() <= timeBean.getTimeToheidht()) {
                int mWidth = Math.min(timeBean.getTimeTowidth(), timeBean.getTimeToheidht());
                int mHeight = Math.max(timeBean.getTimeTowidth(), timeBean.getTimeToheidht());
                //画刻度线
                Paint paintLine = new Paint();
                for (int i = 0; i < 60; i++) {
                    if (i % 5 == 0) {
                        paintLine.setColor(number_colors[8]);
                    } else {
                        paintLine.setColor(number_colors[7]);
                    }
                    ca.drawCircle(mWidth / 2, mHeight / 2 - mWidth / 2 + borderWidth, timeBean.getRadius_center(), paintLine);
                    //ca.drawLine(mWidth / 2, mHeight / 2 - mWidth / 2 + borderWidth, mWidth / 2, mHeight / 2 - mWidth / 2 + lineLength, paintLine);
                    ca.rotate(360 / 60, mWidth / 2, mHeight / 2);
                }
                //刻度数字
                String targetText[] = context.getResources().getStringArray(R.array.clock);
                //绘制时间文字
                float startX = mWidth / 2 - paint.measureText(targetText[1]) / 2;
                float startY = mHeight / 2 - mWidth / 2 + borderWidth;
                float textR = (float) Math.sqrt(Math.pow(mWidth / 2 - startX, 2) + Math.pow(mHeight / 2 - startY, 2));

                for (int i = 0; i < 12; i++) {
                    float x = (float) (startX + Math.sin(Math.PI / 6 * i) * textR);
                    float y = (float) (startY + textR - cos(Math.PI / 6 * i) * textR);
                    if (i != 11 && i != 10 && i != 0) {
                        y = y + paint.measureText(targetText[i]) / 2;
                    } else {
                        x = x - paint.measureText(targetText[i]) / 4;
                        y = y + paint.measureText(targetText[i]) / 4;
                    }
                    ca.drawText(targetText[i], x, y, paint);

                }
                //绘制秒针
                paint.setColor(number_colors[4]);
                paint.setStrokeWidth(timeBean.getWidth_second());
                float degree = timeBean.getRefresh_time() > 1000 ? (float) (timeBean.getSecond() * 360 / 60) : (float) (timeBean.getSecond() * 360 / 60 + timeBean.getMillSecond() / 1000 * 360 / 60);
                ca.rotate(degree, mWidth / 2, mHeight / 2);
                ca.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - (mWidth / 2 - timeBean.getWidth_circle()) * timeBean.getDensity_second(), paint);
                ca.rotate(-degree, mWidth / 2, mHeight / 2);

                //绘制分针
                paint.setColor(number_colors[5]);
                paint.setStrokeWidth(timeBean.getWidth_minutes());
                float degree2 = (float) (timeBean.getMinute() * 360 / 60);
                ca.rotate(degree2, mWidth / 2, mHeight / 2);
                ca.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - (mWidth / 2 - timeBean.getWidth_circle()) * timeBean.getDensity_minute(), paint);
                ca.rotate(-degree2, mWidth / 2, mHeight / 2);

                //绘制时针
                paint.setColor(number_colors[6]);
                paint.setStrokeWidth(timeBean.getWidth_hour());
                float degreeHour = (float) timeBean.getHour() * 360 / 12;
                float degreeMinut = (float) timeBean.getMinute() / 60 * 360 / 12;
                float degree3 = degreeHour + degreeMinut;
                ca.rotate(degree3, mWidth / 2, mHeight / 2);
                ca.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - (mWidth / 2 - timeBean.getWidth_circle()) * timeBean.getDensity_hour(), paint);
                ca.rotate(-degree3, mWidth / 2, mHeight / 2);
                // 画圆心
                paint.setStyle(Paint.Style.FILL);
                ca.drawCircle(mWidth / 2, mHeight / 2, timeBean.getRadius_center(), paint);
                // 绘表标题
                if (!timeBean.getM_strClockText().isEmpty()) {
                    String fixed_text = timeBean.getM_strClockText();
                    float textX = mWidth / 2 - paint.measureText(fixed_text) / 2;
                    float textY = mHeight / 2 - mWidth / 4;
                    paint.setColor(number_colors[4]);
                    ca.drawText(fixed_text, textX, textY, paint);
                }
                //绘制日期
                if (timeBean.isDateshow() == true){
                    int j=0;
                    m_gStuAsc[j].setM_byAsc('M');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j].setM_byAsc('M');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j++].setM_byVaild(0x10);
                    m_gStuAsc[j++].setM_byVaild(0x2);

                    m_gStuAsc[j].setM_byAsc('D');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j].setM_byAsc('D');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j++].setM_byVaild(0x10);
                    m_gStuAsc[j++].setM_byVaild(0x2);

                    long time=System.currentTimeMillis();
                    Date date=new Date(time);
                    paint.setColor(number_colors[5]);
                    SimpleDateFormat format=new SimpleDateFormat("MM月dd日");
                    String textDay = format.format(date);
                    float textDayX = mWidth / 2 - paint.measureText(textDay) / 2;
                    float textDayY =  mHeight / 4 + mHeight / 2;
                    ca.drawText(textDay,textDayX,textDayY,paint);
                }
                //绘制星期
                if (timeBean.isWeekshow() == true) {
                    int j = 0;
                    m_gStuAsc[j++].setM_byVaild(0x10);
                    m_gStuAsc[j++].setM_byVaild(0x2);
                    m_gStuAsc[j++].setM_byVaild(0x10);
                    m_gStuAsc[j++].setM_byVaild(0x2);
                    m_gStuAsc[j].setM_byAsc('W');
                    m_gStuAsc[j++].setM_byVaild(0x11);
                    m_gStuAsc[j].setM_byAsc('W');
                    m_gStuAsc[j++].setM_byVaild(0x1);

                    long time = System.currentTimeMillis();
                    Date date = new Date(time);
                    paint.setColor(number_colors[6]);
                    SimpleDateFormat format = new SimpleDateFormat("EEEE");
                    String weekDay =format.format(date);
                    double height = Math.ceil(fm.descent - fm.ascent);

                    float weekDayX = mWidth / 2 - paint.measureText(weekDay) / 2;
                    double weekDayY = mHeight / 4 + mHeight / 2 + height;
                    ca.drawText(weekDay, weekDayX, (float) weekDayY, paint);
                }
            } else {
                int mWidth = Math.max(timeBean.getTimeTowidth(), timeBean.getTimeToheidht());
                int mHeight = Math.min(timeBean.getTimeTowidth(), timeBean.getTimeToheidht());
                //画刻度线
                Paint paintLine = new Paint();
                for (int i = 0; i < 60; i++) {
                    if (i % 5 == 0) {
                        paintLine.setColor(number_colors[8]);
                    } else {
                        paintLine.setColor(number_colors[7]);
                    }
                    ca.drawCircle(mWidth / 2, borderWidth, timeBean.getRadius_center(), paintLine);
                    ca.rotate(360 / 60, mWidth / 2, mHeight / 2);
                }
                //刻度数字
                String targetText[] = context.getResources().getStringArray(R.array.clock);
                //绘制时间文字
                float startX = mWidth / 2 - paint.measureText(targetText[1]) / 2;
                float startY = borderWidth;
                float textR = (float) Math.sqrt(Math.pow(mWidth / 2 - startX, 2) + Math.pow(mHeight / 2 - startY, 2));

                for (int i = 0; i < 12; i++) {
                    float x = (float) (startX + Math.sin(Math.PI / 6 * i) * textR);
                    float y = (float) (startY + textR - cos(Math.PI / 6 * i) * textR);
                    if (i != 11 && i != 10 && i != 0) {
                        y = y + paint.measureText(targetText[i]) / 2;
                    } else {
                        x = x - paint.measureText(targetText[i]) / 4;
                        y = y + paint.measureText(targetText[i]) / 4;
                    }
                    ca.drawText(targetText[i], x, y, paint);
                }
                //绘制秒针
                paint.setColor(number_colors[4]);
                paint.setStrokeWidth(timeBean.getWidth_second());
                float degree = timeBean.getRefresh_time() > 1000 ? (float) (timeBean.getSecond() * 360 / 60) : (float) (timeBean.getSecond() * 360 / 60 + timeBean.getMillSecond() / 1000 * 360 / 60);
                ca.rotate(degree, mWidth / 2, mHeight / 2);
                ca.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - (mWidth / 3 - timeBean.getWidth_circle()) * timeBean.getDensity_second(), paint);
                ca.rotate(-degree, mWidth / 2, mHeight / 2);

                //绘制分针
                paint.setColor(number_colors[5]);
                paint.setStrokeWidth(timeBean.getWidth_minutes());
                float degree2 = (float) (timeBean.getMinute() * 360 / 60);
                ca.rotate(degree2, mWidth / 2, mHeight / 2);
                ca.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - (mWidth / 3 - timeBean.getWidth_circle()) * timeBean.getDensity_minute(), paint);
                ca.rotate(-degree2, mWidth / 2, mHeight / 2);

                //绘制时针
                paint.setColor(number_colors[6]);
                paint.setStrokeWidth(timeBean.getWidth_hour());
                float degreeHour = (float) timeBean.getHour() * 360 / 12;
                float degreeMinut = (float) timeBean.getMinute() / 60 * 360 / 12;
                float degree3 = degreeHour + degreeMinut;
                ca.rotate(degree3, mWidth / 2, mHeight / 2);
                ca.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - (mWidth / 3 - timeBean.getWidth_circle()) * timeBean.getDensity_hour(), paint);
                ca.rotate(-degree3, mWidth / 2, mHeight / 2);
                // 画圆心
                paint.setStyle(Paint.Style.FILL);
                ca.drawCircle(mWidth / 2, mHeight / 2, timeBean.getRadius_center(), paint);

                //绘制固定文字
                String fixed_text = timeBean.getM_strClockText();
                float textX = mWidth / 2 - paint.measureText(fixed_text) / 2;
                float textY = mHeight / 4;
                paint.setColor(number_colors[4]);
                ca.drawText(fixed_text, textX, textY, paint);

                //绘制日期
                if (timeBean.isDateshow() == true){
                    int j=0;
                    m_gStuAsc[j].setM_byAsc('M');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j].setM_byAsc('M');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j++].setM_byVaild(0x10);
                    m_gStuAsc[j++].setM_byVaild(0x2);

                    m_gStuAsc[j].setM_byAsc('D');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j].setM_byAsc('D');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j++].setM_byVaild(0x10);
                    m_gStuAsc[j++].setM_byVaild(0x2);
                    long time = System.currentTimeMillis();
                    Date date = new Date(time);
                    paint.setColor(number_colors[5]);
                    SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
                    String textDay = format.format(date);
                    float textDayX = mWidth / 2 - paint.measureText(textDay) / 2;
                    float textDayY = mHeight / 4 + mHeight / 2;
                    ca.drawText(textDay, textDayX, textDayY, paint);
                }
                //绘制星期
                if (timeBean.isWeekshow() == true){
                    int j = 0;
                    m_gStuAsc[j++].setM_byVaild(0x10);
                    m_gStuAsc[j++].setM_byVaild(0x2);
                    m_gStuAsc[j++].setM_byVaild(0x10);
                    m_gStuAsc[j++].setM_byVaild(0x2);
                    m_gStuAsc[j].setM_byAsc('W');
                    m_gStuAsc[j++].setM_byVaild(0x11);
                    m_gStuAsc[j].setM_byAsc('W');
                    m_gStuAsc[j++].setM_byVaild(0x1);

                    long time = System.currentTimeMillis();
                    Date date = new Date(time);
                    paint.setColor(number_colors[6]);
                    SimpleDateFormat format = new SimpleDateFormat("EEEE");
                    String weekDay =format.format(date);
                    double height = Math.ceil(fm.descent - fm.ascent);
                    float weekDayX = mWidth / 2 - paint.measureText(weekDay) / 2;
                    double weekDayY = mHeight / 4 + mHeight / 2 + height;
                    ca.drawText(weekDay, weekDayX, (float) weekDayY, paint);
                }
            }
        } else {  //数字时钟
            //偏移
//            int[] timelag = timeDateBean.splitStrTimeLag(timeDateBean.getM_strTimeLag());
            if (timeBean.getM_nOffset() == 0) {   //超前
                calendar.add(Calendar.DATE, timeBean.getM_nDayLag());
                calendar.add(Calendar.HOUR, timelag[0]);
                calendar.add(Calendar.MINUTE, timelag[1]);
            } else {//滞后
                calendar.add(Calendar.DATE, -timeBean.getM_nDayLag());
                calendar.add(Calendar.HOUR, -timelag[0]);
                calendar.add(Calendar.MINUTE, -timelag[1]);
            }
            String year = String.valueOf(calendar.get(Calendar.YEAR));//获取年份
            if (timeBean.getM_nYearType() == 1) { //2位年，把年份截取一半
                year = year.substring(2, 4);
            }
            String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);//获取月份
            if (month.length() == 1) {
                month = "0" + month;
            }
            String day = String.valueOf(calendar.get(Calendar.DATE));//获取日
            if (day.length() == 1) {
                day = "0" + day;
            }
            String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));//小时
            if (hour.length() == 1) {
                hour = "0" + hour;
            }
            String minute = String.valueOf(calendar.get(Calendar.MINUTE));//分
            if (minute.length() == 1) {
                minute = "0" + minute;
            }
            String second = String.valueOf(calendar.get(Calendar.SECOND));//秒
            if (second.length() == 1) {
                second = "0" + second;
            }
            int WeekOfYear = calendar.get(Calendar.DAY_OF_WEEK);//一周的第几天
            //整合数据 ，年月日一组
            String date = "";
            //时钟风格  0,1,2
            int clockStyle = timeBean.getM_nClockType();
            if (LanguageUtil.loadData(context).equals("en") && clockStyle == 2) { //当语言环境为英语的时候，风格2 等于风格0
                clockStyle = 0;
            }
            String space = "";
            if (clockStyle == 0) {
                space = "-";
            }
            if (clockStyle == 1) {
                space = "/";
            }
            //组织文字
            if (str_ints[0] == true) {
                if (clockStyle == 2) {
                    date += year + "年";
                } else {
                    date += year;
                }
            }
            if (str_ints[1] == true) {
                if (clockStyle == 2) {
                    date += month + "月";
                } else {
                    if (!date.equals("")) {
                        date += space + month;
                    } else {
                        date += month;
                    }
                }
            }
            if (str_ints[2] == true) {
                if (clockStyle == 2) {
                    date += day + "日";
                } else {
                    if (!date.equals("")) {
                        date += space + day;
                    } else {
                        date += day;
                    }
                }
            }
            if ((str_ints[0] | str_ints[1] | str_ints[2]) & (str_ints[3] | str_ints[4]) & timeBean.getM_nRowType() == 0) {
                date += " ";
            }
            String week = "";
            if (str_ints[3] == true) {
                if (!LanguageUtil.loadData(context).equals("en")) {
                    String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
                    week += weekDays[WeekOfYear - 1];
                } else {
                    String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
                    week += weekDays[WeekOfYear - 1];
                }
            }
            if (str_ints[3] == true & str_ints[4] == true & timeBean.getM_nRowType() == 0) {
                week += " ";
            }
            String time = "";
            if (str_ints[4] == true) {
                if (clockStyle == 2) {
                    time += hour + "时";
                } else {
                    time += hour;
                }
            }
            if (str_ints[5] == true) {
                if (clockStyle == 2) {
                    time += minute + "分";
                } else {
                    if (!time.equals("")) {
                        time += ":" + minute;
                    } else {
                        time += minute;
                    }
                }
            }
            if (str_ints[6] == true) {
                if (clockStyle == 2) {
                    time += second + "秒";
                } else {
                    if (!time.equals("")) {
                        time += ":" + second;
                    } else {
                        time += second;
                    }
                }
            }
            String fixed_text = timeBean.getM_strClockText();
            int time_x = 0, time_y = 0;
            if (timeBean.getM_nRowType() == 0) { //单行
                String show_str = fixed_text + date + week + time;
                time_x = (int) (timeBean.getTimeTowidth() - Utils.getTextWidth(paint, show_str)) / 2;
                time_y = (int) ((-fm.ascent + 1) + (timeBean.getTimeTowidth() - Utils.getFontHeight(paint)) / 2);
                //绘制固定文字
                if (!fixed_text.equals("")) {
                    paint.setColor(number_colors[0]);
                    ca.drawText(fixed_text, time_x, time_y, paint);
                    time_x += paint.measureText(fixed_text);
                }
                //绘日期
                if (!date.equals("")) {
                    ClockTextOut(time_x, time_y, date, timeBean.getM_rgbDayTextColor(), timeBean, 0);
                    paint.setColor(number_colors[1]);
                    ca.drawText(date, time_x, time_y, paint);
                    time_x += paint.measureText(date);
                }
                //绘制星期
                if (!week.equals("")) {
                    ClockTextOut(time_x, time_y, week, timeBean.getM_rgbWeekTextColor(), timeBean, 1);
                    paint.setColor(number_colors[2]);
                    ca.drawText(week, time_x, time_y, paint);
                    time_x += paint.measureText(week);
                }
                //绘制时间
                if (!time.equals("")) {
                    ClockTextOut(time_x, time_y, time, timeBean.getM_rgbTimeColor(), timeBean, 0);
                    paint.setColor(number_colors[3]);
                    ca.drawText(time, time_x, time_y, paint);
                }
            } else { //多行
                time_y = (int) (-fm.ascent + 1);
                int nRow = 0;
                if (!fixed_text.equals("")) {
                    nRow++;
                }
                if (!date.equals("")) {
                    nRow++;
                }
                if (!week.equals("")) {
                    nRow++;
                }
                if (!time.equals("")) {
                    nRow++;
                }

                if (nRow > 0) {
                    //计算每行高
                    int nRowHeight = (timeBean.getTimeToheidht()) / (nRow);
                    //绘固定文字
                    if (!fixed_text.equals("")) {
                        paint.setColor(number_colors[0]);
                        time_x = (int) (timeBean.getTimeTowidth() - Utils.getTextWidth(paint, fixed_text)) / 2;
                        ca.drawText(fixed_text, time_x, time_y + (int) ((nRowHeight - Utils.getFontHeight(paint)) / 2), paint);
                        time_y += nRowHeight;
                    }
                    //绘日期
                    if (!date.equals("")) {
                        paint.setColor(number_colors[1]);
                        time_x = (int) ((timeBean.getTimeToX()) + (timeBean.getTimeTowidth() - Utils.getTextWidth(paint, date)) / 2);
                        ca.drawText(date, time_x, time_y + (int) ((nRowHeight - Utils.getFontHeight(paint)) / 2), paint);
                        ClockTextOut(time_x, time_y + (int) ((nRowHeight - Utils.getFontHeight(paint)) / 2), date, timeBean.getM_rgbDayTextColor(), timeBean, 0);
                        time_y += nRowHeight;
                    }
                    //绘制星期
                    if (!week.equals("")) {
                        paint.setColor(number_colors[2]);
                        time_x = (int) (timeBean.getTimeTowidth() - Utils.getTextWidth(paint, week)) / 2;
                        ca.drawText(week, time_x, time_y + (int) ((nRowHeight - Utils.getFontHeight(paint)) / 2), paint);
                        ClockTextOut(time_x, time_y + (int) ((nRowHeight - Utils.getFontHeight(paint)) / 2), week, timeBean.getM_rgbWeekTextColor(), timeBean, 1);
                        time_y += nRowHeight;
                    }
                    //绘制时间
                    if (!time.equals("")) {
                        paint.setColor(number_colors[3]);
                        time_x = (int) ((timeBean.getTimeToX()) + (timeBean.getTimeTowidth() - Utils.getTextWidth(paint, time)) / 2);
                        ca.drawText(time, time_x, time_y + (int) ((nRowHeight - Utils.getFontHeight(paint)) / 2), paint);
                        ClockTextOut(time_x, time_y + (int) ((nRowHeight - Utils.getFontHeight(paint)) / 2), time, timeBean.getM_rgbTimeColor(), timeBean, 0);
                    }
                }
            }
        }
        return null;
    }

    /*
      ASc类属性赋值,int  dex 属性， 星期str后不带时间str 时，ilen 必须+1 ，否则报错
    */
    private static void ClockTextOut(int x, int y, String strTextOut, int crColor, TimeBean timeBean, int dex) {
        int i;
        int byColor;
        if (crColor == 2) {
            byColor = 1;        //1 2 3  分别是红绿黄  在android数组里，我设定相反
        } else if (crColor == 1) {
            byColor = 2;
        } else if (crColor == 0) {
            byColor = 3;
        } else {
            byColor = 1;
        }

        int iLen = strTextOut.length();
        if (dex == 1 && strTextOut.indexOf(" ") == -1) {  ///这段逻辑 可能存在错误
            iLen++;
            strTextOut += " ";
        }
        Log.d("test", "循环次数" + iLen + " " + dex + " " + strTextOut.indexOf(" "));
        String str = "";
        int m_iAscPosition = 0;
        EQ_DateFile_Asc[] m_gStuAsc = timeBean.getAsc();
        for (i = 0; i < iLen; i++) {
            if (m_gStuAsc[m_iAscPosition].getM_byVaild() == 0x10) {
                str += strTextOut.charAt(i);
                m_iAscPosition++;
            } else if (m_gStuAsc[m_iAscPosition].getM_byVaild() == 0x11){

                str += strTextOut.charAt(i);
                m_gStuAsc[m_iAscPosition].setM_byColor(byColor);
                m_gStuAsc[m_iAscPosition].setM_wx(x);
                m_gStuAsc[m_iAscPosition].setM_wy(y);
                m_gStuAsc[m_iAscPosition].setM_byVaild(0x80);
                m_iAscPosition++;
                m_gStuAsc[m_iAscPosition].setM_byColor(byColor);
                m_gStuAsc[m_iAscPosition].setM_byVaild(0x80);
                m_iAscPosition++;
            } else if ((m_gStuAsc[m_iAscPosition].getM_byVaild() & 0x01) == 1){

                str += strTextOut.charAt(i);
                m_gStuAsc[m_iAscPosition].setM_byColor(byColor);
                m_gStuAsc[m_iAscPosition].setM_wx(x);
                m_gStuAsc[m_iAscPosition].setM_wy(y);
                m_gStuAsc[m_iAscPosition].setM_byVaild(0x80);
                m_iAscPosition++;
            } else {
                str += strTextOut.charAt(i);
                if (str == ""){
                    str = "a";
                }
                m_iAscPosition++;
            }
//            x += timeBean.getPaint().measureText(str);
            str = "";
        }

    }

    /*
       获取日期字符串
     */
    private static String GetDateText(Calendar calendar, TimeBean timeBean, boolean[] showform) {
        int i, j = 0;
        EQ_DateFile_Asc[] m_gStuAsc = timeBean.getAsc();
        for (i = 0; i < 100; i++) {
            if (m_gStuAsc[i].getM_byVaild() == 0) {
                j = i;
                break;
            }
        }
        String strText = "";
        if (timeBean.getM_nClockType() == 0) {
            if (showform[0]) {
                strText += (timeBean.getM_nYearType() == 0 ? ("yyyy-") : ("yy-"));
                if (timeBean.getM_nYearType() == 0) {
                    m_gStuAsc[j].setM_byAsc('Y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j].setM_byAsc('Y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j].setM_byAsc('Y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j].setM_byAsc('Y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j++].setM_byVaild(0x2);
                } else {
                    m_gStuAsc[j].setM_byAsc('y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j].setM_byAsc('y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j++].setM_byVaild(0x2);
                }
            }

            if (showform[1]) {
                strText += "MM-";
                m_gStuAsc[j].setM_byAsc('M');
                m_gStuAsc[j++].setM_byVaild(0x1);
                m_gStuAsc[j].setM_byAsc('M');
                m_gStuAsc[j++].setM_byVaild(0x1);
                m_gStuAsc[j++].setM_byVaild(0x2);
            }

            if (showform[2]) {
                strText += "dd-";
                m_gStuAsc[j].setM_byAsc('D');
                m_gStuAsc[j++].setM_byVaild(0x1);
                m_gStuAsc[j].setM_byAsc('D');
                m_gStuAsc[j++].setM_byVaild(0x1);
                m_gStuAsc[j++].setM_byVaild(0x2);
            }
            strText = strText.substring(strText.lastIndexOf('-'));

            j=j-1;
            m_gStuAsc[j].setM_byVaild(0);
        }
        else if(timeBean.getM_nClockType() == 1){
            if (showform[0]){
                strText+=(timeBean.getM_nYearType() ==0?("yyyy/"):("yy/"));//年
                if(timeBean.getM_nYearType() == 0){
                    m_gStuAsc[j].setM_byAsc('Y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j].setM_byAsc('Y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j].setM_byAsc('Y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j].setM_byAsc('Y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j++].setM_byVaild(0x2);
                }else{
                    m_gStuAsc[j].setM_byAsc('y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j].setM_byAsc('y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j++].setM_byVaild(0x2);
                }
            }

            if (showform[1]){
                strText+=("MM/");	//月
                m_gStuAsc[j].setM_byAsc('M');
                m_gStuAsc[j++].setM_byVaild(0x1);
                m_gStuAsc[j].setM_byAsc('M');
                m_gStuAsc[j++].setM_byVaild(0x1);
                m_gStuAsc[j++].setM_byVaild(0x2);
            }

            if (showform[2]){
                strText+=("dd/");	//日
                m_gStuAsc[j].setM_byAsc('D');
                m_gStuAsc[j++].setM_byVaild(0x1);
                m_gStuAsc[j].setM_byAsc('D');
                m_gStuAsc[j++].setM_byVaild(0x1);
                m_gStuAsc[j++].setM_byVaild(0x2);
            }
            strText = strText.substring(strText.lastIndexOf('/'));
            j=j-1;
            m_gStuAsc[j].setM_byVaild(0);
        } else if(timeBean.getM_nClockType() == 2) {
            if (showform[0]) {
                strText+=(timeBean.getM_nYearType()==0?"yyyy年":"yy年");//年
                if(timeBean.getM_nYearType()==0) {
                    m_gStuAsc[j].setM_byAsc('Y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j].setM_byAsc('Y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j].setM_byAsc('Y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j].setM_byAsc('Y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j++].setM_byVaild(0x2);
                    m_gStuAsc[j++].setM_byVaild(0x2);
                } else{
                    m_gStuAsc[j].setM_byAsc('y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j].setM_byAsc('y');
                    m_gStuAsc[j++].setM_byVaild(0x1);
                    m_gStuAsc[j++].setM_byVaild(0x2);
                    m_gStuAsc[j++].setM_byVaild(0x2);
                }
            }

            if (showform[1]){
                strText+="MM月";//月
                m_gStuAsc[j].setM_byAsc('M');
                m_gStuAsc[j++].setM_byVaild(0x1);
                m_gStuAsc[j].setM_byAsc('M');
                m_gStuAsc[j++].setM_byVaild(0x1);
                m_gStuAsc[j++].setM_byVaild(0x2);
                m_gStuAsc[j++].setM_byVaild(0x2);
            }
            if (showform[2]){
                strText+=("dd日");//日
                m_gStuAsc[j].setM_byAsc('D');
                m_gStuAsc[j++].setM_byVaild(0x1);
                m_gStuAsc[j].setM_byAsc('D');
                m_gStuAsc[j++].setM_byVaild(0x1);
                m_gStuAsc[j++].setM_byVaild(0x2);
                m_gStuAsc[j++].setM_byVaild(0x2);
            }
        }

        if( (showform[0]) || (showform[1]) || (showform[2]) ){
            if(timeBean.getM_nRowType()==0){
                if( (showform[3]) || (showform[4]) ){
                    strText += "";
                    m_gStuAsc[j++].setM_byVaild(0x2);
                }
            }
        }
        return strText;
    }


    /*
    获取星期字符串
    */
    private static String GetWeekText(Calendar calendar, TimeBean timeBean, boolean[] showform) {
        int i, j = 0;
        EQ_DateFile_Asc[] m_gStuAsc = timeBean.getAsc();
        for (i = 0; i < 100; i++) {
            if (m_gStuAsc[i].getM_byVaild() == 0) {
                j = i;
                break;
            }
        }

        if (showform[3] == false) {
            return "";
        }

        String strText ="";
        switch(calendar.get(Calendar.DAY_OF_WEEK)){
            case 1:	strText=("星期一"); break;
            case 2:	strText=("星期二"); break;
            case 3:	strText=("星期三"); break;
            case 4:	strText=("星期四"); break;
            case 5:	strText=("星期五"); break;
            case 6:	strText=("星期六"); break;
            case 7:	strText=("星期日"); break;
        }

        m_gStuAsc[j++].setM_byVaild(0x10);
        m_gStuAsc[j++].setM_byVaild(0x2);

        m_gStuAsc[j++].setM_byVaild(0x10);
        m_gStuAsc[j++].setM_byVaild(0x2);

        m_gStuAsc[j].setM_byAsc('W');
        m_gStuAsc[j++].setM_byVaild(0x11);
        m_gStuAsc[j].setM_byAsc('W');
        m_gStuAsc[j++].setM_byVaild(0x1);

        if( (showform[3])  ){
            if(timeBean.getM_nRowType()==0){
                if(showform[4]){
                    strText+="";
                    m_gStuAsc[j++].setM_byVaild(0x2);
                }
            }
        }
        return strText;
    }


    /*
       获取时间字符串
       */
    private static String GetTimeText(Calendar calendar, TimeBean timeBean, boolean[] showform) {
        int i, j = 0;
        EQ_DateFile_Asc[] m_gStuAsc = timeBean.getAsc();
        for (i = 0; i < 100; i++) {
            if (m_gStuAsc[i].getM_byVaild() == 0){
                j = i;
                break;
            }
        }
        String strText = "";
        if (showform[4]) {
            strText += (timeBean.getM_nClockType() == 2 ? ("HH点") : ("HH:"));
            m_gStuAsc[j].setM_byAsc('h');
            m_gStuAsc[j++].setM_byVaild(0x1);
            m_gStuAsc[j].setM_byAsc('h');
            m_gStuAsc[j++].setM_byVaild(0x1);


            if (timeBean.getM_nClockType() == 2) {
                m_gStuAsc[j++].setM_byVaild(0x10);
                m_gStuAsc[j++].setM_byVaild(0x02);
            } else {
                m_gStuAsc[j++].setM_byVaild(02);
            }
        }

        if (showform[5]) {
            strText += (timeBean.getM_nClockType() == 2 ? ("MM分"):("MM:"));//分
            m_gStuAsc[j].setM_byAsc('m');
            m_gStuAsc[j++].setM_byVaild(0x1);
            m_gStuAsc[j].setM_byAsc('m');
            m_gStuAsc[j++].setM_byVaild(0x1);

            if (timeBean.getM_nClockType() == 2) {
                m_gStuAsc[j++].setM_byVaild(0x10);
                m_gStuAsc[j++].setM_byVaild(0x02);
            } else {
                m_gStuAsc[j++].setM_byVaild(02);
            }
        }
        if (showform[6]) {
            strText += (timeBean.getM_nClockType()==2?("ss秒"):("ss:"));//秒
            m_gStuAsc[j].setM_byAsc('s');
            m_gStuAsc[j++].setM_byVaild(0x1);
            m_gStuAsc[j].setM_byAsc('s');
            m_gStuAsc[j++].setM_byVaild(0x1);

            if (timeBean.getM_nClockType() == 2) {
                m_gStuAsc[j++].setM_byVaild(0x10);
                m_gStuAsc[j++].setM_byVaild(0x02);
            } else {
                m_gStuAsc[j++].setM_byVaild(02);
            }
        }
        return timeBean.getM_nClockType()== 2 ? strText : strText.substring(strText.lastIndexOf(':'));
    }

}
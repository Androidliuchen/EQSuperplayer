package com.eq.EQSuperPlayer.bean;

import android.graphics.Paint;
import android.text.Editable;

import com.eq.EQSuperPlayer.activity.ProgramActivity;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.thoughtworks.xstream.annotations.XStreamAlias;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */
@XStreamAlias("TextBean")
@DatabaseTable(tableName = "tb_text")
public class TextBean extends TotalBean{
    @DatabaseField(foreign=true,foreignAutoRefresh=true)
    public ProgramBean programBean;
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    public byte[] textImageBytes;
    public Paint paint;
    @DatabaseField(columnName = "singleTextValue")
    public String singleTextValue = " "; //文本内容
    @DatabaseField(columnName = "textType")
    public int textType = 0; //文本类型
    @DatabaseField(columnName = "name")
    public String name; //节目名字
    @DatabaseField(columnName = "border")
    public int border; //边框
    @DatabaseField(columnName = "borderColor")
    public int borderColor; //边框颜色
    @DatabaseField(columnName = "X")
    public int X;    //坐标X
    @DatabaseField(columnName = "Y")
    public int Y;    //坐标Y
    @DatabaseField(columnName = "width")
    public int width; //长度
    @DatabaseField(columnName = "heidht")
    public int height; //宽度
    @DatabaseField(columnName = "stTypeFace")
    public int stTypeFace = 1 ;   //字体
    @DatabaseField(columnName = "stBold")
    public boolean stBold = false;   //加粗 默认不加粗
    @DatabaseField(columnName = "stItalic")
    public boolean stItalic = false;    //斜体 默认正常
    @DatabaseField(columnName = "stUnderLine")
    public boolean stUnderLine = false;  //下划线 默认不加下划线
    @DatabaseField(columnName = "stSize")
    public int stSize = 14;      //字体大小
    @DatabaseField(columnName = "stColoer")
    public int stColor;  //字体颜色
    @DatabaseField(columnName = "entertrick")
    public int entertrick = 1;  //进场特技
    @DatabaseField(columnName = "enterspeed")
    public int enterspeed = 11;    //进场速度
    @DatabaseField(columnName = "STCleartrick")
    public int cleartrick = 2;  //清场特技
    @DatabaseField(columnName = "STClearspeed")
    public int clearspeed = 11;  //清场速度
    @DatabaseField(columnName = "stBackground")
    public int stBackground;  //背景色
    @DatabaseField(columnName = "standtime")
    public int standtime = 1;  //停留时间

    public List<String> texts = new ArrayList<String>();  //文本窗分屏，实时分屏，不存入数据库

    public void TextBean(){

    }
    public ProgramBean getProgramBean() {
        return programBean;
    }

    public void setProgramBean(ProgramBean programBean) {
        this.programBean = programBean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSingleTextValue() {
        return singleTextValue;
    }

    public void setSingleTextValue(String singleTextValue) {
        this.singleTextValue = singleTextValue;
    }

    public Paint getPaint() {
        return paint;
    }

    public int getTextType() {
        return textType;
    }

    public void setTextType(int textType) {
        this.textType = textType;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBorder() {
        return border;
    }

    public void setBorder(int border) {
        this.border = border;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeidht(int height) {
        this.height = height;
    }


    public int getStTypeFace() {
        return stTypeFace;
    }

    public void setStTypeFace(int stTypeFace) {
        this.stTypeFace = stTypeFace;
    }

    public boolean isStBold() {
        return stBold;
    }

    public void setStBold(boolean stBold) {
        this.stBold = stBold;
    }

    public boolean isStItalic() {
        return stItalic;
    }

    public void setStItalic(boolean stItalic) {
        this.stItalic = stItalic;
    }

    public boolean isStUnderLine() {
        return stUnderLine;
    }

    public void setStUnderLine(boolean stUnderLine) {
        this.stUnderLine = stUnderLine;
    }

    public int getStSize() {
        return stSize;
    }

    public void setStSize(int stSize) {
        this.stSize = stSize;
    }

    public int getStColor() {
        return stColor;
    }

    public void setStColor(int stColor) {
        this.stColor = stColor;
    }

    public int getEntertrick() {
        return entertrick;
    }

    public void setEntertrick(int entertrick) {
        this.entertrick = entertrick;
    }

    public int getEnterspeed() {
        return enterspeed;
    }

    public void setEnterspeed(int enterspeed) {
        this.enterspeed = enterspeed;
    }

    public int getCleartrick() {
        return cleartrick;
    }

    public void setCleartrick(int cleartrick) {
        this.cleartrick = cleartrick;
    }

    public int getClearspeed() {
        return clearspeed;
    }

    public void setClearspeed(int clearspeed) {
        this.clearspeed = clearspeed;
    }

    public int getStBackground() {
        return stBackground;
    }

    public void setStBackground(int stBackground) {
        this.stBackground = stBackground;
    }

    public int getStandtime() {
        return standtime;
    }

    public void setStandtime(int standtime) {
        this.standtime = standtime;
    }

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public byte[] getTextImageBytes() {
        return textImageBytes;
    }

    public void setTextImageBytes(byte[] textImageBytes) {
        this.textImageBytes = textImageBytes;
    }

    @Override
    public String toString() {
        return "单行文本{" +
                "programBean:" + programBean +
                ", id:" + id +
                ", singleTextValue:'" + singleTextValue + '\'' +
                ", name:'" + name + '\'' +
                ", border:" + border +
                ", borderColor:" + borderColor +
                ", X:" + X +
                ", Y:" + Y +
                ", width:" + width +
                ", heidht:" + height +
                ", stTypeFace:" + stTypeFace +
                ", stBold:" + stBold +
                ", stItalic：" + stItalic +
                ", stUnderLine：" + stUnderLine +
                ", stSize：" + stSize +
                ", stColor：" + stColor +
                ", entertrick：" + entertrick +
                ", enterspeed：" + enterspeed +
                ", cleartrick：" + cleartrick +
                ", clearspeed：" + clearspeed +
                ", stBackground：" + stBackground +
                ", standtime：" + standtime +
                '}';
    }

}

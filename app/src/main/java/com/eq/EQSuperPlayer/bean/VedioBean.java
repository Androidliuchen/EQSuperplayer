package com.eq.EQSuperPlayer.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2017/1/19.
 * 存放视频属性的工具类
 */
@DatabaseTable(tableName = "tb_vedio")
public class VedioBean extends TotalBean{
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(foreign=true,foreignAutoRefresh=true)
    public ProgramBean programBean;
    @DatabaseField(columnName = "vedioName")
    public String vedioName; //视频名字
    @DatabaseField(columnName = "vedioBorder")
    public int vedioBorder; //边框
    @DatabaseField(columnName = "vedioBorderColor")
    public int vedioBorderColor; //边框颜色
    @DatabaseField(columnName = "vedioX")
    public int vedioX;    //坐标X
    @DatabaseField(columnName = "vedioY")
    public int vedioY;    //坐标Y
    @DatabaseField(columnName = "vedioWidth")
    public int vedioWidth; //长度
    @DatabaseField(columnName = "vedioHeidht")
    public int vedioHeidht; //宽度
    @DatabaseField(columnName = "vedioSize")
    public int vedioSize;      //视频大小
    @DatabaseField(columnName = "vediohour")
    public String vediohour;      //视频播放时长


    public VedioBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProgramBean getProgramBean() {
        return programBean;
    }

    public void setProgramBean(ProgramBean programBean) {
        this.programBean = programBean;
    }

    public String getVedioName() {
        return vedioName;
    }

    public void setVedioName(String vedioName) {
        this.vedioName = vedioName;
    }

    public int getVedioBorder() {
        return vedioBorder;
    }

    public void setVedioBorder(int vedioBorder) {
        this.vedioBorder = vedioBorder;
    }

    public int getVedioBorderColor() {
        return vedioBorderColor;
    }

    public void setVedioBorderColor(int vedioBorderColor) {
        this.vedioBorderColor = vedioBorderColor;
    }

    public int getVedioX() {
        return vedioX;
    }

    public void setVedioX(int vedioX) {
        this.vedioX = vedioX;
    }

    public int getVedioY() {
        return vedioY;
    }

    public void setVedioY(int vedioY) {
        this.vedioY = vedioY;
    }

    public int getVedioWidth() {
        return vedioWidth;
    }

    public void setVedioWidth(int vedioWidth) {
        this.vedioWidth = vedioWidth;
    }

    public int getVedioHeidht() {
        return vedioHeidht;
    }

    public void setVedioHeidht(int vedioHeidht) {
        this.vedioHeidht = vedioHeidht;
    }

    public int getVedioSize() {
        return vedioSize;
    }

    public void setVedioSize(int vedioSize) {
        this.vedioSize = vedioSize;
    }

    public String getVediohour() {
        return vediohour;
    }

    public void setVediohour(String vediohour) {
        this.vediohour = vediohour;
    }

    @Override
    public String toString() {
        return "VedioBean{" +
                "id=" + id +
                ", programBean=" + programBean +
                ", vedioName='" + vedioName + '\'' +
                ", vedioBorder=" + vedioBorder +
                ", vedioBorderColor=" + vedioBorderColor +
                ", vedioX=" + vedioX +
                ", vedioY=" + vedioY +
                ", vedioWidth=" + vedioWidth +
                ", vedioHeidht=" + vedioHeidht +
                ", vedioSize=" + vedioSize +
                ", vediohour=" + vediohour +
                '}';
    }
}

package com.eq.EQSuperPlayer.bean;


import android.graphics.Paint;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;


/**
 * Created by Administrator on 2016/12/13.
 *
 *  区域基类
 */
@XStreamAlias("Areabean")
@DatabaseTable(tableName = "tb_area")
public class Areabean extends TableBean{
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(columnName = "name")
    public String name;
    @DatabaseField(columnName = "num")
    public String num;
    @DatabaseField(columnName = "windowWidth")
    public int windowWidth;//窗口宽度
    @DatabaseField(columnName = "windowHeight")
    public int windowHeight; //窗口高度
    @DatabaseField(columnName = "area_X")
    public int area_X;//区域在窗体中的坐标x
    @DatabaseField(columnName = "area_Y")
    public int area_Y; //坐标
    @DatabaseField(columnName = "area_position")
    private int area_position;  //在区域数组中所处的位置
    private int index;  //区域转data以后在区域数据集合里，所在的位置的起点坐标
    @ForeignCollectionField(eager = true)    //必须是ForeignCollection<>
    public ForeignCollection<ProgramBean> programBeen;
    @DatabaseField(columnName = "equitType")
    public String equitType;
    @DatabaseField(columnName = "equitIp")
    public String equitTp;
    public List<ProgramBean> programBeens;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getArea_X() {
        return area_X;
    }

    public void setArea_X(int area_X) {
        this.area_X = area_X;
    }

    public int getArea_Y() {
        return area_Y;
    }

    public void setArea_Y(int area_Y) {
        this.area_Y = area_Y;
    }

    public ForeignCollection<ProgramBean> getProgramBeen() {
        return programBeen;
    }

    public void setProgramBeen(ForeignCollection<ProgramBean> programBeen) {
        this.programBeen = programBeen;
    }

    public List<ProgramBean> getProgramBeens() {
        return programBeens;
    }

    public void setProgramBeens(List<ProgramBean> programBeens) {
        this.programBeens = programBeens;
    }

    public int getArea_position() {
        return area_position;
    }

    public void setArea_position(int area_position) {
        this.area_position = area_position;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    public String getEquitType() {
        return equitType;
    }

    public void setEquitType(String equitType) {
        this.equitType = equitType;
    }

    public String getEquitTp() {
        return equitTp;
    }

    public void setEquitTp(String equitTp) {
        this.equitTp = equitTp;
    }

    @Override
    public String toString() {
        return "Areabean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", num='" + num + '\'' +
                ", windowWidth=" + windowWidth +
                ", windowHeight=" + windowHeight +
                ", area_X=" + area_X +
                ", area_Y=" + area_Y +
                ", area_position=" + area_position +
                ", index=" + index +
                ", programBeen=" + programBeen +
                ", equitType='" + equitType + '\'' +
                ", equitTp='" + equitTp + '\'' +
                ", programBeens=" + programBeens +
                '}';
    }
}

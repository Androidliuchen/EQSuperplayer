package com.eq.EQSuperPlayer.bean;

import com.j256.ormlite.field.DatabaseField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/27.
 */
public class TotalBean implements Comparable<TotalBean>{
    private int type;
    @DatabaseField(columnName = "total_position")
    private int total_position;  //在区域数组中所处的位置
    private int index;  //区域转data以后在区域数据集合里，所在的位置的起点坐标
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTotal_position() {
        return total_position;
    }

    public void setTotal_position(int total_position) {
        this.total_position = total_position;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int compareTo(TotalBean total) {
        return this.getTotal_position() - total.getTotal_position();
    }

}

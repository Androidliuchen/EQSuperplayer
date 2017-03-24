package com.eq.EQSuperPlayer.bean;


import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Administrator on 2017/1/18.
 */
public class TableBean implements Comparable<TableBean>{
    private int type;
    @DatabaseField(columnName = "atable_position")
    private int table_position;  //在区域数组中所处的位置
    private int index;  //区域转data以后在区域数据集合里，所在的位置的起点坐标

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTable_position() {
        return table_position;
    }

    public void setTable_position(int table_position) {
        this.table_position = table_position;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int compareTo(TableBean table) {
        return this.getTable_position() - table.getTable_position();
    }
}

package com.eq.EQSuperPlayer.bean;

import android.graphics.Bitmap;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/10.
 */
@DatabaseTable(tableName = "tb_iamge")
public class ImageBean extends TotalBean {
    @DatabaseField(generatedId = true)
    public int id;
//    @DatabaseField(dataType = DataType.BYTE_ARRAY)
//    public byte[] imageBytes;
    @DatabaseField(canBeNull = true, foreign = true, columnName = "program_id")
    public ProgramBean programBean;
    @DatabaseField(columnName = "iamgeName")
    public String iamgeName; //节目名字
    @DatabaseField(columnName = "iamgeBorder")
    public int iamgeBorder; //边框
    @DatabaseField(columnName = "iamgeBorderColor")
    public int iamgeBorderColor; //边框颜色
    @DatabaseField(columnName = "iamgeX")
    public int iamgeX;    //坐标X
    @DatabaseField(columnName = "iamgeY")
    public int iamgeY;    //坐标Y
    @DatabaseField(columnName = "iamgeWidth")
    public int iamgeWidth; //长度
    @DatabaseField(columnName = "iamgeHeidht")
    public int iamgeHeidht; //宽度
    @DatabaseField(columnName = "iamgeEntertrick")
    public int iamgeEntertrick;  //进场特技
    @DatabaseField(columnName = "iamgeEnterspeed")
    public int iamgeEnterspeed;    //进场速度
    @DatabaseField(columnName = "iamgeCleartrick")
    public int iamgeCleartrick;  //清场特技
    @DatabaseField(columnName = "iamgeClearspeed")
    public int iamgeClearspeed;  //清场速度
    @DatabaseField(columnName = "iamgeandtime")
    public int iamgeandtime = 5;  //停留时间
    public List<String> iamgeId = new ArrayList<String>();
    @DatabaseField(columnName = "path")
    public String path; //图片路径
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

    public String getIamgeName() {
        return iamgeName;
    }

    public void setIamgeName(String iamgeName) {
        this.iamgeName = iamgeName;
    }

    public int getIamgeBorder() {
        return iamgeBorder;
    }

    public void setIamgeBorder(int iamgeBorder) {
        this.iamgeBorder = iamgeBorder;
    }

    public int getIamgeBorderColor() {
        return iamgeBorderColor;
    }

    public void setIamgeBorderColor(int iamgeBorderColor) {
        this.iamgeBorderColor = iamgeBorderColor;
    }

    public int getIamgeX() {
        return iamgeX;
    }

    public void setIamgeX(int iamgeX) {
        this.iamgeX = iamgeX;
    }

    public int getIamgeY() {
        return iamgeY;
    }

    public void setIamgeY(int iamgeY) {
        this.iamgeY = iamgeY;
    }

    public int getIamgeWidth() {
        return iamgeWidth;
    }

    public void setIamgeWidth(int iamgeWidth) {
        this.iamgeWidth = iamgeWidth;
    }

    public int getIamgeHeidht() {
        return iamgeHeidht;
    }

    public void setIamgeHeidht(int iamgeHeidht) {
        this.iamgeHeidht = iamgeHeidht;
    }

    public int getIamgeEntertrick() {
        return iamgeEntertrick;
    }

    public void setIamgeEntertrick(int iamgeEntertrick) {
        this.iamgeEntertrick = iamgeEntertrick;
    }

    public int getIamgeEnterspeed() {
        return iamgeEnterspeed;
    }

    public void setIamgeEnterspeed(int iamgeEnterspeed) {
        this.iamgeEnterspeed = iamgeEnterspeed;
    }

    public int getIamgeCleartrick() {
        return iamgeCleartrick;
    }

    public void setIamgeCleartrick(int iamgeCleartrick) {
        this.iamgeCleartrick = iamgeCleartrick;
    }

    public int getIamgeClearspeed() {
        return iamgeClearspeed;
    }

    public void setIamgeClearspeed(int iamgeClearspeed) {
        this.iamgeClearspeed = iamgeClearspeed;
    }

    public int getIamgeandtime() {
        return iamgeandtime;
    }

    public void setIamgeandtime(int iamgeandtime) {
        this.iamgeandtime = iamgeandtime;
    }

    public List<String> getIamgeId() {
        return iamgeId;
    }

    public void setIamgeId(List<String> iamgeId) {
        this.iamgeId = iamgeId;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ImageBean{" +
                "id=" + id +
                ", programBean=" + programBean +
                ", iamgeName='" + iamgeName + '\'' +
                ", iamgeBorder=" + iamgeBorder +
                ", iamgeBorderColor=" + iamgeBorderColor +
                ", iamgeX=" + iamgeX +
                ", iamgeY=" + iamgeY +
                ", iamgeWidth=" + iamgeWidth +
                ", iamgeHeidht=" + iamgeHeidht +
                ", iamgeEntertrick=" + iamgeEntertrick +
                ", iamgeEnterspeed=" + iamgeEnterspeed +
                ", iamgeCleartrick=" + iamgeCleartrick +
                ", iamgeClearspeed=" + iamgeClearspeed +
                ", iamgeandtime=" + iamgeandtime +
                ", iamgeId=" + iamgeId +
                '}';
    }

}

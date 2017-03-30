package com.eq.EQSuperPlayer.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2017/3/29.
 */
@DatabaseTable(tableName = "tb_path")
public class ImagePath extends ImageBean{
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(columnName = "path")
    public String path; //图片路径
    @DatabaseField(canBeNull = true, foreign = true, columnName = "image_id")
    public ImageBean imageBean;
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ImageBean getImageBean() {
        return imageBean;
    }

    public void setImageBean(ImageBean imageBean) {
        this.imageBean = imageBean;
    }

    @Override
    public String toString() {
        return "ImagePath{" +
                "id=" + id +
                ", path='" +path + '\'' +
                ", imageBean=" + imageBean +
                '}';
    }
}

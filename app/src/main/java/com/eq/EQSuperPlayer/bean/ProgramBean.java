package com.eq.EQSuperPlayer.bean;


import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;


/**
 * Created by Administrator on 2016/12/12.
 */
@XStreamAlias("ProgramBean")
@DatabaseTable(tableName = "tb_program")
public class ProgramBean extends TableBean{
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(columnName = "name")
    public String name;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    public Areabean areabean;
    @ForeignCollectionField(eager = true)    //必须是ForeignCollection<>
    public ForeignCollection<TextBean> textBeen;
    @ForeignCollectionField(eager = true)    //必须是ForeignCollection<>
    public ForeignCollection<ImageBean> imageBeen;
    @ForeignCollectionField(eager = true)
    public ForeignCollection<VedioBean> vedioBeen;
    @ForeignCollectionField(eager = true)
    public ForeignCollection<TimeBean> timeBeen;
    private List<TextBean> textBeens;
    public ProgramBean() {
    }

    public ProgramBean(int id, String name, Areabean areabean) {
        this.id = id;
        this.name = name;
        this.areabean = areabean;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Areabean getAreabean() {
        return areabean;
    }

    public void setAreabean(Areabean areabean) {
        this.areabean = areabean;
    }

    public ForeignCollection<TextBean> getTextBeen() {
        return textBeen;
    }
    public ForeignCollection<ImageBean> getImageBeen() {
        return imageBeen;
    }

    public void setImageBeen(ForeignCollection<ImageBean> imageBeen) {
        this.imageBeen = imageBeen;
    }

    public void setTextBeen(ForeignCollection<TextBean> textBeen) {
        this.textBeen = textBeen;
    }

    public ForeignCollection<VedioBean> getVedioBeen() {
        return vedioBeen;
    }

    public void setVedioBeen(ForeignCollection<VedioBean> vedioBeen) {
        this.vedioBeen = vedioBeen;
    }

    public List<TextBean> getTextBeens() {
        return textBeens;
    }

    public void setTextBeens(List<TextBean> textBeens) {
        this.textBeens = textBeens;
    }

    public ForeignCollection<TimeBean> getTimeBeen() {
        return timeBeen;
    }

    public void setTimeBeen(ForeignCollection<TimeBean> timeBeen) {
        this.timeBeen = timeBeen;
    }

    @Override
    public String toString() {
        return "ProgramBean{" +
                "areabean=" + areabean +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}

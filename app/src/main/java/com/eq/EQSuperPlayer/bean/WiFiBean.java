package com.eq.EQSuperPlayer.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2017/4/6.
 */
@DatabaseTable(tableName = "tb_wifidata")
public class WiFiBean {
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(columnName = "screen")
    public String screen; //屏幕名称
    @DatabaseField(columnName = "wifiIp")
    public String wifiIp; //wifiIP
    @DatabaseField(columnName = "ipProt")
    public String ipProt; //ip端口号
    @DatabaseField(columnName = "brightness")
    public String brightness; //亮度
    @DatabaseField(columnName = "screenWidth")
    public String screenWidth; //屏幕宽度
    @DatabaseField(columnName = "screenHeight")
    public String screenHeight; //屏幕高度
    @DatabaseField(columnName = "proIndex")
    public String proIndex; //节目套数
    @DatabaseField(columnName = "MAC")
    public String MAC; //MAC
    @DatabaseField(columnName = "mdloe")
    public String mdloe; //型号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getWifiIp() {
        return wifiIp;
    }

    public void setWifiIp(String wifiIp) {
        this.wifiIp = wifiIp;
    }

    public String getIpProt() {
        return ipProt;
    }

    public void setIpProt(String ipProt) {
        this.ipProt = ipProt;
    }

    public String getBrightness() {
        return brightness;
    }

    public void setBrightness(String brightness) {
        this.brightness = brightness;
    }

    public String getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(String screenWidth) {
        this.screenWidth = screenWidth;
    }

    public String getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(String screenHeight) {
        this.screenHeight = screenHeight;
    }

    public String getProIndex() {
        return proIndex;
    }

    public void setProIndex(String proIndex) {
        this.proIndex = proIndex;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getMdloe() {
        return mdloe;
    }

    public void setMdloe(String mdloe) {
        this.mdloe = mdloe;
    }
}

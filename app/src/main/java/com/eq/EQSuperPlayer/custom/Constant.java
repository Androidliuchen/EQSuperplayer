package com.eq.EQSuperPlayer.custom;

/**
 * Created by Administrator on 2016/12/23.
 */
public class Constant {
    public static final String PROGRAM_ID = "program_id";  //传递节目id 的键
    public static final int UDP_WAIT = 3000;  //UDP接收数据包等待时长
    public static final int DATA_SPLIT = 1000;  //数据分包大小
    //区域种类 参数 枚举值
    public static final int AREA_TYPE_PROGRAM = 0;  //节目参数本身
    public static final int AREA_TYPE_TEXT = 1;  //文本
    public static final int AREA_TYPE_IMAGE = 2;    // 图文
    public static final int AREA_TYPE_TIME = 3;    // 时间
    public static final int AREA_TYPE_VIDEO = 4;    // 图文
    //字体大小校正 ，字体默认+3 ，勉强跟PC端大小近似
    public static final int FONT_SIZE_CORRECTION = 3;
    //字体路径 字体库移除的同时一起移除
    public static final String SEND_STR = "send";  //Intet  键值对 ，用于判断是否立即执行节目发送操作
    public static final int SEND = 1;
}


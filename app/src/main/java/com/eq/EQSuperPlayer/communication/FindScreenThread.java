package com.eq.EQSuperPlayer.communication;

import android.content.Context;
import android.util.Log;

import com.eq.EQSuperPlayer.bean.Areabean;
import com.eq.EQSuperPlayer.dao.AreabeanDao;
import com.eq.EQSuperPlayer.utils.WindowSizeManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.List;

/**
 * Created by Administrator on 2017/1/21.
 */
public class FindScreenThread implements Runnable {
    private static final String TAG = "FindScreenThread";
    public int FIND_PORT = 5051;  // 端口
    public String IpAddress = "255.255.255.255";   // 主机地址
    private MulticastSocket dataSocket = null;
    private DatagramPacket dataPacket = null;
    private byte[] bytes = SendPacket.findScreen();
    private int dataLength = 0;
    private Context context;
    private InterfaceConnect interfaceConnect = null;
    private boolean flag = true;
    public static String scrennIP;
    private int screenNum = 0;

    public FindScreenThread(InterfaceConnect interfaceConnect, Context context) {
        super();
        this.context = context;
        this.interfaceConnect = interfaceConnect;
    }


    @Override
    public void run() {
        try {

            dataSocket = new MulticastSocket(FIND_PORT);
            InetAddress server = InetAddress.getByName(IpAddress);
            dataSocket.setLoopbackMode(true);
            dataLength = SendPacket.bytes2HexString(bytes, bytes.length).toString() == null ? 0 : bytes.length;
            DatagramPacket theOutput = new DatagramPacket(bytes, dataLength, server, FIND_PORT);
            dataSocket.send(theOutput);
            System.out.println("address : +发送了" + IpAddress + "--" + SendPacket.byte2hex(theOutput.getData()));
            byte[] buffer = new byte[1024];
            DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
            while (flag) {
                try {
                    System.out.println("address : +要开始接收");
                    dataSocket.setSoTimeout(5000);
                    dataSocket.receive(inPacket);
                    byte[] data = inPacket.getData();
                    String s1 = SendPacket.byte2hex(data);
                    Log.d("S1.......", s1 + "");
                    if (!s1.startsWith("f75b0a00017000005bf7")) {
                        screenNum++;
                        findScreenInfo(s1, inPacket.getAddress().toString().substring(1));
                    }

                } catch (IOException e) {
                    System.out.println("address : +接收异常");
                    if (interfaceConnect != null) {
                        if (screenNum > 0) {
                            interfaceConnect.success(buffer);
                        } else {
                            interfaceConnect.failure(1);
                        }
                    }
                    screenNum = 0;
                    flag = false;
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            Log.e(TAG, "接收数据包IO异常...");
            if (interfaceConnect != null) {
                interfaceConnect.failure(0);
            }
            e.printStackTrace();
        } finally {
            if (dataSocket != null)
                dataSocket.close();
        }


    }

    public void findScreenInfo(String str, String ip) {
        List<Areabean> areabeans = new AreabeanDao(context).getListAll();
        Areabean areabean;
        if (areabeans.size() <= 0) {
            areabean = new Areabean();
        } else {
            areabean = areabeans.get(0);
        }
        //获取端口起始11
        StringBuffer strPort = new StringBuffer();
        for (int i = 1; i <= 4; i++) {
            strPort.append(str.substring(30 - i * 2, 30 - (i - 1) * 2));

        }
        int port = Integer.parseInt(strPort.toString(), 16);
        areabean.setEquitProt(port + "");
        areabean.setEquitTp(ip);
        scrennIP = ip;

        //获取宽度
        StringBuffer strWidth = new StringBuffer();
        for (int i = 1; i <= 2; i++) {
            strWidth.append(str.substring(12 - i * 2, 12 - (i - 1) * 2));

        }
        int width = Integer.parseInt(strWidth.toString(), 16);
        areabean.setWindowWidth(width);
        //获取高度
        StringBuffer strHeight = new StringBuffer();
        for (int i = 1; i <= 2; i++) {
            strHeight.append(str.substring(16 - i * 2, 16 - (i - 1) * 2));
        }
        int height = Integer.parseInt(strHeight.toString(), 16);
        areabean.setWindowHeight(height);
        //获取型号
        String model = str.substring(17, 18) + str.substring(16, 17);
        areabean.setEquitType(model);
        //获取亮度
        int brightness = Integer.parseInt(str.substring(186 * 2, 187 * 2), 16);
        //节目套数
        int num = Integer.parseInt(str.substring(500, 502), 16);
        //屏幕名称
        String ScreenName = SendPacket.hexString2String(str.substring(36 * 2, (36 + 7) * 2));
        areabean.setName(ScreenName);
        if (areabeans.size() <= 0) {
            new AreabeanDao(context).add(areabean);
        } else {
            new AreabeanDao(context).update(areabean);
        }
        WindowSizeManager.setSharedPreference(context, width, height);
        System.out.println("address :" + ip + " port:" + port + " width:" + width + " height:" + height + " model:" + model + " brightness:" + brightness + " num:" + num);
        System.out.println("address ScreenName:" + ScreenName);
    }
}


package com.eq.EQSuperPlayer.communication;

import android.content.Context;
import android.util.Log;

import com.eq.EQSuperPlayer.activity.MainActivity;
import com.eq.EQSuperPlayer.bean.Areabean;
import com.eq.EQSuperPlayer.custom.Constant;
import com.eq.EQSuperPlayer.dao.AreabeanDao;
import com.eq.EQSuperPlayer.fargament.ProgramFragment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/21.
 */
public class ConnectControlCard implements Runnable {
    private static final String TAG = "ConnectControlCard";
    public static int PORT = 5050;  // 端口
    public static String HOSTAddress = null;    // 主机地址
    //    public static String HOSTAddress = "192.168.2.206";    // 主机地址
    private DatagramPacket dataPacket = null;
    private DatagramSocket sendSocket = null;
    private byte[] sendByte = null;
    private String testStr = "";
    private InterfaceConnect interfaceConnect = null;

    public ConnectControlCard(Context context, byte[] sendByte, InterfaceConnect interfaceConnect) {
        super();
        this.sendByte = sendByte;
        this.interfaceConnect = interfaceConnect;
        List<Areabean> areabeans = new AreabeanDao(context).getListAll();
        Areabean areabean = areabeans.get(0);
        HOSTAddress = areabean.getEquitTp();
    }

    public ConnectControlCard(byte[] sendByte) {

        super();
        this.sendByte = sendByte;
    }

    @Override
    public void run() {
        InetAddress local = null;
        try {
            local = InetAddress.getByName(HOSTAddress);
//            Log.e(TAG, "正在检测连接地址...");
        } catch (UnknownHostException e) {
//            Log.e(TAG, "未找到连接地址..." + e.toString());
            if (interfaceConnect != null) {
                interfaceConnect.failure(0);
            }
            e.printStackTrace();
        }
        try {
            if (sendSocket != null){
                sendSocket.close();
            }
            sendSocket = new DatagramSocket();
            try {
//                for (int i = 0; i < sendByte.size(); i++) {
//                    byte[] arrData = sendByte.get(i);
                    dataPacket = new DatagramPacket(sendByte, sendByte.length, local, PORT);
                    sendSocket.send(dataPacket);
//                }
                interfaceConnect.dataSuccess("数据发送完！");
                byte[] buf = new byte[50];
                dataPacket = new DatagramPacket(buf, buf.length);
                try {
                    sendSocket.setSoTimeout(Constant.UDP_WAIT);
                    sendSocket.receive(dataPacket); //	 获得输入流
                    Thread.sleep(100);
                    testStr = new String(buf, "GBK").trim();
                    if (interfaceConnect != null) {
                        interfaceConnect.success(buf); //传递返回值
                    }
                    if (sendSocket != null) {
                        sendSocket.close();
                        sendSocket = null;
                    }
                } catch (UnsupportedEncodingException e) {
                    if (interfaceConnect != null) {
                        interfaceConnect.failure(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    e.printStackTrace();
                    if (interfaceConnect != null) {
                        interfaceConnect.failure(0);
                    }
                }
            } catch (IOException e) {
                if (interfaceConnect != null) {
                    interfaceConnect.failure(0);
                }
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}


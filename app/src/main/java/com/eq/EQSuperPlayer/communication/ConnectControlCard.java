package com.eq.EQSuperPlayer.communication;

import android.util.Log;

import com.eq.EQSuperPlayer.bean.Areabean;
import com.eq.EQSuperPlayer.custom.Constant;
import com.eq.EQSuperPlayer.dao.AreabeanDao;
import com.eq.EQSuperPlayer.fargament.ProgramFragment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
//    public static String HOSTAddress = ProgramFragment.ip;    // 主机地址
    public static String HOSTAddress ="192.168.43.1" ;    // 主机地址
    private DatagramSocket dataSocket = null;
    private DatagramPacket dataPacket = null;
    private int dataLength = 0;    //在当前类，竟然还要传个空值过来
    //    private byte[] sendByte = null;
    private List<byte[]> sendByte = new ArrayList<byte[]>();
    //    private int dataSize = 0;
    private String testStr = "";
    private boolean stop = true;
    private InterfaceConnect interfaceConnect = null;

    public ConnectControlCard(List<byte[]> sendByte, InterfaceConnect interfaceConnect) {
        super();
        this.sendByte = sendByte;
        this.interfaceConnect = interfaceConnect;
    }

    public ConnectControlCard(List<byte[]> sendByte) {
        super();
        this.sendByte = sendByte;
    }

    @Override
    public void run() {
        Log.d("......", "子线程。。。。。。" + 1);
        Log.d("......", "HOSTAddress。。。。。。" + HOSTAddress);
        InetAddress local = null;
        try {
            local = InetAddress.getByName(HOSTAddress);
            Log.e(TAG, "正在检测连接地址...");
        } catch (UnknownHostException e) {
            Log.e(TAG, "未找到连接地址..." + e.toString());
            if (interfaceConnect != null) {
                interfaceConnect.failure(0);
            }
            e.printStackTrace();
        }
        try {
            Log.e(TAG, "正在连接服务器...");
            dataSocket = new DatagramSocket();
            Log.e(TAG, "正在准备数据...");
            try {
                for (int i = 0; i < sendByte.size(); i++) {
                    byte[] arrData = sendByte.get(i);
                    dataLength = SendPacket.bytes2HexString(arrData, arrData.length).toString() == null ? 0 : arrData.length;
                    dataPacket = new DatagramPacket(arrData, dataLength, local, PORT);
                    dataSocket.send(dataPacket);

                }
                Log.d("......", "子线程。。。。。。" + 1.5);
                interfaceConnect.dataSuccess("数据发送完！");
                Log.e(TAG, "发送成功...");
                stop = false;
                Log.e(TAG, "1");
                byte[] buf = new byte[50];
                Log.e(TAG, "2");
                dataPacket = new DatagramPacket(buf, buf.length);
                Log.e(TAG, "3");
                //  while (!stop) {
                try {
                    Log.e(TAG, "4");
                    dataSocket.setSoTimeout(Constant.UDP_WAIT);
                    dataSocket.receive(dataPacket); //	 获得输入流
                    Log.e(TAG, "5");
                    testStr = new String(buf, "GBK").trim();
                    Log.e(TAG, "6");
                    System.out.println("______________" + testStr.length());
                    Log.e(TAG, "7");
                    System.out.println("有数据.............." + SendPacket.bytes2HexString(buf, buf.length));
                    if (interfaceConnect != null) {
                        Log.e(TAG, "8");
                        String s1 = SendPacket.bytes2HexString(buf, buf.length);
                        Log.e(TAG, "9." + s1);
                        interfaceConnect.success(buf); //传递返回值
                        Log.d("......", "子线程。。。。。。" + 2);
                    }
                } catch (UnsupportedEncodingException e) {
                    Log.e(TAG, "接收数据包异常...");
                    if (interfaceConnect != null) {
                        interfaceConnect.failure(0);
                    }
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e(TAG, "接收数据包IO异常...");
                    if (interfaceConnect != null) {
                        interfaceConnect.failure(0);
                    }
                    e.printStackTrace();

                } catch (Exception e) {
                    e.printStackTrace();
                    if (interfaceConnect != null) {
                        interfaceConnect.failure(0);
                    }
                }
                // }
            } catch (IOException e) {
                Log.e(TAG, "发送数据包异常...");
                if (interfaceConnect != null) {
                    interfaceConnect.failure(0);
                }
                e.printStackTrace();
            }
        } catch (SocketException e) {
            Log.e(TAG, "连接服务器失败...");
            if (interfaceConnect != null) {
                interfaceConnect.failure(0);
            }
            e.printStackTrace();
        }

    }
}


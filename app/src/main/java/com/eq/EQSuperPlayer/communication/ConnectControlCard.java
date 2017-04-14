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
    private int dataLength = 0;    //在当前类，竟然还要传个空值过来
    private List<byte[]> sendByte = new ArrayList<byte[]>();
    private String testStr = "";
    //    private UdpMessageTool mUdpMessageTool;
    private InterfaceConnect interfaceConnect = null;

    public ConnectControlCard(Context context, List<byte[]> sendByte, InterfaceConnect interfaceConnect) {
        super();
        this.sendByte = sendByte;
        this.interfaceConnect = interfaceConnect;
        List<Areabean> areabeans = new AreabeanDao(context).getListAll();
        Areabean areabean = areabeans.get(0);
        HOSTAddress = areabean.getEquitTp();
    }

    public ConnectControlCard(List<byte[]> sendByte) {

        super();
        this.sendByte = sendByte;
    }

    @Override
    public void run() {
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
            sendSocket = new DatagramSocket();
            Log.d("......", "PORT。。。。。。" + PORT);
            Log.e(TAG, "正在准备数据...");
            try {
                for (int i = 0; i < sendByte.size(); i++) {
                    byte[] arrData = sendByte.get(i);
//                    dataLength = SendPacket.bytes2HexString(arrData, arrData.length).toString() == null ? 0 : arrData.length;
                    dataPacket = new DatagramPacket(arrData, arrData.length, local, PORT);
                    sendSocket.send(dataPacket);
                }
                interfaceConnect.dataSuccess("数据发送完！");
                Log.e(TAG, "发送成功...");
                byte[] buf = new byte[50];
                dataPacket = new DatagramPacket(buf, buf.length);
                try {
                    sendSocket.setSoTimeout(Constant.UDP_WAIT);
                    sendSocket.receive(dataPacket); //	 获得输入流
                    Thread.sleep(100);
                    testStr = new String(buf, "GBK").trim();
                    System.out.println("______________" + testStr.length());
                    System.out.println("有数据.............." + SendPacket.bytes2HexString(buf, buf.length));
                    if (interfaceConnect != null) {
                        String s1 = SendPacket.bytes2HexString(buf, buf.length);
                        interfaceConnect.success(buf); //传递返回值
                        PORT = dataPacket.getPort();
                        String A = String.valueOf(dataPacket.getAddress());
                    }
                    if (sendSocket != null) {
                        sendSocket.close();
                        sendSocket = null;
                    }
                } catch (UnsupportedEncodingException e) {
                    Log.e(TAG, "接收数据包异常...");
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
                Log.e(TAG, "发送数据包异常...");
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


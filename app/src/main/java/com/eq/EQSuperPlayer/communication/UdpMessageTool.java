package com.eq.EQSuperPlayer.communication;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */
public class UdpMessageTool {
    // DatagramSocket代表UDP协议的Socket,作用就是接收和发送数据报
    private DatagramSocket mDatagramSocket = null;
    public static UdpMessageTool instance;
    private int dataLength = 0;    //在当前类，竟然还要传个空值过来
    // 创建UdpMessageTool对象

    public UdpMessageTool() throws Exception {
        // 初始化DatagramSocket，也可以传入指定端口号
        mDatagramSocket = new DatagramSocket();
    }

    // 操作类获取单例实例
    public static UdpMessageTool getInstance() throws Exception {
        if (instance == null) {
            instance = new UdpMessageTool();
        }
        return instance;
    }

    // 设置超时时间
    public final void setTimeOut(final int timeout) throws Exception {
        mDatagramSocket.setSoTimeout(timeout);
    }

    // 获取DatagramSocket对象
    public final DatagramSocket getDatagramSocket() {
        return mDatagramSocket;
    }

    // 向指定的服务端发送数据信息. 参数介绍： host 服务器主机地址 port 服务端端口 bytes 发送的数据信息
    public final synchronized void send(final InetAddress local, final int port,
                                        final List<byte[]> bytes) throws IOException {
        for (int i = 0; i < bytes.size(); i++) {
            byte[] arrData = bytes.get(i);
            dataLength = SendPacket.bytes2HexString(arrData, arrData.length).toString() == null ? 0 : arrData.length;
            DatagramPacket dp = new DatagramPacket(arrData, dataLength,
                    local, port);
            mDatagramSocket.send(dp);
        }

    }

    // 接收从指定的服务端发回的数据. hostName 服务端主机 hostPort 服务端端口 return 服务端发回的数据.
    public final synchronized byte[] receive(final InetAddress local,
                                             final int hostPort) {
        byte[] bytes = new byte[50];
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
        try {
            mDatagramSocket.receive(dp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String result = new String(dp.getData(), 0, dp.getLength());
        String AA = SendPacket.byte2hex(bytes);
        Log.d("返回的数据.........",AA + "");
        return bytes;
    }

    // 关闭udp连接
    public final void close() {
        if (mDatagramSocket != null) {
            try {
                mDatagramSocket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            mDatagramSocket = null;
        }

    }
}

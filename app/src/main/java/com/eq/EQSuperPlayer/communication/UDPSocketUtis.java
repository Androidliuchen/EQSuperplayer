package com.eq.EQSuperPlayer.communication;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Log;

import com.eq.EQSuperPlayer.bean.Areabean;
import com.eq.EQSuperPlayer.custom.Constant;
import com.eq.EQSuperPlayer.dao.AreabeanDao;
import com.eq.EQSuperPlayer.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */
public class UDPSocketUtis implements Runnable {

    private List<String> allPath = new ArrayList<String>(); //存放所有要发的资源路径的数组
    private ArrayList<String> sourceName; //存放所有资源（除了xml）名的数组
    private DatagramPacket dataPacket = null;
    private DatagramSocket sendSocket = null;
    private InetAddress local;
    private String PROGRAME_ROOT = Environment
            .getExternalStorageDirectory()
            .getAbsolutePath() + "/EQPrograme/";
    public int PORT = 5050;  // 端口
    public String HOSTAddress = null;    // 主机地址
    private Context context;
    private ProgressDialog proDialog;
    private byte[] receiveByte = new byte[1024];
    private boolean isSuccess = false; //文件是否正常发送和接收成功
    private InterfaceConnect interfaceConnect;
    private FileInputStream inputStream = null;
    private RandomAccessFile ism;
    private DhcpInfo dhcpInfo;


    public UDPSocketUtis(Context context, ProgressDialog proDialog, InterfaceConnect interfaceConnect) {
        this.context = context;
        this.proDialog = proDialog;
        this.interfaceConnect = interfaceConnect;
    }

    @Override
    public void run() {
        getUdpSocket();
        if (Utils.phoneOrWifi(context,HOSTAddress)){
            isSuccess = true;
        }else {
            isSuccess = false;
        }
        if (isSuccess) {
            //先开始发送 开始发送节目 指令
            for (int i = 0; i < 3; i++) {
                try {
                    dataPacket = new DatagramPacket(SendPacket.pkgHeadInterface(), SendPacket.pkgHeadInterface().length, local, PORT);
                    sendSocket.send(dataPacket);
                    dataPacket = new DatagramPacket(receiveByte, receiveByte.length);
                    sendSocket.setSoTimeout(Constant.UDP_WAIT);
                    sendSocket.receive(dataPacket);
                    isSuccess = true;
                    break; //如果发送成功结束循环
                } catch (IOException e) {
                    e.printStackTrace();

                    isSuccess = false;
                    Log.d("---", "---开始发送指令异常");
                }
            }
        }else {
            interfaceConnect.failure(1);
        }
        //发送节目指令结束

        //开始发送具体的节目资源
        if (isSuccess) {  //开始发送节目指令接到回包
            //第一步 ：开始发送xml文件 单包发送
            try {
                inputStream = new FileInputStream(new File(allPath.get(0)));
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = inputStream.read(buf)) != -1) {
                    String str = new String(buf, 0, len);
                    proDialog.incrementProgressBy(len);
                    byte[] proBytes = SendPacket.prepareSendDataPkg(str.getBytes(), 0);
                    if (!sentOnebyte(proBytes)) {
                        break;
                    }
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (isSuccess) {
                    //发送 xml文件发送完成指令
                    byte[] pkgHeadend = SendPacket.pkgHeadend();
                    sentOnebyte(pkgHeadend);
                }
                // 第二步 ：如果xml文件发送完成 开始发送单个文件
                if (isSuccess) {
                    //循环发送所有的文件资源
                    for (int i = 0; i < sourceName.size(); i++) {
                        //2.1 ：先发送文件名称
                        String str = sourceName.get(i);
                        byte[] controlCard2 = SendPacket.fileNameData(str.getBytes().length, str.getBytes());
                        if (!sentOnebyte(controlCard2)) {
                            break;
                        }
                        //2.2：开始多包发送文件
                        ism = new RandomAccessFile(new File(allPath.get(i + 1)), "r");
                        if (!sendManyPacket(ism)) {     //开始对当前文件资源进行多包发送
                            break;
                        }
                        if (ism != null) {
                            ism.close();
                        }
                    }
                    //所有的文件发送完成之后发送节目发送完成
                    if (isSuccess) {
                        if (sentOnebyte(SendPacket.pkgPingend())) {
                            interfaceConnect.success("1".getBytes());
                        } else {
                            interfaceConnect.failure(0);
                        }
                    } else {
                        interfaceConnect.failure(0);
                    }
                } else { //三次没有回包，判断发送失败
                    interfaceConnect.failure(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                interfaceConnect.failure(0);
            }
        } else {  //三次没有回包，判断发送失败
            interfaceConnect.failure(0);
        }

    }

    /**
     * 对单个文件进行多包发送的方法
     */
    private boolean sendManyPacket(RandomAccessFile ism) throws IOException {
        long fileSize = ism.length();
        long count = fileSize % 1024 != 0 ? fileSize / 1024 + 1 : fileSize / 1024;//共分为多少个1024包
        long endPacket = fileSize % 1024 != 0 ? fileSize % 1024 : 1024; //最后一包的长度
        long bigCount = count % 50 != 0 ? count / 50 + 1 : count / 50;   //共分为多少个大包
        long endLeng = count % 50 != 0 ? count % 50 : 50;   //最后一个大包有多少个1024包
        long manyStratIndex = 0;
        byte[] manySendStart = null;
        long sendLeng = 0;
        Log.d("-- ", "count: " + count + "  bigCount: " + bigCount + "  endLeng: " + endLeng + "  endPacket " + endPacket);
        for (int i = 0; i < bigCount; i++) {
            //先发送多包开始发送
            //1.多包开始发送的空包
            if (isSuccess) {
                if (i == bigCount - 1) {
                    sendLeng = endLeng;
                    manySendStart = SendPacket.manyPakStart(manyStratIndex, endLeng * 1024, endLeng, 1024);
                } else {
                    sendLeng = 50;
                    manySendStart = SendPacket.manyPakStart(manyStratIndex, 50 * 1024, 50, 1024);
                    manyStratIndex = (i+1) * 50 * 1024;

                }
                if (!sentOnebyte(manySendStart)) {
                    break;
                } else {
                    //2.开始进行多包数据的发送
                    byte[] list = new byte[1024];
                    byte[] manyIangeData;

                    for (int j = 0; j < sendLeng; j++) {
                        if (sendLeng < 50 && j == sendLeng - 1) {
                            list = new byte[1024];
                        }
                        manyIangeData = new byte[list.length + 14];
                        manyIangeData[0] = (byte) 0xF6;
                        manyIangeData[1] = (byte) 0x5A;
                        manyIangeData[2] = (byte) ((list.length + 14 >> 0) & 0xFF);
                        manyIangeData[3] = (byte) ((list.length + 14 >> 8) & 0xFF);
                        manyIangeData[4] = (byte) 0xA5;
                        manyIangeData[5] = (byte) 0xF0;
                        manyIangeData[6] = (byte) 0x07;
                        manyIangeData[7] = (byte) 0x02;
                        try {
                            ism.seek((i * 50) * 1024 + j * 1024);
                            ism.read(list, 0, list.length);
                            proDialog.incrementProgressBy(list.length);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.arraycopy(list, 0, manyIangeData, 8, list.length);
                        manyIangeData[list.length + 8] = (byte) ((j >> 0) & 0xFF);
                        manyIangeData[list.length + 9] = (byte) ((j >> 8) & 0xFF);
                        manyIangeData[list.length + 10] = (byte) 0x00;
                        manyIangeData[list.length + 11] = (byte) 0x00;
                        manyIangeData[list.length + 12] = (byte) 0x5A;
                        manyIangeData[list.length + 13] = (byte) 0xF6;
                        dataPacket = new DatagramPacket(manyIangeData, manyIangeData.length, local, PORT);
                        sendSocket.send(dataPacket);
                        try {
                            if (j % 6 == 0) {
                                Thread.sleep(1);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.d("--", "i = " + i + " j= " + j);
                    }
                    //3.发送多包发送完成
                    if (!sentOnebyte(SendPacket.manySendComplete())) {
                        break;
                    }
                }
            } else {
                break;
            }
            Log.d("--", "i = " + i);
        }
        //4.当前文件发送完成
        if (isSuccess) {
            if (!sentOnebyte(SendPacket.pkgHeadend())) {
                isSuccess = false;
            } else {
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    /**
     * 单包发送 三次
     *
     * @param oneByte 要发送的一拼装协议的单包数据
     */
    private boolean sentOnebyte(byte[] oneByte) {

        for (int i = 0; i < 3; i++) {
            try {
                dataPacket = new DatagramPacket(oneByte, oneByte.length, local, PORT);
                sendSocket.send(dataPacket);
                dataPacket = new DatagramPacket(receiveByte, receiveByte.length);
                sendSocket.setSoTimeout(Constant.UDP_WAIT);
                sendSocket.receive(dataPacket);
                isSuccess = true;
                break;
            } catch (IOException e) {
                e.printStackTrace();
                isSuccess = false;
            }
        }
        return isSuccess;
    }

    private void getUdpSocket() {
        getImageName();
        //创建全局socket对象
        if (sendSocket != null) {
            sendSocket.close();
        }
        List<Areabean> areabeans = new AreabeanDao(context).getListAll();
        Areabean areabean = areabeans.get(0);
        HOSTAddress = areabean.getEquitTp();
        try {
            local = InetAddress.getByName(HOSTAddress);
            try {
                sendSocket = new DatagramSocket();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    //获取所有要发送的资源
    public void getImageName() {
        sourceName = new ArrayList<String>();
        List<String> filePath = new ArrayList<>();
        filePath.add(PROGRAME_ROOT);
        String fileTextPath = Environment.getExternalStorageDirectory().toString() + File.separator
                + "EQText";
        filePath.add(fileTextPath);

        String fileImagePath = Environment.getExternalStorageDirectory().toString() + File.separator
                + "EQImage";
        filePath.add(fileImagePath);
        String fileVedioPath = Environment.getExternalStorageDirectory().toString() + File.separator
                + "EQVedio";
        filePath.add(fileVedioPath);
        String fileTimePath = Environment.getExternalStorageDirectory().toString() + File.separator
                + "EQTime";
        filePath.add(fileTimePath);
        for (int i = 0; i < filePath.size(); i++) {
            File fileAll = new File(filePath.get(i));
            if (!fileAll.exists()) {
                fileAll.mkdir();
            }
            File[] files = fileAll.listFiles();
            for (int j = 0; j < files.length; j++) {
                File file1 = files[j];
                allPath.add(String.valueOf(file1));
                String imageName = file1.getPath().substring(file1.getPath().lastIndexOf("/") + 1, file1.getPath().length());
                String DATA_TYPE = imageName.substring(imageName.indexOf(".") + 1).toString();
                if (!DATA_TYPE.equals("xml")) {
                    String aa = SendPacket.str2HexStr(imageName);
                    sourceName.add(imageName);
                }
            }
        }
    }
    //转换成DNS格式
    private String intToIp(int paramInt) {
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "." + (0xFF & paramInt >> 16) + "."
                + (0xFF & paramInt >> 24);
    }
}

package com.eq.EQSuperPlayer.communication;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/21.
 */
public class SendPacket {
    /**
     * 数组转换成十六进制字符串
     *
     * @param bArray
     * @return HexString
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }


    public static byte[] mergeIndex(byte[]... index) {
        int count = 0;
        for (int i = 0; i < index.length; i++) {
            count += index[i].length;
        }
        byte[] indexs = new byte[count];
        int offset = 0;
        for (byte[] array : index) {
            System.arraycopy(array, 0, indexs, offset, array.length);
            offset += array.length;
        }

        return indexs;
    }
    public static byte[] mergeIndex(ArrayList<byte[]> index) {
        int count = 0;
        for (int i = 0; i < index.size(); i++) {
            count += index.get(i).length;
        }
        byte[] indexs = new byte[count];
        int offset = 0;
        for (byte[] array : index) {
            System.arraycopy(array, 0, indexs, offset, array.length);
            offset += array.length;
        }

        return indexs;
    }
    /**
     * 查询在线设备
     * */
    public static byte[] findScreen(){
        byte[] b = new byte[10];
        b[0] = (byte) 0xF7;
        b[1] = (byte) 0x5B;
        b[2] = (byte) ((10 >> 0) & 0xFF);
        b[3] = (byte) ((10 >> 8) & 0xFF);
        b[4] = (byte) 0x01;
        b[5] = (byte) 0x70;
        b[6] = (byte) 0x00;
        b[7] = (byte) 0x00;
        b[8] = (byte) 0x5B;
        b[9] = (byte) 0xF7;
        return b;
    }
    /**
     * 开启屏幕协议
     */
    public static byte[] openScreen(){
        byte[] b = new byte[42];
        b[0] = (byte) 0xF6;
        b[1] = (byte) 0x5A;
        b[2] = (byte) ((42 >> 0) & 0xFF);
        b[3] = (byte) ((42 >> 8) & 0xFF);
        b[4] = (byte) 0xA4;
        b[5] = (byte) 0xF0;
        b[6] = (byte) 0x03;
        b[7] = (byte) 0x01;
        b[8] = (byte) 0x00;
        b[9] = (byte) 0x00;
        b[10] = (byte) 0x00;
        b[11] = (byte) 0x00;
        b[12] = (byte) 0x00;
        b[13] = (byte) 0x00;
        b[14] = (byte) 0x00;
        b[15] = (byte) 0x00;
        b[16] = (byte) 0x00;
        b[17] = (byte) 0x00;
        b[18] = (byte) 0x00;
        b[19] = (byte) 0x00;
        b[20] = (byte) 0x00;
        b[21] = (byte) 0x00;
        b[22] = (byte) 0x00;
        b[23] = (byte) 0x00;
        b[24] = (byte) 0x00;
        b[25] = (byte) 0x00;
        b[26] = (byte) 0x00;
        b[27] = (byte) 0x00;
        b[28] = (byte) 0x00;
        b[29] = (byte) 0x00;
        b[30] = (byte) 0x00;
        b[31] = (byte) 0x00;
        b[32] = (byte) 0x00;
        b[33] = (byte) 0x00;
        b[34] = (byte) 0x00;
        b[35] = (byte) 0x00;
        b[36] = (byte) 0x00;
        b[37] = (byte) 0x00;
        b[38] = (byte) 0x00;
        b[39] = (byte) 0x00;
        b[40] = (byte) 0x5A;
        b[41] = (byte) 0xF6;
        return b;
    }
/**
 * 关闭屏幕协议
 *
 * */
    public static byte[] guanScreen(){
        byte[] b = new byte[42];
        b[0] = (byte) 0xF6;
        b[1] = (byte) 0x5A;
        b[2] = (byte) ((42 >> 0) & 0xFF);
        b[3] = (byte) ((42 >> 8) & 0xFF);
        b[4] = (byte) 0xA4;
        b[5] = (byte) 0xF0;
        b[6] = (byte) 0x03;
        b[7] = (byte) 0x02;
        b[8] = (byte) 0x00;
        b[9] = (byte) 0x00;
        b[10] = (byte) 0x00;
        b[11] = (byte) 0x00;
        b[12] = (byte) 0x00;
        b[13] = (byte) 0x00;
        b[14] = (byte) 0x00;
        b[15] = (byte) 0x00;
        b[16] = (byte) 0x00;
        b[17] = (byte) 0x00;
        b[18] = (byte) 0x00;
        b[19] = (byte) 0x00;
        b[20] = (byte) 0x00;
        b[21] = (byte) 0x00;
        b[22] = (byte) 0x00;
        b[23] = (byte) 0x00;
        b[24] = (byte) 0x00;
        b[25] = (byte) 0x00;
        b[26] = (byte) 0x00;
        b[27] = (byte) 0x00;
        b[28] = (byte) 0x00;
        b[29] = (byte) 0x00;
        b[30] = (byte) 0x00;
        b[31] = (byte) 0x00;
        b[32] = (byte) 0x00;
        b[33] = (byte) 0x00;
        b[34] = (byte) 0x00;
        b[35] = (byte) 0x00;
        b[36] = (byte) 0x00;
        b[37] = (byte) 0x00;
        b[38] = (byte) 0x00;
        b[39] = (byte) 0x00;
        b[40] = (byte) 0x5A;
        b[41] = (byte) 0xF6;
        return b;
    }
    /**
     * 亮度调节
     * */

    public static byte[]  brightness(int bht){
        byte[] b = new byte[42];
        b[0] = (byte) 0xF6;
        b[1] = (byte) 0x5A;
        b[2] = (byte) ((42 >> 0) & 0xFF);
        b[3] = (byte) ((42 >> 8) & 0xFF);
        b[4] = (byte) 0xA4;
        b[5] = (byte) 0xF0;
        b[6] = (byte) 0x04;
        b[7] = (byte) 0x02;
        b[8] = (byte) ((bht >> 0) & 0xFF);
        b[9] = (byte) 0x00;
        b[10] = (byte) 0x00;
        b[11] = (byte) 0x00;
        b[12] = (byte) 0x00;
        b[13] = (byte) 0x00;
        b[14] = (byte) 0x00;
        b[15] = (byte) 0x00;
        b[16] = (byte) 0x00;
        b[17] = (byte) 0x00;
        b[18] = (byte) 0x00;
        b[19] = (byte) 0x00;
        b[20] = (byte) 0x00;
        b[21] = (byte) 0x00;
        b[22] = (byte) 0x00;
        b[23] = (byte) 0x00;
        b[24] = (byte) 0x00;
        b[25] = (byte) 0x00;
        b[26] = (byte) 0x00;
        b[27] = (byte) 0x00;
        b[28] = (byte) 0x00;
        b[29] = (byte) 0x00;
        b[30] = (byte) 0x00;
        b[31] = (byte) 0x00;
        b[32] = (byte) 0x00;
        b[33] = (byte) 0x00;
        b[34] = (byte) 0x00;
        b[35] = (byte) 0x00;
        b[36] = (byte) 0x00;
        b[37] = (byte) 0x00;
        b[38] = (byte) 0x00;
        b[39] = (byte) 0x00;
        b[40] = (byte) 0x5A;
        b[41] = (byte) 0xF6;
        return b;
    }
    /**
     * 开始发送节目命令 0x01**************************
     */
    public static byte[] pkgHeadInterface() {
        byte[] b = new byte[12];
        b[0] = (byte) 0xF6;
        b[1] = (byte) 0x5A;
        b[2] = (byte) ((12 >> 0) & 0xFF);
        b[3] = (byte) ((12 >> 8) & 0xFF);
        b[4] = (byte) 0xA5;
        b[5] = (byte) 0xF0;
        b[6] = (byte) 0x01;
        b[7] = (byte) 0x00;
        b[8] = (byte) 0x00;
        b[9] = (byte) 0x00;
        b[10] = (byte) 0x5A;
        b[11] = (byte) 0xF6;
        return b;
    }

    /**
     * 发送文件名称  0x02****************************
     */
    public static byte[] fileNameData(int flieSize, byte[] flieName) {
        byte[] b = new byte[13 + flieName.length];
        b[0] = (byte) 0xF6;
        b[1] = (byte) 0x5A;
        b[2] = (byte) ((13 + flieName.length >> 0) & 0xFF);
        b[3] = (byte) ((13 + flieName.length >> 8) & 0xFF);
        b[4] = (byte) 0xA5;
        b[5] = (byte) 0xF0;
        b[6] = 0x02;
        b[7] = 0x00;
        b[8] = (byte) ((flieSize >> 0) & 0xFF);
        for (int i = 0; i < flieName.length; i++) { // 包数据
            b[9 + i] = flieName[i];
        }
        b[flieName.length + 9] = (byte) 0x00;
        b[flieName.length + 10] = (byte) 0x00;
        b[flieName.length + 11] = (byte) 0x5A;
        b[flieName.length + 12] = (byte) 0xF6;
        return b;
    }

    /**
     * 发送文件数据(位置)  0x03****************************
     * pkgData 本包包数据
     * fileData 当分包时用来区分每包所在的位置
     * 发送文件数据方式共两种：发送文件数据时第一种发送方式，以每包数据所在的起始位置为标识。
     * 两种方式任选其中一种。
     */
    public static byte[] filePosition(byte[] pkgData, int fileData) {
        byte[] b = new byte[16 + pkgData.length];
        b[0] = (byte) 0xF6;
        b[1] = (byte) 0x5A;
        b[2] = (byte) ((16 + pkgData.length >> 0) & 0xFF);
        b[3] = (byte) ((16 + pkgData.length >> 8) & 0xFF);
        b[4] = (byte) 0xA5;
        b[5] = (byte) 0xF0;
        b[6] = 0x03;
        b[7] = 0x00;
        b[8] = (byte) ((fileData >> 0) & 0xFF);
        b[9] = (byte) ((fileData >> 8) & 0xFF);
        b[10] = (byte) ((fileData >> 16) & 0xFF);
        b[11] = (byte) ((fileData >> 24) & 0xFF);
        for (int i = 0; i < pkgData.length; i++) { // 包数据
            b[12 + i] = pkgData[i];
        }
        b[pkgData.length + 12] = 0x00;
        b[pkgData.length + 13] = 0x00;
        b[pkgData.length + 14] = 0x5A;
        b[pkgData.length + 15] = (byte) 0xF6;
        return b;
    }

    /**
     * 发送文件数据(索引) 0x04****************************
     * pkgIndex 本包包数据
     * indexData  分包后每包数据的索引
     * 发送文件数据时第二种发送方式，以每包数据的包序（索引）为标识
     */
    public static byte[] prepareSendDataPkg(byte[] pkgIndex, int indexData) {
        byte[] b = new byte[16 + pkgIndex.length];
        String AA = SendPacket.bytes2HexString(b, b.length);
        Log.d("..............", "AA................:" + AA);
        b[0] = (byte) 0xF6;
        b[1] = (byte) 0x5A;
        b[2] = (byte) ((16 + pkgIndex.length >> 0) & 0xFF);
        b[3] = (byte) ((16 + pkgIndex.length >> 8) & 0xFF);
        b[4] = (byte) 0xA5;
        b[5] = (byte) 0xF0;
        b[6] =(byte) 0x03;
        b[7] =(byte) 0x00;
        b[8] = (byte) ((indexData >> 0) & 0xFF);
        b[9] = (byte) ((indexData >> 8) & 0xFF);
        b[10] = (byte) ((indexData >> 16) & 0xff);
        b[11] = (byte) ((indexData >> 24) & 0xff);
        for (int i = 0; i < pkgIndex.length; i++) { // 包数据
            b[12 + i] = pkgIndex[i];
        }
        b[pkgIndex.length + 12] =(byte) 0x00;
        b[pkgIndex.length + 13] =(byte)0x00;
        b[pkgIndex.length + 14] =(byte) 0x5A;
        b[pkgIndex.length + 15] = (byte) 0xF6;
        Log.d("..............", "pkgIndex.length................:" + pkgIndex.length);
        Log.d("..............", "b................:" + b.length);
        return b;
    }

    /**
     * 文件发送完成命令  0x05***********************
     */
    public static byte[] pkgHeadend() {
        byte[] b = new byte[12];
        b[0] = (byte) 0xF6; // 包头 6 字节
        b[1] = (byte) 0x5A;
        b[2] = (byte) ((12 >> 0) & 0xFF);
        b[3] = (byte) ((12 >> 8) & 0xFF);
        b[4] = (byte) 0xA5;
        b[5] = (byte) 0xF0;
        b[6] = (byte) 0x05;
        b[7] = (byte) 0x00;
        b[8] = (byte) 0x00;
        b[9] = (byte) 0x00;
        b[10] = (byte) 0x5A;
        b[11] = (byte) 0xF6;
        return b;
    }

    /**
     * 节目数据发送结束命令  0x06*********************
     */
    public static byte[] pkgPingend() {
        byte[] b = new byte[12];
        b[0] = (byte) 0xF6; // 包头 6 字节
        b[1] = (byte) 0x5A;
        b[2] = (byte) ((12 >> 0) & 0xFF);
        b[3] = (byte) ((12 >> 8) & 0xFF);
        b[4] = (byte) 0xA5;
        b[5] = (byte) 0xF0;
        b[6] = (byte) 0x06;
        b[7] = (byte) 0x00;
        b[8] = (byte) 0x00;
        b[9] = (byte) 0x00;
        b[10] = (byte) 0x5A;
        b[11] = (byte) 0xF6;
        return b;
    }

    /**
     * 开始多包发送,其主指令为7，子指令为1
     * location  多包发送时文件数据的位置
     * totalSize  多包发送时, 所有包的总长度
     * childSize  子包的个数
     * childLength  子包的长度
     */
    public static byte[] manyPakStart(int location, int totalSize, int childSize, int childLength) {
        byte[] b = new byte[24];
        b[0] = (byte) 0xF6;
        b[1] = (byte) 0x5A;
        b[2] = (byte) ((24 >> 0) & 0xFF);
        b[3] = (byte) ((24 >> 8) & 0xFF);
        b[4] = (byte) 0xA5;
        b[5] = (byte) 0xF0;
        b[6] = (byte) 0x07;
        b[7] = (byte) 0x01;

        b[8] = (byte) ((location >> 0) & 0xFF);
        b[9] = (byte) ((location >> 8) & 0xFF);
        b[10] = (byte) ((location >> 16) & 0xFF);
        b[11] = (byte) ((location >> 24) & 0xFF);

        b[12] = (byte) ((totalSize >> 0) & 0xFF);
        b[13] = (byte) ((totalSize >> 8) & 0xFF);
        b[14] = (byte) ((totalSize >> 16) & 0xFF);
        b[15] = (byte) ((totalSize >> 24) & 0xFF);

        b[16] = (byte) ((childSize >> 0) & 0xFF);
        b[17] = (byte) ((childSize >> 8) & 0xFF);

        b[18] = (byte) ((childLength) & 0xFF);
        b[19] = (byte) ((childLength >> 8) & 0xFF);

        b[20] = 0x00;
        b[21] = 0x00;
        b[22] = 0x5A;
        b[23] = (byte) 0xF6;
        return b;
    }

    /**
     * 多包数据发送的时候, 主指令为7 , 子指令为2,
     */
    public static byte[] manyData(byte[] manyDataSend, int pakSequence) {
        byte[] b = new byte[14 + manyDataSend.length];
        b[0] = (byte) 0xF6;
        b[1] = (byte) 0x5A;
        b[2] = (byte) ((14 + manyDataSend.length >> 0) & 0xFF);
        b[3] = (byte) ((14 + manyDataSend.length >> 8) & 0xFF);
        b[4] = (byte) 0xA5;
        b[5] = (byte) 0xF0;
        b[6] = (byte) 0x07;
        b[7] = (byte) 0x02;

        for (int i = 0; i < manyDataSend.length; i++) { // 包数据
            b[8 + i] = manyDataSend[i];
        }
        b[manyDataSend.length + 8] = (byte) ((pakSequence >> 0) & 0xFF);
        b[manyDataSend.length + 9] = (byte) ((pakSequence >> 8) & 0xFF);

        b[manyDataSend.length + 10] = (byte) 0x00;
        b[manyDataSend.length + 11] = (byte) 0x00;
        b[manyDataSend.length + 12] = (byte) 0x5A;
        b[manyDataSend.length + 13] = (byte) 0xF6;
        return b;
    }

    /**
     *多包发送完成, 主指令为 7, 子指令为 3. 在尾部固定标识前添加4个字节, 协议包长度为16个字节
     *
     * */
    public static byte[] manySendComplete(){
        byte[] b = new byte[16];
        b[0] = (byte) 0xF6;
        b[1] = (byte) 0x5A;
        b[2] = (byte) ((16 >> 0) & 0xFF);
        b[3] = (byte) ((16 >> 8) & 0xFF);
        b[4] = (byte) 0xA5;
        b[5] = (byte) 0xF0;
        b[6] = (byte) 0x07;
        b[7] = (byte) 0x03;
        b[8] = (byte) 0x00;
        b[9] = (byte) 0x00;
        b[10] = (byte) 0x00;
        b[11] = (byte) 0x00;
        b[12] = (byte) 0x07;
        b[13] = (byte) 0x03;
        b[14] = (byte) 0x5A;
        b[15] = (byte) 0xF6;
        return b;
    }
    /**
     * 图片转十六进制
     * */
    public static byte[] sendPhoto(Bitmap photo) {
        Bitmap bitmap = photo;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0 - 100)压缩文件
        byte[] bt = stream.toByteArray();
        return bt;
    }

    /**
     * 二进制转字符串
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                sb.append("0" + stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString();
    }

    /**
     * @Title:hexString2String
     * @Description:16进制字符串转字符串
     * @param src
     *            16进制字符串
     * @return 字节数组
     * @throws
     */
    public static String hexString2String(String src) {
        String temp = "";
        for (int i = 0; i < src.length() / 2; i++) {
            temp = temp+ (char) Integer.valueOf(src.substring(i * 2, i * 2 + 2),
                    16).byteValue();
        }
        return temp;
    }
    /**
     * byte 转String
     *
     * @param b
     * @param len
     * @return
     */
    public static String bytes2HexString(byte[] b, int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase() + " ";
        }
        return ret;
    }

    public static String bytes2HexString(ArrayList<Byte> b, int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            //  Log.d("b.get(i)",""+b.get(i).toString());
            String hex = Integer.toHexString(b.get(i) & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            //  Log.d("test",""+hex.toUpperCase());
            ret += hex.toUpperCase() + " ";
        }
        return ret;
    }

    /**
     * 字符串转换成十六进制字符串
     *
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public static String str2HexStr(String str) {

        char[] chars = "0123456789ABCDEF.".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }
    public static String toD(String a, int b) {
        int r = 0;
        for (int i = 0; i < a.length(); i++) {
            r = (int) (r + formatting(a.substring(i, i + 1))
                    * Math.pow(b, a.length() - i - 1));
        }
        return String.valueOf(r);
    }

    // 将十六进制中的字母转为对应的数字
    public static int formatting(String a) {
        int i = 0;
        for (int u = 0; u < 10; u++) {
            if (a.equals(String.valueOf(u))) {
                i = u;
            }
        }
        if (a.equals("a")) {
            i = 10;
        }
        if (a.equals("b")) {
            i = 11;
        }
        if (a.equals("c")) {
            i = 12;
        }
        if (a.equals("d")) {
            i = 13;
        }
        if (a.equals("e")) {
            i = 14;
        }
        if (a.equals("f")) {
            i = 15;
        }
        return i;
    }

}

package com.eq.EQSuperPlayer.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.bean.Areabean;
import com.eq.EQSuperPlayer.bean.SendAdapter;
import com.eq.EQSuperPlayer.communication.ConnectControlCard;
import com.eq.EQSuperPlayer.communication.InterfaceConnect;
import com.eq.EQSuperPlayer.communication.SendPacket;
import com.eq.EQSuperPlayer.custom.CustomPopWindow;
import com.eq.EQSuperPlayer.fargament.LeftFragment;
import com.eq.EQSuperPlayer.fargament.ProgramFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener {
    private FragmentManager mFragmentManager;
    private RadioGroup radioGroup;
    private Button mSend;
    private Fragment mContent;
    private ImageView topButton;
    private TextView topTextView;
    private boolean isExit = false;
    private int count;
    private int AllSize = 0;
    private ProgressDialog proDialog; // 进度条
    private ConnectControlCard ccc;
    private byte[] proBytes;   //存放XML的byte数组
    private List<byte[]> program_lists = null;   //当xml数据包需要分包的时候
    private SendAdapter mSendAdapter;
    private CustomPopWindow customPopWindow;
    private List<byte[]> programe_lists = null;  //当数据需要分包时
    private List<Integer> programe_size = null;  //存放当前包长度
    private ArrayList<String> iamgID; //存放图片名的数组
    private List<String> filr = new ArrayList<String>();
    private List<Bitmap> bitmaps; //存放图片的数组
    private List<String> progrmae = new ArrayList<String>(); //存放图片路径的数组
    private int start;//0 代表发送成功，发送文件数据，1 代表发送失败，2
    //    private int arrds = 0;//去图片名称的位置
    private int imageAllSize = 0;//取出图片的总个数
    private boolean isSuee = true;
    private ArrayList<byte[][]> arrayList = new ArrayList<byte[][]>();
    private List<byte[]> arrays = new ArrayList<byte[]>();
    private int countAdress = 0;//当前发送的个数
    private int manyAllConten = 0;//总包数
    private int sendLeng = 0;
    private int sendConten = 0;
    private int MULTI_PACKAGE_MAX_COUNT = 50;//多包发送时最大的包个数
    private int MULTI_PACKAGE_MIN_COUNT = 10;//多包发送时最小的包个数
    private int DATA_MAX_SIZE = 1024;// 数据最大长度
    private int TIMEOUT = 3;//丢包时发送次数
    private byte[] backData = new byte[100];
    private String PROGRAME_ROOT = Environment
            .getExternalStorageDirectory()
            .getAbsolutePath() + "/EQPrograme/";
    private int indexdata = 0;
    private int errorbig = -1;//那一大包
    private int errorsimall = -1;//那个小包
    private int alllen; //数据包总长度
    private int countOrAdress = 0;
    private int manyStratIndex = 0;//多包发送的起始位置
    private int endLeng = 0;//最后一小包的长度
    private int EORRE_COUNT = 1;//错误次数
//    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();//线程池
    private int resourcesIndex = 0;
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    private DhcpInfo dhcpInfo;
    public static String IP;
    public static DatagramSocket dataSocket = null;
    private byte[] buffer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        initSlidingMenu(savedInstanceState);
        mSend = (Button) findViewById(R.id.send);
        topButton = (ImageView) findViewById(R.id.topButton);
        topButton.setOnClickListener(this);
        topTextView = (TextView) findViewById(R.id.topTv);
        mSend.setOnClickListener(this);
        //获取FragmentManager
        //获取WiFi信息
        WifiManager my_wifiManager = ((WifiManager) getSystemService(WIFI_SERVICE));
        dhcpInfo = my_wifiManager.getDhcpInfo();
        IP = intToIp(dhcpInfo.dns1);
        mFragmentManager = getSupportFragmentManager();
        //获取radioGroup控件
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        //监听点击按钮事件,实现不同Fragment之间的切换
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                Fragment fragment = FragmentFactory.getInstanceByIndex(checkedId,MainActivity.this);
                transaction.replace(R.id.fragment, fragment);
                transaction.commit();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
       RadioButton rb = (RadioButton) radioGroup.getChildAt(0);
        rb.setChecked(true);
    }

    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    countOrAdress = 0;
                    manyStratIndex = 0;
                    resourcesIndex = 0;
                    countAdress = 0;//当前发送的长度
                    manyAllConten = 0;//总包数
                    sendLeng = 0;
                    sendConten = 0;
                    String paths = progrmae.get(resourcesIndex);
                    File file = new File(paths);
                    try {
                        FileInputStream is = new FileInputStream(file);
                        byte[] buffer = new byte[is.available()];
                        Log.d("...", "buffer.length....." + buffer.length);
                        is.read(buffer);
                        if (buffer.length < DATA_MAX_SIZE) {

                        }
                        int endXMLlen = DATA_MAX_SIZE;
                        int xmlCount = buffer.length / DATA_MAX_SIZE;//xml数据可以分为多少个1024包
                        if (buffer.length % DATA_MAX_SIZE > 0) {
                            xmlCount += 1;
                            endXMLlen = buffer.length % DATA_MAX_SIZE;
                        }
                        List<byte[]> cutXml = new ArrayList<byte[]>();
                        byte[] xmlData;
                        for (int i = 0; i < xmlCount; i++) {
                            if (i == xmlCount - 1) {
                                xmlData = new byte[endXMLlen];
                                System.arraycopy(buffer, i * DATA_MAX_SIZE, xmlData, 0, endXMLlen);
                            } else {
                                xmlData = new byte[DATA_MAX_SIZE];
                                System.arraycopy(buffer, i * DATA_MAX_SIZE, xmlData, 0, DATA_MAX_SIZE);
                            }
                            byte[] proBytes = SendPacket.prepareSendDataPkg(xmlData, 0);
                            cutXml.add(proBytes);
                        }
                        ccc = new ConnectControlCard(MainActivity.this,cutXml, new InterfaceConnect() {
                            @Override
                            public void success(byte[] result) {
                                start = 0;
                                handler.sendEmptyMessage(2);
                            }

                            @Override
                            public void failure(int stateCode) {
                                start = 1;
                                if (EORRE_COUNT >= 3) {
                                    handler.sendEmptyMessage(1);
                                    EORRE_COUNT = 1;
                                } else {
                                    handler.sendEmptyMessage(0);
                                    EORRE_COUNT++;
                                }

                            }

                            @Override
                            public void dataSuccess(String str) {

                            }
                        });
                        fixedThreadPool.execute(ccc);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.program_send_failure), Toast.LENGTH_SHORT).show();
                    proDialog.cancel();
                    break;
                case 2://文件发送完成指令
                    List<byte[]> sendEnd = new ArrayList<byte[]>();
                    byte[] managerSend = SendPacket.pkgHeadend();
                    sendEnd.add(managerSend);
                    ccc = new ConnectControlCard(MainActivity.this,sendEnd, new InterfaceConnect() {
                        @Override
                        public void success(byte[] result) {
                            if (start == 0) {
                                handler.sendEmptyMessage(3);
                            } else if (start == 8) {
                                Log.d("....", "imageAllSize88888" + imageAllSize);
                                if (resourcesIndex < imageAllSize) {
                                    resourcesIndex++;
                                    countOrAdress = 0;
                                    countAdress = 0;
                                    handler.sendEmptyMessage(3);
                                } else {
                                    handler.sendEmptyMessage(4);
                                }
                            } else if (start == 6) {
                                Log.d("....", "countAdress" + countAdress);
                                Log.d("....", "AllSize" + AllSize);
                                if (countAdress == AllSize) {
                                    Log.d("....", "imageAllSize11111" + imageAllSize);
                                    Log.d("....", "resourcesIndex2222222" + resourcesIndex);
                                    if (resourcesIndex == imageAllSize - 1) {
                                        handler.sendEmptyMessage(4);
                                    } else {
                                        countOrAdress = 0;
                                        countAdress = 0;
                                        handler.sendEmptyMessage(3);
                                        resourcesIndex++;
                                    }
                                } else {
                                    handler.sendEmptyMessage(6);
                                }
                            }
                        }

                        @Override
                        public void failure(int stateCode) {
                            handler.sendEmptyMessage(1);
                        }

                        @Override
                        public void dataSuccess(String str) {

                        }
                    });
                    fixedThreadPool.execute(ccc);
                    break;
                case 3://发送文件名称指令
                    Log.d("....", "resourcesIndex33333333" + resourcesIndex);
                    List<byte[]> controlData = new ArrayList<byte[]>();
                    String str = iamgID.get(resourcesIndex);
                    byte[] bytes = str.getBytes();
                    String a1 = SendPacket.bytes2HexString(bytes, bytes.length);
                    Log.d(".........", "a1..............." + a1);
                    Log.d(".........", "bytes.length..............." + bytes.length);
                    byte[] controlCard2 = SendPacket.fileNameData(bytes.length, bytes);
                    String aa = SendPacket.bytes2HexString(controlCard2, controlCard2.length);
                    Log.d(".........", "aa..............." + aa);
                    Log.d(".........", "bytes.length..............." + bytes.length);
                    controlData.add(controlCard2);
                    ccc = new ConnectControlCard(MainActivity.this,controlData, new InterfaceConnect() {
                        @Override
                        public void success(byte[] result) {
                            countOrAdress = 0;
                            manyStratIndex = 0;
                            countAdress = 0;//当前发送的长度
                            manyAllConten = 0;//总包数
                            sendLeng = 0;
                            sendConten = 0;
                            start = 3;
                            handler.sendEmptyMessage(6);
                            isSuee = true;
                        }

                        @Override
                        public void failure(int stateCode) {
                            handler.sendEmptyMessage(1);
                        }

                        @Override
                        public void dataSuccess(String str) {

                        }
                    });
                    fixedThreadPool.execute(ccc);
                    break;
                case 4://节目发送完成指令
                    List<byte[]> programeData = new ArrayList<byte[]>();
                    byte[] endSend = SendPacket.pkgPingend();
                    programeData.add(endSend);
                    ccc = new ConnectControlCard(MainActivity.this,programeData, new InterfaceConnect() {
                        @Override
                        public void success(byte[] result) {
                            start = 4;
                            handler.sendEmptyMessage(9);
                        }

                        @Override
                        public void failure(int stateCode) {
                            start = 1;
                            handler.sendEmptyMessage(1);
                        }

                        @Override
                        public void dataSuccess(String str) {

                        }
                    });
                    fixedThreadPool.execute(ccc);
                    break;
                case 6:
                    Log.d("......", "countAdress2222222:" + countAdress);
                    Log.d("......", "resourcesIndex6666666:" + resourcesIndex);
                    List<byte[]> manyData = new ArrayList<byte[]>();
                    String pathData = progrmae.get(resourcesIndex + 1);
                    File files = new File(pathData);
                    try {
                        FileInputStream ism = new FileInputStream(files);
                        buffer = new byte[ism.available()];
                        ism.read(buffer);
                        alllen = buffer.length;//总长度
                        count = alllen / DATA_MAX_SIZE;//总包以50包为单位可以分多少个
                        Log.d("...", "COUNT...." + count);
                        if (alllen % DATA_MAX_SIZE > 0) {
                            count += 1;
                            endLeng = alllen % DATA_MAX_SIZE;
                        }
                        AllSize = count;//总包数
                        if ((AllSize - 1) % MULTI_PACKAGE_MAX_COUNT > 0) {
                            sendLeng = (AllSize - 1) % MULTI_PACKAGE_MAX_COUNT;//最后一包个数
                            manyAllConten = (AllSize - 1) / MULTI_PACKAGE_MAX_COUNT + 2;//数据总长度可以分为多少个50包
                        } else if ((AllSize - 1) % MULTI_PACKAGE_MAX_COUNT == 0) {
                            sendLeng = MULTI_PACKAGE_MAX_COUNT;
                            manyAllConten = (AllSize - 1) / MULTI_PACKAGE_MAX_COUNT + 1;
                        }
                        Log.d(".....", "manyAllConten返回的数据。。。：" + manyAllConten);
                        sendConten = sendLeng;//最后一组数据包的个数
                        if (countOrAdress < manyAllConten - 2) {
                            byte[] manySendStart = SendPacket.manyPakStart(manyStratIndex, MULTI_PACKAGE_MAX_COUNT * DATA_MAX_SIZE, MULTI_PACKAGE_MAX_COUNT, DATA_MAX_SIZE);
                            countAdress = (countOrAdress + 1) * MULTI_PACKAGE_MAX_COUNT;
                            manyStratIndex = countAdress * DATA_MAX_SIZE;//每包起始位置长度
                            manyData.add(manySendStart);
                            ccc = new ConnectControlCard(MainActivity.this,manyData, new InterfaceConnect() {
                                @Override
                                public void success(byte[] result) {
                                    String results = SendPacket.byte2hex(result);
                                    Log.d(".....", "results返回的数据。。。：" + results);
                                    start = 6;
                                    handler.sendEmptyMessage(7);
                                }

                                @Override
                                public void failure(int stateCode) {
                                    start = 1;
                                    handler.sendEmptyMessage(1);
                                }

                                @Override
                                public void dataSuccess(String str) {

                                }
                            });
                            fixedThreadPool.execute(ccc);
                        } else if (countOrAdress == manyAllConten - 2) {
                            if (1 <= sendConten) {
                                Log.d("最后一包当前位置", "manyStratIndex....." + manyStratIndex);
                                Log.d("最后一包当前位置", "sendConten....." + sendConten);
                                byte[] manySendStart = SendPacket.manyPakStart(manyStratIndex, sendConten * DATA_MAX_SIZE, sendConten, DATA_MAX_SIZE);
                                String many = SendPacket.byte2hex(manySendStart);
                                Log.d("最后一包当前位置", "many....." + many);
                                manyData.add(manySendStart);
                                ccc = new ConnectControlCard(MainActivity.this,manyData, new InterfaceConnect() {
                                    @Override
                                    public void success(byte[] result) {
                                        handler.sendEmptyMessage(7);
                                    }

                                    @Override
                                    public void failure(int stateCode) {
                                        start = 1;
                                        handler.sendEmptyMessage(1);
                                    }

                                    @Override
                                    public void dataSuccess(String str) {

                                    }
                                });
                                fixedThreadPool.execute(ccc);
                            }
                        } else {
                            byte[] list = new byte[DATA_MAX_SIZE];
                            System.arraycopy(buffer, alllen - endLeng, list, 0, alllen % DATA_MAX_SIZE);
                            Log.d("......", "countAdress1111111111:" + countAdress);
                            arrays = new ArrayList<byte[]>();
                            arrays.add(list);
                            countAdress++;
                            List<byte[]> simallData = new ArrayList<byte[]>();
                            for (int i = 0; i < arrays.size(); i++) {
                                byte[] arrData = arrays.get(i);
                                byte[] iangeData = SendPacket.prepareSendDataPkg(arrData, i);
                                simallData.add(iangeData);
                            }
                            ccc = new ConnectControlCard(MainActivity.this,simallData, new InterfaceConnect() {
                                @Override
                                public void success(byte[] result) {
                                    String results = SendPacket.byte2hex(result);
                                    Log.d(".........", "results..........:" + results);
                                    start = 6;
                                    handler.sendEmptyMessage(2);
                                }

                                @Override
                                public void failure(int stateCode) {
                                    handler.sendEmptyMessage(1);
                                }

                                @Override
                                public void dataSuccess(String str) {

                                }
                            });
                            fixedThreadPool.execute(ccc);
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;

                case 7:
                    Log.d("...", "countOrAdress.jjjj.." + countOrAdress);
                    if (countOrAdress < manyAllConten - 2) {
                        for (int j = 0; j < MULTI_PACKAGE_MAX_COUNT; j++) {
                            byte[] list = new byte[DATA_MAX_SIZE];
                                    System.arraycopy(buffer, (countOrAdress * MULTI_PACKAGE_MAX_COUNT + j) * DATA_MAX_SIZE, list, 0, DATA_MAX_SIZE);
                            arrays.add(list);
                        }
                        Log.d(".....", "arrays....." + arrays.size());
                        List<byte[]> manySnedBig = new ArrayList<byte[]>();
                        for (int i = 0; i < arrays.size(); i++) {
                            errorsimall = i;
                            byte[] arrData = arrays.get(i);
                            byte[] manyIangeData = SendPacket.manyData(arrData, i);
                            String ss = SendPacket.byte2hex(manyIangeData);
                            manySnedBig.add(manyIangeData);
                        }
                        ccc = new ConnectControlCard(MainActivity.this,manySnedBig, new InterfaceConnect() {
                            @Override
                            public void success(byte[] result) {

                            }

                            @Override
                            public void failure(int stateCode) {

                            }

                            @Override
                            public void dataSuccess(String str) {
                                start = 7;
                                handler.sendEmptyMessage(8);
                            }
                        });
                        fixedThreadPool.execute(ccc);
                        Log.d("....", "countAdress当前长度。。。。。：" + countAdress);
                    } else {
                        if (1 < sendConten) {
                            for (int j = 0; j < sendConten; j++) {
                                byte[] list = new byte[DATA_MAX_SIZE];
                                System.arraycopy(buffer, (countOrAdress * MULTI_PACKAGE_MAX_COUNT + j) * DATA_MAX_SIZE, list, 0, DATA_MAX_SIZE);
                                arrays.add(list);
                            }
                            Log.d("....", "countAdress当前长度。。。。。：" + countAdress + "countAdress当前长度。。。。。：" + (AllSize - countAdress));
                            countAdress = countAdress + sendLeng;
                            Log.d(".....", "arrays2....." + arrays.size());
                            List<byte[]> manySnedBig = new ArrayList<byte[]>();
                            for (int i = 0; i < arrays.size(); i++) {
                                errorsimall = i;
                                byte[] arrData = arrays.get(i);
                                byte[] manyIangeData = SendPacket.manyData(arrData, i);
                                String ss = SendPacket.byte2hex(manyIangeData);
                                manySnedBig.add(manyIangeData);
                            }
                            ccc = new ConnectControlCard(MainActivity.this,manySnedBig, new InterfaceConnect() {

                                @Override
                                public void success(byte[] result) {
                                }

                                @Override
                                public void failure(int stateCode) {

                                }

                                @Override
                                public void dataSuccess(String str) {
                                    handler.sendEmptyMessage(8);
                                }
                            });
                            fixedThreadPool.execute(ccc);
                        }
                    }
                    countOrAdress++;
                    break;
                case 8:
                    List<byte[]> manySnedEnd = new ArrayList<byte[]>();
                    byte[] manySend = SendPacket.manySendComplete();
                    String SEND = SendPacket.byte2hex(manySend);
                    Log.d("......", "SEND返回的数据是。。。。。。。：" + SEND);
                    manySnedEnd.add(manySend);
                    erro(manySnedEnd, EORRE_COUNT);
                    EORRE_COUNT++;
                    break;
                case 9:
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.program_send_success), Toast.LENGTH_SHORT).show();
                    proDialog.cancel();
                    break;

            }
            super.handleMessage(msg);
        }
    };
    //接受错误回报
    public void erro(List<byte[]> bytes, final int erroData) {
        ccc = new ConnectControlCard(MainActivity.this,bytes, new InterfaceConnect() {
            @Override
            public void success(byte[] result) {
                String come = SendPacket.byte2hex(result);
                Log.d("......", "result返回的数据是。。。。。。。：" + come);
                String aa = SendPacket.toD(come, come.length());
                Log.d("......", "aa 返回的数据是。。。。。。。：" + aa);
                Log.d("......", "countAdress 返回的数据是。。。。。。。：" + countAdress);
                Log.d("......", "countAdress2222222:" + countAdress);
                Log.d("......", "countAdress66666:" + AllSize);
                if (countAdress == AllSize + 1) {
                    handler.sendEmptyMessage(2);
                } else {
                    handler.sendEmptyMessage(6);
                }
                start = 8;
            }

            @Override
            public void failure(int stateCode) {
                int eorreindex = erroData;
                if (eorreindex == 3) {
                    start = 1;
                    handler.sendEmptyMessage(1);
                    EORRE_COUNT = 1;
                } else {
                    handler.sendEmptyMessage(8);
                }

            }

            @Override
            public void dataSuccess(String str) {

            }
        });
        fixedThreadPool.execute(ccc);
    }


    /**
     * 节目发送指令
     */
    public void Sendprogram() {
        getImageName();
        if (progrmae.size() > 0){
        List<byte[]> manySnedStart = new ArrayList<byte[]>();
        proDialog = android.app.ProgressDialog.show(this, null,
                getResources().getString(R.string.program_sending));
        byte[] controlCard = SendPacket.pkgHeadInterface();
        manySnedStart.add(controlCard);
        ccc = new ConnectControlCard(MainActivity.this,manySnedStart, new InterfaceConnect() {
            @Override
            public void success(byte[] result) {
                try {
                    Thread.sleep(1000);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failure(int stateCode) {
                start = 1;
                handler.sendEmptyMessage(1);
            }

            @Override
            public void dataSuccess(String str) {

            }
        });
            fixedThreadPool.execute(ccc);
        }else {
            Toast.makeText(this,"当前节目内容为空!",Toast.LENGTH_SHORT).show();
        }
    }

    public void getImageName() {
        iamgID = new ArrayList<String>();
        List<String> filePath = new ArrayList<>();
        filePath.add(PROGRAME_ROOT);
        String fileTextPath = Environment.getExternalStorageDirectory().toString() + File.separator
                + "textImage";
        filePath.add(fileTextPath);

        String fileImagePath = Environment.getExternalStorageDirectory().toString() + File.separator
                + "EQImage";
        filePath.add(fileImagePath);
        String fileVedioPath = Environment.getExternalStorageDirectory().toString() + File.separator
                + "EQVedio";
        filePath.add(fileVedioPath);
        for (int i = 0; i < filePath.size(); i++) {
            File fileAll = new File(filePath.get(i));
            if (!fileAll.exists()) {
                fileAll.mkdir();
            }
            File[] files = fileAll.listFiles();
            for (int j = 0; j < files.length; j++) {
                File file1 = files[j];
                progrmae.add(String.valueOf(file1));
                Log.d("...........", "progrmae........:" + progrmae.toString());
                String imageName = file1.getPath().substring(file1.getPath().lastIndexOf("/") + 1, file1.getPath().length());
                String DATA_TYPE = imageName.substring(imageName.indexOf(".") + 1).toString();
                if (!DATA_TYPE.equals("xml")) {
                    String aa = SendPacket.str2HexStr(imageName);
                    filr.add(aa);
                    iamgID.add(imageName);
                    imageAllSize = iamgID.size();
                }
            }
            Log.d(".......", "图片总个数。。。。:" + iamgID);
        }

    }


    public View getPopWindowListView() {
        List<Areabean> areabeens = new ArrayList<Areabean>();
        View view = this.getLayoutInflater().inflate(R.layout.program, null);
        ListView mListView = (ListView) view.findViewById(R.id.send_list);
        Button mButton = (Button) view.findViewById(R.id.send_button);
        mSendAdapter = new SendAdapter(areabeens, MainActivity.this);
        mListView.setAdapter(mSendAdapter);

        return null;
    }

    /**
     * 初始化侧边栏
     */
    private void initSlidingMenu(Bundle savedInstanceState) {
        // 设置侧面隐藏的布局
        setBehindContentView(R.layout.menu_frame_left);
        // 增加碎片
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        LeftFragment leftFragment = new LeftFragment();
        ft.replace(R.id.menu_frame, leftFragment);
        ft.commit();

        // 实例化滑动菜单对象
        SlidingMenu sm = getSlidingMenu();
        // 设置可以左右滑动的菜单
        sm.setMode(SlidingMenu.LEFT);
        // 设置滑动阴影的宽度
        sm.setShadowWidthRes(R.dimen.shadow_width);
        // 设置滑动菜单阴影的图像资源
        sm.setShadowDrawable(null);
        // 设置滑动菜单视图的宽度
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        sm.setFadeDegree(0.35f);
        // 设置触摸屏幕的模式,这里设置为全屏
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置下方视图的在滚动时的缩放比例
        sm.setBehindScrollScale(0.0f);

    }

    /**
     * ********************* 获取返回键监听 调用退出方法
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    /**
     * ********************* 双击退出程序方法
     */

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true;
            // 准备退出
            Toast.makeText(MainActivity.this, getResources().getText(R.string.hint_exit), Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            MainActivity.this.finish();
            System.exit(0);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topButton:
                toggle();
                break;
            case R.id.send:
                Sendprogram();
                if (customPopWindow == null) {
                    customPopWindow = new CustomPopWindow(MainActivity.this, R.id.send);
                    customPopWindow.setView(getPopWindowListView(), 1.0f, 0.60f);
                    customPopWindow.backgroundAlpha(1f);
                }
                customPopWindow.showPopupWindow(mSend);

                break;
        }
    }
    //转换成DNS格式
    private String intToIp(int paramInt) {
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "." + (0xFF & paramInt >> 16) + "."
                + (0xFF & paramInt >> 24);
    }
}

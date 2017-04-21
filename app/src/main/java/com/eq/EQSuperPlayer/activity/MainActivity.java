package com.eq.EQSuperPlayer.activity;

import android.app.ProgressDialog;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
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
import com.eq.EQSuperPlayer.communication.UDPSocketUtis;
import com.eq.EQSuperPlayer.custom.CustomPopWindow;
import com.eq.EQSuperPlayer.fargament.LeftFragment;
import com.eq.EQSuperPlayer.fargament.ProgramFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private RandomAccessFile ism;
    private ArrayList<String> iamgID; //存放图片名的数组
    private List<String> filr = new ArrayList<String>();
    private List<String> progrmae = new ArrayList<String>(); //存放图片路径的数组
    private int imageAllSize = 0;//取出图片的总个数
    private String PROGRAME_ROOT = Environment
            .getExternalStorageDirectory()
            .getAbsolutePath() + "/EQPrograme/";
    private DhcpInfo dhcpInfo;
    public static String IP;
    private int MAXLENG = 0;//数据最大长度

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
                Fragment fragment = FragmentFactory.getInstanceByIndex(checkedId, MainActivity.this);
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


//    builder = new AlertDialog.Builder(MainActivity.this);
//            LayoutInflater factory = LayoutInflater.from(this);
//            final View textEntryView = factory.inflate(R.layout.layou_loading, null);
//            builder.setView(textEntryView);
//            LoadingView loadingView = new LoadingView(MainActivity.this);
//            loadingView.startAnimation(0,100,5000);
//            loadingView.setMax(MAXLENG);
//            builder.setCancelable(false);
//            builder.create().show();

    /**
     * 节目发送指令
     */
    public void Sendprogram() {
        getImageName();
        if (progrmae.size() > 1) {
            proDialog = new ProgressDialog(this);
            proDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            proDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
            proDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            proDialog.setTitle("节目发送中......");
            proDialog.setMax(MAXLENG);
            proDialog.show();
            new Thread(new UDPSocketUtis(this, proDialog, new InterfaceConnect() {
                @Override
                public void success(byte[] result) {
                    proDialog.cancel();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "文件发送完成", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

                @Override
                public void failure(final int stateCode) {
                    proDialog.cancel();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (stateCode == 0){
                                Toast.makeText(MainActivity.this, "文件发送失败", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(MainActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

                @Override
                public void dataSuccess(String str) {

                }
            })).start();
        } else {
            Toast.makeText(this, "当前节目内容为空!", Toast.LENGTH_SHORT).show();
        }
    }


    public void getImageName() {
        MAXLENG = 0;
        iamgID = new ArrayList<String>();
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
        for (int i = 0; i < filePath.size(); i++) {
            File fileAll = new File(filePath.get(i));
            if (!fileAll.exists()) {
                fileAll.mkdir();
            }
            File[] files = fileAll.listFiles();
            for (int j = 0; j < files.length; j++) {
                File file1 = files[j];
                MAXLENG += file1.length();
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

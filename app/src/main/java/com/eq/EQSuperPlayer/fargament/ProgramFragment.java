package com.eq.EQSuperPlayer.fargament;

import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.activity.ProgramActivity;
import com.eq.EQSuperPlayer.adapter.ProgramAdapter;
import com.eq.EQSuperPlayer.bean.Areabean;
import com.eq.EQSuperPlayer.custom.CustomPopWindow;
import com.eq.EQSuperPlayer.custom.CustomTypeWindow;
import com.eq.EQSuperPlayer.dao.AreabeanDao;
import com.eq.EQSuperPlayer.utils.FileUtils;
import com.eq.EQSuperPlayer.view.SlidingItemListView;
import com.eq.EQSuperPlayer.utils.ProgramNameItemManager;
import com.eq.EQSuperPlayer.utils.WindowSizeManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ProgramFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {
    private EditText program_name;
    private TextView program_type;
    private EditText program_width;
    private EditText program_height;
    private EditText program_ip;
    private EditText progeam_duan;
    private TextView tvbianji;
    private Button program_btn;
    private ImageView mImageView;
    public static CustomPopWindow addPopWindow;
    private SlidingItemListView mListView;
    private ProgramAdapter mProgramAdapter;
    private List<Areabean> areabeens;
    private Areabean mAreabean;
    private int program_name_count;   //自动命名的数字
    private DhcpInfo dhcpInfo;
    private int windowWidth;
    private int windowHeight;
    private CustomTypeWindow customTypeWindow;
    private Button btn_3;
    private Button btn_A1;
    private Button btn_A2;
    private Button btn_A3;
    private Button btn_A2L;
    private Button btn_A3L;
    private Button btn_Q1;
    private Button btn_Q2;
    private Button btn_Q3;
    private Button btn_Q4;
    private Button btn_Q3L;
    private Button btn_Q4L;
    private int updataArea;
    private boolean newOrupdata = true;
    public static String ip;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_program, container, false);
        mImageView = (ImageView) view.findViewById(R.id.iamge);
        mListView = (SlidingItemListView) view.findViewById(R.id.program_list);
        mListView.setEmptyView(view.findViewById(R.id.myText));
        areabeens = new AreabeanDao(getActivity()).getListAll();
        if (areabeens.size() == 0) {
            List<String> filePath = new ArrayList<>();
            String fileTextPath = Environment.getExternalStorageDirectory().toString() + File.separator
                    + "textImage";
            filePath.add(fileTextPath);
            String fileImagePath = Environment.getExternalStorageDirectory().toString() + File.separator
                    + "EQImage";
            filePath.add(fileImagePath);
            String fileVedioPath = Environment.getExternalStorageDirectory().toString() + File.separator
                    + "EQVedio";
            filePath.add(fileVedioPath);
            String PROGRAME_ROOT = Environment
                    .getExternalStorageDirectory()
                    .getAbsolutePath() + "/EQPrograme/";
            filePath.add(PROGRAME_ROOT);
            for (int i = 0; i < filePath.size(); i++) {
                File fileAll = new File(filePath.get(i));
                if (!fileAll.exists()) {
                    fileAll.mkdir();
                }
                FileUtils.deleteDir(fileAll.getPath());
            }
        }
        mImageView.setOnClickListener(this);
        mListView.setOnTouchListener(this);
        mProgramAdapter = new ProgramAdapter(getActivity(), areabeens);
        mListView.setAdapter(mProgramAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(",,,,,,,,", "你点击了id为" + areabeens.get(position).getId() + "itme");
                Intent intent = new Intent(getActivity(), ProgramActivity.class);
                ProgramActivity.program_id = areabeens.get(position).getId();
                getActivity().startActivity(intent);
                getActivity(). overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);


            }
        });

        //获取WiFi信息
        WifiManager my_wifiManager = ((WifiManager) getActivity().getSystemService(getActivity().WIFI_SERVICE));
        dhcpInfo = my_wifiManager.getDhcpInfo();
        String IP = intToIp(dhcpInfo.dns1);
        Log.d(".....", IP + "");
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                areabeens = new AreabeanDao(getContext()).getListAll();
                handler.sendEmptyMessage(1);
            }
        }).start();
    }

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mProgramAdapter = new ProgramAdapter(getActivity(), areabeens);
                    mListView.setAdapter(mProgramAdapter);
                    break;
                case 1:
                    updateList();
                    break;
            }

        }
    };

    public void updateList() {
        if (areabeens.size() == 0) {
            program_name_count = 1;
        } else {
            program_name_count = ProgramNameItemManager.getSahrePreference(getActivity());
        }
        mProgramAdapter = new ProgramAdapter(getActivity(), areabeens);
        mProgramAdapter.setRemoveListener(new ProgramAdapter.OnRemoveListener() {
            @Override
            public void onRemoveItem(int position) {
                // 删除按钮的回调，注意也可以放在adapter里面处理
                areabeens = new AreabeanDao(getActivity()).getListAll();
                new AreabeanDao(getActivity()).delete(areabeens.get(position).getId());
                areabeens.remove(position);
                handler.sendEmptyMessage(0);
                List<String> filePath = new ArrayList<>();
                String fileTextPath = Environment.getExternalStorageDirectory().toString() + File.separator
                        + "textImage";
                filePath.add(fileTextPath);
                String fileImagePath = Environment.getExternalStorageDirectory().toString() + File.separator
                        + "EQImage";
                filePath.add(fileImagePath);
                String fileVedioPath = Environment.getExternalStorageDirectory().toString() + File.separator
                        + "EQVedio";
                filePath.add(fileVedioPath);
                String PROGRAME_ROOT = Environment
                        .getExternalStorageDirectory()
                        .getAbsolutePath() + "/EQPrograme/";
                filePath.add(PROGRAME_ROOT);
                for (int i = 0; i < filePath.size(); i++) {
                    File fileAll = new File(filePath.get(i));
                    if (!fileAll.exists()) {
                        fileAll.mkdir();
                    }
                    FileUtils.deleteDir(fileAll.getPath());
                }
                mListView.slideBack();
            }
        });
        mProgramAdapter.setListener(new ProgramAdapter.OnListener() {
            @Override
            public void onClickListener(int position) {
                areabeens = new AreabeanDao(getActivity()).getListAll();
                mAreabean = areabeens.get(0);
                updataArea = position;
                newOrupdata = false;
                if (addPopWindow == null) {
                    addPopWindow = new CustomPopWindow(getActivity(), R.id.iamge);
                    addPopWindow.setView(getPopWindowView(), 0.8f, 0.50f);
                }
                addPopWindow.showPopupWindow(mImageView);
                program_name.setText(mAreabean.getName() + "");
                program_type.setText(mAreabean.getEquitType());
                program_height.setText(mAreabean.getWindowHeight() + "");
                program_width.setText(mAreabean.getWindowWidth() + "");
                program_ip.setText(mAreabean.getEquitTp());
            }
        });

        mListView.setAdapter(mProgramAdapter);
    }

    @Override
    public void onClick(View view) {
        mAreabean = new Areabean();
        switch (view.getId()) {
            case R.id.iamge:
                newOrupdata = true;
                if (addPopWindow == null) {
                    addPopWindow = new CustomPopWindow(getActivity(), R.id.iamge);
                    addPopWindow.setView(getPopWindowView(), 0.8f, 0.50f);
                }
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.list_item:
                break;
            case R.id.progeam_type:
                if (customTypeWindow == null) {
                    customTypeWindow = new CustomTypeWindow(getActivity(), R.id.progeam_type);
                    customTypeWindow.setView(getTypeWindowView(), 0.8f, 0.5f);
                }
                addPopWindow.dismiss();
                customTypeWindow.showPopupWindow(mImageView);

                break;
            case R.id.btn_3:
                btn_3.setEnabled(false);
                btn_A1.setEnabled(true);
                btn_A2.setEnabled(true);
                btn_A3.setEnabled(true);
                btn_A2L.setEnabled(true);
                btn_A3L.setEnabled(true);
                btn_Q1.setEnabled(true);
                btn_Q2.setEnabled(true);
                btn_Q3.setEnabled(true);
                btn_Q4.setEnabled(true);
                btn_Q3L.setEnabled(true);
                btn_Q4L.setEnabled(true);
                program_type.setText("3");
                program_width.setText("640");
                program_height.setText("480");
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.btn_A1:
                btn_3.setEnabled(true);
                btn_A1.setEnabled(false);
                btn_A2.setEnabled(true);
                btn_A3.setEnabled(true);
                btn_A2L.setEnabled(true);
                btn_A3L.setEnabled(true);
                btn_Q1.setEnabled(true);
                btn_Q2.setEnabled(true);
                btn_Q3.setEnabled(true);
                btn_Q4.setEnabled(true);
                btn_Q3L.setEnabled(true);
                btn_Q4L.setEnabled(true);
                program_type.setText("A1");
                program_width.setText("384");
                program_height.setText("128");
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.btn_A2:
                btn_3.setEnabled(true);
                btn_A1.setEnabled(true);
                btn_A2.setEnabled(false);
                btn_A3.setEnabled(true);
                btn_A2L.setEnabled(true);
                btn_A3L.setEnabled(true);
                btn_Q1.setEnabled(true);
                btn_Q2.setEnabled(true);
                btn_Q3.setEnabled(true);
                btn_Q4.setEnabled(true);
                btn_Q3L.setEnabled(true);
                btn_Q4L.setEnabled(true);
                program_type.setText("A2");
                program_width.setText("512");
                program_height.setText("256");
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.btn_A3:
                btn_3.setEnabled(true);
                btn_A1.setEnabled(true);
                btn_A2.setEnabled(true);
                btn_A3.setEnabled(false);
                btn_A2L.setEnabled(true);
                btn_A3L.setEnabled(true);
                btn_Q1.setEnabled(true);
                btn_Q2.setEnabled(true);
                btn_Q3.setEnabled(true);
                btn_Q4.setEnabled(true);
                btn_Q3L.setEnabled(true);
                btn_Q4L.setEnabled(true);
                program_type.setText("A3");
                program_width.setText("640");
                program_height.setText("480");
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.btn_A2L:
                btn_3.setEnabled(true);
                btn_A1.setEnabled(true);
                btn_A2.setEnabled(true);
                btn_A3.setEnabled(true);
                btn_A2L.setEnabled(false);
                btn_A3L.setEnabled(true);
                btn_Q1.setEnabled(true);
                btn_Q2.setEnabled(true);
                btn_Q3.setEnabled(true);
                btn_Q4.setEnabled(true);
                btn_Q3L.setEnabled(true);
                btn_Q4L.setEnabled(true);
                program_type.setText("A2L");
                program_width.setText("1024");
                program_height.setText("128");
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.btn_A3L:
                btn_3.setEnabled(true);
                btn_A1.setEnabled(true);
                btn_A2.setEnabled(true);
                btn_A3.setEnabled(true);
                btn_A2L.setEnabled(true);
                btn_A3L.setEnabled(false);
                btn_Q1.setEnabled(true);
                btn_Q2.setEnabled(true);
                btn_Q3.setEnabled(true);
                btn_Q4.setEnabled(true);
                btn_Q3L.setEnabled(true);
                btn_Q4L.setEnabled(true);
                program_type.setText("A3L");
                program_width.setText("2048");
                program_height.setText("128");
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.btn_Q1:
                btn_3.setEnabled(true);
                btn_A1.setEnabled(true);
                btn_A2.setEnabled(true);
                btn_A3.setEnabled(true);
                btn_A2L.setEnabled(true);
                btn_A3L.setEnabled(true);
                btn_Q1.setEnabled(false);
                btn_Q2.setEnabled(true);
                btn_Q3.setEnabled(true);
                btn_Q4.setEnabled(true);
                btn_Q3L.setEnabled(true);
                btn_Q4L.setEnabled(true);
                program_type.setText("Q1");
                program_width.setText("640");
                program_height.setText("480");
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.btn_Q2:
                btn_3.setEnabled(true);
                btn_A1.setEnabled(true);
                btn_A2.setEnabled(true);
                btn_A3.setEnabled(true);
                btn_A2L.setEnabled(true);
                btn_A3L.setEnabled(true);
                btn_Q1.setEnabled(false);
                btn_Q2.setEnabled(true);
                btn_Q3.setEnabled(true);
                btn_Q4.setEnabled(true);
                btn_Q3L.setEnabled(true);
                btn_Q4L.setEnabled(true);
                program_type.setText("Q2");
                program_width.setText("800");
                program_height.setText("600");
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.btn_Q3:
                btn_3.setEnabled(true);
                btn_A1.setEnabled(true);
                btn_A2.setEnabled(true);
                btn_A3.setEnabled(true);
                btn_A2L.setEnabled(true);
                btn_A3L.setEnabled(true);
                btn_Q1.setEnabled(true);
                btn_Q2.setEnabled(true);
                btn_Q3.setEnabled(false);
                btn_Q4.setEnabled(true);
                btn_Q3L.setEnabled(true);
                btn_Q4L.setEnabled(true);
                program_type.setText("Q3");
                program_width.setText("1280");
                program_height.setText("800");
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.btn_Q4:
                btn_3.setEnabled(true);
                btn_A1.setEnabled(true);
                btn_A2.setEnabled(true);
                btn_A3.setEnabled(true);
                btn_A2L.setEnabled(true);
                btn_A3L.setEnabled(true);
                btn_Q1.setEnabled(true);
                btn_Q2.setEnabled(true);
                btn_Q3.setEnabled(true);
                btn_Q4.setEnabled(false);
                btn_Q3L.setEnabled(true);
                btn_Q4L.setEnabled(true);
                program_type.setText("Q4");
                program_width.setText("1920");
                program_height.setText("1024");
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.btn_Q3L:
                btn_3.setEnabled(true);
                btn_A1.setEnabled(true);
                btn_A2.setEnabled(true);
                btn_A3.setEnabled(true);
                btn_A2L.setEnabled(true);
                btn_A3L.setEnabled(true);
                btn_Q1.setEnabled(true);
                btn_Q2.setEnabled(true);
                btn_Q3.setEnabled(true);
                btn_Q4.setEnabled(true);
                btn_Q3L.setEnabled(false);
                btn_Q4L.setEnabled(true);
                program_type.setText("Q3L");
                program_width.setText("2048");
                program_height.setText("384");
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.btn_Q4L:
                btn_3.setEnabled(true);
                btn_A1.setEnabled(true);
                btn_A2.setEnabled(true);
                btn_A3.setEnabled(true);
                btn_A2L.setEnabled(true);
                btn_A3L.setEnabled(true);
                btn_Q1.setEnabled(true);
                btn_Q2.setEnabled(true);
                btn_Q3.setEnabled(true);
                btn_Q4.setEnabled(true);
                btn_Q3L.setEnabled(true);
                btn_Q4L.setEnabled(false);
                program_type.setText("Q4L");
                program_width.setText("7680");
                program_height.setText("256");
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
        }
    }

    //转换成DNS格式
    private String intToIp(int paramInt) {
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "." + (0xFF & paramInt >> 16) + "."
                + (0xFF & paramInt >> 24);
    }

    /**
     * 加载节目名称弹出窗体布局
     */
    public View getPopWindowView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.add, null);
        program_name = (EditText) view.findViewById(R.id.progeam_name);
        program_type = (TextView) view.findViewById(R.id.progeam_type);
        program_width = (EditText) view.findViewById(R.id.progeam_width);
        program_height = (EditText) view.findViewById(R.id.progeam_height);
        program_ip = (EditText) view.findViewById(R.id.progeam_ip);
        progeam_duan = (EditText) view.findViewById(R.id.progeam_duan);
        program_btn = (Button) view.findViewById(R.id.progeam_btn);
        program_ip.setText("192.168.43.1");
        progeam_duan.setText(5050 + "");
        program_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newOrupdata) {
                    mAreabean = new Areabean();
                    if (program_type.getText().equals("")) {
                        Toast.makeText(getActivity(), "请选择设备类型！", Toast.LENGTH_SHORT).show();
                    } else {
                        String program = program_name.getText().toString();
                        int wdith = Integer.parseInt(program_width.getText().toString());
                        int height = Integer.parseInt(program_height.getText().toString());
                        Log.d("............", "program.........................." + program);
                        mAreabean.setName(program);
                        mAreabean.setWindowWidth(wdith);
                        mAreabean.setWindowHeight(height);
                        mAreabean.setEquitType(program_type.getText().toString());
                        mAreabean.setEquitTp(program_ip.getText().toString());
                        ip = program_ip.getText().toString();
                        areabeens.add(mAreabean);
                        new AreabeanDao(getActivity()).add(mAreabean);
                        windowWidth = wdith;
                        windowHeight = height;
                        WindowSizeManager.setSharedPreference(getActivity(), windowWidth * 2, windowHeight * 2);
                        program_name_count++;
                        ProgramNameItemManager.setSharedPreference(getActivity(), program_name_count);
                        handler.sendEmptyMessage(0);
                        addPopWindow.dismiss();
                    }
                } else {
                    mAreabean = new AreabeanDao(getActivity()).get(areabeens.get(updataArea).getId());
                    String program = program_name.getText().toString();
                    int wdith = Integer.parseInt(program_width.getText().toString());
                    int height = Integer.parseInt(program_height.getText().toString());
                    Log.d("............", "program.........................." + program);
                    String s = String.valueOf(program_name_count);
                    mAreabean.setNum(s);
                    mAreabean.setName(program);
                    mAreabean.setWindowWidth(wdith);
                    mAreabean.setWindowHeight(height);
                    mAreabean.setEquitType(program_type.getText().toString());
                    ip = program_ip.getText().toString();
                    mAreabean.setEquitTp(program_ip.getText().toString());
                    areabeens.remove(updataArea);
                    areabeens.add(mAreabean);
                    new AreabeanDao(getActivity()).update(mAreabean);
                    windowWidth = wdith;
                    windowHeight = height;
                    WindowSizeManager.setSharedPreference(getActivity(), windowWidth, windowHeight);
                    program_name_count++;
                    ProgramNameItemManager.setSharedPreference(getActivity(), program_name_count);
                    handler.sendEmptyMessage(0);
                    mListView.slideBack();
                    addPopWindow.dismiss();
                }
            }

        });
        program_type.setOnClickListener(this);
        return view;
    }

    /**
     * 加载节目类型弹出窗体布局
     */
    private View getTypeWindowView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.program_type, null);
        btn_3 = (Button) view.findViewById(R.id.btn_3);
        btn_A1 = (Button) view.findViewById(R.id.btn_A1);
        btn_A2 = (Button) view.findViewById(R.id.btn_A2);
        btn_A3 = (Button) view.findViewById(R.id.btn_A3);
        btn_A2L = (Button) view.findViewById(R.id.btn_A2L);
        btn_A3L = (Button) view.findViewById(R.id.btn_A3L);
        btn_Q1 = (Button) view.findViewById(R.id.btn_Q1);
        btn_Q2 = (Button) view.findViewById(R.id.btn_Q2);
        btn_Q3 = (Button) view.findViewById(R.id.btn_Q3);
        btn_Q4 = (Button) view.findViewById(R.id.btn_Q4);
        btn_Q3L = (Button) view.findViewById(R.id.btn_Q3L);
        btn_Q4L = (Button) view.findViewById(R.id.btn_Q4L);

        btn_3.setOnClickListener(this);
        btn_A1.setOnClickListener(this);
        btn_A2.setOnClickListener(this);
        btn_A3.setOnClickListener(this);
        btn_A2L.setOnClickListener(this);
        btn_A3L.setOnClickListener(this);
        btn_Q1.setOnClickListener(this);
        btn_Q2.setOnClickListener(this);
        btn_Q3.setOnClickListener(this);
        btn_Q4.setOnClickListener(this);
        btn_Q3L.setOnClickListener(this);
        btn_Q4L.setOnClickListener(this);
        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e("onTouch==========", "onTouch");
        if (mListView != null) {
            mListView.mode = mListView.MODE_RIGHT;
        }

        return false;
    }
}

package com.eq.EQSuperPlayer.fargament;

import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.eq.EQSuperPlayer.view.SlidingItemListView;
import com.eq.EQSuperPlayer.utils.ProgramNameItemManager;
import com.eq.EQSuperPlayer.utils.WindowSizeManager;

import java.util.List;


public class ProgramFragment extends Fragment implements View.OnClickListener {
    private EditText program_name;
    private TextView program_type;
    private EditText program_width;
    private EditText program_height;
    private EditText program_ip;
    private EditText progeam_duan;
    private Button program_btn;
    private ImageView mImageView;
    private CustomPopWindow addPopWindow;
    private SlidingItemListView mListView;
    private ProgramAdapter mProgramAdapter;
    private List<Areabean> areabeens;
    private Areabean mAreabean;
    private int program_name_count;   //自动命名的数字
    private DhcpInfo dhcpInfo;
    private CustomTypeWindow customTypeWindow;
    private Button btn_Dx;
    private Button btn_Cx;
    private Button btn_A60x;
    private Button btn_Cxi;
    private Button btn_AX0;
    private Button btn_Dx0;
    private Button btn_Cx0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_program, container, false);
        mImageView = (ImageView) view.findViewById(R.id.iamge);
        mListView = (SlidingItemListView) view.findViewById(R.id.program_list);
        mListView.setEmptyView(view.findViewById(R.id.myText));
        mImageView.setOnClickListener(this);
        mProgramAdapter = new ProgramAdapter(getActivity(), areabeens);
        mListView.setAdapter(mProgramAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(",,,,,,,,", "你点击了id为" + areabeens.get(position).getId() + "itme");
                Intent intent = new Intent(getActivity(), ProgramActivity.class);
                ProgramActivity.program_id = areabeens.get(position).getId();
                getActivity().startActivity(intent);
            }
        });

        //获取WiFi信息
        WifiManager my_wifiManager = ((WifiManager) getActivity().getSystemService(getActivity().WIFI_SERVICE));
        dhcpInfo = my_wifiManager.getDhcpInfo();
        String IP = intToIp(dhcpInfo.dns1);
        ProgramAdapter.ipAressd = IP;
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
            updateList();
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
                Toast.makeText(getActivity(), "点击了" + position + "项的删除",
                        Toast.LENGTH_SHORT).show();
                new AreabeanDao(getActivity()).delete(areabeens.get(position).getId());
                areabeens.remove(position);
                mProgramAdapter.notifyDataSetChanged();
            }
        });
        mListView.setAdapter(mProgramAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iamge:
                if (addPopWindow == null) {

                    addPopWindow = new CustomPopWindow(getActivity(), R.id.iamge);
                    addPopWindow.setView(getPopWindowView(), 1.0f, 0.70f);
                }
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.list_item:

                break;
            case R.id.progeam_type:
                if (customTypeWindow == null) {
                    customTypeWindow = new CustomTypeWindow(getActivity(), R.id.progeam_type);
                    customTypeWindow.setView(getTypeWindowView(), 1.0f, 0.28f);
                }
                addPopWindow.dismiss();
                customTypeWindow.showPopupWindow(mImageView);

                break;
            case R.id.btn_Cx:
                btn_Cx.setEnabled(false);
                btn_Dx.setEnabled(true);
                btn_A60x.setEnabled(true);
                btn_AX0.setEnabled(true);
                btn_Cx0.setEnabled(true);
                btn_Cxi.setEnabled(true);
                btn_Dx0.setEnabled(true);
                program_type.setText("Cx");
                program_width.setText("2048");
                program_height.setText("2048");
                int windowWidth = 2048;
                int windowHeight = 2048;
                WindowSizeManager.setSharedPreference(getActivity(), windowWidth, windowHeight);
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.btn_Dx:
                btn_Cx.setEnabled(true);
                btn_Dx.setEnabled(false);
                btn_A60x.setEnabled(true);
                btn_AX0.setEnabled(true);
                btn_Cx0.setEnabled(true);
                btn_Cxi.setEnabled(true);
                btn_Dx0.setEnabled(true);
                int windowWidth2 = 2048;
                int windowHeight2 = 2048;
                WindowSizeManager.setSharedPreference(getActivity(), windowWidth2, windowHeight2);
                program_type.setText("Dx");
                program_width.setText("2048");
                program_height.setText("2048");
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.btn_A60x:
                btn_Cx.setEnabled(true);
                btn_Dx.setEnabled(true);
                btn_A60x.setEnabled(false);
                btn_AX0.setEnabled(true);
                btn_Cx0.setEnabled(true);
                btn_Cxi.setEnabled(true);
                btn_Dx0.setEnabled(true);
                int windowWidth1 = 1920;
                int windowHeight1 = 1080;
                WindowSizeManager.setSharedPreference(getActivity(), windowWidth1, windowHeight1);
                program_type.setText("A60x");
                program_width.setText("1920");
                program_height.setText("1080");
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.btn_Cxi:
                btn_Cx.setEnabled(true);
                btn_Dx.setEnabled(true);
                btn_A60x.setEnabled(true);
                btn_AX0.setEnabled(true);
                btn_Cx0.setEnabled(true);
                btn_Cxi.setEnabled(false);
                btn_Dx0.setEnabled(true);
                int windowWidth3 = 2048;
                int windowHeight3 = 2048;
                WindowSizeManager.setSharedPreference(getActivity(), windowWidth3, windowHeight3);
                program_type.setText("Cxi");
                program_width.setText("2048");
                program_height.setText("2048");
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.btn_AX0:
                btn_Cx.setEnabled(true);
                btn_Dx.setEnabled(true);
                btn_A60x.setEnabled(true);
                btn_AX0.setEnabled(false);
                btn_Cx0.setEnabled(true);
                btn_Cxi.setEnabled(true);
                btn_Dx0.setEnabled(true);
                int windowWidth4 = 1024;
                int windowHeight4 = 512;
                WindowSizeManager.setSharedPreference(getActivity(), windowWidth4, windowHeight4);
                program_type.setText("AX0");
                program_width.setText("1024");
                program_height.setText("512");
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.btn_Dx0:
                btn_Cx.setEnabled(true);
                btn_Dx.setEnabled(true);
                btn_A60x.setEnabled(true);
                btn_AX0.setEnabled(true);
                btn_Cx0.setEnabled(true);
                btn_Cxi.setEnabled(true);
                btn_Dx0.setEnabled(false);
                int windowWidth5 = 1024;
                int windowHeight5 = 64;
                WindowSizeManager.setSharedPreference(getActivity(), windowWidth5, windowHeight5);
                program_type.setText("Dx0");
                program_width.setText("1024");
                program_height.setText("64");
                customTypeWindow.dismiss();
                addPopWindow.showPopupWindow(mImageView);
                break;
            case R.id.btn_Cx0:
                btn_Cx.setEnabled(true);
                btn_Dx.setEnabled(true);
                btn_A60x.setEnabled(true);
                btn_AX0.setEnabled(true);
                btn_Cx0.setEnabled(false);
                btn_Cxi.setEnabled(true);
                btn_Dx0.setEnabled(true);
                int windowWidth6 = 640;
                int windowHeight6 = 480;
                WindowSizeManager.setSharedPreference(getActivity(), windowWidth6, windowHeight6);
                program_type.setText("Dx");
                program_width.setText("640");
                program_height.setText("480");
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
    private View getPopWindowView() {

        String IP = intToIp(dhcpInfo.dns1);
        View view = getActivity().getLayoutInflater().inflate(R.layout.add, null);
        program_name = (EditText) view.findViewById(R.id.progeam_name);
        program_type = (TextView) view.findViewById(R.id.progeam_type);
        program_width = (EditText) view.findViewById(R.id.progeam_width);
        program_height = (EditText) view.findViewById(R.id.progeam_height);
        program_ip = (EditText) view.findViewById(R.id.progeam_ip);
        progeam_duan = (EditText) view.findViewById(R.id.progeam_duan);
        program_btn = (Button) view.findViewById(R.id.progeam_btn);
        program_ip.setText(IP);
        progeam_duan.setText("5005");
        program_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!program_name.getText().toString().equals("")) {
                    String program = program_name.getText().toString();
                    int wdith = Integer.parseInt(program_width.getText().toString());
                    int height = Integer.parseInt(program_height.getText().toString());
                    Log.d("............", "program.........................." + program);
                    mAreabean = new Areabean();
                    String s = String.valueOf(program_name_count);
                    mAreabean.setNum(s);
                    mAreabean.setName(program);
                    mAreabean.setWindowWidth(wdith);
                    mAreabean.setWindowHeight(height);
                    areabeens.add(mAreabean);
                    new AreabeanDao(getActivity()).add(mAreabean);
                    Log.d("............", "mAreabean.........................." + mAreabean.toString());
                    mProgramAdapter.notifyDataSetChanged();
                    program_name_count++;
                    ProgramNameItemManager.setSharedPreference(getActivity(), program_name_count);
                    addPopWindow.dismiss();
                } else {
                    Toast.makeText(getActivity(), "节目名称不能为空！", Toast.LENGTH_SHORT).show();
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
        btn_Dx = (Button) view.findViewById(R.id.btn_Dx);
        btn_Cx = (Button) view.findViewById(R.id.btn_Cx);
        btn_A60x = (Button) view.findViewById(R.id.btn_A60x);
        btn_Cxi = (Button) view.findViewById(R.id.btn_Cxi);
        btn_AX0 = (Button) view.findViewById(R.id.btn_AX0);
        btn_Dx0 = (Button) view.findViewById(R.id.btn_Dx0);
        btn_Cx0 = (Button) view.findViewById(R.id.btn_Cx0);

        btn_Dx.setOnClickListener(this);
        btn_Cx.setOnClickListener(this);
        btn_A60x.setOnClickListener(this);
        btn_Cxi.setOnClickListener(this);
        btn_AX0.setOnClickListener(this);
        btn_Dx0.setOnClickListener(this);
        btn_Cx0.setOnClickListener(this);
        return view;
    }

}

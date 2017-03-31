package com.eq.EQSuperPlayer.fargament;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.adapter.ProgramAdapter;
import com.eq.EQSuperPlayer.adapter.WifiListAdapter;
import com.eq.EQSuperPlayer.communication.ConnectControlCard;
import com.eq.EQSuperPlayer.utils.NetWorkCheck;
import com.eq.EQSuperPlayer.utils.WifiPswDialog;

import java.util.ArrayList;
import java.util.List;

public class EquitmentFragment extends Fragment implements OnClickListener {
    private Switch wifiSwitch; // wifi开关
    private TextView wifiName; // 显示 已连接wifi SSID 的UI
    private String wifiname = null; // wifi 名字
    private ListView wifiList; // wifi 列表
    private Button searchWifi;// 点击搜索附近wifi
    private NetWorkCheck mNetWorkCheck; // WIFI连接类
    private ProgressDialog proDialog; // 进度条
    private WifiListAdapter adapter = null; //
    public List<ScanResult> EqWifiList = null;
    private DhcpInfo dhcpInfo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equitment, container,false);
        initView(view);
        initeData();
    return view;
}


    private void openWifi() { // 打开wifi开关
        mNetWorkCheck.mWifiManager.setWifiEnabled(true);
        proDialog = android.app.ProgressDialog.show(getActivity(), null,
                getResources().getString(R.string.hint_start_wifi));
        mNetWorkCheck.mWifiManager.setWifiEnabled(true);
        Thread thread = new Thread() {
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        if (mNetWorkCheck.getWifiList() != null) {
                            handler.sendEmptyMessage(1);
                            break;
                        }
                    }

                } catch (Exception e) {
                    proDialog.cancel();
                    e.printStackTrace();
                }

            }
        };
        thread.start();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wifiswitch:
                if (!wifiSwitch.isChecked()) { // 关闭wifi 开关
                    mNetWorkCheck.closeWifi();
                    wifiName.setText(getResources().getString(R.string.hint_no_connection)); // 更改提示文字
                    if (adapter != null) {
                        EqWifiList.clear();
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    openWifi();
                }

                break;
            case R.id.searchWifi:
                loadDada();
                break;

            default:
                break;
        }
    }

    /**
     * 检测wifi是否连接
     *
     * @author Administrator
     */
    class isWifi implements Runnable {

        public isWifi() {
            proDialog = android.app.ProgressDialog.show(getActivity(),
                    null, getResources().getString(R.string.hint_connection_wifi));
        }

        @Override
        public void run() {
            int time = 0; // 超时限定

            try {

                while (true) {
                    Thread.sleep(100);
                    time += 100;
                    if (mNetWorkCheck.isWifiConnected(getActivity())) {
                        handler.sendEmptyMessage(2);
                        break;
                    }
                    if (time >= 3000) {
                        handler.sendEmptyMessage(3);
                        break;
                    }
                }
            } catch (InterruptedException e) {
                proDialog.cancel();
                e.printStackTrace();
            }

        }

    }
    /**
     * handle
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    wifiSwitch.setChecked(true);
                    loadDada();
                    proDialog.cancel();
                    break;

                case 2:
                    //获取WiFi信息
                    WifiManager my_wifiManager = ((WifiManager) getActivity().getSystemService(getActivity().WIFI_SERVICE));
                    dhcpInfo = my_wifiManager.getDhcpInfo();
                    String IP = intToIp(dhcpInfo.dns1);
                    ProgramAdapter.ipAressd = IP;
                    ConnectControlCard.HOSTAddress = IP;
                    proDialog.cancel();
                    updateWifiName();
                    Toast.makeText(getActivity(),getResources().getString(R.string.hint_connection_success),Toast.LENGTH_SHORT).show();
                    break;

                case 3:
                    proDialog.cancel();
                    Toast.makeText(getActivity(),getResources().getString(R.string.hint_connection_failure),Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
    };


    /**
     * 连上wifi后 更改提示文字
     */

    private void updateWifiName() {
        if (mNetWorkCheck.isWifiConnected(getActivity())) {
            if (wifiname == null) {
                wifiname = mNetWorkCheck.getSSID().replaceAll("\"", "");
            }
            wifiName.setText(getResources().getString(R.string.hint_connection_to)
                    + wifiname);


        }
    }
    //转换成DNS格式
    private String intToIp(int paramInt) {
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "." + (0xFF & paramInt >> 16) + "."
                + (0xFF & paramInt >> 24);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void initeData() { // 数据加载
        updateWifiName();
        if (mNetWorkCheck.checkState() != 3) { // WIFI状态 !=3 没有打开wifi
            openWifi();
        } else {
            wifiSwitch.setChecked(true);
            loadDada();
        }

    }

    private void initView(View view) {
        mNetWorkCheck = new NetWorkCheck(getActivity());
        EqWifiList = new ArrayList<ScanResult>();
        wifiSwitch = (Switch) view.findViewById(R.id.wifiswitch);
        wifiSwitch.setOnClickListener(this);
        wifiName = (TextView) view.findViewById(R.id.wifi_name);
        wifiList = (ListView) view.findViewById(R.id.wifi_listview);
        searchWifi = (Button) view.findViewById(R.id.searchWifi);
        searchWifi.setOnClickListener(this);
        wifiList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                wifiname = EqWifiList.get(position).SSID.toString().replaceAll(
                        "\"", "");
                if (EqWifiList.get(position).capabilities.indexOf("WPA") == -1) { // 不严谨的加密方式判定
                    mNetWorkCheck.addNetwork(mNetWorkCheck.CreateWifiInfo(
                            EqWifiList.get(position).SSID, null, 1));
                    new Thread(new isWifi()).start();

                } else { // 需要输入密码连接
                    final int count = position;
                    final WifiPswDialog dialog = new WifiPswDialog(
                            getActivity(), EqWifiList.get(position).SSID
                            .toString().replaceAll("\"", ""));
                    dialog.setOnPositiveListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            mNetWorkCheck.addNetwork(mNetWorkCheck
                                    .CreateWifiInfo(EqWifiList.get(count).SSID,
                                            dialog.getEditText(), 3));
                            new Thread(new isWifi()).start();
                        }
                    });
                    dialog.setOnNegativeListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }

            }
        });
    }

    /**
     * 获取wifi列表，加载过滤
     */
    public static final String eq = "EQ"; // wifi是否属于EQ的判断字符

    private void loadDada() {
        if (EqWifiList != null) {
            EqWifiList.clear();
        }
        mNetWorkCheck.startScan();

        if (mNetWorkCheck.mWifiList.size() == 0) {

            mNetWorkCheck.startScan();
        }

        if (mNetWorkCheck.mWifiList.size() == 0) {
            Toast.makeText(getActivity(),getResources().getString(R.string.hint_control_wifi_failure) ,Toast.LENGTH_SHORT).show();

        } else { // 过滤wifi列表
            for (int i = 0; i < mNetWorkCheck.mWifiList.size(); i++) {

                if (mNetWorkCheck.mWifiList.get(i).SSID.replaceAll("\"", "")
                        .length() <= 2) {
                    mNetWorkCheck.mWifiList.remove(i);
                }
            }
            for (int i = 0; i < mNetWorkCheck.mWifiList.size(); i++) {
                if (mNetWorkCheck.mWifiList.get(i).SSID.length() > 2
                        && mNetWorkCheck.mWifiList.get(i).SSID
                        .replaceAll("\"", "").substring(0, 2)
                        .equals(eq)) {
                    Log.d("SSID", mNetWorkCheck.mWifiList.get(i).SSID + " "
                            + mNetWorkCheck.mWifiList.get(i).capabilities);
                    EqWifiList.add(mNetWorkCheck.mWifiList.get(i));
                }
            }
            if (adapter != null) {
                adapter.notifyDataSetChanged();

            } else {

                if (EqWifiList.size() != 0) { // 确认搜索到EQ wifi
                    adapter = new WifiListAdapter(getActivity(),
                            EqWifiList);
                    wifiList.setAdapter(adapter);

                } else {

                }
            }

        }

    }

}
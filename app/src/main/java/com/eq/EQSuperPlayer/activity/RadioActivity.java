package com.eq.EQSuperPlayer.activity;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.adapter.RadioAdapter;
import com.eq.EQSuperPlayer.bean.WiFiBean;
import com.eq.EQSuperPlayer.communication.FindScreenThread;
import com.eq.EQSuperPlayer.communication.InterfaceConnect;
import com.eq.EQSuperPlayer.communication.SendPacket;
import com.eq.EQSuperPlayer.dao.WifiDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RadioActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RadioAdapter radioAdapter;
    private static final int REFRESH_COMPLETE = 0X110;
    private SwipeRefreshLayout mSwipeLayout;
    private ListView mListView;
    private List<WiFiBean> wiFiBeens = new ArrayList<>();
    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case REFRESH_COMPLETE:
                    radioAdapter.notifyDataSetChanged();
                    mSwipeLayout.setRefreshing(false);
                    break;

            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        mListView = (ListView) findViewById(R.id.id_listview);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent,R.color.transparent_blud, R.color.lime);
        radioAdapter = new RadioAdapter(wiFiBeens,this);
        mListView.setAdapter(radioAdapter);
    }

    @Override
    public void onRefresh() {
        FindScreenThread findScreenThread = new FindScreenThread(new InterfaceConnect() {
            @Override
            public void success(byte[] result) {
              wiFiBeens = new WifiDao(RadioActivity.this).getListAll();
              mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
            }

            @Override
            public void failure(int stateCode) {

            }
            @Override
            public void dataSuccess(String str) {

            }
        },this);
        new Thread(findScreenThread).start();

    }
}

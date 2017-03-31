package com.eq.EQSuperPlayer.SlidingMenuActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.activity.MainActivity;
import com.eq.EQSuperPlayer.communication.ConnectControlCard;
import com.eq.EQSuperPlayer.communication.InterfaceConnect;
import com.eq.EQSuperPlayer.communication.SendPacket;

import java.util.ArrayList;
import java.util.List;

public class OpenActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView open_Button;
    private Button button_open;
    private Button button_guan;
    private ConnectControlCard ccc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        initView();
    }

    private void initView() {
        open_Button = (ImageView) findViewById(R.id.open_Button);
        button_open= (Button) findViewById(R.id.btn_open);
        button_guan= (Button) findViewById(R.id.btn_guan);
        open_Button.setOnClickListener(this);
        button_open.setOnClickListener(this);
        button_guan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_Button:
           finish();
                break;
            case R.id.btn_open:
                button_open.setEnabled(false);
                button_guan.setEnabled(true);
                List<byte[]> openPaks = new ArrayList<>();
                byte[] openPak = SendPacket.openScreen();
                openPaks.add(openPak);
                ccc = new ConnectControlCard(openPaks, new InterfaceConnect() {
                    @Override
                    public void success(byte[] result) {
//                        Toast.makeText(OpenActivity.this,"开启屏幕成功！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(int stateCode) {
//                        Toast.makeText(OpenActivity.this,"开启屏幕失败！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void dataSuccess(String str) {

                    }
                });
                new Thread(ccc).start();
                break;
            case R.id.btn_guan:
                button_open.setEnabled(true);
                button_guan.setEnabled(false);
                List<byte[]> guanPaks = new ArrayList<>();
                byte[] guanPak = SendPacket.guanScreen();
                guanPaks.add(guanPak);
                ccc = new ConnectControlCard(guanPaks, new InterfaceConnect() {
                    @Override
                    public void success(byte[] result) {
//                        Toast.makeText(OpenActivity.this,"关闭屏幕成功！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(int stateCode) {
//                        Toast.makeText(OpenActivity.this,"关闭屏幕失败！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void dataSuccess(String str) {

                    }
                });
                new Thread(ccc).start();
                break;
        }
    }
}
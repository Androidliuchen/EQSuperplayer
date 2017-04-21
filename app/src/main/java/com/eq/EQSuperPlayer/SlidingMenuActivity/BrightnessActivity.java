package com.eq.EQSuperPlayer.SlidingMenuActivity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.activity.MainActivity;
import com.eq.EQSuperPlayer.communication.ConnectControlCard;
import com.eq.EQSuperPlayer.communication.InterfaceConnect;
import com.eq.EQSuperPlayer.communication.SendPacket;

import java.util.ArrayList;
import java.util.List;

public class BrightnessActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView bright_Button;
    private Dialog builder;
    private LinearLayout brightness;   //亮度调节
    private TextView bright_text;   //选择的亮度值
    private SeekBar seekBar;
    private RelativeLayout bright_setting;//发送亮度设定
    private int brightness_int = 50;    // 亮度 默认值为100
    private ConnectControlCard ccc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brightness);
        initView();
    }

    private void initView() {
        bright_Button = (ImageView) findViewById(R.id.bright_Button);
        bright_Button.setOnClickListener(this);
        bright_text = (TextView) findViewById(R.id.bright_text);
        seekBar = (SeekBar) findViewById(R.id.bright_seekBar);
        bright_setting = (RelativeLayout) findViewById(R.id.bright_setting);
        bright_setting.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bright_text.setText(progress + "");
                brightness_int = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bright_Button:
                finish();
                break;
            case R.id.bright_setting:
//                List<byte[]> brightPaks = new ArrayList<>();
                byte[] brightPak = SendPacket.brightness(brightness_int);
//                brightPaks.add(brightPak);
                ccc = new ConnectControlCard(BrightnessActivity.this,brightPak, new InterfaceConnect() {
                    @Override
                    public void success(byte[] result) {
                    }

                    @Override
                    public void failure(int stateCode) {
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

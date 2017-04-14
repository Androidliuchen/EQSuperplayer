package com.eq.EQSuperPlayer.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.eq.EQSuperPlayer.R;

public class WelcomeActivity extends AppCompatActivity {
    private ImageView mImageView;
    private AnimationDrawable AniDraw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        mImageView = (ImageView) findViewById(R.id.ima_wel);
        AniDraw = (AnimationDrawable) mImageView.getDrawable();
        AniDraw.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
                finish();
            }
        }).start();
    }
}


package com.eq.EQSuperPlayer.SlidingMenuActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.eq.EQSuperPlayer.R;

public class OpenActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView open_Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        initView();
    }

    private void initView() {
        open_Button = (ImageView) findViewById(R.id.open_Button);
        open_Button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_Button:
                finish();
                break;
        }
    }
}
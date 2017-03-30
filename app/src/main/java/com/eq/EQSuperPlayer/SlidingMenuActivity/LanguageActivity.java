package com.eq.EQSuperPlayer.SlidingMenuActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.activity.MainActivity;

public class LanguageActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView language_Button;
    private RadioButton system_Button;
    private RadioButton ch_Button;
    private RadioButton en_Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        initView();
    }

    private void initView() {
        language_Button = (ImageView) findViewById(R.id.language_Button);
        system_Button = (RadioButton) findViewById(R.id.radioButton);
        ch_Button = (RadioButton) findViewById(R.id.radioButton2);
        en_Button = (RadioButton) findViewById(R.id.radioButton3);

        language_Button.setOnClickListener(this);
        system_Button.setOnClickListener(this);
        ch_Button.setOnClickListener(this);
        en_Button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.language_Button:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.radioButton://跟随系统语言
                system_Button.setChecked(true);
                break;
            case R.id.radioButton2://中文
                ch_Button.setChecked(true);
                break;
            case R.id.radioButton3://英文
                en_Button.setChecked(true);
                break;
        }
    }
}

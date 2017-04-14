package com.eq.EQSuperPlayer.SlidingMenuActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.utils.LanguageUtil;

import java.util.Locale;

public class LanguageActivity extends AppCompatActivity implements View.OnClickListener {
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

        switch (v.getId()) {
            case R.id.language_Button:
                finish();
                break;
            case R.id.radioButton://跟随系统语言
                system_Button.setChecked(true);
                ch_Button.setChecked(false);
                en_Button.setChecked(false);
                reloadLanguageAction();
                break;
            case R.id.radioButton2://中文
                ch_Button.setChecked(true);
                system_Button.setChecked(false);
                en_Button.setChecked(false);
                LanguageUtil.swithLanguage(
                        this, "zh-rCN");
                break;
            case R.id.radioButton3://英文
                en_Button.setChecked(true);
                ch_Button.setChecked(false);
                system_Button.setChecked(false);
                LanguageUtil.swithLanguage(
                        this, "en");
                break;
        }
    }

    public void reloadLanguageAction() {
        Locale locale = getResources().getConfiguration().locale;
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, null);
        getBaseContext().getResources().flushLayoutCache();
    }
}

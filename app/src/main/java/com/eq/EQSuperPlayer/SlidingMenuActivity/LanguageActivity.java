package com.eq.EQSuperPlayer.SlidingMenuActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.activity.MainActivity;

public class LanguageActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView language_Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        initView();
    }

    private void initView() {
        language_Button = (ImageView) findViewById(R.id.language_Button);
        language_Button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.language_Button:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}

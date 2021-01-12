package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

/**
 * NightActivity
 * 实现夜间模式
 */
public class NightActivity extends BaseActivity {
    private Button day;
    private Button night;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night);
    }

    public void modeDay(View v) {
        setEnableNightMode(false);
    }

    public void modeNight(View v) {
        setEnableNightMode(true);
    }
}

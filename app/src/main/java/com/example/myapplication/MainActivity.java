package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button mBtnLogin;
    private Button mBtnRegister;
    private EditText mTUserName;
    private EditText mTPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnLogin = findViewById(R.id.btn_1);
        mBtnRegister = findViewById(R.id.btn_2);
        mTUserName = findViewById(R.id.et_1);
        mTPassword = findViewById(R.id.et_2);
        mBtnLogin.setOnClickListener((View.OnClickListener) this);
        mBtnRegister.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View v) {
        //获取输入的信息
        String username = mTUserName.getText().toString();
        String password = mTPassword.getText().toString();
        String ok = "登陆成功";
        String fail = "密码错误，登陆失败";
        Intent intent = null;
    }
}

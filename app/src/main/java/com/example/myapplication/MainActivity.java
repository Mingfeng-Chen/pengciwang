package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mBtnLogin;
    private Button mBtnRegister;
    private EditText mEtUserName;
    private EditText mEtPassword;
    private TextView mTvForget;
    private TextView mTvFace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        mBtnLogin = findViewById(R.id.btn_1);
        mBtnRegister = findViewById(R.id.btn_2);
        mEtUserName = findViewById(R.id.et_1);
        mEtPassword = findViewById(R.id.et_2);
        mTvForget = findViewById(R.id.tv_1);
        mTvFace = findViewById(R.id.tv_2);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=mEtUserName.getText().toString();
                String password=mEtPassword.getText().toString();
                if(true){               //登录成功
                    Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=mEtUserName.getText().toString();
                String password=mEtPassword.getText().toString();
                if(true){               //注册成功
                    Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mTvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RetrieveActivity.class);
                startActivity(intent);
            }
        });
        mTvFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,FaceActivity.class);
                startActivity(intent);
            }
        });
    }
}

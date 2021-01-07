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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DBOpenHelper mDBOpenHelper;
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
        mDBOpenHelper=new DBOpenHelper(this);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=mEtUserName.getText().toString();
                String password=mEtPassword.getText().toString();
                if(username.isEmpty()){               //登录成功
                    Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    ArrayList<User> data = mDBOpenHelper.getAllData();
                    boolean match = false;
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (username.equals(user.getName()) && password.equals(user.getPassword())) {
                            match = true;
                            break;
                        } else {
                            match = false;
                        }
                    }
                    if (match) {
                        Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,RetrieveActivity.class);//跳转到背单词界面
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=mEtUserName.getText().toString();
                String password=mEtPassword.getText().toString();
                if(username.isEmpty()){               //登录成功
                    Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    ArrayList<User> data = mDBOpenHelper.getAllData();
                    boolean match = false;
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (username.equals(user.getName())) {
                            match = true;
                            break;
                        } else {
                            match = false;
                        }
                    }
                    if (match) {
                        Toast.makeText(getApplicationContext(), "用户名已存在", Toast.LENGTH_SHORT).show();
                    } else {
                        mDBOpenHelper.add(username, password);
                        Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,BindActivity.class);
                        startActivity(intent);
                    }
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

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Util.Utils;
import com.usts.englishlearning.activity.WelcomeActivity;

public class BindActivity extends AppCompatActivity {
    private Button mBtnBind;
    private EditText mEtTelephone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        mBtnBind=findViewById(R.id.btn_1);
        mEtTelephone=findViewById(R.id.et_1);
        mBtnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telephone=mEtTelephone.getText().toString();
                if(telephone.isEmpty()){
                    Toast.makeText(getApplicationContext(),"手机号不能为空",Toast.LENGTH_SHORT).show();
                }else if(!Utils.checkTel(telephone)){
                    Toast.makeText(getApplicationContext(),"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"绑定成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(BindActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Util.DBOpenHelper;
import com.usts.englishlearning.activity.ListActivity;
import com.usts.englishlearning.activity.PlanActivity;
import com.example.myapplication.dao.Constant;
import com.usts.englishlearning.config.ConfigData;
import com.usts.englishlearning.database.User;
import com.usts.englishlearning.database.UserConfig;

import org.json.JSONArray;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * MainActivity
 * 登录注册
 */
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
                    /*OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder().add("name", username).add("password",password).build();
                    Request request = new Request.Builder()
                            .url(Constant.GET)
                            .post(formBody)
                            .build();
                    try (Response response = okHttpClient.newCall(request).execute()) {
                        Looper.prepare();
                        if (Boolean.parseBoolean(response.body().string()))
                        {
                            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }
                        Looper.loop();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    LitePal.initialize(getApplicationContext());
                    List<User> users = LitePal.where("userName=?", username + "").find(User.class);
                    if (users.isEmpty()) {
                        User user = new User();
                        user.setUserName(username);;
                        // 测试
                        user.setUserMoney(0);
                        user.setUserWordNumber(0);
                        user.save();
                    }
                    List<UserConfig> userConfigs = LitePal.where("userId=?",1 + "").find(UserConfig.class);
                    if (userConfigs.isEmpty()) {
                        UserConfig userConfig = new UserConfig();
                        userConfig.setCurrentBookId(-1);
                        userConfig.save();
                    }
                    //ConfigData.setIsLogged(true);
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
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
                    /*OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder().add("name", username).add("password",password).build();
                    Request request = new Request.Builder()
                            .url(Constant.ADD)
                            .post(formBody)
                            .build();
                    try (Response response = okHttpClient.newCall(request).execute()) {
                        Looper.prepare();
                        if (Boolean.parseBoolean(response.body().string()))
                        {
                            Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
                        }
                        Looper.loop();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
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
                Intent intent=new Intent(MainActivity.this, PlanActivity.class);
                startActivity(intent);
            }
        });
    }
}

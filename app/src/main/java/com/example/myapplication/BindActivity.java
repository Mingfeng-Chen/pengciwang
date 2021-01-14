package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Util.DBOpenHelper;
import com.example.myapplication.Util.User;
import com.example.myapplication.Util.Utils;
import com.usts.englishlearning.activity.MainActivity;
import com.usts.englishlearning.activity.WelcomeActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BindActivity extends AppCompatActivity {
    private Button mBtnBind;
    private EditText mEtTelephone;
    private DBOpenHelper mDBOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        mBtnBind=findViewById(R.id.btn_1);
        mEtTelephone=findViewById(R.id.et_1);
        mDBOpenHelper=new DBOpenHelper(this);
        mBtnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String telephone=mEtTelephone.getText().toString();
                if(telephone.isEmpty()){
                    Toast.makeText(getApplicationContext(),"手机号不能为空",Toast.LENGTH_SHORT).show();
                }else if(!Utils.checkTel(telephone)){
                    Toast.makeText(getApplicationContext(),"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<User> data = mDBOpenHelper.getAllData();
                            int size=data.size();
                            User user=data.get(size-1);
                            try{
                                String json="{\n" +
                                        "    \"name\":\""+user.getName()+"\",\n" +
                                        "    \"telephone\":\""+telephone+"\"\n" +
                                        "}";
                                OkHttpClient client=new OkHttpClient();
                                Request request=new Request.Builder()
                                        .url("http://3b5d3d3c.cpolar.io/demo/bind")
                                        .post(RequestBody.create(MediaType.parse("application/json"),json))
                                        .build();
                                Response response=client.newCall(request).execute();
                                String responseData=response.body().string();
                                JSONObject jsonObject=new JSONObject(responseData);
                                int code=jsonObject.getInt("code");
                                if(code==1){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "绑定成功", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(BindActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                }else {
                                    Toast.makeText(getApplicationContext(), "绑定失败", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }
}

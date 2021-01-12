package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Util.Fanyi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TranslateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        Button btn1 = findViewById(R.id.fan_yi); // 翻译按钮
        final EditText editText = findViewById(R.id.yuan_wen); // 原文输入
        final TextView textView = findViewById(R.id.yi_wen); // 翻译后的文本
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable yuanWen = editText.getText();
                String url = "https://fanyi.youdao.com/translate?doctype=json&i=" + yuanWen;
                Log.i("tag", url);
                // TODO http请求
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .get() // 默认就是GET请求，可以不写
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() { // Callback回调函数 请求数据获取后执行
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("tag", e.toString());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String responseDate = response.body().string();
                        Gson gson = new Gson();
                        Type MembersType = new TypeToken<Fanyi>() {
                        }.getType();
                        Fanyi fanYi = gson.fromJson(responseDate,
                                MembersType);// 转换成FanYi类
                        String translateResult = fanYi.getTranslateResult().get(0).get(0).getTgt();
                        Log.i("tag",translateResult +"");
                        textView.setText(translateResult);
                    }
                });
            }
        });
    }
}
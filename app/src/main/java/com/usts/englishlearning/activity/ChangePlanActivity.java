package com.usts.englishlearning.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import com.example.myapplication.R;
import com.usts.englishlearning.config.ConfigData;
import com.usts.englishlearning.config.ConstantData;
import com.usts.englishlearning.database.UserConfig;
import com.usts.englishlearning.database.Word;
import com.usts.englishlearning.util.ActivityCollector;
import com.usts.englishlearning.util.TimeController;

import org.litepal.LitePal;

import java.util.List;

public class ChangePlanActivity extends Activity {

    private EditText editText;

    private TextView textGo, textBook, textWordMaxNum;

    private int maxNum;

    private static final String TAG = "ChangePlanActivity";

    private Thread thread;

    private int currentBookId;

    private List<UserConfig> userConfigs;

    private final int FINISH = 1;
    private final int DOWN_DONE = 2;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_plan);
        init();
        textGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"输入不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        editText = findViewById(R.id.edit_word_num);
        textGo = findViewById(R.id.text_plan_next);
        textBook = findViewById(R.id.text_plan_chosen);
        textWordMaxNum = findViewById(R.id.text_max_word_num);
    }
    /* @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        // 获得数据
        //List<UserConfig> userConfigs = LitePal.where("userId = ?", ConfigData.getSinaNumLogged() + "").find(UserConfig.class);
        /*currentBookId = userConfigs.get(0).getCurrentBookId();

        maxNum = ConstantData.wordTotalNumberById(currentBookId);

        // 设置最大背单词量
        textWordMaxNum.setText(maxNum + "");

        // 设置书名
        textBook.setText(ConstantData.bookNameById(currentBookId));

    }*/
}
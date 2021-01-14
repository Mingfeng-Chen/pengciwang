package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import com.example.myapplication.config.ConfigData;
import com.example.myapplication.domain.ConstantData;
import com.example.myapplication.domain.UserConfig;
import com.example.myapplication.Util.TimeController;

import org.litepal.LitePal;

import java.util.List;

public class PlanActivity extends Activity {

    private TextView textBookName, textNum, textDaily, textExpect, textInformation;

    private ImageView imgBook;

    private RelativeLayout layoutChange, layoutData;

    private String[] planStyle = {"修改书本", "修改每日学习单词量", "重置单词书"};

    private EditText editSpeed, editMatch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        init();

        //updateData();
        int bookId=1;
        int dailyNum=5;
        int wordNum=5500;
        LitePal.initialize(this);
        List<UserConfig> userConfigs = LitePal.where("userId = ?", 0 + "").find(UserConfig.class);
        if(!userConfigs.isEmpty()){
            bookId = userConfigs.get(0).getCurrentBookId();
            wordNum = ConstantData.wordTotalNumberById(bookId);
            dailyNum = userConfigs.get(0).getWordNeedReciteNum();
            Log.d("plan","wordNum"+wordNum);
        }

        Glide.with(this).load(ConstantData.bookPicById(bookId)).into(imgBook);

        textNum.setText("词汇量：" + wordNum);
        textBookName.setText(ConstantData.bookNameById(bookId));
        textDaily.setText("每日学习单词：" + dailyNum);

        int days = 5;
        textExpect.setText("预计将于" + TimeController.getDayAgoOrAfterString(days) + "初学完所有单词");

        layoutChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanActivity.this, ChangePlanActivity.class);
                startActivity(intent);
            }
        });

    }

    private void init() {
        textBookName = findViewById(R.id.text_plan_name);
        textNum = findViewById(R.id.text_plan_num);
        textDaily = findViewById(R.id.text_plan_daily);
        textExpect = findViewById(R.id.text_plan_expect);
        imgBook = findViewById(R.id.img_plan_book);
        layoutChange = findViewById(R.id.layout_plan_change);
    }

    private void updateData() {
        editMatch.setText(ConfigData.getMatchNum() + "");
        editSpeed.setText(ConfigData.getSpeedNum() + "");
    }
}
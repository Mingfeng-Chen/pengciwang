package com.usts.englishlearning.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

        /*updateData();

        List<UserConfig> userConfigs = LitePal.where("userId = ?", com.usts.englishlearning.config.ConfigData.getSinaNumLogged() + "").find(UserConfig.class);
        int bookId = userConfigs.get(0).getCurrentBookId();
        final int wordNum = ConstantData.wordTotalNumberById(bookId);
        int dailyNum = userConfigs.get(0).getWordNeedReciteNum();*/
        int bookId=1;
        final int wordNum=5500;
        int dailyNum=5;

        Glide.with(this).load(ConstantData.bookPicById(bookId)).into(imgBook);

        textNum.setText("词汇量：" + wordNum);
        textBookName.setText(ConstantData.bookNameById(bookId));
        textDaily.setText("每日学习单词：" + dailyNum);

        int days = wordNum / dailyNum + 1;
        textExpect.setText("预计将于" + TimeController.getDayAgoOrAfterString(days) + "初学完所有单词");

        //textInformation.setText("单词数量设置须介于5-" + wordNum + "之间");

        layoutChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanActivity.this, ChangePlanActivity.class);
                //intent.putExtra(ConfigData.UPDATE_NAME, ConfigData.isUpdate);
                startActivity(intent);
            }
        });

        /*layoutData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int speedNum = Integer.parseInt(editSpeed.getText().toString());
                int matchNum = Integer.parseInt(editMatch.getText().toString());
                if (speedNum >= 5 && speedNum <= wordNum && matchNum >= 2 && matchNum <= wordNum) {
                    ConfigData.setSpeedNum(speedNum);
                    ConfigData.setMatchNum(matchNum);
                    Toast.makeText(PlanActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    updateData();
                } else {
                    Toast.makeText(PlanActivity.this, "请输入数据在有效范围内", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    private void init() {
        textBookName = findViewById(R.id.text_plan_name);
        textNum = findViewById(R.id.text_plan_num);
        textDaily = findViewById(R.id.text_plan_daily);
        textExpect = findViewById(R.id.text_plan_expect);
        imgBook = findViewById(R.id.img_plan_book);
        layoutChange = findViewById(R.id.layout_plan_change);
        /*layoutData = findViewById(R.id.layout_data_change);
        editSpeed = findViewById(R.id.edit_plan_speed);
        editMatch = findViewById(R.id.edit_plan_match);
        textInformation = findViewById(R.id.text_plan_data_info);*/
    }

    private void updateData() {
        editMatch.setText(ConfigData.getMatchNum() + "");
        editSpeed.setText(ConfigData.getSpeedNum() + "");
    }
}
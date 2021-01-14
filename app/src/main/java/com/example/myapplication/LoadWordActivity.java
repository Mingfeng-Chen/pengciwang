package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Util.FileUtil;
import com.example.myapplication.Util.JsonHelper;
import com.example.myapplication.Util.TimeController;
import com.example.myapplication.Util.WordController;
import com.example.myapplication.dao.Constant;
import com.example.myapplication.config.ConfigData;
import com.example.myapplication.domain.UserConfig;

import org.litepal.LitePal;

import java.util.List;

/**
 *加载需要的学习复习的单词Activity
 * @author Fenglc
 */
public class LoadWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_word);
        //TODO 读Json放入数据库，测试，实际应当放在其他的Activity中
        FileUtil.saveLocalJson2Data(Constant.CET4_BOOK_1);
        JsonHelper.analyseDefaultAndSave(FileUtil.readLocalJson(Constant.CET4_BOOK_1));
        /****测试-冯凌畅****/
        UserConfig userConfig1 = new UserConfig();
        userConfig1.setId(1);
        userConfig1.setLastStartTime(0);
        userConfig1.setUserId(1);
        userConfig1.setWordNeedReciteNum(5);
        userConfig1.setLastStartTime(0);
        userConfig1.save();
        ConfigData.setUserNumLogged(1);
        /****测试-冯凌畅****/
        List<UserConfig> userConfigs = LitePal.where("userId = ?", ConfigData.getUserNumLogged() + "").find(UserConfig.class);
        userConfigs.get(0);
        WordController.generateDailyLearnWords(userConfigs.get(0).getLastStartTime());
        WordController.generateDailyReviewWords();
        WordController.wordReviewNum = WordController.needReviewWords.size();
        UserConfig userConfig = new UserConfig();
        userConfig.setLastStartTime(TimeController.getCurrentTimeStamp());
        TimeController.todayDate = TimeController.getCurrentDateStamp();
        NormalReciteActivity.lastWordMeaning = "";
        NormalReciteActivity.lastWord = "";
        userConfig.updateAll("userId = ?", ConfigData.getUserNumLogged() + "");
        Intent mIntent = new Intent(LoadWordActivity.this, NormalReciteActivity.class);
        startActivity(mIntent);
    }
}
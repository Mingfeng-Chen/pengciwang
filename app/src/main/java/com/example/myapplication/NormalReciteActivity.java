package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Util.MediaHelper;
import com.example.myapplication.Util.NumberController;
import com.example.myapplication.Util.WordController;
import com.example.myapplication.dao.Constant;
import com.example.myapplication.dialog.CheckWordDialog;
import com.example.myapplication.domain.Interpretation;
import com.example.myapplication.domain.Sentence;
import com.example.myapplication.domain.Word;

import org.litepal.LitePal;

import java.util.Date;
import java.util.List;

/**
 * 背单词Activity
         * @author Fenglc
        */
public class NormalReciteActivity extends BaseActivity implements View.OnClickListener{

    private int currentModel = 0;
    //复习以前学过的单词模式
    private final int REVIEW_LEARNED_BEFORE = 0;
    //学习新单词模式
    private final int LEARN_MODE = 1;
    //复习刚刚学过的单词模式
    private final int REVIEW_JUST_LEARNED = 2;
    //声明控件
    private ImageButton backImageBtn;
    private TextView need2LearnNumTV;
    private TextView need2ReviewNumTV;
    private TextView lastWordTV;
    private TextView lastWordMeaningTV;
    private TextView currentWordTV;
    private TextView pronunciationTV;
    private TextView hintTV;
    private Button[] choicesBtn = new Button[4];
    private ImageButton checkImageBtn;
    private ImageButton hintImageBtn;
    private ImageButton pronunciationImageBtn;
    private ImageButton collectedImageBtn;
    private View dialogView;
    private Button dialogCancelBtn;
    private Button dialogOKBtn;
    //正确选项button的Id
    private int correctChoiceId;
    //上一个学习的单词
    public static String lastWord;
    public static String lastWordMeaning;
    //当前学习（复习）的单词
    private Word currentWord = null;
    //当前单词的提示
    private String currentWordHint;
    //模式转换标记
    private boolean switchFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_recite);

        init();
        need2LearnNumTV.setText(WordController.needLearnWords.size()+"");
        need2ReviewNumTV.setText(WordController.needReviewWords.size()+"");
        nextWord();


    }

    private void init(){
        backImageBtn = findViewById(R.id.imageBtn_backBtn);
        backImageBtn.setOnClickListener(this);
        need2LearnNumTV = findViewById(R.id.tv_need2LearnNum);
        need2ReviewNumTV = findViewById(R.id.tv_need2ReviewNum);
        lastWordTV = findViewById(R.id.tv_lastWord);
        lastWordMeaningTV = findViewById(R.id.tv_lastWordMeaning);
        currentWordTV = findViewById(R.id.tv_currWord);
        pronunciationTV = findViewById(R.id.tv_pronunciation);
        hintTV = findViewById(R.id.tv_hint);
        choicesBtn[0] = findViewById(R.id.btn_choice1);
        choicesBtn[0].setOnClickListener(this);
        choicesBtn[1] = findViewById(R.id.btn_choice2);
        choicesBtn[1].setOnClickListener(this);
        choicesBtn[2] = findViewById(R.id.btn_choice3);
        choicesBtn[2].setOnClickListener(this);
        choicesBtn[3] = findViewById(R.id.btn_choice4);
        choicesBtn[3].setOnClickListener(this);
        checkImageBtn = findViewById(R.id.ib_checkBtn);
        checkImageBtn.setOnClickListener(this);
        hintImageBtn = findViewById(R.id.ib_hint);
        hintImageBtn.setOnClickListener(this);
        pronunciationImageBtn = findViewById(R.id.ib_pronunciation);
        pronunciationImageBtn.setOnClickListener(this);
        collectedImageBtn = findViewById(R.id.ib_collected);
        collectedImageBtn.setOnClickListener(this);
        dialogView = View.inflate(this,R.layout.check_word_dialog,null);
        dialogOKBtn = findViewById(R.id.btn_dialog_ok);
        dialogCancelBtn = findViewById(R.id.btn_dialog_cancel);

    }

    /*@Override
    protected void afterRequestPermission(int requestCode, boolean isAllGranted) {

    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回首页
            case R.id.imageBtn_backBtn:
                Intent intent = new Intent(NormalReciteActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_choice1:
                try {
                    judgeChoice(choicesBtn[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_choice2:
                try {
                    judgeChoice(choicesBtn[1]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_choice3:
                try {
                    judgeChoice(choicesBtn[2]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_choice4:
                try {
                    judgeChoice(choicesBtn[3]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ib_checkBtn:
                CheckWordDialog dialog = new CheckWordDialog(this);
                dialog.setOnCancelListener(new CheckWordDialog.OnCancelListener() {
                    @Override
                    public void onCancel(CheckWordDialog dialog) {
                    }
                });
                dialog.setOnOKListener(new CheckWordDialog.OnOKListener() {
                    @Override
                    public void onOK(CheckWordDialog dialog) {
                        nextWord();
                    }
                });
                dialog.show();
                break;
            case R.id.ib_hint:
                showHint();
                break;
            case R.id.ib_pronunciation:
                MediaHelper.play(currentWord.getWord());
                break;
            case R.id.ib_collected:
                if(currentWord.getIsCollected() == 1){
                    currentWord.setIsCollected(0);
                    currentWord.updateAll("wordId = ?", currentWord.getWordId()+"");
                    Toast.makeText(getApplicationContext(), "取消收藏成功", Toast.LENGTH_SHORT).show();
                }
                else if(currentWord.getIsCollected() == 0){
                    currentWord.setIsCollected(1);
                    currentWord.setCollectedTime(new Date());
                    currentWord.updateAll("wordId = ?", currentWord.getWordId()+"");
                    Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                }
                showCollectedStatus();
                break;
        }
    }
    //判断选择是否正确，并进行相应操作
    public void judgeChoice(Button btn) throws InterruptedException {
        //选择正确
        if(btn.getId() == correctChoiceId){
            //暂停0.5秒后转到下一个单词
            Thread.sleep(500);
            nextWord();
        }else{
            btn.setEnabled(false);
            //选择错误，将按钮背景变为红色，不可选中
            btn.setBackground(getResources().getDrawable(R.drawable.bg_wrong_word_choice));
        }
    }
    //下一个单词，并在必要时切换模式
    public void nextWord(){
        int need2LearnNum, need2ReviewNum;
        defaultLayout();
        if (currentModel == REVIEW_LEARNED_BEFORE){
            if(currentWord != null)
                WordController.reviewOldWordDone(currentWord.getWordId());
            need2ReviewNum = WordController.needReviewWords.size();
            need2ReviewNumTV.setText(need2ReviewNum+"");
            need2LearnNum = WordController.needLearnWords.size();
            need2LearnNumTV.setText(need2LearnNum+"");
            if(need2ReviewNum == 0){
                currentModel = LEARN_MODE;
                switchFlag = true;
            }else{
                ShowNewWord(WordController.reviewOldWord());
            }
        }
        if(currentModel == LEARN_MODE){
            if(currentWord != null && switchFlag != true)
                WordController.learnNewWordDone(currentWord.getWordId());
            switchFlag = false;
            need2ReviewNum = WordController.justLearnedWords.size();
            need2ReviewNumTV.setText(need2ReviewNum+"");
            need2LearnNum = WordController.needLearnWords.size();
            need2LearnNumTV.setText(need2LearnNum+"");
            if(need2LearnNum == 0){
                currentModel = REVIEW_JUST_LEARNED;
                switchFlag = true;
            }else{
                ShowNewWord(WordController.learnNewWord());
            }
        }
        if(currentModel == REVIEW_JUST_LEARNED){
            if(currentWord != null && switchFlag != true)
                WordController.reviewNewWordDone(currentWord.getWordId());
            switchFlag = false;
            need2ReviewNum = WordController.justLearnedWords.size();
            need2ReviewNumTV.setText(need2ReviewNum+"");
            need2LearnNum = WordController.needLearnWords.size();
            need2LearnNumTV.setText(need2LearnNum+"");
            if(need2ReviewNum == 0){
                learnFinish();
            }else{
                ShowNewWord(WordController.reviewNewWord());
            }
        }
    }
    //改变当前单词并显示
    public void ShowNewWord(int wordId){
        currentWord = LitePal.where("wordId = ?", wordId+"").find(Word.class).get(0);
        String meaning = WordController.getWordInterpretation(wordId);
        currentWordTV.setText(currentWord.getWord());
        pronunciationTV.setText(currentWord.getUkPhone());
        //随机选择一个button作为正确选项
        int randomChoice = NumberController.getRandomNumber(1,4);
        correctChoiceId = choicesBtn[randomChoice-1].getId();
        choicesBtn[randomChoice-1].setText(meaning);
        //分配3个错误选项
        int[] choices = new int[3];
        choices = NumberController.getRandomExceptList(1,Constant.NUM_CET4_BOOK_1,3,currentWord.getWordId());
        int j = 0;
        for(int i = 0; i < choicesBtn.length; i++){
            if(i != randomChoice-1 && choices != null){
                choicesBtn[i].setText(WordController.getWordInterpretation(choices[j]));
                j++;
            }
        }
        //显示收藏状态
        showCollectedStatus();
        //获取提示
        try {
            Sentence currentWordSentence = LitePal.where("wordId = ?", currentWord.getWordId()+"").find(Sentence.class).get(0);
            currentWordHint = currentWordSentence.toString();
        }catch (IndexOutOfBoundsException e){
            currentWordHint = "暂无提示";
        }
        //显示上一个单词
        showLastWord();
        //改变上一个单词
        lastWord = currentWord.getWord();
        lastWordMeaning = meaning;
    }
    //显示上一个单词
    public void showLastWord(){
        lastWordTV.setText(lastWord);
        lastWordMeaningTV.setText(lastWordMeaning);
    }
    //显示收藏状态
    public void showCollectedStatus(){
        if(currentWord.getIsCollected() == 1){
            collectedImageBtn.setImageDrawable(getResources().getDrawable(R.drawable.is_collected_btn));
        }else{
            collectedImageBtn.setImageDrawable(getResources().getDrawable(R.drawable.not_collected_btn));
        }
    }
    //显示提示
    public void showHint(){
        hintTV.setText(currentWordHint);
        hintTV.setBackground(getResources().getDrawable(R.drawable.bg_recite_hint));
    }
    //本次学习结束
    public void learnFinish(){

        Intent mIntent = new Intent(NormalReciteActivity.this, ReportActivity.class);
        startActivity(mIntent);
    }
    //将布局恢复成初始状态
    public void defaultLayout(){
        for(int i = 0; i < choicesBtn.length; i++){
            choicesBtn[i].setBackground(getResources().getDrawable(R.drawable.bg_word_choice));
            choicesBtn[i].setEnabled(true);
        }
        hintTV.setText("");
        hintTV.setBackground(getResources().getDrawable(R.color.colorBackground));
    }
}
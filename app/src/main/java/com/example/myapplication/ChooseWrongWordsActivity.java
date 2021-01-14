package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.domain.Word;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ChooseWrongWordsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_wrong_words);

        Button button1 = (Button)findViewById(R.id.history_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.initialize(getApplicationContext());
                List<Word> words=LitePal.where("isCollected=?",1+"").find(Word.class);
                ArrayList<String> words1=new ArrayList<>();
                for(Word word:words){
                    words1.add(word.getWord());
                }
                Intent intent1 = new Intent(ChooseWrongWordsActivity.this,WrongWordsListActivity.class);
                intent1.putStringArrayListExtra("words", words1);
                startActivity(intent1);
            }
        });
    }
}
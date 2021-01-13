package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ChooseWrongWordsActivity extends AppCompatActivity {

    ArrayList<String> words1 = new ArrayList<String >();
    ArrayList<String> words2 = new ArrayList<String >();

    private void makeWordsOrder(){
        words1.add("apple");
        words1.add("banana");
        words1.add("orange");
        words2.add("orange");
        words2.add("banana");
        words2.add("apple");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_wrong_words);

        makeWordsOrder();

        Button button1 = (Button)findViewById(R.id.history_button);
        Button button2 = (Button)findViewById(R.id.dictionary_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ChooseWrongWordsActivity.this,WrongWordsListActivity.class);
                intent1.putStringArrayListExtra("words",words1);
                startActivity(intent1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ChooseWrongWordsActivity.this,WrongWordsListActivity.class);
                intent2.putStringArrayListExtra("words",words2);
                startActivity(intent2);
            }
        });
    }
}
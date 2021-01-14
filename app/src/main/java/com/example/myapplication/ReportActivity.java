package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.Util.WordController;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_layout);
        int letters = WordController.wordLearnNum;
        int days = WordController.wordReviewNum;
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strDateFormat);
        String date_string = simpleDateFormat.format(date);
        TextView textView1 = (TextView) findViewById(R.id.report_layout_textview5);
        TextView textView2 = (TextView) findViewById(R.id.report_layout_textview6);
        TextView textView3 = (TextView) findViewById(R.id.report_layout_textview2);
        String letters_string = String.valueOf(letters);
        String days_string = String.valueOf(days);
        textView1.setText(letters_string);
        textView2.setText(days_string);
        textView3.setText(date_string);
        Button button = (Button) findViewById(R.id.report_layout_button2);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(ReportActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}

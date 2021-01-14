package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.Util.WordController;
import com.example.myapplication.domain.MyDate;
import com.example.myapplication.domain.User;

import org.litepal.LitePal;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        LitePal.initialize(this);
        List<MyDate> myDates = LitePal.where("userId = ?",0 + "").find(MyDate.class);
        if(myDates.isEmpty()){
            MyDate myDate=new MyDate();
            myDate.setUserId(0);
            myDate.setWordLearnNumber(letters);
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonth = calendar.get(Calendar.MONTH);
            int currentDate = calendar.get(Calendar.DAY_OF_MONTH);
            myDate.setDate(currentDate);
            myDate.setMonth(currentMonth+1);
            myDate.setYear(currentYear);
            myDate.save();
            Log.d("MyDate:",myDate.toString());
        }else{
            myDates.get(0).setWordLearnNumber(letters);
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonth = calendar.get(Calendar.MONTH);
            int currentDate = calendar.get(Calendar.DAY_OF_MONTH);
            myDates.get(0).setDate(currentDate);
            myDates.get(0).setMonth(currentMonth+1);
            myDates.get(0).setYear(currentYear);
            myDates.get(0).save();
            Log.d("MyDate:",myDates.get(0).toString());
        }
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

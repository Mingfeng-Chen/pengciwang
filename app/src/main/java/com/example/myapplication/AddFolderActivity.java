package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.domain.Word;

import org.litepal.LitePal;

import java.util.Random;

public class AddFolderActivity extends Activity {

    private EditText editName, editRemark;

    private RelativeLayout layoutAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_folder);

        init();

        layoutAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editName.getText().toString().trim())) {
                    LitePal.initialize(getApplicationContext());
                    Word word=new Word();
                    Random random=new Random();
                    word.setWordId(random.nextInt()+1000);
                    word.setWord(editName.getText().toString());
                    word.save();
                    onBackPressed();
                    Toast.makeText(AddFolderActivity.this, "新建成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddFolderActivity.this, "请输入完整", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        editName = findViewById(R.id.edit_af_name);
        editRemark = findViewById(R.id.edit_af_remark);
        layoutAdd = findViewById(R.id.layout_af_add);
    }

}

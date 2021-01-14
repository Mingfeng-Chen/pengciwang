package com.usts.englishlearning.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.usts.englishlearning.util.FileUtil;
import com.usts.englishlearning.util.JsonHelper;
import com.usts.englishlearning.util.TimeController;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChangePlanActivity extends Activity {

    private EditText editText;

    private TextView textGo, textBook, textWordMaxNum;

    private int maxNum;

    private static final String TAG = "ChangePlanActivity";

    private Thread thread;

    private int currentBookId=1;

    private List<UserConfig> userConfigs;

    private final int FINISH = 1;
    private final int DOWN_DONE = 2;

    private ProgressDialog progressDialog;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case FINISH:
                    // 等待框消失
                    //progressDialog.dismiss();
                    // 重置上次学习时间
                    ActivityCollector.startOtherActivity(ChangePlanActivity.this, MainActivity.class);
                    break;
                case DOWN_DONE:
                    //progressDialog.setMessage("已下载完成，正在解压分析数据中...");
                    break;
            }
        }
    };

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
                    /*InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    /*progressDialog = new ProgressDialog(ChangePlanActivity.this);
                    progressDialog.setTitle("请稍等");
                    progressDialog.setMessage("数据包正在下载中...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();*/
                    int wordNum=0;
                    try{
                        wordNum=Integer.parseInt(editText.getText().toString());
                        if(wordNum>=5&&wordNum<=1162){
                            LitePal.initialize(getApplicationContext());
                            List<UserConfig> userConfigs = LitePal.where("userId = ?", 0 + "").find(UserConfig.class);
                            if(!userConfigs.isEmpty()){
                                userConfigs.get(0).setWordNeedReciteNum(wordNum);
                                userConfigs.get(0).save();
                            }
                            Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(ChangePlanActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(),"输入范围错误",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"请输入数字",Toast.LENGTH_SHORT).show();
                    }
                    // 延迟两秒再运行，防止等待框不显示
                    /*new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 开启线程分析数据
                            thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        OkHttpClient client = new OkHttpClient();
                                        Request request = new Request.Builder()
                                                .url(ConstantData.bookDownLoadAddressById(currentBookId))
                                                .build();
                                        Response response = client.newCall(request).execute();
                                        Message message = new Message();
                                        message.what = DOWN_DONE;
                                        handler.sendMessage(message);
                                        FileUtil.getFileByBytes(response.body().bytes(), "/Android/data/com.example.myapplication/files"+ "/" + ConstantData.DIR_TOTAL, ConstantData.bookFileNameById(currentBookId));
                                        FileUtil.unZipFile("/Android/data/com.example.myapplication/files"+ "/" + ConstantData.DIR_TOTAL + "/" + ConstantData.bookFileNameById(currentBookId)
                                                , getFilesDir() + "/" + ConstantData.DIR_TOTAL + "/" + ConstantData.DIR_AFTER_FINISH, false);
                                    } catch (Exception e) {

                                    }
                                    JsonHelper.analyseDefaultAndSave(FileUtil.readLocalJson(ConstantData.DIR_TOTAL + "/" + ConstantData.DIR_AFTER_FINISH + "/" + ConstantData.bookFileNameById(currentBookId).replace(".zip", ".json")));
                                    Message message = new Message();
                                    message.what = FINISH;
                                    handler.sendMessage(message);
                                }
                            });
                            thread.start();
                        }
                    }, 500);*/
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
     @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        // 获得数据
        //List<UserConfig> userConfigs = LitePal.where("userId = ?", ConfigData.getSinaNumLogged() + "").find(UserConfig.class);
        //currentBookId = userConfigs.get(0).getCurrentBookId();

        maxNum = ConstantData.wordTotalNumberById(currentBookId);

        // 设置最大背单词量
        textWordMaxNum.setText(maxNum + "");

        // 设置书名
        textBook.setText(ConstantData.bookNameById(currentBookId));

    }
}
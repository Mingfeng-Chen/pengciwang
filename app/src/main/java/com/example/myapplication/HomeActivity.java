package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.BaseActivity;
import com.example.myapplication.domain.User;
import com.example.myapplication.domain.UserConfig;
import com.example.myapplication.index.FragmentMe;
import com.example.myapplication.index.FragmentReview;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.myapplication.R;
import com.example.myapplication.index.FragmentWord;

import org.litepal.LitePal;

import java.util.List;

public class HomeActivity extends BaseActivity {

    private Fragment fragWord, fragReview, fragMe;

    private Fragment[] fragments;
    private Button day;
    private Button night;
    //用于记录上个选择的Fragment
    public static int lastFragment;

    private BottomNavigationView bottomNavigationView;

    private LinearLayout linearLayout;

    private static final String TAG = "MainActivity";

    public static boolean needRefresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d(TAG, "onCreate: ");

        init();
        LitePal.initialize(this);
        List<User> users = LitePal.where("userId = ?",0 + "").find(User.class);
        if(users.isEmpty()){
            User user = new User();
            user.setUserId(0);// 测试
            user.setUserMoney(0);
            user.setUserWordNumber(0);
            user.save();
            Log.d("user","新建");
        }
        List<UserConfig> userConfigs=LitePal.where("userId=?",0+"").find(UserConfig.class);
        if(userConfigs.isEmpty()){
            UserConfig userConfig=new UserConfig();
            userConfig.setUserId(0);
            userConfig.setCurrentBookId(1);
            userConfig.setWordNeedReciteNum(5);
            userConfig.save();
            Log.d("userconfig","新建");
        }
        if (needRefresh) {

            TranslateAnimation animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f
            );
            animation.setDuration(2000);
            //bottomNavigationView.startAnimation(animation);
        }

        initFragment();

    }

    // 初始化控件
    private void init() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        linearLayout = findViewById(R.id.linear_frag_container);
    }

    // 初始化initFragment
    private void initFragment() {
        fragWord = new FragmentWord();
        fragReview = new FragmentReview();
        fragMe = new FragmentMe();
        fragments = new Fragment[]{fragWord, fragReview, fragMe};
        switch (lastFragment) {
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_frag_container, fragWord).show(fragWord).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_frag_container, fragReview).show(fragReview).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.linear_frag_container, fragMe).show(fragMe).commit();
                break;
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bnavigation_word:
                        if (lastFragment != 0) {
                            switchFragment(lastFragment, 0);
                            lastFragment = 0;
                        }
                        return true;
                    case R.id.bnavigation_review:
                        if (lastFragment != 1) {
                            switchFragment(lastFragment, 1);
                            lastFragment = 1;
                        }
                        return true;
                    case R.id.bnavigation_me:
                        if (lastFragment != 2) {
                            switchFragment(lastFragment, 2);
                            lastFragment = 2;
                        }
                        return true;
                }
                return true;
            }
        });
    }

    private void switchFragment(int lastIndex, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏上个Fragment
        transaction.hide(fragments[lastIndex]);
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.linear_frag_container, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    /*@Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("今天不再背单词了吗？")
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        needRefresh = true;
                        ActivityCollector.finishAll();
                    }
                })
                .setNegativeButton("再看看", null)
                .show();
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void modeDay(View v) {
        setEnableNightMode(false);
    }

    public void modeNight(View v) {
        setEnableNightMode(true);
    }
}

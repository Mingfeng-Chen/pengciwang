package com.example.myapplication.index;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.AddFolderActivity;
import com.example.myapplication.HomeActivity;
import com.example.myapplication.LoadWordActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.SearchActivity;
import com.example.myapplication.Util.NumberController;
import com.example.myapplication.domain.ConstantData;
import com.example.myapplication.domain.Interpretation;
import com.example.myapplication.domain.UserConfig;
import com.example.myapplication.domain.Word;


import org.litepal.LitePal;

import java.util.Calendar;
import java.util.List;

public class FragmentWord extends Fragment implements View.OnClickListener {

    private CardView cardStart, cardSearch;
    private ImageView imgRefresh, imgSearch, imgFlag;
    private View tranView, tranSearchView;
    private TextView textStart,textView;
    private RelativeLayout layoutFiles;

    private TextView textWord, textMean, textWordNum, textBook;

    private TextView textDate, textMonth;

    private static final String TAG = "FragmentWord";

    private int currentBookId;

    private boolean isOnClick = true;

    private int currentRandomId;

    public static int prepareData = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        Log.d(TAG, "onActivityCreated: ");

        if (HomeActivity.needRefresh) {
            prepareData = 0;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //BaseActivity.prepareDailyData();
                }
            }).start();
        }
        currentBookId=1;
        setRandomWord();

    }

    // 初始化控件
    private void init() {
        imgRefresh = getActivity().findViewById(R.id.img_refresh);
        //imgRefresh.setOnClickListener(this);
        cardStart = getActivity().findViewById(R.id.card_index_start);
        cardStart.setOnClickListener(this);
        tranView = getActivity().findViewById(R.id.view_main_tran);
        textMean = getActivity().findViewById(R.id.text_main_show_word_mean);
        textMean.setOnClickListener(this);
        textWord = getActivity().findViewById(R.id.text_main_show_word);
        textWordNum = getActivity().findViewById(R.id.text_main_show_word_num);
        textBook = getActivity().findViewById(R.id.text_main_show_book_name);
        textStart = getActivity().findViewById(R.id.text_main_start);
        textStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), LoadWordActivity.class);
                startActivity(intent);
            }
        });
        layoutFiles = getActivity().findViewById(R.id.layout_main_words);
        layoutFiles.setOnClickListener(this);
        textDate = getActivity().findViewById(R.id.text_main_date);
        textMonth = getActivity().findViewById(R.id.text_main_month);
        cardSearch = getActivity().findViewById(R.id.card_main_search);
        cardSearch.setOnClickListener(this);
        imgSearch = getActivity().findViewById(R.id.img_review_search);
        imgFlag = getActivity().findViewById(R.id.img_top_flag);
        imgFlag.setOnClickListener(this);
        tranSearchView = getActivity().findViewById(R.id.view_search_tran);
        textView=getActivity().findViewById(R.id.tv_1);
        LitePal.initialize(getContext());
        List<UserConfig> userConfigs=LitePal.where("userId = ?", 0 + "").find(UserConfig.class);
        if(!userConfigs.isEmpty()){
            textView.append(String.valueOf(userConfigs.get(0).getWordNeedReciteNum()));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_main_start:
                if (isOnClick) {
                    Intent mIntent = new Intent(getActivity(), LoadWordActivity.class);
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mIntent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    isOnClick = false;
                }
                break;
            case R.id.card_main_search:
                Intent intent2 = new Intent(getActivity(), SearchActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ActivityOptionsCompat activityOptionsCompat2 = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        tranSearchView, "imgSearch");
                startActivity(intent2, activityOptionsCompat2.toBundle());
                break;
            case R.id.layout_main_words:
                Intent intent3 = new Intent(getActivity(), AddFolderActivity.class);
                startActivity(intent3, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void setRandomWord() {
        ++prepareData;
        Log.d(TAG, "setRandomWord: " + ConstantData.wordTotalNumberById(currentBookId));
        int randomId = NumberController.getRandomNumber(1, ConstantData.wordTotalNumberById(currentBookId));
        Log.d(TAG, "当前ID" + randomId);
        currentRandomId = randomId;
        Log.d(TAG, "要传入的ID" + currentRandomId);
        Log.d(TAG, randomId + "");
        Word word = LitePal.where("wordId = ?", randomId + "").select("wordId", "word").find(Word.class).get(0);
        Log.d(TAG, word.getWord());
        List<Interpretation> interpretations = LitePal.where("wordId = ?", word.getWordId() + "").find(Interpretation.class);
        textWord.setText(word.getWord());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < interpretations.size(); ++i) {
            stringBuilder.append(interpretations.get(i).getWordType() + ". " + interpretations.get(i).getCHSMeaning());
            if (i != interpretations.size() - 1)
                stringBuilder.append("\n");
        }
        textMean.setText(stringBuilder.toString());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

}

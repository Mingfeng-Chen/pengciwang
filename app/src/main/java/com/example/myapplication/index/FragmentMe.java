package com.example.myapplication.index;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.PlanActivity;
import com.example.myapplication.R;
import com.example.myapplication.Util.DBOpenHelper;
import com.example.myapplication.Util.User;


import java.util.ArrayList;

public class FragmentMe extends Fragment implements View.OnClickListener {

    private LinearLayout layoutCalendar, layoutWordList, layoutData, layoutPlan, layoutMoney;

    private RelativeLayout layoutAlarm, layoutNotify, layoutAbout, layoutSyno;

    private TextView textDays, textWordNum, textMoney;

    private TextView textName;

    private Switch aSwitchNight;
    private boolean isNight;
    private static final String TAG = "FragmentMe";
    private DBOpenHelper dbOpenHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        Log.d(TAG, "onActivityCreated: ");
        dbOpenHelper=new DBOpenHelper(getContext());
        ArrayList<User> data =dbOpenHelper.getAllData();
        int size=data.size();
        if(size!=0){
            User user=data.get(size-1);
            textName.setText(user.getName());
        }
    }


    private void init() {
        layoutWordList = getActivity().findViewById(R.id.layout_me_word_list);
        layoutWordList.setOnClickListener(this);
        textDays = getActivity().findViewById(R.id.text_me_days);
        textWordNum = getActivity().findViewById(R.id.text_me_words);
        textMoney = getActivity().findViewById(R.id.text_me_money);
        layoutPlan = getActivity().findViewById(R.id.layout_me_plan);
        layoutPlan.setOnClickListener(this);
        layoutAlarm = getActivity().findViewById(R.id.layout_me_alarm);
        layoutAlarm.setOnClickListener(this);
        layoutMoney = getActivity().findViewById(R.id.layout_me_money);
        layoutAbout = getActivity().findViewById(R.id.layout_me_about);
        textName = getActivity().findViewById(R.id.text_me_name);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.layout_me_plan:
                intent.setClass(getActivity(), PlanActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


}

package com.example.myapplication.index;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.ChooseWrongWordsActivity;
import com.example.myapplication.MyApplication;
import com.example.myapplication.NightActivity;
import com.example.myapplication.TranslateActivity;
import com.google.gson.Gson;
import com.example.myapplication.R;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class FragmentReview extends Fragment implements View.OnClickListener {

    private RelativeLayout layoutPhoto, layoutGame;

    private LinearLayout layoutSpeed, layoutMatch;

    private CircleImageView imgHead;

    private final int REQUEST_CODE_TAKE_PICTURE = 1000;

    private final int IMAGE_REQUEST_CODE = 2000;

    private static final String TAG = "FragmentReview";

    private ProgressDialog progressDialog;

    private final int FINISH = 1;
    private final int WRONG = 2;
    private final int LOAD_DONE = 3;
    private final int LOAD_SPEED = 4;

    final String items[] = {"拍照", "从手机相册选择"};

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case FINISH:
                    progressDialog.dismiss();
                    /*Intent intent = new Intent(getActivity(), OCRActivity.class);
                    startActivity(intent);*/
                    break;
                case WRONG:
                    progressDialog.dismiss();
                    Toast.makeText(MyApplication.getContext(), "发生了小错误，请重试", Toast.LENGTH_SHORT).show();
                    break;
                case LOAD_DONE:
                    progressDialog.dismiss();
                    /*Intent intent2 = new Intent();
                    intent2.setClass(MyApplication.getContext(), MatchActivity.class);
                    startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());*/
                    break;
                case LOAD_SPEED:
                    progressDialog.dismiss();
                    /*Intent intent3 = new Intent();
                    intent3.setClass(MyApplication.getContext(), SpeedActivity.class);
                    startActivity(intent3, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());*/
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        //LitePal.initialize(getContext());
        // 设置头像
        /*List<User> userList = LitePal.where("userId = ?", ConfigData.getSinaNumLogged() + "").find(User.class);
        Glide.with(MyApplication.getContext()).load(userList.get(0).getUserProfile()).into(imgHead);*/

    }

    private void init() {
        layoutPhoto = getActivity().findViewById(R.id.layout_re_photo);
        //layoutPhoto.setOnClickListener(this);
        layoutSpeed = getActivity().findViewById(R.id.layout_re_speed);
        layoutMatch = getActivity().findViewById(R.id.layout_re_match);
        layoutMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),TranslateActivity.class);
                startActivity(intent);
            }
        });
        layoutGame = getActivity().findViewById(R.id.layout_re_game);
        layoutGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ChooseWrongWordsActivity.class);
                startActivity(intent);
            }
        });
        imgHead = getActivity().findViewById(R.id.img_me_head);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        switch (v.getId()) {
            case R.id.layout_re_speed:
                break;
            case R.id.layout_re_match:
                break;
            case R.id.layout_re_game:
                intent.setClass(MyApplication.getContext(), ChooseWrongWordsActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;
        }

    }

    private void showProgressDialog(String content) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("请稍后");
        progressDialog.setMessage(content);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


}

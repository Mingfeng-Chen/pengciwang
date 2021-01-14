package com.example.myapplication.Util;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.example.myapplication.MyApplication;
import com.example.myapplication.dao.Constant;

public class MediaHelper {

    public static MediaPlayer mediaPlayer;

    // 英文发音
    public static final int ENGLISH_VOICE = 1;
    // 美国发音
    public static final int AMERICA_VOICE = 0;

    // 默认
    public static final int DEFAULT_VOICE = ENGLISH_VOICE;

    private static final String TAG = "MediaHelper";

    public static void play(int type, String wordName) {
        if (mediaPlayer != null) {
            releasePlayer();
            mediaPlayer = new MediaPlayer();
        } else
            mediaPlayer = new MediaPlayer();
        try {
            if (ENGLISH_VOICE == type)
                mediaPlayer.setDataSource(Constant.YOU_DAO_VOICE_EN + wordName);
            else
                mediaPlayer.setDataSource(Constant.YOU_DAO_VOICE_USA + wordName);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mediaPlayer != null) {
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void play(String wordName) {
        play(DEFAULT_VOICE, wordName);
    }

    public static void releasePlayer() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
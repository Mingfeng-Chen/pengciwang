package com.example.myapplication.dao;

public class Constant {
    private final static String BASE_URL = "http://192.168.43.222:8080/demo";
    public final static String REGISTER=BASE_URL+"/register";
    public final static String LOGIN =BASE_URL+"login";
    public final static String BIND=BASE_URL+"/bind";
    // 有道英音发音
    public static final String YOU_DAO_VOICE_EN = "https://dict.youdao.com/dictvoice?type=1&audio=";
    // 有道美音发音
    public static final String YOU_DAO_VOICE_USA = "https://dict.youdao.com/dictvoice?type=0&audio=";
    //四级核心词汇
    public static final String CET4_BOOK_1 = "CET4luan_1.json";
    //四级核心词汇单词数量
    public static final int NUM_CET4_BOOK_1 = 1162;

}

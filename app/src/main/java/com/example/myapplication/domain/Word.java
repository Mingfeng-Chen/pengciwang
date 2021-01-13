package com.example.myapplication.domain;


import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class Word extends LitePalSupport {

    // ID
    @Column(unique = true)
    private int wordId;

    // 单词
    private String word;

    // 英国音标
    private String ukPhone;

    // 美国音标
    private String usPhone;

    // 巧记
    private String remMethod;

    // 图片(网址)
    private String picAddress;

    // 自定义照片
    private byte[] picCustom;

    // 自定义备注
    private String remark;

    // 设置归属
    private String belongBook;

    // 是否收藏
    @Column(defaultValue = "0")
    private int isCollected;

    //收藏时间
    @Column(defaultValue = "0")
    private Date collectedTime;


    /*
     * 以下是学习复习专用的
     */

    // 是否需要学习
    @Column(defaultValue = "0")
    private int isNeedLearned;

    // 是否学习过
    @Column(defaultValue = "0")
    private int isLearned;

    //学习时间
    private Date learnedTime;

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getUkPhone() {
        return ukPhone;
    }

    public void setUkPhone(String ukPhone) {
        this.ukPhone = ukPhone;
    }

    public String getUsPhone() {
        return usPhone;
    }

    public void setUsPhone(String usPhone) {
        this.usPhone = usPhone;
    }

    public String getRemMethod() {
        return remMethod;
    }

    public void setRemMethod(String remMethod) {
        this.remMethod = remMethod;
    }

    public String getPicAddress() {
        return picAddress;
    }

    public void setPicAddress(String picAddress) {
        this.picAddress = picAddress;
    }

    public byte[] getPicCustom() {
        return picCustom;
    }

    public void setPicCustom(byte[] picCustom) {
        this.picCustom = picCustom;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBelongBook() {
        return belongBook;
    }

    public void setBelongBook(String belongBook) {
        this.belongBook = belongBook;
    }

    public int getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(int isCollected) {
        this.isCollected = isCollected;
    }

    public Date getCollectedTime() { return collectedTime; }

    public void setCollectedTime(Date collectedTime) { this.collectedTime = collectedTime; }

    public int getIsNeedLearned() { return isNeedLearned; }

    public void setIsNeedLearned(int isNeedLearned) {
        this.isNeedLearned = isNeedLearned;
    }

    public int getIsLearned() {
        return isLearned;
    }

    public void setIsLearned(int isLearned) { this.isLearned = isLearned; }

    public Date getLearnedTime(){ return learnedTime; }

    public void setLearnedTime(Date learnedTime){ this.learnedTime = learnedTime; }
}
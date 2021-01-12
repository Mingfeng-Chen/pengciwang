package com.example.myapplication.Util;

import com.example.myapplication.Util.translateResult;

import java.util.ArrayList;

public class Fanyi {
    private String type;
    private int errorCode;
    private int elapsedTime;
    private ArrayList<ArrayList<translateResult>> translateResult;

    public Fanyi() {
    }

    public Fanyi(String type, int errorCode, int elapsedTime, ArrayList<ArrayList<translateResult>> translateResult) {
        this.type = type;
        this.errorCode = errorCode;
        this.elapsedTime = elapsedTime;
        this.translateResult = translateResult;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public ArrayList<ArrayList<translateResult>> getTranslateResult() {
        return translateResult;
    }

    public void setTranslateResult(ArrayList<ArrayList<translateResult>> translateResult) {
        this.translateResult = translateResult;
    }
}
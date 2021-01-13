package com.example.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.myapplication.R;

public class CheckWordDialog extends Dialog implements View.OnClickListener {

    private Button dialogCancelBtn;
    private Button dialogOKBtn;

    private OnCancelListener cancelListener;

    private OnOKListener okListener;
    public CheckWordDialog(@NonNull Context context) {
        super(context);
    }

    public CheckWordDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_word_dialog);
        //设置宽度
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int)(size.x * 0.8);//设置dialog宽度为当前手机屏幕的宽度的80%
        getWindow().setAttributes(p);

        dialogCancelBtn = findViewById(R.id.btn_dialog_cancel);
        dialogOKBtn = findViewById(R.id.btn_dialog_ok);
        dialogCancelBtn.setOnClickListener(this);
        dialogOKBtn.setOnClickListener(this);
    }

    public void setOnCancelListener(OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public void setOnOKListener(OnOKListener okListener) {
        this.okListener = okListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_dialog_cancel:
                if(cancelListener != null){
                    cancelListener.onCancel(this);
                }
                dismiss();
                break;
            case R.id.btn_dialog_ok:
                if(okListener != null){
                    okListener.onOK(this);
                }
                dismiss();
                break;
        }
    }

    public interface OnCancelListener{
        void onCancel(CheckWordDialog dialog);
    }

    public interface OnOKListener{
        void onOK(CheckWordDialog dialog);
    }
}

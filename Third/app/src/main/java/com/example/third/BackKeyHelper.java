package com.example.third;

import android.app.Activity;
import android.widget.Toast;

public class BackKeyHelper {
    private long lastPress = 0;
    private Toast toast;
    private Activity activity;

    public BackKeyHelper(Activity activity){
        this.activity = activity;
    }

    public void onBackPressed(){
        if(System.currentTimeMillis() > lastPress + 2000){
            lastPress = System.currentTimeMillis();
            showGuide();
            return;
        }
        activity.finish();
        toast.cancel();
    }

    public void showGuide(){
        toast = Toast.makeText(activity, "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}

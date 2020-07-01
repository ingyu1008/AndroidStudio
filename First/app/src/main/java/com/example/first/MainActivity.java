package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView ballIV;
    private TextView answerTV;

    private String[] answersArray = {"구건모", "방인규"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ballIV = findViewById(R.id.btn);
        answerTV = findViewById(R.id.answer);

        ballIV.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn:
                int rand = new Random().nextInt(answersArray.length);
                answerTV.setText(answersArray[rand]);
                break;
        }
    }
}
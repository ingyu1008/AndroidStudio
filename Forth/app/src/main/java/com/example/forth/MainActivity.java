package com.example.forth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private Button button;
    private EditText editText;

    private ArrayList<Long> arrayList;
    private ArrayAdapter<Long> arrayAdapter;

    private long elapsedTime = 0;

    public void update(long i){
        if(i < 0){
            Toast.makeText(this,"피로도가 음수일 수 없습니다.",Toast.LENGTH_SHORT).show();
        } else if (i > 200){
            Toast.makeText(this,"피로도가 최대치보다 높을 수 없습니다.",Toast.LENGTH_SHORT).show();
        } else {
            elapsedTime = TimeControl.updateArrayAdapter(arrayList,arrayAdapter,elapsedTime,i);
            DataControl.writeData(arrayList,this);
            textView.setText(String.format("%d / 200", arrayList.get(0)));
            Toast.makeText(this,"정상적으로 업데이트 되었습니다.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);

        arrayList = DataControl.readData(this);
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);

        elapsedTime = TimeControl.updateArrayAdapter(arrayList,arrayAdapter,elapsedTime,arrayList.get(0));

        try{
            textView.setText(String.format("%d / 200", arrayList.get(0)));
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button:
                String str = editText.getText().toString();
                editText.setText("");
                long i = 0;
                try{
                    i = Long.parseLong(str);
                } catch(NumberFormatException e){
                    e.printStackTrace();
                    Toast.makeText(this,"숫자를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    break;
                }
                update(i);
                break;
        }
    }
}
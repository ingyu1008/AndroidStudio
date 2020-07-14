package com.example.forth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private TextView textView2;
    private Button button;
    private EditText editText;
    private Button updateButton;

    private ArrayList<Long> arrayList;
    private ArrayAdapter<Long> arrayAdapter;

    private long elapsedTime = 0;

    SimpleDateFormat simpleDate = new SimpleDateFormat("이전 업데이트로 부터 mm 분 ss 초가 지났습니다");

    public void update(long i){
        if(i < 0){
            Toast.makeText(this,"피로도가 음수일 수 없습니다.",Toast.LENGTH_SHORT).show();
        } else if (i > 200){
            Toast.makeText(this,"피로도가 최대치보다 높을 수 없습니다.",Toast.LENGTH_SHORT).show();
        } else {
            elapsedTime = TimeControl.updateArrayAdapter(arrayList,arrayAdapter,elapsedTime,i,this);
            DataControl.writeData(arrayList,this);
            textView.setText(String.format("%d / 200", arrayList.get(0)));
            Toast.makeText(this,"정상적으로 업데이트 되었습니다.",Toast.LENGTH_SHORT).show();
        }
        textView2.setText(simpleDate.format(elapsedTime));
    }

    public void update(long i, boolean UPDATE){
        elapsedTime = TimeControl.updateArrayAdapter(arrayList,arrayAdapter,elapsedTime,i,this);
        DataControl.writeData(arrayList,this);
        try {
            textView.setText(String.format("%d / 200", arrayList.get(0)));
        } catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        Toast.makeText(this,"정상적으로 업데이트 되었습니다.",Toast.LENGTH_SHORT).show();
        textView2.setText(simpleDate.format(elapsedTime));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        updateButton = findViewById(R.id.update);

        arrayList = DataControl.readData(this);
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);

        try {
            elapsedTime = TimeControl.updateArrayAdapter(arrayList, arrayAdapter, arrayList.get(2), arrayList.get(0),this,true);
        } catch(NullPointerException e){
            e.printStackTrace();
            elapsedTime = 0;
        } catch(IndexOutOfBoundsException e){
            e.printStackTrace();
            elapsedTime = 0;
        }

        try{
            textView.setText(String.format("%d / 200", arrayList.get(0)));
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e){
            textView.setText(String.format("%d / 200", 200));
            arrayList.add(200L);
            arrayList.add(System.currentTimeMillis());
            arrayList.add(0L);
            DataControl.writeData(arrayList,this);
            e.printStackTrace();
        }

        button.setOnClickListener(this);
        updateButton.setOnClickListener(this);
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
            case R.id.update:
                update(500,true);
                break;
        }
    }
}
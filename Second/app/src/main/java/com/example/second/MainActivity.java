package com.example.second;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText[] editTexts = new EditText[1];
    private Button[] buttons = new Button[1];
    private ListView[] lists = new ListView[1];

    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTexts[0] = findViewById(R.id.editText0);
        buttons[0] = findViewById(R.id.button0);
        lists[0] = findViewById(R.id.list0);

        arrayList = FileHelper.readData(this);
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);

        lists[0].setAdapter(arrayAdapter);

        buttons[0].setOnClickListener(this);

        lists[0].setOnItemClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button0:
                String str = editTexts[0].getText().toString();
                editTexts[0].setText("");
                arrayAdapter.add(str);

                FileHelper.writeData(arrayList,this);

                Toast.makeText(this,"정상적으로 추가되었습니다.",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        arrayList.remove(i);
        arrayAdapter.notifyDataSetChanged();
        FileHelper.writeData(arrayList,this);
        Toast.makeText(this, "정상적으로 삭제되었습니다.",Toast.LENGTH_SHORT).show();
    }
}
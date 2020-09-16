package com.example.codeforcescalender;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public Context context;

    public TableLayout tableLayout;
    public ArrayList<TableRow> rows = new ArrayList<>();
    public ArrayList<TextView> tvs = new ArrayList<>();
    private final String[] diff = {"Very Easy", "Easy", "Normal", "Hard","Very Hard"};

    public ArrayList<String> spinnerArray = new ArrayList<>();

    public Spinner spinner;

    final String[] s = new String[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        tableLayout = findViewById(R.id.table);
        spinner = findViewById(R.id.spinner);

        Toast.makeText(this, "Please wait while fetching information...", Toast.LENGTH_LONG).show();
        Thread thread = new Thread(new Runnable(){
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                try {
                    s[0] = RestCall.restCall("https://codeforces.com/api/contest.list?gym=false").toString();
                    System.out.println(s[0]);
                } catch (Exception e) {
                    System.out.println("rest call fail : " + e.getMessage());
                }
            }
        });
        try {
            init(thread);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private synchronized void init(Thread thread) throws JSONException {
        tableLayout.removeAllViews();
        tvs.clear();
        rows.clear();
        thread.start();
        while(thread.isAlive()){
        }
        updateSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = spinnerArray.get(i);
                try {
                    fillTable(selected);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private String parsePhase(String s){
        switch(s){
            case "BEFORE":
                return "대회 시작 전 입니다.";
            case "CODING":
                return "대회 중 입니다.";
            case "PENDING_SYSTEM_TEST":
                return "참가자들이 '맞왜틀!!' 중 입니다";
            case "SYSTEM_TEST":
                return "시스텟을 돌리는 중 입니다.";
            case "FINISHED":
                return "대회가 종료되었습니다.";
            default:
                return s;
        }
    }

    private void fillTable(String name) throws JSONException {
        tableLayout.removeAllViews();
        tvs.clear();
        rows.clear();
        JSONObject jsonObject = new JSONObject(s[0]);
        JSONArray contests = jsonObject.getJSONArray("result");
        for(int i = 0; i < contests.length(); i++){
            JSONObject me = new JSONObject(contests.get(i).toString());
            if(!me.getString("name").equals(name))continue;
            addRow(name);
            addRow(parsePhase(me.getString("phase")));
            addRow("");
            if(me.getString("phase").equals("BEFORE")) {
                addRow("대회는 " + me.getInt("durationSeconds") / 60 + "분간 진행될 예정입니다.");
                addRow("");
                addRow("대회 시작 시간은 다음과 같습니다");
                addRow(new Date(me.getLong("startTimeSeconds") * 1000L).toString());
            }

            break;
        }
    }

    private void addRow(String content){
        TextView tv = new TextView(this);
        tv.setWidth(tableLayout.getWidth());
        tv.setSingleLine(false);
        tv.setMaxLines(2);
        tv.setText(content);
        tv.setTextSize(20);
        tv.setGravity(Gravity.START);
        TableRow tr = new TableRow(this);
        tr.addView(tv);
        tableLayout.addView(tr);
        tvs.add(tv);
        rows.add(tr);
    }

    private void updateSpinner() throws JSONException {
        JSONObject jsonObject = new JSONObject(s[0]);
        addRow(jsonObject.getString("status"));
        JSONArray contests = jsonObject.getJSONArray("result");
        for(int i = 0; i < contests.length(); i++){
            JSONObject me = new JSONObject(contests.get(i).toString());
            spinnerArray.add(me.getString("name"));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item,spinnerArray
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}


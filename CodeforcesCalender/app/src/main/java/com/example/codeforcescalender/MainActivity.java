package com.example.codeforcescalender;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public Context context;

    public TableLayout tableLayout;
    public ArrayList<TableRow> rows = new ArrayList<>();
    public ArrayList<TextView> tvs = new ArrayList<>();

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
            updateTable(thread);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private synchronized void updateTable(Thread thread) throws JSONException {
        tableLayout.removeAllViews();
        tvs.clear();
        rows.clear();
        thread.start();
        while(thread.isAlive()){
        }
        addNewRows();
    }

    private void addRow(String content){
        TextView tv = new TextView(this);
        tv.setText(content);
        tv.setTextSize(24);
        tv.setGravity(Gravity.START);
        TableRow tr = new TableRow(this);
        tr.addView(tv);
        tableLayout.addView(tr);
        tvs.add(tv);
        rows.add(tr);
    }

    private void addNewRows() throws JSONException {
        JSONObject jsonObject = new JSONObject(s[0]);
        addRow(jsonObject.getString("status"));
        JSONArray contests = jsonObject.getJSONArray("result");
        for(int i = 0; i < contests.length(); i++){
            JSONObject me = new JSONObject(contests.get(i).toString());
            if(me.getString("phase").equals("FINISHED"))break;
            spinnerArray.add(me.getString("name"));
            addRow(me.getString("name"));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item,spinnerArray
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}


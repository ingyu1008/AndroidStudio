package com.example.forth;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class TimeControl {
    private static long timeUpdate(long elapsedTime, ArrayList<Long> arrayList){
        try{
            elapsedTime += System.currentTimeMillis() - arrayList.get(1);
            while(elapsedTime >= 3600000){
                try{
                    arrayList.set(0,arrayList.get(0)+20L);
                    elapsedTime -= 3600000;
                    if(arrayList.get(0) >= 200){
                        arrayList.set(0,200L);
                        elapsedTime %= 3600000;
                    }
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        } catch(NullPointerException e){
            e.printStackTrace();
        }

        return elapsedTime;
    }

    public static long updateArrayAdapter(ArrayList<Long> arrayList, ArrayAdapter<Long> arrayAdapter, long elapsedTime, long i){
        elapsedTime = timeUpdate(elapsedTime, arrayList);
        arrayAdapter.clear();
        arrayAdapter.add(i);
        arrayAdapter.add(System.currentTimeMillis());
        arrayAdapter.add(elapsedTime);

        return elapsedTime;
    }
}

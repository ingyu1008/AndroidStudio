package com.example.forth;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class TimeControl {

    private static final long  x = 3600000L;
    public static long timeUpdate(long elapsedTime, ArrayList<Long> arrayList, Context context){
        try{
            elapsedTime += System.currentTimeMillis() - arrayList.get(1);
            while(elapsedTime >= x){
                long y = arrayList.get(0) - 20;
                arrayList.set(0,y);
                elapsedTime -= x;
                if(arrayList.get(0) <= 0L){
                    arrayList.set(0,0L);
                    elapsedTime %= x;
                }
            }
        } catch(NullPointerException e){
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        DataControl.writeData(arrayList, context);

        return elapsedTime;
    }

    public static long updateArrayAdapter(ArrayList<Long> arrayList, ArrayAdapter<Long> arrayAdapter, long elapsedTime, long i, Context context){
        try {
            if (i == 500) {
                elapsedTime = timeUpdate(elapsedTime, arrayList, context);
            } else {
                arrayList.set(0, i);
            }
            arrayList.set(1, System.currentTimeMillis());
            arrayList.set(2, elapsedTime);

            return arrayList.get(2);
        } catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return 0;
    }
    public static long updateArrayAdapter(ArrayList<Long> arrayList, ArrayAdapter<Long> arrayAdapter, long elapsedTime, long i, Context context, boolean UPDATE) {

        try {
            arrayList.set(0, i);
            elapsedTime += System.currentTimeMillis() - arrayList.get(1);
            arrayList.set(1, System.currentTimeMillis());
            arrayList.set(2, elapsedTime);

            return arrayList.get(2);

        } catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return 0;
    }
}

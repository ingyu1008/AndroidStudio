package com.example.forth;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataControl {

    public static String FILENAME = "savedData_4.dat";

    public static void writeData(ArrayList<Long> arrayList, Context context){

        try{
            FileOutputStream fileOutputStream = context.openFileOutput(FILENAME,Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(arrayList);
            objectOutputStream.close();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    public static ArrayList<Long> readData(Context context){
        ArrayList<Long> items = null;
        try{
            FileInputStream fileInputStream = context.openFileInput(FILENAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            items = (ArrayList<Long>) objectInputStream.readObject();
        } catch (FileNotFoundException e){
            items = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return items;
    }
}

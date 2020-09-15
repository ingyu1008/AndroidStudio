package com.example.codeforcescalender;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestCall {
    public static void restCall(String paramUrl){
        try {
            URL url = new URL(paramUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            if (conn.getResponseCode() != 200) {
                System.out.println("Failed: HTTP error code : " + conn.getResponseCode());
                throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
            } else {
                System.out.println("발송 성공");
            }

            String line = null;
            while((line = br.readLine()) != null){
                System.out.println(line);
            }
            br.close();
            conn.disconnect();
        } catch (IOException e) {
            System.out.println("RestCall Fail : " + e.getMessage());
        }
    }
}

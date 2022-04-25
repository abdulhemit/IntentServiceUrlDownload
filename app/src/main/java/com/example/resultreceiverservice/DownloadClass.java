package com.example.resultreceiverservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadClass extends IntentService {

    /**
     * @param name
     * @deprecated
     */
    private static final String TAG = "DownloadClass";
    public DownloadClass( ) {

        super("Download Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String currentThread = Thread.currentThread().getName();
        Log.d(TAG, "onHandleIntent: " + currentThread);

        String userInput = intent.getStringExtra("url");
        ResultReceiver  myResultReeceiver = intent.getParcelableExtra("receiver");
        String result="";
        URL url;
        HttpURLConnection httpURLConnection = null;

        try{
            url = new URL(userInput);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);


            int data = inputStreamReader.read();

            while(data != -1){
                char current = (char) data;
                result += current;
                data = inputStreamReader.read();
            }
            Bundle bundle = new Bundle();
            bundle.putString("websiteResult",result);
            myResultReeceiver.send(1,bundle);
        }catch (Exception e){
            e.printStackTrace();
            Bundle bundle = new Bundle();
            bundle.putString("websiteResult","Failed");
            myResultReeceiver.send(1,bundle);
        }
    }

    @Override
    public void onCreate() {
        String currentThread = Thread.currentThread().getName();
        Log.d(TAG, "onCreate: " + currentThread);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        String currentThread = Thread.currentThread().getName();
        Log.d(TAG, "onDestroy: " + currentThread);
        super.onDestroy();
    }
}

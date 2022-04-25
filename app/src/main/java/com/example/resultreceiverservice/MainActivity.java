package com.example.resultreceiverservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editTextTextPersonName);
        textView = findViewById(R.id.textView);
    }
    public void Download(View view) {
        myResultReceiver myResultReceiver = new myResultReceiver(null);
        Intent intent = new Intent(this,DownloadClass.class);
        String userInput = editText.getText().toString();
        intent.putExtra("url",userInput);
        textView.setText("Download....");
        intent.putExtra("receiver",myResultReceiver);
        startService(intent);

    }
    public class myResultReceiver extends ResultReceiver{

        public myResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultCode == 1 && resultData !=  null){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String result = resultData.getString("websiteResult");
                        textView.setText(result);
                    }
                });
            }

            super.onReceiveResult(resultCode, resultData);
        }
    }
}
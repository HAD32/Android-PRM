package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            String data = bundle.getString("data");
            textView.setText(data);
            super.handleMessage(msg);
        }
    };
    public void onClick(View view){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int count =0;
                while (count<10){
                    synchronized (this){
                        try{
                            wait(1000);
                            count--;
                            String data = "Counter: "+count;
                            Bundle bundle = new Bundle();
                            bundle.putString("data", data);
                            Message message = handler.obtainMessage();
                            message.setData(bundle);
                            handler.sendMessage(message);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                count = 0;
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        textView.setText("Button Click");

    }
}

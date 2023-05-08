package com.example.serviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonBegin = (Button) findViewById(R.id.button_begin);
        Button buttonEnd = (Button) findViewById(R.id.button_end);
        Intent intent = new Intent(this, SimpleService.class);

        MyReceiver receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SimpleService.ACTION);
        registerReceiver(receiver, intentFilter);

        buttonBegin.setOnClickListener(view -> {
            startService(intent);
        });

        buttonEnd.setOnClickListener(view -> {
            stopService(intent);
        });

    }
}
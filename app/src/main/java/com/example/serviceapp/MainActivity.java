package com.example.serviceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;




public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonBegin = (Button) findViewById(R.id.button_begin);
        Button buttonEnd = (Button) findViewById(R.id.button_end);



        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SimpleService.ACTION);

        // Receiverの登録、manifestにも追記する必要があるReceiverも存在する
        registerReceiver(mReceiver, intentFilter);
        MyReceiver receiver = new MyReceiver();
        registerReceiver(receiver, intentFilter);


        Intent intent = new Intent(this, SimpleService.class);
        buttonBegin.setOnClickListener(view -> {
            // serviceの起動
            startService(intent);
        });

        buttonEnd.setOnClickListener(view -> {
            // serviceの終了
            stopService(intent);
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    // UIの更新のためにReceiverをMainActivityに記述する
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView textView = (TextView) findViewById(R.id.textView);
            String msg = intent.getStringExtra("message");
            textView.setText(msg);
        }
    };




}
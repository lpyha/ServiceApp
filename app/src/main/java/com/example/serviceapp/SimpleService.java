package com.example.serviceapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/*          サービスのライフサイクル
 *
 *          ---開始---
 *              |
 *          ---onCrate(初回起動)初期化---
 *              |
 *          ---onStartCommand(サービスの起動)実処理---
 *              |
 *          ---サービスの実行---
 *              |
 *          ---onDestroy---
 *              |
 *          ---終了---
 */

public class SimpleService extends Service {
    private final String TAG = "SimpleService";
    private ScheduledExecutorService schedule;

    public static final String ACTION = "SimpleService Action";

    public SimpleService() {
    }

    // サービス初回起動時に実行
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    // サービスの起動都度に実行

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        // 定期的に実行
        schedule = Executors.newSingleThreadScheduledExecutor();
        // 1000msecごとに処理を実行
        schedule.scheduleAtFixedRate(()->{
            // 現在時刻をブロードキャスト配信
            Intent intent2 = new Intent(ACTION);
            intent2.putExtra("message", (new Date()).toString());
            // ブロードキャストの送信、受信側はレシーバーを作成しなければいけない-> BroadcastReceiver
            sendBroadcast(intent2);
            Log.i(TAG, "onStartCommand");
                }, 0, 1000, TimeUnit.MILLISECONDS);
        /*
         * onStartCommandの戻り値は、サービスがシステムによって強制終了されたときにどのように振る舞うかを表す。
         * ------------------------
         * START_NOT_STICKY             サービスを起動しない
         * START_STICKY                 サービスを再起動
         * START_REDELIVER_INTENT       終了前と同じインテントを使って再起動する
         * START_STICKY_COMPATIBILITY   再起動は保証されない(START_STICKYとの互換)
         */
        return START_STICKY;
    }

    /* サービスバインド時実行
     * サービスとアクティビティ間で通信するときに利用するメソッド
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        schedule.shutdown();
    }

}
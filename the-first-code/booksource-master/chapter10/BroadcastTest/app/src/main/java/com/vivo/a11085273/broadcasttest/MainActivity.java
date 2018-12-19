package com.vivo.a11085273.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private IntentFilter intentFilter2;
    private LocalReceiver localReceiver;

    private LocalBroadcastManager localBroadcastManager;

    private NetworkChangeReceiver networkChangeReceiver;
    private MyBroadcastReceiver myBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);//获取实例

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction("com.vivo.a11085273.broadcasttest.MY_BROADCAST");
//                sendOrderedBroadcast(intent,null);
                Intent intent = new Intent("com.vivo.a11085273.broadcasttest.LOCALBROADCAST");
                localBroadcastManager.sendBroadcast(intent);
            }
        });
//        intentFilter2 = new IntentFilter();
//        intentFilter2.addAction("com.vivo.a11085273.broadcasttest.MY_BROADCAST");
//        intentFilter2.addAction("com.vivo.a11085273.broadcasttest.MY_BROADCAST2");
//        intentFilter2.setPriority(100);
//        myBroadcastReceiver = new MyBroadcastReceiver();
//        registerReceiver(myBroadcastReceiver, intentFilter2);

        intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.vivo.a11085273.broadcasttest.LOCALBROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter2);//注册监听广播

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
        unregisterReceiver(myBroadcastReceiver);
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "received local broadcast", Toast.LENGTH_SHORT).show();
        }
    }

    class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                Toast.makeText(context, "network is Connected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
            }

//            Toast.makeText(context,"network changes", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.vivo.a11085273.broadcasttest2;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private IntentFilter intentFilter;
    private AnotherBroadcastReceiver anotherBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.vivo.a11085273.broadcasttest.MY_BROADCAST");
        intentFilter.setPriority(50);
        anotherBroadcastReceiver = new AnotherBroadcastReceiver();
        registerReceiver(anotherBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(anotherBroadcastReceiver);
    }
}

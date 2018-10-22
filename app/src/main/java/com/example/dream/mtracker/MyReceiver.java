package com.example.dream.mtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    public MyReceiver()
    {

    }


    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Called my receiver", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(context, NotificationService.class);
        context.startService(intent1);

    }
}

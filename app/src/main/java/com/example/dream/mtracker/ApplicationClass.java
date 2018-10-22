package com.example.dream.mtracker;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import java.util.Calendar;

public class ApplicationClass extends Application {

    DBFunction dbFunction=new DBFunction(this);
    @Override
    public void onCreate() {
        super.onCreate();

        dbFunction.insert_currency();

        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun)
        {
            // Code to run once
            dbFunction.setIconName();

            dbInsertion();


            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.commit();


            Intent notifyIntent = new Intent(this, MyReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (this, 0 , notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
//            calendar.set(Calendar.HOUR_OF_DAY, 21);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 1);


            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);

        }


    }

    //Common insertion ie first time insertion when a user log in to the app
    private void dbInsertion() {

        String type="";
        String name="";
        String email="";
        String phone="";

        DBFunction dbFunction=new DBFunction(this);

        SharedPreferences sp=getSharedPreferences("User",MODE_PRIVATE);
        type=sp.getString("Type","");
        if(type.equalsIgnoreCase("phone"))
        {
            phone=sp.getString("Number","");
        }
        else if(type.equalsIgnoreCase("firebase"))
        {
            name=sp.getString("Name","");
            email=sp.getString("Email","");
            phone=sp.getString("Phone","");
        }



        dbFunction.basicInsertion();
        dbFunction.common_insert(name,email,phone);


    }
}

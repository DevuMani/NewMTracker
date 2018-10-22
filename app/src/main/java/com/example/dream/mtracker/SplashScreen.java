package com.example.dream.mtracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    Boolean register;
    String type="";
    Boolean pass=false;
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    private FirebaseAuth mAuth;

    /** Called when the activity is first created. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */


                SharedPreferences sp=getSharedPreferences("User",MODE_PRIVATE);
                register=sp.getBoolean("Login",false);
                type=sp.getString("Type","");
                pass=sp.getBoolean("Password",false);
                if(register.equals(true)) {

                    if(pass==true) {
                        Intent mainIntent = new Intent(SplashScreen.this, PasswordSettingScreen.class);
                        mainIntent.putExtra("from","splash");
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();
                    }
                    else {
                        Intent mainIntent = new Intent(SplashScreen.this, GoogleHome.class);
                        mainIntent.putExtra("Type", type);
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();
                    }
                }
                else
                {
                    Boolean b=isNetworkAvailable();

                    if(b == true)
                    {
                        Intent mainIntent = new Intent(SplashScreen.this, Login.class);
                        mainIntent.putExtra("Type",type);
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();
                    }
                    else if( b== false)
                    {

                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                SplashScreen.this);
                        builder.setTitle("Network Connection");
                        builder.setMessage("Please... Check your internet connection before registering");
                        builder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

//                                        Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(SplashScreen.this,Login.class);
                                        startActivity(intent);

                                    }
                                });
                        builder.show();

                    }


                }
            }
        }, SPLASH_DISPLAY_LENGTH);



    }

        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }



}


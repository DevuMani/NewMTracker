package com.example.dream.mtracker;

import android.content.Intent;
import android.content.SharedPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordSettingScreen extends AppCompatActivity implements View.OnClickListener {

    String password_check = "";
    int set = 0;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bX;
    String password = "";
    int pass;

    ImageView[] imageViews = new ImageView[4];
    TextView password_heading;
    FloatingActionButton set_fab;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        password_check = "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_setting_screen);
        password_heading = findViewById(R.id.password_tv);
//        set_fab = findViewById(R.id.password_next);
        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        Boolean pass = sharedPreferences.getBoolean("Password", false);
        if (pass == true) {
            if (getIntent().getStringExtra("from").equalsIgnoreCase("splash")) {
                password_heading.setText("Enter Password");
                set = 3;
            } else if (getIntent().getStringExtra("from").equalsIgnoreCase("clear")) {
                set = 5;
                password_heading.setText("Old password");

            } else if (getIntent().getStringExtra("from").equalsIgnoreCase("profile_change")) {

                Toast.makeText(PasswordSettingScreen.this, "Change password", Toast.LENGTH_SHORT).show();
                set = 4;
                password_heading.setText("Old password");

            } else if (getIntent().getStringExtra("from").equalsIgnoreCase("profile_set")) {

                password_heading.setText("Set password");

            }

        } else {
            password_heading.setText("Set password");
        }
        imageViews[0] = findViewById(R.id.p1);
        imageViews[1] = findViewById(R.id.p2);
        imageViews[2] = findViewById(R.id.p3);
        imageViews[3] = findViewById(R.id.p4);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        b0 = findViewById(R.id.b0);
        bX = findViewById(R.id.bX);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        b0.setOnClickListener(this);
        bX.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == b1) {
            password = password + b1.getText();
        } else if (v == b2) {
            password = password + b1.getText();
        } else if (v == b3) {
            password = password + b1.getText();
        } else if (v == b4) {
            password = password + b1.getText();
        } else if (v == b5) {
            password = password + b1.getText();
        } else if (v == b6) {
            password = password + b1.getText();
        } else if (v == b7) {
            password = password + b1.getText();
        } else if (v == b8) {
            password = password + b1.getText();
        } else if (v == b9) {
            password = password + b1.getText();
        } else if (v == b0) {
            password = password + b1.getText();
        } else if (v == bX) {
            if (password.length() > 1)
                password = password.substring(0, password.length() - 1);
        }
        int l = password.length();
        if (l == 4) {
            if (set == 0) {
                pass = Integer.parseInt(password);
                set = 1;
                for (int i = 0; i < 4; i++) {
                    imageViews[i].setImageDrawable(getDrawable(R.drawable.password));
                }
                password = "";

            } else if (set == 1) {
                int conf_pass = Integer.parseInt(password);
                if (conf_pass == pass) {
                    DBFunction dbFunction = new DBFunction(PasswordSettingScreen.this);
                    dbFunction.updatePassword(password, "set");
                    Intent intent = new Intent(PasswordSettingScreen.this, Home.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(PasswordSettingScreen.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < 4; i++) {
                        imageViews[i].setImageDrawable(getDrawable(R.drawable.password));
                    }
                    password = "";
                }
            } else if (set == 3) {
                DBFunction dbFunction = new DBFunction(PasswordSettingScreen.this);
                String pass = dbFunction.fetchPassword();
                if (password.equals(pass)) {
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                    finish();
                } else {
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    for (int i = 0; i < 4; i++) {
                        imageViews[i].startAnimation(shake);
                    }
                    for (int i = 0; i < 4; i++) {
                        imageViews[i].setImageDrawable(getDrawable(R.drawable.password));
                    }

                }
            } else if (set == 4) {

                DBFunction dbFunction = new DBFunction(PasswordSettingScreen.this);
                String pass = dbFunction.fetchPassword();

                if (pass.equals(password)) {
                    set = 0;
                    password_heading.setText("Set Password");
                    password = "";
                } else {
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    for (int i = 0; i < 4; i++) {
                        imageViews[i].startAnimation(shake);
                    }
                    Toast.makeText(PasswordSettingScreen.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < 4; i++) {
                        imageViews[i].setImageDrawable(getDrawable(R.drawable.password));
                    }

                }

            }
        } else if (set == 5) {
            DBFunction dbFunction = new DBFunction(PasswordSettingScreen.this);
            String pass = dbFunction.fetchPassword();

            if (pass.equals(password)) {
                set = 0;
                dbFunction.updatePassword("", "clear");
                Intent intent = new Intent(PasswordSettingScreen.this, Profile_Settings.class);
                startActivity(intent);
                finish();

            } else {
                Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                for (int i = 0; i < 4; i++) {
                    imageViews[i].startAnimation(shake);
                }
                for (int i = 0; i < 4; i++) {
                    imageViews[i].setImageDrawable(getDrawable(R.drawable.password));
                }
                Toast.makeText(PasswordSettingScreen.this, "Incorrect Password", Toast.LENGTH_SHORT).show();

            }

        }


    }
}
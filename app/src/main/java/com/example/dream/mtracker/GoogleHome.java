package com.example.dream.mtracker;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.AccountKit;
import com.google.firebase.auth.FirebaseAuth;

public class GoogleHome extends AppCompatActivity {


    Button google_logout,phone_logout;
    TextView phonetext,hometext,categorytext;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    String intentData="";
    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_home);

        google_logout=findViewById(R.id.logout);
        phone_logout = findViewById(R.id.phone_logout);
        phonetext=findViewById(R.id.phone_text);
        hometext=findViewById(R.id.home_text);
        categorytext=findViewById(R.id.category_text);


        phonetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(GoogleHome.this,Profile_Settings.class);
                startActivity(intent);
            }
        });


        hometext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(GoogleHome.this,Home.class);
                startActivity(intent);

            }
        });

        categorytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(GoogleHome.this,AddCategory.class);
                startActivity(intent);
            }
        });
        intentData=getIntent().getStringExtra("Type");

        mAuth=FirebaseAuth.getInstance();

        if(intentData.equalsIgnoreCase("phone"))
        {
            google_logout.setVisibility(View.GONE);
            phone_logout.setVisibility(View.VISIBLE);
        }
        else
        {
            Toast.makeText(this, "Firebase", Toast.LENGTH_SHORT).show();
            google_logout.setVisibility(View.VISIBLE);
            phone_logout.setVisibility(View.GONE);


        }


        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()==null)
                {
                    firebaseFunction();

                }



            }
        };
        google_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
            }
        });

        phone_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AccountKit.logOut();
                startActivity(new Intent(GoogleHome.this,Login.class));
                finish();

            }
        });

    }

    private void firebaseFunction() {

        if(intentData.equalsIgnoreCase("phone"))
        {

        }
        else if(intentData.equalsIgnoreCase("firebase"))
        {
            startActivity(new Intent(GoogleHome.this,Login.class));
        }

    }
}

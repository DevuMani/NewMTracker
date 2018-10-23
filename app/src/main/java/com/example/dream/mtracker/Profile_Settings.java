package com.example.dream.mtracker;

import android.content.Intent;
import android.content.SharedPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class Profile_Settings extends AppCompatActivity {

    EditText et_name,et_phone;
    TextView im_currency;
    ImageView im_currency_select;
    RecyclerView settings;
    FloatingActionButton save;
    Profile_Settings_MainListAdapter ma;

    static String currency_symbol="";

    String name="";
    String phone="";
    String type="";
    String currency="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__settings);

//        dbInsertion();

        initView();

//        dbSetProfile();

        SharedPreferences sp=getSharedPreferences("User",MODE_PRIVATE);
        type=sp.getString("Type","");
//        currency=sp.getString("Currency","");
        im_currency.setText(currency_symbol);

        if(type.equalsIgnoreCase("phone"))
        {
            if((et_name.getText().toString()).equals(""))
            {
                et_name.requestFocus();
            }
            else
            {
                et_name.setCursorVisible(false);
            }

            phone=sp.getString("Number","");
            et_phone.setText(phone);
        }
        else if(type.equalsIgnoreCase("firebase"))
        {
            name=sp.getString("Name","");
            et_name.setText(name);
            et_phone.setText(sp.getString("Phone",""));

            if((et_phone.getText().toString()).equals(""))
            {
                et_phone.requestFocus();
            }


        }

        ma=new Profile_Settings_MainListAdapter(this, Profile_Settings_Data.getData());

        settings.setLayoutManager(new LinearLayoutManager(this));
        settings.setAdapter(ma);



        im_currency_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Profile_Settings.this, "Currency list coming soon.....", Toast.LENGTH_SHORT).show();

//                final CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");  // dialog title
//
//                picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");
//
//                picker.setListener(new CurrencyPickerListener() {
//                    @Override
//                    public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID) {
//                        // Implement your code here
//
//                        Toast.makeText(Profile_Settings.this, "Currency "+name, Toast.LENGTH_SHORT).show();
////                        im_currency.setImageDrawable();
//
//                        if(code.equalsIgnoreCase("INR"))
//                        {
//                            im_currency.setText(R.string.Rs);
//                        }
//                        else {
//                            im_currency.setText(symbol);
//                        }
//                        picker.dismiss();
//                    }
//
//                });

                startActivity(new Intent(Profile_Settings.this,ChooseCurrency.class));


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String new_name=et_name.getText().toString();
                String phone=et_phone.getText().toString();

                String currency=im_currency.getText().toString();

                if(currency.equalsIgnoreCase("set"))
                {
                    Toast.makeText(Profile_Settings.this, "First choose a currency", Toast.LENGTH_SHORT).show();
                }
                else {
                    dbUpdation(new_name, phone, currency);
                    SharedPreferences sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("Name",new_name);
                    editor.putString("Phone",phone);
                    editor.putString("Number",phone);
                    editor.putString("Currency",currency);
                    editor.apply();
                }


            }
        });



    }

    private void initView() {

        et_name=findViewById(R.id.profile_username);
        et_name.setFocusable(true);
        et_phone=findViewById(R.id.profile_phone);
        et_phone.setFocusable(true);
        im_currency=findViewById(R.id.profile_currency);
        im_currency_select=findViewById(R.id.profile_currency_select);
        save=findViewById(R.id.profile_save);

        settings=findViewById(R.id.profile_settings);

//        try {

//        getSupportActionBar().setTitle("Test");
            Objects.requireNonNull(getSupportActionBar()).setIcon(R.drawable.ic_save_black_24dp);

//        }
//        catch (NullPointerException ne)
//        {
//            Log.e("Icon set",""+ne.toString());
//        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (im_currency.getText().toString().equalsIgnoreCase("set"))
        {
            Toast.makeText(Profile_Settings.this, "First choose a currency", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(Profile_Settings.this, Home.class);
            startActivity(intent);
            finish();
        }
    }

//    //Common insertion ie first time insertion when a user log in to the app
//    private void dbInsertion() {
//
//        String type="";
//        String name="";
//        String email="";
//        String phone="";
//
//        DBFunction dbFunction=new DBFunction(Profile_Settings.this);
//
//        SharedPreferences sp=getSharedPreferences("User",MODE_PRIVATE);
//        type=sp.getString("Type","");
//        if(type.equalsIgnoreCase("phone"))
//        {
//            phone=sp.getString("Number","");
//        }
//        else if(type.equalsIgnoreCase("firebase"))
//        {
//            name=sp.getString("Name","");
//            email=sp.getString("Email","");
//            phone=sp.getString("Phone","");
//        }
//
//
//
//        dbFunction.basicInsertion();
//        dbFunction.common_insert(name,email,phone);
//
//    }

    private void dbUpdation(String new_name, String phone, String currency) {

        DBFunction dbFunction=new DBFunction(Profile_Settings.this);

        Boolean b=dbFunction.userUpdation(name,new_name,phone,currency);
        if(b==true)
        {
            Intent intent = new Intent(Profile_Settings.this,Home.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Updation unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }
}

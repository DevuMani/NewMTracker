package com.example.dream.mtracker;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ChooseCurrency extends AppCompatActivity {

    TextView tv;
    RecyclerView rv_currency;
    FloatingActionButton fab;
    static String choose_currency_symbol="";

    ArrayList<Currency_Model> currency_models;
    GridLayoutManager gridLayoutManager;
    CurrencyListAdapters adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_currency);

        initView();

        init();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Profile_Settings.currency_symbol=choose_currency_symbol;
                startActivity(new Intent(ChooseCurrency.this,Profile_Settings.class));
            }
        });
    }

    private void init() {
        currency_models=new ArrayList<>();
        setCurrencyToList();
        gridLayoutManager = new GridLayoutManager(ChooseCurrency.this, 4,GridLayoutManager.VERTICAL,false);
        adapter = new CurrencyListAdapters(ChooseCurrency.this, currency_models);

        rv_currency.setLayoutManager(gridLayoutManager);
        rv_currency.setAdapter(adapter);
    }

    private void setCurrencyToList() {

        currency_models=new ArrayList<>();

        DBFunction dbFunction=new DBFunction(this);
        currency_models=dbFunction.fetchCurrencyList();
    }

    private void initView() {

        tv=findViewById(R.id.tv_currency);
        rv_currency=findViewById(R.id.rv_currency);
        fab=findViewById(R.id.fab_currency);

        tv.setText(R.string.currency_warning);

    }
}

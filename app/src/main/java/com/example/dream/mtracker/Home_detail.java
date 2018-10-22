package com.example.dream.mtracker;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Home_detail extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView month_detail;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Month_Data> myMonthData;
    String date="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_month);


        initView();

        init();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home_detail.this,IncomeExpenseActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {

//        myMonth=new ArrayList<>();
        myMonthData = new ArrayList<>();
        setDateToList();

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        HomeDetailAdapter adapter= new HomeDetailAdapter(this,myMonthData);

        month_detail.setLayoutManager(linearLayoutManager);
        month_detail.setAdapter(adapter);


    }

    private void setDateToList() {

        DBFunction dbFunction=new DBFunction(this);

//        myMonth = new ArrayList<>();
        myMonthData = new ArrayList<>();

//        myMonth=dbFunction.fetchMonth(date);
        myMonthData=dbFunction.fetchMonthData(date);
    }

    private void initView() {

        fab=findViewById(R.id.save_button2);
        month_detail=findViewById(R.id.month_list_recycler);

        date=getIntent().getStringExtra("Date");

        Toast.makeText(this, "Date home "+date, Toast.LENGTH_SHORT).show();
    }
}

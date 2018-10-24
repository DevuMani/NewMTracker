package com.example.dream.mtracker;

import android.app.ActivityOptions;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class Home extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton fab;
    TextView year_text;
    RecyclerView year_list;
    ImageView imageView;
    LinearLayoutManager linearLayoutManager;
    RelativeLayout left_click,right_click;
    ArrayList<Home_Year_Data> myYearData;

    int year=0;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,GoogleHome.class);
        intent.putExtra("Type","firebase");
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_year);


        initView();

        init();

        animation();

        DBFunction dbFunction=new DBFunction(this);
        dbFunction.display();

        fab.setOnClickListener(this);
    }

    private void animation() {

        Slide slide = new Slide(Gravity.LEFT);
        slide.setDuration(250);
        getWindow().setReenterTransition(slide);

        Fade fade=new Fade();
        fade.setDuration(2000);
        getWindow().setExitTransition(fade);
    }


    private void initView() {
        fab=findViewById(R.id.save_button2);
        year_list=findViewById(R.id.year_list_recycler);
        year_text=findViewById(R.id.home_year_text);
        year= Calendar.getInstance().get(Calendar.YEAR);
        year_text.setText(""+year);

        year_text.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()){
            @Override
            public void onSwipeRight() {
                int year1=Integer.parseInt(year_text.getText().toString());
                year1=year1+1;
                year_text.setText(""+year1);
                init();
            }

            @Override
            public void onSwipeLeft() {
                int year1=Integer.parseInt(year_text.getText().toString());

                year1=year1-1;

                year_text.setText(""+year1);
                init();

            }
        });
    }
    private void init() {
        myYearData = new ArrayList<>();
        setYearDataToList(year_text.getText().toString());
        linearLayoutManager = new LinearLayoutManager(Home.this);
        if(myYearData.size()==0)
        {
            HomeYearAdapter adapter = new HomeYearAdapter(this, myYearData);
//            year_list.setBackground(getDrawable(R.drawable.no_transaction_found));
            year_list.setLayoutManager(linearLayoutManager);
            year_list.setAdapter(adapter);
        }
        else {
            HomeYearAdapter adapter = new HomeYearAdapter(this, myYearData);
//            year_list.setBackground(getDrawable(R.drawable.black_arrow));
            year_list.setLayoutManager(linearLayoutManager);
            year_list.addItemDecoration(new SpacingItemDecoration(10));
            year_list.setAdapter(adapter);
        }
    }

    private void setYearDataToList(String year) {

        DBFunction dbFunction=new DBFunction(this);

        myYearData = new ArrayList<>();

        myYearData=dbFunction.fetchYear(year);
//        myYearData=dbFunction.fetchYearWorking();

    }

    @Override
    public void onClick(View view) {
     if (view == fab)
        {
            Bundle bundle= ActivityOptions.makeSceneTransitionAnimation(Home.this).toBundle();
            Intent intent=new Intent(Home.this,IncomeExpenseActivity.class);
            startActivity(intent,bundle);
        }
    }

    public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int offset;
        public SpacingItemDecoration(int i) {
            offset = i;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(offset*5, offset, offset*5, offset);
        }
    }
}

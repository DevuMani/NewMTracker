package com.example.dream.mtracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class IncomeExpenseActivity extends AppCompatActivity {
    static String g_date="";
    TextView income;
    TextView expense;

    static Chip save_date;
    Switch data_switch;
    TextInputEditText amount, desc;
    FloatingActionButton save;
    RecyclerView category_list;
    ArrayList<Category_Data> myIconList;
    GridLayoutManager gridLayoutManager;
    CategoryListAdapter adapter;
    public static String iconsymbol = "";
    static LinearLayout linearLayout;

    int transaction_id=0;
    int update=0;
    ArrayList<EditModel> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animation();
        setContentView(R.layout.activity_income_expense);

        initView();

        init();
        transaction_id=getIntent().getIntExtra("Transaction_Id",0);

        if(transaction_id==0)
        {
            //null
            setDefaultDate();
            update=0;
        }
        else
        {
            setDataToList();
            update=1;
        }

        data_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switchCheck();

            }
        });

        save_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveFunction();
            }
        });

    }

    private void setDataToList() {

        dataList=new ArrayList<>();
        DBFunction dbFunction=new DBFunction(this);
        dataList=dbFunction.fetchTransactionData(transaction_id);


        int trans_id=0;
        String transaction_date="";
        Double transaction_amount=0.0;
        String transaction_desc="";
        int cat_id=0;
        String category_name="";
        int type_id=0;
        String type_name="";

        EditModel editModel;
        if(dataList.size()>0)
        {
            editModel=dataList.get(0);

            trans_id=editModel.getTrans_id();
            transaction_date=editModel.getTransaction_date();
            transaction_amount=editModel.getTransaction_amount();
            transaction_desc=editModel.getTransaction_desc();
            cat_id=editModel.getCat_id();
            category_name=editModel.getCat_name();
            type_id=editModel.getType_id();
            type_name=editModel.getType_name();

            Toast.makeText(this, ""+trans_id, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, ""+transaction_date, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, ""+transaction_amount, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, ""+transaction_desc, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, ""+cat_id, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, ""+category_name, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, ""+type_id, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, ""+type_name, Toast.LENGTH_SHORT).show();


//            MaterialDrawableBuilder.IconValue m=MaterialDrawableBuilder.IconValue.valueOf(category_name);

            if(type_id==1)
            {
                data_switch.setChecked(false);
                income.setTextColor(getResources().getColor(R.color.green));
                expense.setTextColor(Color.BLACK);
            }
            else
            {
                data_switch.setChecked(true);
                expense.setTextColor(Color.RED);
                income.setTextColor(Color.BLACK);

            }

            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf=new SimpleDateFormat("dd MMM yyyy");
            Date date=null;

            try {
                date=simpleDateFormat.parse(transaction_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            transaction_date=sdf.format(date);
            save_date.setText(transaction_date);
            String cash=""+transaction_amount;
            amount.setText(cash);
            iconsymbol=category_name;
            desc.setText(transaction_desc);


        }


    }

    private void setDefaultDate() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");

        String date=format.format(c.getTime());

        save_date.setText(date);
    }


    private void init() {

        myIconList = new ArrayList<>();
        setIconToList();
        gridLayoutManager = new GridLayoutManager(IncomeExpenseActivity.this, 2,GridLayoutManager.HORIZONTAL,false);

        adapter = new CategoryListAdapter(IncomeExpenseActivity.this, myIconList,data_switch.isChecked());

        category_list.setLayoutManager(gridLayoutManager);
        category_list.setAdapter(adapter);


    }

    private void initView() {

        income = findViewById(R.id.income_text);
        expense = findViewById(R.id.expense_text);
        data_switch = findViewById(R.id.typechoose);
        save_date = findViewById(R.id.datechoose);
        amount = findViewById(R.id.amount_edittext);
        desc = findViewById(R.id.desc_edittext);
        save = findViewById(R.id.save_button);
        category_list = findViewById(R.id.category_list_recycler);
        switchCheck();
    }
    public void switchCheck() {
        Boolean b = data_switch.isChecked();
        if (b == true) {
            expense.setTextColor(Color.RED);
            income.setTextColor(Color.BLACK);

        } else {
            income.setTextColor(getResources().getColor(R.color.green));
            expense.setTextColor(Color.BLACK);
        }

        init();

    }

    public void setIconToList() {
        int type_id=-1;


        myIconList = new ArrayList<>();

        DBFunction dbFunction=new DBFunction(IncomeExpenseActivity.this);

        Boolean transaction= data_switch.isChecked();


        if(transaction==true)
        {
//            fetch id of expense from category table
            type_id=dbFunction.fetchTypeID("expense");

        }
        else
        {
            type_id=dbFunction.fetchTypeID("income");
        }
        if(type_id==-1)
        {
            Log.e("Error Fetching id",""+type_id);
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        else
        {
//            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            myIconList=dbFunction.categoryFetch(type_id,"old");
        }
    }

    private void animation() {

        Slide slide = new Slide(Gravity.LEFT);
        slide.setDuration(250);
        getWindow().setEnterTransition(slide);

        Fade fade=new Fade();
        fade.setDuration(300);
        getWindow().setExitTransition(fade);
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);


        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {

            Calendar c = Calendar.getInstance();
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            month=month+1;//because month value is got as 9 if oct is selected
            String mon="";

            if(month<10)
            {
                mon="0";
            }

            String d="";
            if(day<10)
            {
                d="0";
            }

            g_date=year+"-"+mon+month+"-"+d+day;
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
            String date=format.format(calendar.getTime());
                save_date.setText(date);
        }
    }

    private void saveFunction() {

        //Flag to set as true if all values are valid
        Boolean fail=false;

//        int type_id=0;
        int cat_id=0;

        DBFunction dbFunction=new DBFunction(IncomeExpenseActivity.this);

        String tb_amount=amount.getText().toString();
        String tb_desc=desc.getText().toString();
        String tb_categories= iconsymbol;



        cat_id=dbFunction.fetchCategoryId(tb_categories);


        String date=save_date.getText().toString();

        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");

        Date tb_date=null;

        try {
            tb_date = format.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }



        if(tb_amount.equals(""))
        {
            Toast.makeText(this, "Fill an amount ", Toast.LENGTH_SHORT).show();
            fail=true;
        }
        else if (tb_categories.equals(""))
        {
            Toast.makeText(this, "Choose a category", Toast.LENGTH_SHORT).show();
            fail=true;
        }
        else if (tb_date==null)
        {
            Toast.makeText(this, "Choose a date", Toast.LENGTH_SHORT).show();
            fail=true;
        }


        if(fail==false) {

            Toast.makeText(this, "Category id "+cat_id, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Amount "+tb_amount, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Description "+tb_desc, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Categories "+tb_categories, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Date "+tb_date, Toast.LENGTH_SHORT).show();

            Boolean b= dbFunction.expenditureInsert(transaction_id,cat_id, tb_amount, tb_desc, g_date,update);

            if (b == true) {


                if(update==0)
                {
                    Toast.makeText(this, "Successfully inserted expenditure ", Toast.LENGTH_SHORT).show();

                }
                else if(update==0)
                {
                    Toast.makeText(this, "Successfully updated expenditure ", Toast.LENGTH_SHORT).show();

                }
                Intent intent=new Intent(this,Home.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Expenditure insertion failed", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Enter valid data's", Toast.LENGTH_SHORT).show();
        }
    }

}
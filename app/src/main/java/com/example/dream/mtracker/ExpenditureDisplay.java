package com.example.dream.mtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;

public class ExpenditureDisplay extends AppCompatActivity {
    MaterialIconView iconView;
    TextView cat_name,type,amount,date,desc;
    FloatingActionButton del,edit;

    int transaction_id=0;

    ArrayList<EditModel> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure_display);

        initView();

        transaction_id=getIntent().getIntExtra("ID",0);

        setDataToList(transaction_id);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBFunction dbFunction=new DBFunction(ExpenditureDisplay.this);
                Boolean b =dbFunction.deleteTransaction(transaction_id);
                if(b==true)
                {
                    Toast.makeText(ExpenditureDisplay.this, "Succefully deleted", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ExpenditureDisplay.this,Home.class);
//                    intent.putExtra("date",Home_detail.date);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ExpenditureDisplay.this, "Couldn't delete", Toast.LENGTH_SHORT).show();

                }

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ExpenditureDisplay.this,IncomeExpenseActivity.class);
                intent.putExtra("Transaction_Id",transaction_id);
                startActivity(intent);
            }
        });

    }

    private void setDataToList(int transaction_id) {

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
            Toast.makeText(this, ""+cat_id, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, ""+category_name, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, ""+type_id, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, ""+type_name, Toast.LENGTH_SHORT).show();


            MaterialDrawableBuilder.IconValue m=MaterialDrawableBuilder.IconValue.valueOf(category_name);
            iconView.setIcon(m);
            cat_name.setText(category_name);
            type.setText(type_name);
            amount.setText(String.format("%s", transaction_amount));
            date.setText(transaction_date);
            desc.setText(transaction_desc);

        }

    }

    private void initView() {

        iconView=findViewById(R.id.display_image);
        cat_name=findViewById(R.id.display_cat_name);
        type= findViewById(R.id.display_type);
        amount=findViewById(R.id.display_amount);
        date=findViewById(R.id.display_date);
        desc=findViewById(R.id.display_desc);

        del=findViewById(R.id.display_delete);
        edit=findViewById(R.id.display_edit);

    }


}

package com.example.dream.mtracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewCategoryChooser extends AppCompatDialogFragment {

    RecyclerView category_list;
    ArrayList<Category_Data> myIconList;
    GridLayoutManager gridLayoutManager;
    NewCategoryListAdapter adapter;

    Boolean type=false;

    static String new_iconName="";
    static int new_cid=0;

    Context context;

//    public static NewCategoryChooser newCategoryChooser=new NewCategoryChooser();


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater=Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view=inflater.inflate(R.layout.activity_new_category_chooser,null);

        type=getArguments().getBoolean("type",false);
        builder.setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (!new_iconName.equalsIgnoreCase(""))
                        {
                            AddCategory.iconName=new_iconName;
                            AddCategory.c_id=new_cid;
                        }
                        Intent intent=new Intent(getContext(),AddCategory.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("type",type);
                        Objects.requireNonNull(getContext()).startActivity(intent);
                        dismiss();
                    }
                });

        category_list=view.findViewById(R.id.new_category_recyclerView);

        init();


        return builder.create();
    }



    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_new_category_chooser);
//
//
//        category_list=findViewById(R.id.new_category_recyclerView);
//        init();
//
//
//    }

    private void init() {

        myIconList = new ArrayList<>();
        setIconToList();
        gridLayoutManager = new GridLayoutManager(getContext(), 4,RecyclerView.VERTICAL,false);

        adapter = new NewCategoryListAdapter(getContext(), myIconList,type);

        category_list.setLayoutManager(gridLayoutManager);
        category_list.setAdapter(adapter);


    }
    public void setIconToList() {


        int type_id=-1;


        myIconList = new ArrayList<>();

        DBFunction dbFunction=new DBFunction(getContext());


        if(type==true)
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
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            myIconList=dbFunction.categoryFetch(type_id,"new");

        }
    }


//    @Override
//    public void setter(String iconName,int c_id) {
//
//        //interface code
//        this.iconName=iconName;
//        this.c_id=c_id;
//        AddCategory.iconName=iconName;
//        AddCategory.c_id=c_id;
////        Intent intent=new Intent(getActivity(),AddCategory.class);
////        getActivity().startActivity(intent);
//        dismiss();
//
//    }
}

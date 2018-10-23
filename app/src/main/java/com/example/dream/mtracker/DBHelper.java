package com.example.dream.mtracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dreams on 8/2/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    Context c;
    SQLiteDatabase sqLiteDb;

    public DBHelper(Context context) {
        super(context, "db1", null, 1);

    }

    public void openConnection()
    {
        sqLiteDb=getWritableDatabase();
    }

    public void closeConnection()
    {
        sqLiteDb.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {

//        String query="create table tb_Reg(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(30) NOT NULL,phno VARCHAR(10),uname VARCHAR(20),pass VARCHAR(20))";
        String user_table="create table tb_user(user_id INTEGER PRIMARY KEY AUTOINCREMENT,user_name VARCHAR(30),user_phno VARCHAR(10),user_email VARCHAR(20),user_currency VARCHAR(10),user_password VARCHAR(20),user_status INTEGER DEFAULT 0)";
        String type_table="create table tb_type(type_id INTEGER PRIMARY KEY AUTOINCREMENT,type_name VARCHAR(20))";
        String category_table="create table tb_category(category_id INTEGER PRIMARY KEY AUTOINCREMENT,type_id INTEGER,category_user_name VARCHAR(20),category_name VARCHAR(20),foreground_color VARCHAR(10),status INTEGER)";
        String transaction_table="create table tb_transaction(transaction_id INTEGER PRIMARY KEY AUTOINCREMENT,category_id INTEGER,transaction_amount DOUBLE, transaction_date DATE, transaction_description VARCHAR(50))";
        String currency_table="create table tb_currency(currency_id INTEGER PRIMARY KEY AUTOINCREMENT,currency_name VARCHAR(30),currency_code VARCHAR(10), currency_symbol VARCHAR(5))";

        db.execSQL(user_table);
        Log.i("user_table creation", "Successful "+user_table);
        db.execSQL(type_table);
        Log.i("type_table creation", "Successful "+type_table);
        db.execSQL(category_table);
        Log.i("category_table creation", "Successful "+category_table);
        db.execSQL(transaction_table);
        Log.i("transaction_tb creation", "Successful "+transaction_table);
        db.execSQL(currency_table);
        Log.i("currency_table creation", "Successful "+currency_table);

    }


    public boolean insertData(String query)
    {
        Log.d("Insert/Delete query",query);
        try
        {
            sqLiteDb.execSQL(query);
            return true;
        }
        catch (Exception e)
        {
            Log.e("Insert/Delete row",e.toString());
            return false;
        }
    }

    public Cursor selectData(String query)
    {
        Log.d("Select Query",query);
        Cursor c=sqLiteDb.rawQuery(query,null);
        return c;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}

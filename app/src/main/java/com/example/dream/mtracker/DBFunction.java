package com.example.dream.mtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DBFunction {

    Context context;
    DBHelper dbHelper;

    HashMap< String,Integer> hmap = new HashMap< String,Integer>();

    DBFunction(Context context)
    {

        hmap.put("January",1);
        hmap.put("February",2);
        hmap.put("March",3);
        hmap.put("April",4);
        hmap.put("May",5);
        hmap.put("June",6);
        hmap.put("July",7);
        hmap.put("August",8);
        hmap.put("September",9);
        hmap.put("October",10);
        hmap.put("November",11);
        hmap.put("December",12);



        this.context = context;
        dbHelper=new DBHelper(context);
    }

    public void basicInsertion() {

        dbHelper.openConnection();
//
        String tb_type_ins1="insert into tb_type(type_name) values('" + "income" + "')";
        Boolean b1 = dbHelper.insertData(tb_type_ins1);

        String tb_type_ins2="insert into tb_type(type_name) values('" + "expense" + "')";
        Boolean b2 = dbHelper.insertData(tb_type_ins2);
//        Log.d("Insert",ins);
        if (b1 == true) {
            Log.i("tb_type insertion", "Successful ");
        } else {
            Log.i("tb_type insertion", "Failed");
        }

        if (b2 == true) {
            Log.i("tb_type insertion", "Successful "+tb_type_ins2);
        } else {
            Log.i("tb_type insertion", "Failed"+ tb_type_ins2);
        }


        dbHelper.closeConnection();
    }

    public void common_insert(String name,String email,String phone) {
        dbHelper.openConnection();
//
        String ins="insert into tb_user(user_name,user_email,user_phno,user_status) values('" + name + "','" + email + "','" + phone + "',"+1+")";
        Boolean b = dbHelper.insertData(ins);

//        Log.d("Insert",ins);
        if (b == true) {
            Toast.makeText(context, "Successfully inserted user " + name + " to tb_user", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "User insertion failed", Toast.LENGTH_SHORT).show();

        }


        dbHelper.closeConnection();
    }

    public Boolean userUpdation(String old_name,String new_name,String phone,String currency) {
        String tb_name="";
        String tb_email="";
        String tb_password="";

        dbHelper.openConnection();

        String sel="";
        if(old_name.equals(new_name))
        {
            sel="select user_email,user_password from tb_user where user_name='"+old_name+"'";
            tb_name=old_name;
        }
        else
        {
            sel="select * from tb_user where user_name='"+old_name+"'";
            tb_name=new_name;
        }

        Cursor user_sel=dbHelper.selectData(sel);

        while(user_sel.moveToNext())
        {

            tb_email=user_sel.getString(0);
            tb_password=user_sel.getString(1);
        }


        String del="DELETE from tb_user where user_name='"+old_name+"'";
        Boolean user_del=dbHelper.insertData(del);
        Boolean b=false;
        if(user_del==true)
        {
            String ins="insert into tb_user(user_name,user_phno,user_email,user_currency,user_password,user_status) values('" + tb_name + "','" + phone + "','" + tb_email + "','" + currency + "','" + tb_password + "',"+1+")";
            b = dbHelper.insertData(ins);

//            Log.d("Insert",ins);
            if (b == true) {
                Toast.makeText(context, "Successfully inserted user " + new_name + " to tb_user", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences=context.getSharedPreferences("User",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("Name",tb_name);
                editor.putString("Email",tb_email);
                editor.putString("Phone",phone);
                editor.putString("Currency",currency);
                editor.apply();

            } else {
                Toast.makeText(context, "User insertion failed", Toast.LENGTH_SHORT).show();

            }

        }
        else
        {
            Log.d("Deletion unsuccessful ",del);
        }

        dbHelper.closeConnection();

        return b;

    }

    public void display() {
        dbHelper.openConnection();

        String sel_cuurency="SELECT * FROM tb_type";
        Cursor currency_cursor=dbHelper.selectData(sel_cuurency);
//
//        String sel="SELECT * FROM tb_RegUser";
//        Cursor cursor=dbHelper.selectData(sel);
//        String sel2="select * from tb_RegPhn";
//        Cursor cursor2=dbHelper.selectData(sel2);

        Log.d("Type Table","Type_id   Type_name");
        while(currency_cursor.moveToNext())
        {
            Log.d("Type Table",currency_cursor.getInt(0) +" "+ currency_cursor.getString(1) );
        }

        dbHelper.closeConnection();
    }

    public void insert_currency() {
        dbHelper.openConnection();

        String n[]={"Euro","United States Dollar"," British Pound","Japanese Yen","Australian Dollar","India Rupee"};
        String c[]={"EUR","USD","GBP","JPY","AUD","INR"};
        String s[]={"€","$","£","¥","$","₹"};






            for(int i=0;i<n.length;i++)
            {
                Boolean valid=check(c[i]);

                if(!valid) {

                        String ins = "insert into tb_currency(currency_name,currency_code,currency_symbol) values('" + n[i] + "','" + c[i] + "','" + s[i] + "')";
                        Boolean b = dbHelper.insertData(ins);

                        if (b == true) {
                        Toast.makeText(context, "Successfully inserted "+i+" currency " + c[i] + " to tb_currency", Toast.LENGTH_SHORT).show();
                        }
                        else {
                        Toast.makeText(context, "Currency insertion of"+i+" failed", Toast.LENGTH_SHORT).show();

                        }
                }
                else
                {
                    Toast.makeText(context, "This country is already inserted", Toast.LENGTH_SHORT).show();
                }
            }





    }

    private Boolean check(String code) {

        dbHelper.openConnection();

        String sel_cuurency="SELECT * FROM tb_currency WHERE currency_code='"+code+"'";
        Cursor currency_cursor=dbHelper.selectData(sel_cuurency);

        if(currency_cursor.moveToNext())
        {
            return true;
        }
        else
        {
            return false;
        }


    }

    public Integer fetchTypeID(String type_name) {
        dbHelper.openConnection();
        String sel="SELECT type_id FROM tb_type WHERE type_name='"+type_name+"'";
        Cursor type=dbHelper.selectData(sel);
        int type_id=0;

        if(type.moveToNext()) {

            type_id= type.getInt(0);
        }
        else
        {
            type_id= -1;
        }

        dbHelper.closeConnection();
        return type_id;
    }

    public Integer fetchCategoryId(String category_name) {
        dbHelper.openConnection();
        int cat_id=0;
        String sel="SELECT category_id FROM tb_category WHERE category_name='"+category_name+"'";
        Cursor type=dbHelper.selectData(sel);
        if(type.moveToNext()) {

            cat_id=type.getInt(0);
        }
        else
        {
            cat_id= -1;
        }
        dbHelper.closeConnection();

        return cat_id;
    }

    public ArrayList<Category_Data> categoryFetch(int type_id) {

        dbHelper.openConnection();

        Category_Data c_data;
        ArrayList<Category_Data> myIconList=new ArrayList<>();
        myIconList.clear();

        String sel="SELECT category_id,category_name FROM tb_category WHERE type_id="+type_id+" and status="+0;
        Cursor cursor=dbHelper.selectData(sel);


        while(cursor.moveToNext()) {

            c_data = new Category_Data();

            c_data.setCategory_id(cursor.getInt(0));
            c_data.setCategory_name(cursor.getString(1));

            myIconList.add(c_data);

        }
        String sel1="SELECT category_id,category_name FROM tb_category WHERE type_id="+3+" and status="+0;
        Cursor cursor1=dbHelper.selectData(sel1);


        while(cursor1.moveToNext()) {

            c_data = new Category_Data();

            c_data.setCategory_id(cursor1.getInt(0));
            c_data.setCategory_name(cursor1.getString(1));

            myIconList.add(c_data);

        }



        dbHelper.closeConnection();

        return myIconList;
    }

    public void setIconName() {

        String expense_category_used[]={"category_groceries","category_car","category_clothes","category_entertainment","category_fastfood","category_gift","category_health","category_fuel","category_medicine","category_maintainance","category_toiletries","category_drinks"};
        String expense_user_name_used[]={"Groceries","Car","Clothes","Entertainment","Fast food","Gift","Health","Fuel","Medicine","Maintainance","Toiletries","Drinks"};
        String income_color[]={};

        String expense_category_unused[]={"category_cart","category_bills","category_communication","category_eatout","category_cake","category_coffee","category_alcohol","category_ironing","category_laundry","category_dress","category_home","category_makeup","category_pets","category_sports","category_taxi","category_airplane","category_travel","category_train","category_bus","category_account","category_shirt"};
        String expense_user_name_unused[]={"Cart","Bills","Communication","Eat Out","Cake","Coffee","Alcohol","Ironing","Laundry","Dress","Home","Makeup","Pets","Sports","Taxi","Airplane","Travel","Train","Bus","Account","Shirt"};


        String income_category_used[]={"category_deposit","category_salary"};
        String income_user_name_used[]={"Deposit","Salary"};
        String expense_color[]={};

        String income_category_unused[]={"category_saving"};
        String income_user_name_unused[]={"Saving"};



        dbHelper.openConnection();



        for(int i=0;i<income_category_used.length;i++)
        {
            Boolean valid=checkCategory(income_category_used[i]);

            if(!valid) {

                String ins = "insert into tb_category(type_id,category_user_name,category_name,foreground_color,status) values(1,'" + income_user_name_used[i] + "','" + income_category_used[i] + "','#000000',0)";
                Boolean b = dbHelper.insertData(ins);

                if (b == true) {
                    Toast.makeText(context, "Successfully inserted "+i+" category " + income_category_used[i] + " to tb_category", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Category insertion of"+i+" failed", Toast.LENGTH_SHORT).show();

                }
            }
            else
            {
                Toast.makeText(context, "This category is already inserted", Toast.LENGTH_SHORT).show();
            }
        }
        for(int i=0;i<expense_category_used.length;i++)
        {
            Boolean valid=checkCategory(expense_category_used[i]);

            if(!valid) {

                String ins = "insert into tb_category(type_id,category_user_name,category_name,foreground_color,status) values(2,'" + expense_user_name_used[i] + "','" + expense_category_used[i] + "','#000000',0)";
                Boolean b = dbHelper.insertData(ins);

                if (b == true) {
                    Toast.makeText(context, "Successfully inserted "+i+" category " + expense_category_used[i] + " to tb_category", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Category insertion of"+i+" failed", Toast.LENGTH_SHORT).show();

                }
            }
            else
            {
                Toast.makeText(context, "This category is already inserted", Toast.LENGTH_SHORT).show();
            }
        }
        for(int i=0;i<income_category_unused.length;i++)
        {
            Boolean valid=checkCategory(income_category_unused[i]);

            if(!valid) {

                String ins = "insert into tb_category(type_id,category_user_name,category_name,foreground_color,status) values(1,'" + income_user_name_unused[i] + "','" + income_category_unused[i] + "','#000000',1)";
                Boolean b = dbHelper.insertData(ins);

                if (b == true) {
                    Toast.makeText(context, "Successfully inserted "+i+" category " + income_category_unused[i] + " to tb_category", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Category insertion of"+i+" failed", Toast.LENGTH_SHORT).show();

                }
            }
            else
            {
                Toast.makeText(context, "This category is already inserted", Toast.LENGTH_SHORT).show();
            }
        }
        for(int i=0;i<expense_category_unused.length;i++)
        {
            Boolean valid=checkCategory(expense_category_unused[i]);

            if(!valid) {

                String ins = "insert into tb_category(type_id,category_user_name,category_name,foreground_color,status) values(2,'" + expense_user_name_unused[i] + "','" + expense_category_unused[i] + "','#000000',1)";
                Boolean b = dbHelper.insertData(ins);

                if (b == true) {
                    Toast.makeText(context, "Successfully inserted "+i+" category " + expense_category_unused[i] + " to tb_category", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Category insertion of"+i+" failed", Toast.LENGTH_SHORT).show();

                }
            }
            else
            {
                Toast.makeText(context, "This category is already inserted", Toast.LENGTH_SHORT).show();
            }
        }

        //Should be inserted for every data
        String ins_18="insert into tb_category(type_id,category_user_name,category_name,foreground_color,status) values(3,'Add','category_plus','#388E3C',0)";
        Log.i("Insert 18",""+dbHelper.insertData(ins_18));

//        String ins_1="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(2,'Cart','CART_OUTLINE','#4FC3F7','#B3E5FC')";
//        Log.i("Insert 1",""+dbHelper.insertData(ins_1));
//        String ins_2="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(2,'Movie','MOVIE','#7E57C2','#D1C4E9')";
//        Log.i("Insert 2",""+dbHelper.insertData(ins_2));
//        String ins_3="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(2,'Food','FOOD','#66BB6A','#C8E6C9')";
//        Log.i("Insert 3",""+dbHelper.insertData(ins_3));
//        String ins_4="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(2,'Bills','RECEIPT','#009688','#80CBC4')";
//        Log.i("Insert 4",""+dbHelper.insertData(ins_4));
//        String ins_5="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(2,'Snacks','FOOD_FORK_DRINK','#AB47BC','#E1BEE7')";
//        Log.i("Insert 5",""+dbHelper.insertData(ins_5));
//        String ins_6="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(2,'Fruits','FOOD_APPLE','#fc0a0a','#ff5959')";
//        Log.i("Insert 6",""+dbHelper.insertData(ins_6));
//        String ins_7="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(2,'Clothes','TSHIRT_CREW','#FFEB3B','#FFF59D')";
//        Log.i("Insert 7",""+dbHelper.insertData(ins_7));
//        String ins_8="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(2,'Travel','TRAIN','#880E4F','#F06292')";
//        Log.i("Insert 8",""+dbHelper.insertData(ins_8));
//        String ins_9="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(2,'Petrol','GAS_STATION','#FF5722','#FFAB91')";
//        Log.i("Insert 9",""+dbHelper.insertData(ins_9));
//        String ins_10="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(2,'Kids','BABY_BUGGY','#2E7D32','#81C784')";
//        Log.i("Insert 10",""+dbHelper.insertData(ins_10));
//        String ins_11="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(2,'Jewellery','DIAMOND','#FFEB3B','#FFF59D')";
//        Log.i("Insert 11",""+dbHelper.insertData(ins_11));
//        String ins_12="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(2,'Coffee','COFFEE_OUTLINE','#795548','#BCAAA4')";
//        Log.i("Insert 12",""+dbHelper.insertData(ins_12));
//        String ins_13="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(2,'Account','ACCOUNT_CARD_DETAILS','#006064','#00ACC1')";
//        Log.i("Insert 13",""+dbHelper.insertData(ins_13));
//        String ins_14="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(2,'Flight','AIRPLANE','#6A1B9A','#AB47BC')";
//        Log.i("Insert 14",""+dbHelper.insertData(ins_14));
//        String ins_15="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(2,'Bank','BANK','#3F51B5','#9FA8DA')";
//        Log.i("Insert 15",""+dbHelper.insertData(ins_15));
//        String ins_16="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(1,'Salary','CASH_MULTIPLE','#1B5E20','#66BB6A')";
//        Log.i("Insert 16",""+dbHelper.insertData(ins_16));
//        String ins_17="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(1,'Savings','COIN','#FFEB3B','#FFF176')";
//        Log.i("Insert 17",""+dbHelper.insertData(ins_17));


//        //Should be inserted for every data
//        String ins_18="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(2,'Add','PLUS_CIRCLE_OUTLINE','#388E3C','#388E3C')";
//        Log.i("Insert 18",""+dbHelper.insertData(ins_18));
//        String ins_19="insert into tb_category(type_id,category_user_name,category_name,foreground_color,background_color) values(1,'Add','PLUS_CIRCLE_OUTLINE','#388E3C','#388E3C')";
//        Log.i("Insert 19",""+dbHelper.insertData(ins_19));


        dbHelper.closeConnection();

    }

    private Boolean checkCategory(String code) {


        String sel_cuurency="SELECT category_id FROM tb_category WHERE category_name='"+code+"'";
        Cursor currency_cursor=dbHelper.selectData(sel_cuurency);

        Boolean b=false;

        if(currency_cursor.moveToNext())
        {
            b= true;
        }
        else
        {
            b= false;
        }


        return b;

    }


    public Boolean expenditureInsert(int transaction_id,int cat_id, String tb_amount, String tb_desc, String tb_date,int update) {

        dbHelper.openConnection();

        Boolean b=false;
        if(update==0)
        {
            //insert
            String ins="insert into tb_transaction(category_id,transaction_amount,transaction_date,transaction_description) values('" + cat_id + "','" + tb_amount + "','" + tb_date + "','" + tb_desc + "')";
            b = dbHelper.insertData(ins);
            Log.d("Insert",ins);
        }
        else if(update==1)
        {
            //update
            String upd="update tb_transaction set category_id='"+cat_id+"',transaction_amount='"+tb_amount+"',transaction_date='"+tb_date+"',transaction_description='"+tb_desc+"' where transaction_id="+transaction_id;
            b = dbHelper.insertData(upd);
            Log.d("Update",upd);
        }

        return b;

    }

    public ArrayList<Home_Year_Data> fetchYear(String year) {
        dbHelper.openConnection();


        //To show the data in the table
        Log.d("SQL Result Total","Transaction Id      Category ID         Amount      Date");

        String sel="SELECT transaction_id,category_id,transaction_amount,transaction_date transaction_amount FROM tb_transaction";

        Cursor c=dbHelper.selectData(sel);


        while (c.moveToNext()) {

            Log.d("SQL Result", c.getInt(0) + " " + c.getInt(1) + " " + c.getDouble(2) + " " + c.getString(3));

        }






// Actual Function starts here
        int transaction_id=0;
        String month="";
        Double income=0.0;
        Double expense=0.0;

        ArrayList<Home_Year_Data> arrayIncome=new ArrayList<>();
        ArrayList<Home_Year_Data> arrayExpense=new ArrayList<>();


        int category_id=0;
        Double amount=0.00;
        String date="";

        Home_Year_Data month_data;



//        String sel="SELECT transaction_id,category_id,transaction_amount,transaction_date FROM tb_transaction WHERE strftime('%Y',transaction_date) = strftime('%Y',date('now'))";
        String sel_income="SELECT t1.transaction_id,t1.category_id,t1.transaction_amount,t1.transaction_date,t2.category_id,t2.type_id, SUM(transaction_amount) AS Tot FROM tb_transaction t1,tb_category t2 WHERE  (strftime('%Y',t1.transaction_date) = '"+year+"'  and t1.category_id=t2.category_id and t2.type_id=1)GROUP BY CAST(strftime('%m',t1.transaction_date) AS VARCHAR(2)) + '-' + CAST(strftime('%Y',t1.transaction_date) AS VARCHAR(4)) order by t1.transaction_date desc";

        Cursor c_income=dbHelper.selectData(sel_income);

        Log.d("SQL Result Income","Transaction Id      Category ID         Amount      Date");

            while (c_income.moveToNext()) {

                Log.d("SQL Result", c_income.getInt(0) + " " + c_income.getInt(1) + " " + c_income.getDouble(2) + " " + c_income.getString(3));

                transaction_id=c_income.getInt(0);
                income=c_income.getDouble(6);
                date=c_income.getString(3);

                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("MMMM");
                Date date1=null;

                try {
                   date1 =sdf.parse(date);
                   month=simpleDateFormat.format(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                category_id=c_income.getInt(1);
                amount=c_income.getDouble(2);

                month_data=new Home_Year_Data();
                month_data.setTransaction_id(transaction_id);
                month_data.setIncome(income);
                month_data.setMonth(month);
                month_data.setTransaction_date(date);
                arrayIncome.add(month_data);


            }

        String sel_expense="SELECT t1.transaction_id,t1.category_id,t1.transaction_amount,t1.transaction_date,t2.category_id,t2.type_id, SUM(transaction_amount) AS Tot FROM tb_transaction t1,tb_category t2 WHERE  (strftime('%Y',t1.transaction_date) = '"+year+"' and t1.category_id=t2.category_id and t2.type_id=2)GROUP BY CAST(strftime('%m',t1.transaction_date) AS VARCHAR(2)) + '-' + CAST(strftime('%Y',t1.transaction_date) AS VARCHAR(4)) order by t1.transaction_date desc";

        Cursor c_expense=dbHelper.selectData(sel_expense);

        Log.d("SQL Result Expense","Transaction Id      Category ID         Amount      Date");

        while (c_expense.moveToNext()) {
            Log.d("SQL Result", c_expense.getInt(0) + " " + c_expense.getInt(1) + " " + c_expense.getDouble(2) + " " + c_expense.getString(3));

            transaction_id=c_expense.getInt(0);
            expense=c_expense.getDouble(6);
            date=c_expense.getString(3);

            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("MMMM");
            Date date1=null;

            try {
                date1 =sdf.parse(date);
                month=simpleDateFormat.format(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            category_id=c_expense.getInt(1);
            amount=c_expense.getDouble(2);
            date= c_expense.getString(3);

            month_data=new Home_Year_Data();
            month_data.setTransaction_id(transaction_id);
            month_data.setExpense(expense);
            month_data.setMonth(month);
            month_data.setTransaction_date(date);
            arrayExpense.add(month_data);



        }

//        ArrayList<Home_Year_Data> yearList= new ArrayList<>();
//
//        for(int i=0;i<yearList.size();i++)
//        {
//            Home_Year_Data m_data= yearList.get(i);
//            String m_month=m_data.getMonth();
//            Double m_income=m_data.getIncome();
//            Double m_expense=m_data.getExpense();
//            //ni rand array indaku arrayexpense and array income ,mmm
//
//        }


        ArrayList<Home_Year_Data> yearList= new ArrayList<>();
        yearList.clear();

        int innerCount=0;

        Home_Year_Data year_data;

        String y_date="";
        String y_month="";
        Double y_income=0.0;
        Double y_expense=0.0;
        Double balance=0.0;

        Iterator<Home_Year_Data> it = arrayIncome.iterator();


        while(it.hasNext()) {

//        for(Home_Year_Data year_income :  arrayIncome) {

            Home_Year_Data year_income=(Home_Year_Data)it.next();
            innerCount = 0;
            year_data = new Home_Year_Data();

//            y_month= year_income.getMonth(); //both months are equal here
//            month_list.add(y_month);
//            for (Home_Year_Data year_expense : arrayExpense) {

            Iterator<Home_Year_Data> it2 = arrayExpense.iterator();
            while(it2.hasNext()) {
                Home_Year_Data year_expense=(Home_Year_Data)it2.next();
//                y_date=year_income.getTransaction_date();
//                y_month= year_income.getMonth(); //both months are equal here
//                month_list.add(y_month);

                if (year_income.getMonth().equals(year_expense.getMonth())) {
                    innerCount++;

                    y_month = year_income.getMonth();
                    y_income = year_income.getIncome();
                    y_expense = year_expense.getExpense();

                    balance = y_income - y_expense;

//                    year_data=new Home_Year_Data();

                    year_data.setTransaction_date(y_date);
                    year_data.setMonth(y_month);
                    year_data.setIncome(y_income);
                    year_data.setExpense(y_expense);
                    year_data.setBalance(balance);


                        it.remove();
                        it2.remove();
                        yearList.add(year_data);
                    }
            }


            if (innerCount <= 0) {
                y_date = year_income.getTransaction_date();
                y_month = year_income.getMonth();
                y_income = year_income.getIncome();
                y_expense = 0.0;
                balance = y_income - y_expense;

                year_data.setTransaction_date(y_date);
                year_data.setMonth(y_month);
                year_data.setIncome(y_income);
                year_data.setExpense(y_expense);
                year_data.setBalance(balance);


                    it.remove();

//                arrayIncome.remove(year_data);

                yearList.add(year_data);

//                break;
            }
        }

        Iterator<Home_Year_Data> it3 = arrayExpense.iterator();

        while(it3.hasNext())
        {
//        for(Home_Year_Data year_expense: arrayExpense)
//        {

            Home_Year_Data year_expense=(Home_Year_Data)it3.next();

            year_data=new Home_Year_Data();

            y_date = year_expense.getTransaction_date();
            y_month = year_expense.getMonth();
            y_income = year_expense.getIncome();
            y_expense = year_expense.getExpense();
            balance = y_income - y_expense;

            year_data.setTransaction_date(y_date);
            year_data.setMonth(y_month);
            year_data.setIncome(y_income);
            year_data.setExpense(y_expense);
            year_data.setBalance(balance);

            it3.remove();

            yearList.add(year_data);

        }



            dbHelper.closeConnection();

        return yearList;
    }

    public void updatePassword(String password,String status)
    {
        dbHelper.openConnection();

        SharedPreferences sp=context.getSharedPreferences("User",Context.MODE_PRIVATE);
        String name=sp.getString("Name","");

        String upd="update tb_user set user_password='"+password+"' where user_name='"+name+"'";
        Boolean b=dbHelper.insertData(upd);

        if(b==true)
        {
            SharedPreferences sharedPreferences=context.getSharedPreferences("User",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();


            if(status.equalsIgnoreCase("set")) {
                Toast.makeText(context, "Successfully set password", Toast.LENGTH_SHORT).show();
                editor.putBoolean("Password",true);
            }
            else
            {
                editor.putBoolean("Password",false);
                Toast.makeText(context, "Successfully cleared password", Toast.LENGTH_SHORT).show();

            }
            editor.apply();
        }
        else
        {
            Toast.makeText(context, "Something went wrong try again", Toast.LENGTH_SHORT).show();
        }
        dbHelper.closeConnection();
    }

    public String fetchPassword()
    {
        dbHelper.openConnection();
        String pass="";

        SharedPreferences sp=context.getSharedPreferences("User",Context.MODE_PRIVATE);
        String name=sp.getString("Name","");

        String sel="select user_password from tb_user where user_name='"+name+"'";
        Cursor c=dbHelper.selectData(sel);

        if(c.moveToNext())
        {
            pass= c.getString(0);
        }
        else
        {
            Toast.makeText(context, "Something went wrong try again", Toast.LENGTH_SHORT).show();
            pass= "";
        }
        dbHelper.closeConnection();
        return pass;

    }

    public ArrayList<Month_Data> fetchMonth(String date) {

        dbHelper.openConnection();

        ArrayList<Month_Data> month_list=new ArrayList<>();

        String sel="select distinct t1.transaction_date  from tb_transaction t1 ,tb_category t2 where t1.category_id= t2.category_id and (strftime('%Y',t1.transaction_date) = strftime('%Y',"+date+") and strftime('%m',t1.transaction_date) = strftime('%m',"+date+")) ";
        Cursor cursor=dbHelper.selectData(sel);

        String transaction_date="";


        Month_Data month_data;

        while(cursor.moveToNext())
        {

            transaction_date=cursor.getString(1);

            month_data=new Month_Data(Month_Data.DATE_TYPE,transaction_date);

            month_list.add(month_data);


        }
        dbHelper.closeConnection();


        return month_list;
    }

    public ArrayList<Month_Data> fetchMonthData(String date) {

        dbHelper.openConnection();

        ArrayList<Month_Data> month_list=new ArrayList<>();

        String sel="select t1.transaction_id,t1.category_id,t2.category_name,t2.type_id,t1.transaction_amount,t1.transaction_date,t1.transaction_description from tb_transaction t1 ,tb_category t2 where t1.category_id= t2.category_id and (strftime('%Y',t1.transaction_date) = strftime('%Y','"+date+"') and strftime('%m',t1.transaction_date) = strftime('%m','"+date+"')) order by t1.transaction_date desc ";
        Cursor cursor=dbHelper.selectData(sel);

        int transaction_id=0;
        int category_id=0;
        String category_name="";
        int type_id=0;
        Double transaction_amount=0.0;
        String transaction_date="";
        String transaction_description="";
        String date1="";
        Month_Data month_data;

        Log.d("SQL result","transaction_id      t1.category_id      t2.category_name        t2.type_id      t1.transaction_amount       t1.transaction_date     t1.transaction_description ");
        while(cursor.moveToNext())
        {
            Log.d("SQL result",cursor.getInt(0)+" "+ cursor.getInt(1)+" "+cursor.getString(2)+" "+cursor.getInt(3)+" "+cursor.getDouble(4)+" "+cursor.getString(5)+" "+cursor.getString(6));
            if(!date1.equals(cursor.getString(5)))
            {
                date1=cursor.getString(5);
                month_data=new Month_Data(Month_Data.DATE_TYPE,date1);
                month_list.add(month_data);
            }
            transaction_id=cursor.getInt(0);
            category_id=cursor.getInt(1);
            category_name=cursor.getString(2);
            type_id=cursor.getInt(3);
            transaction_amount=cursor.getDouble(4);
            transaction_date=cursor.getString(5);
            transaction_description=cursor.getString(5);
            month_data=new Month_Data(Month_Data.MONTH_TYPE,transaction_id,category_id,category_name,type_id,transaction_amount,transaction_date,transaction_description);
            month_list.add(month_data);

//            transaction_id =cursor.getInt(0);
//            category_id=cursor.getInt(1);
//            category_name=cursor.getString(2);
//            type_id=cursor.getInt(3);
//            transaction_amount=cursor.getDouble(4);
//            transaction_date=cursor.getString(5);
//            transaction_description=cursor.getString(6);
//
//            month_data=new Month_Data(Month_Data.MONTH_TYPE,transaction_id,category_id,category_name,type_id,transaction_amount,transaction_date,transaction_description);
//
////            month_data.setTransaction_id(transaction_id);
////            month_data.setCategory_id(category_id);
////            month_data.setCat_name(category_name);
////            month_data.setType_id(type_id);
////            month_data.setTransaction_amount(transaction_amount);
////            month_data.setTransaction_date(transaction_date);
////            month_data.setTransaction_description(transaction_description);
//
//            month_list.add(month_data);


        }
        dbHelper.closeConnection();


        return month_list;
    }

    public ArrayList<PieAnualData> fetchPieData(String date,String status)
    {
        dbHelper.openConnection();

        ArrayList<PieAnualData> pie_year_list=new ArrayList<>();
        String sel="";

        if (status.equalsIgnoreCase("year")) {

            sel = "select t1.category_id,t2.category_name,t1.transaction_date, SUM(transaction_amount),t2.type_id  from tb_transaction t1,tb_category t2 where t1.category_id= t2.category_id and strftime('%Y',t1.transaction_date)=strftime('%Y','"+date+"') group by t1.category_id ";

        }
        else if (status.equalsIgnoreCase("month"))
        {
            sel = "select t1.category_id,t2.category_name,t1.transaction_date, SUM(transaction_amount),t2.type_id from tb_transaction t1,tb_category t2 where t1.category_id= t2.category_id and strftime('%Y',t1.transaction_date)=strftime('%Y','"+date+"') and strftime('%m',t1.transaction_date)=strftime('%m','"+date+"') group by t1.category_id";

        }

        Cursor cursor=dbHelper.selectData(sel);;

        PieAnualData pieAnualData;

            while (cursor.moveToNext()) {

                pieAnualData = new PieAnualData();

                pieAnualData.setCategory_id(cursor.getInt(0));
                pieAnualData.setCategory_name(cursor.getString(1));
                pieAnualData.setTransaction_date(cursor.getString(2));
                pieAnualData.setTotal_amount(cursor.getDouble(3));
                pieAnualData.setType_id(cursor.getInt(4));

                pie_year_list.add(pieAnualData);


            }

            dbHelper.closeConnection();

            return pie_year_list;




    }


    public ArrayList<EditModel> fetchTransactionData(int transaction_id) {

        dbHelper.openConnection();

        int trans_id=0;
        String transaction_date="";
        Double transaction_amount=0.0;
        String transaction_desc="";
        int cat_id=0;
        String cat_name="";
        int type_id=0;
        String type_name="";

        EditModel editModel;
        ArrayList<EditModel> dataList=new ArrayList<>();
        dataList.clear();

        String sel="select t1.transaction_id,t1.transaction_date,t1.transaction_amount,t1.transaction_description,t1.category_id,t2.category_name,t2.type_id,t3.type_name from tb_transaction t1,tb_category t2, tb_type t3 where t1.category_id= t2.category_id and t1.transaction_id ="+transaction_id+" group by t1.transaction_id";
        Cursor cursor=dbHelper.selectData(sel);

        if(cursor.moveToNext())
        {
            trans_id=cursor.getInt(0);
            transaction_date=cursor.getString(1);
            transaction_amount=cursor.getDouble(2);
            transaction_desc=cursor.getString(3);
            cat_id=cursor.getInt(4);
            cat_name=cursor.getString(5);
            type_id=cursor.getInt(6);
            type_name=cursor.getString(7);

            editModel=new EditModel();

            editModel.setTrans_id(trans_id);
            editModel.setTransaction_date(transaction_date);
            editModel.setTransaction_amount(transaction_amount);
            editModel.setTransaction_desc(transaction_desc);
            editModel.setCat_id(cat_id);
            editModel.setCat_name(cat_name);
            editModel.setType_id(type_id);
            editModel.setType_name(type_name);

            dataList.add(editModel);

        }

        dbHelper.closeConnection();

        return dataList;
    }


    public Boolean deleteTransaction(int transaction_id) {

        dbHelper.openConnection();

        String del="delete from tb_transaction where transaction_id="+transaction_id;
        Boolean b=dbHelper.insertData(del);

        dbHelper.closeConnection();

        return b;
    }













    public void delete() {

        dbHelper.openConnection();
        String del="delete from tb_currency";
        if(dbHelper.insertData(del))
        {
            Toast.makeText(context, "successfully deleted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Delete went wrong", Toast.LENGTH_SHORT).show();
        }


    }

    public ArrayList<Currency_Model> fetchCurrencyList() {
        dbHelper.openConnection();

        Currency_Model currency_model;
        ArrayList<Currency_Model> myCurrencyList=new ArrayList<>();
        myCurrencyList.clear();

        String sel="Select currency_id,currency_name,currency_code,currency_symbol from tb_currency";
        Cursor c=dbHelper.selectData(sel);

        while(c.moveToNext())
        {
            currency_model=new Currency_Model();

            currency_model.setCurrency_id(c.getInt(0));
            currency_model.setCurrency_name(c.getString(1));
            currency_model.setCurrency_code(c.getString(2));
            currency_model.setCurrency_symbol(c.getString(3));

            myCurrencyList.add(currency_model);

        }


        dbHelper.closeConnection();

        return myCurrencyList;
    }


//    public void monthSort()
//    {
//        final Comparator<String> dateCompare = new Comparator<String>() {
//
//            public int compare(String o1, String o2) {
//
//                SimpleDateFormat s = new SimpleDateFormat("MMM");
//                Date s1 = null;
//                Date s2 = null;
//                try {
//                    s1 = s.parse(o1);
//                    s2 = s.parse(o2);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                return s1.compareTo(s2);
//            }
//        };
//
//        Collections.sort(months, dateCompare);
//
//    }

}

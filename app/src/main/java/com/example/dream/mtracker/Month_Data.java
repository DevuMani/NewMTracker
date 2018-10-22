package com.example.dream.mtracker;

import java.util.Date;

public class Month_Data {

    public static final int DATE_TYPE=0;
    public static final int MONTH_TYPE=1;

    int type=0;

    int transaction_id=0;
    int category_id=0;
    String cat_name="";
    int type_id=0;
    Double transaction_amount=0.00;
    String transaction_date="";
    String transaction_description="";

    Month_Data(int type,String date)
    {
        this.type=type;
        this.setTransaction_date(date);
    }

    Month_Data(int type,int transaction_id,int category_id,String cat_name,int type_id,Double transaction_amount,String date,String transaction_description)
    {
        this.type=type;
        this.setTransaction_id(transaction_id);
        this.setCategory_id(category_id);
        this.setCat_name(cat_name);
        this.setType_id(type_id);
        this.setTransaction_amount(transaction_amount);
        this.setTransaction_date(date);
        this.setTransaction_description(transaction_description);

    }
    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public Double getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(Double transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getTransaction_description() {
        return transaction_description;
    }

    public void setTransaction_description(String transaction_description) {
        this.transaction_description = transaction_description;
    }
}

package com.example.dream.mtracker;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by DevuMani on 10/5/2018.
 */

public class CurrencyListAdapters extends RecyclerView.Adapter<CurrencyListAdapters.MyViewHolder> {

    private Context mContext;
    private ArrayList<Currency_Model> currencyList;

    public CurrencyListAdapters(Context context, ArrayList<Currency_Model> currencyList) {

        mContext = context;
        this.currencyList = currencyList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.currency_cust_row, parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

       Currency_Model c_data= currencyList.get(position);

        holder.currency_text.setText(c_data.currency_symbol);


    }

    @Override
    public int getItemCount() {
//        Toast.makeText(mContext, "Size"+iconList.size(), Toast.LENGTH_SHORT).show();
        return currencyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView currency_text;
        public String symbol;

        public MyViewHolder(View itemView) {
            super(itemView);

            currency_text = itemView.findViewById(R.id.tv_row_currency);

            currency_text.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if(view == currency_text)
            {
                Currency_Model c_data= currencyList.get(getAdapterPosition());

                ChooseCurrency.choose_currency_symbol= String.valueOf(c_data.getCurrency_symbol());
//                Toast.makeText(mContext, "Symbol"+c_data.getCurrency_symbol(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}

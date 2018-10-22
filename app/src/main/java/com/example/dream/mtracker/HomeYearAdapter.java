package com.example.dream.mtracker;

import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Devu Mani on 10/5/2018.
 */

public class HomeYearAdapter extends RecyclerView.Adapter<HomeYearAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Home_Year_Data> yearList;

    public HomeYearAdapter(Context context, ArrayList<Home_Year_Data> yearList) {

        mContext = context;
        this.yearList = yearList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.home_year_cust_row, parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

       Home_Year_Data m_data= yearList.get(position);

       holder.month.setText(m_data.getMonth());
       if(m_data.getIncome()!=0.0)
       {
           String i=""+m_data.getIncome();
           holder.income.setText(i);
       }
       if(m_data.getExpense()!=0.0)
       {
           String e=""+m_data.getExpense();
           holder.expense.setText(e);
       }
       String bal=""+m_data.getBalance();
       holder.balance.setText(bal);






//        if(position== getItemCount()-1) {
//            holder.iconView.setColor(mContext.getResources().getColor(R.color.green));
//            holder.iconView.setSizeDp(60);
//        }
    }

    @Override
    public int getItemCount() {
//        Toast.makeText(mContext, "Size"+iconList.size(), Toast.LENGTH_SHORT).show();
        return yearList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView month,income,expense,balance;
//        public LinearLayout layout_income,layout_expense;
        public CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.home_year_cardView);
            month = itemView.findViewById(R.id.home_tv_month);
            income = itemView.findViewById(R.id.home_month_income);
            expense = itemView.findViewById(R.id.home_month_expense);
            balance = itemView.findViewById(R.id.home_month_balance);
//            layout_income = itemView.findViewById(R.id.home_month_income_layout);
//            layout_expense = itemView.findViewById(R.id.home_month_expense_layout);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {



            if(view == cardView)
            {

                Home_Year_Data y_data= yearList.get(getAdapterPosition());

                Toast.makeText(mContext, "Month"+y_data.getMonth(), Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext, "Date"+y_data.getTransaction_date(), Toast.LENGTH_SHORT).show();


                Intent intent=new Intent(mContext,Home_detail.class);
                intent.putExtra("Date",y_data.transaction_date);
                mContext.startActivity(intent);

//                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMMM");
//                SimpleDateFormat sdf=new SimpleDateFormat("MM");
//
//                String date=simpleDateFormat.format(y_data.getMonth());
//                Date date1=null;
//
//                try {
//                    date1=simpleDateFormat.parse(date);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                date=sdf.format(date1);
//
//                Toast.makeText(mContext, "Day:::"+date, Toast.LENGTH_SHORT).show();
            }
        }
    }

}

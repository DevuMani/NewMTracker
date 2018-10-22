package com.example.dream.mtracker;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;


/**
 * Created by Devu Mani on 10/5/2018.
 */

public class HomeDetailAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private ArrayList<Month_Data> monthList;

    public HomeDetailAdapter(Context context, ArrayList<Month_Data> monthList) {

        mContext = context;
        this.monthList = monthList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        switch(viewType)
        {
            case Month_Data.DATE_TYPE:

                v = LayoutInflater.from(mContext).inflate(R.layout.home_detail_cust_row, parent,false);
                DateTypeViewHolder dateTypeViewHolder = new DateTypeViewHolder(v);
                return dateTypeViewHolder;

            case Month_Data.MONTH_TYPE:

                v = LayoutInflater.from(mContext).inflate(R.layout.detail_expenditure_cust_row, parent,false);
                MonthTypeViewHolder monthTypeViewHolder = new MonthTypeViewHolder(v);
                return monthTypeViewHolder;


        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {


        switch (monthList.get(position).type) {
            case 0:
                return Month_Data.DATE_TYPE;
            case 1:
                return Month_Data.MONTH_TYPE;
            default:
                return -1;
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        Month_Data m_data = monthList.get(position);

        if(m_data!=null)
        {
            switch(m_data.type)
            {
                case Month_Data.DATE_TYPE:

                    ((DateTypeViewHolder) holder).date.setText(m_data.getTransaction_date());

                    break;

                case Month_Data.MONTH_TYPE:

                    MonthTypeViewHolder monthTypeViewHolder= (MonthTypeViewHolder)holder;//casts holder to monthtypeViewHolder.
                    String icon=m_data.getCat_name();
                    MaterialDrawableBuilder.IconValue iconValue=MaterialDrawableBuilder.IconValue.valueOf(icon);
                    monthTypeViewHolder.iconView.setIcon(iconValue);
                    monthTypeViewHolder.desc.setText(m_data.getTransaction_description());
                    if (m_data.getType_id()==1)
                    {
                        monthTypeViewHolder.amount.setTextColor(mContext.getColor(R.color.green));
                    }
                    else if (m_data.getType_id()==2)
                    {
                        monthTypeViewHolder.amount.setTextColor(mContext.getColor(R.color.red));
                    }
                    String amount=""+m_data.getTransaction_amount();
                    monthTypeViewHolder.amount.setText(amount);


            }
        }

//       if(m_data.getIncome()!=0.0)
//       {
//           String i=""+m_data.getIncome();
//           holder.income.setText(i);
//       }
//       if(m_data.getExpense()!=0.0)
//       {
//           String e=""+m_data.getExpense();
//           holder.expense.setText(e);
//       }
//       String bal=""+m_data.getBalance();
//       holder.balance.setText(bal);


    }

    @Override
    public int getItemCount() {
//        Toast.makeText(mContext, "Size"+iconList.size(), Toast.LENGTH_SHORT).show();
        return monthList.size();
    }

    public class DateTypeViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
//        public CardView cardView;
        public RecyclerView recyclerView;

        LinearLayoutManager linearLayoutManager;
        HomeDayAdapter dayAdapter;
        ArrayList<Month_Data> myDayData;


        public DateTypeViewHolder(View itemView) {
            super(itemView);

//            cardView = itemView.findViewById(R.id.home_detail_cardView);
            date = itemView.findViewById(R.id.home_detail_tv_day);
//            recyclerView = itemView.findViewById(R.id.home_detail_expenditure);

//            cardView.setOnClickListener(this);
        }


//        @Override
//        public void onClick(View view) {
//
//
//            if (view == cardView) {
//
//                Month_Data m_data = monthList.get(getAdapterPosition());
//
////                Toast.makeText(mContext, "Month"+y_data.getMonth(), Toast.LENGTH_SHORT).show();
////                Toast.makeText(mContext, "Date"+y_data.getTransaction_date(), Toast.LENGTH_SHORT).show();
//
////                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMMM");
////                SimpleDateFormat sdf=new SimpleDateFormat("MM");
////
////                String date=simpleDateFormat.format(y_data.getMonth());
////                Date date1=null;
////
////                try {
////                    date1=simpleDateFormat.parse(date);
////                } catch (ParseException e) {
////                    e.printStackTrace();
////                }
////                date=sdf.format(date1);
////
////                Toast.makeText(mContext, "Day:::"+date, Toast.LENGTH_SHORT).show();
//            }
//        }
    }

    public class MonthTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MaterialIconView iconView;
        public TextView desc,amount;
        public CardView cardView;

        public MonthTypeViewHolder(View itemView) {
            super(itemView);

            cardView= itemView.findViewById(R.id.detail_month_cardView);
            iconView = itemView.findViewById(R.id.expenditure_iconview);
            desc = itemView.findViewById(R.id.expenditure_description);
            amount = itemView.findViewById(R.id.expenditure_amount);

            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            if (view == cardView) {

                Month_Data m_data = monthList.get(getAdapterPosition());

                Toast.makeText(mContext, "ID" +m_data.getTransaction_id(), Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(mContext,ExpenditureDisplay.class);
                intent.putExtra("ID",m_data.getTransaction_id());
                mContext.startActivity(intent);


            }
        }
    }

}
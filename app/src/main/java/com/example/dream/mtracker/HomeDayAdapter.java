package com.example.dream.mtracker;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;


/**
 * Created by Devu Mani on 10/5/2018.
 */

public class HomeDayAdapter extends RecyclerView.Adapter<HomeDayAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Month_Data> monthList;



    public HomeDayAdapter(Context context, ArrayList<Month_Data> monthList) {

        mContext = context;
        this.monthList = monthList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.detail_expenditure_cust_row, parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Month_Data m_data = monthList.get(position);

        MaterialDrawableBuilder.IconValue icon= MaterialDrawableBuilder.IconValue.valueOf(m_data.getCat_name());

        holder.iconView.setIcon(icon);

        holder.desc.setText(m_data.getTransaction_description());
        holder.desc.setText(m_data.getTransaction_amount().toString());
//       holder.month.setText(m_data.getMonth());
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView desc, amount;
        public CardView cardView;
        public MaterialIconView iconView;


        public MyViewHolder(View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.home_detail_cardView);
            iconView = itemView.findViewById(R.id.expenditure_iconview);
            desc = itemView.findViewById(R.id.expenditure_description);
            amount=itemView.findViewById(R.id.expenditure_amount);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {



            if(view == cardView)
            {

                Month_Data m_data= monthList.get(getAdapterPosition());

//                Toast.makeText(mContext, "Month"+y_data.getMonth(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(mContext, "Date"+y_data.getTransaction_date(), Toast.LENGTH_SHORT).show();

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

package com.example.dream.mtracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;
/**
 * Created by DevuMani on 10/5/2018.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {

    private Context mContext;
    static int sSelected=-1;
    private ArrayList<Category_Data> iconList;

    public CategoryListAdapter(Context context, ArrayList<Category_Data> iconList) {

        mContext = context;
        this.iconList = iconList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.category_cust_row, parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

       final Category_Data c_data= iconList.get(position);
        final AnimatedVectorDrawable iconfill= (AnimatedVectorDrawable) mContext.getDrawable(R.drawable.icon_background_fill);
        final AnimatedVectorDrawable no_iconfill= (AnimatedVectorDrawable) mContext.getDrawable(R.drawable.icon_back_to_normal);
       MaterialDrawableBuilder.IconValue a=MaterialDrawableBuilder.IconValue.valueOf(c_data.getCategory_name());

        holder.iconView.setBackground(iconfill);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sSelected=position;
                IncomeExpenseActivity.iconsymbol= String.valueOf(c_data.getCategory_name());
                selectedItem();
            }
        });
        if(sSelected==position){
            iconfill.start();
            iconfill.registerAnimationCallback(new Animatable2.AnimationCallback() {
                @Override
                public void onAnimationStart(Drawable drawable) {
                    holder.iconView.setColor(Color.WHITE);
                }

                @Override
                public void onAnimationEnd(Drawable drawable) {
                    holder.iconView.setBackground(no_iconfill);


                }
            });

        }
        else {
            if(holder.iconView.getBackground().equals(no_iconfill)){
                no_iconfill.start();
                no_iconfill.registerAnimationCallback(new Animatable2.AnimationCallback() {
                    @Override
                    public void onAnimationStart(Drawable drawable) {
                        holder.iconView.setColor(mContext.getResources().getColor(R.color.icon_color));
                    }

                    @Override
                    public void onAnimationEnd(Drawable drawable) {
                        holder.iconView.setBackground(iconfill);

                    }
                });

            }
        }

        if(position== getItemCount()-1) {
            holder.iconView.setColor(mContext.getResources().getColor(R.color.green));
            holder.iconView.setSizeDp(60);
        }
    }

    @Override
    public int getItemCount() {
//        Toast.makeText(mContext, "Size"+iconList.size(), Toast.LENGTH_SHORT).show();
        return iconList.size();
    }

    public void selectedItem(){
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {

        public MaterialIconView iconView;
        public String symbol;
        View mView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            iconView = itemView.findViewById(R.id.iconView_single_row_iconholder);

        }

    }

}
